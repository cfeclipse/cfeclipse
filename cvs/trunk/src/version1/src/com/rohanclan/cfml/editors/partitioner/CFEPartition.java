/* 
 * $Id: CFEPartition.java,v 1.2 2005-01-21 08:25:15 smilligan Exp $
 * $Revision: 1.2 $
 * $Date: 2005-01-21 08:25:15 $
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
package com.rohanclan.cfml.editors.partitioner;

import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TypedPosition;
import org.eclipse.jface.text.IDocument;
/**
 * Class description...
 * 
 * @author Stephen Milligan
 * @version $Revision: 1.2 $
 */
public class CFEPartition extends TypedPosition {

    private String fNextPartitionType = null;
    /**
     * @param offset
     * @param length
     * @param type
     */
    public CFEPartition(int offset, int length, String type) {
        super(offset, length, type);
      
    }

    /**
     * @param region
     */
    public CFEPartition(ITypedRegion region) {
        super(region);
    }
    
    public void setNextPartitionType(String type) { 
        fNextPartitionType = type;
    }
    
    public String getNextPartitionType() { 
        return fNextPartitionType;
    }

}


/* 
 * CVS LOG
 * ====================================================================
 *
 * $Log: not supported by cvs2svn $
 * Revision 1.1  2005/01/19 02:50:11  smilligan
 * Second commit of (now hopefully working) rewritten partitioner.
 *
 */