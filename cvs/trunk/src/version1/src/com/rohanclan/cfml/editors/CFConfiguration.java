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
package com.rohanclan.cfml.editors;


import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.DefaultUndoManager;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.IAutoIndentStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Shell;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.cfscript.CFScriptScanner;
import com.rohanclan.cfml.editors.style.StyleScanner;
import com.rohanclan.cfml.editors.script.ScriptScanner;
import com.rohanclan.cfml.editors.CFTextHover;
import com.rohanclan.cfml.editors.cfscript.CFScriptCompletionProcessor;
import com.rohanclan.cfml.editors.contentassist.CFEPrimaryAssist;
import com.rohanclan.cfml.editors.indentstrategies.CFScriptIndentStrategy;
import com.rohanclan.cfml.editors.indentstrategies.TagIndentStrategy;
import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.preferences.CFMLPreferenceManager;
import com.rohanclan.cfml.preferences.ICFMLPreferenceConstants;

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
public class CFConfiguration extends SourceViewerConfiguration implements IPropertyChangeListener {
	
	private CFDoubleClickStrategy doubleClickStrategy;
	private ColorManager colorManager;
	private ContentAssistant assistant;
	private TagIndentStrategy indentTagStrategy;
	private CFScriptIndentStrategy indentCFScriptStrategy;
	private CFMLPreferenceManager preferenceManager;
	private int tabWidth;
	private CFMLEditor editor;
	private CFEUndoManager undoManager;
	
	/**
	 * Need a color manager to get partition colors
	 * @param colorManager that would be the color manager
	 */
	private void configTagIndentStrat() {
		indentTagStrategy.setIndentString(tabWidth, preferenceManager.insertSpacesForTabs());
		indentTagStrategy.setDreamweaverCompatibility(preferenceManager.dreamweaverCompatibility());
		indentTagStrategy.setHomesiteCompatibility(preferenceManager.homesiteCompatibility());
		indentTagStrategy.setAutoClose_DoubleQuotes(preferenceManager.getBooleanPref(ICFMLPreferenceConstants.P_AUTOCLOSE_DOUBLE_QUOTES));
		indentTagStrategy.setAutoClose_SingleQuotes(preferenceManager.getBooleanPref(ICFMLPreferenceConstants.P_AUTOCLOSE_SINGLE_QUOTES));
		indentTagStrategy.setAutoClose_Hashes(preferenceManager.getBooleanPref(ICFMLPreferenceConstants.P_AUTOCLOSE_HASHES));
		indentTagStrategy.setAutoClose_Tags(preferenceManager.getBooleanPref(ICFMLPreferenceConstants.P_AUTOCLOSE_TAGS));
		indentTagStrategy.setAutoInsert_CloseTags(preferenceManager.getBooleanPref(ICFMLPreferenceConstants.P_AUTOINSERT_CLOSE_TAGS));
		//indentTagStrategy.setAutoClose_DoubleQuotes(preferenceManager.getBooleanPref(ICFMLPreferenceConstants.P_AUTOCLOSE_DOUBLE_QUOTES));
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
		this.undoManager = new CFEUndoManager(preferenceManager.maxUndoSteps());
		indentCFScriptStrategy = new CFScriptIndentStrategy(editor);
		this.indentTagStrategy = new TagIndentStrategy(editor);
		
		tabWidth = preferenceManager.tabWidth();
		boolean insertSpacesForTabs = preferenceManager.insertSpacesForTabs();
		indentCFScriptStrategy.setIndentString(tabWidth,insertSpacesForTabs);
		indentCFScriptStrategy.setDreamweaverCompatibility(preferenceManager.dreamweaverCompatibility());
		indentCFScriptStrategy.setHomesiteCompatibility(preferenceManager.homesiteCompatibility());
		configTagIndentStrat();
		// This ensures that we are notified when the preferences are saved
		CFMLPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(this);
	}
	
