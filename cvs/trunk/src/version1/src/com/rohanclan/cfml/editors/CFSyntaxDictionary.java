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

import java.util.Set;
import java.util.HashSet;

import com.rohanclan.cfml.dictionary.SyntaxDictionary;
import com.rohanclan.cfml.dictionary.SyntaxDictionaryInterface;

/**
 * @author Rob
 *
 * Extension of the SyntaxDictionary. Has a few cf specific items
 */
public class CFSyntaxDictionary extends SyntaxDictionary implements SyntaxDictionaryInterface {
	protected static Set operators;
	protected static Set scriptkeywords;
	protected static Set sqlkeywords;
	
	public CFSyntaxDictionary()
	{
		super();
		operators = new HashSet();
		buildOperatorSyntax();

		scriptkeywords = new HashSet();
		sqlkeywords = new HashSet();
		buildScriptKeywordSyntax();
		buildSQLKeywordSyntax();
	}
	
	/**
	 * gets any operators (eq, or, and) (lowercase only)
	 * @param elementname
	 * @return
	 */
	public Set getOperators()
	{
		return operators;
	}
	
	/**
	 * gets cfscript specific keywords (if, while, etc);
	 * @return
	 */
	public Set getScriptKeywords()
	{
		return scriptkeywords;
	}
	
	/**
	 * gets SQL specific keywords (SELECT, FROM etc.);
	 * @return
	 */
	public Set getSQLKeywords()
	{
		return sqlkeywords;
	}
	
	/** 
	 * build all the cfscript keywords 
	 */
	protected static void buildScriptKeywordSyntax()
	{
		scriptkeywords.add("for");
		scriptkeywords.add("if");
		scriptkeywords.add("else");
		scriptkeywords.add("while");
		scriptkeywords.add("return");
		scriptkeywords.add("function");
		scriptkeywords.add("var");
		scriptkeywords.add("case");
		scriptkeywords.add("do");
		scriptkeywords.add("try");
		scriptkeywords.add("catch");
		scriptkeywords.add("continue");
		scriptkeywords.add("switch");
		scriptkeywords.add("default");
		scriptkeywords.add("break");
		scriptkeywords.add("true");
		scriptkeywords.add("false");
		scriptkeywords.add("to");
		scriptkeywords.addAll(operators);
	}
	
	/** 
	 * build all the SQL keywords 
	 */
	protected static void buildSQLKeywordSyntax()
	{
	    //This should really be read in from a file
		sqlkeywords.add("add");
		sqlkeywords.add("alter");
		sqlkeywords.add("and");
		sqlkeywords.add("asc");
		sqlkeywords.add("avg");
		sqlkeywords.add("between");
		sqlkeywords.add("by");
		sqlkeywords.add("cascade");
		sqlkeywords.add("constraint");
		sqlkeywords.add("count");
		sqlkeywords.add("create");
		sqlkeywords.add("database");
		sqlkeywords.add("delete");
		sqlkeywords.add("desc");
		sqlkeywords.add("drop");
		sqlkeywords.add("exists");
		sqlkeywords.add("foreign");
		sqlkeywords.add("from");
		sqlkeywords.add("group");
		sqlkeywords.add("having");
		sqlkeywords.add("in");
		sqlkeywords.add("inner");
		sqlkeywords.add("insert");
		sqlkeywords.add("into");
		sqlkeywords.add("join");
		sqlkeywords.add("key");
		sqlkeywords.add("left");
		sqlkeywords.add("like");
		sqlkeywords.add("max");
		sqlkeywords.add("min");
		sqlkeywords.add("modify");
		sqlkeywords.add("not");
		sqlkeywords.add("null");
		sqlkeywords.add("on");
		sqlkeywords.add("or");
		sqlkeywords.add("order");
		sqlkeywords.add("outer");
		sqlkeywords.add("primary");
		sqlkeywords.add("right");
		sqlkeywords.add("select");
		sqlkeywords.add("set");
		sqlkeywords.add("sum");
		sqlkeywords.add("table");
		sqlkeywords.add("trigger");
		sqlkeywords.add("truncate");
		sqlkeywords.add("union");
		sqlkeywords.add("update");
		sqlkeywords.add("view");
		sqlkeywords.add("where");
	}
	
	/** 
	 * build all the operators in the language 
	 */
	protected static void buildOperatorSyntax()
	{
		operators.add("gt");
		operators.add("lt");
		operators.add("gte");
		operators.add("lte");
		operators.add("eq");
		operators.add("neq");
		operators.add("not");
		operators.add("and");
		operators.add("or");
		operators.add("mod");
		operators.add("is");
		operators.add("does");
		operators.add("contains");
		operators.add("greater");
		operators.add("than");
		operators.add("less");
		operators.add("equal");
		//operators.add("to");
		operators.add("xor");
		operators.add("eqv");
		operators.add("imp");
	}
}

