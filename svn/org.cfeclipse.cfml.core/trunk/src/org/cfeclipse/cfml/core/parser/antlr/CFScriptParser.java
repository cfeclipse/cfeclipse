// $ANTLR 3.0 ./CFScript.g 2007-05-24 17:58:46

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "FUNCTION_CALL", "STRUCT_KEY", "ELSEIF", "SEMI_COLON", "VAR", "EQUALS", "OPEN_PAREN", "CLOSE_PAREN", "RETURN", "OPERATOR", "NOT", "NUMBER", "STRING", "HASH", "DOT", "IDENTIFIER", "OPEN_SQUARE", "CLOSE_SQUARE", "COMMA", "IF", "ELSE", "TRY", "CATCH", "FOR", "WHILE", "DO", "OPEN_CURLY", "CLOSE_CURLY", "SWITCH", "CASE", "COLON", "DEFAULT", "BREAK", "MATH_OPERATOR", "STRING_OPERATOR", "BOOLEAN_OPERATOR", "DIGIT", "LETTER", "WS", "COMMENT", "LINE_COMMENT"
    };
    public static final int WHILE=28;
    public static final int CLOSE_CURLY=31;
    public static final int LETTER=41;
    public static final int CASE=33;
    public static final int FOR=27;
    public static final int DO=29;
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
    public static final int TRY=25;
    public static final int ELSEIF=6;
    public static final int COLON=34;
    public static final int WS=42;
    public static final int BOOLEAN_OPERATOR=39;
    public static final int CATCH=26;
    public static final int FUNCTION_CALL=4;
    public static final int STRING_OPERATOR=38;
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
    // ./CFScript.g:94:1: script : ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement | tryStatement | forStatement | whileStatement | doWhileStatement | switchStatement | breakStatement SEMI_COLON )* ;
    public final script_return script() throws RecognitionException {
    traceIn("script", 1);
        script_return retval = new script_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SEMI_COLON2=null;
        Token SEMI_COLON4=null;
        Token SEMI_COLON12=null;
        setStatement_return setStatement1 = null;

        returnStatement_return returnStatement3 = null;

        ifStatement_return ifStatement5 = null;

        tryStatement_return tryStatement6 = null;

        forStatement_return forStatement7 = null;

        whileStatement_return whileStatement8 = null;

        doWhileStatement_return doWhileStatement9 = null;

        switchStatement_return switchStatement10 = null;

        breakStatement_return breakStatement11 = null;


        Object SEMI_COLON2_tree=null;
        Object SEMI_COLON4_tree=null;
        Object SEMI_COLON12_tree=null;

        try {
            // ./CFScript.g:96:2: ( ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement | tryStatement | forStatement | whileStatement | doWhileStatement | switchStatement | breakStatement SEMI_COLON )* )
            // ./CFScript.g:96:2: ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement | tryStatement | forStatement | whileStatement | doWhileStatement | switchStatement | breakStatement SEMI_COLON )*
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:96:2: ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement | tryStatement | forStatement | whileStatement | doWhileStatement | switchStatement | breakStatement SEMI_COLON )*
            loop1:
            do {
                int alt1=10;
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
                case SWITCH:
                    {
                    alt1=8;
                    }
                    break;
                case BREAK:
                    {
                    alt1=9;
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
            	case 8 :
            	    // ./CFScript.g:111:3: switchStatement
            	    {
            	    pushFollow(FOLLOW_switchStatement_in_script132);
            	    switchStatement10=switchStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, switchStatement10.getTree());

            	    }
            	    break;
            	case 9 :
            	    // ./CFScript.g:113:3: breakStatement SEMI_COLON
            	    {
            	    pushFollow(FOLLOW_breakStatement_in_script140);
            	    breakStatement11=breakStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, breakStatement11.getTree());
            	    SEMI_COLON12=(Token)input.LT(1);
            	    match(input,SEMI_COLON,FOLLOW_SEMI_COLON_in_script142); 
            	    SEMI_COLON12_tree = (Object)adaptor.create(SEMI_COLON12);
            	    adaptor.addChild(root_0, SEMI_COLON12_tree);


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
    // ./CFScript.g:117:1: setStatement : ( VAR )? codeStatement ( EQUALS codeStatement )? ;
    public final setStatement_return setStatement() throws RecognitionException {
    traceIn("setStatement", 2);
        setStatement_return retval = new setStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token VAR13=null;
        Token EQUALS15=null;
        codeStatement_return codeStatement14 = null;

        codeStatement_return codeStatement16 = null;


        Object VAR13_tree=null;
        Object EQUALS15_tree=null;

        try {
            // ./CFScript.g:127:2: ( ( VAR )? codeStatement ( EQUALS codeStatement )? )
            // ./CFScript.g:127:2: ( VAR )? codeStatement ( EQUALS codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:127:2: ( VAR )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==VAR) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // ./CFScript.g:127:3: VAR
                    {
                    VAR13=(Token)input.LT(1);
                    match(input,VAR,FOLLOW_VAR_in_setStatement163); 
                    VAR13_tree = (Object)adaptor.create(VAR13);
                    adaptor.addChild(root_0, VAR13_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_codeStatement_in_setStatement167);
            codeStatement14=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement14.getTree());
            // ./CFScript.g:127:23: ( EQUALS codeStatement )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==EQUALS) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ./CFScript.g:127:24: EQUALS codeStatement
                    {
                    EQUALS15=(Token)input.LT(1);
                    match(input,EQUALS,FOLLOW_EQUALS_in_setStatement170); 
                    EQUALS15_tree = (Object)adaptor.create(EQUALS15);
                    adaptor.addChild(root_0, EQUALS15_tree);

                    pushFollow(FOLLOW_codeStatement_in_setStatement172);
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
    // ./CFScript.g:130:1: codeStatement : ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement ) ;
    public final codeStatement_return codeStatement() throws RecognitionException {
    traceIn("codeStatement", 3);
        codeStatement_return retval = new codeStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPEN_PAREN17=null;
        Token CLOSE_PAREN19=null;
        codeStatement_return codeStatement18 = null;

        cfmlBasicStatement_return cfmlBasicStatement20 = null;


        Object OPEN_PAREN17_tree=null;
        Object CLOSE_PAREN19_tree=null;

        try {
            // ./CFScript.g:132:2: ( ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement ) )
            // ./CFScript.g:132:2: ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement )
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:132:2: ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement )
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
                    new NoViableAltException("132:2: ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement )", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // ./CFScript.g:133:3: OPEN_PAREN codeStatement CLOSE_PAREN
                    {
                    OPEN_PAREN17=(Token)input.LT(1);
                    match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_codeStatement190); 
                    OPEN_PAREN17_tree = (Object)adaptor.create(OPEN_PAREN17);
                    adaptor.addChild(root_0, OPEN_PAREN17_tree);

                    pushFollow(FOLLOW_codeStatement_in_codeStatement192);
                    codeStatement18=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement18.getTree());
                    CLOSE_PAREN19=(Token)input.LT(1);
                    match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_codeStatement194); 
                    CLOSE_PAREN19_tree = (Object)adaptor.create(CLOSE_PAREN19);
                    adaptor.addChild(root_0, CLOSE_PAREN19_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:135:3: cfmlBasicStatement
                    {
                    pushFollow(FOLLOW_cfmlBasicStatement_in_codeStatement202);
                    cfmlBasicStatement20=cfmlBasicStatement();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlBasicStatement20.getTree());

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
    // ./CFScript.g:139:1: returnStatement : RETURN ( codeStatement )? ;
    public final returnStatement_return returnStatement() throws RecognitionException {
    traceIn("returnStatement", 4);
        returnStatement_return retval = new returnStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token RETURN21=null;
        codeStatement_return codeStatement22 = null;


        Object RETURN21_tree=null;

        try {
            // ./CFScript.g:141:2: ( RETURN ( codeStatement )? )
            // ./CFScript.g:141:2: RETURN ( codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            RETURN21=(Token)input.LT(1);
            match(input,RETURN,FOLLOW_RETURN_in_returnStatement218); 
            RETURN21_tree = (Object)adaptor.create(RETURN21);
            root_0 = (Object)adaptor.becomeRoot(RETURN21_tree, root_0);

            // ./CFScript.g:141:10: ( codeStatement )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==OPEN_PAREN||(LA5_0>=NOT && LA5_0<=HASH)||LA5_0==IDENTIFIER) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ./CFScript.g:141:11: codeStatement
                    {
                    pushFollow(FOLLOW_codeStatement_in_returnStatement222);
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
    // ./CFScript.g:144:1: cfmlBasicStatement : cfmlValueStatement ( OPERATOR codeStatement )? ;
    public final cfmlBasicStatement_return cfmlBasicStatement() throws RecognitionException {
    traceIn("cfmlBasicStatement", 5);
        cfmlBasicStatement_return retval = new cfmlBasicStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPERATOR24=null;
        cfmlValueStatement_return cfmlValueStatement23 = null;

        codeStatement_return codeStatement25 = null;


        Object OPERATOR24_tree=null;

        try {
            // ./CFScript.g:146:2: ( cfmlValueStatement ( OPERATOR codeStatement )? )
            // ./CFScript.g:146:2: cfmlValueStatement ( OPERATOR codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_cfmlValueStatement_in_cfmlBasicStatement236);
            cfmlValueStatement23=cfmlValueStatement();
            _fsp--;

            adaptor.addChild(root_0, cfmlValueStatement23.getTree());
            // ./CFScript.g:146:21: ( OPERATOR codeStatement )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==OPERATOR) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ./CFScript.g:146:22: OPERATOR codeStatement
                    {
                    OPERATOR24=(Token)input.LT(1);
                    match(input,OPERATOR,FOLLOW_OPERATOR_in_cfmlBasicStatement239); 
                    OPERATOR24_tree = (Object)adaptor.create(OPERATOR24);
                    adaptor.addChild(root_0, OPERATOR24_tree);

                    pushFollow(FOLLOW_codeStatement_in_cfmlBasicStatement241);
                    codeStatement25=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement25.getTree());

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
    // ./CFScript.g:150:1: cfmlValueStatement : ( NOT )? cfmlValue ;
    public final cfmlValueStatement_return cfmlValueStatement() throws RecognitionException {
    traceIn("cfmlValueStatement", 6);
        cfmlValueStatement_return retval = new cfmlValueStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT26=null;
        cfmlValue_return cfmlValue27 = null;


        Object NOT26_tree=null;

        try {
            // ./CFScript.g:152:2: ( ( NOT )? cfmlValue )
            // ./CFScript.g:152:2: ( NOT )? cfmlValue
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:152:2: ( NOT )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==NOT) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ./CFScript.g:152:3: NOT
                    {
                    NOT26=(Token)input.LT(1);
                    match(input,NOT,FOLLOW_NOT_in_cfmlValueStatement257); 
                    NOT26_tree = (Object)adaptor.create(NOT26);
                    adaptor.addChild(root_0, NOT26_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_cfmlValue_in_cfmlValueStatement261);
            cfmlValue27=cfmlValue();
            _fsp--;

            adaptor.addChild(root_0, cfmlValue27.getTree());

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
    // ./CFScript.g:155:1: cfmlValue : ( NUMBER | STRING | cfmlLinking ) ;
    public final cfmlValue_return cfmlValue() throws RecognitionException {
    traceIn("cfmlValue", 7);
        cfmlValue_return retval = new cfmlValue_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUMBER28=null;
        Token STRING29=null;
        cfmlLinking_return cfmlLinking30 = null;


        Object NUMBER28_tree=null;
        Object STRING29_tree=null;

        try {
            // ./CFScript.g:157:2: ( ( NUMBER | STRING | cfmlLinking ) )
            // ./CFScript.g:157:2: ( NUMBER | STRING | cfmlLinking )
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:157:2: ( NUMBER | STRING | cfmlLinking )
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
                    new NoViableAltException("157:2: ( NUMBER | STRING | cfmlLinking )", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // ./CFScript.g:157:3: NUMBER
                    {
                    NUMBER28=(Token)input.LT(1);
                    match(input,NUMBER,FOLLOW_NUMBER_in_cfmlValue274); 
                    NUMBER28_tree = (Object)adaptor.create(NUMBER28);
                    adaptor.addChild(root_0, NUMBER28_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:157:12: STRING
                    {
                    STRING29=(Token)input.LT(1);
                    match(input,STRING,FOLLOW_STRING_in_cfmlValue278); 
                    STRING29_tree = (Object)adaptor.create(STRING29);
                    adaptor.addChild(root_0, STRING29_tree);


                    }
                    break;
                case 3 :
                    // ./CFScript.g:157:21: cfmlLinking
                    {
                    pushFollow(FOLLOW_cfmlLinking_in_cfmlValue282);
                    cfmlLinking30=cfmlLinking();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlLinking30.getTree());

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
    // ./CFScript.g:160:1: cfmlLinking : ( HASH cfmlBasicLinking HASH | cfmlBasicLinking );
    public final cfmlLinking_return cfmlLinking() throws RecognitionException {
    traceIn("cfmlLinking", 8);
        cfmlLinking_return retval = new cfmlLinking_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token HASH31=null;
        Token HASH33=null;
        cfmlBasicLinking_return cfmlBasicLinking32 = null;

        cfmlBasicLinking_return cfmlBasicLinking34 = null;


        Object HASH31_tree=null;
        Object HASH33_tree=null;

        try {
            // ./CFScript.g:162:2: ( HASH cfmlBasicLinking HASH | cfmlBasicLinking )
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
                    new NoViableAltException("160:1: cfmlLinking : ( HASH cfmlBasicLinking HASH | cfmlBasicLinking );", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // ./CFScript.g:162:2: HASH cfmlBasicLinking HASH
                    {
                    root_0 = (Object)adaptor.nil();

                    HASH31=(Token)input.LT(1);
                    match(input,HASH,FOLLOW_HASH_in_cfmlLinking295); 
                    HASH31_tree = (Object)adaptor.create(HASH31);
                    adaptor.addChild(root_0, HASH31_tree);

                    pushFollow(FOLLOW_cfmlBasicLinking_in_cfmlLinking297);
                    cfmlBasicLinking32=cfmlBasicLinking();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlBasicLinking32.getTree());
                    HASH33=(Token)input.LT(1);
                    match(input,HASH,FOLLOW_HASH_in_cfmlLinking299); 
                    HASH33_tree = (Object)adaptor.create(HASH33);
                    adaptor.addChild(root_0, HASH33_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:164:2: cfmlBasicLinking
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_cfmlBasicLinking_in_cfmlLinking305);
                    cfmlBasicLinking34=cfmlBasicLinking();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlBasicLinking34.getTree());

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
    // ./CFScript.g:167:1: cfmlBasicLinking : cfmlBasic ( DOT cfmlBasic )* ;
    public final cfmlBasicLinking_return cfmlBasicLinking() throws RecognitionException {
    traceIn("cfmlBasicLinking", 9);
        cfmlBasicLinking_return retval = new cfmlBasicLinking_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOT36=null;
        cfmlBasic_return cfmlBasic35 = null;

        cfmlBasic_return cfmlBasic37 = null;


        Object DOT36_tree=null;

        try {
            // ./CFScript.g:169:2: ( cfmlBasic ( DOT cfmlBasic )* )
            // ./CFScript.g:169:2: cfmlBasic ( DOT cfmlBasic )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_cfmlBasic_in_cfmlBasicLinking317);
            cfmlBasic35=cfmlBasic();
            _fsp--;

            adaptor.addChild(root_0, cfmlBasic35.getTree());
            // ./CFScript.g:169:12: ( DOT cfmlBasic )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==DOT) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ./CFScript.g:169:13: DOT cfmlBasic
            	    {
            	    DOT36=(Token)input.LT(1);
            	    match(input,DOT,FOLLOW_DOT_in_cfmlBasicLinking320); 
            	    DOT36_tree = (Object)adaptor.create(DOT36);
            	    adaptor.addChild(root_0, DOT36_tree);

            	    pushFollow(FOLLOW_cfmlBasic_in_cfmlBasicLinking322);
            	    cfmlBasic37=cfmlBasic();
            	    _fsp--;

            	    adaptor.addChild(root_0, cfmlBasic37.getTree());

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
    // ./CFScript.g:172:1: cfmlBasic : ( identifier | function );
    public final cfmlBasic_return cfmlBasic() throws RecognitionException {
    traceIn("cfmlBasic", 10);
        cfmlBasic_return retval = new cfmlBasic_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        identifier_return identifier38 = null;

        function_return function39 = null;



        try {
            // ./CFScript.g:174:2: ( identifier | function )
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
                        new NoViableAltException("172:1: cfmlBasic : ( identifier | function );", 11, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("172:1: cfmlBasic : ( identifier | function );", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // ./CFScript.g:174:2: identifier
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_identifier_in_cfmlBasic336);
                    identifier38=identifier();
                    _fsp--;

                    adaptor.addChild(root_0, identifier38.getTree());

                    }
                    break;
                case 2 :
                    // ./CFScript.g:174:15: function
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_in_cfmlBasic340);
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
    // ./CFScript.g:177:1: identifier : IDENTIFIER ( struct )? ;
    public final identifier_return identifier() throws RecognitionException {
    traceIn("identifier", 11);
        identifier_return retval = new identifier_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENTIFIER40=null;
        struct_return struct41 = null;


        Object IDENTIFIER40_tree=null;

        try {
            // ./CFScript.g:179:2: ( IDENTIFIER ( struct )? )
            // ./CFScript.g:179:2: IDENTIFIER ( struct )?
            {
            root_0 = (Object)adaptor.nil();

            IDENTIFIER40=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_identifier352); 
            IDENTIFIER40_tree = (Object)adaptor.create(IDENTIFIER40);
            adaptor.addChild(root_0, IDENTIFIER40_tree);

            // ./CFScript.g:179:13: ( struct )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==OPEN_SQUARE) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ./CFScript.g:179:14: struct
                    {
                    pushFollow(FOLLOW_struct_in_identifier355);
                    struct41=struct();
                    _fsp--;

                    adaptor.addChild(root_0, struct41.getTree());

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
    // ./CFScript.g:182:1: struct : OPEN_SQUARE codeStatement CLOSE_SQUARE -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE ) ;
    public final struct_return struct() throws RecognitionException {
    traceIn("struct", 12);
        struct_return retval = new struct_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPEN_SQUARE42=null;
        Token CLOSE_SQUARE44=null;
        codeStatement_return codeStatement43 = null;


        Object OPEN_SQUARE42_tree=null;
        Object CLOSE_SQUARE44_tree=null;
        RewriteRuleTokenStream stream_OPEN_SQUARE=new RewriteRuleTokenStream(adaptor,"token OPEN_SQUARE");
        RewriteRuleTokenStream stream_CLOSE_SQUARE=new RewriteRuleTokenStream(adaptor,"token CLOSE_SQUARE");
        RewriteRuleSubtreeStream stream_codeStatement=new RewriteRuleSubtreeStream(adaptor,"rule codeStatement");
        try {
            // ./CFScript.g:184:2: ( OPEN_SQUARE codeStatement CLOSE_SQUARE -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE ) )
            // ./CFScript.g:184:2: OPEN_SQUARE codeStatement CLOSE_SQUARE
            {
            OPEN_SQUARE42=(Token)input.LT(1);
            match(input,OPEN_SQUARE,FOLLOW_OPEN_SQUARE_in_struct370); 
            stream_OPEN_SQUARE.add(OPEN_SQUARE42);

            pushFollow(FOLLOW_codeStatement_in_struct372);
            codeStatement43=codeStatement();
            _fsp--;

            stream_codeStatement.add(codeStatement43.getTree());
            CLOSE_SQUARE44=(Token)input.LT(1);
            match(input,CLOSE_SQUARE,FOLLOW_CLOSE_SQUARE_in_struct374); 
            stream_CLOSE_SQUARE.add(CLOSE_SQUARE44);


            // AST REWRITE
            // elements: OPEN_SQUARE, codeStatement, CLOSE_SQUARE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 185:2: -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE )
            {
                // ./CFScript.g:185:5: ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE )
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
    // ./CFScript.g:188:1: function : IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN ) ;
    public final function_return function() throws RecognitionException {
    traceIn("function", 13);
        function_return retval = new function_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENTIFIER45=null;
        Token OPEN_PAREN46=null;
        Token CLOSE_PAREN48=null;
        argumentStatement_return argumentStatement47 = null;


        Object IDENTIFIER45_tree=null;
        Object OPEN_PAREN46_tree=null;
        Object CLOSE_PAREN48_tree=null;
        RewriteRuleTokenStream stream_OPEN_PAREN=new RewriteRuleTokenStream(adaptor,"token OPEN_PAREN");
        RewriteRuleTokenStream stream_CLOSE_PAREN=new RewriteRuleTokenStream(adaptor,"token CLOSE_PAREN");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleSubtreeStream stream_argumentStatement=new RewriteRuleSubtreeStream(adaptor,"rule argumentStatement");
        try {
            // ./CFScript.g:190:2: ( IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN ) )
            // ./CFScript.g:190:2: IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN
            {
            IDENTIFIER45=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_function400); 
            stream_IDENTIFIER.add(IDENTIFIER45);

            OPEN_PAREN46=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_function402); 
            stream_OPEN_PAREN.add(OPEN_PAREN46);

            // ./CFScript.g:190:24: ( argumentStatement )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==OPEN_PAREN||(LA13_0>=NOT && LA13_0<=HASH)||LA13_0==IDENTIFIER) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ./CFScript.g:190:25: argumentStatement
                    {
                    pushFollow(FOLLOW_argumentStatement_in_function405);
                    argumentStatement47=argumentStatement();
                    _fsp--;

                    stream_argumentStatement.add(argumentStatement47.getTree());

                    }
                    break;

            }

            CLOSE_PAREN48=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_function409); 
            stream_CLOSE_PAREN.add(CLOSE_PAREN48);


            // AST REWRITE
            // elements: IDENTIFIER, CLOSE_PAREN, OPEN_PAREN, argumentStatement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 191:2: -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN )
            {
                // ./CFScript.g:191:5: ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(FUNCTION_CALL, "FUNCTION_CALL"), root_1);

                adaptor.addChild(root_1, stream_IDENTIFIER.next());
                adaptor.addChild(root_1, stream_OPEN_PAREN.next());
                // ./CFScript.g:191:43: ( argumentStatement )?
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
    // ./CFScript.g:194:1: argumentStatement : codeStatement ( COMMA codeStatement )* ;
    public final argumentStatement_return argumentStatement() throws RecognitionException {
    traceIn("argumentStatement", 14);
        argumentStatement_return retval = new argumentStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA50=null;
        codeStatement_return codeStatement49 = null;

        codeStatement_return codeStatement51 = null;


        Object COMMA50_tree=null;

        try {
            // ./CFScript.g:196:2: ( codeStatement ( COMMA codeStatement )* )
            // ./CFScript.g:196:2: codeStatement ( COMMA codeStatement )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_codeStatement_in_argumentStatement440);
            codeStatement49=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement49.getTree());
            // ./CFScript.g:196:16: ( COMMA codeStatement )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==COMMA) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ./CFScript.g:196:17: COMMA codeStatement
            	    {
            	    COMMA50=(Token)input.LT(1);
            	    match(input,COMMA,FOLLOW_COMMA_in_argumentStatement443); 
            	    COMMA50_tree = (Object)adaptor.create(COMMA50);
            	    adaptor.addChild(root_0, COMMA50_tree);

            	    pushFollow(FOLLOW_codeStatement_in_argumentStatement445);
            	    codeStatement51=codeStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, codeStatement51.getTree());

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
    // ./CFScript.g:199:1: ifStatement : IF OPEN_PAREN codeStatement CLOSE_PAREN block ( elseifStatement )* ( elseStatement )? ;
    public final ifStatement_return ifStatement() throws RecognitionException {
    traceIn("ifStatement", 15);
        ifStatement_return retval = new ifStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IF52=null;
        Token OPEN_PAREN53=null;
        Token CLOSE_PAREN55=null;
        codeStatement_return codeStatement54 = null;

        block_return block56 = null;

        elseifStatement_return elseifStatement57 = null;

        elseStatement_return elseStatement58 = null;


        Object IF52_tree=null;
        Object OPEN_PAREN53_tree=null;
        Object CLOSE_PAREN55_tree=null;

        try {
            // ./CFScript.g:201:2: ( IF OPEN_PAREN codeStatement CLOSE_PAREN block ( elseifStatement )* ( elseStatement )? )
            // ./CFScript.g:201:2: IF OPEN_PAREN codeStatement CLOSE_PAREN block ( elseifStatement )* ( elseStatement )?
            {
            root_0 = (Object)adaptor.nil();

            IF52=(Token)input.LT(1);
            match(input,IF,FOLLOW_IF_in_ifStatement459); 
            IF52_tree = (Object)adaptor.create(IF52);
            root_0 = (Object)adaptor.becomeRoot(IF52_tree, root_0);

            OPEN_PAREN53=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_ifStatement462); 
            OPEN_PAREN53_tree = (Object)adaptor.create(OPEN_PAREN53);
            adaptor.addChild(root_0, OPEN_PAREN53_tree);

            pushFollow(FOLLOW_codeStatement_in_ifStatement464);
            codeStatement54=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement54.getTree());
            CLOSE_PAREN55=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_ifStatement466); 
            CLOSE_PAREN55_tree = (Object)adaptor.create(CLOSE_PAREN55);
            adaptor.addChild(root_0, CLOSE_PAREN55_tree);

            pushFollow(FOLLOW_block_in_ifStatement469);
            block56=block();
            _fsp--;

            adaptor.addChild(root_0, block56.getTree());
            // ./CFScript.g:203:2: ( elseifStatement )*
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
            	    // ./CFScript.g:203:3: elseifStatement
            	    {
            	    pushFollow(FOLLOW_elseifStatement_in_ifStatement473);
            	    elseifStatement57=elseifStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, elseifStatement57.getTree());

            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

            // ./CFScript.g:204:2: ( elseStatement )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==ELSE) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // ./CFScript.g:204:3: elseStatement
                    {
                    pushFollow(FOLLOW_elseStatement_in_ifStatement479);
                    elseStatement58=elseStatement();
                    _fsp--;

                    adaptor.addChild(root_0, elseStatement58.getTree());

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
    // ./CFScript.g:207:1: elseifStatement : ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block -> ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block ) ;
    public final elseifStatement_return elseifStatement() throws RecognitionException {
    traceIn("elseifStatement", 16);
        elseifStatement_return retval = new elseifStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ELSE59=null;
        Token IF60=null;
        Token OPEN_PAREN61=null;
        Token CLOSE_PAREN63=null;
        codeStatement_return codeStatement62 = null;

        block_return block64 = null;


        Object ELSE59_tree=null;
        Object IF60_tree=null;
        Object OPEN_PAREN61_tree=null;
        Object CLOSE_PAREN63_tree=null;
        RewriteRuleTokenStream stream_OPEN_PAREN=new RewriteRuleTokenStream(adaptor,"token OPEN_PAREN");
        RewriteRuleTokenStream stream_CLOSE_PAREN=new RewriteRuleTokenStream(adaptor,"token CLOSE_PAREN");
        RewriteRuleTokenStream stream_ELSE=new RewriteRuleTokenStream(adaptor,"token ELSE");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_codeStatement=new RewriteRuleSubtreeStream(adaptor,"rule codeStatement");
        RewriteRuleSubtreeStream stream_block=new RewriteRuleSubtreeStream(adaptor,"rule block");
        try {
            // ./CFScript.g:209:2: ( ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block -> ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block ) )
            // ./CFScript.g:209:2: ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block
            {
            ELSE59=(Token)input.LT(1);
            match(input,ELSE,FOLLOW_ELSE_in_elseifStatement494); 
            stream_ELSE.add(ELSE59);

            IF60=(Token)input.LT(1);
            match(input,IF,FOLLOW_IF_in_elseifStatement496); 
            stream_IF.add(IF60);

            OPEN_PAREN61=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_elseifStatement498); 
            stream_OPEN_PAREN.add(OPEN_PAREN61);

            pushFollow(FOLLOW_codeStatement_in_elseifStatement500);
            codeStatement62=codeStatement();
            _fsp--;

            stream_codeStatement.add(codeStatement62.getTree());
            CLOSE_PAREN63=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_elseifStatement502); 
            stream_CLOSE_PAREN.add(CLOSE_PAREN63);

            pushFollow(FOLLOW_block_in_elseifStatement506);
            block64=block();
            _fsp--;

            stream_block.add(block64.getTree());

            // AST REWRITE
            // elements: CLOSE_PAREN, OPEN_PAREN, IF, codeStatement, ELSE, block
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 211:2: -> ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block )
            {
                // ./CFScript.g:211:5: ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block )
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
    // ./CFScript.g:217:1: elseStatement : ELSE block ;
    public final elseStatement_return elseStatement() throws RecognitionException {
    traceIn("elseStatement", 17);
        elseStatement_return retval = new elseStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ELSE65=null;
        block_return block66 = null;


        Object ELSE65_tree=null;

        try {
            // ./CFScript.g:219:2: ( ELSE block )
            // ./CFScript.g:219:2: ELSE block
            {
            root_0 = (Object)adaptor.nil();

            ELSE65=(Token)input.LT(1);
            match(input,ELSE,FOLLOW_ELSE_in_elseStatement550); 
            ELSE65_tree = (Object)adaptor.create(ELSE65);
            root_0 = (Object)adaptor.becomeRoot(ELSE65_tree, root_0);

            pushFollow(FOLLOW_block_in_elseStatement554);
            block66=block();
            _fsp--;

            adaptor.addChild(root_0, block66.getTree());

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
    // ./CFScript.g:223:1: tryStatement : TRY block catchStatement ;
    public final tryStatement_return tryStatement() throws RecognitionException {
    traceIn("tryStatement", 18);
        tryStatement_return retval = new tryStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token TRY67=null;
        block_return block68 = null;

        catchStatement_return catchStatement69 = null;


        Object TRY67_tree=null;

        try {
            // ./CFScript.g:225:2: ( TRY block catchStatement )
            // ./CFScript.g:225:2: TRY block catchStatement
            {
            root_0 = (Object)adaptor.nil();

            TRY67=(Token)input.LT(1);
            match(input,TRY,FOLLOW_TRY_in_tryStatement566); 
            TRY67_tree = (Object)adaptor.create(TRY67);
            root_0 = (Object)adaptor.becomeRoot(TRY67_tree, root_0);

            pushFollow(FOLLOW_block_in_tryStatement570);
            block68=block();
            _fsp--;

            adaptor.addChild(root_0, block68.getTree());
            pushFollow(FOLLOW_catchStatement_in_tryStatement573);
            catchStatement69=catchStatement();
            _fsp--;

            adaptor.addChild(root_0, catchStatement69.getTree());

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
    // ./CFScript.g:230:1: catchStatement : CATCH OPEN_PAREN IDENTIFIER IDENTIFIER CLOSE_PAREN block ;
    public final catchStatement_return catchStatement() throws RecognitionException {
    traceIn("catchStatement", 19);
        catchStatement_return retval = new catchStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CATCH70=null;
        Token OPEN_PAREN71=null;
        Token IDENTIFIER72=null;
        Token IDENTIFIER73=null;
        Token CLOSE_PAREN74=null;
        block_return block75 = null;


        Object CATCH70_tree=null;
        Object OPEN_PAREN71_tree=null;
        Object IDENTIFIER72_tree=null;
        Object IDENTIFIER73_tree=null;
        Object CLOSE_PAREN74_tree=null;

        try {
            // ./CFScript.g:232:2: ( CATCH OPEN_PAREN IDENTIFIER IDENTIFIER CLOSE_PAREN block )
            // ./CFScript.g:232:2: CATCH OPEN_PAREN IDENTIFIER IDENTIFIER CLOSE_PAREN block
            {
            root_0 = (Object)adaptor.nil();

            CATCH70=(Token)input.LT(1);
            match(input,CATCH,FOLLOW_CATCH_in_catchStatement586); 
            CATCH70_tree = (Object)adaptor.create(CATCH70);
            root_0 = (Object)adaptor.becomeRoot(CATCH70_tree, root_0);

            OPEN_PAREN71=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_catchStatement589); 
            OPEN_PAREN71_tree = (Object)adaptor.create(OPEN_PAREN71);
            adaptor.addChild(root_0, OPEN_PAREN71_tree);

            IDENTIFIER72=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_catchStatement591); 
            IDENTIFIER72_tree = (Object)adaptor.create(IDENTIFIER72);
            adaptor.addChild(root_0, IDENTIFIER72_tree);

            IDENTIFIER73=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_catchStatement593); 
            IDENTIFIER73_tree = (Object)adaptor.create(IDENTIFIER73);
            adaptor.addChild(root_0, IDENTIFIER73_tree);

            CLOSE_PAREN74=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_catchStatement595); 
            CLOSE_PAREN74_tree = (Object)adaptor.create(CLOSE_PAREN74);
            adaptor.addChild(root_0, CLOSE_PAREN74_tree);

            pushFollow(FOLLOW_block_in_catchStatement598);
            block75=block();
            _fsp--;

            adaptor.addChild(root_0, block75.getTree());

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
    // ./CFScript.g:236:1: forStatement : FOR OPEN_PAREN forConditions CLOSE_PAREN block ;
    public final forStatement_return forStatement() throws RecognitionException {
    traceIn("forStatement", 20);
        forStatement_return retval = new forStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token FOR76=null;
        Token OPEN_PAREN77=null;
        Token CLOSE_PAREN79=null;
        forConditions_return forConditions78 = null;

        block_return block80 = null;


        Object FOR76_tree=null;
        Object OPEN_PAREN77_tree=null;
        Object CLOSE_PAREN79_tree=null;

        try {
            // ./CFScript.g:238:2: ( FOR OPEN_PAREN forConditions CLOSE_PAREN block )
            // ./CFScript.g:238:2: FOR OPEN_PAREN forConditions CLOSE_PAREN block
            {
            root_0 = (Object)adaptor.nil();

            FOR76=(Token)input.LT(1);
            match(input,FOR,FOLLOW_FOR_in_forStatement610); 
            FOR76_tree = (Object)adaptor.create(FOR76);
            root_0 = (Object)adaptor.becomeRoot(FOR76_tree, root_0);

            OPEN_PAREN77=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_forStatement613); 
            OPEN_PAREN77_tree = (Object)adaptor.create(OPEN_PAREN77);
            adaptor.addChild(root_0, OPEN_PAREN77_tree);

            pushFollow(FOLLOW_forConditions_in_forStatement615);
            forConditions78=forConditions();
            _fsp--;

            adaptor.addChild(root_0, forConditions78.getTree());
            CLOSE_PAREN79=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_forStatement617); 
            CLOSE_PAREN79_tree = (Object)adaptor.create(CLOSE_PAREN79);
            adaptor.addChild(root_0, CLOSE_PAREN79_tree);

            pushFollow(FOLLOW_block_in_forStatement620);
            block80=block();
            _fsp--;

            adaptor.addChild(root_0, block80.getTree());

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
    // ./CFScript.g:241:1: forConditions : setStatement SEMI_COLON setStatement SEMI_COLON setStatement ;
    public final forConditions_return forConditions() throws RecognitionException {
    traceIn("forConditions", 21);
        forConditions_return retval = new forConditions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SEMI_COLON82=null;
        Token SEMI_COLON84=null;
        setStatement_return setStatement81 = null;

        setStatement_return setStatement83 = null;

        setStatement_return setStatement85 = null;


        Object SEMI_COLON82_tree=null;
        Object SEMI_COLON84_tree=null;

        try {
            // ./CFScript.g:243:2: ( setStatement SEMI_COLON setStatement SEMI_COLON setStatement )
            // ./CFScript.g:243:2: setStatement SEMI_COLON setStatement SEMI_COLON setStatement
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_setStatement_in_forConditions630);
            setStatement81=setStatement();
            _fsp--;

            adaptor.addChild(root_0, setStatement81.getTree());
            SEMI_COLON82=(Token)input.LT(1);
            match(input,SEMI_COLON,FOLLOW_SEMI_COLON_in_forConditions633); 
            SEMI_COLON82_tree = (Object)adaptor.create(SEMI_COLON82);
            adaptor.addChild(root_0, SEMI_COLON82_tree);

            pushFollow(FOLLOW_setStatement_in_forConditions636);
            setStatement83=setStatement();
            _fsp--;

            adaptor.addChild(root_0, setStatement83.getTree());
            SEMI_COLON84=(Token)input.LT(1);
            match(input,SEMI_COLON,FOLLOW_SEMI_COLON_in_forConditions639); 
            SEMI_COLON84_tree = (Object)adaptor.create(SEMI_COLON84);
            adaptor.addChild(root_0, SEMI_COLON84_tree);

            pushFollow(FOLLOW_setStatement_in_forConditions642);
            setStatement85=setStatement();
            _fsp--;

            adaptor.addChild(root_0, setStatement85.getTree());

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
    // ./CFScript.g:250:1: whileStatement : WHILE OPEN_PAREN codeStatement CLOSE_PAREN block ;
    public final whileStatement_return whileStatement() throws RecognitionException {
    traceIn("whileStatement", 22);
        whileStatement_return retval = new whileStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token WHILE86=null;
        Token OPEN_PAREN87=null;
        Token CLOSE_PAREN89=null;
        codeStatement_return codeStatement88 = null;

        block_return block90 = null;


        Object WHILE86_tree=null;
        Object OPEN_PAREN87_tree=null;
        Object CLOSE_PAREN89_tree=null;

        try {
            // ./CFScript.g:252:2: ( WHILE OPEN_PAREN codeStatement CLOSE_PAREN block )
            // ./CFScript.g:252:2: WHILE OPEN_PAREN codeStatement CLOSE_PAREN block
            {
            root_0 = (Object)adaptor.nil();

            WHILE86=(Token)input.LT(1);
            match(input,WHILE,FOLLOW_WHILE_in_whileStatement654); 
            WHILE86_tree = (Object)adaptor.create(WHILE86);
            root_0 = (Object)adaptor.becomeRoot(WHILE86_tree, root_0);

            OPEN_PAREN87=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_whileStatement657); 
            OPEN_PAREN87_tree = (Object)adaptor.create(OPEN_PAREN87);
            adaptor.addChild(root_0, OPEN_PAREN87_tree);

            pushFollow(FOLLOW_codeStatement_in_whileStatement659);
            codeStatement88=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement88.getTree());
            CLOSE_PAREN89=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_whileStatement661); 
            CLOSE_PAREN89_tree = (Object)adaptor.create(CLOSE_PAREN89);
            adaptor.addChild(root_0, CLOSE_PAREN89_tree);

            pushFollow(FOLLOW_block_in_whileStatement664);
            block90=block();
            _fsp--;

            adaptor.addChild(root_0, block90.getTree());

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
    // ./CFScript.g:256:1: doWhileStatement : DO block WHILE OPEN_PAREN codeStatement CLOSE_PAREN ;
    public final doWhileStatement_return doWhileStatement() throws RecognitionException {
    traceIn("doWhileStatement", 23);
        doWhileStatement_return retval = new doWhileStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DO91=null;
        Token WHILE93=null;
        Token OPEN_PAREN94=null;
        Token CLOSE_PAREN96=null;
        block_return block92 = null;

        codeStatement_return codeStatement95 = null;


        Object DO91_tree=null;
        Object WHILE93_tree=null;
        Object OPEN_PAREN94_tree=null;
        Object CLOSE_PAREN96_tree=null;

        try {
            // ./CFScript.g:258:2: ( DO block WHILE OPEN_PAREN codeStatement CLOSE_PAREN )
            // ./CFScript.g:258:2: DO block WHILE OPEN_PAREN codeStatement CLOSE_PAREN
            {
            root_0 = (Object)adaptor.nil();

            DO91=(Token)input.LT(1);
            match(input,DO,FOLLOW_DO_in_doWhileStatement676); 
            DO91_tree = (Object)adaptor.create(DO91);
            root_0 = (Object)adaptor.becomeRoot(DO91_tree, root_0);

            pushFollow(FOLLOW_block_in_doWhileStatement680);
            block92=block();
            _fsp--;

            adaptor.addChild(root_0, block92.getTree());
            WHILE93=(Token)input.LT(1);
            match(input,WHILE,FOLLOW_WHILE_in_doWhileStatement683); 
            WHILE93_tree = (Object)adaptor.create(WHILE93);
            adaptor.addChild(root_0, WHILE93_tree);

            OPEN_PAREN94=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_doWhileStatement685); 
            OPEN_PAREN94_tree = (Object)adaptor.create(OPEN_PAREN94);
            adaptor.addChild(root_0, OPEN_PAREN94_tree);

            pushFollow(FOLLOW_codeStatement_in_doWhileStatement687);
            codeStatement95=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement95.getTree());
            CLOSE_PAREN96=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_doWhileStatement689); 
            CLOSE_PAREN96_tree = (Object)adaptor.create(CLOSE_PAREN96);
            adaptor.addChild(root_0, CLOSE_PAREN96_tree);


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
    // ./CFScript.g:263:1: block : OPEN_CURLY script CLOSE_CURLY ;
    public final block_return block() throws RecognitionException {
    traceIn("block", 24);
        block_return retval = new block_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPEN_CURLY97=null;
        Token CLOSE_CURLY99=null;
        script_return script98 = null;


        Object OPEN_CURLY97_tree=null;
        Object CLOSE_CURLY99_tree=null;

        try {
            // ./CFScript.g:265:2: ( OPEN_CURLY script CLOSE_CURLY )
            // ./CFScript.g:265:2: OPEN_CURLY script CLOSE_CURLY
            {
            root_0 = (Object)adaptor.nil();

            OPEN_CURLY97=(Token)input.LT(1);
            match(input,OPEN_CURLY,FOLLOW_OPEN_CURLY_in_block701); 
            OPEN_CURLY97_tree = (Object)adaptor.create(OPEN_CURLY97);
            adaptor.addChild(root_0, OPEN_CURLY97_tree);

            pushFollow(FOLLOW_script_in_block703);
            script98=script();
            _fsp--;

            adaptor.addChild(root_0, script98.getTree());
            CLOSE_CURLY99=(Token)input.LT(1);
            match(input,CLOSE_CURLY,FOLLOW_CLOSE_CURLY_in_block705); 
            CLOSE_CURLY99_tree = (Object)adaptor.create(CLOSE_CURLY99);
            adaptor.addChild(root_0, CLOSE_CURLY99_tree);


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

    public static class switchStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start switchStatement
    // ./CFScript.g:269:1: switchStatement : SWITCH OPEN_PAREN codeStatement CLOSE_PAREN OPEN_CURLY ( caseStatement )* ( defaultStatement )? CLOSE_CURLY ;
    public final switchStatement_return switchStatement() throws RecognitionException {
    traceIn("switchStatement", 25);
        switchStatement_return retval = new switchStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SWITCH100=null;
        Token OPEN_PAREN101=null;
        Token CLOSE_PAREN103=null;
        Token OPEN_CURLY104=null;
        Token CLOSE_CURLY107=null;
        codeStatement_return codeStatement102 = null;

        caseStatement_return caseStatement105 = null;

        defaultStatement_return defaultStatement106 = null;


        Object SWITCH100_tree=null;
        Object OPEN_PAREN101_tree=null;
        Object CLOSE_PAREN103_tree=null;
        Object OPEN_CURLY104_tree=null;
        Object CLOSE_CURLY107_tree=null;

        try {
            // ./CFScript.g:271:2: ( SWITCH OPEN_PAREN codeStatement CLOSE_PAREN OPEN_CURLY ( caseStatement )* ( defaultStatement )? CLOSE_CURLY )
            // ./CFScript.g:271:2: SWITCH OPEN_PAREN codeStatement CLOSE_PAREN OPEN_CURLY ( caseStatement )* ( defaultStatement )? CLOSE_CURLY
            {
            root_0 = (Object)adaptor.nil();

            SWITCH100=(Token)input.LT(1);
            match(input,SWITCH,FOLLOW_SWITCH_in_switchStatement718); 
            SWITCH100_tree = (Object)adaptor.create(SWITCH100);
            root_0 = (Object)adaptor.becomeRoot(SWITCH100_tree, root_0);

            OPEN_PAREN101=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_switchStatement721); 
            OPEN_PAREN101_tree = (Object)adaptor.create(OPEN_PAREN101);
            adaptor.addChild(root_0, OPEN_PAREN101_tree);

            pushFollow(FOLLOW_codeStatement_in_switchStatement723);
            codeStatement102=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement102.getTree());
            CLOSE_PAREN103=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_switchStatement725); 
            CLOSE_PAREN103_tree = (Object)adaptor.create(CLOSE_PAREN103);
            adaptor.addChild(root_0, CLOSE_PAREN103_tree);

            OPEN_CURLY104=(Token)input.LT(1);
            match(input,OPEN_CURLY,FOLLOW_OPEN_CURLY_in_switchStatement728); 
            OPEN_CURLY104_tree = (Object)adaptor.create(OPEN_CURLY104);
            adaptor.addChild(root_0, OPEN_CURLY104_tree);

            // ./CFScript.g:273:2: ( caseStatement )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==CASE) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // ./CFScript.g:273:3: caseStatement
            	    {
            	    pushFollow(FOLLOW_caseStatement_in_switchStatement732);
            	    caseStatement105=caseStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, caseStatement105.getTree());

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            // ./CFScript.g:274:2: ( defaultStatement )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==DEFAULT) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // ./CFScript.g:274:3: defaultStatement
                    {
                    pushFollow(FOLLOW_defaultStatement_in_switchStatement738);
                    defaultStatement106=defaultStatement();
                    _fsp--;

                    adaptor.addChild(root_0, defaultStatement106.getTree());

                    }
                    break;

            }

            CLOSE_CURLY107=(Token)input.LT(1);
            match(input,CLOSE_CURLY,FOLLOW_CLOSE_CURLY_in_switchStatement743); 
            CLOSE_CURLY107_tree = (Object)adaptor.create(CLOSE_CURLY107);
            adaptor.addChild(root_0, CLOSE_CURLY107_tree);


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
    traceOut("switchStatement", 25);
        }
        return retval;
    }
    // $ANTLR end switchStatement

    public static class caseStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start caseStatement
    // ./CFScript.g:278:1: caseStatement : CASE ( STRING | NUMBER ) COLON script ;
    public final caseStatement_return caseStatement() throws RecognitionException {
    traceIn("caseStatement", 26);
        caseStatement_return retval = new caseStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token CASE108=null;
        Token set109=null;
        Token COLON110=null;
        script_return script111 = null;


        Object CASE108_tree=null;
        Object set109_tree=null;
        Object COLON110_tree=null;

        try {
            // ./CFScript.g:280:2: ( CASE ( STRING | NUMBER ) COLON script )
            // ./CFScript.g:280:2: CASE ( STRING | NUMBER ) COLON script
            {
            root_0 = (Object)adaptor.nil();

            CASE108=(Token)input.LT(1);
            match(input,CASE,FOLLOW_CASE_in_caseStatement756); 
            CASE108_tree = (Object)adaptor.create(CASE108);
            root_0 = (Object)adaptor.becomeRoot(CASE108_tree, root_0);

            set109=(Token)input.LT(1);
            if ( (input.LA(1)>=NUMBER && input.LA(1)<=STRING) ) {
                input.consume();
                adaptor.addChild(root_0, adaptor.create(set109));
                errorRecovery=false;
            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_caseStatement759);    throw mse;
            }

            COLON110=(Token)input.LT(1);
            match(input,COLON,FOLLOW_COLON_in_caseStatement767); 
            COLON110_tree = (Object)adaptor.create(COLON110);
            adaptor.addChild(root_0, COLON110_tree);

            pushFollow(FOLLOW_script_in_caseStatement770);
            script111=script();
            _fsp--;

            adaptor.addChild(root_0, script111.getTree());

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
    traceOut("caseStatement", 26);
        }
        return retval;
    }
    // $ANTLR end caseStatement

    public static class defaultStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start defaultStatement
    // ./CFScript.g:284:1: defaultStatement : DEFAULT COLON script ;
    public final defaultStatement_return defaultStatement() throws RecognitionException {
    traceIn("defaultStatement", 27);
        defaultStatement_return retval = new defaultStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEFAULT112=null;
        Token COLON113=null;
        script_return script114 = null;


        Object DEFAULT112_tree=null;
        Object COLON113_tree=null;

        try {
            // ./CFScript.g:286:2: ( DEFAULT COLON script )
            // ./CFScript.g:286:2: DEFAULT COLON script
            {
            root_0 = (Object)adaptor.nil();

            DEFAULT112=(Token)input.LT(1);
            match(input,DEFAULT,FOLLOW_DEFAULT_in_defaultStatement782); 
            DEFAULT112_tree = (Object)adaptor.create(DEFAULT112);
            root_0 = (Object)adaptor.becomeRoot(DEFAULT112_tree, root_0);

            COLON113=(Token)input.LT(1);
            match(input,COLON,FOLLOW_COLON_in_defaultStatement785); 
            COLON113_tree = (Object)adaptor.create(COLON113);
            adaptor.addChild(root_0, COLON113_tree);

            pushFollow(FOLLOW_script_in_defaultStatement788);
            script114=script();
            _fsp--;

            adaptor.addChild(root_0, script114.getTree());

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
    traceOut("defaultStatement", 27);
        }
        return retval;
    }
    // $ANTLR end defaultStatement

    public static class breakStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start breakStatement
    // ./CFScript.g:291:1: breakStatement : BREAK ;
    public final breakStatement_return breakStatement() throws RecognitionException {
    traceIn("breakStatement", 28);
        breakStatement_return retval = new breakStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token BREAK115=null;

        Object BREAK115_tree=null;

        try {
            // ./CFScript.g:293:2: ( BREAK )
            // ./CFScript.g:293:2: BREAK
            {
            root_0 = (Object)adaptor.nil();

            BREAK115=(Token)input.LT(1);
            match(input,BREAK,FOLLOW_BREAK_in_breakStatement801); 
            BREAK115_tree = (Object)adaptor.create(BREAK115);
            adaptor.addChild(root_0, BREAK115_tree);


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
    traceOut("breakStatement", 28);
        }
        return retval;
    }
    // $ANTLR end breakStatement


 

    public static final BitSet FOLLOW_setStatement_in_script72 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_COLON_in_script74 = new BitSet(new long[]{0x000000113A8BD502L});
    public static final BitSet FOLLOW_returnStatement_in_script82 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_COLON_in_script84 = new BitSet(new long[]{0x000000113A8BD502L});
    public static final BitSet FOLLOW_ifStatement_in_script92 = new BitSet(new long[]{0x000000113A8BD502L});
    public static final BitSet FOLLOW_tryStatement_in_script100 = new BitSet(new long[]{0x000000113A8BD502L});
    public static final BitSet FOLLOW_forStatement_in_script108 = new BitSet(new long[]{0x000000113A8BD502L});
    public static final BitSet FOLLOW_whileStatement_in_script116 = new BitSet(new long[]{0x000000113A8BD502L});
    public static final BitSet FOLLOW_doWhileStatement_in_script124 = new BitSet(new long[]{0x000000113A8BD502L});
    public static final BitSet FOLLOW_switchStatement_in_script132 = new BitSet(new long[]{0x000000113A8BD502L});
    public static final BitSet FOLLOW_breakStatement_in_script140 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_COLON_in_script142 = new BitSet(new long[]{0x000000113A8BD502L});
    public static final BitSet FOLLOW_VAR_in_setStatement163 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_setStatement167 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_EQUALS_in_setStatement170 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_setStatement172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_codeStatement190 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_codeStatement192 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_codeStatement194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasicStatement_in_codeStatement202 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RETURN_in_returnStatement218 = new BitSet(new long[]{0x00000000000BC402L});
    public static final BitSet FOLLOW_codeStatement_in_returnStatement222 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlValueStatement_in_cfmlBasicStatement236 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_OPERATOR_in_cfmlBasicStatement239 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_cfmlBasicStatement241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cfmlValueStatement257 = new BitSet(new long[]{0x00000000000B8000L});
    public static final BitSet FOLLOW_cfmlValue_in_cfmlValueStatement261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_cfmlValue274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_cfmlValue278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlLinking_in_cfmlValue282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_cfmlLinking295 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_cfmlBasicLinking_in_cfmlLinking297 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_HASH_in_cfmlLinking299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasicLinking_in_cfmlLinking305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasic_in_cfmlBasicLinking317 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_DOT_in_cfmlBasicLinking320 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_cfmlBasic_in_cfmlBasicLinking322 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_identifier_in_cfmlBasic336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_cfmlBasic340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_identifier352 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_struct_in_identifier355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_SQUARE_in_struct370 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_struct372 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_CLOSE_SQUARE_in_struct374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_function400 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_function402 = new BitSet(new long[]{0x00000000000BCC00L});
    public static final BitSet FOLLOW_argumentStatement_in_function405 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_function409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_codeStatement_in_argumentStatement440 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_COMMA_in_argumentStatement443 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_argumentStatement445 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_IF_in_ifStatement459 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_ifStatement462 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_ifStatement464 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_ifStatement466 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_ifStatement469 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_elseifStatement_in_ifStatement473 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_elseStatement_in_ifStatement479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_elseifStatement494 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_IF_in_elseifStatement496 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_elseifStatement498 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_elseifStatement500 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_elseifStatement502 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_elseifStatement506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_elseStatement550 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_elseStatement554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRY_in_tryStatement566 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_tryStatement570 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_catchStatement_in_tryStatement573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CATCH_in_catchStatement586 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_catchStatement589 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_catchStatement591 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_catchStatement593 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_catchStatement595 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_catchStatement598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_forStatement610 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_forStatement613 = new BitSet(new long[]{0x00000000000BC500L});
    public static final BitSet FOLLOW_forConditions_in_forStatement615 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_forStatement617 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_forStatement620 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_setStatement_in_forConditions630 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_COLON_in_forConditions633 = new BitSet(new long[]{0x00000000000BC500L});
    public static final BitSet FOLLOW_setStatement_in_forConditions636 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_COLON_in_forConditions639 = new BitSet(new long[]{0x00000000000BC500L});
    public static final BitSet FOLLOW_setStatement_in_forConditions642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHILE_in_whileStatement654 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_whileStatement657 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_whileStatement659 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_whileStatement661 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_whileStatement664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DO_in_doWhileStatement676 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_block_in_doWhileStatement680 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_WHILE_in_doWhileStatement683 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_doWhileStatement685 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_doWhileStatement687 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_doWhileStatement689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_CURLY_in_block701 = new BitSet(new long[]{0x00000011BA8BD500L});
    public static final BitSet FOLLOW_script_in_block703 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_CLOSE_CURLY_in_block705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SWITCH_in_switchStatement718 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_switchStatement721 = new BitSet(new long[]{0x00000000000BC400L});
    public static final BitSet FOLLOW_codeStatement_in_switchStatement723 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_switchStatement725 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_OPEN_CURLY_in_switchStatement728 = new BitSet(new long[]{0x0000000A80000000L});
    public static final BitSet FOLLOW_caseStatement_in_switchStatement732 = new BitSet(new long[]{0x0000000A80000000L});
    public static final BitSet FOLLOW_defaultStatement_in_switchStatement738 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_CLOSE_CURLY_in_switchStatement743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CASE_in_caseStatement756 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_set_in_caseStatement759 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_COLON_in_caseStatement767 = new BitSet(new long[]{0x000000113A8BD502L});
    public static final BitSet FOLLOW_script_in_caseStatement770 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DEFAULT_in_defaultStatement782 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_COLON_in_defaultStatement785 = new BitSet(new long[]{0x000000113A8BD502L});
    public static final BitSet FOLLOW_script_in_defaultStatement788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BREAK_in_breakStatement801 = new BitSet(new long[]{0x0000000000000002L});

}