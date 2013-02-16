/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Elias Volanakis - adapted from Workbench.java 
 *******************************************************************************/
package org.cfeclipse.cfml.editors.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.internal.IWorkbenchConstants;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.WorkbenchWindow;

public final class SaveStateAction implements IWorkbenchWindowActionDelegate {

	static final String VERSION_STRING[] = { "0.046", "2.0" // from Workbench
															// class in Eclipse
															// 3.3
	};

	public void run(final IAction action) {
		Runnable op = new Runnable() {
			public void run() {
				XMLMemento memento = XMLMemento.createWriteRoot(IWorkbenchConstants.TAG_WORKBENCH);
				saveState(memento);
				saveMementoToFile(memento);

			}
		};
		PlatformUI.getWorkbench().getDisplay().asyncExec(op);
	}

	public void selectionChanged(final IAction action, final ISelection selection) {
		// unused
	}

	public void dispose() {
		// unused
	}

	public void init(final IWorkbenchWindow window) {
		// unused
	}

	// adapted from org.eclipse.ui.internal.Workbench
	// ///////////////////////////////////////////////

	private IStatus saveState(final IMemento memento) {
		MultiStatus result = new MultiStatus(PlatformUI.PLUGIN_ID, IStatus.OK,
				WorkbenchMessages.Workbench_problemsSaving, null);
		// Save the version number.
		memento.putString(IWorkbenchConstants.TAG_VERSION, VERSION_STRING[1]);
		// Save how many plug-ins were loaded while restoring the workbench
		memento.putInteger(IWorkbenchConstants.TAG_PROGRESS_COUNT, 10); // we
																		// guesstimate
																		// this
		// Save the advisor state.
		result.add(Status.OK_STATUS);
		// Save the workbench windows.
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
		for (int nX = 0; nX < windows.length; nX++) {
			WorkbenchWindow window = (WorkbenchWindow) windows[nX];
			IMemento childMem = memento.createChild(IWorkbenchConstants.TAG_WINDOW);
			//result.merge(window.saveState(childMem));  // not sure when saveState was removed.  TODO: still needed?
		}
		result.add(((Workbench) workbench).getEditorHistory().saveState(
				memento.createChild(IWorkbenchConstants.TAG_MRU_LIST)));
		return result;
	}

	private void saveMementoToFile(XMLMemento memento) {
		File stateFile = getWorkbenchStateFile();
		if (stateFile != null) {
			try {
				FileOutputStream stream = new FileOutputStream(stateFile);
				OutputStreamWriter writer = new OutputStreamWriter(stream, "utf-8"); //$NON-NLS-1$
				memento.save(writer);
				writer.close();
			} catch (IOException ioe) {
				stateFile.delete();
				CFMLPlugin.log(ioe.getMessage());
			}
		}
	}

	private File getWorkbenchStateFile() {
		IPath path = WorkbenchPlugin.getDefault().getDataLocation();
		if (path == null) {
			return null;
		}
		path = path.append("workbench.xml");
		return path.toFile();
	}
}
