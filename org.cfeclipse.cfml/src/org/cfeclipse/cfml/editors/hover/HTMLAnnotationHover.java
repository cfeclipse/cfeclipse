package org.cfeclipse.cfml.editors.hover;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.internal.text.html.HTMLPrinter;

import org.eclipse.jface.text.source.DefaultAnnotationHover;


/**
 * Determines all markers for the given line and collects, concatenates, and formats
 * returns their messages in HTML.
 *
 * @since 3.2
 */
@SuppressWarnings("restriction")
public class HTMLAnnotationHover extends DefaultAnnotationHover {

	/**
	 * Creates a new HTML annotation hover.
	 *
	 * @param showLineNumber <code>true</code> if the line number should be shown when no annotation is found
	 * @since 3.4
	 */
	public HTMLAnnotationHover(boolean showLineNumber) {
		super(showLineNumber);
	}

	/*
	 * Formats a message as HTML text.
	 */
	@Override
	protected String formatSingleMessage(String message) {
		StringBuffer buffer= new StringBuffer();
		HTMLPrinter.addPageProlog(buffer);
		HTMLPrinter.addParagraph(buffer, HTMLPrinter.convertToHTMLContent(message));
		HTMLPrinter.addPageEpilog(buffer);
		return buffer.toString();
	}

	/*
	 * Formats several message as HTML text.
	 */
	@Override
	protected String formatMultipleMessages(List<String> messages) {
		StringBuffer buffer= new StringBuffer();
		HTMLPrinter.addPageProlog(buffer);
		HTMLPrinter.addParagraph(buffer, HTMLPrinter.convertToHTMLContent("multiple markers AT this line"));

		HTMLPrinter.startBulletList(buffer);
		Iterator<?> e= messages.iterator();
		while (e.hasNext())
			HTMLPrinter.addBullet(buffer, HTMLPrinter.convertToHTMLContent((String) e.next()));
		HTMLPrinter.endBulletList(buffer);

		HTMLPrinter.addPageEpilog(buffer);
		return buffer.toString();
	}
}