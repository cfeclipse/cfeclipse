/*
 * Created on Nov 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.views.explorer;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FileDoubleClickListener implements IDoubleClickListener {

    FileContentProvider contentProvider;
    /**
     * 
     */
    public FileDoubleClickListener(FileContentProvider contentProvider) {
       this.contentProvider = contentProvider;
    }

    
    public void doubleClick(DoubleClickEvent e) {
        IEditorInput input = contentProvider.getEditorInput(e.getSelection().toString());
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        try {
            
            page.openEditor(input,"org.cfeclipse.cfml.editors.CFMLEditor");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
