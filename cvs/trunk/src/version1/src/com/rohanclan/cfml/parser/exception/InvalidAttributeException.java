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
 * @author Oliver Tupman
 *
 */
public class InvalidAttributeException extends AttributeException {

	/**
	 * @param name
	 * @param value
	 */
	public InvalidAttributeException(String name, String value) {
		super(name, value);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param name
	 * @param value
	 * @param pLineNum
	 */
	public InvalidAttributeException(String name, String value, int pLineNum) {
		super(name, value, pLineNum);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param name
	 * @param value
	 * @param pLineNum
	 * @param pDocOffset
	 */
	public InvalidAttributeException(String name, String value, int pLineNum,
			int pDocOffset) {
		super(name, value, pLineNum, pDocOffset);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param name
	 * @param value
	 * @param reason
	 */
	public InvalidAttributeException(String name, String value, String reason) {
		super(name, value, reason);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param name
	 * @param value
	 * @param reason
	 * @param pLineNum
	 */
	public InvalidAttributeException(String name, String value, String reason,
			int pLineNum) {
		super(name, value, reason, pLineNum);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param name
	 * @param value
	 * @param reason
	 * @param pLineNum
	 * @param pDocOffset
	 */
	public InvalidAttributeException(String name, String value, String reason,
			int pLineNum, int pDocOffset) {
		super(name, value, reason, pLineNum, pDocOffset);
		// TODO Auto-generated constructor stub
	}
}
