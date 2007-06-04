// $ANTLR 3.0 ./CFML.g 2007-06-04 17:49:08

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CFTAG", "CUSTOMTAG", "IMPORTTAG", "START_TAG_OPEN", "START_TAG_CLOSE", "END_TAG_OPEN", "END_TAG_CLOSE", "TAG_NAME", "LETTER", "DIGIT", "UNDERSCORE", "TAG_ATTRIBUTE", "EQUALS", "ESCAPE_DOUBLE_QUOTE", "ESCAPE_SINGLE_QUOTE", "DOUBLE_QUOTE", "SINGLE_QUOTE", "TAG_IDENT", "COLON", "WS", "COMMENT", "OTHER"
    };
    public static final int TAG_ATTRIBUTE=15;
    public static final int OTHER=25;
    public static final int CFTAG=4;
    public static final int LETTER=12;
    public static final int DOUBLE_QUOTE=19;
    public static final int END_TAG_OPEN=9;
    public static final int UNDERSCORE=14;
    public static final int EQUALS=16;
    public static final int EOF=-1;
    public static final int START_TAG_CLOSE=8;
    public static final int ESCAPE_SINGLE_QUOTE=18;
    public static final int COLON=22;
    public static final int TAG_NAME=11;
    public static final int CUSTOMTAG=5;
    public static final int SINGLE_QUOTE=20;
    public static final int WS=23;
    public static final int TAG_IDENT=21;
    public static final int END_TAG_CLOSE=10;
    public static final int ESCAPE_DOUBLE_QUOTE=17;
    public static final int DIGIT=13;
    public static final int COMMENT=24;
    public static final int START_TAG_OPEN=7;
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


    public static class cfml_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfml
    // ./CFML.g:147:1: cfml : ( tag )* ;
    public final cfml_return cfml() throws RecognitionException {
    traceIn("cfml", 1);
        cfml_return retval = new cfml_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        tag_return tag1 = null;



        try {
            // ./CFML.g:149:2: ( ( tag )* )
            // ./CFML.g:149:2: ( tag )*
            {
            root_0 = (Object)adaptor.nil();

            // ./CFML.g:149:2: ( tag )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==START_TAG_OPEN) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ./CFML.g:149:2: tag
            	    {
            	    pushFollow(FOLLOW_tag_in_cfml86);
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
    // ./CFML.g:152:1: tag : startTag ;
    public final tag_return tag() throws RecognitionException {
    traceIn("tag", 2);
        tag_return retval = new tag_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        startTag_return startTag2 = null;



        try {
            // ./CFML.g:154:3: ( startTag )
            // ./CFML.g:154:3: startTag
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_startTag_in_tag100);
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
    // ./CFML.g:157:1: startTag : to= START_TAG_OPEN START_TAG_CLOSE tagContent -> {isImportTag($to)}? ^( IMPORTTAG[$to] START_TAG_CLOSE tagContent ) -> {isCustomTag($to)}? ^( CUSTOMTAG[$to] START_TAG_CLOSE tagContent ) -> {isColdFusionTag($to)}? ^( CFTAG[$to] START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagContent ) ;
    public final startTag_return startTag() throws RecognitionException {
    traceIn("startTag", 3);
        startTag_return retval = new startTag_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token to=null;
        Token START_TAG_CLOSE3=null;
        tagContent_return tagContent4 = null;


        Object to_tree=null;
        Object START_TAG_CLOSE3_tree=null;
        RewriteRuleTokenStream stream_START_TAG_OPEN=new RewriteRuleTokenStream(adaptor,"token START_TAG_OPEN");
        RewriteRuleTokenStream stream_START_TAG_CLOSE=new RewriteRuleTokenStream(adaptor,"token START_TAG_CLOSE");
        RewriteRuleSubtreeStream stream_tagContent=new RewriteRuleSubtreeStream(adaptor,"rule tagContent");
        try {
            // ./CFML.g:159:2: (to= START_TAG_OPEN START_TAG_CLOSE tagContent -> {isImportTag($to)}? ^( IMPORTTAG[$to] START_TAG_CLOSE tagContent ) -> {isCustomTag($to)}? ^( CUSTOMTAG[$to] START_TAG_CLOSE tagContent ) -> {isColdFusionTag($to)}? ^( CFTAG[$to] START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagContent ) )
            // ./CFML.g:159:2: to= START_TAG_OPEN START_TAG_CLOSE tagContent
            {
            to=(Token)input.LT(1);
            match(input,START_TAG_OPEN,FOLLOW_START_TAG_OPEN_in_startTag114); 
            stream_START_TAG_OPEN.add(to);

            START_TAG_CLOSE3=(Token)input.LT(1);
            match(input,START_TAG_CLOSE,FOLLOW_START_TAG_CLOSE_in_startTag116); 
            stream_START_TAG_CLOSE.add(START_TAG_CLOSE3);

            pushFollow(FOLLOW_tagContent_in_startTag119);
            tagContent4=tagContent();
            _fsp--;

            stream_tagContent.add(tagContent4.getTree());

            // AST REWRITE
            // elements: tagContent, tagContent, tagContent, tagContent, START_TAG_CLOSE, START_TAG_CLOSE, START_TAG_OPEN, START_TAG_CLOSE, START_TAG_CLOSE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 161:2: -> {isImportTag($to)}? ^( IMPORTTAG[$to] START_TAG_CLOSE tagContent )
            if (isImportTag(to)) {
                // ./CFML.g:161:25: ^( IMPORTTAG[$to] START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(IMPORTTAG, to), root_1);

                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 162:2: -> {isCustomTag($to)}? ^( CUSTOMTAG[$to] START_TAG_CLOSE tagContent )
            if (isCustomTag(to)) {
                // ./CFML.g:162:25: ^( CUSTOMTAG[$to] START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(CUSTOMTAG, to), root_1);

                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 163:2: -> {isColdFusionTag($to)}? ^( CFTAG[$to] START_TAG_CLOSE tagContent )
            if (isColdFusionTag(to)) {
                // ./CFML.g:163:29: ^( CFTAG[$to] START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(CFTAG, to), root_1);

                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 164:2: -> ^( START_TAG_OPEN START_TAG_CLOSE tagContent )
            {
                // ./CFML.g:164:5: ^( START_TAG_OPEN START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_START_TAG_OPEN.next(), root_1);

                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagContent.next());

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
    // ./CFML.g:167:1: tagContent : ( tag )* ( endTag | EOF ) ;
    public final tagContent_return tagContent() throws RecognitionException {
    traceIn("tagContent", 4);
        tagContent_return retval = new tagContent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF7=null;
        tag_return tag5 = null;

        endTag_return endTag6 = null;


        Object EOF7_tree=null;

        try {
            // ./CFML.g:169:2: ( ( tag )* ( endTag | EOF ) )
            // ./CFML.g:169:2: ( tag )* ( endTag | EOF )
            {
            root_0 = (Object)adaptor.nil();

            // ./CFML.g:169:2: ( tag )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==START_TAG_OPEN) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ./CFML.g:169:2: tag
            	    {
            	    pushFollow(FOLLOW_tag_in_tagContent185);
            	    tag5=tag();
            	    _fsp--;

            	    adaptor.addChild(root_0, tag5.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // ./CFML.g:169:7: ( endTag | EOF )
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
                    new NoViableAltException("169:7: ( endTag | EOF )", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ./CFML.g:169:8: endTag
                    {
                    pushFollow(FOLLOW_endTag_in_tagContent189);
                    endTag6=endTag();
                    _fsp--;

                    adaptor.addChild(root_0, endTag6.getTree());

                    }
                    break;
                case 2 :
                    // ./CFML.g:169:17: EOF
                    {
                    EOF7=(Token)input.LT(1);
                    match(input,EOF,FOLLOW_EOF_in_tagContent193); 

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
    // ./CFML.g:172:1: endTag : END_TAG_OPEN END_TAG_CLOSE ;
    public final endTag_return endTag() throws RecognitionException {
    traceIn("endTag", 5);
        endTag_return retval = new endTag_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token END_TAG_OPEN8=null;
        Token END_TAG_CLOSE9=null;

        Object END_TAG_OPEN8_tree=null;
        Object END_TAG_CLOSE9_tree=null;

        try {
            // ./CFML.g:174:2: ( END_TAG_OPEN END_TAG_CLOSE )
            // ./CFML.g:174:2: END_TAG_OPEN END_TAG_CLOSE
            {
            root_0 = (Object)adaptor.nil();

            END_TAG_OPEN8=(Token)input.LT(1);
            match(input,END_TAG_OPEN,FOLLOW_END_TAG_OPEN_in_endTag208); 
            END_TAG_OPEN8_tree = (Object)adaptor.create(END_TAG_OPEN8);
            root_0 = (Object)adaptor.becomeRoot(END_TAG_OPEN8_tree, root_0);

            END_TAG_CLOSE9=(Token)input.LT(1);
            match(input,END_TAG_CLOSE,FOLLOW_END_TAG_CLOSE_in_endTag211); 
            END_TAG_CLOSE9_tree = (Object)adaptor.create(END_TAG_CLOSE9);
            adaptor.addChild(root_0, END_TAG_CLOSE9_tree);


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


 

    public static final BitSet FOLLOW_tag_in_cfml86 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_startTag_in_tag100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_START_TAG_OPEN_in_startTag114 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_START_TAG_CLOSE_in_startTag116 = new BitSet(new long[]{0x0000000000000280L});
    public static final BitSet FOLLOW_tagContent_in_startTag119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tag_in_tagContent185 = new BitSet(new long[]{0x0000000000000280L});
    public static final BitSet FOLLOW_endTag_in_tagContent189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_tagContent193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_END_TAG_OPEN_in_endTag208 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_END_TAG_CLOSE_in_endTag211 = new BitSet(new long[]{0x0000000000000002L});

}