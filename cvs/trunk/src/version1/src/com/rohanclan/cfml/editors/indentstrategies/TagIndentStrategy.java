/*
 * Created on July 1st, 2004.
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
package com.rohanclan.cfml.editors.indentstrategies;

//import java.util.HashMap;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.dictionary.SyntaxDictionary;
import com.rohanclan.cfml.dictionary.Tag;
import com.rohanclan.cfml.editors.CFMLEditor;
import com.rohanclan.cfml.editors.ICFDocument;
import com.rohanclan.cfml.editors.partitioner.CFEPartition;
import com.rohanclan.cfml.editors.partitioner.CFEPartitioner;
import com.rohanclan.cfml.preferences.EditorPreferenceConstants;
/**
 * This represents a tag-based auto-indent strategy. It not only
 * does the auto-indenting, but it also does the auto-closing &
 * step-through of various characters.
 *
 * @author Oliver Tupman
 */
public class TagIndentStrategy extends CFEIndentStrategy {

	/** Indent when the tag is closed and a end tag is inserted */
	public static final int INDENT_ONTAGCLOSE = 0;
	/** Indent only when the user presses enter at this point &lt;cfif&gt;&lt;/cfif&gt; */
	public static final int INDENT_ONCLOSEDTAGENTER = 1;
	/** Don't indent */
	public static final int INDENT_DONTDOIT = 2;

	/** Auto-close double quotes */
	private boolean autoClose_DoubleQuotes = true;
	/** Auto-close single quotes */
	private boolean autoClose_SingleQuotes = true;
	/** Auto-close tags */
	private boolean autoClose_Tags = true;
	/** Auto-close hashes (#) */
	private boolean autoClose_Hashes = true;
	/** Auto-insert a closing tag */
	private boolean autoInsert_CloseTags = true;
	/** Use smart indent */
	private boolean useSmartIndent = true;
	/** When to trigger the auto-indent strategy when the user is in a tag */
	private int autoIndent_OnTagClose = INDENT_ONCLOSEDTAGENTER;


	/**
	 * @param editor
	 */
	public TagIndentStrategy(CFMLEditor editor) {
		super(editor);

	}
	/**
	 * Returns the integer position of the previous chevron. Basically
	 * scans backwards through the string until it hits a '<'. Note that
	 * it's not particularly clever so if there's a '<' within a string
	 * it will return it's position.
	 *
	 * @param docData - the string to search in
	 * @param position - the start position
	 * @return - the position of the previous chevron, or -1 on failure
	 */
	/* private int findPreviousChevron(String docData, int position) {
		int retval = -1;
		for(int i = position; i >= 0; i--)
		{
			if(docData.charAt(i) == '<')
			{

			}
		}

		return retval;
	} */

	/* private String getIntRep(String data) {
		String retval = "";

		for(int i = 0; i < data.length(); i++)
		{
			retval += data.charAt(i) + " = " + (int)data.charAt(i) + " ";
		}

		return retval;
	} */

	private String getWhiteSpace(String data, char stopChar) {
		String retval = "";
		//boolean found = false;
		int pos;

		for(pos = 0; pos < data.length(); pos++) {
			char currChar = data.charAt(pos);

			if(currChar != ' ' && currChar != '\t')
			{
				break;
			}
		}
		retval = data.substring(0, pos);

		return retval;
	}

	/**
	 * Returns the closing tag based upon the previous tag entered.
	 * So enter <cf_fred> and the closing tag returned is "cf_fred".
	 * The calling function can sort out the chevrons.
	 *
	 * @param currDoc - Current document that we're in
	 * @param documentOffset - the offset in the document
	 * @return the string name of the tag sans chevrons, otherwise a blank string
	 */
	private String getClosingTag(ICFDocument currDoc, int documentOffset)
	{
		String preTrigger;
		try {
			preTrigger = currDoc.get(0, documentOffset -1);
		} catch(BadLocationException ex) {
			System.err.println("XmlIndentStrategy::getClosingTag() - Caught bad location for preTrigger");
			return "";
		}
		int opener = preTrigger.lastIndexOf('<');
		if(opener == -1)
			return "";

		String startOfTag = preTrigger.substring(opener);
		int i;
		for(i = 1; i < startOfTag.length(); i++) {
			char currChar = startOfTag.charAt(i);
			if(currChar == ' ' || currChar == '/' || currChar == '>')
			{
				break;
			}
		}

		return startOfTag.substring(1, i);
	}

