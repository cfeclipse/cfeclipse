/*
 * Created on Mar 18, 2004
 *
 * A parser exception
 * 
 * @author Oliver Tupman
 */
package com.rohanclan.cfml.parser.exception;

/**
 * @author ollie
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
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param name
	 * @param value
	 */
	public DuplicateAttributeException(String name, String value) {
		super(name, value);
		// TODO Auto-generated constructor stub
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
