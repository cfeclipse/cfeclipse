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

import com.rohanclan.cfml.dictionary.Parameter;

/**
 * The interface for a tag assist contributor.
 * For more information please read the javadoc for IAssistContributor 
 * 
 * @author Oliver Tupman
 * @see IAssistContributor
 *
 */
public interface IAssistTagContributor {
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
	public Parameter [] getAttributeProposals(IAssistTagState state);
}
