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

/**
 * @author Rob
 *
 * This is a function. Functions are like attribtues except they have a return
 * type (and the values have return types but thats not our problem hehe)
 */
public class Function extends Attribute {
	/** functions need the void type */
	public static final String VOID = "void";
	/** return type */
	protected String returns = VOID;
	
	/** what platform this function is avaiable on 
	 * this is kind of lame, but it uses the same values
	 * as Tag - so use those Tag.MX Tag.BD etc
	 */
	protected byte creator = Tag.MX;
	
	/**
	 * creates a function with only a name
	 * @param name the attribute name
	 */
	public Function(String name)
	{
		super(name);
	}
	
	public Function(String name, byte creator)
	{
		super(name);
		this.creator = creator;
	}
	
	/**
	 * creates a function with a name and a type
	 * @param name the name
	 * @param type the type typically string numeric or object
	 */
	public Function(String name, String returntype, byte creator)
	{
		//super(name,type);
		super(name);
		this.creator = creator;
		this.returns = returntype;
	}
}
