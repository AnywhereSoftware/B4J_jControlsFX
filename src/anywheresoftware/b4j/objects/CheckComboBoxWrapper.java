package anywheresoftware.b4j.objects;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import org.controlsfx.control.CheckComboBox;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.DesignerProperties;
import anywheresoftware.b4a.BA.DontInheritEvents;
import anywheresoftware.b4a.BA.Events;
import anywheresoftware.b4a.BA.Hide;
import anywheresoftware.b4a.BA.Property;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.collections.Map;
import anywheresoftware.b4j.objects.CustomViewWrapper.DesignerCustomView;
import anywheresoftware.b4j.objects.NodeWrapper.ControlWrapper;
import anywheresoftware.b4j.objects.PaneWrapper.ConcretePaneWrapper;
/**
 * A ComboBox control. Each item is made of a string and check box.
 *The CheckedChange event happens whenever one of the items is checked or unchecked (whether by the user or by code).
 */
@ShortName("CheckComboBox")
@DontInheritEvents
@Events(values={"CheckedChanged"})
@DesignerProperties(values={
		@Property(key="Items", displayName="Items", defaultValue="Item #1|Item #2|Item #3", fieldType="string"),
			
	})
public class CheckComboBoxWrapper extends ControlWrapper<CheckComboBox<String>> implements DesignerCustomView{
	@Override
	public void DesignerCreateView(final ConcretePaneWrapper base, LabelWrapper label,
			Map args) {
		base.AddNode(getObject(), 0, 0, base.getWidth(), base.getHeight());
		new PaneWrapper.ResizeEventManager(base.getObject(), null, new Runnable() {
			
			@Override
			public void run() {
				SetLayoutAnimated(0, 0, 0, base.getWidth(), base.getHeight());
			}
		});
		getItems().AddAll(Common.ArrayToList(((String)args.Get("Items")).split("\\|")));
		getObject().setStyle(label.getStyle()); //set the font style

		
	}
	/**
	 * Returns the base pane. When the control is added as a custom view it is added to a base pane.
	 *You can use BaseView to move or remove the view.
	 */
	public ConcretePaneWrapper getBaseView() {
		return (ConcretePaneWrapper)AbsObjectWrapper.ConvertToWrapper(new ConcretePaneWrapper(), getObject().getParent());
	}
	@Hide
	@Override
	public void _initialize(BA ba, Object arg1, String EventName) {
		innerInitialize(ba, EventName.toLowerCase(BA.cul), false);
	}
	@Override
	@Hide
	public void innerInitialize(final BA ba, final String eventName, boolean keepOldObject) {
		if (!keepOldObject)
			setObject(new CheckComboBox<String>());
		super.innerInitialize(ba, eventName, true);
		final Object sender = getObject();
		if (ba.subExists(eventName + "_checkedchanged")) {
			getObject().getCheckModel().getCheckedIndices().addListener(new ListChangeListener<Integer>() {
				@Override
				public void onChanged(
						javafx.collections.ListChangeListener.Change<? extends Integer> c) {
					ba.raiseEvent(sender, eventName + "_checkedchanged");
				}
			});
		}
	}
	/**
	 * Returns a List with the items. You can add strings to this list.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getItems() {
		List l = new List();
		l.setObject((ObservableList) getObject().getItems());
		return l;
	}
	/**
	 * Checks or unchecks all items.
	 */
	public void CheckAll(boolean Checked) {
		if (Checked)
			getObject().getCheckModel().checkAll();
		else
			getObject().getCheckModel().clearChecks();
	}
	/**
	 * Sets the checked state of the item at the given index.
	 */
	public void SetChecked(int Index, boolean Checked) {
		if (Checked)
			getObject().getCheckModel().check(Index);
		else
			getObject().getCheckModel().clearCheck(Index);
	}
	/**
	 * Returns true if the item at the given index is checked.
	 */
	public boolean IsChecked(int Index) {
		return getObject().getCheckModel().isChecked(Index);
	}
	/**
	 * Returns a list with the indices of the checked items.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List GetCheckedIndices() {
		List l = new List();
		l.setObject((ObservableList) getObject().getCheckModel().getCheckedIndices());
		return l;
	}

}
