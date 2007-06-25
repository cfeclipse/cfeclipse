// $ANTLR 3.0 ./CFML.g 2007-06-25 21:37:55

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CFTAG", "CUSTOMTAG", "IMPORTTAG", "START_TAG_OPEN", "START_TAG_CLOSE", "END_TAG_OPEN", "END_TAG_CLOSE", "TAG_ATTRIBUTE", "EQUALS", "DOUBLE_QUOTE", "ESCAPE_DOUBLE_QUOTE", "SINGLE_QUOTE", "ESCAPE_SINGLE_QUOTE", "TAG_NAME", "LETTER", "DIGIT", "UNDERSCORE", "TAG_IDENT", "COLON", "WS", "COMMENT", "OTHER"
    };
    public static final int TAG_ATTRIBUTE=11;
    public static final int OTHER=25;
    public static final int CFTAG=4;
    public static final int DOUBLE_QUOTE=13;
    public static final int LETTER=18;
    public static final int END_TAG_OPEN=9;
    public static final int UNDERSCORE=20;
    public static final int EQUALS=12;
    public static final int EOF=-1;
    public static final int START_TAG_CLOSE=8;
    public static final int ESCAPE_SINGLE_QUOTE=16;
    public static final int COLON=22;
    public static final int TAG_NAME=17;
    public static final int CUSTOMTAG=5;
    public static final int SINGLE_QUOTE=15;
    public static final int WS=23;
    public static final int TAG_IDENT=21;
    public static final int END_TAG_CLOSE=10;
    public static final int ESCAPE_DOUBLE_QUOTE=14;
    public static final int DIGIT=19;
    public static final int COMMENT=24;
    public static final int START_TAG_OPEN=7;
    public static final int IMPORTTAG=6;
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
    		return false;
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
    		return false;
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
    // ./CFML.g:214:1: cfml : ( tag )* ;
    public final cfml_return cfml() throws RecognitionException {
    traceIn("cfml", 1);
        cfml_return retval = new cfml_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        tag_return tag1 = null;



        try {
            // ./CFML.g:216:2: ( ( tag )* )
            // ./CFML.g:216:2: ( tag )*
            {
            root_0 = (Object)adaptor.nil();

            // ./CFML.g:216:2: ( tag )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==START_TAG_OPEN) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ./CFML.g:216:2: tag
            	    {
            	    pushFollow(FOLLOW_tag_in_cfml92);
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
    // ./CFML.g:219:1: tag : startTag ;
    public final tag_return tag() throws RecognitionException {
    traceIn("tag", 2);
        tag_return retval = new tag_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        startTag_return startTag2 = null;



        try {
            // ./CFML.g:221:3: ( startTag )
            // ./CFML.g:221:3: startTag
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_startTag_in_tag106);
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
    // ./CFML.g:224:1: startTag : (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) ) ;
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
            // ./CFML.g:227:2: ( (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) ) )
            // ./CFML.g:227:2: (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) )
            {
            // ./CFML.g:227:2: (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) )
            // ./CFML.g:228:2: sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) )
            {
            sto=(Token)input.LT(1);
            match(input,START_TAG_OPEN,FOLLOW_START_TAG_OPEN_in_startTag128); 
            stream_START_TAG_OPEN.add(sto);


            		String name = sto.getText().toLowerCase().substring(1);
            		((tagScope_scope)tagScope_stack.peek()).name = name;	
            	
            pushFollow(FOLLOW_tagInnerValues_in_startTag137);
            tagInnerValues3=tagInnerValues();
            _fsp--;

            stream_tagInnerValues.add(tagInnerValues3.getTree());
            stc=(Token)input.LT(1);
            match(input,START_TAG_CLOSE,FOLLOW_START_TAG_CLOSE_in_startTag144); 
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
            	
            pushFollow(FOLLOW_tagContent_in_startTag152);
            tc=tagContent();
            _fsp--;

            stream_tagContent.add(tc.getTree());
            // ./CFML.g:251:3: ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) )
            // ./CFML.g:252:3: 
            {

            // AST REWRITE
            // elements: START_TAG_CLOSE, tagInnerValues, START_TAG_CLOSE, tagInnerValues, tagInnerValues, tagContent, tagInnerValues, tagContent, START_TAG_OPEN, tagContent, tagContent, START_TAG_CLOSE, START_TAG_CLOSE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 252:3: -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
            if (isImportTag(name)) {
                // ./CFML.g:252:27: ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(IMPORTTAG, sto), root_1);

                adaptor.addChild(root_1, stream_tagInnerValues.next());
                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 253:3: -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
            if (isCustomTag(name)) {
                // ./CFML.g:253:27: ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(CUSTOMTAG, sto), root_1);

                adaptor.addChild(root_1, stream_tagInnerValues.next());
                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 254:3: -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
            if (isColdFusionTag(name)) {
                // ./CFML.g:254:31: ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
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
            else // 261:3: -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent )
            {
                // ./CFML.g:261:6: ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent )
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
    // ./CFML.g:266:1: tagContent : cfml ({...}? => ( endTag ) ) ;
    public final tagContent_return tagContent() throws RecognitionException {
    traceIn("tagContent", 4);
        tagContent_return retval = new tagContent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        cfml_return cfml4 = null;

        endTag_return endTag5 = null;



        try {
            // ./CFML.g:268:2: ( cfml ({...}? => ( endTag ) ) )
            // ./CFML.g:268:2: cfml ({...}? => ( endTag ) )
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_cfml_in_tagContent267);
            cfml4=cfml();
            _fsp--;

            adaptor.addChild(root_0, cfml4.getTree());
            // ./CFML.g:269:3: ({...}? => ( endTag ) )
            // ./CFML.g:270:3: {...}? => ( endTag )
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
            // ./CFML.g:284:3: ( endTag )
            // ./CFML.g:284:4: endTag
            {
            pushFollow(FOLLOW_endTag_in_tagContent286);
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
    // ./CFML.g:307:1: endTag : END_TAG_OPEN END_TAG_CLOSE ;
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
            // ./CFML.g:309:2: ( END_TAG_OPEN END_TAG_CLOSE )
            // ./CFML.g:309:2: END_TAG_OPEN END_TAG_CLOSE
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
            match(input,END_TAG_OPEN,FOLLOW_END_TAG_OPEN_in_endTag313); 
            END_TAG_OPEN6_tree = (Object)adaptor.create(END_TAG_OPEN6);
            root_0 = (Object)adaptor.becomeRoot(END_TAG_OPEN6_tree, root_0);

            END_TAG_CLOSE7=(Token)input.LT(1);
            match(input,END_TAG_CLOSE,FOLLOW_END_TAG_CLOSE_in_endTag316); 
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
    // ./CFML.g:327:1: tagInnerValues : ({...}? => ( ( tagAttribute )* ) | );
    public final tagInnerValues_return tagInnerValues() throws RecognitionException {
    traceIn("tagInnerValues", 6);
        tagInnerValues_return retval = new tagInnerValues_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        tagAttribute_return tagAttribute8 = null;



        try {
            // ./CFML.g:329:2: ({...}? => ( ( tagAttribute )* ) | )
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
                        new NoViableAltException("327:1: tagInnerValues : ({...}? => ( ( tagAttribute )* ) | );", 3, 2, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("327:1: tagInnerValues : ({...}? => ( ( tagAttribute )* ) | );", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ./CFML.g:329:2: {...}? => ( ( tagAttribute )* )
                    {
                    root_0 = (Object)adaptor.nil();

                    if ( !(
                    		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                    		||
                    		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                    		||
                    		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                    	) ) {
                        throw new FailedPredicateException(input, "tagInnerValues", "\n\t\t(isColdFusionTag($tagScope::name) && usesAttributes($tagScope::name))\n\t\t||\n\t\t(isCustomTag($tagScope::name))\n\t\t||\n\t\t(isImportTag($tagScope::name))\n\t");
                    }
                    // ./CFML.g:336:2: ( ( tagAttribute )* )
                    // ./CFML.g:337:3: ( tagAttribute )*
                    {
                    // ./CFML.g:337:3: ( tagAttribute )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==TAG_ATTRIBUTE) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // ./CFML.g:337:3: tagAttribute
                    	    {
                    	    pushFollow(FOLLOW_tagAttribute_in_tagInnerValues336);
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
                    // ./CFML.g:340:2: 
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
    // ./CFML.g:342:1: tagAttribute : TAG_ATTRIBUTE EQUALS stringLiteral ;
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
            // ./CFML.g:344:2: ( TAG_ATTRIBUTE EQUALS stringLiteral )
            // ./CFML.g:344:2: TAG_ATTRIBUTE EQUALS stringLiteral
            {
            root_0 = (Object)adaptor.nil();

            TAG_ATTRIBUTE9=(Token)input.LT(1);
            match(input,TAG_ATTRIBUTE,FOLLOW_TAG_ATTRIBUTE_in_tagAttribute355); 
            TAG_ATTRIBUTE9_tree = (Object)adaptor.create(TAG_ATTRIBUTE9);
            adaptor.addChild(root_0, TAG_ATTRIBUTE9_tree);

            EQUALS10=(Token)input.LT(1);
            match(input,EQUALS,FOLLOW_EQUALS_in_tagAttribute357); 
            EQUALS10_tree = (Object)adaptor.create(EQUALS10);
            adaptor.addChild(root_0, EQUALS10_tree);

            pushFollow(FOLLOW_stringLiteral_in_tagAttribute359);
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
    // ./CFML.g:347:1: stringLiteral : ( ( DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | ~ ( DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE ) )* DOUBLE_QUOTE ) | ( SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | ~ ( SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE ) )* SINGLE_QUOTE ) );
    public final stringLiteral_return stringLiteral() throws RecognitionException {
    traceIn("stringLiteral", 8);
        stringLiteral_return retval = new stringLiteral_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOUBLE_QUOTE12=null;
        Token set13=null;
        Token DOUBLE_QUOTE14=null;
        Token SINGLE_QUOTE15=null;
        Token set16=null;
        Token SINGLE_QUOTE17=null;

        Object DOUBLE_QUOTE12_tree=null;
        Object set13_tree=null;
        Object DOUBLE_QUOTE14_tree=null;
        Object SINGLE_QUOTE15_tree=null;
        Object set16_tree=null;
        Object SINGLE_QUOTE17_tree=null;

        try {
            // ./CFML.g:349:2: ( ( DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | ~ ( DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE ) )* DOUBLE_QUOTE ) | ( SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | ~ ( SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE ) )* SINGLE_QUOTE ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==DOUBLE_QUOTE) ) {
                alt6=1;
            }
            else if ( (LA6_0==SINGLE_QUOTE) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("347:1: stringLiteral : ( ( DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | ~ ( DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE ) )* DOUBLE_QUOTE ) | ( SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | ~ ( SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE ) )* SINGLE_QUOTE ) );", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // ./CFML.g:349:2: ( DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | ~ ( DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE ) )* DOUBLE_QUOTE )
                    {
                    root_0 = (Object)adaptor.nil();

                    // ./CFML.g:349:2: ( DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | ~ ( DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE ) )* DOUBLE_QUOTE )
                    // ./CFML.g:349:3: DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | ~ ( DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE ) )* DOUBLE_QUOTE
                    {
                    DOUBLE_QUOTE12=(Token)input.LT(1);
                    match(input,DOUBLE_QUOTE,FOLLOW_DOUBLE_QUOTE_in_stringLiteral373); 
                    DOUBLE_QUOTE12_tree = (Object)adaptor.create(DOUBLE_QUOTE12);
                    adaptor.addChild(root_0, DOUBLE_QUOTE12_tree);

                    // ./CFML.g:349:16: ( ESCAPE_DOUBLE_QUOTE | ~ ( DOUBLE_QUOTE | ESCAPE_DOUBLE_QUOTE ) )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0>=CFTAG && LA4_0<=EQUALS)||(LA4_0>=ESCAPE_DOUBLE_QUOTE && LA4_0<=OTHER)) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // ./CFML.g:
                    	    {
                    	    set13=(Token)input.LT(1);
                    	    if ( (input.LA(1)>=CFTAG && input.LA(1)<=EQUALS)||(input.LA(1)>=ESCAPE_DOUBLE_QUOTE && input.LA(1)<=OTHER) ) {
                    	        input.consume();
                    	        adaptor.addChild(root_0, adaptor.create(set13));
                    	        errorRecovery=false;
                    	    }
                    	    else {
                    	        MismatchedSetException mse =
                    	            new MismatchedSetException(null,input);
                    	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_stringLiteral375);    throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    DOUBLE_QUOTE14=(Token)input.LT(1);
                    match(input,DOUBLE_QUOTE,FOLLOW_DOUBLE_QUOTE_in_stringLiteral395); 
                    DOUBLE_QUOTE14_tree = (Object)adaptor.create(DOUBLE_QUOTE14);
                    adaptor.addChild(root_0, DOUBLE_QUOTE14_tree);


                    }


                    }
                    break;
                case 2 :
                    // ./CFML.g:351:2: ( SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | ~ ( SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE ) )* SINGLE_QUOTE )
                    {
                    root_0 = (Object)adaptor.nil();

                    // ./CFML.g:351:2: ( SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | ~ ( SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE ) )* SINGLE_QUOTE )
                    // ./CFML.g:351:3: SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | ~ ( SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE ) )* SINGLE_QUOTE
                    {
                    SINGLE_QUOTE15=(Token)input.LT(1);
                    match(input,SINGLE_QUOTE,FOLLOW_SINGLE_QUOTE_in_stringLiteral403); 
                    SINGLE_QUOTE15_tree = (Object)adaptor.create(SINGLE_QUOTE15);
                    adaptor.addChild(root_0, SINGLE_QUOTE15_tree);

                    // ./CFML.g:351:16: ( ESCAPE_SINGLE_QUOTE | ~ ( SINGLE_QUOTE | ESCAPE_SINGLE_QUOTE ) )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0>=CFTAG && LA5_0<=ESCAPE_DOUBLE_QUOTE)||(LA5_0>=ESCAPE_SINGLE_QUOTE && LA5_0<=OTHER)) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // ./CFML.g:
                    	    {
                    	    set16=(Token)input.LT(1);
                    	    if ( (input.LA(1)>=CFTAG && input.LA(1)<=ESCAPE_DOUBLE_QUOTE)||(input.LA(1)>=ESCAPE_SINGLE_QUOTE && input.LA(1)<=OTHER) ) {
                    	        input.consume();
                    	        adaptor.addChild(root_0, adaptor.create(set16));
                    	        errorRecovery=false;
                    	    }
                    	    else {
                    	        MismatchedSetException mse =
                    	            new MismatchedSetException(null,input);
                    	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_stringLiteral405);    throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);

                    SINGLE_QUOTE17=(Token)input.LT(1);
                    match(input,SINGLE_QUOTE,FOLLOW_SINGLE_QUOTE_in_stringLiteral425); 
                    SINGLE_QUOTE17_tree = (Object)adaptor.create(SINGLE_QUOTE17);
                    adaptor.addChild(root_0, SINGLE_QUOTE17_tree);


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
    traceOut("stringLiteral", 8);
        }
        return retval;
    }
    // $ANTLR end stringLiteral


 

    public static final BitSet FOLLOW_tag_in_cfml92 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_startTag_in_tag106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_START_TAG_OPEN_in_startTag128 = new BitSet(new long[]{0x0000000000000900L});
    public static final BitSet FOLLOW_tagInnerValues_in_startTag137 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_START_TAG_CLOSE_in_startTag144 = new BitSet(new long[]{0x0000000000000280L});
    public static final BitSet FOLLOW_tagContent_in_startTag152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfml_in_tagContent267 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_endTag_in_tagContent286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_END_TAG_OPEN_in_endTag313 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_END_TAG_CLOSE_in_endTag316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tagAttribute_in_tagInnerValues336 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_TAG_ATTRIBUTE_in_tagAttribute355 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_EQUALS_in_tagAttribute357 = new BitSet(new long[]{0x000000000000A000L});
    public static final BitSet FOLLOW_stringLiteral_in_tagAttribute359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_QUOTE_in_stringLiteral373 = new BitSet(new long[]{0x0000000003FFFFF0L});
    public static final BitSet FOLLOW_set_in_stringLiteral375 = new BitSet(new long[]{0x0000000003FFFFF0L});
    public static final BitSet FOLLOW_DOUBLE_QUOTE_in_stringLiteral395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SINGLE_QUOTE_in_stringLiteral403 = new BitSet(new long[]{0x0000000003FFFFF0L});
    public static final BitSet FOLLOW_set_in_stringLiteral405 = new BitSet(new long[]{0x0000000003FFFFF0L});
    public static final BitSet FOLLOW_SINGLE_QUOTE_in_stringLiteral425 = new BitSet(new long[]{0x0000000000000002L});

}