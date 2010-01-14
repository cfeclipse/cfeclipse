/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
package org.cfeclipse.cfml.editors;

import org.cfeclipse.cfml.editors.partitioner.CFEPartitioner;
import org.cfeclipse.cfml.editors.partitioner.PartitionTypes;
import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;

/**
 * The document setup participant for CFE docs.
 */
public class CFDocumentSetupParticipant  implements IDocumentSetupParticipant {

	/**
	 * The name of the partitioning.
	 * @since 3.0
	 */
	public final static String CFML_PARTITIONING= "org.cfeclipse.cfml.editors.CFMLEditor";  //$NON-NLS-1$
//	public final static String CFML_PARTITIONING= IDocumentExtension3.DEFAULT_PARTITIONING;  //$NON-NLS-1$

	public CFDocumentSetupParticipant() {
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.filebuffers.IDocumentSetupParticipant#setup(org.eclipse.jface.text.IDocument)
	 */
	public void setup(IDocument document) {
		IDocumentPartitioner partitioner = createDocumentPartitioner();
//		if (document instanceof IDocumentExtension3) {
//			IDocumentExtension3 extension3 = (IDocumentExtension3) document;
//			// extension3.setDocumentPartitioner(CFML_PARTITIONING, partitioner);
//			extension3.setDocumentPartitioner(CFML_PARTITIONING, partitioner);
//		} else {
//			document.setDocumentPartitioner(partitioner);
//		}
		document.setDocumentPartitioner(partitioner);
		partitioner.connect(document);

	}
	
	private IDocumentPartitioner createDocumentPartitioner() {
/* 		need to update to FastP, and change references from getPartitioner() to getPartitioner(string)
 * 		our partitioner looks to be from eclipse 2 or so. 

 */
//		return new FastPartitioner(
//				new CFPartitionScanner(), PartitionTypes.ALL_PARTITION_TYPES);
		return new CFEPartitioner(
				new CFPartitionScanner(), PartitionTypes.ALL_PARTITION_TYPES
			);
	}
}
