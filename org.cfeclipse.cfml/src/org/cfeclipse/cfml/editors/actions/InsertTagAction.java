package org.cfeclipse.cfml.editors.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.cfeclipse.cfml.dialogs.TagEditDialog;
import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.dictionary.Tag;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.views.dictionary.TagFormatter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.texteditor.ITextEditor;

public class InsertTagAction {
	private Tag tag;
	private Shell shell;
	private String tagname;
	
	
	public InsertTagAction(Tag tag, Shell shell) {
		this.tag = tag;
		this.shell = shell;
	}

	//If we dont know the ACTUAL tag, we can pass in the name
	public InsertTagAction(String tagname, Shell shell) {
		this.tagname = tagname;
		this.shell = shell;
	}
	
	public void run() {
		
		//This function opens up the tag edit dialog to insert, no need to do much other stuff really
		IEditorPart activeEditor = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		ITextEditor thisEdit = (ITextEditor)activeEditor;
		IDocument doc =  thisEdit.getDocumentProvider().getDocument(thisEdit.getEditorInput());
		ISelection sel = thisEdit.getSelectionProvider().getSelection();
		final ITextSelection textSelection = (ITextSelection) thisEdit.getSelectionProvider().getSelection();
		
		Tag tagToOpen = null;
		if(this.tag !=null){
			tagToOpen = this.tag;
		}
		else if(this.tagname !=null ) {
			//Get the tag
			tagToOpen = DictionaryManager.getDictionary("CF_DICTIONARY").getTag(this.tagname);
			
		}
		
		
		TagEditDialog tagview = new TagEditDialog(shell, tagToOpen);
		
			if(tagview.open() == IDialogConstants.OK_ID){
				Properties fieldStore = tagview.getFieldStore();  	//The new items
			
				//Loop through the new ones
			
				
				/*
				 * Pass in the attributes into a Tag Formatter
				 */
				TagFormatter tf = new TagFormatter(tagToOpen, fieldStore);
				
				//Here is where we actually do the insertion
				
					if(thisEdit instanceof ITextEditor){
						Encloser enc = new Encloser();
						enc.enclose((ICFDocument) doc, textSelection, tf.getTagStart(), tf.getTagEnd());
						thisEdit.setFocus();
					}
			}
		
		
	}

}
