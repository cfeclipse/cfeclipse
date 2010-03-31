package org.cfeclipse.cfml.views.images;

import java.io.IOException;

import org.cfeclipse.cfml.images.DefaultToolTip;
import org.cfeclipse.cfml.images.IImageEntry;
import org.eclipse.nebula.widgets.gallery.Gallery;
import org.eclipse.nebula.widgets.gallery.GalleryItem;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;

final class ImagesViewTooltip extends DefaultToolTip {
	private final Gallery gallery;

	ImagesViewTooltip(Control control, Gallery gallery) {
		super(control);
		this.gallery = gallery;
	}

	protected Composite createToolTipContentArea(Event event, Composite parent) {
		final Image image = this.getImage(event);
		final Image bgImage = this.getBackgroundImage(event);
		final String text = this.getText(event);
		final Color fgColor = this.getForegroundColor(event);
		final Color bgColor = this.getBackgroundColor(event);
		final Font font = this.getFont(event);
		final FillLayout layout = (FillLayout) parent.getLayout();
		layout.marginWidth = 10;
		layout.marginHeight = 5;
		parent.setBackground(bgColor);
		final CLabel label = new CLabel(parent, this.getStyle(event));
		if (text != null) {
			label.setText(text);
		}

		if (image != null) {
			label.setImage(image);
		}

		if (fgColor != null) {
			label.setForeground(fgColor);
		}

		if (bgColor != null) {
			label.setBackground(bgColor);
		}

		if (bgImage != null) {
			label.setBackgroundImage(image);
		}

		if (font != null) {
			label.setFont(font);
		}

		return label;
	}

	protected Image getImage(Event event) {
		final Point point = new Point(event.x, event.y);
		final GalleryItem item = this.gallery.getItem(point);
		if (item != null) {
			if (item.getParent() != null) {
				final IImageEntry data2 = (IImageEntry) item.getData();
				try {
					Image img = ImageCache.getImage(data2);
					if (img == null)
						return super.getImage(event);
					return img;
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}

		}
		return super.getImage(event);
	}

	protected boolean shouldCreateToolTip(Event event) {
		final boolean shouldCreateToolTip = super.shouldCreateToolTip(event);
		final Point point = new Point(event.x, event.y);
		final GalleryItem item = this.gallery.getItem(point);
		return shouldCreateToolTip && (item != null);
	}

	protected String getText(Event event) {
		final Point point = new Point(event.x, event.y);
		final GalleryItem item = this.gallery.getItem(point);
		if (item != null) {
			if (item.getParent() != null) {
				final IImageEntry data2 = (IImageEntry) item.getData();
				try {
					final Image image2 = ImageCache.getImage(data2);
					if (image2 != null) {
						final Rectangle bounds = image2.getBounds();
						return data2.getName() + "(" + bounds.width + "," + bounds.height + ")";
					} else {
						return data2.getName();
					}
				} catch (final IOException e) {
					e.printStackTrace();
				}
				return data2.getName();
			} else {
				return item.getText();
			}
		}
		return super.getText(event);
	}
}