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

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.xpath.compiler.Compiler;
import org.eclipse.core.internal.runtime.Log;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
//import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.texteditor.MarkerUtilities;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.parser.cfmltagitems.*;
import com.rohanclan.cfml.parser.cfscript.ParseException;
import com.rohanclan.cfml.parser.cfscript.SPLParser;
import com.rohanclan.cfml.parser.cfscript.SimpleNode;
import com.rohanclan.cfml.parser.cfscript.TokenMgrError;
import com.rohanclan.cfml.parser.docitems.AttributeItem;
import com.rohanclan.cfml.parser.docitems.CfmlCustomTag;
import com.rohanclan.cfml.parser.docitems.CfmlTagItem;
import com.rohanclan.cfml.parser.docitems.DocItem;
import com.rohanclan.cfml.parser.docitems.TagItem;

//import com.rohanclan.cfml.util.Debug;

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
public class CFParser {

	/**
	 * <code>REG_TAG</code> - the regular expression for matching tags. NB: Doesn't work on multi-line tags :(
	 * TODO: Either modify the REG_TAG regex to match multiline tags or completely rewrite the tag matcher.
	 */
	static protected final String REG_TAG = "<(\\w*)(.*)/{0,1}>";
	/**
	 * <code>REG_ATTRIBUTES</code> - regular expression for getting the attributes out of a tag match.
	 * \s*(\w*)="(\w*)"
	 */
	static protected final String REG_ATTRIBUTES = "\\s*(\\w*)\\s*=\\s*('[^']*'|\"[^\"]*\")"; 
	
	
	static protected final int USRMSG_INFO 		= 0x00;
	static protected final int USRMSG_WARNING 	= 0x01;
	static protected final int USRMSG_ERROR		= 0x02;
	
	protected State parserState = null;
	
	protected IResource res = null;

	/** Tells the parser whether it should parse CFScript blocks or not. Pretty buggy at the moment */
	protected boolean parseCFScript = false;
	protected boolean reportErrors	= true;
	
	/**
	 * Tells the parser whether it should be reporting the errors it finds.
	 * 
	 * @param enable set to true to report errors to the Problems view or false to not.
	 */
	public void setReportErrors(boolean enable) {
		this.reportErrors = enable;

	}
	
	/**
	 * Tells the parser whether it should parse CFScript blocks or not
	 * @param enable set to true to parse CFScript, false to not
	 */
	public void setCFScriptParsing(boolean enable) {
		this.parseCFScript = enable;
	}
	
	/**
	 * <code>parseDoc</code> - the document to parse. Could just use a string, but IDocument provides line number capabilities.
	 */
	protected IDocument parseDoc = null;
	/**
	 * <code>docFilename</code> - the pathname of the document, so we can stick messages into the Problems tasklist
	 */
	protected IPath docFilename = null;	// Document file info... not working just yet.

	/**
	 * <code>parseResult</code> - the resultant document tree.
	 * <b>NB:</b> Currently the root node is called 'root' and has no real data, it's just a root node.
	 */
	protected CFDocument parseResult = null;	// The end result of the parse.
	
	protected String data2Parse = "";
	
	/**
	 * <code>getParseResult</code> - Get's the document tree from a parse 
	 * @return The CF document tree that results from calling <code>parseDoc()</code>
	 */
	public CFDocument getParseResult()	{
		if(parseResult == null)
			//System.err.println("CFParser::getParseResult() - WARNING: parseResult is null!");
			;
		if(parserState == null)
			//System.err.println("CFParser::getParseResult() - WARNING: parserState is null. This probably means that the parser has not been run.");
			;
		return parseResult; 
	}
	
	/**
	 * <code>CFParser</code> Constructor without params.
	 * 
	 * Just sets the parseDoc to null.
	 *
	 */
	public CFParser() {
		super();
		parseDoc = null;
		data2Parse = null;
	}
	
	private void setData2Parse(String data) {
	    //SPIKE: Added the toLowerCase() as a quick hack to sort out the case sensitivity issues.
		//OLLIE: Stuck a generic call to set the data to parse to put the toLowerCase() in the same
		//       place. This enables better debugging of the actual cause of the problem.
	//	this.data2Parse = data.toLowerCase();
		this.data2Parse = data;
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
		this.setData2Parse(this.parseDoc.get());
		res = newRes;
	}

