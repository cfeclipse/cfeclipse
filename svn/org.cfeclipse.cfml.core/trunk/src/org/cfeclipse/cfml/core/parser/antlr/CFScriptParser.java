// $ANTLR 3.0 ./CFScript.g 2007-06-19 11:37:01

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "FUNCTION_CALL", "STRUCT_KEY", "ELSEIF", "STRING_CFML", "STRING", "SEMI_COLON", "VAR", "EQUALS", "OPEN_PAREN", "CLOSE_PAREN", "RETURN", "OPERATOR", "NOT", "NUMBER", "HASH", "DOT", "DOUBLE_QUOTE", "ESCAPE_DOUBLE_QUOTE", "SINGLE_QUOTE", "ESCAPE_SINGLE_QUOTE", "IDENTIFIER", "OPEN_SQUARE", "CLOSE_SQUARE", "COMMA", "IF", "ELSE", "TRY", "CATCH", "FOR", "WHILE", "DO", "OPEN_CURLY", "CLOSE_CURLY", "SWITCH", "CASE", "COLON", "DEFAULT", "BREAK", "MATH_OPERATOR", "STRING_OPERATOR", "BOOLEAN_OPERATOR", "DIGIT", "LETTER", "WS", "COMMENT", "LINE_COMMENT"
    };
    public static final int WHILE=33;
    public static final int LETTER=46;
    public static final int DOUBLE_QUOTE=20;
    public static final int CLOSE_CURLY=36;
    public static final int CASE=38;
    public static final int FOR=32;
    public static final int DO=34;
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
    // ./CFScript.g:101:1: script : ( ( nonBlockStatement | ifStatement | tryStatement | forStatement | whileStatement | doWhileStatement | switchStatement ) SEMI_COLON )* ;
    public final script_return script() throws RecognitionException {
    traceIn("script", 1);
        script_return retval = new script_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SEMI_COLON8=null;
        nonBlockStatement_return nonBlockStatement1 = null;

        ifStatement_return ifStatement2 = null;

        tryStatement_return tryStatement3 = null;

        forStatement_return forStatement4 = null;

        whileStatement_return whileStatement5 = null;

        doWhileStatement_return doWhileStatement6 = null;

        switchStatement_return switchStatement7 = null;


        Object SEMI_COLON8_tree=null;

        try {
            // ./CFScript.g:103:2: ( ( ( nonBlockStatement | ifStatement | tryStatement | forStatement | whileStatement | doWhileStatement | switchStatement ) SEMI_COLON )* )
            // ./CFScript.g:103:2: ( ( nonBlockStatement | ifStatement | tryStatement | forStatement | whileStatement | doWhileStatement | switchStatement ) SEMI_COLON )*
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:103:2: ( ( nonBlockStatement | ifStatement | tryStatement | forStatement | whileStatement | doWhileStatement | switchStatement ) SEMI_COLON )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==VAR||LA2_0==OPEN_PAREN||LA2_0==RETURN||(LA2_0>=NOT && LA2_0<=HASH)||LA2_0==DOUBLE_QUOTE||LA2_0==SINGLE_QUOTE||LA2_0==IDENTIFIER||LA2_0==IF||LA2_0==TRY||(LA2_0>=FOR && LA2_0<=DO)||LA2_0==SWITCH||LA2_0==BREAK) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ./CFScript.g:104:3: ( nonBlockStatement | ifStatement | tryStatement | forStatement | whileStatement | doWhileStatement | switchStatement ) SEMI_COLON
            	    {
            	    // ./CFScript.g:104:3: ( nonBlockStatement | ifStatement | tryStatement | forStatement | whileStatement | doWhileStatement | switchStatement )
            	    int alt1=7;
            	    switch ( input.LA(1) ) {
            	    case VAR:
            	    case OPEN_PAREN:
            	    case RETURN:
            	    case NOT:
            	    case NUMBER:
            	    case HASH:
            	    case DOUBLE_QUOTE:
            	    case SINGLE_QUOTE:
            	    case IDENTIFIER:
            	    case BREAK:
            	        {
            	        alt1=1;
            	        }
            	        break;
            	    case IF:
            	        {
            	        alt1=2;
            	        }
            	        break;
            	    case TRY:
            	        {
            	        alt1=3;
            	        }
            	        break;
            	    case FOR:
            	        {
            	        alt1=4;
            	        }
            	        break;
            	    case WHILE:
            	        {
            	        alt1=5;
            	        }
            	        break;
            	    case DO:
            	        {
            	        alt1=6;
            	        }
            	        break;
            	    case SWITCH:
            	        {
            	        alt1=7;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("104:3: ( nonBlockStatement | ifStatement | tryStatement | forStatement | whileStatement | doWhileStatement | switchStatement )", 1, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt1) {
            	        case 1 :
            	            // ./CFScript.g:105:4: nonBlockStatement
            	            {
            	            pushFollow(FOLLOW_nonBlockStatement_in_script92);
            	            nonBlockStatement1=nonBlockStatement();
            	            _fsp--;

            	            adaptor.addChild(root_0, nonBlockStatement1.getTree());

            	            }
            	            break;
            	        case 2 :
            	            // ./CFScript.g:107:4: ifStatement
            	            {
            	            pushFollow(FOLLOW_ifStatement_in_script103);
            	            ifStatement2=ifStatement();
            	            _fsp--;

            	            adaptor.addChild(root_0, ifStatement2.getTree());

            	            }
            	            break;
            	        case 3 :
            	            // ./CFScript.g:109:4: tryStatement
            	            {
            	            pushFollow(FOLLOW_tryStatement_in_script113);
            	            tryStatement3=tryStatement();
            	            _fsp--;

            	            adaptor.addChild(root_0, tryStatement3.getTree());

            	            }
            	            break;
            	        case 4 :
            	            // ./CFScript.g:111:4: forStatement
            	            {
            	            pushFollow(FOLLOW_forStatement_in_script123);
            	            forStatement4=forStatement();
            	            _fsp--;

            	            adaptor.addChild(root_0, forStatement4.getTree());

            	            }
            	            break;
            	        case 5 :
            	            // ./CFScript.g:113:4: whileStatement
            	            {
            	            pushFollow(FOLLOW_whileStatement_in_script133);
            	            whileStatement5=whileStatement();
            	            _fsp--;

            	            adaptor.addChild(root_0, whileStatement5.getTree());

            	            }
            	            break;
            	        case 6 :
            	            // ./CFScript.g:115:4: doWhileStatement
            	            {
            	            pushFollow(FOLLOW_doWhileStatement_in_script143);
            	            doWhileStatement6=doWhileStatement();
            	            _fsp--;

            	            adaptor.addChild(root_0, doWhileStatement6.getTree());

            	            }
            	            break;
            	        case 7 :
            	            // ./CFScript.g:117:4: switchStatement
            	            {
            	            pushFollow(FOLLOW_switchStatement_in_script153);
            	            switchStatement7=switchStatement();
            	            _fsp--;

            	            adaptor.addChild(root_0, switchStatement7.getTree());

            	            }
            	            break;

            	    }

            	    SEMI_COLON8=(Token)input.LT(1);
            	    match(input,SEMI_COLON,FOLLOW_SEMI_COLON_in_script159); 
            	    SEMI_COLON8_tree = (Object)adaptor.create(SEMI_COLON8);
            	    adaptor.addChild(root_0, SEMI_COLON8_tree);


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

    public static class nonBlockStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start nonBlockStatement
    // ./CFScript.g:122:1: nonBlockStatement : ( setStatement | returnStatement | breakStatement );
    public final nonBlockStatement_return nonBlockStatement() throws RecognitionException {
    traceIn("nonBlockStatement", 2);
        nonBlockStatement_return retval = new nonBlockStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        setStatement_return setStatement9 = null;

        returnStatement_return returnStatement10 = null;

        breakStatement_return breakStatement11 = null;



        try {
            // ./CFScript.g:124:2: ( setStatement | returnStatement | breakStatement )
            int alt3=3;
            switch ( input.LA(1) ) {
            case VAR:
            case OPEN_PAREN:
            case NOT:
            case NUMBER:
            case HASH:
            case DOUBLE_QUOTE:
            case SINGLE_QUOTE:
            case IDENTIFIER:
                {
                alt3=1;
                }
                break;
            case RETURN:
                {
                alt3=2;
                }
                break;
            case BREAK:
                {
                alt3=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("122:1: nonBlockStatement : ( setStatement | returnStatement | breakStatement );", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // ./CFScript.g:124:2: setStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_setStatement_in_nonBlockStatement176);
                    setStatement9=setStatement();
                    _fsp--;

                    adaptor.addChild(root_0, setStatement9.getTree());

                    }
                    break;
                case 2 :
                    // ./CFScript.g:126:2: returnStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_returnStatement_in_nonBlockStatement182);
                    returnStatement10=returnStatement();
                    _fsp--;

                    adaptor.addChild(root_0, returnStatement10.getTree());

                    }
                    break;
                case 3 :
                    // ./CFScript.g:128:2: breakStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_breakStatement_in_nonBlockStatement188);
                    breakStatement11=breakStatement();
                    _fsp--;

                    adaptor.addChild(root_0, breakStatement11.getTree());

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
    traceOut("nonBlockStatement", 2);
        }
        return retval;
    }
    // $ANTLR end nonBlockStatement

    public static class setStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start setStatement
    // ./CFScript.g:131:1: setStatement : ( VAR )? codeStatement ( EQUALS codeStatement )? ;
    public final setStatement_return setStatement() throws RecognitionException {
    traceIn("setStatement", 3);
        setStatement_return retval = new setStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token VAR12=null;
        Token EQUALS14=null;
        codeStatement_return codeStatement13 = null;

        codeStatement_return codeStatement15 = null;


        Object VAR12_tree=null;
        Object EQUALS14_tree=null;

        try {
            // ./CFScript.g:141:2: ( ( VAR )? codeStatement ( EQUALS codeStatement )? )
            // ./CFScript.g:141:2: ( VAR )? codeStatement ( EQUALS codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:141:2: ( VAR )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==VAR) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ./CFScript.g:141:3: VAR
                    {
                    VAR12=(Token)input.LT(1);
                    match(input,VAR,FOLLOW_VAR_in_setStatement206); 
                    VAR12_tree = (Object)adaptor.create(VAR12);
                    adaptor.addChild(root_0, VAR12_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_codeStatement_in_setStatement210);
            codeStatement13=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement13.getTree());
            // ./CFScript.g:141:23: ( EQUALS codeStatement )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==EQUALS) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ./CFScript.g:141:24: EQUALS codeStatement
                    {
                    EQUALS14=(Token)input.LT(1);
                    match(input,EQUALS,FOLLOW_EQUALS_in_setStatement213); 
                    EQUALS14_tree = (Object)adaptor.create(EQUALS14);
                    adaptor.addChild(root_0, EQUALS14_tree);

                    pushFollow(FOLLOW_codeStatement_in_setStatement215);
                    codeStatement15=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement15.getTree());

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
    traceOut("setStatement", 3);
        }
        return retval;
    }
    // $ANTLR end setStatement

    public static class codeStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start codeStatement
    // ./CFScript.g:144:1: codeStatement : ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement ) ;
    public final codeStatement_return codeStatement() throws RecognitionException {
    traceIn("codeStatement", 4);
        codeStatement_return retval = new codeStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPEN_PAREN16=null;
        Token CLOSE_PAREN18=null;
        codeStatement_return codeStatement17 = null;

        cfmlBasicStatement_return cfmlBasicStatement19 = null;


        Object OPEN_PAREN16_tree=null;
        Object CLOSE_PAREN18_tree=null;

        try {
            // ./CFScript.g:146:2: ( ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement ) )
            // ./CFScript.g:146:2: ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement )
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:146:2: ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==OPEN_PAREN) ) {
                alt6=1;
            }
            else if ( ((LA6_0>=NOT && LA6_0<=HASH)||LA6_0==DOUBLE_QUOTE||LA6_0==SINGLE_QUOTE||LA6_0==IDENTIFIER) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("146:2: ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement )", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // ./CFScript.g:147:3: OPEN_PAREN codeStatement CLOSE_PAREN
                    {
                    OPEN_PAREN16=(Token)input.LT(1);
                    match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_codeStatement233); 
                    OPEN_PAREN16_tree = (Object)adaptor.create(OPEN_PAREN16);
                    adaptor.addChild(root_0, OPEN_PAREN16_tree);

                    pushFollow(FOLLOW_codeStatement_in_codeStatement235);
                    codeStatement17=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement17.getTree());
                    CLOSE_PAREN18=(Token)input.LT(1);
                    match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_codeStatement237); 
                    CLOSE_PAREN18_tree = (Object)adaptor.create(CLOSE_PAREN18);
                    adaptor.addChild(root_0, CLOSE_PAREN18_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:149:3: cfmlBasicStatement
                    {
                    pushFollow(FOLLOW_cfmlBasicStatement_in_codeStatement245);
                    cfmlBasicStatement19=cfmlBasicStatement();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlBasicStatement19.getTree());

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
    traceOut("codeStatement", 4);
        }
        return retval;
    }
    // $ANTLR end codeStatement

    public static class returnStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start returnStatement
    // ./CFScript.g:153:1: returnStatement : RETURN ( codeStatement )? ;
    public final returnStatement_return returnStatement() throws RecognitionException {
    traceIn("returnStatement", 5);
        returnStatement_return retval = new returnStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token RETURN20=null;
        codeStatement_return codeStatement21 = null;


        Object RETURN20_tree=null;

        try {
            // ./CFScript.g:155:2: ( RETURN ( codeStatement )? )
            // ./CFScript.g:155:2: RETURN ( codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            RETURN20=(Token)input.LT(1);
            match(input,RETURN,FOLLOW_RETURN_in_returnStatement261); 
            RETURN20_tree = (Object)adaptor.create(RETURN20);
            root_0 = (Object)adaptor.becomeRoot(RETURN20_tree, root_0);

            // ./CFScript.g:155:10: ( codeStatement )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==OPEN_PAREN||(LA7_0>=NOT && LA7_0<=HASH)||LA7_0==DOUBLE_QUOTE||LA7_0==SINGLE_QUOTE||LA7_0==IDENTIFIER) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ./CFScript.g:155:11: codeStatement
                    {
                    pushFollow(FOLLOW_codeStatement_in_returnStatement265);
                    codeStatement21=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement21.getTree());

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
    traceOut("returnStatement", 5);
        }
        return retval;
    }
    // $ANTLR end returnStatement

    public static class cfmlBasicStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfmlBasicStatement
    // ./CFScript.g:158:1: cfmlBasicStatement : cfmlValueStatement ( OPERATOR codeStatement )? ;
    public final cfmlBasicStatement_return cfmlBasicStatement() throws RecognitionException {
    traceIn("cfmlBasicStatement", 6);
        cfmlBasicStatement_return retval = new cfmlBasicStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPERATOR23=null;
        cfmlValueStatement_return cfmlValueStatement22 = null;

        codeStatement_return codeStatement24 = null;


        Object OPERATOR23_tree=null;

        try {
            // ./CFScript.g:160:2: ( cfmlValueStatement ( OPERATOR codeStatement )? )
            // ./CFScript.g:160:2: cfmlValueStatement ( OPERATOR codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_cfmlValueStatement_in_cfmlBasicStatement279);
            cfmlValueStatement22=cfmlValueStatement();
            _fsp--;

            adaptor.addChild(root_0, cfmlValueStatement22.getTree());
            // ./CFScript.g:160:21: ( OPERATOR codeStatement )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==OPERATOR) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ./CFScript.g:160:22: OPERATOR codeStatement
                    {
                    OPERATOR23=(Token)input.LT(1);
                    match(input,OPERATOR,FOLLOW_OPERATOR_in_cfmlBasicStatement282); 
                    OPERATOR23_tree = (Object)adaptor.create(OPERATOR23);
                    adaptor.addChild(root_0, OPERATOR23_tree);

                    pushFollow(FOLLOW_codeStatement_in_cfmlBasicStatement284);
                    codeStatement24=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement24.getTree());

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
    traceOut("cfmlBasicStatement", 6);
        }
        return retval;
    }
    // $ANTLR end cfmlBasicStatement

    public static class cfmlValueStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfmlValueStatement
    // ./CFScript.g:164:1: cfmlValueStatement : ( NOT )? cfmlValue ;
    public final cfmlValueStatement_return cfmlValueStatement() throws RecognitionException {
    traceIn("cfmlValueStatement", 7);
        cfmlValueStatement_return retval = new cfmlValueStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT25=null;
        cfmlValue_return cfmlValue26 = null;


        Object NOT25_tree=null;

        try {
            // ./CFScript.g:166:2: ( ( NOT )? cfmlValue )
            // ./CFScript.g:166:2: ( NOT )? cfmlValue
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:166:2: ( NOT )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==NOT) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ./CFScript.g:166:3: NOT
                    {
                    NOT25=(Token)input.LT(1);
                    match(input,NOT,FOLLOW_NOT_in_cfmlValueStatement300); 
                    NOT25_tree = (Object)adaptor.create(NOT25);
                    adaptor.addChild(root_0, NOT25_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_cfmlValue_in_cfmlValueStatement304);
            cfmlValue26=cfmlValue();
            _fsp--;

            adaptor.addChild(root_0, cfmlValue26.getTree());

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
    traceOut("cfmlValueStatement", 7);
        }
        return retval;
    }
    // $ANTLR end cfmlValueStatement

    public static class cfmlValue_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfmlValue
    // ./CFScript.g:169:1: cfmlValue : ( NUMBER | stringLiteral | cfmlLinking ) ;
    public final cfmlValue_return cfmlValue() throws RecognitionException {
    traceIn("cfmlValue", 8);
        cfmlValue_return retval = new cfmlValue_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUMBER27=null;
        stringLiteral_return stringLiteral28 = null;

        cfmlLinking_return cfmlLinking29 = null;


        Object NUMBER27_tree=null;

        try {
            // ./CFScript.g:171:2: ( ( NUMBER | stringLiteral | cfmlLinking ) )
            // ./CFScript.g:171:2: ( NUMBER | stringLiteral | cfmlLinking )
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:171:2: ( NUMBER | stringLiteral | cfmlLinking )
            int alt10=3;
            switch ( input.LA(1) ) {
            case NUMBER:
                {
                alt10=1;
                }
                break;
            case DOUBLE_QUOTE:
            case SINGLE_QUOTE:
                {
                alt10=2;
                }
                break;
            case HASH:
            case IDENTIFIER:
                {
                alt10=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("171:2: ( NUMBER | stringLiteral | cfmlLinking )", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // ./CFScript.g:171:3: NUMBER
                    {
                    NUMBER27=(Token)input.LT(1);
                    match(input,NUMBER,FOLLOW_NUMBER_in_cfmlValue317); 
                    NUMBER27_tree = (Object)adaptor.create(NUMBER27);
                    adaptor.addChild(root_0, NUMBER27_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:171:12: stringLiteral
                    {
                    pushFollow(FOLLOW_stringLiteral_in_cfmlValue321);
                    stringLiteral28=stringLiteral();
                    _fsp--;

                    adaptor.addChild(root_0, stringLiteral28.getTree());

                    }
                    break;
                case 3 :
                    // ./CFScript.g:171:28: cfmlLinking
                    {
                    pushFollow(FOLLOW_cfmlLinking_in_cfmlValue325);
                    cfmlLinking29=cfmlLinking();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlLinking29.getTree());

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
    traceOut("cfmlValue", 8);
        }
        return retval;
    }
    // $ANTLR end cfmlValue

    public static class cfmlLinking_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfmlLinking
    // ./CFScript.g:174:1: cfmlLinking : ( hashCfmlLinking | cfmlBasicLinking );
    public final cfmlLinking_return cfmlLinking() throws RecognitionException {
    traceIn("cfmlLinking", 9);
        cfmlLinking_return retval = new cfmlLinking_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        hashCfmlLinking_return hashCfmlLinking30 = null;

        cfmlBasicLinking_return cfmlBasicLinking31 = null;



        try {
            // ./CFScript.g:176:2: ( hashCfmlLinking | cfmlBasicLinking )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==HASH) ) {
                alt11=1;
            }
            else if ( (LA11_0==IDENTIFIER) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("174:1: cfmlLinking : ( hashCfmlLinking | cfmlBasicLinking );", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // ./CFScript.g:176:2: hashCfmlLinking
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_hashCfmlLinking_in_cfmlLinking338);
                    hashCfmlLinking30=hashCfmlLinking();
                    _fsp--;

                    adaptor.addChild(root_0, hashCfmlLinking30.getTree());

                    }
                    break;
                case 2 :
                    // ./CFScript.g:178:2: cfmlBasicLinking
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_cfmlBasicLinking_in_cfmlLinking344);
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
    traceOut("cfmlLinking", 9);
        }
        return retval;
    }
    // $ANTLR end cfmlLinking

    public static class hashCfmlLinking_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start hashCfmlLinking
    // ./CFScript.g:181:1: hashCfmlLinking : HASH cfmlBasicLinking HASH ;
    public final hashCfmlLinking_return hashCfmlLinking() throws RecognitionException {
    traceIn("hashCfmlLinking", 10);
        hashCfmlLinking_return retval = new hashCfmlLinking_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token HASH32=null;
        Token HASH34=null;
        cfmlBasicLinking_return cfmlBasicLinking33 = null;


        Object HASH32_tree=null;
        Object HASH34_tree=null;

        try {
            // ./CFScript.g:183:2: ( HASH cfmlBasicLinking HASH )
            // ./CFScript.g:183:2: HASH cfmlBasicLinking HASH
            {
            root_0 = (Object)adaptor.nil();

            HASH32=(Token)input.LT(1);
            match(input,HASH,FOLLOW_HASH_in_hashCfmlLinking357); 
            HASH32_tree = (Object)adaptor.create(HASH32);
            adaptor.addChild(root_0, HASH32_tree);

            pushFollow(FOLLOW_cfmlBasicLinking_in_hashCfmlLinking359);
            cfmlBasicLinking33=cfmlBasicLinking();
            _fsp--;

            adaptor.addChild(root_0, cfmlBasicLinking33.getTree());
            HASH34=(Token)input.LT(1);
            match(input,HASH,FOLLOW_HASH_in_hashCfmlLinking361); 
            HASH34_tree = (Object)adaptor.create(HASH34);
            adaptor.addChild(root_0, HASH34_tree);


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
    traceOut("hashCfmlLinking", 10);
        }
        return retval;
    }
    // $ANTLR end hashCfmlLinking

    public static class cfmlBasicLinking_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfmlBasicLinking
    // ./CFScript.g:186:1: cfmlBasicLinking : cfmlBasic ( DOT cfmlBasic )* ;
    public final cfmlBasicLinking_return cfmlBasicLinking() throws RecognitionException {
    traceIn("cfmlBasicLinking", 11);
        cfmlBasicLinking_return retval = new cfmlBasicLinking_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOT36=null;
        cfmlBasic_return cfmlBasic35 = null;

        cfmlBasic_return cfmlBasic37 = null;


        Object DOT36_tree=null;

        try {
            // ./CFScript.g:188:2: ( cfmlBasic ( DOT cfmlBasic )* )
            // ./CFScript.g:188:2: cfmlBasic ( DOT cfmlBasic )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_cfmlBasic_in_cfmlBasicLinking373);
            cfmlBasic35=cfmlBasic();
            _fsp--;

            adaptor.addChild(root_0, cfmlBasic35.getTree());
            // ./CFScript.g:188:12: ( DOT cfmlBasic )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==DOT) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // ./CFScript.g:188:13: DOT cfmlBasic
            	    {
            	    DOT36=(Token)input.LT(1);
            	    match(input,DOT,FOLLOW_DOT_in_cfmlBasicLinking376); 
            	    DOT36_tree = (Object)adaptor.create(DOT36);
            	    adaptor.addChild(root_0, DOT36_tree);

            	    pushFollow(FOLLOW_cfmlBasic_in_cfmlBasicLinking378);
            	    cfmlBasic37=cfmlBasic();
            	    _fsp--;

            	    adaptor.addChild(root_0, cfmlBasic37.getTree());

            	    }
            	    break;

            	default :
            	    break loop12;
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
    traceOut("cfmlBasicLinking", 11);
        }
        return retval;
    }
    // $ANTLR end cfmlBasicLinking

    public static class cfmlBasic_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfmlBasic
    // ./CFScript.g:191:1: cfmlBasic : ( identifier | function );
    public final cfmlBasic_return cfmlBasic() throws RecognitionException {
    traceIn("cfmlBasic", 12);
        cfmlBasic_return retval = new cfmlBasic_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        identifier_return identifier38 = null;

        function_return function39 = null;



        try {
            // ./CFScript.g:193:2: ( identifier | function )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==IDENTIFIER) ) {
                int LA13_1 = input.LA(2);

                if ( (LA13_1==OPEN_PAREN) ) {
                    alt13=2;
                }
                else if ( (LA13_1==SEMI_COLON||LA13_1==EQUALS||LA13_1==CLOSE_PAREN||LA13_1==OPERATOR||(LA13_1>=HASH && LA13_1<=DOT)||(LA13_1>=OPEN_SQUARE && LA13_1<=COMMA)) ) {
                    alt13=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("191:1: cfmlBasic : ( identifier | function );", 13, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("191:1: cfmlBasic : ( identifier | function );", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // ./CFScript.g:193:2: identifier
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_identifier_in_cfmlBasic392);
                    identifier38=identifier();
                    _fsp--;

                    adaptor.addChild(root_0, identifier38.getTree());

                    }
                    break;
                case 2 :
                    // ./CFScript.g:193:15: function
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_in_cfmlBasic396);
                    function39=function();
                    _fsp--;

                    adaptor.addChild(root_0, function39.getTree());

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
    traceOut("cfmlBasic", 12);
        }
        return retval;
    }
    // $ANTLR end cfmlBasic

    public static class innerStringCFML_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start innerStringCFML
    // ./CFScript.g:196:1: innerStringCFML : hashCfmlLinking -> ^( STRING_CFML hashCfmlLinking ) ;
    public final innerStringCFML_return innerStringCFML() throws RecognitionException {
    traceIn("innerStringCFML", 13);
        innerStringCFML_return retval = new innerStringCFML_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        hashCfmlLinking_return hashCfmlLinking40 = null;


        RewriteRuleSubtreeStream stream_hashCfmlLinking=new RewriteRuleSubtreeStream(adaptor,"rule hashCfmlLinking");
        try {
            // ./CFScript.g:198:2: ( hashCfmlLinking -> ^( STRING_CFML hashCfmlLinking ) )
            // ./CFScript.g:198:2: hashCfmlLinking
            {
            pushFollow(FOLLOW_hashCfmlLinking_in_innerStringCFML408);
            hashCfmlLinking40=hashCfmlLinking();
            _fsp--;

            stream_hashCfmlLinking.add(hashCfmlLinking40.getTree());

            // AST REWRITE
            // elements: hashCfmlLinking
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 199:2: -> ^( STRING_CFML hashCfmlLinking )
            {
                // ./CFScript.g:199:5: ^( STRING_CFML hashCfmlLinking )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(STRING_CFML, "STRING_CFML"), root_1);

                adaptor.addChild(root_1, stream_hashCfmlLinking.next());

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
    traceOut("innerStringCFML", 13);
        }
        return retval;
    }
    // $ANTLR end innerStringCFML

    public static class stringLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start stringLiteral
    // ./CFScript.g:202:1: stringLiteral : ( ( DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | innerStringCFML | ~ ( DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | HASH ) )* DOUBLE_QUOTE ) | ( SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | innerStringCFML | ~ ( SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | HASH ) )* SINGLE_QUOTE ) );
    public final stringLiteral_return stringLiteral() throws RecognitionException {
    traceIn("stringLiteral", 14);
        stringLiteral_return retval = new stringLiteral_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOUBLE_QUOTE41=null;
        Token ESCAPE_DOUBLE_QUOTE42=null;
        Token set44=null;
        Token DOUBLE_QUOTE45=null;
        Token SINGLE_QUOTE46=null;
        Token ESCAPE_SINGLE_QUOTE47=null;
        Token set49=null;
        Token SINGLE_QUOTE50=null;
        innerStringCFML_return innerStringCFML43 = null;

        innerStringCFML_return innerStringCFML48 = null;


        Object DOUBLE_QUOTE41_tree=null;
        Object ESCAPE_DOUBLE_QUOTE42_tree=null;
        Object set44_tree=null;
        Object DOUBLE_QUOTE45_tree=null;
        Object SINGLE_QUOTE46_tree=null;
        Object ESCAPE_SINGLE_QUOTE47_tree=null;
        Object set49_tree=null;
        Object SINGLE_QUOTE50_tree=null;

        try {
            // ./CFScript.g:204:2: ( ( DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | innerStringCFML | ~ ( DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | HASH ) )* DOUBLE_QUOTE ) | ( SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | innerStringCFML | ~ ( SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | HASH ) )* SINGLE_QUOTE ) )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==DOUBLE_QUOTE) ) {
                alt16=1;
            }
            else if ( (LA16_0==SINGLE_QUOTE) ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("202:1: stringLiteral : ( ( DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | innerStringCFML | ~ ( DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | HASH ) )* DOUBLE_QUOTE ) | ( SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | innerStringCFML | ~ ( SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | HASH ) )* SINGLE_QUOTE ) );", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // ./CFScript.g:204:2: ( DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | innerStringCFML | ~ ( DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | HASH ) )* DOUBLE_QUOTE )
                    {
                    root_0 = (Object)adaptor.nil();

                    // ./CFScript.g:204:2: ( DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | innerStringCFML | ~ ( DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | HASH ) )* DOUBLE_QUOTE )
                    // ./CFScript.g:205:3: DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | innerStringCFML | ~ ( DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | HASH ) )* DOUBLE_QUOTE
                    {
                    DOUBLE_QUOTE41=(Token)input.LT(1);
                    match(input,DOUBLE_QUOTE,FOLLOW_DOUBLE_QUOTE_in_stringLiteral433); 
                    DOUBLE_QUOTE41_tree = (Object)adaptor.create(DOUBLE_QUOTE41);
                    root_0 = (Object)adaptor.becomeRoot(DOUBLE_QUOTE41_tree, root_0);

                    // ./CFScript.g:205:17: ( ESCAPE_DOUBLE_QUOTE | innerStringCFML | ~ ( DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | HASH ) )*
                    loop14:
                    do {
                        int alt14=4;
                        switch ( input.LA(1) ) {
                        case ESCAPE_DOUBLE_QUOTE:
                            {
                            alt14=1;
                            }
                            break;
                        case HASH:
                            {
                            alt14=2;
                            }
                            break;
                        case FUNCTION_CALL:
                        case STRUCT_KEY:
                        case ELSEIF:
                        case STRING_CFML:
                        case STRING:
                        case SEMI_COLON:
                        case VAR:
                        case EQUALS:
                        case OPEN_PAREN:
                        case CLOSE_PAREN:
                        case RETURN:
                        case OPERATOR:
                        case NOT:
                        case NUMBER:
                        case DOT:
                        case SINGLE_QUOTE:
                        case ESCAPE_SINGLE_QUOTE:
                        case IDENTIFIER:
                        case OPEN_SQUARE:
                        case CLOSE_SQUARE:
                        case COMMA:
                        case IF:
                        case ELSE:
                        case TRY:
                        case CATCH:
                        case FOR:
                        case WHILE:
                        case DO:
                        case OPEN_CURLY:
                        case CLOSE_CURLY:
                        case SWITCH:
                        case CASE:
                        case COLON:
                        case DEFAULT:
                        case BREAK:
                        case MATH_OPERATOR:
                        case STRING_OPERATOR:
                        case BOOLEAN_OPERATOR:
                        case DIGIT:
                        case LETTER:
                        case WS:
                        case COMMENT:
                        case LINE_COMMENT:
                            {
                            alt14=3;
                            }
                            break;

                        }

                        switch (alt14) {
                    	case 1 :
                    	    // ./CFScript.g:205:19: ESCAPE_DOUBLE_QUOTE
                    	    {
                    	    ESCAPE_DOUBLE_QUOTE42=(Token)input.LT(1);
                    	    match(input,ESCAPE_DOUBLE_QUOTE,FOLLOW_ESCAPE_DOUBLE_QUOTE_in_stringLiteral438); 
                    	    ESCAPE_DOUBLE_QUOTE42_tree = (Object)adaptor.create(ESCAPE_DOUBLE_QUOTE42);
                    	    adaptor.addChild(root_0, ESCAPE_DOUBLE_QUOTE42_tree);


                    	    }
                    	    break;
                    	case 2 :
                    	    // ./CFScript.g:205:41: innerStringCFML
                    	    {
                    	    pushFollow(FOLLOW_innerStringCFML_in_stringLiteral442);
                    	    innerStringCFML43=innerStringCFML();
                    	    _fsp--;

                    	    adaptor.addChild(root_0, innerStringCFML43.getTree());

                    	    }
                    	    break;
                    	case 3 :
                    	    // ./CFScript.g:205:59: ~ ( DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE | HASH )
                    	    {
                    	    set44=(Token)input.LT(1);
                    	    if ( (input.LA(1)>=FUNCTION_CALL && input.LA(1)<=NUMBER)||input.LA(1)==DOT||(input.LA(1)>=SINGLE_QUOTE && input.LA(1)<=LINE_COMMENT) ) {
                    	        input.consume();
                    	        adaptor.addChild(root_0, adaptor.create(set44));
                    	        errorRecovery=false;
                    	    }
                    	    else {
                    	        MismatchedSetException mse =
                    	            new MismatchedSetException(null,input);
                    	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_stringLiteral446);    throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop14;
                        }
                    } while (true);

                    DOUBLE_QUOTE45=(Token)input.LT(1);
                    match(input,DOUBLE_QUOTE,FOLLOW_DOUBLE_QUOTE_in_stringLiteral462); 
                    DOUBLE_QUOTE45_tree = (Object)adaptor.create(DOUBLE_QUOTE45);
                    adaptor.addChild(root_0, DOUBLE_QUOTE45_tree);


                    }


                    }
                    break;
                case 2 :
                    // ./CFScript.g:208:2: ( SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | innerStringCFML | ~ ( SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | HASH ) )* SINGLE_QUOTE )
                    {
                    root_0 = (Object)adaptor.nil();

                    // ./CFScript.g:208:2: ( SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | innerStringCFML | ~ ( SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | HASH ) )* SINGLE_QUOTE )
                    // ./CFScript.g:209:3: SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | innerStringCFML | ~ ( SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | HASH ) )* SINGLE_QUOTE
                    {
                    SINGLE_QUOTE46=(Token)input.LT(1);
                    match(input,SINGLE_QUOTE,FOLLOW_SINGLE_QUOTE_in_stringLiteral475); 
                    SINGLE_QUOTE46_tree = (Object)adaptor.create(SINGLE_QUOTE46);
                    root_0 = (Object)adaptor.becomeRoot(SINGLE_QUOTE46_tree, root_0);

                    // ./CFScript.g:209:17: ( ESCAPE_SINGLE_QUOTE | innerStringCFML | ~ ( SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | HASH ) )*
                    loop15:
                    do {
                        int alt15=4;
                        switch ( input.LA(1) ) {
                        case ESCAPE_SINGLE_QUOTE:
                            {
                            alt15=1;
                            }
                            break;
                        case HASH:
                            {
                            alt15=2;
                            }
                            break;
                        case FUNCTION_CALL:
                        case STRUCT_KEY:
                        case ELSEIF:
                        case STRING_CFML:
                        case STRING:
                        case SEMI_COLON:
                        case VAR:
                        case EQUALS:
                        case OPEN_PAREN:
                        case CLOSE_PAREN:
                        case RETURN:
                        case OPERATOR:
                        case NOT:
                        case NUMBER:
                        case DOT:
                        case DOUBLE_QUOTE:
                        case ESCAPE_DOUBLE_QUOTE:
                        case IDENTIFIER:
                        case OPEN_SQUARE:
                        case CLOSE_SQUARE:
                        case COMMA:
                        case IF:
                        case ELSE:
                        case TRY:
                        case CATCH:
                        case FOR:
                        case WHILE:
                        case DO:
                        case OPEN_CURLY:
                        case CLOSE_CURLY:
                        case SWITCH:
                        case CASE:
                        case COLON:
                        case DEFAULT:
                        case BREAK:
                        case MATH_OPERATOR:
                        case STRING_OPERATOR:
                        case BOOLEAN_OPERATOR:
                        case DIGIT:
                        case LETTER:
                        case WS:
                        case COMMENT:
                        case LINE_COMMENT:
                            {
                            alt15=3;
                            }
                            break;

                        }

                        switch (alt15) {
                    	case 1 :
                    	    // ./CFScript.g:209:19: ESCAPE_SINGLE_QUOTE
                    	    {
                    	    ESCAPE_SINGLE_QUOTE47=(Token)input.LT(1);
                    	    match(input,ESCAPE_SINGLE_QUOTE,FOLLOW_ESCAPE_SINGLE_QUOTE_in_stringLiteral480); 
                    	    ESCAPE_SINGLE_QUOTE47_tree = (Object)adaptor.create(ESCAPE_SINGLE_QUOTE47);
                    	    adaptor.addChild(root_0, ESCAPE_SINGLE_QUOTE47_tree);


                    	    }
                    	    break;
                    	case 2 :
                    	    // ./CFScript.g:209:41: innerStringCFML
                    	    {
                    	    pushFollow(FOLLOW_innerStringCFML_in_stringLiteral484);
                    	    innerStringCFML48=innerStringCFML();
                    	    _fsp--;

                    	    adaptor.addChild(root_0, innerStringCFML48.getTree());

                    	    }
                    	    break;
                    	case 3 :
                    	    // ./CFScript.g:209:59: ~ ( SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE | HASH )
                    	    {
                    	    set49=(Token)input.LT(1);
                    	    if ( (input.LA(1)>=FUNCTION_CALL && input.LA(1)<=NUMBER)||(input.LA(1)>=DOT && input.LA(1)<=ESCAPE_DOUBLE_QUOTE)||(input.LA(1)>=IDENTIFIER && input.LA(1)<=LINE_COMMENT) ) {
                    	        input.consume();
                    	        adaptor.addChild(root_0, adaptor.create(set49));
                    	        errorRecovery=false;
                    	    }
                    	    else {
                    	        MismatchedSetException mse =
                    	            new MismatchedSetException(null,input);
                    	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_stringLiteral488);    throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);

                    SINGLE_QUOTE50=(Token)input.LT(1);
                    match(input,SINGLE_QUOTE,FOLLOW_SINGLE_QUOTE_in_stringLiteral504); 
                    SINGLE_QUOTE50_tree = (Object)adaptor.create(SINGLE_QUOTE50);
                    adaptor.addChild(root_0, SINGLE_QUOTE50_tree);


                    }


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
    traceOut("stringLiteral", 14);
        }
        return retval;
    }
    // $ANTLR end stringLiteral

    public static class identifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start identifier
    // ./CFScript.g:213:1: identifier : IDENTIFIER ( struct )? ;
    public final identifier_return identifier() throws RecognitionException {
    traceIn("identifier", 15);
        identifier_return retval = new identifier_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENTIFIER51=null;
        struct_return struct52 = null;


        Object IDENTIFIER51_tree=null;

        try {
            // ./CFScript.g:215:2: ( IDENTIFIER ( struct )? )
            // ./CFScript.g:215:2: IDENTIFIER ( struct )?
            {
            root_0 = (Object)adaptor.nil();

            IDENTIFIER51=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_identifier519); 
            IDENTIFIER51_tree = (Object)adaptor.create(IDENTIFIER51);
            adaptor.addChild(root_0, IDENTIFIER51_tree);

            // ./CFScript.g:215:13: ( struct )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==OPEN_SQUARE) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // ./CFScript.g:215:14: struct
                    {
                    pushFollow(FOLLOW_struct_in_identifier522);
                    struct52=struct();
                    _fsp--;

                    adaptor.addChild(root_0, struct52.getTree());

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
    traceOut("identifier", 15);
        }
        return retval;
    }
    // $ANTLR end identifier

    public static class struct_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start struct
    // ./CFScript.g:218:1: struct : OPEN_SQUARE codeStatement CLOSE_SQUARE -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE ) ;
    public final struct_return struct() throws RecognitionException {
    traceIn("struct", 16);
        struct_return retval = new struct_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPEN_SQUARE53=null;
        Token CLOSE_SQUARE55=null;
        codeStatement_return codeStatement54 = null;


        Object OPEN_SQUARE53_tree=null;
        Object CLOSE_SQUARE55_tree=null;
        RewriteRuleTokenStream stream_OPEN_SQUARE=new RewriteRuleTokenStream(adaptor,"token OPEN_SQUARE");
        RewriteRuleTokenStream stream_CLOSE_SQUARE=new RewriteRuleTokenStream(adaptor,"token CLOSE_SQUARE");
        RewriteRuleSubtreeStream stream_codeStatement=new RewriteRuleSubtreeStream(adaptor,"rule codeStatement");
        try {
            // ./CFScript.g:220:2: ( OPEN_SQUARE codeStatement CLOSE_SQUARE -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE ) )
            // ./CFScript.g:220:2: OPEN_SQUARE codeStatement CLOSE_SQUARE
            {
            OPEN_SQUARE53=(Token)input.LT(1);
            match(input,OPEN_SQUARE,FOLLOW_OPEN_SQUARE_in_struct537); 
            stream_OPEN_SQUARE.add(OPEN_SQUARE53);

            pushFollow(FOLLOW_codeStatement_in_struct539);
            codeStatement54=codeStatement();
            _fsp--;

            stream_codeStatement.add(codeStatement54.getTree());
            CLOSE_SQUARE55=(Token)input.LT(1);
            match(input,CLOSE_SQUARE,FOLLOW_CLOSE_SQUARE_in_struct541); 
            stream_CLOSE_SQUARE.add(CLOSE_SQUARE55);


            // AST REWRITE
            // elements: codeStatement, OPEN_SQUARE, CLOSE_SQUARE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 221:2: -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE )
            {
                // ./CFScript.g:221:5: ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE )
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
    traceOut("struct", 16);
        }
        return retval;
    }
    // $ANTLR end struct

    public static class function_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start function
    // ./CFScript.g:224:1: function : IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN ) ;
    public final function_return function() throws RecognitionException {
    traceIn("function", 17);
        function_return retval = new function_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENTIFIER56=null;
        Token OPEN_PAREN57=null;
        Token CLOSE_PAREN59=null;
        argumentStatement_return argumentStatement58 = null;


        Object IDENTIFIER56_tree=null;
        Object OPEN_PAREN57_tree=null;
        Object CLOSE_PAREN59_tree=null;
        RewriteRuleTokenStream stream_OPEN_PAREN=new RewriteRuleTokenStream(adaptor,"token OPEN_PAREN");
        RewriteRuleTokenStream stream_CLOSE_PAREN=new RewriteRuleTokenStream(adaptor,"token CLOSE_PAREN");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleSubtreeStream stream_argumentStatement=new RewriteRuleSubtreeStream(adaptor,"rule argumentStatement");
        try {
            // ./CFScript.g:226:2: ( IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN ) )
            // ./CFScript.g:226:2: IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN
            {
            IDENTIFIER56=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_function567); 
            stream_IDENTIFIER.add(IDENTIFIER56);

            OPEN_PAREN57=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_function569); 
            stream_OPEN_PAREN.add(OPEN_PAREN57);

            // ./CFScript.g:226:24: ( argumentStatement )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==OPEN_PAREN||(LA18_0>=NOT && LA18_0<=HASH)||LA18_0==DOUBLE_QUOTE||LA18_0==SINGLE_QUOTE||LA18_0==IDENTIFIER) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // ./CFScript.g:226:25: argumentStatement
                    {
                    pushFollow(FOLLOW_argumentStatement_in_function572);
                    argumentStatement58=argumentStatement();
                    _fsp--;

                    stream_argumentStatement.add(argumentStatement58.getTree());

                    }
                    break;

            }

            CLOSE_PAREN59=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_function576); 
            stream_CLOSE_PAREN.add(CLOSE_PAREN59);


            // AST REWRITE
            // elements: argumentStatement, CLOSE_PAREN, OPEN_PAREN, IDENTIFIER
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 227:2: -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN )
            {
                // ./CFScript.g:227:5: ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(FUNCTION_CALL, "FUNCTION_CALL"), root_1);

                adaptor.addChild(root_1, stream_IDENTIFIER.next());
                adaptor.addChild(root_1, stream_OPEN_PAREN.next());
                // ./CFScript.g:227:43: ( argumentStatement )?
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
    traceOut("function", 17);
        }
        return retval;
    }
    // $ANTLR end function

    public static class argumentStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start argumentStatement
    // ./CFScript.g:230:1: argumentStatement : codeStatement ( COMMA codeStatement )* ;
    public final argumentStatement_return argumentStatement() throws RecognitionException {
    traceIn("argumentStatement", 18);
        argumentStatement_return retval = new argumentStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA61=null;
        codeStatement_return codeStatement60 = null;

        codeStatement_return codeStatement62 = null;


        Object COMMA61_tree=null;

        try {
            // ./CFScript.g:232:2: ( codeStatement ( COMMA codeStatement )* )
            // ./CFScript.g:232:2: codeStatement ( COMMA codeStatement )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_codeStatement_in_argumentStatement607);
            codeStatement60=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement60.getTree());
            // ./CFScript.g:232:16: ( COMMA codeStatement )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==COMMA) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // ./CFScript.g:232:17: COMMA codeStatement
            	    {
            	    COMMA61=(Token)input.LT(1);
            	    match(input,COMMA,FOLLOW_COMMA_in_argumentStatement610); 
            	    COMMA61_tree = (Object)adaptor.create(COMMA61);
            	    adaptor.addChild(root_0, COMMA61_tree);

            	    pushFollow(FOLLOW_codeStatement_in_argumentStatement612);
            	    codeStatement62=codeStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, codeStatement62.getTree());

            	    }
            	    break;

            	default :
            	    break loop19;
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
    traceOut("argumentStatement", 18);
        }
        return retval;
    }
    // $ANTLR end argumentStatement

    public static class ifStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start ifStatement
    // ./CFScript.g:235:1: ifStatement : IF OPEN_PAREN codeStatement CLOSE_PAREN block ( elseifStatement )* ( elseStatement )? ;
    public final ifStatement_return ifStatement() throws RecognitionException {
    traceIn("ifStatement", 19);
        ifStatement_return retval = new ifStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IF63=null;
        Token OPEN_PAREN64=null;
        Token CLOSE_PAREN66=null;
        codeStatement_return codeStatement65 = null;

        block_return block67 = null;

        elseifStatement_return elseifStatement68 = null;

        elseStatement_return elseStatement69 = null;


        Object IF63_tree=null;
        Object OPEN_PAREN64_tree=null;
        Object CLOSE_PAREN66_tree=null;

        try {
            // ./CFScript.g:237:2: ( IF OPEN_PAREN codeStatement CLOSE_PAREN block ( elseifStatement )* ( elseStatement )? )
            // ./CFScript.g:237:2: IF OPEN_PAREN codeStatement CLOSE_PAREN block ( elseifStatement )* ( elseStatement )?
            {
            root_0 = (Object)adaptor.nil();

            IF63=(Token)input.LT(1);
            match(input,IF,FOLLOW_IF_in_ifStatement626); 
            IF63_tree = (Object)adaptor.create(IF63);
            root_0 = (Object)adaptor.becomeRoot(IF63_tree, root_0);

            OPEN_PAREN64=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_ifStatement629); 
            OPEN_PAREN64_tree = (Object)adaptor.create(OPEN_PAREN64);
            adaptor.addChild(root_0, OPEN_PAREN64_tree);

            pushFollow(FOLLOW_codeStatement_in_ifStatement631);
            codeStatement65=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement65.getTree());
            CLOSE_PAREN66=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_ifStatement633); 
            CLOSE_PAREN66_tree = (Object)adaptor.create(CLOSE_PAREN66);
            adaptor.addChild(root_0, CLOSE_PAREN66_tree);

            pushFollow(FOLLOW_block_in_ifStatement636);
            block67=block();
            _fsp--;

            adaptor.addChild(root_0, block67.getTree());
            // ./CFScript.g:239:2: ( elseifStatement )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==ELSE) ) {
                    int LA20_1 = input.LA(2);

                    if ( (LA20_1==IF) ) {
                        alt20=1;
                    }


                }


                switch (alt20) {
            	case 1 :
            	    // ./CFScript.g:239:3: elseifStatement
            	    {
            	    pushFollow(FOLLOW_elseifStatement_in_ifStatement640);
            	    elseifStatement68=elseifStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, elseifStatement68.getTree());

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            // ./CFScript.g:240:2: ( elseStatement )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==ELSE) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // ./CFScript.g:240:3: elseStatement
                    {
                    pushFollow(FOLLOW_elseStatement_in_ifStatement646);
                    elseStatement69=elseStatement();
                    _fsp--;

                    adaptor.addChild(root_0, elseStatement69.getTree());

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
    traceOut("ifStatement", 19);
        }
        return retval;
    }
    // $ANTLR end ifStatement

    public static class elseifStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start elseifStatement
    // ./CFScript.g:243:1: elseifStatement : ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block -> ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block ) ;
    public final elseifStatement_return elseifStatement() throws RecognitionException {
    traceIn("elseifStatement", 20);
        elseifStatement_return retval = new elseifStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ELSE70=null;
        Token IF71=null;
        Token OPEN_PAREN72=null;
        Token CLOSE_PAREN74=null;
        codeStatement_return codeStatement73 = null;

        block_return block75 = null;


        Object ELSE70_tree=null;
        Object IF71_tree=null;
        Object OPEN_PAREN72_tree=null;
        Object CLOSE_PAREN74_tree=null;
        RewriteRuleTokenStream stream_OPEN_PAREN=new RewriteRuleTokenStream(adaptor,"token OPEN_PAREN");
        RewriteRuleTokenStream stream_CLOSE_PAREN=new RewriteRuleTokenStream(adaptor,"token CLOSE_PAREN");
        RewriteRuleTokenStream stream_ELSE=new RewriteRuleTokenStream(adaptor,"token ELSE");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_codeStatement=new RewriteRuleSubtreeStream(adaptor,"rule codeStatement");
        RewriteRuleSubtreeStream stream_block=new RewriteRuleSubtreeStream(adaptor,"rule block");
        try {
            // ./CFScript.g:245:2: ( ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block -> ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block ) )
            // ./CFScript.g:245:2: ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block
            {
            ELSE70=(Token)input.LT(1);
            match(input,ELSE,FOLLOW_ELSE_in_elseifStatement661); 
            stream_ELSE.add(ELSE70);

            IF71=(Token)input.LT(1);
            match(input,IF,FOLLOW_IF_in_elseifStatement663); 
            stream_IF.add(IF71);

            OPEN_PAREN72=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_elseifStatement665); 
            stream_OPEN_PAREN.add(OPEN_PAREN72);

            pushFollow(FOLLOW_codeStatement_in_elseifStatement667);
            codeStatement73=codeStatement();
            _fsp--;

            stream_codeStatement.add(codeStatement73.getTree());
            CLOSE_PAREN74=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_elseifStatement669); 
            stream_CLOSE_PAREN.add(CLOSE_PAREN74);

            pushFollow(FOLLOW_block_in_elseifStatement673);
            block75=block();
            _fsp--;

            stream_block.add(block75.getTree());

            // AST REWRITE
            // elements: CLOSE_PAREN, ELSE, codeStatement, block, IF, OPEN_PAREN
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 247:2: -> ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block )
            {
                // ./CFScript.g:247:5: ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block )
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
    traceOut("elseifStatement", 20);
        }
        return retval;
    }
    // $ANTLR end elseifStatement

    public static class elseStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start elseStatement
    // ./CFScript.g:253:1: elseStatement : ELSE block ;
    public final elseStatement_return elseStatement() throws RecognitionException {
    traceIn("elseStatement", 21);
        elseStatement_return retval = new elseStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ELSE76=null;
        block_return block77 = null;


        Object ELSE76_tree=null;

        try {
            // ./CFScript.g:255:2: ( ELSE block )
            // ./CFScript.g:255:2: ELSE block
            {
            root_0 = (Object)adaptor.nil();

            ELSE76=(Token)input.LT(1);
            match(input,ELSE,FOLLOW_ELSE_in_elseStatement717); 
            ELSE76_tree = (Object)adaptor.create(ELSE76);
            root_0 = (Object)adaptor.becomeRoot(ELSE76_tree, root_0);

            pushFollow(FOLLOW_block_in_elseStatement721);
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
    traceOut("elseStatement", 21);
        }
        return retval;
    }
    // $ANTLR end elseStatement

    public static class tryStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start tryStatement
    // ./CFScript.g:259:1: tryStatement : TRY block catchStatement ;
    public final tryStatement_return tryStatement() throws RecognitionException {
    traceIn("tryStatement", 22);
        tryStatement_return retval = new tryStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token TRY78=null;
        block_return block79 = null;

        catchStatement_return catchStatement80 = null;


        Object TRY78_tree=null;

        try {
            // ./CFScript.g:261:2: ( TRY block catchStatement )
            // ./CFScript.g:261:2: TRY block catchStatement
            {
            root_0 = (Object)adaptor.nil();

            TRY78=(Token)input.LT(1);
            match(input,TRY,FOLLOW_TRY_in_tryStatement733); 
            TRY78_tree = (Object)adaptor.create(TRY78);
            root_0 = (Object)adaptor.becomeRoot(TRY78_tree, root_0);

            pushFollow(FOLLOW_block_in_tryStatement737);
            block79=block();
            _fsp--;

            adaptor.addChild(root_0, block79.getTree());
            pushFollow(FOLLOW_catchStatement_in_tryStatement740);
            catchStatement80=catchStatement();
            _fsp--;

            adaptor.addChild(root_0, catchStatement80.getTree());

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
    traceOut("tryStatement", 22);
        }
        return retval;
    }
    // $ANTLR end tryStatement

    public static class catchStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start catchStatement
    // ./CFScript.g:266:1: catchStatement : CATCH OPEN_PAREN IDENTIFIER IDENTIFIER CLOSE_PAREN block ;
    public final catchStatement_return catchStatement() throws RecognitionException {
    traceIn("catchStatement", 23);
        catchStatement_return retval = new catchStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CATCH81=null;
        Token OPEN_PAREN82=null;
        Token IDENTIFIER83=null;
        Token IDENTIFIER84=null;
        Token CLOSE_PAREN85=null;
        block_return block86 = null;


        Object CATCH81_tree=null;
        Object OPEN_PAREN82_tree=null;
        Object IDENTIFIER83_tree=null;
        Object IDENTIFIER84_tree=null;
        Object CLOSE_PAREN85_tree=null;

        try {
            // ./CFScript.g:268:2: ( CATCH OPEN_PAREN IDENTIFIER IDENTIFIER CLOSE_PAREN block )
            // ./CFScript.g:268:2: CATCH OPEN_PAREN IDENTIFIER IDENTIFIER CLOSE_PAREN block
            {
            root_0 = (Object)adaptor.nil();

            CATCH81=(Token)input.LT(1);
            match(input,CATCH,FOLLOW_CATCH_in_catchStatement753); 
            CATCH81_tree = (Object)adaptor.create(CATCH81);
            root_0 = (Object)adaptor.becomeRoot(CATCH81_tree, root_0);

            OPEN_PAREN82=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_catchStatement756); 
            OPEN_PAREN82_tree = (Object)adaptor.create(OPEN_PAREN82);
            adaptor.addChild(root_0, OPEN_PAREN82_tree);

            IDENTIFIER83=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_catchStatement758); 
            IDENTIFIER83_tree = (Object)adaptor.create(IDENTIFIER83);
            adaptor.addChild(root_0, IDENTIFIER83_tree);

            IDENTIFIER84=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_catchStatement760); 
            IDENTIFIER84_tree = (Object)adaptor.create(IDENTIFIER84);
            adaptor.addChild(root_0, IDENTIFIER84_tree);

            CLOSE_PAREN85=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_catchStatement762); 
            CLOSE_PAREN85_tree = (Object)adaptor.create(CLOSE_PAREN85);
            adaptor.addChild(root_0, CLOSE_PAREN85_tree);

            pushFollow(FOLLOW_block_in_catchStatement765);
            block86=block();
            _fsp--;

            adaptor.addChild(root_0, block86.getTree());

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
    traceOut("catchStatement", 23);
        }
        return retval;
    }
    // $ANTLR end catchStatement

    public static class forStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start forStatement
    // ./CFScript.g:272:1: forStatement : FOR OPEN_PAREN forConditions CLOSE_PAREN block ;
    public final forStatement_return forStatement() throws RecognitionException {
    traceIn("forStatement", 24);
        forStatement_return retval = new forStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token FOR87=null;
        Token OPEN_PAREN88=null;
        Token CLOSE_PAREN90=null;
        forConditions_return forConditions89 = null;

        block_return block91 = null;


        Object FOR87_tree=null;
        Object OPEN_PAREN88_tree=null;
        Object CLOSE_PAREN90_tree=null;

        try {
            // ./CFScript.g:274:2: ( FOR OPEN_PAREN forConditions CLOSE_PAREN block )
            // ./CFScript.g:274:2: FOR OPEN_PAREN forConditions CLOSE_PAREN block
            {
            root_0 = (Object)adaptor.nil();

            FOR87=(Token)input.LT(1);
            match(input,FOR,FOLLOW_FOR_in_forStatement777); 
            FOR87_tree = (Object)adaptor.create(FOR87);
            root_0 = (Object)adaptor.becomeRoot(FOR87_tree, root_0);

            OPEN_PAREN88=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_forStatement780); 
            OPEN_PAREN88_tree = (Object)adaptor.create(OPEN_PAREN88);
            adaptor.addChild(root_0, OPEN_PAREN88_tree);

            pushFollow(FOLLOW_forConditions_in_forStatement782);
            forConditions89=forConditions();
            _fsp--;

            adaptor.addChild(root_0, forConditions89.getTree());
            CLOSE_PAREN90=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_forStatement784); 
            CLOSE_PAREN90_tree = (Object)adaptor.create(CLOSE_PAREN90);
            adaptor.addChild(root_0, CLOSE_PAREN90_tree);

            pushFollow(FOLLOW_block_in_forStatement787);
            block91=block();
            _fsp--;

            adaptor.addChild(root_0, block91.getTree());

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
    traceOut("forStatement", 24);
        }
        return retval;
    }
    // $ANTLR end forStatement

    public static class forConditions_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start forConditions
    // ./CFScript.g:277:1: forConditions : setStatement SEMI_COLON setStatement SEMI_COLON setStatement ;
    public final forConditions_return forConditions() throws RecognitionException {
    traceIn("forConditions", 25);
        forConditions_return retval = new forConditions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SEMI_COLON93=null;
        Token SEMI_COLON95=null;
        setStatement_return setStatement92 = null;

        setStatement_return setStatement94 = null;

        setStatement_return setStatement96 = null;


        Object SEMI_COLON93_tree=null;
        Object SEMI_COLON95_tree=null;

        try {
            // ./CFScript.g:279:2: ( setStatement SEMI_COLON setStatement SEMI_COLON setStatement )
            // ./CFScript.g:279:2: setStatement SEMI_COLON setStatement SEMI_COLON setStatement
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_setStatement_in_forConditions797);
            setStatement92=setStatement();
            _fsp--;

            adaptor.addChild(root_0, setStatement92.getTree());
            SEMI_COLON93=(Token)input.LT(1);
            match(input,SEMI_COLON,FOLLOW_SEMI_COLON_in_forConditions800); 
            SEMI_COLON93_tree = (Object)adaptor.create(SEMI_COLON93);
            adaptor.addChild(root_0, SEMI_COLON93_tree);

            pushFollow(FOLLOW_setStatement_in_forConditions803);
            setStatement94=setStatement();
            _fsp--;

            adaptor.addChild(root_0, setStatement94.getTree());
            SEMI_COLON95=(Token)input.LT(1);
            match(input,SEMI_COLON,FOLLOW_SEMI_COLON_in_forConditions806); 
            SEMI_COLON95_tree = (Object)adaptor.create(SEMI_COLON95);
            adaptor.addChild(root_0, SEMI_COLON95_tree);

            pushFollow(FOLLOW_setStatement_in_forConditions809);
            setStatement96=setStatement();
            _fsp--;

            adaptor.addChild(root_0, setStatement96.getTree());

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
    traceOut("forConditions", 25);
        }
        return retval;
    }
    // $ANTLR end forConditions

    public static class whileStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start whileStatement
    // ./CFScript.g:286:1: whileStatement : WHILE OPEN_PAREN codeStatement CLOSE_PAREN block ;
    public final whileStatement_return whileStatement() throws RecognitionException {
    traceIn("whileStatement", 26);
        whileStatement_return retval = new whileStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token WHILE97=null;
        Token OPEN_PAREN98=null;
        Token CLOSE_PAREN100=null;
        codeStatement_return codeStatement99 = null;

        block_return block101 = null;


        Object WHILE97_tree=null;
        Object OPEN_PAREN98_tree=null;
        Object CLOSE_PAREN100_tree=null;

        try {
            // ./CFScript.g:288:2: ( WHILE OPEN_PAREN codeStatement CLOSE_PAREN block )
            // ./CFScript.g:288:2: WHILE OPEN_PAREN codeStatement CLOSE_PAREN block
            {
            root_0 = (Object)adaptor.nil();

            WHILE97=(Token)input.LT(1);
            match(input,WHILE,FOLLOW_WHILE_in_whileStatement821); 
            WHILE97_tree = (Object)adaptor.create(WHILE97);
            root_0 = (Object)adaptor.becomeRoot(WHILE97_tree, root_0);

            OPEN_PAREN98=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_whileStatement824); 
            OPEN_PAREN98_tree = (Object)adaptor.create(OPEN_PAREN98);
            adaptor.addChild(root_0, OPEN_PAREN98_tree);

            pushFollow(FOLLOW_codeStatement_in_whileStatement826);
            codeStatement99=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement99.getTree());
            CLOSE_PAREN100=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_whileStatement828); 
            CLOSE_PAREN100_tree = (Object)adaptor.create(CLOSE_PAREN100);
            adaptor.addChild(root_0, CLOSE_PAREN100_tree);

            pushFollow(FOLLOW_block_in_whileStatement831);
            block101=block();
            _fsp--;

            adaptor.addChild(root_0, block101.getTree());

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
    traceOut("whileStatement", 26);
        }
        return retval;
    }
    // $ANTLR end whileStatement

    public static class doWhileStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start doWhileStatement
    // ./CFScript.g:292:1: doWhileStatement : DO block WHILE OPEN_PAREN codeStatement CLOSE_PAREN ;
    public final doWhileStatement_return doWhileStatement() throws RecognitionException {
    traceIn("doWhileStatement", 27);
        doWhileStatement_return retval = new doWhileStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DO102=null;
        Token WHILE104=null;
        Token OPEN_PAREN105=null;
        Token CLOSE_PAREN107=null;
        block_return block103 = null;

        codeStatement_return codeStatement106 = null;


        Object DO102_tree=null;
        Object WHILE104_tree=null;
        Object OPEN_PAREN105_tree=null;
        Object CLOSE_PAREN107_tree=null;

        try {
            // ./CFScript.g:294:2: ( DO block WHILE OPEN_PAREN codeStatement CLOSE_PAREN )
            // ./CFScript.g:294:2: DO block WHILE OPEN_PAREN codeStatement CLOSE_PAREN
            {
            root_0 = (Object)adaptor.nil();

            DO102=(Token)input.LT(1);
            match(input,DO,FOLLOW_DO_in_doWhileStatement843); 
            DO102_tree = (Object)adaptor.create(DO102);
            root_0 = (Object)adaptor.becomeRoot(DO102_tree, root_0);

            pushFollow(FOLLOW_block_in_doWhileStatement847);
            block103=block();
            _fsp--;

            adaptor.addChild(root_0, block103.getTree());
            WHILE104=(Token)input.LT(1);
            match(input,WHILE,FOLLOW_WHILE_in_doWhileStatement850); 
            WHILE104_tree = (Object)adaptor.create(WHILE104);
            adaptor.addChild(root_0, WHILE104_tree);

            OPEN_PAREN105=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_doWhileStatement852); 
            OPEN_PAREN105_tree = (Object)adaptor.create(OPEN_PAREN105);
            adaptor.addChild(root_0, OPEN_PAREN105_tree);

            pushFollow(FOLLOW_codeStatement_in_doWhileStatement854);
            codeStatement106=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement106.getTree());
            CLOSE_PAREN107=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_doWhileStatement856); 
            CLOSE_PAREN107_tree = (Object)adaptor.create(CLOSE_PAREN107);
            adaptor.addChild(root_0, CLOSE_PAREN107_tree);


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
    traceOut("doWhileStatement", 27);
        }
        return retval;
    }
    // $ANTLR end doWhileStatement

    public static class block_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start block
    // ./CFScript.g:299:1: block : ( ( OPEN_CURLY script CLOSE_CURLY ) | ( nonBlockStatement SEMI_COLON ) );
    public final block_return block() throws RecognitionException {
    traceIn("block", 28);
        block_return retval = new block_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPEN_CURLY108=null;
        Token CLOSE_CURLY110=null;
        Token SEMI_COLON112=null;
        script_return script109 = null;

        nonBlockStatement_return nonBlockStatement111 = null;


        Object OPEN_CURLY108_tree=null;
        Object CLOSE_CURLY110_tree=null;
        Object SEMI_COLON112_tree=null;

        try {
            // ./CFScript.g:301:2: ( ( OPEN_CURLY script CLOSE_CURLY ) | ( nonBlockStatement SEMI_COLON ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==OPEN_CURLY) ) {
                alt22=1;
            }
            else if ( (LA22_0==VAR||LA22_0==OPEN_PAREN||LA22_0==RETURN||(LA22_0>=NOT && LA22_0<=HASH)||LA22_0==DOUBLE_QUOTE||LA22_0==SINGLE_QUOTE||LA22_0==IDENTIFIER||LA22_0==BREAK) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("299:1: block : ( ( OPEN_CURLY script CLOSE_CURLY ) | ( nonBlockStatement SEMI_COLON ) );", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // ./CFScript.g:301:2: ( OPEN_CURLY script CLOSE_CURLY )
                    {
                    root_0 = (Object)adaptor.nil();

                    // ./CFScript.g:301:2: ( OPEN_CURLY script CLOSE_CURLY )
                    // ./CFScript.g:301:3: OPEN_CURLY script CLOSE_CURLY
                    {
                    OPEN_CURLY108=(Token)input.LT(1);
                    match(input,OPEN_CURLY,FOLLOW_OPEN_CURLY_in_block869); 
                    OPEN_CURLY108_tree = (Object)adaptor.create(OPEN_CURLY108);
                    adaptor.addChild(root_0, OPEN_CURLY108_tree);

                    pushFollow(FOLLOW_script_in_block871);
                    script109=script();
                    _fsp--;

                    adaptor.addChild(root_0, script109.getTree());
                    CLOSE_CURLY110=(Token)input.LT(1);
                    match(input,CLOSE_CURLY,FOLLOW_CLOSE_CURLY_in_block873); 
                    CLOSE_CURLY110_tree = (Object)adaptor.create(CLOSE_CURLY110);
                    adaptor.addChild(root_0, CLOSE_CURLY110_tree);


                    }


                    }
                    break;
                case 2 :
                    // ./CFScript.g:303:2: ( nonBlockStatement SEMI_COLON )
                    {
                    root_0 = (Object)adaptor.nil();

                    // ./CFScript.g:303:2: ( nonBlockStatement SEMI_COLON )
                    // ./CFScript.g:303:3: nonBlockStatement SEMI_COLON
                    {
                    pushFollow(FOLLOW_nonBlockStatement_in_block881);
                    nonBlockStatement111=nonBlockStatement();
                    _fsp--;

                    adaptor.addChild(root_0, nonBlockStatement111.getTree());
                    SEMI_COLON112=(Token)input.LT(1);
                    match(input,SEMI_COLON,FOLLOW_SEMI_COLON_in_block883); 
                    SEMI_COLON112_tree = (Object)adaptor.create(SEMI_COLON112);
                    adaptor.addChild(root_0, SEMI_COLON112_tree);


                    }


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
    traceOut("block", 28);
        }
        return retval;
    }
    // $ANTLR end block

    public static class switchStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start switchStatement
    // ./CFScript.g:307:1: switchStatement : SWITCH OPEN_PAREN codeStatement CLOSE_PAREN OPEN_CURLY ( caseStatement )* ( defaultStatement )? CLOSE_CURLY ;
    public final switchStatement_return switchStatement() throws RecognitionException {
    traceIn("switchStatement", 29);
        switchStatement_return retval = new switchStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SWITCH113=null;
        Token OPEN_PAREN114=null;
        Token CLOSE_PAREN116=null;
        Token OPEN_CURLY117=null;
        Token CLOSE_CURLY120=null;
        codeStatement_return codeStatement115 = null;

        caseStatement_return caseStatement118 = null;

        defaultStatement_return defaultStatement119 = null;


        Object SWITCH113_tree=null;
        Object OPEN_PAREN114_tree=null;
        Object CLOSE_PAREN116_tree=null;
        Object OPEN_CURLY117_tree=null;
        Object CLOSE_CURLY120_tree=null;

        try {
            // ./CFScript.g:309:2: ( SWITCH OPEN_PAREN codeStatement CLOSE_PAREN OPEN_CURLY ( caseStatement )* ( defaultStatement )? CLOSE_CURLY )
            // ./CFScript.g:309:2: SWITCH OPEN_PAREN codeStatement CLOSE_PAREN OPEN_CURLY ( caseStatement )* ( defaultStatement )? CLOSE_CURLY
            {
            root_0 = (Object)adaptor.nil();

            SWITCH113=(Token)input.LT(1);
            match(input,SWITCH,FOLLOW_SWITCH_in_switchStatement897); 
            SWITCH113_tree = (Object)adaptor.create(SWITCH113);
            root_0 = (Object)adaptor.becomeRoot(SWITCH113_tree, root_0);

            OPEN_PAREN114=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_switchStatement900); 
            OPEN_PAREN114_tree = (Object)adaptor.create(OPEN_PAREN114);
            adaptor.addChild(root_0, OPEN_PAREN114_tree);

            pushFollow(FOLLOW_codeStatement_in_switchStatement902);
            codeStatement115=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement115.getTree());
            CLOSE_PAREN116=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_switchStatement904); 
            CLOSE_PAREN116_tree = (Object)adaptor.create(CLOSE_PAREN116);
            adaptor.addChild(root_0, CLOSE_PAREN116_tree);

            OPEN_CURLY117=(Token)input.LT(1);
            match(input,OPEN_CURLY,FOLLOW_OPEN_CURLY_in_switchStatement907); 
            OPEN_CURLY117_tree = (Object)adaptor.create(OPEN_CURLY117);
            adaptor.addChild(root_0, OPEN_CURLY117_tree);

            // ./CFScript.g:311:2: ( caseStatement )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==CASE) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // ./CFScript.g:311:3: caseStatement
            	    {
            	    pushFollow(FOLLOW_caseStatement_in_switchStatement911);
            	    caseStatement118=caseStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, caseStatement118.getTree());

            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);

            // ./CFScript.g:312:2: ( defaultStatement )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==DEFAULT) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // ./CFScript.g:312:3: defaultStatement
                    {
                    pushFollow(FOLLOW_defaultStatement_in_switchStatement917);
                    defaultStatement119=defaultStatement();
                    _fsp--;

                    adaptor.addChild(root_0, defaultStatement119.getTree());

                    }
                    break;

            }

            CLOSE_CURLY120=(Token)input.LT(1);
            match(input,CLOSE_CURLY,FOLLOW_CLOSE_CURLY_in_switchStatement922); 
            CLOSE_CURLY120_tree = (Object)adaptor.create(CLOSE_CURLY120);
            adaptor.addChild(root_0, CLOSE_CURLY120_tree);


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
    traceOut("switchStatement", 29);
        }
        return retval;
    }
    // $ANTLR end switchStatement

    public static class caseStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start caseStatement
    // ./CFScript.g:316:1: caseStatement : CASE ( stringLiteral | NUMBER ) COLON script ;
    public final caseStatement_return caseStatement() throws RecognitionException {
    traceIn("caseStatement", 30);
        caseStatement_return retval = new caseStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CASE121=null;
        Token NUMBER123=null;
        Token COLON124=null;
        stringLiteral_return stringLiteral122 = null;

        script_return script125 = null;


        Object CASE121_tree=null;
        Object NUMBER123_tree=null;
        Object COLON124_tree=null;

        try {
            // ./CFScript.g:318:2: ( CASE ( stringLiteral | NUMBER ) COLON script )
            // ./CFScript.g:318:2: CASE ( stringLiteral | NUMBER ) COLON script
            {
            root_0 = (Object)adaptor.nil();

            CASE121=(Token)input.LT(1);
            match(input,CASE,FOLLOW_CASE_in_caseStatement935); 
            CASE121_tree = (Object)adaptor.create(CASE121);
            root_0 = (Object)adaptor.becomeRoot(CASE121_tree, root_0);

            // ./CFScript.g:318:8: ( stringLiteral | NUMBER )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==DOUBLE_QUOTE||LA25_0==SINGLE_QUOTE) ) {
                alt25=1;
            }
            else if ( (LA25_0==NUMBER) ) {
                alt25=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("318:8: ( stringLiteral | NUMBER )", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // ./CFScript.g:318:9: stringLiteral
                    {
                    pushFollow(FOLLOW_stringLiteral_in_caseStatement939);
                    stringLiteral122=stringLiteral();
                    _fsp--;

                    adaptor.addChild(root_0, stringLiteral122.getTree());

                    }
                    break;
                case 2 :
                    // ./CFScript.g:318:25: NUMBER
                    {
                    NUMBER123=(Token)input.LT(1);
                    match(input,NUMBER,FOLLOW_NUMBER_in_caseStatement943); 
                    NUMBER123_tree = (Object)adaptor.create(NUMBER123);
                    adaptor.addChild(root_0, NUMBER123_tree);


                    }
                    break;

            }

            COLON124=(Token)input.LT(1);
            match(input,COLON,FOLLOW_COLON_in_caseStatement946); 
            COLON124_tree = (Object)adaptor.create(COLON124);
            adaptor.addChild(root_0, COLON124_tree);

            pushFollow(FOLLOW_script_in_caseStatement949);
            script125=script();
            _fsp--;

            adaptor.addChild(root_0, script125.getTree());

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
    traceOut("caseStatement", 30);
        }
        return retval;
    }
    // $ANTLR end caseStatement

    public static class defaultStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start defaultStatement
    // ./CFScript.g:322:1: defaultStatement : DEFAULT COLON script ;
    public final defaultStatement_return defaultStatement() throws RecognitionException {
    traceIn("defaultStatement", 31);
        defaultStatement_return retval = new defaultStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEFAULT126=null;
        Token COLON127=null;
        script_return script128 = null;


        Object DEFAULT126_tree=null;
        Object COLON127_tree=null;

        try {
            // ./CFScript.g:324:2: ( DEFAULT COLON script )
            // ./CFScript.g:324:2: DEFAULT COLON script
            {
            root_0 = (Object)adaptor.nil();

            DEFAULT126=(Token)input.LT(1);
            match(input,DEFAULT,FOLLOW_DEFAULT_in_defaultStatement961); 
            DEFAULT126_tree = (Object)adaptor.create(DEFAULT126);
            root_0 = (Object)adaptor.becomeRoot(DEFAULT126_tree, root_0);

            COLON127=(Token)input.LT(1);
            match(input,COLON,FOLLOW_COLON_in_defaultStatement964); 
            COLON127_tree = (Object)adaptor.create(COLON127);
            adaptor.addChild(root_0, COLON127_tree);

            pushFollow(FOLLOW_script_in_defaultStatement967);
            script128=script();
            _fsp--;

            adaptor.addChild(root_0, script128.getTree());

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
    traceOut("defaultStatement", 31);
        }
        return retval;
    }
    // $ANTLR end defaultStatement

    public static class breakStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start breakStatement
    // ./CFScript.g:329:1: breakStatement : BREAK ;
    public final breakStatement_return breakStatement() throws RecognitionException {
    traceIn("breakStatement", 32);
        breakStatement_return retval = new breakStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token BREAK129=null;

        Object BREAK129_tree=null;

        try {
            // ./CFScript.g:331:2: ( BREAK )
            // ./CFScript.g:331:2: BREAK
            {
            root_0 = (Object)adaptor.nil();

            BREAK129=(Token)input.LT(1);
            match(input,BREAK,FOLLOW_BREAK_in_breakStatement980); 
            BREAK129_tree = (Object)adaptor.create(BREAK129);
            adaptor.addChild(root_0, BREAK129_tree);


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
    traceOut("breakStatement", 32);
        }
        return retval;
    }
    // $ANTLR end breakStatement


 

    public static final BitSet FOLLOW_nonBlockStatement_in_script92 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ifStatement_in_script103 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_tryStatement_in_script113 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_forStatement_in_script123 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_whileStatement_in_script133 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_doWhileStatement_in_script143 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_switchStatement_in_script153 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SEMI_COLON_in_script159 = new BitSet(new long[]{0x0000022751575402L});
    public static final BitSet FOLLOW_setStatement_in_nonBlockStatement176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_returnStatement_in_nonBlockStatement182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_breakStatement_in_nonBlockStatement188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_setStatement206 = new BitSet(new long[]{0x0000000001571000L});
    public static final BitSet FOLLOW_codeStatement_in_setStatement210 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_EQUALS_in_setStatement213 = new BitSet(new long[]{0x0000000001571000L});
    public static final BitSet FOLLOW_codeStatement_in_setStatement215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_codeStatement233 = new BitSet(new long[]{0x0000000001571000L});
    public static final BitSet FOLLOW_codeStatement_in_codeStatement235 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_codeStatement237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasicStatement_in_codeStatement245 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RETURN_in_returnStatement261 = new BitSet(new long[]{0x0000000001571002L});
    public static final BitSet FOLLOW_codeStatement_in_returnStatement265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlValueStatement_in_cfmlBasicStatement279 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_OPERATOR_in_cfmlBasicStatement282 = new BitSet(new long[]{0x0000000001571000L});
    public static final BitSet FOLLOW_codeStatement_in_cfmlBasicStatement284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cfmlValueStatement300 = new BitSet(new long[]{0x0000000001560000L});
    public static final BitSet FOLLOW_cfmlValue_in_cfmlValueStatement304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_cfmlValue317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stringLiteral_in_cfmlValue321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlLinking_in_cfmlValue325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hashCfmlLinking_in_cfmlLinking338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasicLinking_in_cfmlLinking344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_hashCfmlLinking357 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_cfmlBasicLinking_in_hashCfmlLinking359 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_HASH_in_hashCfmlLinking361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasic_in_cfmlBasicLinking373 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_DOT_in_cfmlBasicLinking376 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_cfmlBasic_in_cfmlBasicLinking378 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_identifier_in_cfmlBasic392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_cfmlBasic396 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hashCfmlLinking_in_innerStringCFML408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_QUOTE_in_stringLiteral433 = new BitSet(new long[]{0x0003FFFFFFFFFFF0L});
    public static final BitSet FOLLOW_ESCAPE_DOUBLE_QUOTE_in_stringLiteral438 = new BitSet(new long[]{0x0003FFFFFFFFFFF0L});
    public static final BitSet FOLLOW_innerStringCFML_in_stringLiteral442 = new BitSet(new long[]{0x0003FFFFFFFFFFF0L});
    public static final BitSet FOLLOW_set_in_stringLiteral446 = new BitSet(new long[]{0x0003FFFFFFFFFFF0L});
    public static final BitSet FOLLOW_DOUBLE_QUOTE_in_stringLiteral462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SINGLE_QUOTE_in_stringLiteral475 = new BitSet(new long[]{0x0003FFFFFFFFFFF0L});
    public static final BitSet FOLLOW_ESCAPE_SINGLE_QUOTE_in_stringLiteral480 = new BitSet(new long[]{0x0003FFFFFFFFFFF0L});
    public static final BitSet FOLLOW_innerStringCFML_in_stringLiteral484 = new BitSet(new long[]{0x0003FFFFFFFFFFF0L});
    public static final BitSet FOLLOW_set_in_stringLiteral488 = new BitSet(new long[]{0x0003FFFFFFFFFFF0L});
    public static final BitSet FOLLOW_SINGLE_QUOTE_in_stringLiteral504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_identifier519 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_struct_in_identifier522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_SQUARE_in_struct537 = new BitSet(new long[]{0x0000000001571000L});
    public static final BitSet FOLLOW_codeStatement_in_struct539 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_CLOSE_SQUARE_in_struct541 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_function567 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_function569 = new BitSet(new long[]{0x0000000001573000L});
    public static final BitSet FOLLOW_argumentStatement_in_function572 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_function576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_codeStatement_in_argumentStatement607 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_COMMA_in_argumentStatement610 = new BitSet(new long[]{0x0000000001571000L});
    public static final BitSet FOLLOW_codeStatement_in_argumentStatement612 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_IF_in_ifStatement626 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_ifStatement629 = new BitSet(new long[]{0x0000000001571000L});
    public static final BitSet FOLLOW_codeStatement_in_ifStatement631 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_ifStatement633 = new BitSet(new long[]{0x0000020801575400L});
    public static final BitSet FOLLOW_block_in_ifStatement636 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_elseifStatement_in_ifStatement640 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_elseStatement_in_ifStatement646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_elseifStatement661 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_IF_in_elseifStatement663 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_elseifStatement665 = new BitSet(new long[]{0x0000000001571000L});
    public static final BitSet FOLLOW_codeStatement_in_elseifStatement667 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_elseifStatement669 = new BitSet(new long[]{0x0000020801575400L});
    public static final BitSet FOLLOW_block_in_elseifStatement673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_elseStatement717 = new BitSet(new long[]{0x0000020801575400L});
    public static final BitSet FOLLOW_block_in_elseStatement721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRY_in_tryStatement733 = new BitSet(new long[]{0x0000020801575400L});
    public static final BitSet FOLLOW_block_in_tryStatement737 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_catchStatement_in_tryStatement740 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CATCH_in_catchStatement753 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_catchStatement756 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_catchStatement758 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_catchStatement760 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_catchStatement762 = new BitSet(new long[]{0x0000020801575400L});
    public static final BitSet FOLLOW_block_in_catchStatement765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_forStatement777 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_forStatement780 = new BitSet(new long[]{0x0000000001571400L});
    public static final BitSet FOLLOW_forConditions_in_forStatement782 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_forStatement784 = new BitSet(new long[]{0x0000020801575400L});
    public static final BitSet FOLLOW_block_in_forStatement787 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_setStatement_in_forConditions797 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SEMI_COLON_in_forConditions800 = new BitSet(new long[]{0x0000000001571400L});
    public static final BitSet FOLLOW_setStatement_in_forConditions803 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SEMI_COLON_in_forConditions806 = new BitSet(new long[]{0x0000000001571400L});
    public static final BitSet FOLLOW_setStatement_in_forConditions809 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHILE_in_whileStatement821 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_whileStatement824 = new BitSet(new long[]{0x0000000001571000L});
    public static final BitSet FOLLOW_codeStatement_in_whileStatement826 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_whileStatement828 = new BitSet(new long[]{0x0000020801575400L});
    public static final BitSet FOLLOW_block_in_whileStatement831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DO_in_doWhileStatement843 = new BitSet(new long[]{0x0000020801575400L});
    public static final BitSet FOLLOW_block_in_doWhileStatement847 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_WHILE_in_doWhileStatement850 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_doWhileStatement852 = new BitSet(new long[]{0x0000000001571000L});
    public static final BitSet FOLLOW_codeStatement_in_doWhileStatement854 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_doWhileStatement856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_CURLY_in_block869 = new BitSet(new long[]{0x0000023751575400L});
    public static final BitSet FOLLOW_script_in_block871 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_CLOSE_CURLY_in_block873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nonBlockStatement_in_block881 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SEMI_COLON_in_block883 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SWITCH_in_switchStatement897 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_switchStatement900 = new BitSet(new long[]{0x0000000001571000L});
    public static final BitSet FOLLOW_codeStatement_in_switchStatement902 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_switchStatement904 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_OPEN_CURLY_in_switchStatement907 = new BitSet(new long[]{0x0000015000000000L});
    public static final BitSet FOLLOW_caseStatement_in_switchStatement911 = new BitSet(new long[]{0x0000015000000000L});
    public static final BitSet FOLLOW_defaultStatement_in_switchStatement917 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_CLOSE_CURLY_in_switchStatement922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CASE_in_caseStatement935 = new BitSet(new long[]{0x0000000000520000L});
    public static final BitSet FOLLOW_stringLiteral_in_caseStatement939 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_NUMBER_in_caseStatement943 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_COLON_in_caseStatement946 = new BitSet(new long[]{0x0000022751575402L});
    public static final BitSet FOLLOW_script_in_caseStatement949 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DEFAULT_in_defaultStatement961 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_COLON_in_defaultStatement964 = new BitSet(new long[]{0x0000022751575402L});
    public static final BitSet FOLLOW_script_in_defaultStatement967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BREAK_in_breakStatement980 = new BitSet(new long[]{0x0000000000000002L});

}