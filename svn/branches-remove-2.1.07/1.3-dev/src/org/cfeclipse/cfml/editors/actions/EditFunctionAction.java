package org.cfeclipse.cfml.editors.actions;

import java.util.Map;
import java.util.Properties;

import org.cfeclipse.cfml.dictionary.Function;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.partitioner.CFEPartitioner;
import org.cfeclipse.cfml.views.dictionary.FunctionEditDialog;
import org.cfeclipse.cfml.views.dictionary.FunctionFormatter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.texteditor.ITextEditor;


public class EditFunctionAction implements IEditorActionDelegate {

	protected Function func;
	protected Shell shell;
	protected IEditorPart ieditor;
	private ITextEditor editor = null;
	private CFEPartitioner partitioner;
	private int funcstart;
	private int funclength;
	private Map selectedattributes;
	private boolean replace = false;
	
	/*
	 * constructors
	 */
	public EditFunctionAction(){
		super();
	}

	public EditFunctionAction(Function func, Shell shell) {
		this.func = func;
		this.shell = shell;
		this.ieditor = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
	}
	
	public EditFunctionAction(Function func, Shell shell, Map attributes){
		this.func = func;
		this.shell = shell;
		this.ieditor = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		this.selectedattributes = attributes;
		//Since we are passing values, has to be pre-filled
		this.replace = true;
	}
	
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.editor = (ITextEditor)targetEditor;
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		ICFDocument cfd = (ICFDocument)doc;
		
		this.partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
		
		
	}
	
	public void setFunctionPosition(int start, int len){
		this.funcstart = start;
		this.funclength = len;
		
	}
	public void run(){
		ITextEditor thisEdit = (ITextEditor)ieditor;
		IDocument doc = thisEdit.getDocumentProvider().getDocument(ieditor.getEditorInput());
		ISelection sel = thisEdit.getSelectionProvider().getSelection();
		FunctionEditDialog funcview = new FunctionEditDialog(this.shell, this.func);
		funcview.setSelectedattributes(this.selectedattributes);
		
		//do the closing action
		if(funcview.open() == IDialogConstants.OK_ID){
			//We get the set properties
			Properties fieldStore = funcview.getFieldStore();
			FunctionFormatter ff = new FunctionFormatter(this.func, fieldStore);
			
			//now we have a format string, we put it at the start, so a starter encloser
			Encloser encloser = new Encloser();
			encloser.enclose(doc, (ITextSelection)sel,ff.getFunction(), "" );
			
			
		}
		
		
		
	}
	
	
	
	public void run(IAction action) {
		// TODO Auto-generated method stub
		
	}
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}

	public ITextEditor getEditor() {
		return editor;
	}

	public void setEditor(ITextEditor editor) {
		this.editor = editor;
	}

	public Function getFunc() {
		return func;
	}

	public void setFunc(Function func) {
		this.func = func;
	}

	public IEditorPart getIeditor() {
		return ieditor;
	}

	public void setIeditor(IEditorPart ieditor) {
		this.ieditor = ieditor;
	}
	
	
	
	
}
