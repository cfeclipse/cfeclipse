/*
 * Created on Oct 8, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package com.rohanclan.cfml.editors;

import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.DefaultInformationControl.IInformationPresenter;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.StyleRange;

import org.eclipse.swt.SWT;



/**
 * @author Stephen Milligan
 */
public class InformationPresenter implements IInformationPresenter {

    /**
     * 
     */
    public InformationPresenter() {
        super();
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.text.DefaultInformationControl.IInformationPresenter#updatePresentation(org.eclipse.swt.widgets.Display, java.lang.String, org.eclipse.jface.text.TextPresentation, int, int)
     */
    public String updatePresentation(Display display, String hoverInfo,
            TextPresentation presentation, int maxWidth, int maxHeight) {
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
