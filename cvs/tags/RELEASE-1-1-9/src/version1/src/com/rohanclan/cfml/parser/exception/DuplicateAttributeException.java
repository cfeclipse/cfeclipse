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
package com.rohanclan.cfml.parser.exception;

/**
 * @author Oliver Tupman.
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DuplicateAttributeException extends AttributeException {
	
	/**
	 * @param name
	 * @param value
	 * @param pLineNum
	 */
	public DuplicateAttributeException(String name, String value, int pLineNum) {
		super(name, value, pLineNum);
	}
	/**
	 * @param name
	 * @param value
	 * @param pLineNum
	 * @param pDocOffset
	 */
	public DuplicateAttributeException(String name, String value, int pLineNum,
			int pDocOffset) {
		super(name, value, pLineNum, pDocOffset);
	}
	/**
	 * @param name
	 * @param value
	 */
	public DuplicateAttributeException(String name, String value) {
		super(name, value);
	}
	/**
	 * @param name
	 * @param value
	 * @param reason
	 */
	public DuplicateAttributeException(String name, String value, String reason) {
		super(name, value, reason);
	}
	/**
	 * @param name
	 * @param value
	 * @param reason
	 * @param pLineNum
	 */
	public DuplicateAttributeException(String name, String value,
			String reason, int pLineNum) {
		super(name, value, reason, pLineNum);
	}
	/**
	 * @param name
	 * @param value
	 * @param reason
	 * @param pLineNum
	 * @param pDocOffset
	 */
	public DuplicateAttributeException(String name, String value,
			String reason, int pLineNum, int pDocOffset) {
		super(name, value, reason, pLineNum, pDocOffset);
	}
}
