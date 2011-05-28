package org.cfeclipse.cfml.images;

import org.eclipse.jface.util.Policy;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * The ColumnViewerTooltipSupport is the class that provides tool tips for
 * ColumnViewers.
 * 
 * @since 3.3
 * 
 */
public class ColumnViewerToolTipSupport extends DefaultToolTip {
	private Viewer viewer;

	private Control tree;

	private static final String VIEWER_CELL_KEY = Policy.JFACE + "_VIEWER_CELL_KEY"; //$NON-NLS-1$

	private static final int DEFAULT_SHIFT_X = 10;

	private static final int DEFAULT_SHIFT_Y = 0;

	/**
	 * Enable ToolTip support for the viewer by creating an instance from this
	 * class. To get all necessary informations this support class consults the
	 * {@link CellLabelProvider}.
	 * 
	 * @param viewer
	 *            the viewer the support is attached to
	 * @param style
	 *            style passed to control tool tip behavior
	 * 
	 * @param manualActivation
	 *            <code>true</code> if the activation is done manually using
	 *            {@link #show(Point)}
	 */
	protected ColumnViewerToolTipSupport(Viewer viewer, int style, boolean manualActivation) {
		super(viewer.getControl(), style, manualActivation);
		this.viewer = viewer;
	}

	protected ColumnViewerToolTipSupport(Control ts, int style, boolean manualActivation) {
		super(ts, style, manualActivation);
		this.tree = ts;
	}

	/**
	 * Enable ToolTip support for the viewer by creating an instance from this
	 * class. To get all necessary informations this support class consults the
	 * {@link CellLabelProvider}.
	 * 
	 * @param viewer
	 *            the viewer the support is attached to
	 */
	public static void enableFor(Viewer viewer) {
		new ColumnViewerToolTipSupport(viewer, ToolTip.NO_RECREATE, false);
	}

	/**
	 * Enable ToolTip support for the table by creating an instance from this
	 * class. To get all necessary informations this support class consults the
	 * {@link CellLabelProvider}.
	 * 
	 * @param viewer
	 *            the viewer the support is attached to
	 */
	public static void enableFor(Table table) {
		new ColumnViewerToolTipSupport(table, ToolTip.NO_RECREATE, false);
	}
	
	
	/**
	 * Enable ToolTip support for the viewer by creating an instance from this
	 * class. To get all necessary informations this support class consults the
	 * {@link CellLabelProvider}.
	 * 
	 * @param viewer
	 *            the viewer the support is attached to
	 * @param style
	 *            style passed to control tool tip behavior
	 * 
	 * @see ToolTip#RECREATE
	 * @see ToolTip#NO_RECREATE
	 */
	public static void enableFor(Viewer viewer, int style) {
		new ColumnViewerToolTipSupport(viewer, style, false);
	}

	protected Object getToolTipArea(Event event) {
		TreeViewer tv = (TreeViewer) viewer;
		int x = tv != null ? (tv.getTree().getLocation().x + 3) : tree.getLocation().x + 3;
		TreeItem item2 = (tv != null ? tv.getTree() : (Tree) tree).getItem(new Point(event.x, event.y));
		TreeItem item = item2;
		return item2;
	}

	/**
	 * Instead of overwriting this method subclasses should overwrite
	 * {@link #createViewerToolTipContentArea(Event, ViewerCell, Composite)}
	 */
	protected Composite createToolTipContentArea(Event event, Composite parent) {
		Object cell = (Object) getData(VIEWER_CELL_KEY);
		setData(VIEWER_CELL_KEY, null);

		return createViewerToolTipContentArea(event, cell, parent);
	}

	/**
	 * Creates the content area of the tool tip giving access to the cell the
	 * tip is shown for. Subclasses can overload this method to implement their
	 * own tool tip design.
	 * 
	 * <p>
	 * This method is called from
	 * {@link #createToolTipContentArea(Event, Composite)} and by default calls
	 * the {@link DefaultToolTip#createToolTipContentArea(Event, Composite)}.
	 * </p>
	 * 
	 * @param event
	 *            the event that which
	 * @param cell
	 *            the cell the tool tip is shown for
	 * @param parent
	 *            the parent of the control to create
	 * @return the control to be displayed in the tool tip area
	 * @since 3.4
	 */
	protected Composite createViewerToolTipContentArea(Event event, Object cell, Composite parent) {
		return super.createToolTipContentArea(event, parent);
	}

	protected boolean shouldCreateToolTip(Event event) {
		if (!super.shouldCreateToolTip(event)) {
			return false;
		}

		TreeViewer tv = (TreeViewer) viewer;
		TreeItem item = tv.getTree().getItem(new Point(event.x, event.y));
		viewer.getControl().setToolTipText(""); //$NON-NLS-1$
		if (item != null) {
			setPopupDelay(200);
			setHideDelay(400);
			setShift(new Point(DEFAULT_SHIFT_X, DEFAULT_SHIFT_Y));
			setData(VIEWER_CELL_KEY, item);
			return true;
		}
		return false;
	}

	protected void afterHideToolTip(Event event) {
		super.afterHideToolTip(event);
		// Clear the restored value else this could be a source of a leak
		setData(VIEWER_CELL_KEY, null);
		if (viewer != null) {
			if (event != null && event.widget != viewer.getControl()) {
				viewer.getControl().setFocus();
			}
		}
	}

}
