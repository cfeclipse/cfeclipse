/*
 * Created on Sep 27, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver Tupman
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package org.cfeclipse.cfml.editors.contentassist;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.dictionary.Function;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.partitioner.CFEPartition;
import org.cfeclipse.cfml.editors.partitioner.CFEPartitioner;
import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.CFParser;
import org.cfeclipse.cfml.parser.cfmltagitems.CfmlComment;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.cfeclipse.cfml.parser.docitems.ScriptItem;
import org.cfeclipse.cfml.preferences.ParserPreferenceConstants;
import org.cfeclipse.cfml.util.ResourceUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;


/**
 *
 * This class provides a few helper utilities for the content assist code.
 *
 * @author Oliver Tupman
 */
public class AssistUtils {

	/**
	 * Regular expression for matching @cfmlvariable declarations
	 */
	private static final String cfmlVarRE = "[\\s]*<!---[\\s]+@cfmlvariable[\\s]+name=\"([A-Za-z0-9_-]+)\"[\\s]+type=\"([A-Za-z0-9\\._-]+)\"[\\s]+--->";

    public static boolean isInCorrectPartitionTypes(IAssistState state, String partitionTypes[])
    {
	    ITextViewer viewer = state.getITextView();
	   
	    int offset = state.getOffset();
	    for(int i = 0; i < partitionTypes.length; i++)
	    {
	        if(AssistUtils.isCorrectPartitionType(viewer, offset, partitionTypes[i]))
	        {
	            return true;
	        }
	    }
	    return false;

    }
    
    public static boolean isCorrectPartitionType(ITextViewer viewer, int offset, String targetPartitionType)
    {
	    String partitionType;
		try {
		    partitionType = viewer.getDocument().getPartition(offset).getType();
		} catch(BadLocationException ex) {
		    ex.printStackTrace();
		    return false;
		}
		return partitionType.equals(targetPartitionType);
    }
	
