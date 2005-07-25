/* 
 * $Id: PositionUpdater.java,v 1.3 2005-07-25 01:29:38 rohanr2 Exp $
 * $Revision: 1.3 $
 * $Date: 2005-07-25 01:29:38 $
 * 
 * Created Jan 30, 2005 11:33:08 AM
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

import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.DocumentEvent;
//import org.eclipse.jface.text.Position;
/**
 * This class takes care of updating all document partitions when changes
 * occur such as keystrokes or and cut, paste operations.
 * 
 * @author Stephen Milligan
 * @version $Revision: 1.3 $
 */
public class PositionUpdater extends DefaultPositionUpdater {
    private String fCategory;
    /**
     * @param category
     */
    public PositionUpdater(String category) {
        super(category);
        fCategory = category;
    	
    }
    
    public void update(DocumentEvent e) {
        super.update(e);
    }


/**
 * Determines whether the currently investigated position has been deleted by 
 * the replace operation specified in the current event. If so, it deletes 
 * the position and removes it from the document's position category.
 *
 * @return <code>true</code> if position has been deleted
 */
protected boolean notDeleted() {
	if (fOffset <= fPosition.offset && (fPosition.offset + fPosition.length <= fOffset + fLength)) {
		
		fPosition.delete();
		
		try {
			fDocument.removePosition(fCategory, fPosition);
		} catch (BadPositionCategoryException x) {
		}
		
		return false;
	}

	return true;
}
    
    
    
    
    
}


/* 
 * CVS LOG
 * ====================================================================
 *
 * $Log: not supported by cvs2svn $
 * Revision 1.2  2005/02/01 01:52:49  smilligan
 * Fixed a couple of issues with the partitioner.
 *
 * Revision 1.1  2005/01/31 08:01:13  smilligan
 * Refactored a lot of the partitioner so it is more manageable. This should be the final refactor I think. From here on in it ought to be performance tweaks and bug fixes.
 *
 */