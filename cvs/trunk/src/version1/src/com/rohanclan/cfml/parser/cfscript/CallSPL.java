/*
 * Created on Jun 23, 2004
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
package com.rohanclan.cfml.parser.cfscript;

/**
 * @author Oliver Tupman
 *
 */
public class CallSPL {

	/**
	 * 
	 */
	public CallSPL() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void CallSPL(String argument) {
		SPLParser parser;
		  try {
	        parser = new SPLParser(new java.io.FileInputStream(argument));
	      } catch (java.io.FileNotFoundException e) {
	        System.out.println("Stupid Programming Language Interpreter Version 0.1:  File " + argument + " not found.");
	        return;
	      }
	      
	      try {
	      	parser.enable_tracing();
	        parser.CompilationUnit();
	        //parser.jjtree.rootNode().interpret();
	        SimpleNode tempNode = (SimpleNode)parser.jjtree.rootNode();
	        tempNode.dump("");
	      } catch (ParseException e) {
	        System.out.println("Stupid Programming Language Interpreter Version 0.1:  Encountered errors during parse.");
	        e.printStackTrace();
	      } catch (Exception e1) {
	        System.out.println("Stupid Programming Language Interpreter Version 0.1:  Encountered errors during interpretation/tree building.");
	        e1.printStackTrace();
	      }	      
	}
	
}
