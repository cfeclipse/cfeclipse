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
package org.cfeclipse.cfml.editors.contentassist;

import java.util.ArrayList;
import java.util.Iterator;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.ICFEFileDocument;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ContentAssistEvent;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.ICompletionListener;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistantExtension2;
import org.eclipse.jface.text.contentassist.IContentAssistantExtension3;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

//import org.cfeclipse.cfml.editors.partitioner.CFEPartitioner;
//import org.cfeclipse.cfml.editors.partitioner.CFEPartition;
/**
 *
 * @author Oliver Tupman
 */
public class CFEPrimaryAssist implements IContentAssistProcessor, ICompletionListener {

    
    
	/** Characters that will trigger content assist */
	private char[] autoActivationChars = null;
	/* cycling stuff */
	private int fRepetition= -1;
	private String fIterationGesture= null;
    protected IContentAssistantExtension2 fContentAssistant;
	private boolean fTemplatesOnly = false;

    /**
     * 
     */
    public CFEPrimaryAssist() {
        super();
        generateAutoActivationChars();
    }

	private KeySequence getIterationBinding() {
	    final IBindingService bindingSvc= (IBindingService) PlatformUI.getWorkbench().getAdapter(IBindingService.class);
		TriggerSequence binding= bindingSvc.getBestActiveBindingFor(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		if (binding instanceof KeySequence)
			return (KeySequence) binding;
		return null;
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
		int iteration= fRepetition;
    	int lastKeyCode = ((CFMLEditor)CFMLPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getSelectionCursorListener().getLastKeyCode();
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
            	ICFEFileDocument curDoc = (ICFEFileDocument)viewer.getDocument();
            	if(fRepetition >= curDoc.getContentAssistManager().getRootAssistors().size()) {
            		fRepetition = -1;
            	}
//            	if(fRepetition == -1) {            		
//            		proposers = curDoc.getContentAssistManager().getRootAssistors();
//            	} else {
//            		proposers = curDoc.getContentAssistManager().getRootAssistors(fRepetition);            		
//            	}
            	if(fTemplatesOnly) {
            		proposers = curDoc.getContentAssistManager().getRootAssistors("org.cfeclipse.cfml.editors.contentassist.TemplateAssist");
            	} else {
            		proposers = curDoc.getContentAssistManager().getRootAssistors();
            	}
            }
            catch (Exception e) {
                // NPE is thrown if the doc is read only.
            	e.printStackTrace();
            }
        }
        // nasty hack for cycling proposal categories if contentAssist is called again
        System.out.println(fTemplatesOnly);
//        if (lastKeyCode == 32 || (lastKeyCode & SWT.CTRL) != 0) {
//        	fRepetition++;
//        	fTemplatesOnly = !fTemplatesOnly;
//            System.out.println(fRepetition);
//        }
        DefaultAssistState state = AssistUtils.initialiseDefaultAssistState(viewer, offset);
        Iterator proposerIter = proposers.iterator();
        while(proposerIter.hasNext())
        {
            Object currProc = proposerIter.next();
            //Assert.isNotNull(currProc,"CFEPrimaryAssist::computeCompletionProposals()");
            if(currProc == null)
            		throw new IllegalArgumentException("CFEPrimaryAssist::computeCompletionProposals()");
            
            ICompletionProposal [] tempProps = null;
            //System.out.println("CFPrimaryAssist::computeCompletionProposals:\n");
            //System.out.println("\tAsking assist of type \'" + currProc.getClass().getName() + "\'");
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
        //DefaultAssistState state = AssistUtils.initialiseDefaultAssistState(viewer, offset);
        	AssistUtils.initialiseDefaultAssistState(viewer, offset);
        Iterator proposerIter = proposers.iterator();
        
        while(proposerIter.hasNext())
        {
            Object currProc = proposerIter.next();
            //Assert.isNotNull(currProc,"CFEPrimaryAssist::computeContextInformation");
            if(currProc == null)
            		throw new IllegalArgumentException("CFEPrimaryAssist::computeContextInformation");
            
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
	    autoActivationString += "(,=._<~\t\n\r\"'#";
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

    /* (non-Javadoc)
     * @see org.eclipse.jface.text.contentassist.ICompletionListener#assistSessionStarted(org.eclipse.jface.text.contentassist.ContentAssistEvent)
     */
    public void assistSessionStarted(ContentAssistEvent event) {
        IContentAssistant assistant= event.assistant;
    	fRepetition = -1;
        if (assistant instanceof IContentAssistantExtension2) {
            fContentAssistant= (IContentAssistantExtension2) assistant;
        }
		System.out.println("STRTED");
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.text.contentassist.ICompletionListener#assistSessionEnded(org.eclipse.jface.text.contentassist.ContentAssistEvent)
     */
    public void assistSessionEnded(ContentAssistEvent event) {
        fContentAssistant= null;
        fTemplatesOnly= false;
		System.out.println("ENDED");
    }

	public void selectionChanged(ICompletionProposal arg0, boolean arg1) {
		if(arg1)
		System.out.println("SMART");
		// TODO Auto-generated method stub
	}

}
