/*
 * Created on Sep 21, 2004
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


import org.eclipse.core.internal.utils.Assert;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

import com.rohanclan.cfml.dictionary.SyntaxDictionary;

/**
 * The CFML tag assistor.
 *
 * This should not be extended by clients.
 * 
 * This enhances upon the default tag assist by only showing it's proposals
 * when the user enters the string '&lt;cf'.
 * 
 * TODO: Should the <cf toggle be user-definable?
 *  
 * @author Oliver Tupman
 */
public class CFMLTagAssist extends DefaultTagAssistContributor implements IAssistContributor {
	private SyntaxDictionary sourceDict = null;
	/**
	 * Setups up the assistor. You must supply the CFML dictionary when creating
	 * this.
	 * 
	 * @param sourceDictionary The dictionary that the CFML assist will base itself on.
	 */
	public CFMLTagAssist(SyntaxDictionary sourceDictionary) {
		super(sourceDictionary);
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.editors.contentassistors.IAssistContributor#getProposals(com.rohanclan.cfml.editors.contentassistors.IAssistState)
	 */
	public ICompletionProposal[] getTagProposals(IAssistState state) {
		Assert.isNotNull(state,"CFMLTagAssist::getTagProposals()");
		//
		// We're only going to provide CFML insight when the user has entered "<cf"
		if(state.getDataSoFar().length() > 0 
			&& !state.getDataSoFar().toLowerCase().trim().startsWith("<cf"))
			return new ICompletionProposal[0];

		return super.getTagProposals(state);
	}

}
