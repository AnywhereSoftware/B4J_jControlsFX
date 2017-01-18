package anywheresoftware.b4j.objects;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;

import org.controlsfx.control.SegmentedButton;

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
 * A control that holds a set of toggle buttons.
 */
@DontInheritEvents
@Events(values={"ValueChanged (Value As String)"})
@ShortName("SegmentedButton")
@DesignerProperties(values={
		@Property(key="DarkStyle", displayName="Dark Style", fieldType="boolean", defaultValue="false"),
		@Property(key="Items", displayName="Items", fieldType="String", defaultValue="", 
			description="Comma separated list of items.")
})
public class SegmentedButtonWrapper extends ControlWrapper<SegmentedButton> implements DesignerCustomView{
	@Override
	public void DesignerCreateView(final ConcretePaneWrapper base, LabelWrapper label,
			Map args) {
		base.AddNode(getObject(), 0, 0, base.getWidth(), base.getHeight());
		base.getObject().setMinHeight(40);
		base.getObject().setMinWidth(80);
		setDarkStyle((Boolean)args.Get("DarkStyle"));
		SetItems(Common.ArrayToList(((String)args.Get("Items")).split(",")));
		new PaneWrapper.ResizeEventManager(base.getObject(), null, new Runnable() {
			
			@Override
			public void run() {
				SetLayoutAnimated(0, 0, 0, base.getWidth(), base.getHeight());
			}
		});

		
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
			setObject(new SegmentedButton());
		super.innerInitialize(ba, eventName, true);
		getObject().getToggleGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>() {


			@Override
			public void changed(ObservableValue<? extends Toggle> observable,
					Toggle oldValue, Toggle newValue) {
				ToggleButton tb = (ToggleButton)newValue;
				ba.raiseEventFromUI(getObject(), eventName + "_valuechanged", tb != null ? tb.getText(): "");
			}
		});
	}
	/**
	 * Gets or sets the selected item. Will return an empty string if no item is selected.
	 */
	public String getSelectedItem() {
		Toggle tb = getObject().getToggleGroup().getSelectedToggle();
		if (tb == null)
			return "";
		return ((ToggleButton)tb).getText();
	}
	public void setSelectedItem(String s) {
		for (ToggleButton t : getObject().getButtons()) {
			if (t.getText().equals(s)) {
				t.setSelected(true);
			}
			else {
				t.setSelected(false);
			}
				
		}
	}
	/**
	 * Sets the items.
	 *Example:<code>
	 *sbtn.SetItems(Array("A", "BBB", "CCC"))</code>
	 */
	public void SetItems(List Items) {
		getObject().getButtons().clear();
		ArrayList<ToggleButton> tbs = new ArrayList<ToggleButton>();
		for (Object o : Items.getObject()) {
			tbs.add(new ToggleButton(String.valueOf(o)));
		}
		getObject().getButtons().addAll(tbs);
	}
	/**
	 * Set to true to use dark style instead of the default style.
	 */
	public void setDarkStyle(boolean b) {
		if (b)
			getObject().getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);
		else
			getObject().getStyleClass().remove(SegmentedButton.STYLE_CLASS_DARK);
	}
	public boolean getDarkStyle() {
		return getObject().getStyleClass().contains(SegmentedButton.STYLE_CLASS_DARK);
	}
}
