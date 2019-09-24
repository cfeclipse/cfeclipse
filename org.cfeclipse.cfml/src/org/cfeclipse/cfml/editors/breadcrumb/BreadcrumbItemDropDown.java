package org.cfeclipse.cfml.editors.breadcrumb;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

import org.eclipse.core.runtime.Assert;
import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.util.SWTUtil;
import org.eclipse.core.resources.IFile;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.util.Geometry;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;

import org.eclipse.ui.forms.FormColors;



/**
 * The part of the breadcrumb item with the drop down menu.
 *
 * @since 3.4
 */
class BreadcrumbItemDropDown {
	private static final boolean IS_MAC_WORKAROUND= "carbon".equals(SWT.getPlatform()); //$NON-NLS-1$

	/**
	 * An arrow image descriptor. The images color is related to the list
	 * fore- and background color. This makes the arrow visible even in high contrast
	 * mode. If <code>ltr</code> is true the arrow points to the right, otherwise it
	 * points to the left.
	 */
	private final class AccessibleArrowImage extends CompositeImageDescriptor {

		private final static int ARROW_SIZE= 5;

		private final boolean fLTR;

		public AccessibleArrowImage(boolean ltr) {
			fLTR= ltr;
		}

		@Override
		protected void drawCompositeImage(int width, int height) {
			Display display= fParentComposite.getDisplay();
			
			ImageDataProvider imageProvider= zoom -> {
				Image image= new Image(display, ARROW_SIZE, ARROW_SIZE * 2);
				
				GC gc= new GC(image, fLTR ? SWT.LEFT_TO_RIGHT : SWT.RIGHT_TO_LEFT);
				gc.setAntialias(SWT.ON);
				
				Color triangleColor= createColor(SWT.COLOR_LIST_FOREGROUND, SWT.COLOR_LIST_BACKGROUND, 20, display);
				gc.setBackground(triangleColor);
				gc.fillPolygon(new int[] { 0, 0, ARROW_SIZE, ARROW_SIZE, 0, ARROW_SIZE * 2 });
				gc.dispose();
				triangleColor.dispose();
				
				ImageData imageData= image.getImageData(zoom);
				image.dispose();
				int zoomedArrowSize= ARROW_SIZE * zoom / 100;
				for (int y1= 0; y1 < zoomedArrowSize; y1++) {
					for (int x1= 0; x1 <= y1; x1++) {
						imageData.setAlpha(fLTR ? x1 : zoomedArrowSize - x1 - 1, y1, 255);
					}
				}
				for (int y2= 0; y2 < zoomedArrowSize; y2++) {
					for (int x2= 0; x2 <= y2; x2++) {
						imageData.setAlpha(fLTR ? x2 : zoomedArrowSize - x2 - 1, zoomedArrowSize * 2 - y2 - 1, 255);
					}
				}
				return imageData;
			};
			drawImage(imageProvider, (width / 2) - (ARROW_SIZE / 2), (height / 2) - ARROW_SIZE);
		}

		@Override
		protected Point getSize() {
			return new Point(10, 16);
		}

		private Color createColor(int color1, int color2, int ratio, Display display) {
			RGB rgb1= display.getSystemColor(color1).getRGB();
			RGB rgb2= display.getSystemColor(color2).getRGB();

			RGB blend= FormColors.blend(rgb2, rgb1, ratio);

			return new Color(display, blend);
		}
	}

	private static final int DROP_DOWN_MIN_WIDTH= 250;
	private static final int DROP_DOWN_MAX_WIDTH= 500;
	
	private static final int DROP_DOWN_DEFAULT_MIN_HEIGHT= 200;
	private static final int DROP_DOWN_DEFAULT_MAX_HEIGHT= 300;

	private static final String DIALOG_SETTINGS= "BreadcrumbItemDropDown"; //$NON-NLS-1$
	private static final String DIALOG_HEIGHT= "height"; //$NON-NLS-1$

	private final BreadcrumbItem fParent;
	private final Composite fParentComposite;
	private final ToolBar fToolBar;

