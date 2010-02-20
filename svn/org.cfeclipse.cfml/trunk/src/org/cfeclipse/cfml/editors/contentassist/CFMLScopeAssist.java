/*
 * Created on Sep 25, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver Tupman
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

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.dictionary.ISyntaxDictionary;
import org.cfeclipse.cfml.dictionary.ScopeVar;
import org.cfeclipse.cfml.dictionary.SyntaxDictionary;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;


/**
 * Provides CFML Scope Assist at the tag-insight level. So the if
 * the user is tapping away
 *
 * @author Oliver Tupman
 */
public class CFMLScopeAssist extends AssistContributor 
	   		 implements IAssistContributor 
{
    /**
     * Source dictionary for the scope info. Currently defaults
     * to the global CF dictionary.
     */
    private SyntaxDictionary sourceDict;
    
    private Pattern wordPattern = Pattern.compile("[\\w]");
    private Pattern scopePattern = Pattern.compile("[0-9a-zA-z_\\.]+$");
    
    
    /**
     * 
     */
    public CFMLScopeAssist() {
        this.sourceDict = DictionaryManager.getDictionary(DictionaryManager.CFDIC);
       // Assert.isNotNull(this.sourceDict,"CFMLScopeAssist()::CFMLScopeAssist()");
        if(this.sourceDict == null)
        		throw new IllegalArgumentException("CFMLScopeAssist()::CFMLScopeAssist()");
    }

    /* (non-Javadoc)
     * @see org.cfeclipse.cfml.editors.contentassist.IAssistContributor#getTagProposals(org.cfeclipse.cfml.editors.contentassist.IAssistState)
     */
    public ICompletionProposal[] getTagProposals(IAssistState state) {
        
        int dotOffset = 0;
        Properties filteredItems = new Properties();
        String doc = state.getIDocument().get();
        //If there isn't a "." in the state data we can exit.
        if(state.getDataSoFar().lastIndexOf(".") < 0){
            return null;
        }
        
        // Check if the next character after the cursor is valid
        try {
            if (doc.length() > state.getOffset()) {
		        Matcher wordMatcher = wordPattern.matcher(state.getIDocument().get(state.getOffset(),1));
		        if (wordMatcher.find()) {
		            // Nope, stop right here.
		            return null;
		        }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // The offset of the last dot before the cursor
        dotOffset = state.getIDocument().get().lastIndexOf(".",state.getOffset())+1;
        
        
        String prefix = state.getDataSoFar().trim();
        

        //Checking if the prefix could be a scope variable
        Matcher scopeMatcher = scopePattern.matcher(prefix);
        
        if (scopeMatcher.find()) {
            prefix = scopeMatcher.group();
        }
        else {
           // Nope, no point in going any further.
           return null;
        }
        
        // Check if the "." came before the prefix.
        if (prefix.indexOf(".") < 0) {
            return null;
        }
        //prefix = prefix.substring(0, prefix.lastIndexOf('.'));
        
        // Get an initial set of possibilities.
		Set proposals = ((ISyntaxDictionary)this.sourceDict).getFilteredScopeVars(prefix);
		
		
		
		Iterator i = proposals.iterator();
		
		int x = 0; 
		ICompletionProposal[] workingSet = new ICompletionProposal[proposals.size()];
		
		// Build the proposals
		while (i.hasNext()) {
		    
		    Object o = i.next();
		    
		    if (o instanceof ScopeVar) {
		        ScopeVar s = (ScopeVar)o;
		        
		        StringBuffer sb = new StringBuffer(s.getName());
		        
		        String newValue = s.getName();
		        
		        // Eliminate anything that exactly matches the prefix or has a "." immediately after the end of current prefix. 
		        if (!newValue.equalsIgnoreCase(prefix) 
		                && sb.charAt(prefix.length()) != '.') {
		            
		            /*
		             * Get the position of the last dot in the proposal so we can replace the
		             * entire string from there forwards in the document. This allows us to maintain
		             * the case of the inserted string the same as it appears in the insight.
		             */
		            if (sb.lastIndexOf(".") > prefix.length()) {
		                int valueDotIndex = sb.indexOf(".",prefix.length());
			            newValue = sb.substring(0,valueDotIndex);
		            }
		            
		            // Make sure we don't keep any duplicates in the list
		            if(!filteredItems.containsKey(newValue)) {
		                filteredItems.setProperty(newValue,"");
		                
		                String insertion = newValue.substring(prefix.lastIndexOf(".")+1,newValue.length());
		    	        
		    	        
		    	        CompletionProposal proposal = new CompletionProposal(insertion,
		    	                dotOffset,
		    	                state.getOffset()-dotOffset,
		    	                insertion.length(),
		    	                CFPluginImages.get(CFPluginImages.ICON_DEFAULT),
		    	                insertion,
		    	                null,
		    	                "");

		    			workingSet[x] = proposal;
		    		    x++;
		                
		            }
		        }
		    }
		}
		
		// Now bulid a new array of the correct size from the working set
		ICompletionProposal[] result =  new ICompletionProposal[x];
		
		for (int n=0;n<x;n++) {
		    result[n] = workingSet[n];
		}
		
		return result;
    }

	public String getId() {
		// TODO Auto-generated method stub
		return "scope.proposals";
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "Scope Proposals";
	}

	public void sessionEnded() {
		// TODO Auto-generated method stub
		
	}

	public void sessionStarted() {
		// TODO Auto-generated method stub
		
	}
    
    
    
}
