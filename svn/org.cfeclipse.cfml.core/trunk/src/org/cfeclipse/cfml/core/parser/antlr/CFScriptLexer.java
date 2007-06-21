// $ANTLR 3.0 ./CFScript.g 2007-06-21 18:44:29

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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class CFScriptLexer extends Lexer {
    public static final int FUNCTION=29;
    public static final int WHILE=37;
    public static final int CLOSE_CURLY=40;
    public static final int LETTER=51;
    public static final int DOUBLE_QUOTE=21;
    public static final int CASE=42;
    public static final int DO=38;
    public static final int FOR=35;
    public static final int CONDITION_OPERATOR=48;
    public static final int EQUALS=12;
    public static final int NOT=17;
    public static final int EOF=-1;
    public static final int BREAK=45;
    public static final int IF=30;
    public static final int SINGLE_QUOTE=23;
    public static final int IN=36;
    public static final int COMMA=28;
    public static final int IDENTIFIER=25;
    public static final int RETURN=15;
    public static final int OPEN_SQUARE=26;
    public static final int ESCAPE_DOUBLE_QUOTE=22;
    public static final int VAR=11;
    public static final int MATH_OPERATOR=46;
    public static final int CLOSE_PAREN=14;
    public static final int DIGIT=50;
    public static final int COMMENT=54;
    public static final int DOT=20;
    public static final int CLOSE_SQUARE=27;
    public static final int STRUCT_KEY=6;
    public static final int LINE_COMMENT=55;
    public static final int STRING_CFML=8;
    public static final int OPERATOR=16;
    public static final int SWITCH=41;
    public static final int DEFAULT=44;
    public static final int ELSE=31;
    public static final int NUMBER=18;
    public static final int HASH=19;
    public static final int OPEN_PAREN=13;
    public static final int UNDERSCORE=52;
    public static final int SEMI_COLON=10;
    public static final int OPEN_CURLY=39;
    public static final int Tokens=56;
    public static final int TRY=32;
    public static final int ESCAPE_SINGLE_QUOTE=24;
    public static final int ELSEIF=7;
    public static final int COLON=43;
    public static final int EXCEPTIONNAME=33;
    public static final int WS=53;
    public static final int BOOLEAN_OPERATOR=49;
    public static final int CATCH=34;
    public static final int FUNCTION_DECLARATION=5;
    public static final int FUNCTION_CALL=4;
    public static final int STRING_OPERATOR=47;
    public static final int STRING=9;

    	public static final int COMMENT_CHANNEL = 90;

    public CFScriptLexer() {;} 
    public CFScriptLexer(CharStream input) {
        super(input);
    }
    public String getGrammarFileName() { return "./CFScript.g"; }

    // $ANTLR start FUNCTION
    public final void mFUNCTION() throws RecognitionException {
    traceIn("FUNCTION", 1);
        try {
            int _type = FUNCTION;
            // ./CFScript.g:370:2: ( 'function' )
            // ./CFScript.g:370:2: 'function'
            {
            match("function"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("FUNCTION", 1);
        }
    }
    // $ANTLR end FUNCTION

    // $ANTLR start IF
    public final void mIF() throws RecognitionException {
    traceIn("IF", 2);
        try {
            int _type = IF;
            // ./CFScript.g:375:2: ( 'if' )
            // ./CFScript.g:375:2: 'if'
            {
            match("if"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("IF", 2);
        }
    }
    // $ANTLR end IF

    // $ANTLR start ELSE
    public final void mELSE() throws RecognitionException {
    traceIn("ELSE", 3);
        try {
            int _type = ELSE;
            // ./CFScript.g:380:2: ( 'else' )
            // ./CFScript.g:380:2: 'else'
            {
            match("else"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("ELSE", 3);
        }
    }
    // $ANTLR end ELSE

    // $ANTLR start TRY
    public final void mTRY() throws RecognitionException {
    traceIn("TRY", 4);
        try {
            int _type = TRY;
            // ./CFScript.g:385:2: ( 'try' )
            // ./CFScript.g:385:2: 'try'
            {
            match("try"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("TRY", 4);
        }
    }
    // $ANTLR end TRY

    // $ANTLR start CATCH
    public final void mCATCH() throws RecognitionException {
    traceIn("CATCH", 5);
        try {
            int _type = CATCH;
            // ./CFScript.g:390:2: ( 'catch' )
            // ./CFScript.g:390:2: 'catch'
            {
            match("catch"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("CATCH", 5);
        }
    }
    // $ANTLR end CATCH

    // $ANTLR start RETURN
    public final void mRETURN() throws RecognitionException {
    traceIn("RETURN", 6);
        try {
            int _type = RETURN;
            // ./CFScript.g:394:2: ( 'return' )
            // ./CFScript.g:394:2: 'return'
            {
            match("return"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("RETURN", 6);
        }
    }
    // $ANTLR end RETURN

    // $ANTLR start FOR
    public final void mFOR() throws RecognitionException {
    traceIn("FOR", 7);
        try {
            int _type = FOR;
            // ./CFScript.g:399:2: ( 'for' )
            // ./CFScript.g:399:2: 'for'
            {
            match("for"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("FOR", 7);
        }
    }
    // $ANTLR end FOR

    // $ANTLR start IN
    public final void mIN() throws RecognitionException {
    traceIn("IN", 8);
        try {
            int _type = IN;
            // ./CFScript.g:404:2: ( 'in' )
            // ./CFScript.g:404:2: 'in'
            {
            match("in"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("IN", 8);
        }
    }
    // $ANTLR end IN

    // $ANTLR start WHILE
    public final void mWHILE() throws RecognitionException {
    traceIn("WHILE", 9);
        try {
            int _type = WHILE;
            // ./CFScript.g:409:2: ( 'while' )
            // ./CFScript.g:409:2: 'while'
            {
            match("while"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("WHILE", 9);
        }
    }
    // $ANTLR end WHILE

    // $ANTLR start DO
    public final void mDO() throws RecognitionException {
    traceIn("DO", 10);
        try {
            int _type = DO;
            // ./CFScript.g:414:2: ( 'do' )
            // ./CFScript.g:414:2: 'do'
            {
            match("do"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("DO", 10);
        }
    }
    // $ANTLR end DO

    // $ANTLR start NOT
    public final void mNOT() throws RecognitionException {
    traceIn("NOT", 11);
        try {
            int _type = NOT;
            // ./CFScript.g:418:2: ( 'not' )
            // ./CFScript.g:418:2: 'not'
            {
            match("not"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("NOT", 11);
        }
    }
    // $ANTLR end NOT

    // $ANTLR start EQUALS
    public final void mEQUALS() throws RecognitionException {
    traceIn("EQUALS", 12);
        try {
            int _type = EQUALS;
            // ./CFScript.g:423:2: ( '=' )
            // ./CFScript.g:423:2: '='
            {
            match('='); 

            }

            this.type = _type;
        }
        finally {
    traceOut("EQUALS", 12);
        }
    }
    // $ANTLR end EQUALS

    // $ANTLR start SWITCH
    public final void mSWITCH() throws RecognitionException {
    traceIn("SWITCH", 13);
        try {
            int _type = SWITCH;
            // ./CFScript.g:428:2: ( 'switch' )
            // ./CFScript.g:428:2: 'switch'
            {
            match("switch"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("SWITCH", 13);
        }
    }
    // $ANTLR end SWITCH

    // $ANTLR start CASE
    public final void mCASE() throws RecognitionException {
    traceIn("CASE", 14);
        try {
            int _type = CASE;
            // ./CFScript.g:433:2: ( 'case' )
            // ./CFScript.g:433:2: 'case'
            {
            match("case"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("CASE", 14);
        }
    }
    // $ANTLR end CASE

    // $ANTLR start DEFAULT
    public final void mDEFAULT() throws RecognitionException {
    traceIn("DEFAULT", 15);
        try {
            int _type = DEFAULT;
            // ./CFScript.g:438:2: ( 'default' )
            // ./CFScript.g:438:2: 'default'
            {
            match("default"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("DEFAULT", 15);
        }
    }
    // $ANTLR end DEFAULT

    // $ANTLR start BREAK
    public final void mBREAK() throws RecognitionException {
    traceIn("BREAK", 16);
        try {
            int _type = BREAK;
            // ./CFScript.g:443:2: ( 'break' )
            // ./CFScript.g:443:2: 'break'
            {
            match("break"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("BREAK", 16);
        }
    }
    // $ANTLR end BREAK

    // $ANTLR start COLON
    public final void mCOLON() throws RecognitionException {
    traceIn("COLON", 17);
        try {
            int _type = COLON;
            // ./CFScript.g:447:2: ( ':' )
            // ./CFScript.g:447:2: ':'
            {
            match(':'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("COLON", 17);
        }
    }
    // $ANTLR end COLON

    // $ANTLR start OPERATOR
    public final void mOPERATOR() throws RecognitionException {
    traceIn("OPERATOR", 18);
        try {
            int _type = OPERATOR;
            // ./CFScript.g:452:2: ( ( MATH_OPERATOR | STRING_OPERATOR | CONDITION_OPERATOR | BOOLEAN_OPERATOR ) )
            // ./CFScript.g:452:2: ( MATH_OPERATOR | STRING_OPERATOR | CONDITION_OPERATOR | BOOLEAN_OPERATOR )
            {
            // ./CFScript.g:452:2: ( MATH_OPERATOR | STRING_OPERATOR | CONDITION_OPERATOR | BOOLEAN_OPERATOR )
            int alt1=4;
            switch ( input.LA(1) ) {
            case '*':
            case '+':
            case '-':
            case '/':
            case '\\':
            case '^':
            case 'm':
                {
                alt1=1;
                }
                break;
            case '&':
                {
                alt1=2;
                }
                break;
            case 'e':
                {
                int LA1_3 = input.LA(2);

                if ( (LA1_3=='q') ) {
                    int LA1_7 = input.LA(3);

                    if ( (LA1_7=='v') ) {
                        alt1=4;
                    }
                    else {
                        alt1=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("452:2: ( MATH_OPERATOR | STRING_OPERATOR | CONDITION_OPERATOR | BOOLEAN_OPERATOR )", 1, 3, input);

                    throw nvae;
                }
                }
                break;
            case 'g':
            case 'l':
            case 'n':
                {
                alt1=3;
                }
                break;
            case 'i':
                {
                int LA1_5 = input.LA(2);

                if ( (LA1_5=='m') ) {
                    alt1=4;
                }
                else if ( (LA1_5=='s') ) {
                    alt1=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("452:2: ( MATH_OPERATOR | STRING_OPERATOR | CONDITION_OPERATOR | BOOLEAN_OPERATOR )", 1, 5, input);

                    throw nvae;
                }
                }
                break;
            case 'a':
            case 'o':
            case 'x':
                {
                alt1=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("452:2: ( MATH_OPERATOR | STRING_OPERATOR | CONDITION_OPERATOR | BOOLEAN_OPERATOR )", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ./CFScript.g:452:4: MATH_OPERATOR
                    {
                    mMATH_OPERATOR(); 

                    }
                    break;
                case 2 :
                    // ./CFScript.g:452:20: STRING_OPERATOR
                    {
                    mSTRING_OPERATOR(); 

                    }
                    break;
                case 3 :
                    // ./CFScript.g:452:38: CONDITION_OPERATOR
                    {
                    mCONDITION_OPERATOR(); 

                    }
                    break;
                case 4 :
                    // ./CFScript.g:452:59: BOOLEAN_OPERATOR
                    {
                    mBOOLEAN_OPERATOR(); 

                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
    traceOut("OPERATOR", 18);
        }
    }
    // $ANTLR end OPERATOR

    // $ANTLR start COMMA
    public final void mCOMMA() throws RecognitionException {
    traceIn("COMMA", 19);
        try {
            int _type = COMMA;
            // ./CFScript.g:456:2: ( ',' )
            // ./CFScript.g:456:2: ','
            {
            match(','); 

            }

            this.type = _type;
        }
        finally {
    traceOut("COMMA", 19);
        }
    }
    // $ANTLR end COMMA

    // $ANTLR start SEMI_COLON
    public final void mSEMI_COLON() throws RecognitionException {
    traceIn("SEMI_COLON", 20);
        try {
            int _type = SEMI_COLON;
            // ./CFScript.g:461:2: ( ';' )
            // ./CFScript.g:461:2: ';'
            {
            match(';'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("SEMI_COLON", 20);
        }
    }
    // $ANTLR end SEMI_COLON

    // $ANTLR start HASH
    public final void mHASH() throws RecognitionException {
    traceIn("HASH", 21);
        try {
            int _type = HASH;
            // ./CFScript.g:465:2: ( '#' )
            // ./CFScript.g:465:2: '#'
            {
            match('#'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("HASH", 21);
        }
    }
    // $ANTLR end HASH

    // $ANTLR start OPEN_PAREN
    public final void mOPEN_PAREN() throws RecognitionException {
    traceIn("OPEN_PAREN", 22);
        try {
            int _type = OPEN_PAREN;
            // ./CFScript.g:470:2: ( '(' )
            // ./CFScript.g:470:2: '('
            {
            match('('); 

            }

            this.type = _type;
        }
        finally {
    traceOut("OPEN_PAREN", 22);
        }
    }
    // $ANTLR end OPEN_PAREN

    // $ANTLR start CLOSE_PAREN
    public final void mCLOSE_PAREN() throws RecognitionException {
    traceIn("CLOSE_PAREN", 23);
        try {
            int _type = CLOSE_PAREN;
            // ./CFScript.g:475:2: ( ')' )
            // ./CFScript.g:475:2: ')'
            {
            match(')'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("CLOSE_PAREN", 23);
        }
    }
    // $ANTLR end CLOSE_PAREN

    // $ANTLR start OPEN_SQUARE
    public final void mOPEN_SQUARE() throws RecognitionException {
    traceIn("OPEN_SQUARE", 24);
        try {
            int _type = OPEN_SQUARE;
            // ./CFScript.g:480:2: ( '[' )
            // ./CFScript.g:480:2: '['
            {
            match('['); 

            }

            this.type = _type;
        }
        finally {
    traceOut("OPEN_SQUARE", 24);
        }
    }
    // $ANTLR end OPEN_SQUARE

    // $ANTLR start CLOSE_SQUARE
    public final void mCLOSE_SQUARE() throws RecognitionException {
    traceIn("CLOSE_SQUARE", 25);
        try {
            int _type = CLOSE_SQUARE;
            // ./CFScript.g:485:2: ( ']' )
            // ./CFScript.g:485:2: ']'
            {
            match(']'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("CLOSE_SQUARE", 25);
        }
    }
    // $ANTLR end CLOSE_SQUARE

    // $ANTLR start OPEN_CURLY
    public final void mOPEN_CURLY() throws RecognitionException {
    traceIn("OPEN_CURLY", 26);
        try {
            int _type = OPEN_CURLY;
            // ./CFScript.g:490:2: ( '{' )
            // ./CFScript.g:490:2: '{'
            {
            match('{'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("OPEN_CURLY", 26);
        }
    }
    // $ANTLR end OPEN_CURLY

    // $ANTLR start CLOSE_CURLY
    public final void mCLOSE_CURLY() throws RecognitionException {
    traceIn("CLOSE_CURLY", 27);
        try {
            int _type = CLOSE_CURLY;
            // ./CFScript.g:495:2: ( '}' )
            // ./CFScript.g:495:2: '}'
            {
            match('}'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("CLOSE_CURLY", 27);
        }
    }
    // $ANTLR end CLOSE_CURLY

    // $ANTLR start DOT
    public final void mDOT() throws RecognitionException {
    traceIn("DOT", 28);
        try {
            int _type = DOT;
            // ./CFScript.g:500:2: ( '.' )
            // ./CFScript.g:500:2: '.'
            {
            match('.'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("DOT", 28);
        }
    }
    // $ANTLR end DOT

    // $ANTLR start VAR
    public final void mVAR() throws RecognitionException {
    traceIn("VAR", 29);
        try {
            int _type = VAR;
            // ./CFScript.g:505:2: ( 'var' )
            // ./CFScript.g:505:2: 'var'
            {
            match("var"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("VAR", 29);
        }
    }
    // $ANTLR end VAR

    // $ANTLR start NUMBER
    public final void mNUMBER() throws RecognitionException {
    traceIn("NUMBER", 30);
        try {
            int _type = NUMBER;
            // ./CFScript.g:510:2: ( ( DIGIT )+ ( DOT ( DIGIT )+ )? )
            // ./CFScript.g:510:2: ( DIGIT )+ ( DOT ( DIGIT )+ )?
            {
            // ./CFScript.g:510:2: ( DIGIT )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ./CFScript.g:510:2: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);

            // ./CFScript.g:510:8: ( DOT ( DIGIT )+ )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='.') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ./CFScript.g:510:9: DOT ( DIGIT )+
                    {
                    mDOT(); 
                    // ./CFScript.g:510:13: ( DIGIT )+
                    int cnt3=0;
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // ./CFScript.g:510:13: DIGIT
                    	    {
                    	    mDIGIT(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt3 >= 1 ) break loop3;
                                EarlyExitException eee =
                                    new EarlyExitException(3, input);
                                throw eee;
                        }
                        cnt3++;
                    } while (true);


                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
    traceOut("NUMBER", 30);
        }
    }
    // $ANTLR end NUMBER

    // $ANTLR start ESCAPE_DOUBLE_QUOTE
    public final void mESCAPE_DOUBLE_QUOTE() throws RecognitionException {
    traceIn("ESCAPE_DOUBLE_QUOTE", 31);
        try {
            int _type = ESCAPE_DOUBLE_QUOTE;
            // ./CFScript.g:515:2: ( '\"\"' )
            // ./CFScript.g:515:2: '\"\"'
            {
            match("\"\""); 


            }

            this.type = _type;
        }
        finally {
    traceOut("ESCAPE_DOUBLE_QUOTE", 31);
        }
    }
    // $ANTLR end ESCAPE_DOUBLE_QUOTE

    // $ANTLR start ESCAPE_SINGLE_QUOTE
    public final void mESCAPE_SINGLE_QUOTE() throws RecognitionException {
    traceIn("ESCAPE_SINGLE_QUOTE", 32);
        try {
            int _type = ESCAPE_SINGLE_QUOTE;
            // ./CFScript.g:520:2: ( '\\'\\'' )
            // ./CFScript.g:520:2: '\\'\\''
            {
            match("\'\'"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("ESCAPE_SINGLE_QUOTE", 32);
        }
    }
    // $ANTLR end ESCAPE_SINGLE_QUOTE

    // $ANTLR start DOUBLE_QUOTE
    public final void mDOUBLE_QUOTE() throws RecognitionException {
    traceIn("DOUBLE_QUOTE", 33);
        try {
            int _type = DOUBLE_QUOTE;
            // ./CFScript.g:525:2: ( '\"' )
            // ./CFScript.g:525:2: '\"'
            {
            match('\"'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("DOUBLE_QUOTE", 33);
        }
    }
    // $ANTLR end DOUBLE_QUOTE

    // $ANTLR start SINGLE_QUOTE
    public final void mSINGLE_QUOTE() throws RecognitionException {
    traceIn("SINGLE_QUOTE", 34);
        try {
            int _type = SINGLE_QUOTE;
            // ./CFScript.g:529:2: ( '\\'' )
            // ./CFScript.g:529:2: '\\''
            {
            match('\''); 

            }

            this.type = _type;
        }
        finally {
    traceOut("SINGLE_QUOTE", 34);
        }
    }
    // $ANTLR end SINGLE_QUOTE

    // $ANTLR start IDENTIFIER
    public final void mIDENTIFIER() throws RecognitionException {
    traceIn("IDENTIFIER", 35);
        try {
            int _type = IDENTIFIER;
            // ./CFScript.g:534:2: ( ( LETTER | UNDERSCORE ) ( LETTER | DIGIT | UNDERSCORE )* )
            // ./CFScript.g:534:2: ( LETTER | UNDERSCORE ) ( LETTER | DIGIT | UNDERSCORE )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

            // ./CFScript.g:534:24: ( LETTER | DIGIT | UNDERSCORE )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')||(LA5_0>='A' && LA5_0<='Z')||LA5_0=='_'||(LA5_0>='a' && LA5_0<='z')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ./CFScript.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            this.type = _type;
        }
        finally {
    traceOut("IDENTIFIER", 35);
        }
    }
    // $ANTLR end IDENTIFIER

    // $ANTLR start EXCEPTIONNAME
    public final void mEXCEPTIONNAME() throws RecognitionException {
    traceIn("EXCEPTIONNAME", 36);
        try {
            int _type = EXCEPTIONNAME;
            // ./CFScript.g:539:2: ( ( LETTER | DIGIT | UNDERSCORE ) ( DOT | LETTER | DIGIT | UNDERSCORE )* )
            // ./CFScript.g:539:2: ( LETTER | DIGIT | UNDERSCORE ) ( DOT | LETTER | DIGIT | UNDERSCORE )*
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

            // ./CFScript.g:539:31: ( DOT | LETTER | DIGIT | UNDERSCORE )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='.'||(LA6_0>='0' && LA6_0<='9')||(LA6_0>='A' && LA6_0<='Z')||LA6_0=='_'||(LA6_0>='a' && LA6_0<='z')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // ./CFScript.g:
            	    {
            	    if ( input.LA(1)=='.'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

            this.type = _type;
        }
        finally {
    traceOut("EXCEPTIONNAME", 36);
        }
    }
    // $ANTLR end EXCEPTIONNAME

    // $ANTLR start MATH_OPERATOR
    public final void mMATH_OPERATOR() throws RecognitionException {
    traceIn("MATH_OPERATOR", 37);
        try {
            // ./CFScript.g:546:2: ( ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' ) )
            // ./CFScript.g:546:2: ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' )
            {
            // ./CFScript.g:546:2: ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' )
            int alt7=7;
            switch ( input.LA(1) ) {
            case '+':
                {
                alt7=1;
                }
                break;
            case '*':
                {
                alt7=2;
                }
                break;
            case '/':
                {
                alt7=3;
                }
                break;
            case '\\':
                {
                alt7=4;
                }
                break;
            case '^':
                {
                alt7=5;
                }
                break;
            case 'm':
                {
                alt7=6;
                }
                break;
            case '-':
                {
                alt7=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("546:2: ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' )", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // ./CFScript.g:546:3: '+'
                    {
                    match('+'); 

                    }
                    break;
                case 2 :
                    // ./CFScript.g:546:9: '*'
                    {
                    match('*'); 

                    }
                    break;
                case 3 :
                    // ./CFScript.g:546:15: '\\/'
                    {
                    match('/'); 

                    }
                    break;
                case 4 :
                    // ./CFScript.g:546:22: '\\\\'
                    {
                    match('\\'); 

                    }
                    break;
                case 5 :
                    // ./CFScript.g:546:29: '^'
                    {
                    match('^'); 

                    }
                    break;
                case 6 :
                    // ./CFScript.g:546:35: 'mod'
                    {
                    match("mod"); 


                    }
                    break;
                case 7 :
                    // ./CFScript.g:546:43: '-'
                    {
                    match('-'); 

                    }
                    break;

            }


            }

        }
        finally {
    traceOut("MATH_OPERATOR", 37);
        }
    }
    // $ANTLR end MATH_OPERATOR

    // $ANTLR start STRING_OPERATOR
    public final void mSTRING_OPERATOR() throws RecognitionException {
    traceIn("STRING_OPERATOR", 38);
        try {
            // ./CFScript.g:550:2: ( '&' )
            // ./CFScript.g:550:2: '&'
            {
            match('&'); 

            }

        }
        finally {
    traceOut("STRING_OPERATOR", 38);
        }
    }
    // $ANTLR end STRING_OPERATOR

    // $ANTLR start CONDITION_OPERATOR
    public final void mCONDITION_OPERATOR() throws RecognitionException {
    traceIn("CONDITION_OPERATOR", 39);
        try {
            // ./CFScript.g:554:2: ( ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' ) )
            // ./CFScript.g:554:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )
            {
            // ./CFScript.g:554:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )
            int alt8=7;
            switch ( input.LA(1) ) {
            case 'e':
                {
                alt8=1;
                }
                break;
            case 'n':
                {
                alt8=2;
                }
                break;
            case 'i':
                {
                alt8=3;
                }
                break;
            case 'g':
                {
                int LA8_4 = input.LA(2);

                if ( (LA8_4=='t') ) {
                    int LA8_6 = input.LA(3);

                    if ( (LA8_6=='e') ) {
                        alt8=7;
                    }
                    else {
                        alt8=4;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("554:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )", 8, 4, input);

                    throw nvae;
                }
                }
                break;
            case 'l':
                {
                int LA8_5 = input.LA(2);

                if ( (LA8_5=='t') ) {
                    int LA8_7 = input.LA(3);

                    if ( (LA8_7=='e') ) {
                        alt8=6;
                    }
                    else {
                        alt8=5;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("554:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )", 8, 5, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("554:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // ./CFScript.g:554:3: 'eq'
                    {
                    match("eq"); 


                    }
                    break;
                case 2 :
                    // ./CFScript.g:554:8: 'neq'
                    {
                    match("neq"); 


                    }
                    break;
                case 3 :
                    // ./CFScript.g:554:14: 'is'
                    {
                    match("is"); 


                    }
                    break;
                case 4 :
                    // ./CFScript.g:554:19: 'gt'
                    {
                    match("gt"); 


                    }
                    break;
                case 5 :
                    // ./CFScript.g:554:24: 'lt'
                    {
                    match("lt"); 


                    }
                    break;
                case 6 :
                    // ./CFScript.g:554:29: 'lte'
                    {
                    match("lte"); 


                    }
                    break;
                case 7 :
                    // ./CFScript.g:554:35: 'gte'
                    {
                    match("gte"); 


                    }
                    break;

            }


            }

        }
        finally {
    traceOut("CONDITION_OPERATOR", 39);
        }
    }
    // $ANTLR end CONDITION_OPERATOR

    // $ANTLR start BOOLEAN_OPERATOR
    public final void mBOOLEAN_OPERATOR() throws RecognitionException {
    traceIn("BOOLEAN_OPERATOR", 40);
        try {
            // ./CFScript.g:559:2: ( ( 'or' | 'and' | 'xor' | 'eqv' | 'imp' ) )
            // ./CFScript.g:559:2: ( 'or' | 'and' | 'xor' | 'eqv' | 'imp' )
            {
            // ./CFScript.g:559:2: ( 'or' | 'and' | 'xor' | 'eqv' | 'imp' )
            int alt9=5;
            switch ( input.LA(1) ) {
            case 'o':
                {
                alt9=1;
                }
                break;
            case 'a':
                {
                alt9=2;
                }
                break;
            case 'x':
                {
                alt9=3;
                }
                break;
            case 'e':
                {
                alt9=4;
                }
                break;
            case 'i':
                {
                alt9=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("559:2: ( 'or' | 'and' | 'xor' | 'eqv' | 'imp' )", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // ./CFScript.g:559:3: 'or'
                    {
                    match("or"); 


                    }
                    break;
                case 2 :
                    // ./CFScript.g:559:8: 'and'
                    {
                    match("and"); 


                    }
                    break;
                case 3 :
                    // ./CFScript.g:559:14: 'xor'
                    {
                    match("xor"); 


                    }
                    break;
                case 4 :
                    // ./CFScript.g:559:20: 'eqv'
                    {
                    match("eqv"); 


                    }
                    break;
                case 5 :
                    // ./CFScript.g:559:26: 'imp'
                    {
                    match("imp"); 


                    }
                    break;

            }


            }

        }
        finally {
    traceOut("BOOLEAN_OPERATOR", 40);
        }
    }
    // $ANTLR end BOOLEAN_OPERATOR

    // $ANTLR start UNDERSCORE
    public final void mUNDERSCORE() throws RecognitionException {
    traceIn("UNDERSCORE", 41);
        try {
            // ./CFScript.g:564:2: ( '_' )
            // ./CFScript.g:564:2: '_'
            {
            match('_'); 

            }

        }
        finally {
    traceOut("UNDERSCORE", 41);
        }
    }
    // $ANTLR end UNDERSCORE

    // $ANTLR start DIGIT
    public final void mDIGIT() throws RecognitionException {
    traceIn("DIGIT", 42);
        try {
            // ./CFScript.g:569:2: ( '0' .. '9' )
            // ./CFScript.g:569:2: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
    traceOut("DIGIT", 42);
        }
    }
    // $ANTLR end DIGIT

    // $ANTLR start LETTER
    public final void mLETTER() throws RecognitionException {
    traceIn("LETTER", 43);
        try {
            // ./CFScript.g:574:2: ( 'a' .. 'z' | 'A' .. 'Z' )
            // ./CFScript.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

        }
        finally {
    traceOut("LETTER", 43);
        }
    }
    // $ANTLR end LETTER

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
    traceIn("WS", 44);
        try {
            int _type = WS;
            // ./CFScript.g:581:2: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // ./CFScript.g:581:2: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            		channel=HIDDEN;
            	

            }

            this.type = _type;
        }
        finally {
    traceOut("WS", 44);
        }
    }
    // $ANTLR end WS

    // $ANTLR start COMMENT
    public final void mCOMMENT() throws RecognitionException {
    traceIn("COMMENT", 45);
        try {
            int _type = COMMENT;
            // ./CFScript.g:589:2: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ./CFScript.g:589:2: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // ./CFScript.g:589:7: ( options {greedy=false; } : . )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0=='*') ) {
                    int LA10_1 = input.LA(2);

                    if ( (LA10_1=='/') ) {
                        alt10=2;
                    }
                    else if ( ((LA10_1>='\u0000' && LA10_1<='.')||(LA10_1>='0' && LA10_1<='\uFFFE')) ) {
                        alt10=1;
                    }


                }
                else if ( ((LA10_0>='\u0000' && LA10_0<=')')||(LA10_0>='+' && LA10_0<='\uFFFE')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ./CFScript.g:589:35: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            match("*/"); 


            		channel=COMMENT_CHANNEL; //90 is the comment channel
            	

            }

            this.type = _type;
        }
        finally {
    traceOut("COMMENT", 45);
        }
    }
    // $ANTLR end COMMENT

    // $ANTLR start LINE_COMMENT
    public final void mLINE_COMMENT() throws RecognitionException {
    traceIn("LINE_COMMENT", 46);
        try {
            int _type = LINE_COMMENT;
            // ./CFScript.g:597:2: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // ./CFScript.g:597:2: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // ./CFScript.g:597:7: (~ ( '\\n' | '\\r' ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='\u0000' && LA11_0<='\t')||(LA11_0>='\u000B' && LA11_0<='\f')||(LA11_0>='\u000E' && LA11_0<='\uFFFE')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ./CFScript.g:597:7: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFE') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            // ./CFScript.g:597:21: ( '\\r' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='\r') ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ./CFScript.g:597:21: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 

            		channel=COMMENT_CHANNEL; //90 is the comment channel
            	

            }

            this.type = _type;
        }
        finally {
    traceOut("LINE_COMMENT", 46);
        }
    }
    // $ANTLR end LINE_COMMENT

    public void mTokens() throws RecognitionException {
        // ./CFScript.g:1:10: ( FUNCTION | IF | ELSE | TRY | CATCH | RETURN | FOR | IN | WHILE | DO | NOT | EQUALS | SWITCH | CASE | DEFAULT | BREAK | COLON | OPERATOR | COMMA | SEMI_COLON | HASH | OPEN_PAREN | CLOSE_PAREN | OPEN_SQUARE | CLOSE_SQUARE | OPEN_CURLY | CLOSE_CURLY | DOT | VAR | NUMBER | ESCAPE_DOUBLE_QUOTE | ESCAPE_SINGLE_QUOTE | DOUBLE_QUOTE | SINGLE_QUOTE | IDENTIFIER | EXCEPTIONNAME | WS | COMMENT | LINE_COMMENT )
        int alt13=39;
        alt13 = dfa13.predict(input);
        switch (alt13) {
            case 1 :
                // ./CFScript.g:1:10: FUNCTION
                {
                mFUNCTION(); 

                }
                break;
            case 2 :
                // ./CFScript.g:1:19: IF
                {
                mIF(); 

                }
                break;
            case 3 :
                // ./CFScript.g:1:22: ELSE
                {
                mELSE(); 

                }
                break;
            case 4 :
                // ./CFScript.g:1:27: TRY
                {
                mTRY(); 

                }
                break;
            case 5 :
                // ./CFScript.g:1:31: CATCH
                {
                mCATCH(); 

                }
                break;
            case 6 :
                // ./CFScript.g:1:37: RETURN
                {
                mRETURN(); 

                }
                break;
            case 7 :
                // ./CFScript.g:1:44: FOR
                {
                mFOR(); 

                }
                break;
            case 8 :
                // ./CFScript.g:1:48: IN
                {
                mIN(); 

                }
                break;
            case 9 :
                // ./CFScript.g:1:51: WHILE
                {
                mWHILE(); 

                }
                break;
            case 10 :
                // ./CFScript.g:1:57: DO
                {
                mDO(); 

                }
                break;
            case 11 :
                // ./CFScript.g:1:60: NOT
                {
                mNOT(); 

                }
                break;
            case 12 :
                // ./CFScript.g:1:64: EQUALS
                {
                mEQUALS(); 

                }
                break;
            case 13 :
                // ./CFScript.g:1:71: SWITCH
                {
                mSWITCH(); 

                }
                break;
            case 14 :
                // ./CFScript.g:1:78: CASE
                {
                mCASE(); 

                }
                break;
            case 15 :
                // ./CFScript.g:1:83: DEFAULT
                {
                mDEFAULT(); 

                }
                break;
            case 16 :
                // ./CFScript.g:1:91: BREAK
                {
                mBREAK(); 

                }
                break;
            case 17 :
                // ./CFScript.g:1:97: COLON
                {
                mCOLON(); 

                }
                break;
            case 18 :
                // ./CFScript.g:1:103: OPERATOR
                {
                mOPERATOR(); 

                }
                break;
            case 19 :
                // ./CFScript.g:1:112: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 20 :
                // ./CFScript.g:1:118: SEMI_COLON
                {
                mSEMI_COLON(); 

                }
                break;
            case 21 :
                // ./CFScript.g:1:129: HASH
                {
                mHASH(); 

                }
                break;
            case 22 :
                // ./CFScript.g:1:134: OPEN_PAREN
                {
                mOPEN_PAREN(); 

                }
                break;
            case 23 :
                // ./CFScript.g:1:145: CLOSE_PAREN
                {
                mCLOSE_PAREN(); 

                }
                break;
            case 24 :
                // ./CFScript.g:1:157: OPEN_SQUARE
                {
                mOPEN_SQUARE(); 

                }
                break;
            case 25 :
                // ./CFScript.g:1:169: CLOSE_SQUARE
                {
                mCLOSE_SQUARE(); 

                }
                break;
            case 26 :
                // ./CFScript.g:1:182: OPEN_CURLY
                {
                mOPEN_CURLY(); 

                }
                break;
            case 27 :
                // ./CFScript.g:1:193: CLOSE_CURLY
                {
                mCLOSE_CURLY(); 

                }
                break;
            case 28 :
                // ./CFScript.g:1:205: DOT
                {
                mDOT(); 

                }
                break;
            case 29 :
                // ./CFScript.g:1:209: VAR
                {
                mVAR(); 

                }
                break;
            case 30 :
                // ./CFScript.g:1:213: NUMBER
                {
                mNUMBER(); 

                }
                break;
            case 31 :
                // ./CFScript.g:1:220: ESCAPE_DOUBLE_QUOTE
                {
                mESCAPE_DOUBLE_QUOTE(); 

                }
                break;
            case 32 :
                // ./CFScript.g:1:240: ESCAPE_SINGLE_QUOTE
                {
                mESCAPE_SINGLE_QUOTE(); 

                }
                break;
            case 33 :
                // ./CFScript.g:1:260: DOUBLE_QUOTE
                {
                mDOUBLE_QUOTE(); 

                }
                break;
            case 34 :
                // ./CFScript.g:1:273: SINGLE_QUOTE
                {
                mSINGLE_QUOTE(); 

                }
                break;
            case 35 :
                // ./CFScript.g:1:286: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 36 :
                // ./CFScript.g:1:297: EXCEPTIONNAME
                {
                mEXCEPTIONNAME(); 

                }
                break;
            case 37 :
                // ./CFScript.g:1:311: WS
                {
                mWS(); 

                }
                break;
            case 38 :
                // ./CFScript.g:1:314: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 39 :
                // ./CFScript.g:1:322: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;

        }

    }


    protected DFA13 dfa13 = new DFA13(this);
    static final String DFA13_eotS =
        "\1\uffff\11\51\1\uffff\2\51\2\uffff\1\16\6\51\12\uffff\1\51\1\105"+
        "\1\110\1\112\1\51\1\uffff\3\51\2\uffff\1\115\1\51\1\117\1\16\1\51"+
        "\1\16\4\51\1\127\5\51\2\uffff\1\51\3\16\3\51\1\52\1\uffff\1\105"+
        "\4\uffff\1\51\1\145\1\uffff\1\16\1\uffff\1\51\1\16\1\147\4\51\1"+
        "\uffff\1\51\1\155\1\16\2\51\5\16\1\160\1\105\1\51\1\uffff\1\162"+
        "\1\uffff\1\51\1\164\3\51\1\uffff\2\51\1\uffff\1\51\1\uffff\1\173"+
        "\1\uffff\1\51\1\175\2\51\1\u0080\1\51\1\uffff\1\u0082\1\uffff\1"+
        "\51\1\u0084\1\uffff\1\51\1\uffff\1\u0086\1\uffff\1\u0087\2\uffff";
    static final String DFA13_eofS =
        "\u0088\uffff";
    static final String DFA13_minS =
        "\1\11\11\56\1\uffff\2\56\2\uffff\1\52\6\56\12\uffff\2\56\1\42\1"+
        "\47\1\56\1\uffff\3\56\2\uffff\20\56\2\uffff\7\56\1\60\1\uffff\1"+
        "\56\4\uffff\2\56\1\uffff\1\56\1\uffff\7\56\1\uffff\15\56\1\uffff"+
        "\1\56\1\uffff\5\56\1\uffff\2\56\1\uffff\1\56\1\uffff\1\56\1\uffff"+
        "\6\56\1\uffff\1\56\1\uffff\2\56\1\uffff\1\56\1\uffff\1\56\1\uffff"+
        "\1\56\2\uffff";
    static final String DFA13_maxS =
        "\1\175\11\172\1\uffff\2\172\2\uffff\1\57\6\172\12\uffff\2\172\1"+
        "\42\1\47\1\172\1\uffff\3\172\2\uffff\20\172\2\uffff\7\172\1\71\1"+
        "\uffff\1\172\4\uffff\2\172\1\uffff\1\172\1\uffff\7\172\1\uffff\15"+
        "\172\1\uffff\1\172\1\uffff\5\172\1\uffff\2\172\1\uffff\1\172\1\uffff"+
        "\1\172\1\uffff\6\172\1\uffff\1\172\1\uffff\2\172\1\uffff\1\172\1"+
        "\uffff\1\172\1\uffff\1\172\2\uffff";
    static final String DFA13_acceptS =
        "\12\uffff\1\14\2\uffff\1\21\1\22\7\uffff\1\23\1\24\1\25\1\26\1\27"+
        "\1\30\1\31\1\32\1\33\1\34\5\uffff\1\45\3\uffff\1\43\1\44\20\uffff"+
        "\1\47\1\46\10\uffff\1\36\1\uffff\1\37\1\41\1\40\1\42\2\uffff\1\2"+
        "\1\uffff\1\10\7\uffff\1\12\15\uffff\1\7\1\uffff\1\4\5\uffff\1\13"+
        "\2\uffff\1\35\1\uffff\1\3\1\uffff\1\16\6\uffff\1\5\1\uffff\1\11"+
        "\2\uffff\1\20\1\uffff\1\6\1\uffff\1\15\1\uffff\1\17\1\1";
    static final String DFA13_specialS =
        "\u0088\uffff}>";
    static final String[] DFA13_transitionS = {
            "\2\45\1\uffff\2\45\22\uffff\1\45\1\uffff\1\42\1\30\2\uffff\1"+
            "\16\1\43\1\31\1\32\2\16\1\26\1\16\1\37\1\17\12\41\1\15\1\27"+
            "\1\uffff\1\12\3\uffff\32\44\1\33\1\16\1\34\1\16\1\44\1\uffff"+
            "\1\24\1\14\1\5\1\10\1\3\1\1\1\21\1\44\1\2\2\44\1\22\1\20\1\11"+
            "\1\23\2\44\1\6\1\13\1\4\1\44\1\40\1\7\1\25\2\44\1\35\1\uffff"+
            "\1\36",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\16\50"+
            "\1\47\5\50\1\46\5\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\5\50"+
            "\1\53\6\50\1\54\1\55\4\50\1\56\7\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\13\50"+
            "\1\57\4\50\1\60\11\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\21\50"+
            "\1\61\10\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\1\62"+
            "\31\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\4\50"+
            "\1\63\25\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\7\50"+
            "\1\64\22\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\4\50"+
            "\1\66\11\50\1\65\13\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\4\50"+
            "\1\70\11\50\1\67\13\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\26\50"+
            "\1\71\3\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\21\50"+
            "\1\72\10\50",
            "",
            "",
            "\1\74\4\uffff\1\73",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\16\50"+
            "\1\75\13\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\23\50"+
            "\1\76\6\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\23\50"+
            "\1\77\6\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\21\50"+
            "\1\100\10\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\15\50"+
            "\1\101\14\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\16\50"+
            "\1\102\13\50",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\1\103"+
            "\31\50",
            "\1\104\1\uffff\12\106\7\uffff\32\52\4\uffff\1\52\1\uffff\32"+
            "\52",
            "\1\107",
            "\1\111",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\15\50"+
            "\1\113\14\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\21\50"+
            "\1\114\10\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\17\50"+
            "\1\116\12\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\22\50"+
            "\1\120\7\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\25\50"+
            "\1\121\4\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\30\50"+
            "\1\122\1\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\22\50"+
            "\1\124\1\123\6\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\23\50"+
            "\1\125\6\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\10\50"+
            "\1\126\21\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\5\50"+
            "\1\130\24\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\23\50"+
            "\1\131\6\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\20\50"+
            "\1\132\11\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\10\50"+
            "\1\133\21\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\4\50"+
            "\1\134\25\50",
            "",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\3\50"+
            "\1\135\26\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\4\50"+
            "\1\136\25\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\4\50"+
            "\1\137\25\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\3\50"+
            "\1\140\26\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\21\50"+
            "\1\141\10\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\21\50"+
            "\1\142\10\50",
            "\12\143",
            "",
            "\1\104\1\uffff\12\106\7\uffff\32\52\4\uffff\1\52\1\uffff\32"+
            "\52",
            "",
            "",
            "",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\2\50"+
            "\1\144\27\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\4\50"+
            "\1\146\25\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\2\50"+
            "\1\150\27\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\4\50"+
            "\1\151\25\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\24\50"+
            "\1\152\5\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\13\50"+
            "\1\153\16\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\1\154"+
            "\31\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\23\50"+
            "\1\156\6\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\1\157"+
            "\31\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\143\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\23\50"+
            "\1\161\6\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\7\50"+
            "\1\163\22\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\21\50"+
            "\1\165\10\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\4\50"+
            "\1\166\25\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\24\50"+
            "\1\167\5\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\2\50"+
            "\1\170\27\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\12\50"+
            "\1\171\17\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\10\50"+
            "\1\172\21\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\15\50"+
            "\1\174\14\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\13\50"+
            "\1\176\16\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\7\50"+
            "\1\177\22\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\16\50"+
            "\1\u0081\13\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\23\50"+
            "\1\u0083\6\50",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\15\50"+
            "\1\u0085\14\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "",
            "\1\52\1\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32\50",
            "",
            ""
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( FUNCTION | IF | ELSE | TRY | CATCH | RETURN | FOR | IN | WHILE | DO | NOT | EQUALS | SWITCH | CASE | DEFAULT | BREAK | COLON | OPERATOR | COMMA | SEMI_COLON | HASH | OPEN_PAREN | CLOSE_PAREN | OPEN_SQUARE | CLOSE_SQUARE | OPEN_CURLY | CLOSE_CURLY | DOT | VAR | NUMBER | ESCAPE_DOUBLE_QUOTE | ESCAPE_SINGLE_QUOTE | DOUBLE_QUOTE | SINGLE_QUOTE | IDENTIFIER | EXCEPTIONNAME | WS | COMMENT | LINE_COMMENT );";
        }
    }
 

}