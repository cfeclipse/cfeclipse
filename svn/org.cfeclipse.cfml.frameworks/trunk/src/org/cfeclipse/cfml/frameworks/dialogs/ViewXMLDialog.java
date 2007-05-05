/**
 * 
 */
package org.cfeclipse.cfml.frameworks.dialogs;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

/**
 * @author markdrew
 *
 */
public class ViewXMLDialog extends Dialog {

	
	private Document dspXML;
	private Text xPathResults;
	private Text xPathExpression;
	
	public ViewXMLDialog(Shell parent, Document document) {
		super(parent);
		this.dspXML = document;
	}



	/**
	 * @param parent
	 */
	public ViewXMLDialog(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * @param parent
	 * @param style
	 */

	protected Control createDialogArea(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		container.setLayout(layout);
		layout.numColumns = 3;
		
		//Create XPATH searcher
			Label xpathLabel = new Label(container, SWT.NONE);
			xpathLabel.setText("Xpath Expression:");
			
			GridData xLabel = new GridData();
			xLabel.widthHint = 200;
			xPathExpression = new Text(container, SWT.BORDER);
			xPathExpression.setLayoutData(xLabel);
		
			
			Button xPathDoBtn = new Button(container, SWT.NONE);
			xPathDoBtn.setText("Test XPath");
			xPathDoBtn.addSelectionListener(new SelectionListener(){

				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					
				}

				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("Clicked on the test thing");
					
					fillResults();
				}

				});
			
			
		
		String xmlString = "";
		
		Format prettyFormat = Format.getPrettyFormat();
		XMLOutputter outputer= new XMLOutputter(prettyFormat);
		xmlString = outputer.outputString(this.dspXML);
		
		
		
		
		GridData gd = new GridData();
		gd.horizontalSpan = 3;
		gd.widthHint = 500;
		gd.heightHint = 400;
		Text xmlText = new Text(container, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		xmlText.setLayoutData(gd);
		xmlText.setText(xmlString);
		
		GridData gd2 = new GridData();
		gd2.horizontalSpan = 3;
		gd2.widthHint = 500;
		gd2.heightHint = 200;
		
		//Display results
		xPathResults = new Text(container, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		xPathResults.setLayoutData(gd2);
		xPathResults.setText("");
		
		
		return container;
	}

	private void fillResults() {
		// TODO Auto-generated method stub
		
		String xpathExpression = this.xPathExpression.getText();
		if(xpathExpression != null && xpathExpression.length() >0){
			StringBuffer buff = new StringBuffer();
			
			try {
				XPath x = XPath.newInstance(xpathExpression);
				List l = x.selectNodes(this.dspXML);
				for (Iterator iter = l.iterator(); iter.hasNext();) {
					Object element = (Object) iter.next();
					
					buff.append(element.toString() + "\n");
					
					
				}
				
				this.xPathResults.setText(buff.toString());
				
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
