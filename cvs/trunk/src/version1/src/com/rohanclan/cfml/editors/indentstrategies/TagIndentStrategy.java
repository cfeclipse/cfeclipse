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

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultAutoIndentStrategy;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;

import com.rohanclan.cfml.editors.CFMLEditor;
import com.rohanclan.cfml.editors.ICFDocument;

/**
 * @author Oliver Tupman
 */
public class TagIndentStrategy extends CFEIndentStrategy {

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
	private int findPreviousChevron(String docData, int position) {
		int retval = -1;
		for(int i = position; i >= 0; i--)
		{
			if(docData.charAt(i) == '<')
			{
				
			}
		}
		
		return retval;
	}
	
	private String getIntRep(String data) {
		String retval = "";
		
		for(int i = 0; i < data.length(); i++)
		{
			retval += data.charAt(i) + " = " + (int)data.charAt(i) + " ";
		}
		
		return retval;
	}
	
	private String getWhiteSpace(String data, char stopChar) {
		String retval = "";
		boolean found = false;
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
	 * So enter <cf_fred> and the closing tag returned is </cf_fred>
	 * 
	 * @param currDoc - Current document that we're in
	 * @param documentOffset - the offset in the document
	 * @return - the string name of the tag sans chevrons, otherwise a blank string
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
		command.text = "";
		command.offset++;
		command.doit = false;
		return command;
	}

	private int findStartofTag(IDocument doc, int offset) throws BadLocationException {
		int retval = -1;
		
		for(int i = offset; i < doc.getLength(); i--) {
			char currChar = doc.getChar(i);
			if(currChar == '>')
				return -1;
			
			if(currChar == '<')  {
				return i;
			}
		}
		
		return retval;
	}
	
