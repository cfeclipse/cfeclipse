package org.cfeclipse.cfml.editors.actions;

import java.util.Iterator;

import org.cfeclipse.cfml.dialogs.GetterAndSetterDialog;
import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.partitioner.CFEPartition;
import org.cfeclipse.cfml.editors.partitioner.CFEPartitioner;
import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.CFParser;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.cfeclipse.cfml.parser.docitems.TagItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

public class InsertGetAndSetAction implements IWorkbenchWindowActionDelegate,IEditorActionDelegate{
	protected ICFDocument cfdocument = null;
	protected ITextSelection docselection = null;
	protected ITextEditor editor = null;
	
	
	public InsertGetAndSetAction(){
		super();
	}
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if( targetEditor instanceof ITextEditor || targetEditor instanceof CFMLEditor ){
			
			editor = (ITextEditor)targetEditor;
			IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
			cfdocument = (ICFDocument) doc;
			docselection = (ITextSelection) editor.getSelectionProvider().getSelection();
		}
		
	}

	public void run(IAction action) {
		
		
		//We open a dialog, with a tree
		GetterAndSetterDialog dialog = new GetterAndSetterDialog(editor.getSite().getShell());
		dialog.open();
		
		int startpos = docselection.getOffset();
		int len = Math.max(docselection.getLength(),1);
		
		//check what type of document this is
		
		
		DocItem docroot	= cfdocument.getCFDocument().getDocumentRoot();
		
		CFNodeList nodes = docroot.selectNodes("//cfproperty");
		 
		Iterator nodeIter = nodes.iterator();
		
		while(nodeIter.hasNext()){
			TagItem property = (TagItem)nodeIter.next();
			StringBuffer sb = new StringBuffer();
			
			String propName = property.getAttributeValue("name"); 
			String propType = property.getAttributeValue("type");
			
			sb.append("<cffunction name=\"" + createFunctionName("get", propName) + "\" returntype=\"" + propType + "\">\n");
			sb.append("\t<cfreturn "  + propName + " />\n");
			sb.append("</cffunction>");
			sb.append("\n\n");
			
			sb.append("<cffunction name=\"" + createFunctionName("set", propName) + "\" returntype=\"void\">\n");
			sb.append("\t<cfargument name=\"" + createFunctionName("", propName) + "\" type=\"" + propType + "\">\n");
			sb.append("\t\t<cfset " + propName + " = arguments."+ createFunctionName("", propName) + " />\n");
			sb.append("</cffunction>");
			
			
			
			
			
			System.out.println(sb.toString());
			/*
			 * <cffunction name="get<bla>" returntype="void">
			 * 		<cfargument name="" type="">
			 * </cffunction>
			 * 
			 */
			
		}
	}

	private String createFunctionName(String type, String propertyName){
		//Need to check the propertyName
		String dspProperty = propertyName;
		if(propertyName.split("\\.").length > 1){
			dspProperty = propertyName.split("\\.")[1];
		}
		
		String firstChar = (dspProperty.charAt(0) + "").toUpperCase();
		String retGetName = type + firstChar + dspProperty.subSequence(1, dspProperty.length()); 
		return retGetName;
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	public void init(IWorkbenchWindow window) {
		IEditorPart activeEditor = window.getActivePage().getActiveEditor();
		if(activeEditor instanceof ITextEditor){
			editor = (ITextEditor)activeEditor;
		}
		
	}

}
