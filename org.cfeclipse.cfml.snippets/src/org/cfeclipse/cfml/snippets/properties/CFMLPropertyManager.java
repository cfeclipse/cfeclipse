/*
 * Created on Apr 29, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package org.cfeclipse.cfml.snippets.properties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.cfeclipse.cfml.snippets.SnippetPlugin;
import org.cfeclipse.cfml.snippets.preferences.CFMLPreferenceConstants;
import org.cfeclipse.cfml.snippets.preferences.CFMLPreferenceManager;
import org.cfeclipse.cfml.snippets.views.snips.SnipTreeView;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Stephen Milligan
 * 
 *         This controls the properies for the per project settings
 * 
 *         TODO: We should put things like project mapping that the open new
 *         browser works with
 */
public class CFMLPropertyManager {
	// private PreferenceStore store;
	private CFMLPreferenceManager preferenceManager;
	/** The list of items that are listenening for property changes */
	private ArrayList listeners;
	private String currentSnippetsPath = "";

	public CFMLPropertyManager() {
		super();
		// this.store = CFMLPlugin.getDefault().getPropertyStore();

		try {
			// store.load();
		} catch (Exception e) {
			// System.err.println("CFMLPropertyManager::CFMLPropertyManager() - Couldn't load property store");
			// e.printStackTrace();
		}
		this.preferenceManager = SnippetPlugin.getDefault().getPreferenceManager();
		this.currentSnippetsPath = preferenceManager.snippetsPath();
		this.listeners = new ArrayList();
	}

	public IPreferenceStore getStore(IProject project) {
		return new ProjectPropertyStore(project);
	}

	public void initializeDefaultValues(IProject project) {
		IPreferenceStore store = new ProjectPropertyStore(project);
		store.setDefault(CFMLPreferenceConstants.P_SNIPPETS_PATH, preferenceManager.snippetsPath());
	}

	public String snippetsPath(IProject project) {
		IPreferenceStore store = new ProjectPropertyStore(project);
		String snippetsPath = store.getString(CFMLPreferenceConstants.P_SNIPPETS_PATH).trim();
		if(snippetsPath.length() == 0) {
			snippetsPath = preferenceManager.snippetsPath();
			if(snippetsPath.length() == 0) {				
				return defaultSnippetsPath();
			}			
		}
		return snippetsPath;
	}

	public String defaultSnippetsPath() {
		return preferenceManager.getPluginStateLocation()+"/snippets";
	}

	private ISelectionListener listener = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
			String lastSnippetPath = currentSnippetsPath;
			IStructuredSelection currentSelection =  getSelection();
			if(currentSelection == null) {
				currentSnippetsPath = defaultSnippetsPath();
			}
			IResource[] currentResource = getSelectedResources(currentSelection);
			if(currentResource.length == 0) {
				currentSnippetsPath = defaultSnippetsPath();
			} else {				
				currentSnippetsPath = snippetsPath(currentResource[0].getProject());
			}
			if(lastSnippetPath!=currentSnippetsPath || lastSnippetPath.length() == 0) {
				String id = SnipTreeView.ID_SNIPVIEWTREE;
				IViewReference viewReferences[] = sourcepart.getSite().getWorkbenchWindow().getActivePage().getViewReferences();
				for (int i = 0; i < viewReferences.length; i++) {
					if (id.equals(viewReferences[i].getId())) {
						SnipTreeView snipTreeView = (SnipTreeView) (viewReferences[i].getView(false));
						if(snipTreeView!= null)snipTreeView.reloadSnippets(false);
					}
				}
			}
		}
	};
	
	
	
	public ISelectionListener getListener() {
		return listener;
	}

	/**
	 * @param selection
	 * @return the resources in the selection
	 */
	private IResource[] getSelectedResources(IStructuredSelection selection) {
		Set<IResource> result = new HashSet<IResource>();
		for (Object o : selection.toList()) {
			IResource resource = (IResource) getAdapter(o, IResource.class);
			if (resource != null)
				result.add(resource);
		}
		return result.toArray(new IResource[result.size()]);
	}

	private Object getAdapter(Object adaptable, Class c) {
		if (c.isInstance(adaptable)) {
			return adaptable;
		}
		if (adaptable instanceof IAdaptable) {
			IAdaptable a = (IAdaptable) adaptable;
			Object adapter = a.getAdapter(c);
			if (c.isInstance(adapter)) {
				return adapter;
			}
		}
		return null;
	}
	
	public String getSnippetsPath() {
		return currentSnippetsPath;
	}

	public void setSnippetsPath(String path, IProject project) {
		IPreferenceStore store = new ProjectPropertyStore(project);
		firePropertyChangeEvent(store, CFMLPreferenceConstants.P_SNIPPETS_PATH, store
				.getString(CFMLPreferenceConstants.P_SNIPPETS_PATH), path);
		store.setValue(CFMLPreferenceConstants.P_SNIPPETS_PATH, path);
	}

	public void setComponentRoot(String root, IProject project) {
		IPreferenceStore store = new ProjectPropertyStore(project);
		store.setValue("componentRoot", root);

	}

	protected IStructuredSelection getSelection() {
		IWorkbenchPartSite site = Workbench.getInstance().getActiveWorkbenchWindow()
		.getActivePage().getActivePart().getSite();
		if (site != null && site.getSelectionProvider() != null) {
			final ISelection partSelection = site
					.getSelectionProvider().getSelection();
			if (partSelection != null) {
				if (partSelection instanceof IStructuredSelection) {
					return (IStructuredSelection) partSelection;
				} else if (partSelection instanceof ITextSelection) {
					IResource resource = ResourceUtil.getResource(site.getWorkbenchWindow().getActivePage()
							.getActiveEditor().getEditorInput());
					return new StructuredSelection(resource);
				}
			}
		}
		return null;
	}	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#addPropertyChangeListener
	 * (org.eclipse.jface.util.IPropertyChangeListener)
	 */
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#firePropertyChangeEvent
	 * (java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void firePropertyChangeEvent(Object srcObj, String name, Object oldValue, Object newValue) {
		Iterator listenerIter = this.listeners.iterator();
		PropertyChangeEvent event = new PropertyChangeEvent(srcObj, name, oldValue, newValue);

		while (listenerIter.hasNext()) {
			IPropertyChangeListener listener = (IPropertyChangeListener) listenerIter.next();
			listener.propertyChange(event);
		}
	}

}
