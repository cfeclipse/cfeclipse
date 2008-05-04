// $ANTLR 3.0.1 /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g 2008-04-23 07:14:07

package org.cfml.parser.antlr;

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CFTAG", "CUSTOMTAG", "IMPORTTAG", "STRING_LITERAL", "CFML_STATEMENT", "START_TAG_OPEN", "START_TAG_CLOSE", "END_TAG_OPEN", "END_TAG_CLOSE", "TAG_ATTRIBUTE", "EQUALS", "DOUBLE_QUOTE", "ESCAPE_DOUBLE_QUOTE", "DOUBLE_QUOTE_STRING", "SINGLE_QUOTE", "ESCAPE_SINGLE_QUOTE", "SINGLE_QUOTE_STRING", "CFML", "HASH", "ESCAPE_HASH", "HASH_CFML", "TAG_NAME", "LETTER", "DIGIT", "UNDERSCORE", "TAG_IDENT", "COLON", "WS", "COMMENT", "OTHER"
    };
    public static final int TAG_ATTRIBUTE=13;
    public static final int OTHER=33;
    public static final int LETTER=26;
    public static final int CFTAG=4;
    public static final int DOUBLE_QUOTE=15;
    public static final int HASH_CFML=24;
    public static final int CFML_STATEMENT=8;
    public static final int HASH=22;
    public static final int END_TAG_OPEN=11;
    public static final int UNDERSCORE=28;
    public static final int EQUALS=14;
    public static final int EOF=-1;
    public static final int START_TAG_CLOSE=10;
    public static final int ESCAPE_SINGLE_QUOTE=19;
    public static final int COLON=30;
    public static final int TAG_NAME=25;
    public static final int CUSTOMTAG=5;
    public static final int SINGLE_QUOTE=18;
    public static final int SINGLE_QUOTE_STRING=20;
    public static final int WS=31;
    public static final int STRING_LITERAL=7;
    public static final int TAG_IDENT=29;
    public static final int DOUBLE_QUOTE_STRING=17;
    public static final int END_TAG_CLOSE=12;
    public static final int ESCAPE_HASH=23;
    public static final int ESCAPE_DOUBLE_QUOTE=16;
    public static final int DIGIT=27;
    public static final int COMMENT=32;
    public static final int CFML=21;
    public static final int START_TAG_OPEN=9;
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
    public String getGrammarFileName() { return "/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g"; }


    	/**
    	* returns false.
    	*/
    	protected boolean isColdFusionTag(String name)
    	{		
    		boolean isColdfusion = name.toLowerCase().startsWith("cf");
    		System.out.println("isColdFusion: " + name + " : " + isColdfusion);
    		return isColdfusion;
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
    	
    	protected boolean allowsCFMLAssignment(String tagName)
    	{
    		boolean assign = tagName.toLowerCase().equals("cfset");
    		System.out.println("allowsCFMLAssignment : " + tagName + " : " + assign);
    		return assign;
    	}

    	protected boolean allowsCFMLCondition(String tagName)
    	{
    		return false;
    	}

    	protected boolean usesAttributes(String name)
    	{
    		boolean attrib = (name.toLowerCase().startsWith("cf") && !name.toLowerCase().equals("cfset"));
    		System.out.println("usesAttributes: " + name + " : " + attrib);
    		return attrib;
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
    		
    	protected Tree parseStringLiteral(Token start, Token stop)
    	{
    		return null;
    	}

    	protected Tree parseCFScript(Token start, Token stop)
    	{
    		return null;
    	}
    	
    	protected Tree parseCFMLCondition(Token start, Token stop)
    	{
    		return null;
    	}
    	
    	protected Tree parseCFMLAssignment(Token start, Token stop)
    	{
    		return null;
    	}


    public static class cfml_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cfml
    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:227:1: cfml : ( tag )* ;
    public final cfml_return cfml() throws RecognitionException {
        cfml_return retval = new cfml_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        tag_return tag1 = null;



        try {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:228:2: ( ( tag )* )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:229:2: ( tag )*
            {
            root_0 = (Object)adaptor.nil();

            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:229:2: ( tag )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==START_TAG_OPEN) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:229:2: tag
            	    {
            	    pushFollow(FOLLOW_tag_in_cfml100);
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
        }
        return retval;
    }
    // $ANTLR end cfml

    public static class tag_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start tag
    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:232:1: tag : startTag ;
    public final tag_return tag() throws RecognitionException {
        tag_return retval = new tag_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        startTag_return startTag2 = null;



        try {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:233:2: ( startTag )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:234:3: startTag
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_startTag_in_tag114);
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
        }
        return retval;
    }
    // $ANTLR end tag

    public static class startTag_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start startTag
    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:237:1: startTag : (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) ) ;
    public final startTag_return startTag() throws RecognitionException {
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
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:239:2: ( (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) ) )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:240:2: (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) )
            {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:240:2: (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:241:2: sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) )
            {
            sto=(Token)input.LT(1);
            match(input,START_TAG_OPEN,FOLLOW_START_TAG_OPEN_in_startTag136); 
            stream_START_TAG_OPEN.add(sto);


            		String name = sto.getText().toLowerCase().substring(1);
            		((tagScope_scope)tagScope_stack.peek()).name = name;	
            	
            pushFollow(FOLLOW_tagInnerValues_in_startTag145);
            tagInnerValues3=tagInnerValues();
            _fsp--;

            stream_tagInnerValues.add(tagInnerValues3.getTree());
            stc=(Token)input.LT(1);
            match(input,START_TAG_CLOSE,FOLLOW_START_TAG_CLOSE_in_startTag152); 
            stream_START_TAG_CLOSE.add(stc);


            		if(!stc.getText().equals("/>"))		
            		{
            			System.out.println("push: " + name);
            			((tagScope_scope)tagScope_stack.peek()).endTagName = name; 
            			getTagStack().addFirst(name);
            		}
            		else
            		{
            			((tagScope_scope)tagScope_stack.peek()).endTagName = ""; 
            			System.out.println("close: " + sto.getText().toLowerCase().substring(1));
            		}
            	
            pushFollow(FOLLOW_tagContent_in_startTag160);
            tc=tagContent();
            _fsp--;

            stream_tagContent.add(tc.getTree());
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:264:3: ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:265:3: 
            {

            // AST REWRITE
            // elements: START_TAG_CLOSE, tagInnerValues, tagContent, START_TAG_CLOSE, START_TAG_CLOSE, tagContent, START_TAG_CLOSE, tagContent, START_TAG_OPEN, tagInnerValues, tagInnerValues, tagContent, tagInnerValues
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 265:3: -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
            if (isImportTag(name)) {
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:265:27: ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(IMPORTTAG, sto), root_1);

                adaptor.addChild(root_1, stream_tagInnerValues.next());
                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 266:3: -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
            if (isCustomTag(name)) {
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:266:27: ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(CUSTOMTAG, sto), root_1);

                adaptor.addChild(root_1, stream_tagInnerValues.next());
                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 267:3: -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
            if (isColdFusionTag(name)) {
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:267:31: ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
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
            else // 274:3: -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent )
            {
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:274:6: ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent )
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
    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:279:1: tagContent : ( hashCFML )* cfml ({...}? => ( endTag ) ) ;
    public final tagContent_return tagContent() throws RecognitionException {
        tagContent_return retval = new tagContent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        hashCFML_return hashCFML4 = null;

        cfml_return cfml5 = null;

        endTag_return endTag6 = null;



        try {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:280:2: ( ( hashCFML )* cfml ({...}? => ( endTag ) ) )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:281:2: ( hashCFML )* cfml ({...}? => ( endTag ) )
            {
            root_0 = (Object)adaptor.nil();

            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:281:2: ( hashCFML )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==HASH) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:281:2: hashCFML
            	    {
            	    pushFollow(FOLLOW_hashCFML_in_tagContent275);
            	    hashCFML4=hashCFML();
            	    _fsp--;

            	    adaptor.addChild(root_0, hashCFML4.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            pushFollow(FOLLOW_cfml_in_tagContent279);
            cfml5=cfml();
            _fsp--;

            adaptor.addChild(root_0, cfml5.getTree());
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:283:3: ({...}? => ( endTag ) )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:284:3: {...}? => ( endTag )
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
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:298:3: ( endTag )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:298:4: endTag
            {
            pushFollow(FOLLOW_endTag_in_tagContent298);
            endTag6=endTag();
            _fsp--;

            adaptor.addChild(root_0, endTag6.getTree());

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
        }
        return retval;
    }
    // $ANTLR end tagContent

    public static class endTag_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start endTag
    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:321:1: endTag : END_TAG_OPEN END_TAG_CLOSE ;
    public final endTag_return endTag() throws RecognitionException {
        endTag_return retval = new endTag_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token END_TAG_OPEN7=null;
        Token END_TAG_CLOSE8=null;

        Object END_TAG_OPEN7_tree=null;
        Object END_TAG_CLOSE8_tree=null;

        try {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:322:2: ( END_TAG_OPEN END_TAG_CLOSE )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:323:2: END_TAG_OPEN END_TAG_CLOSE
            {
            root_0 = (Object)adaptor.nil();


            		String name = input.LT(1).getText().toLowerCase().substring(2);
            		
            		//clear off the chaff
            		while(!name.equals(getTagStack().peek()))
            		{
            			String pastTagName = getTagStack().removeFirst();
            			
            			System.out.println("popped: " + pastTagName);
            		}
            		
            		//pop off the last eleemnt
            		String pastTagName = getTagStack().removeFirst();
            		System.out.println("finally popped: " + pastTagName);
            	
            END_TAG_OPEN7=(Token)input.LT(1);
            match(input,END_TAG_OPEN,FOLLOW_END_TAG_OPEN_in_endTag325); 
            END_TAG_OPEN7_tree = (Object)adaptor.create(END_TAG_OPEN7);
            root_0 = (Object)adaptor.becomeRoot(END_TAG_OPEN7_tree, root_0);

            END_TAG_CLOSE8=(Token)input.LT(1);
            match(input,END_TAG_CLOSE,FOLLOW_END_TAG_CLOSE_in_endTag328); 
            END_TAG_CLOSE8_tree = (Object)adaptor.create(END_TAG_CLOSE8);
            adaptor.addChild(root_0, END_TAG_CLOSE8_tree);


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
        }
        return retval;
    }
    // $ANTLR end endTag

    public static class tagInnerValues_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start tagInnerValues
    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:341:1: tagInnerValues : ( ({...}? => ( tagAttribute )* ) | ({...}? => script ) | ({...}? => ( tagAttribute )* ) );
    public final tagInnerValues_return tagInnerValues() throws RecognitionException {
        tagInnerValues_return retval = new tagInnerValues_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        tagAttribute_return tagAttribute9 = null;

        script_return script10 = null;

        tagAttribute_return tagAttribute11 = null;



        try {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:342:2: ( ({...}? => ( tagAttribute )* ) | ({...}? => script ) | ({...}? => ( tagAttribute )* ) )
            int alt5=3;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:343:2: ({...}? => ( tagAttribute )* )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:343:2: ({...}? => ( tagAttribute )* )
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:344:2: {...}? => ( tagAttribute )*
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
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:350:7: ( tagAttribute )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==TAG_ATTRIBUTE) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:350:7: tagAttribute
                    	    {
                    	    pushFollow(FOLLOW_tagAttribute_in_tagInnerValues346);
                    	    tagAttribute9=tagAttribute();
                    	    _fsp--;

                    	    adaptor.addChild(root_0, tagAttribute9.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:353:2: ({...}? => script )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:353:2: ({...}? => script )
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:354:2: {...}? => script
                    {
                    if ( !(
                    	(	
                    		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                    		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                    		&&
                    		(
                    		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                    		 ||
                    		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                    		)
                    	)
                    	) ) {
                        throw new FailedPredicateException(input, "tagInnerValues", "\n\t(\t\n\t\tisColdFusionTag($tagScope::name) \n\t\t&& !usesAttributes($tagScope::name)\n\t\t&&\n\t\t(\n\t\t allowsCFMLCondition($tagScope::name)\n\t\t ||\n\t\t allowsCFMLAssignment($tagScope::name)\n\t\t)\n\t)\n\t");
                    }
                    pushFollow(FOLLOW_script_in_tagInnerValues362);
                    script10=script();
                    _fsp--;

                    adaptor.addChild(root_0, script10.getTree());

                    }


                    }
                    break;
                case 3 :
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:368:2: ({...}? => ( tagAttribute )* )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:368:2: ({...}? => ( tagAttribute )* )
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:369:2: {...}? => ( tagAttribute )*
                    {
                    if ( !(
                    			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                    	) ) {
                        throw new FailedPredicateException(input, "tagInnerValues", "\n\t\t\t!isColdFusionTag($tagScope::name)\n\t");
                    }
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:371:7: ( tagAttribute )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==TAG_ATTRIBUTE) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:371:7: tagAttribute
                    	    {
                    	    pushFollow(FOLLOW_tagAttribute_in_tagInnerValues377);
                    	    tagAttribute11=tagAttribute();
                    	    _fsp--;

                    	    adaptor.addChild(root_0, tagAttribute11.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);


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
        }
        return retval;
    }
    // $ANTLR end tagInnerValues

    public static class tagAttribute_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start tagAttribute
    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:375:1: tagAttribute : TAG_ATTRIBUTE EQUALS stringLiteral ;
    public final tagAttribute_return tagAttribute() throws RecognitionException {
        tagAttribute_return retval = new tagAttribute_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token TAG_ATTRIBUTE12=null;
        Token EQUALS13=null;
        stringLiteral_return stringLiteral14 = null;


        Object TAG_ATTRIBUTE12_tree=null;
        Object EQUALS13_tree=null;

        try {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:376:2: ( TAG_ATTRIBUTE EQUALS stringLiteral )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:377:2: TAG_ATTRIBUTE EQUALS stringLiteral
            {
            root_0 = (Object)adaptor.nil();

            TAG_ATTRIBUTE12=(Token)input.LT(1);
            match(input,TAG_ATTRIBUTE,FOLLOW_TAG_ATTRIBUTE_in_tagAttribute393); 
            TAG_ATTRIBUTE12_tree = (Object)adaptor.create(TAG_ATTRIBUTE12);
            adaptor.addChild(root_0, TAG_ATTRIBUTE12_tree);

            EQUALS13=(Token)input.LT(1);
            match(input,EQUALS,FOLLOW_EQUALS_in_tagAttribute395); 
            EQUALS13_tree = (Object)adaptor.create(EQUALS13);
            adaptor.addChild(root_0, EQUALS13_tree);

            pushFollow(FOLLOW_stringLiteral_in_tagAttribute397);
            stringLiteral14=stringLiteral();
            _fsp--;

            adaptor.addChild(root_0, stringLiteral14.getTree());

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
        }
        return retval;
    }
    // $ANTLR end tagAttribute

    public static class stringLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start stringLiteral
    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:380:1: stringLiteral : ( (start= DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING )* end= DOUBLE_QUOTE -> ^( STRING_LITERAL ) ) | (start= SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING )* end= SINGLE_QUOTE -> ^( STRING_LITERAL ) ) );
    public final stringLiteral_return stringLiteral() throws RecognitionException {
        stringLiteral_return retval = new stringLiteral_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token start=null;
        Token end=null;
        Token ESCAPE_DOUBLE_QUOTE15=null;
        Token DOUBLE_QUOTE_STRING16=null;
        Token ESCAPE_SINGLE_QUOTE17=null;
        Token SINGLE_QUOTE_STRING18=null;

        Object start_tree=null;
        Object end_tree=null;
        Object ESCAPE_DOUBLE_QUOTE15_tree=null;
        Object DOUBLE_QUOTE_STRING16_tree=null;
        Object ESCAPE_SINGLE_QUOTE17_tree=null;
        Object SINGLE_QUOTE_STRING18_tree=null;
        RewriteRuleTokenStream stream_SINGLE_QUOTE=new RewriteRuleTokenStream(adaptor,"token SINGLE_QUOTE");
        RewriteRuleTokenStream stream_SINGLE_QUOTE_STRING=new RewriteRuleTokenStream(adaptor,"token SINGLE_QUOTE_STRING");
        RewriteRuleTokenStream stream_ESCAPE_DOUBLE_QUOTE=new RewriteRuleTokenStream(adaptor,"token ESCAPE_DOUBLE_QUOTE");
        RewriteRuleTokenStream stream_DOUBLE_QUOTE_STRING=new RewriteRuleTokenStream(adaptor,"token DOUBLE_QUOTE_STRING");
        RewriteRuleTokenStream stream_DOUBLE_QUOTE=new RewriteRuleTokenStream(adaptor,"token DOUBLE_QUOTE");
        RewriteRuleTokenStream stream_ESCAPE_SINGLE_QUOTE=new RewriteRuleTokenStream(adaptor,"token ESCAPE_SINGLE_QUOTE");

        try {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:381:2: ( (start= DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING )* end= DOUBLE_QUOTE -> ^( STRING_LITERAL ) ) | (start= SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING )* end= SINGLE_QUOTE -> ^( STRING_LITERAL ) ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==DOUBLE_QUOTE) ) {
                alt8=1;
            }
            else if ( (LA8_0==SINGLE_QUOTE) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("380:1: stringLiteral : ( (start= DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING )* end= DOUBLE_QUOTE -> ^( STRING_LITERAL ) ) | (start= SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING )* end= SINGLE_QUOTE -> ^( STRING_LITERAL ) ) );", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:382:2: (start= DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING )* end= DOUBLE_QUOTE -> ^( STRING_LITERAL ) )
                    {
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:382:2: (start= DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING )* end= DOUBLE_QUOTE -> ^( STRING_LITERAL ) )
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:383:3: start= DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING )* end= DOUBLE_QUOTE
                    {
                    start=(Token)input.LT(1);
                    match(input,DOUBLE_QUOTE,FOLLOW_DOUBLE_QUOTE_in_stringLiteral416); 
                    stream_DOUBLE_QUOTE.add(start);

                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:383:22: ( ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING )*
                    loop6:
                    do {
                        int alt6=3;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==ESCAPE_DOUBLE_QUOTE) ) {
                            alt6=1;
                        }
                        else if ( (LA6_0==DOUBLE_QUOTE_STRING) ) {
                            alt6=2;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:383:23: ESCAPE_DOUBLE_QUOTE
                    	    {
                    	    ESCAPE_DOUBLE_QUOTE15=(Token)input.LT(1);
                    	    match(input,ESCAPE_DOUBLE_QUOTE,FOLLOW_ESCAPE_DOUBLE_QUOTE_in_stringLiteral419); 
                    	    stream_ESCAPE_DOUBLE_QUOTE.add(ESCAPE_DOUBLE_QUOTE15);


                    	    }
                    	    break;
                    	case 2 :
                    	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:383:45: DOUBLE_QUOTE_STRING
                    	    {
                    	    DOUBLE_QUOTE_STRING16=(Token)input.LT(1);
                    	    match(input,DOUBLE_QUOTE_STRING,FOLLOW_DOUBLE_QUOTE_STRING_in_stringLiteral423); 
                    	    stream_DOUBLE_QUOTE_STRING.add(DOUBLE_QUOTE_STRING16);


                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);

                    end=(Token)input.LT(1);
                    match(input,DOUBLE_QUOTE,FOLLOW_DOUBLE_QUOTE_in_stringLiteral429); 
                    stream_DOUBLE_QUOTE.add(end);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 384:3: -> ^( STRING_LITERAL )
                    {
                        // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:384:6: ^( STRING_LITERAL )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(STRING_LITERAL, "STRING_LITERAL"), root_1);

                        adaptor.addChild(root_1,  (parseStringLiteral(start, end)) );

                        adaptor.addChild(root_0, root_1);
                        }

                    }



                    }


                    }
                    break;
                case 2 :
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:387:2: (start= SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING )* end= SINGLE_QUOTE -> ^( STRING_LITERAL ) )
                    {
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:387:2: (start= SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING )* end= SINGLE_QUOTE -> ^( STRING_LITERAL ) )
                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:388:3: start= SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING )* end= SINGLE_QUOTE
                    {
                    start=(Token)input.LT(1);
                    match(input,SINGLE_QUOTE,FOLLOW_SINGLE_QUOTE_in_stringLiteral454); 
                    stream_SINGLE_QUOTE.add(start);

                    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:388:22: ( ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING )*
                    loop7:
                    do {
                        int alt7=3;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==ESCAPE_SINGLE_QUOTE) ) {
                            alt7=1;
                        }
                        else if ( (LA7_0==SINGLE_QUOTE_STRING) ) {
                            alt7=2;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:388:23: ESCAPE_SINGLE_QUOTE
                    	    {
                    	    ESCAPE_SINGLE_QUOTE17=(Token)input.LT(1);
                    	    match(input,ESCAPE_SINGLE_QUOTE,FOLLOW_ESCAPE_SINGLE_QUOTE_in_stringLiteral457); 
                    	    stream_ESCAPE_SINGLE_QUOTE.add(ESCAPE_SINGLE_QUOTE17);


                    	    }
                    	    break;
                    	case 2 :
                    	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:388:45: SINGLE_QUOTE_STRING
                    	    {
                    	    SINGLE_QUOTE_STRING18=(Token)input.LT(1);
                    	    match(input,SINGLE_QUOTE_STRING,FOLLOW_SINGLE_QUOTE_STRING_in_stringLiteral461); 
                    	    stream_SINGLE_QUOTE_STRING.add(SINGLE_QUOTE_STRING18);


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    end=(Token)input.LT(1);
                    match(input,SINGLE_QUOTE,FOLLOW_SINGLE_QUOTE_in_stringLiteral467); 
                    stream_SINGLE_QUOTE.add(end);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 389:3: -> ^( STRING_LITERAL )
                    {
                        // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:389:6: ^( STRING_LITERAL )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(STRING_LITERAL, "STRING_LITERAL"), root_1);

                        adaptor.addChild(root_1,  (parseStringLiteral(start, end)) );

                        adaptor.addChild(root_0, root_1);
                        }

                    }



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
        }
        return retval;
    }
    // $ANTLR end stringLiteral

    public static class script_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start script
    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:393:1: script : ( TAG_ATTRIBUTE | stringLiteral | EQUALS | CFML )* -> { allowsCFMLCondition($tagScope::name) }? ^( CFML_STATEMENT ) -> ^( CFML_STATEMENT ) ;
    public final script_return script() throws RecognitionException {
        script_return retval = new script_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token TAG_ATTRIBUTE19=null;
        Token EQUALS21=null;
        Token CFML22=null;
        stringLiteral_return stringLiteral20 = null;


        Object TAG_ATTRIBUTE19_tree=null;
        Object EQUALS21_tree=null;
        Object CFML22_tree=null;
        RewriteRuleTokenStream stream_TAG_ATTRIBUTE=new RewriteRuleTokenStream(adaptor,"token TAG_ATTRIBUTE");
        RewriteRuleTokenStream stream_EQUALS=new RewriteRuleTokenStream(adaptor,"token EQUALS");
        RewriteRuleTokenStream stream_CFML=new RewriteRuleTokenStream(adaptor,"token CFML");
        RewriteRuleSubtreeStream stream_stringLiteral=new RewriteRuleSubtreeStream(adaptor,"rule stringLiteral");
        try {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:394:2: ( ( TAG_ATTRIBUTE | stringLiteral | EQUALS | CFML )* -> { allowsCFMLCondition($tagScope::name) }? ^( CFML_STATEMENT ) -> ^( CFML_STATEMENT ) )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:395:2: ( TAG_ATTRIBUTE | stringLiteral | EQUALS | CFML )*
            {

            	Token start = input.LT(1);
            	
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:398:2: ( TAG_ATTRIBUTE | stringLiteral | EQUALS | CFML )*
            loop9:
            do {
                int alt9=5;
                switch ( input.LA(1) ) {
                case TAG_ATTRIBUTE:
                    {
                    alt9=1;
                    }
                    break;
                case DOUBLE_QUOTE:
                case SINGLE_QUOTE:
                    {
                    alt9=2;
                    }
                    break;
                case EQUALS:
                    {
                    alt9=3;
                    }
                    break;
                case CFML:
                    {
                    alt9=4;
                    }
                    break;

                }

                switch (alt9) {
            	case 1 :
            	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:398:3: TAG_ATTRIBUTE
            	    {
            	    TAG_ATTRIBUTE19=(Token)input.LT(1);
            	    match(input,TAG_ATTRIBUTE,FOLLOW_TAG_ATTRIBUTE_in_script496); 
            	    stream_TAG_ATTRIBUTE.add(TAG_ATTRIBUTE19);


            	    }
            	    break;
            	case 2 :
            	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:398:19: stringLiteral
            	    {
            	    pushFollow(FOLLOW_stringLiteral_in_script500);
            	    stringLiteral20=stringLiteral();
            	    _fsp--;

            	    stream_stringLiteral.add(stringLiteral20.getTree());

            	    }
            	    break;
            	case 3 :
            	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:398:35: EQUALS
            	    {
            	    EQUALS21=(Token)input.LT(1);
            	    match(input,EQUALS,FOLLOW_EQUALS_in_script504); 
            	    stream_EQUALS.add(EQUALS21);


            	    }
            	    break;
            	case 4 :
            	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:398:44: CFML
            	    {
            	    CFML22=(Token)input.LT(1);
            	    match(input,CFML,FOLLOW_CFML_in_script508); 
            	    stream_CFML.add(CFML22);


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


            	Token stop = input.LT(-1);
            	System.out.println("start: " + start.getText() + " stop: " + stop.getText());
            	

            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 403:2: -> { allowsCFMLCondition($tagScope::name) }? ^( CFML_STATEMENT )
            if ( allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name) ) {
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:403:47: ^( CFML_STATEMENT )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(CFML_STATEMENT, "CFML_STATEMENT"), root_1);

                adaptor.addChild(root_1,  parseCFMLCondition(start, stop) );

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 404:2: -> ^( CFML_STATEMENT )
            {
                // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:404:5: ^( CFML_STATEMENT )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(CFML_STATEMENT, "CFML_STATEMENT"), root_1);

                adaptor.addChild(root_1,  parseCFMLAssignment(start, stop) );

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
        }
        return retval;
    }
    // $ANTLR end script

    public static class hashCFML_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start hashCFML
    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:407:1: hashCFML : HASH ( ESCAPE_HASH | HASH_CFML )* HASH ;
    public final hashCFML_return hashCFML() throws RecognitionException {
        hashCFML_return retval = new hashCFML_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token HASH23=null;
        Token set24=null;
        Token HASH25=null;

        Object HASH23_tree=null;
        Object set24_tree=null;
        Object HASH25_tree=null;

        try {
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:408:2: ( HASH ( ESCAPE_HASH | HASH_CFML )* HASH )
            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:409:2: HASH ( ESCAPE_HASH | HASH_CFML )* HASH
            {
            root_0 = (Object)adaptor.nil();

            HASH23=(Token)input.LT(1);
            match(input,HASH,FOLLOW_HASH_in_hashCFML545); 
            HASH23_tree = (Object)adaptor.create(HASH23);
            adaptor.addChild(root_0, HASH23_tree);

            // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:409:7: ( ESCAPE_HASH | HASH_CFML )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>=ESCAPE_HASH && LA10_0<=HASH_CFML)) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // /Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/CFML.g:
            	    {
            	    set24=(Token)input.LT(1);
            	    if ( (input.LA(1)>=ESCAPE_HASH && input.LA(1)<=HASH_CFML) ) {
            	        input.consume();
            	        adaptor.addChild(root_0, adaptor.create(set24));
            	        errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_hashCFML547);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            HASH25=(Token)input.LT(1);
            match(input,HASH,FOLLOW_HASH_in_hashCFML556); 
            HASH25_tree = (Object)adaptor.create(HASH25);
            adaptor.addChild(root_0, HASH25_tree);


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
        }
        return retval;
    }
    // $ANTLR end hashCFML


    protected DFA5 dfa5 = new DFA5(this);
    static final String DFA5_eotS =
        "\22\uffff";
    static final String DFA5_eofS =
        "\22\uffff";
    static final String DFA5_minS =
        "\2\12\1\0\1\uffff\1\12\2\uffff\1\17\1\22\2\17\1\0\2\22\1\0\3\uffff";
    static final String DFA5_maxS =
        "\2\25\1\0\1\uffff\1\25\2\uffff\1\21\1\24\2\21\1\0\2\24\1\0\3\uffff";
    static final String DFA5_acceptS =
        "\3\uffff\1\2\1\uffff\1\1\1\3\10\uffff\1\1\2\3";
    static final String DFA5_specialS =
        "\1\4\1\10\1\3\1\uffff\1\13\2\uffff\1\7\1\0\1\11\1\6\1\1\1\2\1\12"+
        "\1\5\3\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\2\2\uffff\1\1\2\3\2\uffff\1\3\2\uffff\1\3",
            "\1\3\2\uffff\1\3\1\4\1\3\2\uffff\1\3\2\uffff\1\3",
            "\1\uffff",
            "",
            "\1\3\2\uffff\2\3\1\7\2\uffff\1\10\2\uffff\1\3",
            "",
            "",
            "\1\13\1\11\1\12",
            "\1\16\1\14\1\15",
            "\1\13\1\11\1\12",
            "\1\13\1\11\1\12",
            "\1\uffff",
            "\1\16\1\14\1\15",
            "\1\16\1\14\1\15",
            "\1\uffff",
            "",
            "",
            ""
    };

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
        int numStates = DFA5_transitionS.length;
        DFA5_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
        }
    }

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = DFA5_eot;
            this.eof = DFA5_eof;
            this.min = DFA5_min;
            this.max = DFA5_max;
            this.accept = DFA5_accept;
            this.special = DFA5_special;
            this.transition = DFA5_transition;
        }
        public String getDescription() {
            return "341:1: tagInnerValues : ( ({...}? => ( tagAttribute )* ) | ({...}? => script ) | ({...}? => ( tagAttribute )* ) );";
        }
        public int specialStateTransition(int s, IntStream input) throws NoViableAltException {
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA5_8 = input.LA(1);

                         
                        int index5_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA5_8==ESCAPE_SINGLE_QUOTE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 12;}

                        else if ( (LA5_8==SINGLE_QUOTE_STRING) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 13;}

                        else if ( (LA5_8==SINGLE_QUOTE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 14;}

                         
                        input.seek(index5_8);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA5_11 = input.LA(1);

                         
                        int index5_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	) ) {s = 15;}

                        else if ( (
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	) ) {s = 3;}

                        else if ( (
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	) ) {s = 16;}

                         
                        input.seek(index5_11);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA5_12 = input.LA(1);

                         
                        int index5_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA5_12==SINGLE_QUOTE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 14;}

                        else if ( (LA5_12==ESCAPE_SINGLE_QUOTE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 12;}

                        else if ( (LA5_12==SINGLE_QUOTE_STRING) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 13;}

                         
                        input.seek(index5_12);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA5_2 = input.LA(1);

                         
                        int index5_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	) ) {s = 5;}

                        else if ( (
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	) ) {s = 3;}

                        else if ( (
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	) ) {s = 6;}

                         
                        input.seek(index5_2);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA5_0 = input.LA(1);

                         
                        int index5_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA5_0==TAG_ATTRIBUTE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 1;}

                        else if ( (LA5_0==START_TAG_CLOSE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 2;}

                        else if ( ((LA5_0>=EQUALS && LA5_0<=DOUBLE_QUOTE)||LA5_0==SINGLE_QUOTE||LA5_0==CFML) && (
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	)) {s = 3;}

                         
                        input.seek(index5_0);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA5_14 = input.LA(1);

                         
                        int index5_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	) ) {s = 15;}

                        else if ( (
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	) ) {s = 3;}

                        else if ( (
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	) ) {s = 17;}

                         
                        input.seek(index5_14);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA5_10 = input.LA(1);

                         
                        int index5_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA5_10==DOUBLE_QUOTE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 11;}

                        else if ( (LA5_10==ESCAPE_DOUBLE_QUOTE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 9;}

                        else if ( (LA5_10==DOUBLE_QUOTE_STRING) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 10;}

                         
                        input.seek(index5_10);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA5_7 = input.LA(1);

                         
                        int index5_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA5_7==ESCAPE_DOUBLE_QUOTE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 9;}

                        else if ( (LA5_7==DOUBLE_QUOTE_STRING) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 10;}

                        else if ( (LA5_7==DOUBLE_QUOTE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 11;}

                         
                        input.seek(index5_7);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA5_1 = input.LA(1);

                         
                        int index5_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA5_1==EQUALS) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 4;}

                        else if ( (LA5_1==START_TAG_CLOSE||LA5_1==TAG_ATTRIBUTE||LA5_1==DOUBLE_QUOTE||LA5_1==SINGLE_QUOTE||LA5_1==CFML) && (
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	)) {s = 3;}

                         
                        input.seek(index5_1);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA5_9 = input.LA(1);

                         
                        int index5_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA5_9==DOUBLE_QUOTE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 11;}

                        else if ( (LA5_9==ESCAPE_DOUBLE_QUOTE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 9;}

                        else if ( (LA5_9==DOUBLE_QUOTE_STRING) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 10;}

                         
                        input.seek(index5_9);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA5_13 = input.LA(1);

                         
                        int index5_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA5_13==SINGLE_QUOTE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 14;}

                        else if ( (LA5_13==ESCAPE_SINGLE_QUOTE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 12;}

                        else if ( (LA5_13==SINGLE_QUOTE_STRING) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 13;}

                         
                        input.seek(index5_13);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA5_4 = input.LA(1);

                         
                        int index5_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA5_4==START_TAG_CLOSE||(LA5_4>=TAG_ATTRIBUTE && LA5_4<=EQUALS)||LA5_4==CFML) && (
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	)) {s = 3;}

                        else if ( (LA5_4==DOUBLE_QUOTE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 7;}

                        else if ( (LA5_4==SINGLE_QUOTE) && ((
                        	(	
                        		isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) 
                        		&& !usesAttributes(((tagScope_scope)tagScope_stack.peek()).name)
                        		&&
                        		(
                        		 allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name)
                        		 ||
                        		 allowsCFMLAssignment(((tagScope_scope)tagScope_stack.peek()).name)
                        		)
                        	)
                        	||
                        		(isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name) && usesAttributes(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isCustomTag(((tagScope_scope)tagScope_stack.peek()).name))
                        		||
                        		(isImportTag(((tagScope_scope)tagScope_stack.peek()).name))
                        	||
                        			!isColdFusionTag(((tagScope_scope)tagScope_stack.peek()).name)
                        	))) {s = 8;}

                         
                        input.seek(index5_4);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 5, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_tag_in_cfml100 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_startTag_in_tag114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_START_TAG_OPEN_in_startTag136 = new BitSet(new long[]{0x000000000024E400L});
    public static final BitSet FOLLOW_tagInnerValues_in_startTag145 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_START_TAG_CLOSE_in_startTag152 = new BitSet(new long[]{0x0000000000400A00L});
    public static final BitSet FOLLOW_tagContent_in_startTag160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hashCFML_in_tagContent275 = new BitSet(new long[]{0x0000000000400A00L});
    public static final BitSet FOLLOW_cfml_in_tagContent279 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_endTag_in_tagContent298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_END_TAG_OPEN_in_endTag325 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_END_TAG_CLOSE_in_endTag328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tagAttribute_in_tagInnerValues346 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_script_in_tagInnerValues362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tagAttribute_in_tagInnerValues377 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_TAG_ATTRIBUTE_in_tagAttribute393 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_EQUALS_in_tagAttribute395 = new BitSet(new long[]{0x0000000000048000L});
    public static final BitSet FOLLOW_stringLiteral_in_tagAttribute397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_QUOTE_in_stringLiteral416 = new BitSet(new long[]{0x0000000000038000L});
    public static final BitSet FOLLOW_ESCAPE_DOUBLE_QUOTE_in_stringLiteral419 = new BitSet(new long[]{0x0000000000038000L});
    public static final BitSet FOLLOW_DOUBLE_QUOTE_STRING_in_stringLiteral423 = new BitSet(new long[]{0x0000000000038000L});
    public static final BitSet FOLLOW_DOUBLE_QUOTE_in_stringLiteral429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SINGLE_QUOTE_in_stringLiteral454 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_ESCAPE_SINGLE_QUOTE_in_stringLiteral457 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_SINGLE_QUOTE_STRING_in_stringLiteral461 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_SINGLE_QUOTE_in_stringLiteral467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TAG_ATTRIBUTE_in_script496 = new BitSet(new long[]{0x000000000024E002L});
    public static final BitSet FOLLOW_stringLiteral_in_script500 = new BitSet(new long[]{0x000000000024E002L});
    public static final BitSet FOLLOW_EQUALS_in_script504 = new BitSet(new long[]{0x000000000024E002L});
    public static final BitSet FOLLOW_CFML_in_script508 = new BitSet(new long[]{0x000000000024E002L});
    public static final BitSet FOLLOW_HASH_in_hashCFML545 = new BitSet(new long[]{0x0000000001C00000L});
    public static final BitSet FOLLOW_set_in_hashCFML547 = new BitSet(new long[]{0x0000000001C00000L});
    public static final BitSet FOLLOW_HASH_in_hashCFML556 = new BitSet(new long[]{0x0000000000000002L});

}