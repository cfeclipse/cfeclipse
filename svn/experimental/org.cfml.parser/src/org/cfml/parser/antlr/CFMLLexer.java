// $ANTLR 3.0.1 /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g 2008-04-23 07:14:08

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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class CFMLLexer extends Lexer {
    public static final int TAG_ATTRIBUTE=13;
    public static final int OTHER=33;
    public static final int CFTAG=4;
    public static final int LETTER=26;
    public static final int DOUBLE_QUOTE=15;
    public static final int HASH_CFML=24;
    public static final int CFML_STATEMENT=8;
    public static final int HASH=22;
    public static final int END_TAG_OPEN=11;
    public static final int UNDERSCORE=28;
    public static final int EQUALS=14;
    public static final int Tokens=34;
    public static final int EOF=-1;
    public static final int START_TAG_CLOSE=10;
    public static final int ESCAPE_SINGLE_QUOTE=19;
    public static final int COLON=30;
    public static final int TAG_NAME=25;
    public static final int CUSTOMTAG=5;
    public static final int SINGLE_QUOTE=18;
    public static final int SINGLE_QUOTE_STRING=20;
    public static final int STRING_LITERAL=7;
    public static final int WS=31;
    public static final int DOUBLE_QUOTE_STRING=17;
    public static final int TAG_IDENT=29;
    public static final int END_TAG_CLOSE=12;
    public static final int ESCAPE_HASH=23;
    public static final int ESCAPE_DOUBLE_QUOTE=16;
    public static final int DIGIT=27;
    public static final int COMMENT=32;
    public static final int CFML=21;
    public static final int START_TAG_OPEN=9;
    public static final int IMPORTTAG=6;

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
    public String getGrammarFileName() { return "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g"; }

    // $ANTLR start END_TAG_OPEN
    public final void mEND_TAG_OPEN() throws RecognitionException {
        try {
            int _type = END_TAG_OPEN;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:415:2: ({...}? => '</' TAG_NAME )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:416:2: {...}? => '</' TAG_NAME
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
        }
    }
    // $ANTLR end END_TAG_OPEN

    // $ANTLR start END_TAG_CLOSE
    public final void mEND_TAG_CLOSE() throws RecognitionException {
        try {
            int _type = END_TAG_CLOSE;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:426:2: ({...}? => '>' )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:427:2: {...}? => '>'
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
        }
    }
    // $ANTLR end END_TAG_CLOSE

    // $ANTLR start START_TAG_OPEN
    public final void mSTART_TAG_OPEN() throws RecognitionException {
        try {
            int _type = START_TAG_OPEN;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:433:2: ({...}? => '<' TAG_NAME )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:434:2: {...}? => '<' TAG_NAME
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
        }
    }
    // $ANTLR end START_TAG_OPEN

    // $ANTLR start START_TAG_CLOSE
    public final void mSTART_TAG_CLOSE() throws RecognitionException {
        try {
            int _type = START_TAG_CLOSE;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:445:2: ({...}? => ( '/' )? '>' )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:446:2: {...}? => ( '/' )? '>'
            {
            if ( !(getMode() == STARTTAG_MODE) ) {
                throw new FailedPredicateException(input, "START_TAG_CLOSE", "getMode() == STARTTAG_MODE");
            }
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:447:2: ( '/' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='/') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:447:2: '/'
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
        }
    }
    // $ANTLR end START_TAG_CLOSE

    // $ANTLR start TAG_ATTRIBUTE
    public final void mTAG_ATTRIBUTE() throws RecognitionException {
        try {
            int _type = TAG_ATTRIBUTE;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:452:2: ({...}? => ( LETTER | DIGIT | UNDERSCORE )+ )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:453:2: {...}? => ( LETTER | DIGIT | UNDERSCORE )+
            {
            if ( !(getMode() == STARTTAG_MODE) ) {
                throw new FailedPredicateException(input, "TAG_ATTRIBUTE", "getMode() == STARTTAG_MODE");
            }
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:454:2: ( LETTER | DIGIT | UNDERSCORE )+
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
            	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:
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
        }
    }
    // $ANTLR end TAG_ATTRIBUTE

    // $ANTLR start EQUALS
    public final void mEQUALS() throws RecognitionException {
        try {
            int _type = EQUALS;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:458:2: ({...}? => '=' )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:459:2: {...}? => '='
            {
            if ( !(getMode() == STARTTAG_MODE) ) {
                throw new FailedPredicateException(input, "EQUALS", "getMode() == STARTTAG_MODE");
            }
            match('='); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end EQUALS

    // $ANTLR start SINGLE_QUOTE
    public final void mSINGLE_QUOTE() throws RecognitionException {
        try {
            int _type = SINGLE_QUOTE;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:464:2: ({...}? => '\\'' )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:465:2: {...}? => '\\''
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
        }
    }
    // $ANTLR end SINGLE_QUOTE

    // $ANTLR start ESCAPE_SINGLE_QUOTE
    public final void mESCAPE_SINGLE_QUOTE() throws RecognitionException {
        try {
            int _type = ESCAPE_SINGLE_QUOTE;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:480:2: ({...}? => '\\'\\'' )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:481:2: {...}? => '\\'\\''
            {
            if ( !( getMode() == SINGLE_QUOTE_STRING_MODE ) ) {
                throw new FailedPredicateException(input, "ESCAPE_SINGLE_QUOTE", " getMode() == SINGLE_QUOTE_STRING_MODE ");
            }
            match("\'\'"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end ESCAPE_SINGLE_QUOTE

    // $ANTLR start SINGLE_QUOTE_STRING
    public final void mSINGLE_QUOTE_STRING() throws RecognitionException {
        try {
            int _type = SINGLE_QUOTE_STRING;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:486:2: ({...}? =>~ ( '\\'' ) )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:487:2: {...}? =>~ ( '\\'' )
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
        }
    }
    // $ANTLR end SINGLE_QUOTE_STRING

    // $ANTLR start DOUBLE_QUOTE
    public final void mDOUBLE_QUOTE() throws RecognitionException {
        try {
            int _type = DOUBLE_QUOTE;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:492:2: ({...}? => '\"' )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:493:2: {...}? => '\"'
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
        }
    }
    // $ANTLR end DOUBLE_QUOTE

    // $ANTLR start ESCAPE_DOUBLE_QUOTE
    public final void mESCAPE_DOUBLE_QUOTE() throws RecognitionException {
        try {
            int _type = ESCAPE_DOUBLE_QUOTE;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:508:2: ({...}? => '\"\"' )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:509:2: {...}? => '\"\"'
            {
            if ( !( getMode() == DOUBLE_QUOTE_STRING_MODE ) ) {
                throw new FailedPredicateException(input, "ESCAPE_DOUBLE_QUOTE", " getMode() == DOUBLE_QUOTE_STRING_MODE ");
            }
            match("\"\""); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end ESCAPE_DOUBLE_QUOTE

    // $ANTLR start DOUBLE_QUOTE_STRING
    public final void mDOUBLE_QUOTE_STRING() throws RecognitionException {
        try {
            int _type = DOUBLE_QUOTE_STRING;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:514:2: ({...}? =>~ ( '\"' ) )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:515:2: {...}? =>~ ( '\"' )
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
        }
    }
    // $ANTLR end DOUBLE_QUOTE_STRING

    // $ANTLR start HASH
    public final void mHASH() throws RecognitionException {
        try {
            int _type = HASH;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:520:2: ({...}? => '#' )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:521:2: {...}? => '#'
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
        }
    }
    // $ANTLR end HASH

    // $ANTLR start ESCAPE_HASH
    public final void mESCAPE_HASH() throws RecognitionException {
        try {
            int _type = ESCAPE_HASH;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:536:2: ({...}? => '##' )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:537:2: {...}? => '##'
            {
            if ( !( getMode() == HASH_CFML_MODE ) ) {
                throw new FailedPredicateException(input, "ESCAPE_HASH", " getMode() == HASH_CFML_MODE ");
            }
            match("##"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end ESCAPE_HASH

    // $ANTLR start HASH_CFML
    public final void mHASH_CFML() throws RecognitionException {
        try {
            int _type = HASH_CFML;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:542:2: ({...}? =>~ ( '#' ) )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:543:2: {...}? =>~ ( '#' )
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
        }
    }
    // $ANTLR end HASH_CFML

    // $ANTLR start CFML
    public final void mCFML() throws RecognitionException {
        try {
            int _type = CFML;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:548:2: ({...}? => ( '*' | '.' | '+' | '(' | ')' | '%' | '[' | ']' | '^' | '&' | '\\/' | '\\\\' | '-' | '#' ) )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:549:2: {...}? => ( '*' | '.' | '+' | '(' | ')' | '%' | '[' | ']' | '^' | '&' | '\\/' | '\\\\' | '-' | '#' )
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
        }
    }
    // $ANTLR end CFML

    // $ANTLR start TAG_NAME
    public final void mTAG_NAME() throws RecognitionException {
        try {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:556:2: ( ( LETTER ) ( TAG_IDENT ) ( ( COLON ) ( TAG_IDENT ) )? )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:557:2: ( LETTER ) ( TAG_IDENT ) ( ( COLON ) ( TAG_IDENT ) )?
            {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:557:2: ( LETTER )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:557:3: LETTER
            {
            mLETTER(); 

            }

            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:557:10: ( TAG_IDENT )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:557:11: TAG_IDENT
            {
            mTAG_IDENT(); 

            }

            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:557:21: ( ( COLON ) ( TAG_IDENT ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==':') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:557:22: ( COLON ) ( TAG_IDENT )
                    {
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:557:22: ( COLON )
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:557:23: COLON
                    {
                    mCOLON(); 

                    }

                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:557:29: ( TAG_IDENT )
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:557:30: TAG_IDENT
                    {
                    mTAG_IDENT(); 

                    }


                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end TAG_NAME

    // $ANTLR start TAG_IDENT
    public final void mTAG_IDENT() throws RecognitionException {
        try {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:561:2: ( ( LETTER | DIGIT | UNDERSCORE )* )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:562:2: ( LETTER | DIGIT | UNDERSCORE )*
            {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:562:2: ( LETTER | DIGIT | UNDERSCORE )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')||(LA4_0>='A' && LA4_0<='Z')||LA4_0=='_'||(LA4_0>='a' && LA4_0<='z')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:
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
        }
    }
    // $ANTLR end TAG_IDENT

    // $ANTLR start DIGIT
    public final void mDIGIT() throws RecognitionException {
        try {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:566:2: ( '0' .. '9' )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:567:2: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end DIGIT

    // $ANTLR start LETTER
    public final void mLETTER() throws RecognitionException {
        try {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:571:2: ( 'a' .. 'z' | 'A' .. 'Z' )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:
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
        }
    }
    // $ANTLR end LETTER

    // $ANTLR start UNDERSCORE
    public final void mUNDERSCORE() throws RecognitionException {
        try {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:576:2: ( '_' )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:577:2: '_'
            {
            match('_'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end UNDERSCORE

    // $ANTLR start COLON
    public final void mCOLON() throws RecognitionException {
        try {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:581:2: ( ':' )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:582:2: ':'
            {
            match(':'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end COLON

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:588:2: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:589:2: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
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
        }
    }
    // $ANTLR end WS

    // $ANTLR start COMMENT
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:596:2: ( '<!---' ( options {greedy=false; } : . )* '--->' )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:597:2: '<!---' ( options {greedy=false; } : . )* '--->'
            {
            match("<!---"); 

            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:597:10: ( options {greedy=false; } : . )*
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
            	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:597:38: .
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
        }
    }
    // $ANTLR end COMMENT

    // $ANTLR start OTHER
    public final void mOTHER() throws RecognitionException {
        try {
            int _type = OTHER;
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:604:2: ({...}? => ( options {greedy=false; } : . ) )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:605:2: {...}? => ( options {greedy=false; } : . )
            {
            if ( !(getMode() == NONE_MODE) ) {
                throw new FailedPredicateException(input, "OTHER", "getMode() == NONE_MODE");
            }
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:606:2: ( options {greedy=false; } : . )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:606:29: .
            {
            matchAny(); 

            }


            		channel=TEXT_CHANNEL; //test is on a seperate channel, in case you want it
            	

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end OTHER

    public void mTokens() throws RecognitionException {
        // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:8: ( END_TAG_OPEN | END_TAG_CLOSE | START_TAG_OPEN | START_TAG_CLOSE | TAG_ATTRIBUTE | EQUALS | SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING | DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING | HASH | ESCAPE_HASH | HASH_CFML | CFML | WS | COMMENT | OTHER )
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
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:10: END_TAG_OPEN
                {
                mEND_TAG_OPEN(); 

                }
                break;
            case 2 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:23: END_TAG_CLOSE
                {
                mEND_TAG_CLOSE(); 

                }
                break;
            case 3 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:37: START_TAG_OPEN
                {
                mSTART_TAG_OPEN(); 

                }
                break;
            case 4 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:52: START_TAG_CLOSE
                {
                mSTART_TAG_CLOSE(); 

                }
                break;
            case 5 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:68: TAG_ATTRIBUTE
                {
                mTAG_ATTRIBUTE(); 

                }
                break;
            case 6 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:82: EQUALS
                {
                mEQUALS(); 

                }
                break;
            case 7 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:89: SINGLE_QUOTE
                {
                mSINGLE_QUOTE(); 

                }
                break;
            case 8 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:102: ESCAPE_SINGLE_QUOTE
                {
                mESCAPE_SINGLE_QUOTE(); 

                }
                break;
            case 9 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:122: SINGLE_QUOTE_STRING
                {
                mSINGLE_QUOTE_STRING(); 

                }
                break;
            case 10 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:142: DOUBLE_QUOTE
                {
                mDOUBLE_QUOTE(); 

                }
                break;
            case 11 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:155: ESCAPE_DOUBLE_QUOTE
                {
                mESCAPE_DOUBLE_QUOTE(); 

                }
                break;
            case 12 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:175: DOUBLE_QUOTE_STRING
                {
                mDOUBLE_QUOTE_STRING(); 

                }
                break;
            case 13 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:195: HASH
                {
                mHASH(); 

                }
                break;
            case 14 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:200: ESCAPE_HASH
                {
                mESCAPE_HASH(); 

                }
                break;
            case 15 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:212: HASH_CFML
                {
                mHASH_CFML(); 

                }
                break;
            case 16 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:222: CFML
                {
                mCFML(); 

                }
                break;
            case 17 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:227: WS
                {
                mWS(); 

                }
                break;
            case 18 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:230: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 19 :
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:1:238: OTHER
                {
                mOTHER(); 

                }
                break;

        }

    }


 

}