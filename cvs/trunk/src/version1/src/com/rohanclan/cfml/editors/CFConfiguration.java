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

/**
 * @author Rob
 * 
 * This sets up the whole editor. Assigin partition damagers and repairers, and
 * assign insight to partitions - bunch of other stuff too.
 */
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
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

import com.rohanclan.cfml.editors.cfscript.CFScriptScanner;
import com.rohanclan.cfml.editors.style.StyleScanner;
import com.rohanclan.cfml.editors.script.ScriptScanner;
import com.rohanclan.cfml.editors.CFTextHover;
import org.eclipse.jface.text.ITextHover;
import com.rohanclan.cfml.dictionary.DictionaryManager;

import com.rohanclan.cfml.editors.cfscript.CFScriptCompletionProcessor;
import com.rohanclan.cfml.editors.script.JSCompletionProcessor;

public class CFConfiguration extends SourceViewerConfiguration {
	private CFDoubleClickStrategy doubleClickStrategy;
	/** the default html tag scanner */
	private HTMTagScanner htmtagScanner;
	/** the default html tag scanner */
	private HTMTagScanner unktagScanner;
	/** the cold fusion tag scanner */
	private CFTagScanner cftagScanner;
	/** plain text scanner (nodes values)*/
	private TextScanner scanner;
	/** cfscript block scanner */
	private CFScriptScanner cfscriptscanner;
	/** style block scanner */
	private StyleScanner stylescanner;
	/** script (as in javascript) block scanner */
	private ScriptScanner scriptscanner;
	
	private ColorManager colorManager;

	/**
	 * Need a color manager to get partition colors
	 * @param colorManager that would be the color manager
	 */
	public CFConfiguration(ColorManager colorManager) 
	{
		this.colorManager = colorManager;
	}
	
