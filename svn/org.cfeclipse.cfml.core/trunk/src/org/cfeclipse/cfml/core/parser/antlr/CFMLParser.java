// $ANTLR 3.0 ./CFML.g 2007-06-18 17:05:29

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
d
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

public class CFMLParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CFTAG", "CUSTOMTAG", "IMPORTTAG", "CFSCRIPT", "START_TAG_OPEN", "START_TAG_CLOSE", "END_TAG_OPEN", "END_TAG_CLOSE", "TAG_NAME", "LETTER", "DIGIT", "UNDERSCORE", "TAG_ATTRIBUTE", "EQUALS", "ESCAPE_DOUBLE_QUOTE", "ESCAPE_SINGLE_QUOTE", "DOUBLE_QUOTE", "SINGLE_QUOTE", "TAG_IDENT", "COLON", "WS", "COMMENT", "OTHER"
    };
    public static final int TAG_ATTRIBUTE=16;
    public static final int OTHER=26;
    public static final int DOUBLE_QUOTE=20;
    public static final int CFTAG=4;
    public static final int LETTER=13;
    public static final int END_TAG_OPEN=10;
    public static final int UNDERSCORE=15;
    public static final int EQUALS=17;
    public static final int EOF=-1;
    public static final int START_TAG_CLOSE=9;
    public static final int ESCAPE_SINGLE_QUOTE=19;
    public static final int COLON=23;
    public static final int TAG_NAME=12;
    public static final int CUSTOMTAG=5;
    public static final int SINGLE_QUOTE=21;
    public static final int WS=24;
    public static final int TAG_IDENT=22;
    public static final int END_TAG_CLOSE=11;
    public static final int ESCAPE_DOUBLE_QUOTE=18;
    public static final int DIGIT=14;
    public static final int COMMENT=25;
    public static final int START_TAG_OPEN=8;
    public static final int CFSCRIPT=7;
    public static final int IMPORTTAG=6;

        public CFMLParser(TokenStream input) {
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
    public String getGrammarFileName() { return "./CFML.g"; }


    	/**
    	* returns false.
    	*/
    	protected boolean isColdFusionTag(Token tag)
    	{		
    		return false;
    	}

    	/**
    	* returns false.
    	*/
    	protected boolean isCustomTag(Token tag)
    	{		
    		return false;
    	}

    	/**
    	* returns false.
    	*/	
    	protected boolean isImportTag(Token tag)
    	{
    													return false;
    	}

    	/*
    	* returns false
    	*/
    	protected boolean containsCFScript(Token tag)
    	{
    		return false;
    	}
    	
    	/*
    	returns null
    	*/
    	
    	protected Tree parseCFScript(Token start, ParserRuleReturnScope stop)
    	{
    		System.out.println("inner method");
    		BitSet bit = new BitSet();
    		bit.add(OTHER);
    		System.out.println(((CommonTokenStream)input).getTokens(start.getTokenIndex(), stop.stop.getTokenIndex(), bit));
    		return null;
    	}


    public static class cfml_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfml
    // ./CFML.g:169:1: cfml : ( tag )* ;
    public final cfml_return cfml() throws RecognitionException {
    traceIn("cfml", 1);
        cfml_return retval = new cfml_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        tag_return tag1 = null;



        try {
            // ./CFML.g:171:2: ( ( tag )* )
            // ./CFML.g:171:2: ( tag )*
            {
            root_0 = (Object)adaptor.nil();

            // ./CFML.g:171:2: ( tag )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==START_TAG_OPEN) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ./CFML.g:171:2: tag
            	    {
            	    pushFollow(FOLLOW_tag_in_cfml90);
            	    tag1=tag();
            	    _fsp--;

            	    adaptor.addChild(root_0, tag1.getTree());

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
    traceOut("cfml", 1);
        }
        return retval;
    }
    // $ANTLR end cfml

    public static class tag_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start tag
    // ./CFML.g:174:1: tag : startTag ;
    public final tag_return tag() throws RecognitionException {
    traceIn("tag", 2);
        tag_return retval = new tag_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        startTag_return startTag2 = null;



        try {
            // ./CFML.g:176:3: ( startTag )
            // ./CFML.g:176:3: startTag
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_startTag_in_tag104);
            startTag2=startTag();
            _fsp--;

            adaptor.addChild(root_0, startTag2.getTree());

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
    traceOut("tag", 2);
        }
        return retval;
    }
    // $ANTLR end tag

    public static class startTag_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start startTag
    // ./CFML.g:179:1: startTag : (sto= START_TAG_OPEN stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag($sto)}? ^( IMPORTTAG[$sto] START_TAG_CLOSE tagContent ) -> {isCustomTag($sto)}? ^( CUSTOMTAG[$sto] START_TAG_CLOSE tagContent ) -> {isColdFusionTag($sto)}? ^( CFTAG[$sto] START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagContent ) ) ) ;
    public final startTag_return startTag() throws RecognitionException {
    traceIn("startTag", 3);
        startTag_return retval = new startTag_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token sto=null;
        Token stc=null;
        tagContent_return tc = null;


        Object sto_tree=null;
        Object stc_tree=null;
        RewriteRuleTokenStream stream_START_TAG_OPEN=new RewriteRuleTokenStream(adaptor,"token START_TAG_OPEN");
        RewriteRuleTokenStream stream_START_TAG_CLOSE=new RewriteRuleTokenStream(adaptor,"token START_TAG_CLOSE");
        RewriteRuleSubtreeStream stream_tagContent=new RewriteRuleSubtreeStream(adaptor,"rule tagContent");
        try {
            // ./CFML.g:181:2: ( (sto= START_TAG_OPEN stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag($sto)}? ^( IMPORTTAG[$sto] START_TAG_CLOSE tagContent ) -> {isCustomTag($sto)}? ^( CUSTOMTAG[$sto] START_TAG_CLOSE tagContent ) -> {isColdFusionTag($sto)}? ^( CFTAG[$sto] START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagContent ) ) ) )
            // ./CFML.g:181:2: (sto= START_TAG_OPEN stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag($sto)}? ^( IMPORTTAG[$sto] START_TAG_CLOSE tagContent ) -> {isCustomTag($sto)}? ^( CUSTOMTAG[$sto] START_TAG_CLOSE tagContent ) -> {isColdFusionTag($sto)}? ^( CFTAG[$sto] START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagContent ) ) )
            {
            // ./CFML.g:181:2: (sto= START_TAG_OPEN stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag($sto)}? ^( IMPORTTAG[$sto] START_TAG_CLOSE tagContent ) -> {isCustomTag($sto)}? ^( CUSTOMTAG[$sto] START_TAG_CLOSE tagContent ) -> {isColdFusionTag($sto)}? ^( CFTAG[$sto] START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagContent ) ) )
            // ./CFML.g:182:2: sto= START_TAG_OPEN stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag($sto)}? ^( IMPORTTAG[$sto] START_TAG_CLOSE tagContent ) -> {isCustomTag($sto)}? ^( CUSTOMTAG[$sto] START_TAG_CLOSE tagContent ) -> {isColdFusionTag($sto)}? ^( CFTAG[$sto] START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagContent ) )
            {
            sto=(Token)input.LT(1);
            match(input,START_TAG_OPEN,FOLLOW_START_TAG_OPEN_in_startTag121); 
            stream_START_TAG_OPEN.add(sto);

            stc=(Token)input.LT(1);
            match(input,START_TAG_CLOSE,FOLLOW_START_TAG_CLOSE_in_startTag127); 
            stream_START_TAG_CLOSE.add(stc);

            pushFollow(FOLLOW_tagContent_in_startTag132);
            tc=tagContent();
            _fsp--;

            stream_tagContent.add(tc.getTree());
            // ./CFML.g:185:3: ( -> {isImportTag($sto)}? ^( IMPORTTAG[$sto] START_TAG_CLOSE tagContent ) -> {isCustomTag($sto)}? ^( CUSTOMTAG[$sto] START_TAG_CLOSE tagContent ) -> {isColdFusionTag($sto)}? ^( CFTAG[$sto] START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagContent ) )
            // ./CFML.g:186:3: 
            {

            // AST REWRITE
            // elements: tagContent, tagContent, START_TAG_CLOSE, START_TAG_CLOSE, START_TAG_CLOSE, START_TAG_OPEN, tagContent, tagContent, START_TAG_CLOSE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 186:3: -> {isImportTag($sto)}? ^( IMPORTTAG[$sto] START_TAG_CLOSE tagContent )
            if (isImportTag(sto)) {
                // ./CFML.g:186:27: ^( IMPORTTAG[$sto] START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(IMPORTTAG, sto), root_1);

                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 187:3: -> {isCustomTag($sto)}? ^( CUSTOMTAG[$sto] START_TAG_CLOSE tagContent )
            if (isCustomTag(sto)) {
                // ./CFML.g:187:27: ^( CUSTOMTAG[$sto] START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(CUSTOMTAG, sto), root_1);

                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 188:3: -> {isColdFusionTag($sto)}? ^( CFTAG[$sto] START_TAG_CLOSE tagContent )
            if (isColdFusionTag(sto)) {
                // ./CFML.g:188:31: ^( CFTAG[$sto] START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(CFTAG, sto), root_1);

                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, 
                							(containsCFScript(sto) ? parseCFScript(stc, tc) : null)
                						);
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 195:3: -> ^( START_TAG_OPEN START_TAG_CLOSE tagContent )
            {
                // ./CFML.g:195:6: ^( START_TAG_OPEN START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_START_TAG_OPEN.next(), root_1);

                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }



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
    traceOut("startTag", 3);
        }
        return retval;
    }
    // $ANTLR end startTag

    public static class tagContent_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start tagContent
    // ./CFML.g:200:1: tagContent : ( tag )* ( endTag | EOF ) ;
    public final tagContent_return tagContent() throws RecognitionException {
    traceIn("tagContent", 4);
        tagContent_return retval = new tagContent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF5=null;
        tag_return tag3 = null;

        endTag_return endTag4 = null;


        Object EOF5_tree=null;

        try {
            // ./CFML.g:202:2: ( ( tag )* ( endTag | EOF ) )
            // ./CFML.g:202:2: ( tag )* ( endTag | EOF )
            {
            root_0 = (Object)adaptor.nil();

            // ./CFML.g:202:2: ( tag )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==START_TAG_OPEN) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ./CFML.g:202:2: tag
            	    {
            	    pushFollow(FOLLOW_tag_in_tagContent240);
            	    tag3=tag();
            	    _fsp--;

            	    adaptor.addChild(root_0, tag3.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // ./CFML.g:202:7: ( endTag | EOF )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==END_TAG_OPEN) ) {
                alt3=1;
            }
            else if ( (LA3_0==EOF) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("202:7: ( endTag | EOF )", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ./CFML.g:202:8: endTag
                    {
                    pushFollow(FOLLOW_endTag_in_tagContent244);
                    endTag4=endTag();
                    _fsp--;

                    adaptor.addChild(root_0, endTag4.getTree());

                    }
                    break;
                case 2 :
                    // ./CFML.g:202:17: EOF
                    {
                    EOF5=(Token)input.LT(1);
                    match(input,EOF,FOLLOW_EOF_in_tagContent248); 

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
    traceOut("tagContent", 4);
        }
        return retval;
    }
    // $ANTLR end tagContent

    public static class endTag_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start endTag
    // ./CFML.g:205:1: endTag : END_TAG_OPEN END_TAG_CLOSE ;
    public final endTag_return endTag() throws RecognitionException {
    traceIn("endTag", 5);
        endTag_return retval = new endTag_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token END_TAG_OPEN6=null;
        Token END_TAG_CLOSE7=null;

        Object END_TAG_OPEN6_tree=null;
        Object END_TAG_CLOSE7_tree=null;

        try {
            // ./CFML.g:210:2: ( END_TAG_OPEN END_TAG_CLOSE )
            // ./CFML.g:210:2: END_TAG_OPEN END_TAG_CLOSE
            {
            root_0 = (Object)adaptor.nil();

            END_TAG_OPEN6=(Token)input.LT(1);
            match(input,END_TAG_OPEN,FOLLOW_END_TAG_OPEN_in_endTag266); 
            END_TAG_OPEN6_tree = (Object)adaptor.create(END_TAG_OPEN6);
            root_0 = (Object)adaptor.becomeRoot(END_TAG_OPEN6_tree, root_0);

            END_TAG_CLOSE7=(Token)input.LT(1);
            match(input,END_TAG_CLOSE,FOLLOW_END_TAG_CLOSE_in_endTag269); 
            END_TAG_CLOSE7_tree = (Object)adaptor.create(END_TAG_CLOSE7);
            adaptor.addChild(root_0, END_TAG_CLOSE7_tree);


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
    traceOut("endTag", 5);
        }
        return retval;
    }
    // $ANTLR end endTag


 

    public static final BitSet FOLLOW_tag_in_cfml90 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_startTag_in_tag104 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_START_TAG_OPEN_in_startTag121 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_START_TAG_CLOSE_in_startTag127 = new BitSet(new long[]{0x0000000000000500L});
    public static final BitSet FOLLOW_tagContent_in_startTag132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tag_in_tagContent240 = new BitSet(new long[]{0x0000000000000500L});
    public static final BitSet FOLLOW_endTag_in_tagContent244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_tagContent248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_END_TAG_OPEN_in_endTag266 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_END_TAG_CLOSE_in_endTag269 = new BitSet(new long[]{0x0000000000000002L});

}