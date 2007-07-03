// $ANTLR 3.0 ./CFML.g 2007-07-03 16:57:58

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CFTAG", "CUSTOMTAG", "IMPORTTAG", "STRING_LITERAL", "CFML_STATEMENT", "START_TAG_OPEN", "START_TAG_CLOSE", "END_TAG_OPEN", "END_TAG_CLOSE", "TAG_ATTRIBUTE", "EQUALS", "DOUBLE_QUOTE", "ESCAPE_DOUBLE_QUOTE", "DOUBLE_QUOTE_STRING", "SINGLE_QUOTE", "ESCAPE_SINGLE_QUOTE", "SINGLE_QUOTE_STRING", "HASH", "CFML", "TAG_NAME", "LETTER", "DIGIT", "UNDERSCORE", "TAG_IDENT", "COLON", "WS", "COMMENT", "OTHER"
    };
    public static final int TAG_ATTRIBUTE=1013;
    public static final int OTHER=1031;
    public static final int LETTER=1024;
    public static final int CFTAG=1004;
    public static final int DOUBLE_QUOTE=1015;
    public static final int CFML_STATEMENT=1008;
    public static final int HASH=1021;
    public static final int END_TAG_OPEN=1011;
    public static final int UNDERSCORE=1026;
    public static final int EQUALS=1014;
    public static final int EOF=-1;
    public static final int START_TAG_CLOSE=1010;
    public static final int ESCAPE_SINGLE_QUOTE=1019;
    public static final int COLON=1028;
    public static final int TAG_NAME=1023;
    public static final int CUSTOMTAG=1005;
    public static final int SINGLE_QUOTE=1018;
    public static final int SINGLE_QUOTE_STRING=1020;
    public static final int WS=1029;
    public static final int STRING_LITERAL=1007;
    public static final int TAG_IDENT=1027;
    public static final int DOUBLE_QUOTE_STRING=1017;
    public static final int END_TAG_CLOSE=1012;
    public static final int ESCAPE_DOUBLE_QUOTE=1016;
    public static final int DIGIT=1025;
    public static final int COMMENT=1030;
    public static final int CFML=1022;
    public static final int START_TAG_OPEN=1009;
    public static final int IMPORTTAG=1006;
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
    		System.out.println(((CommonTokenStream)input).getTokens(start.getTokenIndex(), stop.getTokenIndex()));
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
    // ./CFML.g:229:1: cfml : ( tag )* ;
    public final cfml_return cfml() throws RecognitionException {
    traceIn("cfml", 1);
        cfml_return retval = new cfml_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        tag_return tag1 = null;



        try {
            // ./CFML.g:231:2: ( ( tag )* )
            // ./CFML.g:231:2: ( tag )*
            {
            root_0 = (Object)adaptor.nil();

            // ./CFML.g:231:2: ( tag )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==START_TAG_OPEN) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ./CFML.g:231:2: tag
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
    // ./CFML.g:234:1: tag : startTag ;
    public final tag_return tag() throws RecognitionException {
    traceIn("tag", 2);
        tag_return retval = new tag_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        startTag_return startTag2 = null;



        try {
            // ./CFML.g:236:3: ( startTag )
            // ./CFML.g:236:3: startTag
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
    // ./CFML.g:239:1: startTag : (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) ) ;
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
            // ./CFML.g:242:2: ( (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) ) )
            // ./CFML.g:242:2: (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) )
            {
            // ./CFML.g:242:2: (sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) ) )
            // ./CFML.g:243:2: sto= START_TAG_OPEN tagInnerValues stc= START_TAG_CLOSE tc= tagContent ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) )
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
            			getTagStack().push(name);
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
            // ./CFML.g:266:3: ( -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent ) -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent ) )
            // ./CFML.g:267:3: 
            {

            // AST REWRITE
            // elements: tagInnerValues, START_TAG_CLOSE, START_TAG_CLOSE, START_TAG_CLOSE, START_TAG_CLOSE, START_TAG_OPEN, tagContent, tagInnerValues, tagContent, tagInnerValues, tagContent, tagInnerValues, tagContent
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 267:3: -> {isImportTag(name)}? ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
            if (isImportTag(name)) {
                // ./CFML.g:267:27: ^( IMPORTTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(IMPORTTAG, sto), root_1);

                adaptor.addChild(root_1, stream_tagInnerValues.next());
                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 268:3: -> {isCustomTag(name)}? ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
            if (isCustomTag(name)) {
                // ./CFML.g:268:27: ^( CUSTOMTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(CUSTOMTAG, sto), root_1);

                adaptor.addChild(root_1, stream_tagInnerValues.next());
                adaptor.addChild(root_1, stream_START_TAG_CLOSE.next());
                adaptor.addChild(root_1, stream_tagContent.next());

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 269:3: -> {isColdFusionTag(name)}? ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
            if (isColdFusionTag(name)) {
                // ./CFML.g:269:31: ^( CFTAG[$sto] tagInnerValues START_TAG_CLOSE tagContent )
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
            else // 276:3: -> ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent )
            {
                // ./CFML.g:276:6: ^( START_TAG_OPEN START_TAG_CLOSE tagInnerValues tagContent )
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
    // ./CFML.g:281:1: tagContent : cfml ({...}? => ( endTag ) ) ;
    public final tagContent_return tagContent() throws RecognitionException {
    traceIn("tagContent", 4);
        tagContent_return retval = new tagContent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        cfml_return cfml4 = null;

        endTag_return endTag5 = null;



        try {
            // ./CFML.g:283:2: ( cfml ({...}? => ( endTag ) ) )
            // ./CFML.g:283:2: cfml ({...}? => ( endTag ) )
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_cfml_in_tagContent275);
            cfml4=cfml();
            _fsp--;

            adaptor.addChild(root_0, cfml4.getTree());
            // ./CFML.g:284:3: ({...}? => ( endTag ) )
            // ./CFML.g:285:3: {...}? => ( endTag )
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
            // ./CFML.g:299:3: ( endTag )
            // ./CFML.g:299:4: endTag
            {
            pushFollow(FOLLOW_endTag_in_tagContent294);
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
    // ./CFML.g:322:1: endTag : END_TAG_OPEN END_TAG_CLOSE ;
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
            // ./CFML.g:324:2: ( END_TAG_OPEN END_TAG_CLOSE )
            // ./CFML.g:324:2: END_TAG_OPEN END_TAG_CLOSE
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
            match(input,END_TAG_OPEN,FOLLOW_END_TAG_OPEN_in_endTag321); 
            END_TAG_OPEN6_tree = (Object)adaptor.create(END_TAG_OPEN6);
            root_0 = (Object)adaptor.becomeRoot(END_TAG_OPEN6_tree, root_0);

            END_TAG_CLOSE7=(Token)input.LT(1);
            match(input,END_TAG_CLOSE,FOLLOW_END_TAG_CLOSE_in_endTag324); 
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
    // ./CFML.g:342:1: tagInnerValues : ( ({...}? => ( tagAttribute )* ) | ({...}? => script ) | );
    public final tagInnerValues_return tagInnerValues() throws RecognitionException {
    traceIn("tagInnerValues", 6);
        tagInnerValues_return retval = new tagInnerValues_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        tagAttribute_return tagAttribute8 = null;

        script_return script9 = null;



        try {
            // ./CFML.g:344:2: ( ({...}? => ( tagAttribute )* ) | ({...}? => script ) | )
            int alt3=3;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // ./CFML.g:344:2: ({...}? => ( tagAttribute )* )
                    {
                    root_0 = (Object)adaptor.nil();

                    // ./CFML.g:344:2: ({...}? => ( tagAttribute )* )
                    // ./CFML.g:345:2: {...}? => ( tagAttribute )*
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
                    // ./CFML.g:351:7: ( tagAttribute )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==TAG_ATTRIBUTE) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // ./CFML.g:351:7: tagAttribute
                    	    {
                    	    pushFollow(FOLLOW_tagAttribute_in_tagInnerValues342);
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
                    // ./CFML.g:354:2: ({...}? => script )
                    {
                    root_0 = (Object)adaptor.nil();

                    // ./CFML.g:354:2: ({...}? => script )
                    // ./CFML.g:355:2: {...}? => script
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
                    pushFollow(FOLLOW_script_in_tagInnerValues358);
                    script9=script();
                    _fsp--;

                    adaptor.addChild(root_0, script9.getTree());

                    }


                    }
                    break;
                case 3 :
                    // ./CFML.g:369:2: 
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
    // ./CFML.g:371:1: tagAttribute : TAG_ATTRIBUTE EQUALS stringLiteral ;
    public final tagAttribute_return tagAttribute() throws RecognitionException {
    traceIn("tagAttribute", 7);
        tagAttribute_return retval = new tagAttribute_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token TAG_ATTRIBUTE10=null;
        Token EQUALS11=null;
        stringLiteral_return stringLiteral12 = null;


        Object TAG_ATTRIBUTE10_tree=null;
        Object EQUALS11_tree=null;

        try {
            // ./CFML.g:373:2: ( TAG_ATTRIBUTE EQUALS stringLiteral )
            // ./CFML.g:373:2: TAG_ATTRIBUTE EQUALS stringLiteral
            {
            root_0 = (Object)adaptor.nil();

            TAG_ATTRIBUTE10=(Token)input.LT(1);
            match(input,TAG_ATTRIBUTE,FOLLOW_TAG_ATTRIBUTE_in_tagAttribute376); 
            TAG_ATTRIBUTE10_tree = (Object)adaptor.create(TAG_ATTRIBUTE10);
            adaptor.addChild(root_0, TAG_ATTRIBUTE10_tree);

            EQUALS11=(Token)input.LT(1);
            match(input,EQUALS,FOLLOW_EQUALS_in_tagAttribute378); 
            EQUALS11_tree = (Object)adaptor.create(EQUALS11);
            adaptor.addChild(root_0, EQUALS11_tree);

            pushFollow(FOLLOW_stringLiteral_in_tagAttribute380);
            stringLiteral12=stringLiteral();
            _fsp--;

            adaptor.addChild(root_0, stringLiteral12.getTree());

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
    // ./CFML.g:376:1: stringLiteral : (start= DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING )* end= DOUBLE_QUOTE -> ^( STRING_LITERAL ) | start= SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING )* end= SINGLE_QUOTE -> ^( STRING_LITERAL ) );
    public final stringLiteral_return stringLiteral() throws RecognitionException {
    traceIn("stringLiteral", 8);
        stringLiteral_return retval = new stringLiteral_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token start=null;
        Token end=null;
        Token ESCAPE_DOUBLE_QUOTE13=null;
        Token DOUBLE_QUOTE_STRING14=null;
        Token ESCAPE_SINGLE_QUOTE15=null;
        Token SINGLE_QUOTE_STRING16=null;

        Object start_tree=null;
        Object end_tree=null;
        Object ESCAPE_DOUBLE_QUOTE13_tree=null;
        Object DOUBLE_QUOTE_STRING14_tree=null;
        Object ESCAPE_SINGLE_QUOTE15_tree=null;
        Object SINGLE_QUOTE_STRING16_tree=null;
        RewriteRuleTokenStream stream_SINGLE_QUOTE=new RewriteRuleTokenStream(adaptor,"token SINGLE_QUOTE");
        RewriteRuleTokenStream stream_SINGLE_QUOTE_STRING=new RewriteRuleTokenStream(adaptor,"token SINGLE_QUOTE_STRING");
        RewriteRuleTokenStream stream_ESCAPE_DOUBLE_QUOTE=new RewriteRuleTokenStream(adaptor,"token ESCAPE_DOUBLE_QUOTE");
        RewriteRuleTokenStream stream_DOUBLE_QUOTE_STRING=new RewriteRuleTokenStream(adaptor,"token DOUBLE_QUOTE_STRING");
        RewriteRuleTokenStream stream_DOUBLE_QUOTE=new RewriteRuleTokenStream(adaptor,"token DOUBLE_QUOTE");
        RewriteRuleTokenStream stream_ESCAPE_SINGLE_QUOTE=new RewriteRuleTokenStream(adaptor,"token ESCAPE_SINGLE_QUOTE");

        try {
            // ./CFML.g:378:2: (start= DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING )* end= DOUBLE_QUOTE -> ^( STRING_LITERAL ) | start= SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING )* end= SINGLE_QUOTE -> ^( STRING_LITERAL ) )
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
                    new NoViableAltException("376:1: stringLiteral : (start= DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING )* end= DOUBLE_QUOTE -> ^( STRING_LITERAL ) | start= SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING )* end= SINGLE_QUOTE -> ^( STRING_LITERAL ) );", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // ./CFML.g:378:2: start= DOUBLE_QUOTE ( ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING )* end= DOUBLE_QUOTE
                    {
                    start=(Token)input.LT(1);
                    match(input,DOUBLE_QUOTE,FOLLOW_DOUBLE_QUOTE_in_stringLiteral395); 
                    stream_DOUBLE_QUOTE.add(start);

                    // ./CFML.g:378:21: ( ESCAPE_DOUBLE_QUOTE | DOUBLE_QUOTE_STRING )*
                    loop4:
                    do {
                        int alt4=3;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==ESCAPE_DOUBLE_QUOTE) ) {
                            alt4=1;
                        }
                        else if ( (LA4_0==DOUBLE_QUOTE_STRING) ) {
                            alt4=2;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // ./CFML.g:378:22: ESCAPE_DOUBLE_QUOTE
                    	    {
                    	    ESCAPE_DOUBLE_QUOTE13=(Token)input.LT(1);
                    	    match(input,ESCAPE_DOUBLE_QUOTE,FOLLOW_ESCAPE_DOUBLE_QUOTE_in_stringLiteral398); 
                    	    stream_ESCAPE_DOUBLE_QUOTE.add(ESCAPE_DOUBLE_QUOTE13);


                    	    }
                    	    break;
                    	case 2 :
                    	    // ./CFML.g:378:44: DOUBLE_QUOTE_STRING
                    	    {
                    	    DOUBLE_QUOTE_STRING14=(Token)input.LT(1);
                    	    match(input,DOUBLE_QUOTE_STRING,FOLLOW_DOUBLE_QUOTE_STRING_in_stringLiteral402); 
                    	    stream_DOUBLE_QUOTE_STRING.add(DOUBLE_QUOTE_STRING14);


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    end=(Token)input.LT(1);
                    match(input,DOUBLE_QUOTE,FOLLOW_DOUBLE_QUOTE_in_stringLiteral408); 
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
                    // 379:2: -> ^( STRING_LITERAL )
                    {
                        // ./CFML.g:379:5: ^( STRING_LITERAL )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(STRING_LITERAL, "STRING_LITERAL"), root_1);

                        adaptor.addChild(root_1,  (parseStringLiteral(start, end)) );

                        adaptor.addChild(root_0, root_1);
                        }

                    }



                    }
                    break;
                case 2 :
                    // ./CFML.g:381:2: start= SINGLE_QUOTE ( ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING )* end= SINGLE_QUOTE
                    {
                    start=(Token)input.LT(1);
                    match(input,SINGLE_QUOTE,FOLLOW_SINGLE_QUOTE_in_stringLiteral425); 
                    stream_SINGLE_QUOTE.add(start);

                    // ./CFML.g:381:21: ( ESCAPE_SINGLE_QUOTE | SINGLE_QUOTE_STRING )*
                    loop5:
                    do {
                        int alt5=3;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==ESCAPE_SINGLE_QUOTE) ) {
                            alt5=1;
                        }
                        else if ( (LA5_0==SINGLE_QUOTE_STRING) ) {
                            alt5=2;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // ./CFML.g:381:22: ESCAPE_SINGLE_QUOTE
                    	    {
                    	    ESCAPE_SINGLE_QUOTE15=(Token)input.LT(1);
                    	    match(input,ESCAPE_SINGLE_QUOTE,FOLLOW_ESCAPE_SINGLE_QUOTE_in_stringLiteral428); 
                    	    stream_ESCAPE_SINGLE_QUOTE.add(ESCAPE_SINGLE_QUOTE15);


                    	    }
                    	    break;
                    	case 2 :
                    	    // ./CFML.g:381:44: SINGLE_QUOTE_STRING
                    	    {
                    	    SINGLE_QUOTE_STRING16=(Token)input.LT(1);
                    	    match(input,SINGLE_QUOTE_STRING,FOLLOW_SINGLE_QUOTE_STRING_in_stringLiteral432); 
                    	    stream_SINGLE_QUOTE_STRING.add(SINGLE_QUOTE_STRING16);


                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);

                    end=(Token)input.LT(1);
                    match(input,SINGLE_QUOTE,FOLLOW_SINGLE_QUOTE_in_stringLiteral438); 
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
                    // 382:2: -> ^( STRING_LITERAL )
                    {
                        // ./CFML.g:382:5: ^( STRING_LITERAL )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(STRING_LITERAL, "STRING_LITERAL"), root_1);

                        adaptor.addChild(root_1,  (parseStringLiteral(start, end)) );

                        adaptor.addChild(root_0, root_1);
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
    traceOut("stringLiteral", 8);
        }
        return retval;
    }
    // $ANTLR end stringLiteral

    public static class script_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start script
    // ./CFML.g:385:1: script : ( TAG_ATTRIBUTE | stringLiteral | HASH | EQUALS | CFML )* -> { allowsCFMLCondition($tagScope::name) }? ^( CFML_STATEMENT ) -> ^( CFML_STATEMENT ) ;
    public final script_return script() throws RecognitionException {
    traceIn("script", 9);
        script_return retval = new script_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token TAG_ATTRIBUTE17=null;
        Token HASH19=null;
        Token EQUALS20=null;
        Token CFML21=null;
        stringLiteral_return stringLiteral18 = null;


        Object TAG_ATTRIBUTE17_tree=null;
        Object HASH19_tree=null;
        Object EQUALS20_tree=null;
        Object CFML21_tree=null;
        RewriteRuleTokenStream stream_HASH=new RewriteRuleTokenStream(adaptor,"token HASH");
        RewriteRuleTokenStream stream_TAG_ATTRIBUTE=new RewriteRuleTokenStream(adaptor,"token TAG_ATTRIBUTE");
        RewriteRuleTokenStream stream_EQUALS=new RewriteRuleTokenStream(adaptor,"token EQUALS");
        RewriteRuleTokenStream stream_CFML=new RewriteRuleTokenStream(adaptor,"token CFML");
        RewriteRuleSubtreeStream stream_stringLiteral=new RewriteRuleSubtreeStream(adaptor,"rule stringLiteral");
        try {
            // ./CFML.g:387:2: ( ( TAG_ATTRIBUTE | stringLiteral | HASH | EQUALS | CFML )* -> { allowsCFMLCondition($tagScope::name) }? ^( CFML_STATEMENT ) -> ^( CFML_STATEMENT ) )
            // ./CFML.g:387:2: ( TAG_ATTRIBUTE | stringLiteral | HASH | EQUALS | CFML )*
            {

            	Token start = input.LT(1);
            	
            // ./CFML.g:390:2: ( TAG_ATTRIBUTE | stringLiteral | HASH | EQUALS | CFML )*
            loop7:
            do {
                int alt7=6;
                switch ( input.LA(1) ) {
                case TAG_ATTRIBUTE:
                    {
                    alt7=1;
                    }
                    break;
                case DOUBLE_QUOTE:
                case SINGLE_QUOTE:
                    {
                    alt7=2;
                    }
                    break;
                case HASH:
                    {
                    alt7=3;
                    }
                    break;
                case EQUALS:
                    {
                    alt7=4;
                    }
                    break;
                case CFML:
                    {
                    alt7=5;
                    }
                    break;

                }

                switch (alt7) {
            	case 1 :
            	    // ./CFML.g:390:3: TAG_ATTRIBUTE
            	    {
            	    TAG_ATTRIBUTE17=(Token)input.LT(1);
            	    match(input,TAG_ATTRIBUTE,FOLLOW_TAG_ATTRIBUTE_in_script463); 
            	    stream_TAG_ATTRIBUTE.add(TAG_ATTRIBUTE17);


            	    }
            	    break;
            	case 2 :
            	    // ./CFML.g:390:19: stringLiteral
            	    {
            	    pushFollow(FOLLOW_stringLiteral_in_script467);
            	    stringLiteral18=stringLiteral();
            	    _fsp--;

            	    stream_stringLiteral.add(stringLiteral18.getTree());

            	    }
            	    break;
            	case 3 :
            	    // ./CFML.g:390:35: HASH
            	    {
            	    HASH19=(Token)input.LT(1);
            	    match(input,HASH,FOLLOW_HASH_in_script471); 
            	    stream_HASH.add(HASH19);


            	    }
            	    break;
            	case 4 :
            	    // ./CFML.g:390:42: EQUALS
            	    {
            	    EQUALS20=(Token)input.LT(1);
            	    match(input,EQUALS,FOLLOW_EQUALS_in_script475); 
            	    stream_EQUALS.add(EQUALS20);


            	    }
            	    break;
            	case 5 :
            	    // ./CFML.g:390:51: CFML
            	    {
            	    CFML21=(Token)input.LT(1);
            	    match(input,CFML,FOLLOW_CFML_in_script479); 
            	    stream_CFML.add(CFML21);


            	    }
            	    break;

            	default :
            	    break loop7;
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
            // 395:2: -> { allowsCFMLCondition($tagScope::name) }? ^( CFML_STATEMENT )
            if ( allowsCFMLCondition(((tagScope_scope)tagScope_stack.peek()).name) ) {
                // ./CFML.g:395:47: ^( CFML_STATEMENT )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(CFML_STATEMENT, "CFML_STATEMENT"), root_1);

                adaptor.addChild(root_1,  parseCFMLCondition(start, stop) );

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 396:2: -> ^( CFML_STATEMENT )
            {
                // ./CFML.g:396:5: ^( CFML_STATEMENT )
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
    traceOut("script", 9);
        }
        return retval;
    }
    // $ANTLR end script


    protected DFA3 dfa3 = new DFA3(this);
    static final String DFA3_eotS =
        "\20\uffff";
    static final String DFA3_eofS =
        "\20\uffff";
    static final String DFA3_minS =
        "\2\12\1\0\1\uffff\1\12\2\uffff\1\17\1\22\2\17\1\0\2\22\1\0\1\uffff";
    static final String DFA3_maxS =
        "\2\26\1\0\1\uffff\1\26\2\uffff\1\21\1\24\2\21\1\0\2\24\1\0\1\uffff";
    static final String DFA3_acceptS =
        "\3\uffff\1\2\1\uffff\1\1\1\3\10\uffff\1\1";
    static final String DFA3_specialS =
        "\1\4\1\12\1\7\1\uffff\1\13\2\uffff\1\5\1\2\1\1\1\0\1\3\1\10\1\11"+
        "\1\6\1\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\2\2\uffff\1\1\2\3\2\uffff\1\3\2\uffff\2\3",
            "\1\3\2\uffff\1\3\1\4\1\3\2\uffff\1\3\2\uffff\2\3",
            "\1\uffff",
            "",
            "\1\3\2\uffff\2\3\1\7\2\uffff\1\10\2\uffff\2\3",
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
            ""
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "342:1: tagInnerValues : ( ({...}? => ( tagAttribute )* ) | ({...}? => script ) | );";
        }
        public int specialStateTransition(int s, IntStream input) throws NoViableAltException {
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA3_10 = input.LA(1);

                         
                        int index3_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_10==DOUBLE_QUOTE) && ((
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
                        	))) {s = 11;}

                        else if ( (LA3_10==ESCAPE_DOUBLE_QUOTE) && ((
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
                        	))) {s = 9;}

                        else if ( (LA3_10==DOUBLE_QUOTE_STRING) && ((
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
                        	))) {s = 10;}

                         
                        input.seek(index3_10);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA3_9 = input.LA(1);

                         
                        int index3_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_9==DOUBLE_QUOTE) && ((
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
                        	))) {s = 11;}

                        else if ( (LA3_9==ESCAPE_DOUBLE_QUOTE) && ((
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
                        	))) {s = 9;}

                        else if ( (LA3_9==DOUBLE_QUOTE_STRING) && ((
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
                        	))) {s = 10;}

                         
                        input.seek(index3_9);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA3_8 = input.LA(1);

                         
                        int index3_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_8==ESCAPE_SINGLE_QUOTE) && ((
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
                        	))) {s = 12;}

                        else if ( (LA3_8==SINGLE_QUOTE_STRING) && ((
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
                        	))) {s = 13;}

                        else if ( (LA3_8==SINGLE_QUOTE) && ((
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
                        	))) {s = 14;}

                         
                        input.seek(index3_8);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA3_11 = input.LA(1);

                         
                        int index3_11 = input.index();
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

                         
                        input.seek(index3_11);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA3_0 = input.LA(1);

                         
                        int index3_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_0==TAG_ATTRIBUTE) && ((
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
                        	))) {s = 1;}

                        else if ( (LA3_0==START_TAG_CLOSE) ) {s = 2;}

                        else if ( ((LA3_0>=EQUALS && LA3_0<=DOUBLE_QUOTE)||LA3_0==SINGLE_QUOTE||(LA3_0>=HASH && LA3_0<=CFML)) && (
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

                         
                        input.seek(index3_0);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA3_7 = input.LA(1);

                         
                        int index3_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_7==ESCAPE_DOUBLE_QUOTE) && ((
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
                        	))) {s = 9;}

                        else if ( (LA3_7==DOUBLE_QUOTE_STRING) && ((
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
                        	))) {s = 10;}

                        else if ( (LA3_7==DOUBLE_QUOTE) && ((
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
                        	))) {s = 11;}

                         
                        input.seek(index3_7);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA3_14 = input.LA(1);

                         
                        int index3_14 = input.index();
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

                         
                        input.seek(index3_14);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA3_2 = input.LA(1);

                         
                        int index3_2 = input.index();
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

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index3_2);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA3_12 = input.LA(1);

                         
                        int index3_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_12==SINGLE_QUOTE) && ((
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
                        	))) {s = 14;}

                        else if ( (LA3_12==ESCAPE_SINGLE_QUOTE) && ((
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
                        	))) {s = 12;}

                        else if ( (LA3_12==SINGLE_QUOTE_STRING) && ((
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
                        	))) {s = 13;}

                         
                        input.seek(index3_12);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA3_13 = input.LA(1);

                         
                        int index3_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_13==SINGLE_QUOTE) && ((
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
                        	))) {s = 14;}

                        else if ( (LA3_13==ESCAPE_SINGLE_QUOTE) && ((
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
                        	))) {s = 12;}

                        else if ( (LA3_13==SINGLE_QUOTE_STRING) && ((
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
                        	))) {s = 13;}

                         
                        input.seek(index3_13);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA3_1 = input.LA(1);

                         
                        int index3_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_1==EQUALS) && ((
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
                        	))) {s = 4;}

                        else if ( (LA3_1==START_TAG_CLOSE||LA3_1==TAG_ATTRIBUTE||LA3_1==DOUBLE_QUOTE||LA3_1==SINGLE_QUOTE||(LA3_1>=HASH && LA3_1<=CFML)) && (
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

                         
                        input.seek(index3_1);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA3_4 = input.LA(1);

                         
                        int index3_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_4==DOUBLE_QUOTE) && ((
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
                        	))) {s = 7;}

                        else if ( (LA3_4==SINGLE_QUOTE) && ((
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
                        	))) {s = 8;}

                        else if ( (LA3_4==START_TAG_CLOSE||(LA3_4>=TAG_ATTRIBUTE && LA3_4<=EQUALS)||(LA3_4>=HASH && LA3_4<=CFML)) && (
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

                         
                        input.seek(index3_4);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 3, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_tag_in_cfml100 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_startTag_in_tag114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_START_TAG_OPEN_in_startTag136 = new BitSet(new long[]{0x000000000064E400L});
    public static final BitSet FOLLOW_tagInnerValues_in_startTag145 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_START_TAG_CLOSE_in_startTag152 = new BitSet(new long[]{0x0000000000000A00L});
    public static final BitSet FOLLOW_tagContent_in_startTag160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cfml_in_tagContent275 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_endTag_in_tagContent294 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_END_TAG_OPEN_in_endTag321 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_END_TAG_CLOSE_in_endTag324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tagAttribute_in_tagInnerValues342 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_script_in_tagInnerValues358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TAG_ATTRIBUTE_in_tagAttribute376 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_EQUALS_in_tagAttribute378 = new BitSet(new long[]{0x0000000000048000L});
    public static final BitSet FOLLOW_stringLiteral_in_tagAttribute380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_QUOTE_in_stringLiteral395 = new BitSet(new long[]{0x0000000000038000L});
    public static final BitSet FOLLOW_ESCAPE_DOUBLE_QUOTE_in_stringLiteral398 = new BitSet(new long[]{0x0000000000038000L});
    public static final BitSet FOLLOW_DOUBLE_QUOTE_STRING_in_stringLiteral402 = new BitSet(new long[]{0x0000000000038000L});
    public static final BitSet FOLLOW_DOUBLE_QUOTE_in_stringLiteral408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SINGLE_QUOTE_in_stringLiteral425 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_ESCAPE_SINGLE_QUOTE_in_stringLiteral428 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_SINGLE_QUOTE_STRING_in_stringLiteral432 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_SINGLE_QUOTE_in_stringLiteral438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TAG_ATTRIBUTE_in_script463 = new BitSet(new long[]{0x000000000064E002L});
    public static final BitSet FOLLOW_stringLiteral_in_script467 = new BitSet(new long[]{0x000000000064E002L});
    public static final BitSet FOLLOW_HASH_in_script471 = new BitSet(new long[]{0x000000000064E002L});
    public static final BitSet FOLLOW_EQUALS_in_script475 = new BitSet(new long[]{0x000000000064E002L});
    public static final BitSet FOLLOW_CFML_in_script479 = new BitSet(new long[]{0x000000000064E002L});

}
