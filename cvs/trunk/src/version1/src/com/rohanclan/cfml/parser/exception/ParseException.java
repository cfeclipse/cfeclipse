/*
 * Created on Mar 18, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.parser.exception;

/**
 * @author ollie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ParseException extends java.lang.Exception {
	public int lineNumber;
	public int docOffset = 0;
	/**
	 * 
	 */
	public ParseException() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param arg0
	 */
	public ParseException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param arg0
	 */
	public ParseException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param arg0
	 * @param arg1
	 */
	public ParseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	public ParseException(String arg0, int pLineNum) {
		super(arg0);
		lineNumber = pLineNum;
	}
	public ParseException(String arg0, int pLineNum, int pDocOffset)
	{
		super(arg0);
		lineNumber = pLineNum;
		docOffset = pDocOffset;
	}
	
	public ParseException(int pLineNum, int pDocOffset)
	{
		super();
		lineNumber = pLineNum;
		docOffset = pDocOffset;
	}
	
	public ParseException(int pLineNum)
	{
		super();
		lineNumber = pLineNum;
	}
}
