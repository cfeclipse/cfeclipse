lexer grammar CFML;
@members {
	private static final int COMMENT_CHANNEL = 90;
	private static final int TEXT_CHANNEL = 89;
	
	
	private static int NONE_MODE = 0;
	private static int ENDTAG_MODE = 1;
	private static int STARTTAG_MODE = 2;
	private static int DOUBLE_QUOTE_STRING_MODE = 3;
	private static int SINGLE_QUOTE_STRING_MODE = 4;
	private static int HASH_CFML_MODE = 5;

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
@header {
package org.cfml.parser.antlr;

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

// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 414
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

// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 425
END_TAG_CLOSE
	:
	{getMode() == ENDTAG_MODE}?=>
	'>'
	{setMode(NONE_MODE);}
	;

// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 432
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


// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 444
START_TAG_CLOSE
	:
	{getMode() == STARTTAG_MODE}?=>
	'/'?'>'
	{setMode(NONE_MODE);}
	;

// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 451
TAG_ATTRIBUTE
	:
	{getMode() == STARTTAG_MODE}?=>
	(LETTER | DIGIT | UNDERSCORE)+
	;
	
// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 457
EQUALS
	:
	{getMode() == STARTTAG_MODE}?=>
	'='
	;

// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 463
SINGLE_QUOTE
	:
	{getMode() == STARTTAG_MODE  || getMode() == SINGLE_QUOTE_STRING_MODE}?=>
	'\''
	{
		if(getMode() == STARTTAG_MODE)
		{
			setMode(SINGLE_QUOTE_STRING_MODE);
		}
		else
		{
			setMode(STARTTAG_MODE);
		}
	}
	;
	
// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 479
ESCAPE_SINGLE_QUOTE
	:
	{ getMode() == SINGLE_QUOTE_STRING_MODE }?=>
	'\'\''
	;	

// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 485
SINGLE_QUOTE_STRING
	:
	{ getMode() == SINGLE_QUOTE_STRING_MODE }?=>
	~('\'')
	;

// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 491
DOUBLE_QUOTE
	:
	{getMode() == STARTTAG_MODE  || getMode() == DOUBLE_QUOTE_STRING_MODE}?=>
	'"'
	{
		if(getMode() == STARTTAG_MODE)
		{
			setMode(DOUBLE_QUOTE_STRING_MODE);
		}
		else
		{
			setMode(STARTTAG_MODE);
		}
	}
	;

// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 507
ESCAPE_DOUBLE_QUOTE
	:
	{ getMode() == DOUBLE_QUOTE_STRING_MODE }?=>
	'""'
	;
	
// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 513
DOUBLE_QUOTE_STRING
	:
	{ getMode() == DOUBLE_QUOTE_STRING_MODE }?=>
	~('"')
	;
	
// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 519
HASH
	:
	{getMode() == NONE_MODE  || getMode() == HASH_CFML_MODE}?=>
	'#'
	{
		if(getMode() == NONE_MODE)
		{
			setMode(HASH_CFML_MODE);
		}
		else
		{
			setMode(NONE_MODE);
		}
	}
	;

// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 535
ESCAPE_HASH
	:
	{ getMode() == HASH_CFML_MODE }?=>
	'##'
	;
	
// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 541
HASH_CFML
	:
	{ getMode() == HASH_CFML_MODE }?=>
	~('#')
	;	

// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 547
CFML
	:	
	{getMode() == STARTTAG_MODE}?=>
	('*'|'.'|'+'|'('|')'|'%'|'['|']'|'^'|'&'|'\/'|'\\'|'-'|'#')
	;

/* fragments */

// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 555
fragment TAG_NAME
	:
	(LETTER)(TAG_IDENT)((COLON)(TAG_IDENT))?
	;
	
// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 560
fragment TAG_IDENT
	:
	(LETTER | DIGIT | UNDERSCORE)*
	;

// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 565
fragment DIGIT
	:
	'0'..'9'
	;

// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 570
fragment LETTER
	:
	'a'..'z' | 'A'..'Z'
	;
	
// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 575
fragment UNDERSCORE
	:
	'_'
	;
	
// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 580
fragment COLON
	:
	':'
	;

/* hidden tokens */

// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 587
WS  
	:  
	(' '|'\r'|'\t'|'\u000C'|'\n') 
	{
		$channel=HIDDEN;
	}
	;

// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 595
COMMENT
	:   
	'<!---' ( options {greedy=false;} : . )* '--->'
	{
			$channel=COMMENT_CHANNEL; //90 is hte comment channel
	}
	;	
	
// $ANTLR src "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g" 603
OTHER
	:
	{getMode() == NONE_MODE}?=>
	(options {greedy=false;} : . )
	{
		$channel=TEXT_CHANNEL; //test is on a seperate channel, in case you want it
	}	
	;
