/*
 * $Id: CFEPartitioner.java,v 1.1 2005-02-11 15:10:02 smilligan Exp $
 * $Revision: 1.1 $
 * $Date: 2005-02-11 15:10:02 $
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

/**
 * @author Stephen Milligan
 * 
 * This is an attempt to create a more flexible and powerful partitioner than
 * the default partitioner.
 * 
 * Should now be pretty reliable, but still needs some performance tweaks in the
 * cleanCompositePartitions(), cleanPsuedoPartitions(), and 
 * repairPseudoPartitions() methods.
 */
public class CFEPartitioner implements IDocumentPartitioner,
        IDocumentPartitionerExtension, IDocumentPartitionerExtension2 {

    private final static String CONTENT_TYPES_CATEGORY = "__content_types_category";

    /** The partitioner's scanner */
    protected CFPartitionScanner fScanner;

    /** The legal content types of this partitioner */
    protected String[] fLegalContentTypes;

    /** The partitioner's document */
    protected IDocument fDocument;

    /** The document length before a document change occurred */
    protected int fPreviousDocumentLength;

    /** The position updater used to for the  updating of partitions */
    protected PositionUpdater fPositionUpdater;

    /** The offset at which the first changed partition starts */
    protected int fStartOffset;

    /** The offset at which the last changed partition ends */
    protected int fEndOffset;

    /** The offset at which a partition has been deleted */
    protected int fDeleteOffset;
    
    /** The offset at which a reparse should begin */
    private int fReparseStart = -1;
    
    /** The offset at which a reparse should end */
    private int fReparseEnd = -1;

    /** The position index of the first partition affected by a document change */
    private int fReparseStartIndex;

    /** The position index of the last partition affected by a document change */
    private int fReparseEndIndex;

    
    /**
     * The text to be inserted by the document event 
     */
    
    private String fInsertedText = "";
    
    /**
     * The text to be deleted by the document event
     */
    
    private String fDeletedText = "";
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
    public CFEPartitioner(IPartitionTokenScanner scanner,
            String[] legalContentTypes) {
        fScanner = (CFPartitionScanner)scanner;
        fLegalContentTypes = legalContentTypes;
        fPositionCategory = CONTENT_TYPES_CATEGORY + hashCode();
        fPositionUpdater = new PositionUpdater(fPositionCategory);
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
        try {
            IToken token = fScanner.nextToken();

            while (!token.isEOF()) {

                String contentType = getTokenContentType(token);
                if (isSupportedContentType(contentType)) {
                    handleToken(token, contentType);
                }

                token = fScanner.nextToken();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        repairPseudoPartitions();

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
     * @param token The token that was found
     * @param contentType The content type of the token
     * @return
     */
    private String handleToken(IToken token, String contentType) {
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
                
                //System.out.println("Found a tag token. " + rawData);
                if (!fDocument.containsPosition(fPositionCategory,start,length)) {
                    // Add a partition for the start part of the tag.
                     p = new CFEPartition(start, length, data.getStartPartitionType());
                     p.setTagName(data.tagName());
                    fDocument.addPosition(fPositionCategory, p);
                    
                }
                if (!data.isCloser()) {
                    if (p != null) {
                        p.setNextPartitionType(data.getMidPartitionType());
                        p.setStartPartition(true);
                    }
                    if (data.fHasMid) {
                        
                        indexOffset++;
                        start = start + data.getFirstPartitionEnd();
                        length = data.getMidPartitionEnd()-data.getFirstPartitionEnd();
                        p = new CFEPartition(start, length, data.getMidPartitionType());
                        p.setNextPartitionType(p.getType());
                        p.setMidPartition(true);
                        p.setTagName(data.tagName());
                        if (!fDocument.containsPosition(fPositionCategory,p.offset,p.length)) {
                            fDocument.addPosition(fPositionCategory, p);
                            //System.out.println("Added mid partition of type " + p.getType() + " from " + p.offset + " to " + Integer.toString(p.offset + p.length));
                        }
                    } else {
                        //System.out.println("Tag has no mid part.");
                    }
                    if (data.fHasEnd) {
                        indexOffset++;
                        start = fScanner.getTokenOffset() + data.getMidPartitionEnd();
                        length = data.getRawData().length()- data.getMidPartitionEnd();
	                    if (!fDocument.containsPosition(fPositionCategory,start,length)) {
		                    p = new CFEPartition(start, length, data.getEndPartitionType());
		                    p.setEndPartition(true);
	                        p.setTagName(data.tagName());
	                        fDocument.addPosition(fPositionCategory, p);
	                    }

                    } else {
                        //System.out.println("Tag has no end part.");
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
    private String closesPseudoPartition(String tag) {
        if (tag == null) {
            return null;
        }
        Set keys = fPseudoPartitions.keySet();
        Object[] tags = keys.toArray();
        for (int i = 0; i < tags.length; i++)
        {
            String closer = "</" + tags[i].toString();
            if (tag.startsWith(closer)
                    && (tag.length() == closer.length()
                    || !Character.isLetter(tag.charAt(closer.length())))) {
                return fPseudoPartitions.getProperty(tags[i].toString());
            }
        }
        return null;
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
    
    /**
     * 
     * @param offset - Offset from which to start looking for partitions
     * @param data - The raw data from an instance of TagData. Used to 
     * determine if the tag is a closer or opener
     * @param indexOffset - Indicates the difference between the first and
     * end partitions spanned by the TagData instance. 
     * @return The next partition type.
     */
    private String getNextPartitionType(int offset,String data,int indexOffset) {
        try {
            // Does this open a partition
            boolean opener = false;
            // Does this close a partition
            boolean closer = false;
            //System.out.println("Getting next partition for " + data);
            /*
             * If this returns anything other than null then this opens
             * a partition of the type returned.
             */ 
            String next = opensPseudoPartition(data);
            if (next != null) {
                opener = true;
                //System.out.println("Found a pseudo partition opener");
            }
            /*
             * If this returns anything other than null then this closes
             * a partition of the type returned.
             */
            String closes = closesPseudoPartition(data);
            if (closes != null) {
                next = null;
                closer = true;
                //System.out.println("Found a pseudo partition closer");
            }
            
            Position[] category = fDocument.getPositions(fPositionCategory);
            int index = fDocument.computeIndexInCategory(fPositionCategory,offset);
            /*
             * If we're dealing with an opening tag the first partition 
             * will be the tag name bit and the end partition will be the ">"
             * bit. Otherwise they will both span the whole token that was
             * found by the scanner.
             */
            CFEPartition firstPartition = (CFEPartition)category[index];
            CFEPartition endPartition = (CFEPartition)category[index+indexOffset];
            //System.out.println("End partition set to " + endPartition.getType() + " indexOffset is " + indexOffset);
            /*
             * Assign opener and closer to end partition.
             */
            if (opener) {
                endPartition.setOpensPartition(next);
                //System.out.println("Next partition type set to " + next + " for partition " + endPartition.getType());
            }
            if (closer) {
                endPartition.setClosesPartition(closes);
                //System.out.println("Next partition type set to " + next + " for partition " + endPartition.getType());
            }

       
            /*
             * Make sure we propogate any pseudo partitions along the
             * document.
             */
            if (index > 0 
                    && next == null 
                    && !closer) {
	            CFEPartition prevPartition = (CFEPartition)category[index-1];
                next = prevPartition.getNextPartitionType();
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

        /*
         * Calculate the extent of the document change 
         * before the change occurs. These fields are 
         * used in documentChanged2 to optimize the deletion
         * and re-creation of partitions. Since all partitions
         * start with a < and end with a > we only need to
         * repartition if one of those has been added or removed
         * or if the change starts or ends with a - (could change
         * a comment).
         * 
         */
        fDeletedText = "";
        fInsertedText = "";
        try {
          int textLength = 0;
          if (e.fText != null) {
              textLength = e.fText.length();
          }
	        if (e.fLength > 0) {
	            fDeletedText =  fDocument.get(e.fOffset,e.fLength);
	        } 
	        if (textLength > 0){
	            fInsertedText = e.fText;
	        }
        } catch (Exception ex) {
            // do nothing.
        }
        
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
     * Ensures that any partitions that are affected by the given event
     * are removed from the document.
     * @param e
     */
    private void removeAffectedPartitions(DocumentEvent e) {
        try {
	        Position[] partitions = fDocument.getPositions(fPositionCategory);
	        
	        fReparseStartIndex = partitions.length-1;
	        fReparseEndIndex = 0;
	        
	        fPositionUpdater.update(e);
	        
	        cleanCompositePartitions(partitions,e);
	        
	        cleanPseudoPartitions(partitions,e);
	        
	        for (int i=0;i<partitions.length;i++) {
	            CFEPartition p = (CFEPartition)partitions[i];
	            if (p.isDeleted) {
	                fDocument.removePosition(fPositionCategory,p);
	                fReparseEndIndex--;
	            }
	        }
	        
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    /** 
     * Cleans up and deletes any composite partitions that are currently incorrect.
     * Composite partitions are typically 
     * start tag begin (<cfloop), 
     * start tag mid (from="1" to="10" index="i") 
     * and start tag end (>).
     * 
     * NOTE: This currently rescans every partition in the document, so it 
     * might need to be refactored to only scan a subset if performance is
     * an issue in larger documents.
     */
    private void cleanCompositePartitions(Position[] partitions, DocumentEvent e) {

            CFEPartition prev = null;
            CFEPartition current = null;
            
            for (int i=0;i<partitions.length;i++) {
                current = (CFEPartition)partitions[i];
                /*
                 * If this partition was affected by the event we need 
                 * to delete it.
                 */
                if (overlapsOrTouches(current,e.getOffset(),e.getLength())
                        && !isPseudoPartition(current.getType())) {
                    //System.out.println("Deleted partition " + current.getType() + " " + current.getOffset() + ":" + Integer.toString(current.getOffset() + current.getLength()) + " overlapping");
                    current.delete();
                    updateReparseOffsets(current);
                }
                /*
                 * If the change occured in a start tag we want to remove any
                 * affected partitions
                 */
                if (current.isStartPartition()
                        || current.isMidPartition()
                        || current.isEndPartition()) {

                    if (prev != null) {
                        /*
                         * If it's a mid partition make sure the previous
                         * one is a start partition that hasn't been deleted.
                         */
                        if (current.isMidPartition() 
                                && (prev.isDeleted() 
                                        || !prev.isStartPartition())) {
                            //System.out.println("Deleted partition " + current.getType() + " " + current.getOffset() + ":" + Integer.toString(current.getOffset() + current.getLength()) + " start is invalid.");
                            current.delete();
                            updateReparseOffsets(current);
                            
                        }
                        /*
                         * If it's an end partition make sure the previous one
                         * is a start or mid partition that hasn't been
                         * deleted. 
                         */
                        if (current.isEndPartition()
                                && (prev.isDeleted()
                                || !(prev.isStartPartition() 
                                        || prev.isMidPartition()))) {
                            //System.out.println("Deleted partition " + current.getType() + " " + current.getOffset() + ":" + Integer.toString(current.getOffset() + current.getLength()) + " start is invalid.");
                            current.delete();
                            updateReparseOffsets(current);
                        }
                    }
                }
                prev = current;
            }
            
    }
    
    
    /**
     * Removes any pseudo partitions that have become orphans as a result
     * of partition deletions.
     *
     */
    private void cleanPseudoPartitions(Position[] partitions, DocumentEvent e) {

            // Clean up any pseudo partitions.
            for (int i=0;i<partitions.length;i++) {
                CFEPartition p = (CFEPartition)partitions[i];
                if (p.isDeleted) {
                    if (p.getClosesPartitionType() != null) {
                        //System.out.println("Deleted closer found for " + p.getClosesPartitionType() + ". Need to delete all pseudo partitions until opener is found.");
                        String pType = p.getClosesPartitionType();
                        for (int j=i;j>0;j--) {
                            CFEPartition tmp = (CFEPartition)partitions[j];
                            if (pType.equals(tmp.getOpensPartitionType())) {
                                break;
                            } else if(tmp.getType().equals(pType)) {
                                tmp.delete();
                                updateReparseOffsets(tmp);
                            }
                                                                    
                        }
                    }
                    if (p.getOpensPartitionType() != null) {
                        //System.out.println("Deleted opener found for " + p.getOpensPartitionType() + ". Need to delete all pseudo partitions until closer is found.");
                        String pType = p.getOpensPartitionType();
                        for (int j=i;j<partitions.length;j++) {
                            CFEPartition tmp = (CFEPartition)partitions[j];
                            if (pType.equals(tmp.getClosesPartitionType())) {
                                break;
                            } else if(tmp.getType().equals(pType)) {
                                tmp.delete();
                                updateReparseOffsets(tmp);
                            }
                                                                    
                        }
                    }
                }
                // Merge any adjacent pseudo partitions.
                else if (i+1 < partitions.length 
                        && isPseudoPartition(p.getType())
                        && ((CFEPartition)partitions[i+1]).getType().equals(p.getType()) ) { 
                    CFEPartition tmp = (CFEPartition)partitions[i+1];
                    tmp.offset = p.offset;
                        
                }
            }

    }
    
    /** 
     * Updates the reparse offsets to make sure they span the given partition 
     * @param affectedPartition
     */
    private void updateReparseOffsets(CFEPartition affectedPartition) {

        if (fReparseStart > affectedPartition.offset) {
            fReparseStart = affectedPartition.offset;
        }
        if (fReparseEnd < affectedPartition.offset + affectedPartition.length) {
            fReparseEnd = affectedPartition.offset + affectedPartition.length;
        }
    }
    
    /**
     * Ensures that all psuedo partitions are filling the right gaps.
     *
     */
    private void repairPseudoPartitions() {
        
        try {
            Position[] partitions = fDocument.getPositions(fPositionCategory);
            CFEPartition current = null;
            CFEPartition previous = null;
            String activePseudoPartition = null;
            String closingPartitionType = null;
            boolean isAttributePartition = false;
            for (int i=0;i<partitions.length;i++) {
                current = (CFEPartition)partitions[i];
                
                if (previous != null) {
                    // Is there a gap.
                    if (previous.offset + previous.length < current.offset) {
                        /*
                         * If the current partition is the right type stretch it
                         * to fill the gap.
                         */ 
                        if (current.isPseudoPartition()) {
                            int oldOffset = current.offset;
                            current.offset = previous.offset + previous.length;
                            current.length = oldOffset + current.length - current.offset;
                        }
                        /*
                         * Create a new pseudo partition if there should be one.
                         */
                        else if (activePseudoPartition != null){
                            
                            
                            int start = previous.offset+previous.length;
                            int length = current.offset - start;
                            if (isAttributePartition) {
	                            String s = fDocument.get(start,length);
	                            boolean singleQuoted = false;
	                            boolean doubleQuoted = false;
	                            for (int j=0;j<s.length();j++) {
	                                char c = s.charAt(j);
	                                if (c == '\'') {
	                                    singleQuoted = !singleQuoted;
	                                }
	                                if (c == '"') {
	                                    doubleQuoted = !doubleQuoted;
	                                }
	                                if (c == '>' 
	                                    && !singleQuoted 
	                                    && !doubleQuoted) {

	                                    CFEPartition p = new CFEPartition(start+j,1,closingPartitionType);
	    	                            fDocument.addPosition(fPositionCategory,p);
	                                    closingPartitionType = null;
	                                    length = j-1;
	                                    break;
	                                }
	                            }
                            }
                            if (length > 0) {
	                            CFEPartition p = new CFEPartition(start,length,activePseudoPartition);
	                            fDocument.addPosition(fPositionCategory,p);
	                            if (!isAttributePartition) {
	                                //System.out.println("Created pseudo partition from " + start + " to " + Integer.toString(start+length));
	                                p.setPseudoPartition(true);
	                            } else {
	                                //System.out.println("Created attribute partition from " + start + " to " + Integer.toString(start+length));
	                                p.setMidPartition(true);
	                            }
                            }

                            if (closingPartitionType == null 
                                    && isAttributePartition) {
                                activePseudoPartition = null;
                                isAttributePartition = false;
                            }
                        }
                    } else if (previous.offset + previous.length > current.offset 
                            && previous.isPseudoPartition()) {
                        previous.length = current.offset - previous.offset;
                    }
                }

                
                 if (isAttributePartition 
                        && current.getType().equals(closingPartitionType)) {
                    isAttributePartition = false;
                    closingPartitionType = null;
                    activePseudoPartition = null;
                }
                 
                if (current.getOpensPartitionType() != null) {
                    activePseudoPartition = current.getOpensPartitionType();
                    isAttributePartition = false;
                }
                
                if (activePseudoPartition == null
                        && current.isStartPartition()
                        && !current.getType().startsWith("__cf")) {
                    activePseudoPartition = current.getNextPartitionType();
                    closingPartitionType = current.getType().replaceFirst("_begin","_end");
                    isAttributePartition = true;
                } 
                if (current.getClosesPartitionType() != null) {
                    activePseudoPartition = null;
                }
                
                if (current.length == 0) {
                    fDocument.removePosition(fPositionCategory,current);
                } else {
                    previous = current;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Determines if the update could have changed any document partitions.
     * @param e
     * @return
     */
    private boolean updateCouldChangePartitions(DocumentEvent e) {

        int changeDelta = fInsertedText.length() - fDeletedText.length();
        /*
         * If the deleted or inserted text contains a partition
         * start or end character we can just return true without
         * scanning back or forward.
         */  
        
        if (fDeletedText.indexOf('>') >= 0
            || fDeletedText.indexOf('<') >= 0
            || fInsertedText.indexOf('>') >= 0
            || fInsertedText.indexOf('<') >= 0) {
            return true;
        }
        
        String doc = fDocument.get();
        /*
         * Read back from the offset looking for either a < or a 
         * character that couldn't be part of a rule start sequence.
         */ 
        for (int i=e.fOffset-1;i>=0;i--) {
            char c = doc.charAt(i);
            if (!isValidPartitionStartChar(doc.charAt(i))) {
                break;
            }
            if (c == '<') {
                return true;
            }
        }


        /*
         * Read forward from the offset looking for either a < or a 
         * character that couldn't be part of a rule start sequence.
         */
        if (e.fOffset + fDeletedText.length() >= doc.length()) {
            return false;
        }
        
        for (int i = e.fOffset + changeDelta;i<doc.length();i++) {
            char c = doc.charAt(i);
            if (!isValidPartitionEndChar(c)) {
                break;
            }
            if (c == '>' 
                || c == '<') {
                return true;
            }
            
        }
        
        return false;
        
    }
    

    private static final boolean isValidPartitionStartChar(char c) {

        if (c == '<') {
            return true;
        }
        if (Character.isLetterOrDigit(c)) {
            return true;
        }
        if (c == '_') {
            return true;
        }
        if (c == '-') {
            return true;
        }
        if (c == '!') {
            return true;
        }
        if (c == '/') {
            return true;
        }
        
        return false;
    }
    

    private static final boolean isValidPartitionEndChar(char c) {

        if (c == '>') {
            return true;
        }
        if (Character.isLetterOrDigit(c)) {
            return true;
        }
        if (c == '-') {
            return true;
        }
        if (c == '<') {
            return true;
        }
        
        return false;
    }
    
    /**
     * Updates the document partition offsets affected by the given
     * document change. Resizes the containing partition to the size
     * required by the document change.
     * @param e
     * @return
     */
    private IRegion updatePartitionOffsets(DocumentEvent e) {
        int changeDelta = fInsertedText.length() - fDeletedText.length();
        try {
         
            Position[] partitions = fDocument.getPositions(fPositionCategory);
            for (int i=0;i<partitions.length;i++) {
                if (partitions[i].offset > e.fOffset) {
                    partitions[i].offset += changeDelta;
                } else if (partitions[i].offset + partitions[i].length > e.fOffset) {
                    partitions[i].length += changeDelta;   
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return createRegion();
    }
    
    
    
    
    /**
     * Called when the document content changes. Updates partitions based on the
     * area of text that has changed.
     */
    public IRegion documentChanged2(DocumentEvent e) {
        try {
            // Grab a reference to the document
            IDocument d = e.getDocument();
            
            if (!updateCouldChangePartitions(e)) {
                return updatePartitionOffsets(e);
            }
            
            
            // Grab all the positions in the document
            Position[] category = d.getPositions(fPositionCategory);
            // Get the line where the document event started
            IRegion line = d.getLineInformationOfOffset(e.getOffset());
            // Initialize the reparse start position to the start of the line
            fReparseStart = line.getOffset();
            
            // Initialize the content type of the next partition 
            // Initialize the length of the text change.
            int newLength = e.getText() == null ? 0 : e.getText().length();
            
            fReparseEnd = e.getOffset() + newLength;
            // Find the index in the position array where a new position with 
            // this offset would be inserted
            
            
            // Clean up any partitions affected by the change.
            removeAffectedPartitions(e);
           
           
            /*
             * This should be the index of the next partition that starts
             * that starts after the event offset. Used to limit the extent
             * of reparsing partitions that may have changed.
             */
            int first = d.computeIndexInCategory(fPositionCategory,
                    e.getOffset());
            
            if (fReparseStart == 0 
                    && category.length > first) {
                fReparseEnd = Math.max(fReparseStart,category[first].getOffset()+category[first].getLength());
            }
           
            //System.out.println("Reparsing from " + fReparseStart + " to " + fReparseEnd + " doc length is " + fDocument.getLength());
            fScanner.setPartialRange(d, fReparseStart, d.getLength()
                    - fReparseStart, IDocument.DEFAULT_CONTENT_TYPE, fReparseStart);

            int lastScannedPosition = fReparseStart;
            IToken token = fScanner.nextToken();
            
            int lastOffset = -1;

            String contentType = null;
 
            while (!token.isEOF()) {
                lastOffset = fScanner.getTokenOffset();
                contentType = getTokenContentType(token);


                if (lastOffset > fReparseEnd) {
                    repairPseudoPartitions();
                    return createRegion();
                }
                if (!isSupportedContentType(contentType)) {
                    token = fScanner.nextToken();
                    continue;
                }
                
                //System.out.println("Found token of type " + contentType + " at offset " + fScanner.getTokenOffset() + " of length " + fScanner.getTokenLength());

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
                   
                    //System.out.println("Checking if " + p.getType() + " " + p.offset + ":" + Integer.toString(p.offset+p.length) + " should be deleted.");
                    // Check if this partition needs to be removed.
                    if (lastScannedPosition >= p.offset + p.length
                            || (p.overlapsWith(start, length) && (!d
                                    .containsPosition(fPositionCategory, start,
                                            length) || !contentType.equals(p
                                    .getType())))) {
                        //System.out.println("It should be deleted.");
                        
                        String closes = p.getClosesPartitionType();
                        String opens = p.getOpensPartitionType(); 
                        if (closes != null) {
                            //System.out.println("Rescan found deleted position that closes partitions of type " + closes);
                        }
                        if (opens != null) {
                            //System.out.println("Rescan found deleted position that opens partitions of type " + opens);
                        }
                        
                        /*
                         * Don't delete it if it's a pseudo partition.
                         * Pseudo partitions have the same next type 
                         * as themselves
                         */
                        if (!p.getType().equals(p.getNextPartitionType())) {
                            rememberRegion(p.offset, p.length);
                            //System.out.println("Deleted position " + p.offset + ":" + Integer.toString(p.offset+p.length));
	                        d.removePosition(fPositionCategory, p);
	                        
                        } else {
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
                    //System.out.println("Position already exists from " + start + " to " + Integer.toString(start+length));
                    if (lastScannedPosition >= fReparseEnd) {
                        repairPseudoPartitions();
                        return createRegion();
                    }
                    ++first;
                } else {
                    
                    //System.out.println("Calling handleToken.");
                    handleToken(token, getTokenContentType(token));
                    
                    rememberRegion(start, length);
                }
                token = fScanner.nextToken();
            }

            // remove all positions behind lastScannedPosition since there
            // aren't any further types
            if (lastScannedPosition != fReparseStart) {
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

        repairPseudoPartitions();
        return createRegion();
    }
    
    /**
     * Returns an array of CFEPartitions which intersect the given start and 
     * end offsets
     * @param start
     * @param end
     * @return
     */
    public CFEPartition[] getCFEPartitions(int start, int end) {
        ArrayList list = new ArrayList();
        try {
            Position[] partitions = fDocument.getPositions(fPositionCategory);
            for (int i=0;i<partitions.length;i++) {
                if (partitions[i].offset >= start 
                        && partitions[i].offset <= end) {
                    list.add(partitions[i]);
                }
            }
            CFEPartition[] result = new CFEPartition[list.size()];
            list.toArray(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    
    
    
    private String getStartPartitionName(String partitionType) {
        if (partitionType.startsWith("__htm_") 
                && !partitionType.startsWith("__htm_end")) {
            return CFPartitionScanner.HTM_START_TAG_BEGIN;
        } else if (partitionType.startsWith("__form_")
                && !partitionType.startsWith("__form_end")) {
            return CFPartitionScanner.FORM_START_TAG_BEGIN;
        } else if (partitionType.startsWith("__table_")
                && !partitionType.startsWith("__table_end")) {
            return CFPartitionScanner.TABLE_START_TAG_BEGIN;
        } else if (partitionType.startsWith("__cf_")
                && !partitionType.startsWith("__cf_end")) {
            return CFPartitionScanner.CF_START_TAG_BEGIN;
        }
            
        return null;
    }

    
    
    
    private String getEndPartitionName(String partitionType) {
        if (partitionType.startsWith("__htm_") 
                && !partitionType.startsWith("__htm_end")) {
            return CFPartitionScanner.HTM_START_TAG_END;
        } else if (partitionType.startsWith("__form_")
                && !partitionType.startsWith("__form_end")) {
            return CFPartitionScanner.FORM_START_TAG_END;
        } else if (partitionType.startsWith("__table_")
                && !partitionType.startsWith("__table_end")) {
            return CFPartitionScanner.TABLE_START_TAG_END;
        } else if (partitionType.startsWith("__cf_")
                && !partitionType.startsWith("__cf_end")) {
            return CFPartitionScanner.CF_START_TAG_END;
        }
            
        return null;
    }
    
    /** 
     * Returns a position that inicates the start offset and length of the
     * tag that surrounds the given offset.
     */ 
    public CFEPartition[] getTagPartitions(int offset) {
        
        int start = -1;
        int end = -1;
        String firstPartition = null;
        String lastPartition = null;
        try {
	        CFEPartition cfp = findClosestPosition(offset);
	        if (cfp == null) {
	            return null;
	        }
	        String tagName = cfp.getTagName();
            firstPartition = getStartPartitionName(cfp.getType());
            lastPartition = getEndPartitionName(cfp.getType());
	        if (firstPartition == null || lastPartition == null) {
	            return null;
	        }
	        
	        Position[] positions = fDocument.getPositions(fPositionCategory);
	        int index = getFirstIndexEndingAfterOffset(positions,offset);
	        //System.out.println("Index is " + index + " of " + positions.length);
	        for (int i=Math.max(0,index-1);i<positions.length;i++) {
	            //System.out.println("Looking for end in index " + i);
	            CFEPartition p = (CFEPartition)positions[i];
	            if (p.getType().equals(lastPartition)) {
	                //System.out.println("Found.");
	                end = i;
	                break;
	            }
	        }
	        
	        for (int i=index-1;i>=0;i--) {
	            CFEPartition p = (CFEPartition)positions[i];
	            //System.out.println("Looking for start in index " + i);
	            if (p.getType().equals(firstPartition)) {
	                //System.out.println("Found.");
	                start = i;
	                break;
	            }
	        }
	        
	        if (start == -1 || end == -1 ) {
	            return null;
	        }
	        
	        //System.out.println("Tag starts at index " + start + " and ends at index " + end);
	        CFEPartition[] partitions = new CFEPartition[end-start+1];
	        int i = 0;
	        while (start <= end) {
	           partitions[i] = (CFEPartition)positions[start];
	           start++;
	           i++;
	        }
	        return partitions;
	        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* zero-length partition support */

    /*
     * @see org.eclipse.jface.text.IDocumentPartitionerExtension2#getContentType(int)
     * @since 3.0
     */
    public String getContentType(int offset, boolean preferOpenPartitions) {
        ITypedRegion region = getPartition(offset, preferOpenPartitions);
        /*
        try {
            throw new Exception();
        } catch (Exception e) {
            System.err.println("Region is " + region.getType());
            e.printStackTrace();
        }
        */
        return region.getType();
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
		//System.out.println("Computing partitioning from " + offset + " to "+ Integer.toString(offset +length));
		
		try {
			int endOffset= offset + length;
			if (offset > 0) {
			    offset--;
			}
			Position[] category= fDocument.getPositions(fPositionCategory);
			CFEPartition previous= null, current= null;
			int start, end, gapOffset;
			Position gap= new Position(0);
			
			int startIndex= getFirstIndexEndingAfterOffset(category, offset);
			int endIndex= getFirstIndexStartingAfterOffset(category, endOffset);
			//System.out.println("Start index is " + startIndex + " end index is " + endIndex);
			if (startIndex < category.length) {
				CFEPartition first  = (CFEPartition) category[startIndex];
				length = offset + length - first.getOffset()+1;
				offset = first.getOffset();
			}
			for (int i= startIndex; i < endIndex; i++) {
				
				current= (CFEPartition) category[i];
				//System.out.println("Checking partition " + current.getType() + " " + current.offset + ":" + Integer.toString(current.offset+current.length));
			    if (previous != null) {
			        gapOffset= previous.getOffset() + previous.getLength();
			    } else {
			        gapOffset = 0;
			    }
			    
				gap.setOffset(gapOffset);
				if (current.getOffset() >= gapOffset) {
					gap.setLength(current.getOffset() - gapOffset);
					
					if ((includeZeroLengthPartitions && overlapsOrTouches(gap, offset, length)) || 
							(gap.getLength() > 0 && gap.overlapsWith(offset, length))) {
						start= Math.max(offset, gapOffset);
						end= Math.min(endOffset, gap.getOffset() + gap.getLength());
						//System.out.println("Adding a new region of type " + current.getNextPartitionType() + " from " + start + " to " + end + " Current is " + current.getType());
						String next = null;
						if (previous != null) {
						    previous.getNextPartitionType();
						}
						if (next == null) {
						    next = IDocument.DEFAULT_CONTENT_TYPE;
						}
						list.add(new TypedRegion(start, end - start, next));
						
					}
					
					if (current.overlapsWith(offset, length)) {
						start= Math.max(offset, current.getOffset());
						end= Math.min(endOffset, current.getOffset() + current.getLength());
						list.add(new TypedRegion(start, end - start, current.getType()));
						//System.out.println("Added a new region of type " + current.getType() + " from " + start + " to " + end);
					}
				} else {
				    //System.out.println("current offset less than gap offset.");
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
					//System.out.println("Added a new region of type " + IDocument.DEFAULT_CONTENT_TYPE + " from " + start + " to " + end);
					list.add(new TypedRegion(start, end - start, IDocument.DEFAULT_CONTENT_TYPE));
				}
			}
			
			if (list.isEmpty())
				list.add(new TypedRegion(offset, length, IDocument.DEFAULT_CONTENT_TYPE));
				
		} catch (BadPositionCategoryException x) {
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		TypedRegion[] result= new TypedRegion[list.size()];
		list.toArray(result);
		/*
		for (int i =0;i<result.length;i++) {
		    System.out.println("Got region - " + result[i].getType() + " " + result[i].getOffset() + ":" + Integer.toString(result[i].getOffset()+result[i].getLength()));
		}
		*/
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