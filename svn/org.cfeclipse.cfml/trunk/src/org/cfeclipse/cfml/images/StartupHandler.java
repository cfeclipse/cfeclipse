package org.cfeclipse.cfml.images;

import java.lang.reflect.Method;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class StartupHandler implements IStartup {

	private final class MC extends ColumnViewerToolTipSupport {
		private final Tree invoke;
		private Image ima;

		private MC(Viewer viewer, int style, boolean manualActivation, TreeViewer invoke) {
			super(viewer, style, manualActivation);
			this.invoke = invoke.getTree();
		}

		public MC(Tree ts, int style, boolean manualActivation) {
			super(ts, style, manualActivation);
			this.invoke = ts;
		}

		protected boolean shouldCreateToolTip(Event event) {
			final TreeItem item = invoke.getItem(new Point(event.x, event.y));
			if (item == null) {
				return false;
			}
			final Object data = item.getData();
			if (data instanceof IFile) {
				final IFile fs = (IFile) data;
				final String name2 = fs.getName();
				final boolean image = AbstractImageEntry.isImage(name2);
				return image;
			}
			return false;
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
			label.addDisposeListener(new DisposeListener() {

				public void widgetDisposed(DisposeEvent e) {
					image.dispose();
					ima = null;
				}

			});
			return label;
		}

		protected Image getImage(Event event) {
			final TreeItem item = invoke.getItem(new Point(event.x, event.y));
			final Object data = item.getData();
			if (data instanceof IFile) {
				final IFile fs = (IFile) data;
				try {
					this.ima = new Image(Display.getCurrent(), fs.getContents(true));
					return this.ima;
				} catch (final CoreException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		protected String getText(Event event) {
			final TreeItem item = invoke.getItem(new Point(event.x, event.y));
			final Object data = item.getData();
			if (data instanceof IFile) {
				final Image image2 = this.ima;
				if (this.ima != null) {
					final Rectangle bounds = image2.getBounds();
					return "(" + bounds.width + "," + bounds.height + ")";
				}
				return "";

			}
			return "";
		}
	}

	public void earlyStartup() {
		Display.getDefault().asyncExec(new Runnable() {

			public void run() {
				Display.getDefault().addFilter(SWT.Show, new Listener() {

					public void handleEvent(Event event) {
						if (event.widget instanceof Composite) {
							Composite cm = (Composite) event.widget;
							rec(cm);
						}
					}

					private void rec(Composite cm) {
						Control[] children = cm.getChildren();
						for (Control c : children) {
							if (c instanceof Tree) {
								Tree m = (Tree) c;
								// final TreeViewer invoke = (TreeViewer) method
								// .invoke(part);
								final String name = this.getClass().getName();

								final Object data = m.getData(name);
								if (data == null) {

									final ColumnViewerToolTipSupport ts = new MC(m, ToolTip.NO_RECREATE, false);
									m.setData(name, ts);
								}
								// ColumnViewerToolTipSupport.enableFor(m);

							} else if (c instanceof Composite) {
								Composite m = (Composite) c;
								rec(m);
							}
						}
					}

				});
			}

		});

		final IWorkbenchWindow[] workbenchWindows = PlatformUI.getWorkbench().getWorkbenchWindows();
		PlatformUI.getWorkbench().addWindowListener(new IWindowListener() {

			public void windowActivated(IWorkbenchWindow window) {

			}

			public void windowClosed(IWorkbenchWindow window) {

			}

			public void windowDeactivated(IWorkbenchWindow window) {

			}

			public void windowOpened(IWorkbenchWindow window) {
				StartupHandler.this.processWindow(window);
			}

		});
		for (final IWorkbenchWindow w : workbenchWindows) {
			this.processWindow(w);
		}
	}

	private void processWindow(IWorkbenchWindow w) {
		w.addPageListener(new IPageListener() {

			public void pageActivated(IWorkbenchPage page) {

			}

			public void pageClosed(IWorkbenchPage page) {

			}

			public void pageOpened(IWorkbenchPage page) {
				StartupHandler.this.processPage(page);
			}
		});
		final IWorkbenchPage[] pages = w.getPages();
		for (final IWorkbenchPage p : pages) {
			this.processPage(p);
		}
	}

	private void processPage(IWorkbenchPage p) {
		final IViewReference[] viewReferences = p.getViewReferences();
		for (final IViewReference v : viewReferences) {
			final String id = v.getId();
			if (this.accept(id)) {
				final IWorkbenchPart part = v.getPart(false);
				if (part != null) {
					this.initPart(part);
				}
			}
		}
		p.addPartListener(new IPartListener() {

			public void partActivated(IWorkbenchPart part) {

			}

			public void partBroughtToTop(IWorkbenchPart part) {

			}

			public void partClosed(IWorkbenchPart part) {

			}

			public void partDeactivated(IWorkbenchPart part) {

			}

			public void partOpened(IWorkbenchPart part) {
				final String id = part.getSite().getId();
				if (StartupHandler.this.accept(id)) {
					StartupHandler.this.initPart(part);
				}
			}

		});
	}

	private boolean accept(String id) {
		return id.equals("org.cfeclipse.cfml.views.explorer.FileExplorerView")
				|| id.equals("org.eclipse.ui.views.ResourceNavigator")
				|| id.equals("org.eclipse.ui.navigator.ProjectExplorer")
				|| id.equals("org.eclipse.jdt.ui.PackageExplorer") || id.equals("org.eclipse.jdt.ui.ProjectsView");
	}

	private void initPart(IWorkbenchPart part) {
		try {

			Method method = null;
			try {
				method = part.getClass().getMethod("getTreeViewer");
			} catch (final Exception e) {
			}
			try {
				method = part.getClass().getMethod("getCommonViewer");
			} catch (final Exception e) {
			}
			try {
				method = part.getClass().getMethod("getViewer");
			} catch (final Exception e) {
			}
			if (method == null) {
				return;
			}

			final TreeViewer invoke = (TreeViewer) method.invoke(part);
			final String name = this.getClass().getName();

			Display.getDefault().asyncExec(new Runnable() {

				public void run() {
					final Object data = invoke.getData(name);
					if (data == null) {

						final ColumnViewerToolTipSupport ts = new MC(invoke, ToolTip.NO_RECREATE, false, invoke);
						invoke.setData(name, ts);
					}

				}

			});

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
