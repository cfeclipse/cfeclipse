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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

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
        /*
        CFContentAssist temp = new CFContentAssist();
        temp.getCompletionProposalAutoActivationCharacters();
        proposers.add(temp);
        proposers.add(new CFMLScopeAssist());
        */
        if(viewer.getDocument() instanceof ICFEFileDocument)
        {
            proposers = ((ICFEFileDocument)viewer.getDocument()).getContentAssistManager().getRootAssistors();
        }
        DefaultAssistState state = AssistUtils.initialiseDefaultAssistState(viewer, offset);
        Iterator proposerIter = proposers.iterator();
        while(proposerIter.hasNext())
        {
            Object currProc = proposerIter.next();
            Assert.isNotNull(currProc);
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

    /* (non-Javadoc)
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeContextInformation(org.eclipse.jface.text.ITextViewer, int)
     */
    public IContextInformation[] computeContextInformation(ITextViewer viewer,
            int offset) {
        // TODO Auto-generated method stub
        return null;
    }

	/**
	 * Creates the array of characters that will trigger content assist
	 */
	private void generateAutoActivationChars() {
	    char[] chars = new char[72];
	    int index = 0;
	    
	    try {
	        /*
	         * No idea why this doesn't work
	         * 
	        // Upper case letters
		    for (int i = 65; i <= 90; i++) {
		        index++;
			    chars[index]  = (char)i;
		    }
		    // Lower case letters
		    for (int i = 65; i <= 90; i++) {
		        index++;
			    chars[index]  = (char)i;
		    }
		    // Numbers
		    for (int i = 48; i <= 57; i++) {
		        index++;
			    chars[index]  = (char)i;
		    }
		    */
		    // Other characters

		    index++;
		    chars[index] = '<';
		    index++;
		    chars[index] = ' ';
		    index++;
		    chars[index] = '~';
		    index++;
		    chars[index] = '\t';
		    index++;
		    chars[index] = '\n';
		    index++;
		    chars[index] = '\r';
		    index++;
		    chars[index] = '>';
		    index++;
		    chars[index] = '\"';
		    index++;
		    chars[index] = '.';
		    index++;
		    chars[index] = '_';
		    index++;
		    chars[index] = 'A';
		    index++;
		    chars[index] = 'B';
		    index++;
		    chars[index] = 'C';
		    index++;
		    chars[index] = 'D';
		    index++;
		    chars[index] = 'E';
		    index++;
		    chars[index] = 'F';
		    index++;
		    chars[index] = 'G';
		    index++;
		    chars[index] = 'H';
		    index++;
		    chars[index] = 'I';
		    index++;
		    chars[index] = 'J';
		    index++;
		    chars[index] = 'K';
		    index++;
		    chars[index] = 'L';
		    index++;
		    chars[index] = 'M';
		    index++;
		    chars[index] = 'N';
		    index++;
		    chars[index] = 'O';
		    index++;
		    chars[index] = 'P';
		    index++;
		    chars[index] = 'Q';
		    index++;
		    chars[index] = 'R';
		    index++;
		    chars[index] = 'S';
		    index++;
		    chars[index] = 'T';
		    index++;
		    chars[index] = 'U';
		    index++;
		    chars[index] = 'V';
		    index++;
		    chars[index] = 'W';
		    index++;
		    chars[index] = 'X';
		    index++;
		    chars[index] = 'Y';
		    index++;
		    chars[index] = 'Z';
		    index++;
		    chars[index] = 'a';
		    index++;
		    chars[index] = 'b';
		    index++;
		    chars[index] = 'c';
		    index++;
		    chars[index] = 'd';
		    index++;
		    chars[index] = 'e';
		    index++;
		    chars[index] = 'f';
		    index++;
		    chars[index] = 'g';
		    index++;
		    chars[index] = 'h';
		    index++;
		    chars[index] = 'i';
		    index++;
		    chars[index] = 'j';
		    index++;
		    chars[index] = 'k';
		    index++;
		    chars[index] = 'l';
		    index++;
		    chars[index] = 'm';
		    index++;
		    chars[index] = 'n';
		    index++;
		    chars[index] = 'o';
		    index++;
		    chars[index] = 'p';
		    index++;
		    chars[index] = 'q';
		    index++;
		    chars[index] = 'r';
		    index++;
		    chars[index] = 's';
		    index++;
		    chars[index] = 't';
		    index++;
		    chars[index] = 'u';
		    index++;
		    chars[index] = 'v';
		    index++;
		    chars[index] = 'w';
		    index++;
		    chars[index] = 'x';
		    index++;
		    chars[index] = 'y';
		    index++;
		    chars[index] = 'z';
		    index++;
		    chars[index] = '1';
		    index++;
		    chars[index] = '2';
		    index++;
		    chars[index] = '3';
		    index++;
		    chars[index] = '4';
		    index++;
		    chars[index] = '5';
		    index++;
		    chars[index] = '6';
		    index++;
		    chars[index] = '7';
		    index++;
		    chars[index] = '8';
		    index++;
		    chars[index] = '9';



		   
		    
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    //System.out.println("Auto-activation chars: " + new String(chars));
	    
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
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getErrorMessage()
     */
    public String getErrorMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationValidator()
     */
    public IContextInformationValidator getContextInformationValidator() {
        // TODO Auto-generated method stub
        return null;
    }

}
