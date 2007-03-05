package org.cfeclipse.cfml;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

public class EditorPartListener implements IPartListener {

	public EditorPartListener() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void partActivated(IWorkbenchPart part) {
		if(part instanceof CFMLEditor){
			
			CFMLEditor openEditor =(CFMLEditor)part;
			openEditor.setStatusLine();
		}
	}

	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub
			//System.out.println("Part brought to top" + part.getClass());
		if(part instanceof CFMLEditor){
			
			CFMLEditor openEditor =(CFMLEditor)part;
			openEditor.setStatusLine();
		}
		
	}

	public void partClosed(IWorkbenchPart part) {

	}

	public void partDeactivated(IWorkbenchPart part) {

	}

	public void partOpened(IWorkbenchPart part) {
		if(part instanceof CFMLEditor){
			
			CFMLEditor openEditor =(CFMLEditor)part;
			openEditor.setStatusLine();
		}
		
		
	}

}
