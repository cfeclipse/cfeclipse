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
package org.cfeclipse.cfml.editors.actions;

import java.util.Map;
import java.util.Properties;

import org.cfeclipse.cfml.dialogs.TagEditDialog;
import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.dictionary.SyntaxDictionary;
import org.cfeclipse.cfml.dictionary.Tag;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.partitioner.CFEPartitioner;
import org.cfeclipse.cfml.views.dictionary.TagFormatter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.texteditor.ITextEditor;


/**
 * This Class is used to call the Edit Tag Dialog and write out the contents to the editor
 * @author mdrew
 *
 */
public class EditTagAction implements IEditorActionDelegate{
		protected Tag tag;
		protected Shell shell;
		protected IEditorPart ieditor;
		private ITextEditor editor = null;
		private CFEPartitioner partitioner;
		private int tagstart;
		private int taglength;
		private Map selectedattributes;
		private boolean replace = false;
		private SyntaxDictionary dictionary;
		
		/*
		 * constructors
		 */
		public EditTagAction(){
			// Re-Write this to try and set everything up, called a self init function maybe
			
			super();
		}

		/**
		 * This  Tag Action needs a tag and a shell. It will setup a a *blank* dialog
		 * @param tag
		 * @param shell
		 */
		public EditTagAction(Tag tag, Shell shell) {
			this.tag = tag;
			this.shell = shell;
			this.ieditor = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			this.dictionary =  DictionaryManager.getDictionary("CF_DICTIONARY");
		}
		
		/** This Tag ACtion needs a tag, a shell and the attibutes of a tag. It will setup a pre-filled dialog
		 * 
		 */
		public EditTagAction(Tag tag, Shell shell, Map attributes){
			this.tag = tag;
			this.shell = shell;
			this.ieditor = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			this.selectedattributes = attributes;
			//Since we are passing values, has to be pre-filled
			this.replace = true;
		}
		
		public EditTagAction(String tag, Shell shell){
			this.shell = shell;
			this.dictionary = DictionaryManager.getDictionary("CF_DICTIONARY");
			this.ieditor = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			this.tag = this.dictionary.getTag(tag);
			
		}
		
		
		public void setActiveEditor(IAction action, IEditorPart targetEditor) {

			this.editor = (ITextEditor)targetEditor;
			IDocument doc = editor.getDocumentProvider().getDocument(
					editor.getEditorInput());

			ICFDocument cfd = (ICFDocument) doc;

			this.partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
		}
		
		
		
		public void setTagPosition(int start, int len){
			this.tagstart = start;
			this.taglength = len;
		}
				
	
	

	public void run(){
			//We should be able to pass the attributes if we are editing.
			
			ITextEditor thisEdit = (ITextEditor)ieditor;
			IDocument doc =  thisEdit.getDocumentProvider().getDocument(ieditor.getEditorInput());
			ISelection sel = thisEdit.getSelectionProvider().getSelection();
			TagEditDialog tagview = new TagEditDialog(this.shell, this.tag);
			tagview.setSelectedattributes(this.selectedattributes);
			
						
			//Do the closing action
			if(tagview.open() == IDialogConstants.OK_ID){
				Properties fieldStore = tagview.getFieldStore();
				TagFormatter tf = new TagFormatter(this.tag, fieldStore);
				
				//Here is where we actually do the insertion
				
					if(ieditor instanceof ITextEditor){
							
							if(replace){
								System.out.println("replacing...." + tf.getTagStart());
								int selstart = ((ITextSelection) sel).getOffset();
								int selectionLength = ((ITextSelection) sel).getLength();
								
								try {
									doc.replace(selstart, selectionLength, tf.getTagStart());
								} catch (BadLocationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								ieditor.setFocus();
								
							} else {
							//we surround the selection
							Encloser encloser = new Encloser();
							
							int selectionLength = ((ITextSelection) sel).getLength();
							if (selectionLength > 0) {
								tf.setWrapping(true);
							}
							
							encloser.enclose(doc, (ITextSelection) sel, tf.getTagStart(), tf.getTagEnd());
							
							//Now set the focus back to the editor
							ieditor.setFocus();
							}
					}

				
			}

	}

	public ITextEditor getEditor() {
		return editor;
	}

	public void setEditor(ITextEditor editor) {
		this.editor = editor;
	}

	public IEditorPart getIeditor() {
		return ieditor;
	}

	public void setIeditor(IEditorPart ieditor) {
		this.ieditor = ieditor;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public void run(IAction action) {
		// TODO Auto-generated method stub
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}
	

}
