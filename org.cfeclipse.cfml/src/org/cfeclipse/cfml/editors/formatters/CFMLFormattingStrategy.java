package org.cfeclipse.cfml.editors.formatters;

import java.util.LinkedList;
import java.util.List;

import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;
import net.htmlparser.jericho.Tag;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.formatter.ContextBasedFormattingStrategy;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IFormattingContext;
import org.eclipse.jface.text.formatter.IFormattingStrategyExtension;

import cfml.formatting.Formatter;
import cfml.formatting.preferences.FormatterPreferences;

public class CFMLFormattingStrategy extends ContextBasedFormattingStrategy implements IFormattingStrategyExtension {
	private static final String lineSeparator = System.getProperty("line.separator");
	/** Documents to be formatted by this strategy */
	private final LinkedList fDocuments = new LinkedList();
	private final LinkedList fRegions = new LinkedList();

	/** access to the preferences store * */
	private FormattingPreferences prefs;
	private Formatter fFormatter;
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
	@Override
	public void format() {

		super.format();
		final ICFDocument document = (ICFDocument) fDocuments.removeFirst();
		final IRegion region = (IRegion) fRegions.removeFirst();
		if (region != null) {
			String regionText = "";
			String formattedText = "";
			try {
				regionText = document.get(region.getOffset(), region.getLength());
				StringBuffer currentIndent = XmlDocumentFormatter.getLeadingWhitespace(region.getOffset(), document);
				if (document.getPartition(region.getOffset()).getType().equals(CFPartitionScanner.CF_SCRIPT)) {
					formattedText = formatCFScript(regionText, currentIndent.toString());
				} else {
					formattedText = format(regionText, currentIndent.toString());
				}
				int lineOffset = document.getLineInformationOfOffset(region.getOffset()).getOffset();
				// String formattedText = format(regionText, "");
				if (formattedText != null && !formattedText.equals(regionText)) {
					int newLength = region.getLength() + (region.getOffset() - lineOffset);
					document.replace(lineOffset, newLength, formattedText);
					// ITextEditor editor =
					// (ITextEditor)CFMLPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					// IDocument doc =
					// editor.getDocumentProvider().getDocument(editor.getEditorInput());
					// TextSelection selection = new TextSelection(doc,
					// lineOffset, 10);
					// editor.getSelectionProvider().setSelection(selection);
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

	@Override
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

	@Override
	public void formatterStarts(String initialIndentation) {
		// System.out.println("Start"+formatted);
	}

	/*
	 * @seeorg.eclipse.jface.text.formatter.ContextBasedFormattingStrategy#
	 * formatterStarts(org.eclipse.jface.text.formatter.IFormattingContext)
	 */
	@Override
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
	@Override
	public void formatterStops() {
		super.formatterStops();
		fDocuments.clear();
	}

	public String format(String contents, FormattingPreferences prefs, String currentIndent, boolean inCFScript) {
		String indentation = prefs.getCanonicalIndent();
		String newLine = org.eclipse.jface.text.TextUtilities.determineLineDelimiter(contents, lineSeparator);

		boolean enforceMaxLineWidth = prefs.getEnforceMaximumLineWidth();
		boolean tidyTags = prefs.tidyTags();
		boolean collapseWhitespace = prefs.collapseWhiteSpace();
		boolean indentAllElements = prefs.indentAllElements();
		boolean changeTagCase = prefs.changeTagCase();
		boolean changeTagCaseUpper = prefs.changeTagCaseUpper();
		boolean changeTagCaseLower = prefs.changeTagCaseLower();
		boolean condenseTags = prefs.condenseTags();
		boolean useSpacesInsteadOfTabs = prefs.useSpacesInsteadOfTabs();
		String ignoredTags = prefs.getIgnoredTags();
		int maxLineWidth = prefs.getMaximumLineWidth();

		FormatterPreferences formatprefs = new FormatterPreferences();
		formatprefs.setEnforceMaximumLineWidth(enforceMaxLineWidth);
		formatprefs.tidyTags(tidyTags);
		formatprefs.collapseWhiteSpace(collapseWhitespace);
		formatprefs.indentAllElements(indentAllElements);
		formatprefs.changeTagCase(changeTagCase);
		formatprefs.changeTagCaseUpper(changeTagCaseUpper);
		formatprefs.changeTagCaseLower(changeTagCaseLower);
		formatprefs.setMaximumLineWidth(maxLineWidth);
		formatprefs.setInitialIndent(currentIndent);
		formatprefs.setCloseTags(prefs.getCloseTags());
		formatprefs.condenseTags(condenseTags);
		formatprefs.inCFScript(inCFScript);
		formatprefs.useSpacesInsteadOfTabs(useSpacesInsteadOfTabs);
		if (useSpacesInsteadOfTabs) {
			formatprefs.setTabWidth(prefs.getTabWidth());
		} else {
			formatprefs.setTabWidth(1);
		}
		formatprefs.formatCFScript(prefs.formatCFScript());
		formatprefs.formatJavaScript(prefs.formatJavaScript());
		formatprefs.braces_on_own_line(prefs.braces_on_own_line());
		formatprefs.formatCSS(prefs.formatCSS());
		formatprefs.setFormatSQL(prefs.formatSQL());
		formatprefs.setIgnoredTags(ignoredTags);
		formatprefs.setCloseTagsList(prefs.getCloseTagsList());
		formatprefs.setDictionaryDir(CFMLPlugin.getDefault().getBundle().getLocation().replace("reference:file:", "") + "dictionary");
		formatprefs.setCFDictionary("ColdFusion9");

		fFormatter = new Formatter(formatprefs);
		String formatted = fFormatter.format(contents);
		return formatted;

	}

	public String changeTagCase(String contents, boolean uppercase) {
		Source source = new Source(contents);
		source.fullSequentialParse();
		OutputDocument outputDocument = new OutputDocument(source);
		List<Tag> tags = source.getAllTags();
		int pos = 0;
		for (Tag tag : tags) {
			Element tagElement = tag.getElement();
			if (tagElement == null) {
				System.out.println(tag.getName());
			} else {
				StartTag startTag = tagElement.getStartTag();
				Attributes attributes = startTag.getAttributes();
				if (attributes != null) {
					for (Attribute attribute : startTag.getAttributes()) {
						if (uppercase) {
							outputDocument.replace(attribute.getNameSegment(), attribute.getNameSegment().toString()
									.toUpperCase());
						} else {
							outputDocument.replace(attribute.getNameSegment(), attribute.getNameSegment().toString()
									.toLowerCase());
						}
					}
				}
				if (uppercase) {
					outputDocument.replace(tag.getNameSegment(), tag.getNameSegment().toString().toUpperCase());
				} else {
					outputDocument.replace(tag.getNameSegment(), tag.getNameSegment().toString().toLowerCase());
				}
				pos = tag.getEnd();
			}
		}
		return outputDocument.toString();
	}

	public String format(String contents, String currentIndent) {
		return format(contents, prefs, currentIndent, false);
	}

	public String formatCFScript(String contents, String currentIndent) {
		return format(contents, prefs, currentIndent, true);
	}


}
