/*
 * Created on Mar 21, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver Tupman
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */

package com.rohanclan.cfml.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.MarkerUtilities;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.editors.CFMLEditor;
import com.rohanclan.cfml.parser.exception.DuplicateAttributeException;
import com.rohanclan.cfml.parser.exception.InvalidAttributeException;
import com.rohanclan.cfml.parser.exception.InvalidChildItemException;
import com.rohanclan.cfml.parser.cfmltagitems.*;



import com.rohanclan.cfml.parser.ParseError;
import com.rohanclan.cfml.parser.ParseMessage;

/*
 Nasty, bastard test data for the parser:
 
<html>
	<cffunction name="test">
		<cfargument name="fred" test="test"/>
		<cfscript>
			WriteOutput("FREDFREDFRED");
		</cfscript>
		<cfif thisisatest is 1>
			<cfoutput>asdfasdf</cfoutput>
		</cfif>
	</cffunction>

	<cfset fred = 2/>
	<cfset test(fred)/>
	<cffunction name="test" >
		<cfargument name="test" default="#WriteOutput("">"")#"/> <!--- I think this is valid! --->
	</cffunction>
	<cfoutput>
		This is a <b>test</b>
	</cfoutput>
	<table>
		<tr>
			<td style="<cfoutput>#somethinghere#</cfoutput>">asdfasdf</td>
			<td style="fred"></td>
		</td>
	</table>
</html>

 */

/**
 * @author Oliver Tupman
 *
 * CFParser basically parses a CF file and builds the document tree from the result.
 * 
 * Currently the document tree only contains CF elements as the HTML handling isn't implemented.
 * HTML tags are matched, just not inserted into the document tree.
 * I believe we need some kind of server-side/client-side view. The reasoning for this is that
 * at the client-side one only sees HTML and no CF. But the documents we are viewing can contain
 * embedded CF therefore breaking some of the attributes - we may not know the correct values 
 * because the embedded CF decides it. 
 */
public class CFParser implements IEditorActionDelegate {

	/**
	 * <code>REG_TAG</code> - the regular expression for matching tags. NB: Doesn't work on multi-line tags :(
	 * TODO: Either modify the REG_TAG regex to match multiline tags or completely rewrite the tag matcher.
	 */
	static protected final String REG_TAG = "<(\\w*)(.*)/{0,1}>";
	/**
	 * <code>REG_ATTRIBUTES</code> - regular expression for getting the attributes out of a tag match.
	 * \s*(\w*)="(\w*)"
	 */
	static protected final String REG_ATTRIBUTES = "\\s*(\\w*)=\"(\\w*)\"";
	
	static protected final int USRMSG_INFO 		= 0x00;
	static protected final int USRMSG_WARNING 	= 0x01;
	static protected final int USRMSG_ERROR		= 0x02;
	
	/**
	 * 
	 * @author Oliver Tupman
	 *
	 * Represents the current state of the parser.
	 */
	public class State {
		protected ArrayList messages = new ArrayList();
		protected String filename;
		protected int errCount = 0;
		protected boolean hadFatal = false;
		protected MatchList matches = new MatchList();
		
		//
		// The following is to keep track of function & variable names
		// TODO: I think the following should be a map so we can store the doc items against name for type recognition, etc.
		protected HashMap functionNames = new HashMap();
		protected HashMap variableNames = new HashMap();
		
		static public final int ADD_BEFORE = 0x01;
		static public final int ADD_AFTER =  0x02;
		
		public void addFunction(TagItem newFunction)
		{
			String funcName = newFunction.getAttribute("name");
			if(functionNames.containsKey(funcName))
			{
				addMessage(new ParseError(newFunction.lineNumber, newFunction.startPosition,
												newFunction.endPosition, newFunction.getItemData(), 
												"Duplicate function \'" + funcName + "\' found."));
			}
			else
				functionNames.put(funcName, funcName);
		}
		
		public MatchList getMatches()
		{
			return matches;
		}
		
		public State(String docFilename)
		{
			filename = docFilename;
		}
		
		public ArrayList getMessages()
		{
			return messages;
		}
		
		public void addMatch(TagMatch newMatch, int position, int numIndicies)
		{
			switch(position)
			{
				case ADD_BEFORE:
					matches.add(matches.size() - numIndicies, newMatch);
					break;
				case ADD_AFTER:
					addMatch(newMatch);
					break;
				default:
					// Should this raise an exception?
					break;
			}
		}
		
		public void addMatch(TagMatch newMatch)
		{
			matches.add(newMatch);
		}
		
		public boolean hadFatal() { return hadFatal; }
		
		/**
		 * Adds a message to the parser state.
		 * @param newMsg
		 */
		public void addMessage(ParseMessage newMsg)
		{
			if(newMsg instanceof ParseError)
			{
				if(((ParseError)newMsg).isFatal())
					hadFatal = true;
				
				errCount++;				
			}
			
			messages.add(newMsg);
		}
	}
	
	protected State parserState = null;
	
	protected IResource res = null;

	
	/**
	 * <code>parseDoc</code> - the document to parse. Could just use a string, but IDocument provides line number capabilities.
	 */
	protected IDocument parseDoc = null;
	/**
	 * <code>docFilename</code> - the pathname of the document, so we can stick messages into the Problems tasklist
	 */
	protected IPath docFilename = null;	// Document file info... not working just yet.
	/**
	 * <code>editor</code> - the TextEditor for the action (not used at present)
	 */
	protected ITextEditor editor = null;	// If this actually works as an action, we'll need this.
	/**
	 * <code>parseResult</code> - the resultant document tree.
	 * <b>NB:</b> Currently the root node is called 'root' and has no real data, it's just a root node.
	 */
	protected CFDocument parseResult = null;	// The end result of the parse.
	
