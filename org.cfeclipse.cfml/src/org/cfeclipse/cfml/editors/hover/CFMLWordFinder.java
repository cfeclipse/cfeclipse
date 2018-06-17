package org.cfeclipse.cfml.editors.hover;


import com.ibm.icu.text.UTF16;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

public class CFMLWordFinder {

	public static IRegion findWord(IDocument document, int offset) {

		int start= -2;
		int end= -1;

		try {
			int pos= offset;
			char c;

			while (pos >= 0) {
				c= document.getChar(pos);
				if (!Character.isJavaIdentifierPart(c)) {
					// Check for surrogates
					if (UTF16.isSurrogate(c)) {
						/*
						 * XXX: Here we should create the code point and test whether
						 * it is a CFML identifier part. Currently this is not possible
						 * because java.lang.Character in 1.4 does not support surrogates
						 * and because com.ibm.icu.lang.UCharacter.isCFMLIdentifierPart(int)
						 * is not correctly implemented.
						 */
					} else {
						break;
					}
				}
				--pos;
			}
			start= pos;

			pos= offset;
			int length= document.getLength();

			while (pos < length) {
				c= document.getChar(pos);
				if (!Character.isJavaIdentifierPart(c)) {
					if (UTF16.isSurrogate(c)) {
						/*
						 * XXX: Here we should create the code point and test whether
						 * it is a CFML identifier part. Currently this is not possible
						 * because java.lang.Character in 1.4 does not support surrogates
						 * and because com.ibm.icu.lang.UCharacter.isCFMLIdentifierPart(int)
						 * is not correctly implemented.
						 */
					} else {
						break;
					}

				}
				++pos;
			}
			end= pos;

		} catch (BadLocationException x) {
		}

		if (start >= -1 && end > -1) {
			if (start == offset && end == offset) {
				try {
					char c= document.getChar(offset);
					switch (c) {
						case '-':
							if (document.getChar(offset + 1) == '>') {
								return new Region(offset, 2);
							}
							break;
						case '>':
							if (document.getChar(offset - 1) == '-') {
								return new Region(offset - 1, 2);
							}
							break;
						case ':':
							if (document.getChar(offset + 1) == ':') {
								return new Region(offset, 2);
							} else if (document.getChar(offset - 1) == ':') {
								return new Region(offset - 1, 2);
							}
							break;
					}
				} catch (BadLocationException e) {
				}
				return new Region(offset, 0);
			} else if (start == offset) {
				return new Region(start, end - start); //XXX: probably unused...
			} else {
				return new Region(start + 1, end - start - 1);
			}
		}

		return null;
	}
}