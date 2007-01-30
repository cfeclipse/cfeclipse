/*
 * Created on May 06, 2006
 *
 * The MIT License
 * Copyright (c) 2006 Mark Drew
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
package org.cfeclipse.cfml.editors.contentassist;

import java.util.HashMap;
import java.util.Iterator;

import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.CFParser;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;


/**
 * Provides "arguments" assistance when you are in a CFC. It should provide you a list of the arguments you have set.
 * 
 * @author Mark Drew
 */
public class CFMLArgumentAssist 
	   		 implements IAssistContributor 
{
    
    
    /**
     * 
     */
    public CFMLArgumentAssist() {
    }

    /* (non-Javadoc)
     * @see org.cfeclipse.cfml.editors.contentassist.IAssistContributor#getTagProposals(org.cfeclipse.cfml.editors.contentassist.IAssistState)
     */
    public ICompletionProposal[] getTagProposals(IAssistState state) {
    	ICFDocument doc = (ICFDocument)state.getIDocument();
    	String extension = doc.getResource().getFileExtension();

    	System.out.println("The data so far is..." + state.getDataSoFar());
    	
    	/*
         * Only show content assist if the trigger was .
         */
        if (state.getTriggerData() != '.') {
            return null;
        }
        //Also check we are in a CFC
        else if(!extension.equalsIgnoreCase("cfc")){
        	System.out.println("Extension isnt cfc");
        	return null;
        }
        else {
        	String allData = state.getDataSoFar();
        	
        	if(allData.endsWith(".")){
        		allData = allData.substring(0, allData.length()-1);
        	}
        	
        	String VarName = "";
        	
        	StringBuffer buf = new StringBuffer();
        	for (int i=allData.length()-1;i>=0;i--) {
        		if (!Character.isJavaIdentifierPart(allData.charAt(i))){
        			break; 
        		}
        		buf.insert(0,allData.charAt(i));
        	}
        	VarName = buf.toString();
        	
        	if(!VarName.equalsIgnoreCase("arguments")){
        		return null;
        	}
        	
        	//Here we find out where we are in the document.
        	ICompletionProposal[] result = getArguments(state, doc);
        	
        	return result;
        }
    }
    /**
     * This function finds the function you are in, then finds the arguments for that function.
     * @param state
     * @param doc
     * @return
     */
    public ICompletionProposal[] getArguments(IAssistState state, ICFDocument doc){

    	
        
        
//		TODO: Somehow scan back across the paritions, to find the first cfargument.
//		TODO: Make sure we are IN a cffunction
		/*
		 * What I could do, strip down the document up to the state.getOffeset. Then find the LAST 
		 * cffunction. Parse that last CFfunction for items, and get it. I am sure the partitioner would be better but need to speak to Spike about how it works, 
		 * Backwards looping over partitions would be very handy since we will be doing this a lot
		 * 
		 * 
		 * Forgetting to parse!
		 */
		
    	CFParser parser = new CFParser();
    	
    	String docSoFar = doc.get().substring(0, state.getOffset());
        CFDocument cfdoc = parser.parseDoc(state.getIDocument().get());
        
        //Get the variables:
        HashMap varMap = cfdoc.getVariableMap();
        ICompletionProposal[] proposals = new ICompletionProposal[varMap.size()];
        
        //Find arguments (just for show really
        
        Iterator iter = varMap.keySet().iterator();
        int counter = 0;
        while(iter.hasNext()){
        	 Object key = iter.next();
             Object  value= varMap.get(key);
        	
             CompletionProposal proposal = new CompletionProposal(key.toString(), 1, 1, 1);
             proposals[counter] = proposal;
        	
        	  counter++;
        }
        
        
    	/*DocItem docroot = cfdoc.getDocumentRoot();
        CFNodeList nodes = docroot.selectNodes("//cffunction");
        
        Iterator iter = nodes.iterator();
        TagItem lasttag = null;
        while(iter.hasNext()){
        	
        	lasttag = (TagItem)iter.next();
        	
        	System.out.println("found a function "+ lasttag.getAttributeValue("name"));
        }*/
        
        //now we have set the lasttag to the "nearest" cffunction, lets see whats the deal
       /* if(lasttag != null){
        	System.out.println("The last cffunction is + " + lasttag.getAttributeValue("name"));
        }*/
        
        
        
    	
    	 
    	
    	
    	
    	return proposals;
    }
    
}
