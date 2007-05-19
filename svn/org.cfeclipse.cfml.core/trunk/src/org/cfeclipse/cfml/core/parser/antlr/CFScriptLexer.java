// $ANTLR 3.0 ./CFScript.g 2007-05-19 17:25:09

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
    public static final int LINE_COMMENT=35;
    public static final int CLOSE_CURLY=24;
    public static final int LETTER=32;
    public static final int OPERATOR=12;
    public static final int ELSE=25;
    public static final int NUMBER=14;
    public static final int HASH=16;
    public static final int OPEN_PAREN=10;
    public static final int SEMI_COLON=6;
    public static final int EQUALS=8;
    public static final int OPEN_CURLY=23;
    public static final int NOT=13;
    public static final int Tokens=36;
    public static final int EOF=-1;
    public static final int TRY=26;
    public static final int IF=22;
    public static final int WS=33;
    public static final int COMMA=21;
    public static final int BOOLEAN_OPERATOR=30;
    public static final int IDENTIFIER=18;
    public static final int RETURN=9;
    public static final int OPEN_SQUARE=19;
    public static final int VAR=7;
    public static final int CLOSE_PAREN=11;
    public static final int MATH_OPERATOR=28;
    public static final int DIGIT=31;
    public static final int DOT=17;
    public static final int CATCH=27;
    public static final int COMMENT=34;
    public static final int FUNCTION_CALL=4;
    public static final int CLOSE_SQUARE=20;
    public static final int STRING_OPERATOR=29;
    public static final int STRUCT_KEY=5;
    public static final int STRING=15;
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
            // ./CFScript.g:216:2: ( 'if' )
            // ./CFScript.g:216:2: 'if'
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
            // ./CFScript.g:221:2: ( 'else' )
            // ./CFScript.g:221:2: 'else'
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
            // ./CFScript.g:226:2: ( 'try' )
            // ./CFScript.g:226:2: 'try'
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
            // ./CFScript.g:231:2: ( 'catch' )
            // ./CFScript.g:231:2: 'catch'
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
            // ./CFScript.g:235:2: ( 'return' )
            // ./CFScript.g:235:2: 'return'
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

    // $ANTLR start NOT
    public final void mNOT() throws RecognitionException {
    traceIn("NOT", 6);
        try {
            int _type = NOT;
            // ./CFScript.g:239:2: ( 'not' )
            // ./CFScript.g:239:2: 'not'
            {
            match("not"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("NOT", 6);
        }
    }
    // $ANTLR end NOT

    // $ANTLR start EQUALS
    public final void mEQUALS() throws RecognitionException {
    traceIn("EQUALS", 7);
        try {
            int _type = EQUALS;
            // ./CFScript.g:244:2: ( '=' )
            // ./CFScript.g:244:2: '='
            {
            match('='); 

            }

            this.type = _type;
        }
        finally {
    traceOut("EQUALS", 7);
        }
    }
    // $ANTLR end EQUALS

    // $ANTLR start OPERATOR
    public final void mOPERATOR() throws RecognitionException {
    traceIn("OPERATOR", 8);
        try {
            int _type = OPERATOR;
            // ./CFScript.g:249:2: ( ( MATH_OPERATOR | STRING_OPERATOR | BOOLEAN_OPERATOR ) )
            // ./CFScript.g:249:2: ( MATH_OPERATOR | STRING_OPERATOR | BOOLEAN_OPERATOR )
            {
            // ./CFScript.g:249:2: ( MATH_OPERATOR | STRING_OPERATOR | BOOLEAN_OPERATOR )
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
                    new NoViableAltException("249:2: ( MATH_OPERATOR | STRING_OPERATOR | BOOLEAN_OPERATOR )", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ./CFScript.g:249:4: MATH_OPERATOR
                    {
                    mMATH_OPERATOR(); 

                    }
                    break;
                case 2 :
                    // ./CFScript.g:249:20: STRING_OPERATOR
                    {
                    mSTRING_OPERATOR(); 

                    }
                    break;
                case 3 :
                    // ./CFScript.g:249:38: BOOLEAN_OPERATOR
                    {
                    mBOOLEAN_OPERATOR(); 

                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
    traceOut("OPERATOR", 8);
        }
    }
    // $ANTLR end OPERATOR

    // $ANTLR start COMMA
    public final void mCOMMA() throws RecognitionException {
    traceIn("COMMA", 9);
        try {
            int _type = COMMA;
            // ./CFScript.g:253:2: ( ',' )
            // ./CFScript.g:253:2: ','
            {
            match(','); 

            }

            this.type = _type;
        }
        finally {
    traceOut("COMMA", 9);
        }
    }
    // $ANTLR end COMMA

    // $ANTLR start SEMI_COLON
    public final void mSEMI_COLON() throws RecognitionException {
    traceIn("SEMI_COLON", 10);
        try {
            int _type = SEMI_COLON;
            // ./CFScript.g:258:2: ( ';' )
            // ./CFScript.g:258:2: ';'
            {
            match(';'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("SEMI_COLON", 10);
        }
    }
    // $ANTLR end SEMI_COLON

    // $ANTLR start HASH
    public final void mHASH() throws RecognitionException {
    traceIn("HASH", 11);
        try {
            int _type = HASH;
            // ./CFScript.g:262:2: ( '#' )
            // ./CFScript.g:262:2: '#'
            {
            match('#'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("HASH", 11);
        }
    }
    // $ANTLR end HASH

    // $ANTLR start OPEN_PAREN
    public final void mOPEN_PAREN() throws RecognitionException {
    traceIn("OPEN_PAREN", 12);
        try {
            int _type = OPEN_PAREN;
            // ./CFScript.g:267:2: ( '(' )
            // ./CFScript.g:267:2: '('
            {
            match('('); 

            }

            this.type = _type;
        }
        finally {
    traceOut("OPEN_PAREN", 12);
        }
    }
    // $ANTLR end OPEN_PAREN

    // $ANTLR start CLOSE_PAREN
    public final void mCLOSE_PAREN() throws RecognitionException {
    traceIn("CLOSE_PAREN", 13);
        try {
            int _type = CLOSE_PAREN;
            // ./CFScript.g:272:2: ( ')' )
            // ./CFScript.g:272:2: ')'
            {
            match(')'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("CLOSE_PAREN", 13);
        }
    }
    // $ANTLR end CLOSE_PAREN

    // $ANTLR start OPEN_SQUARE
    public final void mOPEN_SQUARE() throws RecognitionException {
    traceIn("OPEN_SQUARE", 14);
        try {
            int _type = OPEN_SQUARE;
            // ./CFScript.g:277:2: ( '[' )
            // ./CFScript.g:277:2: '['
            {
            match('['); 

            }

            this.type = _type;
        }
        finally {
    traceOut("OPEN_SQUARE", 14);
        }
    }
    // $ANTLR end OPEN_SQUARE

    // $ANTLR start CLOSE_SQUARE
    public final void mCLOSE_SQUARE() throws RecognitionException {
    traceIn("CLOSE_SQUARE", 15);
        try {
            int _type = CLOSE_SQUARE;
            // ./CFScript.g:282:2: ( ']' )
            // ./CFScript.g:282:2: ']'
            {
            match(']'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("CLOSE_SQUARE", 15);
        }
    }
    // $ANTLR end CLOSE_SQUARE

    // $ANTLR start OPEN_CURLY
    public final void mOPEN_CURLY() throws RecognitionException {
    traceIn("OPEN_CURLY", 16);
        try {
            int _type = OPEN_CURLY;
            // ./CFScript.g:287:2: ( '{' )
            // ./CFScript.g:287:2: '{'
            {
            match('{'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("OPEN_CURLY", 16);
        }
    }
    // $ANTLR end OPEN_CURLY

    // $ANTLR start CLOSE_CURLY
    public final void mCLOSE_CURLY() throws RecognitionException {
    traceIn("CLOSE_CURLY", 17);
        try {
            int _type = CLOSE_CURLY;
            // ./CFScript.g:292:2: ( '}' )
            // ./CFScript.g:292:2: '}'
            {
            match('}'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("CLOSE_CURLY", 17);
        }
    }
    // $ANTLR end CLOSE_CURLY

    // $ANTLR start DOT
    public final void mDOT() throws RecognitionException {
    traceIn("DOT", 18);
        try {
            int _type = DOT;
            // ./CFScript.g:297:2: ( '.' )
            // ./CFScript.g:297:2: '.'
            {
            match('.'); 

            }

            this.type = _type;
        }
        finally {
    traceOut("DOT", 18);
        }
    }
    // $ANTLR end DOT

    // $ANTLR start VAR
    public final void mVAR() throws RecognitionException {
    traceIn("VAR", 19);
        try {
            int _type = VAR;
            // ./CFScript.g:302:2: ( 'var' )
            // ./CFScript.g:302:2: 'var'
            {
            match("var"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("VAR", 19);
        }
    }
    // $ANTLR end VAR

    // $ANTLR start STRING
    public final void mSTRING() throws RecognitionException {
    traceIn("STRING", 20);
        try {
            int _type = STRING;
            // ./CFScript.g:307:2: ( '\"' ( options {greedy=false; } : . )* '\"' | '\\'' '\"' ( options {greedy=false; } : . )* '\\'' )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\"') ) {
                alt4=1;
            }
            else if ( (LA4_0=='\'') ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("305:1: STRING : ( '\"' ( options {greedy=false; } : . )* '\"' | '\\'' '\"' ( options {greedy=false; } : . )* '\\'' );", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // ./CFScript.g:307:2: '\"' ( options {greedy=false; } : . )* '\"'
                    {
                    match('\"'); 
                    // ./CFScript.g:307:6: ( options {greedy=false; } : . )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0=='\"') ) {
                            alt2=2;
                        }
                        else if ( ((LA2_0>='\u0000' && LA2_0<='!')||(LA2_0>='#' && LA2_0<='\uFFFE')) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // ./CFScript.g:307:34: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // ./CFScript.g:309:2: '\\'' '\"' ( options {greedy=false; } : . )* '\\''
                    {
                    match('\''); 
                    match('\"'); 
                    // ./CFScript.g:309:11: ( options {greedy=false; } : . )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0=='\'') ) {
                            alt3=2;
                        }
                        else if ( ((LA3_0>='\u0000' && LA3_0<='&')||(LA3_0>='(' && LA3_0<='\uFFFE')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // ./CFScript.g:309:39: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }
            this.type = _type;
        }
        finally {
    traceOut("STRING", 20);
        }
    }
    // $ANTLR end STRING

    // $ANTLR start NUMBER
    public final void mNUMBER() throws RecognitionException {
    traceIn("NUMBER", 21);
        try {
            int _type = NUMBER;
            // ./CFScript.g:314:2: ( ( DIGIT )+ ( DOT ( DIGIT )+ ) )
            // ./CFScript.g:314:2: ( DIGIT )+ ( DOT ( DIGIT )+ )
            {
            // ./CFScript.g:314:2: ( DIGIT )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ./CFScript.g:314:2: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);

            // ./CFScript.g:314:9: ( DOT ( DIGIT )+ )
            // ./CFScript.g:314:10: DOT ( DIGIT )+
            {
            mDOT(); 
            // ./CFScript.g:314:14: ( DIGIT )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // ./CFScript.g:314:14: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            }


            }

            this.type = _type;
        }
        finally {
    traceOut("NUMBER", 21);
        }
    }
    // $ANTLR end NUMBER

    // $ANTLR start IDENTIFIER
    public final void mIDENTIFIER() throws RecognitionException {
    traceIn("IDENTIFIER", 22);
        try {
            int _type = IDENTIFIER;
            // ./CFScript.g:319:2: ( ( LETTER | DIGIT | '_' )+ )
            // ./CFScript.g:319:2: ( LETTER | DIGIT | '_' )+
            {
            // ./CFScript.g:319:2: ( LETTER | DIGIT | '_' )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')||(LA7_0>='A' && LA7_0<='Z')||LA7_0=='_'||(LA7_0>='a' && LA7_0<='z')) ) {
                    alt7=1;
                }


                switch (alt7) {
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
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            }

            this.type = _type;
        }
        finally {
    traceOut("IDENTIFIER", 22);
        }
    }
    // $ANTLR end IDENTIFIER

    // $ANTLR start MATH_OPERATOR
    public final void mMATH_OPERATOR() throws RecognitionException {
    traceIn("MATH_OPERATOR", 23);
        try {
            // ./CFScript.g:326:2: ( ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' ) )
            // ./CFScript.g:326:2: ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' )
            {
            // ./CFScript.g:326:2: ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' )
            int alt8=7;
            switch ( input.LA(1) ) {
            case '+':
                {
                alt8=1;
                }
                break;
            case '*':
                {
                alt8=2;
                }
                break;
            case '/':
                {
                alt8=3;
                }
                break;
            case '\\':
                {
                alt8=4;
                }
                break;
            case '^':
                {
                alt8=5;
                }
                break;
            case 'm':
                {
                alt8=6;
                }
                break;
            case '-':
                {
                alt8=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("326:2: ( '+' | '*' | '\\/' | '\\\\' | '^' | 'mod' | '-' )", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // ./CFScript.g:326:3: '+'
                    {
                    match('+'); 

                    }
                    break;
                case 2 :
                    // ./CFScript.g:326:9: '*'
                    {
                    match('*'); 

                    }
                    break;
                case 3 :
                    // ./CFScript.g:326:15: '\\/'
                    {
                    match('/'); 

                    }
                    break;
                case 4 :
                    // ./CFScript.g:326:22: '\\\\'
                    {
                    match('\\'); 

                    }
                    break;
                case 5 :
                    // ./CFScript.g:326:29: '^'
                    {
                    match('^'); 

                    }
                    break;
                case 6 :
                    // ./CFScript.g:326:35: 'mod'
                    {
                    match("mod"); 


                    }
                    break;
                case 7 :
                    // ./CFScript.g:326:43: '-'
                    {
                    match('-'); 

                    }
                    break;

            }


            }

        }
        finally {
    traceOut("MATH_OPERATOR", 23);
        }
    }
    // $ANTLR end MATH_OPERATOR

    // $ANTLR start STRING_OPERATOR
    public final void mSTRING_OPERATOR() throws RecognitionException {
    traceIn("STRING_OPERATOR", 24);
        try {
            // ./CFScript.g:330:2: ( '&' )
            // ./CFScript.g:330:2: '&'
            {
            match('&'); 

            }

        }
        finally {
    traceOut("STRING_OPERATOR", 24);
        }
    }
    // $ANTLR end STRING_OPERATOR

    // $ANTLR start BOOLEAN_OPERATOR
    public final void mBOOLEAN_OPERATOR() throws RecognitionException {
    traceIn("BOOLEAN_OPERATOR", 25);
        try {
            // ./CFScript.g:334:2: ( ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' ) )
            // ./CFScript.g:334:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )
            {
            // ./CFScript.g:334:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )
            int alt9=7;
            switch ( input.LA(1) ) {
            case 'e':
                {
                alt9=1;
                }
                break;
            case 'n':
                {
                alt9=2;
                }
                break;
            case 'i':
                {
                alt9=3;
                }
                break;
            case 'g':
                {
                int LA9_4 = input.LA(2);

                if ( (LA9_4=='t') ) {
                    int LA9_6 = input.LA(3);

                    if ( (LA9_6=='e') ) {
                        alt9=7;
                    }
                    else {
                        alt9=4;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("334:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )", 9, 4, input);

                    throw nvae;
                }
                }
                break;
            case 'l':
                {
                int LA9_5 = input.LA(2);

                if ( (LA9_5=='t') ) {
                    int LA9_7 = input.LA(3);

                    if ( (LA9_7=='e') ) {
                        alt9=6;
                    }
                    else {
                        alt9=5;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("334:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )", 9, 5, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("334:2: ( 'eq' | 'neq' | 'is' | 'gt' | 'lt' | 'lte' | 'gte' )", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // ./CFScript.g:334:3: 'eq'
                    {
                    match("eq"); 


                    }
                    break;
                case 2 :
                    // ./CFScript.g:334:8: 'neq'
                    {
                    match("neq"); 


                    }
                    break;
                case 3 :
                    // ./CFScript.g:334:14: 'is'
                    {
                    match("is"); 


                    }
                    break;
                case 4 :
                    // ./CFScript.g:334:19: 'gt'
                    {
                    match("gt"); 


                    }
                    break;
                case 5 :
                    // ./CFScript.g:334:24: 'lt'
                    {
                    match("lt"); 


                    }
                    break;
                case 6 :
                    // ./CFScript.g:334:29: 'lte'
                    {
                    match("lte"); 


                    }
                    break;
                case 7 :
                    // ./CFScript.g:334:35: 'gte'
                    {
                    match("gte"); 


                    }
                    break;

            }


            }

        }
        finally {
    traceOut("BOOLEAN_OPERATOR", 25);
        }
    }
    // $ANTLR end BOOLEAN_OPERATOR

    // $ANTLR start DIGIT
    public final void mDIGIT() throws RecognitionException {
    traceIn("DIGIT", 26);
        try {
            // ./CFScript.g:339:2: ( '0' .. '9' )
            // ./CFScript.g:339:2: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
    traceOut("DIGIT", 26);
        }
    }
    // $ANTLR end DIGIT

    // $ANTLR start LETTER
    public final void mLETTER() throws RecognitionException {
    traceIn("LETTER", 27);
        try {
            // ./CFScript.g:344:2: ( 'a' .. 'z' | 'A' .. 'Z' )
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
    traceOut("LETTER", 27);
        }
    }
    // $ANTLR end LETTER

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
    traceIn("WS", 28);
        try {
            int _type = WS;
            // ./CFScript.g:351:2: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // ./CFScript.g:351:2: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
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
    traceOut("WS", 28);
        }
    }
    // $ANTLR end WS

    // $ANTLR start COMMENT
    public final void mCOMMENT() throws RecognitionException {
    traceIn("COMMENT", 29);
        try {
            int _type = COMMENT;
            // ./CFScript.g:359:2: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ./CFScript.g:359:2: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // ./CFScript.g:359:7: ( options {greedy=false; } : . )*
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
            	    // ./CFScript.g:359:35: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            match("*/"); 


            		channel=HIDDEN;
            	

            }

            this.type = _type;
        }
        finally {
    traceOut("COMMENT", 29);
        }
    }
    // $ANTLR end COMMENT

    // $ANTLR start LINE_COMMENT
    public final void mLINE_COMMENT() throws RecognitionException {
    traceIn("LINE_COMMENT", 30);
        try {
            int _type = LINE_COMMENT;
            // ./CFScript.g:367:2: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // ./CFScript.g:367:2: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // ./CFScript.g:367:7: (~ ( '\\n' | '\\r' ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='\u0000' && LA11_0<='\t')||(LA11_0>='\u000B' && LA11_0<='\f')||(LA11_0>='\u000E' && LA11_0<='\uFFFE')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ./CFScript.g:367:7: ~ ( '\\n' | '\\r' )
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

            // ./CFScript.g:367:21: ( '\\r' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='\r') ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ./CFScript.g:367:21: '\\r'
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
    traceOut("LINE_COMMENT", 30);
        }
    }
    // $ANTLR end LINE_COMMENT

    public void mTokens() throws RecognitionException {
        // ./CFScript.g:1:10: ( IF | ELSE | TRY | CATCH | RETURN | NOT | EQUALS | OPERATOR | COMMA | SEMI_COLON | HASH | OPEN_PAREN | CLOSE_PAREN | OPEN_SQUARE | CLOSE_SQUARE | OPEN_CURLY | CLOSE_CURLY | DOT | VAR | STRING | NUMBER | IDENTIFIER | WS | COMMENT | LINE_COMMENT )
        int alt13=25;
        alt13 = dfa13.predict(input);
        switch (alt13) {
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
                // ./CFScript.g:1:35: NOT
                {
                mNOT(); 

                }
                break;
            case 7 :
                // ./CFScript.g:1:39: EQUALS
                {
                mEQUALS(); 

                }
                break;
            case 8 :
                // ./CFScript.g:1:46: OPERATOR
                {
                mOPERATOR(); 

                }
                break;
            case 9 :
                // ./CFScript.g:1:55: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 10 :
                // ./CFScript.g:1:61: SEMI_COLON
                {
                mSEMI_COLON(); 

                }
                break;
            case 11 :
                // ./CFScript.g:1:72: HASH
                {
                mHASH(); 

                }
                break;
            case 12 :
                // ./CFScript.g:1:77: OPEN_PAREN
                {
                mOPEN_PAREN(); 

                }
                break;
            case 13 :
                // ./CFScript.g:1:88: CLOSE_PAREN
                {
                mCLOSE_PAREN(); 

                }
                break;
            case 14 :
                // ./CFScript.g:1:100: OPEN_SQUARE
                {
                mOPEN_SQUARE(); 

                }
                break;
            case 15 :
                // ./CFScript.g:1:112: CLOSE_SQUARE
                {
                mCLOSE_SQUARE(); 

                }
                break;
            case 16 :
                // ./CFScript.g:1:125: OPEN_CURLY
                {
                mOPEN_CURLY(); 

                }
                break;
            case 17 :
                // ./CFScript.g:1:136: CLOSE_CURLY
                {
                mCLOSE_CURLY(); 

                }
                break;
            case 18 :
                // ./CFScript.g:1:148: DOT
                {
                mDOT(); 

                }
                break;
            case 19 :
                // ./CFScript.g:1:152: VAR
                {
                mVAR(); 

                }
                break;
            case 20 :
                // ./CFScript.g:1:156: STRING
                {
                mSTRING(); 

                }
                break;
            case 21 :
                // ./CFScript.g:1:163: NUMBER
                {
                mNUMBER(); 

                }
                break;
            case 22 :
                // ./CFScript.g:1:170: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 23 :
                // ./CFScript.g:1:181: WS
                {
                mWS(); 

                }
                break;
            case 24 :
                // ./CFScript.g:1:184: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 25 :
                // ./CFScript.g:1:192: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;

        }

    }


    protected DFA13 dfa13 = new DFA13(this);
    static final String DFA13_eotS =
        "\1\uffff\6\32\2\uffff\1\10\3\32\12\uffff\1\32\1\uffff\1\32\2\uffff"+
        "\1\54\2\10\6\32\2\uffff\1\32\2\10\1\32\2\uffff\1\32\1\70\2\32\1"+
        "\73\4\10\1\74\1\75\1\uffff\2\32\3\uffff\1\100\1\32\1\uffff\1\102"+
        "\1\uffff";
    static final String DFA13_eofS =
        "\103\uffff";
    static final String DFA13_minS =
        "\1\11\1\146\1\154\1\162\1\141\2\145\2\uffff\1\52\1\157\2\164\12"+
        "\uffff\1\141\1\uffff\1\56\2\uffff\3\60\1\163\1\171\3\164\1\161\2"+
        "\uffff\1\144\2\60\1\162\2\uffff\1\145\1\60\1\143\1\165\7\60\1\uffff"+
        "\1\150\1\162\3\uffff\1\60\1\156\1\uffff\1\60\1\uffff";
    static final String DFA13_maxS =
        "\1\175\1\163\1\161\1\162\1\141\1\145\1\157\2\uffff\1\57\1\157\2"+
        "\164\12\uffff\1\141\1\uffff\1\71\2\uffff\3\172\1\163\1\171\3\164"+
        "\1\161\2\uffff\1\144\2\172\1\162\2\uffff\1\145\1\172\1\143\1\165"+
        "\7\172\1\uffff\1\150\1\162\3\uffff\1\172\1\156\1\uffff\1\172\1\uffff";
    static final String DFA13_acceptS =
        "\7\uffff\1\7\1\10\4\uffff\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20"+
        "\1\21\1\22\1\uffff\1\24\1\uffff\1\26\1\27\11\uffff\1\30\1\31\4\uffff"+
        "\1\25\1\1\13\uffff\1\3\2\uffff\1\6\1\23\1\2\2\uffff\1\4\1\uffff"+
        "\1\5";
    static final String DFA13_specialS =
        "\103\uffff}>";
    static final String[] DFA13_transitionS = {
            "\2\33\1\uffff\2\33\22\uffff\1\33\1\uffff\1\30\1\17\2\uffff\1"+
            "\10\1\30\1\20\1\21\2\10\1\15\1\10\1\26\1\11\12\31\1\uffff\1"+
            "\16\1\uffff\1\7\3\uffff\32\32\1\22\1\10\1\23\1\10\1\32\1\uffff"+
            "\2\32\1\4\1\32\1\2\1\32\1\13\1\32\1\1\2\32\1\14\1\12\1\6\3\32"+
            "\1\5\1\32\1\3\1\32\1\27\4\32\1\24\1\uffff\1\25",
            "\1\34\14\uffff\1\35",
            "\1\37\4\uffff\1\36",
            "\1\40",
            "\1\41",
            "\1\42",
            "\1\44\11\uffff\1\43",
            "",
            "",
            "\1\45\4\uffff\1\46",
            "\1\47",
            "\1\50",
            "\1\51",
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
            "\1\52",
            "",
            "\1\53\1\uffff\12\31",
            "",
            "",
            "\12\32\7\uffff\32\32\4\uffff\1\32\1\uffff\32\32",
            "\12\32\7\uffff\32\32\4\uffff\1\32\1\uffff\32\32",
            "\12\32\7\uffff\32\32\4\uffff\1\32\1\uffff\32\32",
            "\1\55",
            "\1\56",
            "\1\57",
            "\1\60",
            "\1\61",
            "\1\62",
            "",
            "",
            "\1\63",
            "\12\32\7\uffff\32\32\4\uffff\1\32\1\uffff\4\32\1\64\25\32",
            "\12\32\7\uffff\32\32\4\uffff\1\32\1\uffff\4\32\1\65\25\32",
            "\1\66",
            "",
            "",
            "\1\67",
            "\12\32\7\uffff\32\32\4\uffff\1\32\1\uffff\32\32",
            "\1\71",
            "\1\72",
            "\12\32\7\uffff\32\32\4\uffff\1\32\1\uffff\32\32",
            "\12\32\7\uffff\32\32\4\uffff\1\32\1\uffff\32\32",
            "\12\32\7\uffff\32\32\4\uffff\1\32\1\uffff\32\32",
            "\12\32\7\uffff\32\32\4\uffff\1\32\1\uffff\32\32",
            "\12\32\7\uffff\32\32\4\uffff\1\32\1\uffff\32\32",
            "\12\32\7\uffff\32\32\4\uffff\1\32\1\uffff\32\32",
            "\12\32\7\uffff\32\32\4\uffff\1\32\1\uffff\32\32",
            "",
            "\1\76",
            "\1\77",
            "",
            "",
            "",
            "\12\32\7\uffff\32\32\4\uffff\1\32\1\uffff\32\32",
            "\1\101",
            "",
            "\12\32\7\uffff\32\32\4\uffff\1\32\1\uffff\32\32",
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
            return "1:1: Tokens : ( IF | ELSE | TRY | CATCH | RETURN | NOT | EQUALS | OPERATOR | COMMA | SEMI_COLON | HASH | OPEN_PAREN | CLOSE_PAREN | OPEN_SQUARE | CLOSE_SQUARE | OPEN_CURLY | CLOSE_CURLY | DOT | VAR | STRING | NUMBER | IDENTIFIER | WS | COMMENT | LINE_COMMENT );";
        }
    }
 

}