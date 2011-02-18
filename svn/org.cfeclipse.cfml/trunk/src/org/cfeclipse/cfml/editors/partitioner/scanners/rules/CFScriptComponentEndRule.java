/*
 * Created on Nov 18, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.cfml.editors.partitioner.scanners.rules;

import java.util.regex.Pattern;

import org.cfeclipse.cfml.editors.partitioner.FakeTagData;
import org.eclipse.jface.text.Assert;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

/**
 * @author denstar
 * 
 *         Creates a rule for matching the closing } of cfscript based components.
 * 
 */
public class CFScriptComponentEndRule implements IPredicateRule {

	/** Internal setting for the uninitialized column constraint */
	protected static final int UNDEFINED= -1;

	/** The partition type for the start and end tags */
	protected String fPartitionType;
	/** The partition type for the contents of the start tag */
	protected String fMidPartitionType;
	/** The pattern's start sequence */
	protected char[] fStartSequence;
	/** The pattern's end sequence */
	protected char[] fEndSequence;
	/** The pattern's column constrain */
	protected int fColumn = 0;

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

	/** Pattern to make sure that the character after the end of the start sequence is valid */
	private static Pattern p = Pattern.compile("[\\s{]");

	/**
	 * Creates a rule for matching the closing } of cfscript based components.
	 * 
	 * @param endSequence
	 *            the pattern's end sequence, <code>null</code> is a legal value
	 * @param token
	 *            the token which will be returned on success
	 * @param escapeCharacter
	 *            any character following this one will be ignored
	 * @param breaksOnEOL
	 *            indicates whether the end of the line also terminates the pattern
	 */
	public CFScriptComponentEndRule(String endSequence, String partitionType, String midPartitionType) {
		Assert.isTrue(endSequence != null && endSequence.length() > 0);
		Assert.isNotNull(partitionType);
		fEndSequence= (endSequence == null ? new char[0] : endSequence.toCharArray());
		fPartitionType= partitionType;
		fMidPartitionType = midPartitionType;
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
	
	
	/*
	 * @see IRule#evaluate(ICharacterScanner)
	 */
	public IToken evaluate(ICharacterScanner scanner) {
		return evaluate(scanner, false);
	}	
	

	/*
	 * @see IPredicateRule#evaluate(ICharacterScanner, boolean)
	 * @since 2.0
	 */
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		int c = scanner.read();
		int nextC = scanner.read();
		int i = 0;
		scanner.unread();
		if ((c) == fEndSequence[0] && nextC == ICharacterScanner.EOF) {
			FakeTagData data = null;
			data = new FakeTagData(fPartitionType, "}", fMidPartitionType, fPartitionType, "cfscript");
			return new Token(data);
		} else if (nextC == ICharacterScanner.EOF) {
			scanner.unread();
			nextC = scanner.read();
			while (nextC == '\n' || nextC == ' ' || nextC == '\t' || nextC == '\r' || nextC == fEndSequence[0]) {
				if (nextC == fEndSequence[0]) {
					for (int j = i; j >= 0; j--) {
						scanner.read();
					}
					FakeTagData data = null;
					data = new FakeTagData(fPartitionType, "}", fMidPartitionType, fPartitionType, "cfscript");
					return new Token(data);
				}
				scanner.unread();
				scanner.unread();
				if (scanner.getColumn() == -1) {
					for (int j = i; j >= 0; j--) {
						scanner.read();
					}
					return Token.UNDEFINED;
				}
				nextC = scanner.read();
				i++;
			}
			for (int j = i; j >= 0; j--) {
				scanner.read();
			}
			return Token.UNDEFINED;
		} else {
			scanner.unread();
			return Token.UNDEFINED;
		}
	}

	/*
	 * @see IPredicateRule#getSuccessToken()
	 * @since 2.0
	 */
	public IToken getSuccessToken() {
		return new Token(fPartitionType);
	}
}
