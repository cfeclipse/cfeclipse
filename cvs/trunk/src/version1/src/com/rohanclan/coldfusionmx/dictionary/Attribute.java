/*
 * Created on Feb 27, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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
package com.rohanclan.coldfusionmx.dictionary;

import java.util.Set;
import java.util.HashSet;

/**
 * @author Rob
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Attribute {
	public static final String STRING  = "string";
	public static final String NUMERIC = "numeric";
	public static final String OBJECT  = "object";
	
	protected String name = "";
	protected String type = STRING;
	protected String help = "";
	
	protected Set values = null;
	
	/**
	 * creates a new attribute with only a name
	 * @param name the attribute name
	 */
	public Attribute(String name)
	{
		this.name = name;
	}
	
	/**
	 * creates an attribute with a name and a type
	 * @param name the name
	 * @param type the type typically string numeric or object
	 */
	public Attribute(String name, String type)
	{
		this(name);
		this.type = type;
	}

	/**
	 * add a value to this attribute
	 */
	public void addValue(Value val)
	{
		if(values == null)
			values = new HashSet();
		
		values.add(val);
	}

	public String getName()
	{
		return this.name;
	}

	/**
	 * get all the values for this attribute
	 */
	public Set getValues()
	{
		return values;
	}

	/**
	 * set the help for this attribute
     */
	public void setHelp(String help)
	{
		this.help = help;
	}
	
	public String getHelp()
	{
		return help;
	}

	public String toString()
	{
		return "[" + name + ":" + values.toString() + "]";
	}
}
