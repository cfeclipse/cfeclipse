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
package com.rohanclan.cfml.editors.indentstrategies;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultAutoIndentStrategy;
//import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
//import org.eclipse.jface.viewers.ISelection;

//import com.rohanclan.cfml.editors.CFConfiguration;
import com.rohanclan.cfml.editors.CFMLEditor;

/**
 * @author Oliver Tupman
 *
 */
public class CFEIndentStrategy extends DefaultAutoIndentStrategy {

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
	
}
