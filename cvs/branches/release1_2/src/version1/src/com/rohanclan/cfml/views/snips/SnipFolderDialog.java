/*
 * Created on May 6, 2004
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
