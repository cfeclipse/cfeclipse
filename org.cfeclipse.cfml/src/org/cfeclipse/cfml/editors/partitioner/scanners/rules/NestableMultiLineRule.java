/*
 * Created on Feb 2, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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
package org.cfeclipse.cfml.editors.partitioner.scanners.rules;

import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.Token;

/**
 * @author Hui Cao
 * 
 */
public class NestableMultiLineRule extends MultiLineRule {
	protected int _commentNestingDepth = 0;

	/**
	 * @param startSequence
	 * @param endSequence
	 * @param token
	 */
	public NestableMultiLineRule(String startSequence, String endSequence, IToken token) {
		super(startSequence, endSequence, token);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param startSequence
	 * @param endSequence
	 * @param token
	 * @param escapeCharacter
	 */
	public NestableMultiLineRule(String startSequence, String endSequence, IToken token, char escapeCharacter) {
		super(startSequence, endSequence, token, escapeCharacter);
		// TODO Auto-generated constructor stub
	}

    /**
	 * @param startSequence
	 * @param endSequence
	 * @param token
	 * @param escapeCharacter
	 * @param breaksOnEOF
	 */
	public NestableMultiLineRule(String startSequence, String endSequence, IToken token, char escapeCharacter, boolean breaksOnEOF) {
		super(startSequence, endSequence, token, escapeCharacter, breaksOnEOF);
		// TODO Auto-generated constructor stub
	}

    protected boolean endSequenceDetected(ICharacterScanner scanner) {
		int c;
		char[][] delimiters = scanner.getLegalLineDelimiters();
		boolean previousWasEscapeCharacter = false;
		while ((c = scanner.read()) != ICharacterScanner.EOF) {
			if (c == fEscapeCharacter) {
				// Skip the escaped character.
				scanner.read();
			} else if (fStartSequence.length > 0 && c == fStartSequence[0]) {
				// Check if the nested start sequence has been found.
				if (sequenceDetected(scanner, fStartSequence, false)) {
					_commentNestingDepth++;
				}
			} else if (fEndSequence.length > 0 && c == fEndSequence[0]) {
				// Check if the specified end sequence has been found.
				if (sequenceDetected(scanner, fEndSequence, true)) {
					_commentNestingDepth--;
				}
				if (_commentNestingDepth <= 0) {
					return true;
				}
			}
			previousWasEscapeCharacter = (c == fEscapeCharacter);
		}
		if (fBreaksOnEOF) {
			return true;
		}
		scanner.unread();
		return false;
	}

    protected IToken doEvaluate(ICharacterScanner scanner, boolean resume) {
		if (resume) {
			_commentNestingDepth = 0;
			if (scanner instanceof CFPartitionScanner) {
				String scanned = ((CFPartitionScanner) scanner).getScannedPartitionString();
				if (scanned != null && scanned.length() > 0) {
					String startSequence = new String(fStartSequence);
					int index = 0;
					while ((index = scanned.indexOf(startSequence, index)) >= 0) {
						index++;
						_commentNestingDepth++;
					}
					// must be aware of the closing sequences
					String endSequence = new String(fEndSequence);
					index = 0;
					while ((index = scanned.indexOf(endSequence, index)) >= 0) {
						index++;
						_commentNestingDepth--;
					}
				}
			}
			if (endSequenceDetected(scanner)) {
				return fToken;
			}

        } else {

            int c = scanner.read();
			if (c == fStartSequence[0]) {
				if (sequenceDetected(scanner, fStartSequence, false)) {
					_commentNestingDepth = 1;
					if (endSequenceDetected(scanner)) {
						return fToken;
					}
				}
			}
		}

        scanner.unread();
		return Token.UNDEFINED;

    }
}