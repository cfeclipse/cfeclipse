/*
 * $Id: CFEDefaultPartitioner.java,v 1.13 2005-01-21 08:25:15 smilligan Exp $
 * $Revision: 1.13 $
 * $Date: 2005-01-21 08:25:15 $
 * 
 * Created on Oct 17, 2004
 *
 * CFEclipse - The Open Source ColdFusion Development Environment
 * 
 * Copyright (c) 2005 Stephen Milligan and others.  All rights reserved.
 * For more information on cfeclipse, please see http://cfeclipse.tigris.og/.
 *
 * ====================================================================
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the Eclipse Public License
 * as published by the Eclipse Foundation; either
 * version 1.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Eclipse 
 * Public License for more details.
 *
 * You should have received a copy of the Eclipse Public License with this 
 * software. If not, the full text of version 1.0 of the Eclipse Public License 
 * is available online at the following URL:
 * 
 * http://www.eclipse.org/legal/epl-v10.html
 * ====================================================================
 */
package com.rohanclan.cfml.editors.partitioner;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.eclipse.jface.text.Assert;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IDocumentPartitionerExtension;
import org.eclipse.jface.text.IDocumentPartitionerExtension2;
//import org.eclipse.jface.text.rules.DefaultPartitioner;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TypedRegion;
//import org.eclipse.jface.text.TypedPosition;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.rules.IToken;
import com.rohanclan.cfml.editors.partitioner.scanners.CFPartitionScanner;
import com.rohanclan.cfml.editors.ICFDocument;

/**
 * @author Stephen Milligan
 * 
 * This is an attempt to create a more flexible and powerful partitioner than
 * the default RuleBasedPartitionScanner.
 * 
 * Very much a work in progress at the minute.
 */
