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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.cfeclipse.cfml.editors.ICFEFileDocument;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ContentAssistEvent;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.ICompletionListener;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistantExtension2;
import org.eclipse.jface.text.contentassist.IContentAssistantExtension3;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

//import org.cfeclipse.cfml.editors.partitioner.CFEPartitioner;
//import org.cfeclipse.cfml.editors.partitioner.CFEPartition;
/**
 *
 * @author Oliver Tupman
 */
public class CFEPrimaryAssist implements IContentAssistProcessor {
	// stats checking
	private static final boolean DEBUG= false;
    
    
	/** Characters that will trigger content assist */
	private char[] autoActivationChars = null;
	/* cycling stuff */
	private int fRepetition= -1;
	private String fIterationGesture= null;
    private final IContentAssistantExtension2 fContentAssistant;
	private boolean fTemplatesOnly = false;
	private List fCategories;
	private List fCategoryIteration= null;
	private String fErrorMessage;
	private int fNumberOfComputedResults;
	private ISourceViewer fSourceViewer;


	private DefaultAssistState fState;


	private int fLastOffset;

	private static final Comparator ORDER_COMPARATOR= new Comparator() {

		public int compare(Object o1, Object o2) {
			IAssistContributor d1= (IAssistContributor) o1;
			IAssistContributor d2= (IAssistContributor) o2;
			
			return d1.getSortOrder() - d2.getSortOrder();
		}
		
	};

    /**
     * @param sourceViewer 
     * @param assistant 
     * 
     */
    public CFEPrimaryAssist(ISourceViewer sourceViewer, ContentAssistant assistant) {
        super();
        fContentAssistant = assistant;
        fSourceViewer = sourceViewer;
        generateAutoActivationChars();
        // content assist category cycling
		fContentAssistant.addCompletionListener(new ICompletionListener() {			
			/*
			 * @see org.eclipse.jface.text.contentassist.ICompletionListener#assistSessionStarted(org.eclipse.jface.text.contentassist.ContentAssistEvent)
			 */
			public void assistSessionStarted(ContentAssistEvent event) {
				if (event.processor != CFEPrimaryAssist.this)
					return;
				fIterationGesture= getIterationGesture();
				KeySequence binding= getIterationBinding();

				// this may show the warning dialog if all categories are disabled
				fCategoryIteration= getCategoryIteration();
				for (Iterator it= fCategories.iterator(); it.hasNext();) {
					IAssistContributor cat= (IAssistContributor) it.next();
					cat.sessionStarted();
				}
				System.out.println("catItSize "+fCategoryIteration.size());
				fRepetition= 0;
				if (event.assistant instanceof IContentAssistantExtension2) {
					IContentAssistantExtension2 extension= (IContentAssistantExtension2) event.assistant;

					if (fCategoryIteration.size() == 1) {
						extension.setRepeatedInvocationMode(false);
						extension.setShowEmptyList(false);
					} else {
						extension.setRepeatedInvocationMode(true);
						extension.setStatusLineVisible(true);
						extension.setStatusMessage(createIterationMessage());
						extension.setShowEmptyList(true);
						if (extension instanceof IContentAssistantExtension3) {
							IContentAssistantExtension3 ext3= (IContentAssistantExtension3) extension;
							((ContentAssistant) ext3).setRepeatedInvocationTrigger(binding);
						}
					}
				
				}
			}
			
			/*
			 * @see org.eclipse.jface.text.contentassist.ICompletionListener#assistSessionEnded(org.eclipse.jface.text.contentassist.ContentAssistEvent)
			 */
			public void assistSessionEnded(ContentAssistEvent event) {
				if (event.processor != CFEPrimaryAssist.this)
					return;
				fCategoryIteration= null;
				fState = null;
				fRepetition= -1;
				fIterationGesture= null;
				if(fCategories == null) {					
					return;
				}
				for (Iterator it= fCategories.iterator(); it.hasNext();) {
					IAssistContributor cat= (IAssistContributor) it.next();
					cat.sessionEnded();
				}

				if (event.assistant instanceof IContentAssistantExtension2) {
					IContentAssistantExtension2 extension= (IContentAssistantExtension2) event.assistant;
					extension.setShowEmptyList(false);
					extension.setRepeatedInvocationMode(false);
					extension.setStatusLineVisible(false);
					if (extension instanceof IContentAssistantExtension3) {
						IContentAssistantExtension3 ext3= (IContentAssistantExtension3) extension;
						((ContentAssistant) ext3).setRepeatedInvocationTrigger(KeySequence.getInstance());
					}
				}
			}

			/*
			 * @see org.eclipse.jface.text.contentassist.ICompletionListener#selectionChanged(org.eclipse.jface.text.contentassist.ICompletionProposal, boolean)
			 */
			public void selectionChanged(ICompletionProposal proposal, boolean smartToggle) {}
			
		});
		setRootAssistorsToCategories();
	}

	private void clearState() {
		fErrorMessage=null;
		fNumberOfComputedResults= 0;
	}