	/*
	 * 
	 * @author Oliver Tupman
	 *
	 * This is simply a alias for an ArrayList, used to show what the return result really is.
	 */
	public class MatchList extends ArrayList {	}	// Just so I know what the array is!
	
	/**
	 * <code>getParseResult</code> - Get's the document tree from a parse 
	 * @return The CF document tree that results from calling <code>parseDoc()</code>
	 */
	public CFDocument getParseResult()	{ return parseResult; }

	
	/**
	 * @author Oliver Tupman
	 *
	 * <code>TagMatch</code> is a class to represent a match within a document. 
	 */
	public class TagMatch {
		/**
		 * <code>match</code> - The full text of the tag match made.
		 */
		public String match;
		/**
		 * <code>startPos</code> - The document offset where the match began
		 */
		public int startPos;
		/**
		 * <code>endPos</code> - The document offset where the match ended.
		 */
		public int endPos;
		
		/**
		 * <code>lineNumber</code> - the line number on which this occured.
		 * TODO: Actually set the line number.
		 */
		public int lineNumber;
		
		/**
		 * <code>TagMatch</code> - Constructor for the TagMatch class. 
		 * @param text
		 * @param start
		 * @param end
		 */
		public TagMatch(String text, int start, int end, int lineNum)
		{
			match = text;
			startPos = start;
			endPos = end;
			lineNumber = lineNum;
		}
	}
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if( targetEditor instanceof ITextEditor || targetEditor instanceof CFMLEditor )
		{
			editor = (ITextEditor)targetEditor;
			IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput()); 
			this.parseDoc = doc;
		}
		
		//IEditorPart iep = this.getViewSite().getWorkbenchWindow().getActivePage().getActiveEditor();
		
		//IDocument testdoc = ((ITextEditor)targetEditor).getDocumentProvider().getDocument(targetEditor);
		//this.parseDoc = testdoc;
		//System.err.println(">>>" + testdoc + "<<<");
	}

	public void run() {
		parseResult = parseDoc();
	}

	public void run(IAction arg0) {
		parseResult = parseDoc();
		
	}
	public void selectionChanged(IAction arg0, ISelection arg1) { ; }
	
	/**
	 * <code>CFParser</code> Constructor without params.
	 * 
	 * Just sets the parseDoc to null.
	 *
	 */
	public CFParser() {
		super();
		parseDoc = null;
	}
	
	/**
	 * <code>CFParser</code> - Constructor.
	 *  
	 * @param doc2Parse - the IDocument doc to parse
	 * @param filename - the pathname of the file so we can attempt to report stuff to the user
	 */
	public CFParser(IDocument doc2Parse, IResource newRes)
	{
		parseDoc = doc2Parse;
		res = newRes;
	}

	/**
	 * <code>GetTagMatches</code> - Scans the document's data for tags.
	 * 
	 * Uses the regulary expression library to scan through the document building the 
	 * tag list. 
	 * 
	 * NB: The doc tree construction could occur here, but just doing the tag matching
	 * here allows us to separate the two processes. 
	 * 
	 * TODO: Investigate whether DIY'ing it would be better. Oh, and get it to properly ignore comments.
	 * 
	 * @param inDoc - the document to run the tag matcher over.
	 * @return an Array of TagMatches
	 */
	protected MatchList getTagMatches(IDocument inDoc)
	{
		MatchList matches = new MatchList();
		String inText = inDoc.get();
		
		Matcher matcher;
		Pattern pattern;
	
		pattern = Pattern.compile(REG_TAG);
		matcher = pattern.matcher(inText);
		
		while(matcher.find())
		{
			int lineNumber = 0;
			try {
				lineNumber = inDoc.getLineOfOffset(matcher.start());
			System.err.println("Line " + inDoc.getLineOfOffset(matcher.start()) + ": Got the text \'" + matcher.group() + "\'");
			}catch(BadLocationException excep) {
				//System.err.println("Apparently the match was out of the document!");
				userMessage(0, "getTagMatches", "Apparently the match was out of the document!");
			}
			matches.add(new TagMatch(matcher.group(), matcher.start(), matcher.end(), lineNumber));
		}
		
		return matches;
	}
	
	/**
	 * The line offsets within the parsed file.
	 * Element 0 is line 1 and the value is the end of the line doc offset
	 * Element 1 is line 2 and the value is the end of line 2 in a doc offset, and so on.
	 */
	protected int [] lineOffsets = null;

	/**
	 * Returns the line number that the document offset lies on.
	 * @param docOffset
	 * @return the line number, 0 indexed (i.e. 0 is line 1)
	 */
	protected int getLineNumber(int docOffset)/* throws BadLocationException */
	{
		int retval = 0;
		/*
		 * Line number calculation simply goes through the lineOffset var looking for when the 
		 * line offset is greater than the docOffset passed.
		 * 
		 * Should this method throw a BadLocationException???
		 */
		
		//
		// Quick check to make sure it's not negative.
		if(docOffset < 0)
			return 0;//throw new BadLocationException("Doc offset is less than 0. Value was " + docOffset);
		
		for(int i = 0; i < lineOffsets.length; i++)
		{
			if(lineOffsets[i] > docOffset)
				return i;
		}
		//
		// We should never reach here. Throw a BadLocationException;
		//throw new BadLocationException("Doc offset " + docOffset + " is out of range. Max value is " + lineOffsets[lineOffsets.length]);
		return 0;
	}	
	
	/**
	 * Calculates line numbers.
	 * 
	 * @param inputData - the string to scan
	 */
	protected void calcLineNumbers(String inputData)
	{
		/*
		 * This is a very simple line scanner. It simply runs the regex \r\n that
		 * should search for line breaks (assuming a CRLF method). If it finds 0
		 * it runs again with the line break method \n. Otherwise we finally
		 * try \r.
		 * 
		 * Having done that we simply loop through the matches. Each match is a line,
		 * the end value being the end position of the line in document offsets.
		 */
		int [] lineOffs = null;
		try {
			MatchList matches = new MatchList();
			String inText = inputData;
			Matcher matcher;
			Pattern pattern;
	
			pattern = Pattern.compile("(\\r\\n)");
			matcher = pattern.matcher(inText);
			
			if(!matcher.find(0))
			{
				pattern = Pattern.compile("(\\n)");
				matcher = pattern.matcher(inText);
				if(!matcher.find(0))
				{
					pattern = Pattern.compile("(\\r)");
					matcher = pattern.matcher(inText);
				}
			}
			if(matcher.find(0))
			{
				
				int lineCnt = 0;
				while(matcher.find())
					lineCnt++;

				lineOffs = new int[lineCnt+2];
				matcher = pattern.matcher(inText);
				lineCnt = 0;
				while(matcher.find())
				{
					lineOffs[lineCnt] = matcher.end();
					lineCnt++;
				}
				lineOffsets = lineOffs;
			}
			else
			{
				System.err.println("CFParser::calcLineNumbers() - Didn't find any lines!");
				lineOffsets = new int[1];
				lineOffsets[0] = 0;
			}
		} catch(Exception anException) {
			System.err.println("CFParser::calcLineNumbers() - Error, could not calculate line numbers because: " + anException.getMessage());
		}
		System.err.println("CFParser::calcLineNumbers() - Got " + lineOffsets.length + " lines. Is this correct?");		
	}
	
	
	
	/**
	 * <code>IsCFTag</code> - Simple helper function to determine whether some text is a CF tag or not.
	 * @param inString - String to test
	 * @return <code>true</code> - is a CF tag, <code>false</code> - isn't a CF tag
	 */
	protected boolean IsCFTag(String inString)
	{
		return inString.indexOf("<cf") != -1;
	}
	
	/**
	 * <code>GetTabs</code> - Helper function for debugging.
	 * @param inStack - stack to use as a count for the number of tabs required.
	 * @return - a string with tabs in.
	 */
	protected String GetTabs(Stack inStack)
	{
		String retval = "";
		for(int i = 0; i < inStack.size(); i++)
			retval += "\t";
		return retval;
	}
	
	/**
	 * <code>GetIndent</code> does much the same as @see GetTabs(Stack inStack) except you pass an integer
	 * @param count - tabs to make
	 * @return - string with <code>count</code> tabs in
	 */
	protected String GetIndent(int count)
	{
		String retval = "";
		for(int i = 0; i < count; i++)
			retval += "\t";
		
		return retval;
	}
	/**
	 * <code>walkTreeMain</code> - recursive tree walker, dumps the tree info out.
	 * @param rootItem - the current root item
	 * @param count - current depth in the tree, used for outputting indentation.
	 */
	protected void walkTreeMain(DocItem rootItem, int count)
	{
		System.out.println(GetIndent(count) + "Tree: " + rootItem.itemName  + "\' + match data was : " + rootItem.getItemData());
		if(rootItem.hasChildren())
		{
			ArrayList children = rootItem.getChildren();
			for(int i = 0; i < children.size(); i++)
			{
				walkTreeMain((DocItem)children.get(i), count + 1);
			}
		}
	}

	/**
	 * <code>walkTreeNamesOnly</code> - recursive tree walker, only dumps the names of the nodes for brevity
	 * @param rootItem - the current root item
	 * @param count - current depth in the tree, used for outputting indentation.
	 */
	protected void walkTreeNamesOnly(DocItem rootItem, int count)
	{
		System.out.println(GetIndent(count) + rootItem.itemName);
		if(rootItem.hasChildren())
		{
			ArrayList children = rootItem.getChildren();
			for(int i = 0; i < children.size(); i++)
			{
				walkTreeNamesOnly((DocItem)children.get(i), count + 1);
			}
		}
	}	
	
	/**
	 * <code>walkTreeMain</code> - Call to dump the document tree to the console.
	 * @param rootItem - the node to begin at.
	 */
	protected void walkTree(DocItem rootItem)
	{
		System.out.println("########### Tree walk 1, full info:");
		walkTreeMain(rootItem, 1);
		System.out.println("########### Tree walk 2, names only:");
		walkTreeNamesOnly(rootItem, 1);
	}
	
	/**
	 * <code>dumpStack</code> - Dumps all of the elements of the stack to the console.
	 * @param inStack
	 */
	protected void dumpStack(Stack inStack)
	{
		for(int i = 0; i < inStack.size(); i++)
		{
			DocItem tempItem = (DocItem)inStack.get(i);
			System.out.println("Parser: Stack at "+ i+ " is \', " + tempItem.itemName + "\' + match data was : " + tempItem.getItemData());
		}
		
	}
	
	/**
	 * <code>userMessage</code> - Outputs a message at a certain tree depth to the console
	 * @param indent - the indent to use
	 * @param method - the method that is doing the calling, so we can keep track nicely
	 * @param message - the message to give to the user.
	 */
	protected void userMessage(int indent, String method, String message)
	{
		System.out.println(GetIndent(indent) + "CFParser::" + method + "() - " + message);
	}
	
	/**
	 * <code>userMessage</code> - Outputs a message at a certain tree depth to the console.
	 * Also allows the passing of message types so the method can decide whether to report
	 * the message to the user or not.
	 * 
	 * <b>NB:</b> USRMSG_ERRORs are inserted into the Problems tasklist <b>and will not disappear</b>
	 * until you restart Eclipse. 
	 * TODO: Need to work out how to keep track of markers & invalidate them as the parser is run again & again. 
	 * 
	 * @param indent - the indent to output the message at
	 * @param method - the method doing the calling
	 * @param message - the message
	 * @param msgType - the type of message. CFParser.USERMSG_* (i.e. CFParser.USERMSG_ERROR is an error to the user)
	 */
	protected void userMessage(int indent, String method, String message, int msgType, TagMatch match)
	{
		switch(msgType)
		{
			case USRMSG_WARNING:
				userMessage(indent, method, "WARNING: " + message);
				break;
			case USRMSG_ERROR:
				System.err.println("ERROR: CFParser::" + method + "() - " + message + " for file \'" + docFilename.toOSString() + "\'");

				IWorkspaceRoot myWorkspaceRoot = CFMLPlugin.getWorkspace().getRoot();
				try {

					Map attrs = new HashMap();
					MarkerUtilities.setLineNumber(attrs, match.lineNumber+1);
					MarkerUtilities.setMessage(attrs, message);
					//
					// Not sure what the start & end positions are good for!
					//MarkerUtilities.setCharStart(attrs, match.startPos);
					//MarkerUtilities.setCharEnd(attrs, match.endPos);
					MarkerUtilities.createMarker(this.res, attrs, IMarker.PROBLEM);
				}catch(CoreException excep) {
					userMessage(0, "userMessage", "ERROR: Caught CoreException when creating a problem marker");
				}
				break;
			default:
			case USRMSG_INFO:
				userMessage(indent, method, message);
				break;
		}
	}	
	
	/**
	 * <code>stripAttributes</code> - Strips the attributes from some data and puts them into a hash map
	 * @param inData - the string data to get the attributes out of
	 * @return a HashMap with the attributes in.
	 */
	protected HashMap stripAttributes(String inData)
	{
		HashMap attributes = new HashMap();
		Matcher matcher;
		Pattern pattern;
		pattern = Pattern.compile(REG_ATTRIBUTES);
		
		matcher = pattern.matcher(inData);
		while(matcher.find())
		{
			attributes.put(matcher.group(1), matcher.group(2));
//			System.out.println("CFParser::stripAttributes() - Got \'" + matcher.group(1) + "\'=\"" + matcher.group(2) + "\"");
		}
		
		return attributes;
	}

	/**
	 * <code>searchItemStack</code> - Searches the item stack for an item with <code>itemName</code>
	 * 
	 * This method is intended for when there is a parse error - we can try and determine whether
	 * the erroring tag is actually correct and the previous, opening tag is incorrect or not. Need
	 * to work on that really.
	 * 
	 * @param matchStack - the stack to search
	 * @param itemName - the item to search for
	 * @return the position in the stack where the item is
	 */
	protected int searchItemStack(Stack matchStack, String itemName)
	{
		int startSize = matchStack.size();
		int popCount = 0;
		Stack tempStack = new Stack();
		tempStack.copyInto(matchStack.toArray());
		
		while(tempStack.size() > 0)
		{
			DocItem tempItem = (DocItem)tempStack.pop();
			if(tempItem.getName().compareTo(itemName) == 0)
				break;
		}
		return startSize - popCount;
	}
	
	/**
	 * <code>handleClosingTag</code> - Handles a closing tag in the document
	 * 
	 * @param match the match that's a closer
	 * @param matchStack - the stack of matched items
	 */
	protected void handleClosingTag(TagMatch match, Stack matchStack)
	{
	/*
 	 * Quite simply it works out what the item is. Then it grabs the top-most item
	 * out of the matchStack, as that's the most recent opening tag. If the topItem
	 * matches the closing tag we're okay. We get the next item off the stack which
	 * is the tag's parent and add the opener & closer to the parent's children. 
	 * Then we push the parent back onto the stack to wait for another day.
	 * 
	 * If the item does not match the most recent item we've a problem. At the moment
	 * it reports an error and throws away the closer.
	*/
		//System.out.println(GetTabs(matchStack) + "Parser: Found closing tag of " + match.match);
		// Closing tag, so we attempt to match it to the current top of the stack.
		String closerName = match.match;
		if(closerName.indexOf("</cf") != -1)
		{
			// CF tag
			closerName = closerName.substring(4, closerName.length()-1);
			
			DocItem topItem = (DocItem)matchStack.pop();	// Should be the opening item for this closer
//			System.out.println(GetTabs(matchStack) + "Parser: Does \'" + closerName + "\' match \'" + topItem.itemName + "\'");							
		
			if(topItem.itemName.compareTo(closerName) == 0)
			{
				DocItem parentItem = (DocItem)matchStack.pop();
				try {
					parentItem.addChild(topItem);
				} catch(InvalidChildItemException addExcep) {
					parserState.addMessage(new ParseError(topItem.lineNumber, topItem.getStartPosition(), match.endPos,
															topItem.getItemData(), addExcep.getMessage()));
				}
				matchStack.push(parentItem);
			}
			else
			{
				//
				// We just report that a parse error has occured and we will continue trying to parse the document.
				// TODO: Record errors somehow and ensure we don't overload the user with parse errors caused by one
				// error that occured earlier.
				userMessage(matchStack.size(), 
							"handleClosingTag", "Found a closing tag with the name \'" + match.match + 
							"\' that does not match the current parent item: \'" + topItem.itemName + "\'", 
							USRMSG_ERROR, match);
				
				// 
				// So we just push the top item back onto the stack, ready to be matched again.
				// NB: Note that this only copes with extra closing tags, not extra opening tags.
				matchStack.push(topItem);
			}
		}
	}
	
	/**
	 * <code>handleCFScriptBlock</code> - handles a CFScript'd block (at the moment it does nothing)
	 * @param match - the match
	 * @param matchStack - the stack of tag items.
	 */
	protected void handleCFScriptBlock(TagMatch match, Stack matchStack)
	{
//		System.out.println(GetTabs(matchStack) + "Parser: found a cfscript block. Ignoring for the moment");		
	}
	
	/**
	 * <code>handleHTMLTag</code> - Handles an HTML tag (does nothing at the moment.)<br/>
	 * <b>NB:</b> HTML files will bugger up the parser as handleClosing doesn't handle closing HTML tags!
	 * @param tagName - tag name with chevrons removed
	 * @param match - the tag match made
	 * @param matchStack
	 * @param attrMap - map of attributes for this item
	 * @param isACloser - whether the tag is a closer or not (or has been closed by the user)
	 */
	protected void handleHTMLTag(String tagName, TagMatch match, Stack matchStack, HashMap attrMap, boolean isACloser)
	{
//		System.err.println(GetTabs(matchStack) + "Parser: Got an HTML tag called \'" + tagName + "\'. Ignoring for the moment");
	}
	
	/**
	 * Returns an instance of a CfmlTagItem based upon the tag name provided. 
	 * Essentially this is a class factory... it would go in a separate class but I'm not
	 * sure how best to do that. Gah, my poor Java knowledge :)
	 *
	 * @param tagName - tag name to match. Note this tag name should just be the cf-less name (e.g. else for &lt;cfelse&gt;)
	 * @param match - the TagMatch data.
	 * @param lineNum - line that the match occured on.
	 * @return An instance of the matched tag.
	 */
	// TODO: Make a class factory for CfmlTagItems...
	protected CfmlTagItem getNameBasedCfmlTag(String tagName, TagMatch match, int lineNum)
	{
		//
		// There _so_ must be a better way of doing this, but what it is I'm not sure!
		if(tagName.compareToIgnoreCase("else") == 0)
			return new CfmlTagElse(lineNum, match.startPos, match.endPos, tagName);
		else if(tagName.compareToIgnoreCase("if") == 0)
			return new CfmlTagIf(lineNum, match.startPos, match.endPos, tagName);
		else if(tagName.compareToIgnoreCase("case") == 0)
			return new CfmlTagCase(lineNum, match.startPos, match.endPos, tagName);
		else if(tagName.compareToIgnoreCase("defaultcase") == 0)
			return new CfmlTagDefaultCase(lineNum, match.startPos, match.endPos, tagName);
		else if(tagName.compareToIgnoreCase("catch") == 0)
			return new CfmlTagCatch(lineNum, match.startPos, match.endPos, tagName);
		else if(tagName.compareToIgnoreCase("property") == 0)
			return new CfmlTagProperty(lineNum, match.startPos, match.endPos, tagName);
		else if(tagName.compareToIgnoreCase("elseif") == 0)
			return new CfmlTagElseIf(lineNum, match.startPos, match.endPos, tagName);
		else if(tagName.compareToIgnoreCase("queryparam") == 0)
			return new CfmlTagQueryParam(lineNum, match.startPos, match.endPos, tagName);
		else if(tagName.compareToIgnoreCase("invokeargument") == 0)
			return new CfmlTagInvokeArgument(lineNum, match.startPos, match.endPos, tagName);
		else if(tagName.compareToIgnoreCase("function") == 0)
			return new CfmlTagFunction(lineNum, match.startPos, match.endPos, tagName);
		else
			return new CfmlTagItem(lineNum, match.startPos, match.endPos, tagName);
	}
	
	/**
	 * <code>handleCFTag</code> - Handles a opening CF tag.
	 * 
	 * @param tagName - name of the tag
	 * @param match - the TagMatch made
	 * @param matchStack - match stack
	 * @param attrMap - map of attributes that are for this tag
	 * @param isACloser - whether it's a self-closer
	 */
	protected void handleCFTag(String tagName, TagMatch match, Stack matchStack, HashMap attrMap, boolean isACloser)
	{
		//
		// If a CF tag then we get it's CF tag name (i.e. <cffunction, CF tag name is 'function')
		// and init the new CfmlTagItem with that. We then check to see whether this type of tag
		// has a closing tag.
		// If it has it means that it is a branch element on the tree, so we pop it onto the stack.
		// If not then it's a child element and so we add it to the child list of the top element
		// of the stack.
		
		tagName = tagName.substring(3, tagName.length());
		TagItem newItem;
		//
		// First test to see whether we've found a custom tag. If so we do nothing fancy (yet).
		// Also tests to make sure it catches CFX tags.
		if(tagName.charAt(0) == '_' || (tagName.charAt(0) == 'x' && tagName.charAt(1) == '_'))
		{
			newItem = new CfmlCustomTag(getLineNumber(match.startPos), match.startPos, match.endPos, tagName);
			newItem.setItemData(match.match);
		}
		else
		{
			newItem = getNameBasedCfmlTag(tagName, match, getLineNumber(match.startPos));
			newItem.initDictionary(DictionaryManager.getDictionary(DictionaryManager.CFDIC));
			newItem.setItemData(match.match);
		}
		
		try {
			newItem.addAttributes(attrMap);
		} catch(DuplicateAttributeException excep) {
			parserState.addMessage(new ParseError(getLineNumber(match.startPos), match.startPos, match.endPos, match.match, "The tag " + tagName + " already has the attribute " + excep.getName()));
		} catch(InvalidAttributeException excep) {
			parserState.addMessage(new ParseError(getLineNumber(match.startPos), match.startPos, match.endPos, match.match, "The attribute " + excep.getName() + " is not valid for the tag " + tagName));			
		}

		if(newItem instanceof CfmlTagFunction)
			parserState.addFunction(newItem);
		
		//
		//	Either the syntax dictionary says it closes itself or the user has specified it will
		try {
			if(newItem.hasClosingTag() && !isACloser) 
			{	// Not a closing item, it's an opener so on the stack it goes.
				matchStack.push(newItem);
			}
			else
			{ 	// It's a closing item, so we get the parent item and add this item to it's children.
				DocItem top = (DocItem)matchStack.pop();
				try {
					top.addChild(newItem);
				} catch(InvalidChildItemException excep) {
					//
					// An invalid child is not a fatal exception. Document is still valid, parsing can continue.
					// The user can sort it out later.
					parserState.addMessage(new ParseError(getLineNumber(match.startPos), match.startPos, match.endPos,
															match.match, excep.getMessage()));
				}
				matchStack.push(top);
//				System.out.println(GetTabs(matchStack) + "Parser: Item is a single tag and is now the child of " + top.itemName);
			}	
		} catch(Exception anExcep) {
			parserState.addMessage(new ParseError(getLineNumber(match.startPos), match.startPos, match.endPos, match.match, "An unknown error occurred during parsing."));
			System.err.println("CFParser::handleCFTag() - Caught an exception during item popping. Exception was " + anExcep.getLocalizedMessage());
		}
	}
	
	/**
	 * <code>createDocTree</code> - Creates the document tree from the TagMatches made
	 * 
	 * @param matches - the matches found previously
	 * @return a document tree.
	 * 
	 */
	/*
	 * The document tree is created using two things: 
	 * 1) A match stack.
	 * 2) A document tree
	 * 
	 * The method loops through all of the matches. For every opening tag there must be a closing tag.
	 * Therefore for every opening tag we take it and push it onto the stack. For every closing tag
	 * we pop the most recent tag off, compare it with the closer. If there's a match it's the closing
	 * tag for the opener. We add them to their parent's entry and carry on.
	 * If they don't match then at present the closer is thrown away.
	 * 
	 * Eventually we reach the end of the matches and that should mean - in theory - that we've got a
	 * valid tree. 
	 * 
	 * Unfortunately this can easily not be the case due to any user errors. At the moment it just 
	 * carries on regardless of any major errors. Perhaps some higher-level routine should take care
	 * of whether the document tree is valid or not. Perhaps the tree display?
	 * 
	 * TODO: break open CFSET's and grab variable assignments.
	 * TODO: somehow implement tag variable grabbing (i.e. from <cfquery>'s 'name' attribute) Should the tag object do it, or the parser?
	 */
	public CFDocument createDocTree(MatchList matches)
	{
		
		CFDocument newDoc = new CFDocument();
		Stack matchStack = new Stack();
		ArrayList rootElements = new ArrayList();
		TagItem rootItem = new TagItem(0, 0, 0, "Doc Root");
		matchStack.push(rootItem);
		
//		System.err.println("###################################################################");
//		System.err.println("####################### Begining Parser ###########################");
//		System.err.println("###################################################################");
		
//		System.out.println(GetTabs(matchStack) + "Parser: About to create doc tree");
		int matchPos = 0;
		try {
			for(; matchPos < matches.size(); matchPos++)
			{
				TagMatch match = (TagMatch)matches.get(matchPos);
				String matchStr = match.match;
				//dumpStack(matchStack);
				
				if(matchStr.charAt(0) == '<')	// Funnily enough this should always be the case!
				{
//					System.out.println("Parser: Working on match \'" + match.match + "\'");
					//
					// Is a tag
					if(matchStr.charAt(1) == '/')
					{
						handleClosingTag(match, matchStack);
					}
					else
					{
						int tagEnd = matchStr.indexOf(" ");	// Look for the first space
						if(tagEnd == -1)
						{
							//
							// No spaces, therefore it has no attributes (i.e. <cfscript>)
							tagEnd = matchStr.indexOf(">");
						}
						String tagName = match.match.substring(0, tagEnd);
//						System.out.println(GetTabs(matchStack) + "Parser: I think I have found \'" + tagName + "\'");
						
						boolean isACloser = false;
						HashMap attrMap = new HashMap();
						
						if(match.match.indexOf("/") != -1)	// Handle a self-closer (i.e. <cfproperty ... />
						{
							if(tagName.indexOf("/") != -1)
								tagName = tagName.substring(0, tagName.length()-1); // Is a self-closer (i.e. <br/>)
							isACloser = true;
//							System.out.println(GetTabs(matchStack) + "Parser: Hmmm, user specified it closes");
						}
						//
						// Get the attributes from the tag.
						String attributes = match.match.substring(tagEnd, match.match.length()-1); // minus one to strip the closing '>'
						attrMap = stripAttributes(attributes);

						if(IsCFTag(tagName))
						{
							//
							// Anything within a CFScript block should really be placed at the current level of the
							// doc tree. So send it off to the CFScript block hanlder
							// TODO: CFScript blocks are ignored at present! Sort it! Should there be a specialised cfscript tag that does it?
							if(tagName.compareTo("script") == 0)
							{
								handleCFScriptBlock(match, matchStack);
							}
							else
								handleCFTag(tagName, match, matchStack, attrMap, isACloser);
						}
						else	// Anything else is an HTML tag
						{
							handleHTMLTag(tagName, match, matchStack, attrMap, isACloser);
						}
					}
				}
			}
		}catch(Exception anyException) {
			parserState.addMessage(new ParseMessage(getLineNumber(matchPos), matchPos, matchPos, "", "Doc tree creation: caught an unhandled exception: " + anyException.getMessage()));
			System.err.println(GetTabs(matchStack) + "Parser: Caught an exception!" + anyException.getMessage());
		}
//		System.err.println("#################### TREE DUMP ##################");
//		walkTree(rootItem);
		return newDoc;
	}
	
	protected final int MATCHER_NOTHING = 		0x00;
	protected final int MATCHER_COMMENT = 		0x01;
	protected final int MATCHER_HTMLTAG =		0x02;
	protected final int MATCHER_ATTRIBUTE = 	0x04;
	protected final int MATCHER_CFSCRIPT = 		0x08;
	protected final int MATCHER_CFMLCOMMENT = 	0x16;
	protected final int MATCHER_CFSCRCOMMENT = 	0x32;
	protected final int MATCHER_STRING = 		0x64;
	protected final int MATCHER_CFMLTAG = 		0x128;
	
	protected final int INDEX_NOTFOUND =	-1;	// For String::indexOf(), make it nicer to read!
	
	protected int matchingHTML(CFParser.State parseState, String inData, int currDocOffset)
	{
		int finalOffset = currDocOffset;
		int currPos = currDocOffset + 1;
		TagMatch embeddedMatch = null;
		int cfTagCount = 0;
		int quoteCount = 0;
		
		for(; currPos < inData.length(); currPos++)
		{
			char currChar = inData.charAt(currPos);
			boolean inQuotes = (1 == quoteCount % 2);
			String next2Chars = "";
			String next3Chars = "";
			
			if(inData.length() - currPos > 2)	// For CF stuff we get the next two chars as well.
				next2Chars = inData.substring(currPos + 1, currPos + 3);
			if(inData.length() - currPos > 3)	// For CF closer tags </cf...
				next3Chars = inData.substring(currPos + 1, currPos + 4);
			
			if(currChar == '<' && (next2Chars.compareTo("cf") == 0 || next3Chars.compareTo("/cf") == 0))
			{	// CFML tag embedded in HTML
//				System.out.println("FOUND!: an embedded CFML tag within HTML! : " + inData.substring(0, currPos));
				currPos = matchingCFML(parseState, inData, currPos);
				cfTagCount++;
			}
			else if(!inQuotes && currChar == '>')
			{
//				System.out.println("FOUND!: an HTML tag!: " + inData.substring(currDocOffset, currPos+1));				
				parseState.addMatch(new TagMatch(inData.substring(currDocOffset, currPos+1), currDocOffset, currPos, 0), 
									State.ADD_BEFORE, cfTagCount);
				finalOffset = currPos;
				break;
			}
			else if(currChar == '\"')
				quoteCount++;
		}
		if(finalOffset != currPos)
		{
			System.err.println("FATAL ERROR: Failed to find the end of an HTML tag!: " + inData.substring(currDocOffset, currPos));
			
			parseState.addMessage(new ParseError(getLineNumber(currDocOffset), currDocOffset, currPos,
												inData.substring(currDocOffset, currPos), 
												"Reached end of document before finding end of HTML tag.",
												true )); // Fatal error
		}
		return currPos;
	}
	
	protected int matchingCFScript(CFParser.State parseState, String inData, int currDocOffset)
	{
		int finalOffset = currDocOffset;
		int currPos = currDocOffset;
		String nextChars; // </cfscript>
		String closingText = "</cfscript>";
		
		for(; currPos < inData.length(); currPos++)
		{
			if(inData.length() - currPos > closingText.length())
				nextChars = inData.substring(currPos, currPos + closingText.length());
			else 
				break;	// Not enough space left for it to be a closing cfscript tag.
			
			if(nextChars.compareTo(closingText) == 0)
			{
				finalOffset = currPos;
				break;
			}
		}
		
		if(finalOffset != currPos)
		{
			System.err.println("FATAL ERROR: Searching for a closing <cfscript> tag but could not find one: " + inData.substring(currDocOffset, currPos));
			
			parseState.addMessage(new ParseError(getLineNumber(currDocOffset), currDocOffset, currPos,
												inData.substring(currDocOffset, currPos), 
												"Reached end of document before finding a closing cfscript tag.",
												true )); // Fatal error
		
		}
		
		return finalOffset;
	}
	
	protected int matchingCFML(CFParser.State parseState, String inData, int currDocOffset)
	{
		int finalOffset = currDocOffset;
		int currPos = currDocOffset;
		//
		// for recognising double quote escape sequences.
		// If it's even we're out of quotes, odd we're in 'em. Try it out manually and see!
		int quoteCount = 0;
		
		for(; currPos < inData.length(); currPos++)
		{
			char currChar = inData.charAt(currPos);
			boolean inQuotes = (1 == quoteCount % 2);
			if(!inQuotes && currChar == '>')
			{
				finalOffset = currPos;
//				System.out.println("FOUND!:a CFML tag!: " + inData.substring(currDocOffset, currPos+1));
				parseState.addMatch(new TagMatch(inData.substring(currDocOffset, currPos+1), currDocOffset, currPos, 
												getLineNumber(currDocOffset)));
				break;
			}
			else if(currChar == '\"')
				quoteCount++;

		}
		if(finalOffset != currPos)
		{
			System.err.println("FATAL ERROR: Failed to find the end of a CFML tag!: " + inData.substring(currDocOffset, currPos));
			
			parseState.addMessage(new ParseError(getLineNumber(currDocOffset), currDocOffset, currPos,
												inData.substring(currDocOffset, currPos), 
												"Reached end of document before finding end of CFML tag.",
												true )); // Fatal error
		}
		
		return finalOffset;
	}
	
	protected MatchList tagMatchingAttempts(String inData)
	{
		String data = inData;
		int lastMatch = 0;
		int currPos = 0;
		int currState = 0;
		MatchList matches = new MatchList();
		try {
			for(currPos = 0; currPos < data.length(); currPos++)
			{
				char currChar = data.charAt(currPos);
				String next2Chars = "";
				String next3Chars = "";
				String around = "";
				
				// Make sure we haven't had any fatal errors during parsing.
				if(parserState.hadFatal())
				{
					System.err.println("Parser encountered a fatal parse error");
					break;
				}
				
				if(data.length() - currPos > 2)	// For CF stuff we get the next two chars as well.
					next2Chars = data.substring(currPos + 1, currPos + 3);
				
				if(data.length() - currPos > 3)
					next3Chars = next2Chars + data.charAt(currPos + 3);
				
				if(data.length() - currPos > 10 && currPos > 10)
					around = data.substring(currPos - 10, currPos) + data.substring(currPos, currPos + 10);
				else if(data.length() - currPos > 10)
					around = data.substring(currPos, currPos + 10);
				
				if(currState == MATCHER_NOTHING)
				{	
					switch(currChar) 
					{
						case '<': 
							if(next2Chars.compareTo("!-") == 0)
							{	// Testing for comment: <!--
								// TODO: Find out whether comments can occur in tags
//								System.out.println("Found a comment");
								if(next3Chars.compareTo("!--") == 0 && data.charAt(currPos + 4) == '-')
								{
//									System.out.println("\t it's a CFML comment");
									currState = MATCHER_CFMLCOMMENT;
									lastMatch = currPos;
								}
								else
								{
									currState = MATCHER_COMMENT;
									lastMatch = currPos;
								}
							}
							else if(next2Chars.compareTo("cf") == 0)
							{
//								System.out.println("Found the beginnings of a CF tag");
								//
								// The following handles a CFScript tag. A CFScript tag is NOT part of the document tree as it is a 
								// container *only* for things to go in the document tree.
								if(data.length() - currPos > "<cfscript>".length() && 
								   data.substring(currPos, currPos + "<cfscript>".length()).compareTo("<cfscript>") == 0)
								{
									// 
									// It's a CFScript tag
									currPos = matchingCFScript(parserState, inData, currPos);
								}
								else	// Not a CFScript tag.
									currPos = matchingCFML(parserState, inData, currPos);
							}
							else // Notice that the above if doesn't match </cf, that's because it's like a standard HTML tag.
							{
								//System.out.println("Found the beginnings of an HTML tag.");
								currPos = matchingHTML(parserState, inData, currPos);
							}
							break;
						default:
							// Not a char we care about.
							break;
					}
				}
				else if(currState == MATCHER_CFMLCOMMENT && currChar == '-' && 
						next2Chars.compareTo("--") == 0 && 
						inData.charAt(currPos+3) == '>')
				{
//					System.out.println("Found the end of a CFML comment");
					currState = MATCHER_NOTHING;
				}
				else if(currState == MATCHER_COMMENT && currChar == '-' && next2Chars.compareTo("->") == 0)
				{
//					System.out.println("Found the end of an HTML comment");
					currState = MATCHER_NOTHING;
				}
			}
		}catch(Exception excep) {
			parserState.addMessage(new ParseError(0, currPos, currPos, "", "Caught an exception during parsing.", true));
		}
		return matches;
	}
	
	
	protected void dumpMatches(MatchList matches)
	{
		System.out.println("Dumping the matches:");
		for(int i = 0; i < matches.size(); i++)
		{
			System.out.println("Match: \'" + ((TagMatch)matches.get(i)).match + "\'");
		}
	}
	
	protected void processParseResultMessages()
	{
		ArrayList messages = parserState.getMessages();
		IWorkspaceRoot myWorkspaceRoot = CFMLPlugin.getWorkspace().getRoot();
		
		for(int i = 0; i < messages.size(); i++)
		{
			ParseMessage currMsg = (ParseMessage)messages.get(i);
			
			Map attrs = new HashMap();
			MarkerUtilities.setLineNumber(attrs, currMsg.getLineNumber()+1);
			MarkerUtilities.setMessage(attrs, currMsg.getMessage());
			//
			// Not sure what the start & end positions are good for!
			MarkerUtilities.setCharStart(attrs, currMsg.getDocStartOffset());
			MarkerUtilities.setCharEnd(attrs, currMsg.getDocEndOffset());
			//
			// Not sure right now how to set the problem to be a warning or an error.
			// There is IMarker.SEVERITY_ERROR & IMarker.SEVERITY_WARNING but I'm 
			// not sure how I set them.
			if(currMsg instanceof ParseError)
			{

			}
			else if(currMsg instanceof ParseWarning)
			{
				

			}			
			
			try {
				MarkerUtilities.createMarker(this.res, attrs, IMarker.PROBLEM);
/*
				IMarker newMarker = myWorkspaceRoot.createMarker(IMarker.PROBLEM);
				newMarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				newMarker.setAttribute(IMarker.CHAR_START, currMsg.getDocStartOffset());
				newMarker.setAttribute(IMarker.CHAR_END, currMsg.getDocEndOffset());
				newMarker.setAttribute(IMarker.TRANSIENT, true);
				//newMarker.setAttribute(IMarker.LOCATION, this.res);
*/
				
			}catch(CoreException excep) {
				userMessage(0, "userMessage", "ERROR: Caught CoreException when creating a problem marker");
			}catch(Exception anyExcep) {
				userMessage(0, "processParseResultMessage", "ERROR: Caught exception " + anyExcep.getMessage());
			}

		}
	}
	
	public CFDocument parseDoc()
	{
		CFDocument docTree = null;
		try {
			parserState = new CFParser.State("doesn\'t matter!");
			calcLineNumbers(parseDoc.get());
			MatchList matches = tagMatchingAttempts(parseDoc.get());
			docTree = createDocTree(parserState.getMatches());
			//dumpMatches(parserState.getMatches());
			processParseResultMessages();
		} catch(Exception excep) 
		{
			System.err.println("CFParser::parseDoc() - Exception: " + excep.getMessage());
		}
		return docTree;
	}
	
	public CFDocument parseDoc(IDocument doc2Parse)
	{
		
		return createDocTree(getTagMatches(doc2Parse));
	}
}
