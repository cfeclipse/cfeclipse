/*
 * Created on July 16, 2007
 * by Mark Gaulin
 */
package org.cfeclipse.cfml.editors.actions;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.TextNavigationAction;

/**
 * This class is a replacement for the WORD_NEXT and WORD_PREVIOUS
 * action handlers that are used by default by an AbstractDecoratedTextEditor.
 * This class makes CFEclipse behave more like the Java editor does with
 * respect to whitespace and punctuation marks, so only those cases
 * are handled directly by this class; all other cases are defered to
 * the base class.
 *  
 * @author mgaulin
 */
public class TextEditorWordNavigationAction extends TextNavigationAction {

	private ITextEditor editor;
	private StyledText styledText;
	
	private boolean moveRight; 
	
	private static CharacterMatcher TAB_MATCHER = new TabMatcher();
	private static CharacterMatcher PUNCTUATION_MATCHER = new PunctuationMatcher();
	
	/**
	 * Create a new Action for the WORD_PREVIOUS command, and connect it
	 * to the given editor.
	 * 
	 * @param editor
	 * @param styledText
	 * @return
	 */
	public static TextEditorWordNavigationAction createTextEditorWordPreviousAction(ITextEditor editor, StyledText styledText) {
		TextEditorWordNavigationAction action = new TextEditorWordNavigationAction(editor, styledText, 
				ITextEditorActionDefinitionIds.WORD_PREVIOUS, ST.WORD_PREVIOUS,
				false);	// false = move left
		
		// Hook the action up to the editor, overriding any existing
		// handler for the given actionId
		editor.setAction(action.getActionDefinitionId(), action);
		
		return action;
	}

	/**
	 * Create a new Action for the WORD_NEXT command, and connect it
	 * to the given editor.
	 * 
	 * @param editor
	 * @param styledText
	 * @return
	 */
	public static TextEditorWordNavigationAction createTextEditorWordNextAction(ITextEditor editor, StyledText styledText) {
		TextEditorWordNavigationAction action = new TextEditorWordNavigationAction(editor, styledText, 
				ITextEditorActionDefinitionIds.WORD_NEXT, ST.WORD_NEXT,
				true);	// true = move right
		
		// Hook the action up to the editor, overriding any existing
		// handler for the given actionId
		editor.setAction(action.getActionDefinitionId(), action);
		
		return action;
	}

	/**
	 * Override the WORD_PREVIOUS and WORD_NEXT text nagivation actions
	 * for this Editor. 
	 */
	private TextEditorWordNavigationAction(ITextEditor editor, StyledText styledText, 
			String actionId, int rawAction,
			boolean moveRight) {
		super(styledText, rawAction);
		this.editor = editor;
		this.styledText = styledText;
		this.moveRight = moveRight;
		setActionDefinitionId(actionId);
	}
	

	/*
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		// Get the document and selection.
		IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput());
		ITextSelection sel = (ITextSelection) editor.getSelectionProvider().getSelection();

		if (sel.getLength() != 0) {
			// If there is a selection then defer to the default implementation.
			super.run();
		} else {
			// What character is adjacent to the cursor?
			int offset = sel.getOffset();
			char charToInspect;
			try {
				if (this.moveRight) {
					// Moving to right, so look at next char
					charToInspect = doc.getChar(offset);
				} else {
					// Moving to left, so look at previous char
					charToInspect = (offset > 0) ? doc.getChar(offset-1) : ' ';
				}
			} catch (org.eclipse.jface.text.BadLocationException ex) {
				// (This should not happen)
				// Defer to the default implementation.
				super.run();
				return;
			}
			
			// Is the character to inspect one we are interested in?
			if (TAB_MATCHER.doesCharacterMatch(charToInspect)) {
				// Skip all tabs
				skipAllCharacters(doc, offset, TAB_MATCHER);
			} else if (PUNCTUATION_MATCHER.doesCharacterMatch(charToInspect)) {
				// Skip all runs of punctuation
				// (even if the actual characters are different)
				skipAllCharacters(doc, offset, PUNCTUATION_MATCHER);
			} else {
				// Defer to the default implementation for all other cases.
				super.run();
			}
		}		
	}
	
	/**
	 * Skip all matching characters in the document starting at the
	 * given offset.  Scanning will go either left or right, depending
	 * on how this instance is configured.
	 * @param doc
	 * @param offset
	 * @param charMatcher
	 */
	private void skipAllCharacters(IDocument doc, int offset, CharacterMatcher charMatcher) {
		try {
			// Scan past all "charToSkip" chars, leaving us on the first 
			// non-matching char (but not on a different line, or in the
			// middle of a two-character line delimiter, which is worse).
			
			if (!this.moveRight) {
				// Note that we need to decrement offset before we start looping
				// so we can read the chars before the caret.
				offset--;
			}
			
			// Where does the current line start?
			int startOfLine = styledText.getOffsetAtLine(styledText.getLineAtOffset(offset));
			int docLen = doc.getLength();
			while ((offset >= startOfLine) 
					&& (offset < docLen) 
					&& charMatcher.doesCharacterMatch(doc.getChar(offset))) {
				if (this.moveRight) {
					offset++;
				} else {
					offset--;
				}
			}
			
			if (!this.moveRight) {
				// Move to caret to the correct spot relative to the
				// non-matching character we just found
				offset++;
			}
			
			// Move the cursor to the right spot.
			styledText.setSelection(offset);
			// Notify others of the selection change.
			fireSelectionChanged();
		} catch (org.eclipse.jface.text.BadLocationException ex) {
			// Ignore! (This should not happen)
			return;
		}
	}	

	/**
	 * Little interface to make the "skip all" code above easier to share. 
	 * @author gaulinm27
	 *
	 */
	private interface CharacterMatcher {
		public boolean doesCharacterMatch(char c);
	}
	
	/**
	 * Detect tabs
	 * @author gaulinm27
	 */
	private static class TabMatcher implements CharacterMatcher {
		public boolean doesCharacterMatch(char c) {
			return (c == '\t');
		}
	}

	/**
	 * Detect punctuation (but not underscores)
	 * @author gaulinm27
	 */
	private static class PunctuationMatcher implements CharacterMatcher {
		public boolean doesCharacterMatch(char c) {
			return charIsPunctuation(c);
		}
		
		private boolean charIsPunctuation(char c) {
			// If it is not an alphanum or whitespace then it must
			// be punctuation. 
			// Treat underscore as a "letter" so it can be used in
			// variable names.
			return (c != '_') 
					&& !Character.isLetterOrDigit(c) 
					&& !Character.isWhitespace(c);
		}	
	}
		
}
