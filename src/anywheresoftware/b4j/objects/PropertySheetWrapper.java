package anywheresoftware.b4j.objects;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map.Entry;
import java.util.Optional;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import org.controlsfx.control.PropertySheet;
import org.controlsfx.control.PropertySheet.Item;
import org.controlsfx.property.editor.DefaultPropertyEditorFactory;
import org.controlsfx.property.editor.Editors;
import org.controlsfx.property.editor.PropertyEditor;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.Hide;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.collections.Map;
import anywheresoftware.b4j.objects.JFX.Colors;
import anywheresoftware.b4j.objects.JFX.FontWrapper;
import anywheresoftware.b4j.objects.JFX.PaintWrapper;
import anywheresoftware.b4j.objects.NodeWrapper.ControlWrapper;

@ShortName("PropertySheet")
public class PropertySheetWrapper extends ControlWrapper<PropertySheet>{
	@Override
	@Hide
	public void innerInitialize(final BA ba, final String eventName, boolean keepOldObject) {
		if (!keepOldObject)
			setObject(new PropertySheet());
		super.innerInitialize(ba, eventName, true);
		getObject().setPropertyEditorFactory(new DefaultPropertyEditorFactory() {
			@Override 
			public PropertyEditor<?> call(Item item) {
				TypePropertyItem tpi = (TypePropertyItem)item;
				if (tpi.metaData.choices != null) {
					return Editors.createChoiceEditor(tpi, tpi.metaData.choices.getObject());
				}
		        return super.call(item);
			}
		});
	}
	/**
	 * Sets the type instance that holds the data and a Map with the meta data that describes each of the fields.
	 */
	public void Set(Object DataObject, Map MetaData) throws NoSuchFieldException, SecurityException {
		ObservableList<Item> items = FXCollections.observableArrayList();
		for (Entry<Object, Object> e : MetaData.getObjectOrNull().entrySet()) {
			String fieldName = (String)e.getKey();
			PropertyMetaData pmd = (PropertyMetaData)e.getValue();
			Field f = DataObject.getClass().getDeclaredField(fieldName);
			TypePropertyItem tpi;
			if (f.getType() == FontWrapper.class) { //fonts
				tpi = new TypePropertyItem(f, DataObject, pmd) {
					@Override
					public Class<?> getType() {
						return Font.class;
					}
					@Override
					public Object getValue() {
						try {
							FontWrapper fw = (FontWrapper) field.get(target);
							if (fw.IsInitialized() == false)
								return JFX.DefaultFont(12).getObject();
							return fw.getObject();
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}

					@Override
					public void setValue(Object value) {
						try {
							field.set(target, AbsObjectWrapper.ConvertToWrapper(new FontWrapper(), value));
						} catch (Exception e) {
							throw new RuntimeException(e);
						}

					}
				};
			} else if (f.getType() == PaintWrapper.class) { //color
				tpi = new TypePropertyItem(f, DataObject, pmd) {
					@Override
					public Class<?> getType() {
						return Color.class;
					}
					@Override
					public Object getValue() {
						try {
							PaintWrapper fw = (PaintWrapper) field.get(target);
							if (fw.IsInitialized() == false)
								return Colors.Black;
							return fw.getObject();
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}

					@Override
					public void setValue(Object value) {
						try {
							field.set(target, AbsObjectWrapper.ConvertToWrapper(new PaintWrapper(), value));
						} catch (Exception e) {
							throw new RuntimeException(e);
						}

					}
				};
			}else if (f.getType() == long.class) { //date
				tpi = new TypePropertyItem(f, DataObject, pmd) {
					@Override
					public Class<?> getType() {
						return LocalDate.class;
					}
					@Override
					public Object getValue() {
						try {
							long value = (Long) field.get(target);
							Instant i = Instant.ofEpochMilli(value);
							ZonedDateTime zdt = ZonedDateTime.ofInstant(i, ZoneId.systemDefault());
							return zdt.toLocalDate();
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}

					@Override
					public void setValue(Object value) {
						try {
							field.set(target, LocaleDateToLong((LocalDate) value));
						} catch (Exception e) {
							throw new RuntimeException(e);
						}

					}
				};
			}
			else {
				tpi = new TypePropertyItem(f, DataObject, pmd); //other
			}
			items.add(tpi);
		}
		getObject().getItems().setAll(items);
		
	}
	/**
	 * Creates a PropertyMetaData object that describes a single property.
	 *Name - The text that will appear at the left side of the property.
	 *Category - The category that holds this property.
	 *Description - Longer text that appears when as the property tool tip. 
	 */
	public PropertyMetaData CreateMeta(String Name, String Category, String Description) {
		return new PropertyMetaData(Name, Category, Description);
	}
	/**
	 * Gets or sets whether the search box is visible.
	 */
	public boolean getSearchBoxVisible() {
		return getObject().isSearchBoxVisible();
	}
	public void setSearchBoxVisible(boolean b) {
		getObject().setSearchBoxVisible(b);
	}
	/**
	 * Gets or sets whether the mode switcher is visible.
	 */
	public boolean getModeSwitcherVisible() {
		return getObject().isModeSwitcherVisible();
	}
	public void setModeSwitcherVisible(boolean b) {
		getObject().setModeSwitcherVisible(b);
	}
	
	private long LocaleDateToLong(LocalDate ld) {
		if (ld == null)
			return 0;
		return ld.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	/**
	 * Meta-data describing a single property field.
	 */
	@ShortName("PropertyMetaData")
	public static class PropertyMetaData {
		public String Name;
		public String Category;
		public String Description;
		List choices; 
		boolean Readonly;
		public PropertyMetaData() {
			Name = "";
			Category = "";
			Description = "";
		}
		public PropertyMetaData(String name, String category, String description) {
			Name = name;
			Category = category;
			Description = description;
		}
		/**
		 * Limits the possible values. Only supported for strings and numbers types.
		 */
		public PropertyMetaData SetChoices(List Values) {
			this.choices = Values;
			return this;
		}
		public PropertyMetaData SetReadonly() {
			this.Readonly = true;
			return this;
		}

	}

	@Hide
	public static class TypePropertyItem implements Item {
		protected final Field field;
		protected final Object target;
		public final PropertyMetaData metaData;
		
		public TypePropertyItem(Field field, Object target, PropertyMetaData metaData) {
			this.field = field;
			this.target = target;
			this.metaData = metaData;

		}

		
		@Override
		public String getCategory() {
			return metaData.Category;
		}

		@Override
		public String getName() {
			return metaData.Name;
		}

		@Override
		public String getDescription() {
			return metaData.Description;
		}
		@Override
		public Class<?> getType() {
			return field.getType();
		}
		@Override
		public Object getValue() {
			try {
				return field.get(target);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void setValue(Object value) {
			try {
				field.set(target, value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}
		public boolean isEditable() {
            return !metaData.Readonly;
        }


		public Optional<ObservableValue<? extends Object>> getObservableValue() {
			return Optional.empty();
		}
		
	}
}
