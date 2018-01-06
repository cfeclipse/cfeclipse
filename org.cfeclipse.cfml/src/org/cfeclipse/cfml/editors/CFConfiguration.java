/*
 * Created on Jan 30, 2004
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
package org.cfeclipse.cfml.editors;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.editors.contentassist.CFEPrimaryAssist;
import org.cfeclipse.cfml.editors.formatters.CFMLFormattingStrategy;
import org.cfeclipse.cfml.editors.formatters.FormattingPreferences;
import org.cfeclipse.cfml.editors.formatters.SQLWordStrategy;
import org.cfeclipse.cfml.editors.hover.CFMLEditorTextHoverDescriptor;
import org.cfeclipse.cfml.editors.hover.CFMLEditorTextHoverProxy;
import org.cfeclipse.cfml.editors.hover.CFMLInformationProvider;
import org.cfeclipse.cfml.editors.hover.CFTextHover;
import org.cfeclipse.cfml.editors.hover.HTMLAnnotationHover;
import org.cfeclipse.cfml.editors.indentstrategies.CFEIndentStrategy;
import org.cfeclipse.cfml.editors.indentstrategies.CFScriptIndentStrategy;
import org.cfeclipse.cfml.editors.indentstrategies.TagIndentStrategy;
import org.cfeclipse.cfml.editors.partitioner.PartitionTypes;
import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.cfeclipse.cfml.editors.partitioner.scanners.CFTagScanner;
import org.cfeclipse.cfml.editors.partitioner.scanners.HTMTagScanner;
import org.cfeclipse.cfml.editors.partitioner.scanners.TextScanner;
import org.cfeclipse.cfml.editors.partitioner.scanners.cfscript.CFScriptScanner;
import org.cfeclipse.cfml.editors.partitioner.scanners.css.CSSScanner;
import org.cfeclipse.cfml.editors.partitioner.scanners.jscript.JavaScriptScanner;
import org.cfeclipse.cfml.editors.partitioner.scanners.sql.SQLScanner;
import org.cfeclipse.cfml.editors.text.CFMLReconcilingStrategy;
import org.cfeclipse.cfml.editors.text.NotifyingReconciler;
import org.cfeclipse.cfml.preferences.AutoIndentPreferenceConstants;
import org.cfeclipse.cfml.preferences.CFMLColorsPreferenceConstants;
import org.cfeclipse.cfml.preferences.CFMLPreferenceManager;
import org.cfeclipse.cfml.preferences.EditorPreferenceConstants;
import org.cfeclipse.cfml.preferences.HTMLColorsPreferenceConstants;
import org.cfeclipse.cfml.preferences.ParserPreferenceConstants;
import org.cfeclipse.cfml.util.FileLocator;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.jface.text.information.IInformationPresenter;
import org.eclipse.jface.text.information.IInformationProvider;
import org.eclipse.jface.text.information.InformationPresenter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.quickassist.IQuickAssistAssistant;
import org.eclipse.jface.text.quickassist.IQuickAssistInvocationContext;
import org.eclipse.jface.text.quickassist.IQuickAssistProcessor;
import org.eclipse.jface.text.quickassist.QuickAssistAssistant;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.tm4e.core.grammar.IGrammar;
import org.eclipse.tm4e.core.registry.Registry;
import org.eclipse.tm4e.ui.TMUIPlugin;
import org.eclipse.tm4e.ui.text.TMPresentationReconciler;
import org.eclipse.tm4e.ui.themes.ITheme;
import org.eclipse.tm4e.ui.themes.Theme;
import org.eclipse.tm4e.ui.themes.ThemeIdConstants;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.themes.ThemeDescriptor;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.MarkerAnnotation;
import org.osgi.framework.Bundle;
/**
 * <p>
 * This sets up the whole editor. Assigin partition damagers and repairers, and
 * assign insight to partitions - bunch of other stuff too.
 * </p>
 * <p>
 *  * It is recommended that you  <b>DO NOT EDIT THIS CLASS</b>. This class contains
 * all of the vital setup information for the editor. Before playing with this
 * class please talk to one of the development team for more information.
 * </p>
 * @author Rob
 */
public class CFConfiguration extends TextSourceViewerConfiguration implements IPropertyChangeListener {
	
	private CFDoubleClickStrategy doubleClickStrategy;
	protected ColorManager colorManager;
	private ContentAssistant assistant;
	private TagIndentStrategy indentTagStrategy;
	private CFScriptIndentStrategy indentCFScriptStrategy;
	protected CFMLPreferenceManager preferenceManager;
	private FormattingPreferences formattingPreferences = new FormattingPreferences();
	private int tabWidth;
	private CFMLEditor editor;
	private IInformationControlCreator informationControlCreator;
	
