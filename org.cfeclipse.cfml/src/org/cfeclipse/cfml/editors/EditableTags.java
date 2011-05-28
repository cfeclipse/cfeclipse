package org.cfeclipse.cfml.editors;

import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.dictionary.SyntaxDictionary;
import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;

/**
 * @author Mark Drew
 * This is a singleton class that lets the CFEditor know partitions can be edited
 */
public class EditableTags {

	
	public static boolean isEditable(String partType){
		
		if(partType.equals(CFPartitionScanner.CF_START_TAG_BEGIN)){
			return true;
		}
		else if(partType.equals(CFPartitionScanner.CF_TAG_ATTRIBS)){
			return true;
		}
		else if(partType.equals(CFPartitionScanner.HTM_START_TAG_BEGIN)){
			return true;
		}
		else if(partType.equals(CFPartitionScanner.HTM_TAG_ATTRIBS)){
			return true;
		}
		else if(partType.equals(CFPartitionScanner.TABLE_START_TAG_BEGIN)){
			return true;
		}
		else if(partType.equals(CFPartitionScanner.TABLE_TAG_ATTRIBS)){
			return true;
		}
	
		return false;
	}

	public static SyntaxDictionary getDictionary(String partType){
		
		//Make sure we are getting the right dictionary here
		if(partType.equals(CFPartitionScanner.CF_START_TAG_BEGIN)){
			//Need to at least print out which dictionary we are getting
			System.out.println(DictionaryManager.getDictionary(DictionaryManager.CFDIC));
			return DictionaryManager.getDictionary(DictionaryManager.CFDIC);
			
		}
		else if(partType.equals(CFPartitionScanner.CF_TAG_ATTRIBS)){
			return DictionaryManager.getDictionary(DictionaryManager.CFDIC);
		}
		else if(partType.equals(CFPartitionScanner.HTM_START_TAG_BEGIN)){
			return DictionaryManager.getDictionary(DictionaryManager.HTDIC);
		}
		else if(partType.equals(CFPartitionScanner.HTM_TAG_ATTRIBS)){
			return DictionaryManager.getDictionary(DictionaryManager.HTDIC);
		}
		else if(partType.equals(CFPartitionScanner.TABLE_START_TAG_BEGIN)){
			return DictionaryManager.getDictionary(DictionaryManager.HTDIC);
		}
		else if(partType.equals(CFPartitionScanner.TABLE_TAG_ATTRIBS)){
			return DictionaryManager.getDictionary(DictionaryManager.HTDIC);
		}
		
		
		
		return null;
	}
}
