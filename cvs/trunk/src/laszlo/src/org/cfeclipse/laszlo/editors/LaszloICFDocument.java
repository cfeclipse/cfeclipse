/*
 * Created on Nov 14, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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
package org.cfeclipse.laszlo.editors;

import org.cfeclipse.laszlo.LaszloPlugin;
import org.eclipse.core.runtime.CoreException;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.ICFDocument;
import com.rohanclan.cfml.editors.contentassist.CFEContentAssistManager;
import com.rohanclan.cfml.external.ExternalFile;
import com.rohanclan.cfml.natures.CFENature;
import com.rohanclan.cfml.util.ResourceUtils;

/**
 * @author robrohan
 */
public class LaszloICFDocument extends ICFDocument {

	/**
	 * 
	 */
	public LaszloICFDocument() {
		super();
	}
	
	/**
	 * Retrieves the Content Assist Manager (CAM) for this document. This is the 
     * manager for the CACors that are associated with this document.
     * 
     * The current implementation of this simply retrieves the CFE-global CAM
     * as project natures are not yet implemented. Once implemented this will work
     * in the default manner.
	 * 
	 * @see com.rohanclan.cfml.editors.ICFEFileDocument
	 */
	public CFEContentAssistManager getContentAssistManager() {
		if (this.getResource() instanceof ExternalFile) {
			return LaszloPlugin.getDefault().getGlobalCAM();
		}
        
        	if(this.lastRes == null)
		    return LaszloPlugin.getDefault().getGlobalCAM();
        	
        	CFEContentAssistManager returnManager = null;
        	CFENature nature;		    
        	try {
        		if(!ResourceUtils.hasNature(this.lastRes.getProject(), CFMLPlugin.NATURE_ID))
        			return LaszloPlugin.getDefault().getGlobalCAM();
		    
        		nature = (CFENature)lastRes.getProject().getNature(CFMLPlugin.NATURE_ID);
		    
        		//returnManager = nature.getNatureCAM();
        		returnManager = getCAM();
        	} catch(CoreException ex) {
        		ex.printStackTrace();
		}
        	return returnManager;
	}
}
