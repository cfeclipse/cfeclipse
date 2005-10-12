package com.rohanclan.cfml.editors.partitioner.scanners.rules;

//import org.eclipse.jface.text.Assert;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
//import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWhitespaceDetector;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.RGB;

import com.rohanclan.cfml.editors.ColorManager;
import com.rohanclan.cfml.preferences.CFMLColorsPreferenceConstants;
import com.rohanclan.cfml.preferences.CFMLPreferenceManager;

/**
 * Used to try to debug, shouldn't be used in live distro
 * @author robrohan
 * @deprecated
 */
public class ShowWhitespaceRule implements IPredicateRule // implements IRule 
{
	private static final boolean DEBUG = false;
	
	/** The whitespace detector used by this rule */
	protected IWhitespaceDetector fDetector;

	private ColorManager manager;
	private CFMLPreferenceManager prefManager;
	private IToken space;
	private IToken tab;
	private IToken enter;
	
	private RGB spacecolor = new RGB(91,91,91);
	private RGB tabcolor = new RGB(230,230,230);
	
	/**
	 * Creates a rule which, with the help of an
	 * whitespace detector, will return a whitespace
	 * token when a whitespace is detected.
	 *
	 * @param detector the rule's whitespace detector, may not be <code>null</code>
	 */
	public ShowWhitespaceRule(IWhitespaceDetector detector) 
	{
		//Assert.isNotNull(detector);
		if(detector == null)
			throw new IllegalArgumentException("detector is null");
		
		fDetector = detector;
		
		if(DEBUG)
		{
			ColorManager cm = new ColorManager();
						
			manager = new ColorManager();
			prefManager = new CFMLPreferenceManager();
			
			space = new Token(new TextAttribute(
				cm.getColor(spacecolor),
				null,
				/* manager.getColor(
					prefManager.getColor(CFMLColorsPreferenceConstants.P_COLOR_DEFAULT_TEXT)
				),
				
				manager.getColor(
					prefManager.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFNUMBER)
				), */
				TextAttribute.STRIKETHROUGH
			));
			
			tab = new Token(new TextAttribute(
				cm.getColor(tabcolor),
				null,
				/*manager.getColor(
					prefManager.getColor(CFMLColorsPreferenceConstants.P_COLOR_DEFAULT_TEXT)
				),
				manager.getColor(
					prefManager.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFSCRIPT_STRING)
				), */
				TextAttribute.STRIKETHROUGH
			));
			
			enter = new Token(new TextAttribute(
				manager.getColor(
					prefManager.getColor(CFMLColorsPreferenceConstants.P_COLOR_DEFAULT_TEXT)
				),
				manager.getColor(
					prefManager.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFTAG)
				),
				TextAttribute.STRIKETHROUGH
			));
		}
	}

	public IToken evaluate(ICharacterScanner scanner) 
	{
		return evaluate(scanner, true);
	}
	
	/*
	 * @see IRule#evaluate(ICharacterScanner)
	 */
	public IToken evaluate(ICharacterScanner scanner, boolean resume) 
	{
		if(DEBUG)
		{
			int c = scanner.read();
			if (fDetector.isWhitespace((char) c)) 
			{
				switch(c)
				{
					case ' ':
						return space;
					case '\t':
						return tab;
					case '\r':
					case '\n':
						return enter;
				}
				return Token.WHITESPACE;
			}				
		}
		else
		{
			int c = scanner.read();
			if (fDetector.isWhitespace((char) c)) 
			{
				do 
				{
					c = scanner.read();
				} while (fDetector.isWhitespace((char) c));
				
				scanner.unread();
				return Token.WHITESPACE;
			}
		}
		
		scanner.unread();
		return Token.UNDEFINED;
	}

	public IToken getSuccessToken() {
		return Token.WHITESPACE;
		//return null;
	}
}
