package org.cfeclipse.cfml.views.images;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import org.cfeclipse.cfml.images.DefaultToolTip;
import org.cfeclipse.cfml.images.IImageEntry;
import org.cfeclipse.cfml.images.IImageEntryCallback;
import org.cfeclipse.cfml.images.IStoreImageListener;
import org.cfeclipse.cfml.images.ImageTransferWrapper;
import org.cfeclipse.cfml.images.ItemGroup;
import org.cfeclipse.cfml.images.StringMatcher;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryGroupRenderer;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryItemRenderer;
import org.eclipse.nebula.widgets.gallery.Gallery;
import org.eclipse.nebula.widgets.gallery.GalleryItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ImagesView extends ViewPart {

	public final static String ID_IMAGES = "org.cfeclipse.cfml.images.org.cfeclipse.cfml.images";
	private DefaultToolTip tooltip;

	ImageDescriptor collapse = AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.search",
			"$nl$/icons/full/elcl16/collapseall.gif");
	ImageDescriptor expand = AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.search",
			"$nl$/icons/full/elcl16/expandall.gif");
	ImageDescriptor zoom = CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_ZOOMIN);
	ImageDescriptor zoomout = CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_ZOOMOUT);
	ImageDescriptor clearCo = CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_DELETE);

	private Text textFilter;

	private CTabFolder fld;

	private String pattern;

	public ImagesView() {
	}

	public void dispose() {
		this.tooltip.hide();
		ImageCache.clearCache();
		super.dispose();
	}

	public void createPartControl(Composite parent) {
		final IImageStore store1 = SelectionImages.getSelectionImages();
		parent.setLayout(new GridLayout(1, false));
		final Composite mn = new Composite(parent, SWT.NONE);
		mn.setLayout(new GridLayout(3, false));
		final Label ls = new Label(mn, SWT.NONE);
		ls.setText("Search:");
		mn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.textFilter = new Text(mn, SWT.BORDER);
		this.textFilter.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.textFilter.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				ImagesView.this.refilter(ImagesView.this.textFilter);
			}
		});
		final ToolBar bs = new ToolBar(mn, SWT.NONE);
		final ToolBarManager man = new ToolBarManager(bs);
		final Action clearFlt = new Action("Clear Search") {
			public void run() {
				ImagesView.this.textFilter.setText("");
			}
		};
		clearFlt.setImageDescriptor(this.clearCo);
		man.add(clearFlt);
		man.update(true);
		this.fld = new CTabFolder(parent, SWT.BOTTOM | SWT.FLAT);
		this.configureTab(store1, this.fld);
		this.fld.setSelection(0);
		final Action action = new Action() {

			public void run() {
				final CTabItem[] items = ImagesView.this.fld.getItems();
				for (final CTabItem i : items) {
					final DefaultGalleryGroupRenderer rr = (DefaultGalleryGroupRenderer) i.getData("g");
					ImagesView.this.zoomIn(rr);
				}
			}
		};
		this.fld.setLayoutData(new GridData(GridData.FILL_BOTH));
		action.setText("Zoom In");
		action.setImageDescriptor(this.zoom);
		final Action action2 = new Action() {

			public void run() {
				final CTabItem[] items = ImagesView.this.fld.getItems();
				for (final CTabItem i : items) {
					final DefaultGalleryGroupRenderer rr = (DefaultGalleryGroupRenderer) i.getData("g");
					ImagesView.this.zoomOut(rr);
				}
			}
		};
		action2.setText("Zoom Out");
		action2.setImageDescriptor(this.zoomout);
		final IActionBars actionBars = this.getViewSite().getActionBars();
		final IToolBarManager toolBarManager = actionBars.getToolBarManager();
		final Action colapseA = new Action() {
			public void run() {
				final CTabItem[] items = ImagesView.this.fld.getItems();
				for (final CTabItem i : items) {
					final Collapser rr = (Collapser) i.getData("ga");
					rr.collapse();
				}
			}

		};

		final Action expand = new Action() {
			public void run() {
				final CTabItem[] items = ImagesView.this.fld.getItems();
				for (final CTabItem i : items) {
					final Collapser rr = (Collapser) i.getData("ga");
					rr.expand();
				}
			}

		};
		expand.setText("Expand All");
		expand.setImageDescriptor(this.expand);
		colapseA.setText("Collapse All");
		colapseA.setImageDescriptor(this.collapse);
		toolBarManager.add(expand);
		toolBarManager.add(colapseA);
		toolBarManager.add(new Separator());
		toolBarManager.add(action);
		toolBarManager.add(action2);

		actionBars.updateActionBars();
	}

	public abstract static class Collapser {

		abstract void collapse();

		abstract void expand();

	}

	protected void refilter(Text textFilter2) {
		final String text = textFilter2.getText();
		this.pattern = text;

		final CTabItem[] items = this.fld.getItems();
		for (final CTabItem i : items) {
			final Refresher rr = (Refresher) i.getData("r");
			rr.refresh();
		}
	}

	@SuppressWarnings("deprecation")
	private void configureTab(final IImageStore store, CTabFolder fld) {
		final CTabItem item = new CTabItem(fld, SWT.NONE);
		item.setText(store.getName());
		final Composite cm = new Composite(fld, SWT.NONE);
		cm.setLayout(new FillLayout());
		final Gallery gallery = new Gallery(cm, SWT.VIRTUAL | SWT.V_SCROLL | SWT.BORDER);
		item.setControl(cm);

		gallery.setVertical(false);

		final DefaultGalleryGroupRenderer gr = new DefaultGalleryGroupRenderer();
		gallery.setGroupRenderer(gr);
		gallery.setItemRenderer(new DefaultGalleryItemRenderer() {

			protected Image getImage(GalleryItem item) {
				final IImageEntry entry = (IImageEntry) item.getData();
				try {
					return ImageCache.getImage(entry);
				} catch (final Exception e) {
					e.printStackTrace();
				}
				return null;
			}

		});
		final DragSource dragSource = new DragSource(gallery, DND.DROP_COPY);
		dragSource.setTransfer(new Transfer[] { FileTransfer.getInstance() });
		dragSource.addDragListener(new DragSourceListener() {

			private String[] dataX;

			public void dragFinished(DragSourceEvent event) {
				this.dataX = null;
			}

			public void dragSetData(DragSourceEvent event) {

				event.data = this.dataX;

			}

			public void dragStart(DragSourceEvent event) {
				event.detail = DND.DROP_COPY;
				final GalleryItem item2 = gallery.getItem(new Point(event.x, event.y));
				if (item2 != null) {
					final Object data = item2.getData();
					if (data instanceof IImageEntry) {
						final IImageEntry e = (IImageEntry) data;
						final String file = e.getFile();
						this.dataX = new String[] { file };
						try {
							event.image = e.getImage();
						} catch (final IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}

		});

		final IImageEntryCallback cb = new IImageEntryCallback() {

			public void imageLoaded() {
				gallery.redraw();
			}

		};
		ImageCache.addCallback(cb);
		gallery.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				ImageCache.removeCallback(cb);
			}

		});
		item.setData("g", gr);
		gr.setItemHeight(56);
		gr.setItemWidth(72);
		this.fillContextMenu(gallery, gr);

		this.tooltip = new ImagesViewTooltip(gallery, gallery);

		final ArrayList<Object> images = new ArrayList<Object>(store.getContents());
		this.prepareImages(images);
		gallery.addListener(SWT.SetData, new Listener() {

			public void handleEvent(Event event) {
				final GalleryItem item = (GalleryItem) event.item;
				final GalleryItem parentItem = item.getParentItem();
				if (parentItem == null) {
					final int index = gallery.indexOf(item);
					final ItemGroup itemGroup = (ItemGroup) images.get(index);
					item.setText(itemGroup.getName());
					item.setData(itemGroup);
					item.setItemCount(itemGroup.getChildCount());
				} else {
					final int indexOf = parentItem.indexOf(item);
					final ItemGroup ga = (ItemGroup) parentItem.getData();
					item.setItemCount(0);
					final IImageEntry image = ga.getImage(indexOf);
					try {
						item.setImage(image.getImage());
					} catch (IOException e) {
						e.printStackTrace();
					}
					item.setText(image.getName());
					item.setData(image);
				}
			}
		});
		final HashSet<Object> expanded = new HashSet<Object>();
		item.setData("ga", new Collapser() {

			void collapse() {
				for (int a = 0; a < gallery.getItemCount(); a++) {
					final GalleryItem item2 = gallery.getItem(a);
					item2.setExpanded(false);
				}
				expanded.clear();
			}

			void expand() {
				for (int a = 0; a < gallery.getItemCount(); a++) {
					final GalleryItem item2 = gallery.getItem(a);
					item2.setExpanded(true);
					expanded.add(item2.getData());
				}
			}

		});
		gallery.addTreeListener(new TreeListener() {

			public void treeCollapsed(TreeEvent e) {
				expanded.remove(e.data);

			}

			public void treeExpanded(TreeEvent e) {
				expanded.add(e.data);
			}

		});
		final IStoreImageListener storeImageListener = new IStoreImageListener() {

			public void platformChanged() {
				final ArrayList<?> contents = new ArrayList<Object>(store.getContents());
				Display.getDefault().asyncExec(new Runnable() {

					public void run() {
						ImagesView.this.refresh(gallery, images, expanded, contents);
					}

				});

			}

		};
		item.setData("r", new Refresher() {

			public void refresh() {
				final ArrayList<?> contents = new ArrayList<Object>(store.getContents());
				ImagesView.this.refresh(gallery, images, expanded, new ArrayList<Object>(contents));
			}

		});
		store.addListener(storeImageListener);
		gallery.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				store.removeListener(storeImageListener);
			}

		});
		gallery.setItemCount(images.size());
		gallery.setVertical(true);
	}

	static abstract class Refresher {

		public abstract void refresh();
	}

	@SuppressWarnings("unchecked")
	private void refresh(final Gallery gallery, final ArrayList images, final HashSet<Object> expanded,
			final ArrayList<?> contents) {
		images.clear();
		if ((this.pattern == null) || (this.pattern.trim().length() == 0)) {
			images.addAll(contents);
		} else {
			final StringMatcher ma = new StringMatcher("*" + this.pattern + "*", true, false);
			for (final Object o : contents) {
				if (o instanceof ItemGroup) {
					final ItemGroup ga = (ItemGroup) o;

					if (ma.match(ga.getName())) {
						images.add(o);
					} else {
						final ArrayList<IImageEntry> z = new ArrayList<IImageEntry>();
						for (int a = 0; a < ga.getChildCount(); a++) {
							final IImageEntry image = ga.getImage(a);
							if (ma.match(image.getName())) {
								z.add(image);
							}
						}
						if (!z.isEmpty()) {
							images.add(new ItemGroup(ga.getName(), z));
						}
					}
				}
			}
		}
		this.prepareImages(images);
		gallery.setItemCount(images.size());
		gallery.clearAll();
		for (final Object o : expanded) {
			final int indexOf = images.indexOf(o);
			if (indexOf != -1) {
				gallery.getItem(indexOf).setExpanded(true);
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void prepareImages(ArrayList images) {
		Collections.sort(images, new Comparator<Object>() {

			public int compare(Object o1, Object o2) {
				if (o1 instanceof ItemGroup) {
					if (o2 instanceof ItemGroup) {
						final ItemGroup i1 = (ItemGroup) o1;
						final ItemGroup i2 = (ItemGroup) o2;
						return i1.getName().compareTo(i2.getName());
					} else {
						return -1;
					}
				}
				return 1;
			}

		});
	}

	private void fillContextMenu(final Gallery gallery, final DefaultGalleryGroupRenderer gr) {
		final MenuManager manager = new MenuManager();
		manager.add(new Action("Copy", IAction.AS_PUSH_BUTTON) {
			public void run() {
				final GalleryItem[] selection = gallery.getSelection();
				boolean av = ImageTransferWrapper.isAvalable();
				final Object[] data = new Object[selection.length * (av ? 3 : 2)];
				int a = 0;
				for (final GalleryItem s : selection) {
					final IImageEntry entry = (IImageEntry) s.getData();
					data[a++] = new String[] { entry.getFile() };
					data[a++] = entry.getPath();
					if (av) {
						try {
							data[a++] = entry.getImage().getImageData();
						} catch (final IOException e) {
							e.printStackTrace();
						}
					}
				}
				final Clipboard clipboard = new Clipboard(Display.getCurrent());
				clipboard.setContents(data, new Transfer[] { FileTransfer.getInstance(), TextTransfer.getInstance(),
						(Transfer) ImageTransferWrapper.getInstance() });
				clipboard.dispose();
			}
		});
		manager.add(new Separator());
		final Action action = new Action("Zoom In", IAction.AS_PUSH_BUTTON) {
			public void run() {
				ImagesView.this.zoomIn(gr);
			}
		};
		action.setImageDescriptor(this.zoom);
		manager.add(action);
		final Action action2 = new Action("Zoom Out", IAction.AS_PUSH_BUTTON) {
			public void run() {
				ImagesView.this.zoomOut(gr);
			}
		};
		action2.setImageDescriptor(this.zoomout);
		manager.add(action2);
		final Menu createContextMenu = manager.createContextMenu(gallery);
		gallery.setMenu(createContextMenu);
	}

	public void setFocus() {

	}

	private void zoomOut(final DefaultGalleryGroupRenderer gr) {
		int itemHeight = gr.getItemHeight();
		int itemWidth = gr.getItemWidth();
		if ((itemHeight > 40) && (itemWidth > 56)) {
			itemHeight -= 16;
			itemWidth -= 16;
		}
		gr.setItemSize(itemWidth, itemHeight);
	}

	private void zoomIn(final DefaultGalleryGroupRenderer gr) {
		int itemHeight = gr.getItemHeight();
		int itemWidth = gr.getItemWidth();
		if (itemHeight < 128) {
			itemHeight += 16;
			itemWidth += 16;
		}
		gr.setItemSize(itemWidth, itemHeight);
	}

}
