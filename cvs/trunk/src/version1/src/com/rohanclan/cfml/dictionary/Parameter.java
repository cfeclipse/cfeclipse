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
package com.rohanclan.cfml.dictionary;

import java.util.Set;
import java.util.HashSet;
/**
 * @author Rob
 *
 * This is a function's (and tag's) parameter (aka attribute).
 */
public class Parameter implements Comparable {
	protected String name = "";
	protected String type = Procedure.VOID;
	protected String help = "";
	protected Set values;
	protected boolean required;
		
	public Parameter(String name)
	{
		this.name = name;
	}
	
	public Parameter(String name, String type)
	{
		this(name);
		this.type = type;
	}
	
	public Parameter(String name, String type, boolean required)
	{
		this(name,type);
		this.required = required;
	}
	
	public boolean isRequired()
	{
		return required;
	}
	
	/**
	 * this sets the name and type of this parameter - generally this should not
	 * be used as types dont often change.
	 * @param name the param name
	 * @param type the param type @see Procedure
	 */
	public void setNameAndType(String name, String type)
	{
		this.name = name;
		this.type = type;
	}
	
	/**
	 * Adds a default value to this parameter
	 * @param value the value to add
	 */
	public void addValue(Value value)
	{
		if(values == null)
			values = new HashSet();
		
		values.add(value);
	}
	
	public Set getValues()
	{
		if(values == null)
			return new HashSet();

		
		System.err.println("Parameter::getValues() - I have " + values.size() + " elements");
		return values;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public String getHelp()
	{
		return help;
	}
	
	public void setHelp(String help)
	{
		this.help = help;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(name + " ");
		sb.append("[" + type + "]");
		if(required)
			sb.append("*");
		
		return sb.toString();
	}
	
	public boolean equals(Object obj)
	{
		if(obj instanceof Parameter)
		{
			//if the name is the same and the type is the same
			//assume its the same
			if( ((Parameter)obj).getName().equals(this.name) && ((Parameter)obj).getType().equals(this.type) )
			{
				return true;
			}
		}
		
		return false;
	}
	
	public int compareTo(Object o)
	{
		if(o == null)
			throw new NullPointerException("Null!");
		
		if(o instanceof Parameter)
		{
			return name.compareTo( ((Parameter)o).getName() );
		}
		
		return 0;
	}
	
}
