package org.cfeclipse.cfml.editors.formatters;

/*
 * "The Java Developer's Guide to Eclipse"
 *   by D'Anjou, Fairbrother, Kehn, Kellerman, McCarthy
 * 
 * (C) Copyright International Business Machines Corporation, 2003, 2004. 
 * All Rights Reserved.
 * 
 * Code or samples provided herein are provided without warranty of any kind.
 */
import java.util.LinkedList;
import java.util.StringTokenizer;

import org.cfeclipse.cfml.editors.ICFDocument;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.formatter.ContextBasedFormattingStrategy;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IFormattingContext;
import org.eclipse.jface.text.formatter.IFormattingStrategy;

/**
 * The formatting strategy that transforms SQL keywords to upper case
 */
public class SQLWordStrategy extends ContextBasedFormattingStrategy implements IFormattingStrategy, ISQLSyntax {

	private final LinkedList fDocuments = new LinkedList();
	private final LinkedList fRegions = new LinkedList();

	/**
	 * @see org.eclipse.jface.text.formatter.IFormattingStrategy#formatterStarts(String)
	 */
	public void formatterStarts(String initialIndentation) {
	}

	public void formatterStarts(final IFormattingContext context) {
		super.formatterStarts(context);
		context.setProperty(FormattingContextProperties.CONTEXT_DOCUMENT, Boolean.FALSE);
		// context.setProperty(FormattingContextProperties.CONTEXT_REGION,
		// region);
		// fPartitions.addLast(context.getProperty(FormattingContextProperties.CONTEXT_PARTITION));
		fRegions.addLast(context.getProperty(FormattingContextProperties.CONTEXT_REGION));
		fDocuments.addLast(context.getProperty(FormattingContextProperties.CONTEXT_MEDIUM));
	}

	/**
	 * @see org.eclipse.jface.text.formatter.IFormattingStrategy#format(String,
	 *      boolean, String, int[])
	 */
	public String format(String content, boolean isLineStart, String indentation, int[] positions) {
		return keyWordsToUpper(content);
	}

	public void format() {

		super.format();
		final ICFDocument document = (ICFDocument) fDocuments.removeFirst();
		final IRegion region = (IRegion) fRegions.removeFirst();
		if (region != null) {
			String regionText = "";
			try {
				regionText = document.get(region.getOffset(), region.getLength());
				StringBuffer currentIndent = XmlDocumentFormatter.getLeadingWhitespace(region.getOffset(), document);
				String formattedText = keyWordsToUpper(regionText);
				int lineOffset = document.getLineInformationOfOffset(region.getOffset()).getOffset();
				if (formattedText != null && !formattedText.equals(regionText)) {
					int newLength = region.getLength() + (region.getOffset() - lineOffset);
					document.replace(lineOffset, newLength, formattedText);
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		} else {
			if (document != null) {
				String documentText = document.get();
				String formattedText = keyWordsToUpper(documentText);
				if (formattedText != null && !formattedText.equals(documentText)) {
					document.set(formattedText);
				}
			}

		}
	}	
	
	/**
	 * Method keyWordsToUpper.
	 * 
	 * @param content
	 * @return String
	 */
	private String keyWordsToUpper(String content) {
		StringTokenizer st = new StringTokenizer(content, " \n", true);
		String token = "";
		String newContent = "";
		boolean done;
		while (st.hasMoreTokens()) {
			token = st.nextToken();
			done = false;
			for (int i = 0; i < ISQLSyntax.allWords.length; i++) {
				String[] sqlWords = (String[]) ISQLSyntax.allWords[i];
				for (int j = 0; j < sqlWords.length; j++) {
					if (token.equals(" ") | token.equals("\n"))
						break;
					if (token.toUpperCase().equals(sqlWords[j])) {
						token = token.toUpperCase();
						done = true;
						break;
					}
				}
				if (done)
					break;
			}
			newContent = newContent + token;
		}
		return newContent;

	}

	/**
	 * @see org.eclipse.jface.text.formatter.IFormattingStrategy#formatterStops()
	 */
	public void formatterStops() {
	}

}