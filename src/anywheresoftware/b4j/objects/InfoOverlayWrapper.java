package anywheresoftware.b4j.objects;

import javafx.scene.Node;

import org.controlsfx.control.InfoOverlay;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.Hide;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4j.objects.NodeWrapper.ControlWrapper;

@ShortName("InfoOverlay")
public class InfoOverlayWrapper extends ControlWrapper<InfoOverlay>{
	@Override
	@Hide
	public void innerInitialize(final BA ba, final String eventName, boolean keepOldObject) {
		if (!keepOldObject)
			setObject(new InfoOverlay());
		super.innerInitialize(ba, eventName, true);
	}
	/**
	 * Gets or sets the inner node.
	 */
	public ConcreteNodeWrapper getInnerNode() {
		return (ConcreteNodeWrapper)AbsObjectWrapper.ConvertToWrapper(new ConcreteNodeWrapper(), getObject().getContent());
	}
	public void setInnerNode(Node Node) {
		getObject().setContent(Node);
		ControlsUtils.SetSize(Node, getPrefWidth(), getPrefHeight());
	}
	/**
	 * Gets or sets whether the overlay expands when the mouse hovers over the inner node.
	 *Default value is True.
	 */
	public boolean getShowOnHover() {
		return getObject().isShowOnHover();
	}
	public void setShowOnHover(boolean b) {
		getObject().setShowOnHover(b);
	}
	/**
	 * Gets or sets the overlay text.
	 */
	public String getText() {
		return getObject().getText();
	}
	public void setText(String s) {
		getObject().setText(s);
	}
	
}
