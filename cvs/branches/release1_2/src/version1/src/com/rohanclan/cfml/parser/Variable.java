/*
 * Created on Mar 21, 2004
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
package com.rohanclan.cfml.parser;

import com.rohanclan.cfml.parser.docitems.DocItem;
/** Represents a variable within a document. Derived from DocItem to enable standard document creation to find it.
 *  
 *  In theory it should be stored separately from the document tree.
 */
class Variable extends DocItem 
{

protected String varType;
  /* {transient=false, volatile=false, author=Oliver Tupman, version=0.1}*/

  
/** Constructor for a Variable.
   *  
   *  All the parameters apart from newType are passed to the DocItem constructor.
   */

	public Variable(int line, int startDocPos, int endDocPos, String name)
	{
		super(line, startDocPos, endDocPos, name);
		varType = "any";
	}
	public Variable(int line, int startDocPos, int endDocPos, String name, String newType) 
	{
		super(line, startDocPos, endDocPos, name);
		varType = newType;
	}
	public String getType() {
		return varType;
	}
}