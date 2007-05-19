// $ANTLR 3.0 ./CFScript.g 2007-05-19 17:25:08

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "FUNCTION_CALL", "STRUCT_KEY", "SEMI_COLON", "VAR", "EQUALS", "RETURN", "OPEN_PAREN", "CLOSE_PAREN", "OPERATOR", "NOT", "NUMBER", "STRING", "HASH", "DOT", "IDENTIFIER", "OPEN_SQUARE", "CLOSE_SQUARE", "COMMA", "IF", "OPEN_CURLY", "CLOSE_CURLY", "ELSE", "TRY", "CATCH", "MATH_OPERATOR", "STRING_OPERATOR", "BOOLEAN_OPERATOR", "DIGIT", "LETTER", "WS", "COMMENT", "LINE_COMMENT"
    };
    public static final int LINE_COMMENT=35;
    public static final int LETTER=32;
    public static final int CLOSE_CURLY=24;
    public static final int OPERATOR=12;
    public static final int ELSE=25;
    public static final int NUMBER=14;
    public static final int HASH=16;
    public static final int OPEN_PAREN=10;
    public static final int SEMI_COLON=6;
    public static final int EQUALS=8;
    public static final int OPEN_CURLY=23;
    public static final int NOT=13;
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
    public static final int MATH_OPERATOR=28;
    public static final int CLOSE_PAREN=11;
    public static final int DIGIT=31;
    public static final int COMMENT=34;
    public static final int CATCH=27;
    public static final int DOT=17;
    public static final int CLOSE_SQUARE=20;
    public static final int FUNCTION_CALL=4;
    public static final int STRING_OPERATOR=29;
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


    public static class script_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start script
    // ./CFScript.g:118:1: script : ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement )* ;
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


        Object SEMI_COLON2_tree=null;
        Object SEMI_COLON4_tree=null;

        try {
            // ./CFScript.g:120:2: ( ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement )* )
            // ./CFScript.g:120:2: ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement )*
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:120:2: ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement )*
            loop1:
            do {
                int alt1=4;
                switch ( input.LA(1) ) {
                case VAR:
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

                }

                switch (alt1) {
            	case 1 :
            	    // ./CFScript.g:121:3: setStatement SEMI_COLON
            	    {
            	    pushFollow(FOLLOW_setStatement_in_script70);
            	    setStatement1=setStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, setStatement1.getTree());
            	    SEMI_COLON2=(Token)input.LT(1);
            	    match(input,SEMI_COLON,FOLLOW_SEMI_COLON_in_script72); 
            	    SEMI_COLON2_tree = (Object)adaptor.create(SEMI_COLON2);
            	    adaptor.addChild(root_0, SEMI_COLON2_tree);


            	    }
            	    break;
            	case 2 :
            	    // ./CFScript.g:123:3: returnStatement SEMI_COLON
            	    {
            	    pushFollow(FOLLOW_returnStatement_in_script80);
            	    returnStatement3=returnStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, returnStatement3.getTree());
            	    SEMI_COLON4=(Token)input.LT(1);
            	    match(input,SEMI_COLON,FOLLOW_SEMI_COLON_in_script82); 
            	    SEMI_COLON4_tree = (Object)adaptor.create(SEMI_COLON4);
            	    adaptor.addChild(root_0, SEMI_COLON4_tree);


            	    }
            	    break;
            	case 3 :
            	    // ./CFScript.g:125:3: ifStatement
            	    {
            	    pushFollow(FOLLOW_ifStatement_in_script90);
            	    ifStatement5=ifStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, ifStatement5.getTree());

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
    // ./CFScript.g:129:1: setStatement : ( VAR )? cfmlValue ( EQUALS codeStatement )? ;
    public final setStatement_return setStatement() throws RecognitionException {
    traceIn("setStatement", 2);
        setStatement_return retval = new setStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token VAR6=null;
        Token EQUALS8=null;
        cfmlValue_return cfmlValue7 = null;

        codeStatement_return codeStatement9 = null;


        Object VAR6_tree=null;
        Object EQUALS8_tree=null;

        try {
            // ./CFScript.g:131:2: ( ( VAR )? cfmlValue ( EQUALS codeStatement )? )
            // ./CFScript.g:131:2: ( VAR )? cfmlValue ( EQUALS codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:131:2: ( VAR )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==VAR) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // ./CFScript.g:131:3: VAR
                    {
                    VAR6=(Token)input.LT(1);
                    match(input,VAR,FOLLOW_VAR_in_setStatement108); 
                    VAR6_tree = (Object)adaptor.create(VAR6);
                    adaptor.addChild(root_0, VAR6_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_cfmlValue_in_setStatement112);
            cfmlValue7=cfmlValue();
            _fsp--;

            adaptor.addChild(root_0, cfmlValue7.getTree());
            // ./CFScript.g:131:19: ( EQUALS codeStatement )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==EQUALS) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ./CFScript.g:131:20: EQUALS codeStatement
                    {
                    EQUALS8=(Token)input.LT(1);
                    match(input,EQUALS,FOLLOW_EQUALS_in_setStatement115); 
                    EQUALS8_tree = (Object)adaptor.create(EQUALS8);
                    adaptor.addChild(root_0, EQUALS8_tree);

                    pushFollow(FOLLOW_codeStatement_in_setStatement117);
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
    // ./CFScript.g:134:1: returnStatement : RETURN ( codeStatement )? ;
    public final returnStatement_return returnStatement() throws RecognitionException {
    traceIn("returnStatement", 3);
        returnStatement_return retval = new returnStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token RETURN10=null;
        codeStatement_return codeStatement11 = null;


        Object RETURN10_tree=null;

        try {
            // ./CFScript.g:136:2: ( RETURN ( codeStatement )? )
            // ./CFScript.g:136:2: RETURN ( codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            RETURN10=(Token)input.LT(1);
            match(input,RETURN,FOLLOW_RETURN_in_returnStatement132); 
            RETURN10_tree = (Object)adaptor.create(RETURN10);
            root_0 = (Object)adaptor.becomeRoot(RETURN10_tree, root_0);

            // ./CFScript.g:136:10: ( codeStatement )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==OPEN_PAREN||(LA4_0>=NOT && LA4_0<=HASH)||LA4_0==IDENTIFIER) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ./CFScript.g:136:11: codeStatement
                    {
                    pushFollow(FOLLOW_codeStatement_in_returnStatement136);
                    codeStatement11=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement11.getTree());

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
    // ./CFScript.g:139:1: codeStatement : ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement );
    public final codeStatement_return codeStatement() throws RecognitionException {
    traceIn("codeStatement", 4);
        codeStatement_return retval = new codeStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPEN_PAREN12=null;
        Token CLOSE_PAREN14=null;
        codeStatement_return codeStatement13 = null;

        cfmlBasicStatement_return cfmlBasicStatement15 = null;


        Object OPEN_PAREN12_tree=null;
        Object CLOSE_PAREN14_tree=null;

        try {
            // ./CFScript.g:141:2: ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==OPEN_PAREN) ) {
                alt5=1;
            }
            else if ( ((LA5_0>=NOT && LA5_0<=HASH)||LA5_0==IDENTIFIER) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("139:1: codeStatement : ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement );", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // ./CFScript.g:141:2: OPEN_PAREN codeStatement CLOSE_PAREN
                    {
                    root_0 = (Object)adaptor.nil();

                    OPEN_PAREN12=(Token)input.LT(1);
                    match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_codeStatement150); 
                    OPEN_PAREN12_tree = (Object)adaptor.create(OPEN_PAREN12);
                    adaptor.addChild(root_0, OPEN_PAREN12_tree);

                    pushFollow(FOLLOW_codeStatement_in_codeStatement152);
                    codeStatement13=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement13.getTree());
                    CLOSE_PAREN14=(Token)input.LT(1);
                    match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_codeStatement154); 
                    CLOSE_PAREN14_tree = (Object)adaptor.create(CLOSE_PAREN14);
                    adaptor.addChild(root_0, CLOSE_PAREN14_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:143:2: cfmlBasicStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_cfmlBasicStatement_in_codeStatement160);
                    cfmlBasicStatement15=cfmlBasicStatement();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlBasicStatement15.getTree());

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
    // ./CFScript.g:146:1: cfmlBasicStatement : cfmlValueStatement ( OPERATOR codeStatement )? ;
    public final cfmlBasicStatement_return cfmlBasicStatement() throws RecognitionException {
    traceIn("cfmlBasicStatement", 5);
        cfmlBasicStatement_return retval = new cfmlBasicStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPERATOR17=null;
        cfmlValueStatement_return cfmlValueStatement16 = null;

        codeStatement_return codeStatement18 = null;


        Object OPERATOR17_tree=null;

        try {
            // ./CFScript.g:148:2: ( cfmlValueStatement ( OPERATOR codeStatement )? )
            // ./CFScript.g:148:2: cfmlValueStatement ( OPERATOR codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_cfmlValueStatement_in_cfmlBasicStatement172);
            cfmlValueStatement16=cfmlValueStatement();
            _fsp--;

            adaptor.addChild(root_0, cfmlValueStatement16.getTree());
            // ./CFScript.g:148:21: ( OPERATOR codeStatement )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==OPERATOR) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ./CFScript.g:148:22: OPERATOR codeStatement
                    {
                    OPERATOR17=(Token)input.LT(1);
                    match(input,OPERATOR,FOLLOW_OPERATOR_in_cfmlBasicStatement175); 
                    OPERATOR17_tree = (Object)adaptor.create(OPERATOR17);
                    adaptor.addChild(root_0, OPERATOR17_tree);

                    pushFollow(FOLLOW_codeStatement_in_cfmlBasicStatement177);
                    codeStatement18=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement18.getTree());

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
    // ./CFScript.g:152:1: cfmlValueStatement : ( NOT )? cfmlValue ;
    public final cfmlValueStatement_return cfmlValueStatement() throws RecognitionException {
    traceIn("cfmlValueStatement", 6);
        cfmlValueStatement_return retval = new cfmlValueStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT19=null;
        cfmlValue_return cfmlValue20 = null;


        Object NOT19_tree=null;

        try {
            // ./CFScript.g:154:2: ( ( NOT )? cfmlValue )
            // ./CFScript.g:154:2: ( NOT )? cfmlValue
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:154:2: ( NOT )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==NOT) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ./CFScript.g:154:3: NOT
                    {
                    NOT19=(Token)input.LT(1);
                    match(input,NOT,FOLLOW_NOT_in_cfmlValueStatement193); 
                    NOT19_tree = (Object)adaptor.create(NOT19);
                    adaptor.addChild(root_0, NOT19_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_cfmlValue_in_cfmlValueStatement197);
            cfmlValue20=cfmlValue();
            _fsp--;

            adaptor.addChild(root_0, cfmlValue20.getTree());

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
    // ./CFScript.g:157:1: cfmlValue : ( NUMBER | STRING | cfmlLinking ) ;
    public final cfmlValue_return cfmlValue() throws RecognitionException {
    traceIn("cfmlValue", 7);
        cfmlValue_return retval = new cfmlValue_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUMBER21=null;
        Token STRING22=null;
        cfmlLinking_return cfmlLinking23 = null;


        Object NUMBER21_tree=null;
        Object STRING22_tree=null;

        try {
            // ./CFScript.g:159:2: ( ( NUMBER | STRING | cfmlLinking ) )
            // ./CFScript.g:159:2: ( NUMBER | STRING | cfmlLinking )
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:159:2: ( NUMBER | STRING | cfmlLinking )
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
                    new NoViableAltException("159:2: ( NUMBER | STRING | cfmlLinking )", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // ./CFScript.g:159:3: NUMBER
                    {
                    NUMBER21=(Token)input.LT(1);
                    match(input,NUMBER,FOLLOW_NUMBER_in_cfmlValue210); 
                    NUMBER21_tree = (Object)adaptor.create(NUMBER21);
                    adaptor.addChild(root_0, NUMBER21_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:159:12: STRING
                    {
                    STRING22=(Token)input.LT(1);
                    match(input,STRING,FOLLOW_STRING_in_cfmlValue214); 
                    STRING22_tree = (Object)adaptor.create(STRING22);
                    adaptor.addChild(root_0, STRING22_tree);


                    }
                    break;
                case 3 :
                    // ./CFScript.g:159:21: cfmlLinking
                    {
                    pushFollow(FOLLOW_cfmlLinking_in_cfmlValue218);
                    cfmlLinking23=cfmlLinking();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlLinking23.getTree());

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
    // ./CFScript.g:162:1: cfmlLinking : ( HASH cfmlBasicLinking HASH | cfmlBasicLinking );
    public final cfmlLinking_return cfmlLinking() throws RecognitionException {
    traceIn("cfmlLinking", 8);
        cfmlLinking_return retval = new cfmlLinking_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token HASH24=null;
        Token HASH26=null;
        cfmlBasicLinking_return cfmlBasicLinking25 = null;

        cfmlBasicLinking_return cfmlBasicLinking27 = null;


        Object HASH24_tree=null;
        Object HASH26_tree=null;

        try {
            // ./CFScript.g:164:2: ( HASH cfmlBasicLinking HASH | cfmlBasicLinking )
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
                    new NoViableAltException("162:1: cfmlLinking : ( HASH cfmlBasicLinking HASH | cfmlBasicLinking );", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // ./CFScript.g:164:2: HASH cfmlBasicLinking HASH
                    {
                    root_0 = (Object)adaptor.nil();

                    HASH24=(Token)input.LT(1);
                    match(input,HASH,FOLLOW_HASH_in_cfmlLinking231); 
                    HASH24_tree = (Object)adaptor.create(HASH24);
                    adaptor.addChild(root_0, HASH24_tree);

                    pushFollow(FOLLOW_cfmlBasicLinking_in_cfmlLinking233);
                    cfmlBasicLinking25=cfmlBasicLinking();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlBasicLinking25.getTree());
                    HASH26=(Token)input.LT(1);
                    match(input,HASH,FOLLOW_HASH_in_cfmlLinking235); 
                    HASH26_tree = (Object)adaptor.create(HASH26);
                    adaptor.addChild(root_0, HASH26_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:166:2: cfmlBasicLinking
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_cfmlBasicLinking_in_cfmlLinking241);
                    cfmlBasicLinking27=cfmlBasicLinking();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlBasicLinking27.getTree());

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
    // ./CFScript.g:169:1: cfmlBasicLinking : cfmlBasic ( DOT cfmlBasic )* ;
    public final cfmlBasicLinking_return cfmlBasicLinking() throws RecognitionException {
    traceIn("cfmlBasicLinking", 9);
        cfmlBasicLinking_return retval = new cfmlBasicLinking_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOT29=null;
        cfmlBasic_return cfmlBasic28 = null;

        cfmlBasic_return cfmlBasic30 = null;


        Object DOT29_tree=null;

        try {
            // ./CFScript.g:171:2: ( cfmlBasic ( DOT cfmlBasic )* )
            // ./CFScript.g:171:2: cfmlBasic ( DOT cfmlBasic )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_cfmlBasic_in_cfmlBasicLinking253);
            cfmlBasic28=cfmlBasic();
            _fsp--;

            adaptor.addChild(root_0, cfmlBasic28.getTree());
            // ./CFScript.g:171:12: ( DOT cfmlBasic )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==DOT) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ./CFScript.g:171:13: DOT cfmlBasic
            	    {
            	    DOT29=(Token)input.LT(1);
            	    match(input,DOT,FOLLOW_DOT_in_cfmlBasicLinking256); 
            	    DOT29_tree = (Object)adaptor.create(DOT29);
            	    adaptor.addChild(root_0, DOT29_tree);

            	    pushFollow(FOLLOW_cfmlBasic_in_cfmlBasicLinking258);
            	    cfmlBasic30=cfmlBasic();
            	    _fsp--;

            	    adaptor.addChild(root_0, cfmlBasic30.getTree());

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
    // ./CFScript.g:174:1: cfmlBasic : ( identifier | function );
    public final cfmlBasic_return cfmlBasic() throws RecognitionException {
    traceIn("cfmlBasic", 10);
        cfmlBasic_return retval = new cfmlBasic_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        identifier_return identifier31 = null;

        function_return function32 = null;



        try {
            // ./CFScript.g:176:2: ( identifier | function )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==IDENTIFIER) ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1==OPEN_PAREN) ) {
                    alt11=2;
                }
                else if ( (LA11_1==SEMI_COLON||LA11_1==EQUALS||(LA11_1>=CLOSE_PAREN && LA11_1<=OPERATOR)||(LA11_1>=HASH && LA11_1<=DOT)||(LA11_1>=OPEN_SQUARE && LA11_1<=COMMA)) ) {
                    alt11=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("174:1: cfmlBasic : ( identifier | function );", 11, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("174:1: cfmlBasic : ( identifier | function );", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // ./CFScript.g:176:2: identifier
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_identifier_in_cfmlBasic272);
                    identifier31=identifier();
                    _fsp--;

                    adaptor.addChild(root_0, identifier31.getTree());

                    }
                    break;
                case 2 :
                    // ./CFScript.g:176:15: function
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_in_cfmlBasic276);
                    function32=function();
                    _fsp--;

                    adaptor.addChild(root_0, function32.getTree());

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
    // ./CFScript.g:179:1: identifier : IDENTIFIER ( struct )? ;
    public final identifier_return identifier() throws RecognitionException {
    traceIn("identifier", 11);
        identifier_return retval = new identifier_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENTIFIER33=null;
        struct_return struct34 = null;


        Object IDENTIFIER33_tree=null;

        try {
            // ./CFScript.g:181:2: ( IDENTIFIER ( struct )? )
            // ./CFScript.g:181:2: IDENTIFIER ( struct )?
            {
            root_0 = (Object)adaptor.nil();

            IDENTIFIER33=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_identifier288); 
            IDENTIFIER33_tree = (Object)adaptor.create(IDENTIFIER33);
            adaptor.addChild(root_0, IDENTIFIER33_tree);

            // ./CFScript.g:181:13: ( struct )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==OPEN_SQUARE) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ./CFScript.g:181:14: struct
                    {
                    pushFollow(FOLLOW_struct_in_identifier291);
                    struct34=struct();
                    _fsp--;

                    adaptor.addChild(root_0, struct34.getTree());

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
    // ./CFScript.g:184:1: struct : OPEN_SQUARE codeStatement CLOSE_SQUARE -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE ) ;
    public final struct_return struct() throws RecognitionException {
    traceIn("struct", 12);
        struct_return retval = new struct_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPEN_SQUARE35=null;
        Token CLOSE_SQUARE37=null;
        codeStatement_return codeStatement36 = null;


        Object OPEN_SQUARE35_tree=null;
        Object CLOSE_SQUARE37_tree=null;
        RewriteRuleTokenStream stream_OPEN_SQUARE=new RewriteRuleTokenStream(adaptor,"token OPEN_SQUARE");
        RewriteRuleTokenStream stream_CLOSE_SQUARE=new RewriteRuleTokenStream(adaptor,"token CLOSE_SQUARE");
        RewriteRuleSubtreeStream stream_codeStatement=new RewriteRuleSubtreeStream(adaptor,"rule codeStatement");
        try {
            // ./CFScript.g:186:2: ( OPEN_SQUARE codeStatement CLOSE_SQUARE -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE ) )
            // ./CFScript.g:186:2: OPEN_SQUARE codeStatement CLOSE_SQUARE
            {
            OPEN_SQUARE35=(Token)input.LT(1);
            match(input,OPEN_SQUARE,FOLLOW_OPEN_SQUARE_in_struct306); 
            stream_OPEN_SQUARE.add(OPEN_SQUARE35);

            pushFollow(FOLLOW_codeStatement_in_struct308);
            codeStatement36=codeStatement();
            _fsp--;

            stream_codeStatement.add(codeStatement36.getTree());
            CLOSE_SQUARE37=(Token)input.LT(1);
            match(input,CLOSE_SQUARE,FOLLOW_CLOSE_SQUARE_in_struct310); 
            stream_CLOSE_SQUARE.add(CLOSE_SQUARE37);


            // AST REWRITE
            // elements: CLOSE_SQUARE, codeStatement, OPEN_SQUARE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 187:2: -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE )
            {
                // ./CFScript.g:187:5: ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE )
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
    // ./CFScript.g:190:1: function : IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN ) ;
    public final function_return function() throws RecognitionException {
    traceIn("function", 13);
        function_return retval = new function_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENTIFIER38=null;
        Token OPEN_PAREN39=null;
        Token CLOSE_PAREN41=null;
        argumentStatement_return argumentStatement40 = null;


        Object IDENTIFIER38_tree=null;
        Object OPEN_PAREN39_tree=null;
        Object CLOSE_PAREN41_tree=null;
        RewriteRuleTokenStream stream_OPEN_PAREN=new RewriteRuleTokenStream(adaptor,"token OPEN_PAREN");
        RewriteRuleTokenStream stream_CLOSE_PAREN=new RewriteRuleTokenStream(adaptor,"token CLOSE_PAREN");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleSubtreeStream stream_argumentStatement=new RewriteRuleSubtreeStream(adaptor,"rule argumentStatement");
        try {
            // ./CFScript.g:192:2: ( IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN ) )
            // ./CFScript.g:192:2: IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN
            {
            IDENTIFIER38=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_function336); 
            stream_IDENTIFIER.add(IDENTIFIER38);

            OPEN_PAREN39=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_function338); 
            stream_OPEN_PAREN.add(OPEN_PAREN39);

            // ./CFScript.g:192:24: ( argumentStatement )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==OPEN_PAREN||(LA13_0>=NOT && LA13_0<=HASH)||LA13_0==IDENTIFIER) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ./CFScript.g:192:25: argumentStatement
                    {
                    pushFollow(FOLLOW_argumentStatement_in_function341);
                    argumentStatement40=argumentStatement();
                    _fsp--;

                    stream_argumentStatement.add(argumentStatement40.getTree());

                    }
                    break;

            }

            CLOSE_PAREN41=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_function345); 
            stream_CLOSE_PAREN.add(CLOSE_PAREN41);


            // AST REWRITE
            // elements: argumentStatement, IDENTIFIER, OPEN_PAREN, CLOSE_PAREN
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 193:2: -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN )
            {
                // ./CFScript.g:193:5: ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(FUNCTION_CALL, "FUNCTION_CALL"), root_1);

                adaptor.addChild(root_1, stream_IDENTIFIER.next());
                adaptor.addChild(root_1, stream_OPEN_PAREN.next());
                // ./CFScript.g:193:43: ( argumentStatement )?
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
    // ./CFScript.g:196:1: argumentStatement : codeStatement ( COMMA codeStatement )* ;
    public final argumentStatement_return argumentStatement() throws RecognitionException {
    traceIn("argumentStatement", 14);
        argumentStatement_return retval = new argumentStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA43=null;
        codeStatement_return codeStatement42 = null;

        codeStatement_return codeStatement44 = null;


        Object COMMA43_tree=null;

        try {
            // ./CFScript.g:198:2: ( codeStatement ( COMMA codeStatement )* )
            // ./CFScript.g:198:2: codeStatement ( COMMA codeStatement )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_codeStatement_in_argumentStatement376);
            codeStatement42=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement42.getTree());
            // ./CFScript.g:198:16: ( COMMA codeStatement )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==COMMA) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ./CFScript.g:198:17: COMMA codeStatement
            	    {
            	    COMMA43=(Token)input.LT(1);
            	    match(input,COMMA,FOLLOW_COMMA_in_argumentStatement379); 
            	    COMMA43_tree = (Object)adaptor.create(COMMA43);
            	    adaptor.addChild(root_0, COMMA43_tree);

            	    pushFollow(FOLLOW_codeStatement_in_argumentStatement381);
            	    codeStatement44=codeStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, codeStatement44.getTree());

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
    // ./CFScript.g:201:1: ifStatement : IF OPEN_PAREN codeStatement CLOSE_PAREN block ;
    public final ifStatement_return ifStatement() throws RecognitionException {
    traceIn("ifStatement", 15);
        ifStatement_return retval = new ifStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IF45=null;
        Token OPEN_PAREN46=null;
        Token CLOSE_PAREN48=null;
        codeStatement_return codeStatement47 = null;

        block_return block49 = null;


        Object IF45_tree=null;
        Object OPEN_PAREN46_tree=null;
        Object CLOSE_PAREN48_tree=null;

        try {
            // ./CFScript.g:203:2: ( IF OPEN_PAREN codeStatement CLOSE_PAREN block )
            // ./CFScript.g:203:2: IF OPEN_PAREN codeStatement CLOSE_PAREN block
            {
            root_0 = (Object)adaptor.nil();

            IF45=(Token)input.LT(1);
            match(input,IF,FOLLOW_IF_in_ifStatement395); 
            IF45_tree = (Object)adaptor.create(IF45);
            adaptor.addChild(root_0, IF45_tree);

            OPEN_PAREN46=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_ifStatement397); 
            OPEN_PAREN46_tree = (Object)adaptor.create(OPEN_PAREN46);
            adaptor.addChild(root_0, OPEN_PAREN46_tree);

            pushFollow(FOLLOW_codeStatement_in_ifStatement399);
            codeStatement47=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement47.getTree());
            CLOSE_PAREN48=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_ifStatement401); 
            CLOSE_PAREN48_tree = (Object)adaptor.create(CLOSE_PAREN48);
            adaptor.addChild(root_0, CLOSE_PAREN48_tree);

            pushFollow(FOLLOW_block_in_ifStatement404);
            block49=block();
            _fsp--;

            adaptor.addChild(root_0, block49.getTree());

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

    public static class block_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start block
    // ./CFScript.g:207:1: block : OPEN_CURLY script CLOSE_CURLY ;
    public final block_return block() throws RecognitionException {
    traceIn("block", 16);
        block_return retval = new block_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPEN_CURLY50=null;
        Token CLOSE_CURLY52=null;
        script_return script51 = null;


        Object OPEN_CURLY50_tree=null;
        Object CLOSE_CURLY52_tree=null;

        try {
            // ./CFScript.g:209:2: ( OPEN_CURLY script CLOSE_CURLY )
            // ./CFScript.g:209:2: OPEN_CURLY script CLOSE_CURLY
            {
            root_0 = (Object)adaptor.nil();

            OPEN_CURLY50=(Token)input.LT(1);
            match(input,OPEN_CURLY,FOLLOW_OPEN_CURLY_in_block416); 
            OPEN_CURLY50_tree = (Object)adaptor.create(OPEN_CURLY50);
            adaptor.addChild(root_0, OPEN_CURLY50_tree);

            pushFollow(FOLLOW_script_in_block418);
            script51=script();
            _fsp--;

            adaptor.addChild(root_0, script51.getTree());
            CLOSE_CURLY52=(Token)input.LT(1);
            match(input,CLOSE_CURLY,FOLLOW_CLOSE_CURLY_in_block420); 
            CLOSE_CURLY52_tree = (Object)adaptor.create(CLOSE_CURLY52);
            adaptor.addChild(root_0, CLOSE_CURLY52_tree);


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
    traceOut("block", 16);
        }
        return retval;
    }
    // $ANTLR end block


 

    public static final BitSet FOLLOW_setStatement_in_script70 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SEMI_COLON_in_script72 = new BitSet(new long[]{0x000000000045C282L});
    public static final BitSet FOLLOW_returnStatement_in_script80 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SEMI_COLON_in_script82 = new BitSet(new long[]{0x000000000045C282L});
    public static final BitSet FOLLOW_ifStatement_in_script90 = new BitSet(new long[]{0x000000000045C282L});
    public static final BitSet FOLLOW_VAR_in_setStatement108 = new BitSet(new long[]{0x000000000005C000L});
    public static final BitSet FOLLOW_cfmlValue_in_setStatement112 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_EQUALS_in_setStatement115 = new BitSet(new long[]{0x000000000005E400L});
    public static final BitSet FOLLOW_codeStatement_in_setStatement117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RETURN_in_returnStatement132 = new BitSet(new long[]{0x000000000005E402L});
    public static final BitSet FOLLOW_codeStatement_in_returnStatement136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_codeStatement150 = new BitSet(new long[]{0x000000000005E400L});
    public static final BitSet FOLLOW_codeStatement_in_codeStatement152 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_codeStatement154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasicStatement_in_codeStatement160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlValueStatement_in_cfmlBasicStatement172 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_OPERATOR_in_cfmlBasicStatement175 = new BitSet(new long[]{0x000000000005E400L});
    public static final BitSet FOLLOW_codeStatement_in_cfmlBasicStatement177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cfmlValueStatement193 = new BitSet(new long[]{0x000000000005C000L});
    public static final BitSet FOLLOW_cfmlValue_in_cfmlValueStatement197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_cfmlValue210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_cfmlValue214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlLinking_in_cfmlValue218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_cfmlLinking231 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_cfmlBasicLinking_in_cfmlLinking233 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_HASH_in_cfmlLinking235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasicLinking_in_cfmlLinking241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasic_in_cfmlBasicLinking253 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_DOT_in_cfmlBasicLinking256 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_cfmlBasic_in_cfmlBasicLinking258 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_identifier_in_cfmlBasic272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_cfmlBasic276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_identifier288 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_struct_in_identifier291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_SQUARE_in_struct306 = new BitSet(new long[]{0x000000000005E400L});
    public static final BitSet FOLLOW_codeStatement_in_struct308 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_CLOSE_SQUARE_in_struct310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_function336 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_function338 = new BitSet(new long[]{0x000000000005EC00L});
    public static final BitSet FOLLOW_argumentStatement_in_function341 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_function345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_codeStatement_in_argumentStatement376 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_COMMA_in_argumentStatement379 = new BitSet(new long[]{0x000000000005E400L});
    public static final BitSet FOLLOW_codeStatement_in_argumentStatement381 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_IF_in_ifStatement395 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_ifStatement397 = new BitSet(new long[]{0x000000000005E400L});
    public static final BitSet FOLLOW_codeStatement_in_ifStatement399 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_ifStatement401 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_block_in_ifStatement404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_CURLY_in_block416 = new BitSet(new long[]{0x000000000145C280L});
    public static final BitSet FOLLOW_script_in_block418 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_CLOSE_CURLY_in_block420 = new BitSet(new long[]{0x0000000000000002L});

}