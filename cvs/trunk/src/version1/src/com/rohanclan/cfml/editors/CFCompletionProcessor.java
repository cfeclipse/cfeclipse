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
		int docOffset)
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
			//attributes typed text (if it exists)
			String limiting = "";
			//often 'cf' for cf tags
			String tagnamespace = "";
			//where we are going to lookup in sight stuff
			SyntaxDictionary syntax = null;
			//System.err.println("in CFML completion processor");
			//assume its not a cftag
			boolean cftag = false;
			boolean httag = false;
			
			//what invoked us a space or a f? (make sure we are not at the start
			//of a file
			String invoker = "";
			if(documentOffset > 0)
				invoker = viewer.getDocument().get(documentOffset-1,1);
			
			// Stop the content assist from popping up if there are multiple spaces or tabs
			try {
				if (viewer.getDocument().get(documentOffset-2,2).matches("\\s+")){
					assistant.enableAutoActivation(false);
					System.out.println("Auto activation disabled");
					
				} else {
					assistant.enableAutoActivation(true);
					System.out.println("Auto activation enabled");
				}
			}
			catch (Exception e) {}
			
			IDocument document = viewer.getDocument();
			String current_partition = viewer.getDocument().getPartition(documentOffset).getType();
			
			int start = 0;
			
			//this is because when they hit > it often moves them into
			//another partiton type - so get the last partition
			if(invoker.equals(">"))
				start = document.getPartition(documentOffset - 1).getOffset();
			else
				start = document.getPartition(documentOffset).getOffset();
			
			String prefix =	document.get(start, documentOffset - start);
			
			//System.err.println("Going in" + prefix + " type " + document.getPartition(documentOffset).getType());
			
			//change any newlines or returns to a space so we can 
			//tokenize correctly
			prefix = prefix.replace('\n',' ');
			prefix = prefix.replace('\r',' ');
			prefix = prefix.replace('\t',' ');
			
			///////////////////////////////////////////////////////////////////
			
			//now go over the whole tag using spaces as the delimiter
			StringTokenizer st = new StringTokenizer(prefix," ");
			String tagname = "";
			
			//if st has nothing then we got called by mistake or something just
			//bail out
			if(!st.hasMoreTokens())
			{
				//return null;
				tagname = prefix;
			}
			else
			{
				//first token should be the tag name (with <cf attached)
				tagname = st.nextToken();	
			}
			//System.err.println("Finished tokens");
			//System.err.println("tag1>>"+tagname+"<<");
			
			//if the tagname has the possibility of being a cf tag
			if(tagname.trim().length() >= 3)
			{
				//clean it up for our lookup
				//System.err.println("Looking for <cf");
				if(prefix.trim().substring(0,3).equalsIgnoreCase("<cf"))
				{
					cftag = true;
					tagnamespace = "cf";
					//should now have just the lookup key : "abort" for example
					tagname = tagname.trim().substring(3);
					//System.err.println("tag2>>"+tagname+"<<");
					syntax = DictionaryManager.getDictionary(DictionaryManager.CFDIC);
				}
			}
			
			//if it was a cftag it should no longer start with a <
			if(tagname.trim().startsWith("<"))
			{
				//do the html dictionary
				//System.err.println("Got an HTML tag");
				httag = true;
				tagname = tagname.trim().substring(1);
				syntax = DictionaryManager.getDictionary(DictionaryManager.HTDIC);
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
			//System.err.println("Done more token crap");
			//if it looks like they have started typing the contents of an
			//attribtue (or they are done) set limiting to nothing
			if(limiting.indexOf("\"") > 0 || limiting.indexOf("'") > 0)
			{
				limiting = "";
			}
			
			//if we are in a cftag, and there are no attribtues (and we did not
			//start this mess by getting called with a space or tab) then we
			//should lookup cf tag names to suggest
			if(cftag && limiting.length() <= 0 && (!invoker.equals(" ") && !invoker.equals("\t") && !invoker.equals(">")) )
			{
				//if they have typed more then the cf part get the rest so we
				//can filter out non matches
				String taglimiting = prefix.trim().substring(3);
				//System.err.println(limiting);
				//System.err.println("In cf tag name lookup");
				//System.err.println("taglimiting is: \'" + taglimiting + "\'");
				//System.err.println("doc offset: " + documentOffset);
				//System.err.println("invoker: \'" + invoker + "\'"); 
				//System.err.println("prefix: \'" + prefix + "\'");
					
				if(invoker.charAt(0) == '\"')
				{
					/* spike@spike.org.uk :: Added code
                     * Make sure we aren't at the end of the document 
                     * before doing the check to see if we have two sets of double quotes. 
                     */
                    if (document.getLength() > documentOffset) 
                    {
                    	/* spike@spike.org.uk :: Added comment
                         * This checks if the invoking charcter is the second of a pair of qoutes
                         * and if the first is preceded by an '='. If so we don't want to show
                         * insight, so it returns null. 
                         */
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
						documentOffset
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
			else if(httag && limiting.length() <= 0 && (!invoker.equals(" ") && !invoker.equals("\t") && !invoker.equals(">")) )
			{
				String taglimiting = prefix.trim().substring(1);
				//System.out.println("tl:" + taglimiting);
				//System.err.println("Doing tag limiting");
				
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
							documentOffset
					);
				}
				else
				{
				/*////////////////////////// copy from above dup code! //////*/	
					
					return makeSetToProposal(
						((SyntaxDictionaryInterface)syntax).getFilteredElements(taglimiting),
						//CFSyntaxDictionary.getFilteredElements(taglimiting),
						documentOffset,
						TAGTYPE,
						taglimiting.length()
					);
					
				}
			}
			//this is (hopefully) a close tag try to finish it out if needed
			//careful though that (for now) javascript and css sections will run
			//in here and doing > can mess stuff up
			else if(invoker.equals(">")
				//&& !current_partition.equals(CFPartitionScanner.J_SCRIPT)
				//&& !current_partition.equals(CFPartitionScanner.CSS_TAG)
			)
			{
				//System.err.println("Close tag crap");
				//System.err.println("i go");
				if(syntax != null && syntax.tagExists(tagname))
				{	
					Tag tag = syntax.getTag(tagname);
					//get the char right before the >, if its a / then they
					//did an xhtml style <br/> kind of thing. If so then
					//don't auto close it
					String xmlclose = viewer.getDocument().get(documentOffset-2,1);
					
					if(tag != null && !tag.isSingle())
					{
						//if they didnt xml close it
						if(!xmlclose.equals("/"))
						{
							
							StringBuffer sb = new StringBuffer();
							sb.append("</" + tagnamespace + tagname + ">");
							//String addtag = "</";
							//addtag += tagnamespace;
							//addtag += tagname + ">";
							String addtag = sb.toString();
							
							IDocument doc = viewer.getDocument();
							
							//the fully qualified tag name
							String fqt = tagnamespace + tagname;
							
							//get the word before this one and see if it is a closing
							//tag for this tag, if it is dont auto close
							String wordb4 = doc.get(
								(documentOffset - fqt.length()-2),(fqt.length()+1)
							);
							//System.err.println(wordb4);
							//if it doesnt look like they closed the tag already
							//go ahead and close it
							if( !wordb4.equalsIgnoreCase("/" + fqt) )
							{
								doc.replace(documentOffset, 0, addtag);
							}
						}
					}
				}
			}
			
			//little bit-o-debug. Hit ~ to see what partiton you are in 
			//(shows in the debug window
			else if(invoker.equals("~"))
			{
				System.err.println("Partition: " + current_partition);
			}
			else
			{	
				//we are probably in need of attribtue in sight
				//clean up the text typed so far
				//System.err.println("Something else");
				
				limiting = limiting.trim();
				//System.err.println("tag2>>"+tagname+"<<");
				//System.err.println("lim2::"+limiting+"::");
				//System.err.println("prefix::"+prefix+"::"+prefix.indexOf('>'));
				
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
						return makeSetToProposal(
							syntax.getFilteredAttributes(tagname.trim(),limiting),
							documentOffset,
							ATTRTYPE,
							limiting.length()
						);
					}
				}
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
					name = ((Parameter)obj[i]).getName();
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
					help = "";
				}
				else if(obj[i] instanceof String)
				{
					name = obj[i].toString();
					display = new String(name);
					help = "";
				}
				
				//now remove chars so when they hit enter it wont write the whole
				//word just the part they havent typed
				name = name.substring(currentlen, name.length());
				
				//the tag len and icon
				int insertlen = 0;
				org.eclipse.swt.graphics.Image img = null;
				
				switch(type)
				{
					case ATTRTYPE:
						name += "=\"\"";
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
				}
								
				//System.err.println(name);
				result[i] = new CompletionProposal(
					name,
					offset, 
					0, 
					insertlen,
					img,
					display,
					null,
					help
				);
			}	
			return result;
		}
		return null;
	}

	/**
	 * What characters cause us to wake up (for tags and attributes)
	 */
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] { '<', 'f', ' ', 'F', '~', '\t', '\n', '\r', '>', '\"' };
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
