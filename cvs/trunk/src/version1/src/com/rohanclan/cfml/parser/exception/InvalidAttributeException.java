/*
 * Created on Mar 18, 2004
 *
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