	/**
	 * Filters and sorts the proposals. The passed list may be modified
	 * and returned, or a new list may be created and returned.
	 * 
	 * Nothing happening here right now, but eventually maybe
	 * 
	 * @param proposals the list of collected proposals (element type:
	 *        {@link ICompletionProposal})
	 * @param monitor a progress monitor
	 * @return the list of filtered and sorted proposals, ready for
	 *         display (element type: {@link ICompletionProposal})
	 */
	protected List filterAndSortProposals(List proposals, IProgressMonitor monitor) {
		return proposals;
	}
    
	/*
	 * Currently returns java binding, eventually we need to add our own preferences!
	 * @return binding for proposal cycling
	 * 
	 */
	private KeySequence getIterationBinding() {
	    final IBindingService bindingSvc= (IBindingService) PlatformUI.getWorkbench().getAdapter(IBindingService.class);
		TriggerSequence binding= bindingSvc.getBestActiveBindingFor(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		if (binding instanceof KeySequence)
			return (KeySequence) binding;
		return null;
    }

    private String getIterationGesture() {
        TriggerSequence binding = getIterationBinding();
        return binding != null ? binding.format(): "completion key";
    }

	private String createEmptyMessage() {
		return "No " + getCategoryLabel(fRepetition);
	}
	
	private String createIterationMessage() {
		return getCategoryLabel(fRepetition) + " - Press " + fIterationGesture + " to show " + getCategoryLabel(fRepetition + 1);
	}
	
	/*
	 * kludge job for getting assistors
	 */

	private void setRootAssistorsToCategories() {
		if(fCategories == null) {			
			ICFEFileDocument curDoc = (ICFEFileDocument)fSourceViewer.getDocument();
			if(curDoc != null && curDoc instanceof ICFEFileDocument)
				fCategories = curDoc.getContentAssistManager().getRootAssistors();
		}
	}
	
	
	private String getCategoryLabel(int repetition) {
		int iteration= repetition % fCategoryIteration.size();
		if (iteration == 0)
			return "All proposals";
		return toString((IAssistContributor) ((List) fCategoryIteration.get(iteration)).get(0));
	}
	
	private String toString(IAssistContributor category) {
		return category.getName();
	}

	/**
	 * Creates a progress monitor.
	 * <p>
	 * The default implementation creates a
	 * <code>NullProgressMonitor</code>.
	 * </p>
	 * 
	 * @return a progress monitor
	 */
	protected IProgressMonitor createProgressMonitor() {
		return new NullProgressMonitor();
	}
	
	
	private List getCategories() {
		setRootAssistorsToCategories();
		if (fCategoryIteration == null)
			return fCategories;
		
		int iteration= fRepetition % fCategoryIteration.size();
		fContentAssistant.setStatusMessage(createIterationMessage());
		fContentAssistant.setEmptyMessage(createEmptyMessage());
		// a little hack because it seems like we get the cycle trigger with each keypress?!?! :denny
		if(fLastOffset == fState.getOffset()) {			
			fRepetition++;
		}
		fLastOffset = fState.getOffset();
		
//		fAssistant.setShowMessage(fRepetition % 2 != 0);
//		
		return (List) fCategoryIteration.get(iteration);
	}

	private List getCategoryIteration() {
		List sequence= new ArrayList();
		sequence.add(getDefaultCategories());
		for (Iterator it= getSeparateCategories().iterator(); it.hasNext();) {
			IAssistContributor cat= (IAssistContributor) it.next();
			sequence.add(Collections.singletonList(cat));
		}
		return sequence;
	}

	private List getDefaultCategories() {
		// default mix - enable all included computers
		List included= getDefaultCategoriesUnchecked();
		// TODO:  set up preference page for setting default vs. cycled
		return included;
	}

	private List getDefaultCategoriesUnchecked() {
		setRootAssistorsToCategories();
		List included= new ArrayList();
		for (Iterator it= fCategories.iterator(); it.hasNext();) {
			IAssistContributor category= (IAssistContributor) it.next();
			if (category.isIncluded())
				included.add(category);
		}
		return included;
	}

	/*
	 * this and the above two functions would do more if we had preferences
	 * for enabling default assistors etc.
	 */
	private List getSeparateCategories() {
		ArrayList sorted= new ArrayList();
		for (Iterator it= fCategories.iterator(); it.hasNext();) {
			IAssistContributor category= (IAssistContributor) it.next();
			if (category.isSeparateCommand())
				sorted.add(category);
		}
		Collections.sort(sorted, ORDER_COMPARATOR);
		return sorted;
	}


    private ArrayList arrayToCollection(Object [] array)
    {
        ArrayList  retVal = new ArrayList();
        if(array == null)
        	return retVal;
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
    	long start= DEBUG ? System.currentTimeMillis() : 0;
		
		clearState();
		
		IProgressMonitor monitor= createProgressMonitor();
		monitor.beginTask("Computing proposals", fCategories.size() + 1);
		long setup= DEBUG ? System.currentTimeMillis() : 0;

		monitor.subTask("Collecting proposals");
		List proposals= collectProposals(viewer, offset, monitor);
		long collect= DEBUG ? System.currentTimeMillis() : 0;

		monitor.subTask("Sorting proposals");
		List filtered= filterAndSortProposals(proposals, monitor);
		fNumberOfComputedResults= filtered.size();
		long filter= DEBUG ? System.currentTimeMillis() : 0;
		
		ICompletionProposal[] result= (ICompletionProposal[]) filtered.toArray(new ICompletionProposal[filtered.size()]);
		monitor.done();
		
		if (DEBUG) {
			System.err.println("Code Assist Stats (" + result.length + " proposals)"); //$NON-NLS-1$ //$NON-NLS-2$
			System.err.println("Code Assist (setup):\t" + (setup - start) ); //$NON-NLS-1$
			System.err.println("Code Assist (collect):\t" + (collect - setup) ); //$NON-NLS-1$
			System.err.println("Code Assist (sort):\t" + (filter - collect) ); //$NON-NLS-1$
		}
		
		return result;
    }

	private List collectProposals(ITextViewer viewer, int offset, IProgressMonitor monitor) {
		if(viewer == null) 
			return new ArrayList();
		fState = AssistUtils.initialiseDefaultAssistState(viewer, offset);
		List proposals= new ArrayList();
		List providers= getCategories();
		IAssistContributor cat = null;
		for (Iterator it= providers.iterator(); it.hasNext();) {
			Object assistor = it.next();
			if(assistor instanceof CFContentAssist) {
				cat= (IAssistContributor) assistor;
		        fState.setPrevDelim(0);	// TODO: Bodge job, need to assign correct previous delim position
		        //assistState.setDataSoFar(assistState.getDataSoFar().trim()); // FIXME: see trac ticket #472
				ICompletionProposal[] computed= cat.getTagProposals(fState);
				proposals.addAll(arrayToCollection(computed));
			}
			else if(assistor instanceof IAssistContributor) {
				cat= (IAssistContributor) assistor;
				ICompletionProposal[] computed= cat.getTagProposals(fState);
				proposals.addAll(arrayToCollection(computed));
			} else {
				System.out.println(assistor.getClass().getName());
			}
			if (fErrorMessage == null)
				fErrorMessage= cat.getErrorMessage();
		}
//			if (fErrorMessage == null)
//				fErrorMessage= cat.getErrorMessage();		

		return proposals;
	}
    
    
    
	/*
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeContextInformation(org.eclipse.jface.text.ITextViewer, int)
	 */
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		clearState();

		IProgressMonitor monitor= createProgressMonitor();
		monitor.beginTask("Computing contexts", fCategories.size() + 1);
		
		monitor.subTask("Collecting contexts");
		List proposals= collectContextInformation(viewer, offset, monitor);

		monitor.subTask("Sorting contexts");
		List filtered= filterAndSortContextInformation(proposals, monitor);
		fNumberOfComputedResults= filtered.size();
		
		IContextInformation[] result= (IContextInformation[]) filtered.toArray(new IContextInformation[filtered.size()]);
		monitor.done();
		return result;
	}

