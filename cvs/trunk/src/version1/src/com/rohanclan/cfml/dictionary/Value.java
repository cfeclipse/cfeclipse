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

/**
 * @author Rob
 *
 * This is really just a string wrapper. Its a possible value for an attribute
 * its only here to abstract it a bit and to give some room to play for future 
 * additions
 */
public class Value implements Comparable {
	protected String value;
	
	public Value(String val)
	{
		value = val;
	}
	
	/**
	 * gets this values value
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}
	
	public void setValue(String newValue)
	{
		value = newValue;
	}
	
	public String toString()
	{
		return value;
	}

	public boolean equals(Object obj)
	{
		if(obj instanceof Parameter)
		{
			//if the name is the same and the type is the same
			//assume its the same
			if( ((Value)obj).getValue().equals(this.value))
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
			return value.compareTo( ((Value)o).getValue() );
		}
		
		return 0;
	}
	
}
