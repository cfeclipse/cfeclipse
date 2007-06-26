grammar CFML;


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

options
{
	output=AST;
}

tokens
{
	CFTAG;
	CUSTOMTAG;
	IMPORTTAG;
	STRING_LITERAL;
}

scope tagScope {
 String endTagName;
 String name;
}

@parser::header 
{
package org.cfeclipse.cfml.core.parser.antlr;

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
d
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.	
*/	

import java.util.LinkedList;

}

@lexer::header
{
package org.cfeclipse.cfml.core.parser.antlr;

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
}


@lexer::members
{
	private static final int COMMENT_CHANNEL = 90;
	private static final int TEXT_CHANNEL = 89;
	
	
	private static int NONE_MODE = 0;
	private static int ENDTAG_MODE = 1;
	private static int STARTTAG_MODE = 2;

	
	private int mode;
	
	private int getMode()
	{
		return mode;
	}
	
	private void setMode(int mode)
	{
		this.mode = mode;
	}
}

@parser::members
{
	/**
	* returns false.
	*/
	protected boolean isColdFusionTag(String name)
	{		
		return name.toLowerCase().startsWith("cf");
	}

	/**
	* returns false.
	*/
	protected boolean isCustomTag(String name)
	{		
		return false;
	}

	/**
	* returns false.
	*/	
	protected boolean isImportTag(String name)
	{
		return false;
	}

	/*
	* returns false
	*/
	protected boolean containsCFScript(String name)
	{
		return false;
	}
	
	/*
	returns endTagName
	*/
	protected Tree parseCFScript(Token start, Token stop)
	{
		BitSet bit = new BitSet();
		bit.add(OTHER);
		System.out.println(((CommonTokenStream)input).getTokens(start.getTokenIndex(), stop.getTokenIndex(), bit));
		return null;
	}
	
	protected Tree parseStringLiteral(Token start, Token stop)
	{
		System.out.println(((CommonTokenStream)input).getTokens(start.getTokenIndex(), stop.getTokenIndex()));
		return null;
	}
	
	protected boolean allowsCFMLAssignment(String tagName)
	{
		return false;
	}

	protected boolean allowsCFMLCondition(String tagName)
	{
		return false;
	}

	protected boolean usesAttributes(String name)
	{
		return false;
	}	

	/**
	* reports an error
	*/	
	protected void reportError(RecognitionException e, String errorMessage)	
	{
		System.err.println(errorMessage);
	}
	
	private LinkedList<String> tagStack = new LinkedList<String>();
	
	private LinkedList<String> getTagStack()
	{
		return tagStack;
	}
	
	private void setTagStack(LinkedList<String> stack)
	{
		tagStack = stack;
	}
}

/* Parser */

cfml
	:
	tag*
	;

tag
	:
		startTag
	;

startTag
scope tagScope;
	:
	(
	sto=START_TAG_OPEN 
	{
		String name = $sto.text.toLowerCase().substring(1);
		$tagScope::name = name;	
	}
	
	tagInnerValues
	
	stc=START_TAG_CLOSE
	{
		if(!$stc.text.equals("/>"))		
		{
			System.out.println("push: " + name);
			$tagScope::endTagName = name; 
			getTagStack().push(name);
		}
		else
		{
			$tagScope::endTagName = ""; 
			System.out.println("close: " + $sto.text.toLowerCase().substring(1));
		}
	}
	tc=tagContent
		(
		-> {isImportTag(name)}? ^(IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent)
		-> {isCustomTag(name)}? ^(CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent)		
		-> {isColdFusionTag(name)}? ^(CFTAG[$sto] tagInnerValues START_TAG_CLOSE   
						{
							(containsCFScript(name) ? parseCFScript(stc, tc.stop) : null)
						}
						  tagContent)
		
		
		-> ^(START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent)
		)
	)
	;

tagContent
	:
	cfml
		(
		{
			Token t = input.LT(1);
			String name;
			
			if(t.getText() == null)
			{
				name = "*"; //never be a name				
			}
			else
			{
				name = t.getText().toLowerCase().substring(2);
			}
		}
		{ $tagScope::endTagName.equals(name)}?=> 
		(endTag)
		)
	;
catch [FailedPredicateException fpe]
{
	String text = input.LT(1).getText();

	System.out.println("caught: " + input.LT(1).getText());
	retval.stop = input.LT(-1);
	retval.tree = (Object)adaptor.rulePostProcessing(root_0);
	//adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
	
	if(!(text == null || getTagStack().contains(text.toLowerCase().substring(2))))
	{
		//this is a bad error. Norti norti.
		String msg = getErrorHeader(fpe);
    		msg += " end tag (" + text + ">" +
		                 ") cannot be matched to any start tag currently open";
		                 
		reportError(fpe, msg);
	}
}