public class CFEDefaultPartitioner implements IDocumentPartitioner,
        IDocumentPartitionerExtension, IDocumentPartitionerExtension2 {

    private final static String CONTENT_TYPES_CATEGORY = "__content_types_category";

    /** The partitioner's scanner */
    protected IPartitionTokenScanner fScanner;

    /** The legal content types of this partitioner */
    protected String[] fLegalContentTypes;

    /** The partitioner's document */
    protected IDocument fDocument;

    /** The document length before a document change occurred */
    protected int fPreviousDocumentLength;

    /** The position updater used to for the default updating of partitions */
    protected DefaultPositionUpdater fPositionUpdater;

    /** The offset at which the first changed partition starts */
    protected int fStartOffset;

    /** The offset at which the last changed partition ends */
    protected int fEndOffset;

    /** The offset at which a partition has been deleted */
    protected int fDeleteOffset;

    /** 
     * Pseudo partitions such as the contents of a cfquery tag block
     * That is, partitions that exist only because of what comes before 
     * and after them rather than because of any intrinsic property of
     * the partition itself. 
     */
    private Properties fPseudoPartitions = null;
    
    
    /**
     * The position category this partitioner uses to store the document's
     * partitioning information.
     * 
     * @since 3.0
     */
    private String fPositionCategory;

    /**
     * Creates a new partitioner that uses the given scanner and may return
     * partitions of the given legal content types.
     * 
     * @param scanner
     *            the scanner this partitioner is supposed to use
     * @param legalContentTypes
     *            the legal content types of this partitioner
     */
    public CFEDefaultPartitioner(IPartitionTokenScanner scanner,
            String[] legalContentTypes) {
        fScanner = scanner;
        fLegalContentTypes = legalContentTypes;
        fPositionCategory = CONTENT_TYPES_CATEGORY + hashCode();
        fPositionUpdater = new DefaultPositionUpdater(fPositionCategory);
        fPseudoPartitions = new Properties();
        fPseudoPartitions.put("cfquery",CFPartitionScanner.SQL);
        fPseudoPartitions.put("cfscript",CFPartitionScanner.CF_SCRIPT);
        //fPseudoPartitions.put("cfxml",CFPartitionScanner.XML);
        fPseudoPartitions.put("style",CFPartitionScanner.CSS);
        fPseudoPartitions.put("script",CFPartitionScanner.J_SCRIPT);
    }

    /*
     * @see org.eclipse.jface.text.IDocumentPartitionerExtension2#getManagingPositionCategories()
     * @since 3.0
     */
    public String[] getManagingPositionCategories() {
        return new String[] { fPositionCategory };
    }

    /*
     * @see IDocumentPartitioner#connect(IDocument)
     */
    public void connect(IDocument document) {
        Assert.isNotNull(document, "CFEDefaultPartitioner::connect()");
        Assert.isTrue(!document.containsPositionCategory(fPositionCategory),
                "CFEDefaultPartitioner::connect()");

        fDocument = document;
        fDocument.addPositionCategory(fPositionCategory);

        initialize();
    }

    /**
     * Performs the initial partitioning of the partitioner's document.
     */
    protected void initialize() {

        fScanner.setRange(fDocument, 0, fDocument.getLength());
        String nextPartitionType = IDocument.DEFAULT_CONTENT_TYPE;
        try {
            IToken token = fScanner.nextToken();

            while (!token.isEOF()) {

                String contentType = getTokenContentType(token);
                if (isSupportedContentType(contentType)) {
                    nextPartitionType = handleToken(token, contentType,nextPartitionType);
                }

                token = fScanner.nextToken();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * @see IDocumentPartitioner#disconnect()
     */
    public void disconnect() {

        Assert.isTrue(fDocument.containsPositionCategory(fPositionCategory),
                "CFEDefaultPartitioner::disconnect()");

        try {
            fDocument.removePositionCategory(fPositionCategory);
        } catch (BadPositionCategoryException x) {
            // can not happen because of Assert
        }
    }

    /**
     * 
     *  
     * @param token - The token that was found by the scanner
     * @param contentType - The content type of the token
     */
    private String handleToken(IToken token, String contentType, String nextPartitionType) {
        try {
            CFEPartition p = null;
            int length;
            int indexOffset = 0;
            String rawData = null;
            if (token.getData() instanceof TagData) {
                TagData data = (TagData) token.getData();
                int start = fScanner.getTokenOffset();
                length = data.getFirstPartitionEnd();
                rawData = data.getRawData().toLowerCase();
                if (!fDocument.containsPosition(fPositionCategory,start,length)) {
                    // Add a partition for the start part of the tag.
                     p = new CFEPartition(start, length, data.getStartPartitionType());
                    fDocument.addPosition(fPositionCategory, p);
                }
                if (!data.isCloser()) {
                    if (p != null) {
                        p.setNextPartitionType(data.getMidPartitionType());
                    }
                    if (data.fHasMid) {
                        indexOffset++;
                        start = start + data.getFirstPartitionEnd();
                        length = data.getMidPartitionEnd()-data.getFirstPartitionEnd();
                        p = new CFEPartition(start, length, data.getMidPartitionType());
                        if (!fDocument.containsPosition(fPositionCategory,p.offset,p.length)) {
                            fDocument.addPosition(fPositionCategory, p);
                        }
                    }
                    if (data.fHasEnd) {
                        indexOffset++;
                        start = fScanner.getTokenOffset() + data.getMidPartitionEnd();
                        length = data.getRawData().length()- data.getMidPartitionEnd();
	                    if (!fDocument.containsPosition(fPositionCategory,start,length)) {
		                    p = new CFEPartition(start, length, data.getEndPartitionType());
	                        fDocument.addPosition(fPositionCategory, p);
	                    }

                    }

                } // End handle opening tag
            } // End token is instance of tagData 
            else {
                
                if (!fDocument.containsPosition(fPositionCategory,fScanner.getTokenOffset(),fScanner.getTokenLength())) {
                    p = new CFEPartition(fScanner.getTokenOffset(),fScanner.getTokenLength(), contentType);
                    fDocument.addPosition(fPositionCategory, p);
                }
            }
            
            if (p != null 
                    && p.getNextPartitionType() == null) {
                String next = getNextPartitionType(fScanner.getTokenOffset(),rawData,indexOffset);
                //System.out.println("Got next " + next + " for partition " + p.getType());
		        p.setNextPartitionType(next);
		        return next;
            } else {
                return null;
            }
    
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (BadPositionCategoryException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Determines if the given partition type is a pseudo partition */
    private boolean isPseudoPartition(String type) {
        Object[] types = fPseudoPartitions.values().toArray();
        for (int i=0;i<types.length;i++) {
            if (types[i].equals(type)) {
                return true;
            }
        }
        return false;
    }

    /** Determines whether or not the given tag terminates a pseudo partition */
    private boolean closesPseudoPartition(String tag) {
        if (tag == null) {
            return false;
        }
        Set keys = fPseudoPartitions.keySet();
        Object[] tags = keys.toArray();
        for (int i = 0; i < tags.length; i++)
        {
            String closer = "</" + tags[i].toString();
            if (tag.startsWith(closer)
                    && !Character.isLetter(tag.charAt(closer.length()))) {
                return true;
            }
        }
        return false;
    }


    /** Determines whether or not the given tag starts a pseudo partition */
    private String opensPseudoPartition(String tag) {
        if (tag == null) {
            return null;
        }
        Set keys = fPseudoPartitions.keySet();
        Object[] tags = keys.toArray();
        for (int i = 0; i < tags.length; i++)
        {
            String closer = "<" + tags[i].toString();
            if (tag.startsWith(closer)
                    && !Character.isLetter(tag.charAt(closer.length()))) {
                return fPseudoPartitions.getProperty(tags[i].toString());
            }
        }
        return null;
    }
    
    
    private String getNextPartitionType(int offset,String data,int indexOffset) {
        try {
            String next = opensPseudoPartition(data);
            //System.out.println("Checking next partition for " + data + " next is " + next);
            if (closesPseudoPartition(data)) {
                next = null;
            }
            
            Position[] category = fDocument.getPositions(fPositionCategory);
            int index = fDocument.computeIndexInCategory(fPositionCategory,offset);
            CFEPartition thisPartition = (CFEPartition)category[index];
            // System.out.println("Index is " + index + " of " + category.length);
            if (index > 1) {
	            CFEPartition prevPartition = (CFEPartition)category[index-1];
	            if (next == null) {
	                
	                next = prevPartition.getNextPartitionType();
	                // System.out.println("Prev partition is " + prevPartition.getNextPartitionType());
	            }
	            
	            if (thisPartition.offset > prevPartition.offset + prevPartition.length) {
	                int start = prevPartition.offset + prevPartition.length;
	                int length = thisPartition.offset - start;
	                CFEPartition p = new CFEPartition(start,length,prevPartition.getNextPartitionType());
	                p.setNextPartitionType(next);
	                fDocument.addPosition(fPositionCategory, p);
	                
	            }
	            
            }
            
            // Check if the subsequent partition needs to be modified.
            if (index+1+indexOffset < category.length) {
                thisPartition = (CFEPartition)category[index+indexOffset];
                CFEPartition nextPartition = (CFEPartition)category[index+indexOffset+1];
                //System.out.println("This partition is " + thisPartition.getType() + " from " + thisPartition.offset + " to " + Integer.toString(thisPartition.offset + thisPartition.length) + " next type should be " + next);
                //System.out.println("Next partition is " + nextPartition.getType() + " from " + nextPartition.offset + " to " + Integer.toString(nextPartition.offset + nextPartition.length));
                if (nextPartition.getType().equals(next)) {
                    //System.out.println("Moved partition start from " + nextPartition.offset + " to "  + Integer.toString(thisPartition.offset + thisPartition.length) + " for partition of type " + nextPartition.getType());
                    nextPartition.setOffset(thisPartition.offset + thisPartition.length);
                } else if (thisPartition.offset + thisPartition.length < nextPartition.offset) {
                    int start = thisPartition.offset + thisPartition.length;
	                int length = nextPartition.offset - start;
	                CFEPartition p = new CFEPartition(start,length,next);
	                p.setNextPartitionType(next);
	                fDocument.addPosition(fPositionCategory, p);
                }
            }
            
            return next;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * @see IDocumentPartitioner#documentAboutToBeChanged(DocumentEvent)
     */
    public void documentAboutToBeChanged(DocumentEvent e) {

        Assert.isTrue(e.getDocument() == fDocument,
                "CFEDefaultPartitioner::documentAboutToBeChanged()");

        fPreviousDocumentLength = e.getDocument().getLength();
        fStartOffset = -1;
        fEndOffset = -1;
        fDeleteOffset = -1;
    }

    /*
     * @see IDocumentPartitioner#documentChanged(DocumentEvent)
     */
    public boolean documentChanged(DocumentEvent e) {

        IRegion region = documentChanged2(e);
        return (region != null);
    }

    /**
     * Helper method for tracking the minimal region containing all partition
     * changes. If <code>offset</code> is smaller than the remembered offset,
     * <code>offset</code> will from now on be remembered. If
     * <code>offset  + length</code> is greater than the remembered end offset,
     * it will be remembered from now on.
     * 
     * @param offset
     *            the offset
     * @param length
     *            the length
     */
    private void rememberRegion(int offset, int length) {
        // remember start offset
        if (fStartOffset == -1)
            fStartOffset = offset;
        else if (offset < fStartOffset)
            fStartOffset = offset;

        // remember end offset
        int endOffset = offset + length;
        if (fEndOffset == -1)
            fEndOffset = endOffset;
        else if (endOffset > fEndOffset)
            fEndOffset = endOffset;
    }

    /**
     * Remembers the given offset as the deletion offset.
     * 
     * @param offset
     *            the offset
     */
    private void rememberDeletedOffset(int offset) {
        fDeleteOffset = offset;
    }

    /**
     * Creates the minimal region containing all partition changes using the
     * remembered offset, end offset, and deletion offset.
     * 
     * @return the minimal region containing all the partition changes
     */
    private IRegion createRegion() {
    
        if (fDeleteOffset == -1) {
            if (fStartOffset == -1 || fEndOffset == -1)
                return null;
            return new Region(fStartOffset, fEndOffset - fStartOffset);
        } else if (fStartOffset == -1 || fEndOffset == -1) {
            return new Region(fDeleteOffset, 0);
        } else {
            int offset = Math.min(fDeleteOffset, fStartOffset);
            int endOffset = Math.max(fDeleteOffset, fEndOffset);
            return new Region(offset, endOffset - offset);
        }


    }

    /**
     * Called when the document content changes. Updates partitions based on the
     * area of text that has changed.
     */
    public IRegion documentChanged2(DocumentEvent e) {
        try {
            // Grab a reference to the document
            IDocument d = e.getDocument();
            // Grab all the positions in the document
            Position[] category = d.getPositions(fPositionCategory);
            // Get the line where the document event started
            IRegion line = d.getLineInformationOfOffset(e.getOffset());
            // Initialize the reparse start position to the start of the line
            int reparseStart = line.getOffset();
            // Initialize the start of the reparse to before the start of the doc
            int partitionStart = -1;
            // Initialize the end of the partition to the same as the start
            int partitionEnd = partitionStart;
            // Initialize the content type of the start partition to null
            String contentType = null;
            // Initialize the content type of the next partition 
            String nextPartitionType = null;
            // Initialize the length of the text change.
            int newLength = e.getText() == null ? 0 : e.getText().length();
            // Find the index in the position array where a new position with 
            // this offset would be inserted
            
            //System.out.println("Got document event at " + e.getOffset() + " of length " + e.getLength());
            
            /*
             * This should be the index of the next partition that starts
             * that starts after the event offset. Used for figuring out
             * where to start reparsing partitions that may have changed.
             */
            int first = d.computeIndexInCategory(fPositionCategory,
                    e.getOffset());
            
            // Check if we are starting from somewhere in the middle of the document
            if (first > 0) {
                
                
                //Get the partition that was affected by this change.
                CFEPartition activePartition = (CFEPartition) category[first - 1];
                
                String partitionType = activePartition.getType(); 
                
                
                /*
                 * If the event occurred at the end of a start tag 
                 * jump back to the beginning of the tag
                 */
                if (partitionType.equals(CFPartitionScanner.CF_START_TAG_END)) {
                    --first;
                    activePartition = (CFEPartition) category[first - 1];
                    if (!activePartition.getType().equals(CFPartitionScanner.CF_START_TAG_BEGIN)) {
                        --first;
                        activePartition = (CFEPartition) category[first - 1];
                    }
                } else {
	                /*
	                 * If we're in the middle of a tag we want to jump back to
	                 * the beginning
	                 */
	                if (partitionType.equals(CFPartitionScanner.CF_BOOLEAN_STATEMENT) 
	                        || partitionType.equals(CFPartitionScanner.CF_EXPRESSION)
	                        || partitionType.equals(CFPartitionScanner.CF_SET_STATEMENT)
	                        || partitionType.equals(CFPartitionScanner.CF_TAG_ATTRIBS) ) {
	                    --first;
	                    activePartition = (CFEPartition) category[first - 1];
	                }
                }
                
                /*
                 * If this isn't the first partition in the document get the
                 * next partition info from the partition before this one.
                 */ 
                if (first - 2 > 0) {
                    CFEPartition prevPartition = (CFEPartition) category[first - 2];
                    nextPartitionType = prevPartition.getNextPartitionType();
                }

                
                contentType = IDocument.DEFAULT_CONTENT_TYPE;
                
                --first;
                
                // Set the start position to the start of the partition.
                partitionStart = activePartition.getOffset();
                partitionEnd = activePartition.getOffset() + activePartition.getLength();
                
                // Set the content type to the type of the partition
                // contentType = activePartition.getType();
                
                
                /*
                 * If we're inside a partition that can have nested items we
                 * want to reparse from the start of the partition rather than 
                 * from the offset of the line where the change occured.
                 */ 
                if (contentType.equals(CFPartitionScanner.CF_COMMENT)
                        || contentType.equals(CFPartitionScanner.HTM_COMMENT)) {
                    //System.out.println("Reparsing from start of comment block " + partitionStart);
                    reparseStart = partitionStart;
                    /*
                     * Make sure we set the content type to a cf comment.
                     * If we dont' do that then adding a - to a <!-- will
                     * result in the scanner running the check for html comment
                     * first.
                     */
                    contentType = CFPartitionScanner.CF_COMMENT;
                    //fDocument.removePosition(fPositionCategory,new Position(partition.offset,partition.length));
                }

            }

            /*
             * Update the positions in the doc
             */
            fPositionUpdater.update(e);
            /*
             * loop from the start position to the end of the partition array
             * If we find a partition that should be deleted mark it as such
             * and break out of  the loop.
             * Not sure why it breaks after finding the first deleted position.
             */
            for (int i = first; i < category.length; i++) {
                Position p = category[i];
                CFEPartition cfp = (CFEPartition)p;
                if (p.isDeleted) {
                    rememberDeletedOffset(e.getOffset());
                    break;
                }
            }
            /*
             * Now that we've removed partitions we want to get them from the
             * document again.
             */
            category = d.getPositions(fPositionCategory);
            
            fScanner.setPartialRange(d, reparseStart, d.getLength()
                    - reparseStart, contentType, partitionStart);

            int lastScannedPosition = reparseStart;
            IToken token = fScanner.nextToken();
            
            int lastOffset = -1;
            

            while (!token.isEOF()) {
                lastOffset = fScanner.getTokenOffset();
                contentType = getTokenContentType(token);
                if (contentType != null) {
                    //System.out.println("Found token of type " + contentType + " at offset " + fScanner.getTokenOffset() + " of length " + fScanner.getTokenLength());
                }


                if (!isSupportedContentType(contentType)) {
                    token = fScanner.nextToken();
                    continue;
                }


                int start = fScanner.getTokenOffset();
                int length = fScanner.getTokenLength();
                // Find out where the scanner got up to with the last scan.
                lastScannedPosition = start + length - 1;

                /*
                 * Remove all affected positions
                 */ 
                while (first < category.length) {
                    // Get the next partition
                    CFEPartition p = (CFEPartition) category[first];
                   
                    // Check if this partition needs to be removed.
                    if (lastScannedPosition >= p.offset + p.length
                            || (p.overlapsWith(start, length) && (!d
                                    .containsPosition(fPositionCategory, start,
                                            length) || !contentType.equals(p
                                    .getType())))) {
                        /*
                         * Don't delete it if it's a pseudo partition.
                         * Pseudo partitions have the same next type 
                         * as themselves
                         */
                        if (!p.getType().equals(p.getNextPartitionType())) {
                            rememberRegion(p.offset, p.length);
	                        d.removePosition(fPositionCategory, p);
	                        
                        } else {
                            //System.out.println("Found pseudo partition from " + p.offset + " with length " + p.length + " start is " + start );
                                if (p.offset+p.length > start) {
                                    int oldLength = p.length;
                                    p.length = start - p.offset;
                                } else if (p.offset + p.length < start) {
                                    p.length = start - p.offset;
                                } else {
                                }
                                if (p.length < 0) {
                                    rememberRegion(p.offset, p.length);
        	                        d.removePosition(fPositionCategory, p);
                                }
                        }
                        ++first;

                    } else
                        break;
                }

                // if position already exists and we have scanned at least the
                // area covered by the event, we are done
                if (d.containsPosition(fPositionCategory, start, length)) {
                    if (lastScannedPosition >= e.getOffset() + newLength) {
                        return createRegion();
                    }
                    ++first;
                } else {
                    
                    //System.out.println("Calling handleToken.");
                    handleToken(token, getTokenContentType(token),nextPartitionType);
                    
                    rememberRegion(start, length);
                }
                token = fScanner.nextToken();
            }

            // remove all positions behind lastScannedPosition since there
            // aren't any further types
            if (lastScannedPosition != reparseStart) {
                // if this condition is not met, nothing has been scanned
                // because of a deletion
                ++lastScannedPosition;
            }
            first = d.computeIndexInCategory(fPositionCategory,
                    lastScannedPosition);

            CFEPartition p;
            while (first < category.length) {
                p = (CFEPartition) category[first++];
                d.removePosition(fPositionCategory, p);
                rememberRegion(p.offset, p.length);
            }

        } catch (BadPositionCategoryException x) {
            // should never happen on connected documents
        } catch (BadLocationException x) {
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return createRegion();
    }

    /**
     * Returns the position in the partitoner's position category which is close
     * to the given offset. This is, the position has either an offset which is
     * the same as the given offset or an offset which is smaller than the given
     * offset. This method profits from the knowledge that a partitioning is a
     * ordered set of disjoint position.
     * 
     * @param offset
     *            the offset for which to search the closest position
     * @return the closest position in the partitioner's category
     */
    protected CFEPartition findClosestPosition(int offset) {

        try {

            int index = fDocument.computeIndexInCategory(fPositionCategory,
                    offset);
            Position[] category = fDocument.getPositions(fPositionCategory);

            if (category.length == 0)
                return null;

            if (index < category.length) {
                if (offset == category[index].offset)
                    return (CFEPartition) category[index];
            }

            if (index > 0)
                index--;

            return (CFEPartition) category[index];

        } catch (BadPositionCategoryException x) {
        } catch (BadLocationException x) {
        }

        return null;
    }

    /*
     * @see IDocumentPartitioner#getContentType(int)
     */
    public String getContentType(int offset) {

        CFEPartition p = findClosestPosition(offset);
        if (p != null && p.includes(offset))
            return p.getType();

        return IDocument.DEFAULT_CONTENT_TYPE;
    }

    /*
     * @see IDocumentPartitioner#getPartition(int)
     */
    public ITypedRegion getPartition(int offset) {

        try {

            Position[] category = fDocument.getPositions(fPositionCategory);

            if (category == null || category.length == 0)
                return new TypedRegion(0, fDocument.getLength(),
                        IDocument.DEFAULT_CONTENT_TYPE);

            int index = fDocument.computeIndexInCategory(fPositionCategory,
                    offset);

            if (index < category.length) {

                CFEPartition next = (CFEPartition) category[index];

                if (offset == next.offset)
                    return new TypedRegion(next.getOffset(), next.getLength(),
                            next.getType());

                if (index == 0)
                    return new TypedRegion(0, next.offset,
                            IDocument.DEFAULT_CONTENT_TYPE);

                CFEPartition previous = (CFEPartition) category[index - 1];
                if (previous.includes(offset))
                    return new TypedRegion(previous.getOffset(), previous
                            .getLength(), previous.getType());

                int endOffset = previous.getOffset() + previous.getLength();
                return new TypedRegion(endOffset, next.getOffset() - endOffset,
                        IDocument.DEFAULT_CONTENT_TYPE);
            }

            CFEPartition previous = (CFEPartition) category[category.length - 1];
            if (previous.includes(offset))
                return new TypedRegion(previous.getOffset(), previous
                        .getLength(), previous.getType());

            int endOffset = previous.getOffset() + previous.getLength();
            return new TypedRegion(endOffset,
                    fDocument.getLength() - endOffset,
                    IDocument.DEFAULT_CONTENT_TYPE);

        } catch (BadPositionCategoryException x) {
        } catch (BadLocationException x) {
        }

        return new TypedRegion(0, fDocument.getLength(),
                IDocument.DEFAULT_CONTENT_TYPE);
    }

    /*
     * @see IDocumentPartitioner#computePartitioning(int, int)
     */
    public ITypedRegion[] computePartitioning(int offset, int length) {
        return computePartitioning(offset, length, false);
    }

    /*
     * @see IDocumentPartitioner#getLegalContentTypes()
     */
    public String[] getLegalContentTypes() {
        return fLegalContentTypes;
    }

    /**
     * Returns whether the given type is one of the legal content types.
     * 
     * @param contentType
     *            the content type to check
     * @return <code>true</code> if the content type is a legal content type
     */
    protected boolean isSupportedContentType(String contentType) {
        if (contentType != null) {
            for (int i = 0; i < fLegalContentTypes.length; i++) {
                if (fLegalContentTypes[i].equals(contentType))
                    return true;
            }
        }

        return false;
    }

    /**
     * Returns a content type encoded in the given token. If the token's data is
     * not <code>null</code> and a string it is assumed that it is the encoded
     * content type.
     * 
     * @param token
     *            the token whose content type is to be determined
     * @return the token's content type
     */
    protected String getTokenContentType(IToken token) {
        Object data = token.getData();
        if (data != null)
            return data.toString();
        return null;
    }

    /* zero-length partition support */

    /*
     * @see org.eclipse.jface.text.IDocumentPartitionerExtension2#getContentType(int)
     * @since 3.0
     */
    public String getContentType(int offset, boolean preferOpenPartitions) {
        return getPartition(offset, preferOpenPartitions).getType();
    }

    /*
     * @see org.eclipse.jface.text.IDocumentPartitionerExtension2#getPartition(int)
     * @since 3.0
     */
    public ITypedRegion getPartition(int offset, boolean preferOpenPartitions) {
        ITypedRegion region = getPartition(offset);
        if (preferOpenPartitions) {
            if (region.getOffset() == offset
                    && !region.getType().equals(IDocument.DEFAULT_CONTENT_TYPE)) {
                if (offset > 0) {
                    region = getPartition(offset - 1);
                    if (region.getType().equals(IDocument.DEFAULT_CONTENT_TYPE))
                        return region;
                }
                return new TypedRegion(offset, 0,
                        IDocument.DEFAULT_CONTENT_TYPE);
            }
        }
        return region;
    }

    /*
     * @see org.eclipse.jface.text.IDocumentPartitionerExtension2#computePartitioning(int,
     *      int, boolean)
     * @since 3.0
     */
    public ITypedRegion[] computePartitioning(int offset, int length, boolean includeZeroLengthPartitions) {
		List list= new ArrayList();
		//System.out.println("Computing partitioning from " + offset + " to " + Integer.toString(offset +length));
		try {
			
			int endOffset= offset + length;
			
			Position[] category= fDocument.getPositions(fPositionCategory);
			CFEPartition previous= null, current= null;
			int start, end, gapOffset;
			Position gap= new Position(0);
			
			int startIndex= getFirstIndexEndingAfterOffset(category, offset);
			int endIndex= getFirstIndexStartingAfterOffset(category, endOffset);
			//System.out.println("Start index is " + startIndex + " end index is " + endIndex);
			for (int i= startIndex; i < endIndex; i++) {
				
				current= (CFEPartition) category[i];
				/*
				
				*/
			    if (previous != null) {
			        gapOffset= previous.getOffset() + previous.getLength();
			    } else {
			        gapOffset = 0;
			    }

				gap.setOffset(gapOffset);
				gap.setLength(current.getOffset() - gapOffset);
				
				if ((includeZeroLengthPartitions && overlapsOrTouches(gap, offset, length)) || 
						(gap.getLength() > 0 && gap.overlapsWith(offset, length))) {
					start= Math.max(offset, gapOffset);
					end= Math.min(endOffset, gap.getOffset() + gap.getLength());
					//System.out.println("Adding a new region of type " + current.getNextPartitionType() + " from " + start + " to " + end + " Current is " + current.getType());
					list.add(new TypedRegion(start, end - start, current.getNextPartitionType()));
					
				}
				
				if (current.overlapsWith(offset, length)) {
					start= Math.max(offset, current.getOffset());
					end= Math.min(endOffset, current.getOffset() + current.getLength());
					list.add(new TypedRegion(start, end - start, current.getType()));
					//System.out.println("Added a new region of type " + current.getType() + " from " + start + " to " + end);
				}
				
				previous = current;
			}
			
			if (previous != null) {
				gapOffset= previous.getOffset() + previous.getLength();
				gap.setOffset(gapOffset);
				gap.setLength(fDocument.getLength() - gapOffset);
				if ((includeZeroLengthPartitions && overlapsOrTouches(gap, offset, length)) ||
						(gap.getLength() > 0 && gap.overlapsWith(offset, length))) {
					start= Math.max(offset, gapOffset);
					end= Math.min(endOffset, fDocument.getLength());
					list.add(new TypedRegion(start, end - start, IDocument.DEFAULT_CONTENT_TYPE));
				}
			}
			
			if (list.isEmpty())
				list.add(new TypedRegion(offset, length, IDocument.DEFAULT_CONTENT_TYPE));
				
		} catch (BadPositionCategoryException x) {
		} catch (Exception e) {
		    //e.printStackTrace();
		}
		
		TypedRegion[] result= new TypedRegion[list.size()];
		list.toArray(result);
		return result;
	}

    /**
     * Returns <code>true</code> if the given ranges overlap with or touch
     * each other.
     * 
     * @param gap
     *            the first range
     * @param offset
     *            the offset of the second range
     * @param length
     *            the length of the second range
     * @return <code>true</code> if the given ranges overlap with or touch
     *         each other
     * @since 3.0
     */
    private boolean overlapsOrTouches(Position gap, int offset, int length) {
        return gap.getOffset() <= offset + length
                && offset <= gap.getOffset() + gap.getLength();
    }

    /**
     * Returns the index of the first position which ends after the given
     * offset.
     * 
     * @param positions
     *            the positions in linear order
     * @param offset
     *            the offset
     * @return the index of the first position which ends after the offset
     * 
     * @since 3.0
     */
    private int getFirstIndexEndingAfterOffset(Position[] positions, int offset) {
        int i = -1, j = positions.length;
        while (j - i > 1) {
            int k = (i + j) >> 1;
            Position p = positions[k];
            if (p.getOffset() + p.getLength() > offset)
                j = k;
            else
                i = k;
        }
        return j;
    }

    /**
     * Returns the index of the first position which starts at or after the
     * given offset.
     * 
     * @param positions
     *            the positions in linear order
     * @param offset
     *            the offset
     * @return the index of the first position which starts after the offset
     * 
     * @since 3.0
     */
    private int getFirstIndexStartingAfterOffset(Position[] positions,
            int offset) {
        int i = -1, j = positions.length;
        while (j - i > 1) {
            int k = (i + j) >> 1;
            Position p = positions[k];
            if (p.getOffset() >= offset)
                j = k;
            else
                i = k;
        }
        return j;
    }
}