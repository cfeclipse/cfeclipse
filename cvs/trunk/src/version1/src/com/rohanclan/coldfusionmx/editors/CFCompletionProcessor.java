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
package com.rohanclan.coldfusionmx.editors;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import org.eclipse.jface.text.IDocument;
import com.rohanclan.coldfusionmx.util.CFPluginImages;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Set;
import com.rohanclan.coldfusionmx.dictionary.DictionaryManager;
import com.rohanclan.coldfusionmx.dictionary.SyntaxDictionary;
import com.rohanclan.coldfusionmx.dictionary.SyntaxDictionaryInterface;


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
	
	/**
	 * Startup the completer
	 */
	public CFCompletionProcessor(){;}

	/**
	 * for tag and attribute insight
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
			//where we are going to lookup insight stuff
			SyntaxDictionary syntax = null;
			
			//assume its not a cftag
			boolean cftag = false;
			
			//what invoked us a space or a f?
			String invoker = viewer.getDocument().get(documentOffset-1,1);
			
			IDocument document = viewer.getDocument();
			int start = document.getPartition(documentOffset).getOffset();
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
			
			//if st has nothing then we got called by mistake or something just
			//bail out
			if(!st.hasMoreTokens())
			{
				return null;
			}
			
			//first token should be the tag name (with <cf attached)
			String tagname = st.nextToken();
			//System.err.println("tag1>>"+tagname+"<<");
			
			//if the tagname has the possibility of being a cf tag
			if(tagname.trim().length() >= 3)
			{
				//clean it up for out lookup
				if(prefix.trim().substring(0,3).equalsIgnoreCase("<cf"))
				{
					cftag = true;
					//should now have just the lookup key : "abort" for example
					tagname = tagname.trim().substring(3);
					//System.err.println("tag2>>"+tagname+"<<");
					syntax = DictionaryManager.getDictionary(DictionaryManager.CFDIC);
				}
			}
			
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
			if(cftag && limiting.length() <= 0 && (!invoker.equals(" ") && !invoker.equals("\t")) )
			{
				//if they have typed more then the cf part get the rest so we
				//can filter out non matches
				String taglimiting = prefix.trim().substring(3);
				//System.err.println(limiting);
				
				return makeSetToProposal(
					((SyntaxDictionaryInterface)syntax).getFilteredElements(taglimiting),
					//CFSyntaxDictionary.getFilteredElements(taglimiting),
					documentOffset,
					TAGTYPE,
					taglimiting.length()
				);
			}
			//little bit-o-debug. Hit ~ to see what partiton you are in 
			//(shows in the debug window
			else if(invoker.equals("~"))
			{
				System.err.println(
					"Partition: " + 
					viewer.getDocument().getPartition(documentOffset).getType()
				);
			}
			else
			{	
				//we are probably in need of attribtue insight;
				//clean up the text typed so far
				limiting = limiting.trim();
				//System.err.println("tag2>>"+tagname+"<<");
				//System.err.println("lim2>>"+limiting+"<<");
				
				//and return our best guess (tagname should have been defined
				//up there ^
				if(syntax != null)
				{
					return makeSetToProposal(
						((SyntaxDictionaryInterface)syntax).getFilteredAttributes(tagname.trim(),limiting),
						//CFSyntaxDictionary.getFilteredAttributes(tagname.trim(),limiting),
						documentOffset,
						ATTRTYPE,
						limiting.length()
					);
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
			obj = new TreeSet(st).toArray();
			
			//build a Completion dodad with the right amount of records
			ICompletionProposal[] result = new ICompletionProposal[obj.length];
	
			for(int i=0; i<obj.length; i++)
			{
				//if(currentlen == 0) currentlen = 1;
				//get the full on name
				String name = obj[i].toString();
				//make a displayable name
				String display = new String(name);
				
				//now remove chars so when they hit enter it wont write the whole
				//word just the part they havent typed
				name = name.substring(currentlen, name.length());
				//System.err.println("in::" + name);
				
				//the tag len and icon
				int insertlen = 0;
				org.eclipse.swt.graphics.Image img = null;
				
				if(type == ATTRTYPE)
				{
					name += "=\"\"";
					insertlen = name.length() - 1;
					img = CFPluginImages.get(CFPluginImages.ICON_ATTR);
				}
				else if(type == TAGTYPE)
				{
					name += " ";
					//default to the tag len and icon
					insertlen = name.length();
					img = CFPluginImages.get(CFPluginImages.ICON_TAG);
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
					"test" 
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
		return new char[] { '<', 'f', ' ', 'F', '~', '\t', '\n', '\r' };
	}

	/**
	 * What characters cause us to wake up (for functions / methods)
	 */
	public char[] getContextInformationAutoActivationCharacters() {
		return new char[] { '(' };
	}
 
	/**  
	 * TODO this is breaking the rules. This needs to be implemented
	 * not sure what it does though :-/
	 */
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}
	
	/**
	 * for functions insight
	 */
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
		int documentOffset) {
		
		try
		{
			//find out the line number and get the begining of the line
			int linenum = viewer.getDocument().getLineOfOffset(documentOffset);
			int linestart = viewer.getDocument().getLineOffset(linenum);
			//get the line
			String currentline = viewer.getDocument().get(linestart,documentOffset - linestart);
			//make it a space delimited list
			currentline = currentline.replace('\"',' ');
			currentline = currentline.replace('\'',' ');
			currentline = currentline.replace('#',' ');
			//tokenize the bad boy
			StringTokenizer st = new StringTokenizer(currentline," ");
			
			//the last item should be our function (because of documentOffset)
			String functionname = "";
			while(st.hasMoreTokens())
			{
				functionname = st.nextToken();
			}
			//remove the last char (which should be the '(')
			functionname = functionname.substring(0,functionname.length() - 1);
		
			//System.err.println(functionname.trim());
			SyntaxDictionary syntax = DictionaryManager.getDictionary(DictionaryManager.CFDIC);
			String usage = ((SyntaxDictionaryInterface)syntax).getFunctionUsage(functionname.trim());
			//String usage = CFSyntaxDictionary.getFunctionUsage(functionname.trim());
			
			if(usage != null)
			{
				//bit of a hack - there are only a copule functions that have
				//several wasys to call them, so if there are more then one
				//they are sperated by ||s
				st = new StringTokenizer(usage,"||");
				
				////////////////////////////////////////////////////////////////
				//TODO figure out why this has to have 2 - it wont show otherwise
				IContextInformation[] result = new IContextInformation[st.countTokens() + 1];
				
				int i = 0;
				while(st.hasMoreTokens())
				{
					String info = st.nextToken().trim();
					result[i] = new ContextInformation(
						CFPluginImages.get(CFPluginImages.ICON_FUNC),
						info,
						""
					);
					i++;
				}
				result[i] = new ContextInformation(
					"",
					""
				);
				
				return result;
			}
			return null;
			
		}catch(Exception e)
		{
			//?
		}
		
		return null;
	}

	/**
	 * yeah...
	 */
	public String getErrorMessage() {
		return null;
	}
}
