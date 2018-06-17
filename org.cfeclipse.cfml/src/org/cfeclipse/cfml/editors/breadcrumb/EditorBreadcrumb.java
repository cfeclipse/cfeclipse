package org.cfeclipse.cfml.editors.breadcrumb;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

import org.eclipse.core.runtime.Assert;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.StructuredSelection;

import org.eclipse.jface.text.ITextViewer;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;

import org.eclipse.ui.texteditor.ITextEditor;


/**
 * The editor breadcrumb shows the parent chain of the active editor item inside a
 * {@link BreadcrumbViewer}.
 *
 * <p>
 * Clients must implement the abstract methods.
 * </p>
 *
 * @since 3.4
 */
public abstract class EditorBreadcrumb implements IBreadcrumb {

	private static final String ACTIVE_TAB_BG_END= "org.eclipse.ui.workbench.ACTIVE_TAB_BG_END"; //$NON-NLS-1$

	private ITextEditor fTextEditor;
	private ITextViewer fTextViewer;

	private BreadcrumbViewer fBreadcrumbViewer;

	private boolean fHasFocus;
	private boolean fIsActive;

	private Composite fComposite;

	private Listener fDisplayFocusListener;
	private Listener fDisplayKeyListener;

	private IPropertyChangeListener fPropertyChangeListener;

	private ISelection fOldTextSelection;

	private IPartListener fPartListener;


	/**
	 * The editor inside which this breadcrumb is shown.
	 *
	 * @param editor the editor
	 */
	public EditorBreadcrumb(ITextEditor editor) {
		setTextEditor(editor);
	}

	/**
	 * The active element of the editor.
	 *
	 * @return the active element of the editor, or <b>null</b> if none
	 */
	protected abstract Object getCurrentInput();

	/**
	 * Create and configure the viewer used to display the parent chain.
	 *
	 * @param parent the parent composite
	 * @return the viewer
	 */
	protected abstract BreadcrumbViewer createViewer(Composite parent);

	/**
	 * Reveal the given element in the editor if possible.
	 *
	 * @param element the element to reveal
	 * @return true if the element could be revealed
	 */
	protected abstract boolean reveal(Object element);

	/**
	 * Open the element in a new editor if possible.
	 *
	 * @param element the element to open
	 * @return true if the element could be opened
	 */
	protected abstract boolean open(Object element);

	/**
	 * Create an action group for the context menu shown for the selection of the given selection
	 * provider or <code>null</code> if no context menu should be shown.
	 *
	 * @param selectionProvider the provider of the context selection
	 * @return action group to use to fill the context menu or <code>null</code>
	 */
	protected abstract ActionGroup createContextMenuActionGroup(ISelectionProvider selectionProvider);

	/**
	 * The breadcrumb has been activated. Implementors must retarget the editor actions to the
	 * breadcrumb aware actions.
	 */
	protected abstract void activateBreadcrumb();

	/**
	 * The breadcrumb has been deactivated. Implementors must retarget the breadcrumb actions to the
	 * editor actions.
	 */
	protected abstract void deactivateBreadcrumb();

	@Override
	public ISelectionProvider getSelectionProvider() {
		return fBreadcrumbViewer;
	}

