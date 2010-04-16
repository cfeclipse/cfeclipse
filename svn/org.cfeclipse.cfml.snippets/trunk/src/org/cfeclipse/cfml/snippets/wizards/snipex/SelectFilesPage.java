/**
 * 
 */
package org.cfeclipse.cfml.snippets.wizards.snipex;

import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.internal.ide.misc.CheckboxTreeAndListGroup;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * @author markdrew
 *
 */
public class SelectFilesPage extends WizardPage {

	
	   private IAdaptable root;

	    // the visual selection widget group
	    private CheckboxTreeAndListGroup selectionGroup;

		private SnipExExportBean exportBean;

	    // constants
	    private final static int SIZING_SELECTION_WIDGET_WIDTH = 400;

	    private final static int SIZING_SELECTION_WIDGET_HEIGHT = 300;
	    
	
	/**
	 * @param pageName
	 * @param exportBean 
	 */
	public SelectFilesPage(String pageName, SnipExExportBean exportBean) {
		super(pageName);
		setTitle(pageName);
		setDescription("Select the files in the project you want to export");
		this.exportBean = exportBean;
		
		if(this.exportBean.getProject() !=null){
			root = this.exportBean.getProject();
		}
		//Get the selected project
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pageName
	 * @param title
	 * @param titleImage
	 */
	public SelectFilesPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.marginHeight = 2;
		layout.marginWidth = 2;
		
		  //create the input element, which has the root resource
        //as its only child
        ArrayList input = new ArrayList();
       input.add(root);

      //  createMessageArea(composite);
        selectionGroup = new CheckboxTreeAndListGroup(container, input,
                getResourceProvider(IResource.FOLDER | IResource.PROJECT
                        | IResource.ROOT), WorkbenchLabelProvider
                        .getDecoratingWorkbenchLabelProvider(),
                getResourceProvider(IResource.FILE), WorkbenchLabelProvider
                        .getDecoratingWorkbenchLabelProvider(), SWT.NONE,
                // since this page has no other significantly-sized
                // widgets we need to hardcode the combined widget's
                // size, otherwise it will open too small
                SIZING_SELECTION_WIDGET_WIDTH, SIZING_SELECTION_WIDGET_HEIGHT);

        container.addControlListener(new ControlListener() {
            public void controlMoved(ControlEvent e) {
            }

            public void controlResized(ControlEvent e) {
                //Also try and reset the size of the columns as appropriate
                TableColumn[] columns = selectionGroup.getListTable()
                        .getColumns();
                for (int i = 0; i < columns.length; i++) {
                    columns[i].pack();
                }
            }
        });
        
        this.exportBean.setFiles(selectionGroup);
        setControl(container);

	}
	
	 private ITreeContentProvider getResourceProvider(final int resourceType) {
	        return new WorkbenchContentProvider() {
	            public Object[] getChildren(Object o) {
	                if (o instanceof IContainer) {
	                    IResource[] members = null;
	                    try {
	                        members = ((IContainer) o).members();
	                    } catch (CoreException e) {
	                        //just return an empty set of children
	                        return new Object[0];
	                    }

	                    //filter out the desired resource types
	                    ArrayList results = new ArrayList();
	                    for (int i = 0; i < members.length; i++) {
	                        //And the test bits with the resource types to see if they are what we want
	                        if ((members[i].getType() & resourceType) > 0) {
	                            results.add(members[i]);
	                        }
	                    }
	                    return results.toArray();
	                }
	                //input element case
	                if (o instanceof ArrayList) {
	                    return ((ArrayList) o).toArray();
	                } 
	                return new Object[0];
	            }
	        };
	    }

}
