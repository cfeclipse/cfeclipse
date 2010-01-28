package org.cfeclipse.cfeclipsecall.plugin.pub;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.cfeclipse.cfeclipsecall.core.IHandleOpenRequest;
import org.cfeclipse.cfeclipsecall.plugin.CFECallPlugin;
import org.cfeclipse.cfeclipsecall.plugin.IDEWorkbenchMessages;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.UIPlugin;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

public class HandleOpenRequest implements IHandleOpenRequest {

	public HandleOpenRequest() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.cfeclipse.cfeclipsecall.core.IHandleOpenRequest#handleOpenRequest(java
	 * .lang.String, java.lang.String, int, int[], boolean)
	 */
	public void handleOpenRequest(final String origRequest, final String fileName, final int row, final int[] col, final boolean focus) {
		try {
			CFECallPlugin.log(IStatus.INFO, "got request: " + origRequest);
			UIPlugin.getDefault().getWorkbench().getDisplay().asyncExec(new Runnable() {

				public void run() {
					IPath path = Path.fromOSString(fileName).removeLastSegments(1);

					int numberOfFilesNotFound = 0;
					StringBuffer notFound = new StringBuffer();
					// Action

					IFileStore fileStore = EFS.getLocalFileSystem().getStore(path);
					fileStore = fileStore.getChild(fileName.substring(fileName.lastIndexOf(java.io.File.separator) + 1));
					if (!fileStore.fetchInfo().isDirectory() && fileStore.fetchInfo().exists()) {
						IWorkbenchWindow _window = UIPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
						if (_window == null) {
							_window = UIPlugin.getDefault().getWorkbench().getWorkbenchWindows()[0];
						}
						final IWorkbenchWindow window = _window;

						final IWorkbenchPage page = window.getActivePage();
						try {
							IEditorInput input = getEditorInput(fileStore);
							String editorId = getEditorId(fileStore);
							if (editorId == null || editorId.equals(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID)
									|| editorId.equals(IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID)) {
								CFECallPlugin.log(IStatus.WARNING,
										"Can't use external editor, as this would invoke the same call again! Using DefaultTextEditor instead.");
								editorId = "org.eclipse.ui.DefaultTextEditor";
							}
							final IEditorPart editor = page.openEditor(input, editorId);

							if (editor instanceof ITextEditor) {
								ITextEditor textEditor = (ITextEditor) editor;
								if (row != 0 || (col != null && col[0] != 0)) {
									// look up the current range of the marker when the document has been edited
									try
									{
										IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
										// Important: getLineInformation starts with offset 0! So it's necessary to decrement row by 1 when getting the info!
										IRegion lineInfo = document.getLineInformation(row-1);
										int offset = lineInfo.getOffset();
										int lineLength = lineInfo.getLength();

										if (col != null && (col[0] != 0 || col[1] != 0)) {
											if (col[1] >= col[0]) {
												textEditor.selectAndReveal(offset + col[0] - 1, col[1] - col[0] + 1);
											} else {
												if (col[0] < lineLength) {
													textEditor.selectAndReveal(offset + col[0] - 1, lineLength - col[0]);
												} else {
													textEditor.selectAndReveal(offset, lineLength);
												}
											}
										} else {
											textEditor.selectAndReveal(offset, lineLength);
										}
									}
									catch (Throwable T) {
										CFECallPlugin.log(T);
									}
								}
							}
							if (focus) {
								CFECallPlugin.log(IStatus.INFO, "calling focus");

								final Shell shell = window.getShell();
								UIPlugin.getDefault().getWorkbench().getDisplay().asyncExec(new Runnable() {
									public void run() {
										try {
											shell.setActive();
											shell.setMinimized(false);
											shell.setVisible(true);
											shell.setFocus();
											synchronized (this) {
												this.wait(100);
											}
											shell.forceActive();
											shell.setActive();
											shell.forceFocus();

											page.activate(editor);
										} catch (Throwable T) {
											T.printStackTrace();
										}
									}
								});
							}
							else {
								page.activate(editor);
							}
						} catch (PartInitException e) {
							String msg = NLS.bind(IDEWorkbenchMessages.OpenLocalFileAction_message_errorOnOpen, fileStore.getName());
							IDEWorkbenchPlugin.log(msg, e.getStatus());
							MessageDialog.openError(null /* window */, IDEWorkbenchMessages.OpenLocalFileAction_title, msg);
						}
					} else {
						if (++numberOfFilesNotFound > 1)
							notFound.append('\n');
						notFound.append(fileStore.getName());
					}

					if (numberOfFilesNotFound > 0) {
						String msgFmt = numberOfFilesNotFound == 1 ? IDEWorkbenchMessages.OpenLocalFileAction_message_fileNotFound
								: IDEWorkbenchMessages.OpenLocalFileAction_message_filesNotFound;
						String msg = NLS.bind(msgFmt, notFound.toString());
						MessageDialog.openError(null /* window.getShell() */, IDEWorkbenchMessages.OpenLocalFileAction_title, msg + "\nparams = '" + origRequest
								+ "'");
					}
				}
			});

		} catch (final Throwable T) {
			CFECallPlugin.log(T);

			UIPlugin.getDefault().getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					MessageDialog.openError(null /* window.getShell() */, IDEWorkbenchMessages.OpenLocalFileAction_title, "got exception: " + T.getMessage()
							+ "\n" + "params = '" + origRequest + "'");
				}
			});
		}
	}

	//////////////////////////////////////////////////////////////////////////////
	////// copied code from IDE.openEditorOnFileStore(page, fileStore) ///////////
	//////////////////////////////////////////////////////////////////////////////
	static IFile[] filterNonExistentFiles(IFile[] files) {
		if (files == null)
			return null;

		int length = files.length;
		ArrayList existentFiles = new ArrayList(length);
		for (int i = 0; i < length; i++) {
			if (files[i].exists())
				existentFiles.add(files[i]);
		}
		return (IFile[]) existentFiles.toArray(new IFile[existentFiles.size()]);
	}

	static IFile getWorkspaceFile(IFileStore fileStore) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile[] files = root.findFilesForLocationURI(fileStore.toURI());
		files = filterNonExistentFiles(files);
		if (files == null || files.length == 0)
			return null;

		// for now only return the first file
		return files[0];
	}

	static IEditorInput getEditorInput(IFileStore fileStore) {
		IFile workspaceFile = getWorkspaceFile(fileStore);
		if (workspaceFile != null)
			return new FileEditorInput(workspaceFile);
		Class c = null;
		try {
			// Eclipse 3.3 or later
			c = Class.forName("org.eclipse.ui.ide.FileStoreEditorInput");
			// nothing to do
		} catch (ClassNotFoundException e) {
			try {
				// Eclipse 3.2
				c = Class.forName("org.eclipse.ui.internal.editors.text.JavaFileEditorInput");
			} catch (ClassNotFoundException e2) {
				// nothing to do
			}
		}
		if (c != null) {
			try {
				return (IEditorInput)c.getConstructor(new Class[] { IFileStore.class }).newInstance(new Object[]{fileStore});
			} catch (Throwable T) {
				// nothing to do
			}
		}
		return null;
	}

	static String getEditorId(IFileStore fileStore) throws PartInitException {
		String name = fileStore.fetchInfo().getName();
		if (name == null) {
			throw new IllegalArgumentException();
		}

		IContentType contentType = null;
		try {
			InputStream is = null;
			try {
				is = fileStore.openInputStream(EFS.NONE, null);
				contentType = Platform.getContentTypeManager().findContentTypeFor(is, name);
			} finally {
				if (is != null) {
					is.close();
				}
			}
		} catch (CoreException ex) {
			// continue without content type
		} catch (IOException ex) {
			// continue without content type
		}

		IEditorRegistry editorReg = PlatformUI.getWorkbench().getEditorRegistry();

		return getEditorDescriptor(name, editorReg, editorReg.getDefaultEditor(name, contentType)).getId();
	}

	static IEditorDescriptor getEditorDescriptor(String name, IEditorRegistry editorReg, IEditorDescriptor defaultDescriptor) throws PartInitException {

		if (defaultDescriptor != null) {
			return defaultDescriptor;
		}

		IEditorDescriptor editorDesc = defaultDescriptor;

		// next check the OS for in-place editor (OLE on Win32)
		if (editorReg.isSystemInPlaceEditorAvailable(name)) {
			editorDesc = editorReg.findEditor(IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID);
		}

		// next check with the OS for an external editor
		if (editorDesc == null && editorReg.isSystemExternalEditorAvailable(name)) {
			editorDesc = editorReg.findEditor(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
		}

		// next lookup the default text editor
		if (editorDesc == null) {
			editorDesc = editorReg.findEditor("org.eclipse.ui.DefaultTextEditor");
		}

		// if no valid editor found, bail out
		if (editorDesc == null) {
			throw new PartInitException(IDEWorkbenchMessages.IDE_noFileEditorFound);
		}

		return editorDesc;
	}

}
