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
package com.rohanclan.cfml.editors.script;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
//import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import org.eclipse.jface.text.IDocument;
import com.rohanclan.cfml.util.CFPluginImages;
//import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Set;
//import com.rohanclan.coldfusionmx.dictionary.DictionaryManager;
import com.rohanclan.cfml.dictionary.SyntaxDictionary;
//import com.rohanclan.coldfusionmx.dictionary.SyntaxDictionaryInterface;

import com.rohanclan.cfml.dictionary.*;
//import org.eclipse.jface.text.ITextSelection;

/**
 * @author Rob
 *
 * This is a simple completion processor (also called code insight) it shows the
 * little window with possible completion suggestions. - handles Tags, attribtues,
 * and functions for the coldfusion language only (at present).
 */
public class JSCompletionProcessor implements IContentAssistProcessor {
	/** tag type */
	private static final short TAGTYPE = 0;
	/** attribute type */
	private static final short ATTRTYPE = 1;
	
	/**
	 * Startup the completer
	 */
	public JSCompletionProcessor(){;}

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
			//where we are going to lookup in sight stuff
			SyntaxDictionary syntax = null;
			
			//what invoked us a space or a f?
			String invoker = viewer.getDocument().get(documentOffset-1,1);
			
			IDocument document = viewer.getDocument();
			String current_partition = viewer.getDocument().getPartition(documentOffset).getType();
			
			//the start of the partition
			int start = document.getPartition(documentOffset).getOffset();
			//everything from the start of the partition to here
			String prefix =	document.get(start, documentOffset - start);
			
			//System.err.println("Going in" + prefix + " type " + document.getPartition(documentOffset).getType());
			
			//change any newlines or returns to a space so we can 
			//tokenize correctly
			prefix = prefix.replace('\n',' ');
			prefix = prefix.replace('\r',' ');
			prefix = prefix.replace('\t',' ');
			
			if(invoker.equals(">"))
			{
				String tagnamespace = "";
				String tagname = "script";
				
				StringBuffer sb = new StringBuffer();
				sb.append("</" + tagnamespace + tagname + ">");
				//sb.append("</script>");
				
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
					(documentOffset - fqt.length()-1),(fqt.length())
				);
				
				System.err.println(wordb4);
				
				//if it looks like the word before this is the
				//same tag close it
				if(wordb4.equalsIgnoreCase(fqt))
				{
					doc.replace(documentOffset, 0, addtag);
				}
			}
			
			//little bit-o-debug. Hit ~ to see what partiton you are in 
			//(shows in the debug window
			else if(invoker.equals("~"))
			{
				System.err.println("Partition: " + current_partition);
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
	private ICompletionProposal[] makeSetToProposal(
		Set st, int offset, short type, int currentlen)
	{
		if(st != null)
		{
			Object obj[] = new Object[st.size()];
			TreeSet ts = new TreeSet();
			ts.addAll(st);
			//obj = new TreeSet(st).toArray();
			obj = ts.toArray();
			//obj = st.toArray();
			
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
					help = ((Parameter)obj[i]).getHelp();
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
		return new char[] { '~', '>' };
	}

	/**
	 * What characters cause us to wake up (for functions / methods)
	 */
	public char[] getContextInformationAutoActivationCharacters() {
		return new char[] { '.' };
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
		
		return null;
	}

	/**
	 * yeah...
	 */
	public String getErrorMessage() {
		return null;
	}
}
