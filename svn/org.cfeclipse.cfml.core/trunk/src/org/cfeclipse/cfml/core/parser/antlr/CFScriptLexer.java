// $ANTLR 3.0 ./CFScript.g 2007-06-18 17:05:29

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
    public static final int WHILE=33;
    public static final int LETTER=46;
    public static final int DOUBLE_QUOTE=20;
    public static final int CLOSE_CURLY=36;
    public static final int CASE=38;
    public static final int DO=34;
    public static final int FOR=32;
    public static final int EQUALS=11;
    public static final int NOT=16;
    public static final int EOF=-1;
    public static final int BREAK=41;
    public static final int IF=28;
    public static final int SINGLE_QUOTE=22;
    public static final int COMMA=27;
    public static final int IDENTIFIER=24;
    public static final int RETURN=14;
    public static final int OPEN_SQUARE=25;
    public static final int ESCAPE_DOUBLE_QUOTE=21;
    public static final int VAR=10;
    public static final int CLOSE_PAREN=13;
    public static final int MATH_OPERATOR=42;
    public static final int DIGIT=45;
    public static final int COMMENT=48;
    public static final int DOT=19;
    public static final int CLOSE_SQUARE=26;
    public static final int STRUCT_KEY=5;
    public static final int LINE_COMMENT=49;
    public static final int STRING_CFML=7;
    public static final int OPERATOR=15;
    public static final int SWITCH=37;
    public static final int ELSE=29;
    public static final int DEFAULT=40;
    public static final int NUMBER=17;
    public static final int HASH=18;
    public static final int OPEN_PAREN=12;
    public static final int SEMI_COLON=9;
    public static final int OPEN_CURLY=35;
    public static final int Tokens=50;
    public static final int TRY=30;
    public static final int ESCAPE_SINGLE_QUOTE=23;
    public static final int ELSEIF=6;
    public static final int COLON=39;
    public static final int WS=47;
    public static final int BOOLEAN_OPERATOR=44;
    public static final int CATCH=31;
    public static final int FUNCTION_CALL=4;
    public static final int STRING_OPERATOR=43;
    public static final int STRING=8;

    	public static final int COMMENT_CHANNEL = 90;

    public CFScriptLexer() {;} 
    public CFScriptLexer(CharStream input) {
        super(input);
    }
    public String getGrammarFileName() { return "./CFScript.g"; }

    // $ANTLR start IF
    public final void mIF() throws RecognitionException {
    traceIn("IF", 1);
        try {
            int _type = IF;
            // ./CFScript.g:338:2: ( 'if' )
            // ./CFScript.g:338:2: 'if'
            {
            match("if"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("IF", 1);
        }
    }
    // $ANTLR end IF

    // $ANTLR start ELSE
    public final void mELSE() throws RecognitionException {
    traceIn("ELSE", 2);
        try {
            int _type = ELSE;
            // ./CFScript.g:343:2: ( 'else' )
            // ./CFScript.g:343:2: 'else'
            {
            match("else"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("ELSE", 2);
        }
    }
    // $ANTLR end ELSE

    // $ANTLR start TRY
    public final void mTRY() throws RecognitionException {
    traceIn("TRY", 3);
        try {
            int _type = TRY;
            // ./CFScript.g:348:2: ( 'try' )
            // ./CFScript.g:348:2: 'try'
            {
            match("try"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("TRY", 3);
        }
    }
    // $ANTLR end TRY

    // $ANTLR start CATCH
    public final void mCATCH() throws RecognitionException {
    traceIn("CATCH", 4);
        try {
            int _type = CATCH;
            // ./CFScript.g:353:2: ( 'catch' )
            // ./CFScript.g:353:2: 'catch'
            {
            match("catch"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("CATCH", 4);
        }
    }
    // $ANTLR end CATCH

    // $ANTLR start RETURN
    public final void mRETURN() throws RecognitionException {
    traceIn("RETURN", 5);
        try {
            int _type = RETURN;
            // ./CFScript.g:357:2: ( 'return' )
            // ./CFScript.g:357:2: 'return'
            {
            match("return"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("RETURN", 5);
        }
    }
    // $ANTLR end RETURN

    // $ANTLR start FOR
    public final void mFOR() throws RecognitionException {
    traceIn("FOR", 6);
        try {
            int _type = FOR;
            // ./CFScript.g:362:2: ( 'for' )
            // ./CFScript.g:362:2: 'for'
            {
            match("for"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("FOR", 6);
        }
    }
    // $ANTLR end FOR

    // $ANTLR start WHILE
    public final void mWHILE() throws RecognitionException {
    traceIn("WHILE", 7);
        try {
            int _type = WHILE;
            // ./CFScript.g:367:2: ( 'while' )
            // ./CFScript.g:367:2: 'while'
            {
            match("while"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("WHILE", 7);
        }
    }
    // $ANTLR end WHILE

    // $ANTLR start DO
    public final void mDO() throws RecognitionException {
    traceIn("DO", 8);
        try {
            int _type = DO;
            // ./CFScript.g:372:2: ( 'do' )
            // ./CFScript.g:372:2: 'do'
            {
            match("do"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("DO", 8);
        }
    }
    // $ANTLR end DO

    // $ANTLR start NOT
    public final void mNOT() throws RecognitionException {
    traceIn("NOT", 9);
        try {
            int _type = NOT;
            // ./CFScript.g:376:2: ( 'not' )
            // ./CFScript.g:376:2: 'not'
            {
            match("not"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("NOT", 9);
        }
    }
    // $ANTLR end NOT

    // $ANTLR start EQUALS
    public final void mEQUALS() throws RecognitionException {
    traceIn("EQUALS", 10);
        try {
            int _type = EQUALS;
            // ./CFScript.g:381:2: ( '=' )
            // ./CFScript.g:381:2: '='
            {
            match('='); 

            }

            this.type = _type;
        }
        finally {
    traceOut("EQUALS", 10);
        }
    }
    // $ANTLR end EQUALS

    // $ANTLR start SWITCH
    public final void mSWITCH() throws RecognitionException {
    traceIn("SWITCH", 11);
        try {
            int _type = SWITCH;
            // ./CFScript.g:386:2: ( 'switch' )
            // ./CFScript.g:386:2: 'switch'
            {
            match("switch"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("SWITCH", 11);
        }
    }
    // $ANTLR end SWITCH

    // $ANTLR start CASE
    public final void mCASE() throws RecognitionException {
    traceIn("CASE", 12);
        try {
            int _type = CASE;
            // ./CFScript.g:391:2: ( 'case' )
            // ./CFScript.g:391:2: 'case'
            {
            match("case"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("CASE", 12);
        }
    }
    // $ANTLR end CASE

    // $ANTLR start DEFAULT
    public final void mDEFAULT() throws RecognitionException {
    traceIn("DEFAULT", 13);
        try {
            int _type = DEFAULT;
            // ./CFScript.g:396:2: ( 'default' )
            // ./CFScript.g:396:2: 'default'
            {
            match("default"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("DEFAULT", 13);
        }
    }
    // $ANTLR end DEFAULT

    // $ANTLR start BREAK
    public final void mBREAK() throws RecognitionException {
    traceIn("BREAK", 14);
        try {
            int _type = BREAK;
            // ./CFScript.g:401:2: ( 'break' )
            // ./CFScript.g:401:2: 'break'
            {
            match("break"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("BREAK", 14);
        }
    }
    // $ANTLR end BREAK

    // $ANTLR start COLON
    public final void mCOLON() throws RecognitionException {
    traceIn("COLON", 15);
        try {
            int _type = COLON;
            // ./CFScript.g:405:2: ( ':' )
            // ./CFScript.g:405:2: ':'
            {
            match(':'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("COLON", 15);
        }
    }
    // $ANTLR end COLON

    // $ANTLR start OPERATOR
    public final void mOPERATOR() throws RecognitionException {
    traceIn("OPERATOR", 16);
        try {
            int _type = OPERATOR;
            // ./CFScript.g:410:2: ( ( MATH_OPERATOR | STRING_OPERATOR | BOOLEAN_OPERATOR ) )
            // ./CFScript.g:410:2: ( MATH_OPERATOR | STRING_OPERATOR | BOOLEAN_OPERATOR )
            {
            // ./CFScript.g:410:2: ( MATH_OPERATOR | STRING_OPERATOR | BOOLEAN_OPERATOR )
            int alt1=3;
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
            case 'g':
            case 'i':
            case 'l':
            case 'n':
                {
                alt1=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("410:2: ( MATH_OPERATOR | STRING_OPERATOR | BOOLEAN_OPERATOR )", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ./CFScript.g:410:4: MATH_OPERATOR
                    {
                    mMATH_OPERATOR(); 

                    }
                    break;
                case 2 :
                    // ./CFScript.g:410:20: STRING_OPERATOR
                    {
                    mSTRING_OPERATOR(); 

                    }
                    break;
                case 3 :
                    // ./CFScript.g:410:38: BOOLEAN_OPERATOR
                    {
                    mBOOLEAN_OPERATOR(); 

                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
    traceOut("OPERATOR", 16);
        }
    }
    // $ANTLR end OPERATOR

    // $ANTLR start COMMA
    public final void mCOMMA() throws RecognitionException {
    traceIn("COMMA", 17);
        try {
            int _type = COMMA;
            // ./CFScript.g:414:2: ( ',' )
            // ./CFScript.g:414:2: ','
            {
            match(','); 

            }

            this.type = _type;
        }
        finally {
    traceOut("COMMA", 17);
        }
    }
    // $ANTLR end COMMA

    // $ANTLR start SEMI_COLON
    public final void mSEMI_COLON() throws RecognitionException {
    traceIn("SEMI_COLON", 18);
        try {
            int _type = SEMI_COLON;
            // ./CFScript.g:419:2: ( ';' )
            // ./CFScript.g:419:2: ';'
            {
            match(';'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("SEMI_COLON", 18);
        }
    }
    // $ANTLR end SEMI_COLON

    // $ANTLR start HASH
    public final void mHASH() throws RecognitionException {
    traceIn("HASH", 19);
        try {
            int _type = HASH;
            // ./CFScript.g:423:2: ( '#' )
            // ./CFScript.g:423:2: '#'
            {
            match('#'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("HASH", 19);
        }
    }
    // $ANTLR end HASH

    // $ANTLR start OPEN_PAREN
    public final void mOPEN_PAREN() throws RecognitionException {
    traceIn("OPEN_PAREN", 20);
        try {
            int _type = OPEN_PAREN;
            // ./CFScript.g:428:2: ( '(' )
            // ./CFScript.g:428:2: '('
            {
            match('('); 

            }

            this.type = _type;
        }
        finally {
    traceOut("OPEN_PAREN", 20);
        }
    }
    // $ANTLR end OPEN_PAREN

    // $ANTLR start CLOSE_PAREN
    public final void mCLOSE_PAREN() throws RecognitionException {
    traceIn("CLOSE_PAREN", 21);
        try {
            int _type = CLOSE_PAREN;
            // ./CFScript.g:433:2: ( ')' )
            // ./CFScript.g:433:2: ')'
            {
            match(')'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("CLOSE_PAREN", 21);
        }
    }
    // $ANTLR end CLOSE_PAREN

    // $ANTLR start OPEN_SQUARE
    public final void mOPEN_SQUARE() throws RecognitionException {
    traceIn("OPEN_SQUARE", 22);
        try {
            int _type = OPEN_SQUARE;
            // ./CFScript.g:438:2: ( '[' )
            // ./CFScript.g:438:2: '['
            {
            match('['); 

            }

            this.type = _type;
        }
        finally {
    traceOut("OPEN_SQUARE", 22);
        }
    }
    // $ANTLR end OPEN_SQUARE

    // $ANTLR start CLOSE_SQUARE
    public final void mCLOSE_SQUARE() throws RecognitionException {
    traceIn("CLOSE_SQUARE", 23);
        try {
            int _type = CLOSE_SQUARE;
            // ./CFScript.g:443:2: ( ']' )
            // ./CFScript.g:443:2: ']'
            {
            match(']'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("CLOSE_SQUARE", 23);
        }
    }
    // $ANTLR end CLOSE_SQUARE

    // $ANTLR start OPEN_CURLY
    public final void mOPEN_CURLY() throws RecognitionException {
    traceIn("OPEN_CURLY", 24);
        try {
            int _type = OPEN_CURLY;
            // ./CFScript.g:448:2: ( '{' )
            // ./CFScript.g:448:2: '{'
            {
            match('{'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("OPEN_CURLY", 24);
        }
    }
    // $ANTLR end OPEN_CURLY

    // $ANTLR start CLOSE_CURLY
    public final void mCLOSE_CURLY() throws RecognitionException {
    traceIn("CLOSE_CURLY", 25);
        try {
            int _type = CLOSE_CURLY;
            // ./CFScript.g:453:2: ( '}' )
            // ./CFScript.g:453:2: '}'
            {
            match('}'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("CLOSE_CURLY", 25);
        }
    }
    // $ANTLR end CLOSE_CURLY

    // $ANTLR start DOT
    public final void mDOT() throws RecognitionException {
    traceIn("DOT", 26);
        try {
            int _type = DOT;
            // ./CFScript.g:458:2: ( '.' )
            // ./CFScript.g:458:2: '.'
            {
            match('.'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("DOT", 26);
        }
    }
    // $ANTLR end DOT

    // $ANTLR start VAR
    public final void mVAR() throws RecognitionException {
    traceIn("VAR", 27);
        try {
            int _type = VAR;
            // ./CFScript.g:463:2: ( 'var' )
            // ./CFScript.g:463:2: 'var'
            {
            match("var"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("VAR", 27);
        }
    }
    // $ANTLR end VAR

    // $ANTLR start NUMBER
    public final void mNUMBER() throws RecognitionException {
    traceIn("NUMBER", 28);
        try {
            int _type = NUMBER;
            // ./CFScript.g:468:2: ( ( DIGIT )+ ( DOT ( DIGIT )+ )? )
            // ./CFScript.g:468:2: ( DIGIT )+ ( DOT ( DIGIT )+ )?
            {
            // ./CFScript.g:468:2: ( DIGIT )+
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
            	    // ./CFScript.g:468:2: DIGIT
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

            // ./CFScript.g:468:8: ( DOT ( DIGIT )+ )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='.') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ./CFScript.g:468:9: DOT ( DIGIT )+
                    {
                    mDOT(); 
                    // ./CFScript.g:468:13: ( DIGIT )+
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
                    	    // ./CFScript.g:468:13: DIGIT
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
    traceOut("NUMBER", 28);
        }
    }
    // $ANTLR end NUMBER

    // $ANTLR start ESCAPE_DOUBLE_QUOTE
    public final void mESCAPE_DOUBLE_QUOTE() throws RecognitionException {
    traceIn("ESCAPE_DOUBLE_QUOTE", 29);
        try {
            int _type = ESCAPE_DOUBLE_QUOTE;
            // ./CFScript.g:473:2: ( '\"\"' )
            // ./CFScript.g:473:2: '\"\"'
            {
            match("\"\""); 


            }

            this.type = _type;
        }
        finally {
    traceOut("ESCAPE_DOUBLE_QUOTE", 29);
        }
    }
    // $ANTLR end ESCAPE_DOUBLE_QUOTE

    // $ANTLR start ESCAPE_SINGLE_QUOTE
    public final void mESCAPE_SINGLE_QUOTE() throws RecognitionException {
    traceIn("ESCAPE_SINGLE_QUOTE", 30);
        try {
            int _type = ESCAPE_SINGLE_QUOTE;
            // ./CFScript.g:478:2: ( '\\'\\'' )
            // ./CFScript.g:478:2: '\\'\\''
            {
            match("\'\'"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("ESCAPE_SINGLE_QUOTE", 30);
        }
    }
    // $ANTLR end ESCAPE_SINGLE_QUOTE

    // $ANTLR start DOUBLE_QUOTE
    public final void mDOUBLE_QUOTE() throws RecognitionException {
    traceIn("DOUBLE_QUOTE", 31);
        try {
            int _type = DOUBLE_QUOTE;
            // ./CFScript.g:483:2: ( '\"' )
            // ./CFScript.g:483:2: '\"'
            {
            match('\"'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("DOUBLE_QUOTE", 31);
        }
    }
    // $ANTLR end DOUBLE_QUOTE

    // $ANTLR start SINGLE_QUOTE
    public final void mSINGLE_QUOTE() throws RecognitionException {
    traceIn("SINGLE_QUOTE", 32);
        try {
            int _type = SINGLE_QUOTE;
            // ./CFScript.g:487:2: ( '\\'' )
            // ./CFScript.g:487:2: '\\''
            {
            match('\''); 

            }

            this.type = _type;
        }
        finally {
    traceOut("SINGLE_QUOTE", 32);
        }
    }
    // $ANTLR end SINGLE_QUOTE

    // $ANTLR start IDENTIFIER
    public final void mIDENTIFIER() throws RecognitionException {
    traceIn("IDENTIFIER", 33);
        try {
            int _type = IDENTIFIER;
            // ./CFScript.g:492:2: ( ( LETTER | '_' ) ( LETTER | DIGIT | '_' )* )
            // ./CFScript.g:492:2: ( LETTER | '_' ) ( LETTER | DIGIT | '_' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

            // ./CFScript.g:492:17: ( LETTER | DIGIT | '_' )*
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
    traceOut("IDENTIFIER", 33);
        }
    }
    // $ANTLR end IDENTIFIER

    // $ANTLR start MATH_OPERATOR
    public final void mMATH_OPERATOR() throws RecognitionException {
    traceIn("MATH_OPERATOR", 34);
        try {
            // ./CFScript.g:499:2: ( ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' ) )
            // ./CFScript.g:499:2: ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' )
            {
            // ./CFScript.g:499:2: ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' )
            int alt6=7;
            switch ( input.LA(1) ) {
            case '+':
                {
                alt6=1;
                }
                break;
            case '*':
                {
                alt6=2;
                }
                break;
            case '/':
                {
                alt6=3;
                }
                break;
            case '\\':
                {
                alt6=4;
                }
                break;
            case '^':
                {
                alt6=5;
                }
                break;
            case 'm':
                {
                alt6=6;
                }
                break;
            case '-':
                {
                alt6=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("499:2: ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' )", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // ./CFScript.g:499:3: '+'
                    {
                    match('+'); 

                    }
                    break;
                case 2 :
                    // ./CFScript.g:499:9: '*'
                    {
                    match('*'); 

                    }
                    break;
                case 3 :
                    // ./CFScript.g:499:15: '\\/'
                    {
                    match('/'); 

                    }
                    break;
                case 4 :
                    // ./CFScript.g:499:22: '\\\\'
                    {
                    match('\\'); 

                    }
                    break;
                case 5 :
                    // ./CFScript.g:499:29: '^'
                    {
                    match('^'); 

                    }
                    break;
                case 6 :
                    // ./CFScript.g:499:35: 'mod'
                    {
                    match("mod"); 


                    }
                    break;
                case 7 :
                    // ./CFScript.g:499:43: '-'
                    {
                    match('-'); 

                    }
                    break;

            }


            }

        }
        finally {
    traceOut("MATH_OPERATOR", 34);
        }
    }
    // $ANTLR end MATH_OPERATOR

    // $ANTLR start STRING_OPERATOR
    public final void mSTRING_OPERATOR() throws RecognitionException {
    traceIn("STRING_OPERATOR", 35);
        try {
            // ./CFScript.g:503:2: ( '&' )
            // ./CFScript.g:503:2: '&'
            {
            match('&'); 

            }

        }
        finally {
    traceOut("STRING_OPERATOR", 35);
        }
    }
    // $ANTLR end STRING_OPERATOR

    // $ANTLR start BOOLEAN_OPERATOR
    public final void mBOOLEAN_OPERATOR() throws RecognitionException {
    traceIn("BOOLEAN_OPERATOR", 36);
        try {
            // ./CFScript.g:507:2: ( ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' ) )
            // ./CFScript.g:507:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )
            {
            // ./CFScript.g:507:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )
            int alt7=7;
            switch ( input.LA(1) ) {
            case 'e':
                {
                alt7=1;
                }
                break;
            case 'n':
                {
                alt7=2;
                }
                break;
            case 'i':
                {
                alt7=3;
                }
                break;
            case 'g':
                {
                int LA7_4 = input.LA(2);

                if ( (LA7_4=='t') ) {
                    int LA7_6 = input.LA(3);

                    if ( (LA7_6=='e') ) {
                        alt7=7;
                    }
                    else {
                        alt7=4;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("507:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )", 7, 4, input);

                    throw nvae;
                }
                }
                break;
            case 'l':
                {
                int LA7_5 = input.LA(2);

                if ( (LA7_5=='t') ) {
                    int LA7_7 = input.LA(3);

                    if ( (LA7_7=='e') ) {
                        alt7=6;
                    }
                    else {
                        alt7=5;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("507:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )", 7, 5, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("507:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // ./CFScript.g:507:3: 'eq'
                    {
                    match("eq"); 


                    }
                    break;
                case 2 :
                    // ./CFScript.g:507:8: 'neq'
                    {
                    match("neq"); 


                    }
                    break;
                case 3 :
                    // ./CFScript.g:507:14: 'is'
                    {
                    match("is"); 


                    }
                    break;
                case 4 :
                    // ./CFScript.g:507:19: 'gt'
                    {
                    match("gt"); 


                    }
                    break;
                case 5 :
                    // ./CFScript.g:507:24: 'lt'
                    {
                    match("lt"); 


                    }
                    break;
                case 6 :
                    // ./CFScript.g:507:29: 'lte'
                    {
                    match("lte"); 


                    }
                    break;
                case 7 :
                    // ./CFScript.g:507:35: 'gte'
                    {
                    match("gte"); 


                    }
                    break;

            }


            }

        }
        finally {
    traceOut("BOOLEAN_OPERATOR", 36);
        }
    }
    // $ANTLR end BOOLEAN_OPERATOR

    // $ANTLR start DIGIT
    public final void mDIGIT() throws RecognitionException {
    traceIn("DIGIT", 37);
        try {
            // ./CFScript.g:512:2: ( '0' .. '9' )
            // ./CFScript.g:512:2: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
    traceOut("DIGIT", 37);
        }
    }
    // $ANTLR end DIGIT

    // $ANTLR start LETTER
    public final void mLETTER() throws RecognitionException {
    traceIn("LETTER", 38);
        try {
            // ./CFScript.g:517:2: ( 'a' .. 'z' | 'A' .. 'Z' )
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
    traceOut("LETTER", 38);
        }
    }
    // $ANTLR end LETTER

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
    traceIn("WS", 39);
        try {
            int _type = WS;
            // ./CFScript.g:524:2: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // ./CFScript.g:524:2: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
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
    traceOut("WS", 39);
        }
    }
    // $ANTLR end WS

    // $ANTLR start COMMENT
    public final void mCOMMENT() throws RecognitionException {
    traceIn("COMMENT", 40);
        try {
            int _type = COMMENT;
            // ./CFScript.g:532:2: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ./CFScript.g:532:2: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // ./CFScript.g:532:7: ( options {greedy=false; } : . )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0=='*') ) {
                    int LA8_1 = input.LA(2);

                    if ( (LA8_1=='/') ) {
                        alt8=2;
                    }
                    else if ( ((LA8_1>='\u0000' && LA8_1<='.')||(LA8_1>='0' && LA8_1<='\uFFFE')) ) {
                        alt8=1;
                    }


                }
                else if ( ((LA8_0>='\u0000' && LA8_0<=')')||(LA8_0>='+' && LA8_0<='\uFFFE')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ./CFScript.g:532:35: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            match("*/"); 


            		channel=COMMENT_CHANNEL; //90 is the comment channel
            	

            }

            this.type = _type;
        }
        finally {
    traceOut("COMMENT", 40);
        }
    }
    // $ANTLR end COMMENT

    // $ANTLR start LINE_COMMENT
    public final void mLINE_COMMENT() throws RecognitionException {
    traceIn("LINE_COMMENT", 41);
        try {
            int _type = LINE_COMMENT;
            // ./CFScript.g:540:2: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // ./CFScript.g:540:2: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // ./CFScript.g:540:7: (~ ( '\\n' | '\\r' ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='\u0000' && LA9_0<='\t')||(LA9_0>='\u000B' && LA9_0<='\f')||(LA9_0>='\u000E' && LA9_0<='\uFFFE')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ./CFScript.g:540:7: ~ ( '\\n' | '\\r' )
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
            	    break loop9;
                }
            } while (true);

            // ./CFScript.g:540:21: ( '\\r' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='\r') ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ./CFScript.g:540:21: '\\r'
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
    traceOut("LINE_COMMENT", 41);
        }
    }
    // $ANTLR end LINE_COMMENT

    public void mTokens() throws RecognitionException {
        // ./CFScript.g:1:10: ( IF | ELSE | TRY | CATCH | RETURN | FOR | WHILE | DO | NOT | EQUALS | SWITCH | CASE | DEFAULT | BREAK | COLON | OPERATOR | COMMA | SEMI_COLON | HASH | OPEN_PAREN | CLOSE_PAREN | OPEN_SQUARE | CLOSE_SQUARE | OPEN_CURLY | CLOSE_CURLY | DOT | VAR | NUMBER | ESCAPE_DOUBLE_QUOTE | ESCAPE_SINGLE_QUOTE | DOUBLE_QUOTE | SINGLE_QUOTE | IDENTIFIER | WS | COMMENT | LINE_COMMENT )
        int alt11=36;
        switch ( input.LA(1) ) {
        case 'i':
            {
            switch ( input.LA(2) ) {
            case 's':
                {
                int LA11_35 = input.LA(3);

                if ( ((LA11_35>='0' && LA11_35<='9')||(LA11_35>='A' && LA11_35<='Z')||LA11_35=='_'||(LA11_35>='a' && LA11_35<='z')) ) {
                    alt11=33;
                }
                else {
                    alt11=16;}
                }
                break;
            case 'f':
                {
                int LA11_36 = input.LA(3);

                if ( ((LA11_36>='0' && LA11_36<='9')||(LA11_36>='A' && LA11_36<='Z')||LA11_36=='_'||(LA11_36>='a' && LA11_36<='z')) ) {
                    alt11=33;
                }
                else {
                    alt11=1;}
                }
                break;
            default:
                alt11=33;}

            }
            break;
        case 'e':
            {
            switch ( input.LA(2) ) {
            case 'q':
                {
                int LA11_37 = input.LA(3);

                if ( ((LA11_37>='0' && LA11_37<='9')||(LA11_37>='A' && LA11_37<='Z')||LA11_37=='_'||(LA11_37>='a' && LA11_37<='z')) ) {
                    alt11=33;
                }
                else {
                    alt11=16;}
                }
                break;
            case 'l':
                {
                int LA11_38 = input.LA(3);

                if ( (LA11_38=='s') ) {
                    int LA11_61 = input.LA(4);

                    if ( (LA11_61=='e') ) {
                        int LA11_78 = input.LA(5);

                        if ( ((LA11_78>='0' && LA11_78<='9')||(LA11_78>='A' && LA11_78<='Z')||LA11_78=='_'||(LA11_78>='a' && LA11_78<='z')) ) {
                            alt11=33;
                        }
                        else {
                            alt11=2;}
                    }
                    else {
                        alt11=33;}
                }
                else {
                    alt11=33;}
                }
                break;
            default:
                alt11=33;}

            }
            break;
        case 't':
            {
            int LA11_3 = input.LA(2);

            if ( (LA11_3=='r') ) {
                int LA11_39 = input.LA(3);

                if ( (LA11_39=='y') ) {
                    int LA11_62 = input.LA(4);

                    if ( ((LA11_62>='0' && LA11_62<='9')||(LA11_62>='A' && LA11_62<='Z')||LA11_62=='_'||(LA11_62>='a' && LA11_62<='z')) ) {
                        alt11=33;
                    }
                    else {
                        alt11=3;}
                }
                else {
                    alt11=33;}
            }
            else {
                alt11=33;}
            }
            break;
        case 'c':
            {
            int LA11_4 = input.LA(2);

            if ( (LA11_4=='a') ) {
                switch ( input.LA(3) ) {
                case 't':
                    {
                    int LA11_63 = input.LA(4);

                    if ( (LA11_63=='c') ) {
                        int LA11_80 = input.LA(5);

                        if ( (LA11_80=='h') ) {
                            int LA11_91 = input.LA(6);

                            if ( ((LA11_91>='0' && LA11_91<='9')||(LA11_91>='A' && LA11_91<='Z')||LA11_91=='_'||(LA11_91>='a' && LA11_91<='z')) ) {
                                alt11=33;
                            }
                            else {
                                alt11=4;}
                        }
                        else {
                            alt11=33;}
                    }
                    else {
                        alt11=33;}
                    }
                    break;
                case 's':
                    {
                    int LA11_64 = input.LA(4);

                    if ( (LA11_64=='e') ) {
                        int LA11_81 = input.LA(5);

                        if ( ((LA11_81>='0' && LA11_81<='9')||(LA11_81>='A' && LA11_81<='Z')||LA11_81=='_'||(LA11_81>='a' && LA11_81<='z')) ) {
                            alt11=33;
                        }
                        else {
                            alt11=12;}
                    }
                    else {
                        alt11=33;}
                    }
                    break;
                default:
                    alt11=33;}

            }
            else {
                alt11=33;}
            }
            break;
        case 'r':
            {
            int LA11_5 = input.LA(2);

            if ( (LA11_5=='e') ) {
                int LA11_41 = input.LA(3);

                if ( (LA11_41=='t') ) {
                    int LA11_65 = input.LA(4);

                    if ( (LA11_65=='u') ) {
                        int LA11_82 = input.LA(5);

                        if ( (LA11_82=='r') ) {
                            int LA11_93 = input.LA(6);

                            if ( (LA11_93=='n') ) {
                                int LA11_99 = input.LA(7);

                                if ( ((LA11_99>='0' && LA11_99<='9')||(LA11_99>='A' && LA11_99<='Z')||LA11_99=='_'||(LA11_99>='a' && LA11_99<='z')) ) {
                                    alt11=33;
                                }
                                else {
                                    alt11=5;}
                            }
                            else {
                                alt11=33;}
                        }
                        else {
                            alt11=33;}
                    }
                    else {
                        alt11=33;}
                }
                else {
                    alt11=33;}
            }
            else {
                alt11=33;}
            }
            break;
        case 'f':
            {
            int LA11_6 = input.LA(2);

            if ( (LA11_6=='o') ) {
                int LA11_42 = input.LA(3);

                if ( (LA11_42=='r') ) {
                    int LA11_66 = input.LA(4);

                    if ( ((LA11_66>='0' && LA11_66<='9')||(LA11_66>='A' && LA11_66<='Z')||LA11_66=='_'||(LA11_66>='a' && LA11_66<='z')) ) {
                        alt11=33;
                    }
                    else {
                        alt11=6;}
                }
                else {
                    alt11=33;}
            }
            else {
                alt11=33;}
            }
            break;
        case 'w':
            {
            int LA11_7 = input.LA(2);

            if ( (LA11_7=='h') ) {
                int LA11_43 = input.LA(3);

                if ( (LA11_43=='i') ) {
                    int LA11_67 = input.LA(4);

                    if ( (LA11_67=='l') ) {
                        int LA11_84 = input.LA(5);

                        if ( (LA11_84=='e') ) {
                            int LA11_94 = input.LA(6);

                            if ( ((LA11_94>='0' && LA11_94<='9')||(LA11_94>='A' && LA11_94<='Z')||LA11_94=='_'||(LA11_94>='a' && LA11_94<='z')) ) {
                                alt11=33;
                            }
                            else {
                                alt11=7;}
                        }
                        else {
                            alt11=33;}
                    }
                    else {
                        alt11=33;}
                }
                else {
                    alt11=33;}
            }
            else {
                alt11=33;}
            }
            break;
        case 'd':
            {
            switch ( input.LA(2) ) {
            case 'e':
                {
                int LA11_44 = input.LA(3);

                if ( (LA11_44=='f') ) {
                    int LA11_68 = input.LA(4);

                    if ( (LA11_68=='a') ) {
                        int LA11_85 = input.LA(5);

                        if ( (LA11_85=='u') ) {
                            int LA11_95 = input.LA(6);

                            if ( (LA11_95=='l') ) {
                                int LA11_101 = input.LA(7);

                                if ( (LA11_101=='t') ) {
                                    int LA11_105 = input.LA(8);

                                    if ( ((LA11_105>='0' && LA11_105<='9')||(LA11_105>='A' && LA11_105<='Z')||LA11_105=='_'||(LA11_105>='a' && LA11_105<='z')) ) {
                                        alt11=33;
                                    }
                                    else {
                                        alt11=13;}
                                }
                                else {
                                    alt11=33;}
                            }
                            else {
                                alt11=33;}
                        }
                        else {
                            alt11=33;}
                    }
                    else {
                        alt11=33;}
                }
                else {
                    alt11=33;}
                }
                break;
            case 'o':
                {
                int LA11_45 = input.LA(3);

                if ( ((LA11_45>='0' && LA11_45<='9')||(LA11_45>='A' && LA11_45<='Z')||LA11_45=='_'||(LA11_45>='a' && LA11_45<='z')) ) {
                    alt11=33;
                }
                else {
                    alt11=8;}
                }
                break;
            default:
                alt11=33;}

            }
            break;
        case 'n':
            {
            switch ( input.LA(2) ) {
            case 'o':
                {
                int LA11_46 = input.LA(3);

                if ( (LA11_46=='t') ) {
                    int LA11_70 = input.LA(4);

                    if ( ((LA11_70>='0' && LA11_70<='9')||(LA11_70>='A' && LA11_70<='Z')||LA11_70=='_'||(LA11_70>='a' && LA11_70<='z')) ) {
                        alt11=33;
                    }
                    else {
                        alt11=9;}
                }
                else {
                    alt11=33;}
                }
                break;
            case 'e':
                {
                int LA11_47 = input.LA(3);

                if ( (LA11_47=='q') ) {
                    int LA11_71 = input.LA(4);

                    if ( ((LA11_71>='0' && LA11_71<='9')||(LA11_71>='A' && LA11_71<='Z')||LA11_71=='_'||(LA11_71>='a' && LA11_71<='z')) ) {
                        alt11=33;
                    }
                    else {
                        alt11=16;}
                }
                else {
                    alt11=33;}
                }
                break;
            default:
                alt11=33;}

            }
            break;
        case '=':
            {
            alt11=10;
            }
            break;
        case 's':
            {
            int LA11_11 = input.LA(2);

            if ( (LA11_11=='w') ) {
                int LA11_48 = input.LA(3);

                if ( (LA11_48=='i') ) {
                    int LA11_72 = input.LA(4);

                    if ( (LA11_72=='t') ) {
                        int LA11_87 = input.LA(5);

                        if ( (LA11_87=='c') ) {
                            int LA11_96 = input.LA(6);

                            if ( (LA11_96=='h') ) {
                                int LA11_102 = input.LA(7);

                                if ( ((LA11_102>='0' && LA11_102<='9')||(LA11_102>='A' && LA11_102<='Z')||LA11_102=='_'||(LA11_102>='a' && LA11_102<='z')) ) {
                                    alt11=33;
                                }
                                else {
                                    alt11=11;}
                            }
                            else {
                                alt11=33;}
                        }
                        else {
                            alt11=33;}
                    }
                    else {
                        alt11=33;}
                }
                else {
                    alt11=33;}
            }
            else {
                alt11=33;}
            }
            break;
        case 'b':
            {
            int LA11_12 = input.LA(2);

            if ( (LA11_12=='r') ) {
                int LA11_49 = input.LA(3);

                if ( (LA11_49=='e') ) {
                    int LA11_73 = input.LA(4);

                    if ( (LA11_73=='a') ) {
                        int LA11_88 = input.LA(5);

                        if ( (LA11_88=='k') ) {
                            int LA11_97 = input.LA(6);

                            if ( ((LA11_97>='0' && LA11_97<='9')||(LA11_97>='A' && LA11_97<='Z')||LA11_97=='_'||(LA11_97>='a' && LA11_97<='z')) ) {
                                alt11=33;
                            }
                            else {
                                alt11=14;}
                        }
                        else {
                            alt11=33;}
                    }
                    else {
                        alt11=33;}
                }
                else {
                    alt11=33;}
            }
            else {
                alt11=33;}
            }
            break;
        case ':':
            {
            alt11=15;
            }
            break;
        case '&':
        case '*':
        case '+':
        case '-':
        case '\\':
        case '^':
            {
            alt11=16;
            }
            break;
        case '/':
            {
            switch ( input.LA(2) ) {
            case '/':
                {
                alt11=36;
                }
                break;
            case '*':
                {
                alt11=35;
                }
                break;
            default:
                alt11=16;}

            }
            break;
        case 'm':
            {
            int LA11_16 = input.LA(2);

            if ( (LA11_16=='o') ) {
                int LA11_52 = input.LA(3);

                if ( (LA11_52=='d') ) {
                    int LA11_74 = input.LA(4);

                    if ( ((LA11_74>='0' && LA11_74<='9')||(LA11_74>='A' && LA11_74<='Z')||LA11_74=='_'||(LA11_74>='a' && LA11_74<='z')) ) {
                        alt11=33;
                    }
                    else {
                        alt11=16;}
                }
                else {
                    alt11=33;}
            }
            else {
                alt11=33;}
            }
            break;
        case 'g':
            {
            int LA11_17 = input.LA(2);

            if ( (LA11_17=='t') ) {
                switch ( input.LA(3) ) {
                case 'e':
                    {
                    int LA11_75 = input.LA(4);

                    if ( ((LA11_75>='0' && LA11_75<='9')||(LA11_75>='A' && LA11_75<='Z')||LA11_75=='_'||(LA11_75>='a' && LA11_75<='z')) ) {
                        alt11=33;
                    }
                    else {
                        alt11=16;}
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                case '_':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    {
                    alt11=33;
                    }
                    break;
                default:
                    alt11=16;}

            }
            else {
                alt11=33;}
            }
            break;
        case 'l':
            {
            int LA11_18 = input.LA(2);

            if ( (LA11_18=='t') ) {
                switch ( input.LA(3) ) {
                case 'e':
                    {
                    int LA11_76 = input.LA(4);

                    if ( ((LA11_76>='0' && LA11_76<='9')||(LA11_76>='A' && LA11_76<='Z')||LA11_76=='_'||(LA11_76>='a' && LA11_76<='z')) ) {
                        alt11=33;
                    }
                    else {
                        alt11=16;}
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                case '_':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    {
                    alt11=33;
                    }
                    break;
                default:
                    alt11=16;}

            }
            else {
                alt11=33;}
            }
            break;
        case ',':
            {
            alt11=17;
            }
            break;
        case ';':
            {
            alt11=18;
            }
            break;
        case '#':
            {
            alt11=19;
            }
            break;
        case '(':
            {
            alt11=20;
            }
            break;
        case ')':
            {
            alt11=21;
            }
            break;
        case '[':
            {
            alt11=22;
            }
            break;
        case ']':
            {
            alt11=23;
            }
            break;
        case '{':
            {
            alt11=24;
            }
            break;
        case '}':
            {
            alt11=25;
            }
            break;
        case '.':
            {
            alt11=26;
            }
            break;
        case 'v':
            {
            int LA11_29 = input.LA(2);

            if ( (LA11_29=='a') ) {
                int LA11_55 = input.LA(3);

                if ( (LA11_55=='r') ) {
                    int LA11_77 = input.LA(4);

                    if ( ((LA11_77>='0' && LA11_77<='9')||(LA11_77>='A' && LA11_77<='Z')||LA11_77=='_'||(LA11_77>='a' && LA11_77<='z')) ) {
                        alt11=33;
                    }
                    else {
                        alt11=27;}
                }
                else {
                    alt11=33;}
            }
            else {
                alt11=33;}
            }
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt11=28;
            }
            break;
        case '\"':
            {
            int LA11_31 = input.LA(2);

            if ( (LA11_31=='\"') ) {
                alt11=29;
            }
            else {
                alt11=31;}
            }
            break;
        case '\'':
            {
            int LA11_32 = input.LA(2);

            if ( (LA11_32=='\'') ) {
                alt11=30;
            }
            else {
                alt11=32;}
            }
            break;
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case '_':
        case 'a':
        case 'h':
        case 'j':
        case 'k':
        case 'o':
        case 'p':
        case 'q':
        case 'u':
        case 'x':
        case 'y':
        case 'z':
            {
            alt11=33;
            }
            break;
        case '\t':
        case '\n':
        case '\f':
        case '\r':
        case ' ':
            {
            alt11=34;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( IF | ELSE | TRY | CATCH | RETURN | FOR | WHILE | DO | NOT | EQUALS | SWITCH | CASE | DEFAULT | BREAK | COLON | OPERATOR | COMMA | SEMI_COLON | HASH | OPEN_PAREN | CLOSE_PAREN | OPEN_SQUARE | CLOSE_SQUARE | OPEN_CURLY | CLOSE_CURLY | DOT | VAR | NUMBER | ESCAPE_DOUBLE_QUOTE | ESCAPE_SINGLE_QUOTE | DOUBLE_QUOTE | SINGLE_QUOTE | IDENTIFIER | WS | COMMENT | LINE_COMMENT );", 11, 0, input);

            throw nvae;
        }

        switch (alt11) {
            case 1 :
                // ./CFScript.g:1:10: IF
                {
                mIF(); 

                }
                break;
            case 2 :
                // ./CFScript.g:1:13: ELSE
                {
                mELSE(); 

                }
                break;
            case 3 :
                // ./CFScript.g:1:18: TRY
                {
                mTRY(); 

                }
                break;
            case 4 :
                // ./CFScript.g:1:22: CATCH
                {
                mCATCH(); 

                }
                break;
            case 5 :
                // ./CFScript.g:1:28: RETURN
                {
                mRETURN(); 

                }
                break;
            case 6 :
                // ./CFScript.g:1:35: FOR
                {
                mFOR(); 

                }
                break;
            case 7 :
                // ./CFScript.g:1:39: WHILE
                {
                mWHILE(); 

                }
                break;
            case 8 :
                // ./CFScript.g:1:45: DO
                {
                mDO(); 

                }
                break;
            case 9 :
                // ./CFScript.g:1:48: NOT
                {
                mNOT(); 

                }
                break;
            case 10 :
                // ./CFScript.g:1:52: EQUALS
                {
                mEQUALS(); 

                }
                break;
            case 11 :
                // ./CFScript.g:1:59: SWITCH
                {
                mSWITCH(); 

                }
                break;
            case 12 :
                // ./CFScript.g:1:66: CASE
                {
                mCASE(); 

                }
                break;
            case 13 :
                // ./CFScript.g:1:71: DEFAULT
                {
                mDEFAULT(); 

                }
                break;
            case 14 :
                // ./CFScript.g:1:79: BREAK
                {
                mBREAK(); 

                }
                break;
            case 15 :
                // ./CFScript.g:1:85: COLON
                {
                mCOLON(); 

                }
                break;
            case 16 :
                // ./CFScript.g:1:91: OPERATOR
                {
                mOPERATOR(); 

                }
                break;
            case 17 :
                // ./CFScript.g:1:100: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 18 :
                // ./CFScript.g:1:106: SEMI_COLON
                {
                mSEMI_COLON(); 

                }
                break;
            case 19 :
                // ./CFScript.g:1:117: HASH
                {
                mHASH(); 

                }
                break;
            case 20 :
                // ./CFScript.g:1:122: OPEN_PAREN
                {
                mOPEN_PAREN(); 

                }
                break;
            case 21 :
                // ./CFScript.g:1:133: CLOSE_PAREN
                {
                mCLOSE_PAREN(); 

                }
                break;
            case 22 :
                // ./CFScript.g:1:145: OPEN_SQUARE
                {
                mOPEN_SQUARE(); 

                }
                break;
            case 23 :
                // ./CFScript.g:1:157: CLOSE_SQUARE
                {
                mCLOSE_SQUARE(); 

                }
                break;
            case 24 :
                // ./CFScript.g:1:170: OPEN_CURLY
                {
                mOPEN_CURLY(); 

                }
                break;
            case 25 :
                // ./CFScript.g:1:181: CLOSE_CURLY
                {
                mCLOSE_CURLY(); 

                }
                break;
            case 26 :
                // ./CFScript.g:1:193: DOT
                {
                mDOT(); 

                }
                break;
            case 27 :
                // ./CFScript.g:1:197: VAR
                {
                mVAR(); 

                }
                break;
            case 28 :
                // ./CFScript.g:1:201: NUMBER
                {
                mNUMBER(); 

                }
                break;
            case 29 :
                // ./CFScript.g:1:208: ESCAPE_DOUBLE_QUOTE
                {
                mESCAPE_DOUBLE_QUOTE(); 

                }
                break;
            case 30 :
                // ./CFScript.g:1:228: ESCAPE_SINGLE_QUOTE
                {
                mESCAPE_SINGLE_QUOTE(); 

                }
                break;
            case 31 :
                // ./CFScript.g:1:248: DOUBLE_QUOTE
                {
                mDOUBLE_QUOTE(); 

                }
                break;
            case 32 :
                // ./CFScript.g:1:261: SINGLE_QUOTE
                {
                mSINGLE_QUOTE(); 

                }
                break;
            case 33 :
                // ./CFScript.g:1:274: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 34 :
                // ./CFScript.g:1:285: WS
                {
                mWS(); 

                }
                break;
            case 35 :
                // ./CFScript.g:1:288: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 36 :
                // ./CFScript.g:1:296: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;

        }

    }


 

}