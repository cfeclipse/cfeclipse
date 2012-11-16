/**
 * 
 */
package org.cfeclipse.cfml.frameworks.actions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.views.snips.SnipSmartDialog;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * @author markdrew
 *
 */
public class AddChild extends BaseAction implements IBaseAction{

	Log logger = LogFactory.getLog(AddChild.class);
	private Shell shell;
	
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.shell = targetPart.getSite().getShell();
	}
	
	
	public void run() {

		Element element = super.getNode().getElement();
		logger.debug("Running the Add Child" + super.getNode().getElement());
		
		
		String parsedAction = super.getParsedAction();
		String returnedString  = SnipSmartDialog.parse(parsedAction, shell);
		
		
		
		SAXBuilder builder = new SAXBuilder();
		
		StringReader sr = new StringReader(returnedString);
		
		Element newDocumentsElement = null;
		
		try {
			Document document = builder.build(sr);
			newDocumentsElement = document.getRootElement();
			element.addContent((Content)newDocumentsElement.clone());
			Format prettyFormat = Format.getPrettyFormat();
			XMLOutputter outputer= new XMLOutputter(prettyFormat);
			String newDocumentContent = outputer.outputString(element.getDocument());
			
			ByteArrayInputStream str = new ByteArrayInputStream(newDocumentContent.getBytes());

			//Now we have done that, we need to save the document, hopefully triggering a refresh by Eclipe
			
			super.getNode().getFrameworkFile().getFile().setContents(str,true,true,null);
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			super.showError(e.getMessage() + "\n The node was trying to add:\n" + parsedAction);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			super.showError(e.getMessage());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(newDocumentsElement !=null){
			
		}
		
		
		logger.debug(element);
		
		
	}
	
	

}
