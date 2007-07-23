/*
 * Created on Jul 22, 2004
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
package org.cfeclipse.cfml.editors.actions;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.preferences.ScribblePadPreferenceConstants;
import org.cfeclipse.cfml.views.browser.BrowserView;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Stephen Milligan
 */
public class LoadScribblePadAction implements IWorkbenchWindowActionDelegate,IEditorActionDelegate {

	protected ITextEditor editor = null;

	private String scribbleFileName, scribbleProjectName, scribbleURL;

	boolean clearOnLoad, loadBrowser;

	class ScribbleSettings {
		public String path;
		public String url;
		public String project;
	}
	
	private IPreferenceStore store;

	public LoadScribblePadAction() {
		store = CFMLPlugin.getDefault().getPreferenceStore();
	}

	public void run(IAction action) {

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		scribbleFileName = store
				.getString(ScribblePadPreferenceConstants.P_SCRIBBLE_PAD_FILE);
		scribbleProjectName = store
				.getString(ScribblePadPreferenceConstants.P_SCRIBBLE_PROJECT_NAME);
		clearOnLoad = store
				.getBoolean(ScribblePadPreferenceConstants.P_SCRIBBLE_CLEAR_ON_LOAD);
		loadBrowser = store
				.getBoolean(ScribblePadPreferenceConstants.P_SCRIBBLE_LOAD_BROWSER);
		scribbleURL = store
				.getString(ScribblePadPreferenceConstants.P_SCRIBBLE_URL);

		boolean showDialog = false;
		String dialogMessage = "";

		try {
			IProject project = null;
			if (scribbleProjectName == null 
					|| scribbleProjectName.length() == 0) {
				showDialog = true;
				dialogMessage = "No scribble pad project specified.";
			} else {
				project = root.getProject(scribbleProjectName);
				if (project != null && !project.exists()) {
					showDialog = true;
					dialogMessage = "The scribble project could not be found.";
				} else {
					if (!project.isOpen()) {
						project.open(null);
					}
					IFile scribbleFile = project.getFile(scribbleFileName);
					if (!scribbleFile.exists()) {
						showDialog = true;
						dialogMessage = "The scribble pad file could not be found.";

					} else {
						// FileEditorInput input = new
						// FileEditorInput(scribbleFile);
						IWorkbenchPage page = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage();

						String currentFile = ((IResource) ((FileEditorInput) editor
								.getEditorInput()).getFile()).getName();

						if (currentFile
								.equalsIgnoreCase(scribbleFile.getName())) {
							editor.doSave(new NullProgressMonitor());
							BrowserView browser = (BrowserView) page
									.showView(BrowserView.ID_BROWSER);
							browser.setUrl(scribbleURL);
							browser.setFocus();
						} else {
							// IEditorPart editorPart =
							// IDE.openEditor(page,scribbleFile,true);
							IDE.openEditor(page, scribbleFile, true);

							if (clearOnLoad) {
								editor.getDocumentProvider().getDocument(
										editor.getEditorInput()).set("");
							}
							if (loadBrowser) {

								BrowserView browser = (BrowserView) page
										.showView(BrowserView.ID_BROWSER);
								browser.setUrl(scribbleURL);
								// browser.refresh();
							}
						}
						editor.setFocus();
					}
				}
			}
		} catch (Exception e) {
			MessageBox msg = new MessageBox(editor.getEditorSite().getShell());
			msg.setText("Error!");
			msg.setMessage(e.getLocalizedMessage());
			msg.open();
			e.printStackTrace();
		}

		if (showDialog) {
			ScribbleSettings settings = new ScribbleSettings();
			ScribbleDialog dialog = new ScribbleDialog(editor.getEditorSite()
					.getShell(), dialogMessage,settings);
			dialog.url = scribbleURL;
			dialog.path = scribbleFileName;
			dialog.project = scribbleProjectName;
			if (dialog.open() == Dialog.OK) {
				store.setValue(ScribblePadPreferenceConstants.P_SCRIBBLE_PAD_FILE,settings.path);
				store.setValue(ScribblePadPreferenceConstants.P_SCRIBBLE_URL,settings.url);
				store.setValue(ScribblePadPreferenceConstants.P_SCRIBBLE_PROJECT_NAME,settings.project);
				run(action);
			}
		}
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {

		if (targetEditor instanceof ITextEditor
				|| targetEditor instanceof CFMLEditor) {
			editor = (ITextEditor) targetEditor;
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		;
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbenchWindow window) {
		IEditorPart activeEditor = window.getActivePage().getActiveEditor();
		if (activeEditor instanceof ITextEditor) {
			this.editor = (ITextEditor) activeEditor;
			
		}
		
	}
}
