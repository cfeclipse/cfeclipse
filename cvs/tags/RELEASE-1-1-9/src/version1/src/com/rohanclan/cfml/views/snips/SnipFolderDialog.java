/*
 * Created on May 6, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.snips;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;
import java.io.File;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * @author Stephen Milligan
 *
 * This dialog box allows the user to create a new snippet folder
 */
public class SnipFolderDialog extends InputDialog {
	
	private File parentFolder;
	private TreeViewer treeView;
	private static String dialogTitle = "New Folder";
	private static String dialogMessage = "New Folder Name: ";
	private static String initialValue = "";
	private SnipWriter writer;
	
	public SnipFolderDialog(Shell parent,  SnipWriter filewriter, TreeViewer treeView) {
		super(parent, dialogTitle, dialogMessage, initialValue, null);
		this.writer = filewriter;
		this.treeView = treeView;
	}
	
	
	protected void okPressed() {

		writer.writeFolder(this.getValue());
		close();
		treeView.refresh();


	}
	
	
}
