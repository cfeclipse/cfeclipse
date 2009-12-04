package org.cfeclipse.cfml.templates.template;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension4;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.swt.graphics.Image;

public class CFTemplateProposal extends TemplateProposal implements ICompletionProposalExtension4
{

	public CFTemplateProposal(Template template, TemplateContext context, IRegion region, Image image, int relevance) {
		super(template, context, region, image, relevance);
		// TODO Auto-generated constructor stub
	}

	public boolean isAutoInsertable() {
		// TODO Auto-generated method stub
		return getTemplate().isAutoInsertable();
	}

}
