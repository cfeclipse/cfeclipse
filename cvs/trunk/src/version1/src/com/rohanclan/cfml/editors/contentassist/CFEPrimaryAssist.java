/*
 * Created on Sep 27, 2004
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
package com.rohanclan.cfml.editors.contentassist;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.internal.utils.Assert;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import com.rohanclan.cfml.editors.ICFEFileDocument;

/**
 *
 * @author Oliver Tupman
 */
public class CFEPrimaryAssist implements IContentAssistProcessor {

    
    
	/** Characters that will trigger content assist */
	private char[] autoActivationChars = null;

    /**
     * 
     */
    public CFEPrimaryAssist() {
        super();
        generateAutoActivationChars();
    }

    private ArrayList arrayToCollection(Object [] array)
    {
        ArrayList  retVal = new ArrayList();
        for(int i = 0; i < array.length; i++)
        {
            retVal.add(array[i]);
        }
        return retVal;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeCompletionProposals(org.eclipse.jface.text.ITextViewer, int)
     */
    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
            int offset) {
        ArrayList proposals = new ArrayList();
        ArrayList proposers = new ArrayList();
        try {
        /*
        CFContentAssist temp = new CFContentAssist();
        temp.getCompletionProposalAutoActivationCharacters();
        proposers.add(temp);
        proposers.add(new CFMLScopeAssist());
        */
        if(viewer.getDocument() instanceof ICFEFileDocument)
        {
            try {
                proposers = ((ICFEFileDocument)viewer.getDocument()).getContentAssistManager().getRootAssistors();
            }
            catch (Exception e) {
                // NPE is thrown if the doc is read only.
            }
        }
        DefaultAssistState state = AssistUtils.initialiseDefaultAssistState(viewer, offset);
        Iterator proposerIter = proposers.iterator();
        while(proposerIter.hasNext())
        {
            Object currProc = proposerIter.next();
            Assert.isNotNull(currProc,"CFEPrimaryAssist::computeCompletionProposals()");
            ICompletionProposal [] tempProps = null;
// System.out.println("CFPrimaryAssist::computeCompletionProposals:\n");
// System.out.println("\tAsking assist of type \'" + currProc.getClass().getName() + "\'");
            if(currProc instanceof IContentAssistProcessor)
            {
                IContentAssistProcessor currProcessor = (IContentAssistProcessor)currProc;
                tempProps = currProcessor.computeCompletionProposals(viewer, offset);
            }
            else if(currProc instanceof IAssistContributor) 
            {
                IAssistContributor currContrib = (IAssistContributor)currProc;
                tempProps = currContrib.getTagProposals(state);
            }
            
            if(tempProps != null && tempProps.length > 0)
            {
// System.out.println("Got \'" + tempProps.length + "\' proposals");             
                proposals.addAll(arrayToCollection(tempProps));
            }
            else
            {
// System.out.println("It decided not to give any proposals (null)");
            }
        }
        
        return (ICompletionProposal[])proposals.toArray(new ICompletionProposal[proposals.size()]);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeContextInformation(org.eclipse.jface.text.ITextViewer, int)
     */
    public IContextInformation[] computeContextInformation(ITextViewer viewer,
            int offset) {
        ArrayList proposals = new ArrayList();
        ArrayList proposers = new ArrayList();
        
        if(viewer.getDocument() instanceof ICFEFileDocument)
        {
            proposers = ((ICFEFileDocument)viewer.getDocument()).getContentAssistManager().getRootAssistors();
        }
        DefaultAssistState state = AssistUtils.initialiseDefaultAssistState(viewer, offset);
        Iterator proposerIter = proposers.iterator();
        
        while(proposerIter.hasNext())
        {
            Object currProc = proposerIter.next();
            Assert.isNotNull(currProc,"CFEPrimaryAssist::computeContextInformation");
            IContextInformation [] tempProps = null;
            if(currProc instanceof IContentAssistProcessor)
            {
                IContentAssistProcessor currProcessor = (IContentAssistProcessor)currProc;
                tempProps = currProcessor.computeContextInformation(viewer, offset);
            }
            /*
             * TODO: Should probably add a custom interface for context info like we have for completion proposals
            else if(currProc instanceof IAssistContributor) 
            {
                IAssistContributor currContrib = (IAssistContributor)currProc;
                tempProps = currContrib.getTagProposals(state);
            }
            */
            if(tempProps != null && tempProps.length > 0)
            {
// System.out.println("Got \'" + tempProps.length + "\' proposals");             
                proposals.addAll(arrayToCollection(tempProps));
            }
            else
            {
// System.out.println("It decided not to give any proposals (null)");
            }
            
        }
        
        return (IContextInformation[])proposals.toArray(new IContextInformation[proposals.size()]);
        
    }

	/**
	 * Creates the array of characters that will trigger content assist
	 */
	private void generateAutoActivationChars() {
	    String autoActivationString = new String("");
	    autoActivationString += "abcdefghijklmnopqrstuvwxyz";
	    autoActivationString += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    autoActivationString += "0123456789";
	    autoActivationString += "(,=._< ~\t\n\r\"'#";
	    
	    char[] chars = autoActivationString.toCharArray();
	    
	    this.autoActivationChars = chars;
	    
	}
	
	
	/**
	 * What characters cause us to wake up (for tags and attributes)
	 */
	public char[] getCompletionProposalAutoActivationCharacters() {
	    //System.out.println("Auto-activation chars retrieved: " + new String(this.autoActivationChars));
	    return this.autoActivationChars;
	}

	/*
    public char[] getCompletionProposalAutoActivationCharacters() {
        return new char[] { '<', 'f', ' ', 'F', '~', '\t', '\n', '\r', '>', '\"', '.' };
    }
    */

    /* (non-Javadoc)
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationAutoActivationCharacters()
     */
    public char[] getContextInformationAutoActivationCharacters() {
        return null;
        //return new char[] {',', '('};
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getErrorMessage()
     */
    public String getErrorMessage() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationValidator()
     */
    public IContextInformationValidator getContextInformationValidator() {
        return null;
    }

}
