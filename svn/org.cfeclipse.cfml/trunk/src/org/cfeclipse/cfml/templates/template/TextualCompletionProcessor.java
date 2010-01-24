/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.cfeclipse.cfml.templates.template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Region;
import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.formatters.XmlDocumentFormatter;
import org.cfeclipse.cfml.editors.indentstrategies.CFEIndentStrategy;
import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.cfeclipse.cfml.templates.editors.TemplateEditorUI;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.jface.text.templates.TemplateProposal;

/**
 * A completion processor for templates.
 */
public class TextualCompletionProcessor extends TemplateCompletionProcessor {

	private static final class ProposalComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			return ((TemplateProposal) o2).getRelevance() - ((TemplateProposal) o1).getRelevance();
		}
	}

	private static final Comparator fgProposalComparator = new ProposalComparator();

	/**
	 * We watch for angular brackets since those are often part of XML
	 * templates.
	 * 
	 * @param viewer
	 *            the viewer
	 * @param offset
	 *            the offset left of which the prefix is detected
	 * @return the detected prefix
	 */
	protected String extractPrefix(ITextViewer viewer, int offset) {
		IDocument document = viewer.getDocument();
		int i = offset;
		if (i > document.getLength())
			return ""; //$NON-NLS-1$

		try {
			while (i > 0) {
				char ch = document.getChar(i - 1);
				if (ch != '.' && !Character.isJavaIdentifierPart(ch))
					break;
				i--;
			}
			return document.get(i, offset - i);
		} catch (BadLocationException e) {
			return ""; //$NON-NLS-1$
		}
	}

	protected ICompletionProposal createProposal(Template template, TemplateContext context, Region region, int relevance) {
		return new CFTemplateProposal(template, context, region, getImage(template), relevance);
	}

	/**
	 * Cut out angular brackets for relevance sorting, since the template name
	 * does not contain the brackets.
	 * 
	 * @param template
	 *            the template
	 * @param prefix
	 *            the prefix
	 * @return the relevance of the <code>template</code> for the given
	 *         <code>prefix</code>
	 */
	protected int getRelevance(Template template, String prefix) {
		if (prefix.length() == 0) {
			//return 0;
			return 90;
		}
		if (prefix.startsWith("<")) //$NON-NLS-1$
			prefix = prefix.substring(1);
		if (template.getName().startsWith(prefix)) {
			return 90;
		}
		return 0;
	}

	/**
	 * Simply return all templates.
	 * 
	 * @param contextTypeId
	 *            the context type, ignored in this implementation
	 * @return all templates
	 */
	protected Template[] getTemplates(String contextTypeId) {
		return TemplateEditorUI.getDefault().getTemplateStore().getTemplates();
	}

	/**
	 * Return the XML context type that is supported by this plug-in.
	 * 
	 * @param viewer
	 *            the viewer, ignored in this implementation
	 * @param region
	 *            the region, ignored in this implementation
	 * @return the supported XML context type
	 */
	protected TemplateContextType getContextType(ITextViewer viewer, IRegion region) {
		boolean inCfscript = false;
		try {
			if (viewer.getDocument().getContentType(region.getOffset()) == CFPartitionScanner.CF_SCRIPT) {
				inCfscript = true;
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!inCfscript) {			
			return TemplateEditorUI.getDefault().getContextTypeRegistry().getContextType(
					CFTemplateContextType.XML_CONTEXT_TYPE);
		} else {
			return TemplateEditorUI.getDefault().getContextTypeRegistry().getContextType(
					CFScriptTemplateContextType.CFSCRIPT_CONTEXT_TYPE);			
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.templates.TemplateCompletionProcessor#createContext(org.eclipse.jface.text.ITextViewer, org.eclipse.jface.text.IRegion)
	 */
	protected TemplateContext createContext(ITextViewer contextViewer, IRegion region) {
		TemplateContextType contextType= getContextType(contextViewer, region);
		if (contextType != null) {
            Point selection= contextViewer.getSelectedRange();
            Position position;
            if (selection.y > 0) {
                position= new Position(selection.x, selection.y);    
            } else {
                position= new Position(region.getOffset(), region.getLength());
            }
            
			return new CFTemplateContext(contextType, contextViewer, position);
		}
		return null;
	}	
	
	/*
	 * @seeorg.eclipse.jface.text.contentassist.IContentAssistProcessor#
	 * computeCompletionProposals(org.eclipse.jface.text.ITextViewer, int)
	 */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		int relavance = 0;

		ITextSelection selection = (ITextSelection) viewer.getSelectionProvider().getSelection();

		// adjust offset to end of normalized selection
		/* incorrect for templates that instert what is selected
		if (selection.getOffset() == offset) {
			offset = selection.getOffset() + selection.getLength();
		}
		*/
		String prefix = extractPrefix(viewer, offset);
		String selectionText = selection.getText();
		//remove anything typed
		offset = offset - prefix.length();
		int length = selectionText.length() + prefix.length();
		Region region = new Region(offset, length);
		String templatePattern;
		Boolean hasSelectionVariable = false;
		IDocument doc = viewer.getDocument();
		TemplateContext context = createContext(viewer, region);
		if (context == null || doc.getLength() < 1)
			return new ICompletionProposal[0];

		Template[] templates = getTemplates(context.getContextType().getId());
		// TODO: move these variables to the CFTemplateContext class
		context.setVariable("selection", selectionText); // name of the selection variables {line, word}_selection //$NON-NLS-1$
		if(((ICFDocument)doc).getCFDocument() != null) {			
			context.setVariable("currentfile", ((ICFDocument)doc).getCFDocument().getFilename());
		}
		boolean inChevron = false;
		try {
			if(doc.getChar(offset-1) == '<' || doc.getChar(offset+1) == '>') {
				System.out.println(doc.getChar(offset+length));
				System.out.println(doc.getChar(offset - 1));
				inChevron = true;
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		List matches = new ArrayList();
		for (int i = 0; i < templates.length; i++) {
			Template template = templates[i];
			hasSelectionVariable = false;
			try {
				templatePattern = template.getPattern();
				context.getContextType().validate(templatePattern);
				if((templatePattern.indexOf("${word_selection}") > 0 || templatePattern.indexOf("${line_selection}") > 0 ) 
					&& selectionText.length() > 0){
					hasSelectionVariable = true;
				}
			} catch (TemplateException e) {
				continue;
			}
			relavance = getRelevance(template, prefix);
			if (template.matches(prefix, context.getContextType().getId()) && relavance > 0  && selectionText.length() == 0 || hasSelectionVariable && template.matches("", context.getContextType().getId())) {
				if(inChevron && templatePattern.startsWith("<") && templatePattern.endsWith(">")){
					region = getRegionNoChevrons(doc, offset, length);
					context = createContext(viewer, region);
					context.setVariable("selection", selectionText);
				}
				matches.add(createProposal(template, context, (IRegion) region, relavance));
			}
		}

		Collections.sort(matches, fgProposalComparator);

		return (ICompletionProposal[]) matches.toArray(new ICompletionProposal[matches.size()]);
	}
	
	private Region getRegionNoChevrons(IDocument doc, int offset, int length) {
		int retOffset = offset;
		if(doc.getLength() == 0 || offset == 0) {			
			return new Region(retOffset, length);
		}
		try {
			if(doc.getChar(offset-1) == '<') {
				retOffset--;
			}
			if(doc.getLength() > offset+length) {				
				if(doc.getChar(offset+length) == '>') {
					length++;
					length++;				
					//length++;				
					//length++;				
				}
			}
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Region region = new Region(retOffset, length);
		return region;
	}

	/**
	 * Always return the default image.
	 * 
	 * @param template
	 *            the template, ignored in this implementation
	 * @return the default template image
	 */
	protected Image getImage(Template template) {
		return CFPluginImages.get(CFPluginImages.ICON_TEMPLATE_SNIP);
	}

}
