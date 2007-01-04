package org.cfeclipse.cfml.cfunit.editor.actions;

import org.cfeclipse.cfml.cfunit.CFUnitTestCase;
import org.cfeclipse.cfml.cfunit.views.CFUnitView;
import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

public class RefreshTestAction implements IEditorActionDelegate {
	protected ITextEditor editor = null;
	
	public RefreshTestAction() {
		super();
	}
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		/*
		if(targetEditor instanceof CFMLEditor) {
			CFMLEditor cfe = (CFMLEditor)targetEditor;
			
			ICFDocument icfd = null;
			CFDocument cfd = null;
			
			if (cfe.getDocumentProvider().getDocument(cfe.getEditorInput()) instanceof ICFDocument) {
				icfd = (ICFDocument)cfe.getDocumentProvider().getDocument(cfe.getEditorInput());
				cfd = icfd.getCFDocument();
			}
			
			org.cfeclipse.cfml.parser.docitems.DocItem docRoot = cfd.getDocumentRoot();
			CFNodeList nodes = docRoot.selectNodes("//cfcomponent");
						
			if(nodes.size() > 0) {
				CfmlTagItem tag = (CfmlTagItem)nodes.get(0);
				String sc = tag.getAttributeValue("extends");
				
				if(sc != null) {
					if(sc.contains(".TestCase")) {
						// TODO: Load this CFC as a test to be executed
					}
				}
			}
		}
		*/
	}

	public void run(IAction action) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		try {
			page.showView(CFUnitView.ID_CFUNIT);
		} catch (Exception e) {
	        e.printStackTrace();
	    }
		
		
		CFUnitTestCase cfunit = CFUnitTestCase.getInstence();
		cfunit.run();
	}

	public void selectionChanged(IAction action, ISelection selection) {}

}
