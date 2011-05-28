/*
 * Created on Jul 1, 2004
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
package org.cfeclipse.cfml.editors.indentstrategies;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.formatters.XmlDocumentFormatter;
import org.cfeclipse.cfml.editors.partitioner.CFEPartition;
import org.cfeclipse.cfml.editors.partitioner.CFEPartitioner;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.cfeclipse.cfml.preferences.EditorPreferenceConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextUtilities;

//import org.cfeclipse.cfml.editors.CFConfiguration;

/**
 * @author Oliver Tupman
 * 
 */
public class CFEIndentStrategy extends DefaultIndentLineAutoEditStrategy {

	/** Auto-close double quotes */
	private boolean autoClose_DoubleQuotes = true;

	/** Auto-close single quotes */
	private boolean autoClose_SingleQuotes = true;

	/** Auto-close hashes (#) */
	private boolean autoClose_Hashes = true;

	/** Auto-insert a closing bracket */
	private boolean autoClose_Brackets = true;

	/** Auto-insert closing parenthesis */
	private boolean autoClose_Parens = true;

	/** Use smart indent */
	private boolean useSmartIndent = true;

	/** Use smart comments */
	protected boolean useSmartComments = true;

	public void setUseSmartComments(boolean useSmartComments) {
		this.useSmartComments = useSmartComments;
	}

	private boolean tabIndentSingleLine = false;
	protected String indentString = "\t";
	protected int indentSize = 4;

	protected CFMLEditor editor;
	// private CFConfiguration configuration;

	private int fAccumulatedChange = 0;

	public CFEIndentStrategy(CFMLEditor editor) {
		this.editor = editor;
	}

	protected synchronized void autoIndentAfterNewLine(IDocument d, DocumentCommand c) {

		if (c.offset == -1 || d.getLength() == 0) {
			return;
		}

		int position = (c.offset == d.getLength() ? c.offset - 1 : c.offset);
		/*
		 * AntElementNode node= fModel.getProjectNode(false).getNode(position -
		 * fAccumulatedChange); if (node == null) { return; }
		 */

		try {
			// StringBuffer correct=
			// org.cfeclipse.cfml.editors.formatters.XmlDocumentFormatter.getLeadingWhitespace(node.getOffset(),
			// d);
			StringBuffer correct = org.cfeclipse.cfml.editors.formatters.XmlDocumentFormatter.getLeadingWhitespace(
					position, d);
			if (!nextNodeIsEndTag(c.offset, d)) {
				correct.append(XmlDocumentFormatter.createIndent());
			}
			StringBuffer buf = new StringBuffer(c.text);
			buf.append(correct);
			fAccumulatedChange += buf.length();

			int line = d.getLineOfOffset(position);
			IRegion reg = d.getLineInformation(line);
			int lineEnd = reg.getOffset() + reg.getLength();
			int contentStart = findEndOfWhiteSpace(d, c.offset, lineEnd);

			c.length = Math.max(contentStart - c.offset, 0);
			c.caretOffset = c.offset + buf.length();
			c.shiftsCaret = false;
			c.text = buf.toString();

		} catch (BadLocationException e) {
			e.printStackTrace();
			// AntUIPlugin.log(e);
		}
	}

