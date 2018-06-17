package org.cfeclipse.cfml.editors.breadcrumb;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerRow;


/**
 * A viewer row for the breadcrumb viewer.
 *
 * @since 3.4
 */
class BreadcrumbViewerRow extends ViewerRow {

	private Color fForeground;
	private Font fFont;
	private Color fBackground;

	private final BreadcrumbItem fItem;
	private final BreadcrumbViewer fViewer;


	public BreadcrumbViewerRow(BreadcrumbViewer viewer, BreadcrumbItem item) {
		fViewer= viewer;
		fItem= item;
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#clone()
	 */
	@Override
	public Object clone() {
		return new BreadcrumbViewerRow(fViewer, fItem);
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#getBackground(int)
	 */
	@Override
	public Color getBackground(int columnIndex) {
		return fBackground;
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#getBounds(int)
	 */
	@Override
	public Rectangle getBounds(int columnIndex) {
		return getBounds();
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#getBounds()
	 */
	@Override
	public Rectangle getBounds() {
		return fItem.getBounds();
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 1;
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#getControl()
	 */
	@Override
	public Control getControl() {
		return fViewer.getControl();
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#getElement()
	 */
	@Override
	public Object getElement() {
		return fItem.getData();
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#getFont(int)
	 */
	@Override
	public Font getFont(int columnIndex) {
		return fFont;
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#getForeground(int)
	 */
	@Override
	public Color getForeground(int columnIndex) {
		return fForeground;
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#getImage(int)
	 */
	@Override
	public Image getImage(int columnIndex) {
		return fItem.getImage();
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#getItem()
	 */
	@Override
	public Widget getItem() {
		return fItem;
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#getNeighbor(int, boolean)
	 */
	@Override
	public ViewerRow getNeighbor(int direction, boolean sameLevel) {
		return null;
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#getText(int)
	 */
	@Override
	public String getText(int columnIndex) {
		return fItem.getText();
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#getTreePath()
	 */
	@Override
	public TreePath getTreePath() {
		return new TreePath(new Object[] { getElement() });
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#setBackground(int, org.eclipse.swt.graphics.Color)
	 */
	@Override
	public void setBackground(int columnIndex, Color color) {
		fBackground= color;
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#setFont(int, org.eclipse.swt.graphics.Font)
	 */
	@Override
	public void setFont(int columnIndex, Font font) {
		fFont= font;
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#setForeground(int, org.eclipse.swt.graphics.Color)
	 */
	@Override
	public void setForeground(int columnIndex, Color color) {
		fForeground= color;
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#setImage(int, org.eclipse.swt.graphics.Image)
	 */
	@Override
	public void setImage(int columnIndex, Image image) {
		fItem.setImage(image);
	}

	/*
	 * @see org.eclipse.jface.viewers.ViewerRow#setText(int, java.lang.String)
	 */
	@Override
	public void setText(int columnIndex, String text) {
		fItem.setText(text);
	}

}