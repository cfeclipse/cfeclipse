/*
 * Created on Mar 20, 2004
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
