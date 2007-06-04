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
	public static final int COMMENT_CHANNEL = 90;
	public static final int TEXT_CHANNEL = 89;
	
	
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
	protected boolean isColdFusionTag(Token tag)
	{		
		return false;
	}

	/**
	* returns false.
	*/
	protected boolean isCustomTag(Token tag)
	{		
		return false;
	}

	/**
	* returns false.
	*/	
	protected boolean isImportTag(Token tag)
	{
		return false;
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
	:
	to=START_TAG_OPEN START_TAG_CLOSE
	tagContent
	-> {isImportTag($to)}? ^(IMPORTTAG[$to] START_TAG_CLOSE tagContent)
	-> {isCustomTag($to)}? ^(CUSTOMTAG[$to] START_TAG_CLOSE tagContent)
	-> {isColdFusionTag($to)}? ^(CFTAG[$to] START_TAG_CLOSE tagContent)
	-> ^(START_TAG_OPEN START_TAG_CLOSE tagContent)
	;
	
tagContent
	:
	tag* (endTag | EOF!)
	;	

endTag
	:
	END_TAG_OPEN^ END_TAG_CLOSE
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
