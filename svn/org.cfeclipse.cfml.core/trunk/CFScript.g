grammar CFScript;

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
	FUNCTION_CALL;
	STRUCT_KEY;
	ELSEIF;
}

/* Parser */

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

script
	:
	(		
		setStatement SEMI_COLON
		|
		returnStatement SEMI_COLON
		|
		ifStatement
	)*
	;
	
setStatement
	:
	(VAR)? cfmlValue (EQUALS codeStatement)?
	;
	
returnStatement
	:
	RETURN^ (codeStatement)?
	;

codeStatement
	:
	OPEN_PAREN codeStatement CLOSE_PAREN
	|
	cfmlBasicStatement
	;

cfmlBasicStatement
	:
	cfmlValueStatement (OPERATOR codeStatement)?
	;


cfmlValueStatement
	:
	(NOT)? cfmlValue
	;

cfmlValue
	:
	(NUMBER | STRING | cfmlLinking)
	;

cfmlLinking
	:
	HASH cfmlBasicLinking HASH
	|
	cfmlBasicLinking
	;

cfmlBasicLinking
	:
	cfmlBasic (DOT cfmlBasic)*
	;

cfmlBasic
	:
	identifier | function
	;

identifier
	:
	IDENTIFIER (struct)?
	;
	
struct
	:
	OPEN_SQUARE codeStatement CLOSE_SQUARE
	-> ^(STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE)
	;

function 
	:
	IDENTIFIER OPEN_PAREN (argumentStatement)? CLOSE_PAREN
	-> ^(FUNCTION_CALL IDENTIFIER OPEN_PAREN (argumentStatement)? CLOSE_PAREN)
	;
	
argumentStatement
	:
	codeStatement (COMMA codeStatement)*
	;

ifStatement
	:
	IF^ OPEN_PAREN codeStatement CLOSE_PAREN
	block
	(elseifStatement)*
	(elseStatement)?
	;
	
elseifStatement
	:
	ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN	
	block
	-> ^(
		ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN 
		block
	    )
	;
	
elseStatement
	:
	ELSE^
	block
	;

block
	:
	OPEN_CURLY script CLOSE_CURLY
	;

/* Lexer */

IF
	:
	'if'
	;

ELSE
	:
	'else'
	;

TRY
	:
	'try'
	;

CATCH
	:
	'catch'
	;
RETURN
	:
	'return'
	;

NOT	:
	'not'
	;

EQUALS
	:
	'='
	;

OPERATOR
	:
	( MATH_OPERATOR | STRING_OPERATOR | BOOLEAN_OPERATOR )
	;

COMMA	:
	','
	;

SEMI_COLON
	:
	';'
	;
	
HASH	:
	'#'
	;

OPEN_PAREN
	:
	'('
	;

CLOSE_PAREN
	:
	')'
	;

OPEN_SQUARE
	:
	'['
	;

CLOSE_SQUARE
	:
	']'
	;

OPEN_CURLY
	:
	'{'
	;

CLOSE_CURLY
	:
	'}'
	;

DOT
	:
	'.'
	;

VAR
	:
	'var'
	;

STRING
	:
	'"' ( options {greedy=false;} : . )* '"' 
	|
	'\'' '"' ( options {greedy=false;} : . )* '\'' 
	;

NUMBER
	:
	DIGIT+ (DOT DIGIT+)
	;

IDENTIFIER
	:
	(LETTER | DIGIT | '_' )+
	;

/* fragments */

fragment MATH_OPERATOR
	:
	('+' | '*' | '\/' | '\\' | '^' | 'mod' | '-')
	;
fragment STRING_OPERATOR
	:
	'&'
	;
fragment BOOLEAN_OPERATOR
	:
	('eq'|'neq'|'is'|'gt'|'lt'|'lte'|'gte')
	;

fragment DIGIT
	:
	'0'..'9'
	;

fragment LETTER
	:
	'a'..'z' | 'A'..'Z'
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
	'/*' ( options {greedy=false;} : . )* '*/' 
	{
		$channel=HIDDEN;
	}
	;

LINE_COMMENT
	: 
	'//' ~('\n'|'\r')* '\r'? '\n' 
	{
		$channel=HIDDEN;
	}
	;