	protected void setTextViewer(ITextViewer viewer) {
		fTextViewer= viewer;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.javaeditor.IBreadcrumb#setInput(java.lang.Object)
	 */
	@Override
	public void setInput(Object element) {
		if (element == null)
			return;

		Object input= fBreadcrumbViewer.getInput();
		if (input == element || element.equals(input))
			return;

		if (fBreadcrumbViewer.isDropDownOpen())
			return;

		fBreadcrumbViewer.setInput(element);
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.javaeditor.IBreadcrumb#setFocus()
	 */
	@Override
	public void activate() {
		if (fBreadcrumbViewer.getSelection().isEmpty())
			fBreadcrumbViewer.setSelection(new StructuredSelection(fBreadcrumbViewer.getInput()));
		fBreadcrumbViewer.setFocus();
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.javaeditor.breadcrumb.IBreadcrumb#isActive()
	 */
	@Override
	public boolean isActive() {
		return fIsActive;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.javaeditor.IBreadcrumb#createContent(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public Control createContent(Composite parent) {
		Assert.isTrue(fComposite == null, "Content must only be created once."); //$NON-NLS-1$

		boolean rtl= (getTextEditor().getSite().getShell().getStyle() & SWT.RIGHT_TO_LEFT) != 0;

		fComposite= new Composite(parent, rtl ? SWT.RIGHT_TO_LEFT : SWT.NONE);
		GridData data= new GridData(SWT.FILL, SWT.TOP, true, false);
		fComposite.setLayoutData(data);
		GridLayout gridLayout= new GridLayout(1, false);
		gridLayout.marginWidth= 0;
		gridLayout.marginHeight= 0;
		gridLayout.verticalSpacing= 0;
		gridLayout.horizontalSpacing= 0;
		fComposite.setLayout(gridLayout);

		fDisplayFocusListener= new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (isBreadcrumbEvent(event)) {
					if (fHasFocus)
						return;

					fIsActive= true;

					focusGained();
				} else {
					if (!fIsActive)
						return;

					boolean hasTextFocus= fTextViewer.getTextWidget().isFocusControl();
					if (hasTextFocus) {
						fIsActive= false;
					}

					if (!fHasFocus)
						return;

					focusLost();
				}
			}
		};
		Display.getCurrent().addFilter(SWT.FocusIn, fDisplayFocusListener);

		fBreadcrumbViewer= createViewer(fComposite);
		fBreadcrumbViewer.getControl().setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		fBreadcrumbViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				Object element= ((IStructuredSelection) event.getSelection()).getFirstElement();
				if (element == null)
					return;

				BreadcrumbItem item= (BreadcrumbItem) fBreadcrumbViewer.doFindItem(element);
				if (item == null)
					return;

				int index= fBreadcrumbViewer.getIndexOfItem(item);
				BreadcrumbItem parentItem= fBreadcrumbViewer.getItem(index - 1);
				parentItem.openDropDownMenu();
			}
		});

		fBreadcrumbViewer.addOpenListener(new IOpenListener() {
			@Override
			public void open(OpenEvent event) {
				doRevealOrOpen(event.getSelection());
			}
		});

		fBreadcrumbViewer.addMenuDetectListener(new MenuDetectListener() {
			@Override
			public void menuDetected(MenuDetectEvent event) {
				ISelectionProvider selectionProvider;
				if (fBreadcrumbViewer.isDropDownOpen()) {
					selectionProvider= fBreadcrumbViewer.getDropDownSelectionProvider();
				} else {
					selectionProvider= fBreadcrumbViewer;
				}

				ActionGroup actionGroup= createContextMenuActionGroup(selectionProvider);
				if (actionGroup == null)
					return;

				try {
					MenuManager manager= new MenuManager();
					actionGroup.setContext(new ActionContext(selectionProvider.getSelection()));
					actionGroup.fillContextMenu(manager);

					getTextEditor().getEditorSite().registerContextMenu(manager, selectionProvider, false);

					if (manager.isEmpty())
						return;

					Menu menu= manager.createContextMenu(fBreadcrumbViewer.getControl());
					menu.setLocation(event.x + 10, event.y + 10);
					menu.setVisible(true);
					while (!menu.isDisposed() && menu.isVisible()) {
						if (!menu.getDisplay().readAndDispatch())
							menu.getDisplay().sleep();
					}
				} finally {
					actionGroup.dispose();
				}
			}
		});

		fPropertyChangeListener= new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (ACTIVE_TAB_BG_END.equals(event.getProperty())) {
					if (fComposite.isFocusControl()) {
						fComposite.setBackground(JFaceResources.getColorRegistry().get(ACTIVE_TAB_BG_END));
					}
				}
			}
		};
		JFaceResources.getColorRegistry().addListener(fPropertyChangeListener);

		return fComposite;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.javaeditor.IEditorViewPart#dispose()
	 */
	@Override
	public void dispose() {
		if (fPropertyChangeListener != null) {
			JFaceResources.getColorRegistry().removeListener(fPropertyChangeListener);
		}
		if (fDisplayFocusListener != null) {
			Display.getDefault().removeFilter(SWT.FocusIn, fDisplayFocusListener);
		}
		deinstallDisplayListeners();
		if (fPartListener != null) {
			getTextEditor().getSite().getPage().removePartListener(fPartListener);
		}

		setTextEditor(null);
	}

	/**
	 * Either reveal the selection in the editor or open the selection in a new editor. If both fail
	 * open the child pop up of the selected element.
	 *
	 * @param selection the selection to open
	 */
	private void doRevealOrOpen(ISelection selection) {
		if (doReveal(selection)) {
			fTextViewer.getTextWidget().setFocus();
		} else if (doOpen(selection)) {
			fIsActive= false;
			focusLost();
			fBreadcrumbViewer.setInput(getCurrentInput());
		}
	}

	private boolean doOpen(ISelection selection) {
		if (!(selection instanceof StructuredSelection))
			return false;

		StructuredSelection structuredSelection= (StructuredSelection) selection;
		if (structuredSelection.isEmpty())
			return false;

		return open(structuredSelection.getFirstElement());
	}

	private boolean doReveal(ISelection selection) {
		if (!(selection instanceof StructuredSelection))
			return false;

		StructuredSelection structuredSelection= (StructuredSelection) selection;
		if (structuredSelection.isEmpty())
			return false;

		if (fOldTextSelection != null) {
			getTextEditor().getSelectionProvider().setSelection(fOldTextSelection);

			boolean result= reveal(structuredSelection.getFirstElement());

			fOldTextSelection= getTextEditor().getSelectionProvider().getSelection();
			getTextEditor().getSelectionProvider().setSelection(new StructuredSelection(this));
			return result;
		} else {
			return reveal(structuredSelection.getFirstElement());
		}
	}

	/**
	 * Focus has been transfered into the breadcrumb.
	 */
	private void focusGained() {
		if (fHasFocus)
			focusLost();

		fComposite.setBackground(JFaceResources.getColorRegistry().get(ACTIVE_TAB_BG_END));
		fHasFocus= true;

		installDisplayListeners();

		activateBreadcrumb();

		getTextEditor().getEditorSite().getActionBars().updateActionBars();

		fOldTextSelection= getTextEditor().getSelectionProvider().getSelection();

		getTextEditor().getSelectionProvider().setSelection(new StructuredSelection(this));
	}

	/**
	 * Focus has been revoked from the breadcrumb.
	 */
	private void focusLost() {
		if (!fHasFocus)
			return;

		fComposite.setBackground(null);
		fHasFocus= false;

		deinstallDisplayListeners();

		deactivateBreadcrumb();

		getTextEditor().getEditorSite().getActionBars().updateActionBars();

		getTextEditor().getSelectionProvider().setSelection(fOldTextSelection);
		fOldTextSelection= null;
	}

	/**
	 * Installs all display listeners.
	 */
	private void installDisplayListeners() {
		//Sanity check
		deinstallDisplayListeners();

		fDisplayKeyListener= new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.keyCode != SWT.ESC)
					return;

				if (!isBreadcrumbEvent(event))
					return;

				fTextViewer.getTextWidget().setFocus();
			}
		};
		Display.getDefault().addFilter(SWT.KeyDown, fDisplayKeyListener);
	}

	/**
	 * Removes all previously installed display listeners.
	 */
	private void deinstallDisplayListeners() {
		if (fDisplayKeyListener != null) {
			Display.getDefault().removeFilter(SWT.KeyDown, fDisplayKeyListener);
			fDisplayKeyListener= null;
		}
	}

	/**
	 * Tells whether the given event was issued inside the breadcrumb viewer's control.
	 *
	 * @param event the event to inspect
	 * @return <code>true</code> if event was generated by a breadcrumb child
	 */
	private boolean isBreadcrumbEvent(Event event) {
		if (fBreadcrumbViewer == null)
			return false;

		Widget item= event.widget;
		if (!(item instanceof Control))
			return false;

		Shell dropDownShell= fBreadcrumbViewer.getDropDownShell();
		if (dropDownShell != null && isChild((Control) item, dropDownShell))
			return true;

		return isChild((Control) item, fBreadcrumbViewer.getControl());
	}

	private boolean isChild(Control child, Control parent) {
		if (child == null)
			return false;

		if (child == parent)
			return true;

		return isChild(child.getParent(), parent);
	}

	/**
	 * Sets the text editor for which this breadcrumb is.
	 *
	 * @param textEditor the text editor to be used
	 */
	protected void setTextEditor(ITextEditor textEditor) {
		fTextEditor= textEditor;

		if (fTextEditor == null)
			return;

		fPartListener= new IPartListener() {

			@Override
			public void partActivated(IWorkbenchPart part) {
				if (part == fTextEditor && fHasFocus) {
					//focus-in event comes before part activation and the
					//workbench activates the editor -> reactivate the breadcrumb
					//if it is the active part.
					focusGained();
				}
			}

			@Override
			public void partBroughtToTop(IWorkbenchPart part) {
			}

			@Override
			public void partClosed(IWorkbenchPart part) {

			}

			@Override
			public void partDeactivated(IWorkbenchPart part) {
				if (part == fTextEditor && fHasFocus) {
					focusLost();
				}
			}

			@Override
			public void partOpened(IWorkbenchPart part) {
			}

		};
		fTextEditor.getSite().getPage().addPartListener(fPartListener);
	}

	/**
	 * This breadcrumb's text editor.
	 *
	 * @return the text editor
	 */
	protected ITextEditor getTextEditor() {
		return fTextEditor;
	}

}