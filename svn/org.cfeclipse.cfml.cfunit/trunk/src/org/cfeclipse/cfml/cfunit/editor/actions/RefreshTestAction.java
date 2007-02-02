package org.cfeclipse.cfml.cfunit.editor.actions;

import org.cfeclipse.cfml.CFMLPlugin;
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
			
		boolean bAutoload = CFMLPlugin.getDefault().getPreferenceStore().getBoolean("CFUnitAutoload");
		
		if(bAutoload) {
			if(targetEditor instanceof CFMLEditor) {
				CFMLEditor cfe = (CFMLEditor)targetEditor;
				
				String tc = retrieveTestCase( cfe );
				
				if(tc != null) {
					CFUnitTestCase.getInstence().setTest( tc );
				}
			}
		}
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
	

	/** 
	 * Returns the Test Case from the current edtiroe, or null if one was not 
	 * found. There are a few places it may get the test case from:
	 *     1) If the current document is a Test Case.
	 *     2) The the current CFC has a "tester" attribute.
	 *     3) If there is a @tester argument inside a comment in the document.
	 * 
	 * @param e The CFML editor to get the test case from
	 * @return The test case currently in the editor.
	 */
	private String retrieveTestCase(CFMLEditor e) {
		ICFDocument icfd = null;
		CFDocument cfd = null;
		
		if (e.getDocumentProvider().getDocument(e.getEditorInput()) instanceof ICFDocument) {
			icfd = (ICFDocument)e.getDocumentProvider().getDocument(e.getEditorInput());
			cfd = icfd.getCFDocument();
		}
		
		org.cfeclipse.cfml.parser.docitems.DocItem docRoot = cfd.getDocumentRoot();
		CFNodeList nodes = docRoot.selectNodes("//cfcomponent");		
		
		// Check if this document is a CFC (contains a <cfcomponent> tag)
		if(nodes.size() > 0) {
			CfmlTagItem tag = (CfmlTagItem)nodes.get(0);
			
			// If the CFC has a tester argument, return that as the test case
			String t = tag.getAttributeValue("tester");
			if(t != null) {
				return t;
			}
			
			// If the CFC extends a TestCase, return the current document's name
			String sc = tag.getAttributeValue("extends");
			if(sc != null) {
				if(sc.matches(".*\\.TestCase.*")) {
					return CFUnitTestCase.getResourceFullName( icfd.getResource() );
				}
			}
		}
		
		return null;
	}

}