	/**
	 * This defines what sections (partitions) are valid for the document
	 * (I think)
	 */
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) 
	{	
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			CFPartitionScanner.HTM_COMMENT,
			CFPartitionScanner.ALL_TAG,
			CFPartitionScanner.CF_TAG,
			CFPartitionScanner.CF_END_TAG,
			CFPartitionScanner.CF_SCRIPT,
			CFPartitionScanner.J_SCRIPT,
			CFPartitionScanner.CSS_TAG,
			CFPartitionScanner.UNK_TAG
		};
	}
	
	/**
	 * get the double click strat-m-gee for the view & content type = (section, partition)
	 */
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer,
		String contentType) 
	{
		if (doubleClickStrategy == null)
			doubleClickStrategy = new CFDoubleClickStrategy();
		
		return doubleClickStrategy;
	}

	/**
	 * get the scanner for the document and set the default return color
	 * type i.e. the plain text on the document (not a tag)
	 * partitons
	 * @return
	 */
	protected TextScanner getTextScanner() 
	{
		if (scanner == null) {
			scanner = new TextScanner(colorManager);
			scanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(ICFColorConstants.DEFAULT)
					)
				)
			);
		}
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
		if (htmtagScanner == null) 
		{
			htmtagScanner = new HTMTagScanner(colorManager);
			htmtagScanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(ICFColorConstants.TAG)
					)
				)
			);
		}
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
		if (unktagScanner == null) 
		{
			unktagScanner = new HTMTagScanner(colorManager);
			unktagScanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(ICFColorConstants.UNK_TAG)
					)
				)
			);
		}
		return unktagScanner;
	}
	
	/**
	 * gets the cfml tag scanner (handles highlighting cf tags)
	 * partitons
	 * @return
	 */
	protected CFTagScanner getCFTagScanner() 
	{
		if (cftagScanner == null) 
		{
			cftagScanner = new CFTagScanner(colorManager);
			cftagScanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(ICFColorConstants.CFTAG)
					)
				)
			);
		}
		return cftagScanner;
	}
	
	/**
	 * gets the cfscript scanner (handles highlighting for cfscript
	 * partitons
	 * @return
	 */
	protected CFScriptScanner getCFScriptScanner() 
	{
		if(cfscriptscanner == null) 
		{
			cfscriptscanner = new CFScriptScanner(colorManager);
			cfscriptscanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(ICFColorConstants.CFSCRIPT)
					)
				)
			);
		}
		return cfscriptscanner;
	}
	
	/**
	 * gets the style scanner (handles highlighting for the style tag)
	 * partitions
	 * @return
	 */
	protected StyleScanner getStyleScanner() 
	{
		if(stylescanner == null) 
		{
			stylescanner = new StyleScanner(colorManager);
			stylescanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(ICFColorConstants.CSS)
					)
				)
			);
		}
		return stylescanner;
	}
	
	/**
	 * gets the script scanner (handles highlighting for the script tag)
	 * partitions
	 * @return
	 */
	protected ScriptScanner getScriptScanner() 
	{
		if(scriptscanner == null) 
		{
			scriptscanner = new ScriptScanner(colorManager);
			scriptscanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(ICFColorConstants.DEFAULT)
					)
				)
			);
		}
		
		return scriptscanner;
	}
	
	/**
	 * get all the damager and repairers for the source type
	 */
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
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
		
		
		//.... the default text in the document
		dr = new DefaultDamagerRepairer(getTextScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		//unknown tags
		dr = new DefaultDamagerRepairer(getUNKTagScanner());
		reconciler.setDamager(dr, CFPartitionScanner.UNK_TAG);
		reconciler.setRepairer(dr, CFPartitionScanner.UNK_TAG);
		
		//set up the comment section
		NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(
			new TextAttribute(
				colorManager.getColor(ICFColorConstants.HTM_COMMENT)
			)
		);
		reconciler.setDamager(ndr, CFPartitionScanner.HTM_COMMENT);
		reconciler.setRepairer(ndr, CFPartitionScanner.HTM_COMMENT);
		
		return reconciler;
	}
	
	/**
	 * Define code insight stuff (mostly assign it to different sections)
	 */
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		//make our assistant and processor
		ContentAssistant assistant = new ContentAssistant();
		CFCompletionProcessor cfcp = new CFCompletionProcessor();
	
		//assign to the needed partitions
		
		//for normal text
		assistant.setContentAssistProcessor(
			cfcp,
			IDocument.DEFAULT_CONTENT_TYPE
		); 
		//inside cftags
		assistant.setContentAssistProcessor(
			cfcp,
			CFPartitionScanner.CF_TAG
		);
		//inside any other tags
		assistant.setContentAssistProcessor(
			cfcp,
			CFPartitionScanner.ALL_TAG
		);
		
		//unknown tags
		assistant.setContentAssistProcessor(
			cfcp,
			CFPartitionScanner.UNK_TAG
		);
		
		//inside cfscript tags
		//new processor by oliver :)
		assistant.setContentAssistProcessor(
			new CFScriptCompletionProcessor(),
			CFPartitionScanner.CF_SCRIPT
		);
		//in style tags 
		assistant.setContentAssistProcessor(
			cfcp,
			CFPartitionScanner.CSS_TAG
		);
		//in javascript tags 
		assistant.setContentAssistProcessor(
			//cfcp,
			new JSCompletionProcessor(),
			CFPartitionScanner.J_SCRIPT
		);
		
		//TODO this stuff should be user setable 
		assistant.enableAutoActivation(true);
		assistant.setAutoActivationDelay(0);

		assistant.setProposalPopupOrientation(
			IContentAssistant.PROPOSAL_OVERLAY
		);
		
		assistant.setInformationControlCreator(
			getInformationControlCreator(sourceViewer)
		);
		return assistant;
	}
	/**
	 * Register the text hover
	 * @author Oliver Tupman
	 */
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType)
	{
		//keep the hover only in the parts where it should be
		//i.e. not in comments ... (we could add a javascript
		//css specific thing in the future...
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
	
	public IAutoIndentStrategy getAutoIndentStrategy(ISourceViewer arg0,
            String arg1) {
        CFAutoIndentStrategy newStrategy = new CFAutoIndentStrategy();
        return newStrategy;
    } 	
	
}