	/**
	 * Initialises a default assist state object ready for the beginning of content assist.
	 * 
     * @param viewer The view
     * @param offset The offset within the document.
     * @return The initialised DefaultAssistState ready to be used.
     * @see DefaultAssistState
     */
    public static DefaultAssistState initialiseDefaultAssistState(ITextViewer viewer, int offset) {
        DefaultAssistState assistState = new DefaultAssistState();
		ICFDocument document = (ICFDocument) viewer.getDocument();
		//char invokerChar = ' ';
		
		try {
		    assistState.setTriggerChar((offset > 0) ? document.getChar(offset-1) : ' ');
			assistState.setOffsetPartition(document.getPartition(offset));
		} catch(BadLocationException ex) {
			ex.printStackTrace();
		}	
		

		
		assistState.setOffset(offset);
		assistState.setDoc(viewer.getDocument());
		assistState.setTextViewer(viewer);

        CFEPartitioner partitioner = (CFEPartitioner)document.getDocumentPartitioner();
        CFEPartition[] partitions = partitioner.getTagPartitions(assistState.getOffset());
        if (partitions != null) {
	        int start = partitions[0].getOffset();
	        int end = partitions[partitions.length-1].getOffset() + partitions[partitions.length-1].getLength();
	        end = assistState.getOffset();
	        try {
	          assistState.setDataSoFar(document.get(start,end-start));  
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        } else {
            assistState = AssistUtils.getPrefix(assistState);
        }
        return assistState;
    }
    
    /**
     * Eliminates characters such as carriage returns, line feeds & tabs.
     * Basically clears up the string prior to content assist.
     * 
     * @param prefix The string to filter
     * @return The filtered string
     */
	public static String eliminateUnwantedChars(String prefix) {
		prefix = prefix.replace('\n',' ');
		prefix = prefix.replace('\r',' ');
		prefix = prefix.replace('\t',' ');
		return prefix;
	}

	/**
	 * Get prefix works out the text that represents the tag the user is requesting insight on.
	 * It works by working out the start position of the tag (based upon partition start)
	 * and then substrings that from the current insight invocation position. 
	 * 
	 * @param currState The current state of the content assist
	 * @return The content assist with it's dataSoFar member set.
	 */
	public static DefaultAssistState getPrefix(DefaultAssistState currState)
	{
		ITypedRegion partition = currState.getOffsetPartition();
		int start = partition.getOffset();
		int offset = currState.getOffset();
		if (start == offset) {
			try {
				start = currState.getIDocument().getPartition(offset - 1).getOffset();
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String prefix = "";
		/*
		if(partition.getType() == CFPartitionScanner.J_SCRIPT) {
			System.err.println("HTMLContentAssistant::computeCompletionProposals() - JavaScript partition handling not implemented yet!");
			return null;
		}
		*/
		
		try {
			prefix = eliminateUnwantedChars(currState.getIDocument().get(start, offset - start));
		} catch(BadLocationException ex) {
			System.err.println("HTMLContentAssistant::computeCompletionProposals() - Caught Exception during prefix get!");
			ex.printStackTrace();
			return null;
		}
		currState.setDataSoFar(prefix);
		
		return currState;
	}

	public static String getCFCName(String variableName, IAssistState state) {
		CFDocument doc = ((ICFDocument) state.getIDocument()).getCFDocument();
		return getCFCName(variableName, doc);
	}

	public static String getCFCName(String variableName, CFDocument doc) {
		CFNodeList nodelist = doc.getDocumentRoot().getChildNodes();
		String cfcName = null;

		Iterator iter = nodelist.iterator();

		while (iter.hasNext()) {
			Object cfItem = iter.next();

			if (cfItem instanceof CfmlComment) {
				CfmlComment comment = (CfmlComment) cfItem;
				String commentText = ((CfmlComment) cfItem).getItemData();

				// Now get the type from the comment text    			
				Pattern p = Pattern.compile(cfmlVarRE);
				Matcher m = p.matcher(commentText);

				if (m.find() && m.group(1).equalsIgnoreCase(variableName)) {
					cfcName = m.group(2);
					break;
				}
			} else if (cfItem instanceof CfmlTagItem) {
				CfmlTagItem cfsetTag = (CfmlTagItem) cfItem;
				if (!((CfmlTagItem) cfItem).getName().equalsIgnoreCase("cfset")) {
					continue;
				}
				String tagText = ((CfmlTagItem) cfItem).getItemData();
				Map<String, String> varMap = parseCfSetText(tagText);
				if (varMap == null) {
					continue;
				} else {
					if (varMap.get("variableName").equalsIgnoreCase(variableName)) {
						cfcName = varMap.get("variableType");
						break;
					}
				}
			}
		}
		CFNodeList scriptNodes = doc.getDocumentRoot().selectNodes("//ASTAssignment");
		Iterator i = scriptNodes.iterator();
		while (i.hasNext()) {
			ScriptItem assignment = (ScriptItem) i.next();
			if (assignment.getFirstChild().getItemData().equals(variableName)) {
				return assignment.getLastChild().getItemData().replaceAll("\\(.*", "");
			}
		}
		scriptNodes = doc.getDocumentRoot().selectNodes("//ASTVarDeclaration");
		i = scriptNodes.iterator();
		while (i.hasNext()) {
			ScriptItem assignment = (ScriptItem) i.next();
			Iterator id = assignment.selectNodes("//ASTIdentifier").iterator();
			while (id.hasNext()) {
				ScriptItem identifier = (ScriptItem) id.next();
				if (identifier.getItemData().equals(variableName)) {
					return identifier.getParent().getLastChild().getItemData().replaceAll("\\(.*", "");
				}
			}
		}
		return cfcName;
	}

	private static Map<String, String> parseCfSetText(String tagText) {
		/* 
		 * Two cases we need to handle:
		 * 
		 * <cfset var foo = "bar">
		 * <cfset foo = "bar">
		 * 
		 */
		Map<String, String> resultMap = new HashMap<String, String>();
		String withVarKeywordRE = "<cfset[\\s]+var[\\s]+([A-Za-z0-9\\._-]+)[\\s]+=(.*)>";
		String withoutVarKeywordRE = "<cfset[\\s]+([A-Za-z0-9\\._-]+)[\\s]+=(.*)>";

		// Now get the type from the comment text    			
		Pattern p = Pattern.compile(withVarKeywordRE);
		Matcher m = p.matcher(tagText);

		if (m.find()) {
			resultMap.put("variableName", m.group(1));
			resultMap.put("variableType", parseCreateObjectType(m.group(2)));
		} else {
			p = Pattern.compile(withoutVarKeywordRE);
			m = p.matcher(tagText);
			if (m.find()) {
				resultMap.put("variableName", m.group(1));
				resultMap.put("variableType", parseCreateObjectType(m.group(2)));
			}
		}

		if (resultMap.size() != 2) {
			return null;
		} else {
			return resultMap;
		}
	}

	private static String parseCreateObjectType(String tagText) {
		/*
		 * tagText should be a string in the form createObject("component","componentName")
		 */

		String result = null;
		String trimmedTagText = tagText.replaceAll("[\\s]+", "");
		Pattern p = Pattern.compile("createObject\\([\"']component[\"'],[\"']([A-Za-z0-9\\._-]+)[\"']\\)", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(trimmedTagText);

		if (m.find()) {
			result = m.group(1);
		}
		return result;
	}

	/**
	 * This function loops through a project finding references to the CFC we seek. This can be done in 2 ways, break at
	 * the first, or return a whole bunch of proposals
	 * 
	 * Initally breaks at the first one
	 * 
	 * @param cfcname
	 */
	public static IFile findCFC(String fullyQualifiedCfc) {
		if (fullyQualifiedCfc == null)
			return null;
		IEditorPart editor = CFMLPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IProject project = ((IFileEditorInput) editor.getEditorInput()).getFile().getProject();

		//Will need recursive function
		IFile foundCFC = null;

		// Using the . as a separator, get the CFC name from the last token

		String cfcname = splitFullyQualifiedName(fullyQualifiedCfc) + ".cfc";

		try {
			IResource firstChildren[] = project.members();

			// To make this function quicker, doing two loops. The first is through the files, 
			// Then, we go into the directory, why? becuase I dont want to loop through the whole directory 
			// tree if the file is in the first directory!

			//Now loop through directories if we didnt find the file.
			if (foundCFC == null) {
				for (int i = 0; i < firstChildren.length; i++) {
					Object item = firstChildren[i];
					if (item instanceof IFolder) {
						foundCFC = reFindCFC(cfcname, (IFolder) item);
						if (foundCFC != null) {
							return foundCFC;
						}
					} else { //Its a file
						IFile theFile = (IFile) item;
						if (theFile.getName().equalsIgnoreCase(cfcname)) {
							return theFile;
						}

					}
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return foundCFC;
	}

	private static IFile reFindCFC(String cfcname, IFolder folder) {
		IFile foundCFC = null;

		try {
			IResource firstChildren[] = folder.members();

			// To make this function quicker, doing two loops. The first is through the files, 
			// Then, we go into the directory, why? because I don't want to loop through the whole directory 
			// tree if the file is in the first directory!

			// Now loop through directories if we didn't find the file.
			if (foundCFC == null) {
				for (int i = 0; i < firstChildren.length; i++) {
					Object item = firstChildren[i];
					if (item instanceof IFolder) {
						foundCFC = reFindCFC(cfcname, (IFolder) item);
						if (foundCFC != null) {
							return foundCFC;
						}
					} else { //Its a file
						IFile theFile = (IFile) item;
						if (theFile.getName().equalsIgnoreCase(cfcname)) {
							return theFile;
						}

					}
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return foundCFC;
	}

	public static String splitFullyQualifiedName(String fullyQualifiedCfc) {

		String result = null;
		if (fullyQualifiedCfc != null && fullyQualifiedCfc.length() > 0) {
			String[] tokens = fullyQualifiedCfc.split("\\.");
			result = tokens[tokens.length - 1];
		}
		return result;
	}

	public static Set<Function> getFunctions(IFile cfcresource, int offset) {
		if (cfcresource == null) {
			//CFMLPlugin.logError("AssistUtil#getFunctions: Resource not found");
			return null;
		}

		//
		//Now that we have the resource, convert it to a string
		String inputString = "";
		try {
			inputString = ResourceUtils.getStringFromInputStream(cfcresource.getContents());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CFParser parser = new CFParser();
		IPreferenceStore prefStore = CFMLPlugin.getDefault().getPreferenceStore();
		parser.setCFScriptParsing(prefStore.getBoolean(ParserPreferenceConstants.P_PARSE_DOCFSCRIPT));
		CFDocument doc = parser.parseDoc(inputString);
		if (doc == null) {
			return null;
		}
		return doc.getFunctions();
	}

}