	private boolean fMenuIsShown;
	private boolean fEnabled;
	private TreeViewer fDropDownViewer;
	private Shell fShell;
	private boolean isResizingProgrammatically;

	public BreadcrumbItemDropDown(BreadcrumbItem parent, Composite composite) {
		fParent= parent;
		fParentComposite= composite;
		fMenuIsShown= false;
		fEnabled= true;

		fToolBar= new ToolBar(composite, SWT.FLAT);
		fToolBar.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
		SWTUtil.setAccessibilityText(fToolBar, BreadcrumbMessages.BreadcrumbItemDropDown_showDropDownMenu_action_toolTip);
		ToolBarManager manager= new ToolBarManager(fToolBar);

		final Action showDropDownMenuAction= new Action(null, SWT.NONE) {
			@Override
			public void run() {
				Shell shell= fParent.getDropDownShell();
				if (shell != null)
					return;

				shell= fParent.getViewer().getDropDownShell();
				if (shell != null)
					shell.close();

				showMenu();

				fShell.setFocus();
			}
		};

		showDropDownMenuAction.setImageDescriptor(new AccessibleArrowImage(isLTR()));
		showDropDownMenuAction.setToolTipText(BreadcrumbMessages.BreadcrumbItemDropDown_showDropDownMenu_action_toolTip);
		manager.add(showDropDownMenuAction);

		manager.update(true);
		if (IS_MAC_WORKAROUND) {
			manager.getControl().addMouseListener(new MouseAdapter() {
				// see also BreadcrumbItemDetails#addElementListener(Control)
				@Override
				public void mouseDown(MouseEvent e) {
					showDropDownMenuAction.run();
				}
			});
		}
	}

	/**
	 * Return the width of this element.
	 *
	 * @return the width of this element
	 */
	public int getWidth() {
		return fToolBar.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
	}

	/**
	 * Set whether the drop down menu is available.
	 *
	 * @param enabled true if available
	 */
	public void setEnabled(boolean enabled) {
		fEnabled= enabled;

		fToolBar.setVisible(enabled);
	}

	/**
	 * Tells whether the menu is shown.
	 *
	 * @return true if the menu is open
	 */
	public boolean isMenuShown() {
		return fMenuIsShown;
	}

	/**
	 * Returns the shell used for the drop down menu if it is shown.
	 *
	 * @return the drop down shell or <code>null</code>
	 */
	public Shell getDropDownShell() {
		if (!isMenuShown())
			return null;

		return fShell;
	}

	/**
	 * Returns the drop down selection provider.
	 *
	 * @return the selection provider of the drop down if {@link #isMenuShown()}, <code>null</code>
	 *         otherwise
	 */
	public ISelectionProvider getDropDownSelectionProvider() {
		if (!fMenuIsShown)
			return null;

		return fDropDownViewer;
	}

