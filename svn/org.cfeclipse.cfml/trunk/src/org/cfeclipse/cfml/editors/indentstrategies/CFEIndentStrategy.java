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

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultAutoIndentStrategy;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;

//import org.cfeclipse.cfml.editors.CFConfiguration;

/**
 * @author Oliver Tupman
 *
 */
public class CFEIndentStrategy extends DefaultAutoIndentStrategy {

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

	private boolean tabIndentSingleLine = false;
	protected String indentString = "\t";
	protected int indentSize = 4;
	
	protected CFMLEditor editor;
	//private CFConfiguration configuration;
	
	public CFEIndentStrategy(CFMLEditor editor) {
		this.editor = editor;
	}
	
	protected String getIndentString() { return this.indentString; }
	
	public void setIndentString(int tabWidth, boolean tabsAsSpaces) {
		indentSize = tabWidth;
		if(tabsAsSpaces) {
			//System.err.println("Indent string set to "+tabWidth+" spaces.");
			String s = new String();
			for (int i=0;i<tabWidth;i++) {
				s+= " ";
			}
			indentString = s;
		}
		else {
			//System.err.println("Indent string set to 1 tab.");
			indentString = "\t";
		}
	}
	
	/**
	 * Steps through one character. Essentially a alias to stepThrough(command, 1)
	 * 
	 * @param docCommand
	 */
	protected void stepThrough(DocumentCommand docCommand) {
		stepThrough(docCommand, 1);
	}
	
	/**
	 * Steps through a number of characters
	 * 
	 * @param docCommand - the doc command to work upon
	 * @param chars2StepThru - number of characters to step through
	 */
	protected void stepThrough(DocumentCommand docCommand, int chars2StepThru) {
		docCommand.text = "";
		docCommand.shiftsCaret = false;
		docCommand.caretOffset = docCommand.offset += chars2StepThru;
	}
	
	/* private String getSelectedText(){
        ISelection selection = editor.getSelectionProvider().getSelection();
        String text = null;       
        if(selection != null && selection instanceof ITextSelection){
            ITextSelection textSelection = (ITextSelection) selection;
            text = textSelection.getText();
        }
        return text;
    } */
	
	public void setTabIndentSingleLine(boolean state) {
		tabIndentSingleLine = state;
	}
	

	public boolean getTabIndentSingleLine() { return this.tabIndentSingleLine; }
	
	
	protected void singleLineIndent(IDocument d, DocumentCommand c) 
	{
	    
		/*
		 * TODO: Need to fix the stuff in comments below 
		 * Plugin currently broken because of missing files
		 * and broken stuff in CFParser, so can't go any further :-(
		 */
			ITextSelection textSelection = (ITextSelection)editor.getSelectionProvider().getSelection();
			String selectedText = textSelection.getText();
			//System.out.println("Command offset: |"+c.offset+"|");
			//System.out.println("Command caret offset: |"+c.caretOffset+"|");
		 	if (selectedText.length() > 0 && (getTabIndentSingleLine())) {

				try {
					// Find the start of the line
					int lineOffset = d.getLineInformationOfOffset(c.offset).getOffset();
					// Get the string that we are about to overwrite with the indentstring
					String lineStartString = d.get(lineOffset,indentString.length());
					// Replace the start of the line with the indent string and what ever was there before
					d.replace(lineOffset,indentString.length(),indentString+lineStartString);
					// Update the offset of the command to reflect the changed line 
					c.offset += indentString.length();
					// Set the command text to what is currently selected so we don't change the document
					c.text = selectedText;
				}
				catch (BadLocationException e) {
					// do nothing
					System.err.println("BadLocationException caught in CFEIndentStrategy::singleLineIndent method");
				}
		 	}
		 	else {
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
	 * @param autoClose_Brackets the autoClose_Brackets to set
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
	 * @param autoClose_Parens the autoClose_Parens to set
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
	 * @param useSmartIndent
	 *            The useSmartIndent value.
	 */
	public void setUseSmartIndent(boolean useSmartIndent) {
		this.useSmartIndent = useSmartIndent;
	}

}
