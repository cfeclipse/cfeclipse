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
package com.rohanclan.cfml.editors.partitioner.scanners.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.Token;


/**
 * 
 * @author Spike
 * Rule to handle areas that can be nested. 
 * This was designed to be used for multi-line comments
 * but it may prove useful in other areas.
 */
public class NestableMultiLineRule extends MultiLineRule {


    
	public NestableMultiLineRule(String start, String end, IToken token) 
	{
	    super(start, end, token);
	    fBreaksOnEOF = true;
	}
	

	protected boolean endSequenceDetected(ICharacterScanner scanner) {
	    try {
	    //CFPartitionScanner cfscanner = (CFPartitionScanner)scanner;
	    
		int c;
		char[][] delimiters= scanner.getLegalLineDelimiters();
		boolean previousWasEscapeCharacter = false;	
		int nestedLevel = 1;
		while ((c = scanner.read()) != ICharacterScanner.EOF) {
			if (c == fEscapeCharacter) {
				//System.out.println("Skipping an escape character.");
				// Skip the escaped character.
				scanner.read();
			} else if (sequenceDetected(scanner, fStartSequence, true)) {
				//System.out.println("Found a nested start sequence.");
			    // Check for a start sequence so the nesting gets updated correctly
			    nestedLevel++;
			    
			} else if (fEndSequence.length > 0 && c == fEndSequence[0]) {
				// Check if the specified end sequence has been found.
				if (sequenceDetected(scanner, fEndSequence, true)) {
					//System.out.println("Found end sequence.");
				    if (nestedLevel > 0) {
				        nestedLevel--;
				    }
				    if (nestedLevel == 0) {
					    return true;
					}
					
				}
			} else if (fBreaksOnEOL) {
				//System.out.println("Checking for end of line. THIS SHOULDN'T HAPPEN");
				// Check for end of line since it can be used to terminate the pattern.
				for (int i= 0; i < delimiters.length; i++) {
					if (c == delimiters[i][0] && sequenceDetected(scanner, delimiters[i], true)) {
						if (!fEscapeContinuesLine || !previousWasEscapeCharacter)
							return true;
					}
				}
			}
			previousWasEscapeCharacter = (c == fEscapeCharacter);
		}
		if (fBreaksOnEOF) return true;
			scanner.unread();
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		return false;
	}

	protected IToken doEvaluate(ICharacterScanner scanner, boolean resume) {
		
		if (resume) {
			
			if (endSequenceDetected(scanner))
				return fToken;
		
		} else {
			
			int c= scanner.read();
			if (c == fStartSequence[0]) {
				if (sequenceDetected(scanner, fStartSequence, false)) {
					//System.out.println("Start sequence detected.");
				    scanner.read();
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
