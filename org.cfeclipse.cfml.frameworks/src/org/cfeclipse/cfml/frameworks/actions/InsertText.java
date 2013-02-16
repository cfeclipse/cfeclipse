package org.cfeclipse.cfml.frameworks.actions;

import org.cfeclipse.cfml.frameworks.views.TreeNode;
import org.cfeclipse.cfml.views.snips.SnipSmartDialog;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.jdom.Element;

public class InsertText extends BaseAction implements IBaseAction{

	
	
	private Shell shell;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.shell = targetPart.getSite().getShell();
	}

	public void run() {
		//Lets parse the bugger
	//	SnippetParser parser = new SnippetParser(getInsertSnippet(),getNode().getElement());
 		String parsedSnippet = super.getParsedAction();
		
		parsedSnippet  = SnipSmartDialog.parse(parsedSnippet, shell);
		
		//Now insert it!
		IWorkbench wb = PlatformUI.getWorkbench();
		   IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		   IWorkbenchPage page = win.getActivePage();
		   

		   IEditorPart part = page.getActiveEditor();
		   if (!(part instanceof AbstractTextEditor)){
			   return;
		   }
		      
		   ITextEditor editor = (ITextEditor)part;
		   IDocumentProvider dp = editor.getDocumentProvider();
		   IDocument doc = dp.getDocument(editor.getEditorInput());
		  ISelectionProvider selectionProvider = editor.getSelectionProvider();
		  
		  ISelection selection = selectionProvider.getSelection();
		  int currentOffset = 0;
		  int currentLength = 0;
		  if (selection instanceof ITextSelection) {
				currentOffset = ((ITextSelection) selection).getOffset();
				currentLength = ((ITextSelection) selection).getLength();
		  }
		   //Need to get the current cursor position
		   
		   int offset;
		try {
			doc.replace(currentOffset, currentLength, parsedSnippet);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		
	//	showMessage("Call from Insert Node Action" + parsedSnippet);
	}
	
	public void setElement(Element actionElement) {
		// TODO Auto-generated method stub
		super.setElement(actionElement);
	}

	public void setNode(TreeNode treeNode) {
		// TODO Auto-generated method stub
		super.setNode(treeNode);
	}
	
	public void showMessage(String message) {
		MessageDialog.openInformation(
			null,
			"Framewokrs",
			message);
	}
}