	private List collectContextInformation(ITextViewer viewer, int offset, IProgressMonitor monitor) {
		List proposals= new ArrayList();
		
		List providers= getCategories();
		for (Iterator it= providers.iterator(); it.hasNext();) {
			IContentAssistProcessor cat= (IContentAssistProcessor) it.next();
			IContextInformation[] computed= cat.computeContextInformation(viewer,offset);
			proposals.addAll(arrayToCollection(computed));
			if (fErrorMessage == null)
				fErrorMessage= cat.getErrorMessage();
		}
		
		return proposals;
	}

	/**
	 * Filters and sorts the context information objects. The passed
	 * list may be modified and returned, or a new list may be created
	 * and returned.
	 * 
	 * @param contexts the list of collected proposals (element type:
	 *        {@link IContextInformation})
	 * @param monitor a progress monitor
	 * @return the list of filtered and sorted proposals, ready for
	 *         display (element type: {@link IContextInformation})
	 */
	protected List filterAndSortContextInformation(List contexts, IProgressMonitor monitor) {
		return contexts;
	}


	/**
	 * Creates the array of characters that will trigger content assist
	 */
	private void generateAutoActivationChars() {
	    String autoActivationString = new String("");
	    autoActivationString += "abcdefghijklmnopqrstuvwxyz";
	    autoActivationString += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    autoActivationString += "0123456789";
// with short auto-activate times these are annoying now:	    
//	    autoActivationString += "(,=._<~\t\n\r\"'# ";
	    autoActivationString += "(,=._<~#";
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

	/*
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getErrorMessage()
	 */
	public String getErrorMessage() {
		if (fNumberOfComputedResults > 0)
			return null;
		if (fErrorMessage != null)
			return fErrorMessage;
		return "No proposals";
	}


    /* (non-Javadoc)
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationValidator()
     */
    public IContextInformationValidator getContextInformationValidator() {
        return null;
    }
    
}