	public CFParser(String inData, IResource dataRes)
	{
		this.setData2Parse(inData);
		res = dataRes;
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
	 * <code>userMessage</code> - Outputs a message at a certain tree depth to the console
	 * @param indent - the indent to use
	 * @param method - the method that is doing the calling, so we can keep track nicely
	 * @param message - the message to give to the user.
	 */
	protected void userMessage(int indent, String method, String message)
	{
		////System.out..println("CFParser::userMessage() - " + Util.GetIndent(indent) + "CFParser::" + method + "() - " + message);
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
	protected void userMessage(int indent, String method, String message, int msgType, ParseItemMatch match)
	{
		switch(msgType)
		{
			case USRMSG_WARNING:
				userMessage(indent, method, "WARNING: " + message);
				break;
			case USRMSG_ERROR:
				if(this.reportErrors) {
					IWorkspaceRoot myWorkspaceRoot = CFMLPlugin.getWorkspace().getRoot();
					
					
					try {					
						//
						// Not sure what the start & end positions are good for!
						
						//MarkerUtilities.createMarker(this.res, attrs, IMarker.PROBLEM);
						IMarker marker = this.res.createMarker("com.rohanclan.cfml.parserProblemMarker");
						Map attrs = new HashMap();
						MarkerUtilities.setLineNumber(attrs, match.lineNumber+1);
						MarkerUtilities.setMessage(attrs, message);
						MarkerUtilities.setCharStart(attrs, match.startPos);
						MarkerUtilities.setCharEnd(attrs, match.endPos);
						marker.setAttributes(attrs);
						marker.setAttribute(IMarker.MESSAGE,message);
						

					}catch(CoreException excep) {
						userMessage(0, "userMessage", "ERROR: Caught CoreException when creating a problem marker. Message: \'" + excep.getMessage() + "\'");
						}
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
	 * @return array list of the attributes found. May contain duplicates
	 */
	protected ArrayList stripAttributes(String inData, int lineNum, int offset)
	{
		ArrayList attributes = new ArrayList();
		Matcher matcher;
		Pattern pattern;
		String attributeName,attributeValue;
		//SPIKE: Added the case insensitive bit
		pattern = Pattern.compile(REG_ATTRIBUTES,Pattern.CASE_INSENSITIVE);
		
		//SPIKE: Added the toLowerCase() bit.
		//matcher = pattern.matcher(inData.toLowerCase());
		matcher = pattern.matcher(inData);
		while(matcher.find())
		{
			
		    if (matcher.group(1) != null && matcher.group(2) != null) {
		    	AttributeItem newAttr;
		    	
			    attributeName = matcher.group(1).trim();
			    attributeValue = matcher.group(2).trim();
			    attributeValue = attributeValue.substring(1,attributeValue.length()-1);
			    newAttr = new AttributeItem(lineNum, offset + matcher.start(1), offset + matcher.end(1),
			    								attributeName, attributeValue);
			    attributes.add(newAttr);
		    }
		    else {
		        ////System.out..println("CFParser::stripAttributes() - failed on |" + inData + "| with " + matcher.groupCount() + " matches");
		        //for (int i = 0; i<=matcher.groupCount(); i++) {
		        //	//System.out..println("Match " + i + " : " + matcher.group(i));
		        //}
		    }
		}
		
		return attributes;
	}

	
	
	/**
	 * <code>handleClosingTag</code> - Handles a closing tag in the document
	 * @return true - everything okay, false - error during parsing.
	 * @param match the match that's a closer
	 * @param matchStack - the stack of matched items
	 */
	protected boolean handleClosingTag(ParseItemMatch match, Stack matchStack)
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
		//System.out..println("CFParser::handleClosingTag() - " + Util.GetTabs(matchStack) + "Parser: Found closing tag of " + match.match);
		// Closing tag, so we attempt to match it to the current top of the stack.
		String closerName = match.match;
		//SPIKE: Added the toLowerCase()
		if(closerName.toLowerCase().indexOf("</cf") != -1)
		{
			// CF tag
		    closerName = closerName.substring(2, closerName.length()-1);
			
		    
		    DocItem topItem = (DocItem)matchStack.peek();
		    
			//System.out.println("Top item on stack is " + topItem.getName());
			
			if(topItem instanceof TagItem)
			{	
			    // Look for hybrids at the top of the stack
			    // and remove them if there is an opener below them.
			    try {
				boolean foundCloser = false;
			    ArrayList removals = new ArrayList();
			    Object[] items = matchStack.toArray();
			    //System.out.println("Looking on stack for opening " + closerName + ". Closer found on line: " + this.getLineNumber(match.getStartPos()));
			    for (int i=items.length-1;i>0;i--) {
			        if (items[i] instanceof TagItem) {
			            TagItem item = (TagItem)items[i];
			            //System.out.println("Checking " + item.getName());
			        	
			        	if (item.getName().equalsIgnoreCase(closerName)) {
			        	    //System.out.println("Found opener. Exiting loop.");
			        	    foundCloser = true;
			        	    break;
			        	} else if (item.isHybrid()) {
			        	    //System.out.println("Found hybrid. Adding it to the removals list.");
			        	    removals.add(item);
			        	}
			        }
			    }
			    
			    // If we found a closer, we want to remove any unclosed hybrids.
			    if (foundCloser) {
			        items = removals.toArray();
		            DocItem parent = (DocItem)matchStack.get(items.length);
			        for (int i=0;i<items.length;i++) {
			            TagItem item = (TagItem)items[i];
			            //System.out.println(item.getChildNodes().size() + " children need to be moved to " + parent.getName());
			            Object[] orphans = item.getChildNodes().toArray();
			            for (int j=0;j<orphans.length;j++) {
			                DocItem orphan = (DocItem)orphans[j];
			                //System.out.println("Moving " + orphan.getName() + " under " + parent.getName());
			                parent.addChild(orphan);
			                item.removeChild(orphan);
			            }
			            //System.out.println("Removing " + item.getName() + " from the stack.");
			            matchStack.remove(items[i]);
			        }
			    }
			    else {
			        //System.out.println("Opener not found on stack for " + closerName);
			        //System.out.println(" ");
			    }
			    
			    }
			    catch (Exception e) {
			        e.printStackTrace();
			    }
			    
				try {
					TagItem tempItem = new TagItem(match.lineNumber, match.startPos, match.endPos+1, match.match);
					((TagItem)topItem).setMatchingItem(tempItem);
					} catch(Exception e){
					System.err.println("Caught exception: " + e.getMessage());
					e.printStackTrace();
				}
			}
			
			// Take the top item off the stack.
			topItem = (DocItem)matchStack.pop();	// Should be the opening item for this closer
			//System.out..println("CFParser::handleClosingTag() - " + Util.GetTabs(matchStack) + "Parser: Does \'" + closerName + "\' match \'" + topItem.itemName + "\'");							
			
			//SPIKE: Made this case insensitive
			if(topItem.getName().compareToIgnoreCase(closerName) == 0)
			{
			    //System.out.println("Found matcher at top of stack!!!");
				DocItem parentItem = (DocItem)matchStack.pop();
				try {
					parentItem.addChild(topItem);
				}
				catch(Exception excep)
				{
					//
					// Tell the user there was a problem and then rethrow the exception.
					//System.out..println("CFParser::handleClosingTag() - Caught exception \'" + excep.getMessage() + "\'");
					excep.printStackTrace();
					//System.out..println(excep.hashCode());
					throw (RuntimeException)excep.fillInStackTrace();
				}
				matchStack.push(parentItem);
			}
			
			else
			{
				//
				// If we're here that means that the top item of the match stack isn't the
				// opener of the current closer. Therefore we report this as an error
				// and finish parsing as we can't easily make sense of the rest of the document.
				
				ParseItemMatch tempMatch = new ParseItemMatch(match.match, match.startPos, match.endPos, 
													getLineNumber(match.startPos), MATCHER_NOTHING);
													
				userMessage(matchStack.size(), 
							"handleClosingTag", "Closing tag \'" + match.match + 
							"\' does not match the current parent item: \'" + topItem.getName() + "\'", 
							USRMSG_ERROR, tempMatch);
/*
				while(matchStack.size() > 0)
				{
					
					TagMatch currMatch = (TagMatch)matchStack.pop();
					userMessage(matchStack.size(),
								"handleClosingTag", "Tag trace: \'" + 
								currMatch.match + "\' at line " + 
								getLineNumber(currMatch.startPos), USRMSG_ERROR, currMatch); 
				}
*/
				return false;
				// 
				// So we just push the top item back onto the stack, ready to be matched again.
				// NB: Note that this only copes with extra closing tags, not extra opening tags.
				//matchStack.push(topItem);
			}
		}
		return true;
	}
	
	static private SPLParser parser = null;
	static {
		parser = new SPLParser(new StringReader(""));
	}
	/**
	 * <code>handleCFScriptBlock</code> - handles a CFScript'd block (at the moment it does nothing)
	 * @param match - the match
	 * @param matchStack - the stack of tag items. This will have all of the new Script items added to it.
	 */
	protected void handleCFScriptBlock(ParseItemMatch match, Stack matchStack)
	{
		//
		// CFScript parsing is broken completely at the moment, so the following line
		// just nicely quits out for us without any "unreachable code" errors or warnings.
		//System.out..println("handleCFScriptBlock - ");
		//if(true) return;
		////System.out..println("CFParser::handleCFScriptBlock() - " + Util.GetTabs(matchStack) + "Parser: found a cfscript block. Ignoring for the moment");
		String mainData = match.match;
		//SPIKE: Added the toLowerCase() bit.
		mainData = mainData.toLowerCase().substring("<cfscript>".length());
		StringReader tempRdr =new StringReader(mainData);
		SimpleNode rootElement = null;
		
		if(!this.parseCFScript) {
			return;
		}
		
		if(parser == null) {
			parser = new SPLParser(tempRdr);
		}
		else
			SPLParser.ReInit(tempRdr);
		
		try {
			SPLParser.CompilationUnit();
			rootElement = (SimpleNode)parser.getDocumentRoot();
			
			if(rootElement != null) {
				
			}
			
		}  catch(ParseException ex) {
			//
			// A ParseException has a nice error message for us... unfortunately the message returned
			// contains a reference to the line number of the error from _the start of the CFScript block_
			// which is therefore confusing to the user. 
			// So we format our own message here. First we tell the user what the parser was processing
			// when it encountered the error, then it goes through the tokens it was expecting.
			// Finally we create a temp TagMatch that we ajust it's document positions so that the line
			// number isn't the start of the CFScript block but the actual line of the error.
			
			String errMsg = "Encountered \"" + ex.currentToken.next.image + "\". Was expecting one of: ";
			for(int i = 0; i < ex.expectedTokenSequences.length; i++) {
				String expToken =ex.tokenImage[ex.expectedTokenSequences[i][0]]; 
				errMsg+= expToken.substring(1, expToken.length()-1);
				if(i > 0) errMsg += ",";
			}
			ParseItemMatch tempMatch = match;
			tempMatch.lineNumber+= ex.currentToken.beginLine-1;
			tempMatch.startPos += ex.currentToken.beginColumn;
			tempMatch.endPos += ex.currentToken.endColumn;
			try {
				userMessage(matchStack.size(), "handleCFScriptBlock()", errMsg, CFParser.USRMSG_ERROR, tempMatch);
			} catch(Exception innerEx) {
				System.err.println("CFParser::handleCFScriptBlock() - Caught exception whilst creating markers!");
				innerEx.printStackTrace();
			}
		} catch(Throwable lastDitch) {
			userMessage(matchStack.size(), "handleCFScriptBlock()", "Error during parse: " + lastDitch.getMessage(), CFParser.USRMSG_ERROR, match);
			lastDitch.printStackTrace();
		}
		return;
		
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
	protected void handleHTMLTag(String tagName, ParseItemMatch match, Stack matchStack, ArrayList attrList, boolean isACloser)
	{
		//System.err.println("CFParser::handleHTMLTag() - " +  Util.GetTabs(matchStack) + "Parser: Got an HTML tag called \'" + tagName + "\'. Ignoring for the moment");
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
	protected CfmlTagItem getNameBasedCfmlTag(String tagName, ParseItemMatch match, int lineNum)
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
		else if(tagName.compareToIgnoreCase("set") == 0)
			return new CfmlTagSet(lineNum, match.startPos, match.endPos, tagName);
		else
			return new CfmlTagItem(lineNum, match.startPos, match.endPos, tagName);
	}
	
	/**
	 * <code>handleCFTag</code> - Handles a opening CF tag.
	 * 
	 * @param tagName - name of the tag
	 * @param match - the TagMatch made
	 * @param matchStack - match stack
	 * @param attrList - map of attributes that are for this tag
	 * @param isACloser - whether it's a self-closer
	 */
	protected void handleCFTag(String tagName, ParseItemMatch match, Stack matchStack, ArrayList attrList, boolean isACloser)
	throws Exception
	{
		//
		// If a CF tag then we get it's CF tag name (i.e. <cffunction, CF tag name is 'function')
		// and init the new CfmlTagItem with that. We then check to see whether this type of tag
		// has a closing tag.
		// If it has it means that it is a branch element on the tree, so we pop it onto the stack.
		// If not then it's a child element and so we add it to the child list of the top element
		// of the stack.
	    
	    tagName = tagName.substring(1, tagName.length());
		TagItem newItem;
		//System.out.println("CFParser::handleCFTag found " + tagName);
		
		// Tokenize the tag and check for invalid contents
		String[] tokens = match.getMatch().split("\\s");
		
		if (tokens.length > 1) {
			for (int i=1;i<tokens.length;i++) {
				if (tokens[i].length() > 0 
						&& tokens[i].charAt(0) == '<') {
					parserState.addMessage(new ParseError(
							getLineNumber(match.getStartPos()), match.getStartPos(), match.getStartPos() + match.getMatch().length(), match.getMatch(), 
							"Invalid token \"" + tokens[i].charAt(0) + "\" found in opening <b>"  + tagName + "</b> tag. The tag is probably missing a closing \">\""
						));
					
					throw new FatalException("Fatal parser error. Unable to continue parsing past line " + getLineNumber(match.getStartPos()));
				}
				
			}
		}
		//
		// First test to see whether we've found a custom tag. If so we do nothing fancy (yet).
		// Also tests to make sure it catches CFX tags.
		if(tagName.charAt(2) == '_' || (tagName.charAt(2) == 'x' && tagName.charAt(3) == '_'))
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
		
		newItem.addAttributes(attrList);
		addTagItemToTree(match, matchStack, isACloser, newItem);
	}
	
	private void addDocItemToTree(ParseItemMatch match, Stack matchStack, DocItem newItem)
	{
		if(newItem instanceof TagItem) {
			addTagItemToTree(match, matchStack, false, (TagItem)newItem);
			System.err.println("CFParser::addDocItemToTree() - A tag item has been passed. This is wrong but I\'ve passed it to addTagItemToTree as a non-closer");
			return;
		}
		DocItem topItem = (DocItem)matchStack.pop();
		topItem.addChild(newItem);
		matchStack.push(topItem);
	}
	
	/**
	 * @param match
	 * @param matchStack
	 * @param isACloser
	 * @param newItem
	 */
	private void addTagItemToTree(ParseItemMatch match, Stack matchStack, boolean isACloser, TagItem newItem) {
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

				top.addChild(newItem);
				matchStack.push(top);
				////System.out..println("CFParser::handleCFTag() - " + Util.GetTabs(matchStack) + "Parser: Item is a single tag and is now the child of " + top.itemName);
			}	
		} catch(Exception anExcep) {
			parserState.addMessage(new ParseError(getLineNumber(match.startPos), match.startPos, match.endPos, match.match, "An unknown error occurred during parsing."));
			System.err.println("CFParser::handleCFTag() - Caught an exception during item popping. Exception was " + anExcep.getLocalizedMessage());
			anExcep.printStackTrace();
			////System.out..println(anExcep.hashCode());
			throw (RuntimeException)anExcep.fillInStackTrace();
		}
	}

	/**
	 * 
	 * @param tagName
	 * @param matches
	 * @param matchPos
	 * @param isACloser
	 * @return
	 */
	boolean isTagACloser(String tagName, ArrayList matches, int matchPos, boolean isACloser)
	{
		if(tagName.compareToIgnoreCase("<cfinvoke") == 0)
		{
			if(((ParseItemMatch)matches.get(matchPos+1)).match.indexOf("invokeargument") == -1)
			{
				isACloser = true;
			}
			else
			{
				isACloser = false;
			}
			
		}
		return isACloser;
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
	 * 
	 */
	public CFDocument createDocTree(ArrayList matches)
	{
		CFDocument newDoc = new CFDocument();
		Stack matchStack = new Stack();
		ArrayList rootElements = new ArrayList();
		TagItem rootItem = new TagItem(0, 0, 0, "Doc Root");
		int matchPos = 0;

		matchStack.push(rootItem);
	
		try {
			
			for(; matchPos < matches.size(); matchPos++)
			{
				ParseItemMatch match = (ParseItemMatch)matches.get(matchPos);
				
				String matchStr = match.match;
				if(matchStr.charAt(0) == '<')	// Funnily enough this should always be the case!
				{
					if(matchStr.charAt(1) == '/') {
						if(!handleClosingTag(match, matchStack)) {
						    
							return null;
						}
					}
					else {
					    int tagEnd = -1;
					    Pattern p = Pattern.compile("[ \\t\\r\\n]");
					    Matcher m = p.matcher(matchStr);
					    if (m.find()) {
					        tagEnd  = m.end()-1;
					    }
					    
					    /*
						tagEnd = matchStr.indexOf(" ");	// Look for the first space
						int tabIndex = matchStr.indexOf("\t"); // Check if there's a tab before the space
						
						if (tabIndex > 0 
						        && (tabIndex < tagEnd || tagEnd < 0)) {
						    
						    tagEnd = tabIndex;
						}
						*/
						if(tagEnd == -1) {
							// No spaces, therefore it has no attributes (i.e. <cfscript>)
							tagEnd = matchStr.indexOf(">");
						}
						String tagName = match.match.substring(0, tagEnd);
						
						boolean isACloser = false;
						//
						// Find the end of the tag
						int currPos = 0;
						for(int quoteCount = 0; currPos < match.match.length(); currPos++) {
							char currChar = match.match.charAt(currPos);
							boolean inQuotes = (1 == quoteCount % 2);
							if(!inQuotes && currChar == '>') {
								break;
							}
							else if(currChar == '\"')
								quoteCount++;
						}

						//
						// Handle a self-closer (i.e. <cfproperty ... />
						String attributes = "";						
						if(match.match.charAt(currPos-1) == '/') {
							if(tagName.indexOf("/") != -1)
								tagName = tagName.substring(0, tagName.length()-1); // Is a self-closer (i.e. <br/>)
							isACloser = true;
							if(match.match.length() - tagEnd >= 2)
								attributes = match.match.substring(tagEnd, match.match.length()-2); // minus one to strip the closing '/>'
						}
						else
							attributes = match.match.substring(tagEnd, match.match.length()-1); // minus one to strip the closing '>'

						switch(match.getMatchType())
						{
							case MATCHER_CFMLTAG:
								handleCFTag(tagName, match, matchStack,
											stripAttributes(attributes, match.lineNumber, tagEnd), isACloser);
								break;
							case MATCHER_CFMLCOMMENT:
								//System.out.println("CFParser::createDocTree() - Got a CFML comment!");
								DocItem newComment = new CfmlComment(
										match.getLineNumber(),
										match.getStartPos(),
										match.getEndPos(),
										match.getMatch()
								);
								
								newComment.setItemData(match.getMatch());
								addDocItemToTree(match, matchStack, newComment);
								
								break;
							case MATCHER_CFSCRIPT:
								tagName = tagName.substring(1, tagName.length());
								TagItem newItem;								
								newItem = getNameBasedCfmlTag(tagName.substring(0, "cfscript".length()), match, getLineNumber(match.startPos));
								newItem.initDictionary(DictionaryManager.getDictionary(DictionaryManager.CFDIC));
								newItem.setItemData("");
								addTagItemToTree(match, matchStack, isACloser, newItem);
								handleCFScriptBlock(match, matchStack);								
								break;
							default:
								break;
						}
					}
				}
			}
			
			//newDoc.docTree = matchStack;
			
		}
		catch (FatalException e) {
			parserState.addMessage(new ParseError(
					getLineNumber(matchPos), matchPos, matchPos, "", 
					e.getMessage()
				));
		}
		catch(Exception anyException) {
			parserState.addMessage(new ParseError(
				getLineNumber(matchPos), matchPos, matchPos, "", 
				"Doc tree creation: caught an unhandled exception: " 
				+ anyException.getMessage()
			));
			//System.err.println(
			//	Util.GetTabs(matchStack) + "Parser: Caught an exception!" 
			//	+ anyException.getMessage()
			//);
			anyException.printStackTrace();
			////System.out..println(anyException.hashCode());
		}
		
		//System.out.println(rootItem.getFirstChild().getChildNodes().size() + " first grandchild");
		
		newDoc.setDocumentRoot(rootItem);
		return newDoc;
	}
	
	public final int MATCHER_NOTHING = 		0x00;
	public final int MATCHER_COMMENT = 		0x01;
	public final int MATCHER_HTMLTAG =		0x02;
	public final int MATCHER_ATTRIBUTE = 	0x04;
	public final int MATCHER_CFSCRIPT = 	0x08;
	public final int MATCHER_CFMLCOMMENT = 	0x16;
	public final int MATCHER_CFSCRCOMMENT = 0x32;
	public final int MATCHER_STRING = 		0x64;
	public final int MATCHER_CFMLTAG = 		0x128;
	
	protected final int INDEX_NOTFOUND =	-1;	// For String::indexOf(), make it nicer to read!
	
	protected int matchingHTML(State parseState, String inData, int currDocOffset)
	{
		int finalOffset = currDocOffset;
		int currPos = currDocOffset + 1;
		ParseItemMatch embeddedMatch = null;
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
				////System.out..println("CFParser::matchingHTML() - FOUND!: an embedded CFML tag within HTML! : " + inData.substring(0, currPos));
				currPos = matchingCFML(parseState, inData, currPos);
				cfTagCount++;
			}
			else if(!inQuotes && currChar == '>')
			{
				////System.out..println("CFParser::matchingHTML() - FOUND!: an HTML tag!: " + inData.substring(currDocOffset, currPos+1));				
				parseState.addMatch(new ParseItemMatch(inData.substring(currDocOffset, currPos+1), currDocOffset, currPos, 0, MATCHER_HTMLTAG), 
									State.ADD_BEFORE, cfTagCount);
				finalOffset = currPos;
				break;
			}
			else if(currChar == '\"')
				quoteCount++;
		}
		if(finalOffset != currPos)
		{
			//System.err.println("CFParser::matchingHTML() - FATAL ERROR: Failed to find the end of an HTML tag!: " + inData.substring(currDocOffset, currPos));
			
			parseState.addMessage(new ParseError(getLineNumber(currDocOffset), currDocOffset, currPos,
												inData.substring(currDocOffset, currPos), 
												"Reached end of document before finding end of HTML tag.",
												true )); // Fatal error
		}
		return currPos;
	}
	
	protected int matchingCFScript(State parseState, String inData, int currDocOffset)
	{
 		int finalOffset = currDocOffset;
		int currPos = currDocOffset;
		String nextChars = ""; // </cfscript>
		String closingText = "</cfscript>";
		//System.out.println("CFParser::matchingCFScript() - Matching CFScript");
		for(; currPos < inData.length(); currPos++)
		{
			if(inData.length() - currPos + 1 > closingText.length())
				nextChars = inData.substring(currPos, currPos + closingText.length());
			else 
				break;	// Not enough space left for it to be a closing cfscript tag.
			
			if(nextChars.compareTo(closingText) == 0)
			{
				finalOffset = currPos;
				break;
			}
			
		}
		//System.out.println("matchingCFScript() -");
		//System.out.println(inData.substring(currDocOffset, finalOffset));
	
		if(finalOffset != currPos)
		{
			System.err.println("FATAL ERROR: Searching for a closing <cfscript> tag but could not find one: " + inData.substring(currDocOffset, currPos));
			
			parseState.addMessage(new ParseError(getLineNumber(currDocOffset), currDocOffset, currPos,
												inData.substring(currDocOffset, currPos), 
												"Reached end of document before finding a closing cfscript tag.",
												true )); // Fatal error
		
		//} else if(this.parseCFScript) {
		} else if(true) {
			int scriptStart = currDocOffset + "<cfscript>".length();
			String cfScriptData = inData.substring(currDocOffset, finalOffset);
			cfScriptData = cfScriptData.trim();
			//System.out.println("CFScript data:");
			//System.out.println(cfScriptData);
			//
			// We cheat now. We're actually creating a tag match for a <cfscript> block and pass all
			// of the data in so we have a tag called "<cfscript>...". But this breaks if it's empty,
			// so we trimmed the data and now we compare with <cfscript>. If it doesn't equal it (
			// and therefore it's got CFScript data in) we add the match.
			//if(cfScriptData.toLowerCase().startsWith("<cfscript>")) {
				ParseItemMatch scriptMatch = new ParseItemMatch(cfScriptData, scriptStart, finalOffset, getLineNumber(scriptStart), MATCHER_CFSCRIPT);
				parseState.addMatch(scriptMatch);
				/*
				TagMatch endScriptTag = new TagMatch("</cfscript>", finalOffset, 
													finalOffset + "</cfscript>".length(), getLineNumber(finalOffset + 3));
				parseState.addMatch(endScriptTag);
				*/
			//}

		}
		
		
		//
		// finalOffset is assigned the end of the cfscript block _including_ the closing '</cfscript>'
		// so we remove it from the offset so that the parser can handle the closing tag correctly.
		return finalOffset - "</cfscript>".length();
	}
	
	/*
	protected int matchingCFScript(State parseState, String inData, int currDocOffset)
	{
		int finalOffset = currDocOffset;
		int currPos = currDocOffset;
		String nextChars = ""; // </cfscript>
		String closingText = "</cfscript>";
		////System.out..println("CFParser::matchingCFScript() - Matching CFScript");
		for(; currPos < inData.length(); currPos++)
		{
			if(inData.length() - currPos + 1 > closingText.length())
				nextChars = inData.substring(currPos, currPos + closingText.length());
			else 
				break;	// Not enough space left for it to be a closing cfscript tag.
			
			if(nextChars.compareTo(closingText) == 0)
			{
				finalOffset = currPos;
				break;
			}
			
		}
		
		int scriptStart = currDocOffset + "<cfscript>".length();
		String cfScriptData = inData.substring(scriptStart, finalOffset);
		
		TagMatch scriptMatch = new TagMatch(cfScriptData, scriptStart, finalOffset, getLineNumber(scriptStart));
		parseState.addMatch(scriptMatch);
	
		if(finalOffset != currPos)
		{
			//System.err.println("FATAL ERROR: Searching for a closing <cfscript> tag but could not find one: " + inData.substring(currDocOffset, currPos));
			
			parseState.addMessage(new ParseError(getLineNumber(currDocOffset), currDocOffset, currPos,
												inData.substring(currDocOffset, currPos), 
												"Reached end of document before finding a closing cfscript tag.",
												true )); // Fatal error
		
		}
		
		return finalOffset;
	}
	*/
	protected int matchingCFML(State parseState, String inData, int currDocOffset)
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
				
				parseState.addMatch(new ParseItemMatch(inData.substring(currDocOffset, currPos+1), currDocOffset, currPos, 
												getLineNumber(currDocOffset), MATCHER_CFMLTAG));
				break;
			}
			else if(currChar == '\"')
				quoteCount++;

		}
		if(finalOffset != currPos)
		{
			//System.err.println("FATAL ERROR: Failed to find the end of a CFML tag!: " + inData.substring(currDocOffset, currPos));
			
			parseState.addMessage(new ParseError(getLineNumber(currDocOffset), currDocOffset, currPos,
												inData.substring(currDocOffset, currPos), 
												"Reached end of document before finding end of CFML tag.",
												true )); // Fatal error
		}
		
