/*
 * Created on Nov 18, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rohanclan.cfml.editors.partitioner.scanners.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import org.eclipse.jface.text.Assert;
/**
 * @author Stephen Milligan
 *
 * This rule is used to find CF tags in the active document.
 */
public class CFTagRule implements IPredicateRule {

	/** Internal setting for the uninitialized column constraint */
	protected static final int UNDEFINED= -1;

	/** The token to be returned on success */
	protected IToken fToken;
	/** The pattern's start sequence */
	protected char[] fStartSequence;
	/** The pattern's end sequence */
	protected char[] fEndSequence;
	/** The pattern's column constrain */
	protected int fColumn= UNDEFINED;

	/** Are we inside double quotes open */
	private boolean fDblQuotesOpen = false;
	/** Are we inside single quotes open */
	private boolean fSnglQuotesOpen = false;
	/**
	 * Indicates whether the escape character continues a line
	 * @since 3.0
	 */
	protected boolean fEscapeContinuesLine;
	/** Indicates whether end of line terminates the pattern */
	protected boolean fBreaksOnEOL = false;
	/** Indicates whether end of file terminates the pattern */
	protected boolean fBreaksOnEOF = true;

	/**
	 * Creates a rule for the given starting and ending sequence.
	 * When these sequences are detected the rule will return the specified token.
	 * Alternatively, the sequence can also be ended by the end of the line.
	 * Any character which follows the given escapeCharacter will be ignored.
	 *
	 * @param startSequence the pattern's start sequence
	 * @param endSequence the pattern's end sequence, <code>null</code> is a legal value
	 * @param token the token which will be returned on success
	 * @param escapeCharacter any character following this one will be ignored
	 * @param breaksOnEOL indicates whether the end of the line also terminates the pattern
	 */
	public CFTagRule(String startSequence, String endSequence, IToken token) {
		Assert.isTrue(startSequence != null && startSequence.length() > 0);
		Assert.isNotNull(token);
		
		fStartSequence= startSequence.toCharArray();
		fEndSequence= (endSequence == null ? new char[0] : endSequence.toCharArray());
		fToken= token;
	}
	

	
	/**
	 * Sets a column constraint for this rule. If set, the rule's token
	 * will only be returned if the pattern is detected starting at the 
	 * specified column. If the column is smaller then 0, the column
	 * constraint is considered removed.
	 *
	 * @param column the column in which the pattern starts
	 */
	public void setColumnConstraint(int column) {
		if (column < 0)
			column= UNDEFINED;
		fColumn= column;
	}
	
	
	/**
	 * Evaluates this rules without considering any column constraints.
	 *
	 * @param scanner the character scanner to be used
	 * @return the token resulting from this evaluation
	 */
	protected IToken doEvaluate(ICharacterScanner scanner) {
		return doEvaluate(scanner, false);
	}
	
	/**
	 * Evaluates this rules without considering any column constraints. Resumes
	 * detection, i.e. look sonly for the end sequence required by this rule if the
	 * <code>resume</code> flag is set.
	 *
	 * @param scanner the character scanner to be used
	 * @param resume <code>true</code> if detection should be resumed, <code>false</code> otherwise
	 * @return the token resulting from this evaluation
	 * @since 2.0
	 */
	protected IToken doEvaluate(ICharacterScanner scanner, boolean resume) {
		
		if (resume) {
			
			if (endSequenceDetected(scanner))
				return fToken;
		
		} else {
			
			int c= scanner.read();
			if (c == fStartSequence[0]) {
				if (sequenceDetected(scanner, fStartSequence, false)) {
					if (endSequenceDetected(scanner))
						return fToken;
				}
			}
		}
		
		scanner.unread();
		return Token.UNDEFINED;
	}	
	
	/*
	 * @see IRule#evaluate(ICharacterScanner)
	 */
	public IToken evaluate(ICharacterScanner scanner) {
		return evaluate(scanner, false);
	}	
	
	/**
	 * Returns whether the end sequence was detected. As the pattern can be considered 
	 * ended by a line delimiter, the result of this method is <code>true</code> if the 
	 * rule breaks on the end  of the line, or if the EOF character is read.
	 *
	 * @param scanner the character scanner to be used
	 * @return <code>true</code> if the end sequence has been detected
	 */
	protected boolean endSequenceDetected(ICharacterScanner scanner) {
		int c;
		char[][] delimiters= scanner.getLegalLineDelimiters();

		while ((c= scanner.read()) != ICharacterScanner.EOF) {
			boolean isEscapeChar = false;
			// Check if we're inside quotes
			if (c == '"' 
				|| c == '\'') {
				if (c == '"' && !fSnglQuotesOpen) { 
					fDblQuotesOpen = !fDblQuotesOpen;
				}
				if (c == '\'' && !fDblQuotesOpen) {
					fSnglQuotesOpen = !fSnglQuotesOpen;
				}
				// Skip the escaped character.
				//scanner.read();
			} else if (fEndSequence.length > 0 && c == fEndSequence[0]) {
				// Check if the specified end sequence has been found.
				if (!fDblQuotesOpen && !fSnglQuotesOpen) {
					if (sequenceDetected(scanner, fEndSequence, true)) {
						return true;
					}
				}
			}
		}
		if (fBreaksOnEOF) return true;
		scanner.unread();
		return false;
	}
	
	/**
	 * Returns whether the next characters to be read by the character scanner
	 * are an exact match with the given sequence. No escape characters are allowed 
	 * within the sequence. If specified the sequence is considered to be found
	 * when reading the EOF character.
	 *
	 * @param scanner the character scanner to be used
	 * @param sequence the sequence to be detected
	 * @param eofAllowed indicated whether EOF terminates the pattern
	 * @return <code>true</code> if the given sequence has been detected
	 */
	protected boolean sequenceDetected(ICharacterScanner scanner, char[] sequence, boolean eofAllowed) {
		for (int i= 1; i < sequence.length; i++) {
			int c= scanner.read();
			if (c == ICharacterScanner.EOF && eofAllowed) {
				return true;
			} else if (c != sequence[i]) {
				// Non-matching character detected, rewind the scanner back to the start.
				// Do not unread the first character.
				scanner.unread();
				for (int j= i-1; j > 0; j--)
					scanner.unread();
				return false;
			}
		}
		
		return true;
	}
	
	/*
	 * @see IPredicateRule#evaluate(ICharacterScanner, boolean)
	 * @since 2.0
	 */
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		if (fColumn == UNDEFINED)
			return doEvaluate(scanner, resume);
		
		int c= scanner.read();
		scanner.unread();
		if (c == fStartSequence[0])
			return (fColumn == scanner.getColumn() ? doEvaluate(scanner, resume) : Token.UNDEFINED);
		else
			return Token.UNDEFINED;	
	}

	/*
	 * @see IPredicateRule#getSuccessToken()
	 * @since 2.0
	 */
	public IToken getSuccessToken() {
		return fToken;
	}
}
