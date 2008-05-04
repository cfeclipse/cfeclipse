lexer grammar CFScript;
@members {
	public static final int COMMENT_CHANNEL = 90;
	
	private static final int CATCH_MODE = 1;
	private static final int NORMAL_MODE = 0;
	
	private int mode = NORMAL_MODE;
	
	private void setMode(int mode)
	{
		this.mode = mode;
	}
	
	private int getMode()
	{
		return this.mode;
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

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 380
FUNCTION
	:
	'function'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 385
IF
	:
	'if'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 390
ELSE
	:
	'else'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 395
TRY
	:
	'try'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 400
CATCH
	:
	'catch'
	{
		setMode(CATCH_MODE);
	}
	;
// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 407
RETURN
	:
	'return'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 412
FOR
	:
	'for'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 417
IN
	:
	'in'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 422
WHILE
	:
	'while'
	;
	
// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 427
DO
	:
	'do'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 432
NOT	:
	'not'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 436
EQUALS
	:
	'='
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 441
SWITCH
	:
	'switch'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 446
CASE
	:
	'case'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 451
DEFAULT
	:
	'default'
	;
	
// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 456
BREAK
	:
	'break'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 461
COLON	:
	':'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 465
OPERATOR
	:
	( MATH_OPERATOR | STRING_OPERATOR | CONDITION_OPERATOR | BOOLEAN_OPERATOR )
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 470
COMMA	:
	','
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 474
SEMI_COLON
	:
	';'
	;
	
// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 479
HASH	:
	'#'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 483
OPEN_PAREN
	:
	'('
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 488
CLOSE_PAREN
	:
	')'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 493
OPEN_SQUARE
	:
	'['
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 498
CLOSE_SQUARE
	:
	']'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 503
OPEN_CURLY
	:
	'{'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 508
CLOSE_CURLY
	:
	'}'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 513
DOT
	:
	'.'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 518
VAR
	:
	'var'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 523
NUMBER
	:
	DIGIT+(DOT DIGIT+)?
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 528
ESCAPE_DOUBLE_QUOTE
	:
	'""'
	;
	
// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 533
ESCAPE_SINGLE_QUOTE
	:
	'\'\''
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 538
DOUBLE_QUOTE
	:
	'"'
	;
// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 542
SINGLE_QUOTE
	:
	'\''
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 547
IDENTIFIER
	:
	(LETTER | UNDERSCORE )(LETTER | DIGIT | UNDERSCORE )*	
	;

/*
EXCEPTIONNAME
	:
	{ getMode() == CATCH_MODE }?
	(LETTER | DIGIT | UNDERSCORE)(DOT | LETTER | DIGIT | UNDERSCORE)*
	{
		setMode(NORMAL_MODE);
	}
	;
*/
/* fragments */

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 564
fragment MATH_OPERATOR
	:
	('+' | '*' | '\/' | '\\' | '^' | 'mod' | '-')
	;
// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 568
fragment STRING_OPERATOR
	:
	'&'
	;
// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 572
fragment CONDITION_OPERATOR
	:
	('eq'|'neq'|'is'|'gt'|'lt'|'lte'|'gte')
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 577
fragment BOOLEAN_OPERATOR
	:
	('or'|'and'|'xor'|'eqv'|'imp')
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 582
fragment UNDERSCORE
	:
	'_'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 587
fragment DIGIT
	:
	'0'..'9'
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 592
fragment LETTER
	:
	'a'..'z' | 'A'..'Z'
	;

/* hidden tokens */

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 599
WS  
	:  
	(' '|'\r'|'\t'|'\u000C'|'\n') 
	{
		$channel=HIDDEN;
	}
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 607
COMMENT
	:   
	'/*' ( options {greedy=false;} : . )* '*/' 
	{
		$channel=COMMENT_CHANNEL; //90 is the comment channel
	}
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 615
LINE_COMMENT
	: 
	'//' ~('\n'|'\r')* '\r'? '\n' 
	{
		$channel=COMMENT_CHANNEL; //90 is the comment channel
	}
	;

// $ANTLR src "./src/org/cfml/parser/antlr/CFScript.g" 623
OTHER
	:
	(options {greedy=false;} : . )
	;
