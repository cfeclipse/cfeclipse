// $ANTLR 3.0 ./CFScript.g 2007-05-24 17:58:48

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
    public static final int WHILE=28;
    public static final int CLOSE_CURLY=31;
    public static final int LETTER=41;
    public static final int CASE=33;
    public static final int DO=29;
    public static final int FOR=27;
    public static final int EQUALS=9;
    public static final int NOT=14;
    public static final int EOF=-1;
    public static final int BREAK=36;
    public static final int IF=23;
    public static final int COMMA=22;
    public static final int IDENTIFIER=19;
    public static final int RETURN=12;
    public static final int OPEN_SQUARE=20;
    public static final int VAR=8;
    public static final int CLOSE_PAREN=11;
    public static final int MATH_OPERATOR=37;
    public static final int DIGIT=40;
    public static final int DOT=18;
    public static final int COMMENT=43;
    public static final int CLOSE_SQUARE=21;
    public static final int STRUCT_KEY=5;
    public static final int LINE_COMMENT=44;
    public static final int OPERATOR=13;
    public static final int SWITCH=32;
    public static final int ELSE=24;
    public static final int DEFAULT=35;
    public static final int NUMBER=15;
    public static final int HASH=17;
    public static final int OPEN_PAREN=10;
    public static final int SEMI_COLON=7;
    public static final int OPEN_CURLY=30;
    public static final int Tokens=45;
    public static final int TRY=25;
    public static final int ELSEIF=6;
    public static final int COLON=34;
    public static final int WS=42;
    public static final int BOOLEAN_OPERATOR=39;
    public static final int CATCH=26;
    public static final int FUNCTION_CALL=4;
    public static final int STRING_OPERATOR=38;
    public static final int STRING=16;
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
            // ./CFScript.g:300:2: ( 'if' )
            // ./CFScript.g:300:2: 'if'
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
            // ./CFScript.g:305:2: ( 'else' )
            // ./CFScript.g:305:2: 'else'
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
            // ./CFScript.g:310:2: ( 'try' )
            // ./CFScript.g:310:2: 'try'
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
            // ./CFScript.g:315:2: ( 'catch' )
            // ./CFScript.g:315:2: 'catch'
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
            // ./CFScript.g:319:2: ( 'return' )
            // ./CFScript.g:319:2: 'return'
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
            // ./CFScript.g:324:2: ( 'for' )
            // ./CFScript.g:324:2: 'for'
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
            // ./CFScript.g:329:2: ( 'while' )
            // ./CFScript.g:329:2: 'while'
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
            // ./CFScript.g:334:2: ( 'do' )
            // ./CFScript.g:334:2: 'do'
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
            // ./CFScript.g:338:2: ( 'not' )
            // ./CFScript.g:338:2: 'not'
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
            // ./CFScript.g:343:2: ( '=' )
            // ./CFScript.g:343:2: '='
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
            // ./CFScript.g:348:2: ( 'switch' )
            // ./CFScript.g:348:2: 'switch'
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
            // ./CFScript.g:353:2: ( 'case' )
            // ./CFScript.g:353:2: 'case'
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
            // ./CFScript.g:358:2: ( 'default' )
            // ./CFScript.g:358:2: 'default'
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
            // ./CFScript.g:363:2: ( 'break' )
            // ./CFScript.g:363:2: 'break'
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
            // ./CFScript.g:367:2: ( ':' )
            // ./CFScript.g:367:2: ':'
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
            // ./CFScript.g:372:2: ( ( MATH_OPERATOR | STRING_OPERATOR | BOOLEAN_OPERATOR ) )
            // ./CFScript.g:372:2: ( MATH_OPERATOR | STRING_OPERATOR | BOOLEAN_OPERATOR )
            {
            // ./CFScript.g:372:2: ( MATH_OPERATOR | STRING_OPERATOR | BOOLEAN_OPERATOR )
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
                    new NoViableAltException("372:2: ( MATH_OPERATOR | STRING_OPERATOR | BOOLEAN_OPERATOR )", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ./CFScript.g:372:4: MATH_OPERATOR
                    {
                    mMATH_OPERATOR(); 

                    }
                    break;
                case 2 :
                    // ./CFScript.g:372:20: STRING_OPERATOR
                    {
                    mSTRING_OPERATOR(); 

                    }
                    break;
                case 3 :
                    // ./CFScript.g:372:38: BOOLEAN_OPERATOR
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
            // ./CFScript.g:376:2: ( ',' )
            // ./CFScript.g:376:2: ','
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
            // ./CFScript.g:381:2: ( ';' )
            // ./CFScript.g:381:2: ';'
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
            // ./CFScript.g:385:2: ( '#' )
            // ./CFScript.g:385:2: '#'
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
            // ./CFScript.g:390:2: ( '(' )
            // ./CFScript.g:390:2: '('
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
            // ./CFScript.g:395:2: ( ')' )
            // ./CFScript.g:395:2: ')'
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
            // ./CFScript.g:400:2: ( '[' )
            // ./CFScript.g:400:2: '['
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
            // ./CFScript.g:405:2: ( ']' )
            // ./CFScript.g:405:2: ']'
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
            // ./CFScript.g:410:2: ( '{' )
            // ./CFScript.g:410:2: '{'
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
            // ./CFScript.g:415:2: ( '}' )
            // ./CFScript.g:415:2: '}'
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
            // ./CFScript.g:420:2: ( '.' )
            // ./CFScript.g:420:2: '.'
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
            // ./CFScript.g:425:2: ( 'var' )
            // ./CFScript.g:425:2: 'var'
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
            // ./CFScript.g:430:2: ( ( DIGIT )+ ( DOT ( DIGIT )+ )? )
            // ./CFScript.g:430:2: ( DIGIT )+ ( DOT ( DIGIT )+ )?
            {
            // ./CFScript.g:430:2: ( DIGIT )+
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
            	    // ./CFScript.g:430:2: DIGIT
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

            // ./CFScript.g:430:8: ( DOT ( DIGIT )+ )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='.') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ./CFScript.g:430:9: DOT ( DIGIT )+
                    {
                    mDOT(); 
                    // ./CFScript.g:430:13: ( DIGIT )+
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
                    	    // ./CFScript.g:430:13: DIGIT
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

    // $ANTLR start STRING
    public final void mSTRING() throws RecognitionException {
    traceIn("STRING", 29);
        try {
            int _type = STRING;
            // ./CFScript.g:435:2: ( '\"' ( options {greedy=false; } : . )* '\"' | '\\'' '\"' ( options {greedy=false; } : . )* '\\'' )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='\"') ) {
                alt7=1;
            }
            else if ( (LA7_0=='\'') ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("433:1: STRING : ( '\"' ( options {greedy=false; } : . )* '\"' | '\\'' '\"' ( options {greedy=false; } : . )* '\\'' );", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ./CFScript.g:435:2: '\"' ( options {greedy=false; } : . )* '\"'
                    {
                    match('\"'); 
                    // ./CFScript.g:435:6: ( options {greedy=false; } : . )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0=='\"') ) {
                            alt5=2;
                        }
                        else if ( ((LA5_0>='\u0000' && LA5_0<='!')||(LA5_0>='#' && LA5_0<='\uFFFE')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // ./CFScript.g:435:34: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // ./CFScript.g:437:2: '\\'' '\"' ( options {greedy=false; } : . )* '\\''
                    {
                    match('\''); 
                    match('\"'); 
                    // ./CFScript.g:437:11: ( options {greedy=false; } : . )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0=='\'') ) {
                            alt6=2;
                        }
                        else if ( ((LA6_0>='\u0000' && LA6_0<='&')||(LA6_0>='(' && LA6_0<='\uFFFE')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // ./CFScript.g:437:39: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }
            this.type = _type;
        }
        finally {
    traceOut("STRING", 29);
        }
    }
    // $ANTLR end STRING

    // $ANTLR start IDENTIFIER
    public final void mIDENTIFIER() throws RecognitionException {
    traceIn("IDENTIFIER", 30);
        try {
            int _type = IDENTIFIER;
            // ./CFScript.g:442:2: ( ( LETTER | '_' ) ( LETTER | DIGIT | '_' )* )
            // ./CFScript.g:442:2: ( LETTER | '_' ) ( LETTER | DIGIT | '_' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

            // ./CFScript.g:442:17: ( LETTER | DIGIT | '_' )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='0' && LA8_0<='9')||(LA8_0>='A' && LA8_0<='Z')||LA8_0=='_'||(LA8_0>='a' && LA8_0<='z')) ) {
                    alt8=1;
                }


                switch (alt8) {
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
            	    break loop8;
                }
            } while (true);


            }

            this.type = _type;
        }
        finally {
    traceOut("IDENTIFIER", 30);
        }
    }
    // $ANTLR end IDENTIFIER

    // $ANTLR start MATH_OPERATOR
    public final void mMATH_OPERATOR() throws RecognitionException {
    traceIn("MATH_OPERATOR", 31);
        try {
            // ./CFScript.g:449:2: ( ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' ) )
            // ./CFScript.g:449:2: ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' )
            {
            // ./CFScript.g:449:2: ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' )
            int alt9=7;
            switch ( input.LA(1) ) {
            case '+':
                {
                alt9=1;
                }
                break;
            case '*':
                {
                alt9=2;
                }
                break;
            case '/':
                {
                alt9=3;
                }
                break;
            case '\\':
                {
                alt9=4;
                }
                break;
            case '^':
                {
                alt9=5;
                }
                break;
            case 'm':
                {
                alt9=6;
                }
                break;
            case '-':
                {
                alt9=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("449:2: ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' )", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // ./CFScript.g:449:3: '+'
                    {
                    match('+'); 

                    }
                    break;
                case 2 :
                    // ./CFScript.g:449:9: '*'
                    {
                    match('*'); 

                    }
                    break;
                case 3 :
                    // ./CFScript.g:449:15: '\\/'
                    {
                    match('/'); 

                    }
                    break;
                case 4 :
                    // ./CFScript.g:449:22: '\\\\'
                    {
                    match('\\'); 

                    }
                    break;
                case 5 :
                    // ./CFScript.g:449:29: '^'
                    {
                    match('^'); 

                    }
                    break;
                case 6 :
                    // ./CFScript.g:449:35: 'mod'
                    {
                    match("mod"); 


                    }
                    break;
                case 7 :
                    // ./CFScript.g:449:43: '-'
                    {
                    match('-'); 

                    }
                    break;

            }


            }

        }
        finally {
    traceOut("MATH_OPERATOR", 31);
        }
    }
    // $ANTLR end MATH_OPERATOR

    // $ANTLR start STRING_OPERATOR
    public final void mSTRING_OPERATOR() throws RecognitionException {
    traceIn("STRING_OPERATOR", 32);
        try {
            // ./CFScript.g:453:2: ( '&' )
            // ./CFScript.g:453:2: '&'
            {
            match('&'); 

            }

        }
        finally {
    traceOut("STRING_OPERATOR", 32);
        }
    }
    // $ANTLR end STRING_OPERATOR

    // $ANTLR start BOOLEAN_OPERATOR
    public final void mBOOLEAN_OPERATOR() throws RecognitionException {
    traceIn("BOOLEAN_OPERATOR", 33);
        try {
            // ./CFScript.g:457:2: ( ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' ) )
            // ./CFScript.g:457:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )
            {
            // ./CFScript.g:457:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )
            int alt10=7;
            switch ( input.LA(1) ) {
            case 'e':
                {
                alt10=1;
                }
                break;
            case 'n':
                {
                alt10=2;
                }
                break;
            case 'i':
                {
                alt10=3;
                }
                break;
            case 'g':
                {
                int LA10_4 = input.LA(2);

                if ( (LA10_4=='t') ) {
                    int LA10_6 = input.LA(3);

                    if ( (LA10_6=='e') ) {
                        alt10=7;
                    }
                    else {
                        alt10=4;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("457:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )", 10, 4, input);

                    throw nvae;
                }
                }
                break;
            case 'l':
                {
                int LA10_5 = input.LA(2);

                if ( (LA10_5=='t') ) {
                    int LA10_7 = input.LA(3);

                    if ( (LA10_7=='e') ) {
                        alt10=6;
                    }
                    else {
                        alt10=5;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("457:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )", 10, 5, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("457:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // ./CFScript.g:457:3: 'eq'
                    {
                    match("eq"); 


                    }
                    break;
                case 2 :
                    // ./CFScript.g:457:8: 'neq'
                    {
                    match("neq"); 


                    }
                    break;
                case 3 :
                    // ./CFScript.g:457:14: 'is'
                    {
                    match("is"); 


                    }
                    break;
                case 4 :
                    // ./CFScript.g:457:19: 'gt'
                    {
                    match("gt"); 


                    }
                    break;
                case 5 :
                    // ./CFScript.g:457:24: 'lt'
                    {
                    match("lt"); 


                    }
                    break;
                case 6 :
                    // ./CFScript.g:457:29: 'lte'
                    {
                    match("lte"); 


                    }
                    break;
                case 7 :
                    // ./CFScript.g:457:35: 'gte'
                    {
                    match("gte"); 


                    }
                    break;

            }


            }

        }
        finally {
    traceOut("BOOLEAN_OPERATOR", 33);
        }
    }
    // $ANTLR end BOOLEAN_OPERATOR

    // $ANTLR start DIGIT
    public final void mDIGIT() throws RecognitionException {
    traceIn("DIGIT", 34);
        try {
            // ./CFScript.g:462:2: ( '0' .. '9' )
            // ./CFScript.g:462:2: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
    traceOut("DIGIT", 34);
        }
    }
    // $ANTLR end DIGIT

    // $ANTLR start LETTER
    public final void mLETTER() throws RecognitionException {
    traceIn("LETTER", 35);
        try {
            // ./CFScript.g:467:2: ( 'a' .. 'z' | 'A' .. 'Z' )
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
    traceOut("LETTER", 35);
        }
    }
    // $ANTLR end LETTER

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
    traceIn("WS", 36);
        try {
            int _type = WS;
            // ./CFScript.g:474:2: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // ./CFScript.g:474:2: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
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
    traceOut("WS", 36);
        }
    }
    // $ANTLR end WS

    // $ANTLR start COMMENT
    public final void mCOMMENT() throws RecognitionException {
    traceIn("COMMENT", 37);
        try {
            int _type = COMMENT;
            // ./CFScript.g:482:2: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ./CFScript.g:482:2: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // ./CFScript.g:482:7: ( options {greedy=false; } : . )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0=='*') ) {
                    int LA11_1 = input.LA(2);

                    if ( (LA11_1=='/') ) {
                        alt11=2;
                    }
                    else if ( ((LA11_1>='\u0000' && LA11_1<='.')||(LA11_1>='0' && LA11_1<='\uFFFE')) ) {
                        alt11=1;
                    }


                }
                else if ( ((LA11_0>='\u0000' && LA11_0<=')')||(LA11_0>='+' && LA11_0<='\uFFFE')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ./CFScript.g:482:35: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            match("*/"); 


            		channel=HIDDEN;
            	

            }

            this.type = _type;
        }
        finally {
    traceOut("COMMENT", 37);
        }
    }
    // $ANTLR end COMMENT

    // $ANTLR start LINE_COMMENT
    public final void mLINE_COMMENT() throws RecognitionException {
    traceIn("LINE_COMMENT", 38);
        try {
            int _type = LINE_COMMENT;
            // ./CFScript.g:490:2: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // ./CFScript.g:490:2: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // ./CFScript.g:490:7: (~ ( '\\n' | '\\r' ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>='\u0000' && LA12_0<='\t')||(LA12_0>='\u000B' && LA12_0<='\f')||(LA12_0>='\u000E' && LA12_0<='\uFFFE')) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // ./CFScript.g:490:7: ~ ( '\\n' | '\\r' )
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
            	    break loop12;
                }
            } while (true);

            // ./CFScript.g:490:21: ( '\\r' )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0=='\r') ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ./CFScript.g:490:21: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 

            		channel=HIDDEN;
            	

            }

            this.type = _type;
        }
        finally {
    traceOut("LINE_COMMENT", 38);
        }
    }
    // $ANTLR end LINE_COMMENT

    public void mTokens() throws RecognitionException {
        // ./CFScript.g:1:10: ( IF | ELSE | TRY | CATCH | RETURN | FOR | WHILE | DO | NOT | EQUALS | SWITCH | CASE | DEFAULT | BREAK | COLON | OPERATOR | COMMA | SEMI_COLON | HASH | OPEN_PAREN | CLOSE_PAREN | OPEN_SQUARE | CLOSE_SQUARE | OPEN_CURLY | CLOSE_CURLY | DOT | VAR | NUMBER | STRING | IDENTIFIER | WS | COMMENT | LINE_COMMENT )
        int alt14=33;
        switch ( input.LA(1) ) {
        case 'i':
            {
            switch ( input.LA(2) ) {
            case 's':
                {
                int LA14_34 = input.LA(3);

                if ( ((LA14_34>='0' && LA14_34<='9')||(LA14_34>='A' && LA14_34<='Z')||LA14_34=='_'||(LA14_34>='a' && LA14_34<='z')) ) {
                    alt14=30;
                }
                else {
                    alt14=16;}
                }
                break;
            case 'f':
                {
                int LA14_35 = input.LA(3);

                if ( ((LA14_35>='0' && LA14_35<='9')||(LA14_35>='A' && LA14_35<='Z')||LA14_35=='_'||(LA14_35>='a' && LA14_35<='z')) ) {
                    alt14=30;
                }
                else {
                    alt14=1;}
                }
                break;
            default:
                alt14=30;}

            }
            break;
        case 'e':
            {
            switch ( input.LA(2) ) {
            case 'q':
                {
                int LA14_36 = input.LA(3);

                if ( ((LA14_36>='0' && LA14_36<='9')||(LA14_36>='A' && LA14_36<='Z')||LA14_36=='_'||(LA14_36>='a' && LA14_36<='z')) ) {
                    alt14=30;
                }
                else {
                    alt14=16;}
                }
                break;
            case 'l':
                {
                int LA14_37 = input.LA(3);

                if ( (LA14_37=='s') ) {
                    int LA14_56 = input.LA(4);

                    if ( (LA14_56=='e') ) {
                        int LA14_73 = input.LA(5);

                        if ( ((LA14_73>='0' && LA14_73<='9')||(LA14_73>='A' && LA14_73<='Z')||LA14_73=='_'||(LA14_73>='a' && LA14_73<='z')) ) {
                            alt14=30;
                        }
                        else {
                            alt14=2;}
                    }
                    else {
                        alt14=30;}
                }
                else {
                    alt14=30;}
                }
                break;
            default:
                alt14=30;}

            }
            break;
        case 't':
            {
            int LA14_3 = input.LA(2);

            if ( (LA14_3=='r') ) {
                int LA14_38 = input.LA(3);

                if ( (LA14_38=='y') ) {
                    int LA14_57 = input.LA(4);

                    if ( ((LA14_57>='0' && LA14_57<='9')||(LA14_57>='A' && LA14_57<='Z')||LA14_57=='_'||(LA14_57>='a' && LA14_57<='z')) ) {
                        alt14=30;
                    }
                    else {
                        alt14=3;}
                }
                else {
                    alt14=30;}
            }
            else {
                alt14=30;}
            }
            break;
        case 'c':
            {
            int LA14_4 = input.LA(2);

            if ( (LA14_4=='a') ) {
                switch ( input.LA(3) ) {
                case 't':
                    {
                    int LA14_58 = input.LA(4);

                    if ( (LA14_58=='c') ) {
                        int LA14_75 = input.LA(5);

                        if ( (LA14_75=='h') ) {
                            int LA14_86 = input.LA(6);

                            if ( ((LA14_86>='0' && LA14_86<='9')||(LA14_86>='A' && LA14_86<='Z')||LA14_86=='_'||(LA14_86>='a' && LA14_86<='z')) ) {
                                alt14=30;
                            }
                            else {
                                alt14=4;}
                        }
                        else {
                            alt14=30;}
                    }
                    else {
                        alt14=30;}
                    }
                    break;
                case 's':
                    {
                    int LA14_59 = input.LA(4);

                    if ( (LA14_59=='e') ) {
                        int LA14_76 = input.LA(5);

                        if ( ((LA14_76>='0' && LA14_76<='9')||(LA14_76>='A' && LA14_76<='Z')||LA14_76=='_'||(LA14_76>='a' && LA14_76<='z')) ) {
                            alt14=30;
                        }
                        else {
                            alt14=12;}
                    }
                    else {
                        alt14=30;}
                    }
                    break;
                default:
                    alt14=30;}

            }
            else {
                alt14=30;}
            }
            break;
        case 'r':
            {
            int LA14_5 = input.LA(2);

            if ( (LA14_5=='e') ) {
                int LA14_40 = input.LA(3);

                if ( (LA14_40=='t') ) {
                    int LA14_60 = input.LA(4);

                    if ( (LA14_60=='u') ) {
                        int LA14_77 = input.LA(5);

                        if ( (LA14_77=='r') ) {
                            int LA14_88 = input.LA(6);

                            if ( (LA14_88=='n') ) {
                                int LA14_94 = input.LA(7);

                                if ( ((LA14_94>='0' && LA14_94<='9')||(LA14_94>='A' && LA14_94<='Z')||LA14_94=='_'||(LA14_94>='a' && LA14_94<='z')) ) {
                                    alt14=30;
                                }
                                else {
                                    alt14=5;}
                            }
                            else {
                                alt14=30;}
                        }
                        else {
                            alt14=30;}
                    }
                    else {
                        alt14=30;}
                }
                else {
                    alt14=30;}
            }
            else {
                alt14=30;}
            }
            break;
        case 'f':
            {
            int LA14_6 = input.LA(2);

            if ( (LA14_6=='o') ) {
                int LA14_41 = input.LA(3);

                if ( (LA14_41=='r') ) {
                    int LA14_61 = input.LA(4);

                    if ( ((LA14_61>='0' && LA14_61<='9')||(LA14_61>='A' && LA14_61<='Z')||LA14_61=='_'||(LA14_61>='a' && LA14_61<='z')) ) {
                        alt14=30;
                    }
                    else {
                        alt14=6;}
                }
                else {
                    alt14=30;}
            }
            else {
                alt14=30;}
            }
            break;
        case 'w':
            {
            int LA14_7 = input.LA(2);

            if ( (LA14_7=='h') ) {
                int LA14_42 = input.LA(3);

                if ( (LA14_42=='i') ) {
                    int LA14_62 = input.LA(4);

                    if ( (LA14_62=='l') ) {
                        int LA14_79 = input.LA(5);

                        if ( (LA14_79=='e') ) {
                            int LA14_89 = input.LA(6);

                            if ( ((LA14_89>='0' && LA14_89<='9')||(LA14_89>='A' && LA14_89<='Z')||LA14_89=='_'||(LA14_89>='a' && LA14_89<='z')) ) {
                                alt14=30;
                            }
                            else {
                                alt14=7;}
                        }
                        else {
                            alt14=30;}
                    }
                    else {
                        alt14=30;}
                }
                else {
                    alt14=30;}
            }
            else {
                alt14=30;}
            }
            break;
        case 'd':
            {
            switch ( input.LA(2) ) {
            case 'e':
                {
                int LA14_43 = input.LA(3);

                if ( (LA14_43=='f') ) {
                    int LA14_63 = input.LA(4);

                    if ( (LA14_63=='a') ) {
                        int LA14_80 = input.LA(5);

                        if ( (LA14_80=='u') ) {
                            int LA14_90 = input.LA(6);

                            if ( (LA14_90=='l') ) {
                                int LA14_96 = input.LA(7);

                                if ( (LA14_96=='t') ) {
                                    int LA14_100 = input.LA(8);

                                    if ( ((LA14_100>='0' && LA14_100<='9')||(LA14_100>='A' && LA14_100<='Z')||LA14_100=='_'||(LA14_100>='a' && LA14_100<='z')) ) {
                                        alt14=30;
                                    }
                                    else {
                                        alt14=13;}
                                }
                                else {
                                    alt14=30;}
                            }
                            else {
                                alt14=30;}
                        }
                        else {
                            alt14=30;}
                    }
                    else {
                        alt14=30;}
                }
                else {
                    alt14=30;}
                }
                break;
            case 'o':
                {
                int LA14_44 = input.LA(3);

                if ( ((LA14_44>='0' && LA14_44<='9')||(LA14_44>='A' && LA14_44<='Z')||LA14_44=='_'||(LA14_44>='a' && LA14_44<='z')) ) {
                    alt14=30;
                }
                else {
                    alt14=8;}
                }
                break;
            default:
                alt14=30;}

            }
            break;
        case 'n':
            {
            switch ( input.LA(2) ) {
            case 'o':
                {
                int LA14_45 = input.LA(3);

                if ( (LA14_45=='t') ) {
                    int LA14_65 = input.LA(4);

                    if ( ((LA14_65>='0' && LA14_65<='9')||(LA14_65>='A' && LA14_65<='Z')||LA14_65=='_'||(LA14_65>='a' && LA14_65<='z')) ) {
                        alt14=30;
                    }
                    else {
                        alt14=9;}
                }
                else {
                    alt14=30;}
                }
                break;
            case 'e':
                {
                int LA14_46 = input.LA(3);

                if ( (LA14_46=='q') ) {
                    int LA14_66 = input.LA(4);

                    if ( ((LA14_66>='0' && LA14_66<='9')||(LA14_66>='A' && LA14_66<='Z')||LA14_66=='_'||(LA14_66>='a' && LA14_66<='z')) ) {
                        alt14=30;
                    }
                    else {
                        alt14=16;}
                }
                else {
                    alt14=30;}
                }
                break;
            default:
                alt14=30;}

            }
            break;
        case '=':
            {
            alt14=10;
            }
            break;
        case 's':
            {
            int LA14_11 = input.LA(2);

            if ( (LA14_11=='w') ) {
                int LA14_47 = input.LA(3);

                if ( (LA14_47=='i') ) {
                    int LA14_67 = input.LA(4);

                    if ( (LA14_67=='t') ) {
                        int LA14_82 = input.LA(5);

                        if ( (LA14_82=='c') ) {
                            int LA14_91 = input.LA(6);

                            if ( (LA14_91=='h') ) {
                                int LA14_97 = input.LA(7);

                                if ( ((LA14_97>='0' && LA14_97<='9')||(LA14_97>='A' && LA14_97<='Z')||LA14_97=='_'||(LA14_97>='a' && LA14_97<='z')) ) {
                                    alt14=30;
                                }
                                else {
                                    alt14=11;}
                            }
                            else {
                                alt14=30;}
                        }
                        else {
                            alt14=30;}
                    }
                    else {
                        alt14=30;}
                }
                else {
                    alt14=30;}
            }
            else {
                alt14=30;}
            }
            break;
        case 'b':
            {
            int LA14_12 = input.LA(2);

            if ( (LA14_12=='r') ) {
                int LA14_48 = input.LA(3);

                if ( (LA14_48=='e') ) {
                    int LA14_68 = input.LA(4);

                    if ( (LA14_68=='a') ) {
                        int LA14_83 = input.LA(5);

                        if ( (LA14_83=='k') ) {
                            int LA14_92 = input.LA(6);

                            if ( ((LA14_92>='0' && LA14_92<='9')||(LA14_92>='A' && LA14_92<='Z')||LA14_92=='_'||(LA14_92>='a' && LA14_92<='z')) ) {
                                alt14=30;
                            }
                            else {
                                alt14=14;}
                        }
                        else {
                            alt14=30;}
                    }
                    else {
                        alt14=30;}
                }
                else {
                    alt14=30;}
            }
            else {
                alt14=30;}
            }
            break;
        case ':':
            {
            alt14=15;
            }
            break;
        case '&':
        case '*':
        case '+':
        case '-':
        case '\\':
        case '^':
            {
            alt14=16;
            }
            break;
        case '/':
            {
            switch ( input.LA(2) ) {
            case '/':
                {
                alt14=33;
                }
                break;
            case '*':
                {
                alt14=32;
                }
                break;
            default:
                alt14=16;}

            }
            break;
        case 'm':
            {
            int LA14_16 = input.LA(2);

            if ( (LA14_16=='o') ) {
                int LA14_51 = input.LA(3);

                if ( (LA14_51=='d') ) {
                    int LA14_69 = input.LA(4);

                    if ( ((LA14_69>='0' && LA14_69<='9')||(LA14_69>='A' && LA14_69<='Z')||LA14_69=='_'||(LA14_69>='a' && LA14_69<='z')) ) {
                        alt14=30;
                    }
                    else {
                        alt14=16;}
                }
                else {
                    alt14=30;}
            }
            else {
                alt14=30;}
            }
            break;
        case 'g':
            {
            int LA14_17 = input.LA(2);

            if ( (LA14_17=='t') ) {
                switch ( input.LA(3) ) {
                case 'e':
                    {
                    int LA14_70 = input.LA(4);

                    if ( ((LA14_70>='0' && LA14_70<='9')||(LA14_70>='A' && LA14_70<='Z')||LA14_70=='_'||(LA14_70>='a' && LA14_70<='z')) ) {
                        alt14=30;
                    }
                    else {
                        alt14=16;}
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
                    alt14=30;
                    }
                    break;
                default:
                    alt14=16;}

            }
            else {
                alt14=30;}
            }
            break;
        case 'l':
            {
            int LA14_18 = input.LA(2);

            if ( (LA14_18=='t') ) {
                switch ( input.LA(3) ) {
                case 'e':
                    {
                    int LA14_71 = input.LA(4);

                    if ( ((LA14_71>='0' && LA14_71<='9')||(LA14_71>='A' && LA14_71<='Z')||LA14_71=='_'||(LA14_71>='a' && LA14_71<='z')) ) {
                        alt14=30;
                    }
                    else {
                        alt14=16;}
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
                    alt14=30;
                    }
                    break;
                default:
                    alt14=16;}

            }
            else {
                alt14=30;}
            }
            break;
        case ',':
            {
            alt14=17;
            }
            break;
        case ';':
            {
            alt14=18;
            }
            break;
        case '#':
            {
            alt14=19;
            }
            break;
        case '(':
            {
            alt14=20;
            }
            break;
        case ')':
            {
            alt14=21;
            }
            break;
        case '[':
            {
            alt14=22;
            }
            break;
        case ']':
            {
            alt14=23;
            }
            break;
        case '{':
            {
            alt14=24;
            }
            break;
        case '}':
            {
            alt14=25;
            }
            break;
        case '.':
            {
            alt14=26;
            }
            break;
        case 'v':
            {
            int LA14_29 = input.LA(2);

            if ( (LA14_29=='a') ) {
                int LA14_54 = input.LA(3);

                if ( (LA14_54=='r') ) {
                    int LA14_72 = input.LA(4);

                    if ( ((LA14_72>='0' && LA14_72<='9')||(LA14_72>='A' && LA14_72<='Z')||LA14_72=='_'||(LA14_72>='a' && LA14_72<='z')) ) {
                        alt14=30;
                    }
                    else {
                        alt14=27;}
                }
                else {
                    alt14=30;}
            }
            else {
                alt14=30;}
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
            alt14=28;
            }
            break;
        case '\"':
        case '\'':
            {
            alt14=29;
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
            alt14=30;
            }
            break;
        case '\t':
        case '\n':
        case '\f':
        case '\r':
        case ' ':
            {
            alt14=31;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( IF | ELSE | TRY | CATCH | RETURN | FOR | WHILE | DO | NOT | EQUALS | SWITCH | CASE | DEFAULT | BREAK | COLON | OPERATOR | COMMA | SEMI_COLON | HASH | OPEN_PAREN | CLOSE_PAREN | OPEN_SQUARE | CLOSE_SQUARE | OPEN_CURLY | CLOSE_CURLY | DOT | VAR | NUMBER | STRING | IDENTIFIER | WS | COMMENT | LINE_COMMENT );", 14, 0, input);

            throw nvae;
        }

        switch (alt14) {
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
                // ./CFScript.g:1:208: STRING
                {
                mSTRING(); 

                }
                break;
            case 30 :
                // ./CFScript.g:1:215: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 31 :
                // ./CFScript.g:1:226: WS
                {
                mWS(); 

                }
                break;
            case 32 :
                // ./CFScript.g:1:229: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 33 :
                // ./CFScript.g:1:237: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;

        }

    }


 

}