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

/**
 * This represents the state of a tag attribute assist request.
 * 
 * Example:
 * <code>&lt;cfinclude template=""</code>
 * 
 * Insight requested between the double quotes.
 * 
 * @author Oliver Tupman
 */
public interface IAssistTagAttributeState extends IAssistTagState {
	/**
	 * Returns the name of the attribute that the user is requesting
	 * content assist on.
	 * 
	 * @return The name of the attribute, raw from the user.
	 */
	public String getAttribute();
	
	/**
	 * Gets the attribute value so far that the user has entered.
	 * 
	 * @return The value so far that the user has entered.
	 */
	public String getValueSoFar();
}
