package org.cfeclipse.cfml.editors.actions;

import org.cfeclipse.cfml.editors.selection.CFTextSelection;
import org.eclipse.core.runtime.Assert;

import org.eclipse.swt.widgets.Shell;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import org.eclipse.jface.text.ITextSelection;

import org.eclipse.ui.IWorkbenchSite;

/**
 * Action that dispatches the <code>IAction#run() and the 
 * <code>ISelectionChangedListener#selectionChanged 
 * according to the type of the selection. 
 * 
 * <ul>
 * 	<li>if selection is of type ITextSelection then
 * 	<code>run(ITextSelection) and selectionChanged(ITextSelection)
 * 	is called.</li> 
 * 	<li>if selection is of type IStructuredSelection then
 * 	<code>run(IStructuredSelection) and 
 * 	selectionChanged(IStructuredSelection)</code> is called.
 * 	<li>default is to call run(ISelection) and 
 * 	selectionChanged(ISelection)</code>.
 * </ul>
 * 
 * <p>
 * Note: This class is not intended to be subclassed outside the JDT UI plug-in.
 * </p>
 * 
 * @since 2.0
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public abstract class SelectionDispatchAction extends Action implements ISelectionChangedListener {
	
	private IWorkbenchSite fSite;
	private ISelectionProvider fSpecialSelectionProvider;
	
	/**
	 * Creates a new action with no text and no image.
	 * <p>
	 * Configure the action later using the set methods.
	 * </p>
	 * 
	 * @param site the site this action is working on
	 */
	protected SelectionDispatchAction(IWorkbenchSite site) {
		Assert.isNotNull(site);
		fSite= site;
	}

	/**
	 * Creates a new action with no text and no image
	 * 
	 * <p>
	 * Configure the action later using the set methods.
	 * </p>
	 * 
	 * @param site the site this action is working on
	 * @param provider a special selection provider which is used
	 * instead of the site's selection provider or <code>null to use the site's
	 * selection provider. Clients can for example use a {@link ConvertingSelectionProvider}
	 * to first convert a selection before passing it to the action.
	 * @return 
	 *  
	 * @since 3.2
	 * @deprecated Use {@link #setSpecialSelectionProvider(ISelectionProvider)} instead. This constructor will be
	 * removed after 3.2 M5.
	 */
	protected  void setSpecialSelectionProvider(IWorkbenchSite site, ISelectionProvider provider) {
		setSpecialSelectionProvider(provider);
	}

	/**
	 * Returns the site owning this action.
	 * 
	 * @return the site owning this action
	 */
	public IWorkbenchSite getSite() {
		return fSite;
	}

	/**
	 * Returns the selection provided by the site owning this action.
	 * 
	 * @return the site's selection
	 */	
	public ISelection getSelection() {
		ISelectionProvider selectionProvider= getSelectionProvider();
		if (selectionProvider != null)
			return selectionProvider.getSelection();
		else
			return null;
	}

	/**
	 * Returns the shell provided by the site owning this action.
	 * 
	 * @return the site's shell	
	 */
	public Shell getShell() {
		return fSite.getShell();
	}
	
	/**
	 * Returns the selection provider managed by the site owning this action or the selection
	 * provider explicitly set in {@link #setSpecialSelectionProvider(ISelectionProvider)}.
	 * 
	 * @return the site's selection provider	
	 */
	public ISelectionProvider getSelectionProvider() {
		if (fSpecialSelectionProvider != null) {
			return fSpecialSelectionProvider;
		}
		return fSite.getSelectionProvider();
	}
	
	/**
	 * Sets a special selection provider which will be used instead of the site's selection provider.
	 * This method should be used directly after constructing the action and before the action is
	 * registered as a selection listener. The invocation will not a perform a selection change notification. 
	 * 
	 * @param provider a special selection provider which is used
	 * instead of the site's selection provider or <code>null to use the site's
	 * selection provider. Clients can for example use a {@link ConvertingSelectionProvider}
	 * to first convert a selection before passing it to the action.
	 * 
	 * @since 3.2
	 */
	public void setSpecialSelectionProvider(ISelectionProvider provider) {
		fSpecialSelectionProvider= provider;
	}

	/**
	 * Updates the action's enablement state according to the given selection. This
	 * default implementation calls one of the <code>selectionChanged
	 * methods depending on the type of the passed selection.
	 * 
	 * @param selection the selection this action is working on
	 */
	public void update(ISelection selection) {
		dispatchSelectionChanged(selection);
	}

	/**
	 * Notifies this action that the given structured selection has changed. This default
	 * implementation calls <code>selectionChanged(ISelection selection).
	 * 
	 * @param selection the new selection
 	 */
	public void selectionChanged(IStructuredSelection selection) {
		selectionChanged((ISelection)selection);
	}

	/**
	 * Executes this actions with the given structured selection. This default implementation
	 * calls <code>run(ISelection selection).
	 * 
	 * @param selection the selection
	 */
	public void run(IStructuredSelection selection) {
		run((ISelection)selection);
	}
	
	/**
	 * Note: This method is for internal use only. Clients should not call this method.
	 * 
	 * @param selection the selection
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	public void selectionChanged(CFTextSelection selection) {
		selectionChanged((ITextSelection)selection);
	}
	
	/**
	 * Note: This method is for internal use only. Clients should not call this method.
	 * 
	 * @param selection the selection
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	public void run(CFTextSelection selection) {
		run((ITextSelection)selection);
	}
	
	/**
	 * Notifies this action that the given text selection has changed.  This default
	 * implementation calls <code>selectionChanged(ISelection selection).
	 * 
	 * @param selection the new selection
 	 */
	public void selectionChanged(ITextSelection selection) {
		selectionChanged((ISelection)selection);
	}
	
	/**
	 * Executes this actions with the given text selection. This default implementation
	 * calls <code>run(ISelection selection).
	 * 
	 * @param selection the selection
	 */
	public void run(ITextSelection selection) {
		run((ISelection)selection);
	}
	
	/**
	 * Notifies this action that the given selection has changed.  This default
	 * implementation sets the action's enablement state to <code>false.
	 * 
	 * @param selection the new selection
 	 */
	public void selectionChanged(ISelection selection) {
		setEnabled(true);
	}
	
	/**
	 * Executes this actions with the given selection. This default implementation
	 * does nothing.
	 * 
	 * @param selection the selection
	 */
	public void run(ISelection selection) {
	}

	/* (non-Javadoc)
	 * Method declared on IAction.
	 */
	public void run() {
		dispatchRun(getSelection());
	}
	
	/* (non-Javadoc)
	 * Method declared on ISelectionChangedListener.
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		dispatchSelectionChanged(event.getSelection());
	}

	private void dispatchSelectionChanged(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			selectionChanged((IStructuredSelection)selection);
		} else if (selection instanceof CFTextSelection) {
			selectionChanged((CFTextSelection)selection);
		} else if (selection instanceof ITextSelection) {
			selectionChanged((ITextSelection)selection);
		} else {
			selectionChanged(selection);
		}
	}

	private void dispatchRun(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			run((IStructuredSelection)selection);
		} else if (selection instanceof CFTextSelection) {
			run((CFTextSelection)selection);
	    } else if (selection instanceof ITextSelection) {
			run((ITextSelection)selection);
		} else {
			run(selection);
		}
	}
}
