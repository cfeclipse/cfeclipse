/*
 * Created on Jul 15, 2004
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

import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.dictionary.Function;
import org.cfeclipse.cfml.dictionary.Parameter;
import org.cfeclipse.cfml.dictionary.ScopeVar;
import org.cfeclipse.cfml.dictionary.Tag;
import org.cfeclipse.cfml.dictionary.Value;
import org.cfeclipse.cfml.preferences.AutoIndentPreferenceConstants;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.util.Assert;


/**
 * @author Oliver Tupman
 * 
 * This is the abstract base class for all of the CFE content assist processors.
 * It provides a number of useful methods for the derived classes, though I suspect
 * they could all probably be refactored into a utility class.
 *
 */
public abstract class CFEContentAssist implements IContentAssistProcessor {

	/** tag type */
	public static final short TAGTYPE = 0;
	/** attribute type */
	public static final short ATTRTYPE = 1;
	/** value type */
	public static final short VALUETYPE = 2;
	/** scope type */
	public static final short SCOPETYPE = 3;
	/** function parameter type */
	public static final short PARAMETERTYPE = 4;

	
	/**
	 * Tokenises a string based upon the delimiter of a space.
	 * 
	 * @param inStr The string to tokenise
	 * @return The tokenised string returned as an ArrayList
	 */
	public static ArrayList getTokenisedString(String inStr)
	{
		Assert.isNotNull(inStr, "Parameter inStr, the string to parse, is null");
		
		if(inStr.trim().length() == 0)
			return new ArrayList();
		
		StringTokenizer st = new StringTokenizer(inStr, " ");
		ArrayList retArray = new ArrayList(st.countTokens() + 1);
		//retArray.add(inStr);
		
		while(st.hasMoreTokens()) {
			retArray.add(st.nextToken());
		}
		
		return retArray;
	}
	
	/**
	 * This method essentially tokenises a string having eliminated any unwanted characters.
	 * 
	 * @param document The source document
	 * @param partition The partition info where the offset lies
	 * @param offset The offset within the document
	 * @return A list of items that are space-deliminated.
	 */
	public static ArrayList getPartitionItems(IDocument document, ITypedRegion partition, int offset) {
		int start = partition.getOffset();
		
		String prefix = "";
		try {
			prefix = AssistUtils.eliminateUnwantedChars(document.get(start, offset - start));
		} catch(BadLocationException ex) {
			System.err.println("HTMLContentAssistant::computeCompletionProposals() - Caught Exception during prefix get!");
			ex.printStackTrace();
			return null;
		}
		
		StringTokenizer st = new StringTokenizer(prefix, " ");
		ArrayList retArray = new ArrayList(st.countTokens() + 1);
		retArray.add(prefix);
		
		while(st.hasMoreTokens()) {
			retArray.add(st.nextToken());
		}

		return retArray;
	}
	
	
	/** 
	 * helper function
	 * @param st the set to get the information from
	 * @param offset where in the document the items will be
	 * @param type attribute or tag (see finals in this class)
	 * @return
	 */
	public static ICompletionProposal[] makeSetToProposal(Set st, int offset, short type, int currentlen)
	{
		if(st != null) {
			Object obj[] = new Object[st.size()];
			TreeSet ts = new TreeSet();
			ts.addAll(st);
			obj = ts.toArray();
			
			//build a Completion dodad with the right amount of records
			ICompletionProposal[] result = new ICompletionProposal[obj.length];
			
			for(int i=0; i<obj.length; i++)
			{
				String name = "";
				String display = "";
				String help = "";
				
				if(obj[i] instanceof Tag) 
				{	
					Tag ptr_tg = (Tag)obj[i];
					
					//get the full on name
					name = ptr_tg.getName();
					display = ptr_tg.toString();
					help = ptr_tg.getHelp();
					boolean hasParams = ptr_tg.hasParameters();
					boolean isXmlStyle = ptr_tg.isXMLStyle();
					boolean isSingle = ptr_tg.isSingle();
					boolean takesCFScriptExp = false;
					
					// Bodge job for cfif, cfelse, cfelseif, cfset, and cfreturn
					if(name.compareToIgnoreCase("cfif") == 0 
						|| name.compareToIgnoreCase("cfelse") == 0
						|| name.compareToIgnoreCase("cfelseif") == 0
						|| name.compareToIgnoreCase("cfset") == 0
						|| name.compareToIgnoreCase("cfreturn") == 0) {
						takesCFScriptExp = true;
					}
					
					// If it does not have parameters self close the tag
					if (!takesCFScriptExp && !hasParams) {
						name += (isXmlStyle) ? "/" : "";
						name += (isSingle) ? " " : ">";
					} else {
						name+= " ";
					}
				} else if(obj[i] instanceof Parameter) {
					name = ((Parameter)obj[i]).getName();
					display = ((Parameter)obj[i]).toString();
					help = ((Parameter)obj[i]).getHelp();
				} else if(obj[i] instanceof Value) {
					/* spike@spike.org.uk :: Added code
                     * Append qoute to end of value so the cursor jumps past the 
                     * closing quote when the user selects the insight value. 
                     */
					name = ((Value)obj[i]).getValue() + "\"";
					display = ((Value)obj[i]).toString();
					help = "";
				} else if(obj[i] instanceof ScopeVar) {
					name = ((ScopeVar)obj[i]).getValue();
					display = ((ScopeVar)obj[i]).toString();
					help = ((ScopeVar)obj[i]).getHelp();
// System.out.println("Scope var found with name " + name);
				} else if(obj[i] instanceof Function) {
					name = ((Function)obj[i]).getInsertion();
					display = ((Function)obj[i]).getInsertion();
					help = ((Function)obj[i]).getHelp();
// System.out.println("Function found with name " + name);
					// Dirty hack
					currentlen=0;
				} else if(obj[i] instanceof String) {
					name = obj[i].toString();
					display = new String(name);
					help = "";
				}
				
				result[i] = finaliseProposal(
								offset
								,type
								,(currentlen > name.length()) ? name.length() : currentlen
								,name
								,display
								,help);
			}	
			return result;
		}
		return null;
	}
	
