package anywheresoftware.b4j.objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.decoration.Decorator;
import org.controlsfx.control.decoration.GraphicDecoration;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import anywheresoftware.b4a.BA.Hide;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.BA.Version;
@ShortName("ControlsUtils")
@Version(1.50f)
public class ControlsUtils {
	private static  boolean registeredFontAwesome;
	static void registerFontAwesome() {
		if (registeredFontAwesome)
			return;
		registeredFontAwesome = true;
		GlyphFontRegistry.register(new FontAwesome());
	}
	/**
	 * This method is deprecated and does not do anything.
	 */
	public void DisableCssWarnings() {
		//Logging.getCSSLogger().setLevel(Level.OFF);
	}
	/**
	 * Sets the control background color.
	 *ImageView, Canvas and WebView do not support this method.
	 */
	public void SetBackgroundColor(Node Node, Paint Color, double CornersRadius) {
		if (Node instanceof Region) {
			((Region)Node).setBackground(new Background(new BackgroundFill(Color, new CornerRadii(CornersRadius), Insets.EMPTY)));
		} else {
			System.out.println("Unsupported type of node: " + Node.getClass());
		}
	}
	/**
	 * Sets the control background image.
	 *ImageView, Canvas and WebView do not support this method.
	 */
	public void SetBackgroundImage(Node Node, Image Image, boolean Tile) {
		if (Node instanceof Region) {
			BackgroundImage bi;
			if (Tile)
				bi = new BackgroundImage(Image, null, null, null, null);
			else
				bi = new BackgroundImage(Image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, 
						true, true, true, false));
			((Region)Node).setBackground(new Background(bi));
		} else {
			System.out.println("Unsupported type of node: " + Node.getClass());
		}
	}
	/**
	 *Adds a decoration image to the node.
	 *Position - Decoration position.
	 *The possible values are: TOP_LEFT, TOP_CENTER, TOP_RIGHT,
	 *	CENTER_LEFT, CENTER, CENTER_RIGHT,
	 *	BOTTOM_LEFT, BOTTOM_CENTER and BOTTOM_RIGHT.
	 *OffsetX / OffsetY - Offset from the corner measured in pixels.
	 *
	 */
	public void AddDecoration(Node Node, Image Image, String Position, double OffsetX, double OffsetY) {
		Decorator.addDecoration(Node, new GraphicDecoration(new ImageView(Image), getPosition(Position),
				OffsetX, OffsetY));
	}
	@Hide
	public Pos getPosition(String Position) {
		return Enum.valueOf(Pos.class, Position);
	}
	/**
	 * Removes all decorations from the given node.
	 */
	public void RemoveDecorations(Node Node) {
		Decorator.removeAllDecorations(Node);
	}
	public static final int ICON_NONE = 0;
	public static final int ICON_WARNING = 1;
	public static final int ICON_CONFIRM = 2;
	public static final int ICON_INFORMATION = 3;
	public static final int ICON_ERROR = 4;
	/**
	 * Shows a notification message in the bottom right corner of the main screen. The message will disappear after 5 seconds.
	 *Title - Message title.
	 *Text - Message Text.
	 *Icon - One of the ICON constants.
	 */
	public void ShowNotification(String Title, String Text, int Icon) {
		ShowNotification2(Title, Text, Icon, null);
	}
	/**
	 * Similar to ShowNotification. If the Owner property is not null the message will appear inside the form.
	 */
	public void ShowNotification2(String Title, String Text, int Icon, Form Owner) {
		ShowNotification3(Title, Text, Icon, Owner, "BOTTOM_RIGHT", 5000);
	}
	/**
	 * Similar to ShowNotification2.
	 *Position - The message position. 
	 *The possible values are: TOP_LEFT, TOP_CENTER, TOP_RIGHT,
	 *	CENTER_LEFT, CENTER, CENTER_RIGHT,
	 *	BOTTOM_LEFT, BOTTOM_CENTER and BOTTOM_RIGHT.
	 *DurationMs - Message duration in milliseconds.
	 */
	public void ShowNotification3(String Title, String Text, int Icon, Form Owner, String Position, int DurationMs) {
		Notifications n = Notifications.create().title(Title).text(Text);
		if (Owner != null) {
			n.owner(Owner.stage);
		}
		n.position(getPosition(Position));
		n.hideAfter(Duration.millis(DurationMs));
		if (Icon == ICON_WARNING)
			n.showWarning();
		else if (Icon == ICON_CONFIRM)
			n.showConfirm();
		else if (Icon == ICON_INFORMATION)
			n.showInformation();
		else if (Icon == ICON_ERROR)
			n.showError();
		else
			n.show();

	}
	/**
	 * Sets the width and height of the node.
	 *This method will set the correct width and height properties based on the node type.
	 */
	public static void SetSize(Node Node, double Width, double Height) {
		if (Node instanceof Control) {
			((Control)Node).setPrefSize(Width > 0 ? Width : Control.USE_COMPUTED_SIZE,
					Height > 0 ? Height : Control.USE_COMPUTED_SIZE);
		} else if (Node instanceof Region){
			((Region)Node).setPrefSize(Width > 0 ? Width : Control.USE_COMPUTED_SIZE,
					Height > 0 ? Height : Control.USE_COMPUTED_SIZE);
		} else if (Node instanceof ImageView) {
			((ImageView)Node).setFitWidth(Width);
			((ImageView)Node).setFitHeight(Height);
		}
		else if (Node instanceof Canvas) {
			((Canvas)Node).setWidth(Width);
			((Canvas)Node).setHeight(Height);
		} else if (Node instanceof WebView) {
			((WebView)Node).setPrefWidth(Width);
			((WebView)Node).setPrefHeight(Height);
		}
	}
}
