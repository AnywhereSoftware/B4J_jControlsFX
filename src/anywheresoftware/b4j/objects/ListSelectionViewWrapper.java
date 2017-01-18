package anywheresoftware.b4j.objects;

import javafx.collections.ObservableList;
import javafx.scene.control.Labeled;

import org.controlsfx.control.ListSelectionView;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.DesignerProperties;
import anywheresoftware.b4a.BA.DontInheritEvents;
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
 * A view that holds two lists (source and target) and allows the user to move items from one list to the other.
 */
@ShortName("ListSelectionView")
@DontInheritEvents
@DesignerProperties(values={
		@Property(key="SourceItems", displayName="Source Items", defaultValue="Item #1|Item #2|Item #3", fieldType="string"),
		@Property(key="TargetItems", displayName="Target Items", defaultValue="Item #4|Item #5|Item #6", fieldType="string"),
		@Property(key="SourceTitle", displayName="Source Title", defaultValue="Source", fieldType="string"),
		@Property(key="TargetTitle", displayName="Target Title", defaultValue="Target", fieldType="string"),
			
	})
public class ListSelectionViewWrapper extends ControlWrapper<ListSelectionView<String>> implements DesignerCustomView{
	static {
		ControlsUtils.registerFontAwesome();
	}
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
		getSourceItems().AddAll(Common.ArrayToList(((String)args.Get("SourceItems")).split("\\|")));
		getTargetItems().AddAll(Common.ArrayToList(((String)args.Get("TargetItems")).split("\\|")));
		setSourceTitle((String)args.Get("SourceTitle"));
		setTargetTitle((String)args.Get("TargetTitle"));
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
			setObject(new ListSelectionView<String>());
		super.innerInitialize(ba, eventName, true);
	}
	/**
	 * Returns the source items. You can add string items to this list.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getSourceItems() {
		List l = new List();
		l.setObject((ObservableList)getObject().getSourceItems());
		return l;
	}
	/**
	 * Returns the target items. You can add string items to this list.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTargetItems() {
		List l = new List();
		l.setObject((ObservableList)getObject().getTargetItems());
		return l;
	}
	/**
	 * Gets or sets the source list title.
	 */
	public String getSourceTitle() {
		return ((Labeled)getObject().getSourceHeader()).getText();
	}
	public void setSourceTitle(String s) {
		((Labeled)getObject().getSourceHeader()).setText(s);
	}
	/**
	 * Gets or sets the target list title.
	 */
	public String getTargetTitle() {
		return ((Labeled)getObject().getTargetHeader()).getText();
	}
	public void setTargetTitle(String s) {
		((Labeled)getObject().getTargetHeader()).setText(s);
	}
	
}