	/**
	 * Modifies the DocumentCommand to handle the user closing a single tag (i.e. &lt;nop/&gt;)
	 * @param command - the document command to modify
	 * @return the modified document command.
	 */
	private DocumentCommand singleTagTakeAction(IDocument doc, DocumentCommand command) {


	    char nextChar = ' ';
	    if (command.offset < doc.getLength()) {
	        try {
	        nextChar = doc.getChar(command.offset);
	        }
	        catch (BadLocationException e) {
	            e.printStackTrace();
	        }
	    }

	    if (nextChar != '>') {
	        return command;
	    }

		stepThrough(command);
		return command;
	}

	/* private int findStartofTag(IDocument doc, int offset) throws BadLocationException {
		int retval = -1;

		for(int i = offset; i >= 0; i--) {
			char currChar = doc.getChar(i);
			if(currChar == '>')
				return -1;

			if(currChar == '<')  {
				return i;
			}
		}

		return retval;
	} */

	/* private boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	} */

	/* private String getTagName(IDocument doc, int startPos) throws BadLocationException {
		//String retStr = "";
		int i = 0;
		for(i = startPos; i < doc.getLength(); i++) {
			if(isWhitespace(doc.getChar(i))) {
				break;
			}
		}

		return doc.get(startPos, i - startPos);
	} */

	private boolean tagIsSingle(IDocument doc, DocumentCommand docCommand, String tagName) {
		//boolean retval = true;

		boolean cftag = tagName.toLowerCase().startsWith("cf");
		//
		// End of block to comment out
		//
		SyntaxDictionary syntax = DictionaryManager.getDictionary((cftag) ? DictionaryManager.CFDIC : DictionaryManager.HTDIC);
		Tag currTag = syntax.getTag(tagName);
		if(currTag == null)
			return true;
		return currTag.isSingle();
	}

