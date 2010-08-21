/* 
 * $Id: PartitionTypes.java,v 1.2 2005/03/29 19:19:49 smilligan Exp $
 * $Revision: 1.2 $
 * $Date: 2005/03/29 19:19:49 $
 * 
 * Created Mar 1, 2005 11:51:23 AM
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
import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.eclipse.jface.text.IDocument;

/**
 * Class description...
 * 
 * @author Stephen Milligan
 * @version $Revision: 1.2 $
 */
public final class PartitionTypes {

    public static final String[] ALL_PARTITION_TYPES  = new String[] {
        IDocument.DEFAULT_CONTENT_TYPE,
    	CFPartitionScanner.DOCTYPE,
    	CFPartitionScanner.CF_COMMENT,
    	CFPartitionScanner.HTM_COMMENT,
    	CFPartitionScanner.CF_START_TAG,
    	CFPartitionScanner.CF_START_TAG_BEGIN,
    	CFPartitionScanner.CF_START_TAG_END,
    	CFPartitionScanner.CF_TAG_ATTRIBS,
    	CFPartitionScanner.CF_SET_STATEMENT,
    	CFPartitionScanner.CF_RETURN_STATEMENT,
    	CFPartitionScanner.CF_BOOLEAN_STATEMENT,
    	CFPartitionScanner.CF_END_TAG,
    	CFPartitionScanner.HTM_START_TAG,
    	CFPartitionScanner.HTM_END_TAG,
    	CFPartitionScanner.HTM_START_TAG_BEGIN,
    	CFPartitionScanner.HTM_START_TAG_END,
    	CFPartitionScanner.HTM_TAG_ATTRIBS,
    	CFPartitionScanner.CF_SCRIPT,
    	CFPartitionScanner.CF_EXPRESSION,
    	CFPartitionScanner.J_SCRIPT,
    	CFPartitionScanner.CSS,
    	CFPartitionScanner.SQL,
    	CFPartitionScanner.UNK_TAG,
    	CFPartitionScanner.TAGLIB_TAG,
    	CFPartitionScanner.FORM_END_TAG,
    	CFPartitionScanner.FORM_START_TAG,
    	CFPartitionScanner.FORM_START_TAG_BEGIN,
    	CFPartitionScanner.FORM_TAG_ATTRIBS,
    	CFPartitionScanner.FORM_START_TAG_END,
    	CFPartitionScanner.TABLE_END_TAG,
    	CFPartitionScanner.TABLE_START_TAG,
    	CFPartitionScanner.TABLE_START_TAG_BEGIN,
    	CFPartitionScanner.TABLE_TAG_ATTRIBS,
    	CFPartitionScanner.TABLE_START_TAG_END
    };

    // excludes comments
    public static final String[] ASSIST_PARTITION_TYPES  = new String[] {
        IDocument.DEFAULT_CONTENT_TYPE,
    	CFPartitionScanner.DOCTYPE,
    	CFPartitionScanner.CF_START_TAG,
    	CFPartitionScanner.CF_START_TAG_BEGIN,
    	CFPartitionScanner.CF_START_TAG_END,
    	CFPartitionScanner.CF_TAG_ATTRIBS,
    	CFPartitionScanner.CF_SET_STATEMENT,
    	CFPartitionScanner.CF_RETURN_STATEMENT,
    	CFPartitionScanner.CF_BOOLEAN_STATEMENT,
    	CFPartitionScanner.CF_END_TAG,
    	CFPartitionScanner.HTM_START_TAG,
    	CFPartitionScanner.HTM_END_TAG,
    	CFPartitionScanner.HTM_START_TAG_BEGIN,
    	CFPartitionScanner.HTM_START_TAG_END,
    	CFPartitionScanner.HTM_TAG_ATTRIBS,
    	CFPartitionScanner.CF_SCRIPT,
    	CFPartitionScanner.CF_EXPRESSION,
    	CFPartitionScanner.J_SCRIPT,
    	CFPartitionScanner.CSS,
    	CFPartitionScanner.SQL,
    	CFPartitionScanner.UNK_TAG,
    	CFPartitionScanner.TAGLIB_TAG,
    	CFPartitionScanner.FORM_END_TAG,
    	CFPartitionScanner.FORM_START_TAG,
    	CFPartitionScanner.FORM_START_TAG_BEGIN,
    	CFPartitionScanner.FORM_TAG_ATTRIBS,
    	CFPartitionScanner.FORM_START_TAG_END,
    	CFPartitionScanner.TABLE_END_TAG,
    	CFPartitionScanner.TABLE_START_TAG,
    	CFPartitionScanner.TABLE_START_TAG_BEGIN,
    	CFPartitionScanner.TABLE_TAG_ATTRIBS,
    	CFPartitionScanner.TABLE_START_TAG_END
    };
}