endTag
	:
	{
		String name = input.LT(1).getText().toLowerCase().substring(2);
		
		//clear off the chaff
		while(!name.equals(getTagStack().peek()))
		{
			String pastTagName = getTagStack().pop();
			
			System.out.println("popped: " + pastTagName);
		}
		
		//pop off the last eleemnt
		String pastTagName = getTagStack().pop();
		System.out.println("finally popped: " + pastTagName);
	}	
	END_TAG_OPEN^ END_TAG_CLOSE
	;

tagInnerValues
	:
	{
		(isColdFusionTag($tagScope::name) && usesAttributes($tagScope::name))
		||
		(isCustomTag($tagScope::name))
		||
		(isImportTag($tagScope::name))
	}?=>
	(
		tagAttribute*
	)
	|	
	;

tagAttribute
	:
	TAG_ATTRIBUTE EQUALS stringLiteral
	;
	
stringLiteral
	:
	(start=DOUBLE_QUOTE doubleQuoteString* end=DOUBLE_QUOTE)
	-> ^(STRING_LITERAL { (parseStringLiteral($start, $end)) })
	|
	(start=SINGLE_QUOTE singleQuoteString* end=SINGLE_QUOTE)
	-> ^($start singleQuoteString* $end)
	;

singleQuoteString
	:	
	( ESCAPE_SINGLE_QUOTE  | ~(SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE ) )
	;

doubleQuoteString
	:	
	( ESCAPE_DOUBLE_QUOTE  | ~(DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE ) )
	;


/* Lexer */

END_TAG_OPEN
	:
	{
		getMode() == NONE_MODE
	}?=>
	{
		setMode(ENDTAG_MODE);
	}	
	'</'TAG_NAME
	;

END_TAG_CLOSE
	:
	{getMode() == ENDTAG_MODE}?=>
	'>'
	{setMode(NONE_MODE);}
	;

START_TAG_OPEN
	:
	{
		getMode() == NONE_MODE
	}?=>
	{
		setMode(STARTTAG_MODE);
	}
	'<'TAG_NAME
	;


START_TAG_CLOSE
	:
	{getMode() == STARTTAG_MODE}?=>
	'/'?'>'
	{setMode(NONE_MODE);}
	;

TAG_ATTRIBUTE
	:
	{getMode() == STARTTAG_MODE}?=>
	(LETTER | DIGIT | UNDERSCORE)+
	;
	
EQUALS
	:
	{getMode() == STARTTAG_MODE}?=>
	'='
	;

ESCAPE_DOUBLE_QUOTE
	:
	{getMode() == STARTTAG_MODE}?=>
	'""'
	;
	
ESCAPE_SINGLE_QUOTE
	:
	{getMode() == STARTTAG_MODE}?=>
	'\'\''
	;

DOUBLE_QUOTE
	:
	{getMode() == STARTTAG_MODE}?=>
	'"'
	;
SINGLE_QUOTE
	:
	{getMode() == STARTTAG_MODE}?=>
	'\''
	;
HASH
	:
	'#'
	;
	
CFML
	:
	'('|')'|'['|']'|'.'
	;	

/* fragments */

fragment TAG_NAME
	:
	(LETTER)(TAG_IDENT)((COLON)(TAG_IDENT))?
	;
	
fragment TAG_IDENT
	:
	(LETTER | DIGIT | UNDERSCORE)*
	;

fragment DIGIT
	:
	'0'..'9'
	;

fragment LETTER
	:
	'a'..'z' | 'A'..'Z'
	;
	
fragment UNDERSCORE
	:
	'_'
	;
	
fragment COLON
	:
	':'
	;

/* hidden tokens */

WS  
	:  
	(' '|'\r'|'\t'|'\u000C'|'\n') 
	{
		$channel=HIDDEN;
	}
	;

COMMENT
	:   
	'<!---' ( options {greedy=false;} : . )* '--->'
	{
			$channel=COMMENT_CHANNEL; //90 is hte comment channel
	}
	;	
	
OTHER
	:
	{getMode() == NONE_MODE}?=>
	(options {greedy=false;} : . )
	{
		$channel=TEXT_CHANNEL; //test is on a seperate channel, in case you want it
	}	
	;
