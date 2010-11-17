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


import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.editors.contentassist.CFEPrimaryAssist;
import org.cfeclipse.cfml.editors.formatters.CFMLFormattingStrategy;
import org.cfeclipse.cfml.editors.formatters.FormattingPreferences;
import org.cfeclipse.cfml.editors.formatters.SQLWordStrategy;
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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistantExtension2;
import org.eclipse.jface.text.contentassist.IContentAssistantExtension3;
import org.eclipse.jface.text.formatter.ContentFormatter;
import org.eclipse.jface.text.formatter.FormattingContext;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.IFormattingContext;
import org.eclipse.jface.text.formatter.IFormattingStrategy;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
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
	//private CFMLEditor editor;
	private CFMLEditor editor;
	
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
	 * Returns the prefixes to be used by the line-shift operation. This implementation
	 * always returns <code>new String[] { "\t", " " }</code>.
	 *
	 * @param sourceViewer the source viewer to be configured by this configuration
	 * @param contentType the content type for which the prefix is applicable
	 * @return the prefixes or <code>null</code> if the prefix operation should not be supported
	 */
	public String[] getIndentPrefixes(ISourceViewer sourceViewer, String contentType) {
		return new String[] { "\t", " ", "" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	/*
	public IUndoManager getUndoManager(ISourceViewer sourceViewer) {
		return this.undoManager;
	}
	*/
	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
	    return new CFAnnotationHover();
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
	
		TextScanner scanner = new TextScanner(colorManager);
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
	 * get all the damager and repairers for the source type
	 */
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) 
	{
		PresentationReconciler reconciler = new PresentationReconciler();

		//setup the partiton scanner to break and fix each part of the
		//document
		//
		// WARNING order is important here - the document will be painted
		// with the rules in this order - it seems anyway
		
		//HTML part
		DefaultDamagerRepairer dr =	new DefaultDamagerRepairer(getHTMTagScanner());
		reconciler.setDamager(dr, CFPartitionScanner.HTM_END_TAG);
		reconciler.setRepairer(dr, CFPartitionScanner.HTM_END_TAG);
		
		reconciler.setDamager(dr, CFPartitionScanner.HTM_START_TAG_BEGIN);
		reconciler.setRepairer(dr, CFPartitionScanner.HTM_START_TAG_BEGIN);
		
		reconciler.setDamager(dr, CFPartitionScanner.HTM_START_TAG_END);
		reconciler.setRepairer(dr, CFPartitionScanner.HTM_START_TAG_END);
		
		reconciler.setDamager(dr, CFPartitionScanner.HTM_TAG_ATTRIBS);
		reconciler.setRepairer(dr, CFPartitionScanner.HTM_TAG_ATTRIBS);
		
		//javascript tag
		dr = new DefaultDamagerRepairer(getScriptScanner());
		reconciler.setDamager(dr, CFPartitionScanner.J_SCRIPT);
		reconciler.setRepairer(dr, CFPartitionScanner.J_SCRIPT);
		
		//style tag
		dr = new DefaultDamagerRepairer(getStyleScanner());
		reconciler.setDamager(dr, CFPartitionScanner.CSS);
		reconciler.setRepairer(dr, CFPartitionScanner.CSS);
		dr = new DefaultDamagerRepairer(getTaglibTagScanner());
		reconciler.setDamager(dr, CFPartitionScanner.TAGLIB_TAG);
		reconciler.setRepairer(dr, CFPartitionScanner.TAGLIB_TAG);
		
		//SQL
		dr = new DefaultDamagerRepairer(getSQLScanner());
		reconciler.setDamager(dr, CFPartitionScanner.SQL);
		reconciler.setRepairer(dr, CFPartitionScanner.SQL);
		
		//CF script (if this is put before the cfscript stuff
		//you'll get jacked up keyword highlighting
		dr = new DefaultDamagerRepairer(getCFScriptScanner());
		
		reconciler.setDamager(dr, CFPartitionScanner.CF_SCRIPT);
		reconciler.setRepairer(dr, CFPartitionScanner.CF_SCRIPT);
		
		// cfset tag contents.
		reconciler.setDamager(dr, CFPartitionScanner.CF_SET_STATEMENT);
		reconciler.setRepairer(dr, CFPartitionScanner.CF_SET_STATEMENT);
		
		// cfif and cfelseif tag contents.
		reconciler.setDamager(dr, CFPartitionScanner.CF_BOOLEAN_STATEMENT);
		reconciler.setRepairer(dr, CFPartitionScanner.CF_BOOLEAN_STATEMENT);
		
		// cfreturn tag contents.
		reconciler.setDamager(dr, CFPartitionScanner.CF_RETURN_STATEMENT);
		reconciler.setRepairer(dr, CFPartitionScanner.CF_RETURN_STATEMENT);

		//general CF
		dr = new DefaultDamagerRepairer(getCFTagScanner());
		reconciler.setDamager(dr, CFPartitionScanner.CF_START_TAG_BEGIN);
		reconciler.setRepairer(dr, CFPartitionScanner.CF_START_TAG_BEGIN);

		reconciler.setDamager(dr, CFPartitionScanner.CF_START_TAG_END);
		reconciler.setRepairer(dr, CFPartitionScanner.CF_START_TAG_END);
		
		reconciler.setDamager(dr, CFPartitionScanner.CF_TAG_ATTRIBS);
		reconciler.setRepairer(dr, CFPartitionScanner.CF_TAG_ATTRIBS);
		
		//general end cftag
		//dr = new DefaultDamagerRepairer(getCFTagScanner());
		reconciler.setDamager(dr, CFPartitionScanner.CF_END_TAG);
		reconciler.setRepairer(dr, CFPartitionScanner.CF_END_TAG);
		
		dr = new DefaultDamagerRepairer(getFormScanner());
		reconciler.setDamager(dr, CFPartitionScanner.FORM_END_TAG);
		reconciler.setRepairer(dr, CFPartitionScanner.FORM_END_TAG);
		
		reconciler.setDamager(dr, CFPartitionScanner.FORM_START_TAG_BEGIN);
		reconciler.setRepairer(dr, CFPartitionScanner.FORM_START_TAG_BEGIN);
		
		reconciler.setDamager(dr, CFPartitionScanner.FORM_START_TAG_END);
		reconciler.setRepairer(dr, CFPartitionScanner.FORM_START_TAG_END);
		
		reconciler.setDamager(dr, CFPartitionScanner.FORM_TAG_ATTRIBS);
		reconciler.setRepairer(dr, CFPartitionScanner.FORM_TAG_ATTRIBS);
		
		dr = new DefaultDamagerRepairer(getTableScanner());
		reconciler.setDamager(dr, CFPartitionScanner.TABLE_END_TAG);
		reconciler.setRepairer(dr, CFPartitionScanner.TABLE_END_TAG);
		
		reconciler.setDamager(dr, CFPartitionScanner.TABLE_START_TAG_BEGIN);
		reconciler.setRepairer(dr, CFPartitionScanner.TABLE_START_TAG_BEGIN);
		
		reconciler.setDamager(dr, CFPartitionScanner.TABLE_START_TAG_END);
		reconciler.setRepairer(dr, CFPartitionScanner.TABLE_START_TAG_END);
		
		reconciler.setDamager(dr, CFPartitionScanner.TABLE_TAG_ATTRIBS);
		reconciler.setRepairer(dr, CFPartitionScanner.TABLE_TAG_ATTRIBS);
		
		//.... the default text in the document
		dr = new DefaultDamagerRepairer(getCFScriptScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		//unknown tags
		dr = new DefaultDamagerRepairer(getUNKTagScanner());
		reconciler.setDamager(dr, CFPartitionScanner.UNK_TAG);
		reconciler.setRepairer(dr, CFPartitionScanner.UNK_TAG);
		
		//set up the cf comment section
		NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
							CFMLColorsPreferenceConstants.P_COLOR_CFCOMMENT
					)
				),
				colorManager.getColor(
					preferenceManager.getColor(
							CFMLColorsPreferenceConstants.P_COLOR_BACKGROUND_CFCOMMENT
					)
				), tabWidth
			)
		);
		reconciler.setDamager(ndr, CFPartitionScanner.CF_COMMENT);
		reconciler.setRepairer(ndr, CFPartitionScanner.CF_COMMENT);
		
		//set up the html comment section
		NonRuleBasedDamagerRepairer ndr2 = new NonRuleBasedDamagerRepairer(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
						HTMLColorsPreferenceConstants.P_COLOR_HTM_COMMENT
					)
				)
			)
		);
		reconciler.setDamager(ndr2, CFPartitionScanner.HTM_COMMENT);
		reconciler.setRepairer(ndr2, CFPartitionScanner.HTM_COMMENT);
		
		//set up the doctype section
		NonRuleBasedDamagerRepairer ndr3 = new NonRuleBasedDamagerRepairer(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
						HTMLColorsPreferenceConstants.P_COLOR_HTM_COMMENT
					)
				)
			)
		);
		reconciler.setDamager(ndr3, CFPartitionScanner.DOCTYPE);
		reconciler.setRepairer(ndr3, CFPartitionScanner.DOCTYPE);
		
		return reconciler;
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
		CFScriptCompletionProcessor cfscp = new CFScriptCompletionProcessor();
		cfscp.changeDictionary(DictionaryManager.JSDIC);
		assistant.setContentAssistProcessor(cfscp,	CFPartitionScanner.J_SCRIPT);
		*/
		
		
		//IPreferenceStore store = CFMLPlugin.getDefault().getPreferenceStore();
		
		int delay = preferenceManager.insightDelay();
		
		assistant.enableAutoActivation(true);
		assistant.setAutoActivationDelay(delay);
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
		// automatically insert if only one suggestion
		assistant.enableAutoInsert(false);
		
		
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
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				return new DefaultInformationControl(parent,new InformationPresenter());
			}
		};
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
        /*
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.CF_START_TAG_END);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.CF_TAG_ATTRIBS);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.HTM_END_TAG);	//inside any other tags
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.HTM_START_TAG_BEGIN);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.HTM_START_TAG_END);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.HTM_TAG_ATTRIBS);	
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.UNK_TAG);	//unknown tags
		assistant.setContentAssistProcessor(mainCFAssistant,IDocument.DEFAULT_CONTENT_TYPE);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.FORM_END_TAG);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.FORM_START_TAG_BEGIN);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.FORM_START_TAG_END);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.FORM_TAG_ATTRIBS);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.TABLE_END_TAG);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.TABLE_START_TAG_BEGIN);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.TABLE_START_TAG_END);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.FORM_TAG_ATTRIBS);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.J_SCRIPT);
		*/
    }

    /**
	 * Register the text hover
	 * @author Oliver Tupman
	 */
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType)
	{
		//keep the hover only in the parts where it should be
		//i.e. not in comments ... (we could add a javascript
		//css specific thing in the future...)
		if(contentType == CFPartitionScanner.HTM_END_TAG
		        || contentType == CFPartitionScanner.HTM_START_TAG_BEGIN
		        || contentType == CFPartitionScanner.HTM_START_TAG_END
		        || contentType == CFPartitionScanner.HTM_TAG_ATTRIBS)
		{
			return new CFTextHover(
				DictionaryManager.getDictionary(DictionaryManager.HTDIC)
			);
		}
		else if(!(contentType == CFPartitionScanner.HTM_COMMENT))
		{
			//load the text hover with the cf dictionary
			return new CFTextHover(
				DictionaryManager.getDictionary(DictionaryManager.CFDIC)
			);
		}
		
		return null;
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
		if(partitionType.compareTo(CFPartitionScanner.CF_SCRIPT) == 0) 
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
