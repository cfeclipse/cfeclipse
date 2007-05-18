// $ANTLR 3.0 ./CFScript.g 2007-05-18 18:55:06

package org.cfeclipse.cfml.core.parser;

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


import org.antlr.runtime.tree.*;

public class CFScriptParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "FUNCTION_CALL", "STRUCT_KEY", "SEMI_COLON", "VAR", "EQUALS", "RETURN", "OPEN_PAREN", "CLOSE_PAREN", "OPERATOR", "NOT", "NUMBER", "STRING", "HASH", "DOT", "IDENTIFIER", "OPEN_SQUARE", "CLOSE_SQUARE", "COMMA", "IF", "ELSE", "TRY", "CATCH", "MATH_OPERATOR", "STRING_OPERATOR", "BOOLEAN_OPERATOR", "OPEN_CURLY", "CLOSE_CURLY", "DIGIT", "LETTER", "WS", "COMMENT", "LINE_COMMENT"
    };
    public static final int LINE_COMMENT=35;
    public static final int LETTER=32;
    public static final int CLOSE_CURLY=30;
    public static final int OPERATOR=12;
    public static final int ELSE=23;
    public static final int NUMBER=14;
    public static final int HASH=16;
    public static final int OPEN_PAREN=10;
    public static final int SEMI_COLON=6;
    public static final int EQUALS=8;
    public static final int OPEN_CURLY=29;
    public static final int NOT=13;
    public static final int EOF=-1;
    public static final int TRY=24;
    public static final int IF=22;
    public static final int WS=33;
    public static final int COMMA=21;
    public static final int BOOLEAN_OPERATOR=28;
    public static final int IDENTIFIER=18;
    public static final int RETURN=9;
    public static final int OPEN_SQUARE=19;
    public static final int VAR=7;
    public static final int MATH_OPERATOR=26;
    public static final int CLOSE_PAREN=11;
    public static final int DIGIT=31;
    public static final int COMMENT=34;
    public static final int CATCH=25;
    public static final int DOT=17;
    public static final int CLOSE_SQUARE=20;
    public static final int FUNCTION_CALL=4;
    public static final int STRING_OPERATOR=27;
    public static final int STRUCT_KEY=5;
    public static final int STRING=15;

        public CFScriptParser(TokenStream input) {
            super(input);
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "./CFScript.g"; }


    	ErrorObservable observable = new ErrorObservable();
    	
    	public void addObserver(IErrorObserver observer)
    	{
    		observable.addObserver(observer);
    	}
    	
    	public void removeObserver(IErrorObserver observer)
    	{
    		observable.removeObserver(observer);
    	}
    	
    	public void displayRecognitionError(String[] tokenNames, RecognitionException e)
    	{
    		ErrorEvent event = new ErrorEvent(e, getErrorMessage(e, tokenNames));
    		
    		observable.notifyObservers(event);
    		
    		super.displayRecognitionError(tokenNames, e);
    	}


    public static class script_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start script
    // ./CFScript.g:140:1: script : ( ( setStatement | returnStatement ) SEMI_COLON )* ;
    public final script_return script() throws RecognitionException {
    traceIn("script", 1);
        script_return retval = new script_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SEMI_COLON3=null;
        setStatement_return setStatement1 = null;

        returnStatement_return returnStatement2 = null;


        Object SEMI_COLON3_tree=null;

        try {
            // ./CFScript.g:142:2: ( ( ( setStatement | returnStatement ) SEMI_COLON )* )
            // ./CFScript.g:142:2: ( ( setStatement | returnStatement ) SEMI_COLON )*
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:142:2: ( ( setStatement | returnStatement ) SEMI_COLON )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==VAR||LA2_0==RETURN||(LA2_0>=NUMBER && LA2_0<=HASH)||LA2_0==IDENTIFIER) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ./CFScript.g:143:3: ( setStatement | returnStatement ) SEMI_COLON
            	    {
            	    // ./CFScript.g:143:3: ( setStatement | returnStatement )
            	    int alt1=2;
            	    int LA1_0 = input.LA(1);

            	    if ( (LA1_0==VAR||(LA1_0>=NUMBER && LA1_0<=HASH)||LA1_0==IDENTIFIER) ) {
            	        alt1=1;
            	    }
            	    else if ( (LA1_0==RETURN) ) {
            	        alt1=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("143:3: ( setStatement | returnStatement )", 1, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt1) {
            	        case 1 :
            	            // ./CFScript.g:144:3: setStatement
            	            {
            	            pushFollow(FOLLOW_setStatement_in_script89);
            	            setStatement1=setStatement();
            	            _fsp--;

            	            adaptor.addChild(root_0, setStatement1.getTree());

            	            }
            	            break;
            	        case 2 :
            	            // ./CFScript.g:146:3: returnStatement
            	            {
            	            pushFollow(FOLLOW_returnStatement_in_script98);
            	            returnStatement2=returnStatement();
            	            _fsp--;

            	            adaptor.addChild(root_0, returnStatement2.getTree());

            	            }
            	            break;

            	    }

            	    SEMI_COLON3=(Token)input.LT(1);
            	    match(input,SEMI_COLON,FOLLOW_SEMI_COLON_in_script104); 
            	    SEMI_COLON3_tree = (Object)adaptor.create(SEMI_COLON3);
            	    adaptor.addChild(root_0, SEMI_COLON3_tree);


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
    traceOut("script", 1);
        }
        return retval;
    }
    // $ANTLR end script

    public static class setStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start setStatement
    // ./CFScript.g:151:1: setStatement : ( VAR )? cfmlValue ( EQUALS codeStatement )? ;
    public final setStatement_return setStatement() throws RecognitionException {
    traceIn("setStatement", 2);
        setStatement_return retval = new setStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token VAR4=null;
        Token EQUALS6=null;
        cfmlValue_return cfmlValue5 = null;

        codeStatement_return codeStatement7 = null;


        Object VAR4_tree=null;
        Object EQUALS6_tree=null;

        try {
            // ./CFScript.g:153:2: ( ( VAR )? cfmlValue ( EQUALS codeStatement )? )
            // ./CFScript.g:153:2: ( VAR )? cfmlValue ( EQUALS codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:153:2: ( VAR )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==VAR) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ./CFScript.g:153:3: VAR
                    {
                    VAR4=(Token)input.LT(1);
                    match(input,VAR,FOLLOW_VAR_in_setStatement122); 
                    VAR4_tree = (Object)adaptor.create(VAR4);
                    adaptor.addChild(root_0, VAR4_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_cfmlValue_in_setStatement126);
            cfmlValue5=cfmlValue();
            _fsp--;

            adaptor.addChild(root_0, cfmlValue5.getTree());
            // ./CFScript.g:153:19: ( EQUALS codeStatement )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==EQUALS) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ./CFScript.g:153:20: EQUALS codeStatement
                    {
                    EQUALS6=(Token)input.LT(1);
                    match(input,EQUALS,FOLLOW_EQUALS_in_setStatement129); 
                    EQUALS6_tree = (Object)adaptor.create(EQUALS6);
                    adaptor.addChild(root_0, EQUALS6_tree);

                    pushFollow(FOLLOW_codeStatement_in_setStatement131);
                    codeStatement7=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement7.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
    traceOut("setStatement", 2);
        }
        return retval;
    }
    // $ANTLR end setStatement

    public static class returnStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start returnStatement
    // ./CFScript.g:156:1: returnStatement : RETURN ( codeStatement )? ;
    public final returnStatement_return returnStatement() throws RecognitionException {
    traceIn("returnStatement", 3);
        returnStatement_return retval = new returnStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token RETURN8=null;
        codeStatement_return codeStatement9 = null;


        Object RETURN8_tree=null;

        try {
            // ./CFScript.g:158:2: ( RETURN ( codeStatement )? )
            // ./CFScript.g:158:2: RETURN ( codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            RETURN8=(Token)input.LT(1);
            match(input,RETURN,FOLLOW_RETURN_in_returnStatement146); 
            RETURN8_tree = (Object)adaptor.create(RETURN8);
            root_0 = (Object)adaptor.becomeRoot(RETURN8_tree, root_0);

            // ./CFScript.g:158:10: ( codeStatement )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==OPEN_PAREN||(LA5_0>=NOT && LA5_0<=HASH)||LA5_0==IDENTIFIER) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ./CFScript.g:158:11: codeStatement
                    {
                    pushFollow(FOLLOW_codeStatement_in_returnStatement150);
                    codeStatement9=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement9.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
    traceOut("returnStatement", 3);
        }
        return retval;
    }
    // $ANTLR end returnStatement

    public static class codeStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start codeStatement
    // ./CFScript.g:161:1: codeStatement : ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement );
    public final codeStatement_return codeStatement() throws RecognitionException {
    traceIn("codeStatement", 4);
        codeStatement_return retval = new codeStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPEN_PAREN10=null;
        Token CLOSE_PAREN12=null;
        codeStatement_return codeStatement11 = null;

        cfmlBasicStatement_return cfmlBasicStatement13 = null;


        Object OPEN_PAREN10_tree=null;
        Object CLOSE_PAREN12_tree=null;

        try {
            // ./CFScript.g:163:2: ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==OPEN_PAREN) ) {
                alt6=1;
            }
            else if ( ((LA6_0>=NOT && LA6_0<=HASH)||LA6_0==IDENTIFIER) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("161:1: codeStatement : ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement );", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // ./CFScript.g:163:2: OPEN_PAREN codeStatement CLOSE_PAREN
                    {
                    root_0 = (Object)adaptor.nil();

                    OPEN_PAREN10=(Token)input.LT(1);
                    match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_codeStatement164); 
                    OPEN_PAREN10_tree = (Object)adaptor.create(OPEN_PAREN10);
                    adaptor.addChild(root_0, OPEN_PAREN10_tree);

                    pushFollow(FOLLOW_codeStatement_in_codeStatement166);
                    codeStatement11=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement11.getTree());
                    CLOSE_PAREN12=(Token)input.LT(1);
                    match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_codeStatement168); 
                    CLOSE_PAREN12_tree = (Object)adaptor.create(CLOSE_PAREN12);
                    adaptor.addChild(root_0, CLOSE_PAREN12_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:165:2: cfmlBasicStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_cfmlBasicStatement_in_codeStatement174);
                    cfmlBasicStatement13=cfmlBasicStatement();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlBasicStatement13.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
    traceOut("codeStatement", 4);
        }
        return retval;
    }
    // $ANTLR end codeStatement

    public static class cfmlBasicStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfmlBasicStatement
    // ./CFScript.g:168:1: cfmlBasicStatement : cfmlValueStatement ( OPERATOR codeStatement )? ;
    public final cfmlBasicStatement_return cfmlBasicStatement() throws RecognitionException {
    traceIn("cfmlBasicStatement", 5);
        cfmlBasicStatement_return retval = new cfmlBasicStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPERATOR15=null;
        cfmlValueStatement_return cfmlValueStatement14 = null;

        codeStatement_return codeStatement16 = null;


        Object OPERATOR15_tree=null;

        try {
            // ./CFScript.g:170:2: ( cfmlValueStatement ( OPERATOR codeStatement )? )
            // ./CFScript.g:170:2: cfmlValueStatement ( OPERATOR codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_cfmlValueStatement_in_cfmlBasicStatement186);
            cfmlValueStatement14=cfmlValueStatement();
            _fsp--;

            adaptor.addChild(root_0, cfmlValueStatement14.getTree());
            // ./CFScript.g:170:21: ( OPERATOR codeStatement )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==OPERATOR) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ./CFScript.g:170:22: OPERATOR codeStatement
                    {
                    OPERATOR15=(Token)input.LT(1);
                    match(input,OPERATOR,FOLLOW_OPERATOR_in_cfmlBasicStatement189); 
                    OPERATOR15_tree = (Object)adaptor.create(OPERATOR15);
                    adaptor.addChild(root_0, OPERATOR15_tree);

                    pushFollow(FOLLOW_codeStatement_in_cfmlBasicStatement191);
                    codeStatement16=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement16.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
    traceOut("cfmlBasicStatement", 5);
        }
        return retval;
    }
    // $ANTLR end cfmlBasicStatement

    public static class cfmlValueStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfmlValueStatement
    // ./CFScript.g:174:1: cfmlValueStatement : ( NOT )? cfmlValue ;
    public final cfmlValueStatement_return cfmlValueStatement() throws RecognitionException {
    traceIn("cfmlValueStatement", 6);
        cfmlValueStatement_return retval = new cfmlValueStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT17=null;
        cfmlValue_return cfmlValue18 = null;


        Object NOT17_tree=null;

        try {
            // ./CFScript.g:176:2: ( ( NOT )? cfmlValue )
            // ./CFScript.g:176:2: ( NOT )? cfmlValue
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:176:2: ( NOT )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==NOT) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ./CFScript.g:176:3: NOT
                    {
                    NOT17=(Token)input.LT(1);
                    match(input,NOT,FOLLOW_NOT_in_cfmlValueStatement207); 
                    NOT17_tree = (Object)adaptor.create(NOT17);
                    adaptor.addChild(root_0, NOT17_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_cfmlValue_in_cfmlValueStatement211);
            cfmlValue18=cfmlValue();
            _fsp--;

            adaptor.addChild(root_0, cfmlValue18.getTree());

            }

            retval.stop = input.LT(-1);

                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
    traceOut("cfmlValueStatement", 6);
        }
        return retval;
    }
    // $ANTLR end cfmlValueStatement

    public static class cfmlValue_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfmlValue
    // ./CFScript.g:179:1: cfmlValue : ( NUMBER | STRING | cfmlLinking ) ;
    public final cfmlValue_return cfmlValue() throws RecognitionException {
    traceIn("cfmlValue", 7);
        cfmlValue_return retval = new cfmlValue_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUMBER19=null;
        Token STRING20=null;
        cfmlLinking_return cfmlLinking21 = null;


        Object NUMBER19_tree=null;
        Object STRING20_tree=null;

        try {
            // ./CFScript.g:181:2: ( ( NUMBER | STRING | cfmlLinking ) )
            // ./CFScript.g:181:2: ( NUMBER | STRING | cfmlLinking )
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:181:2: ( NUMBER | STRING | cfmlLinking )
            int alt9=3;
            switch ( input.LA(1) ) {
            case NUMBER:
                {
                alt9=1;
                }
                break;
            case STRING:
                {
                alt9=2;
                }
                break;
            case HASH:
            case IDENTIFIER:
                {
                alt9=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("181:2: ( NUMBER | STRING | cfmlLinking )", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // ./CFScript.g:181:3: NUMBER
                    {
                    NUMBER19=(Token)input.LT(1);
                    match(input,NUMBER,FOLLOW_NUMBER_in_cfmlValue224); 
                    NUMBER19_tree = (Object)adaptor.create(NUMBER19);
                    adaptor.addChild(root_0, NUMBER19_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:181:12: STRING
                    {
                    STRING20=(Token)input.LT(1);
                    match(input,STRING,FOLLOW_STRING_in_cfmlValue228); 
                    STRING20_tree = (Object)adaptor.create(STRING20);
                    adaptor.addChild(root_0, STRING20_tree);


                    }
                    break;
                case 3 :
                    // ./CFScript.g:181:21: cfmlLinking
                    {
                    pushFollow(FOLLOW_cfmlLinking_in_cfmlValue232);
                    cfmlLinking21=cfmlLinking();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlLinking21.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
    traceOut("cfmlValue", 7);
        }
        return retval;
    }
    // $ANTLR end cfmlValue

    public static class cfmlLinking_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfmlLinking
    // ./CFScript.g:184:1: cfmlLinking : ( HASH cfmlBasicLinking HASH | cfmlBasicLinking );
    public final cfmlLinking_return cfmlLinking() throws RecognitionException {
    traceIn("cfmlLinking", 8);
        cfmlLinking_return retval = new cfmlLinking_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token HASH22=null;
        Token HASH24=null;
        cfmlBasicLinking_return cfmlBasicLinking23 = null;

        cfmlBasicLinking_return cfmlBasicLinking25 = null;


        Object HASH22_tree=null;
        Object HASH24_tree=null;

        try {
            // ./CFScript.g:186:2: ( HASH cfmlBasicLinking HASH | cfmlBasicLinking )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==HASH) ) {
                alt10=1;
            }
            else if ( (LA10_0==IDENTIFIER) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("184:1: cfmlLinking : ( HASH cfmlBasicLinking HASH | cfmlBasicLinking );", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // ./CFScript.g:186:2: HASH cfmlBasicLinking HASH
                    {
                    root_0 = (Object)adaptor.nil();

                    HASH22=(Token)input.LT(1);
                    match(input,HASH,FOLLOW_HASH_in_cfmlLinking245); 
                    HASH22_tree = (Object)adaptor.create(HASH22);
                    adaptor.addChild(root_0, HASH22_tree);

                    pushFollow(FOLLOW_cfmlBasicLinking_in_cfmlLinking247);
                    cfmlBasicLinking23=cfmlBasicLinking();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlBasicLinking23.getTree());
                    HASH24=(Token)input.LT(1);
                    match(input,HASH,FOLLOW_HASH_in_cfmlLinking249); 
                    HASH24_tree = (Object)adaptor.create(HASH24);
                    adaptor.addChild(root_0, HASH24_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:188:2: cfmlBasicLinking
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_cfmlBasicLinking_in_cfmlLinking255);
                    cfmlBasicLinking25=cfmlBasicLinking();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlBasicLinking25.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
    traceOut("cfmlLinking", 8);
        }
        return retval;
    }
    // $ANTLR end cfmlLinking

    public static class cfmlBasicLinking_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfmlBasicLinking
    // ./CFScript.g:191:1: cfmlBasicLinking : cfmlBasic ( DOT cfmlBasic )* ;
    public final cfmlBasicLinking_return cfmlBasicLinking() throws RecognitionException {
    traceIn("cfmlBasicLinking", 9);
        cfmlBasicLinking_return retval = new cfmlBasicLinking_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOT27=null;
        cfmlBasic_return cfmlBasic26 = null;

        cfmlBasic_return cfmlBasic28 = null;


        Object DOT27_tree=null;

        try {
            // ./CFScript.g:193:2: ( cfmlBasic ( DOT cfmlBasic )* )
            // ./CFScript.g:193:2: cfmlBasic ( DOT cfmlBasic )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_cfmlBasic_in_cfmlBasicLinking267);
            cfmlBasic26=cfmlBasic();
            _fsp--;

            adaptor.addChild(root_0, cfmlBasic26.getTree());
            // ./CFScript.g:193:12: ( DOT cfmlBasic )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==DOT) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ./CFScript.g:193:13: DOT cfmlBasic
            	    {
            	    DOT27=(Token)input.LT(1);
            	    match(input,DOT,FOLLOW_DOT_in_cfmlBasicLinking270); 
            	    DOT27_tree = (Object)adaptor.create(DOT27);
            	    adaptor.addChild(root_0, DOT27_tree);

            	    pushFollow(FOLLOW_cfmlBasic_in_cfmlBasicLinking272);
            	    cfmlBasic28=cfmlBasic();
            	    _fsp--;

            	    adaptor.addChild(root_0, cfmlBasic28.getTree());

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
    traceOut("cfmlBasicLinking", 9);
        }
        return retval;
    }
    // $ANTLR end cfmlBasicLinking

    public static class cfmlBasic_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfmlBasic
    // ./CFScript.g:196:1: cfmlBasic : ( identifier | function );
    public final cfmlBasic_return cfmlBasic() throws RecognitionException {
    traceIn("cfmlBasic", 10);
        cfmlBasic_return retval = new cfmlBasic_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        identifier_return identifier29 = null;

        function_return function30 = null;



        try {
            // ./CFScript.g:198:2: ( identifier | function )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==IDENTIFIER) ) {
                int LA12_1 = input.LA(2);

                if ( (LA12_1==OPEN_PAREN) ) {
                    alt12=2;
                }
                else if ( (LA12_1==SEMI_COLON||LA12_1==EQUALS||(LA12_1>=CLOSE_PAREN && LA12_1<=OPERATOR)||(LA12_1>=HASH && LA12_1<=DOT)||(LA12_1>=OPEN_SQUARE && LA12_1<=COMMA)) ) {
                    alt12=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("196:1: cfmlBasic : ( identifier | function );", 12, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("196:1: cfmlBasic : ( identifier | function );", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // ./CFScript.g:198:2: identifier
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_identifier_in_cfmlBasic286);
                    identifier29=identifier();
                    _fsp--;

                    adaptor.addChild(root_0, identifier29.getTree());

                    }
                    break;
                case 2 :
                    // ./CFScript.g:198:15: function
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_in_cfmlBasic290);
                    function30=function();
                    _fsp--;

                    adaptor.addChild(root_0, function30.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
    traceOut("cfmlBasic", 10);
        }
        return retval;
    }
    // $ANTLR end cfmlBasic

    public static class identifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start identifier
    // ./CFScript.g:201:1: identifier : IDENTIFIER ( struct )? ;
    public final identifier_return identifier() throws RecognitionException {
    traceIn("identifier", 11);
        identifier_return retval = new identifier_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENTIFIER31=null;
        struct_return struct32 = null;


        Object IDENTIFIER31_tree=null;

        try {
            // ./CFScript.g:203:2: ( IDENTIFIER ( struct )? )
            // ./CFScript.g:203:2: IDENTIFIER ( struct )?
            {
            root_0 = (Object)adaptor.nil();

            IDENTIFIER31=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_identifier302); 
            IDENTIFIER31_tree = (Object)adaptor.create(IDENTIFIER31);
            adaptor.addChild(root_0, IDENTIFIER31_tree);

            // ./CFScript.g:203:13: ( struct )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==OPEN_SQUARE) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ./CFScript.g:203:14: struct
                    {
                    pushFollow(FOLLOW_struct_in_identifier305);
                    struct32=struct();
                    _fsp--;

                    adaptor.addChild(root_0, struct32.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
    traceOut("identifier", 11);
        }
        return retval;
    }
    // $ANTLR end identifier

    public static class struct_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start struct
    // ./CFScript.g:206:1: struct : OPEN_SQUARE codeStatement CLOSE_SQUARE -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE ) ;
    public final struct_return struct() throws RecognitionException {
    traceIn("struct", 12);
        struct_return retval = new struct_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPEN_SQUARE33=null;
        Token CLOSE_SQUARE35=null;
        codeStatement_return codeStatement34 = null;


        Object OPEN_SQUARE33_tree=null;
        Object CLOSE_SQUARE35_tree=null;
        RewriteRuleTokenStream stream_OPEN_SQUARE=new RewriteRuleTokenStream(adaptor,"token OPEN_SQUARE");
        RewriteRuleTokenStream stream_CLOSE_SQUARE=new RewriteRuleTokenStream(adaptor,"token CLOSE_SQUARE");
        RewriteRuleSubtreeStream stream_codeStatement=new RewriteRuleSubtreeStream(adaptor,"rule codeStatement");
        try {
            // ./CFScript.g:208:2: ( OPEN_SQUARE codeStatement CLOSE_SQUARE -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE ) )
            // ./CFScript.g:208:2: OPEN_SQUARE codeStatement CLOSE_SQUARE
            {
            OPEN_SQUARE33=(Token)input.LT(1);
            match(input,OPEN_SQUARE,FOLLOW_OPEN_SQUARE_in_struct320); 
            stream_OPEN_SQUARE.add(OPEN_SQUARE33);

            pushFollow(FOLLOW_codeStatement_in_struct322);
            codeStatement34=codeStatement();
            _fsp--;

            stream_codeStatement.add(codeStatement34.getTree());
            CLOSE_SQUARE35=(Token)input.LT(1);
            match(input,CLOSE_SQUARE,FOLLOW_CLOSE_SQUARE_in_struct324); 
            stream_CLOSE_SQUARE.add(CLOSE_SQUARE35);


            // AST REWRITE
            // elements: OPEN_SQUARE, codeStatement, CLOSE_SQUARE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 209:2: -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE )
            {
                // ./CFScript.g:209:5: ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(STRUCT_KEY, "STRUCT_KEY"), root_1);

                adaptor.addChild(root_1, stream_OPEN_SQUARE.next());
                adaptor.addChild(root_1, stream_codeStatement.next());
                adaptor.addChild(root_1, stream_CLOSE_SQUARE.next());

                adaptor.addChild(root_0, root_1);
                }

            }



            }

            retval.stop = input.LT(-1);

                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
    traceOut("struct", 12);
        }
        return retval;
    }
    // $ANTLR end struct

    public static class function_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start function
    // ./CFScript.g:212:1: function : IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN ) ;
    public final function_return function() throws RecognitionException {
    traceIn("function", 13);
        function_return retval = new function_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENTIFIER36=null;
        Token OPEN_PAREN37=null;
        Token CLOSE_PAREN39=null;
        argumentStatement_return argumentStatement38 = null;


        Object IDENTIFIER36_tree=null;
        Object OPEN_PAREN37_tree=null;
        Object CLOSE_PAREN39_tree=null;
        RewriteRuleTokenStream stream_OPEN_PAREN=new RewriteRuleTokenStream(adaptor,"token OPEN_PAREN");
        RewriteRuleTokenStream stream_CLOSE_PAREN=new RewriteRuleTokenStream(adaptor,"token CLOSE_PAREN");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleSubtreeStream stream_argumentStatement=new RewriteRuleSubtreeStream(adaptor,"rule argumentStatement");
        try {
            // ./CFScript.g:214:2: ( IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN ) )
            // ./CFScript.g:214:2: IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN
            {
            IDENTIFIER36=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_function350); 
            stream_IDENTIFIER.add(IDENTIFIER36);

            OPEN_PAREN37=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_function352); 
            stream_OPEN_PAREN.add(OPEN_PAREN37);

            // ./CFScript.g:214:24: ( argumentStatement )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==OPEN_PAREN||(LA14_0>=NOT && LA14_0<=HASH)||LA14_0==IDENTIFIER) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // ./CFScript.g:214:25: argumentStatement
                    {
                    pushFollow(FOLLOW_argumentStatement_in_function355);
                    argumentStatement38=argumentStatement();
                    _fsp--;

                    stream_argumentStatement.add(argumentStatement38.getTree());

                    }
                    break;

            }

            CLOSE_PAREN39=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_function359); 
            stream_CLOSE_PAREN.add(CLOSE_PAREN39);


            // AST REWRITE
            // elements: IDENTIFIER, argumentStatement, CLOSE_PAREN, OPEN_PAREN
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 215:2: -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN )
            {
                // ./CFScript.g:215:5: ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(FUNCTION_CALL, "FUNCTION_CALL"), root_1);

                adaptor.addChild(root_1, stream_IDENTIFIER.next());
                adaptor.addChild(root_1, stream_OPEN_PAREN.next());
                // ./CFScript.g:215:43: ( argumentStatement )?
                if ( stream_argumentStatement.hasNext() ) {
                    adaptor.addChild(root_1, stream_argumentStatement.next());

                }
                stream_argumentStatement.reset();
                adaptor.addChild(root_1, stream_CLOSE_PAREN.next());

                adaptor.addChild(root_0, root_1);
                }

            }



            }

            retval.stop = input.LT(-1);

                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
    traceOut("function", 13);
        }
        return retval;
    }
    // $ANTLR end function

    public static class argumentStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start argumentStatement
    // ./CFScript.g:218:1: argumentStatement : codeStatement ( COMMA codeStatement )* ;
    public final argumentStatement_return argumentStatement() throws RecognitionException {
    traceIn("argumentStatement", 14);
        argumentStatement_return retval = new argumentStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA41=null;
        codeStatement_return codeStatement40 = null;

        codeStatement_return codeStatement42 = null;


        Object COMMA41_tree=null;

        try {
            // ./CFScript.g:220:2: ( codeStatement ( COMMA codeStatement )* )
            // ./CFScript.g:220:2: codeStatement ( COMMA codeStatement )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_codeStatement_in_argumentStatement390);
            codeStatement40=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement40.getTree());
            // ./CFScript.g:220:16: ( COMMA codeStatement )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==COMMA) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // ./CFScript.g:220:17: COMMA codeStatement
            	    {
            	    COMMA41=(Token)input.LT(1);
            	    match(input,COMMA,FOLLOW_COMMA_in_argumentStatement393); 
            	    COMMA41_tree = (Object)adaptor.create(COMMA41);
            	    adaptor.addChild(root_0, COMMA41_tree);

            	    pushFollow(FOLLOW_codeStatement_in_argumentStatement395);
            	    codeStatement42=codeStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, codeStatement42.getTree());

            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
    traceOut("argumentStatement", 14);
        }
        return retval;
    }
    // $ANTLR end argumentStatement


 

    public static final BitSet FOLLOW_setStatement_in_script89 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_returnStatement_in_script98 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SEMI_COLON_in_script104 = new BitSet(new long[]{0x000000000005C282L});
    public static final BitSet FOLLOW_VAR_in_setStatement122 = new BitSet(new long[]{0x000000000005C000L});
    public static final BitSet FOLLOW_cfmlValue_in_setStatement126 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_EQUALS_in_setStatement129 = new BitSet(new long[]{0x000000000005E400L});
    public static final BitSet FOLLOW_codeStatement_in_setStatement131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RETURN_in_returnStatement146 = new BitSet(new long[]{0x000000000005E402L});
    public static final BitSet FOLLOW_codeStatement_in_returnStatement150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_codeStatement164 = new BitSet(new long[]{0x000000000005E400L});
    public static final BitSet FOLLOW_codeStatement_in_codeStatement166 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_codeStatement168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasicStatement_in_codeStatement174 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlValueStatement_in_cfmlBasicStatement186 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_OPERATOR_in_cfmlBasicStatement189 = new BitSet(new long[]{0x000000000005E400L});
    public static final BitSet FOLLOW_codeStatement_in_cfmlBasicStatement191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cfmlValueStatement207 = new BitSet(new long[]{0x000000000005C000L});
    public static final BitSet FOLLOW_cfmlValue_in_cfmlValueStatement211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_cfmlValue224 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_cfmlValue228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlLinking_in_cfmlValue232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_cfmlLinking245 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_cfmlBasicLinking_in_cfmlLinking247 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_HASH_in_cfmlLinking249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasicLinking_in_cfmlLinking255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasic_in_cfmlBasicLinking267 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_DOT_in_cfmlBasicLinking270 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_cfmlBasic_in_cfmlBasicLinking272 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_identifier_in_cfmlBasic286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_cfmlBasic290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_identifier302 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_struct_in_identifier305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_SQUARE_in_struct320 = new BitSet(new long[]{0x000000000005E400L});
    public static final BitSet FOLLOW_codeStatement_in_struct322 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_CLOSE_SQUARE_in_struct324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_function350 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_function352 = new BitSet(new long[]{0x000000000005EC00L});
    public static final BitSet FOLLOW_argumentStatement_in_function355 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_function359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_codeStatement_in_argumentStatement390 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_COMMA_in_argumentStatement393 = new BitSet(new long[]{0x000000000005E400L});
    public static final BitSet FOLLOW_codeStatement_in_argumentStatement395 = new BitSet(new long[]{0x0000000000200002L});

}