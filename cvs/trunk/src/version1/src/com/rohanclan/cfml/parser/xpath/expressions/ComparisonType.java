/*
 * Created on Jun 25, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver Tupman
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
package com.rohanclan.cfml.parser.xpath.expressions;


public class ComparisonType {
	public static final String COMP_EQ = "=";
	public static final String COMP_NEQ = "!=";
	public static final String COMP_GT = ">";
	public static final String COMP_LT = "<";
	public static final String COMP_GTE = ">=";
	public static final String COMP_LTE = "<=";
	private String name;
	private String value;
	private String compType;
	
	public ComparisonType(String name, String value, String compType) {
		this.name = name;
		this.value = value;
		this.compType = compType;
	}
	
	public String getType() { return this.compType; }
	public String getName() { return this.name; }
	public String getValue() { return this.value; }
	
	public boolean performComparison(int value) {
		int thisValue = Integer.parseInt(this.value);
//	System.out.println("ComparisonType::performComparison() - Is " + value + "(" + this.name + ") " + this.compType + " " + thisValue);
		if(this.compType.compareTo(ComparisonType.COMP_EQ) == 0) {
			return thisValue == value;
		} else if(this.compType.compareTo(ComparisonType.COMP_NEQ) == 0) {
			return thisValue != value;
		} else if(this.compType.compareTo(ComparisonType.COMP_GT) == 0) {
			return value > thisValue;
		} else if(this.compType.compareTo(ComparisonType.COMP_GTE) == 0) {
			return value >= thisValue;
		} else if(this.compType.compareTo(ComparisonType.COMP_LT) == 0) {
			return value < thisValue;
		} else if(this.compType.compareTo(ComparisonType.COMP_LTE) == 0) {
			return value <= thisValue;
		} 				
		
		return false;
	}
}