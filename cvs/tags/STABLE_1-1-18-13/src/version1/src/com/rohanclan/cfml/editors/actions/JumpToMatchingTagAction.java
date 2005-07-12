/*
 * Created on Feb 20, 2005
 * by Christopher Bradford
 *
 */
package com.rohanclan.cfml.editors.actions;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.SubStatusLineManager;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.rohanclan.cfml.editors.CFMLEditor;
import com.rohanclan.cfml.editors.ICFDocument;
import com.rohanclan.cfml.editors.partitioner.CFEPartition;
import com.rohanclan.cfml.editors.partitioner.CFEPartitioner;

/**
 * @author Christopher Bradford
 *
 */
public class JumpToMatchingTagAction implements IEditorActionDelegate {

	private ITextEditor editor = null;
	private CFEPartitioner partitioner;

	/**
     * 
     */
    public JumpToMatchingTagAction() {
        super();
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
     */
    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if(targetEditor instanceof ITextEditor || targetEditor instanceof CFMLEditor)
		{
			this.editor = (ITextEditor)targetEditor;
			IDocument doc = editor.getDocumentProvider().getDocument(
					editor.getEditorInput());

			ICFDocument cfd = (ICFDocument) doc;

			this.partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
		}
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
			    
		ITextSelection sel = (ITextSelection) this.editor.getSelectionProvider().getSelection();
		int startpos = sel.getOffset();
	    Position match = findMatchingTag(startpos);
	    if (match != null) {
	        JumpToDocPos jumpAction = new JumpToDocPos();
		    jumpAction.setDocPos(match.getOffset());
		    jumpAction.setSelectionLength(match.getLength());
			jumpAction.setActiveEditor(null, editor);
		    jumpAction.run(null);
	    }
   	    /*
	    else {
	        String info = "No matching tag found";
		    String[] labels = new String[1];
			labels[0] = "OK";
		    MessageDialog msg = new MessageDialog(
					Display.getCurrent().getActiveShell(),
					"Matching tag",
					null,
					info,
					MessageDialog.WARNING, 
					labels, 
					0);
		    msg.open();
	    }*/
 
	    Display.getDefault().timerExec(2000, new Runnable() {
	       public void run() {
 	           editor.getEditorSite().getActionBars().getStatusLineManager().setMessage(null);
	       }
	    });
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
    }

