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
public class Tag {
	/** tag for MX */
	public static final byte MX  = 0x1;
	/** tag for BlueDragon */
	public static final byte BD  = 0x2;
	/** w3c tag (normal html etc) */
	public static final byte W3C = 0x4;
	/** user defined tag */
	public static final byte USR = 0x8;
	
	protected boolean single = false;
	protected boolean xmlstyle = false;
	protected String name = "";
	protected byte creator = MX;
	protected String help = "";
	
	protected Set attributes = null;
	
	public Tag(String name)
	{
		this.name = name;
	}
	
	public Tag(String name, boolean single)
	{
		this(name);
		this.single = single;
	}
	
	public Tag(String name, boolean single, byte creator)
	{
		this(name,single);
		this.creator = creator;
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
	
	/**
	 * Adds an attribute to this tag
	 * @param attr the attribute
	 */
	public void addAttribute(Attribute attr)
	{
		if(attributes == null)
			attributes = new HashSet();
		
		attributes.add(attr);
	}
	
	/**
	 * get all the values for this attribute
	 */
	public Set getAttributes()
	{
		return attributes;
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
		return "[" + name + ":" + attributes.toString() + "]";
	}
}
