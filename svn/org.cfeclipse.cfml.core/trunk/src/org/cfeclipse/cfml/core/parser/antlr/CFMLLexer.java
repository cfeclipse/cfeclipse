// $ANTLR 3.0 ./CFML.g 2007-07-04 16:09:30

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

public class CFMLLexer extends Lexer {
    public static final int TAG_ATTRIBUTE=1013;
    public static final int OTHER=1033;
    public static final int CFTAG=1004;
    public static final int LETTER=1026;
    public static final int DOUBLE_QUOTE=1015;
    public static final int HASH_CFML=1024;
    public static final int CFML_STATEMENT=1008;
    public static final int HASH=1022;
    public static final int END_TAG_OPEN=1011;
    public static final int UNDERSCORE=1028;
    public static final int EQUALS=1014;
    public static final int Tokens=1034;
    public static final int EOF=-1;
    public static final int START_TAG_CLOSE=1010;
    public static final int ESCAPE_SINGLE_QUOTE=1019;
    public static final int COLON=1030;
    public static final int TAG_NAME=1025;
    public static final int CUSTOMTAG=1005;
    public static final int SINGLE_QUOTE=1018;
    public static final int SINGLE_QUOTE_STRING=1020;
    public static final int STRING_LITERAL=1007;
    public static final int WS=1031;
    public static final int DOUBLE_QUOTE_STRING=1017;
    public static final int TAG_IDENT=1029;
    public static final int END_TAG_CLOSE=1012;
    public static final int ESCAPE_HASH=1023;
    public static final int ESCAPE_DOUBLE_QUOTE=1016;
    public static final int DIGIT=1027;
    public static final int COMMENT=1032;
    public static final int CFML=1021;
    public static final int START_TAG_OPEN=1009;
    public static final int IMPORTTAG=1006;

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

    public CFMLLexer() {;} 
    public CFMLLexer(CharStream input) {
        super(input);
    }
    public String getGrammarFileName() { return "./CFML.g"; }

    // $ANTLR start END_TAG_OPEN
    public final void mEND_TAG_OPEN() throws RecognitionException {
    traceIn("END_TAG_OPEN", 1);
        try {
            int _type = END_TAG_OPEN;
            // ./CFML.g:416:2: ({...}? => '</' TAG_NAME )
            // ./CFML.g:416:2: {...}? => '</' TAG_NAME
            {
            if ( !(
            		getMode() == NONE_MODE
            	) ) {
                throw new FailedPredicateException(input, "END_TAG_OPEN", "\n\t\tgetMode() == NONE_MODE\n\t");
            }

            		setMode(ENDTAG_MODE);
            	
            match("</"); 

            mTAG_NAME(); 

            }

            this.type = _type;
        }
        finally {
    traceOut("END_TAG_OPEN", 1);
        }
    }
    // $ANTLR end END_TAG_OPEN

    // $ANTLR start END_TAG_CLOSE
    public final void mEND_TAG_CLOSE() throws RecognitionException {
    traceIn("END_TAG_CLOSE", 2);
        try {
            int _type = END_TAG_CLOSE;
            // ./CFML.g:427:2: ({...}? => '>' )
            // ./CFML.g:427:2: {...}? => '>'
            {
            if ( !(getMode() == ENDTAG_MODE) ) {
                throw new FailedPredicateException(input, "END_TAG_CLOSE", "getMode() == ENDTAG_MODE");
            }
            match('>'); 
            setMode(NONE_MODE);

            }

            this.type = _type;
        }
        finally {
    traceOut("END_TAG_CLOSE", 2);
        }
    }
    // $ANTLR end END_TAG_CLOSE

    // $ANTLR start START_TAG_OPEN
    public final void mSTART_TAG_OPEN() throws RecognitionException {
    traceIn("START_TAG_OPEN", 3);
        try {
            int _type = START_TAG_OPEN;
            // ./CFML.g:434:2: ({...}? => '<' TAG_NAME )
            // ./CFML.g:434:2: {...}? => '<' TAG_NAME
            {
            if ( !(
            		getMode() == NONE_MODE
            	) ) {
                throw new FailedPredicateException(input, "START_TAG_OPEN", "\n\t\tgetMode() == NONE_MODE\n\t");
            }

            		setMode(STARTTAG_MODE);
            	
            match('<'); 
            mTAG_NAME(); 

            }

            this.type = _type;
        }
        finally {
    traceOut("START_TAG_OPEN", 3);
        }
    }
    // $ANTLR end START_TAG_OPEN

