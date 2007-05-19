// $ANTLR 3.0 ./CFScript.g 2007-05-19 23:11:15

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "FUNCTION_CALL", "STRUCT_KEY", "ELSEIF", "SEMI_COLON", "VAR", "EQUALS", "RETURN", "OPEN_PAREN", "CLOSE_PAREN", "OPERATOR", "NOT", "NUMBER", "STRING", "HASH", "DOT", "IDENTIFIER", "OPEN_SQUARE", "CLOSE_SQUARE", "COMMA", "IF", "ELSE", "OPEN_CURLY", "CLOSE_CURLY", "TRY", "CATCH", "MATH_OPERATOR", "STRING_OPERATOR", "BOOLEAN_OPERATOR", "DIGIT", "LETTER", "WS", "COMMENT", "LINE_COMMENT"
    };
    public static final int LINE_COMMENT=36;
    public static final int LETTER=33;
    public static final int CLOSE_CURLY=26;
    public static final int OPERATOR=13;
    public static final int ELSE=24;
    public static final int NUMBER=15;
    public static final int HASH=17;
    public static final int OPEN_PAREN=11;
    public static final int SEMI_COLON=7;
    public static final int EQUALS=9;
    public static final int OPEN_CURLY=25;
    public static final int NOT=14;
    public static final int EOF=-1;
    public static final int TRY=27;
    public static final int IF=23;
    public static final int ELSEIF=6;
    public static final int WS=34;
    public static final int COMMA=22;
    public static final int BOOLEAN_OPERATOR=31;
    public static final int IDENTIFIER=19;
    public static final int RETURN=10;
    public static final int OPEN_SQUARE=20;
    public static final int VAR=8;
    public static final int MATH_OPERATOR=29;
    public static final int CLOSE_PAREN=12;
    public static final int DIGIT=32;
    public static final int COMMENT=35;
    public static final int CATCH=28;
    public static final int DOT=18;
    public static final int CLOSE_SQUARE=21;
    public static final int FUNCTION_CALL=4;
    public static final int STRING_OPERATOR=30;
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
    // ./CFScript.g:94:1: script : ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement )* ;
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
            // ./CFScript.g:96:2: ( ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement )* )
            // ./CFScript.g:96:2: ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement )*
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:96:2: ( setStatement SEMI_COLON | returnStatement SEMI_COLON | ifStatement )*
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
    // ./CFScript.g:105:1: setStatement : ( VAR )? cfmlValue ( EQUALS codeStatement )? ;
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
            // ./CFScript.g:107:2: ( ( VAR )? cfmlValue ( EQUALS codeStatement )? )
            // ./CFScript.g:107:2: ( VAR )? cfmlValue ( EQUALS codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:107:2: ( VAR )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==VAR) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // ./CFScript.g:107:3: VAR
                    {
                    VAR6=(Token)input.LT(1);
                    match(input,VAR,FOLLOW_VAR_in_setStatement110); 
                    VAR6_tree = (Object)adaptor.create(VAR6);
                    adaptor.addChild(root_0, VAR6_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_cfmlValue_in_setStatement114);
            cfmlValue7=cfmlValue();
            _fsp--;

            adaptor.addChild(root_0, cfmlValue7.getTree());
            // ./CFScript.g:107:19: ( EQUALS codeStatement )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==EQUALS) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ./CFScript.g:107:20: EQUALS codeStatement
                    {
                    EQUALS8=(Token)input.LT(1);
                    match(input,EQUALS,FOLLOW_EQUALS_in_setStatement117); 
                    EQUALS8_tree = (Object)adaptor.create(EQUALS8);
                    adaptor.addChild(root_0, EQUALS8_tree);

                    pushFollow(FOLLOW_codeStatement_in_setStatement119);
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
    // ./CFScript.g:110:1: returnStatement : RETURN ( codeStatement )? ;
    public final returnStatement_return returnStatement() throws RecognitionException {
    traceIn("returnStatement", 3);
        returnStatement_return retval = new returnStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token RETURN10=null;
        codeStatement_return codeStatement11 = null;


        Object RETURN10_tree=null;

        try {
            // ./CFScript.g:112:2: ( RETURN ( codeStatement )? )
            // ./CFScript.g:112:2: RETURN ( codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            RETURN10=(Token)input.LT(1);
            match(input,RETURN,FOLLOW_RETURN_in_returnStatement134); 
            RETURN10_tree = (Object)adaptor.create(RETURN10);
            root_0 = (Object)adaptor.becomeRoot(RETURN10_tree, root_0);

            // ./CFScript.g:112:10: ( codeStatement )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==OPEN_PAREN||(LA4_0>=NOT && LA4_0<=HASH)||LA4_0==IDENTIFIER) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ./CFScript.g:112:11: codeStatement
                    {
                    pushFollow(FOLLOW_codeStatement_in_returnStatement138);
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
    // ./CFScript.g:115:1: codeStatement : ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement );
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
            // ./CFScript.g:117:2: ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement )
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
                    new NoViableAltException("115:1: codeStatement : ( OPEN_PAREN codeStatement CLOSE_PAREN | cfmlBasicStatement );", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // ./CFScript.g:117:2: OPEN_PAREN codeStatement CLOSE_PAREN
                    {
                    root_0 = (Object)adaptor.nil();

                    OPEN_PAREN12=(Token)input.LT(1);
                    match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_codeStatement152); 
                    OPEN_PAREN12_tree = (Object)adaptor.create(OPEN_PAREN12);
                    adaptor.addChild(root_0, OPEN_PAREN12_tree);

                    pushFollow(FOLLOW_codeStatement_in_codeStatement154);
                    codeStatement13=codeStatement();
                    _fsp--;

                    adaptor.addChild(root_0, codeStatement13.getTree());
                    CLOSE_PAREN14=(Token)input.LT(1);
                    match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_codeStatement156); 
                    CLOSE_PAREN14_tree = (Object)adaptor.create(CLOSE_PAREN14);
                    adaptor.addChild(root_0, CLOSE_PAREN14_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:119:2: cfmlBasicStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_cfmlBasicStatement_in_codeStatement162);
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
    // ./CFScript.g:122:1: cfmlBasicStatement : cfmlValueStatement ( OPERATOR codeStatement )? ;
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
            // ./CFScript.g:124:2: ( cfmlValueStatement ( OPERATOR codeStatement )? )
            // ./CFScript.g:124:2: cfmlValueStatement ( OPERATOR codeStatement )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_cfmlValueStatement_in_cfmlBasicStatement174);
            cfmlValueStatement16=cfmlValueStatement();
            _fsp--;

            adaptor.addChild(root_0, cfmlValueStatement16.getTree());
            // ./CFScript.g:124:21: ( OPERATOR codeStatement )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==OPERATOR) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ./CFScript.g:124:22: OPERATOR codeStatement
                    {
                    OPERATOR17=(Token)input.LT(1);
                    match(input,OPERATOR,FOLLOW_OPERATOR_in_cfmlBasicStatement177); 
                    OPERATOR17_tree = (Object)adaptor.create(OPERATOR17);
                    adaptor.addChild(root_0, OPERATOR17_tree);

                    pushFollow(FOLLOW_codeStatement_in_cfmlBasicStatement179);
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
    // ./CFScript.g:128:1: cfmlValueStatement : ( NOT )? cfmlValue ;
    public final cfmlValueStatement_return cfmlValueStatement() throws RecognitionException {
    traceIn("cfmlValueStatement", 6);
        cfmlValueStatement_return retval = new cfmlValueStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT19=null;
        cfmlValue_return cfmlValue20 = null;


        Object NOT19_tree=null;

        try {
            // ./CFScript.g:130:2: ( ( NOT )? cfmlValue )
            // ./CFScript.g:130:2: ( NOT )? cfmlValue
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:130:2: ( NOT )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==NOT) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ./CFScript.g:130:3: NOT
                    {
                    NOT19=(Token)input.LT(1);
                    match(input,NOT,FOLLOW_NOT_in_cfmlValueStatement195); 
                    NOT19_tree = (Object)adaptor.create(NOT19);
                    adaptor.addChild(root_0, NOT19_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_cfmlValue_in_cfmlValueStatement199);
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
    // ./CFScript.g:133:1: cfmlValue : ( NUMBER | STRING | cfmlLinking ) ;
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
            // ./CFScript.g:135:2: ( ( NUMBER | STRING | cfmlLinking ) )
            // ./CFScript.g:135:2: ( NUMBER | STRING | cfmlLinking )
            {
            root_0 = (Object)adaptor.nil();

            // ./CFScript.g:135:2: ( NUMBER | STRING | cfmlLinking )
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
                    new NoViableAltException("135:2: ( NUMBER | STRING | cfmlLinking )", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // ./CFScript.g:135:3: NUMBER
                    {
                    NUMBER21=(Token)input.LT(1);
                    match(input,NUMBER,FOLLOW_NUMBER_in_cfmlValue212); 
                    NUMBER21_tree = (Object)adaptor.create(NUMBER21);
                    adaptor.addChild(root_0, NUMBER21_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:135:12: STRING
                    {
                    STRING22=(Token)input.LT(1);
                    match(input,STRING,FOLLOW_STRING_in_cfmlValue216); 
                    STRING22_tree = (Object)adaptor.create(STRING22);
                    adaptor.addChild(root_0, STRING22_tree);


                    }
                    break;
                case 3 :
                    // ./CFScript.g:135:21: cfmlLinking
                    {
                    pushFollow(FOLLOW_cfmlLinking_in_cfmlValue220);
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
    // ./CFScript.g:138:1: cfmlLinking : ( HASH cfmlBasicLinking HASH | cfmlBasicLinking );
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
            // ./CFScript.g:140:2: ( HASH cfmlBasicLinking HASH | cfmlBasicLinking )
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
                    new NoViableAltException("138:1: cfmlLinking : ( HASH cfmlBasicLinking HASH | cfmlBasicLinking );", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // ./CFScript.g:140:2: HASH cfmlBasicLinking HASH
                    {
                    root_0 = (Object)adaptor.nil();

                    HASH24=(Token)input.LT(1);
                    match(input,HASH,FOLLOW_HASH_in_cfmlLinking233); 
                    HASH24_tree = (Object)adaptor.create(HASH24);
                    adaptor.addChild(root_0, HASH24_tree);

                    pushFollow(FOLLOW_cfmlBasicLinking_in_cfmlLinking235);
                    cfmlBasicLinking25=cfmlBasicLinking();
                    _fsp--;

                    adaptor.addChild(root_0, cfmlBasicLinking25.getTree());
                    HASH26=(Token)input.LT(1);
                    match(input,HASH,FOLLOW_HASH_in_cfmlLinking237); 
                    HASH26_tree = (Object)adaptor.create(HASH26);
                    adaptor.addChild(root_0, HASH26_tree);


                    }
                    break;
                case 2 :
                    // ./CFScript.g:142:2: cfmlBasicLinking
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_cfmlBasicLinking_in_cfmlLinking243);
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
    // ./CFScript.g:145:1: cfmlBasicLinking : cfmlBasic ( DOT cfmlBasic )* ;
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
            // ./CFScript.g:147:2: ( cfmlBasic ( DOT cfmlBasic )* )
            // ./CFScript.g:147:2: cfmlBasic ( DOT cfmlBasic )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_cfmlBasic_in_cfmlBasicLinking255);
            cfmlBasic28=cfmlBasic();
            _fsp--;

            adaptor.addChild(root_0, cfmlBasic28.getTree());
            // ./CFScript.g:147:12: ( DOT cfmlBasic )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==DOT) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ./CFScript.g:147:13: DOT cfmlBasic
            	    {
            	    DOT29=(Token)input.LT(1);
            	    match(input,DOT,FOLLOW_DOT_in_cfmlBasicLinking258); 
            	    DOT29_tree = (Object)adaptor.create(DOT29);
            	    adaptor.addChild(root_0, DOT29_tree);

            	    pushFollow(FOLLOW_cfmlBasic_in_cfmlBasicLinking260);
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
    // ./CFScript.g:150:1: cfmlBasic : ( identifier | function );
    public final cfmlBasic_return cfmlBasic() throws RecognitionException {
    traceIn("cfmlBasic", 10);
        cfmlBasic_return retval = new cfmlBasic_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        identifier_return identifier31 = null;

        function_return function32 = null;



        try {
            // ./CFScript.g:152:2: ( identifier | function )
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
                        new NoViableAltException("150:1: cfmlBasic : ( identifier | function );", 11, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("150:1: cfmlBasic : ( identifier | function );", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // ./CFScript.g:152:2: identifier
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_identifier_in_cfmlBasic274);
                    identifier31=identifier();
                    _fsp--;

                    adaptor.addChild(root_0, identifier31.getTree());

                    }
                    break;
                case 2 :
                    // ./CFScript.g:152:15: function
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_in_cfmlBasic278);
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
    // ./CFScript.g:155:1: identifier : IDENTIFIER ( struct )? ;
    public final identifier_return identifier() throws RecognitionException {
    traceIn("identifier", 11);
        identifier_return retval = new identifier_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENTIFIER33=null;
        struct_return struct34 = null;


        Object IDENTIFIER33_tree=null;

        try {
            // ./CFScript.g:157:2: ( IDENTIFIER ( struct )? )
            // ./CFScript.g:157:2: IDENTIFIER ( struct )?
            {
            root_0 = (Object)adaptor.nil();

            IDENTIFIER33=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_identifier290); 
            IDENTIFIER33_tree = (Object)adaptor.create(IDENTIFIER33);
            adaptor.addChild(root_0, IDENTIFIER33_tree);

            // ./CFScript.g:157:13: ( struct )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==OPEN_SQUARE) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ./CFScript.g:157:14: struct
                    {
                    pushFollow(FOLLOW_struct_in_identifier293);
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
    // ./CFScript.g:160:1: struct : OPEN_SQUARE codeStatement CLOSE_SQUARE -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE ) ;
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
            // ./CFScript.g:162:2: ( OPEN_SQUARE codeStatement CLOSE_SQUARE -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE ) )
            // ./CFScript.g:162:2: OPEN_SQUARE codeStatement CLOSE_SQUARE
            {
            OPEN_SQUARE35=(Token)input.LT(1);
            match(input,OPEN_SQUARE,FOLLOW_OPEN_SQUARE_in_struct308); 
            stream_OPEN_SQUARE.add(OPEN_SQUARE35);

            pushFollow(FOLLOW_codeStatement_in_struct310);
            codeStatement36=codeStatement();
            _fsp--;

            stream_codeStatement.add(codeStatement36.getTree());
            CLOSE_SQUARE37=(Token)input.LT(1);
            match(input,CLOSE_SQUARE,FOLLOW_CLOSE_SQUARE_in_struct312); 
            stream_CLOSE_SQUARE.add(CLOSE_SQUARE37);


            // AST REWRITE
            // elements: codeStatement, OPEN_SQUARE, CLOSE_SQUARE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 163:2: -> ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE )
            {
                // ./CFScript.g:163:5: ^( STRUCT_KEY OPEN_SQUARE codeStatement CLOSE_SQUARE )
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
    // ./CFScript.g:166:1: function : IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN ) ;
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
            // ./CFScript.g:168:2: ( IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN ) )
            // ./CFScript.g:168:2: IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN
            {
            IDENTIFIER38=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_function338); 
            stream_IDENTIFIER.add(IDENTIFIER38);

            OPEN_PAREN39=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_function340); 
            stream_OPEN_PAREN.add(OPEN_PAREN39);

            // ./CFScript.g:168:24: ( argumentStatement )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==OPEN_PAREN||(LA13_0>=NOT && LA13_0<=HASH)||LA13_0==IDENTIFIER) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ./CFScript.g:168:25: argumentStatement
                    {
                    pushFollow(FOLLOW_argumentStatement_in_function343);
                    argumentStatement40=argumentStatement();
                    _fsp--;

                    stream_argumentStatement.add(argumentStatement40.getTree());

                    }
                    break;

            }

            CLOSE_PAREN41=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_function347); 
            stream_CLOSE_PAREN.add(CLOSE_PAREN41);


            // AST REWRITE
            // elements: OPEN_PAREN, IDENTIFIER, argumentStatement, CLOSE_PAREN
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 169:2: -> ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN )
            {
                // ./CFScript.g:169:5: ^( FUNCTION_CALL IDENTIFIER OPEN_PAREN ( argumentStatement )? CLOSE_PAREN )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(FUNCTION_CALL, "FUNCTION_CALL"), root_1);

                adaptor.addChild(root_1, stream_IDENTIFIER.next());
                adaptor.addChild(root_1, stream_OPEN_PAREN.next());
                // ./CFScript.g:169:43: ( argumentStatement )?
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
    // ./CFScript.g:172:1: argumentStatement : codeStatement ( COMMA codeStatement )* ;
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
            // ./CFScript.g:174:2: ( codeStatement ( COMMA codeStatement )* )
            // ./CFScript.g:174:2: codeStatement ( COMMA codeStatement )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_codeStatement_in_argumentStatement378);
            codeStatement42=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement42.getTree());
            // ./CFScript.g:174:16: ( COMMA codeStatement )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==COMMA) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ./CFScript.g:174:17: COMMA codeStatement
            	    {
            	    COMMA43=(Token)input.LT(1);
            	    match(input,COMMA,FOLLOW_COMMA_in_argumentStatement381); 
            	    COMMA43_tree = (Object)adaptor.create(COMMA43);
            	    adaptor.addChild(root_0, COMMA43_tree);

            	    pushFollow(FOLLOW_codeStatement_in_argumentStatement383);
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
    // ./CFScript.g:177:1: ifStatement : IF OPEN_PAREN codeStatement CLOSE_PAREN block ( elseifStatement )* ( elseStatement )? ;
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

        elseifStatement_return elseifStatement50 = null;

        elseStatement_return elseStatement51 = null;


        Object IF45_tree=null;
        Object OPEN_PAREN46_tree=null;
        Object CLOSE_PAREN48_tree=null;

        try {
            // ./CFScript.g:179:2: ( IF OPEN_PAREN codeStatement CLOSE_PAREN block ( elseifStatement )* ( elseStatement )? )
            // ./CFScript.g:179:2: IF OPEN_PAREN codeStatement CLOSE_PAREN block ( elseifStatement )* ( elseStatement )?
            {
            root_0 = (Object)adaptor.nil();

            IF45=(Token)input.LT(1);
            match(input,IF,FOLLOW_IF_in_ifStatement397); 
            IF45_tree = (Object)adaptor.create(IF45);
            root_0 = (Object)adaptor.becomeRoot(IF45_tree, root_0);

            OPEN_PAREN46=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_ifStatement400); 
            OPEN_PAREN46_tree = (Object)adaptor.create(OPEN_PAREN46);
            adaptor.addChild(root_0, OPEN_PAREN46_tree);

            pushFollow(FOLLOW_codeStatement_in_ifStatement402);
            codeStatement47=codeStatement();
            _fsp--;

            adaptor.addChild(root_0, codeStatement47.getTree());
            CLOSE_PAREN48=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_ifStatement404); 
            CLOSE_PAREN48_tree = (Object)adaptor.create(CLOSE_PAREN48);
            adaptor.addChild(root_0, CLOSE_PAREN48_tree);

            pushFollow(FOLLOW_block_in_ifStatement407);
            block49=block();
            _fsp--;

            adaptor.addChild(root_0, block49.getTree());
            // ./CFScript.g:181:2: ( elseifStatement )*
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
            	    // ./CFScript.g:181:3: elseifStatement
            	    {
            	    pushFollow(FOLLOW_elseifStatement_in_ifStatement411);
            	    elseifStatement50=elseifStatement();
            	    _fsp--;

            	    adaptor.addChild(root_0, elseifStatement50.getTree());

            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

            // ./CFScript.g:182:2: ( elseStatement )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==ELSE) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // ./CFScript.g:182:3: elseStatement
                    {
                    pushFollow(FOLLOW_elseStatement_in_ifStatement417);
                    elseStatement51=elseStatement();
                    _fsp--;

                    adaptor.addChild(root_0, elseStatement51.getTree());

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
    // ./CFScript.g:185:1: elseifStatement : ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block -> ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block ) ;
    public final elseifStatement_return elseifStatement() throws RecognitionException {
    traceIn("elseifStatement", 16);
        elseifStatement_return retval = new elseifStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ELSE52=null;
        Token IF53=null;
        Token OPEN_PAREN54=null;
        Token CLOSE_PAREN56=null;
        codeStatement_return codeStatement55 = null;

        block_return block57 = null;


        Object ELSE52_tree=null;
        Object IF53_tree=null;
        Object OPEN_PAREN54_tree=null;
        Object CLOSE_PAREN56_tree=null;
        RewriteRuleTokenStream stream_OPEN_PAREN=new RewriteRuleTokenStream(adaptor,"token OPEN_PAREN");
        RewriteRuleTokenStream stream_CLOSE_PAREN=new RewriteRuleTokenStream(adaptor,"token CLOSE_PAREN");
        RewriteRuleTokenStream stream_ELSE=new RewriteRuleTokenStream(adaptor,"token ELSE");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_codeStatement=new RewriteRuleSubtreeStream(adaptor,"rule codeStatement");
        RewriteRuleSubtreeStream stream_block=new RewriteRuleSubtreeStream(adaptor,"rule block");
        try {
            // ./CFScript.g:187:2: ( ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block -> ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block ) )
            // ./CFScript.g:187:2: ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block
            {
            ELSE52=(Token)input.LT(1);
            match(input,ELSE,FOLLOW_ELSE_in_elseifStatement432); 
            stream_ELSE.add(ELSE52);

            IF53=(Token)input.LT(1);
            match(input,IF,FOLLOW_IF_in_elseifStatement434); 
            stream_IF.add(IF53);

            OPEN_PAREN54=(Token)input.LT(1);
            match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_elseifStatement436); 
            stream_OPEN_PAREN.add(OPEN_PAREN54);

            pushFollow(FOLLOW_codeStatement_in_elseifStatement438);
            codeStatement55=codeStatement();
            _fsp--;

            stream_codeStatement.add(codeStatement55.getTree());
            CLOSE_PAREN56=(Token)input.LT(1);
            match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_elseifStatement440); 
            stream_CLOSE_PAREN.add(CLOSE_PAREN56);

            pushFollow(FOLLOW_block_in_elseifStatement444);
            block57=block();
            _fsp--;

            stream_block.add(block57.getTree());

            // AST REWRITE
            // elements: block, codeStatement, OPEN_PAREN, CLOSE_PAREN, ELSE, IF
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 189:2: -> ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block )
            {
                // ./CFScript.g:189:5: ^( ELSEIF ELSE IF OPEN_PAREN codeStatement CLOSE_PAREN block )
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
    // ./CFScript.g:195:1: elseStatement : ELSE block ;
    public final elseStatement_return elseStatement() throws RecognitionException {
    traceIn("elseStatement", 17);
        elseStatement_return retval = new elseStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ELSE58=null;
        block_return block59 = null;


        Object ELSE58_tree=null;

        try {
            // ./CFScript.g:197:2: ( ELSE block )
            // ./CFScript.g:197:2: ELSE block
            {
            root_0 = (Object)adaptor.nil();

            ELSE58=(Token)input.LT(1);
            match(input,ELSE,FOLLOW_ELSE_in_elseStatement488); 
            ELSE58_tree = (Object)adaptor.create(ELSE58);
            root_0 = (Object)adaptor.becomeRoot(ELSE58_tree, root_0);

            pushFollow(FOLLOW_block_in_elseStatement492);
            block59=block();
            _fsp--;

            adaptor.addChild(root_0, block59.getTree());

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

    public static class block_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start block
    // ./CFScript.g:201:1: block : OPEN_CURLY script CLOSE_CURLY ;
    public final block_return block() throws RecognitionException {
    traceIn("block", 18);
        block_return retval = new block_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPEN_CURLY60=null;
        Token CLOSE_CURLY62=null;
        script_return script61 = null;


        Object OPEN_CURLY60_tree=null;
        Object CLOSE_CURLY62_tree=null;

        try {
            // ./CFScript.g:203:2: ( OPEN_CURLY script CLOSE_CURLY )
            // ./CFScript.g:203:2: OPEN_CURLY script CLOSE_CURLY
            {
            root_0 = (Object)adaptor.nil();

            OPEN_CURLY60=(Token)input.LT(1);
            match(input,OPEN_CURLY,FOLLOW_OPEN_CURLY_in_block504); 
            OPEN_CURLY60_tree = (Object)adaptor.create(OPEN_CURLY60);
            adaptor.addChild(root_0, OPEN_CURLY60_tree);

            pushFollow(FOLLOW_script_in_block506);
            script61=script();
            _fsp--;

            adaptor.addChild(root_0, script61.getTree());
            CLOSE_CURLY62=(Token)input.LT(1);
            match(input,CLOSE_CURLY,FOLLOW_CLOSE_CURLY_in_block508); 
            CLOSE_CURLY62_tree = (Object)adaptor.create(CLOSE_CURLY62);
            adaptor.addChild(root_0, CLOSE_CURLY62_tree);


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
    traceOut("block", 18);
        }
        return retval;
    }
    // $ANTLR end block


 

    public static final BitSet FOLLOW_setStatement_in_script72 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_COLON_in_script74 = new BitSet(new long[]{0x00000000008B8502L});
    public static final BitSet FOLLOW_returnStatement_in_script82 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_COLON_in_script84 = new BitSet(new long[]{0x00000000008B8502L});
    public static final BitSet FOLLOW_ifStatement_in_script92 = new BitSet(new long[]{0x00000000008B8502L});
    public static final BitSet FOLLOW_VAR_in_setStatement110 = new BitSet(new long[]{0x00000000000B8000L});
    public static final BitSet FOLLOW_cfmlValue_in_setStatement114 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_EQUALS_in_setStatement117 = new BitSet(new long[]{0x00000000000BC800L});
    public static final BitSet FOLLOW_codeStatement_in_setStatement119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RETURN_in_returnStatement134 = new BitSet(new long[]{0x00000000000BC802L});
    public static final BitSet FOLLOW_codeStatement_in_returnStatement138 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_codeStatement152 = new BitSet(new long[]{0x00000000000BC800L});
    public static final BitSet FOLLOW_codeStatement_in_codeStatement154 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_codeStatement156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasicStatement_in_codeStatement162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlValueStatement_in_cfmlBasicStatement174 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_OPERATOR_in_cfmlBasicStatement177 = new BitSet(new long[]{0x00000000000BC800L});
    public static final BitSet FOLLOW_codeStatement_in_cfmlBasicStatement179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_cfmlValueStatement195 = new BitSet(new long[]{0x00000000000B8000L});
    public static final BitSet FOLLOW_cfmlValue_in_cfmlValueStatement199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_cfmlValue212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_cfmlValue216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlLinking_in_cfmlValue220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_cfmlLinking233 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_cfmlBasicLinking_in_cfmlLinking235 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_HASH_in_cfmlLinking237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasicLinking_in_cfmlLinking243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfmlBasic_in_cfmlBasicLinking255 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_DOT_in_cfmlBasicLinking258 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_cfmlBasic_in_cfmlBasicLinking260 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_identifier_in_cfmlBasic274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_cfmlBasic278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_identifier290 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_struct_in_identifier293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_SQUARE_in_struct308 = new BitSet(new long[]{0x00000000000BC800L});
    public static final BitSet FOLLOW_codeStatement_in_struct310 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_CLOSE_SQUARE_in_struct312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_function338 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_function340 = new BitSet(new long[]{0x00000000000BD800L});
    public static final BitSet FOLLOW_argumentStatement_in_function343 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_function347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_codeStatement_in_argumentStatement378 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_COMMA_in_argumentStatement381 = new BitSet(new long[]{0x00000000000BC800L});
    public static final BitSet FOLLOW_codeStatement_in_argumentStatement383 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_IF_in_ifStatement397 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_ifStatement400 = new BitSet(new long[]{0x00000000000BC800L});
    public static final BitSet FOLLOW_codeStatement_in_ifStatement402 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_ifStatement404 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_block_in_ifStatement407 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_elseifStatement_in_ifStatement411 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_elseStatement_in_ifStatement417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_elseifStatement432 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_IF_in_elseifStatement434 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_elseifStatement436 = new BitSet(new long[]{0x00000000000BC800L});
    public static final BitSet FOLLOW_codeStatement_in_elseifStatement438 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_elseifStatement440 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_block_in_elseifStatement444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_elseStatement488 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_block_in_elseStatement492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPEN_CURLY_in_block504 = new BitSet(new long[]{0x00000000048B8500L});
    public static final BitSet FOLLOW_script_in_block506 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_CLOSE_CURLY_in_block508 = new BitSet(new long[]{0x0000000000000002L});

}