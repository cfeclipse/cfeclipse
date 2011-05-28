/*
 * Created on Sep 25, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver Tupman
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
package org.cfeclipse.cfml.editors.contentassist;

import org.cfeclipse.cfml.preferences.AutoIndentPreferenceConstants;
import org.cfeclipse.cfml.templates.template.TextualCompletionProcessor;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.swt.graphics.Image;

/**
 * Provides CFML Scope Assist at the tag-insight level. So the if the user is
 * tapping away
 * 
 * @author Oliver Tupman
 */
public class TemplateAssist extends AssistContributor implements IAssistContributor {
	private static final String DEFAULT_IMAGE = "$nl$/icons/template.gif"; //$NON-NLS-1$
	/**
	 * Source dictionary for the scope info. Currently defaults to the global CF
	 * dictionary.
	 */
	private TextualCompletionProcessor templateProcessor = new TextualCompletionProcessor();
	private int fSortOrder = 0x00000;

	/**
	 * @param cfmlAssistor
	 * 
	 */
	public TemplateAssist() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cfeclipse.cfml.editors.contentassist.IAssistContributor#getTagProposals
	 * (org.cfeclipse.cfml.editors.contentassist.IAssistState)
	 */
	public ICompletionProposal[] getTagProposals(IAssistState state) {
		if (!preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_SUGGEST_TEMPLATES)
				|| state.getTriggerData() == (' ')) {
			return null;
		}
		ICompletionProposal[] templateProposals = templateProcessor.computeCompletionProposals(state.getITextView(),
				state.getOffset());
		// TODO Auto-generated method stub
		return templateProposals;
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

	public String getId() {
		return "templates.proposals";
	}

	public String getName() {
		return "Templates Proposals";
	}

	/**
	 * @return sortOrder
	 */
	public int getSortOrder() {
		return fSortOrder;
	}

	public void sessionEnded() {
		// TODO Auto-generated method stub

	}

	public void sessionStarted() {
		// TODO Auto-generated method stub

	}

}
