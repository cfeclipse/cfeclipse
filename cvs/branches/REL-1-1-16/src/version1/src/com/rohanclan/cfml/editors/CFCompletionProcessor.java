/*
 * Created on Jan 22, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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
package com.rohanclan.cfml.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import org.eclipse.jface.text.IDocument;
import com.rohanclan.cfml.util.CFPluginImages;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.*;
import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.dictionary.SyntaxDictionary;
import com.rohanclan.cfml.dictionary.SyntaxDictionaryInterface;
import org.eclipse.jface.text.contentassist.ContentAssistant;

import com.rohanclan.cfml.dictionary.*;
//import org.eclipse.jface.text.ITextSelection;

import org.eclipse.jface.text.contentassist.IContextInformationPresenter;
//import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.text.TextPresentation;

/**
 * @author Rob
 *
 * This is a simple completion processor (also called code insight) it shows the
 * little window with possible completion suggestions. - handles Tags, attribtues,
 * and functions for the coldfusion language only (at present).
 */
public class CFCompletionProcessor implements IContentAssistProcessor {
	/** tag type */
	private static final short TAGTYPE = 0;
	/** attribute type */
	private static final short ATTRTYPE = 1;
	/** value type */
	private static final short VALUETYPE = 2;
	/** scope type */
	private static final short SCOPETYPE = 3;
	
	private Pattern pattern = Pattern.compile("[^a-z.]$",Pattern.CASE_INSENSITIVE);
	
	
	private ContentAssistant assistant;
	
	protected IContextInformationValidator validator = new Validator();
		