	public int getTabWidth(ISourceViewer sourceViewer) {
		return tabWidth;
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
	
	public IUndoManager getUndoManager(ISourceViewer sourceViewer) {
		return this.undoManager;
	}
	
	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
	    return new CFAnnotationHover();
	}
	
	
	/**
	 * This defines what sections (partitions) are valid for the document
	 */
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) 
	{	
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			CFPartitionScanner.CF_COMMENT,
			CFPartitionScanner.DOCTYPE,
			CFPartitionScanner.HTM_COMMENT,
			CFPartitionScanner.ALL_TAG,
			CFPartitionScanner.CF_TAG,
			CFPartitionScanner.CF_END_TAG,
			CFPartitionScanner.CF_SCRIPT,
			CFPartitionScanner.J_SCRIPT,
			CFPartitionScanner.CSS_TAG,
			CFPartitionScanner.UNK_TAG,
			CFPartitionScanner.FORM_TAG,
			CFPartitionScanner.TABLE_TAG
		};
	}
	
	/**
	 * get the double click strat-m-gee for the view & content type = (section, 
	 * partition)
	 */
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer,
		String contentType) 
	{
		if (doubleClickStrategy == null)
			doubleClickStrategy = new CFDoubleClickStrategy();
		
		return doubleClickStrategy;
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
						CFMLPreferenceManager.P_COLOR_DEFAULT_TEXT
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
						CFMLPreferenceManager.P_COLOR_HTM_TAG
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
						CFMLPreferenceManager.P_COLOR_UNK_TAG
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
						CFMLPreferenceManager.P_COLOR_CFTAG
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
						CFMLPreferenceManager.P_COLOR_CFSCRIPT_TEXT
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
	protected StyleScanner getStyleScanner() 
	{
		Token textToken = new Token(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
						CFMLPreferenceManager.P_COLOR_CSS
					)
				)
			)
		);
		StyleScanner stylescanner = new StyleScanner(colorManager, preferenceManager);
		stylescanner.setDefaultReturnToken(textToken);
		return stylescanner;
	}
	
	/**
	 * gets the script scanner (handles highlighting for the script tag)
	 * partitions
	 * @return
	 */
	protected ScriptScanner getScriptScanner() 
	{
		Token textToken = new Token(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
						CFMLPreferenceManager.P_COLOR_DEFAULT_TEXT
					)
				)
			)
		);
		ScriptScanner scriptscanner = new ScriptScanner(colorManager, preferenceManager);
		scriptscanner.setDefaultReturnToken(textToken);
		return scriptscanner;
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
						CFMLPreferenceManager.P_COLOR_HTM_FORM_TAG
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
						CFMLPreferenceManager.P_COLOR_HTM_TABLE_TAG
					)
				)
			)
		);
		HTMTagScanner tablescanner = new HTMTagScanner(colorManager, preferenceManager);
		tablescanner.setDefaultReturnToken(textToken);
		return tablescanner;
	}
	
	
	///////////////////////// SCANNERS /////////////////////////////////////////////
	
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
		reconciler.setDamager(dr, CFPartitionScanner.ALL_TAG);
		reconciler.setRepairer(dr, CFPartitionScanner.ALL_TAG);
		
		//javascript tag
		dr = new DefaultDamagerRepairer(getScriptScanner());
		reconciler.setDamager(dr, CFPartitionScanner.J_SCRIPT);
		reconciler.setRepairer(dr, CFPartitionScanner.J_SCRIPT);
		
		//style tag
		dr = new DefaultDamagerRepairer(getStyleScanner());
		reconciler.setDamager(dr, CFPartitionScanner.CSS_TAG);
		reconciler.setRepairer(dr, CFPartitionScanner.CSS_TAG);
		
		//CF script (if this is put before the cfscript stuff
		//you'll get jacked up keyword highlighting
		dr = new DefaultDamagerRepairer(getCFScriptScanner());
		reconciler.setDamager(dr, CFPartitionScanner.CF_SCRIPT);
		reconciler.setRepairer(dr, CFPartitionScanner.CF_SCRIPT);
		
		//general CF
		dr = new DefaultDamagerRepairer(getCFTagScanner());
		reconciler.setDamager(dr, CFPartitionScanner.CF_TAG);
		reconciler.setRepairer(dr, CFPartitionScanner.CF_TAG);
		
		//general end cftag
		//dr = new DefaultDamagerRepairer(getCFTagScanner());
		reconciler.setDamager(dr, CFPartitionScanner.CF_END_TAG);
		reconciler.setRepairer(dr, CFPartitionScanner.CF_END_TAG);
		
		dr = new DefaultDamagerRepairer(getFormScanner());
		reconciler.setDamager(dr, CFPartitionScanner.FORM_TAG);
		reconciler.setRepairer(dr, CFPartitionScanner.FORM_TAG);
		
		dr = new DefaultDamagerRepairer(getTableScanner());
		reconciler.setDamager(dr, CFPartitionScanner.TABLE_TAG);
		reconciler.setRepairer(dr, CFPartitionScanner.TABLE_TAG);
		
		//.... the default text in the document
		dr = new DefaultDamagerRepairer(getTextScanner());
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
						CFMLPreferenceManager.P_COLOR_CFCOMMENT
					)
				)
			)
		);
		reconciler.setDamager(ndr, CFPartitionScanner.CF_COMMENT);
		reconciler.setRepairer(ndr, CFPartitionScanner.CF_COMMENT);
		
		//set up the html comment section
		NonRuleBasedDamagerRepairer ndr2 = new NonRuleBasedDamagerRepairer(
			new TextAttribute(
				colorManager.getColor(
					preferenceManager.getColor(
						CFMLPreferenceManager.P_COLOR_HTM_COMMENT
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
						CFMLPreferenceManager.P_COLOR_HTM_COMMENT
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
		
		setupPrimaryCFEContentAssist();

		assistant.setContentAssistProcessor(new CFScriptCompletionProcessor(),CFPartitionScanner.CF_SCRIPT);
		
		//in javascript tags - try to give js its own type of completion using the
		//cfscript processor but using the js dictionary...
		/*
		CFScriptCompletionProcessor cfscp = new CFScriptCompletionProcessor();
		cfscp.changeDictionary(DictionaryManager.JSDIC);
		assistant.setContentAssistProcessor(cfscp,	CFPartitionScanner.J_SCRIPT);
		*/
		
		
		IPreferenceStore store = CFMLPlugin.getDefault().getPreferenceStore();
		
		int delay = preferenceManager.insightDelay();
		
		assistant.enableAutoActivation(true);
		assistant.setAutoActivationDelay(delay);
		
		assistant.setProposalPopupOrientation(
			IContentAssistant.PROPOSAL_OVERLAY
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
     */
    private void setupPrimaryCFEContentAssist() {
        CFEPrimaryAssist mainCFAssistant = new CFEPrimaryAssist();
		assistant.setContentAssistProcessor(mainCFAssistant, CFPartitionScanner.CF_TAG);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.ALL_TAG);	//inside any other tags
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.UNK_TAG);	//unknown tags
		assistant.setContentAssistProcessor(mainCFAssistant,IDocument.DEFAULT_CONTENT_TYPE);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.FORM_TAG);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.TABLE_TAG);
		assistant.setContentAssistProcessor(mainCFAssistant,CFPartitionScanner.J_SCRIPT);
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
		if(contentType == CFPartitionScanner.ALL_TAG)
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
	
	/**
	 * Returns the autoindent strategy for a give partition type. Currently there are
	 * two separate indent strategies: CFScript & everything else. This is because
	 * the auto-completion & character step-through are now implemented within the
	 * auto-indentors. The auto-indentors differ for the two languages.
	 */
	public IAutoIndentStrategy getAutoIndentStrategy(ISourceViewer arg0, String partitionType) 
	{
		if(partitionType.compareTo(CFPartitionScanner.CF_SCRIPT) == 0) {
			return indentCFScriptStrategy;
		} else if(partitionType.compareTo(CFPartitionScanner.J_SCRIPT) == 0) {
			return indentCFScriptStrategy;
		}
        return indentTagStrategy;
    }

	// This method gets called when the preference page is saved.
	public void propertyChange(PropertyChangeEvent event)
    {
// System.out.println("CFConfiguration property change listener notified." + event.getProperty());
		String prop = event.getProperty(); 
    	
		if(prop.equals("insightDelay")) {
			int delay = preferenceManager.insightDelay();
			assistant.enableAutoActivation(true);			
			assistant.setAutoActivationDelay(delay);
    			//System.err.println("Insight delay set to " + delay);
        }
        else if(prop.equals("tabsAsSpaces") || prop.equals("tabWidth")) {
	    		tabWidth = preferenceManager.tabWidth();
	    		boolean tabsAsSpaces = preferenceManager.insertSpacesForTabs();    		
	        	indentCFScriptStrategy.setIndentString(tabWidth,tabsAsSpaces);
	        	indentTagStrategy.setIndentString(tabWidth, tabsAsSpaces);
        }
        else if(prop.equals("dreamweaverCompatibility")) {
        		indentCFScriptStrategy.setDreamweaverCompatibility(preferenceManager.dreamweaverCompatibility());
        }
        else if(prop.equals("homesiteCompatibility")) {
        		indentCFScriptStrategy.setHomesiteCompatibility(preferenceManager.homesiteCompatibility());
        }
        else if(prop.equals(ICFMLPreferenceConstants.P_AUTOCLOSE_DOUBLE_QUOTES)) {
        		indentTagStrategy.setAutoClose_DoubleQuotes(((Boolean)event.getNewValue()).booleanValue());
        }
        else if(prop.equals(ICFMLPreferenceConstants.P_AUTOCLOSE_SINGLE_QUOTES)) {
        		indentTagStrategy.setAutoClose_SingleQuotes(((Boolean)event.getNewValue()).booleanValue());
        }
        else if(prop.equals(ICFMLPreferenceConstants.P_AUTOCLOSE_HASHES)) {
        		indentTagStrategy.setAutoClose_Hashes(((Boolean)event.getNewValue()).booleanValue());
        }
        else if(prop.equals(ICFMLPreferenceConstants.P_AUTOCLOSE_TAGS)) {
        		indentTagStrategy.setAutoClose_Tags(((Boolean)event.getNewValue()).booleanValue());
        }
        else if(prop.equals(ICFMLPreferenceConstants.P_AUTOINSERT_CLOSE_TAGS)) {
        		indentTagStrategy.setAutoInsert_CloseTags(((Boolean)event.getNewValue()).booleanValue());
        }
        else if(prop.equals(ICFMLPreferenceConstants.P_AUTOINDENT_ONTAGCLOSE)) {
        	int indentValue = ((Boolean)event.getNewValue()).booleanValue() ? TagIndentStrategy.INDENT_ONTAGCLOSE : TagIndentStrategy.INDENT_DONTDOIT;
        	indentTagStrategy.setAutoIndent_OnTagClose(indentValue);
        }
        else if(prop.equals(ICFMLPreferenceConstants.P_PARSE_REPORT_ERRORS)) {
	        	boolean reportErrors = ((Boolean)event.getNewValue()).booleanValue();
	        	try {
	        		CFMLPlugin.getWorkspace().getRoot().deleteMarkers(null, true, IResource.DEPTH_INFINITE);
	        	}catch(CoreException ex) {
	        		ex.printStackTrace();
	        	}
        }
    }
}
