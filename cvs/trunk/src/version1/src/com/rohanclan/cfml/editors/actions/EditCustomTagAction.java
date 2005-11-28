package com.rohanclan.cfml.editors.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.rohanclan.cfml.dialogs.EditCustomTagDialog;
import com.rohanclan.cfml.dictionary.Tag;
import com.rohanclan.cfml.editors.ICFDocument;
import com.rohanclan.cfml.editors.partitioner.CFEPartitioner;

public class EditCustomTagAction  implements IEditorActionDelegate{
	protected Tag tag = null;
	protected Shell shell;
	protected IEditorPart ieditor;
	private ITextEditor editor = null;
	private CFEPartitioner partitioner;
	
	/**
	 * Constructor
	 *
	 */
	public EditCustomTagAction() {
		super();
	}
	
	/**
	 * New tag
	 * @param shell
	 */
	public EditCustomTagAction(Shell shell){
		
		this.shell = shell;
	}
	/**
	 * Edit existing
	 * @param tag
	 * @param shell
	 */
	public EditCustomTagAction(Tag tag, Shell shell){
		this.tag = tag;
		this.shell = shell;
	}
	
	
	public void run(){
		EditCustomTagDialog ectd;
		
		if(this.tag != null){
			ectd = new EditCustomTagDialog(this.shell, this.tag);
			ectd.open();
		} else {
			ectd = new EditCustomTagDialog(this.shell);
			ectd.open();
		}
		
		if(ectd.open() == IDialogConstants.OK_ID){
			System.out.println("Clicked ok");
			
		}
		
		
	}
	
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.editor = (ITextEditor)targetEditor;
		IDocument doc = editor.getDocumentProvider().getDocument(
				editor.getEditorInput());

		ICFDocument cfd = (ICFDocument) doc;

		this.partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}

	public void run(IAction action) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
