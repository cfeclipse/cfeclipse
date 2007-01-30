/*
 * Created on Aug 9, 2004
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
package org.cfeclipse.cfml.parser.docitems;


/**
 * Represents an attribute within a document.
 * An attribute simply has a name and a value.
 * 
 * @author Oliver Tupman
 */
public class AttributeItem extends DocItem {
	/**
	 * The string-based value that this attribute has.
	 */
	protected String attrValue = "";
	
	/**
	 * 
	 * @param line
	 * @param startDocPos
	 * @param endDocPos
	 * @param name
	 */
	public AttributeItem(int line, int startDocPos, int endDocPos, String name) {
		super(line, startDocPos, endDocPos, name);
	}

	/**
	 * 
	 * @param line - line at which this item was found
	 * @param startDocPos - start position in the document for this item
	 * @param endDocPos - end position in the document for this item
	 * @param name - the 'name' of this item
	 * @param value - the value of this attribute
	 */
	public AttributeItem(int line, int startDocPos, int endDocPos, String name, String value) {
		super(line, startDocPos, endDocPos, name);
		this.attrValue = value;
	}
	
	/**
	 * Returns the string-based value that this attribute has.
	 * 
	 * @return The attribute
	 */
	public String getValue() {
		return this.attrValue;
	}
}