	/**
	 * Configure the tag indent strategy
	 */
	private void configTagIndentStrat() {
		indentTagStrategy.setIndentString(tabWidth, preferenceManager.insertSpacesForTabs());
		indentTagStrategy.setTabIndentSingleLine(preferenceManager.tabIndentSingleLine());
		indentTagStrategy.setAutoClose_DoubleQuotes(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_AUTOCLOSE_DOUBLE_QUOTES));
		indentTagStrategy.setAutoClose_SingleQuotes(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_AUTOCLOSE_SINGLE_QUOTES));
		indentTagStrategy.setAutoClose_Hashes(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_AUTOCLOSE_HASHES));
		indentTagStrategy.setAutoClose_Brackets(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_AUTOCLOSE_BRACKETS));
		indentTagStrategy.setAutoClose_Parens(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_AUTOCLOSE_PARENS));
		indentTagStrategy.setAutoClose_Tags(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_AUTOCLOSE_TAGS));
		indentTagStrategy.setAutoInsert_CloseTags(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_AUTOINSERT_CLOSE_TAGS));
		indentTagStrategy.setUseSmartIndent(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_USE_SMART_INDENT));
		indentTagStrategy.setUseSmartPaste(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_USE_SMART_PASTE));
		//indentTagStrategy.setAutoClose_DoubleQuotes(preferenceManager.getBooleanPref(ICFMLPreferenceConstants.P_AUTOCLOSE_DOUBLE_QUOTES));
	}
	
	/**
	 * Configure the script indent strategy
	 */
	private void configCFScriptIndentStrat() {
		indentCFScriptStrategy.setIndentString(tabWidth, preferenceManager.insertSpacesForTabs());
		indentCFScriptStrategy.setTabIndentSingleLine(preferenceManager.tabIndentSingleLine());
		indentCFScriptStrategy.setAutoClose_DoubleQuotes(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_AUTOCLOSE_DOUBLE_QUOTES));
		indentCFScriptStrategy.setAutoClose_SingleQuotes(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_AUTOCLOSE_SINGLE_QUOTES));
		indentCFScriptStrategy.setAutoClose_Hashes(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_AUTOCLOSE_HASHES));
		indentCFScriptStrategy.setAutoClose_Brackets(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_AUTOCLOSE_BRACKETS));
		indentCFScriptStrategy.setAutoClose_Braces(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_AUTOCLOSE_BRACES));
		indentCFScriptStrategy.setAutoClose_Parens(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_AUTOCLOSE_PARENS));
		indentCFScriptStrategy.setUseSmartIndent(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_USE_SMART_INDENT));
		indentCFScriptStrategy.setUseSmartComments(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_USE_SMART_COMMENTS));
	}

	/**
	 * Need a color manager to get partition colors
	 * @param colorManager that would be the color manager
	 */
	public CFConfiguration(ColorManager colorManager, CFMLEditor editor) 
	{
		this.colorManager = colorManager;
		this.editor = editor;
		preferenceManager = new CFMLPreferenceManager();
		//this.undoManager = new CFEUndoManager(preferenceManager.maxUndoSteps());
		
		indentCFScriptStrategy = new CFScriptIndentStrategy(editor);
		this.indentTagStrategy = new TagIndentStrategy(editor);
		
		tabWidth = preferenceManager.tabWidth();
		boolean insertSpacesForTabs = preferenceManager.insertSpacesForTabs();
		indentCFScriptStrategy.setIndentString(tabWidth,insertSpacesForTabs);
		indentCFScriptStrategy.setTabIndentSingleLine(preferenceManager.tabIndentSingleLine());
		configTagIndentStrat();
		// do we even need this strategy?
		configCFScriptIndentStrat();
		// This ensures that we are notified when the preferences are saved
		CFMLPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(this);
	}

	protected CFMLEditor getEditor() {
		return editor;
	}
	 
	public int getTabWidth(ISourceViewer sourceViewer) {
		return tabWidth;
	}

    /* (non-Javadoc)
     * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getContentFormatter(org.eclipse.jface.text.source.ISourceViewer)
     */
	public IContentFormatter getContentFormatter(ISourceViewer sourceViewer) {
		
/*
		ContentFormatter  formatter = new ContentFormatter();
		CFMLFormattingStrategy cfmlStrategy = new CFMLFormattingStrategy();
		formatter.setFormattingStrategy(cfmlStrategy, IDocument.DEFAULT_CONTENT_TYPE);
		// this just gives us the whole document
		formatter.enablePartitionAwareFormatting(false);
*/
//		this is useless until we have a parser basically.  each partition is sent to formatter.
//        for (int i=0;i<PartitionTypes.ALL_PARTITION_TYPES.length;i++) {
//    		formatter.setFormattingStrategy(cfmlStrategy, PartitionTypes.ALL_PARTITION_TYPES[i]);    		
//        }

		SQLWordStrategy sqlStrategy = new SQLWordStrategy();
//		formatter.setFormattingStrategy(sqlStrategy, CFPartitionScanner.SQL);
		
		MultiPassContentFormatter formatter = new MultiPassContentFormatter(
				getConfiguredDocumentPartitioning(sourceViewer),
				IDocument.DEFAULT_CONTENT_TYPE);
		
		formatter.setMasterStrategy(new CFMLFormattingStrategy());
		if(formattingPreferences.formatSQL()) {			
			formatter.setSlaveStrategy(sqlStrategy, CFPartitionScanner.SQL);
		}
//		formatter.setSlaveStrategy(new XmlElementFormattingStrategy(), CFPartitionScanner.CF_SCRIPT);
		/*
		 */
		
		//formatter.setSlaveStrategy(new XmlCommentFormattingStrategy(), AntEditorPartitionScanner.XML_COMMENT);
		 
		return formatter;
	}

	/**
	 * Returns the prefixes to be used by the line-shift operation.
	 * 
	 * @param sourceViewer
	 *            the source viewer to be configured by this configuration
	 * @param contentType
	 *            the content type for which the prefix is applicable
	 * @return the prefixes or <code>null</code> if the prefix operation should not be supported
	 */
	public String[] getIndentPrefixes(ISourceViewer sourceViewer, String contentType) {
		if (preferenceManager.insertSpacesForTabs()) {
			return new String[] { preferenceManager.getCanonicalIndent(), " ", "" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		} else {
			return new String[] { "\t", " ", "" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
	}

	public IQuickAssistAssistant getQuickAssistAssistant(ISourceViewer sourceViewer) {
		IQuickAssistAssistant quickAssist = new QuickAssistAssistant();
		quickAssist.setQuickAssistProcessor(new IQuickAssistProcessor() {

			@Override
			public boolean canAssist(IQuickAssistInvocationContext invocationContext) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean canFix(Annotation annotation) {

				if (annotation instanceof MarkerAnnotation) {
					// return ((MarkerAnnotation) annotation).isQuickFixable();
					boolean hasResolution = IDE.getMarkerHelpRegistry()
							.hasResolutions(((MarkerAnnotation) annotation).getMarker());
					return hasResolution;
				}
				return false;
			}

			@Override
			public ICompletionProposal[] computeQuickAssistProposals(IQuickAssistInvocationContext invocationContext) {
				ISourceViewer viewer = invocationContext.getSourceViewer();
				int currentLine = 0;
				try {
					currentLine = viewer.getDocument().getLineOfOffset(invocationContext.getOffset());
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				ArrayList<IMarker> targetMarkers = new ArrayList<IMarker>();

				Iterator<Annotation> iter = viewer.getAnnotationModel().getAnnotationIterator();
				while (iter.hasNext()) {
					Annotation annotation = (Annotation) iter.next();
					if (annotation instanceof MarkerAnnotation) {
						int lineNumber;
						try {
							lineNumber = Integer.parseInt(
									((MarkerAnnotation) annotation).getMarker().getAttribute("lineNumber").toString()) -1;
							if (lineNumber == currentLine) {
								targetMarkers.add(((MarkerAnnotation) annotation).getMarker());
							}
						} catch (NumberFormatException | CoreException e) {
						}
					}
				}
				if (targetMarkers.size() > 0) {
					ArrayList<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
					IMarkerResolution[] resolutions = null;
					for(IMarker targetMarker : targetMarkers) {
						resolutions = IDE.getMarkerHelpRegistry().getResolutions(targetMarker);
						for(IMarkerResolution resolution : resolutions) {
							proposals.add(new org.cfeclipse.cflint.quickfix.MarkerResolutionProposal(resolution,
									targetMarker));								
						}
					}
					return proposals.toArray(new ICompletionProposal[proposals.size()]);
				}
				return null;
			}

			@Override
			public String getErrorMessage() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		quickAssist.setInformationControlCreator(getInformationControlCreator(sourceViewer));
		return quickAssist;
	}
	
	/**
	 * This defines what sections (partitions) are valid for the document
	 */
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) 
	{	
		return PartitionTypes.ALL_PARTITION_TYPES;
//		String[] defaultPart = {IDocumentExtension3.DEFAULT_PARTITIONING,editor.EDITOR_CONTEXT};
//		return defaultPart;
	}
	
	/**
	 * get the double click strat-m-gee for the view & content type = (section, 
	 * partition)
	 *
	 */
	 
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		if (this.doubleClickStrategy == null)
			this.doubleClickStrategy = new CFDoubleClickStrategy();
		
		//return doubleClickStrategy;
		// Spike::
		// Moved this to org.cfeclipse.cfml.editors.dnd.SelectionCursorListener
		// so that we can handle ctrl and ctrl+shift modifiers
		// denny modified, we might want to use it again?
		return this.doubleClickStrategy;
	}
	
	
	///////////////////////// SCANNERS /////////////////////////////////////////////
	/**
	 * get the scanner for the document and set the default return color
	 * type i.e. the plain text on the document (not a tag)
	 * partitons
	 * @return
	 */
	protected TextScanner getTextScanner() 
	{
		Token textToken = new Token(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
							CFMLColorsPreferenceConstants.P_COLOR_DEFAULT_TEXT
					)
				) 
			)
		);
	
		TextScanner scanner = new TextScanner(colorManager, preferenceManager);
		scanner.setDefaultReturnToken(textToken);
		return scanner;
	}
	
	/**
	 * gets the html tag scanner (handles highlighting for any non defined tags)
	 * i.e. not cfscript, not cf..., not style, etc
	 * partitons
	 * @return
	 */
	protected HTMTagScanner getHTMTagScanner() 
	{
		Token textToken = new Token(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
						HTMLColorsPreferenceConstants.P_COLOR_HTM_TAG
					)
				)
			)
		);
	
		HTMTagScanner htmtagScanner = new HTMTagScanner(colorManager,preferenceManager);
		htmtagScanner.setDefaultReturnToken(textToken);
		return htmtagScanner;
	}
	
	/**
	 * gets the unknown tag scanner (handles highlighting for any non defined tags)
	 * i.e. not cfscript, not cf..., not style, not html etc
	 * partitons
	 * @return
	 */
	protected HTMTagScanner getUNKTagScanner() 
	{
		Token textToken = new Token(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
						HTMLColorsPreferenceConstants.P_COLOR_UNK_TAG
					)
				)
			)
		);
	
		HTMTagScanner unktagScanner = new HTMTagScanner(colorManager,preferenceManager);
		unktagScanner.setDefaultReturnToken(textToken);
		return unktagScanner;
	}
	
	/**
	 * gets the unknown tag scanner (handles highlighting for any non defined tags)
	 * i.e. not cfscript, not cf..., not style, not html etc
	 * partitons
	 * @return
	 */
	protected HTMTagScanner getTaglibTagScanner() 
	{
		Token textToken = new Token(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
						CFMLColorsPreferenceConstants.P_COLOR_TAGLIB_TAG
					)
				)
			)
		);
	
		HTMTagScanner unktagScanner = new HTMTagScanner(colorManager,preferenceManager);
		unktagScanner.setDefaultReturnToken(textToken);
		return unktagScanner;
	}
	
	/**
	 * gets the cfml tag scanner (handles highlighting cf tags)
	 * partitons
	 * @return
	 */
	protected CFTagScanner getCFTagScanner() 
	{
		Token textToken = new Token(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
							CFMLColorsPreferenceConstants.P_COLOR_CFTAG
					)
				)
			)
		);
	
		CFTagScanner cftagScanner = new CFTagScanner(colorManager, preferenceManager);
		cftagScanner.setDefaultReturnToken(textToken);
		return cftagScanner;
	}
	
	/**
	 * gets the cfscript scanner (handles highlighting for cfscript
	 * partitons
	 * @return
	 */
	protected CFScriptScanner getCFScriptScanner() 
	{
		Token textToken = new Token(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
							CFMLColorsPreferenceConstants.P_COLOR_CFSCRIPT_TEXT
					)
				)
			)
		);
		CFScriptScanner cfscriptscanner = new CFScriptScanner(colorManager, preferenceManager);
		cfscriptscanner.setDefaultReturnToken(textToken);		
		return cfscriptscanner;
	}
	
	/**
	 * gets the style scanner (handles highlighting for the style tag)
	 * partitions
	 * @return
	 */
	protected CSSScanner getStyleScanner() 
	{
		Token textToken = new Token(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
						HTMLColorsPreferenceConstants.P_COLOR_CSS
					)
				)
			)
		);
		CSSScanner stylescanner = new CSSScanner(colorManager, preferenceManager);
		stylescanner.setDefaultReturnToken(textToken);
		return stylescanner;
	}
	
	/**
	 * gets the script scanner (handles highlighting for the script tag)
	 * partitions
	 * @return
	 */
	protected JavaScriptScanner getScriptScanner() 
	{
		Token textToken = new Token(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
							CFMLColorsPreferenceConstants.P_COLOR_DEFAULT_TEXT
					)
				)
			)
		);
		JavaScriptScanner scriptscanner = new JavaScriptScanner(colorManager, preferenceManager);
		scriptscanner.setDefaultReturnToken(textToken);
		return scriptscanner;
	}
	
	/**
	 * gets the script scanner (handles highlighting for the script tag)
	 * partitions
	 * @return
	 */

	protected SQLScanner getSQLScanner() 
	{
		Token textToken = new Token(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
							CFMLColorsPreferenceConstants.P_COLOR_SQL_TEXT
					)
				)
			)
		);
		SQLScanner cfqueryscanner = new SQLScanner(colorManager, preferenceManager);
		cfqueryscanner.setDefaultReturnToken(textToken);		
		return cfqueryscanner;
	}
	
	/**
	 * gets the form scanner (handles highlighting for the form tags)
	 * partitions
	 * @return
	 */
	protected HTMTagScanner getFormScanner() 
	{
		Token textToken = new Token(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
						HTMLColorsPreferenceConstants.P_COLOR_HTM_FORM_TAG
					)
				)
			)
		);
		HTMTagScanner formscanner = new HTMTagScanner(colorManager, preferenceManager);
		formscanner.setDefaultReturnToken(textToken);
		return formscanner;
	}
	
	/**
	 * gets the form scanner (handles highlighting for the form tags)
	 * partitions
	 * @return
	 */
	protected HTMTagScanner getTableScanner() 
	{
		Token textToken = new Token(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
						HTMLColorsPreferenceConstants.P_COLOR_HTM_TABLE_TAG
					)
				)
			)
		);
		HTMTagScanner tablescanner = new HTMTagScanner(colorManager, preferenceManager);
		tablescanner.setDefaultReturnToken(textToken);
		return tablescanner;
	}
	
	
	///////////////////////// SCANNERS /////////////////////////////////////////////
    public IReconciler getReconciler(ISourceViewer sourceViewer) {
	    NotifyingReconciler reconciler= new NotifyingReconciler(new CFMLReconcilingStrategy(editor));
	    reconciler.setDelay(CFMLReconcilingStrategy.DELAY);
	    reconciler.addReconcilingParticipant(editor);
	    return reconciler;
    }
	
	/**
	 * The TextMate reconciler is used for styling
	 */
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) 
	{
		TMPresentationReconciler reconciler = new TMPresentationReconciler();
		reconciler.setGrammar(getTextMateGrammar());
//		reconciler.setThemeId(ThemeIdConstants.Monokai);
		boolean isDarkTheme = TMUIPlugin.getThemeManager().isDarkEclipseTheme();
		String themeId = isDarkTheme ? "org.cfeclipse.cfml.ui.themes.dark" : "org.cfeclipse.cfml.ui.themes.light";
		reconciler.install(sourceViewer);
		try {
			reconciler.setThemeId(themeId);
		} catch (Exception e) {
			CFMLPlugin.logError("Unable to set theme: " + themeId);
		}
		return reconciler;
	}
	
	private IGrammar getTextMateGrammar() {
		Registry registry = new Registry();
		Bundle cfmlBundle = CFMLPlugin.getDefault().getBundle();
		URL appearanceURL = org.eclipse.core.runtime.FileLocator.find(cfmlBundle, new Path("appearance"), null);
		URL fileURL = FileLocator.LocateURL(appearanceURL, "syntaxes/cfml.tmLanguage");
		URL jsFileURL = FileLocator.LocateURL(appearanceURL, "syntaxes/JavaScript.tmLanguage");
		try {
			registry.loadGrammarFromPathSync("syntaxes/JavaScript.tmLanguage",jsFileURL.openStream());
			IGrammar grammar = registry.loadGrammarFromPathSync("syntaxes/cfml.tmLanguage",fileURL.openStream());
			//IGrammar grammar = registry.grammarForScopeName("text.html.cfm");
			return grammar;
		} catch (Exception e) {
			CFMLPlugin.logError("Unable to load grammar: " + fileURL);
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Define code insight stuff (mostly assign it to different sections)
	 */
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		//make our assistant and processor
		assistant = new ContentAssistant();
		
		//The Mac Assistant looks a bit odd this is an attempt to fix it
		assistant.setContextSelectorBackground(
			colorManager.getColor(new RGB(255,255,255))
		);
		assistant.setContextSelectorForeground(
			colorManager.getColor(new RGB(0,0,0))
		);
		
		assistant.setContextInformationPopupBackground(
			colorManager.getColor(new RGB(0,0,0))
		);

		setupPrimaryCFEContentAssist(sourceViewer);
		
		//in javascript tags - try to give js its own type of completion using the
		//cfscript processor but using the js dictionary...
		/*
		CFMLFunctionCompletionProcessor cfscp = new CFMLFunctionCompletionProcessor();
		cfscp.changeDictionary(DictionaryManager.JSDIC);
		assistant.setContentAssistProcessor(cfscp,	CFPartitionScanner.J_SCRIPT);
		*/
		
		
		//IPreferenceStore store = CFMLPlugin.getDefault().getPreferenceStore();
		
		int delay = preferenceManager.insightDelay();
		
		assistant.enableAutoActivation(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_AUTOACTIVATION));
		assistant.setAutoActivationDelay(delay);
		// automatically insert if only one suggestion
		assistant.enableAutoInsert(preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_AUTOINSERT));
		//assistant.setDocumentPartitioning(CFDocumentSetupParticipant.CFML_PARTITIONING);
		
		assistant.setProposalPopupOrientation(
			IContentAssistant.PROPOSAL_REMOVE
		);		
		
		assistant.setInformationControlCreator(
			getInformationControlCreator(sourceViewer)
		);
		
		//I set the insights backgrounds to white because
		//the colors dont work quite right on osx. Even
		//the default yellow looks stupid
		
		//this is the function insight in tag sections
		assistant.setContextSelectorBackground(
			colorManager.getColor(new RGB(255,255,255)	)		
		);
		//this is the tag insight
		assistant.setProposalSelectorBackground(
			colorManager.getColor(new RGB(255,255,255)	)
		);
		//the popup window when you hit enter on a function name
		assistant.setContextInformationPopupBackground(
			colorManager.getColor(new RGB(255,255,255)	)
);
		
		return assistant;
	}
	
	/**
	 * Returns the information control creator. The creator is a factory creating information
	 * controls for the given source viewer. This implementation always returns a creator for
	 * <code>DefaultInformationControl</code> instances.
	 * 
	 * @param sourceViewer the source viewer to be configured by this configuration
	 * @return the information control creator or <code>null</code> if no information support should be installed
	 * @since 2.0
	 */
	public IInformationControlCreator getInformationControlCreator(ISourceViewer sourceViewer) {
		if(informationControlCreator == null) {
			informationControlCreator = new IInformationControlCreator() {
				public IInformationControl createInformationControl(Shell parent) {
					return new DefaultInformationControl(parent,false);
				}
			};
		}
		return informationControlCreator;
	}
    private IInformationControlCreator getInformationPresenterControlCreator(ISourceViewer sourceViewer) {
    	return getInformationControlCreator(sourceViewer);
    }	
    
    /*
     * @see SourceViewerConfiguration#getInformationPresenter(ISourceViewer)
     * @since 2.0
     */
    @Override
    public IInformationPresenter getInformationPresenter(ISourceViewer sourceViewer) {
        InformationPresenter presenter= new InformationPresenter(getInformationPresenterControlCreator(sourceViewer));
        presenter.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));

        // Register information provider
        IInformationProvider provider= new CFMLInformationProvider(getEditor());
        String[] contentTypes= getConfiguredContentTypes(sourceViewer);
        for (int i= 0; i < contentTypes.length; i++)
            presenter.setInformationProvider(provider, contentTypes[i]);

        // sizes: see org.eclipse.jface.text.TextViewer.TEXT_HOVER_*_CHARS
        presenter.setSizeConstraints(100, 12, false, true);
        return presenter;
    }
	/**
     * Sets up the primary CFE Content Assistor. CFE now uses it's own series of
     * content assist code to future proof the content assist process. This should
     * allow developers to extend the CFE code easily and more reliably in the future
     * resulting in fewer hacks and changes to the core code.
	 * @param sourceViewer 
     */
    private void setupPrimaryCFEContentAssist(ISourceViewer sourceViewer) {
        CFEPrimaryAssist mainCFAssistant = new CFEPrimaryAssist(sourceViewer,assistant);
        // we only do the assist partition types here (excludes comment partitions)
        for (int i=0;i<PartitionTypes.ASSIST_PARTITION_TYPES.length;i++) {
            assistant.setContentAssistProcessor(mainCFAssistant,PartitionTypes.ASSIST_PARTITION_TYPES[i]);
        }
    }