	private boolean nextNodeIsEndTag(int offset, IDocument document) {
		if (offset + 1 > document.getLength()) {
			return false;
		}
		try {
			IRegion lineRegion = document.getLineInformationOfOffset(offset);
			offset = findEndOfWhiteSpace(document, offset, lineRegion.getOffset() + lineRegion.getLength());
			String nextChars = document.get(offset, 2).trim();
			if ("</".equals(nextChars) || "/>".equals(nextChars)) { //$NON-NLS-1$ //$NON-NLS-2$
				return true;
			}
		} catch (BadLocationException e) {
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.text.IAutoEditStrategy#customizeDocumentCommand(org
	 * .eclipse.jface.text.IDocument, org.eclipse.jface.text.DocumentCommand)
	 */
	public void customizeDocumentCommand(IDocument d, DocumentCommand c) {

		if (c.length == 0 && c.text != null && isLineDelimiter(d, c.text)) {
			autoIndentAfterNewLine(d, c);
		} else if (c.text.length() > 1) {
			smartPaste(d, c);
		}
	}

	protected boolean isLineDelimiter(IDocument document, String text) {
		String[] delimiters = document.getLegalLineDelimiters();
		if (delimiters != null)
			return TextUtilities.equals(delimiters, text) > -1;
		return false;
	}

	public synchronized void reconciled() {
		fAccumulatedChange = 0;
	}

	boolean doIndent(IDocument doc, DocumentCommand docCommand) {

		boolean doIndent = true;
		if (doc instanceof ICFDocument && this.isUseSmartIndent()) {
			ICFDocument cfd = (ICFDocument) doc;
			CFEPartitioner partitioner = (CFEPartitioner) cfd.getDocumentPartitioner();
			CFEPartition prevPartition = partitioner.getPreviousPartition(docCommand.offset);
			CFEPartition nextPartition = null;
			if (prevPartition != null) {
				nextPartition = partitioner.getNextPartition(prevPartition.offset);
			}
			// System.out.println("Command at offset: " + docCommand.offset);
			// System.out.println(prevPartition);
			// System.out.println(nextPartition);
			if (nextPartition != null && prevPartition != null) {
				if (prevPartition.getType().endsWith("end_tag")
						|| (prevPartition.getTagName() != null && prevPartition.getTagName().equals("cfset"))) {
					//doIndent = false;
				}
			}
		}
		return doIndent;
	}

	protected void smartPaste(IDocument document, DocumentCommand command) {
		try {
			if (command.offset == -1 || document.getLength() == 0) {
				return;
			}
			String origChange = command.text;
			int position = (command.offset == document.getLength() ? command.offset - 1 : command.offset);
			ICFDocument cfd = (ICFDocument) document;
			// CfmlTagItem node = cfd.getTagAt(command.offset, command.offset,
			// true);
			DocItem node = editor.getSelectionCursorListener().getCurrentDocItem();
			if (node == null) {
				return;
			}

			// eat any WS before the insertion to the beginning of the line
			int firstLine = 1; // don't format the first line if it has other
								// content before it
			IRegion line = document.getLineInformationOfOffset(command.offset);
			String notSelected = document.get(line.getOffset(), command.offset - line.getOffset());
			if (notSelected.trim().length() == 0) {
				command.length += notSelected.length();
				command.offset = line.getOffset();
				firstLine = 0;
			}

			// handle the indentation computation inside a temporary document
			Document temp = new Document(command.text);

			// indent the first and second line
			// compute the relative indentation difference from the second line
			// (as the first might be partially selected) and use the value to
			// indent all other lines.
			boolean isIndentDetected = false;
			StringBuffer addition = new StringBuffer();
			int insertLength = 0;
			int lines = temp.getNumberOfLines();
			for (int l = firstLine; l < lines; l++) { // we don't change the
														// number of lines while
														// adding indents

				IRegion r = temp.getLineInformation(l);
				int lineOffset = r.getOffset();
				int lineLength = r.getLength();

				if (lineLength == 0) { // don't modify empty lines
					continue;
				}

				if (!isIndentDetected) {

					// indent the first pasted line
						StringBuffer current = XmlDocumentFormatter.getLeadingWhitespace(lineOffset, temp);
						StringBuffer correct = XmlDocumentFormatter.getLeadingWhitespace(node.getStartPosition(), document);
						// relatively indent all pasted lines
						if (doIndent(document, command)) {
							correct.append(XmlDocumentFormatter.createIndent());
						}
						insertLength = subtractIndent(correct, current, addition);
						isIndentDetected = true;
				}

				if (insertLength > 0) {
					addIndent(temp, l, addition);
				} else if (insertLength < 0) {
					cutIndent(temp, l, -insertLength);
				}
			}

			// modify the command
			if (!origChange.equals(temp.get())) {
				fAccumulatedChange += temp.getLength();
				command.text = temp.get();
			}

		} catch (BadLocationException e) {
			e.printStackTrace();
			// AntUIPlugin.log(e);
		}
	}

	/**
	 * Indents line <code>line</code> in <code>document</code> with
	 * <code>indent</code>. Leaves leading comment signs alone.
	 * 
	 * @param document
	 *            the document
	 * @param line
	 *            the line
	 * @param indent
	 *            the indentation to insert
	 * @throws BadLocationException
	 *             on concurrent document modification
	 */
	private void addIndent(Document document, int line, CharSequence indent) throws BadLocationException {
		IRegion region = document.getLineInformation(line);
		int insert = region.getOffset();

		// insert indent
		document.replace(insert, 0, indent.toString());
	}

	/**
	 * Cuts the visual equivalent of <code>toDelete</code> characters out of the
	 * indentation of line <code>line</code> in <code>document</code>.
	 * 
	 * @param document
	 *            the document
	 * @param line
	 *            the line
	 * @param toDelete
	 *            the number of space equivalents to delete.
	 * @throws BadLocationException
	 *             on concurrent document modification
	 */
	protected void cutIndent(Document document, int line, int toDelete) throws BadLocationException {
		IRegion region = document.getLineInformation(line);
		int from = region.getOffset();
		int endOffset = region.getOffset() + region.getLength();

		int to = from;
		while (toDelete > 0 && to < endOffset) {
			char ch = document.getChar(to);
			if (!Character.isWhitespace(ch))
				break;
			toDelete -= computeVisualLength(ch);
			if (toDelete >= 0) {
				to++;
			} else {
				break;
			}
		}

		document.replace(from, to - from, null);
	}

	/**
	 * Returns the visual length of a given character taking into account the
	 * visual tabulator length.
	 * 
	 * @param ch
	 *            the character to measure
	 * @return the visual length of <code>ch</code>
	 */
	private int computeVisualLength(char ch) {
		if (ch == '\t') {
			return getVisualTabLengthPreference();
		}

		return 1;
	}

	/**
	 * Returns the visual length of a given <code>CharSequence</code> taking
	 * into account the visual tabulator length.
	 * 
	 * @param seq
	 *            the string to measure
	 * @return the visual length of <code>seq</code>
	 */
	private int computeVisualLength(CharSequence seq) {
		int size = 0;
		int tablen = getVisualTabLengthPreference();

		for (int i = 0; i < seq.length(); i++) {
			char ch = seq.charAt(i);
			if (ch == '\t') {
				size += tablen - size % tablen;
			} else {
				size++;
			}
		}
		return size;
	}

	/**
	 * Computes the difference of two indentations and returns the difference in
	 * length of current and correct. If the return value is positive,
	 * <code>addition</code> is initialized with a substring of that length of
	 * <code>correct</code>.
	 * 
	 * @param correct
	 *            the correct indentation
	 * @param current
	 *            the current indentation (might contain non-whitespace)
	 * @param difference
	 *            a string buffer - if the return value is positive, it will be
	 *            cleared and set to the substring of <code>current</code> of
	 *            that length
	 * @return the difference in length of <code>correct</code> and
	 *         <code>current</code>
	 */
	private int subtractIndent(CharSequence correct, CharSequence current, StringBuffer difference) {
		int c1 = computeVisualLength(correct);
		int c2 = computeVisualLength(current);
		int diff = c1 - c2;
		if (diff <= 0) {
			return diff;
		}

		difference.setLength(0);
		int len = 0, i = 0;
		while (len < diff) {
			char c = correct.charAt(i++);
			difference.append(c);
			len += computeVisualLength(c);
		}

		return diff;
	}

	/**
	 * The preference setting for the visual tabulator display.
	 * 
	 * @return the number of spaces displayed for a tabulator in the editor
	 */
	private int getVisualTabLengthPreference() {
		int tabWidth = CFMLPlugin.getDefault().getPreferenceStore().getInt(
				EditorPreferenceConstants.P_TAB_WIDTH);
		if (tabWidth == 0) {
			tabWidth = 4;
		}
		return tabWidth;
	}

	protected String getIndentString() {
		return this.indentString;
	}

	public void setIndentString(int tabWidth, boolean tabsAsSpaces) {
		indentSize = tabWidth;
		if (tabsAsSpaces) {
			// System.err.println("Indent string set to "+tabWidth+" spaces.");
			String s = new String();
			for (int i = 0; i < tabWidth; i++) {
				s += " ";
			}
			indentString = s;
		} else {
			// System.err.println("Indent string set to 1 tab.");
			indentString = "\t";
		}
	}

	/**
	 * Steps through one character. Essentially a alias to stepThrough(command,
	 * 1)
	 * 
	 * @param docCommand
	 */
	protected void stepThrough(DocumentCommand docCommand) {
		stepThrough(docCommand, 1);
	}

	/**
	 * Steps through a number of characters
	 * 
	 * @param docCommand
	 *            - the doc command to work upon
	 * @param chars2StepThru
	 *            - number of characters to step through
	 */
	protected void stepThrough(DocumentCommand docCommand, int chars2StepThru) {
		docCommand.text = "";
		docCommand.shiftsCaret = false;
		docCommand.caretOffset = docCommand.offset += chars2StepThru;
	}

	/*
	 * private String getSelectedText(){ ISelection selection =
	 * editor.getSelectionProvider().getSelection(); String text = null;
	 * if(selection != null && selection instanceof ITextSelection){
	 * ITextSelection textSelection = (ITextSelection) selection; text =
	 * textSelection.getText(); } return text; }
	 */

	public void setTabIndentSingleLine(boolean state) {
		tabIndentSingleLine = state;
	}

	public boolean getTabIndentSingleLine() {
		return this.tabIndentSingleLine;
	}

	protected void singleLineIndent(IDocument d, DocumentCommand c) {

		/*
		 * TODO: Need to fix the stuff in comments below Plugin currently broken
		 * because of missing files and broken stuff in CFParser, so can't go
		 * any further :-(
		 */
		ITextSelection textSelection = (ITextSelection) editor.getSelectionProvider().getSelection();
		String selectedText = textSelection.getText();
		// System.out.println("Command offset: |"+c.offset+"|");
		// System.out.println("Command caret offset: |"+c.caretOffset+"|");
		if (selectedText.length() > 0 && (getTabIndentSingleLine())) {

			try {
				// Find the start of the line
				int lineOffset = d.getLineInformationOfOffset(c.offset).getOffset();
				// Get the string that we are about to overwrite with the
				// indentstring
				String lineStartString = d.get(lineOffset, indentString.length());
				// Replace the start of the line with the indent string and what
				// ever was there before
				d.replace(lineOffset, indentString.length(), indentString + lineStartString);
				// Update the offset of the command to reflect the changed line
				c.offset += indentString.length();
				// Set the command text to what is currently selected so we
				// don't change the document
				c.text = selectedText;
			} catch (BadLocationException e) {
				// do nothing
				System.err.println("BadLocationException caught in CFEIndentStrategy::singleLineIndent method");
			}
		} else {
			c.text = indentString;
		}

	}

	/**
	 * @return Returns the autoClose_DoubleQuotes.
	 */
	public boolean isAutoClose_DoubleQuotes() {
		return autoClose_DoubleQuotes;
	}

	/**
	 * @param autoClose_DoubleQuotes
	 *            The autoClose_DoubleQuotes to set.
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
	 * @param autoClose_Hashes
	 *            The autoClose_Hashes to set.
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
	 * @param autoClose_SingleQuotes
	 *            The autoClose_SingleQuotes to set.
	 */
	public void setAutoClose_SingleQuotes(boolean autoClose_SingleQuotes) {
		this.autoClose_SingleQuotes = autoClose_SingleQuotes;
	}

	/**
	 * @return the autoClose_Brackets
	 */
	public boolean isAutoClose_Brackets() {
		return autoClose_Brackets;
	}

	/**
	 * @param autoClose_Brackets
	 *            the autoClose_Brackets to set
	 */
	public void setAutoClose_Brackets(boolean autoClose_Brackets) {
		this.autoClose_Brackets = autoClose_Brackets;
	}

	/**
	 * @return the autoClose_Parens
	 */
	public boolean isAutoClose_Parens() {
		return autoClose_Parens;
	}

	/**
	 * @param autoClose_Parens
	 *            the autoClose_Parens to set
	 */
	public void setAutoClose_Parens(boolean autoClose_Parens) {
		this.autoClose_Parens = autoClose_Parens;
	}

	/**
	 * @return the useSmartIndent
	 */
	public boolean isUseSmartIndent() {
		return useSmartIndent;
	}

	/**
	 * @return the useSmartComments
	 */
	public boolean isUseSmartComments() {
		return useSmartComments;
	}

	/**
	 * @param useSmartIndent
	 *            The useSmartIndent value.
	 */
	public void setUseSmartIndent(boolean useSmartIndent) {
		this.useSmartIndent = useSmartIndent;
	}

}
