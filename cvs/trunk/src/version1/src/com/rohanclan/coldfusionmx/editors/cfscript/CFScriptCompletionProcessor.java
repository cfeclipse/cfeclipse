/*
 * Created on Feb 27, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver B. Tupman <otupman@dts-workshop.com>
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
package com.rohanclan.coldfusionmx.editors.cfscript;

import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import org.eclipse.jface.text.Position;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.DeleteEdit;

import org.eclipse.jface.text.*;

import com.rohanclan.coldfusionmx.util.*;
import com.rohanclan.coldfusionmx.dictionary.*;

import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Set;
import java.util.HashSet;
import java.lang.Character;
import com.keygeotech.utils.Debug;

/**
 * @author ollie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
/**
 * @author ollie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
/**
 * @author OLIVER
 *
 * This handles CFScript code insight / completion for cfscript blocks. It 
 * provides a number of facilities:
 * 	1) Code insight
 * 	2) Automatic bracket insertion (and subsequent matching of any typed close bracket)
 * 	3) Automatic parentheses insertion (ditto)
 * 
 * Externally the CFScriptCompletionProcessor defines a number of difference charcters
 * that may trigger code insight. Internally we actually use the characters defined
 * in the member variable 'completionChars'. For these charcters we actually will provide
 * code insight.
 * 
 * The other characters, found in 'matchChars', are characters that we perform other non-
 * code insight functions for. For example, when encountering a '{' the class will insert
 * a matching '}' for the user. If the user enters a ')' or '}' the class will check to
 * see whether it can find a matching opening character. If it does so, then it will
 * not allow the editor to insert the closing character and simply 'skip' to the next, just
 * past the existing closing character (that was probably inserted by the class earlier on).
 * 
 * TODO: If I get this to work, stick in quote matching as well, oh, and hash (pound sign for you USA people) matching
 *  
 * @r2 - if this gets tasty we may want to use it everywhere a function can be 
 */
public class CFScriptCompletionProcessor implements IContentAssistProcessor {
	private static final short TAGTYPE = 0;
	private static final short ATTRTYPE = 1;
	private String className = "CFScriptCompletionProcessor";
	
	protected static final String completionChars = ".(;";
	// TODO: Add '#' to the list, once I've decided how to cope with it.
	protected static final String closerChars = ")}";
	protected static final String openerChars = "({";

	protected static final String close2openMatchChars = ")(}{##"; 
	
	/**
	 * What characters cause us to wake up (for tags and attributes)
	 * 
	 * This is based upon the two strings that define the characters for which
	 * we provide code insight and the characters that we simply wish to do
	 * a match-check.
	 */
	public char[] getCompletionProposalAutoActivationCharacters() {
		return (completionChars + closerChars + openerChars).toCharArray();
	}

	/**
	 * What characters cause us to wake up (for functions)
	 */
	public char[] getContextInformationAutoActivationCharacters() {
		return new char[] { '+' };
	}
 
	
	
	/**
	 * Startup the completer
	 */
	public CFScriptCompletionProcessor(){
	}

	/*
	 * SpacerCharacter
	 * 
	 * Returns whether the character passed is a 'spacer character' (see comment for 
	 * FindItemStart()). Currently just tests to see whether it's a letter or not
	 * and returns the result.
	 */
	protected boolean SpacerCharacter(char inChar)
	{
		return !Character.isLetter(inChar);
	}
	
	/*
	 * FindItemStart
	 * 
	 * Searches backwards through inString looking for a 'spacer character'. A spacer character
	 * is something that, erm, spaces elements. For example:
	 * 
	 * ArrayAppend(myArray, ArrayNew(1
	 * 
	 * If the cursor is at the end of the line above, the spacer is between "myArray," and
	 * "ArrayNew(1". I.e. it's the space. Then again, if the line read "myArray,ArrayNew(1"
	 * then the spacer character is the comma. The test for a spacer character is performed
	 * by SpacerCharacter().
	 */
	protected int FindItemStart(String inString, int startPos)
	{
		int strPos = startPos;
		for(; strPos > 0; strPos--)
		{
			char currChar = inString.charAt(strPos);
			
			if(SpacerCharacter(currChar))
				break;
		}
		
		return strPos;
	}
	
	
	