		return finalOffset;
	}
	
	/**
	 * This function goes through the incoming stream of characters that represents the document to parse.
	 * It processes the document scanning for patterns within the data. Once a pattern has been found it
	 * is added to a list of matches. This match list will be used later on by the document tree creator.
	 * 
	 * @param inData
	 * @return
	 */
	protected ArrayList tagMatchingAttempts(String inData)
	{
		//String data = inData;
		String data = this.data2Parse;
		int lastMatch = 0;
		int currPos = 0;
		int currState = 0;
		Stack stateStack = new Stack();
		Stack statePositionStack = new Stack();
		
		ArrayList matches = new ArrayList();
		try {
			for(currPos = 0; currPos < data.length(); currPos++)
			{
				char currChar = data.charAt(currPos);
				String next2Chars = "";
				String next3Chars = "";
				String around = "";
				
				// Make sure we haven't had any fatal errors during parsing.
				if(parserState.hadFatal())
					break;
				//
				// Get some next data that will make our life easier in the code ahead
				next2Chars = (data.length() - currPos > 2) ? data.substring(currPos + 1, currPos + 3) : ""; 
				next3Chars = (data.length() - currPos > 3) ? next2Chars + data.charAt(currPos + 3) : "";
				
				around = getSurroundingData(data, currPos);
				
				if(currState == MATCHER_NOTHING && currChar == '<')
				{	
					if(next2Chars.compareTo("!-") == 0)
					{	// Testing for comment: <!--
						// TODO: Find out whether comments can occur in tags
						if(next3Chars.compareTo("!--") == 0 && data.charAt(currPos + 4) == '-')
						{
							stateStack.push(new Integer(currState));
							statePositionStack.push(new Integer(currPos));
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
						//
						// The following handles a CFScript tag. A CFScript tag is NOT part of the document tree as it is a 
						// container *only* for things to go in the document tree.
						if(data.length() - currPos > "<cfscript>".length() && 
						   data.substring(currPos, currPos + "<cfscript>".length()).compareTo("<cfscript>") == 0)
						{
							currPos = matchingCFScript(parserState, inData, currPos);
						}
						else 
							currPos = matchingCFML(parserState, inData, currPos);
					}
					else // Notice that the above if doesn't match </cf, that's because it's like a standard HTML tag.
					{
						currPos = matchingHTML(parserState, inData, currPos);
					}
					
				}
				else if(currState == MATCHER_CFMLCOMMENT 
						&& currChar == '-' 
						&& next2Chars.compareTo("--") == 0 
						&& inData.charAt(currPos+3) == '>')
				{
					currState = ((Integer)stateStack.pop()).intValue();
					int lastStatePos = ((Integer)statePositionStack.pop()).intValue();
					if(currState == MATCHER_NOTHING)
					{
						
						ParseItemMatch commentMatch = new ParseItemMatch(
							inData.substring(lastStatePos, currPos + 4), lastStatePos, currPos + 4, 
							getLineNumber(lastStatePos), MATCHER_CFMLCOMMENT
						);
						parserState.addMatch(commentMatch);
						
					}
				}
				else if(currState == MATCHER_COMMENT 
						&& currChar == '-' 
						&& next2Chars.compareTo("->") == 0)
				{
					currState = MATCHER_NOTHING;
				}
			}
		}catch(Exception excep) {
			parserState.addMessage(new ParseError(0, currPos, currPos, "", "Caught an exception during parsing.", true));
		}
		return matches;
	}
	
	/**
	 * Gets some surrounding data that is around the current cursor position.
	 * 
	 * @param data The data currently being scanned
	 * @param currPos The current position in the document
	 * @return The +/- 10 characters around the current position in the document 
	 */
	private String getSurroundingData(String data, int currPos) {
		String around = "";
		if(data.length() - currPos > 10 && currPos > 10)
			around = data.substring(currPos - 10, currPos) + data.substring(currPos, currPos + 10);
		else if(data.length() - currPos > 10)
			around = data.substring(currPos, currPos + 10);
		
		return around;
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
			attrs.put(IMarker.CHAR_START,new Integer(currMsg.docStartOffset));
			int endOffset = 0;
			if (currMsg.docEndOffset > currMsg.docStartOffset) {
				endOffset = currMsg.docEndOffset;
				System.out.println("End offset is: " + endOffset + " start is " + currMsg.docStartOffset);
			}
			else {
				endOffset = currMsg.docStartOffset+currMsg.docData.length();
			}
			attrs.put(IMarker.CHAR_END,new Integer(endOffset));
			
			//
			// Not sure what the start & end positions are good for!
			//MarkerUtilities.setCharStart(attrs, currMsg.getDocStartOffset());
			//MarkerUtilities.setCharEnd(attrs, currMsg.getDocEndOffset());
			//
			// Not sure right now how to set the problem to be a warning or an error.
			// There is IMarker.SEVERITY_ERROR & IMarker.SEVERITY_WARNING but I'm 
			// not sure how I set them.
			String type = "com.rohanclan.cfml.parserProblemMarker";
			if(currMsg instanceof ParseError)
			{
				type = "com.rohanclan.cfml.parserProblemMarker";
			}
			else if(currMsg instanceof ParseWarning)
			{
				type = "com.rohanclan.cfml.parserWarningMarker";
			}			
			
			try {
				MarkerUtilities.createMarker(this.res, attrs, type);
			}catch(CoreException excep) {
				userMessage(0, "userMessage", "ERROR: Caught CoreException when creating a problem marker. Message: \'" + excep.getMessage() + "\'");
			}catch(Exception anyExcep) {
				userMessage(0, "processParseResultMessage", "ERROR: Caught exception " + anyExcep.getMessage());
			}

		}
	}
	
	/**
	 * Traverses the document tree for the final time, calling each document item's
	 * sanity checker and then retrieving any parse messages that each document item
	 * may hold.
	 * @param startNode The node to start at.
	 * @return an <code>ArrayList</code> of the messages retrieved.
	 */
	public ArrayList finalDocTreeTraversal(DocItem startNode)
	{
		ArrayList messages = new ArrayList();
		
		//
		// Perform sanity check. Method adds to the object's message list which we shall gather next.
		startNode.IsSane();
		messages.addAll(startNode.getParseState().getMessages());
		if(startNode.hasChildren())
		{
			CFNodeList children = startNode.getChildNodes();
			Iterator nodeIter = children.iterator();
			
			while(nodeIter.hasNext())
			{
				messages.addAll(finalDocTreeTraversal((DocItem)nodeIter.next()));
			}
		}
		return messages;
	}
	
	public CFDocument parseDoc(String inData)
	{
		CFDocument docTree = null;
		
		try {
			parserState = new State("doesn\'t matter!");
			lineOffsets = Util.calcLineNumbers(inData);
			
			this.setData2Parse(inData);
			ArrayList matches = tagMatchingAttempts(inData);
			//System.out.println("=============> Beginning match dump");
			//Util.dumpMatches(parserState.getMatches());
			//System.out.println("=============> Finishing match dump");
			docTree = createDocTree(parserState.getMatches());
			parserState.addMessages(finalDocTreeTraversal(docTree. getDocumentRoot()));
			processParseResultMessages();
			
		} catch(Exception excep) 
		{
			//System.err.println("CFParser::parseDoc() - Exception: " + excep.getMessage());
		}
		return docTree;		
	}
	
	public CFDocument parseDoc()
	{
		if(parseDoc == null)
		{
			return parseDoc(data2Parse);
		}
		else
			return parseDoc(parseDoc.get());
	}
	
	public CFDocument parseDoc(IDocument doc2Parse)
	{
		return parseDoc(doc2Parse.get());
	}
	
	/**
	 * Parses the document and saves the result into the parseResult variable
	 * so it maintains it's tree.
	 * @author rob
	 * @deprecated
	 */
	/* public void parseSaveDoc()
	{
		//////System.out..println(parseDoc.get());
		parseResult = parseDoc();
	} */
	
}
