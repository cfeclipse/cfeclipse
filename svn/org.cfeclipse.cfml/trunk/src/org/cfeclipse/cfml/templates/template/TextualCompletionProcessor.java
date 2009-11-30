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
import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.cfeclipse.cfml.templates.editors.TemplateEditorUI;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.swt.graphics.Image;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
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
				if (ch != '<' && !Character.isJavaIdentifierPart(ch))
					break;
				i--;
			}
			return document.get(i, offset - i);
		} catch (BadLocationException e) {
			return ""; //$NON-NLS-1$
		}
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
		// System.out.println("getRev for:" + template.getName() + " : |" +
		// prefix + "|");
		if (prefix.length() == 0) {
			// System.out.println("getRev=null");
			return 0;
		}
		if (prefix.startsWith("<")) //$NON-NLS-1$
			prefix = prefix.substring(1);
		if (template.getName().startsWith(prefix)) {
			// System.out.println("getRev=" + 90);
			return 90;
		}
		// System.out.println("getRev=" + 0);
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

	/*
	 * @seeorg.eclipse.jface.text.contentassist.IContentAssistProcessor#
	 * computeCompletionProposals(org.eclipse.jface.text.ITextViewer, int)
	 */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		int relavance = 0;

		ITextSelection selection = (ITextSelection) viewer.getSelectionProvider().getSelection();

		// adjust offset to end of normalized selection
		if (selection.getOffset() == offset) {
			offset = selection.getOffset() + selection.getLength();
		}
		String prefix = extractPrefix(viewer, offset);
		Region region = new Region(offset - prefix.length(), prefix.length());
		TemplateContext context = createContext(viewer, region);
		if (context == null)
			return new ICompletionProposal[0];

		context.setVariable("selection", selection.getText()); // name of the selection variables {line, word}_selection //$NON-NLS-1$
		Template[] templates = getTemplates(context.getContextType().getId());
		boolean ctrlIsDown = ((CFMLEditor)CFMLPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getSelectionCursorListener().isCrtlDown();
		boolean isCtrlSpaceSpace = ((CFMLEditor)CFMLPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getSelectionCursorListener().isCrtlSpaceSpace();

		List matches = new ArrayList();
		for (int i = 0; i < templates.length; i++) {
			Template template = templates[i];
			try {
				context.getContextType().validate(template.getPattern());
			} catch (TemplateException e) {
				continue;
			}
			relavance = getRelevance(template, prefix);
			if (template.matches(prefix, context.getContextType().getId()) && relavance > 0) {				
				matches.add(createProposal(template, context, (IRegion) region, relavance));
			} else if(template.matches(prefix, context.getContextType().getId()) && isCtrlSpaceSpace) {
				matches.add(createProposal(template, context, (IRegion) region, relavance));								
			}
		}

		Collections.sort(matches, fgProposalComparator);

		return (ICompletionProposal[]) matches.toArray(new ICompletionProposal[matches.size()]);
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