	protected int BracketScan(String input, int startPos)
	{
		return BalanceScan(input, '(', ')', startPos, true);
	}
	
	/*
	 * BalanceScan
	 * 
	 * Balance scan travels through a string attempting to find balance between
	 * the opener and closer characters. Balance is 0.

	 * 				// Nope, search until we find a completion char.
				// Brackets are a special case. If we're in a nested function then
				// simply searching fo a completion char would be fine. But if we're
				// in a function that has nested functions, we need to by-pass them
				// and find the original, current function.
				// In theory we use a bracket counter to do this.

	 * 	 * 
	 */
	protected int BalanceScan(String input, char opener, char closer, 
								int startPos, boolean backwards)
	{
		int searchPos = startPos;
		int balanceCount = 1;
		int increment = (backwards) ? -1 : 1;
		
		for(; searchPos > 0; searchPos+= increment)
		{
			char searchChar = input.charAt(searchPos);
			
			if(completionChars.indexOf(searchChar) != -1 
			   || searchChar == closer)
			{
				if(searchChar == opener)
					balanceCount--;
				else if(searchChar == closer)
					balanceCount++;
				else
					break;
				// If we reach 0, then we've no more brackets
				// and we're at the right place.
				if(balanceCount == 0)
					break;
			}
		}		
		
		return searchPos;
	}
	
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
		int documentOffset) {
		String mName = "computeCompletionProposals";
		HashSet proposals = new HashSet();
		String messages = "";
		int length = 40;
		
		try {
			String invoker = viewer.getDocument().get(documentOffset-1,1);
			IDocument document = viewer.getDocument();
			int start = document.getPartition(documentOffset).getOffset();
			String scanData =	document.get(start, documentOffset - start);
			String originalData = scanData;	// This should never be changed.

			scanData = scanData.replace('\n',' ');	// Eliminate any non-character characters
			scanData = scanData.replace('\r',' ');	// as this allows us to treat the buffer as
			scanData = scanData.replace('\t',' ');	// one long string

			String needle = "cfcatch";
			char lastChar = scanData.charAt(scanData.length()-1);
			int triggerPos = scanData.length()-1;
			int closerCharMatch = closerChars.indexOf(lastChar);
			if(closerCharMatch != -1)
			{
				// Closing bracket scan will fail if the start of the function string is at the
				// start of the document partition.
				// TODO: Get closing character scan working on 0 starting partitions (i.e. # blocks)
				char closer = lastChar;
				//char opener = close2openMatchChars.charAt(close2openMatchChars.indexOf(lastChar) + 1);
				char opener = openerChars.charAt(closerCharMatch);
				
				if(BalanceScan(scanData, opener, closer, triggerPos-1, true) > 0)
				{
					// TODO: Test to see whether this will fail at the end of the document partition
					// Should this really live here, and is there a better way of doing it?
					// Something pre-display?
					if(documentOffset + 1 < document.getLength())
					{
					//	String restOfDocument = document.get(documentOffset + 1, 
					//										document.getLength() - documentOffset-1);
					//	document.replace(documentOffset, restOfDocument.length()-1, restOfDocument);
						//InsertEdit newEdit = new InsertEdit(documentOffset+1, ")");
						DeleteEdit charRemoval = new DeleteEdit(documentOffset, 1);
						charRemoval.apply(document);

					}
				}
				else
					Debug.println("Not a closing bracket. Is this not one?: '" + lastChar + "'");
				return null;
			}
			//
			// Is the char just typed a proper trigger char? See decl of completionChars for info
			else if(completionChars.indexOf(lastChar) == -1 && lastChar != '{')
			{
				int searchPos = triggerPos;
				int bracketCount = 1;
				searchPos = BracketScan(scanData, triggerPos);
				//
				// Cut off the part that is nothing to do with the function
				// search. Later on we'll use something else to cut from so
				// we can work out which parameter we're in.
				scanData = scanData.substring(0, searchPos+1);		
				lastChar = scanData.charAt(scanData.length()-1);
				triggerPos = searchPos;
			}
			else
			{
				String restOfDocument = document.get(documentOffset, document.getLength() - documentOffset);
				String docBefore = document.get(0, documentOffset);
				String extraData = "";
				switch(lastChar)
				{
					case '{':
						//
						// Opening a parenthesis causes an closing one to be inserted, but
						// it really should follow the tab count the user is on. So we back-track
						// through the document looking for the start of tabs. Once we have found
						// them we simply add a tab for every tab we find.
						int strPos = documentOffset-1;
						for(; strPos > 0 && docBefore.charAt(strPos) != '\t'; strPos--);
						{;}
						for(; strPos > 0 && docBefore.charAt(strPos) == '\t'; strPos--)
						{
							extraData+= "\t";
						}
						
						extraData+= "\n}";
						break;
					case '\"':
						extraData = "\"";
						break;
					case '(':
						extraData = ")";
						Debug.println(mName, this, "Inserting closing bracket");
						break;
					default:
						break;
				}

				InsertEdit newEdit = new InsertEdit(documentOffset, extraData);
				newEdit.apply(document);
				
				Debug.println(mName, this, "NOT: '" + scanData + "'");
				
				if(lastChar != '(')
					return null;
			}
			
			switch(lastChar)
			{
				case '.':
					// Period for a struct or CFC
					// TODO: Implement variable insight (just hard code if for the moment).
					messages = " - I this it\'s a period.";
					break;
				case '(':
					messages = " - In a function... maybe";
					Debug.println(mName, this, messages);
					int strPos = FindItemStart(scanData, triggerPos - 1); 
					
					if(strPos == 0)
					{
						messages = "Reached start of string.";
					}
					else
					{
						//
						// TODO: Indent is getting large, stick this in a separate method
						// TODO: Work out which argument we are currently in and highlight it
						// TODO: Possibly mark when the parameter being passed to the argument is incorrect?
						String toBeMatched = scanData.substring(strPos+1, triggerPos);
						messages = "End of brackets at " + strPos;
						messages+= ". String I think we will have is '" + toBeMatched + "'";
						Debug.println(mName, this, messages);
						
						//... just so it works with the new dictionaries ...
						String usage = 
							((SyntaxDictionaryInterface)DictionaryManager.getDictionary(DictionaryManager.CFDIC)).getFunctionUsage(toBeMatched);
						
						//String usage = CFSyntaxDictionary.getFunctionUsage(toBeMatched);
						if(usage == null)
						{
							Debug.println(mName, this, "Cannot found a match for '" + toBeMatched + "'");
							return null;
						}
						proposals.add(usage);
						messages = usage;
						//
						// Has the user just opened a bracket?
						// If so we stick a closing one in for them
						/*
						if(triggerPos == originalData.length()-1)
						{
							// TODO: This routine grabs the rest of the doc partition... must be slow?
							String restOfDocument = document.get(documentOffset, document.getLength() - documentOffset-1);
							restOfDocument = ")" + restOfDocument;
							
							document.replace(documentOffset, restOfDocument.length()-1, restOfDocument);
						}
						*/
					}
					break;
				default:
					messages = "Received the character " + lastChar;
					//
					// Think some sort of combined bracket + spacer character search needs to
					// occur here. Not sure how though. Basically it wants to try and match
					// the originating bracket.
					//	Good demo is this one:
					// Len(asdfasdf, ArrayAppend(fred, ArrayNew(asdf), fred))
					break;	
			}
			Debug.println(mName, this, messages);
			
		}
		catch (BadLocationException e)
		{
			Debug.println(mName, this, "Caught a bad location exception!");
		}
		catch (Exception e)
		{
			Debug.println(mName, this, "Caught exception: " + e.getMessage());
		}
		Debug.println("");
		if(proposals.isEmpty())
			return null;
		
		length = 10;
		
		return makeSetToProposal(proposals, documentOffset, TAGTYPE, length);
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
