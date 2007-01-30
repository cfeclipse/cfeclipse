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
package org.cfeclipse.cfml.parser.exception;

/**
 * @author Oliver Tupman
 * Generic class for defining a parse error involving an attribute
 */
public class AttributeException extends ParseException {
	protected String attrName = "";
	protected String attrValue = "";
	
	public String getName() { return attrName; }
	public String getValue() { return attrValue; }

	public AttributeException(String name, String value)
	{
		super();
		attrName = name;
		attrValue = value;
	}	
	
	public AttributeException(String name, String value, String reason)
	{
		super(reason);
		attrName = name;
		attrValue = value;
	}
	
	public AttributeException(String name, String value, String reason, int pLineNum) {
		super(reason, pLineNum);
		attrName = name;
		attrValue = value;

	}
	public AttributeException(String name, String value, String reason, int pLineNum, int pDocOffset)
	{
		super(reason, pLineNum, pDocOffset);
		attrName = name;
		attrValue = value;
	}	
	
	public AttributeException(String name, String value, int pLineNum)
	{
		super(pLineNum);
		attrName = name;
		attrValue = value;
	}
	
	public AttributeException(String name, String value, int pLineNum, int pDocOffset)
	{
		super(pLineNum, pDocOffset);
		attrName = name;
		attrValue = value;
	}
}
