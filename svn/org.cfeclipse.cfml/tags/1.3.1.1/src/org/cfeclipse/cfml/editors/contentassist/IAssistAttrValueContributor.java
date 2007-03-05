/*
 * Created on Sep 22, 2004
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

import org.cfeclipse.cfml.dictionary.Value;

/**
 * Clients implement this interface if they wish to provide tag attribute value
 * insight.
 * For example we have the following entered by the user:
 * <code>&lt;cffunction returntype=""</code>
 * The user then invoked content assist between the quotes. 
 * A class that implements this interface will be asked for any contributions
 * it has based upon the assist state provided to it.
 *
 * @author Oliver Tupman
 */
public interface IAssistAttrValueContributor {
    /**
     * Requests proposals from the contributor.
     * 
     * @param state The current state of play with the assist.
     * @return An array of strings that contains the proposals made
     */
    public Value [] getAttributeValueProposals(IAssistTagAttributeState state);
}
