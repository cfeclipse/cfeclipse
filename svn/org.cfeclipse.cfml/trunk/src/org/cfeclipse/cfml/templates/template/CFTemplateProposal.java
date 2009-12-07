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

	private final Template fTemplate;
	private final TemplateContext fContext;
	private final Image fImage;
	private IRegion fRegion;
	private int fRelevance;

	private IRegion fSelectedRegion; // initialized by apply()

	public CFTemplateProposal(Template template, TemplateContext context, IRegion region, Image image, int relevance) {
		super(template, context, region, image, relevance);
		fTemplate= template;
		fContext= context;
		fImage= image;
		fRegion= region;

	}

	/*
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposalExtension4#isAutoInsertable()
	 */
	public boolean isAutoInsertable() {
		return fTemplate.isAutoInsertable();
	}

	/**
	 * Returns <code>true</code> if the proposal has a selection, e.g. will wrap some code.
	 * 
	 * @return <code>true</code> if the proposals completion length is non zero
	 * @since 3.2
	 */
	private boolean isSelectionTemplate() {
		if(fRegion.getLength()>0){
//			return true;
		}
		return false;
	}
	
	
}
