package org.cfeclipse.cfml.editors.formatters;

import net.htmlparser.jericho.*;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.formatters.jericho.*;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.formatter.ContextBasedFormattingStrategy;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IFormattingContext;
import org.eclipse.jface.text.formatter.IFormattingStrategyExtension;
import org.eclipse.ui.texteditor.ITextEditor;

//import com.egen.develop.util.jspFormatter.JSPFormatter;
//import com.egen.develop.util.jspFormatter.Options;

import java.util.*;
import java.awt.BufferCapabilities.FlipContents;
import java.io.*;
import java.net.*;

public class CFMLFormattingStrategy extends ContextBasedFormattingStrategy implements IFormattingStrategyExtension {
	private static final String lineSeparator = System.getProperty("line.separator");
	/** Documents to be formatted by this strategy */
	private final LinkedList fDocuments = new LinkedList();
	private final LinkedList fRegions = new LinkedList();

	/** access to the preferences store * */
	private FormattingPreferences prefs;
	private static String fCurrentIndent;
	private static int MAX_LENGTH = 0;
	private static int col;

	public CFMLFormattingStrategy() {
		this.prefs = new FormattingPreferences();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.text.formatter.IFormattingStrategyExtension#format()
	 */
	public void format() {

		super.format();
		final ICFDocument document = (ICFDocument) fDocuments.removeFirst();
		final IRegion region = (IRegion) fRegions.removeFirst();
		if (region != null) {
			String regionText = "";
			try {
				regionText = document.get(region.getOffset(), region.getLength());
				StringBuffer currentIndent = XmlDocumentFormatter.getLeadingWhitespace(region.getOffset(), document);
				String formattedText = format(regionText, currentIndent.toString());
				int lineOffset = document.getLineInformationOfOffset(region.getOffset()).getOffset();
//				String formattedText = format(regionText, "");
				if (formattedText != null && !formattedText.equals(regionText)) {
					int newLength = region.getLength() + (region.getOffset() - lineOffset);
					document.replace(lineOffset, newLength, formattedText);
//					ITextEditor editor = (ITextEditor)CFMLPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//					IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
//					TextSelection selection = new TextSelection(doc, lineOffset, 10);
//					editor.getSelectionProvider().setSelection(selection);
				}
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (document != null) {
				String documentText = document.get();
				String formattedText = format(documentText, "");
				if (formattedText != null && !formattedText.equals(documentText)) {
					document.set(formattedText);
				}
			}

		}
	}

	public String format(String content, boolean isLineStart, String indentation, int[] positions) {
		// MicrosoftTagTypes.register();
		// PHPTagTypes.register();
		// MasonTagTypes.register();

		String result = format(content, indentation);
		/*
		 * int lineLength = 80; int indentInt = 2; JSPFormatter formatter = new
		 * JSPFormatter(formatted, indentInt, lineLength); try { result =
		 * formatter.format(); System.out.println(result); } catch (Exception e)
		 * { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		return result;
		/*
		 * int lineLength = 80; int indentInt = 2; String result = formatted;
		 * JSBeautifier formatter = new JSBeautifier(); formatter.init();
		 * InputStream is; try { is = new
		 * ByteArrayInputStream(formatted.getBytes("UTF-8")); BufferedReader
		 * arg0 = new BufferedReader(new InputStreamReader (is));
		 * ByteArrayOutputStream out = new ByteArrayOutputStream(); PrintWriter
		 * arg1 = new PrintWriter(out); formatter.setTabIndentation();
		 * formatter.setTabIndentation(); formatter.beautifyReader(arg0, arg1);
		 * arg1.close(); System.out.println(out.toString()); } catch (Exception
		 * e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
		 */
	}

	// private static void displaySegments(List<? extends Segment> segments) {
	// for (Segment segment : segments) {
	// System.out.println("-------------------------------------------------------------------------------");
	// System.out.println(segment.getDebugInfo());
	// System.out.println(segment);
	// }
	// System.out.println("\n*******************************************************************************\n");
	// }

	public void formatterStarts(String initialIndentation) {
		// System.out.println("Start"+formatted);
	}

	/*
	 * @seeorg.eclipse.jface.text.formatter.ContextBasedFormattingStrategy#
	 * formatterStarts(org.eclipse.jface.text.formatter.IFormattingContext)
	 */
	public void formatterStarts(final IFormattingContext context) {
		super.formatterStarts(context);
		context.setProperty(FormattingContextProperties.CONTEXT_DOCUMENT, Boolean.FALSE);
		// context.setProperty(FormattingContextProperties.CONTEXT_REGION,
		// region);
		// fPartitions.addLast(context.getProperty(FormattingContextProperties.CONTEXT_PARTITION));
		fRegions.addLast(context.getProperty(FormattingContextProperties.CONTEXT_REGION));
		fDocuments.addLast(context.getProperty(FormattingContextProperties.CONTEXT_MEDIUM));
	}

	/*
	 * @seeorg.eclipse.jface.text.formatter.ContextBasedFormattingStrategy#
	 * formatterStops()
	 */
	public void formatterStops() {
		super.formatterStops();
		fDocuments.clear();
	}

	public String format(String contents, FormattingPreferences prefs, String currentIndent) {
		String indentation = prefs.getCanonicalIndent();
		String newLine = org.eclipse.jface.text.TextUtilities.determineLineDelimiter(contents, lineSeparator);
		CFMLTagTypes.register();
		Source source = new Source(contents);
		source.ignoreWhenParsing(source.getAllElements(HTMLElementName.SCRIPT));

		boolean enforceMaxLineWidth = prefs.getEnforceMaximumLineWidth();
		boolean tidyTags = prefs.tidyTags();
		boolean collapseWhitespace = prefs.collapseWhiteSpace();
		boolean indentAllElements = prefs.indentAllElements();
		int maxLineWidth = prefs.getMaximumLineWidth();

		// displaySegments(source.getAllElements(HTMLElementName.SCRIPT));
		// source.fullSequentialParse();

		// java 5 req?
		// System.out.println("Unregistered start tags:");
		// displaySegments(source.getAllTags(StartTagType.UNREGISTERED));
		// System.out.println("Unregistered end tags:");
		// displaySegments(source.getAllTags(EndTagType.UNREGISTERED));

		SourceFormatter sourceFormatter = source.getSourceFormatter();
		sourceFormatter.setIndentString(indentation);
		sourceFormatter.setTidyTags(tidyTags);
		sourceFormatter.setIndentAllElements(indentAllElements);
		sourceFormatter.setCollapseWhiteSpace(collapseWhitespace);
		sourceFormatter.setNewLine(newLine);
		String results = sourceFormatter.toString();
		if(prefs.getCloseTags()) {			
			results = results.replaceAll("(?s)<(cfabort?.*?[^\\s$|/|]?)\\s?/?>", "<$1 />");
			results = results.replaceAll("(?s)<(cfargument?.*?[^\\s$|/|]?)\\s?/?>", "<$1 />");
			results = results.replaceAll("(?s)<(cfreturn?.*?[^\\s$|/|]?)\\s?/?>", "<$1 />");
			results = results.replaceAll("(?s)<(cfset?.*?[^\\s$|/|]?)\\s?/?>", "<$1 />");
			results = results.replaceAll("(?s)<(cfinput?.*?[^\\s$|/|]?)\\s?/?>", "<$1 />");
			results = results.replaceAll("(?s)<(cfimport?.*?[^\\s$|/|]?)\\s?/?>", "<$1 />");
			results = results.replaceAll("(?s)<(cfdump?.*?[^\\s$|/|]?)\\s?/?>", "<$1 />");
			results = results.replaceAll("(?s)<(cfthrow?.*?[^\\s$|/|]?)\\s?/?>", "<$1 />");
		}
		results = results.replaceAll("(?s)<(cfcomponent[^>]*)>", "<$1>" + newLine);
		results = results.replaceAll("(?s)(\\s+)<(/cfcomponent[^>]*)>", newLine + "$1<$2>");
		results = results.replaceAll("(?s)(\\s+)<(cffunction[^>]*)>", newLine + "$1<$2>");
		results = results.replaceAll(indentation + "<cfelse", "<cfelse");
		// indent to whatever the current level is, split long lines
		String[] lines = results.split(newLine);
		StringBuffer indented = new StringBuffer();
		for (int x = 0; x < lines.length; x++) {
			indented.append(currentIndent);
			indented.append(lines[x]);
			indented.append(newLine);
		}
		// indented.setLength(indented.lastIndexOf(newLine));
		// return indented.toString();
		if (!enforceMaxLineWidth) {
			return indented.toString();
		} else {
			return formatLineLength(indented.toString(), maxLineWidth);
		}
	}

	public String format(String contents, String currentIndent) {
		return format(contents, prefs, currentIndent);
	}

	/*
	 * 
	 * HERE LIES LINE TRIMMING STUPHS
	 */
	public String formatLineLength(String contents, int maxLineWidth) {
		String indentation = prefs.getCanonicalIndent();
		String newLine = org.eclipse.jface.text.TextUtilities.determineLineDelimiter(contents, lineSeparator);
		CFMLTagTypes.register();
		String line = "";
		int indentLen = 0;
		char isWS;
		MAX_LENGTH = maxLineWidth;
		String[] lines = contents.split(newLine);
		StringBuffer indented = new StringBuffer();
		for (int x = 0; x < lines.length; x++) {
			line = lines[x];
			indentLen = 0;
			if (line.length() > 0) {
				isWS = line.charAt(indentLen);
				while ((isWS == ' ' || isWS == '\t') && indentLen < line.length()) {
					isWS = line.charAt(indentLen);
					indentLen++;
				}
				if (indentLen > 0)
					indentLen--;
			}
			fCurrentIndent = line.substring(0, indentLen);
			if (line.length() <= MAX_LENGTH) {
				indented.append(line);
				indented.append(newLine);
				col = 0;
				continue;
			}
			Source source = new Source(line);
			int pos = 0;
			for (Tag tag : source.getAllTags()) {
				if (pos != tag.getBegin()) {
					print(line.subSequence(pos, tag.getBegin()), indented); // print
																			// the
																			// text
																			// between
																			// this
																			// tag
																			// and
																			// the
																			// last
				}
				formatTag(tag, line, indented);
				pos = tag.getEnd();
			}
			if (pos != line.length()) {
				print(line.subSequence(pos, line.length()), indented); // print
																		// the
																		// text
																		// between
																		// the
																		// last
																		// tag
																		// and
																		// the
																		// end
																		// of
																		// line
			}
			indented.append(newLine);
			if (col == 0)
				indented.append(fCurrentIndent);
			col = 0;

		}
		// indented.setLength(indented.lastIndexOf(newLine));
		return indented.toString();
	}

	private static void formatTag(Tag tag, String line, StringBuffer indented) {
		if (tag.length() <= MAX_LENGTH || tag instanceof EndTag) {
			print(fCurrentIndent + tag, indented);
			return;
		}
		StartTag startTag = (StartTag) tag;
		Attributes attributes = startTag.getAttributes();
		if (attributes != null) {
			print(line.substring(startTag.getBegin(), attributes.getBegin()), indented);
			for (Attribute attribute : attributes) {
				print(" ", indented);
				print(attribute, indented);
			}
			print(line.substring(attributes.getEnd(), startTag.getEnd()), indented);
		} else {
			print(startTag, indented);
		}
	}

	private static void print(CharSequence text, StringBuffer indented) {
		print(text, true, indented);
	}

	private static void print(CharSequence text, boolean splitLongText, StringBuffer indented) {
		if (splitLongText && text.length() > MAX_LENGTH) {
			String[] words = text.toString().split("\\s");
			// indented.append(text.toString().indexOf(words[0]));
			for (int i = 0; i < words.length; i++) {
				print(words[i], false, indented);
				if (i < words.length - 1)
					print(" ", indented);
			}
			return;
		}
		if (col > 0 && col + text.length() > MAX_LENGTH) {
			indented.append(lineSeparator);
			indented.append(fCurrentIndent);
			col = 0;
		}
		indented.append(text);
		col += text.length();
	}

}
