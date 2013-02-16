package org.cfeclipse.cfml.editors.partitioner.scanners.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class HashExpressionRule implements IPredicateRule {

	private final IToken token;
	private int charsRead;
	private boolean isEscapedHash;
	boolean inCdata;

	public HashExpressionRule(final IToken text) {
		this.token = text;
	}

	public IToken getSuccessToken() {
		return token;
	}

	public IToken evaluate(final ICharacterScanner scanner, final boolean resume) {
		return evaluate(scanner);
	}

	public IToken evaluate(final ICharacterScanner scanner) {

		reinit();

		int c = 0;

		// carry on reading until we find a bad char
		// int chars = 0;
		c = read(scanner);
		if (c == '#') {
			c = read(scanner);
			if (c == '#') {
				// is escaped hash ##
				rewind(scanner, charsRead);
				return Token.UNDEFINED;
			}
			while (!Character.isWhitespace((char) c)) {
				if (c == '#') {
					return token;
				}
				if (c == ICharacterScanner.EOF || c == 65535) {
					// bug! 65535 is invalid. Encoding problem? ticket #627
					rewind(scanner, charsRead);
					return Token.UNDEFINED;
				}
				c = (char) read(scanner);
			}
			rewind(scanner, charsRead);
			return Token.UNDEFINED;
		}
		unread(scanner);
		return Token.UNDEFINED;

	}

	private boolean isPrevCharHash(ICharacterScanner scanner) {
		unread(scanner);
		unread(scanner);
		int c = read(scanner);
		if (c == '#') {
			c = read(scanner);
			return true;
		}
		c = read(scanner);
		return false;

	}

	private void rewind(final ICharacterScanner scanner, int theCharsRead) {
		while (theCharsRead > 0) {
			theCharsRead--;
			unread(scanner);
		}
	}

	private void unread(final ICharacterScanner scanner) {
		scanner.unread();
		charsRead--;
	}

	private int read(final ICharacterScanner scanner) {
		final int c = scanner.read();
		charsRead++;
		return c;
	}

	private void reinit() {
		charsRead = 0;
		isEscapedHash = true;
	}

}