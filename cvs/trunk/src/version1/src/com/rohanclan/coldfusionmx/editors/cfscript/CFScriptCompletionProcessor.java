/*
 * Created on Feb 27, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.coldfusionmx.editors.cfscript;

import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import org.eclipse.jface.text.*;

import com.rohanclan.coldfusionmx.util.*;
import com.rohanclan.coldfusionmx.dictionary.*;

//import org.eclipse.swt.graphics.Image;
//import com.rohanclan.coldfusion.ColdfusionPlugin;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Set;
import java.util.HashSet;
import java.lang.Character;
import com.keygeotech.utils.Debug;

/**
 * @author OLIVER
 *
 * This handles CFScript code insight / completion for cfscript blocks
 * @r2 - if this gets tasty we may want to use it everywhere a function can be 
 */
public class CFScriptCompletionProcessor implements IContentAssistProcessor {
	private static final short TAGTYPE = 0;
	private static final short ATTRTYPE = 1;
	private String className = "CFScriptCompletionProcessor";
	/**
	 * Startup the completer
	 */
	public CFScriptCompletionProcessor(){
		
	}

	/**
	 * for tag and attribute insight
	 * this whole thing is a bit of a hack, but basically it looks at the current
	 * partition and tokenizes the contents to the current offset. It then tries
	 * to figure out what is being typed and limits the suggested items accordingly
	 * ... you know code completion ... :)
	 */
	