//    /**
//	 * Register the text hover
//	 * @author Oliver Tupman
//	 */
//	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType)
//	{
//		//keep the hover only in the parts where it should be
//		//i.e. not in comments ... (we could add a javascript
//		//css specific thing in the future...)
//		if(contentType == CFPartitionScanner.HTM_END_TAG
//		        || contentType == CFPartitionScanner.HTM_START_TAG_BEGIN
//		        || contentType == CFPartitionScanner.HTM_START_TAG_END
//		        || contentType == CFPartitionScanner.HTM_TAG_ATTRIBS)
//		{
//			return new CFTextHover(
//				DictionaryManager.getDictionary(DictionaryManager.HTDIC)
//			);
//		}
//		else if(!(contentType == CFPartitionScanner.HTM_COMMENT))
//		{
//			//load the text hover with the cf dictionary
//			return new CFTextHover(
//				DictionaryManager.getDictionary(DictionaryManager.CFDIC)
//			);
//		}
//		
//		return null;
//	}

	/*
	 * @see SourceViewerConfiguration#getTextHover(ISourceViewer, String, int)
	 */
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType, int stateMask) {
		if (contentType == CFPartitionScanner.HTM_END_TAG || contentType == CFPartitionScanner.HTM_START_TAG_BEGIN
				|| contentType == CFPartitionScanner.HTM_START_TAG_END || contentType == CFPartitionScanner.HTM_TAG_ATTRIBS) {
			return new CFTextHover(DictionaryManager.getDictionary(DictionaryManager.HTDIC));
		}
		CFMLEditorTextHoverDescriptor[] hoverDescs= CFMLPlugin.getDefault().getCFMLEditorTextHoverDescriptors();
		int i= 0;
		while (i < hoverDescs.length) {
			if (hoverDescs[i].isEnabled() &&  hoverDescs[i].getStateMask() == stateMask)
				return new CFMLEditorTextHoverProxy(hoverDescs[i], getEditor());
			i++;
		}
		return null;
	}

	@Override
	public int[] getConfiguredTextHoverStateMasks(ISourceViewer sourceViewer, String contentType) {
		CFMLEditorTextHoverDescriptor[] hoverDescs= CFMLPlugin.getDefault().getCFMLEditorTextHoverDescriptors();
		int stateMasks[]= new int[hoverDescs.length];
		int stateMasksLength= 0;
		for (int i= 0; i < hoverDescs.length; i++) {
			if (hoverDescs[i].isEnabled()) {
				int j= 0;
				int stateMask= hoverDescs[i].getStateMask();
				while (j < stateMasksLength) {
					if (stateMasks[j] == stateMask)
						break;
					j++;
				}
				if (j == stateMasksLength)
					stateMasks[stateMasksLength++]= stateMask;
			}
		}
		if (stateMasksLength == hoverDescs.length)
			return stateMasks;

		int[] shortenedStateMasks= new int[stateMasksLength];
		System.arraycopy(stateMasks, 0, shortenedStateMasks, 0, stateMasksLength);
		return shortenedStateMasks;
	}
	
    /*
    public IUndoManager getUndoManager(ISourceViewer sourceViewer) {
        return this.undoManager;
    }
    */

	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