	/**
	 * Startup the completer
	 */
	public CFCompletionProcessor(ContentAssistant assistant){
		this.assistant = assistant;
	}

	
	/*
	 * getAttributeValueProposals
	 * 
	 * @param syntax The SyntaxDictionary to lookup from
	 * @param inputText The input string that we're analysing. Should start at the start of the relevant tag
	 * @param indexOfFirstSpace The index of the first space in the string (really should work this out)
	 * @param docOffset Offset in the document that the activation occurred at.
	 *  
	 * @author Oliver Tupman
	 */
	protected ICompletionProposal[] getAttributeValueProposals(
		SyntaxDictionary syntax, String inputText, int indexOfFirstSpace, 
		int docOffset, IDocument doc)
	{
		int lastSpace = inputText.lastIndexOf(" ");
		int quotes = inputText.lastIndexOf("\"");
		String valueSoFar = "";
		if(quotes != -1)
		{
			// Attribute entered, user is typing.
			valueSoFar = inputText.substring(quotes+1, inputText.length());
		}
		else
			quotes = inputText.length() - 2;
		
		String attribute = inputText.substring(lastSpace+1, quotes-1);
		String tag = inputText.substring(0, indexOfFirstSpace);
		
		//System.err.println("I think I need to be looking up: " + attribute);
		//System.err.println("Tag I think I have is \'" + tag + "\'");

		Set attrProps = ((SyntaxDictionaryInterface)syntax).getFilteredAttributeValues(tag, attribute, valueSoFar);
		if(attribute.compareToIgnoreCase("template") == 0) {
			TreeSet suggestions = new TreeSet();
			if(doc instanceof ICFDocument) {
				IResource res = ((ICFDocument)doc).getResource();
				IPath folder = res.getFullPath().removeLastSegments(1);
				folder = folder.append(valueSoFar);
				valueSoFar = "";
				System.out.println("OS Path: \'" + folder.toOSString() + "\'");
				IFolder folderRes = res.getWorkspace().getRoot().getFolder(folder);
				
				if(folderRes == null) {
					System.out.println("Folder is null!");
				} else {
					System.out.println("Got folder: \'" + folderRes.getName() + "\'");
					try {
						IResource children[] = folderRes.members();
						for(int i = 0; i < children.length; i++) {
							if(children[i] instanceof IFolder) {
								suggestions.add(new Value(children[i].getName() + "/"));
							} else if(children[i] instanceof IFile) {
								suggestions.add(new Value(children[i].getName()));
							}
						}
					}catch(CoreException ex) {
						ex.printStackTrace();
					}
				}
				attrProps.addAll(suggestions);
			}
		}
		if(attrProps != null/* && attrProps.size() > 0*/)
		{

			if(attrProps.size() > 0 && ((Value)attrProps.toArray()[0]).getValue().compareTo(valueSoFar) == 0)
				return null;
			
			//System.err.println("CFCompletionProcessor::computeCompletionProposals() - I have " + attrProps.size() + " elements available to me");
			return makeSetToProposal(
				attrProps,
				docOffset,
				VALUETYPE,
				valueSoFar.length()
			);
			
		}	
		return null;
	}
	/**
	 * for tag, attribute, value, insight and auto close ... um this might be 
	 * getting out of control
	 * this whole thing is a bit of a hack, but basically it looks at the current
	 * partition and tokenizes the contents to the current offset. It then tries
	 * to figure out what is being typed and limits the suggested items accordingly
	 * ... you know code completion ... :)
	 */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
		int documentOffset) {
		
		try
		{
			String limiting = "";			// attributes typed text (if it exists)
			String tagnamespace = ""; 		// often 'cf' for cf tags
			boolean cftag = false;			// assume its not a cftag
			boolean httag = false;			// assume it's not an HTML tag
			String invoker = "";
			IDocument document = viewer.getDocument();
			String currPartitionType = document.getPartition(documentOffset).getType();
			String prevPartitionType = document.getPartition(documentOffset -1).getType();
			String tagname = "";
			int start = 0;
			
			if(documentOffset > 0)	// Get the text that invoked content assist
				invoker = document.get(documentOffset-1,1);
			
			
			//this is because when they hit > it often moves them into
			//another partiton type - so get the last partition
			if(invoker.equals(">"))
				start = document.getPartition(documentOffset - 1).getOffset();
			else if (invoker.equals(".")) {
			    /*
			    String text = document.get(0,documentOffset);
			    Matcher matcher = pattern.matcher(text);
			    
			    System.out.println("Searching for start of scope vars from " + documentOffset);
			    
			    if (matcher.find()) {
			        System.out.println("Matcher found " + matcher.group());
			    }
			    */

			    FindReplaceDocumentAdapter finder = new FindReplaceDocumentAdapter(document);
				IRegion region = finder.find(documentOffset-2,"[^a-z.]",false,false,false,true);
				if (region != null) {
				    start = region.getOffset()+1;
				    
				    //System.out.println("Start set to " + start);
				}
				
			    
				//System.out.println("Start is " + start);
			}
			else {
				start = document.getPartition(documentOffset).getOffset();
				if(currPartitionType.compareToIgnoreCase(CFPartitionScanner.J_SCRIPT) == 0) {
					start+= 8;
				}
			}
						
			String prefix =	eliminateUnwantedChars(document.get(start, documentOffset - start));
			

			
			///////////////////////////////////////////////////////////////////
			
			//now go over the whole tag using spaces as the delimiter
			StringTokenizer st = new StringTokenizer(prefix," ");
			StringTokenizer st2 = new StringTokenizer(prefix," ");
			
			
			//if st has nothing then we got called by mistake or something just
			//bail out
		// System.out.println("Prefix: \'" + prefix + "\'");
			tagname = (!st.hasMoreTokens()) ? prefix : st.nextToken();
		// System.out.println("tagname: \'" + tagname + "\'");
			
			//looks like this is just here to skip, but it causes a 
			//java.util.NoSuchElementException sometimes so I am just throwning the
			//hasMore as a quick pactch
			if(st2.hasMoreTokens())
				st2.nextToken();

			Set attribs = new HashSet();

			String[] fullAttrib;
			String attribName = "";
			while(st2.hasMoreTokens()) {
			    fullAttrib = st2.nextToken().split("=");
			    if (fullAttrib.length > 1 && fullAttrib[1].length() > 1) {
				    attribName = fullAttrib[0];
				    attribs.add(attribName.trim());
			    }
			}
			

			//if the tagname has the possibility of being a cf tag
			if(tagname.trim().length() >= 3)
			{
				//clean it up for our lookup
				if(prefix.trim().substring(0,3).equalsIgnoreCase("<cf"))
				{
					cftag = true;
					tagnamespace = "cf";
					//should now have just the lookup key : "abort" for example
					tagname = tagname.trim().substring(3);
				}
			}
			
			if(tagname.trim().startsWith("<"))
			{	// Gets the HTML syntax dictionary (CF tags will have been handled above,
				// therefore they won't have an open chevron)).
				httag = true;
				tagname = tagname.trim().substring(1);
			}
			
			//if this was a <booga> type tag remove the last >
			//so we can look up the tag and see if it needs a closer
			if(tagname.endsWith(">")) 
				tagname = tagname.substring(0,tagname.length()-1);
			
			//if this is an attribtue, limiting should have the last
			//entered text (the part we shall filter attribtues on)
			while(st.hasMoreTokens())
			{
				//in the end should have the thing to limit with
				limiting = st.nextToken();
			}

			//if it looks like they have started typing the contents of an
			//attribtue (or they are done) set limiting to nothing
			if(limiting.indexOf("\"") > 0 || limiting.indexOf("'") > 0)
			{
				limiting = "";
			}
			
			//if we are in a cftag, and there are no attribtues (and we did not
			//start this mess by getting called with a space or tab) then we
			//should lookup cf tag names to suggest
			boolean invokerIsSpace = invoker.equals(" ");
			boolean invokerIsTab = invoker.equals("\t");
			boolean invokerIsCloseChevron = invoker.equals(">");
			boolean invokerIsPeriod = invoker.equals(".");
			
			SyntaxDictionary syntax = null;
			
			if (cftag || invokerIsPeriod) {
			    syntax = DictionaryManager.getDictionary(DictionaryManager.CFDIC); 
			}
			else {
			    syntax = DictionaryManager.getDictionary(DictionaryManager.HTDIC);
			}
			

			
			if(limiting.length() <= 0 && !invokerIsSpace && !invokerIsTab && !invokerIsCloseChevron)
			{
				if(cftag) {
					return lookUpCFTagNames(documentOffset, syntax, invoker, document, prefix);
				} else if(httag) {
					return lookUpTagNames(documentOffset, syntax, invoker, document, prefix);
				}
				else if (invokerIsPeriod) {
				    return lookUpScopeVars(documentOffset, syntax, invoker, document, prefix);
				}
			}
			else
			{	
				return getAttributeProposals(documentOffset, limiting, syntax, prefix, tagname, attribs);
			}	
		}
		catch(Exception e)
		{
			//die all quite like.
			e.printStackTrace(System.err);
		}
		
		return null;
	}
	
	/**
	 * @param documentOffset
	 * @param limiting
	 * @param syntax
	 * @param prefix
	 * @param tagname
	 * @return
	 */
	private ICompletionProposal[] getAttributeProposals(int documentOffset, String limiting, SyntaxDictionary syntax, String prefix, String tagname, Set attribs) {
		//we are probably in need of attribtue in sight
		//clean up the text typed so far
		
	    String searchText = limiting.trim();
		if (limiting.endsWith("=")) {
		    searchText = limiting.substring(0,limiting.length()-1);
		}
		
		//hacks hacks everywhere :) this looks to see if there are an
		//odd number of " in the string prior to this invoke before 
		//showing attribute insight. (to keep it from showing attributes
		//inside of attributes)
		String quote_parts[] = prefix.split("\"");
		if(quote_parts.length % 2 != 0)
		{
			//and return our best guess (tagname should have been defined
			//up there ^
			if(syntax != null && prefix.indexOf('>') < 0)
			{
			    //This is a nasty hack to filter out the attributes that have already been typed.
			    Set proposalSet = syntax.getFilteredAttributes(tagname.trim(),searchText); 
			    Set toRemove = new HashSet();
			    Iterator proposals = proposalSet.iterator();
			    Iterator attributes;
			    Parameter p;
			    while(proposals.hasNext()) {
			        p = (Parameter)proposals.next();
			        attributes = attribs.iterator();
			        while(attributes.hasNext()) {
			            if (attributes.next().toString().equalsIgnoreCase(p.getName())) {
			                toRemove.add(p);
			            }
			        }
			    }
			    
			    Iterator removals = toRemove.iterator();
			    while (removals.hasNext()) {
			        proposalSet.remove(removals.next());
			    }
			    
			    
			    
				return makeSetToProposal(
					proposalSet,
					documentOffset,
					ATTRTYPE,
					limiting.length()
				);
			}
		}
		return null;
	}


	/**
	 * Content assist comes here if the user is typing out a tag or if the
	 * user is now in an attribute. If in an attribute we give out the possible
	 * attribute values.
	 * 
	 * @param documentOffset Offset within the document
	 * @param syntax Syntax dictionary to use
	 * @param invoker The string that invoked the content assist
	 * @param document Document that we're working in
	 * @param prefix Data that the user has typed
	 * @return array of completion proposals, null if no proposals found
	 * @throws BadLocationException
	 */
	private ICompletionProposal[] lookUpTagNames(int documentOffset, SyntaxDictionary syntax, String invoker, IDocument document, String prefix) throws BadLocationException {
		String taglimiting = prefix.trim().substring(1);
		
		/*////////////////////////// copy from above dup code! //////*/
		if(invoker.charAt(0) == '\"')
		{
			if(document.getChar(documentOffset) == '\"' &&
					document.getChar(documentOffset-2) != '=')
			{	// " entered and there already is one in the document.
				document.replace(documentOffset, 1, "");
				return null;
			}
		}				
		
		// If the taglimiting has a space in we're assuming that the user
		// is intending to input or has inputted some attributes.
		int indexOfFirstSpace = taglimiting.indexOf(" "); 
		if(indexOfFirstSpace != -1)
		{
			return getAttributeValueProposals(
					syntax, 
					taglimiting, 
					indexOfFirstSpace, 
					documentOffset, document
			);
		}
		else
		{
		/*////////////////////////// copy from above dup code! //////*/	
			
			return makeSetToProposal(
				((SyntaxDictionaryInterface)syntax).getFilteredElements(taglimiting),
				documentOffset,
				TAGTYPE,
				taglimiting.length()
			);
			
		}
	}

	

	private ICompletionProposal[] lookUpScopeVars(int documentOffset, SyntaxDictionary syntax, String invoker, IDocument document, String prefix) throws BadLocationException {
		
	    System.out.println("Looking for scope vars with prefix " + prefix);
		int length = prefix.length();
		// If the taglimiting has a space in we're assuming that the user
		// is intending to input or has inputted some attributes.
		Set proposals = ((SyntaxDictionaryInterface)syntax).getFilteredScopeVars(prefix);
		
		Iterator i = proposals.iterator();
		
		while (i.hasNext()) {
		    if (i.next() instanceof Function) {
		        length = prefix.length() - prefix.lastIndexOf(".");
		        System.out.println("Function found in scope lookup. Length reset to " + length);
		        break;
		    }
		}
		
		
		// Do we have methods in the returned set?
			return makeSetToProposal(
				proposals,
				documentOffset,
				SCOPETYPE,
				length
			);
			
	}



	/**
	 * Change any newlines or returns to a space so we can tokenize correctly
	 * 
	 * @param prefix text to work upon
	 * @return the cleaned string
	 */
	private String eliminateUnwantedChars(String prefix) {
		prefix = prefix.replace('\n',' ');
		prefix = prefix.replace('\r',' ');
		prefix = prefix.replace('\t',' ');
		return prefix;
	}


	/**
	 * Content assist comes here if the user is typing out a tag or if the
	 * user is now in an attribute. If in an attribute we give out the possible
	 * attribute values.
	 * 
	 * @param documentOffset Offset within the document
	 * @param syntax Syntax dictionary to use
	 * @param invoker The string that invoked the content assist
	 * @param document Document that we're working in
	 * @param prefix Data that the user has typed
	 * @return array of completion proposals, null if no proposals found
	 * @throws BadLocationException
	 */
	private ICompletionProposal[] lookUpCFTagNames(int documentOffset, 
												 SyntaxDictionary syntax, 
												 String invoker, IDocument document, 
												 String prefix) throws BadLocationException
	{
		//if they have typed more then the cf part get the rest so we
		//can filter out non matches
		String taglimiting = prefix.trim().substring(3);
			
		if(invoker.charAt(0) == '\"')
		{
			// spike@spike.org.uk :: Added code
		    // Make sure we aren't at the end of the document 
		    // before doing the check to see if we have two sets of double quotes. 
		    //
		    if (document.getLength() > documentOffset) 
		    {
		    	// spike@spike.org.uk :: Added comment
		        // This checks if the invoking charcter is the second of a pair of qoutes
		        // and if the first is preceded by an '='. If so we don't want to show
		        // insight, so it returns null. 
		        //
				if(document.getChar(documentOffset) == '\"' &&
				   document.getChar(documentOffset-2) != '=')
				{	// " entered and there already is one in the document.
					document.replace(documentOffset, 1, "");
					return null;
				}
		    }
		}				
		
		// If the taglimiting has a space in we're assuming that the user
		// is intending to input or has inputted some attributes.
		int indexOfFirstSpace = taglimiting.indexOf(" "); 
		if(indexOfFirstSpace != -1)
		{
			return getAttributeValueProposals(
				syntax, 
				taglimiting, 
				indexOfFirstSpace, 
				documentOffset, document
			);
		}
		else
		{
			return makeSetToProposal(
				((SyntaxDictionaryInterface)syntax).getFilteredElements(taglimiting),
				documentOffset,
				TAGTYPE,
				taglimiting.length()
			);
		}
	}


	/** 
	 * helper function
	 * @param st the set to get the information from
	 * @param offset where in the document the items will be
	 * @param type attribute or tag (see finals in this class)
	 * @return
	 */
	private ICompletionProposal[] makeSetToProposal(Set st, int offset, short type, int currentlen)
	{
		if(st != null)
		{
			Object obj[] = new Object[st.size()];
			//System.err.println("st is " + st.size() + " elements in size");
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
					
					if(!ptr_tg.isXMLStyle() && !ptr_tg.hasParameters())
					{
						name += ">";
					}
					else if( ptr_tg.isSingle() && ptr_tg.isXMLStyle() && !ptr_tg.hasParameters())
					{
						name += " />";
					}
					else
					{
						name += " ";
					}
				}
				else if(obj[i] instanceof Parameter)
				{					
					name = ((Parameter)obj[i]).getName() + "=";
					display = ((Parameter)obj[i]).toString();
					//if(((Parameter)obj[i]).isRequired())
					//	display += "*";
					help = ((Parameter)obj[i]).getHelp();
				}
				else if(obj[i] instanceof Value) 
				{
					/* spike@spike.org.uk :: Added code
                     * Append qoute to end of value so the cursor jumps past the 
                     * closing quote when the user selects the insight value. 
                     */
					name = ((Value)obj[i]).getValue() + "\"";
					display = ((Value)obj[i]).toString();
					help = ((Value)obj[i]).getHelp();
				}
				else if(obj[i] instanceof ScopeVar) 
				{
					name = ((ScopeVar)obj[i]).getValue();
					display = ((ScopeVar)obj[i]).toString();
					help = ((ScopeVar)obj[i]).getHelp();
					System.out.println("Scope var found with name " + name);
				}
				else if(obj[i] instanceof Function) 
				{
					name = ((Function)obj[i]).getInsertion();
					display = ((Function)obj[i]).getInsertion();
					help = ((Function)obj[i]).getHelp();
					System.out.println("Function found with name " + name);
					// Dirty hack
					currentlen=0;
				}
				else if(obj[i] instanceof String)
				{
					name = obj[i].toString();
					display = new String(name);
					help = "";
				}
				else {
				    System.out.println("Proposal of type " + obj[i].getClass().getName());
				}
				
				//System.err.println(name);
				result[i] = finaliseProposal(offset, type, currentlen, name, display, help);
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
	 */
	private CompletionProposal finaliseProposal(int offset, short type, int currentlen, String name, 
												String display, String help) 
	{
		//now remove chars so when they hit enter it wont write the whole
		//word just the part they havent typed
		name = name.substring(currentlen, name.length());
		
		//the tag len and icon
		int insertlen = 0;
		org.eclipse.swt.graphics.Image img = null;
		
		switch(type)
		{
			case ATTRTYPE:
				name += "\"\"";
				insertlen = name.length() - 1;
				img = CFPluginImages.get(CFPluginImages.ICON_ATTR);
				break;
			case TAGTYPE:
				//name += " ";
				//default to the tag len and icon
				insertlen = name.length();
				img = CFPluginImages.get(CFPluginImages.ICON_TAG);
				break;
			case VALUETYPE:
				insertlen = name.length();
				img = CFPluginImages.get(CFPluginImages.ICON_VALUE);
				break;
			case SCOPETYPE:
				insertlen = name.length();
				img = CFPluginImages.get(CFPluginImages.ICON_VALUE);
				break;
		}
		CompletionProposal prop = new CompletionProposal(
				name,
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
	 * What characters cause us to wake up (for tags and attributes)
	 */
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] { '<', 'f', ' ', 'F', '~', '\t', '\n', '\r', '>', '\"', '.' };
	}

	/**
	 * What characters cause us to wake up (for functions / methods)
	 */
	public char[] getContextInformationAutoActivationCharacters() {
		return new char[] { '(', '.',',' };
		//return null;
	}
 
	/**  
	 * Shows a little popup that stays active until the class at the bottom of
	 * the file says its not
	 */
	public IContextInformationValidator getContextInformationValidator() {
		//return null;
		return validator;
	}
	
	/**
	 * for functions insight
	 */
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
		int documentOffset) {
		
		//System.out.println("context do dad running");
		
		boolean insideFunction = false;
		
		try
		{
			//find out the line number and get the begining of the line
			int linenum = viewer.getDocument().getLineOfOffset(documentOffset);
			int linestart = viewer.getDocument().getLineOffset(linenum);
			//get the line
			String currentline = viewer.getDocument().get(linestart,documentOffset - linestart);
			// Check if we could be inside a function
			if (currentline.lastIndexOf("(") > 0) {
				// Ensure there isn't a closing bracket
				if (currentline.lastIndexOf(")") == -1 || currentline.lastIndexOf(")") < currentline.lastIndexOf("(")) {
					currentline = currentline.substring(0,currentline.lastIndexOf("("));
					insideFunction = true;
				}
			}
			//make it a space delimited list
			currentline = currentline.replace('\"',' ');
			currentline = currentline.replace('\'',' ');
			currentline = currentline.replace('#',' ');
			currentline = currentline.replace('(',' ');
			currentline = currentline.replace(')',' ');

			//tokenize the bad boy
			StringTokenizer st = new StringTokenizer(currentline," ");
			
			//the last item should be our function (because of documentOffset)
			String functionname = "";
			while(st.hasMoreTokens())
			{
				functionname = st.nextToken();
			}
			
			if (insideFunction) {
				functionname += "(";
			}
			
			//remove the last char (which should be the '(')
			//if(functionname.length() > 1)
			//	functionname = functionname.substring(0,functionname.length() - 1);
			
			//System.out.println("f>>" + functionname.trim() + "<<f");
			
			//System.err.println(functionname.trim());
			SyntaxDictionary syntax = DictionaryManager.getDictionary(DictionaryManager.CFDIC);
			
			Set fst = syntax.getFunctions();
			fst = SyntaxDictionary.limitSet(fst,functionname.trim());
			
			//Function fun = syntax.getFunction(functionname.trim());
			//String usage = fun.toString();
			
			//String usage = ((SyntaxDictionaryInterface)syntax).getFunctionUsage(functionname.trim());
						
			//if(usage != null)
			//System.out.println(fst.size());
			if(fst.size() > 0)
			{
				//bit of a hack - there are only a copule functions that have
				//several wasys to call them, so if there are more then one
				//they are sperated by ||s
				//st = new StringTokenizer(usage,"||");
				
				////////////////////////////////////////////////////////////////
				//TODO figure out why this has to have 2 - it wont show otherwise
				//IContextInformation[] result = new IContextInformation[st.countTokens() + 1];
				//IContextInformation result[] = new IContextInformation[2];
				IContextInformation result[] = new IContextInformation[fst.size()+1];
				
				Iterator i = fst.iterator();
				int x = 0;
				while(i.hasNext())
				{
					//System.out.println(x);
					Function fun = syntax.getFunction((String)i.next()); 
						//(Function)i.next();

						String usage = fun.toString();
						//System.err.println(usage);
						
						result[x] = new ContextInformation(
							CFPluginImages.get(CFPluginImages.ICON_FUNC),
							//info,
							usage,
							//""
							usage //fun.getHelp()
						);
						this.validator.install(result[x], viewer, documentOffset);
						x++;
				}
				//System.out.println(x);
				result[x] = new ContextInformation(
					"",
					""
				);
				
				/*int i = 0;
				//while(st.hasMoreTokens())
				//{
					//String info = st.nextToken().trim();
					result[i] = new ContextInformation(
						CFPluginImages.get(CFPluginImages.ICON_FUNC),
						//info,
						usage,
						//""
						usage //fun.getHelp()
					);
					i++;
				//}
				result[i] = new ContextInformation(
					"",
					""
				); */
				
				//this.validator.install(result[0], viewer, documentOffset);
				
				return result;
			}
			return null;
			
		}catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
		
		return null;
	}

	/**
	 * yeah... not sure what to do here
	 */
	public String getErrorMessage() {
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////
	protected static class Validator implements IContextInformationValidator, 
		IContextInformationPresenter {
		
		//protected int installoffset;
		protected ITextViewer view;
		
		/*
		 * @see IContextInformationValidator#isContextInformationValid(int)
		 */
		public boolean isContextInformationValid(int offset) 
		{
			try
			{
				String paren = view.getDocument().get(
					offset-1,
					1
				);
				//System.out.println(paren);
				if(paren.equals(")"))
				{
					return false;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace(System.err);
				return false;
			}
			
			return true;
			//return Math.abs(installoffset - offset) < 5;
		}

		/*
		 * @see IContextInformationValidator#install(IContextInformation, ITextViewer, int)
		 */
		public void install(IContextInformation info, ITextViewer viewer, int offset) 
		{
			//installoffset = offset;
			view = viewer;
		}

		/*
		 * @see org.eclipse.jface.text.contentassist.IContextInformationPresenter#updatePresentation(int, TextPresentation)
		 */
		public boolean updatePresentation(int documentPosition, TextPresentation presentation)
		{
			return true;
		}
	};
}
