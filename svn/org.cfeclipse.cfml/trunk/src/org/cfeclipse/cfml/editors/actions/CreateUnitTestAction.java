package org.cfeclipse.cfml.editors.actions;

import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.CFParser;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.cfeclipse.cfml.parser.docitems.TagItem;
import org.cfeclipse.cfml.views.packageview.objects.FunctionNode;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class CreateUnitTestAction implements IWorkbenchWindowActionDelegate,IEditorActionDelegate {

	static ITextEditor editor = null;
	static ICFDocument cfdocument = null;
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		// TODO Auto-generated method stub
		if(targetEditor instanceof ITextEditor || targetEditor instanceof CFMLEditor)
			{
			editor = (ITextEditor)targetEditor;
			 
			 IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
			 cfdocument = (ICFDocument) doc;
				
			}
	}

	public void run(IAction action) {
		// TODO Auto-generated method stub
		if(editor != null){
			DocumentBuilder builder = null;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setCoalescing(true);
			
			try {
			 builder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			String cfcXML = "";
		
			//We can create a stringed XML instead of adding nodes etc
			
			
			
			
				//need to go and get all the cffunctions
			CFParser parser = new CFParser();
	        CFDocument doc = parser.parseDoc(cfdocument.get());

	        // Now we just want to add the nodes!
	        DocItem docroot = doc.getDocumentRoot();
	        
	        CFNodeList compNodes = docroot.selectNodes("/cfcomponent");
	        
	        CFNodeList nodes = docroot.selectNodes("//cffunction");
			
	       Iterator funcIter =  nodes.iterator();
	       while(funcIter.hasNext()){
	            TagItem thisFunction = (TagItem)funcIter.next();

	            System.out.println("function " + thisFunction.getAttributeValue("name"));
	       }
	        
			
		System.out.println("running the action" + compNodes.get(1));
		
		}
	}

	public void selectionChanged(IAction action, ISelection selection){
		if(editor != null){
			setActiveEditor(null,  editor.getSite().getPage().getActiveEditor());
		}
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
