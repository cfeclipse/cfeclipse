/*
 * Created on Oct 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.editors;

import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.DefaultInformationControl.IInformationPresenter;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.StyleRange;

import org.eclipse.swt.SWT;



/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class InformationPresenter implements IInformationPresenter {

    /**
     * 
     */
    public InformationPresenter() {
        super();
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.text.DefaultInformationControl.IInformationPresenter#updatePresentation(org.eclipse.swt.widgets.Display, java.lang.String, org.eclipse.jface.text.TextPresentation, int, int)
     */
    public String updatePresentation(Display display, String hoverInfo,
            TextPresentation presentation, int maxWidth, int maxHeight) {
        // TODO Auto-generated method stub
        //System.out.println("Text submitted for update." + hoverInfo);
        String newText = hoverInfo;
        
        try {
        
	        boolean searching = true;
	        
	        StyleRange defaultStyle = presentation.getDefaultStyleRange();
	        
	        while(searching) {
	            if (newText.indexOf("<b>") > -1
	                    && newText.indexOf("</b>") > newText.indexOf("<b>") ) {
	                
	              //System.out.println("Start Tag found at " + newText.indexOf("<b>"));
	              //System.out.println("End Tag found at " + newText.indexOf("</b>"));
	                
	                int start = newText.indexOf("<b>");
	                int length = newText.indexOf("</b>") - start -3 ; 
	                
	                StyleRange range = new StyleRange(start,length,null,null,SWT.BOLD);
	                presentation.addStyleRange(range);
	                //System.out.println("Text before: " + newText);
	                newText = newText.replaceFirst("<b>","");
	                newText = newText.replaceFirst("</b>","");
	                //System.out.println("Text after: " + newText);
	            }
	            else {
	                break;
	            }
	        }
        
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return newText;
    }

}
