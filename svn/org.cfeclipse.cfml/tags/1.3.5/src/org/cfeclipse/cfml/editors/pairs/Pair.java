/*
 * Created on Sep 21, 2004
 * 
 * The MIT License
 * Copyright (c) 2004 Matt Cristantello
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
 *
 */
package org.cfeclipse.cfml.editors.pairs;

/**
 * @author Matt
 *
 * Abstracted storage type for a PairMatcher pair.
 */
public class Pair
{
	private String left;
	private String right;
	private int length;
	/**
	 * @param left
	 * @param right
	 * @param length
	 */
	public Pair(String left, String right, int length)
	{
		this.left = left;
		this.right = right;
		this.length = length;
	}
	/**
	 * @return Returns the left value.
	 */
	public String getLeft()
	{
		return left;
	}
	/**
	 * @param left The left value to set.
	 */
	public void setLeft(String left)
	{
		this.left = left;
	}
	/**
	 * @return Returns the length value.
	 */
	public int getLength()
	{
		return length;
	}
	/**
	 * @param length The length value to set.
	 */
	public void setLength(int length)
	{
		this.length = length;
	}
	/**
	 * @return Returns the right value.
	 */
	public String getRight()
	{
		return right;
	}
	/**
	 * @param right The right value to set.
	 */
	public void setRight(String right)
	{
		this.right = right;
	}
}