	/**
	 * Opens the drop down menu.
	 */
	public void showMenu() {
		if (CFMLPlugin.DEBUG_BREADCRUMB_ITEM_DROP_DOWN)
			System.out.println("BreadcrumbItemDropDown.showMenu()"); //$NON-NLS-1$

		if (!fEnabled || fMenuIsShown)
			return;

		fMenuIsShown= true;

		fShell= new Shell(fToolBar.getShell(), SWT.RESIZE | SWT.TOOL | SWT.ON_TOP);
		if (CFMLPlugin.DEBUG_BREADCRUMB_ITEM_DROP_DOWN)
			System.out.println("	creating new shell"); //$NON-NLS-1$

		GridLayout layout= new GridLayout(1, false);
		layout.marginHeight= 0;
		layout.marginWidth= 0;
		fShell.setLayout(layout);

		Composite composite= new Composite(fShell, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout gridLayout= new GridLayout(1, false);
		gridLayout.marginHeight= 0;
		gridLayout.marginWidth= 0;
		composite.setLayout(gridLayout);

//        fDropDownViewer= new ProblemTreeViewer(composite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		fDropDownViewer= new TreeViewer(composite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		fDropDownViewer.setUseHashlookup(true);

		final Tree tree= (Tree) fDropDownViewer.getControl();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Object input= fParent.getData();
		fParent.getViewer().configureDropDownViewer(fDropDownViewer, input);
		fDropDownViewer.setInput(input);

		setShellBounds(fShell);
		
		fShell.addControlListener(new ControlAdapter() {
			/*
			 * @see org.eclipse.swt.events.ControlAdapter#controlResized(org.eclipse.swt.events.ControlEvent)
			 */
			@Override
			public void controlResized(ControlEvent e) {
				if (isResizingProgrammatically)
					return;
				
				Point size= fShell.getSize();
				getDialogSettings().put(DIALOG_HEIGHT, size.y);
			}
		});

		fDropDownViewer.addOpenListener(new IOpenListener() {
			@Override
			public void open(OpenEvent event) {
				if (CFMLPlugin.DEBUG_BREADCRUMB_ITEM_DROP_DOWN)
					System.out.println("BreadcrumbItemDropDown.showMenu()$treeViewer>open"); //$NON-NLS-1$

				ISelection selection= event.getSelection();
				if (!(selection instanceof IStructuredSelection))
					return;

				Object element= ((IStructuredSelection) selection).getFirstElement();
				if (element == null)
					return;

				openElement(element);
			}
		});

		tree.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (CFMLPlugin.DEBUG_BREADCRUMB_ITEM_DROP_DOWN)
					System.out.println("BreadcrumbItemDropDown.showMenu()$treeViewer>mouseUp"); //$NON-NLS-1$

				if (e.button != 1)
					return;

				if ((OpenStrategy.getOpenMethod() & OpenStrategy.SINGLE_CLICK) != 0)
					return;

				Item item= tree.getItem(new Point(e.x, e.y));
				if (item == null)
					return;

				openElement(item.getData());
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});

		tree.addMouseMoveListener(new MouseMoveListener() {
			TreeItem fLastItem= null;

			@Override
			public void mouseMove(MouseEvent e) {
				if (tree.equals(e.getSource())) {
					Object o= tree.getItem(new Point(e.x, e.y));
					if ((o != null && !o.equals(fLastItem)) || (fLastItem != null && o == null)) {
						boolean showHandPointer= false;
//						if (o instanceof TreeItem) {
//							Object itemData= ((TreeItem)o).getData();
//							if (itemData instanceof IJavaElement) {
//								int elementType= ((IJavaElement)itemData).getElementType();
//								if (elementType != IJavaElement.JAVA_PROJECT && elementType != IJavaElement.PACKAGE_FRAGMENT && elementType != IJavaElement.PACKAGE_FRAGMENT_ROOT) {
//									showHandPointer= true;
//								}
//							} else if (itemData instanceof IFile) {
//								showHandPointer= true;
//							} else if (itemData instanceof IJarEntryResource) {
//								showHandPointer= ((IJarEntryResource)itemData).isFile();
//							}
//						}
						tree.setCursor(showHandPointer ? tree.getDisplay().getSystemCursor(SWT.CURSOR_HAND) : null);
					}
					if (o instanceof TreeItem) {
						Rectangle clientArea = tree.getClientArea();
						TreeItem currentItem= (TreeItem) o;
						if (!o.equals(fLastItem)) {
							fLastItem= (TreeItem) o;
							tree.setSelection(new TreeItem[] { fLastItem });
						} else if (e.y - clientArea.y < tree.getItemHeight() / 4) {
							// Scroll up
							if (currentItem.getParentItem() == null) {
								int index= tree.indexOf((TreeItem) o);
								if (index < 1)
									return;

								fLastItem= tree.getItem(index - 1);
								tree.setSelection(new TreeItem[] { fLastItem });
							} else {
								Point p= tree.toDisplay(e.x, e.y);
								Item item= fDropDownViewer.scrollUp(p.x, p.y);
								if (item instanceof TreeItem) {
									fLastItem= (TreeItem) item;
									tree.setSelection(new TreeItem[] { fLastItem });
								}
							}
						} else if (clientArea.y + clientArea.height - e.y < tree.getItemHeight() / 4) {
							// Scroll down
							if (currentItem.getParentItem() == null) {
								int index= tree.indexOf((TreeItem) o);
								if (index >= tree.getItemCount() - 1)
									return;

								fLastItem= tree.getItem(index + 1);
								tree.setSelection(new TreeItem[] { fLastItem });
							} else {
								Point p= tree.toDisplay(e.x, e.y);
								Item item= fDropDownViewer.scrollDown(p.x, p.y);
								if (item instanceof TreeItem) {
									fLastItem= (TreeItem) item;
									tree.setSelection(new TreeItem[] { fLastItem });
								}
							}
						}
					} else if (o == null) {
						fLastItem= null;
					}
				}
			}
		});

