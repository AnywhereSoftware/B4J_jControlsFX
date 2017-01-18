package anywheresoftware.b4j.objects;

import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;

import org.controlsfx.control.BreadCrumbBar;
import org.controlsfx.control.BreadCrumbBar.BreadCrumbActionEvent;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.DesignerProperties;
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
 * A bar with one or more segments that allow the user to go back to a previous state.
 */
@ShortName("BreadCrumbBar")
@Events(values={"CrumbAction (Selected As String)"})
@DesignerProperties(values={
	@Property(key="Items", displayName="Items", defaultValue="Item #1|Item #2|Item #3", fieldType="string"),
	@Property(key="AutoNavigationEnabled", displayName="AutoNavigationEnabled", defaultValue="True", fieldType="Boolean")
		
})
public class BreadCrumbBarWrapper extends ControlWrapper<BreadCrumbBar<String>> implements DesignerCustomView{
	
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
		setAutoNavigationEnabled((Boolean)args.Get("AutoNavigationEnabled"));
		SetItems(Common.ArrayToList(((String)args.Get("Items")).split("\\|")));
		

		
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
	
	
	
	public void innerInitialize(final BA ba, final String eventName, boolean keepOldObject) {
		if (!keepOldObject)
			setObject(new BreadCrumbBar<String>());
		super.innerInitialize(ba, eventName, true);
		if (ba.subExists(eventName + "_crumbaction")) {
			getObject().setOnCrumbAction(new EventHandler<BreadCrumbActionEvent<String>>() {


				@Override
				public void handle(BreadCrumbActionEvent<String> event) {
					ba.raiseEventFromUI(getObject(), eventName + "_crumbaction", event.getSelectedCrumb().getValue());
					event.consume();
				}
			});
		}
	}
	/**
	 * Gets or sets whether the items will collapse automatically when clicked. Default value is true.
	 */
	public boolean getAutoNavigationEnabled() {
		return getObject().isAutoNavigationEnabled();
	}
	public void setAutoNavigationEnabled(boolean b) {
		getObject().setAutoNavigationEnabled(b);
	}
	/**
	 * Returns the value of the right most item.
	 */
	public String getSelectedValue() {
		return getObject().getSelectedCrumb().getValue();
	}
	/**
	 * Sets the items.
	 */
	public void SetItems(List Items) {
		TreeItem<String> ti = null;
		for (Object s : Items.getObject()) {
			TreeItem<String> t = new TreeItem<String>(String.valueOf(s));
			if (ti != null)
				ti.getChildren().add(t);
			ti = t;
		}
		getObject().setSelectedCrumb(ti);
	}
	/**
	 * Returns a list with the current items.
	 */
	public List GetItems() {
		List l1 = new List();
		l1.Initialize();
		TreeItem<String> ti = getObject().getSelectedCrumb();
		while (ti != null){
			l1.InsertAt(0, ti.getValue());
			ti = ti.getParent();
		}
		return l1;
	}
}
