/*
 * Created on Mar 4, 2004
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
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Procedure implements Comparable {
	/* cold fusion "types" */
	/** cf string type */
	public static final String STRING  = "string";
	/** cf numeric type */
	public static final String NUMERIC = "numeric";
	/** cf object type */
	public static final String OBJECT  = "object";
	/** cf void type (functions) */
	public static final String VOID    = "void";
	/** cf struct type (functions) */
	public static final String STRUCT  = "struct";
	/** cf query type (functions) */
	public static final String QUERY   = "query";
	
	/** tag for MX */
	public static final byte MX  = 0x1;
	/** tag for BlueDragon */
	public static final byte BD  = 0x2;
	/** w3c tag (normal html etc) */
	public static final byte W3C = 0x4;
	/** user defined tag */
	public static final byte USR = 0x8;
	
	/** this procedure's name */
	protected String name = "";
	
	/** what platform this procedure is avaiable on 
	 * this is kind of lame, but it uses the same values
	 * as Tag - so use those Tag.MX Tag.BD etc
	 */
	protected byte creator = MX;
	protected String help = "";
	
	protected Set parameters = null;
	
	public Procedure(String name)
	{
		this.name = name;
	}
	
	/**
	 * Gets the defined users of this tag. For example
	 * 3 is both MX and BD
	 * @return who can use this tag
	 */
	public byte getCreatorFlags()
	{
		return creator;
	}
	
	public boolean hasParameters()
	{
		if(parameters == null || parameters.size() < 1)
			return false;
		
		return true;
	}
	
	/**
	 * Adds a parameter to this procedure
	 * @param param the parameter to add
	 */
	public void addParameter(Parameter param)
	{
		if(parameters == null)
			parameters = new HashSet();
		
		parameters.add(param);
	}

	public void dumpParams()
	{
		Object [] params = parameters.toArray();
		for(int i = 0; i < params.length; i++)
		{
			System.err.println("Procedure::getParameters() - Param for \'" + name + "\' is \'" + ((Parameter)params[i]).name + "\'");
		}
	}
	
	/**
	 * get all the values for this attribute
	 */
	public Set getParameters()
	{
		return parameters;
	}
	
	public String getName()
	{
		return this.name;
	}
	
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
		if(parameters != null)
		{
			return name + ":" + parameters.size();	
		}
		return name;
	}
	
	public boolean equals(Object obj)
	{
		if(obj instanceof Procedure)
		{
			//if it has the same name and number of parameters assume its
			//the same (this may need to be adjusted in the future)
			if( ((Procedure)obj).getName().equals(this.name) && ((Procedure)obj).getParameters().size() == parameters.size() )
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * so we can sort these bad boys
	 */
	public int compareTo(Object o)
	{
		if(o == null)
			throw new NullPointerException("Null!");
		
		if(o instanceof Procedure)
		{
			return name.compareTo( ((Procedure)o).getName() );
		}
		
		return 0;
	}
}