//      return new CFAnnotationHover(true);
//      return new DefaultAnnotationHover(false);
        return new HTMLAnnotationHover(true);
    }

	/*
	 * @see SourceViewerConfiguration#getTextHover(ISourceViewer, String)
	 */
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
		return getTextHover(sourceViewer, contentType, ITextViewerExtension2.DEFAULT_HOVER_STATE_MASK);
	}	
	
	/*
	 * @see SourceViewerConfiguration#getOverviewRulerAnnotationHover(ISourceViewer)
	 * @since 3.0
	 */
	@Override
	public IAnnotationHover getOverviewRulerAnnotationHover(ISourceViewer sourceViewer) {
		return new HTMLAnnotationHover(true) {
			@Override
			protected boolean isIncluded(Annotation annotation) {
				return isShowInOverviewRuler(annotation);
//				return true;
			}
		};
	}
	
	public IUndoManager getUndoManager(ISourceViewer sourceViewer) {
		IPreferenceStore generalTextStore= EditorsUI.getPreferenceStore();
		return new TextViewerUndoManager( generalTextStore.getInt(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_UNDO_HISTORY_SIZE) );
	}
	
	/**
	 * Returns the autoindent strategy for a give partition type. Currently there are
	 * two separate indent strategies: CFScript & everything else. This is because
	 * the auto-completion & character step-through are now implemented within the
	 * auto-indentors. The auto-indentors differ for the two languages.
	 * r2: just happened upon the fix for this so I just made it non-deprecated, 
	 *  	could probably use some rework now that it can give back an array in 3.1
	 */
	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer, String partitionType) 
	{
		if(partitionType == null) {
			return new CFEIndentStrategy[] { indentTagStrategy };
		}
		if (partitionType.compareTo(CFPartitionScanner.CF_SCRIPT) == 0
				|| partitionType.compareTo(CFPartitionScanner.CF_SCRIPT_COMMENT_BLOCK) == 0
				|| partitionType.compareTo(CFPartitionScanner.CF_SCRIPT_COMMENT) == 0
				|| partitionType.compareTo(CFPartitionScanner.JAVADOC_COMMENT) == 0)
		{
			return new CFEIndentStrategy[] { indentCFScriptStrategy };
		} else if(partitionType.compareTo(CFPartitionScanner.J_SCRIPT) == 0) 
		{
			return new CFEIndentStrategy[] { indentCFScriptStrategy };
		}
		return new CFEIndentStrategy[] { indentTagStrategy };
	}
	
	// This method gets called when the preference page is saved.
	public void propertyChange(PropertyChangeEvent event)
    {
// System.out.println("CFConfiguration property change listener notified." + event.getProperty());
		String prop = event.getProperty(); 
		Object newValue = event.getNewValue();
		
		boolean setting = false;
		Object settingObj = null;
		
		if(newValue instanceof Boolean){
			setting = ((Boolean)newValue).booleanValue();
		}
		else {
			settingObj = newValue;
			CFMLPlugin.logError("Unhandled setting type: " + settingObj.getClass().getName());
		}
    //	boolean setting = ((Boolean)event.getNewValue()).booleanValue();
	//	System.out.println("The Property we are setting is: " + prop + " [" + setting + "]");
    	
		if(prop.equals(EditorPreferenceConstants.P_INSIGHT_DELAY)) {
			int delay = preferenceManager.insightDelay();
			assistant.enableAutoActivation(true);			
			assistant.setAutoActivationDelay(delay);
   			//System.err.println("Insight delay set to " + delay);
        }
        else if(prop.equals(EditorPreferenceConstants.P_INSERT_SPACES_FOR_TABS) || prop.equals(EditorPreferenceConstants.P_TAB_WIDTH)) {
    		tabWidth = preferenceManager.tabWidth();
    		boolean tabsAsSpaces = preferenceManager.insertSpacesForTabs();    		
        	indentCFScriptStrategy.setIndentString(tabWidth,tabsAsSpaces);
        	indentTagStrategy.setIndentString(tabWidth, tabsAsSpaces);
        }
        else if(prop.equals(AutoIndentPreferenceConstants.P_AUTOCLOSE_DOUBLE_QUOTES)) {
        	indentCFScriptStrategy.setAutoClose_DoubleQuotes(setting);
        	indentTagStrategy.setAutoClose_DoubleQuotes(setting);
        }
        else if(prop.equals(AutoIndentPreferenceConstants.P_AUTOCLOSE_SINGLE_QUOTES)) {
        	indentCFScriptStrategy.setAutoClose_SingleQuotes(setting);
        	indentTagStrategy.setAutoClose_SingleQuotes(setting);
        }
        else if(prop.equals(AutoIndentPreferenceConstants.P_AUTOCLOSE_HASHES)) {
        	indentCFScriptStrategy.setAutoClose_Hashes(setting);
        	indentTagStrategy.setAutoClose_Hashes(setting);
        }
        else if(prop.equals(AutoIndentPreferenceConstants.P_AUTOCLOSE_BRACKETS)) {
        	indentCFScriptStrategy.setAutoClose_Brackets(setting);
    		indentTagStrategy.setAutoClose_Brackets(setting);
        }
        else if(prop.equals(AutoIndentPreferenceConstants.P_AUTOCLOSE_BRACES)) {
        	indentCFScriptStrategy.setAutoClose_Braces(setting);
        }
        else if(prop.equals(AutoIndentPreferenceConstants.P_AUTOCLOSE_PARENS)) {
        	indentCFScriptStrategy.setAutoClose_Parens(setting);
    		indentTagStrategy.setAutoClose_Parens(setting);
        }
        else if(prop.equals(AutoIndentPreferenceConstants.P_AUTOCLOSE_TAGS)) {
        	indentTagStrategy.setAutoClose_Tags(setting);
        }
        else if(prop.equals(AutoIndentPreferenceConstants.P_AUTOINSERT_CLOSE_TAGS)) {
    		indentTagStrategy.setAutoInsert_CloseTags(setting);
        }
        else if(prop.equals(AutoIndentPreferenceConstants.P_USE_SMART_COMMENTS)) {
			indentCFScriptStrategy.setUseSmartComments(setting);
        }
        else if(prop.equals(AutoIndentPreferenceConstants.P_USE_SMART_INDENT)) {
        	indentCFScriptStrategy.setUseSmartIndent(setting);
    		indentTagStrategy.setUseSmartIndent(setting);
        }
        else if(prop.equals(AutoIndentPreferenceConstants.P_AUTOINDENT_ONTAGCLOSE)) {
        	int indentValue = setting ? TagIndentStrategy.INDENT_ONTAGCLOSE : TagIndentStrategy.INDENT_DONTDOIT;
        	indentTagStrategy.setAutoIndent_OnTagClose(indentValue);
        }
        else if(prop.equals(AutoIndentPreferenceConstants.P_USE_SMART_PASTE)) {
    		indentTagStrategy.setUseSmartPaste(setting);
        }
        else if(prop.equals(EditorPreferenceConstants.P_TAB_INDENTS_CURRENT_LINE)) {
        	indentTagStrategy.setTabIndentSingleLine(setting);
        	indentCFScriptStrategy.setTabIndentSingleLine(setting);
        }
        else if(prop.equals(ParserPreferenceConstants.P_PARSE_REPORT_ERRORS)) {
        	//boolean reportErrors = ((Boolean)event.getNewValue()).booleanValue();
        	try {
        		CFMLPlugin.getWorkspace().getRoot().deleteMarkers(null, true, IResource.DEPTH_INFINITE);
        	}catch(CoreException ex) {
        		ex.printStackTrace();
        	}
        }
    }
}
