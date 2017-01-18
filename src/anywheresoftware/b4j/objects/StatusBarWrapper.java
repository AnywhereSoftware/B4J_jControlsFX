package anywheresoftware.b4j.objects;

import org.controlsfx.control.StatusBar;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.DependsOn;
import anywheresoftware.b4a.BA.Hide;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.collections.Map;
import anywheresoftware.b4j.objects.CustomViewWrapper.DesignerCustomView;
import anywheresoftware.b4j.objects.NodeWrapper.ControlWrapper;
import anywheresoftware.b4j.objects.PaneWrapper.ConcretePaneWrapper;

/**
 * A bar that is usually added at the bottom of the form.
 *It shows a label at the left side and a progress bar at the right side.
 *You can add more nodes.
 */
@DependsOn(values={"controlsfx-8"})
@ShortName("StatusBar")
public class StatusBarWrapper extends ControlWrapper<StatusBar> implements DesignerCustomView{
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
			setObject(new StatusBar());
		super.innerInitialize(ba, eventName, true);
		getObject().setText("");
	}
	/**
	 * Gets or sets the default label text value.
	 */
	public String getText() {
		return getObject().getText();
	}
	public void setText(String s) {
		getObject().setText(s);
	}
	/**
	 * Gets or sets the progress bar value. This is a value between 0 to 1.
	 */
	public double getProgress() {
		return getObject().getProgress();
	}
	public void setProgress(double d) {
		getObject().setProgress(d);
	}
	/**
	 * Returns a List of the nodes at the left side. You can add nodes to this list.
	 */
	public List getLeftItems() {
		return (List)AbsObjectWrapper.ConvertToWrapper(new List(), getObject().getLeftItems());
	}
	/**
	 * Returns a List of the nodes at the right side. You can add nodes to this list.
	 */
	public List getRightItems() {
		return (List)AbsObjectWrapper.ConvertToWrapper(new List(), getObject().getRightItems());
	}
	
	
}