    /**
     * Returns the position in the partitioner's position category which matches
     * the tag at the given offset. This is, the position is that of the matching
     * end tag if the offset is in a start tag, or vice versa. This method profits
     * from the knowledge that a partitioning is an ordered set of disjoint positions.
     * 
     * @param offset
     *            the offset for which to search the closest position
     * @return the closest position in the partitioner's category
     */
    private Position findMatchingTag(int offset) {
    
        CFEPartition currentPartition =  this.partitioner.findClosestPartition(offset);
        
        if (currentPartition == null) {
            return null;
        }
        
        CFEPartition[] fullStartTagPartitions = this.partitioner.getStartTagPartitions(currentPartition.getOffset());
        
        if (currentPartition.isMidPartition() || currentPartition.isEndPartition()) {
            
            //System.out.println("Clicked in tag attributes or by closing chevron; finding start tag");
            
            // CFEPartition prevPartition = (CFEPartition) category[--index];
            CFEPartition prevPartition = this.partitioner.getPreviousPartition(currentPartition.getOffset());
            //System.out.println("Checked previous partition: " + prevPartition.getType() + "(" + prevPartition.getTagName());
            while (prevPartition != null && (!prevPartition.isStartPartition())) {
                // prevPartition = (CFEPartition) category[--index];
                prevPartition = this.partitioner.getPreviousPartition(prevPartition.getOffset());
                //System.out.println("Checked previous partition: " + prevPartition.getType() + "(" + prevPartition.getTagName());
            }
            //System.out.println("Found start tag!");
            currentPartition = prevPartition;
        }
        
        // By this point, we have either returned null or we have the correct start or end CF Tag partition
        
        String tagName = currentPartition.getTagName();
        //System.out.println("Current tag name: " + tagName);
        int stackDepth = 0;
    
        if (currentPartition.isStartPartition()) {
            //System.out.println("In start tag; looking for matching end tag");
            
            int lastPartitionIndex =  fullStartTagPartitions.length - 1;
            int closingPartitionLength = fullStartTagPartitions[lastPartitionIndex].getLength();
            
            if (closingPartitionLength == 2) {
                // Self-closing tag
                //System.out.println("This is a self-closing tag.");
                IStatusLineManager statusLM = editor.getEditorSite().getActionBars().getStatusLineManager();
                statusLM.setMessage("This is a self-closing tag.");
                return null;
            }
            
            // Start after the closing chevron
            CFEPartition checkMatchPartition = this.partitioner.getNextPartition(fullStartTagPartitions[lastPartitionIndex].getOffset());
            while (checkMatchPartition != null) {
                // System.out.println("Checked partition tag name: " + checkMatchPartition.getTagName() + " & type: " + checkMatchPartition.getType());
                if (checkMatchPartition.isStartPartition() && currentPartition.getTagName().equalsIgnoreCase(checkMatchPartition.getTagName())) {
                    //System.out.println("Encountered nested start tag before end tag");
                    stackDepth++;
                    checkMatchPartition = this.partitioner.getNextPartition(checkMatchPartition.getOffset());
                    continue;
                }
                if (!checkMatchPartition.isCloser() || !tagName.equalsIgnoreCase(checkMatchPartition.getTagName())) {
                    //System.out.println("Partition at offset " + checkMatchPartition.getOffset() + " is not a closer or is a different tag");
                    checkMatchPartition = this.partitioner.getNextPartition(checkMatchPartition.getOffset());
                    continue;
                }
                // If we've reached this point, we should be at a closing tag with the same name
                if (stackDepth > 0) {
                    //System.out.println("Found match for nested tag; removing from stack");
                    stackDepth--;
                    checkMatchPartition = this.partitioner.getNextPartition(checkMatchPartition.getOffset());
                    continue;
                }
                //System.out.println("Found match! Is it a start partition: " + checkMatchPartition.isStartPartition());
                IStatusLineManager statusLM = editor.getEditorSite().getActionBars().getStatusLineManager();
                statusLM.setMessage(null);
                return checkMatchPartition;
            }
            //System.out.println("Looks like there's no match.");
        }
        else { // Trying to find the matching start tag for an end tag
            CFEPartition checkMatchPartition = this.partitioner.getPreviousPartition(currentPartition.getOffset());
            int start = 0;
            int end = 0;
            boolean matchFound = false;
            while (checkMatchPartition != null) {
                if (checkMatchPartition.isCloser() && currentPartition.getTagName().equalsIgnoreCase(checkMatchPartition.getTagName())) {
                    //System.out.println("Encountered nested end tag before start tag");
                    stackDepth++;
                    checkMatchPartition = this.partitioner.getPreviousPartition(checkMatchPartition.getOffset());
                    continue;
                }
                if (!tagName.equalsIgnoreCase(checkMatchPartition.getTagName()) || (checkMatchPartition.isMidPartition())) {
                    //System.out.println("Partition at offset " + checkMatchPartition.getOffset() + " doesn't match");
                    checkMatchPartition = this.partitioner.getPreviousPartition(checkMatchPartition.getOffset());
                    continue;
                }
                if (checkMatchPartition.isStartPartition() && currentPartition.getTagName().equals(checkMatchPartition.getTagName())) {
                    if (stackDepth == 0) {
                        //System.out.println("Found match at offset " + checkMatchPartition.getOffset());
                        // return checkMatchPartition;
                        matchFound = true;
                        break;
                    }
                    else {
                        //System.out.println("Found match for nested tag; removing from stack");
                        stackDepth--;
                    }
                }
                checkMatchPartition = this.partitioner.getPreviousPartition(checkMatchPartition.getOffset());
            }
            if (!matchFound) {
                IStatusLineManager statusLM = editor.getEditorSite().getActionBars().getStatusLineManager();
                statusLM.setMessage("No matching tag found.");
                return null;
            }
            
            if (checkMatchPartition == null ) {
                return null;
            }
            
            CFEPartition[] startPartitions = this.partitioner.getStartTagPartitions(checkMatchPartition.getOffset());
            if (startPartitions == null || startPartitions.length < 2) {
                return null;
            }
            start = startPartitions[0].getOffset();
            end = startPartitions[startPartitions.length - 1].getOffset() + startPartitions[startPartitions.length - 1].getLength();
            IStatusLineManager statusLM = editor.getEditorSite().getActionBars().getStatusLineManager();
            statusLM.setMessage(null);
            return new Position(start, end - start);
        }
        
        IStatusLineManager statusLM = editor.getEditorSite().getActionBars().getStatusLineManager();
        statusLM.setMessage("No matching tag found.");
        return null;
    
    };
    
}
