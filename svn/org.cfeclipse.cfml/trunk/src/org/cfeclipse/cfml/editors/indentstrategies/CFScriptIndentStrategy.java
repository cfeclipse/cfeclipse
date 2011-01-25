/**********************************************************************
Copyright (c) 2000, 2002 IBM Corp. and others.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Common Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/cpl-v10.html

Contributors:
    IBM Corporation - Initial implementation
    Klaus Hartlage - www.eclipseproject.de
**********************************************************************/
package org.cfeclipse.cfml.editors.indentstrategies;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.formatters.FormattingPreferences;
import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;


public class CFScriptIndentStrategy extends CFEIndentStrategy {
	
	/** Auto-insert a closing brace */
	private boolean autoClose_Braces = true;
	private String fIndentString = new FormattingPreferences().getCanonicalIndent();
	
	/**
	 * @param editor
	 */
	public CFScriptIndentStrategy(CFMLEditor editor) {
		super(editor);
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
	private void handlePotentialClosingChar(IDocument doc, DocumentCommand docCommand, char quoteChar)
	throws BadLocationException {
		char nextChar = doc.getChar(docCommand.offset);
		if(nextChar == quoteChar)
		{
			stepThrough(docCommand);
			return;
		}
		insertSingleChar(docCommand, quoteChar);
		return;
	}

	/**
	 * Inserts one character and steps over the character (make sense?)
	 * @param docCommand - the doc command to work upon
	 * @param newChar - the character to insert
	 */
	private void insertSingleChar(DocumentCommand docCommand, char newChar) {
		// do no auto insert if this is a paste
		if (docCommand.text.length() == 1) {
			docCommand.text += newChar;
			docCommand.caretOffset = docCommand.offset + 1;
			docCommand.shiftsCaret = false;
		}
	}
	
	/* private void handleOpeningParen(char nextChar,char prevChar, char trigChar, 
									IDocument doc, DocumentCommand docCommand)
	{
		
	} */
	
	private void handleClosingBracket(char nextChar,char prevChar, char trigChar, 
										IDocument doc, DocumentCommand docCommand)
	{
		if(nextChar == trigChar)
			stepThrough(docCommand);
	}
	
	/**
	 * Handles the insertion & step-through of code.
	 * 
	 * @param doc - the document to work upon
	 * @param docCommand - command to modify
	 */
	private void codeInsertion(IDocument doc, DocumentCommand docCommand) {
		char nextChar = ' ';
		char prevChar = ' ';
		//char currChar = ' ';
		char trigChar = ' ';
		boolean inComment = false;
		
		//
		// We don't do anything if there isn't any text that's been entered.
		// Basicall we're not interested in anything but the user inserting text.
		
		//
		// Handle a closing chevron.
		if(docCommand.length > 0 && docCommand.text.length() == 0) {
			trigChar = '\b';
		}
		else
			trigChar = docCommand.text.charAt(0);
		
		try {
			if(docCommand.offset < doc.getLength()) {
				nextChar = doc.getChar(docCommand.offset);
			}
			//currChar = doc.getChar(docCommand.offset-1);
			doc.getChar(docCommand.offset-1);
			if(docCommand.offset > 0) {
				prevChar = doc.getChar(docCommand.offset-2);
			}
			ITypedRegion partition = doc.getPartition(docCommand.offset);
			String partType = partition.getType();
			if (partType == CFPartitionScanner.CF_COMMENT || partType == CFPartitionScanner.CF_SCRIPT_COMMENT_BLOCK
					|| partType == CFPartitionScanner.CF_SCRIPT_COMMENT
					|| partType == CFPartitionScanner.JAVADOC_COMMENT) {
				inComment = true;
			}
			
		
		}catch(BadLocationException ex) {
			ex.printStackTrace();
			return;
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
	//System.out.println("CFScriptIndentStrategy::codeInsertion() - ");
	//System.out.println("nextChar = \'" + nextChar + "\'");
	//System.out.println("prevChar = \'" + prevChar + "\'");
	//System.out.println("trigChar = \'" + trigChar + "\'");
		try {
			switch(trigChar) {
			case '\b':
				switch(nextChar) {
					case '\"':
					case '#':
						if(nextChar == prevChar) {
							docCommand.text = "";
							docCommand.caretOffset = docCommand.offset + 2;
						}
						break;
					default:
						break;
				}
				return;
	// The following are for closed chars that have the same
	// opening and closing character.
			case '\'':
				if (this.isAutoClose_SingleQuotes() && !inComment)
					handlePotentialClosingChar(doc, docCommand, '\'');
				break;
			case '\"':
				if (this.isAutoClose_DoubleQuotes() && !inComment)
					handlePotentialClosingChar(doc, docCommand, '\"');
				break;
			case '#':
				if (this.isAutoClose_Hashes() && !inComment)
					handlePotentialClosingChar(doc, docCommand, '#');
				break;
	// The following is for braces...
			case '[':
				if (this.isAutoClose_Brackets() && !inComment)
					insertSingleChar(docCommand, ']');
				break;
			case '{':
				if (this.isAutoClose_Braces() && !inComment)
					insertSingleChar(docCommand, '}');
				break;
			case '(':
				if (this.isAutoClose_Parens() && !inComment)
					insertSingleChar(docCommand, ')');
				break;
			case ')':
				if (this.isAutoClose_Parens() && !inComment)
					handleClosingBracket(nextChar, prevChar, trigChar, doc, docCommand);
				break;
			case '}':
				if (this.isAutoClose_Braces() && !inComment)
					handleClosingBracket(nextChar, prevChar, trigChar, doc, docCommand);
				break;
			case ']':
				if (this.isAutoClose_Brackets() && !inComment)
					handleClosingBracket(nextChar, prevChar, trigChar, doc, docCommand);
				break;
			}
		} catch(BadLocationException ex) {
			ex.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * Method declared on IAutoIndentStrategy
	 */
	public void customizeDocumentCommand(IDocument d, DocumentCommand c) {
	    
		codeInsertion(d, c);
		if (c.length == 0 && c.text != null && endsWithDelimiter(d, c.text)) {
			if (this.isUseSmartIndent())
				smartIndentAfterNewLine(d, c);
			if (this.isUseSmartComments())
				smartCommentAfterNewLine(d, c);
		}
		else if ("}".equals(c.text)) {  
			if (this.isUseSmartIndent())
				smartInsertAfterBracket(d, c);
		}
		else if (c.text.equals("\t")) {
			singleLineIndent(d,c);
		}
	}

	
	/**
	 * Returns whether or not the text ends with one of the given search strings.
	 */
	private boolean endsWithDelimiter(IDocument d, String txt) {

		String[] delimiters= d.getLegalLineDelimiters();

		for (int i= 0; i < delimiters.length; i++) {
			if (txt.endsWith(delimiters[i]))
				return true;
		}

		return false;
	}

	/**
	 * Returns the line number of the next bracket after end.
	 * 
	 * @returns the line number of the next matching bracket after end
	 * @param document
	 *            - the document being parsed
	 * @param line
	 *            - the line to start searching back from
	 * @param end
	 *            - the end position to search back from
	 * @param closingBracketIncrease
	 *            - the number of brackets to skip
	 */
	 protected int findMatchingOpenBracket(IDocument document, int line, int end, int closingBracketIncrease) throws BadLocationException {

		int start= document.getLineOffset(line);
		int brackcount= getBracketCount(document, start, end, false) - closingBracketIncrease;

		// sum up the brackets counts of each line (closing brackets count negative, 
		// opening positive) until we find a line the brings the count to zero
		while (brackcount < 0) {
			line--;
			if (line < 0) {
				return -1;
			}
			start= document.getLineOffset(line);
			end= start + document.getLineLength(line) - 1;
			brackcount += getBracketCount(document, start, end, false);
		}
		return line;
	}
	
	/**
	 * Returns the bracket value of a section of text. Closing brackets have a value of -1 and 
	 * open brackets have a value of 1.
	 * @returns the line number of the next matching bracket after end
	 * @param document - the document being parsed
	 * @param start - the start position for the search
	 * @param end - the end position for the search
	 * @param ignoreCloseBrackets - whether or not to ignore closing brackets in the count
	 */
	 private int getBracketCount(IDocument document, int start, int end, boolean ignoreCloseBrackets) throws BadLocationException {
		int begin = start;
		int bracketcount= 0;
		while (begin < end) {
			char curr= document.getChar(begin);
			begin++;
			switch (curr) {
				case '/' :
					if (begin < end) {
						char next= document.getChar(begin);
						if (next == '*') {
							// a comment starts, advance to the comment end
							begin= getCommentEnd(document, begin + 1, end);
						} else if (next == '/') {
							// '//'-comment: nothing to do anymore on this line 
							begin= end;
						}
					}
					break;
				case '*' :
					if (begin < end) {
						char next= document.getChar(begin);
						if (next == '/') {
							// we have been in a comment: forget what we read before
							bracketcount= 0;
							begin++;
						}
					}
					break;
				case '{' :
					bracketcount++;
					ignoreCloseBrackets= false;
					break;
				case '}' :
					if (!ignoreCloseBrackets) {
						bracketcount--;
					}
					break;
				case '"' :
				case '\'' :
					begin= getStringEnd(document, begin, end, curr);
					break;
				default :
					}
		}
		return bracketcount;
	}
	
	/**
	 * Returns the end position a comment starting at pos.
	 * @returns the end position a comment starting at pos
	 * @param document - the document being parsed
	 * @param position - the start position for the search
	 * @param end - the end position for the search
	 */
	 private int getCommentEnd(IDocument document, int position, int end) throws BadLocationException {
		int currentPosition = position;
		while (currentPosition < end) {
			char curr= document.getChar(currentPosition);
			currentPosition++;
			if (curr == '*') {
				if (currentPosition < end && document.getChar(currentPosition) == '/') {
					return currentPosition + 1;
				}
			}
		}
		return end;
	}
	
	/**
	 * Returns the String at line with the leading whitespace removed.
	 * @returns the String at line with the leading whitespace removed.
	 * @param document - the document being parsed
	 * @param line - the line being searched
	 */
	 protected String getIndentOfLine(IDocument document, int line) throws BadLocationException {
		if (line > -1) {
			int start= document.getLineOffset(line);
			int end= start + document.getLineLength(line) - 1;
			int whiteend= findEndOfWhiteSpace(document, start, end);
			return document.get(start, whiteend - start);
		} else {
			return ""; //$NON-NLS-1$
		}
	}
	
	/**
	 * Returns the position of the character in the document after position.
	 * @returns the next location of character.
	 * @param document - the document being parsed
	 * @param position - the position to start searching from
	 * @param end - the end of the document
	 * @param character - the character you are trying to match
	 */
	 private int getStringEnd(IDocument document, int position, int end, char character) throws BadLocationException {
		int currentPosition = position;
		while (currentPosition < end) {
			char currentCharacter= document.getChar(currentPosition);
			currentPosition++;
			if (currentCharacter == '\\') {
				// ignore escaped characters
				currentPosition++;
			} else if (currentCharacter == character) {
				return currentPosition;
			}
		}
		return end;
	}

	/**
	 * extends comment with each new line while in a comment block
	 * 
	 * @param document
	 *            - the document being parsed
	 * @param command
	 *            - the command being performed
	 */
	protected void smartCommentAfterNewLine(IDocument document, DocumentCommand command) {

		int docLength= document.getLength();
		if (command.offset == -1 || docLength == 0)
			return;

		try {
			int p= (command.offset == docLength ? command.offset - 1 : command.offset);
			int line = document.getLineOfOffset(p);
			int lineOffset = document.getLineOffset(line);
			int offset = p;
			char curChar = document.getChar(offset);
			char nextChar = document.getChar(offset+1);
			int newCaretOffset = offset;

			StringBuffer buf= new StringBuffer(command.text);
			String curIndent = getIndentOfLine(document, line);
			String curText = document.get(lineOffset, offset - lineOffset).trim();
			ITypedRegion partition = document.getPartition(p);
			String partType = partition.getType();
			if (partType == CFPartitionScanner.CF_SCRIPT_COMMENT_BLOCK || partType == CFPartitionScanner.JAVADOC_COMMENT) {
				if ((curText.equals("/**") || curText.equals("/*"))
						&& (curChar == '\n' || curChar == '*' || (curChar == '/') && nextChar == '\n')) {
					buf.append(" * ");
					newCaretOffset += 3;
					if (curChar == '*') {
						buf.append(document.getLineDelimiter(line) + curIndent + " *");
						newCaretOffset += curIndent.length();
						if (nextChar != '/' && curChar != '/') {
							buf.append('/');
						}
					} else {
						buf.append(document.getLineDelimiter(line) + curIndent + " **");
						newCaretOffset += curIndent.length();
						if (nextChar != '/' && curChar != '/') {
							buf.append('/');
						}
					}
					command.caretOffset = newCaretOffset + 1;
					command.shiftsCaret = false;
				} else {
					buf.append("* ");
				}
				command.text = buf.toString();
				return;
			}

			command.text = buf.toString();

		} catch (BadLocationException excp) {
			System.err.println("BadLocationException in CFScriptIndentStrategy::smartIndentAfterNewLine");
		}
	}

	/**
	 * Set the indent of a new line based on the command provided in the supplied document.
	 * 
	 * @param document
	 *            - the document being parsed
	 * @param command
	 *            - the command being performed
	 */
	protected void smartIndentAfterNewLine(IDocument document, DocumentCommand command) {

		int docLength = document.getLength();
		if (command.offset == -1 || docLength == 0)
			return;

		try {
			int p = (command.offset == docLength ? command.offset - 1 : command.offset);
			int line = document.getLineOfOffset(p);
			int lineOffset = document.getLineOffset(line);
			int offset = p;
			char prevPrevChar = document.getChar(offset - 2);
			char prevChar = document.getChar(offset - 1);
			char curChar = document.getChar(offset);
			char nextChar = document.getChar(offset + 1);

			StringBuffer buf = new StringBuffer(command.text);

			if (command.offset < docLength && (document.getChar(command.offset) == '}' || document.getChar(command.offset - 1) == '{')) {
				int indLine = findMatchingOpenBracket(document, line, command.offset, 0);
				if (indLine == -1) {
					indLine = line;
					}
				String curLineIndent = getIndentOfLine(document, indLine);
				buf.append(curLineIndent);
				int cursorPos = command.offset;
				// for when flush and no existing indent
				if (document.getChar(command.offset - 1) == '{'
						&& document.getChar(command.offset) == '}') {
					buf.append(fIndentString);
					if (curLineIndent.length() == 0) {
						buf.append(fIndentString);
						cursorPos = cursorPos + fIndentString.length();
						buf.append(document.getLineDelimiter(line));
					}
					buf.append(document.getLineDelimiter(line));
					buf.append(curLineIndent);
					command.caretOffset = cursorPos + 1 + curLineIndent.length() + fIndentString.length();
					command.shiftsCaret = false;
				} else {
					buf.append(fIndentString);
				}
			} else {
				int start = document.getLineOffset(line);
				int whiteend = findEndOfWhiteSpace(document, start, command.offset);
				buf.append(document.get(start, whiteend - start));
				}
			command.text = buf.toString();

		} catch (BadLocationException excp) {
			System.err.println("BadLocationException in CFScriptIndentStrategy::smartIndentAfterNewLine");
			}
		}

	/**
	 * Set the indent of a bracket based on the command provided in the supplied document.
	 * @param document - the document being parsed
	 * @param command - the command being performed
	 */
	 protected void smartInsertAfterBracket(IDocument document, DocumentCommand command) {
		if (command.offset == -1 || document.getLength() == 0)
			return;
		if (this.isUseSmartIndent() == false)
			return;

		try {
			int p= (command.offset == document.getLength() ? command.offset - 1 : command.offset);
			int line= document.getLineOfOffset(p);
			int start= document.getLineOffset(line);
			int whiteend= findEndOfWhiteSpace(document, start, command.offset);

			// shift only when line does not contain any text up to the closing bracket
			if (whiteend == command.offset) {
				// evaluate the line with the opening bracket that matches out closing bracket
				int indLine= findMatchingOpenBracket(document, line, command.offset, 1);
				if (indLine != -1 && indLine != line) {
					// take the indent of the found line
					StringBuffer replaceText= new StringBuffer(getIndentOfLine(document, indLine));
					// add the rest of the current line including the just added close bracket
					replaceText.append(fIndentString);
					replaceText.append(command.text);
					// modify document command
					command.length= command.offset - start;
					command.offset= start;
					command.text= replaceText.toString();
				}
			}
		} catch (BadLocationException excp) {
		////System.out.println(PHPEditorMessages.getString("AutoIndent.error.bad_location_2")); //$NON-NLS-1$
		//System.out.println("BadLocationException");
		}
	}

	/**
	 * @return the autoClose_Braces
	 */
	public boolean isAutoClose_Braces() {
		return autoClose_Braces;
	}


	/**
	 * @param autoClose_Braces the autoClose_Braces to set
	 */
	public void setAutoClose_Braces(boolean autoClose_Braces) {
		this.autoClose_Braces = autoClose_Braces;
	}
} 