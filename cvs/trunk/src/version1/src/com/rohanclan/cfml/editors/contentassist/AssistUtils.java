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

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;

import com.rohanclan.cfml.editors.partitioner.CFEPartitioner;
import com.rohanclan.cfml.editors.partitioner.CFEPartition;

/**
 *
 * This class provides a few helper utilities for the content assist code.
 *
 * @author Oliver Tupman
 */
public class AssistUtils {

    public static boolean isInCorrectPartitionTypes(IAssistState state, String partitionTypes[])
    {
	    ITextViewer viewer = state.getITextView();
	   
	    int offset = state.getOffset();
	    for(int i = 0; i < partitionTypes.length; i++)
	    {
	        if(AssistUtils.isCorrectPartitionType(viewer, offset, partitionTypes[i]))
	        {
	            return true;
	        }
	    }
	    return false;

    }
    
    public static boolean isCorrectPartitionType(ITextViewer viewer, int offset, String targetPartitionType)
    {
	    String partitionType;
		try {
		    partitionType = viewer.getDocument().getPartition(offset).getType();
		} catch(BadLocationException ex) {
		    ex.printStackTrace();
		    return false;
		}
		return partitionType.equals(targetPartitionType);
    }
	
	/**
	 * Initialises a default assist state object ready for the beginning of content assist.
	 * 
     * @param viewer The view
     * @param offset The offset within the document.
     * @return The initialised DefaultAssistState ready to be used.
     * @see DefaultAssistState
     */
    public static DefaultAssistState initialiseDefaultAssistState(ITextViewer viewer, int offset) {
        DefaultAssistState assistState = new DefaultAssistState();
        IDocument document = viewer.getDocument();
		//char invokerChar = ' ';
		
		try {
		    assistState.setTriggerChar((offset > 0) ? document.getChar(offset-1) : ' ');
			assistState.setOffsetPartition(document.getPartition(offset));
		} catch(BadLocationException ex) {
			ex.printStackTrace();
		}	
		

		
		assistState.setOffset(offset);
		assistState.setDoc(viewer.getDocument());
		assistState.setTextViewer(viewer);

        CFEPartitioner partitioner = (CFEPartitioner)document.getDocumentPartitioner();
        CFEPartition[] partitions = partitioner.getTagPartitions(assistState.getOffset());
        if (partitions != null) {
	        int start = partitions[0].getOffset();
	        int end = partitions[partitions.length-1].getOffset() + partitions[partitions.length-1].getLength();
	        end = assistState.getOffset();
	        try {
	          assistState.setDataSoFar(document.get(start,end-start));  
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        } else {
            assistState = AssistUtils.getPrefix(assistState);
        }
        return assistState;
    }
    
    /**
     * Eliminates characters such as carriage returns, line feeds & tabs.
     * Basically clears up the string prior to content assist.
     * 
     * @param prefix The string to filter
     * @return The filtered string
     */
	public static String eliminateUnwantedChars(String prefix) {
		prefix = prefix.replace('\n',' ');
		prefix = prefix.replace('\r',' ');
		prefix = prefix.replace('\t',' ');
		return prefix;
	}

	/**
	 * Get prefix works out the text that represents the tag the user is requesting insight on.
	 * It works by working out the start position of the tag (based upon partition start)
	 * and then substrings that from the current insight invocation position. 
	 * 
	 * @param currState The current state of the content assist
	 * @return The content assist with it's dataSoFar member set.
	 */
	public static DefaultAssistState getPrefix(DefaultAssistState currState)
	{
		ITypedRegion partition = currState.getOffsetPartition();
		int start = partition.getOffset();
		int offset = currState.getOffset();
		
		String prefix = "";
		/*
		if(partition.getType() == CFPartitionScanner.J_SCRIPT) {
			System.err.println("HTMLContentAssistant::computeCompletionProposals() - JavaScript partition handling not implemented yet!");
			return null;
		}
		*/
		
		try {
			prefix = eliminateUnwantedChars(currState.getIDocument().get(start, offset - start));
		} catch(BadLocationException ex) {
			System.err.println("HTMLContentAssistant::computeCompletionProposals() - Caught Exception during prefix get!");
			ex.printStackTrace();
			return null;
		}
		currState.setDataSoFar(prefix);
		
		return currState;
	}
}