		tree.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_UP) {
					TreeItem[] selection= tree.getSelection();
					if (selection.length != 1)
						return;

					int selectionIndex= tree.indexOf(selection[0]);
					if (selectionIndex != 0)
						return;

					fShell.close();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		fDropDownViewer.addTreeListener(new ITreeViewerListener() {
			@Override
			public void treeCollapsed(TreeExpansionEvent event) {
			}

			@Override
			public void treeExpanded(TreeExpansionEvent event) {
				tree.setRedraw(false);
				fShell.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						if (fShell.isDisposed())
							return;

						try {
							resizeShell(fShell);
						} finally {
							tree.setRedraw(true);
						}
					}
				});
			}
		});

		int index= fParent.getViewer().getIndexOfItem(fParent);
		if (index < fParent.getViewer().getItemCount() - 1) {
			BreadcrumbItem childItem= fParent.getViewer().getItem(index + 1);
			Object child= childItem.getData();

			fDropDownViewer.setSelection(new StructuredSelection(child), true);

			TreeItem[] selection= tree.getSelection();
			if (selection.length > 0) {
				tree.setTopItem(selection[0]);
			}
		}

		fShell.setVisible(true);
		installCloser(fShell);
	}

	private void openElement(Object data) {
		if (data == null)
			return;

		// This might or might not open an editor
		fParent.getViewer().fireMenuSelection(data);

		boolean treeHasFocus= !fShell.isDisposed() && fDropDownViewer.getTree().isFocusControl();

		if (CFMLPlugin.DEBUG_BREADCRUMB_ITEM_DROP_DOWN) {
			System.out.println("	isDisposed: " + fShell.isDisposed()); //$NON-NLS-1$
			System.out.println("	shell hasFocus: " + (!fShell.isDisposed() && fShell.isFocusControl())); //$NON-NLS-1$
			System.out.println("	tree hasFocus: " + treeHasFocus); //$NON-NLS-1$
		}

		if (fShell.isDisposed())
			return;

		if (!treeHasFocus) {
			fShell.close();
			return;
		}

		toggleExpansionState(data);
	}

	private void toggleExpansionState(Object element) {
		Tree tree= fDropDownViewer.getTree();
		if (fDropDownViewer.getExpandedState(element))
			fDropDownViewer.collapseToLevel(element, 1);
		else {
			tree.setRedraw(false);
			try {
				fDropDownViewer.expandToLevel(element, 1);
				resizeShell(fShell);
			} finally {
				tree.setRedraw(true);
			}
		}
	}

	/**
	 * The closer closes the given shell when the focus is lost.
	 *
	 * @param shell the shell to install the closer to
	 */
	private void installCloser(final Shell shell) {
		final Listener focusListener= new Listener() {
			@Override
			public void handleEvent(Event event) {
				Widget focusElement= event.widget;
				boolean isFocusBreadcrumbTreeFocusWidget= focusElement == shell || focusElement instanceof Tree && ((Tree)focusElement).getShell() == shell;
				boolean isFocusWidgetParentShell= focusElement instanceof Control && ((Control)focusElement).getShell().getParent() == shell;

				switch (event.type) {
					case SWT.FocusIn:
						if (CFMLPlugin.DEBUG_BREADCRUMB_ITEM_DROP_DOWN)
							System.out.println("focusIn - is breadcrumb tree: " + isFocusBreadcrumbTreeFocusWidget); //$NON-NLS-1$

						if (!isFocusBreadcrumbTreeFocusWidget && !isFocusWidgetParentShell) {
							if (CFMLPlugin.DEBUG_BREADCRUMB_ITEM_DROP_DOWN)
								System.out.println("==> closing shell since focus in other widget"); //$NON-NLS-1$
							shell.close();
						}
						break;

					case SWT.FocusOut:
						if (CFMLPlugin.DEBUG_BREADCRUMB_ITEM_DROP_DOWN)
							System.out.println("focusOut - is breadcrumb tree: " + isFocusBreadcrumbTreeFocusWidget); //$NON-NLS-1$

						if (event.display.getActiveShell() == null) {
							if (CFMLPlugin.DEBUG_BREADCRUMB_ITEM_DROP_DOWN)
								System.out.println("==> closing shell since event.display.getActiveShell() == null"); //$NON-NLS-1$
							shell.close();
						}
						break;

					default:
						Assert.isTrue(false);
				}
			}
		};

		final Display display= shell.getDisplay();
		display.addFilter(SWT.FocusIn, focusListener);
		display.addFilter(SWT.FocusOut, focusListener);

		final ControlListener controlListener= new ControlListener() {
			@Override
			public void controlMoved(ControlEvent e) {
				shell.close();
			}

			@Override
			public void controlResized(ControlEvent e) {
				shell.close();
			}
		};
		fToolBar.getShell().addControlListener(controlListener);

		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (CFMLPlugin.DEBUG_BREADCRUMB_ITEM_DROP_DOWN)
					System.out.println("==> shell disposed"); //$NON-NLS-1$

				display.removeFilter(SWT.FocusIn, focusListener);
				display.removeFilter(SWT.FocusOut, focusListener);

				if (!fToolBar.isDisposed()) {
					fToolBar.getShell().removeControlListener(controlListener);
				}
			}
		});
		shell.addShellListener(new ShellListener() {
			@Override
			public void shellActivated(ShellEvent e) {
			}

			@Override
			public void shellClosed(ShellEvent e) {
				if (CFMLPlugin.DEBUG_BREADCRUMB_ITEM_DROP_DOWN)
					System.out.println("==> shellClosed"); //$NON-NLS-1$

				if (!fMenuIsShown)
					return;

				fMenuIsShown= false;
				fDropDownViewer= null;
			}

			@Override
			public void shellDeactivated(ShellEvent e) {
			}

			@Override
			public void shellDeiconified(ShellEvent e) {
			}

			@Override
			public void shellIconified(ShellEvent e) {
			}
		});
	}

	private IDialogSettings getDialogSettings() {
		IDialogSettings javaSettings= CFMLPlugin.getDefault().getDialogSettings();
		IDialogSettings settings= javaSettings.getSection(DIALOG_SETTINGS);
		if (settings == null)
			settings= javaSettings.addNewSection(DIALOG_SETTINGS);
		return settings;
	}
	
	private int getMaxHeight() {
		try {
			return getDialogSettings().getInt(DIALOG_HEIGHT);
		} catch (NumberFormatException e) {
			return DROP_DOWN_DEFAULT_MAX_HEIGHT;
		}
	}
	
	/**
	 * Calculates a useful size for the given shell.
	 *
	 * @param shell the shell to calculate the size for.
	 */
	private void setShellBounds(Shell shell) {
		Rectangle rect= fParentComposite.getBounds();
		Rectangle toolbarBounds= fToolBar.getBounds();

		shell.pack();
		Point size= shell.getSize();
		int height= Math.max(Math.min(size.y, getMaxHeight()), DROP_DOWN_DEFAULT_MIN_HEIGHT);
		int width= Math.max(Math.min(size.x, DROP_DOWN_MAX_WIDTH), DROP_DOWN_MIN_WIDTH);

		int imageBoundsX= 0;
		if (fDropDownViewer.getTree().getItemCount() > 0) {
			TreeItem item= fDropDownViewer.getTree().getItem(0);
			imageBoundsX= item.getImageBounds(0).x;
		}

		Rectangle trim= fShell.computeTrim(0, 0, width, height);
		int x= toolbarBounds.x + toolbarBounds.width + 2 + trim.x - imageBoundsX;
		if (!isLTR())
			x+= width;

		Point pt= new Point(x, rect.y + rect.height);
		pt= fParentComposite.toDisplay(pt);

		Rectangle monitor= getClosestMonitor(shell.getDisplay(), pt).getClientArea();
		int overlap= (pt.x + width) - (monitor.x + monitor.width);
		if (overlap > 0)
			pt.x-= overlap;
		if (pt.x < monitor.x)
			pt.x= monitor.x;

		shell.setLocation(pt);
		shell.setSize(width, height);
	}

	/**
	 * Returns the monitor whose client area contains the given point. If no monitor contains the
	 * point, returns the monitor that is closest to the point.
	 * <p>
	 * Copied from <code>org.eclipse.jface.window.Window.getClosestMonitor(Display, Point)</code>
	 * </p>
	 *
	 * @param display the display showing the monitors
	 * @param point point to find (display coordinates)
	 * @return the monitor closest to the given point
	 */
	private static Monitor getClosestMonitor(Display display, Point point) {
		int closest= Integer.MAX_VALUE;

		Monitor[] monitors= display.getMonitors();
		Monitor result= monitors[0];

		for (int i= 0; i < monitors.length; i++) {
			Monitor current= monitors[i];

			Rectangle clientArea= current.getClientArea();

			if (clientArea.contains(point))
				return current;

			int distance= Geometry.distanceSquared(Geometry.centerPoint(clientArea), point);
			if (distance < closest) {
				closest= distance;
				result= current;
			}
		}

		return result;
	}

	/**
	 * Set the size of the given shell such that more content can be shown. The shell size does not
	 * exceed a user-configurable maximum.
	 *
	 * @param shell the shell to resize
	 */
	private void resizeShell(final Shell shell) {
		Point size= shell.getSize();
		int currentWidth= size.x;
		int currentHeight= size.y;

		int maxHeight= getMaxHeight();
		
		if (currentHeight >= maxHeight && currentWidth >= DROP_DOWN_MAX_WIDTH)
			return;

		Point preferedSize= shell.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);

		int newWidth;
		if (currentWidth >= DROP_DOWN_MAX_WIDTH) {
			newWidth= currentWidth;
		} else {
			newWidth= Math.min(Math.max(preferedSize.x, currentWidth), DROP_DOWN_MAX_WIDTH);
		}
		int newHeight;
		if (currentHeight >= maxHeight) {
			newHeight= currentHeight;
		} else {
			newHeight= Math.min(Math.max(preferedSize.y, currentHeight), maxHeight);
		}

		if (newHeight != currentHeight || newWidth != currentWidth) {
			shell.setRedraw(false);
			try {
				isResizingProgrammatically= true;
				shell.setSize(newWidth, newHeight);
				if (!isLTR()) {
					Point location= shell.getLocation();
					shell.setLocation(location.x - (newWidth - currentWidth), location.y);
				}
			} finally {
				isResizingProgrammatically= false;
				shell.setRedraw(true);
			}
		}
	}

	/**
	 * Tells whether this the breadcrumb is in LTR or RTL mode.
	 *
	 * @return <code>true</code> if the breadcrumb in left-to-right mode, <code>false</code>
	 *         otherwise
	 */
	private boolean isLTR() {
		return (fParentComposite.getStyle() & SWT.RIGHT_TO_LEFT) == 0;
	}
}
