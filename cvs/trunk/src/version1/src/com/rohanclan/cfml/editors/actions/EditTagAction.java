/*
 * Created on Nov 9, 2005
 *
 * The MIT License
 * Copyright (c) 2005 Mark Drew
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
package com.rohanclan.cfml.editors.actions;

import java.util.Properties;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.rohanclan.cfml.dictionary.Tag;
import com.rohanclan.cfml.views.dictionary.TagEditDialog;
import com.rohanclan.cfml.views.dictionary.TagFormatter;
import com.rohanclan.cfml.views.dictionary.TagItem;

/**
 * This Class is used to call the Edit Tag Dialog and write out the contents to the editor
 * @author mdrew
 *
 */
public class EditTagAction {
		protected Tag tag;
		protected Shell shell;
		
	public EditTagAction(Tag tag, Shell shell) {
		this.tag = tag;
		this.shell = shell;
	}
//
	public void run(){
			System.out.println("---Starting the EditTagAction ---");
			//We should be able to pass the attributes if we are editing. think about it later.
			TagEditDialog tagview = new TagEditDialog(this.shell, this.tag);
				
			
			//Do the closing action
			if(tagview.open() == IDialogConstants.OK_ID){
				System.out.println("--- Clicked OK and closed ---");
				Properties fieldStore = tagview.getFieldStore();
				TagFormatter tf = new TagFormatter(this.tag, fieldStore);
				System.out.println("Start of the tag should be " + tf.getTagStart());
				System.out.println("End of the tag should be " + tf.getTagEnd());
				
				
			}
			
			/*
			
			try {	
			
			// Open the dialog and check if the OK button was pressed
			if (tagview.open() == IDialogConstants.OK_ID) {
				//Surely here we get the fieldstore
				Properties fieldStore = tagview.getFieldStore();
				TagFormatter tf = new TagFormatter(tg.getTag());
					tf.setAttribs(fieldStore);
				System.out.println("Start of the tag should be " + tf.getTagStart());
				System.out.println("End of the tag should be " + tf.getTagEnd());
					
				//Now we should be able to do some formatting as it is
				
				// OK button was pressed. Check the values and do whatever
				// we need to with them.

				// Get Info about the editor
				//IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
						
				IEditorPart iep = this.shell.getShell().getWorkbenchWindow()
						.getActivePage().getActiveEditor();
				IDocument doc = ((ITextEditor) iep).getDocumentProvider()
						.getDocument(iep.getEditorInput());
				ITextEditor ite = (ITextEditor) iep;
				ISelection sel = ite.getSelectionProvider().getSelection();
				//int cursorOffset = ((ITextSelection) sel).getOffset();
				int selectionLength = ((ITextSelection) sel).getLength();
				Encloser encloser = new Encloser();
				// -> this inserts it
				//encloser.enclose(doc,(ITextSelection)sel,selectedMethod.getInsertString(),"");

				// End Get info about the editor
				
				if (selectionLength > 0) {
					tf.setWrapping(true);
				}
				
				encloser.enclose(doc, (ITextSelection) sel, tf
						.getTagStart(), tf.getTagEnd());

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

}
