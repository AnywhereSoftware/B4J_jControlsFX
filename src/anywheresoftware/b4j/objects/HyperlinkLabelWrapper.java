package anywheresoftware.b4j.objects;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

import org.controlsfx.control.HyperlinkLabel;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.DesignerProperties;
import anywheresoftware.b4a.BA.DontInheritEvents;
import anywheresoftware.b4a.BA.Events;
import anywheresoftware.b4a.BA.Hide;
import anywheresoftware.b4a.BA.Property;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.objects.collections.Map;
import anywheresoftware.b4j.objects.CustomViewWrapper.DesignerCustomView;
import anywheresoftware.b4j.objects.NodeWrapper.ControlWrapper;
import anywheresoftware.b4j.objects.PaneWrapper.ConcretePaneWrapper;
@ShortName("HyperlinkLabel")
@Events(values={"Click (Link As String)"})
@DontInheritEvents
@DesignerProperties(values = {
	@Property(key="Text", displayName="Text", fieldType="String", defaultValue="", description="Text between square brackets will be treated as a link")	
})
public class HyperlinkLabelWrapper extends ControlWrapper<HyperlinkLabel> implements DesignerCustomView{
	@Override
	public void DesignerCreateView(final ConcretePaneWrapper base, LabelWrapper lbl,
			Map args) {
		base.AddNode(getObject(), 0, 0, base.getWidth(), base.getHeight());
		getObject().setText((String)args.Get("Text"));
		getObject().setStyle(lbl.getStyle());
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
	@Override
	public void _initialize(BA arg0, Object arg1, String arg2) {
		innerInitialize(arg0, arg2.toLowerCase(BA.cul), false);
	}
	@Override
	@Hide
	public void innerInitialize(final BA ba, final String eventName, boolean keepOldObject) {
		if (!keepOldObject)
			setObject(new HyperlinkLabel());
		super.innerInitialize(ba, eventName, true);
		if (ba.subExists(eventName + "_click")) {
			getObject().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					Hyperlink link = (Hyperlink)event.getSource();
			        if (link != null)
			        	ba.raiseEventFromUI(getObject(), eventName + "_click", link.getText());
				}
				
			});
		}
	}
	/**
	 * Gets or sets the text. Text between square brackets will be treated as a link.
	 */
	public void setText(String s) {
		getObject().setText(s);
	}
	public String getText() {
		return getObject().getText();
	}
	
}