	protected boolean SpacerCharacter(char inChar)
	{
		return !Character.isLetter(inChar);
	}
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
		int documentOffset) {
		String mName = "computeCompletionProposals";
		HashSet proposals = new HashSet();
		String messages = "";
		int length = 40;
		
		try {
			String limiting = "";
			
			//assume its not a cftag
			boolean cftag = false;
			
			//what invoked us a space or a f?
			String invoker = viewer.getDocument().get(documentOffset-1,1);
			
			IDocument document = viewer.getDocument();
			int start = document.getPartition(documentOffset).getOffset();
			String scanData =	document.get(start, documentOffset - start);
			String preTagCheck = scanData;
			
			scanData = scanData.replace('\n',' ');
			scanData = scanData.replace('\r',' ');
			scanData = scanData.replace('\t',' ');
			
			/*<cfscript>
				if(fred = 1)
				{
					ArrayAppend(fred, ArrayNew(1));
				}
			</cfscript>*/
			messages = mName + "In. Data: '" + scanData + "'. Type: " + 
			document.getPartition(documentOffset).getType();
			
			Debug.println(messages);
			proposals.add(messages);
			
			String needle = "cfcatch";
			char lastChar = scanData.charAt(scanData.length()-1);
			int triggerPos = scanData.length()-1;
			String completionChars = "#.(;";

			//
			// Is the char just typed a proper trigger char?
			if(completionChars.indexOf(lastChar) == -1)
			{
				// Nope, search until we find a completion char.
				// Brackets are a special case. If we're in a nested function then
				// simply searching fo a completion char would be fine. But if we're
				// in a function that has nested functions, we need to by-pass them
				// and find the original, current function.
				// In theory we use a bracket counter to do this.
				int searchPos = triggerPos;
				int bracketCount = 1;
				for(; searchPos > 0; searchPos--)
				{
					char searchChar = scanData.charAt(searchPos);
					
					if(completionChars.indexOf(searchChar) != -1 
					   || searchChar == ')')
					{
						if(searchChar == '(')
							bracketCount--;
						else if(searchChar == ')')
							bracketCount++;
						else
							break;
						// If we reach 0, then we've no more brackets
						// and we're at the right place.
						if(bracketCount == 0)
							break;
					}
				}
				//
				// Cut off the part that is nothing to do with the function
				// search. Later on we'll use something else to cut from so
				// we can work out which parameter we're in.
				scanData = scanData.substring(0, searchPos+1);		
				lastChar = scanData.charAt(scanData.length()-1);
				triggerPos = searchPos;
			}
			
			//return new char[] { '#', '.', '(', ';', '\t', '\n', '\r' };
			
			switch(lastChar)
			{
				case '.':
					// Period for a struct or CFC
					messages = " - I this it\'s a period.";
					break;
				case '#':
					messages = " - Ah, young padawan, we are in a hash var";
					break;
				case '(':
					messages = " - In a function... maybe";
					Debug.println(mName, this, messages);
					proposals.add(messages);
					int strPos = triggerPos - 1; 
					for(; strPos > 0; strPos--)
					{
						char currChar = scanData.charAt(strPos);
						
						if(SpacerCharacter(currChar))
							break;
					}
					if(strPos == 0)
					{
						messages = "Reached start of string.";
					}
					else
					{
						String toBeMatched = scanData.substring(strPos+1, triggerPos);
						messages = "End of brackets at " + strPos;
						messages+= ". String I think we will have is '" + toBeMatched + "'";
						Debug.println(mName, this, messages);
						proposals.add(messages);
						
						//... just so it works with the new dictionaries ...
						String usage = 
							((SyntaxDictionaryInterface)DictionaryManager.getDictionary(DictionaryManager.CFDIC)).getFunctionUsage(toBeMatched);
						
						//String usage = CFSyntaxDictionary.getFunctionUsage(toBeMatched);
						if(usage == null)
						{
							Debug.println(mName, this, "Cannot found a match for '" + toBeMatched + "'");
							return null;
						}
						messages = usage;
						
					}
					break;
				default:
					messages = " - Something I don't handle.";
					break;	
			}
			Debug.println(mName, this, messages);
			proposals.add(messages);
			
		}
		catch (Exception e)
		{
			Debug.println(mName, this, "Caught exception: " + e.getMessage());
			proposals.add("Caught exception: " + e.getMessage());
		}
		
		if(proposals.isEmpty())
			return null;
		
		length = 10;
		
		return makeSetToProposal(proposals, documentOffset, TAGTYPE, length);
		//return null;
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
				Debug.println("makeSetToProposal", this, "in::" + name);
				
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
		return new char[] { '#', '.', '(', ';', ',', '\t', '\n', '\r' };
	}

	/**
	 * What characters cause us to wake up (for functions)
	 */
	public char[] getContextInformationAutoActivationCharacters() {
		return new char[] { '+' };
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
		String mName = "computeContextInformation";
		try
		{
			//find out the line number and get the begining of the line
			int linenum = viewer.getDocument().getLineOfOffset(documentOffset);
			int linestart = viewer.getDocument().getLineOffset(linenum);
			//get the line
			String currentline = viewer.getDocument().get(linestart,documentOffset - linestart);
			Debug.println(mName, this, "Currentline: " + currentline);
			//make it a space delimited list
			currentline = currentline.replace('\"',' ');
			currentline = currentline.replace('\'',' ');
			currentline = currentline.replace('#',' ');
			Debug.println(mName, this, "Post replace: " + currentline);
			//tokenize the bad boy
			StringTokenizer st = new StringTokenizer(currentline," ");
			
			//the last item should be our function (because of documentOffset)
			Debug.println(mName, this, "Line: " + currentline);
			String functionname = "";
			while(st.hasMoreTokens())
			{
				functionname = st.nextToken();
			}
			
			
			int bracketPos = functionname.indexOf('(') + 1;
			Debug.println(mName, this, "Bracket at " + bracketPos + ", length = " + functionname.length());
			if(bracketPos != functionname.length())
			{
				functionname = functionname.substring(0, bracketPos);
			}
			
//			remove the last char (which should be the '(')
			functionname = functionname.substring(0,functionname.length() - 1);
		
			Debug.println("computeContextInformation", this, functionname.trim());
			
			String usage = ((SyntaxDictionaryInterface)DictionaryManager.getDictionary(DictionaryManager.CFDIC)).getFunctionUsage(functionname.trim());
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
