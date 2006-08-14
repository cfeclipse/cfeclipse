package org.cfeclipse.cfml.editors.partitioner;

import java.util.Map;

import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.util.CFDocUtils;
import org.eclipse.jface.text.TextSelection;

/**
 * @author mark
 * this is a class that helps you find out partitions and information
 * I noticed that the partition is used a lot so here we add some helper methods
 * 	
 */
public class PartitionHelper {
	
	private ICFDocument document;
	private int startpos;
	private CFEPartition part; 
	
	public PartitionHelper(ICFDocument document , int startPos){
		this.document = document;
		this.startpos = startPos;
		CFEPartitioner partitioner = (CFEPartitioner)document.getDocumentPartitioner();
		this.part = partitioner.findClosestPartition(startpos);
	}

	
	public String getTagName(){
		return	part.getTagName();
	}
	
	
	public String getType(){
		return part.getType();
	}
	
	
	public boolean isCFTag(){
		
		return true;
	}
	
	public boolean isHTMLTag(){
		
		return true;
	}
	
	
}