    // $ANTLR start START_TAG_CLOSE
    public final void mSTART_TAG_CLOSE() throws RecognitionException {
    traceIn("START_TAG_CLOSE", 4);
        try {
            int _type = START_TAG_CLOSE;
            // ./CFML.g:446:2: ({...}? => ( '/' )? '>' )
            // ./CFML.g:446:2: {...}? => ( '/' )? '>'
            {
            if ( !(getMode() == STARTTAG_MODE) ) {
                throw new FailedPredicateException(input, "START_TAG_CLOSE", "getMode() == STARTTAG_MODE");
            }
            // ./CFML.g:447:2: ( '/' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='/') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // ./CFML.g:447:2: '/'
                    {
                    match('/'); 

                    }
                    break;

            }

            match('>'); 
            setMode(NONE_MODE);

            }

            this.type = _type;
        }
        finally {
    traceOut("START_TAG_CLOSE", 4);
        }
    }
    // $ANTLR end START_TAG_CLOSE

    // $ANTLR start TAG_ATTRIBUTE
    public final void mTAG_ATTRIBUTE() throws RecognitionException {
    traceIn("TAG_ATTRIBUTE", 5);
        try {
            int _type = TAG_ATTRIBUTE;
            // ./CFML.g:453:2: ({...}? => ( LETTER | DIGIT | UNDERSCORE )+ )
            // ./CFML.g:453:2: {...}? => ( LETTER | DIGIT | UNDERSCORE )+
            {
            if ( !(getMode() == STARTTAG_MODE) ) {
                throw new FailedPredicateException(input, "TAG_ATTRIBUTE", "getMode() == STARTTAG_MODE");
            }
            // ./CFML.g:454:2: ( LETTER | DIGIT | UNDERSCORE )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')||(LA2_0>='A' && LA2_0<='Z')||LA2_0=='_'||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ./CFML.g:
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
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            this.type = _type;
        }
        finally {
    traceOut("TAG_ATTRIBUTE", 5);
        }
    }
    // $ANTLR end TAG_ATTRIBUTE

    // $ANTLR start EQUALS
    public final void mEQUALS() throws RecognitionException {
    traceIn("EQUALS", 6);
        try {
            int _type = EQUALS;
            // ./CFML.g:459:2: ({...}? => '=' )
            // ./CFML.g:459:2: {...}? => '='
            {
            if ( !(getMode() == STARTTAG_MODE) ) {
                throw new FailedPredicateException(input, "EQUALS", "getMode() == STARTTAG_MODE");
            }
            match('='); 

            }

            this.type = _type;
        }
        finally {
    traceOut("EQUALS", 6);
        }
    }
    // $ANTLR end EQUALS

    // $ANTLR start SINGLE_QUOTE
    public final void mSINGLE_QUOTE() throws RecognitionException {
    traceIn("SINGLE_QUOTE", 7);
        try {
            int _type = SINGLE_QUOTE;
            // ./CFML.g:465:2: ({...}? => '\\'' )
            // ./CFML.g:465:2: {...}? => '\\''
            {
            if ( !(getMode() == STARTTAG_MODE  || getMode() == SINGLE_QUOTE_STRING_MODE) ) {
                throw new FailedPredicateException(input, "SINGLE_QUOTE", "getMode() == STARTTAG_MODE  || getMode() == SINGLE_QUOTE_STRING_MODE");
            }
            match('\''); 

            		if(getMode() == STARTTAG_MODE)
            		{
            			setMode(SINGLE_QUOTE_STRING_MODE);
            		}
            		else
            		{
            			setMode(STARTTAG_MODE);
            		}
            	

            }

            this.type = _type;
        }
        finally {
    traceOut("SINGLE_QUOTE", 7);
        }
    }
    // $ANTLR end SINGLE_QUOTE

    // $ANTLR start ESCAPE_SINGLE_QUOTE
    public final void mESCAPE_SINGLE_QUOTE() throws RecognitionException {
    traceIn("ESCAPE_SINGLE_QUOTE", 8);
        try {
            int _type = ESCAPE_SINGLE_QUOTE;
            // ./CFML.g:481:2: ({...}? => '\\'\\'' )
            // ./CFML.g:481:2: {...}? => '\\'\\''
            {
            if ( !( getMode() == SINGLE_QUOTE_STRING_MODE ) ) {
                throw new FailedPredicateException(input, "ESCAPE_SINGLE_QUOTE", " getMode() == SINGLE_QUOTE_STRING_MODE ");
            }
            match("\'\'"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("ESCAPE_SINGLE_QUOTE", 8);
        }
    }
    // $ANTLR end ESCAPE_SINGLE_QUOTE

    // $ANTLR start SINGLE_QUOTE_STRING
    public final void mSINGLE_QUOTE_STRING() throws RecognitionException {
    traceIn("SINGLE_QUOTE_STRING", 9);
        try {
            int _type = SINGLE_QUOTE_STRING;
            // ./CFML.g:487:2: ({...}? =>~ ( '\\'' ) )
            // ./CFML.g:487:2: {...}? =>~ ( '\\'' )
            {
            if ( !( getMode() == SINGLE_QUOTE_STRING_MODE ) ) {
                throw new FailedPredicateException(input, "SINGLE_QUOTE_STRING", " getMode() == SINGLE_QUOTE_STRING_MODE ");
            }
            if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='\uFFFE') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

            this.type = _type;
        }
        finally {
    traceOut("SINGLE_QUOTE_STRING", 9);
        }
    }
    // $ANTLR end SINGLE_QUOTE_STRING

    // $ANTLR start DOUBLE_QUOTE
    public final void mDOUBLE_QUOTE() throws RecognitionException {
    traceIn("DOUBLE_QUOTE", 10);
        try {
            int _type = DOUBLE_QUOTE;
            // ./CFML.g:493:2: ({...}? => '\"' )
            // ./CFML.g:493:2: {...}? => '\"'
            {
            if ( !(getMode() == STARTTAG_MODE  || getMode() == DOUBLE_QUOTE_STRING_MODE) ) {
                throw new FailedPredicateException(input, "DOUBLE_QUOTE", "getMode() == STARTTAG_MODE  || getMode() == DOUBLE_QUOTE_STRING_MODE");
            }
            match('\"'); 

            		if(getMode() == STARTTAG_MODE)
            		{
            			setMode(DOUBLE_QUOTE_STRING_MODE);
            		}
            		else
            		{
            			setMode(STARTTAG_MODE);
            		}
            	

            }

            this.type = _type;
        }
        finally {
    traceOut("DOUBLE_QUOTE", 10);
        }
    }
    // $ANTLR end DOUBLE_QUOTE

    // $ANTLR start ESCAPE_DOUBLE_QUOTE
    public final void mESCAPE_DOUBLE_QUOTE() throws RecognitionException {
    traceIn("ESCAPE_DOUBLE_QUOTE", 11);
        try {
            int _type = ESCAPE_DOUBLE_QUOTE;
            // ./CFML.g:509:2: ({...}? => '\"\"' )
            // ./CFML.g:509:2: {...}? => '\"\"'
            {
            if ( !( getMode() == DOUBLE_QUOTE_STRING_MODE ) ) {
                throw new FailedPredicateException(input, "ESCAPE_DOUBLE_QUOTE", " getMode() == DOUBLE_QUOTE_STRING_MODE ");
            }
            match("\"\""); 


            }

            this.type = _type;
        }
        finally {
    traceOut("ESCAPE_DOUBLE_QUOTE", 11);
        }
    }
    // $ANTLR end ESCAPE_DOUBLE_QUOTE

    // $ANTLR start DOUBLE_QUOTE_STRING
    public final void mDOUBLE_QUOTE_STRING() throws RecognitionException {
    traceIn("DOUBLE_QUOTE_STRING", 12);
        try {
            int _type = DOUBLE_QUOTE_STRING;
            // ./CFML.g:515:2: ({...}? =>~ ( '\"' ) )
            // ./CFML.g:515:2: {...}? =>~ ( '\"' )
            {
            if ( !( getMode() == DOUBLE_QUOTE_STRING_MODE ) ) {
                throw new FailedPredicateException(input, "DOUBLE_QUOTE_STRING", " getMode() == DOUBLE_QUOTE_STRING_MODE ");
            }
            if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='\uFFFE') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

            this.type = _type;
        }
        finally {
    traceOut("DOUBLE_QUOTE_STRING", 12);
        }
    }
    // $ANTLR end DOUBLE_QUOTE_STRING

    // $ANTLR start HASH
    public final void mHASH() throws RecognitionException {
    traceIn("HASH", 13);
        try {
            int _type = HASH;
            // ./CFML.g:521:2: ({...}? => '#' )
            // ./CFML.g:521:2: {...}? => '#'
            {
            if ( !(getMode() == NONE_MODE  || getMode() == HASH_CFML_MODE) ) {
                throw new FailedPredicateException(input, "HASH", "getMode() == NONE_MODE  || getMode() == HASH_CFML_MODE");
            }
            match('#'); 

            		if(getMode() == NONE_MODE)
            		{
            			setMode(HASH_CFML_MODE);
            		}
            		else
            		{
            			setMode(NONE_MODE);
            		}
            	

            }

            this.type = _type;
        }
        finally {
    traceOut("HASH", 13);
        }
    }
    // $ANTLR end HASH

    // $ANTLR start ESCAPE_HASH
    public final void mESCAPE_HASH() throws RecognitionException {
    traceIn("ESCAPE_HASH", 14);
        try {
            int _type = ESCAPE_HASH;
            // ./CFML.g:537:2: ({...}? => '##' )
            // ./CFML.g:537:2: {...}? => '##'
            {
            if ( !( getMode() == HASH_CFML_MODE ) ) {
                throw new FailedPredicateException(input, "ESCAPE_HASH", " getMode() == HASH_CFML_MODE ");
            }
            match("##"); 


            }

            this.type = _type;
        }
        finally {
    traceOut("ESCAPE_HASH", 14);
        }
    }
    // $ANTLR end ESCAPE_HASH

    // $ANTLR start HASH_CFML
    public final void mHASH_CFML() throws RecognitionException {
    traceIn("HASH_CFML", 15);
        try {
            int _type = HASH_CFML;
            // ./CFML.g:543:2: ({...}? =>~ ( '#' ) )
            // ./CFML.g:543:2: {...}? =>~ ( '#' )
            {
            if ( !( getMode() == HASH_CFML_MODE ) ) {
                throw new FailedPredicateException(input, "HASH_CFML", " getMode() == HASH_CFML_MODE ");
            }
            if ( (input.LA(1)>='\u0000' && input.LA(1)<='\"')||(input.LA(1)>='$' && input.LA(1)<='\uFFFE') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

            this.type = _type;
        }
        finally {
    traceOut("HASH_CFML", 15);
        }
    }
    // $ANTLR end HASH_CFML

    // $ANTLR start CFML
    public final void mCFML() throws RecognitionException {
    traceIn("CFML", 16);
        try {
            int _type = CFML;
            // ./CFML.g:549:2: ({...}? => ( '*' | '.' | '+' | '(' | ')' | '%' | '[' | ']' | '^' | '&' | '\\/' | '\\\\' | '-' | '#' ) )
            // ./CFML.g:549:2: {...}? => ( '*' | '.' | '+' | '(' | ')' | '%' | '[' | ']' | '^' | '&' | '\\/' | '\\\\' | '-' | '#' )
            {
            if ( !(getMode() == STARTTAG_MODE) ) {
                throw new FailedPredicateException(input, "CFML", "getMode() == STARTTAG_MODE");
            }
            if ( input.LA(1)=='#'||(input.LA(1)>='%' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='+')||(input.LA(1)>='-' && input.LA(1)<='/')||(input.LA(1)>='[' && input.LA(1)<='^') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

            this.type = _type;
        }
        finally {
    traceOut("CFML", 16);
        }
    }
    // $ANTLR end CFML

    // $ANTLR start TAG_NAME
    public final void mTAG_NAME() throws RecognitionException {
    traceIn("TAG_NAME", 17);
        try {
            // ./CFML.g:557:2: ( ( LETTER ) ( TAG_IDENT ) ( ( COLON ) ( TAG_IDENT ) )? )
            // ./CFML.g:557:2: ( LETTER ) ( TAG_IDENT ) ( ( COLON ) ( TAG_IDENT ) )?
            {
            // ./CFML.g:557:2: ( LETTER )
            // ./CFML.g:557:3: LETTER
            {
            mLETTER(); 

            }

            // ./CFML.g:557:10: ( TAG_IDENT )
            // ./CFML.g:557:11: TAG_IDENT
            {
            mTAG_IDENT(); 

            }

            // ./CFML.g:557:21: ( ( COLON ) ( TAG_IDENT ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==':') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ./CFML.g:557:22: ( COLON ) ( TAG_IDENT )
                    {
                    // ./CFML.g:557:22: ( COLON )
                    // ./CFML.g:557:23: COLON
                    {
                    mCOLON(); 

                    }

                    // ./CFML.g:557:29: ( TAG_IDENT )
                    // ./CFML.g:557:30: TAG_IDENT
                    {
                    mTAG_IDENT(); 

                    }


                    }
                    break;

            }


            }

        }
        finally {
    traceOut("TAG_NAME", 17);
        }
    }
    // $ANTLR end TAG_NAME

    // $ANTLR start TAG_IDENT
    public final void mTAG_IDENT() throws RecognitionException {
    traceIn("TAG_IDENT", 18);
        try {
            // ./CFML.g:562:2: ( ( LETTER | DIGIT | UNDERSCORE )* )
            // ./CFML.g:562:2: ( LETTER | DIGIT | UNDERSCORE )*
            {
            // ./CFML.g:562:2: ( LETTER | DIGIT | UNDERSCORE )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')||(LA4_0>='A' && LA4_0<='Z')||LA4_0=='_'||(LA4_0>='a' && LA4_0<='z')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ./CFML.g:
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
            	    break loop4;
                }
            } while (true);


            }

        }
        finally {
    traceOut("TAG_IDENT", 18);
        }
    }
    // $ANTLR end TAG_IDENT

    // $ANTLR start DIGIT
    public final void mDIGIT() throws RecognitionException {
    traceIn("DIGIT", 19);
        try {
            // ./CFML.g:567:2: ( '0' .. '9' )
            // ./CFML.g:567:2: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
    traceOut("DIGIT", 19);
        }
    }
    // $ANTLR end DIGIT

    // $ANTLR start LETTER
    public final void mLETTER() throws RecognitionException {
    traceIn("LETTER", 20);
        try {
            // ./CFML.g:572:2: ( 'a' .. 'z' | 'A' .. 'Z' )
            // ./CFML.g:
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
    traceOut("LETTER", 20);
        }
    }
    // $ANTLR end LETTER

    // $ANTLR start UNDERSCORE
    public final void mUNDERSCORE() throws RecognitionException {
    traceIn("UNDERSCORE", 21);
        try {
            // ./CFML.g:577:2: ( '_' )
            // ./CFML.g:577:2: '_'
            {
            match('_'); 

            }

        }
        finally {
    traceOut("UNDERSCORE", 21);
        }
    }
    // $ANTLR end UNDERSCORE

    // $ANTLR start COLON
    public final void mCOLON() throws RecognitionException {
    traceIn("COLON", 22);
        try {
            // ./CFML.g:582:2: ( ':' )
            // ./CFML.g:582:2: ':'
            {
            match(':'); 

            }

        }
        finally {
    traceOut("COLON", 22);
        }
    }
    // $ANTLR end COLON

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
    traceIn("WS", 23);
        try {
            int _type = WS;
            // ./CFML.g:589:2: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // ./CFML.g:589:2: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
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
    traceOut("WS", 23);
        }
    }
    // $ANTLR end WS

    // $ANTLR start COMMENT
    public final void mCOMMENT() throws RecognitionException {
    traceIn("COMMENT", 24);
        try {
            int _type = COMMENT;
            // ./CFML.g:597:2: ( '<!---' ( options {greedy=false; } : . )* '--->' )
            // ./CFML.g:597:2: '<!---' ( options {greedy=false; } : . )* '--->'
            {
            match("<!---"); 

            // ./CFML.g:597:10: ( options {greedy=false; } : . )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0=='-') ) {
                    int LA5_1 = input.LA(2);

                    if ( (LA5_1=='-') ) {
                        int LA5_3 = input.LA(3);

                        if ( (LA5_3=='-') ) {
                            int LA5_4 = input.LA(4);

                            if ( ((LA5_4>='\u0000' && LA5_4<='=')||(LA5_4>='?' && LA5_4<='\uFFFE')) ) {
                                alt5=1;
                            }
                            else if ( (LA5_4=='>') ) {
                                alt5=2;
                            }


                        }
                        else if ( ((LA5_3>='\u0000' && LA5_3<=',')||(LA5_3>='.' && LA5_3<='\uFFFE')) ) {
                            alt5=1;
                        }


                    }
                    else if ( ((LA5_1>='\u0000' && LA5_1<=',')||(LA5_1>='.' && LA5_1<='\uFFFE')) ) {
                        alt5=1;
                    }


                }
                else if ( ((LA5_0>='\u0000' && LA5_0<=',')||(LA5_0>='.' && LA5_0<='\uFFFE')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ./CFML.g:597:38: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            match("--->"); 


            			channel=COMMENT_CHANNEL; //90 is hte comment channel
            	

            }

            this.type = _type;
        }
        finally {
    traceOut("COMMENT", 24);
        }
    }
    // $ANTLR end COMMENT

    // $ANTLR start OTHER
    public final void mOTHER() throws RecognitionException {
    traceIn("OTHER", 25);
        try {
            int _type = OTHER;
            // ./CFML.g:605:2: ({...}? => ( options {greedy=false; } : . ) )
            // ./CFML.g:605:2: {...}? => ( options {greedy=false; } : . )
            {
            if ( !(getMode() == NONE_MODE) ) {
                throw new FailedPredicateException(input, "OTHER", "getMode() == NONE_MODE");
            }
            // ./CFML.g:606:2: ( options {greedy=false; } : . )
            // ./CFML.g:606:29: .
            {
            matchAny(); 

            }


            		channel=TEXT_CHANNEL; //test is on a seperate channel, in case you want it
            	

            }

            this.type = _type;
        }
        finally {
    traceOut("OTHER", 25);
        }
    }
    // $ANTLR end OTHER

    public void mTokens() throws RecognitionException {
        // ./CFML.g:1:10: ( END_TAG_OPEN | END_TAG_CLOSE | START_TAG_OPEN | START_TAG_CLOSE | TAG_ATTRIBUTE | EQUALS | SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING | DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING | HASH | ESCAPE_HASH | HASH_CFML | CFML | WS | COMMENT | OTHER )
        int alt6=19;
        int LA6_0 = input.LA(1);

        if ( (LA6_0=='<') ) {
            int LA6_1 = input.LA(2);

            if ( (LA6_1=='/') && (
            		getMode() == NONE_MODE
            	)) {
                alt6=1;
            }
            else if ( (LA6_1=='!') ) {
                alt6=18;
            }
            else if ( ((LA6_1>='A' && LA6_1<='Z')||(LA6_1>='a' && LA6_1<='z')) && (
            		getMode() == NONE_MODE
            	)) {
                alt6=3;
            }
            else if ( ( getMode() == SINGLE_QUOTE_STRING_MODE ) ) {
                alt6=9;
            }
            else if ( ( getMode() == DOUBLE_QUOTE_STRING_MODE ) ) {
                alt6=12;
            }
            else if ( ( getMode() == HASH_CFML_MODE ) ) {
                alt6=15;
            }
            else if ( (getMode() == NONE_MODE) ) {
                alt6=19;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( END_TAG_OPEN | END_TAG_CLOSE | START_TAG_OPEN | START_TAG_CLOSE | TAG_ATTRIBUTE | EQUALS | SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING | DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING | HASH | ESCAPE_HASH | HASH_CFML | CFML | WS | COMMENT | OTHER );", 6, 1, input);

                throw nvae;
            }
        }
        else if ( (LA6_0=='>') && (( getMode() == DOUBLE_QUOTE_STRING_MODE ||getMode() == NONE_MODE|| getMode() == HASH_CFML_MODE ||getMode() == ENDTAG_MODE||getMode() == STARTTAG_MODE|| getMode() == SINGLE_QUOTE_STRING_MODE ))) {
            int LA6_2 = input.LA(2);

            if ( (getMode() == ENDTAG_MODE) ) {
                alt6=2;
            }
            else if ( (getMode() == STARTTAG_MODE) ) {
                alt6=4;
            }
            else if ( ( getMode() == SINGLE_QUOTE_STRING_MODE ) ) {
                alt6=9;
            }
            else if ( ( getMode() == DOUBLE_QUOTE_STRING_MODE ) ) {
                alt6=12;
            }
            else if ( ( getMode() == HASH_CFML_MODE ) ) {
                alt6=15;
            }
            else if ( (getMode() == NONE_MODE) ) {
                alt6=19;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( END_TAG_OPEN | END_TAG_CLOSE | START_TAG_OPEN | START_TAG_CLOSE | TAG_ATTRIBUTE | EQUALS | SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING | DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING | HASH | ESCAPE_HASH | HASH_CFML | CFML | WS | COMMENT | OTHER );", 6, 2, input);

                throw nvae;
            }
        }
        else if ( (LA6_0=='/') && (( getMode() == DOUBLE_QUOTE_STRING_MODE ||getMode() == NONE_MODE|| getMode() == HASH_CFML_MODE ||getMode() == STARTTAG_MODE|| getMode() == SINGLE_QUOTE_STRING_MODE ))) {
            int LA6_3 = input.LA(2);

            if ( (LA6_3=='>') && (getMode() == STARTTAG_MODE)) {
                alt6=4;
            }
            else if ( ( getMode() == SINGLE_QUOTE_STRING_MODE ) ) {
                alt6=9;
            }
            else if ( ( getMode() == DOUBLE_QUOTE_STRING_MODE ) ) {
                alt6=12;
            }
            else if ( ( getMode() == HASH_CFML_MODE ) ) {
                alt6=15;
            }
            else if ( (getMode() == STARTTAG_MODE) ) {
                alt6=16;
            }
            else if ( (getMode() == NONE_MODE) ) {
                alt6=19;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( END_TAG_OPEN | END_TAG_CLOSE | START_TAG_OPEN | START_TAG_CLOSE | TAG_ATTRIBUTE | EQUALS | SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING | DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING | HASH | ESCAPE_HASH | HASH_CFML | CFML | WS | COMMENT | OTHER );", 6, 3, input);

                throw nvae;
            }
        }
        else if ( ((LA6_0>='0' && LA6_0<='9')||(LA6_0>='A' && LA6_0<='Z')||LA6_0=='_'||(LA6_0>='a' && LA6_0<='z')) && (( getMode() == DOUBLE_QUOTE_STRING_MODE ||getMode() == NONE_MODE|| getMode() == HASH_CFML_MODE ||getMode() == STARTTAG_MODE|| getMode() == SINGLE_QUOTE_STRING_MODE ))) {
            int LA6_4 = input.LA(2);

            if ( ((LA6_4>='0' && LA6_4<='9')||(LA6_4>='A' && LA6_4<='Z')||LA6_4=='_'||(LA6_4>='a' && LA6_4<='z')) && (getMode() == STARTTAG_MODE)) {
                alt6=5;
            }
            else if ( (getMode() == STARTTAG_MODE) ) {
                alt6=5;
            }
            else if ( ( getMode() == SINGLE_QUOTE_STRING_MODE ) ) {
                alt6=9;
            }
            else if ( ( getMode() == DOUBLE_QUOTE_STRING_MODE ) ) {
                alt6=12;
            }
            else if ( ( getMode() == HASH_CFML_MODE ) ) {
                alt6=15;
            }
            else if ( (getMode() == NONE_MODE) ) {
                alt6=19;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( END_TAG_OPEN | END_TAG_CLOSE | START_TAG_OPEN | START_TAG_CLOSE | TAG_ATTRIBUTE | EQUALS | SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING | DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING | HASH | ESCAPE_HASH | HASH_CFML | CFML | WS | COMMENT | OTHER );", 6, 4, input);

                throw nvae;
            }
        }
        else if ( (LA6_0=='=') && (( getMode() == DOUBLE_QUOTE_STRING_MODE ||getMode() == NONE_MODE|| getMode() == HASH_CFML_MODE ||getMode() == STARTTAG_MODE|| getMode() == SINGLE_QUOTE_STRING_MODE ))) {
            int LA6_5 = input.LA(2);

            if ( (getMode() == STARTTAG_MODE) ) {
                alt6=6;
            }
            else if ( ( getMode() == SINGLE_QUOTE_STRING_MODE ) ) {
                alt6=9;
            }
            else if ( ( getMode() == DOUBLE_QUOTE_STRING_MODE ) ) {
                alt6=12;
            }
            else if ( ( getMode() == HASH_CFML_MODE ) ) {
                alt6=15;
            }
            else if ( (getMode() == NONE_MODE) ) {
                alt6=19;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( END_TAG_OPEN | END_TAG_CLOSE | START_TAG_OPEN | START_TAG_CLOSE | TAG_ATTRIBUTE | EQUALS | SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING | DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING | HASH | ESCAPE_HASH | HASH_CFML | CFML | WS | COMMENT | OTHER );", 6, 5, input);

                throw nvae;
            }
        }
        else if ( (LA6_0=='\'') && (( getMode() == DOUBLE_QUOTE_STRING_MODE ||getMode() == NONE_MODE|| getMode() == HASH_CFML_MODE ||getMode() == STARTTAG_MODE  || getMode() == SINGLE_QUOTE_STRING_MODE|| getMode() == SINGLE_QUOTE_STRING_MODE ))) {
            int LA6_6 = input.LA(2);

            if ( (LA6_6=='\'') && ( getMode() == SINGLE_QUOTE_STRING_MODE )) {
                alt6=8;
            }
            else if ( (getMode() == STARTTAG_MODE  || getMode() == SINGLE_QUOTE_STRING_MODE) ) {
                alt6=7;
            }
            else if ( ( getMode() == DOUBLE_QUOTE_STRING_MODE ) ) {
                alt6=12;
            }
            else if ( ( getMode() == HASH_CFML_MODE ) ) {
                alt6=15;
            }
            else if ( (getMode() == NONE_MODE) ) {
                alt6=19;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( END_TAG_OPEN | END_TAG_CLOSE | START_TAG_OPEN | START_TAG_CLOSE | TAG_ATTRIBUTE | EQUALS | SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING | DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING | HASH | ESCAPE_HASH | HASH_CFML | CFML | WS | COMMENT | OTHER );", 6, 6, input);

                throw nvae;
            }
        }
        else if ( (LA6_0=='\"') && (( getMode() == DOUBLE_QUOTE_STRING_MODE ||getMode() == NONE_MODE|| getMode() == HASH_CFML_MODE ||getMode() == STARTTAG_MODE  || getMode() == DOUBLE_QUOTE_STRING_MODE|| getMode() == SINGLE_QUOTE_STRING_MODE ))) {
            int LA6_7 = input.LA(2);

            if ( (LA6_7=='\"') && ( getMode() == DOUBLE_QUOTE_STRING_MODE )) {
                alt6=11;
            }
            else if ( ( getMode() == SINGLE_QUOTE_STRING_MODE ) ) {
                alt6=9;
            }
            else if ( (getMode() == STARTTAG_MODE  || getMode() == DOUBLE_QUOTE_STRING_MODE) ) {
                alt6=10;
            }
            else if ( ( getMode() == HASH_CFML_MODE ) ) {
                alt6=15;
            }
            else if ( (getMode() == NONE_MODE) ) {
                alt6=19;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( END_TAG_OPEN | END_TAG_CLOSE | START_TAG_OPEN | START_TAG_CLOSE | TAG_ATTRIBUTE | EQUALS | SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING | DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING | HASH | ESCAPE_HASH | HASH_CFML | CFML | WS | COMMENT | OTHER );", 6, 7, input);

                throw nvae;
            }
        }
        else if ( (LA6_0=='#') && (( getMode() == DOUBLE_QUOTE_STRING_MODE ||getMode() == NONE_MODE|| getMode() == HASH_CFML_MODE ||getMode() == STARTTAG_MODE||getMode() == NONE_MODE  || getMode() == HASH_CFML_MODE|| getMode() == SINGLE_QUOTE_STRING_MODE ))) {
            int LA6_8 = input.LA(2);

            if ( (LA6_8=='#') && ( getMode() == HASH_CFML_MODE )) {
                alt6=14;
            }
            else if ( ( getMode() == SINGLE_QUOTE_STRING_MODE ) ) {
                alt6=9;
            }
            else if ( ( getMode() == DOUBLE_QUOTE_STRING_MODE ) ) {
                alt6=12;
            }
            else if ( (getMode() == NONE_MODE  || getMode() == HASH_CFML_MODE) ) {
                alt6=13;
            }
            else if ( (getMode() == STARTTAG_MODE) ) {
                alt6=16;
            }
            else if ( (getMode() == NONE_MODE) ) {
                alt6=19;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( END_TAG_OPEN | END_TAG_CLOSE | START_TAG_OPEN | START_TAG_CLOSE | TAG_ATTRIBUTE | EQUALS | SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING | DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING | HASH | ESCAPE_HASH | HASH_CFML | CFML | WS | COMMENT | OTHER );", 6, 8, input);

                throw nvae;
            }
        }
        else if ( ((LA6_0>='%' && LA6_0<='&')||(LA6_0>='(' && LA6_0<='+')||(LA6_0>='-' && LA6_0<='.')||(LA6_0>='[' && LA6_0<='^')) && (( getMode() == DOUBLE_QUOTE_STRING_MODE ||getMode() == NONE_MODE|| getMode() == HASH_CFML_MODE ||getMode() == STARTTAG_MODE|| getMode() == SINGLE_QUOTE_STRING_MODE ))) {
            int LA6_9 = input.LA(2);

            if ( ( getMode() == SINGLE_QUOTE_STRING_MODE ) ) {
                alt6=9;
            }
            else if ( ( getMode() == DOUBLE_QUOTE_STRING_MODE ) ) {
                alt6=12;
            }
            else if ( ( getMode() == HASH_CFML_MODE ) ) {
                alt6=15;
            }
            else if ( (getMode() == STARTTAG_MODE) ) {
                alt6=16;
            }
            else if ( (getMode() == NONE_MODE) ) {
                alt6=19;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( END_TAG_OPEN | END_TAG_CLOSE | START_TAG_OPEN | START_TAG_CLOSE | TAG_ATTRIBUTE | EQUALS | SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING | DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING | HASH | ESCAPE_HASH | HASH_CFML | CFML | WS | COMMENT | OTHER );", 6, 9, input);

                throw nvae;
            }
        }
        else if ( ((LA6_0>='\t' && LA6_0<='\n')||(LA6_0>='\f' && LA6_0<='\r')||LA6_0==' ') ) {
            int LA6_10 = input.LA(2);

            if ( ( getMode() == SINGLE_QUOTE_STRING_MODE ) ) {
                alt6=9;
            }
            else if ( ( getMode() == DOUBLE_QUOTE_STRING_MODE ) ) {
                alt6=12;
            }
            else if ( ( getMode() == HASH_CFML_MODE ) ) {
                alt6=15;
            }
            else if ( (!(( getMode() == DOUBLE_QUOTE_STRING_MODE ||getMode() == NONE_MODE|| getMode() == HASH_CFML_MODE || getMode() == SINGLE_QUOTE_STRING_MODE ))) ) {
                alt6=17;
            }
            else if ( (getMode() == NONE_MODE) ) {
                alt6=19;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( END_TAG_OPEN | END_TAG_CLOSE | START_TAG_OPEN | START_TAG_CLOSE | TAG_ATTRIBUTE | EQUALS | SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING | DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING | HASH | ESCAPE_HASH | HASH_CFML | CFML | WS | COMMENT | OTHER );", 6, 10, input);

                throw nvae;
            }
        }
        else if ( ((LA6_0>='\u0000' && LA6_0<='\b')||LA6_0=='\u000B'||(LA6_0>='\u000E' && LA6_0<='\u001F')||LA6_0=='!'||LA6_0=='$'||LA6_0==','||(LA6_0>=':' && LA6_0<=';')||(LA6_0>='?' && LA6_0<='@')||LA6_0=='`'||(LA6_0>='{' && LA6_0<='\uFFFE')) && (( getMode() == DOUBLE_QUOTE_STRING_MODE ||getMode() == NONE_MODE|| getMode() == HASH_CFML_MODE || getMode() == SINGLE_QUOTE_STRING_MODE ))) {
            int LA6_11 = input.LA(2);

            if ( ( getMode() == SINGLE_QUOTE_STRING_MODE ) ) {
                alt6=9;
            }
            else if ( ( getMode() == DOUBLE_QUOTE_STRING_MODE ) ) {
                alt6=12;
            }
            else if ( ( getMode() == HASH_CFML_MODE ) ) {
                alt6=15;
            }
            else if ( (getMode() == NONE_MODE) ) {
                alt6=19;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( END_TAG_OPEN | END_TAG_CLOSE | START_TAG_OPEN | START_TAG_CLOSE | TAG_ATTRIBUTE | EQUALS | SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING | DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING | HASH | ESCAPE_HASH | HASH_CFML | CFML | WS | COMMENT | OTHER );", 6, 11, input);

                throw nvae;
            }
        }
        else {
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( END_TAG_OPEN | END_TAG_CLOSE | START_TAG_OPEN | START_TAG_CLOSE | TAG_ATTRIBUTE | EQUALS | SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING | DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING | HASH | ESCAPE_HASH | HASH_CFML | CFML | WS | COMMENT | OTHER );", 6, 0, input);

            throw nvae;
        }
        switch (alt6) {
            case 1 :
                // ./CFML.g:1:10: END_TAG_OPEN
                {
                mEND_TAG_OPEN(); 

                }
                break;
            case 2 :
                // ./CFML.g:1:23: END_TAG_CLOSE
                {
                mEND_TAG_CLOSE(); 

                }
                break;
            case 3 :
                // ./CFML.g:1:37: START_TAG_OPEN
                {
                mSTART_TAG_OPEN(); 

                }
                break;
            case 4 :
                // ./CFML.g:1:52: START_TAG_CLOSE
                {
                mSTART_TAG_CLOSE(); 

                }
                break;
            case 5 :
                // ./CFML.g:1:68: TAG_ATTRIBUTE
                {
                mTAG_ATTRIBUTE(); 

                }
                break;
            case 6 :
                // ./CFML.g:1:82: EQUALS
                {
                mEQUALS(); 

                }
                break;
            case 7 :
                // ./CFML.g:1:89: SINGLE_QUOTE
                {
                mSINGLE_QUOTE(); 

                }
                break;
            case 8 :
                // ./CFML.g:1:102: ESCAPE_SINGLE_QUOTE
                {
                mESCAPE_SINGLE_QUOTE(); 

                }
                break;
            case 9 :
                // ./CFML.g:1:122: SINGLE_QUOTE_STRING
                {
                mSINGLE_QUOTE_STRING(); 

                }
                break;
            case 10 :
                // ./CFML.g:1:142: DOUBLE_QUOTE
                {
                mDOUBLE_QUOTE(); 

                }
                break;
            case 11 :
                // ./CFML.g:1:155: ESCAPE_DOUBLE_QUOTE
                {
                mESCAPE_DOUBLE_QUOTE(); 

                }
                break;
            case 12 :
                // ./CFML.g:1:175: DOUBLE_QUOTE_STRING
                {
                mDOUBLE_QUOTE_STRING(); 

                }
                break;
            case 13 :
                // ./CFML.g:1:195: HASH
                {
                mHASH(); 

                }
                break;
            case 14 :
                // ./CFML.g:1:200: ESCAPE_HASH
                {
                mESCAPE_HASH(); 

                }
                break;
            case 15 :
                // ./CFML.g:1:212: HASH_CFML
                {
                mHASH_CFML(); 

                }
                break;
            case 16 :
                // ./CFML.g:1:222: CFML
                {
                mCFML(); 

                }
                break;
            case 17 :
                // ./CFML.g:1:227: WS
                {
                mWS(); 

                }
                break;
            case 18 :
                // ./CFML.g:1:230: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 19 :
                // ./CFML.g:1:238: OTHER
                {
                mOTHER(); 

                }
                break;

        }

    }


 

}