	/**
	 * Handles a closing, non-single tag. It will insert two lines. The first will be the next line
	 * that the cursor will be upon and will be indented by the opener tag's indent + 1 tab. The 2nd
	 * line will be on the same level as the opener and will contain the closing tag.
	 *
	 * @param doc - the document that this action is taking place within
	 * @param docCommand - the document command to modify
	 * @return the modified document command
	 * @throws BadLocationException
	 */
	private DocumentCommand doCloser(IDocument doc, DocumentCommand docCommand) throws BadLocationException {
		char lastChar = ' ';
		char nextChar = ' ';
		boolean autoCloseTag = this.autoClose_Tags;
		boolean isSingleTag = false;

		if(docCommand.offset >= 0)
			lastChar = doc.getChar(docCommand.offset - 1);

		if(docCommand.offset < doc.getLength())
			nextChar = doc.getChar(docCommand.offset);

		String closingTag = getClosingTag((ICFDocument)doc, docCommand.offset+1);

		isSingleTag = tagIsSingle(doc,docCommand,closingTag);

		autoCloseTag = !tagIsSingle(doc,docCommand, closingTag) && autoCloseTag;

		// If the user hasn't got auto-insertion of closing chevrons on, then
		// add a closing chevron onto our close tag (handled otherwise due to the fact we're inserting code IN the tag itself!
		if(!autoCloseTag
	        || nextChar != '>')
		    //System.out.println("Next char is a " + nextChar);
		//if(!autoCloseTag)
			closingTag+= ">";

		closingTag = "</" + closingTag;

		if(this.autoIndent_OnTagClose == INDENT_ONTAGCLOSE)
			doInBetweenTagIndent(doc, docCommand, lastChar);	// User wants us to insert a CR and indent.
		else {
			try {
				if(docCommand.offset != doc.getLength() && doc.getChar(docCommand.offset) == '>') {
					stepThrough(docCommand);
				}
				else {
					docCommand.caretOffset = docCommand.offset + 1;
					docCommand.shiftsCaret = false;
				}
				if(autoCloseTag && !closingTag.endsWith(">"))	// If we're auto-closing tags then we need a closing chevron.
					closingTag+= ">";

				//docCommand.caretOffset = docCommand.offset;
				//docCommand.shiftsCaret = true;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		if(!isSingleTag)
			docCommand.text += closingTag;		// Use the first line's whitespace and insert the closing tag.
		return docCommand;
	}

	/**
	 * Handles the case where a user has <tr>|<td> and presses enter.
	 * <tr>|</tr> will not indent.
	 * <tr>|<td> will indent.
	 * 
	 * @param doc
	 * @param docCommand
	 */
	private void handleEnterBetweenTags(IDocument doc, DocumentCommand docCommand) {
		
		if (doc instanceof ICFDocument && useSmartIndent) {
			ICFDocument cfd = (ICFDocument)doc;
			CFEPartitioner partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
			CFEPartition prevPartition = partitioner.getPreviousPartition(docCommand.offset);
			CFEPartition nextPartition = partitioner.getNextPartition(prevPartition.offset);
			//System.out.println(prevPartition);
			//System.out.println(nextPartition);
			if (nextPartition != null && prevPartition != null) {
				if (prevPartition.getType().endsWith("start_tag_end") && !nextPartition.getType().endsWith("end_tag")) {
					try {
						CFEPartition closer = partitioner.getCloser(prevPartition);
						if (closer != null) {
							
							String prevLineWhitespace = getPrevLineWhiteSpace(doc, docCommand.offset);
							docCommand.text+= indentString + guessNewIndentWhitespace(prevLineWhitespace);
							return;
						
						}
					} catch (BadLocationException e) {
						//
					}
				} else if (nextPartition.getType().endsWith("end_tag")) {
					try {
						CFEPartition opener = partitioner.getOpener(nextPartition);
						
						if (opener == null) {
							return;
						}
						int openerLine = doc.getLineOfOffset(opener.offset);
						int lineStart = doc.getLineOffset(openerLine);
						String prefix = doc.get(lineStart,opener.offset-lineStart);
						String tagStart = "<";
						int indent = prefix.indexOf(tagStart);
						
						for (int i=0;i<prefix.length();i++) {
							char c = prefix.charAt(i);
							if (c == '\t') {
								indent += indentSize-1;
							}
						}
						int cnt = (int)Math.round(new Float(indent).floatValue() / new Float(indentSize).floatValue());
						prefix = "";
						for (int i=0;i<cnt;i++) {
							prefix+=indentString;
						}
						docCommand.text+= prefix;
						return;
					
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		try {
			String prevLineWhitespace = getPrevLineWhiteSpace(doc, docCommand.offset);
			docCommand.text+= guessNewIndentWhitespace(prevLineWhitespace);
		} catch (BadLocationException e) {
			//
		}
	}
	/**
	 * @param doc
	 * @param docCommand
	 * @param lastChar
	 * @throws BadLocationException
	 */
	private void doInBetweenTagIndent(IDocument doc, DocumentCommand docCommand, char lastChar) throws BadLocationException {
		// Testing find start tag code
		//int startOfTag = findStartofTag(doc, docCommand.offset-1);
		/* if(startOfTag == -1) {
		}
		else {
			String tagName = getTagName(doc, startOfTag);
		} */
		// end of test

		//get the position of the < of this tag (so we can get it's indet level
		int openChevron = doc.get(0, docCommand.offset).lastIndexOf('<');
		if(openChevron == -1)
			return;

		//get the the space of the previous line useing the < as an anchor
		String whiteSpace = getPrevLineWhiteSpace(doc,openChevron);
		
		docCommand.caretOffset = docCommand.offset + indentString.length() + 2 + whiteSpace.length();

		if(lastChar == '>')
		{
			docCommand.text = "";
			docCommand.offset++;
		}

		//if(whiteSpace.length() == 0)
		//	docCommand.caretOffset--;

		docCommand.shiftsCaret = false;

		docCommand.text += "\n";	// End the current line the user is on.
		if(lastChar == '>') 
			docCommand.text+=indentString;
		
		docCommand.text += whiteSpace + indentString + "\n";	// Create the whitespace for the next line (the user will end up on this one)
		docCommand.text += whiteSpace;
	}

	/*
	 * <cffunction name="fred>				: true
	 * <cffunction name="fred"
	 * 			   returntype="boolean">	: true
	 *
	 * </cfoutput>							: false (1)
	 * <cfoutput>
	 * </cfoutput>
	 * >									: false (2)
	 */
	private static final int AFTEROPENTAG_AFTER 	= 0;
	private static final int AFTEROPENTAG_CLOSER 	= 1;
	private static final int AFTEROPENTAG_NOTAFTER 	= 2;

	private int afterOpenTag(IDocument doc, int offset) {
		int pos = offset - 1;
		try {
			for(; pos > 0; pos--) {
				char currChar = doc.getChar(pos);
				if(currChar == '>')					// Condition (2)
					return AFTEROPENTAG_NOTAFTER;
				else if(currChar == '/') {
					if(doc.getChar(pos-1) == '<')	// Condition (1)
						return AFTEROPENTAG_CLOSER;
				}
				else if(currChar =='<') {
				    return AFTEROPENTAG_AFTER;
				}
			}
		}catch(BadLocationException ex) {
			ex.printStackTrace();
		}
		return AFTEROPENTAG_AFTER;
	}

	/**
	 * Handles the case where the user has pressed <! and the strategy has
	 * auto-inserted a closing '>'. This deletes the next '>'
	 * @param doc
	 * @param docCommand
	 */
	private void handleHTMLComment(IDocument doc, DocumentCommand docCommand) {
		docCommand.offset++;
	}
	/**
	 * The method called by the editor.
	 */
	public void customizeDocumentCommand(IDocument doc, DocumentCommand docCommand)
    {
		try 
		{
			// We're only interested in the insertion of single characters, so catch 
			// the user pasting something (making sure that it's not going to be a 
			// carriage return)
			if(docCommand.text.length() > 1 && docCommand.text.compareTo("\r\n") != 0) 
			{
				return;
			}
			
			//int pos = docCommand.text.compareTo(">");
			char beforeLastChar = ' ';
			char firstCommandChar = (docCommand.text.length() > 0) ? docCommand.text.charAt(0) : ' ';

			if(docCommand.offset - 1 >= 0) 
			{
				beforeLastChar = doc.getChar(docCommand.offset-1);
			}
			
			// Handle a backspace or delete
			if(docCommand.length > 0 && docCommand.text.length() == 0) 
			{
				firstCommandChar = '\b';
			}

			switch(firstCommandChar) 
			{
				//User wishes to delete something
				case '\b':	
					//handleDelete(doc, docCommand);
					return;
				case '!':
					if(doc.getChar(docCommand.offset-1) == '<'
						&& doc.getLength() > docCommand.offset+1
						&& doc.getChar(docCommand.offset+1) == '>')
					{
							handleHTMLComment(doc, docCommand);
					}
					return;
				case '>':
					if(!this.autoInsert_CloseTags)
					{
						if(doc.getLength() > docCommand.offset 
						        && doc.getChar(docCommand.offset+1) == '>')
							stepThrough(docCommand);
						return;
					}
	
					handleClosingChevron(doc, docCommand, beforeLastChar);
					return;
				case '<':
					if(!this.autoClose_Tags) return;
	
					handleOpenChevron(docCommand);
					return;
				case '\"':
					if(!this.autoClose_DoubleQuotes) 
					{
						//User doesn't want us to do this
						return; 
					}
					handleQuotes(doc, docCommand, firstCommandChar);
					return;
				//Handle opening/closing quotes
				case '\'':	
					if(!this.autoClose_SingleQuotes) return;
					handleQuotes(doc, docCommand, firstCommandChar);
					return;
				//Handle opening/closing quotes
				case '#':	
					if(!this.autoClose_Hashes) return;
					handleHashes(doc, docCommand);
					return;
				//handle tabs
				case '\t': 
				    singleLineIndent(doc,docCommand);
				    return;
				default:
					// Check to make sure that the text entered isn't a LF/CRLF and that
					// there is actually data in the command. Otherwise cop out.
					if((docCommand.text.compareTo("\r\n") != 0
							&& docCommand.text.compareTo("\n") != 0)
							|| docCommand.length != 0) 
					{
						//attempt to register a default behavior
						super.customizeDocumentCommand(doc,docCommand);
						return;
					}
				break;
			}
			
			// Here the user must've pressed enter to go to a new line.
			// So we have two options:
			// 1) They're just pressing in between two tags OR
			// 2) They've pressed enter WITHIN a tag (i.e. <cffred name="blah" [ENTER]>
			if(isEnterInTag(doc, docCommand)) 
			{
				handleEnterInTag(doc, docCommand);
			}
			else 
			{
				handleEnterBetweenTags(doc,docCommand);
				//String prevLineWhitespace = getPrevLineWhiteSpace(doc, docCommand.offset);
				//docCommand.text += guessNewIndentWhitespace(prevLineWhitespace); //prevLineWhitespace
			}
		} 
		catch(BadLocationException ex) 
		{
			System.err.println("TagIndentStategy::customizeDocumentCommand() - Caught BadLocationException");
			ex.printStackTrace();
			return;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
    }

	/**
	 * When a client hits enter between tags the previous line is looked at to get the
	 * indent depth - if the file was made with "tabs as spaces" and the new person
	 * editing uses proper tabs then the new indents should be tabs not spaces (or
	 * so the bug report goes :)
	 * 
	 * This looks at the preferences and switches the tab/space indent as best it can.
	 * you run into trouble with things like is 6 spaces one tab or 2.
	 * @param guessInfo
	 * @return
	 */
	private String guessNewIndentWhitespace(String previousWhitespace)
	{
		if (previousWhitespace.length() == 0) {
			return "";
		}
		//if we are now set to "tabs for tabs"..
		if(this.getIndentString().compareTo("\t") == 0) 
		{
			//if the last whitespace is a tab too assume we are all good
			if(previousWhitespace.charAt(0) == '\t')
			{
				return previousWhitespace;
			}
			//last indent line was spaces so time to guess how many tabs that is
			else if(previousWhitespace.charAt(0) == ' ')
			{
				//use the tab width setting as a guide
				int numtabs = previousWhitespace.length() / indentSize;
				StringBuffer sb = new StringBuffer();
				for(int x=0; x<numtabs;x++)
				{
					sb.append('\t');
				}
				return sb.toString();
			}
			else
			{
				throw new IllegalArgumentException(
					"whitespaces guesser got non whitespace: ]" + previousWhitespace + "["
				);
			}
		}
		//we are set to spaces for tabs
		else 
		{
			//if the last whitespace is a space too assume we are all good
			if(previousWhitespace.charAt(0) == ' ')
			{
				return previousWhitespace;
			}
			else if(previousWhitespace.charAt(0) == '\t')
			{
				int spacelen = previousWhitespace.length() * indentSize;
				StringBuffer sb = new StringBuffer();
				for(int x=0; x<spacelen;x++)
				{
					sb.append(' ');
				}
				return sb.toString();
			}
			else
			{
				throw new IllegalArgumentException(
					"whitespaces guesser got non whitespace: ]" + previousWhitespace + "["
				);
			}
		}
	}
	
	/**
	 * Handles the event when a user presses 'delete'.
	 * Basically it's here to try and remove any auto-inserted characters.
	 * Say the user entered an open chevron. It's quite probable that on
	 * opening a closer was inserted. So here we try and work out whether the user
	 * entered an opener, we auto-inserted a closer and the user now wishes to
	 * get rid of both.
	 *
	 * Doesn't work at the moment :(
	 *
	 * @param doc document to work upon
	 * @param docCommand command to change
	 * @throws BadLocationException doh
	 
	private void handleDelete(IDocument doc, DocumentCommand docCommand) throws BadLocationException {
		char prevChar = doc.getChar(docCommand.offset);
		// Initialize nextChar to an ASCII null
		char nextChar = (char)0;
		// If we're not at the end of the document reassign nextChar
		if (doc.getLength() > docCommand.offset + 1) {
		 nextChar = doc.getChar(docCommand.offset + 1);
		}

		switch(prevChar) {
			case '\"':
			case '#':
				if(nextChar == prevChar) {
					docCommand.text = "";
					docCommand.caretOffset = docCommand.offset + 2;
				}
				break;
			case '<':
				if(nextChar == '>') {
					docCommand.text = "";
					docCommand.caretOffset = docCommand.offset + 2;
				}
				break;
			default:
				break;
		}
		return;
	}
	*/
	/**
	 * Handles the user typing in a closing chevron.
	 * s
	 * @param doc
	 * @param docCommand
	 * @param beforeLastChar
	 * @throws BadLocationException
	 */
	private void handleClosingChevron(IDocument doc, DocumentCommand docCommand, char beforeLastChar) throws BadLocationException {

	    if(beforeLastChar == '<')	// Have we not got a tag name
			return;
		else if(beforeLastChar == '/')	{	// A self-closer, i.e. : <br/>
			//singleTagTakeAction(doc, docCommand);
			if(this.autoClose_Tags) {
				singleTagTakeAction(doc, docCommand);
			}
			else
				docCommand.doit =true;
		}
		else {
			// Got a '>', make sure that it's not a random closer and if not close the tag.
			switch(afterOpenTag(doc, docCommand.offset)) {
			case AFTEROPENTAG_AFTER:
				doCloser(doc, docCommand);
				break;
			case AFTEROPENTAG_CLOSER:
				char currChar = doc.getChar(docCommand.offset);
				if(currChar == '>')
					stepThrough(docCommand);
				break;
			case AFTEROPENTAG_NOTAFTER:
			default:
				break;
			}

		}
		return;
	}
	/**
	 * Performs the required operations to provide indenting when the user presses enter
	 * inside a tag.
	 *
	 * @param docCommand - the document command to work up.
	 */
	private void handleEnterInTag(IDocument doc, DocumentCommand docCommand) 
	{
		//String thisLineWhitespace = "";
		try 
		{
			int currLine = doc.getLineOfOffset(docCommand.offset);
			//int nextLineOffset = doc.getLineOffset(currLine+1);

			String lineDelim = doc.getLineDelimiter(currLine);
			//windows :-p
			if(lineDelim == null) lineDelim = "\r\n";
						
			String prefix = getPrevLineWhiteSpace(doc,docCommand.offset);
			
			int extraIndentSize = 0;
			
			for(int i=docCommand.offset-1; i > 0; i--) {
				char c = doc.getChar(i);
				extraIndentSize++;
				if(c == '\r' || c == '\n')
					break; // Got to the start of the line. 
				if (c == '\t' || c == ' ') {
					extraIndentSize=1;
				}
				if(doc.getChar(i) == '<') {
					break;
				}
			}
			
			int cnt = (int)Math.round(new Float(extraIndentSize).floatValue() / new Float(indentSize).floatValue());
			
			for (int i=0;i<cnt;i++) {
				prefix+=indentString;
			}
			
			//figure out what the indent level is and take into account the tab/space
			//setting
			docCommand.text += guessNewIndentWhitespace(prefix);
		} 
		catch(BadLocationException ex) 
		{
			ex.printStackTrace();
			return;
		} 
		catch(Exception e) 
		{	
			// Catch-all. One would hope we never reach here!
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Figures out if the document command occurred inside a tag that starts on the current line.
	 * 
	 * @param doc
	 * @param docCommand
	 * @return
	 */
	private boolean isTagStartOnLine(IDocument doc,DocumentCommand command) {
		int position = command.offset - 1;
		String docData = doc.get();
		boolean openerFound = false;
		//
		// First, search backwards. We should hit a '<' before we hit a '>'.
		int i = position;
		try {
			for(; i > 0; i--) {
				char c = docData.charAt(i);
				if(c == '\r' || c == '\n')
					return false; // Got to the start of the line. 
				if(docData.charAt(i) == '<') {
					return true;
				}
			}
		} catch(Exception e) {
			System.err.println("TagIndentStrategy::isEnterInTag() - Caught exception \'" + e.getMessage() +"\'. Dumping.");
			e.printStackTrace();
			return false;
		}
		
		
		return false;
	}
	
	/**
	 * Finds the end of the tag. Really just looks until it hits non-whitespace.
	 *
	 * @param data - the data to scan
	 * @param offset - the offset within the document to start looking from.
	 * @return - the position of the character <strong>after</strong> the end of tag.
	 */
	//private int findEndOfTag(String data, int offset) {
	/* private int findEndOfTag(IDocument data, int offset) {
		int pos = offset;
		int startOfTag = 0;

		try {
			startOfTag = findStartofTag(data, pos-1);
		}catch(BadLocationException ex) {
			ex.printStackTrace();
			return 0;
		}

		if(startOfTag == -1)
			return 0;

		try {
			for(int i = startOfTag; i < data.getLength(); i++) {
				char currChar = data.getChar(i);
				if(isWhitespace(currChar)) {
					return i;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		return pos;
	} */

	/**
	 * Has the user pressed enter from within a tag (i.e. they were editing the attributes)?
	 *
	 * @param doc - Document that we're operating on
	 * @param command - the command
	 * @return - True: yes, enter pressed in a tag. False: Nope, outside of a tag.
	 */
	/*
	 * <cffunction name="fred"   >
	 */
	private boolean isEnterInTag(IDocument doc, DocumentCommand command) {
		int position = command.offset - 1;
		String docData = doc.get();
		boolean openerFound = false;
		//
		// First, search backwards. We should hit a '<' before we hit a '>'.
		int i = position;
		try {
			for(; i >= 0; i--) {
				if(docData.charAt(i) == '>')
					return false;	// Found closing chevron, die now.	
									// TODO: Will kill if closing chevron is in quotes!

				if(docData.charAt(i) == '<' && Character.isLetter(docData.charAt(i+1))) {
					openerFound = true;
					break;
				}
			}
		} catch(Exception e) {
			System.err.println("TagIndentStrategy::isEnterInTag() - Caught exception \'" + e.getMessage() +"\'. Dumping.");
			e.printStackTrace();
			return false;
		}

		return openerFound;
	}

	/**
	 * Handles the opening of a chevron. Basically it inserts a matching closing '>' and does
	 * not shift the caret.
	 * @param docCommand - the document command to modify
	 */
	private void handleOpenChevron(DocumentCommand docCommand) {
		docCommand.text += ">";
		docCommand.shiftsCaret = false;
		docCommand.caretOffset = docCommand.offset+1;
	}

	/**
	 * Handles the insertion of quotes by the user. If the user has opened quotes then
	 * it inserts a closing quote after the opened quote and does not move the caret.
	 * If the user is closing some quotes it steps through the existing quote.
	 *
	 * @param doc - The document that the command is being performed in
	 * @param docCommand - the command to modify
	 * @param quoteChar - the quote character that triggered this. This allows us to handle " and ' quotes.
	 * @throws BadLocationException - ack.
	 */
	private void handleQuotes(IDocument doc, DocumentCommand docCommand, char quoteChar) throws BadLocationException {
		char nextChar = (char)0;
		char prevChar = (char)0;
		try {
			nextChar = doc.getChar(docCommand.offset);
			prevChar = doc.getChar(docCommand.offset-1);
		}
		catch (BadLocationException bex) {
			// do nothing
		}
		if (prevChar == quoteChar
				|| nextChar == '#'
				|| prevChar == '#') {
			return;
		}
		
		if (quoteChar == '\'') {
			if (prevChar == '"' || nextChar == '"') {
				return;
			}
		}
		
		if (quoteChar == '"') {
			if (prevChar == '\'' || nextChar == '\'') {
				return;
			}
		}
		
		if(nextChar == quoteChar)
		{
			docCommand.text = "";
			docCommand.shiftsCaret = false;
			docCommand.caretOffset = docCommand.offset+1;
			return;
		}

		docCommand.text+= quoteChar;
		docCommand.caretOffset = docCommand.offset+1;
		docCommand.shiftsCaret = false;
		return;
	}

	/**
	 * Handles the insertion of quotes by the user. If the user has opened quotes then
	 * it inserts a closing quote after the opened quote and does not move the caret.
	 * If the user is closing some quotes it steps through the existing quote.
	 *
	 * @param doc - The document that the command is being performed in
	 * @param docCommand - the command to modify
	 * @param quoteChar - the quote character that triggered this. This allows us to handle " and ' quotes.
	 * @throws BadLocationException - ack.
	 */
	private void handleHashes(IDocument doc, DocumentCommand docCommand) throws BadLocationException {
		char nextChar = (char)0;
		try {
			nextChar = doc.getChar(docCommand.offset);
		}
		catch (BadLocationException bex) {
			// do nothing
		}
		if(nextChar == '#')
		{
			docCommand.text = "";
			docCommand.shiftsCaret = false;
			docCommand.caretOffset = docCommand.offset+1;
			return;
		}

		docCommand.text+= '#';
		docCommand.caretOffset = docCommand.offset+1;
		docCommand.shiftsCaret = false;
		return;
	}

	/**
	 * Gets the whitespace of the previous line. Doesn't work very well at the moment :(
	 *
	 * @param doc
	 * @param docCommand
	 * @param lineNumber
	 * @throws BadLocationException
	 */
	private String getPrevLineWhiteSpace(IDocument doc, int offset) throws BadLocationException {
		int lineNumber = doc.getLineOfOffset(offset);

		if(lineNumber == 0)
			return "";
		int prevLineOffset = doc.getLineOffset(lineNumber);
		String prevLineData = doc.get(prevLineOffset, offset - prevLineOffset);
		return getWhiteSpace(prevLineData, '<');
	}
	/**
	 * @return Returns the autoClose_DoubleQuotes.
	 */
	public boolean isAutoClose_DoubleQuotes() {
		return autoClose_DoubleQuotes;
	}
	/**
	 * @param autoClose_DoubleQuotes The autoClose_DoubleQuotes to set.
	 */
	public void setAutoClose_DoubleQuotes(boolean autoClose_DoubleQuotes) {
		this.autoClose_DoubleQuotes = autoClose_DoubleQuotes;
	}
	/**
	 * @return Returns the autoClose_Hashes.
	 */
	public boolean isAutoClose_Hashes() {
		return autoClose_Hashes;
	}
	/**
	 * @param autoClose_Hashes The autoClose_Hashes to set.
	 */
	public void setAutoClose_Hashes(boolean autoClose_Hashes) {
		this.autoClose_Hashes = autoClose_Hashes;
	}
	/**
	 * @return Returns the autoClose_SingleQuotes.
	 */
	public boolean isAutoClose_SingleQuotes() {
		return autoClose_SingleQuotes;
	}
	/**
	 * @param autoClose_SingleQuotes The autoClose_SingleQuotes to set.
	 */
	public void setAutoClose_SingleQuotes(boolean autoClose_SingleQuotes) {
		this.autoClose_SingleQuotes = autoClose_SingleQuotes;
	}
	/**
	 * @return Returns the autoClose_Tags.
	 */
	public boolean isAutoClose_Tags() {
		return autoClose_Tags;
	}
	/**
	 * @param autoClose_Tags The autoClose_Tags to set.
	 */
	public void setAutoClose_Tags(boolean autoClose_Tags) {
		this.autoClose_Tags = autoClose_Tags;
	}
	/**
	 * @return Returns the autoIndent_OnTagClose.
	 */
	public int getAutoIndent_OnTagClose() {
		return autoIndent_OnTagClose;
	}
	/**
	 * @param autoIndent_OnTagClose The autoIndent_OnTagClose to set.
	 */
	public void setAutoIndent_OnTagClose(int autoIndent_OnTagClose) {
		this.autoIndent_OnTagClose = autoIndent_OnTagClose;
	}
	/**
	 * @return Returns the autoInsert_CloseTags.
	 */
	public boolean isAutoInsert_CloseTags() {
		return autoInsert_CloseTags;
	}
	/**
	 * @param autoInsert_CloseTags The autoInsert_CloseTags to set.
	 */
	public void setAutoInsert_CloseTags(boolean autoInsert_CloseTags) {
		this.autoInsert_CloseTags = autoInsert_CloseTags;
	}
	/**
	 * @param useSmartIndent The useSmartIndent value.
	 */
	public void setUseSmartIndent(boolean useSmartIndent) {
		this.useSmartIndent = useSmartIndent;
	}
}