	/**
	 * Gets the proposal ready. Sets up the image, the text to insert into the text,
	 * and finally returns the completed proposal.
	 * 
	 * @param offset - offset in the document
	 * @param type - type of thing we're making a proposal for
	 * @param currentlen - length that we'd need to insert if the user selected the proposal
	 * @param name - name of the proposal
	 * @param display - string to display
	 * @param help - the help associated with this proposal
	 * @return - the completed, indented, image'd proposal
	 * 
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal
	 */
	public static CompletionProposal finaliseProposal(int offset, short type, int currentlen, String name, 
												String display, String help) 
	{
		Assert.isNotNull(name,"CFEContentAssist::finaliseProposal()");
		Assert.isNotNull(display,"CFEContentAssist::finaliseProposal()");
		Assert.isNotNull(help,"CFEContentAssist::finaliseProposal()");
		Assert.isTrue(currentlen <= name.length(),"CFEContentAssist::finaliseProposal()");
		
		//now remove chars so when they hit enter it wont write the whole
		//word just the part they havent typed
		String replacementString = name.substring(currentlen, name.length());
		
		//the tag len and icon
		int insertlen = replacementString.length();
		org.eclipse.swt.graphics.Image img = null;
		
		switch(type)
		{
			case ATTRTYPE:
				replacementString += "=\"\"";
				insertlen+= "\"\"".length();	// Compensate for the addition of =""
				img = CFPluginImages.get(CFPluginImages.ICON_ATTR);
				break;
			case TAGTYPE:
				//Check if we need to add the closer.
				if (name.endsWith(">"))  {
					IPreferenceStore store = CFMLPlugin.getDefault().getPreferenceStore();
					if(store.getBoolean(AutoIndentPreferenceConstants.P_AUTOINSERT_CLOSE_TAGS)) {
						if(store.getBoolean(AutoIndentPreferenceConstants.P_AUTOINDENT_ONTAGCLOSE)) {
							/* TODO figure out how to do this properly. 
							 * Right now the whole auto insertion thing is a mess that needs to be seriously thought out and refactored. 
							 */ 
							replacementString += "</"+name;
						}
						else {
							replacementString += "</"+name;
						}
					}
					
				}
				
				//Find out what type it is
				if(name.startsWith("cf")){
					img = CFPluginImages.get(CFPluginImages.ICON_DEFAULT);
				}
				else{
					img = CFPluginImages.get(CFPluginImages.ICON_TAG);
				}
				
				break;
			case VALUETYPE:
				img = CFPluginImages.get(CFPluginImages.ICON_VALUE);
				break;
			case SCOPETYPE:
				img = CFPluginImages.get(CFPluginImages.ICON_VALUE);
				break;
			case PARAMETERTYPE:
				insertlen = name.length();
				img = CFPluginImages.get(CFPluginImages.ICON_VALUE);
				break;
		}

		CompletionProposal prop = new CompletionProposal(
				replacementString,
				offset, 
				0, 
				insertlen,
				img,
				display,
				null,
				help
			);
		return prop;
	}
		
	
	/**
	 * 
	 */
	public CFEContentAssist() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeCompletionProposals(org.eclipse.jface.text.ITextViewer, int)
	 */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeContextInformation(org.eclipse.jface.text.ITextViewer, int)
	 */
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getCompletionProposalAutoActivationCharacters()
	 */
	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationAutoActivationCharacters()
	 */
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getErrorMessage()
	 */
	public String getErrorMessage() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationValidator()
	 */
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

}
