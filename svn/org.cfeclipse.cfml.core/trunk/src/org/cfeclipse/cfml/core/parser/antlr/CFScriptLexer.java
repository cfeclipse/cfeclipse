// $ANTLR 3.0 ./CFScript.g 2007-07-02 14:21:41

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
    public static final int WHILE=36;
    public static final int CLOSE_CURLY=39;
    public static final int LETTER=50;
    public static final int DOUBLE_QUOTE=21;
    public static final int CASE=41;
    public static final int DO=37;
    public static final int FOR=34;
    public static final int CONDITION_OPERATOR=47;
    public static final int EQUALS=12;
    public static final int NOT=17;
    public static final int EOF=-1;
    public static final int BREAK=44;
    public static final int IF=30;
    public static final int SINGLE_QUOTE=23;
    public static final int IN=35;
    public static final int COMMA=28;
    public static final int IDENTIFIER=25;
    public static final int RETURN=15;
    public static final int OPEN_SQUARE=26;
    public static final int ESCAPE_DOUBLE_QUOTE=22;
    public static final int VAR=11;
    public static final int MATH_OPERATOR=45;
    public static final int CLOSE_PAREN=14;
    public static final int DIGIT=49;
    public static final int COMMENT=53;
    public static final int DOT=20;
    public static final int CLOSE_SQUARE=27;
    public static final int STRUCT_KEY=6;
    public static final int OTHER=55;
    public static final int LINE_COMMENT=54;
    public static final int STRING_CFML=8;
    public static final int OPERATOR=16;
    public static final int SWITCH=40;
    public static final int ELSE=31;
    public static final int DEFAULT=43;
    public static final int NUMBER=18;
    public static final int HASH=19;
    public static final int OPEN_PAREN=13;
    public static final int UNDERSCORE=51;
    public static final int SEMI_COLON=10;
    public static final int OPEN_CURLY=38;
    public static final int Tokens=56;
    public static final int TRY=32;
    public static final int ESCAPE_SINGLE_QUOTE=24;
    public static final int ELSEIF=7;
    public static final int COLON=42;
    public static final int WS=52;
    public static final int BOOLEAN_OPERATOR=48;
    public static final int CATCH=33;
    public static final int FUNCTION_DECLARATION=5;
    public static final int FUNCTION_CALL=4;
    public static final int STRING_OPERATOR=46;
    public static final int STRING=9;

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
            // ./CFScript.g:382:2: ( 'function' )
            // ./CFScript.g:382:2: 'function'
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
            // ./CFScript.g:387:2: ( 'if' )
            // ./CFScript.g:387:2: 'if'
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
            // ./CFScript.g:392:2: ( 'else' )
            // ./CFScript.g:392:2: 'else'
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
            // ./CFScript.g:397:2: ( 'try' )
            // ./CFScript.g:397:2: 'try'
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
            // ./CFScript.g:402:2: ( 'catch' )
            // ./CFScript.g:402:2: 'catch'
            {
            match("catch"); 


            		setMode(CATCH_MODE);
            	

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
            // ./CFScript.g:409:2: ( 'return' )
            // ./CFScript.g:409:2: 'return'
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
            // ./CFScript.g:414:2: ( 'for' )
            // ./CFScript.g:414:2: 'for'
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
            // ./CFScript.g:419:2: ( 'in' )
            // ./CFScript.g:419:2: 'in'
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
            // ./CFScript.g:424:2: ( 'while' )
            // ./CFScript.g:424:2: 'while'
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
            // ./CFScript.g:429:2: ( 'do' )
            // ./CFScript.g:429:2: 'do'
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
            // ./CFScript.g:433:2: ( 'not' )
            // ./CFScript.g:433:2: 'not'
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
            // ./CFScript.g:438:2: ( '=' )
            // ./CFScript.g:438:2: '='
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
            // ./CFScript.g:443:2: ( 'switch' )
            // ./CFScript.g:443:2: 'switch'
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
            // ./CFScript.g:448:2: ( 'case' )
            // ./CFScript.g:448:2: 'case'
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
            // ./CFScript.g:453:2: ( 'default' )
            // ./CFScript.g:453:2: 'default'
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
            // ./CFScript.g:458:2: ( 'break' )
            // ./CFScript.g:458:2: 'break'
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
            // ./CFScript.g:462:2: ( ':' )
            // ./CFScript.g:462:2: ':'
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
            // ./CFScript.g:467:2: ( ( MATH_OPERATOR | STRING_OPERATOR | CONDITION_OPERATOR | BOOLEAN_OPERATOR ) )
            // ./CFScript.g:467:2: ( MATH_OPERATOR | STRING_OPERATOR | CONDITION_OPERATOR | BOOLEAN_OPERATOR )
            {
            // ./CFScript.g:467:2: ( MATH_OPERATOR | STRING_OPERATOR | CONDITION_OPERATOR | BOOLEAN_OPERATOR )
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
                        new NoViableAltException("467:2: ( MATH_OPERATOR | STRING_OPERATOR | CONDITION_OPERATOR | BOOLEAN_OPERATOR )", 1, 3, input);

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
                        new NoViableAltException("467:2: ( MATH_OPERATOR | STRING_OPERATOR | CONDITION_OPERATOR | BOOLEAN_OPERATOR )", 1, 5, input);

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
                    new NoViableAltException("467:2: ( MATH_OPERATOR | STRING_OPERATOR | CONDITION_OPERATOR | BOOLEAN_OPERATOR )", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ./CFScript.g:467:4: MATH_OPERATOR
                    {
                    mMATH_OPERATOR(); 

                    }
                    break;
                case 2 :
                    // ./CFScript.g:467:20: STRING_OPERATOR
                    {
                    mSTRING_OPERATOR(); 

                    }
                    break;
                case 3 :
                    // ./CFScript.g:467:38: CONDITION_OPERATOR
                    {
                    mCONDITION_OPERATOR(); 

                    }
                    break;
                case 4 :
                    // ./CFScript.g:467:59: BOOLEAN_OPERATOR
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
            // ./CFScript.g:471:2: ( ',' )
            // ./CFScript.g:471:2: ','
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
            // ./CFScript.g:476:2: ( ';' )
            // ./CFScript.g:476:2: ';'
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
            // ./CFScript.g:480:2: ( '#' )
            // ./CFScript.g:480:2: '#'
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
            // ./CFScript.g:485:2: ( '(' )
            // ./CFScript.g:485:2: '('
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
            // ./CFScript.g:490:2: ( ')' )
            // ./CFScript.g:490:2: ')'
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
            // ./CFScript.g:495:2: ( '[' )
            // ./CFScript.g:495:2: '['
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
            // ./CFScript.g:500:2: ( ']' )
            // ./CFScript.g:500:2: ']'
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
            // ./CFScript.g:505:2: ( '{' )
            // ./CFScript.g:505:2: '{'
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
            // ./CFScript.g:510:2: ( '}' )
            // ./CFScript.g:510:2: '}'
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
            // ./CFScript.g:515:2: ( '.' )
            // ./CFScript.g:515:2: '.'
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
            // ./CFScript.g:520:2: ( 'var' )
            // ./CFScript.g:520:2: 'var'
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
            // ./CFScript.g:525:2: ( ( DIGIT )+ ( DOT ( DIGIT )+ )? )
            // ./CFScript.g:525:2: ( DIGIT )+ ( DOT ( DIGIT )+ )?
            {
            // ./CFScript.g:525:2: ( DIGIT )+
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
            	    // ./CFScript.g:525:2: DIGIT
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

            // ./CFScript.g:525:8: ( DOT ( DIGIT )+ )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='.') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ./CFScript.g:525:9: DOT ( DIGIT )+
                    {
                    mDOT(); 
                    // ./CFScript.g:525:13: ( DIGIT )+
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
                    	    // ./CFScript.g:525:13: DIGIT
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
            // ./CFScript.g:530:2: ( '\"\"' )
            // ./CFScript.g:530:2: '\"\"'
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
            // ./CFScript.g:535:2: ( '\\'\\'' )
            // ./CFScript.g:535:2: '\\'\\''
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
            // ./CFScript.g:540:2: ( '\"' )
            // ./CFScript.g:540:2: '\"'
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
            // ./CFScript.g:544:2: ( '\\'' )
            // ./CFScript.g:544:2: '\\''
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
            // ./CFScript.g:549:2: ( ( LETTER | UNDERSCORE ) ( LETTER | DIGIT | UNDERSCORE )* )
            // ./CFScript.g:549:2: ( LETTER | UNDERSCORE ) ( LETTER | DIGIT | UNDERSCORE )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

            // ./CFScript.g:549:24: ( LETTER | DIGIT | UNDERSCORE )*
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

    // $ANTLR start MATH_OPERATOR
    public final void mMATH_OPERATOR() throws RecognitionException {
    traceIn("MATH_OPERATOR", 36);
        try {
            // ./CFScript.g:566:2: ( ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' ) )
            // ./CFScript.g:566:2: ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' )
            {
            // ./CFScript.g:566:2: ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' )
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
                    new NoViableAltException("566:2: ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' )", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // ./CFScript.g:566:3: '+'
                    {
                    match('+'); 

                    }
                    break;
                case 2 :
                    // ./CFScript.g:566:9: '*'
                    {
                    match('*'); 

                    }
                    break;
                case 3 :
                    // ./CFScript.g:566:15: '\\/'
                    {
                    match('/'); 

                    }
                    break;
                case 4 :
                    // ./CFScript.g:566:22: '\\\\'
                    {
                    match('\\'); 

                    }
                    break;
                case 5 :
                    // ./CFScript.g:566:29: '^'
                    {
                    match('^'); 

                    }
                    break;
                case 6 :
                    // ./CFScript.g:566:35: 'mod'
                    {
                    match("mod"); 


                    }
                    break;
                case 7 :
                    // ./CFScript.g:566:43: '-'
                    {
                    match('-'); 

                    }
                    break;

            }


            }

        }
        finally {
    traceOut("MATH_OPERATOR", 36);
        }
    }
    // $ANTLR end MATH_OPERATOR

    // $ANTLR start STRING_OPERATOR
    public final void mSTRING_OPERATOR() throws RecognitionException {
    traceIn("STRING_OPERATOR", 37);
        try {
            // ./CFScript.g:570:2: ( '&' )
            // ./CFScript.g:570:2: '&'
            {
            match('&'); 

            }

        }
        finally {
    traceOut("STRING_OPERATOR", 37);
        }
    }
    // $ANTLR end STRING_OPERATOR

    // $ANTLR start CONDITION_OPERATOR
    public final void mCONDITION_OPERATOR() throws RecognitionException {
    traceIn("CONDITION_OPERATOR", 38);
        try {
            // ./CFScript.g:574:2: ( ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' ) )
            // ./CFScript.g:574:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )
            {
            // ./CFScript.g:574:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )
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
                        new NoViableAltException("574:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )", 7, 4, input);

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
                        new NoViableAltException("574:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )", 7, 5, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("574:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // ./CFScript.g:574:3: 'eq'
                    {
                    match("eq"); 


                    }
                    break;
                case 2 :
                    // ./CFScript.g:574:8: 'neq'
                    {
                    match("neq"); 


                    }
                    break;
                case 3 :
                    // ./CFScript.g:574:14: 'is'
                    {
                    match("is"); 


                    }
                    break;
                case 4 :
                    // ./CFScript.g:574:19: 'gt'
                    {
                    match("gt"); 


                    }
                    break;
                case 5 :
                    // ./CFScript.g:574:24: 'lt'
                    {
                    match("lt"); 


                    }
                    break;
                case 6 :
                    // ./CFScript.g:574:29: 'lte'
                    {
                    match("lte"); 


                    }
                    break;
                case 7 :
                    // ./CFScript.g:574:35: 'gte'
                    {
                    match("gte"); 


                    }
                    break;

            }


            }

        }
        finally {
    traceOut("CONDITION_OPERATOR", 38);
        }
    }
    // $ANTLR end CONDITION_OPERATOR

    // $ANTLR start BOOLEAN_OPERATOR
    public final void mBOOLEAN_OPERATOR() throws RecognitionException {
    traceIn("BOOLEAN_OPERATOR", 39);
        try {
            // ./CFScript.g:579:2: ( ( 'or' | 'and' | 'xor' | 'eqv' | 'imp' ) )
            // ./CFScript.g:579:2: ( 'or' | 'and' | 'xor' | 'eqv' | 'imp' )
            {
            // ./CFScript.g:579:2: ( 'or' | 'and' | 'xor' | 'eqv' | 'imp' )
            int alt8=5;
            switch ( input.LA(1) ) {
            case 'o':
                {
                alt8=1;
                }
                break;
            case 'a':
                {
                alt8=2;
                }
                break;
            case 'x':
                {
                alt8=3;
                }
                break;
            case 'e':
                {
                alt8=4;
                }
                break;
            case 'i':
                {
                alt8=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("579:2: ( 'or' | 'and' | 'xor' | 'eqv' | 'imp' )", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // ./CFScript.g:579:3: 'or'
                    {
                    match("or"); 


                    }
                    break;
                case 2 :
                    // ./CFScript.g:579:8: 'and'
                    {
                    match("and"); 


                    }
                    break;
                case 3 :
                    // ./CFScript.g:579:14: 'xor'
                    {
                    match("xor"); 


                    }
                    break;
                case 4 :
                    // ./CFScript.g:579:20: 'eqv'
                    {
                    match("eqv"); 


                    }
                    break;
                case 5 :
                    // ./CFScript.g:579:26: 'imp'
                    {
                    match("imp"); 


                    }
                    break;

            }


            }

        }
        finally {
    traceOut("BOOLEAN_OPERATOR", 39);
        }
    }
    // $ANTLR end BOOLEAN_OPERATOR

    // $ANTLR start UNDERSCORE
    public final void mUNDERSCORE() throws RecognitionException {
    traceIn("UNDERSCORE", 40);
        try {
            // ./CFScript.g:584:2: ( '_' )
            // ./CFScript.g:584:2: '_'
            {
            match('_'); 

            }

        }
        finally {
    traceOut("UNDERSCORE", 40);
        }
    }
    // $ANTLR end UNDERSCORE

    // $ANTLR start DIGIT
    public final void mDIGIT() throws RecognitionException {
    traceIn("DIGIT", 41);
        try {
            // ./CFScript.g:589:2: ( '0' .. '9' )
            // ./CFScript.g:589:2: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
    traceOut("DIGIT", 41);
        }
    }
    // $ANTLR end DIGIT

    // $ANTLR start LETTER
    public final void mLETTER() throws RecognitionException {
    traceIn("LETTER", 42);
        try {
            // ./CFScript.g:594:2: ( 'a' .. 'z' | 'A' .. 'Z' )
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
    traceOut("LETTER", 42);
        }
    }
    // $ANTLR end LETTER

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
    traceIn("WS", 43);
        try {
            int _type = WS;
            // ./CFScript.g:601:2: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // ./CFScript.g:601:2: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
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
    traceOut("WS", 43);
        }
    }
    // $ANTLR end WS

    // $ANTLR start COMMENT
    public final void mCOMMENT() throws RecognitionException {
    traceIn("COMMENT", 44);
        try {
            int _type = COMMENT;
            // ./CFScript.g:609:2: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ./CFScript.g:609:2: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // ./CFScript.g:609:7: ( options {greedy=false; } : . )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0=='*') ) {
                    int LA9_1 = input.LA(2);

                    if ( (LA9_1=='/') ) {
                        alt9=2;
                    }
                    else if ( ((LA9_1>='\u0000' && LA9_1<='.')||(LA9_1>='0' && LA9_1<='\uFFFE')) ) {
                        alt9=1;
                    }


                }
                else if ( ((LA9_0>='\u0000' && LA9_0<=')')||(LA9_0>='+' && LA9_0<='\uFFFE')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ./CFScript.g:609:35: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            match("*/"); 


            		channel=COMMENT_CHANNEL; //90 is the comment channel
            	

            }

            this.type = _type;
        }
        finally {
    traceOut("COMMENT", 44);
        }
    }
    // $ANTLR end COMMENT

    // $ANTLR start LINE_COMMENT
    public final void mLINE_COMMENT() throws RecognitionException {
    traceIn("LINE_COMMENT", 45);
        try {
            int _type = LINE_COMMENT;
            // ./CFScript.g:617:2: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // ./CFScript.g:617:2: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // ./CFScript.g:617:7: (~ ( '\\n' | '\\r' ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='\u0000' && LA10_0<='\t')||(LA10_0>='\u000B' && LA10_0<='\f')||(LA10_0>='\u000E' && LA10_0<='\uFFFE')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ./CFScript.g:617:7: ~ ( '\\n' | '\\r' )
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
            	    break loop10;
                }
            } while (true);

            // ./CFScript.g:617:21: ( '\\r' )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0=='\r') ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ./CFScript.g:617:21: '\\r'
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
    traceOut("LINE_COMMENT", 45);
        }
    }
    // $ANTLR end LINE_COMMENT

    // $ANTLR start OTHER
    public final void mOTHER() throws RecognitionException {
    traceIn("OTHER", 46);
        try {
            int _type = OTHER;
            // ./CFScript.g:625:2: ( ( options {greedy=false; } : . ) )
            // ./CFScript.g:625:2: ( options {greedy=false; } : . )
            {
            // ./CFScript.g:625:2: ( options {greedy=false; } : . )
            // ./CFScript.g:625:29: .
            {
            matchAny(); 

            }


            }

            this.type = _type;
        }
        finally {
    traceOut("OTHER", 46);
        }
    }
    // $ANTLR end OTHER

    public void mTokens() throws RecognitionException {
        // ./CFScript.g:1:10: ( FUNCTION | IF | ELSE | TRY | CATCH | RETURN | FOR | IN | WHILE | DO | NOT | EQUALS | SWITCH | CASE | DEFAULT | BREAK | COLON | OPERATOR | COMMA | SEMI_COLON | HASH | OPEN_PAREN | CLOSE_PAREN | OPEN_SQUARE | CLOSE_SQUARE | OPEN_CURLY | CLOSE_CURLY | DOT | VAR | NUMBER | ESCAPE_DOUBLE_QUOTE | ESCAPE_SINGLE_QUOTE | DOUBLE_QUOTE | SINGLE_QUOTE | IDENTIFIER | WS | COMMENT | LINE_COMMENT | OTHER )
        int alt12=39;
        int LA12_0 = input.LA(1);

        if ( (LA12_0=='f') ) {
            switch ( input.LA(2) ) {
            case 'o':
                {
                int LA12_44 = input.LA(3);

                if ( (LA12_44=='r') ) {
                    int LA12_91 = input.LA(4);

                    if ( ((LA12_91>='0' && LA12_91<='9')||(LA12_91>='A' && LA12_91<='Z')||LA12_91=='_'||(LA12_91>='a' && LA12_91<='z')) ) {
                        alt12=35;
                    }
                    else {
                        alt12=7;}
                }
                else {
                    alt12=35;}
                }
                break;
            case 'u':
                {
                int LA12_45 = input.LA(3);

                if ( (LA12_45=='n') ) {
                    int LA12_92 = input.LA(4);

                    if ( (LA12_92=='c') ) {
                        int LA12_116 = input.LA(5);

                        if ( (LA12_116=='t') ) {
                            int LA12_128 = input.LA(6);

                            if ( (LA12_128=='i') ) {
                                int LA12_137 = input.LA(7);

                                if ( (LA12_137=='o') ) {
                                    int LA12_144 = input.LA(8);

                                    if ( (LA12_144=='n') ) {
                                        int LA12_148 = input.LA(9);

                                        if ( ((LA12_148>='0' && LA12_148<='9')||(LA12_148>='A' && LA12_148<='Z')||LA12_148=='_'||(LA12_148>='a' && LA12_148<='z')) ) {
                                            alt12=35;
                                        }
                                        else {
                                            alt12=1;}
                                    }
                                    else {
                                        alt12=35;}
                                }
                                else {
                                    alt12=35;}
                            }
                            else {
                                alt12=35;}
                        }
                        else {
                            alt12=35;}
                    }
                    else {
                        alt12=35;}
                }
                else {
                    alt12=35;}
                }
                break;
            default:
                alt12=35;}

        }
        else if ( (LA12_0=='i') ) {
            switch ( input.LA(2) ) {
            case 'f':
                {
                int LA12_47 = input.LA(3);

                if ( ((LA12_47>='0' && LA12_47<='9')||(LA12_47>='A' && LA12_47<='Z')||LA12_47=='_'||(LA12_47>='a' && LA12_47<='z')) ) {
                    alt12=35;
                }
                else {
                    alt12=2;}
                }
                break;
            case 'm':
                {
                int LA12_48 = input.LA(3);

                if ( (LA12_48=='p') ) {
                    int LA12_94 = input.LA(4);

                    if ( ((LA12_94>='0' && LA12_94<='9')||(LA12_94>='A' && LA12_94<='Z')||LA12_94=='_'||(LA12_94>='a' && LA12_94<='z')) ) {
                        alt12=35;
                    }
                    else {
                        alt12=18;}
                }
                else {
                    alt12=35;}
                }
                break;
            case 'n':
                {
                int LA12_49 = input.LA(3);

                if ( ((LA12_49>='0' && LA12_49<='9')||(LA12_49>='A' && LA12_49<='Z')||LA12_49=='_'||(LA12_49>='a' && LA12_49<='z')) ) {
                    alt12=35;
                }
                else {
                    alt12=8;}
                }
                break;
            case 's':
                {
                int LA12_50 = input.LA(3);

                if ( ((LA12_50>='0' && LA12_50<='9')||(LA12_50>='A' && LA12_50<='Z')||LA12_50=='_'||(LA12_50>='a' && LA12_50<='z')) ) {
                    alt12=35;
                }
                else {
                    alt12=18;}
                }
                break;
            default:
                alt12=35;}

        }
        else if ( (LA12_0=='e') ) {
            switch ( input.LA(2) ) {
            case 'l':
                {
                int LA12_51 = input.LA(3);

                if ( (LA12_51=='s') ) {
                    int LA12_96 = input.LA(4);

                    if ( (LA12_96=='e') ) {
                        int LA12_117 = input.LA(5);

                        if ( ((LA12_117>='0' && LA12_117<='9')||(LA12_117>='A' && LA12_117<='Z')||LA12_117=='_'||(LA12_117>='a' && LA12_117<='z')) ) {
                            alt12=35;
                        }
                        else {
                            alt12=3;}
                    }
                    else {
                        alt12=35;}
                }
                else {
                    alt12=35;}
                }
                break;
            case 'q':
                {
                switch ( input.LA(3) ) {
                case 'v':
                    {
                    int LA12_97 = input.LA(4);

                    if ( ((LA12_97>='0' && LA12_97<='9')||(LA12_97>='A' && LA12_97<='Z')||LA12_97=='_'||(LA12_97>='a' && LA12_97<='z')) ) {
                        alt12=35;
                    }
                    else {
                        alt12=18;}
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
                case 'e':
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
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    {
                    alt12=35;
                    }
                    break;
                default:
                    alt12=18;}

                }
                break;
            default:
                alt12=35;}

        }
        else if ( (LA12_0=='t') ) {
            int LA12_4 = input.LA(2);

            if ( (LA12_4=='r') ) {
                int LA12_53 = input.LA(3);

                if ( (LA12_53=='y') ) {
                    int LA12_98 = input.LA(4);

                    if ( ((LA12_98>='0' && LA12_98<='9')||(LA12_98>='A' && LA12_98<='Z')||LA12_98=='_'||(LA12_98>='a' && LA12_98<='z')) ) {
                        alt12=35;
                    }
                    else {
                        alt12=4;}
                }
                else {
                    alt12=35;}
            }
            else {
                alt12=35;}
        }
        else if ( (LA12_0=='c') ) {
            int LA12_5 = input.LA(2);

            if ( (LA12_5=='a') ) {
                switch ( input.LA(3) ) {
                case 't':
                    {
                    int LA12_99 = input.LA(4);

                    if ( (LA12_99=='c') ) {
                        int LA12_119 = input.LA(5);

                        if ( (LA12_119=='h') ) {
                            int LA12_130 = input.LA(6);

                            if ( ((LA12_130>='0' && LA12_130<='9')||(LA12_130>='A' && LA12_130<='Z')||LA12_130=='_'||(LA12_130>='a' && LA12_130<='z')) ) {
                                alt12=35;
                            }
                            else {
                                alt12=5;}
                        }
                        else {
                            alt12=35;}
                    }
                    else {
                        alt12=35;}
                    }
                    break;
                case 's':
                    {
                    int LA12_100 = input.LA(4);

                    if ( (LA12_100=='e') ) {
                        int LA12_120 = input.LA(5);

                        if ( ((LA12_120>='0' && LA12_120<='9')||(LA12_120>='A' && LA12_120<='Z')||LA12_120=='_'||(LA12_120>='a' && LA12_120<='z')) ) {
                            alt12=35;
                        }
                        else {
                            alt12=14;}
                    }
                    else {
                        alt12=35;}
                    }
                    break;
                default:
                    alt12=35;}

            }
            else {
                alt12=35;}
        }
        else if ( (LA12_0=='r') ) {
            int LA12_6 = input.LA(2);

            if ( (LA12_6=='e') ) {
                int LA12_55 = input.LA(3);

                if ( (LA12_55=='t') ) {
                    int LA12_101 = input.LA(4);

                    if ( (LA12_101=='u') ) {
                        int LA12_121 = input.LA(5);

                        if ( (LA12_121=='r') ) {
                            int LA12_132 = input.LA(6);

                            if ( (LA12_132=='n') ) {
                                int LA12_139 = input.LA(7);

                                if ( ((LA12_139>='0' && LA12_139<='9')||(LA12_139>='A' && LA12_139<='Z')||LA12_139=='_'||(LA12_139>='a' && LA12_139<='z')) ) {
                                    alt12=35;
                                }
                                else {
                                    alt12=6;}
                            }
                            else {
                                alt12=35;}
                        }
                        else {
                            alt12=35;}
                    }
                    else {
                        alt12=35;}
                }
                else {
                    alt12=35;}
            }
            else {
                alt12=35;}
        }
        else if ( (LA12_0=='w') ) {
            int LA12_7 = input.LA(2);

            if ( (LA12_7=='h') ) {
                int LA12_56 = input.LA(3);

                if ( (LA12_56=='i') ) {
                    int LA12_102 = input.LA(4);

                    if ( (LA12_102=='l') ) {
                        int LA12_122 = input.LA(5);

                        if ( (LA12_122=='e') ) {
                            int LA12_133 = input.LA(6);

                            if ( ((LA12_133>='0' && LA12_133<='9')||(LA12_133>='A' && LA12_133<='Z')||LA12_133=='_'||(LA12_133>='a' && LA12_133<='z')) ) {
                                alt12=35;
                            }
                            else {
                                alt12=9;}
                        }
                        else {
                            alt12=35;}
                    }
                    else {
                        alt12=35;}
                }
                else {
                    alt12=35;}
            }
            else {
                alt12=35;}
        }
        else if ( (LA12_0=='d') ) {
            switch ( input.LA(2) ) {
            case 'e':
                {
                int LA12_57 = input.LA(3);

                if ( (LA12_57=='f') ) {
                    int LA12_103 = input.LA(4);

                    if ( (LA12_103=='a') ) {
                        int LA12_123 = input.LA(5);

                        if ( (LA12_123=='u') ) {
                            int LA12_134 = input.LA(6);

                            if ( (LA12_134=='l') ) {
                                int LA12_141 = input.LA(7);

                                if ( (LA12_141=='t') ) {
                                    int LA12_146 = input.LA(8);

                                    if ( ((LA12_146>='0' && LA12_146<='9')||(LA12_146>='A' && LA12_146<='Z')||LA12_146=='_'||(LA12_146>='a' && LA12_146<='z')) ) {
                                        alt12=35;
                                    }
                                    else {
                                        alt12=15;}
                                }
                                else {
                                    alt12=35;}
                            }
                            else {
                                alt12=35;}
                        }
                        else {
                            alt12=35;}
                    }
                    else {
                        alt12=35;}
                }
                else {
                    alt12=35;}
                }
                break;
            case 'o':
                {
                int LA12_58 = input.LA(3);

                if ( ((LA12_58>='0' && LA12_58<='9')||(LA12_58>='A' && LA12_58<='Z')||LA12_58=='_'||(LA12_58>='a' && LA12_58<='z')) ) {
                    alt12=35;
                }
                else {
                    alt12=10;}
                }
                break;
            default:
                alt12=35;}

        }
        else if ( (LA12_0=='n') ) {
            switch ( input.LA(2) ) {
            case 'o':
                {
                int LA12_59 = input.LA(3);

                if ( (LA12_59=='t') ) {
                    int LA12_105 = input.LA(4);

                    if ( ((LA12_105>='0' && LA12_105<='9')||(LA12_105>='A' && LA12_105<='Z')||LA12_105=='_'||(LA12_105>='a' && LA12_105<='z')) ) {
                        alt12=35;
                    }
                    else {
                        alt12=11;}
                }
                else {
                    alt12=35;}
                }
                break;
            case 'e':
                {
                int LA12_60 = input.LA(3);

                if ( (LA12_60=='q') ) {
                    int LA12_106 = input.LA(4);

                    if ( ((LA12_106>='0' && LA12_106<='9')||(LA12_106>='A' && LA12_106<='Z')||LA12_106=='_'||(LA12_106>='a' && LA12_106<='z')) ) {
                        alt12=35;
                    }
                    else {
                        alt12=18;}
                }
                else {
                    alt12=35;}
                }
                break;
            default:
                alt12=35;}

        }
        else if ( (LA12_0=='=') ) {
            alt12=12;
        }
        else if ( (LA12_0=='s') ) {
            int LA12_11 = input.LA(2);

            if ( (LA12_11=='w') ) {
                int LA12_62 = input.LA(3);

                if ( (LA12_62=='i') ) {
                    int LA12_107 = input.LA(4);

                    if ( (LA12_107=='t') ) {
                        int LA12_125 = input.LA(5);

                        if ( (LA12_125=='c') ) {
                            int LA12_135 = input.LA(6);

                            if ( (LA12_135=='h') ) {
                                int LA12_142 = input.LA(7);

                                if ( ((LA12_142>='0' && LA12_142<='9')||(LA12_142>='A' && LA12_142<='Z')||LA12_142=='_'||(LA12_142>='a' && LA12_142<='z')) ) {
                                    alt12=35;
                                }
                                else {
                                    alt12=13;}
                            }
                            else {
                                alt12=35;}
                        }
                        else {
                            alt12=35;}
                    }
                    else {
                        alt12=35;}
                }
                else {
                    alt12=35;}
            }
            else {
                alt12=35;}
        }
        else if ( (LA12_0=='b') ) {
            int LA12_12 = input.LA(2);

            if ( (LA12_12=='r') ) {
                int LA12_63 = input.LA(3);

                if ( (LA12_63=='e') ) {
                    int LA12_108 = input.LA(4);

                    if ( (LA12_108=='a') ) {
                        int LA12_126 = input.LA(5);

                        if ( (LA12_126=='k') ) {
                            int LA12_136 = input.LA(6);

                            if ( ((LA12_136>='0' && LA12_136<='9')||(LA12_136>='A' && LA12_136<='Z')||LA12_136=='_'||(LA12_136>='a' && LA12_136<='z')) ) {
                                alt12=35;
                            }
                            else {
                                alt12=16;}
                        }
                        else {
                            alt12=35;}
                    }
                    else {
                        alt12=35;}
                }
                else {
                    alt12=35;}
            }
            else {
                alt12=35;}
        }
        else if ( (LA12_0==':') ) {
            alt12=17;
        }
        else if ( (LA12_0=='+') ) {
            alt12=18;
        }
        else if ( (LA12_0=='*') ) {
            alt12=18;
        }
        else if ( (LA12_0=='/') ) {
            switch ( input.LA(2) ) {
            case '/':
                {
                alt12=38;
                }
                break;
            case '*':
                {
                alt12=37;
                }
                break;
            default:
                alt12=18;}

        }
        else if ( (LA12_0=='\\') ) {
            alt12=18;
        }
        else if ( (LA12_0=='^') ) {
            alt12=18;
        }
        else if ( (LA12_0=='m') ) {
            int LA12_19 = input.LA(2);

            if ( (LA12_19=='o') ) {
                int LA12_68 = input.LA(3);

                if ( (LA12_68=='d') ) {
                    int LA12_109 = input.LA(4);

                    if ( ((LA12_109>='0' && LA12_109<='9')||(LA12_109>='A' && LA12_109<='Z')||LA12_109=='_'||(LA12_109>='a' && LA12_109<='z')) ) {
                        alt12=35;
                    }
                    else {
                        alt12=18;}
                }
                else {
                    alt12=35;}
            }
            else {
                alt12=35;}
        }
        else if ( (LA12_0=='-') ) {
            alt12=18;
        }
        else if ( (LA12_0=='&') ) {
            alt12=18;
        }
        else if ( (LA12_0=='g') ) {
            int LA12_22 = input.LA(2);

            if ( (LA12_22=='t') ) {
                switch ( input.LA(3) ) {
                case 'e':
                    {
                    int LA12_110 = input.LA(4);

                    if ( ((LA12_110>='0' && LA12_110<='9')||(LA12_110>='A' && LA12_110<='Z')||LA12_110=='_'||(LA12_110>='a' && LA12_110<='z')) ) {
                        alt12=35;
                    }
                    else {
                        alt12=18;}
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
                    alt12=35;
                    }
                    break;
                default:
                    alt12=18;}

            }
            else {
                alt12=35;}
        }
        else if ( (LA12_0=='l') ) {
            int LA12_23 = input.LA(2);

            if ( (LA12_23=='t') ) {
                switch ( input.LA(3) ) {
                case 'e':
                    {
                    int LA12_111 = input.LA(4);

                    if ( ((LA12_111>='0' && LA12_111<='9')||(LA12_111>='A' && LA12_111<='Z')||LA12_111=='_'||(LA12_111>='a' && LA12_111<='z')) ) {
                        alt12=35;
                    }
                    else {
                        alt12=18;}
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
                    alt12=35;
                    }
                    break;
                default:
                    alt12=18;}

            }
            else {
                alt12=35;}
        }
        else if ( (LA12_0=='o') ) {
            int LA12_24 = input.LA(2);

            if ( (LA12_24=='r') ) {
                int LA12_71 = input.LA(3);

                if ( ((LA12_71>='0' && LA12_71<='9')||(LA12_71>='A' && LA12_71<='Z')||LA12_71=='_'||(LA12_71>='a' && LA12_71<='z')) ) {
                    alt12=35;
                }
                else {
                    alt12=18;}
            }
            else {
                alt12=35;}
        }
        else if ( (LA12_0=='a') ) {
            int LA12_25 = input.LA(2);

            if ( (LA12_25=='n') ) {
                int LA12_72 = input.LA(3);

                if ( (LA12_72=='d') ) {
                    int LA12_112 = input.LA(4);

                    if ( ((LA12_112>='0' && LA12_112<='9')||(LA12_112>='A' && LA12_112<='Z')||LA12_112=='_'||(LA12_112>='a' && LA12_112<='z')) ) {
                        alt12=35;
                    }
                    else {
                        alt12=18;}
                }
                else {
                    alt12=35;}
            }
            else {
                alt12=35;}
        }
        else if ( (LA12_0=='x') ) {
            int LA12_26 = input.LA(2);

            if ( (LA12_26=='o') ) {
                int LA12_73 = input.LA(3);

                if ( (LA12_73=='r') ) {
                    int LA12_113 = input.LA(4);

                    if ( ((LA12_113>='0' && LA12_113<='9')||(LA12_113>='A' && LA12_113<='Z')||LA12_113=='_'||(LA12_113>='a' && LA12_113<='z')) ) {
                        alt12=35;
                    }
                    else {
                        alt12=18;}
                }
                else {
                    alt12=35;}
            }
            else {
                alt12=35;}
        }
        else if ( (LA12_0==',') ) {
            alt12=19;
        }
        else if ( (LA12_0==';') ) {
            alt12=20;
        }
        else if ( (LA12_0=='#') ) {
            alt12=21;
        }
        else if ( (LA12_0=='(') ) {
            alt12=22;
        }
        else if ( (LA12_0==')') ) {
            alt12=23;
        }
        else if ( (LA12_0=='[') ) {
            alt12=24;
        }
        else if ( (LA12_0==']') ) {
            alt12=25;
        }
        else if ( (LA12_0=='{') ) {
            alt12=26;
        }
        else if ( (LA12_0=='}') ) {
            alt12=27;
        }
        else if ( (LA12_0=='.') ) {
            alt12=28;
        }
        else if ( (LA12_0=='v') ) {
            int LA12_37 = input.LA(2);

            if ( (LA12_37=='a') ) {
                int LA12_84 = input.LA(3);

                if ( (LA12_84=='r') ) {
                    int LA12_114 = input.LA(4);

                    if ( ((LA12_114>='0' && LA12_114<='9')||(LA12_114>='A' && LA12_114<='Z')||LA12_114=='_'||(LA12_114>='a' && LA12_114<='z')) ) {
                        alt12=35;
                    }
                    else {
                        alt12=29;}
                }
                else {
                    alt12=35;}
            }
            else {
                alt12=35;}
        }
        else if ( ((LA12_0>='0' && LA12_0<='9')) ) {
            alt12=30;
        }
        else if ( (LA12_0=='\"') ) {
            int LA12_39 = input.LA(2);

            if ( (LA12_39=='\"') ) {
                alt12=31;
            }
            else {
                alt12=33;}
        }
        else if ( (LA12_0=='\'') ) {
            int LA12_40 = input.LA(2);

            if ( (LA12_40=='\'') ) {
                alt12=32;
            }
            else {
                alt12=34;}
        }
        else if ( ((LA12_0>='A' && LA12_0<='Z')||LA12_0=='_'||LA12_0=='h'||(LA12_0>='j' && LA12_0<='k')||(LA12_0>='p' && LA12_0<='q')||LA12_0=='u'||(LA12_0>='y' && LA12_0<='z')) ) {
            alt12=35;
        }
        else if ( ((LA12_0>='\t' && LA12_0<='\n')||(LA12_0>='\f' && LA12_0<='\r')||LA12_0==' ') ) {
            alt12=36;
        }
        else if ( ((LA12_0>='\u0000' && LA12_0<='\b')||LA12_0=='\u000B'||(LA12_0>='\u000E' && LA12_0<='\u001F')||LA12_0=='!'||(LA12_0>='$' && LA12_0<='%')||LA12_0=='<'||(LA12_0>='>' && LA12_0<='@')||LA12_0=='`'||LA12_0=='|'||(LA12_0>='~' && LA12_0<='\uFFFE')) ) {
            alt12=39;
        }
        else {
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( FUNCTION | IF | ELSE | TRY | CATCH | RETURN | FOR | IN | WHILE | DO | NOT | EQUALS | SWITCH | CASE | DEFAULT | BREAK | COLON | OPERATOR | COMMA | SEMI_COLON | HASH | OPEN_PAREN | CLOSE_PAREN | OPEN_SQUARE | CLOSE_SQUARE | OPEN_CURLY | CLOSE_CURLY | DOT | VAR | NUMBER | ESCAPE_DOUBLE_QUOTE | ESCAPE_SINGLE_QUOTE | DOUBLE_QUOTE | SINGLE_QUOTE | IDENTIFIER | WS | COMMENT | LINE_COMMENT | OTHER );", 12, 0, input);

            throw nvae;
        }
        switch (alt12) {
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
                // ./CFScript.g:1:297: WS
                {
                mWS(); 

                }
                break;
            case 37 :
                // ./CFScript.g:1:300: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 38 :
                // ./CFScript.g:1:308: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;
            case 39 :
                // ./CFScript.g:1:321: OTHER
                {
                mOTHER(); 

                }
                break;

        }

    }


 

}