// $ANTLR 3.0 ./CFML.g 2007-06-27 18:21:17

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

import java.util.LinkedList;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class CFMLParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CFTAG", "CUSTOMTAG", "IMPORTTAG", "STRING_LITERAL", "START_TAG_OPEN", "START_TAG_CLOSE", "END_TAG_OPEN", "END_TAG_CLOSE", "TAG_ATTRIBUTE", "EQUALS", "DOUBLE_QUOTE", "ESCAPE_DOUBLE_QUOTE", "STRING", "TAG_NAME", "LETTER", "DIGIT", "UNDERSCORE", "ESCAPE_SINGLE_QUOTE", "SINGLE_QUOTE", "TAG_IDENT", "COLON", "WS", "COMMENT", "OTHER"
    };
    public static final int TAG_ATTRIBUTE=1012;
    public static final int OTHER=1027;
    public static final int CFTAG=1004;
    public static final int DOUBLE_QUOTE=1014;
    public static final int LETTER=1018;
    public static final int END_TAG_OPEN=1010;
    public static final int UNDERSCORE=1020;
    public static final int EQUALS=1013;
    public static final int EOF=-1;
    public static final int ESCAPE_SINGLE_QUOTE=1021;
    public static final int START_TAG_CLOSE=1009;
    public static final int COLON=1024;
    public static final int TAG_NAME=1017;
    public static final int CUSTOMTAG=1005;
    public static final int SINGLE_QUOTE=1022;
    public static final int WS=1025;
    public static final int STRING_LITERAL=1007;
    public static final int TAG_IDENT=1023;
    public static final int END_TAG_CLOSE=1011;
    public static final int ESCAPE_DOUBLE_QUOTE=1015;
    public static final int DIGIT=1019;
    public static final int COMMENT=1026;
    public static final int START_TAG_OPEN=1008;
    public static final int IMPORTTAG=1006;
    public static final int STRING=1016;
    protected static class tagScope_scope {
        String endTagName;
        String name;
    }
    protected Stack tagScope_stack = new Stack();


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
    	protected boolean isColdFusionTag(String name)
    	{		
    		return name.toLowerCase().startsWith("cf");
    	}

    	/**
    	* returns false.
    	*/
    	protected boolean isCustomTag(String name)
    	{		
    		return false;
    	}

    	/**
    	* returns false.
    	*/	
    	protected boolean isImportTag(String name)
    	{
    		return false;
    	}

    	/*
    	* returns false
    	*/
    	protected boolean containsCFScript(String name)
    	{
    		return false;
    	}
    	
    	/*
    	returns endTagName
    	*/
    	protected Tree parseCFScript(Token start, Token stop)
    	{
    		BitSet bit = new BitSet();
    		bit.add(OTHER);
    		System.out.println(((CommonTokenStream)input).getTokens(start.getTokenIndex(), stop.getTokenIndex(), bit));
    		return null;
    	}
    	
    	protected Tree parseStringLiteral(Token start, Token stop)
    	{
    		System.out.println(((CommonTokenStream)input).getTokens(start.getTokenIndex(), stop.getTokenIndex()));
    		return null;
    	}
    	
    	protected boolean allowsCFMLAssignment(String tagName)
    	{
    		return false;
    	}

    	protected boolean allowsCFMLCondition(String tagName)
    	{
    		return false;
    	}

    	protected boolean usesAttributes(String name)
    	{
    		return name.toLowerCase().startsWith("cf");
    	}	

    	/**
    	* reports an error
    	*/	
    	protected void reportError(RecognitionException e, String errorMessage)	
    	{
    		System.err.println(errorMessage);
    	}
    	
    	private LinkedList<String> tagStack = new LinkedList<String>();
    	
    	private LinkedList<String> getTagStack()
    	{
    		return tagStack;
    	}
    	
    	private void setTagStack(LinkedList<String> stack)
    	{
    		tagStack = stack;
    	}


    public static class cfml_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfml
    // ./CFML.g:216:1: cfml : ( tag )* ;
    public final cfml_return cfml() throws RecognitionException {
    traceIn("cfml", 1);
        cfml_return retval = new cfml_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        tag_return tag1 = null;



        try {
            // ./CFML.g:218:2: ( ( tag )* )
            // ./CFML.g:218:2: ( tag )*
            {
            root_0 = (Object)adaptor.nil();

            // ./CFML.g:218:2: ( tag )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==START_TAG_OPEN) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ./CFML.g:218:2: tag
            	    {
            	    pushFollow(FOLLOW_tag_in_cfml96);
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
    // ./CFML.g:221:1: tag : startTag ;
    public final tag_return tag() throws RecognitionException {
    traceIn("tag", 2);
        tag_return retval = new tag_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        startTag_return startTag2 = null;



        try {
            // ./CFML.g:223:3: ( startTag )
            // ./CFML.g:223:3: startTag
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_startTag_in_tag110);
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
    // ./CFML.g:226:1: startTag : (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) ) ;
    public final startTag_return startTag() throws RecognitionException {
    traceIn("startTag", 3);
        tagScope_stack.push(new tagScope_scope());

        startTag_return retval = new startTag_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token sto=null;
        Token stc=null;
        tagContent_return tc = null;

        tagInnerValues_return tagInnerValues3 = null;


        Object sto_tree=null;
        Object stc_tree=null;
        RewriteRuleTokenStream stream_START_TAG_OPEN=new RewriteRuleTokenStream(adaptor,"token START_TAG_OPEN");
        RewriteRuleTokenStream stream_START_TAG_CLOSE=new RewriteRuleTokenStream(adaptor,"token START_TAG_CLOSE");
        RewriteRuleSubtreeStream stream_tagInnerValues=new RewriteRuleSubtreeStream(adaptor,"rule tagInnerValues");
        RewriteRuleSubtreeStream stream_tagContent=new RewriteRuleSubtreeStream(adaptor,"rule tagContent");
        try {
            // ./CFML.g:229:2: ( (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) ) )
            // ./CFML.g:229:2: (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) )
            {
            // ./CFML.g:229:2: (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) )
            // ./CFML.g:230:2: sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) )
            {
            sto=(Token)input.LT(1);
            match(input,START_TAG_OPEN,FOLLOW_START_TAG_OPEN_in_startTag132); 
            stream_START_TAG_OPEN.add(sto);


            		String name = sto.getText().toLowerCase().substring(1);
            		((tagScope_scope)tagScope_stack.peek()).name = name;	
            	
            pushFollow(FOLLOW_tagInnerValues_in_startTag141);
            tagInnerValues3=tagInnerValues();
            _fsp--;

            stream_tagInnerValues.add(tagInnerValues3.getTree());
            stc=(Token)input.LT(1);
            match(input,START_TAG_CLOSE,FOLLOW_START_TAG_CLOSE_in_startTag148); 
            stream_START_TAG_CLOSE.add(stc);


            		if(!stc.getText().equals("/>"))		
            		{
            			System.out.println("push: " + name);
            			((tagScope_scope)tagScope_stack.peek()).endTagName = name; 
            			getTagStack().push(name);
            		}
            		else
            		{
            			((tagScope_scope)tagScope_stack.peek()).endTagName = ""; 
            			System.out.println("close: " + sto.getText().toLowerCase().substring(1));
            		}
            	
            pushFollow(FOLLOW_tagContent_in_startTag156);
            tc=tagContent();
            _fsp--;

            stream_tagContent.add(tc.getTree());
            // ./CFML.g:253:3: ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) )
            // ./CFML.g:254:3: 
            {

            // AST REWRITE
            // elements: tagInnerValues, tagContent, tagInnerValues, tagInnerValues, tagContent, START_TAG_CLOSE, tagContent, tagContent, START_TAG_CLOSE, START_TAG_OPEN, START_TAG_CLOSE, tagInnerValues, START_TAG_CLOSE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 254:3: -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
            if (isImportTag(name)) {
                // ./CFML.g:254:27: ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(IMPORTTAG, sto), root_1);

                adaptor.addChild(root_1, stream_tagInnerValues.next());
                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 255:3: -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
            if (isCustomTag(name)) {
                // ./CFML.g:255:27: ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(CUSTOMTAG, sto), root_1);

                adaptor.addChild(root_1, stream_tagInnerValues.next());
                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 256:3: -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
            if (isColdFusionTag(name)) {
                // ./CFML.g:256:31: ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(CFTAG, sto), root_1);

                adaptor.addChild(root_1, stream_tagInnerValues.next());
                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, 
                							(containsCFScript(name) ? parseCFScript(stc, tc.stop) : null)
                						);
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 263:3: -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent )
            {
                // ./CFML.g:263:6: ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_START_TAG_OPEN.next(), root_1);

                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagInnerValues.next());
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
            tagScope_stack.pop();

        }
        return retval;
    }
    // $ANTLR end startTag

    public static class tagContent_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start tagContent
    // ./CFML.g:268:1: tagContent : cfml ({...}? => ( endTag ) ) ;
    public final tagContent_return tagContent() throws RecognitionException {
    traceIn("tagContent", 4);
        tagContent_return retval = new tagContent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        cfml_return cfml4 = null;

        endTag_return endTag5 = null;



        try {
            // ./CFML.g:270:2: ( cfml ({...}? => ( endTag ) ) )
            // ./CFML.g:270:2: cfml ({...}? => ( endTag ) )
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_cfml_in_tagContent271);
            cfml4=cfml();
            _fsp--;

            adaptor.addChild(root_0, cfml4.getTree());
            // ./CFML.g:271:3: ({...}? => ( endTag ) )
            // ./CFML.g:272:3: {...}? => ( endTag )
            {

            			Token t = input.LT(1);
            			String name;
            			
            			if(t.getText() == null)
            			{
            				name = "*"; //never be a name				
            			}
            			else
            			{
            				name = t.getText().toLowerCase().substring(2);
            			}
            		
            if ( !( ((tagScope_scope)tagScope_stack.peek()).endTagName.equals(name)) ) {
                throw new FailedPredicateException(input, "tagContent", " $tagScope::endTagName.equals(name)");
            }
            // ./CFML.g:286:3: ( endTag )
            // ./CFML.g:286:4: endTag
            {
            pushFollow(FOLLOW_endTag_in_tagContent290);
            endTag5=endTag();
            _fsp--;

            adaptor.addChild(root_0, endTag5.getTree());

            }


            }


            }

            retval.stop = input.LT(-1);

                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (FailedPredicateException fpe) {

            	String text = input.LT(1).getText();

            	System.out.println("caught: " + input.LT(1).getText());
            	retval.stop = input.LT(-1);
            	retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            	//adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            	
            	if(!(text == null || getTagStack().contains(text.toLowerCase().substring(2))))
            	{
            		//this is a bad error. Norti norti.
            		String msg = getErrorHeader(fpe);
                		msg += " end tag (" + text + ">" +
            		                 ") cannot be matched to any start tag currently open";
            		                 
            		reportError(fpe, msg);
            	}

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
    // ./CFML.g:309:1: endTag : END_TAG_OPEN END_TAG_CLOSE ;
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
            // ./CFML.g:311:2: ( END_TAG_OPEN END_TAG_CLOSE )
            // ./CFML.g:311:2: END_TAG_OPEN END_TAG_CLOSE
            {
            root_0 = (Object)adaptor.nil();


            		String name = input.LT(1).getText().toLowerCase().substring(2);
            		
            		//clear off the chaff
            		while(!name.equals(getTagStack().peek()))
            		{
            			String pastTagName = getTagStack().pop();
            			
            			System.out.println("popped: " + pastTagName);
            		}
            		
            		//pop off the last eleemnt
            		String pastTagName = getTagStack().pop();
            		System.out.println("finally popped: " + pastTagName);
            	
            END_TAG_OPEN6=(Token)input.LT(1);
            match(input,END_TAG_OPEN,FOLLOW_END_TAG_OPEN_in_endTag317); 
            END_TAG_OPEN6_tree = (Object)adaptor.create(END_TAG_OPEN6);
            root_0 = (Object)adaptor.becomeRoot(END_TAG_OPEN6_tree, root_0);

            END_TAG_CLOSE7=(Token)input.LT(1);
            match(input,END_TAG_CLOSE,FOLLOW_END_TAG_CLOSE_in_endTag320); 
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

    public static class tagInnerValues_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start tagInnerValues
    // ./CFML.g:329:1: tagInnerValues : ( ({...}? => ( tagAttribute )* ) | );
    public final tagInnerValues_return tagInnerValues() throws RecognitionException {
    traceIn("tagInnerValues", 6);
        tagInnerValues_return retval = new tagInnerValues_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        tagAttribute_return tagAttribute8 = null;



        try {
            // ./CFML.g:331:2: ( ({...}? => ( tagAttribute )* ) | )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==TAG_ATTRIBUTE) && (
            		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
            		||
            		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
            		||
            		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
            	)) {
                alt3=1;
            }
            else if ( (LA3_0==START_TAG_CLOSE) ) {
                int LA3_2 = input.LA(2);

                if ( (
                		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                		||
                		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                		||
                		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                	) ) {
                    alt3=1;
                }
                else if ( (true) ) {
                    alt3=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("329:1: tagInnerValues : ( ({...}? => ( tagAttribute )* ) | );", 3, 2, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("329:1: tagInnerValues : ( ({...}? => ( tagAttribute )* ) | );", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ./CFML.g:331:2: ({...}? => ( tagAttribute )* )
                    {
                    root_0 = (Object)adaptor.nil();

                    // ./CFML.g:331:2: ({...}? => ( tagAttribute )* )
                    // ./CFML.g:332:2: {...}? => ( tagAttribute )*
                    {
                    if ( !(
                    		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                    		||
                    		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                    		||
                    		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                    	) ) {
                        throw new FailedPredicateException(input, "tagInnerValues", "\n\t\t(isColdFusionTag($tagScope::name) && usesAttributes($tagScope::name))\n\t\t||\n\t\t(isCustomTag($tagScope::name))\n\t\t||\n\t\t(isImportTag($tagScope::name))\n\t");
                    }
                    // ./CFML.g:338:7: ( tagAttribute )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==TAG_ATTRIBUTE) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // ./CFML.g:338:7: tagAttribute
                    	    {
                    	    pushFollow(FOLLOW_tagAttribute_in_tagInnerValues338);
                    	    tagAttribute8=tagAttribute();
                    	    _fsp--;

                    	    adaptor.addChild(root_0, tagAttribute8.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // ./CFML.g:356:2: 
                    {
                    root_0 = (Object)adaptor.nil();

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
    traceOut("tagInnerValues", 6);
        }
        return retval;
    }
    // $ANTLR end tagInnerValues

    public static class tagAttribute_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start tagAttribute
    // ./CFML.g:358:1: tagAttribute : TAG_ATTRIBUTE EQUALS stringLiteral ;
    public final tagAttribute_return tagAttribute() throws RecognitionException {
    traceIn("tagAttribute", 7);
        tagAttribute_return retval = new tagAttribute_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token TAG_ATTRIBUTE9=null;
        Token EQUALS10=null;
        stringLiteral_return stringLiteral11 = null;


        Object TAG_ATTRIBUTE9_tree=null;
        Object EQUALS10_tree=null;

        try {
            // ./CFML.g:360:2: ( TAG_ATTRIBUTE EQUALS stringLiteral )
            // ./CFML.g:360:2: TAG_ATTRIBUTE EQUALS stringLiteral
            {
            root_0 = (Object)adaptor.nil();

            TAG_ATTRIBUTE9=(Token)input.LT(1);
            match(input,TAG_ATTRIBUTE,FOLLOW_TAG_ATTRIBUTE_in_tagAttribute361); 
            TAG_ATTRIBUTE9_tree = (Object)adaptor.create(TAG_ATTRIBUTE9);
            adaptor.addChild(root_0, TAG_ATTRIBUTE9_tree);

            EQUALS10=(Token)input.LT(1);
            match(input,EQUALS,FOLLOW_EQUALS_in_tagAttribute363); 
            EQUALS10_tree = (Object)adaptor.create(EQUALS10);
            adaptor.addChild(root_0, EQUALS10_tree);

            pushFollow(FOLLOW_stringLiteral_in_tagAttribute365);
            stringLiteral11=stringLiteral();
            _fsp--;

            adaptor.addChild(root_0, stringLiteral11.getTree());

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
    traceOut("tagAttribute", 7);
        }
        return retval;
    }
    // $ANTLR end tagAttribute

    public static class stringLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start stringLiteral
    // ./CFML.g:363:1: stringLiteral : DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | STRING )* DOUBLE_QUOTE ;
    public final stringLiteral_return stringLiteral() throws RecognitionException {
    traceIn("stringLiteral", 8);
        stringLiteral_return retval = new stringLiteral_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOUBLE_QUOTE12=null;
        Token set13=null;
        Token DOUBLE_QUOTE14=null;

        Object DOUBLE_QUOTE12_tree=null;
        Object set13_tree=null;
        Object DOUBLE_QUOTE14_tree=null;

        try {
            // ./CFML.g:365:2: ( DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | STRING )* DOUBLE_QUOTE )
            // ./CFML.g:365:2: DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | STRING )* DOUBLE_QUOTE
            {
            root_0 = (Object)adaptor.nil();

            DOUBLE_QUOTE12=(Token)input.LT(1);
            match(input,DOUBLE_QUOTE,FOLLOW_DOUBLE_QUOTE_in_stringLiteral378); 
            DOUBLE_QUOTE12_tree = (Object)adaptor.create(DOUBLE_QUOTE12);
            adaptor.addChild(root_0, DOUBLE_QUOTE12_tree);

            // ./CFML.g:365:15: ( ESCAPE_DOUBLE_QUOTE | STRING )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>=ESCAPE_DOUBLE_QUOTE && LA4_0<=STRING)) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ./CFML.g:
            	    {
            	    set13=(Token)input.LT(1);
            	    if ( (input.LA(1)>=ESCAPE_DOUBLE_QUOTE && input.LA(1)<=STRING) ) {
            	        input.consume();
            	        adaptor.addChild(root_0, adaptor.create(set13));
            	        errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_stringLiteral380);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            DOUBLE_QUOTE14=(Token)input.LT(1);
            match(input,DOUBLE_QUOTE,FOLLOW_DOUBLE_QUOTE_in_stringLiteral389); 
            DOUBLE_QUOTE14_tree = (Object)adaptor.create(DOUBLE_QUOTE14);
            adaptor.addChild(root_0, DOUBLE_QUOTE14_tree);


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
    traceOut("stringLiteral", 8);
        }
        return retval;
    }
    // $ANTLR end stringLiteral


 

    public static final BitSet FOLLOW_tag_in_cfml96 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_startTag_in_tag110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_START_TAG_OPEN_in_startTag132 = new BitSet(new long[]{0x0000000000001200L});
    public static final BitSet FOLLOW_tagInnerValues_in_startTag141 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_START_TAG_CLOSE_in_startTag148 = new BitSet(new long[]{0x0000000000000500L});
    public static final BitSet FOLLOW_tagContent_in_startTag156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfml_in_tagContent271 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_endTag_in_tagContent290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_END_TAG_OPEN_in_endTag317 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_END_TAG_CLOSE_in_endTag320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tagAttribute_in_tagInnerValues338 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_TAG_ATTRIBUTE_in_tagAttribute361 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_EQUALS_in_tagAttribute363 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_stringLiteral_in_tagAttribute365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_QUOTE_in_stringLiteral378 = new BitSet(new long[]{0x000000000001C000L});
    public static final BitSet FOLLOW_set_in_stringLiteral380 = new BitSet(new long[]{0x000000000001C000L});
    public static final BitSet FOLLOW_DOUBLE_QUOTE_in_stringLiteral389 = new BitSet(new long[]{0x0000000000000002L});

}
