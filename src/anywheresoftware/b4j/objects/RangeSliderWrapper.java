package anywheresoftware.b4j.objects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;

import org.controlsfx.control.RangeSlider;

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

/**
 * A vertical or horizontal slider with two thumbs that allows the user to select a range.
 *The range is defined between the low value and the high value.
 */
@DontInheritEvents
@ShortName("RangeSlider")
@Events(values={"ValueChange (LowValue As Double, HighValue As Double)"})
@DesignerProperties(values={
		@Property(key="MinValue", displayName="Min Value", fieldType="float", defaultValue="0", minRange="0"),
		@Property(key="MaxValue", displayName="Max Value", fieldType="float", defaultValue="100"),
		@Property(key="LowValue", displayName="Low Value", fieldType="float", defaultValue="0", minRange="0",
			description="Current lower value."),
		@Property(key="HighValue", displayName="High Value", fieldType="float", defaultValue="100", 
			description="Current lower value."),
		@Property(key="ShowTickLabels", displayName="Show Tick Labels", fieldType="boolean", defaultValue="false"),
		@Property(key="ShowTickMarks", displayName="Show Tick Marks", fieldType="boolean", defaultValue="false"),
		@Property(key="SnapToTicks", displayName="Snap To Ticks", fieldType="boolean", defaultValue="false"),
		@Property(key="Orientation", displayName="Orientation", fieldType="string", defaultValue="Horizontal",
				list="Horizontal|Vertical"),
		
})
public class RangeSliderWrapper extends ControlWrapper<RangeSlider> implements DesignerCustomView{
	@Override
	public void DesignerCreateView(final ConcretePaneWrapper base, LabelWrapper label,
			Map args) {
		base.AddNode(getObject(), 0, 0, base.getWidth(), base.getHeight());
		base.getObject().setMinHeight(40);
		base.getObject().setMinWidth(80);
		setMinValue((Float)args.Get("MinValue"));
		setMaxValue((Float)args.Get("MaxValue"));
		setLowValue((Float)args.Get("LowValue"));
		setHighValue((Float)args.Get("HighValue"));
		setShowTickLabels((Boolean)args.Get("ShowTickLabels"));
		setShowTickMarks((Boolean)args.Get("ShowTickMarks"));
		setSnapToTicks((Boolean)args.Get("SnapToTicks"));
		setVertical(((String)args.Get("Orientation")).equals("Vertical"));
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
			setObject(new RangeSlider(0, 100, 0, 100));
		super.innerInitialize(ba, eventName, true);
		if (ba.subExists(eventName + "_valuechange")) {
			ChangeListener<Number> cl = new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> arg0,
						Number arg1, Number arg2) {
					ba.raiseEventFromUI(getObject(), eventName + "_valuechange", getObject().getLowValue(),
							getObject().getHighValue());
					
				}
			};
			getObject().lowValueProperty().addListener(cl);
			getObject().highValueProperty().addListener(cl);
		}
	}
	/**
	 * Gets or sets the current low value.
	 */
	public double getLowValue() {
		return getObject().getLowValue();
	}
	public void setLowValue(double d) {
		getObject().setLowValue(d);
	}
	/**
	 * Gets or sets the current high value.
	 */
	public double getHighValue() {
		return getObject().getHighValue();
	}
	public void setHighValue(double d) {
		getObject().setHighValue(d);
	}
	/**
	 * Gets or sets the minimum value. The default value is 0.
	 */
	public double getMinValue() {
		return getObject().getMin();
	}
	public void setMinValue(double d) {
		getObject().setMin(d);
	}
	/**
	 * Gets or sets the maximum value. The default value is 100.
	 */
	public double getMaxValue() {
		return getObject().getMax();
	}
	public void setMaxValue(double d) {
		getObject().setMax(d);
	}
	
	/**
	 * Gets or sets the slider orientation. The default value is False (horizontal).
	 */
	public boolean getVertical() {
		return getObject().getOrientation() == Orientation.VERTICAL;
	}
	public void setVertical(boolean b) {
		getObject().setOrientation(b ? Orientation.VERTICAL : Orientation.HORIZONTAL);
	}
	/**
	 * Set to True to show tick marks.
	 */
	public boolean getShowTickMarks() {
		return getObject().isShowTickMarks();
	}
	public void setShowTickMarks(boolean b) {
		getObject().setShowTickMarks(b);
	}
	/**
	 * Set to true to show tick labels.
	 */
	public boolean getShowTickLabels() {
		return getObject().isShowTickLabels();
	}
	public void setShowTickLabels(boolean b) {
		getObject().setShowTickLabels(b);
	}
	/**
	 * Set to True to make the thumbs snap to the nearest tick.
	 */
	public boolean getSnapToTicks() {
		return getObject().isSnapToTicks();
	}
	public void setSnapToTicks(boolean b) {
		getObject().setSnapToTicks(b);
	}
	/**
	 * Gets or sets the distance between major ticks. Default value is 25.
	 */
	public double getMajorTickUnit() {
		return getObject().getMajorTickUnit();
	}
	public void setMajorTickUnit(double d) {
		getObject().setMajorTickUnit(d);
	}
	/**
	 * Gets or sets the number of minor ticks between major ticks.
	 */
	public int getMinorTickCount() {
		return getObject().getMinorTickCount();
	}
	public void setMinorTickCount(int d) {
		getObject().setMinorTickCount(d);
	}
	
}
