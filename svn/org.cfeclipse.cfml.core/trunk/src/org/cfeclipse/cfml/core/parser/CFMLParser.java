package org.cfeclipse.cfml.core.parser;

/*
Copyright (c) 2007 Mark Mandel, Mark Drew

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.	
*/

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import org.cfeclipse.cfml.core.parser.antlr.*;

import java.util.*;

public class CFMLParser extends org.cfeclipse.cfml.core.parser.antlr.CFMLParser
{
	private ErrorObservable observable;
	private ICFMLDictionary dictionary;
	
	public CFMLParser(TokenStream input, ICFMLDictionary dictionary)
	{
		super(input);
		setObservable(new ErrorObservable());
		setDictionary(dictionary);
	}
	
	public void addObserver(IErrorObserver observer)
	{
		getObservable().addObserver(observer);
	}
	
	public void removeObserver(IErrorObserver observer)
	{
		getObservable().removeObserver(observer);
	}
	
	public void displayRecognitionError(String[] tokenNames, RecognitionException e)
	{
		ErrorEvent event = new ErrorEvent(e, getErrorMessage(e, tokenNames));
		
		getObservable().notifyObservers(event);
		
		super.displayRecognitionError(tokenNames, e);
	}

	protected boolean containsCFScript(Token tag)
	{
		return getDictionary().conatinsCFScript(tag.getText().substring(1));
	}	
	
	protected boolean isColdFusionTag(Token tag)
	{
		//strip off the top layer
		return getDictionary().isColdFusoinTag(tag.getText().substring(1));
	}

	protected boolean isCustomTag(Token tag)
	{		
		return tag.getText().toLowerCase().startsWith("<cf_"); 
	}

	protected boolean isImportTag(Token tag)
	{
		return tag.getText().contains(":");
	}
	
	protected Tree parseCFScript(Token start, ParserRuleReturnScope stop)
	{
		org.antlr.runtime.BitSet bit = new org.antlr.runtime.BitSet();
		bit.add(OTHER);
		List otherTokens = ((CommonTokenStream)input).getTokens(start.getTokenIndex(), stop.stop.getTokenIndex(), bit);
		
		StringBuffer buffer = new StringBuffer();
		
		for(Object t : otherTokens)
		{
			buffer.append(((Token)t).getText());
		}

		CharStream input = new ANTLRNoCaseStringStream(buffer.toString());
        CFScriptLexer lexer = new CFScriptLexer(input);
        
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CFScriptParser parser = new CFScriptParser(tokens);
        
        try
        {
        	CFScriptParser.script_return root = parser.script();
        	Tree ast = (Tree)root.getTree();
        	return ast;
        }
        catch(RecognitionException exc)
        {
        	ErrorEvent event = new ErrorEvent(exc, "CFScript Error");
        	getObservable().notifyObservers(event);
        }
		
		return null;
	}	
	
	private ErrorObservable getObservable()
	{
		return observable;
	}

	private void setObservable(ErrorObservable observable)
	{
		this.observable = observable;
	}

	private ICFMLDictionary getDictionary()
	{
		return dictionary;
	}

	private void setDictionary(ICFMLDictionary dictionary)
	{
		this.dictionary = dictionary;
	}
}
