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
package com.rohanclan.cfml.editors.contentassist;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.internal.utils.Assert;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.dictionary.Function;
import com.rohanclan.cfml.dictionary.Parameter;
import com.rohanclan.cfml.dictionary.SyntaxDictionary;
import com.rohanclan.cfml.dictionary.SyntaxDictionaryInterface;
import com.rohanclan.cfml.editors.CFCompletionProcessor;

/**
 * Provides CFML Scope Assist at the tag-insight level. So the if
 * the user is tapping away
 *
 * @author Oliver Tupman
 */
public class CFMLScopeAssist 
	   		 implements IAssistContributor 
{
    /**
     * Source dictionary for the scope info. Currently defaults
     * to the global CF dictionary.
     */
    private SyntaxDictionary sourceDict;
    /**
     * 
     */
    public CFMLScopeAssist() {
        this.sourceDict = DictionaryManager.getDictionary(DictionaryManager.CFDIC);
        Assert.isNotNull(this.sourceDict);
    }

    /* (non-Javadoc)
     * @see com.rohanclan.cfml.editors.contentassist.IAssistContributor#getTagProposals(com.rohanclan.cfml.editors.contentassist.IAssistState)
     */
    public ICompletionProposal[] getTagProposals(IAssistState state) {
        if(state.getTriggerData() != '.')
            return null;

        String prefix = state.getDataSoFar().trim();
        int length = prefix.length();
		// If the taglimiting has a space in we're assuming that the user
		// is intending to input or has inputted some attributes.
		Set proposals = ((SyntaxDictionaryInterface)this.sourceDict).getFilteredScopeVars(prefix);
		
		Iterator i = proposals.iterator();
		
		while (i.hasNext()) {
		    if (i.next() instanceof Function) {
		        length = prefix.length() - prefix.lastIndexOf(".");
// System.out.println("Function found in scope lookup. Length reset to " + length);
		        break;
		    }
		}
		
		
		// Do we have methods in the returned set?
		return CFEContentAssist.makeSetToProposal(
			proposals,
			state.getOffset(),
			CFEContentAssist.SCOPETYPE,
			length
		);
    }
}
