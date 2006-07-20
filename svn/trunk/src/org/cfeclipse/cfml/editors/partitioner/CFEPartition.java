/* 
 * $Id: CFEPartition.java,v 1.10 2005/11/11 17:47:40 cybersonic Exp $
 * $Revision: 1.10 $
 * $Date: 2005/11/11 17:47:40 $
 * 
 * Created Jan 18, 2005 2:08:20 PM
 *
 * CFEclipse - The Open Source ColdFusion Development Environment
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
package org.cfeclipse.cfml.editors.partitioner;

import org.eclipse.jface.text.Assert;
import org.eclipse.jface.text.TypedPosition;
/**
 * Class description...
 * 
 * @author Stephen Milligan
 * @version $Revision: 1.10 $
 */
public final class CFEPartition extends TypedPosition {

    /** The type that the next partition should be */
    private String fNextPartitionType = null;
    /** The type that the previous partition should be */
    private String fPreviousPartitionType = null;
    /** The type of partition that this partition opens */
    private String fOpensPseudoPartition = null;
    /** The type of partition that this partition closes */
    private String fClosesPseudoPartition = null;
    
    
    private boolean fStartPartition = false;
    private boolean fMidPartition = false;
    private boolean fEndPartition = false;
    private String fTagName = null;
    private boolean fIsPseudoPartition = false;
    private boolean fIsCloser;
    
    /**
     * @param offset
     * @param length
     * @param type
     */
    public CFEPartition(int offset, int length, String type) {
        super(offset, length, type);
        Assert.isNotNull(type,"Null partition type passed to CFEPartition constructor.");
        //System.out.println("Partition of type " + type + " created from " + offset + " to " + Integer.toString(offset+length));
    }

    /** 
     * Sets the type of partition that should come after this one 
     * @param type - The type of partition that should come next.
     */ 
    public final void setNextPartitionType(String type) { 
        fNextPartitionType = type;
    }
    
    /**
     * 
     * @return The type of partition that should come next 
     * or null if none specified.
     */
    public String getNextPartitionType() { 
        return fNextPartitionType;
    }
    
    /**
     * 
     * @param type - The type of partition that this partition opens.
     */
    public final void setOpensPartition(String type) {
        fOpensPseudoPartition = type;
    }
    
    public final String getOpensPartitionType() {
        return fOpensPseudoPartition;
    }
    
    /**
     * 
     * @param type - The type of partition that this partition closes.
     */
    public final void setClosesPartition(String type) {
        fClosesPseudoPartition = type;
    }
    
    /**
     * 
     * @return The type of partition that is closed by this partition or null
     */
    public final String getClosesPartitionType() {
        return fClosesPseudoPartition;
    }
    
    public final boolean isStartPartition() {
        return fStartPartition;
    }
    
    public final boolean isMidPartition() {
        return fMidPartition;
    }
    
    public final boolean isEndPartition() {
        return fEndPartition;
    }
    
    public final void setStartPartition(boolean value) {
        fStartPartition = value;
    }
    
    public final void setMidPartition(boolean value) {
        fMidPartition = value;
    }
    
    public final void setEndPartition(boolean value) {
        fEndPartition = value;
    }
    
    public final void setTagName(String name) {
        fTagName = name;
    }
    
    public String getTagName() {
        return fTagName;
    }
    
    public void delete() {
        //System.out.println("Deleted partition " + getType() + " (" + getTagName() + ") from " + offset + " to " + Integer.toString(offset + length));
        super.delete();
    }
    
    public void setPseudoPartition(boolean value) {
        fIsPseudoPartition = value;
    }
    
    public boolean isPseudoPartition() {
        return fIsPseudoPartition;
    }

    /**
     * @param value
     */
    public void setCloser(boolean value) {
        this.fIsCloser = value;
    }
    
    /**
     * @return whether this partition is a closer
     */
    public boolean isCloser() {
        return this.fIsCloser;
    }

    /**
     * @return a meningful string representation of this partition
     */
    public String toString() {
    	String info = "";
    	info += getType(); 
	    info += " starting at ";
	    info += getOffset();
	    info += " ending at "; 
	    info += Integer.toString(getOffset() + getLength());
	    if (getTagName() != null) {
	        info += " (";
	        info += getTagName();
	        info += ") ";
	    };
	    return info;
    }
}


/* 
 * CVS LOG
 * ====================================================================
 *
 * $Log: CFEPartition.java,v $
 * Revision 1.10  2005/11/11 17:47:40  cybersonic
 * More work on the Edit this tag. Nearly there, nearly there
 *
 * Revision 1.9  2005/06/14 21:36:11  smilligan
 * Added external browser action.
 *
 * Fixed partitioner bug with multi-line comments in script, cfscript and cfquery blocks.
 *
 * Revision 1.8  2005/02/25 23:24:22  chrisbradford
 * Added field and methods for whether the partition is a closer tag
 *
 * Revision 1.7  2005/02/01 01:52:49  smilligan
 * Fixed a couple of issues with the partitioner.
 *
 * Revision 1.6  2005/01/31 08:01:13  smilligan
 * Refactored a lot of the partitioner so it is more manageable. This should be the final refactor I think. From here on in it ought to be performance tweaks and bug fixes.
 *
 * Revision 1.5  2005/01/30 18:54:57  smilligan
 * Committing a few more minor patches for the partitioner.
 *
 * Revision 1.4  2005/01/27 01:37:25  smilligan
 * put a band aid on the content assist for attributes and tags. It will need to be properly sorted out another time, but it basically works for now.
 *
 * Revision 1.3  2005/01/24 23:36:35  smilligan
 * Hopefully the last major change to the partitioner.
 *
 * Seems to be relatively robust and stable now.
 *
 * Revision 1.2  2005/01/21 08:25:15  smilligan
 * Re-implemented the partitioning in a slightly more robust way.
 *
 * Revision 1.1  2005/01/19 02:50:11  smilligan
 * Second commit of (now hopefully working) rewritten partitioner.
 *
 */