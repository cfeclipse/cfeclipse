// $ANTLR 3.0 ./CFScript.g 2007-05-21 17:48:46

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


import org.antlr.runtime.tree.*;

public class CFScriptParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "FUNCTION_CALL", "STRUCT_KEY", "ELSEIF", "SEMI_COLON", "VAR", "EQUALS", "OPEN_PAREN", "CLOSE_PAREN", "RETURN", "OPERATOR", "NOT", "NUMBER", "STRING", "HASH", "DOT", "IDENTIFIER", "OPEN_SQUARE", "CLOSE_SQUARE", "COMMA", "IF", "ELSE", "TRY", "CATCH", "FOR", "WHILE", "DO", "OPEN_CURLY", "CLOSE_CURLY", "MATH_OPERATOR", "STRING_OPERATOR", "BOOLEAN_OPERATOR", "DIGIT", "LETTER", "WS", "COMMENT", "LINE_COMMENT"
    };
    public static final int WHILE=28;
    public static final int LINE_COMMENT=39;
    public static final int LETTER=36;
    public static final int CLOSE_CURLY=31;
    public static final int OPERATOR=13;
    public static final int ELSE=24;
    public static final int NUMBER=15;
    public static final int HASH=17;
    public static final int DO=29;
    public static final int FOR=27;
    public static final int OPEN_PAREN=10;
    public static final int SEMI_COLON=7;
    public static final int EQUALS=9;
    public static final int OPEN_CURLY=30;
    public static final int NOT=14;
    public static final int EOF=-1;
    public static final int TRY=25;
    public static final int IF=23;
    public static final int ELSEIF=6;
    public static final int WS=37;
    public static final int COMMA=22;
    public static final int BOOLEAN_OPERATOR=34;
    public static final int IDENTIFIER=19;
    public static final int RETURN=12;
    public static final int OPEN_SQUARE=20;
    public static final int VAR=8;
    public static final int MATH_OPERATOR=32;
    public static final int CLOSE_PAREN=11;
    public static final int DIGIT=35;
    public static final int COMMENT=38;
    public static final int CATCH=26;
    public static final int DOT=18;
    public static final int CLOSE_SQUARE=21;
    public static final int FUNCTION_CALL=4;
    public static final int STRING_OPERATOR=33;
    public static final int STRUCT_KEY=5;
    public static final int STRING=16;

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


    public static class script_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start script
    // ./CFScript.g:94:1: script : ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement | tryStatement | forStatement | whileStatement | doWhileStatement )* ;
    public final script_return script() throws RecognitionException {
    traceIn("script", 1);
        script_return retval = new script_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SEMI_COLON2=null;
        Token SEMI_COLON4=null;
        setStatement_return setStatement1 = null;

        returnStatement_return returnStatement3 = null;

        ifStatement_return ifStatement5 = null;

        tryStatement_return tryStatement6 = null;

        forStatement_return forStatement7 = null;

        whileStatement_return whileStatement8 = null;

        doWhileStatement_return doWhileStatement9 = null;


        Object SEMI_COLON2_tree=null;
        Object SEMI_COLON4_tree=null;

        try {
            // ./CFScript.g:96:2: ( ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement | tryStatement | forStatement | whileStatement | doWhileStatement )* )
            // ./CFScript.g:96:2: ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement | tryStatement | forStatement | whileStatement | doWhileStatement )*
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:96:2: ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement | tryStatement | forStatement | whileStatement | doWhileStatement )*
            loop1:
            do {
                int alt1=8;
                switch ( input.LA(1) ) {
                case VAR:
                case OPEN_PAREN:
                case NOT:
                case NUMBER:
                case STRING:
                case HASH:
                case IDENTIFIER:
                    {
                    alt1=1;
                    }
                    break;
                case RETURN:
                    {
                    alt1=2;
                    }
                    break;
                case IF:
                    {
                    alt1=3;
                    }
                    break;
                case TRY:
                    {
                    alt1=4;
                    }
                    break;
                case FOR:
                    {
                    alt1=5;
                    }
                    break;
                case WHILE:
                    {
                    alt1=6;
                    }
                    break;
                case DO:
                    {
                    alt1=7;
                    }
                    break;

                }

                switch (alt1) {
            	case 1 :
            	    // ./CFScript.g:97:3: setStatement SEMI_COLON
            	    {
            	    pushFollow(FOLLOW_setStatement_in_script72);
            	    setStatement1=setStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, setStatement1.getTree());
            	    SEMI_COLON2=(Token)input.LT(1);
            	    match(input,SEMI_COLON,FOLLOW_SEMI_COLON_in_script74); 
            	    SEMI_COLON2_tree = (Object)adaptor.create(SEMI_COLON2);
            	    adaptor.addChild(root_0, SEMI_COLON2_tree);


            	    }
            	    break;
            	case 2 :
            	    // ./CFScript.g:99:3: returnStatement SEMI_COLON
            	    {
            	    pushFollow(FOLLOW_returnStatement_in_script82);
            	    returnStatement3=returnStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, returnStatement3.getTree());
            	    SEMI_COLON4=(Token)input.LT(1);
            	    match(input,SEMI_COLON,FOLLOW_SEMI_COLON_in_script84); 
            	    SEMI_COLON4_tree = (Object)adaptor.create(SEMI_COLON4);
            	    adaptor.addChild(root_0, SEMI_COLON4_tree);


            	    }
            	    break;
            	case 3 :
            	    // ./CFScript.g:101:3: ifStatement
            	    {
            	    pushFollow(FOLLOW_ifStatement_in_script92);
            	    ifStatement5=ifStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, ifStatement5.getTree());

            	    }
            	    break;
            	case 4 :
            	    // ./CFScript.g:103:3: tryStatement
            	    {
            	    pushFollow(FOLLOW_tryStatement_in_script100);
            	    tryStatement6=tryStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, tryStatement6.getTree());

            	    }
            	    break;
            	case 5 :
            	    // ./CFScript.g:105:3: forStatement
            	    {
            	    pushFollow(FOLLOW_forStatement_in_script108);
            	    forStatement7=forStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, forStatement7.getTree());

            	    }
            	    break;
            	case 6 :
            	    // ./CFScript.g:107:3: whileStatement
            	    {
            	    pushFollow(FOLLOW_whileStatement_in_script116);
            	    whileStatement8=whileStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, whileStatement8.getTree());

            	    }
            	    break;
            	case 7 :
            	    // ./CFScript.g:109:3: doWhileStatement
            	    {
            	    pushFollow(FOLLOW_doWhileStatement_in_script124);
            	    doWhileStatement9=doWhileStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, doWhileStatement9.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
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
    // ./CFScript.g:113:1: setStatement : ( VAR )? codeStatement ( EQUALS codeStatement )? ;
    public final setStatement_return setStatement() throws RecognitionException {
    traceIn("setStatement", 2);
        setStatement_return retval = new setStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token VAR10=null;
        Token EQUALS12=null;
        codeStatement_return codeStatement11 = null;

        codeStatement_return codeStatement13 = null;


        Object VAR10_tree=null;
        Object EQUALS12_tree=null;

        try {
            // ./CFScript.g:123:2: ( ( VAR )? codeStatement ( EQUALS codeStatement )? )
            // ./CFScript.g:123:2: ( VAR )? codeStatement ( EQUALS codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:123:2: ( VAR )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==VAR) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // ./CFScript.g:123:3: VAR
                    {
                    VAR10=(Token)input.LT(1);
                    match(input,VAR,FOLLOW_VAR_in_setStatement145); 
                    VAR10_tree = (Object)adaptor.create(VAR10);
                    adaptor.addChild(root_0, VAR10_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_codeStatement_in_setStatement149);
            codeStatement11=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement11.getTree());
            // ./CFScript.g:123:23: ( EQUALS codeStatement )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==EQUALS) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ./CFScript.g:123:24: EQUALS codeStatement
                    {
                    EQUALS12=(Token)input.LT(1);
                    match(input,EQUALS,FOLLOW_EQUALS_in_setStatement152); 
                    EQUALS12_tree = (Object)adaptor.create(EQUALS12);
                    adaptor.addChild(root_0, EQUALS12_tree);

                    pushFollow(FOLLOW_codeStatement_in_setStatement154);
                    codeStatement13=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement13.getTree());

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

    public static class codeStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start codeStatement
    // ./CFScript.g:126:1: codeStatement : ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement ) ;
    public final codeStatement_return codeStatement() throws RecognitionException {
    traceIn("codeStatement", 3);
        codeStatement_return retval = new codeStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPEN_PAREN14=null;
        Token CLOSE_PAREN16=null;
        codeStatement_return codeStatement15 = null;

        cfmlBasicStatement_return cfmlBasicStatement17 = null;


        Object OPEN_PAREN14_tree=null;
        Object CLOSE_PAREN16_tree=null;

        try {
            // ./CFScript.g:128:2: ( ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement ) )
            // ./CFScript.g:128:2: ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement )
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:128:2: ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==OPEN_PAREN) ) {
                alt4=1;
            }
            else if ( ((LA4_0>=NOT && LA4_0<=HASH)||LA4_0==IDENTIFIER) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("128:2: ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement )", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // ./CFScript.g:129:3: OPEN_PAREN codeStatement CLOSE_PAREN
                    {
                    OPEN_PAREN14=(Token)input.LT(1);
                    match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_codeStatement172); 
                    OPEN_PAREN14_tree = (Object)adaptor.create(OPEN_PAREN14);
                    adaptor.addChild(root_0, OPEN_PAREN14_tree);

                    pushFollow(FOLLOW_codeStatement_in_codeStatement174);
                    codeStatement15=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement15.getTree());
                    CLOSE_PAREN16=(Token)input.LT(1);
                    match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_codeStatement176); 
                    CLOSE_PAREN16_tree = (Object)adaptor.create(CLOSE_PAREN16);
                    adaptor.addChild(root_0, CLOSE_PAREN16_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:131:3: cfmlBasicStatement
                    {
                    pushFollow(FOLLOW_cfmlBasicStatement_in_codeStatement184);
                    cfmlBasicStatement17=cfmlBasicStatement();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlBasicStatement17.getTree());

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
    traceOut("codeStatement", 3);
        }
        return retval;
    }
    // $ANTLR end codeStatement

    public static class returnStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start returnStatement
    // ./CFScript.g:135:1: returnStatement : RETURN ( codeStatement )? ;
    public final returnStatement_return returnStatement() throws RecognitionException {
    traceIn("returnStatement", 4);
        returnStatement_return retval = new returnStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token RETURN18=null;
        codeStatement_return codeStatement19 = null;


        Object RETURN18_tree=null;

        try {
            // ./CFScript.g:137:2: ( RETURN ( codeStatement )? )
            // ./CFScript.g:137:2: RETURN ( codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            RETURN18=(Token)input.LT(1);
            match(input,RETURN,FOLLOW_RETURN_in_returnStatement200); 
            RETURN18_tree = (Object)adaptor.create(RETURN18);
            root_0 = (Object)adaptor.becomeRoot(RETURN18_tree, root_0);

            // ./CFScript.g:137:10: ( codeStatement )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==OPEN_PAREN||(LA5_0>=NOT && LA5_0<=HASH)||LA5_0==IDENTIFIER) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ./CFScript.g:137:11: codeStatement
                    {
                    pushFollow(FOLLOW_codeStatement_in_returnStatement204);
                    codeStatement19=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement19.getTree());

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
    traceOut("returnStatement", 4);
        }
        return retval;
    }
    // $ANTLR end returnStatement

    public static class cfmlBasicStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfmlBasicStatement
    // ./CFScript.g:140:1: cfmlBasicStatement : cfmlValueStatement ( OPERATOR codeStatement )? ;
    public final cfmlBasicStatement_return cfmlBasicStatement() throws RecognitionException {
    traceIn("cfmlBasicStatement", 5);
        cfmlBasicStatement_return retval = new cfmlBasicStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPERATOR21=null;
        cfmlValueStatement_return cfmlValueStatement20 = null;

        codeStatement_return codeStatement22 = null;


        Object OPERATOR21_tree=null;

        try {
            // ./CFScript.g:142:2: ( cfmlValueStatement ( OPERATOR codeStatement )? )
            // ./CFScript.g:142:2: cfmlValueStatement ( OPERATOR codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_cfmlValueStatement_in_cfmlBasicStatement218);
            cfmlValueStatement20=cfmlValueStatement();
            _fsp--;

            adaptor.addChild(root_0, cfmlValueStatement20.getTree());
            // ./CFScript.g:142:21: ( OPERATOR codeStatement )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==OPERATOR) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ./CFScript.g:142:22: OPERATOR codeStatement
                    {
                    OPERATOR21=(Token)input.LT(1);
                    match(input,OPERATOR,FOLLOW_OPERATOR_in_cfmlBasicStatement221); 
                    OPERATOR21_tree = (Object)adaptor.create(OPERATOR21);
                    adaptor.addChild(root_0, OPERATOR21_tree);

                    pushFollow(FOLLOW_codeStatement_in_cfmlBasicStatement223);
                    codeStatement22=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement22.getTree());

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
    // ./CFScript.g:146:1: cfmlValueStatement : ( NOT )? cfmlValue ;
    public final cfmlValueStatement_return cfmlValueStatement() throws RecognitionException {
    traceIn("cfmlValueStatement", 6);
        cfmlValueStatement_return retval = new cfmlValueStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT23=null;
        cfmlValue_return cfmlValue24 = null;


        Object NOT23_tree=null;

        try {
            // ./CFScript.g:148:2: ( ( NOT )? cfmlValue )
            // ./CFScript.g:148:2: ( NOT )? cfmlValue
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:148:2: ( NOT )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==NOT) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ./CFScript.g:148:3: NOT
                    {
                    NOT23=(Token)input.LT(1);
                    match(input,NOT,FOLLOW_NOT_in_cfmlValueStatement239); 
                    NOT23_tree = (Object)adaptor.create(NOT23);
                    adaptor.addChild(root_0, NOT23_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_cfmlValue_in_cfmlValueStatement243);
            cfmlValue24=cfmlValue();
            _fsp--;

            adaptor.addChild(root_0, cfmlValue24.getTree());

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
    // ./CFScript.g:151:1: cfmlValue : ( NUMBER | STRING | cfmlLinking ) ;
    public final cfmlValue_return cfmlValue() throws RecognitionException {
    traceIn("cfmlValue", 7);
        cfmlValue_return retval = new cfmlValue_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUMBER25=null;
        Token STRING26=null;
        cfmlLinking_return cfmlLinking27 = null;


        Object NUMBER25_tree=null;
        Object STRING26_tree=null;

        try {
            // ./CFScript.g:153:2: ( ( NUMBER | STRING | cfmlLinking ) )
            // ./CFScript.g:153:2: ( NUMBER | STRING | cfmlLinking )
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:153:2: ( NUMBER | STRING | cfmlLinking )
            int alt8=3;
            switch ( input.LA(1) ) {
            case NUMBER:
                {
                alt8=1;
                }
                break;
            case STRING:
                {
                alt8=2;
                }
                break;
            case HASH:
            case IDENTIFIER:
                {
                alt8=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("153:2: ( NUMBER | STRING | cfmlLinking )", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // ./CFScript.g:153:3: NUMBER
                    {
                    NUMBER25=(Token)input.LT(1);
                    match(input,NUMBER,FOLLOW_NUMBER_in_cfmlValue256); 
                    NUMBER25_tree = (Object)adaptor.create(NUMBER25);
                    adaptor.addChild(root_0, NUMBER25_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:153:12: STRING
                    {
                    STRING26=(Token)input.LT(1);
                    match(input,STRING,FOLLOW_STRING_in_cfmlValue260); 
                    STRING26_tree = (Object)adaptor.create(STRING26);
                    adaptor.addChild(root_0, STRING26_tree);


                    }
                    break;
                case 3 :
                    // ./CFScript.g:153:21: cfmlLinking
                    {
                    pushFollow(FOLLOW_cfmlLinking_in_cfmlValue264);
                    cfmlLinking27=cfmlLinking();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlLinking27.getTree());

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
    // ./CFScript.g:156:1: cfmlLinking : ( HASH cfmlBasicLinking HASH | cfmlBasicLinking );
    public final cfmlLinking_return cfmlLinking() throws RecognitionException {
    traceIn("cfmlLinking", 8);
        cfmlLinking_return retval = new cfmlLinking_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token HASH28=null;
        Token HASH30=null;
        cfmlBasicLinking_return cfmlBasicLinking29 = null;

        cfmlBasicLinking_return cfmlBasicLinking31 = null;


        Object HASH28_tree=null;
        Object HASH30_tree=null;

        try {
            // ./CFScript.g:158:2: ( HASH cfmlBasicLinking HASH | cfmlBasicLinking )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==HASH) ) {
                alt9=1;
            }
            else if ( (LA9_0==IDENTIFIER) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("156:1: cfmlLinking : ( HASH cfmlBasicLinking HASH | cfmlBasicLinking );", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // ./CFScript.g:158:2: HASH cfmlBasicLinking HASH
                    {
                    root_0 = (Object)adaptor.nil();

                    HASH28=(Token)input.LT(1);
                    match(input,HASH,FOLLOW_HASH_in_cfmlLinking277); 
                    HASH28_tree = (Object)adaptor.create(HASH28);
                    adaptor.addChild(root_0, HASH28_tree);

                    pushFollow(FOLLOW_cfmlBasicLinking_in_cfmlLinking279);
                    cfmlBasicLinking29=cfmlBasicLinking();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlBasicLinking29.getTree());
                    HASH30=(Token)input.LT(1);
                    match(input,HASH,FOLLOW_HASH_in_cfmlLinking281); 
                    HASH30_tree = (Object)adaptor.create(HASH30);
                    adaptor.addChild(root_0, HASH30_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:160:2: cfmlBasicLinking
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_cfmlBasicLinking_in_cfmlLinking287);
                    cfmlBasicLinking31=cfmlBasicLinking();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlBasicLinking31.getTree());

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
    // ./CFScript.g:163:1: cfmlBasicLinking : cfmlBasic ( DOT cfmlBasic )* ;
    public final cfmlBasicLinking_return cfmlBasicLinking() throws RecognitionException {
    traceIn("cfmlBasicLinking", 9);
        cfmlBasicLinking_return retval = new cfmlBasicLinking_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOT33=null;
        cfmlBasic_return cfmlBasic32 = null;

        cfmlBasic_return cfmlBasic34 = null;


        Object DOT33_tree=null;

        try {
            // ./CFScript.g:165:2: ( cfmlBasic ( DOT cfmlBasic )* )
            // ./CFScript.g:165:2: cfmlBasic ( DOT cfmlBasic )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_cfmlBasic_in_cfmlBasicLinking299);
            cfmlBasic32=cfmlBasic();
            _fsp--;

            adaptor.addChild(root_0, cfmlBasic32.getTree());
            // ./CFScript.g:165:12: ( DOT cfmlBasic )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==DOT) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ./CFScript.g:165:13: DOT cfmlBasic
            	    {
            	    DOT33=(Token)input.LT(1);
            	    match(input,DOT,FOLLOW_DOT_in_cfmlBasicLinking302); 
            	    DOT33_tree = (Object)adaptor.create(DOT33);
            	    adaptor.addChild(root_0, DOT33_tree);

            	    pushFollow(FOLLOW_cfmlBasic_in_cfmlBasicLinking304);
            	    cfmlBasic34=cfmlBasic();
            	    _fsp--;

            	    adaptor.addChild(root_0, cfmlBasic34.getTree());

            	    }
            	    break;

            	default :
            	    break loop10;
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
    // ./CFScript.g:168:1: cfmlBasic : ( identifier | function );
    public final cfmlBasic_return cfmlBasic() throws RecognitionException {
    traceIn("cfmlBasic", 10);
        cfmlBasic_return retval = new cfmlBasic_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        identifier_return identifier35 = null;

        function_return function36 = null;



        try {
            // ./CFScript.g:170:2: ( identifier | function )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==IDENTIFIER) ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1==OPEN_PAREN) ) {
                    alt11=2;
                }
                else if ( (LA11_1==SEMI_COLON||LA11_1==EQUALS||LA11_1==CLOSE_PAREN||LA11_1==OPERATOR||(LA11_1>=HASH && LA11_1<=DOT)||(LA11_1>=OPEN_SQUARE && LA11_1<=COMMA)) ) {
                    alt11=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("168:1: cfmlBasic : ( identifier | function );", 11, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("168:1: cfmlBasic : ( identifier | function );", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // ./CFScript.g:170:2: identifier
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_identifier_in_cfmlBasic318);
                    identifier35=identifier();
                    _fsp--;

                    adaptor.addChild(root_0, identifier35.getTree());

                    }
                    break;
                case 2 :
                    // ./CFScript.g:170:15: function
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_in_cfmlBasic322);
                    function36=function();
                    _fsp--;

                    adaptor.addChild(root_0, function36.getTree());

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
    // ./CFScript.g:173:1: identifier : IDENTIFIER ( struct )? ;
    public final identifier_return identifier() throws RecognitionException {
    traceIn("identifier", 11);
        identifier_return retval = new identifier_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENTIFIER37=null;
        struct_return struct38 = null;


        Object IDENTIFIER37_tree=null;

        try {
            // ./CFScript.g:175:2: ( IDENTIFIER ( struct )? )
            // ./CFScript.g:175:2: IDENTIFIER ( struct )?
            {
            root_0 = (Object)adaptor.nil();

            IDENTIFIER37=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_identifier334); 
            IDENTIFIER37_tree = (Object)adaptor.create(IDENTIFIER37);
            adaptor.addChild(root_0, IDENTIFIER37_tree);

            // ./CFScript.g:175:13: ( struct )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==OPEN_SQUARE) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ./CFScript.g:175:14: struct
                    {
                    pushFollow(FOLLOW_struct_in_identifier337);
                    struct38=struct();
                    _fsp--;

                    adaptor.addChild(root_0, struct38.getTree());

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
    // ./CFScript.g:178:1: struct : OPEN_SQUARE codeStatement CLOSE_SQUARE -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE ) ;
    public final struct_return struct() throws RecognitionException {
    traceIn("struct", 12);
        struct_return retval = new struct_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPEN_SQUARE39=null;
        Token CLOSE_SQUARE41=null;
        codeStatement_return codeStatement40 = null;


        Object OPEN_SQUARE39_tree=null;
        Object CLOSE_SQUARE41_tree=null;
        RewriteRuleTokenStream stream_OPEN_SQUARE=new RewriteRuleTokenStream(adaptor,"token OPEN_SQUARE");
        RewriteRuleTokenStream stream_CLOSE_SQUARE=new RewriteRuleTokenStream(adaptor,"token CLOSE_SQUARE");
        RewriteRuleSubtreeStream stream_codeStatement=new RewriteRuleSubtreeStream(adaptor,"rule codeStatement");
        try {
            // ./CFScript.g:180:2: ( OPEN_SQUARE codeStatement CLOSE_SQUARE -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE ) )
            // ./CFScript.g:180:2: OPEN_SQUARE codeStatement CLOSE_SQUARE
            {
            OPEN_SQUARE39=(Token)input.LT(1);
            match(input,OPEN_SQUARE,FOLLOW_OPEN_SQUARE_in_struct352); 
            stream_OPEN_SQUARE.add(OPEN_SQUARE39);

            pushFollow(FOLLOW_codeStatement_in_struct354);
            codeStatement40=codeStatement();
            _fsp--;

            stream_codeStatement.add(codeStatement40.getTree());
            CLOSE_SQUARE41=(Token)input.LT(1);
            match(input,CLOSE_SQUARE,FOLLOW_CLOSE_SQUARE_in_struct356); 
            stream_CLOSE_SQUARE.add(CLOSE_SQUARE41);


            // AST REWRITE
            // elements: codeStatement, CLOSE_SQUARE, OPEN_SQUARE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 181:2: -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE )
            {
                // ./CFScript.g:181:5: ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE )
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
    // ./CFScript.g:184:1: function : IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN ) ;
    public final function_return function() throws RecognitionException {
    traceIn("function", 13);
        function_return retval = new function_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENTIFIER42=null;
        Token OPEN_PAREN43=null;
        Token CLOSE_PAREN45=null;
        argumentStatement_return argumentStatement44 = null;


        Object IDENTIFIER42_tree=null;
        Object OPEN_PAREN43_tree=null;
        Object CLOSE_PAREN45_tree=null;
        RewriteRuleTokenStream stream_OPEN_PAREN=new RewriteRuleTokenStream(adaptor,"token OPEN_PAREN");
        RewriteRuleTokenStream stream_CLOSE_PAREN=new RewriteRuleTokenStream(adaptor,"token CLOSE_PAREN");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleSubtreeStream stream_argumentStatement=new RewriteRuleSubtreeStream(adaptor,"rule argumentStatement");
        try {
            // ./CFScript.g:186:2: ( IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN ) )
            // ./CFScript.g:186:2: IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN
            {
            IDENTIFIER42=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_function382); 
            stream_IDENTIFIER.add(IDENTIFIER42);

            OPEN_PAREN43=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_function384); 
            stream_OPEN_PAREN.add(OPEN_PAREN43);

            // ./CFScript.g:186:24: ( argumentStatement )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==OPEN_PAREN||(LA13_0>=NOT && LA13_0<=HASH)||LA13_0==IDENTIFIER) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ./CFScript.g:186:25: argumentStatement
                    {
                    pushFollow(FOLLOW_argumentStatement_in_function387);
                    argumentStatement44=argumentStatement();
                    _fsp--;

                    stream_argumentStatement.add(argumentStatement44.getTree());

                    }
                    break;

            }

            CLOSE_PAREN45=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_function391); 
            stream_CLOSE_PAREN.add(CLOSE_PAREN45);


            // AST REWRITE
            // elements: CLOSE_PAREN, OPEN_PAREN, argumentStatement, IDENTIFIER
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 187:2: -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN )
            {
                // ./CFScript.g:187:5: ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(FUNCTION_CALL, "FUNCTION_CALL"), root_1);

                adaptor.addChild(root_1, stream_IDENTIFIER.next());
                adaptor.addChild(root_1, stream_OPEN_PAREN.next());
                // ./CFScript.g:187:43: ( argumentStatement )?
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
    // ./CFScript.g:190:1: argumentStatement : codeStatement ( COMMA codeStatement )* ;
    public final argumentStatement_return argumentStatement() throws RecognitionException {
    traceIn("argumentStatement", 14);
        argumentStatement_return retval = new argumentStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA47=null;
        codeStatement_return codeStatement46 = null;

        codeStatement_return codeStatement48 = null;


        Object COMMA47_tree=null;

        try {
            // ./CFScript.g:192:2: ( codeStatement ( COMMA codeStatement )* )
            // ./CFScript.g:192:2: codeStatement ( COMMA codeStatement )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_codeStatement_in_argumentStatement422);
            codeStatement46=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement46.getTree());
            // ./CFScript.g:192:16: ( COMMA codeStatement )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==COMMA) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ./CFScript.g:192:17: COMMA codeStatement
            	    {
            	    COMMA47=(Token)input.LT(1);
            	    match(input,COMMA,FOLLOW_COMMA_in_argumentStatement425); 
            	    COMMA47_tree = (Object)adaptor.create(COMMA47);
            	    adaptor.addChild(root_0, COMMA47_tree);

            	    pushFollow(FOLLOW_codeStatement_in_argumentStatement427);
            	    codeStatement48=codeStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, codeStatement48.getTree());

            	    }
            	    break;

            	default :
            	    break loop14;
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

    public static class ifStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start ifStatement
    // ./CFScript.g:195:1: ifStatement : IF OPEN_PAREN codeStatement CLOSE_PAREN block ( elseifStatement )* ( elseStatement )? ;
    public final ifStatement_return ifStatement() throws RecognitionException {
    traceIn("ifStatement", 15);
        ifStatement_return retval = new ifStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IF49=null;
        Token OPEN_PAREN50=null;
        Token CLOSE_PAREN52=null;
        codeStatement_return codeStatement51 = null;

        block_return block53 = null;

        elseifStatement_return elseifStatement54 = null;

        elseStatement_return elseStatement55 = null;


        Object IF49_tree=null;
        Object OPEN_PAREN50_tree=null;
        Object CLOSE_PAREN52_tree=null;

        try {
            // ./CFScript.g:197:2: ( IF OPEN_PAREN codeStatement CLOSE_PAREN block ( elseifStatement )* ( elseStatement )? )
            // ./CFScript.g:197:2: IF OPEN_PAREN codeStatement CLOSE_PAREN block ( elseifStatement )* ( elseStatement )?
            {
            root_0 = (Object)adaptor.nil();

            IF49=(Token)input.LT(1);
            match(input,IF,FOLLOW_IF_in_ifStatement441); 
            IF49_tree = (Object)adaptor.create(IF49);
            root_0 = (Object)adaptor.becomeRoot(IF49_tree, root_0);

            OPEN_PAREN50=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_ifStatement444); 
            OPEN_PAREN50_tree = (Object)adaptor.create(OPEN_PAREN50);
            adaptor.addChild(root_0, OPEN_PAREN50_tree);

            pushFollow(FOLLOW_codeStatement_in_ifStatement446);
            codeStatement51=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement51.getTree());
            CLOSE_PAREN52=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_ifStatement448); 
            CLOSE_PAREN52_tree = (Object)adaptor.create(CLOSE_PAREN52);
            adaptor.addChild(root_0, CLOSE_PAREN52_tree);

            pushFollow(FOLLOW_block_in_ifStatement451);
            block53=block();
            _fsp--;

            adaptor.addChild(root_0, block53.getTree());
            // ./CFScript.g:199:2: ( elseifStatement )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==ELSE) ) {
                    int LA15_1 = input.LA(2);

                    if ( (LA15_1==IF) ) {
                        alt15=1;
                    }


                }


                switch (alt15) {
            	case 1 :
            	    // ./CFScript.g:199:3: elseifStatement
            	    {
            	    pushFollow(FOLLOW_elseifStatement_in_ifStatement455);
            	    elseifStatement54=elseifStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, elseifStatement54.getTree());

            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

            // ./CFScript.g:200:2: ( elseStatement )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==ELSE) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // ./CFScript.g:200:3: elseStatement
                    {
                    pushFollow(FOLLOW_elseStatement_in_ifStatement461);
                    elseStatement55=elseStatement();
                    _fsp--;

                    adaptor.addChild(root_0, elseStatement55.getTree());

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
    traceOut("ifStatement", 15);
        }
        return retval;
    }
    // $ANTLR end ifStatement

    public static class elseifStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start elseifStatement
    // ./CFScript.g:203:1: elseifStatement : ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block -> ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block ) ;
    public final elseifStatement_return elseifStatement() throws RecognitionException {
    traceIn("elseifStatement", 16);
        elseifStatement_return retval = new elseifStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ELSE56=null;
        Token IF57=null;
        Token OPEN_PAREN58=null;
        Token CLOSE_PAREN60=null;
        codeStatement_return codeStatement59 = null;

        block_return block61 = null;


        Object ELSE56_tree=null;
        Object IF57_tree=null;
        Object OPEN_PAREN58_tree=null;
        Object CLOSE_PAREN60_tree=null;
        RewriteRuleTokenStream stream_OPEN_PAREN=new RewriteRuleTokenStream(adaptor,"token OPEN_PAREN");
        RewriteRuleTokenStream stream_CLOSE_PAREN=new RewriteRuleTokenStream(adaptor,"token CLOSE_PAREN");
        RewriteRuleTokenStream stream_ELSE=new RewriteRuleTokenStream(adaptor,"token ELSE");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_codeStatement=new RewriteRuleSubtreeStream(adaptor,"rule codeStatement");
        RewriteRuleSubtreeStream stream_block=new RewriteRuleSubtreeStream(adaptor,"rule block");
        try {
            // ./CFScript.g:205:2: ( ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block -> ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block ) )
            // ./CFScript.g:205:2: ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block
            {
            ELSE56=(Token)input.LT(1);
            match(input,ELSE,FOLLOW_ELSE_in_elseifStatement476); 
            stream_ELSE.add(ELSE56);

            IF57=(Token)input.LT(1);
            match(input,IF,FOLLOW_IF_in_elseifStatement478); 
            stream_IF.add(IF57);

            OPEN_PAREN58=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_elseifStatement480); 
            stream_OPEN_PAREN.add(OPEN_PAREN58);

            pushFollow(FOLLOW_codeStatement_in_elseifStatement482);
            codeStatement59=codeStatement();
            _fsp--;

            stream_codeStatement.add(codeStatement59.getTree());
            CLOSE_PAREN60=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_elseifStatement484); 
            stream_CLOSE_PAREN.add(CLOSE_PAREN60);

            pushFollow(FOLLOW_block_in_elseifStatement488);
            block61=block();
            _fsp--;

            stream_block.add(block61.getTree());

            // AST REWRITE
            // elements: CLOSE_PAREN, block, ELSE, IF, OPEN_PAREN, codeStatement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 207:2: -> ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block )
            {
                // ./CFScript.g:207:5: ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(ELSEIF, "ELSEIF"), root_1);

                adaptor.addChild(root_1, stream_ELSE.next());
                adaptor.addChild(root_1, stream_IF.next());
                adaptor.addChild(root_1, stream_OPEN_PAREN.next());
                adaptor.addChild(root_1, stream_codeStatement.next());
                adaptor.addChild(root_1, stream_CLOSE_PAREN.next());
                adaptor.addChild(root_1, stream_block.next());

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
    traceOut("elseifStatement", 16);
        }
        return retval;
    }
    // $ANTLR end elseifStatement

    public static class elseStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start elseStatement
    // ./CFScript.g:213:1: elseStatement : ELSE block ;
    public final elseStatement_return elseStatement() throws RecognitionException {
    traceIn("elseStatement", 17);
        elseStatement_return retval = new elseStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ELSE62=null;
        block_return block63 = null;


        Object ELSE62_tree=null;

        try {
            // ./CFScript.g:215:2: ( ELSE block )
            // ./CFScript.g:215:2: ELSE block
            {
            root_0 = (Object)adaptor.nil();

            ELSE62=(Token)input.LT(1);
            match(input,ELSE,FOLLOW_ELSE_in_elseStatement532); 
            ELSE62_tree = (Object)adaptor.create(ELSE62);
            root_0 = (Object)adaptor.becomeRoot(ELSE62_tree, root_0);

            pushFollow(FOLLOW_block_in_elseStatement536);
            block63=block();
            _fsp--;

            adaptor.addChild(root_0, block63.getTree());

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
    traceOut("elseStatement", 17);
        }
        return retval;
    }
    // $ANTLR end elseStatement

    public static class tryStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start tryStatement
    // ./CFScript.g:219:1: tryStatement : TRY block catchStatement ;
    public final tryStatement_return tryStatement() throws RecognitionException {
    traceIn("tryStatement", 18);
        tryStatement_return retval = new tryStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token TRY64=null;
        block_return block65 = null;

        catchStatement_return catchStatement66 = null;


        Object TRY64_tree=null;

        try {
            // ./CFScript.g:221:2: ( TRY block catchStatement )
            // ./CFScript.g:221:2: TRY block catchStatement
            {
            root_0 = (Object)adaptor.nil();

            TRY64=(Token)input.LT(1);
            match(input,TRY,FOLLOW_TRY_in_tryStatement548); 
            TRY64_tree = (Object)adaptor.create(TRY64);
            root_0 = (Object)adaptor.becomeRoot(TRY64_tree, root_0);

            pushFollow(FOLLOW_block_in_tryStatement552);
            block65=block();
            _fsp--;

            adaptor.addChild(root_0, block65.getTree());
            pushFollow(FOLLOW_catchStatement_in_tryStatement555);
            catchStatement66=catchStatement();
            _fsp--;

            adaptor.addChild(root_0, catchStatement66.getTree());

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
    traceOut("tryStatement", 18);
        }
        return retval;
    }
    // $ANTLR end tryStatement

    public static class catchStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start catchStatement
    // ./CFScript.g:226:1: catchStatement : CATCH OPEN_PAREN IDENTIFIER IDENTIFIER CLOSE_PAREN block ;
    public final catchStatement_return catchStatement() throws RecognitionException {
    traceIn("catchStatement", 19);
        catchStatement_return retval = new catchStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CATCH67=null;
        Token OPEN_PAREN68=null;
        Token IDENTIFIER69=null;
        Token IDENTIFIER70=null;
        Token CLOSE_PAREN71=null;
        block_return block72 = null;


        Object CATCH67_tree=null;
        Object OPEN_PAREN68_tree=null;
        Object IDENTIFIER69_tree=null;
        Object IDENTIFIER70_tree=null;
        Object CLOSE_PAREN71_tree=null;

        try {
            // ./CFScript.g:228:2: ( CATCH OPEN_PAREN IDENTIFIER IDENTIFIER CLOSE_PAREN block )
            // ./CFScript.g:228:2: CATCH OPEN_PAREN IDENTIFIER IDENTIFIER CLOSE_PAREN block
            {
            root_0 = (Object)adaptor.nil();

            CATCH67=(Token)input.LT(1);
            match(input,CATCH,FOLLOW_CATCH_in_catchStatement568); 
            CATCH67_tree = (Object)adaptor.create(CATCH67);
            root_0 = (Object)adaptor.becomeRoot(CATCH67_tree, root_0);

            OPEN_PAREN68=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_catchStatement571); 
            OPEN_PAREN68_tree = (Object)adaptor.create(OPEN_PAREN68);
            adaptor.addChild(root_0, OPEN_PAREN68_tree);

            IDENTIFIER69=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_catchStatement573); 
            IDENTIFIER69_tree = (Object)adaptor.create(IDENTIFIER69);
            adaptor.addChild(root_0, IDENTIFIER69_tree);

            IDENTIFIER70=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_catchStatement575); 
            IDENTIFIER70_tree = (Object)adaptor.create(IDENTIFIER70);
            adaptor.addChild(root_0, IDENTIFIER70_tree);

            CLOSE_PAREN71=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_catchStatement577); 
            CLOSE_PAREN71_tree = (Object)adaptor.create(CLOSE_PAREN71);
            adaptor.addChild(root_0, CLOSE_PAREN71_tree);

            pushFollow(FOLLOW_block_in_catchStatement580);
            block72=block();
            _fsp--;

            adaptor.addChild(root_0, block72.getTree());

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
    traceOut("catchStatement", 19);
        }
        return retval;
    }
    // $ANTLR end catchStatement

    public static class forStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start forStatement
    // ./CFScript.g:232:1: forStatement : FOR OPEN_PAREN forConditions CLOSE_PAREN block ;
    public final forStatement_return forStatement() throws RecognitionException {
    traceIn("forStatement", 20);
        forStatement_return retval = new forStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token FOR73=null;
        Token OPEN_PAREN74=null;
        Token CLOSE_PAREN76=null;
        forConditions_return forConditions75 = null;

        block_return block77 = null;


        Object FOR73_tree=null;
        Object OPEN_PAREN74_tree=null;
        Object CLOSE_PAREN76_tree=null;

        try {
            // ./CFScript.g:234:2: ( FOR OPEN_PAREN forConditions CLOSE_PAREN block )
            // ./CFScript.g:234:2: FOR OPEN_PAREN forConditions CLOSE_PAREN block
            {
            root_0 = (Object)adaptor.nil();

            FOR73=(Token)input.LT(1);
            match(input,FOR,FOLLOW_FOR_in_forStatement592); 
            FOR73_tree = (Object)adaptor.create(FOR73);
            root_0 = (Object)adaptor.becomeRoot(FOR73_tree, root_0);

            OPEN_PAREN74=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_forStatement595); 
            OPEN_PAREN74_tree = (Object)adaptor.create(OPEN_PAREN74);
            adaptor.addChild(root_0, OPEN_PAREN74_tree);

            pushFollow(FOLLOW_forConditions_in_forStatement597);
            forConditions75=forConditions();
            _fsp--;

            adaptor.addChild(root_0, forConditions75.getTree());
            CLOSE_PAREN76=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_forStatement599); 
            CLOSE_PAREN76_tree = (Object)adaptor.create(CLOSE_PAREN76);
            adaptor.addChild(root_0, CLOSE_PAREN76_tree);

            pushFollow(FOLLOW_block_in_forStatement602);
            block77=block();
            _fsp--;

            adaptor.addChild(root_0, block77.getTree());

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
    traceOut("forStatement", 20);
        }
        return retval;
    }
    // $ANTLR end forStatement

    public static class forConditions_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start forConditions
    // ./CFScript.g:237:1: forConditions : setStatement SEMI_COLON setStatement SEMI_COLON setStatement ;
    public final forConditions_return forConditions() throws RecognitionException {
    traceIn("forConditions", 21);
        forConditions_return retval = new forConditions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SEMI_COLON79=null;
        Token SEMI_COLON81=null;
        setStatement_return setStatement78 = null;

        setStatement_return setStatement80 = null;

        setStatement_return setStatement82 = null;


        Object SEMI_COLON79_tree=null;
        Object SEMI_COLON81_tree=null;

        try {
            // ./CFScript.g:239:2: ( setStatement SEMI_COLON setStatement SEMI_COLON setStatement )
            // ./CFScript.g:239:2: setStatement SEMI_COLON setStatement SEMI_COLON setStatement
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_setStatement_in_forConditions612);
            setStatement78=setStatement();
            _fsp--;

            adaptor.addChild(root_0, setStatement78.getTree());
            SEMI_COLON79=(Token)input.LT(1);
            match(input,SEMI_COLON,FOLLOW_SEMI_COLON_in_forConditions615); 
            SEMI_COLON79_tree = (Object)adaptor.create(SEMI_COLON79);
            adaptor.addChild(root_0, SEMI_COLON79_tree);

            pushFollow(FOLLOW_setStatement_in_forConditions618);
            setStatement80=setStatement();
            _fsp--;

            adaptor.addChild(root_0, setStatement80.getTree());
            SEMI_COLON81=(Token)input.LT(1);
            match(input,SEMI_COLON,FOLLOW_SEMI_COLON_in_forConditions621); 
            SEMI_COLON81_tree = (Object)adaptor.create(SEMI_COLON81);
            adaptor.addChild(root_0, SEMI_COLON81_tree);

            pushFollow(FOLLOW_setStatement_in_forConditions624);
            setStatement82=setStatement();
            _fsp--;

            adaptor.addChild(root_0, setStatement82.getTree());

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
    traceOut("forConditions", 21);
        }
        return retval;
    }
    // $ANTLR end forConditions

    public static class whileStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start whileStatement
    // ./CFScript.g:246:1: whileStatement : WHILE OPEN_PAREN codeStatement CLOSE_PAREN block ;
    public final whileStatement_return whileStatement() throws RecognitionException {
    traceIn("whileStatement", 22);
        whileStatement_return retval = new whileStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token WHILE83=null;
        Token OPEN_PAREN84=null;
        Token CLOSE_PAREN86=null;
        codeStatement_return codeStatement85 = null;

        block_return block87 = null;


        Object WHILE83_tree=null;
        Object OPEN_PAREN84_tree=null;
        Object CLOSE_PAREN86_tree=null;

        try {
            // ./CFScript.g:248:2: ( WHILE OPEN_PAREN codeStatement CLOSE_PAREN block )
            // ./CFScript.g:248:2: WHILE OPEN_PAREN codeStatement CLOSE_PAREN block
            {
            root_0 = (Object)adaptor.nil();

            WHILE83=(Token)input.LT(1);
            match(input,WHILE,FOLLOW_WHILE_in_whileStatement636); 
            WHILE83_tree = (Object)adaptor.create(WHILE83);
            root_0 = (Object)adaptor.becomeRoot(WHILE83_tree, root_0);

            OPEN_PAREN84=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_whileStatement639); 
            OPEN_PAREN84_tree = (Object)adaptor.create(OPEN_PAREN84);
            adaptor.addChild(root_0, OPEN_PAREN84_tree);

            pushFollow(FOLLOW_codeStatement_in_whileStatement641);
            codeStatement85=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement85.getTree());
            CLOSE_PAREN86=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_whileStatement643); 
            CLOSE_PAREN86_tree = (Object)adaptor.create(CLOSE_PAREN86);
            adaptor.addChild(root_0, CLOSE_PAREN86_tree);

            pushFollow(FOLLOW_block_in_whileStatement646);
            block87=block();
            _fsp--;

            adaptor.addChild(root_0, block87.getTree());

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
    traceOut("whileStatement", 22);
        }
        return retval;
    }
    // $ANTLR end whileStatement

    public static class doWhileStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start doWhileStatement
    // ./CFScript.g:252:1: doWhileStatement : DO block WHILE OPEN_PAREN codeStatement CLOSE_PAREN ;
    public final doWhileStatement_return doWhileStatement() throws RecognitionException {
    traceIn("doWhileStatement", 23);
        doWhileStatement_return retval = new doWhileStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DO88=null;
        Token WHILE90=null;
        Token OPEN_PAREN91=null;
        Token CLOSE_PAREN93=null;
        block_return block89 = null;

        codeStatement_return codeStatement92 = null;


        Object DO88_tree=null;
        Object WHILE90_tree=null;
        Object OPEN_PAREN91_tree=null;
        Object CLOSE_PAREN93_tree=null;

        try {
            // ./CFScript.g:254:2: ( DO block WHILE OPEN_PAREN codeStatement CLOSE_PAREN )
            // ./CFScript.g:254:2: DO block WHILE OPEN_PAREN codeStatement CLOSE_PAREN
            {
            root_0 = (Object)adaptor.nil();

            DO88=(Token)input.LT(1);
            match(input,DO,FOLLOW_DO_in_doWhileStatement658); 
            DO88_tree = (Object)adaptor.create(DO88);
            root_0 = (Object)adaptor.becomeRoot(DO88_tree, root_0);

            pushFollow(FOLLOW_block_in_doWhileStatement662);
            block89=block();
            _fsp--;

            adaptor.addChild(root_0, block89.getTree());
            WHILE90=(Token)input.LT(1);
            match(input,WHILE,FOLLOW_WHILE_in_doWhileStatement665); 
            WHILE90_tree = (Object)adaptor.create(WHILE90);
            adaptor.addChild(root_0, WHILE90_tree);

            OPEN_PAREN91=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_doWhileStatement667); 
            OPEN_PAREN91_tree = (Object)adaptor.create(OPEN_PAREN91);
            adaptor.addChild(root_0, OPEN_PAREN91_tree);

            pushFollow(FOLLOW_codeStatement_in_doWhileStatement669);
            codeStatement92=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement92.getTree());
            CLOSE_PAREN93=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_doWhileStatement671); 
            CLOSE_PAREN93_tree = (Object)adaptor.create(CLOSE_PAREN93);
            adaptor.addChild(root_0, CLOSE_PAREN93_tree);


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
    traceOut("doWhileStatement", 23);
        }
        return retval;
    }
    // $ANTLR end doWhileStatement

    public static class block_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start block
    // ./CFScript.g:259:1: block : OPEN_CURLY script CLOSE_CURLY ;
    public final block_return block() throws RecognitionException {
    traceIn("block", 24);
        block_return retval = new block_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPEN_CURLY94=null;
        Token CLOSE_CURLY96=null;
        script_return script95 = null;


        Object OPEN_CURLY94_tree=null;
        Object CLOSE_CURLY96_tree=null;

        try {
            // ./CFScript.g:261:2: ( OPEN_CURLY script CLOSE_CURLY )
            // ./CFScript.g:261:2: OPEN_CURLY script CLOSE_CURLY
            {
            root_0 = (Object)adaptor.nil();

            OPEN_CURLY94=(Token)input.LT(1);
            match(input,OPEN_CURLY,FOLLOW_OPEN_CURLY_in_block683); 
            OPEN_CURLY94_tree = (Object)adaptor.create(OPEN_CURLY94);
            adaptor.addChild(root_0, OPEN_CURLY94_tree);

            pushFollow(FOLLOW_script_in_block685);
            script95=script();
            _fsp--;

            adaptor.addChild(root_0, script95.getTree());
            CLOSE_CURLY96=(Token)input.LT(1);
            match(input,CLOSE_CURLY,FOLLOW_CLOSE_CURLY_in_block687); 
            CLOSE_CURLY96_tree = (Object)adaptor.create(CLOSE_CURLY96);
            adaptor.addChild(root_0, CLOSE_CURLY96_tree);


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
    traceOut("block", 24);
        }
        return retval;
    }
    // $ANTLR end block


 

    public static final BitSet FOLLOW_setStatement_in_script72 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_COLON_in_script74 = new BitSet(new long[]{0x000000003A8BD502L});
    public static final BitSet FOLLOW_returnStatement_in_script82 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_COLON_in_script84 = new BitSet(new long[]{0x000000003A8BD502L});
    public static final BitSet FOLLOW_ifStatement_in_script92 = new BitSet(new long[]{0x000000003A8BD502L});
    public static final BitSet FOLLOW_tryStatement_in_script100 = new BitSet(new long[]{0x000000003A8BD502L});
    public static final BitSet FOLLOW_forStatement_in_script108 = new BitSet(new long[]{0x000000003A8BD502L});
    public static final BitSet FOLLOW_whileStatement_in_script116 = new BitSet(new long[]{0x000000003A8BD502L});
    public static final BitSet FOLLOW_doWhileStatement_in_script124 = new BitSet(new long[]{0x000000003A8BD502L});
    public static final BitSet FOLLOW_VAR_in_setStatement145 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_setStatement149 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_EQUALS_in_setStatement152 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_setStatement154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_codeStatement172 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_codeStatement174 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_codeStatement176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasicStatement_in_codeStatement184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RETURN_in_returnStatement200 = new BitSet(new long[]{0x00000000000BC402L});
    public static final BitSet FOLLOW_codeStatement_in_returnStatement204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlValueStatement_in_cfmlBasicStatement218 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_OPERATOR_in_cfmlBasicStatement221 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_cfmlBasicStatement223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cfmlValueStatement239 = new BitSet(new long[]{0x00000000000B8000L});
    public static final BitSet FOLLOW_cfmlValue_in_cfmlValueStatement243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_cfmlValue256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_cfmlValue260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlLinking_in_cfmlValue264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_cfmlLinking277 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_cfmlBasicLinking_in_cfmlLinking279 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_HASH_in_cfmlLinking281 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasicLinking_in_cfmlLinking287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasic_in_cfmlBasicLinking299 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_DOT_in_cfmlBasicLinking302 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_cfmlBasic_in_cfmlBasicLinking304 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_identifier_in_cfmlBasic318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_cfmlBasic322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_identifier334 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_struct_in_identifier337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_SQUARE_in_struct352 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_struct354 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_CLOSE_SQUARE_in_struct356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_function382 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_function384 = new BitSet(new long[]{0x00000000000BCC00L});
    public static final BitSet FOLLOW_argumentStatement_in_function387 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_function391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_codeStatement_in_argumentStatement422 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_COMMA_in_argumentStatement425 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_argumentStatement427 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_IF_in_ifStatement441 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_ifStatement444 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_ifStatement446 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_ifStatement448 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_ifStatement451 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_elseifStatement_in_ifStatement455 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_elseStatement_in_ifStatement461 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_elseifStatement476 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_IF_in_elseifStatement478 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_elseifStatement480 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_elseifStatement482 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_elseifStatement484 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_elseifStatement488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_elseStatement532 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_elseStatement536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRY_in_tryStatement548 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_tryStatement552 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_catchStatement_in_tryStatement555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CATCH_in_catchStatement568 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_catchStatement571 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_catchStatement573 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_catchStatement575 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_catchStatement577 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_catchStatement580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_forStatement592 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_forStatement595 = new BitSet(new long[]{0x00000000000BC500L});
    public static final BitSet FOLLOW_forConditions_in_forStatement597 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_forStatement599 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_forStatement602 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_setStatement_in_forConditions612 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_COLON_in_forConditions615 = new BitSet(new long[]{0x00000000000BC500L});
    public static final BitSet FOLLOW_setStatement_in_forConditions618 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_COLON_in_forConditions621 = new BitSet(new long[]{0x00000000000BC500L});
    public static final BitSet FOLLOW_setStatement_in_forConditions624 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHILE_in_whileStatement636 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_whileStatement639 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_whileStatement641 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_whileStatement643 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_whileStatement646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DO_in_doWhileStatement658 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_doWhileStatement662 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_WHILE_in_doWhileStatement665 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_doWhileStatement667 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_doWhileStatement669 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_doWhileStatement671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_CURLY_in_block683 = new BitSet(new long[]{0x00000000BA8BD500L});
    public static final BitSet FOLLOW_script_in_block685 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_CLOSE_CURLY_in_block687 = new BitSet(new long[]{0x0000000000000002L});

}