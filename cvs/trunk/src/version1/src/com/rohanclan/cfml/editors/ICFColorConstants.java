/*
 * Created on Jan 30, 2004
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
package com.rohanclan.cfml.editors;

/**
 * @author Rob
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
import org.eclipse.swt.graphics.RGB;

public interface ICFColorConstants {
	/** doctype */
	//RGB DOCTYPE		= new RGB(128, 128, 128);
	/** cf comments */
	RGB CF_COMMENT 		= new RGB(128, 128, 128);
	/** html comments */
	RGB HTM_COMMENT 		= new RGB(96, 153, 96);
	/** html strings */
	RGB STRING      		= new RGB(0, 0, 255);
	/** page default text */
	RGB DEFAULT     		= new RGB(0, 0, 0);
	/** html tag color */
	RGB TAG         		= new RGB(0, 0, 128);
	/** html form color */
	RGB FORM				= new RGB(195, 136, 0);
	/** html table color  */
	RGB TABLE      		= new RGB(132, 0, 215);
	/////////////////////////////////////
	/** coldfusion attribute strings */
	RGB CFSTRING    		= new RGB(0, 0, 255);
	/** coldfusion tags */
	RGB CFTAG       		= new RGB(128, 0, 0);
	//RGB CFVARIABLE  = new RGB(128, 10, 10);
	/** coldfusion keywords (eq, and, or, et cetra) */
	RGB CFKEYWORD   		= new RGB(0, 10, 255);
	/** coldfusion numbers */
	RGB CFNUMBER    		= new RGB(255, 10, 10);
	//////////////////////////////////////
	RGB CFSCRIPT    		= new RGB(0, 0, 0);
	RGB CFSCRIPT_KEYWORD	= new RGB(0, 0, 255);
	RGB CFSCRIPT_FUNCTION	= new RGB(0, 0, 102);
	RGB CFSCRIPT_STRING	= new RGB(0, 102, 0);
	
	/** javascript tag color */
	RGB JSCRIPT 		    = new RGB(51, 0, 153);
	/** javascript function color */
	RGB JSCRIPT_FUNCTION	= new RGB(0, 153, 255);
	
	/** non css values color */
	RGB CSS         		= new RGB(255, 0, 255);
	/** the css tag color */
	RGB CSS_TAG     		= new RGB(153, 0, 153);
	
	/** all xml type tags (i.e. not cf and not html */
	RGB UNK_TAG			= new RGB(0, 0, 128);
	/** editor background color */
	RGB BACKGROUND_COLOR = new RGB(255, 255, 255);
	
	/** bracket matching color */
	RGB BRACKET_MATCHING_COLOR = new RGB(255, 0, 0);
}