	private boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}
	
	private String getTagName(IDocument doc, int startPos) throws BadLocationException {
		String retStr = "";
		int i = 0;
		for(i = startPos; i < doc.getLength(); i++) {
			if(isWhitespace(doc.getChar(i))) {
				break;
			}
		}
		
		return doc.get(startPos, i - startPos);
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

		if(docCommand.offset >= 0)
			lastChar = doc.getChar(docCommand.offset - 1);
		
		String closingTag = getClosingTag((ICFDocument)doc, docCommand.offset+1)/* + ">"*/;	// Don't close because that character is there as the user just entered it!
		closingTag = "</" + closingTag;

		// Testing find start tag code
		int startOfTag = findStartofTag(doc, docCommand.offset-1);
		if(startOfTag == -1) {
		}
		else {
			String tagName = getTagName(doc, startOfTag);
		}
		// end of test

		int openChevron = doc.get(0, docCommand.offset).lastIndexOf('<');
		if(openChevron == -1) 
			return docCommand;
		
		String whiteSpace = getPrevLineWhiteSpace(doc,openChevron);		
		docCommand.caretOffset = docCommand.offset + 3 + whiteSpace.length();

		
		if(lastChar == '>')
		{
			docCommand.text = "";
			docCommand.offset++;
		}
			
		if(whiteSpace.length() == 0)
			docCommand.caretOffset--;
		
		docCommand.shiftsCaret = false;
		
		docCommand.text += "\n";	// End the current line the user is on.
		if(lastChar == '>') docCommand.text+="\t";
		docCommand.text += whiteSpace + "\t" + "\n";	// Create the whitespace for the next line (the user will end up on this one)
		docCommand.text += whiteSpace + closingTag;		// Use the first line's whitespace and insert the closing tag.
		return docCommand;
	}
	
	/**
	 * The method called by the editor.
	 */
	public void customizeDocumentCommand(IDocument doc, DocumentCommand docCommand)
    {
		try {
			char lastChar = doc.getChar(docCommand.offset);
			int pos = docCommand.text.compareTo(">");
			char beforeLastChar = ' ';
			char firstCommandChar = (docCommand.text.length() > 0) ? docCommand.text.charAt(0) : ' ';
			
			if(docCommand.offset - 1 >= 0) {
				beforeLastChar = doc.getChar(docCommand.offset-1);
			}
			
			//
			// Handle a closing chevron.
			if(docCommand.length > 0 && docCommand.text.length() == 0) {
				firstCommandChar = '\b';
			}
			
			switch(firstCommandChar) {
			case '\b':
				char prevChar = doc.getChar(docCommand.offset);
				char nextChar = doc.getChar(docCommand.offset + 1);
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
			case '>':
				if(beforeLastChar == '<')
					return;
				else if(beforeLastChar == '/')	{
					docCommand = singleTagTakeAction(doc, docCommand);
				}
				else {
					docCommand = doCloser(doc, docCommand);
				}			
				return;
			case '<':
				handleOpenChevron(docCommand);
				return;
			case '\"':
			case '\'':	// Handle opening/closing quotes
				handleQuotes(doc, docCommand, firstCommandChar);
				return;
			default:
				//
				// Check to make sure that the text entered isn't a CF/CRLF and that
				// there is actually data in the command. Otherwise cop out.
				if((docCommand.text.compareTo("\r\n") != 0
						&& docCommand.text.compareTo("\n") != 0)
						|| docCommand.length != 0) {
				//System.out..println("TagIndentStrategy::customizeDocument() - In fall out");
					return;
				}
				break;
			}
			//
			// Here the user must've pressed enter to go to a new line. 
			// So we have two options:
			// 1) They're just pressing in between two tags OR
			// 2) They've pressed enter WITHIN a tag (i.e. <cffred name="blah" [ENTER]>
			
			if(isEnterInTag(doc, docCommand)) {
			//System.out..println("TagIndentStrategy::customizeDocumentCommand() - User is in a tag...");
				handleEnterInTag(doc, docCommand);
			}
			//else
			//System.out..println("TagIndentStrategy::customizeDocumentCommand() - User is NOT IN a tag...");
	
			String prevLineWhitespace = getPrevLineWhiteSpace(doc, docCommand.offset);
			docCommand.text+= prevLineWhitespace;
		} catch(BadLocationException ex) {
			System.err.println("XmlIndentStategy::customizeDocumentCommand() - Caught BadLocationException");
			return;
		}
    }
	
	/**
	 * Performs the required operations to provide indenting when the user presses enter
	 * inside a tag.
	 * 
	 * @param docCommand - the document command to work up.
	 */
	private void handleEnterInTag(IDocument doc, DocumentCommand docCommand) {
		String thisLineWhitespace = "";
		try {
			int currLine = doc.getLineOfOffset(docCommand.offset);
			int nextLineOffset = doc.getLineOffset(currLine+1);
			
			String lineDelim = doc.getLineDelimiter(currLine);
			if(lineDelim == null) lineDelim = "\r\n";
			
			int colPosition = doc.getLineLength(currLine) - 1 - lineDelim.length();
			
			//
			// Now we just need to work out how much indentation to do...
			//int posForIndent = findEndOfTagNameOrStartOfAttribute(doc.get(), docCommand.offset-1);
			int posForIndent = findEndOfTag(doc, docCommand.offset);
			int numIndents = 0;
			int indentWidth = 0;
			int spaceRemainder = 0;
			int colPos = posForIndent - doc.getLineOffset(currLine);

			//
			// Work out our indent. If it's a tab we just use 4 (editor default), otherwise the length of the indent string
			// Then we work out the indents required to get us to the column position. Then we work out
			// what the remainder is that cannot be made up of full indents. This will be made up of spaces.
			// Then we simply append the required indents in, then the required number of spaces.
			if(this.getIndentString().compareTo("\t") == 0) 
				indentWidth = 4;	// TODO: Work out how to get the texteditor tab width
			else
				indentWidth = this.getIndentString().length();
			
			numIndents = colPos / indentWidth;
			spaceRemainder = colPos - (indentWidth * numIndents);
			
			for(int i = 0; i < numIndents; i++ ) {
				docCommand.text += this.getIndentString();
			}
			
			for(int i = 0; i < spaceRemainder; i++) {
				docCommand.text += " ";
			}
				
		} catch(BadLocationException ex) {
			ex.printStackTrace();
			return;
		} catch(Exception e) {	// Catch-all. One would hope we never reach here!
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Finds the end of the tag. Really just looks until it hits non-whitespace.
	 * 
	 * @param data - the data to scan
	 * @param offset - the offset within the document to start looking from.
	 * @return - the position of the character <strong>after</strong> the end of tag.
	 */
	//private int findEndOfTag(String data, int offset) {
	private int findEndOfTag(IDocument data, int offset) {	
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
	}
		
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

		//
		// First, search backwards. We should hit a '<' before we hit a '>'.
		int i = position;
		try {
			for(; i > 0; i--) {
				if(docData.charAt(i) == '>')
					return false;	// Found closing chevron, die now.	// TODO: Will kill if closing chevron is in quotes!
				
				if(docData.charAt(i) == '<')
					break;
			}
		} catch(Exception e) {
			System.err.println("TagIndentStrategy::isEnterInTag() - Caught exception \'" + e.getMessage() +"\'. Dumping.");
			e.printStackTrace();
			return false;
		}
		
		return true;
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
		char nextChar = doc.getChar(docCommand.offset);
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
}
