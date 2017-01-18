package anywheresoftware.b4j.objects;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.Hide;
import anywheresoftware.b4a.BA.ShortName;
/**
 *A vertical separator. Can be used with StatusBar to separate nodes.
 */
@ShortName("Separator")
public class SeparatorWrapper extends NodeWrapper<Separator> {
	@Hide
	public void innerInitialize(final BA ba, final String eventName, boolean keepOldObject) {
		if (!keepOldObject)
			setObject(new Separator(Orientation.VERTICAL));
		super.innerInitialize(ba, eventName, true);
		
	}
	
}
