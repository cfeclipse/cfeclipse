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

import org.eclipse.jface.text.contentassist.ICompletionProposal;

/**
 * <p>
 * All clients that wish to provide assist for documents should implement
 * this interface. Plugins should register their contributors with the
 * CFE core (please see XXX for registration information).
 * </p><br/>
 * The CFE core assistors compute the assist state information that is
 * kept in a class derived from AssistState. The assistor then calls each
 * assist contributor for a specific assist type (tag, attribute, attr value,
 * etc.) and asks it whether it will contribute.
 *
 * Once the list of contributors that will contribute is gathered they will
 * have their getProposals() called. The core assist will perform some finalising
 * on the gathered contributions and present them to the user.
 *
 * The gathering process is based upon a first-come, first-serve method.
 * 
 * This type of contributor will be asked to contribute every time the user invokes
 * content assist in a CFML context where they're not after attribute or attribute
 * value insight. You may, of course, implement this interface and the attribute/
 * attribute insight assist interfaces.
 * 
 * @author Oliver Tupman
 */ 
public interface IAssistContributor {
	/**
     * Determines whether the assist wishes to contribute to the
     * current proposals.
     * Not sure whether this is really needed as getProposals() would
     * need to provide the same information as to whether it would trigger
     * so getProposals() could just return a 0 length array or null. 
	 * 
	 * @param state The state of the assist
	 * @return An array of completion proposals
	 */
	public ICompletionProposal [] getTagProposals(IAssistState state);
}
