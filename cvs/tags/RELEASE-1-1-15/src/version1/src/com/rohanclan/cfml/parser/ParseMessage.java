/*
 * Created on Mar 23, 2004
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

public class ParseMessage {
	protected int lineNumber;
	protected int docStartOffset;
	protected int docEndOffset;
	protected String docData;
	protected String message;
	protected boolean fatal = false;
	
	public ParseMessage(int lineNum, int docStart, int docEnd, String data, String msg)
	{
		lineNumber = lineNum;
		docStartOffset = docStart;
		docEnd = docStartOffset;
		docData = data;
		message = msg;
	}

	public ParseMessage(int lineNum, int docStart, int docEnd, String data, String msg, boolean isFatal)
	{
		lineNumber = lineNum;
		docStartOffset = docStart;
		docEnd = docStartOffset;
		docData = data;
		message = msg;
		fatal = isFatal;
	}
	
	public boolean isFatal() { return fatal; }
	
	/**
	 * @return Returns the docData.
	 */
	public String getDocData() {
		return docData;
	}
	/**
	 * @param docData The docData to set.
	 */
	public void setDocData(String docData) {
		this.docData = docData;
	}
	/**
	 * @return Returns the docEndOffset.
	 */
	public int getDocEndOffset() {
		return docEndOffset;
	}
	/**
	 * @param docEndOffset The docEndOffset to set.
	 */
	public void setDocEndOffset(int docEndOffset) {
		this.docEndOffset = docEndOffset;
	}
	/**
	 * @return Returns the docStartOffset.
	 */
	public int getDocStartOffset() {
		return docStartOffset;
	}
	/**
	 * @param docStartOffset The docStartOffset to set.
	 */
	public void setDocStartOffset(int docStartOffset) {
		this.docStartOffset = docStartOffset;
	}
	/**
	 * @return Returns the lineNumber.
	 */
	public int getLineNumber() {
		return lineNumber;
	}
	/**
	 * @param lineNumber The lineNumber to set.
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
