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
package com.rohanclan.cfml.editors.cfscript;

import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
//import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
//import org.eclipse.jface.text.contentassist.IContextInformationPresenter;

// Need the following for inserting & deleting text (duh).
// Not sure where to pass the resulting UndoEdits though.
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.UndoEdit;

import org.eclipse.jface.text.*;
import java.util.StringTokenizer;
import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.dictionary.*;
import com.rohanclan.cfml.editors.CFPartitionScanner;
import com.rohanclan.cfml.editors.contentassist.AssistUtils;

import java.util.TreeSet;
import java.util.Set;
import java.util.HashSet;
import java.lang.Character;
import java.util.Iterator;
import org.eclipse.swt.graphics.Image;

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
	
	private String DictionaryToUse = DictionaryManager.CFDIC;
	
	// Define the characters that make up the activation set.
	// There are three parts to the activation set:
	
	// 1) The standard completion chars. These are some activation characters 
	//that non-opener/closer characters
	protected static final String completionChars = ".(;~\"#[\'>,";

	// 2) The opener/closer characters. This assists with the opening & closing 
	//of things such as brackets
	protected static final String closerChars = "})#]\'";
	protected static final String openerChars = "{(#[\'";

	// This is a simple map between the character that closes a pair to the 
	//character that does the opening.
	protected static final String close2openMatchChars = "\'\')(}{##\"\"]["; 
	
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
	 * change the dictionary this uses to the pass dictionary name (use 
	 * DictionaryManager to get the name. This is here so we can re-use this
	 * for the JS partition. (we should really make only one completion class
	 * somehow)
	 * 
	 * @param to the new dictionary to use
	 * 
	 * @author Rob
	 */
	public void changeDictionary(String to)
	{
		DictionaryToUse = to;
	}
	
	/**
	 * Startup the completer
	 */
	public CFScriptCompletionProcessor(){
	}

	/*
	 * @author Oliver
	 * 
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
	 * @author Oliver
	 * 
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
			
			if(currChar == ';')
				return -1;
			
			if(SpacerCharacter(currChar))
				break;
		}
		
		return strPos;
	}
	
	protected int FindItemStartIgnoreCommas(String inString, int startPos)
	{
		int strPos = startPos;
		for(; strPos > 0; strPos--)
		{
			char currChar = inString.charAt(strPos);

			if(SpacerCharacter(currChar) && currChar != ',')
				break;
		}
		
		return strPos;
	}
	
	
	protected int BracketScan(String input, int startPos)
	{
		return BalanceScan(input, '(', ')', startPos, true);
	}
	
	/*
	 * @author Oliver
	 * 
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
		return BalanceScan(input, opener, closer, startPos, backwards, 0);
	}
	

	
	
	protected int BalanceScan(String input, char opener, char closer, 
								int startPos, boolean backwards, int balancePoint)
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
				if(balanceCount == balancePoint)
					break;
			}
		}	
		
		
		return searchPos;
	}
	
	/*
	 * @author Oliver
	 * 
	 * DeleteText
	 */
	protected UndoEdit  DeleteText(IDocument doc2Alter, int offset, int length)
	{
		DeleteEdit charRemoval = new DeleteEdit(offset, length);
		UndoEdit removal = null;
		try {
			removal = charRemoval.apply(doc2Alter);
		} catch (BadLocationException e) {
			// Doing nowt right now. The Java editor doesn't, so should we?
		}
		return removal;
	}
	
	protected UndoEdit  InsertText(IDocument doc2Alter, int offset, String newText)
	{
		UndoEdit insertion = null;
		InsertEdit newEdit = new InsertEdit(offset, newText);
		try {
			newEdit.apply(doc2Alter);
		} catch(BadLocationException e) { // And context for the worst variable names goes to me!
			// Still doing nowt...
		}
		return insertion;
	}
	
	
	private void DebugFunction(ITextViewer viewer, int documentOffset)
	{
		String mName = "DebugFunction";
		IDocument doc = viewer.getDocument();
		try {
			int start = doc.getPartition(documentOffset).getOffset(); // Not sure what this does!
			
			String prefix =	doc.get(start, documentOffset - start); 
			StringTokenizer	tokeniser = new StringTokenizer(prefix, ";");
			
			prefix = prefix.replace('\n',' ');	// Eliminate any non-character characters
			prefix = prefix.replace('\r',' ');	// as this allows us to treat the buffer as
			prefix = prefix.replace('\t',' ');	// one long string
			
			
			//Debug.println(mName, this, "Partition start: " + start);
			//Debug.println(mName, this, "Prefix is: '" + prefix + "'");
			int tokenCount = 0;
			String output = "";
			while(tokeniser.hasMoreTokens())
			{
				output+= "[" + tokenCount + "] Got this token: '" + tokeniser.nextToken() + "'\n";
				tokenCount++;
			}
			//Debug.println(mName, this, "Tokeniser output:\n" + output);
		} catch(BadLocationException e) {
			//Debug.println(mName, this, "Caught a BadLocationException");
		} catch(Exception anyE)	{
			//Debug.println(mName, this, "Caught a random exception. Message is: " + anyE.getMessage());
			
		}
		
	}
	
	/**
	 * <b>HandleCloser</b> - Helper function for computeCompletionProposals()
	 * 
	 * The calling function must establish that a character is to be closed 
	 * (generally a ')'). For example:
	 * <pre>
	 * ArrayAppend(myArray, ArrayLen())
	 *                               ^
	 * </pre>
	 * 
	 * Now the CFScript completion processor automatically adds the closing bracket when
	 * the user opens one. Therefore to save the keypress count of the user the completer
	 * attempts to match the closing brackets typed by the user with the close brackets
	 * already existing.   
	 * 
	 * @author Oliver
	 * @param document The document to work upon
	 * @param scanData	The post-processing, pre-completion text of the document
	 * @param currentChar The character that is the closer
	 * @param matchPos The position that the match has been found at
	 * @param triggerPos The completion trigger position
	 * @param documentOffset Offset within the document (surely the same as the triggerPos?)
	 * @return A instance of an UndoEdit if the deletion took place, otherwise null.
	 */
	protected UndoEdit HandleCloser(IDocument document, String scanData, char currentChar, 
									int matchPos, int triggerPos, int documentOffset)
	{
		// TODO: Get closing character scan working on 0 starting partitions (i.e. # blocks)
		char closer = currentChar;
		char opener = openerChars.charAt(matchPos);
		if(documentOffset + 1 >= document.getLength())	// Don't want to go out of bounds
		{
			return null;
		}
		try {
			// First make sure that the next char is a closing char.
			// TODO: Do some funky forward closer matching so it doesn't have to be the next char
			char nextChar = document.getChar(documentOffset);
			if(nextChar == closer)
			{
				if(BalanceScan(scanData, opener, closer, triggerPos-1, true) > 0)
				{
					// TODO: Test to see whether this will fail at the end of the document partition
					// Should this really live here, and is there a better way of doing it?
					if(documentOffset + 1 < document.getLength())
					{
						return DeleteText(document, documentOffset, 1);
					}
				}
				//else
					//Debug.println("Not a closing bracket. Is this not one?: '" + currentChar + "'");
			}
		} catch(BadLocationException excep) {
			//Debug.println("HandleCloser", this, "Caught BadLocationException when getting a char");
		}
		return null;
	}
	
	protected int findAndCountTabs(IDocument document, int startPos)
	{
		int tabCount = 0;
		int strPos = startPos;
		try {
			for(; strPos > 0 && document.getChar(strPos) != '\t'; strPos--);
			{;}
			for(; strPos > 0 && document.getChar(strPos) == '\t'; strPos--)
			{ tabCount++; }
		} catch(BadLocationException excep) {
			//Debug.println("findAndCountTabs", this, "Caught a BadLocationException whilst counting tabs.");
		}
		return tabCount;
	}
	
	/**
	 * <b>HandleOpener</b> - Performs the document operations when the user triggers an 'opener' character.
	 * 
	 * HandleOpener() takes the opener character and in general it inserts a matching closing item. Therefore
	 * in the case of '(' it sticks in a ')'. 
	 * 
	 * Life is slightly more complicated for '{' because these follow a tab strategy. Ours is to simply 
	 * count the tabs of the line where the opener was entered and then	insert a carriage return and 
	 * then the tabs and then the closer. Life is made even more complicated by the fact that really the
	 * routine should not enter a closer if one exists at the current tab level with &lt;x&gt; lines...
	 * at least it shouldn't. Not implemented yet.
	 *
	 * Note that characters such as '#' and '"' are not handled because there is no way of distinguishing
	 * between the user entering a closing character or performing the necessary double character ('##')
	 * for CF to insert that character in a string.
	 * 
	 * Doh, there's a solution sat somewhere in my brain but it's not popping out :( Ah well, someone brainier
	 * can sort it!
	 * 
	 * @param document - The document that the user is editing. 
	 * @param currentChar - The character that we are trying to opener (probably at documentOffset) 
	 * @param documentOffset - Offset at the document that the trigger was caused.
	 * @return UndoEdit - The undo actions to perform to undo the operation.
	 * @author Oliver
	 */
		
	protected UndoEdit handleOpener(IDocument document, char currentChar, int documentOffset)
	{
		String extraData = "";
		String mName = "HandlerOpener";
		switch(currentChar)
		{
			case '{':
				int tabCount = findAndCountTabs(document, documentOffset);
				extraData += "\n";
				for(int tabCnt = 0; tabCnt < tabCount; tabCnt++)
					extraData += "\t";
				/*
				 * Doesn't work very nicely. Not in at the moment.
				int maxCheckLength = 100;	// Closer in 100 chars?
				int tempPos = documentOffset;
				for(; tempPos < document.getLength() && tempPos - documentOffset < maxCheckLength; tempPos++)
				{
					try {
						if(document.getChar(tempPos) == '}')
						{
							//
							// Check to see whether the tab count for the matching } is the same as the line it
							// was entered on.
							

							return null;	// Doing nothing more, so cop out of the function completely.
						}
					} catch(BadLocationException excep) {
						Debug.println(mName, this, "Caught a bad location exception during { opener.");
						return null;
					}
				}
				*/

				extraData+= "}";
				break;
			case '#':
				extraData = "#";
				break;
			case '\"':
				extraData = "\"";
				break;
			case '\'':
				extraData = "\'";
				break;
			case '(':
				extraData = ")";
				break;
			case '[':
				extraData = "]";
				break;
			default:
				break;
		}
		return InsertText(document, documentOffset, extraData);		
	}
	
	/**
	 * Finds the start of a function from inside a function, i.e.:
	 * ArrayAppend(ArrayNew(),
	 * 
	 * @param input - the input to search through
	 * @param startPos - erm, where to start
	 * @return The position of the start of the function
	 */
	protected int FindFuncStartFromInside(String input, int startPos)
	{
		int searchPos = startPos;
		int balanceCount = 1;
		char opener = '(';
		char closer = ')';
		
		for(; searchPos > 0 && balanceCount > 0; searchPos--)
		{
			char searchChar = input.charAt(searchPos);
			
			if(searchChar == opener)
				balanceCount--;
			else if(searchChar == closer)
				balanceCount++;
		}	
		
		
		return searchPos;
	}
	
	
	/*
	 * Welcome to the horror that is the computeCompletionProposals() method.
	 * 
	 * @author Oliver
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeCompletionProposals(org.eclipse.jface.text.ITextViewer, int)
	 * 
	 * 
	 */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
		int documentOffset) {
		String mName = "computeCompletionProposals";
		HashSet proposals = new HashSet();
		String messages = "";
		int length = 40;
		// TODO: Use the tokeniser!

		//System.err.println("runnin!!!!");
		
		if(!(AssistUtils.isCorrectPartitionType(viewer, documentOffset, CFPartitionScanner.J_SCRIPT)
		     || AssistUtils.isCorrectPartitionType(viewer, documentOffset, CFPartitionScanner.CF_SCRIPT)))
		    return null;
	
		try {
			String invoker = viewer.getDocument().get(documentOffset-1,1);
			IDocument document = viewer.getDocument();
			int start = document.getPartition(documentOffset).getOffset();
			String scanData =	document.get(start, documentOffset - start);
			String originalData = scanData;	// This should never be changed.

			scanData = scanData.replace('\n',' ');	// Eliminate any non-character characters
			scanData = scanData.replace('\r',' ');	// as this allows us to treat the buffer as
			scanData = scanData.replace('\t',' ');	// one long string
			
			char lastChar = scanData.charAt(scanData.length()-1);
			
			
			// Gonna allow debug by entering the tilde character. Entering this will cause
			// my debug function to run...
			if(lastChar == '~')
			{
				DebugFunction(viewer, documentOffset);
			}

			int triggerPos = scanData.length()-1;
			int closerCharMatch = closerChars.indexOf(lastChar);

			// First test to see whether the last two characters are "it"...  if so then we'll
			// quit out of this
			if(lastChar == '\'' && document.getChar(documentOffset-1) == 't' && 
			   document.getChar(documentOffset-2) == 'i')
			{
				//System.out.println("hit IT");
				return null;
			}
			
			if(completionChars.indexOf(lastChar) == -1 && lastChar != '{')
			{
				//System.out.println("hit this other part");
				
				int searchPos = triggerPos;
				int bracketCount = 1;
				searchPos = BracketScan(scanData, triggerPos);
				//
				// Cut off the part that is nothing to do with the function
				// search. Later on we'll use something else to cut from so
				// we can work out which parameter we're in.
				scanData = scanData.substring(0, searchPos+1);
				//System.out.println(originalData);
				lastChar = scanData.charAt(scanData.length()-1);
				triggerPos = searchPos;
			}
			else
			{
				//System.out.println("hit opener");
				//handleOpener(document, lastChar, documentOffset);
				
				if(lastChar != '(' && lastChar != ',')
					return null;
			}

			switch(lastChar)
			{
				case '.':
					// Period for a struct or CFC
					// TODO: Implement variable insight (just hard code if for the moment).
					messages = " - I this it\'s a period.";
					break;
				case ',':
					
					//
					// First check: is there an open bracket after a semicolon, if so
					// then we're in business.
					int prevBracket = scanData.lastIndexOf('(');
					int prevSemicolon = scanData.lastIndexOf(';');
					if(prevBracket < prevSemicolon)
						return null;

					//
					// Call FindFuncStartFromInside() to work out where the start of the function is.
					triggerPos = FindFuncStartFromInside(scanData, triggerPos) + 1;
					//System.out.println("CFScriptCompletionProcessor - substring result: "+ triggerPos + "scanData: \'" + scanData.substring(0, triggerPos) + "\'\n");
				case '(':
					messages = " - In a function... maybe";
					//Debug.println(mName, this, messages);
					int strPos = FindItemStart(scanData, triggerPos - 1); 
					
					if(strPos == 0)
					{
						messages = "Reached start of string.";
					}
					else
					{
						//System.err.println("getting here");
						//
						// TODO: Work out which argument we are currently in and highlight it
						// TODO: Possibly mark when the parameter being passed to the argument is incorrect?
						String toBeMatched = scanData.substring(strPos+1, triggerPos);
						
						//Function fun = DictionaryManager.getDictionary(DictionaryManager.CFDIC).getFunction(toBeMatched);
						//String usage = fun.toString(); 
							
						//if(usage == null)
						//{
							//Debug.println(mName, this, "Cannot found a match for '" + toBeMatched + "'");
						//	System.err.println("Cannot found a match for '" + toBeMatched + "'");
						//	return null;
						//}
						
						Set poss = SyntaxDictionary.limitSet(
							DictionaryManager.getDictionary(DictionaryToUse).getAllFunctions(),
							toBeMatched
						);
						
						return makeSetToProposal(poss, documentOffset, TAGTYPE, toBeMatched.length());
						//proposals.add(fun);
					}
					break;
				case ';':
					break;
				case '>':
					break;
				case '\"':
					break;
				default:
					messages = "Received the character " + lastChar;
					//
					// Think some sort of combined bracket + spacer character search needs to
					// occur here. Not sure how though. Basically it wants to try and match
					// the originating bracket.
					//	Good demo is this one:
					// Len(asdfasdf, ArrayAppend(fred, ArrayNew(asdf), fred))
						
					//r2 QA 2004-05-15 
					//quick an dirty way to see all functions (hitting ctrl+space
					//but this doesn't limit the selections and only works when
					//not in a function :-/
					Set mst = DictionaryManager.getDictionary(DictionaryToUse).getAllFunctions();
					return makeSetToProposal(
						mst,
						documentOffset, 
						TAGTYPE, 
						0
					);				
					//break;	
			}
			//Debug.println(mName, this, messages);
			
		}
		catch (BadLocationException e)
		{
			//Debug.println(mName, this, "Caught a bad location exception!");
		}
		catch (Exception e)
		{
			//Debug.println(mName, this, "Caught exception: " + e.getMessage());
		}
		//Debug.println("");
		if(proposals.isEmpty())
			return null;
		
		//length = 10;
		
		return makeSetToProposal(proposals, documentOffset, TAGTYPE, 0); //length);
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
		//r2 QA 2004-05-15
		//Moved to the new function object way from the original string way
		//like I first had the dictionary...
		//System.out.println("got " + offset + " " + type + " " + currentlen + " " + st.size());
		if(st != null)
		{
			st = new TreeSet(st);
			
			//build a Completion dodad with the right amount of records
			CompletionProposal[] result = new CompletionProposal[st.size()];
			
			int z=0;
			Iterator i = st.iterator();
			while(i.hasNext())
			{
				Function fun = (Function)i.next();
				
				//the toLowerCase is because of the createobject hack, we need
				//to either make a setting in prefs for upper/lower or find a
				//better way to handle the createobject case
				String name = fun.getName().toLowerCase();
				//now remove chars so when they hit enter it wont write the whole
				//word just the part they havent typed
				if(name.length() > 0)
				{
					name = name.substring(currentlen, name.length());
					//System.out.println("makeSetToProposal" + "in::" + name);
				}
				
				//the tag len and icon
				int insertlen = 0;
				Image img = null;
				//if there is anyof the function name left add a closing(
				if(name.length() > 0) name += "(";
				//default to the tag len and icon
				insertlen = name.length();
				img = CFPluginImages.get(CFPluginImages.ICON_FUNC);
				
				result[z] = new CompletionProposal(
					name,
					offset, 
					0, 
					insertlen,
					img,
					fun.toString(),
					null,
					fun.getHelp()
				);
				z++;
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
	 * TODO check out cfcompletion this has changed a bit... maybe we should
	 * workout some way to make these call the same place?
	 */
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
		int documentOffset) {
		/* String mName = "computeContextInformation";
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
			
			//String usage = ((SyntaxDictionaryInterface)DictionaryManager.getDictionary(DictionaryManager.CFDIC)).getFunctionUsage(functionname.trim());
			//String usage = CFSyntaxDictionary.getFunctionUsage(functionname.trim());
			// Dictionary change again
			Function fun = DictionaryManager.getDictionary(DictionaryManager.CFDIC).getFunction(functionname.trim());
			String usage = fun.toString(); 
			
			if(usage != null)
			{
				//bit of a hack - there are only a copule functions that have
				//several wasys to call them, so if there are more then one
				//they are sperated by ||s
				//st = new StringTokenizer(usage,"||");
				
				////////////////////////////////////////////////////////////////
				//TODO figure out why this has to have 2 - it wont show otherwise
				//IContextInformation[] result = new IContextInformation[st.countTokens() + 1];
				IContextInformation[] result = new IContextInformation[2];
				
				int i = 0;
				//while(st.hasMoreTokens())
				//{
					//String info = st.nextToken().trim();
					result[i] = new ContextInformation(
						CFPluginImages.get(CFPluginImages.ICON_FUNC),
						//info,
						usage,
						//""
						fun.getHelp()
					);
					i++;
				//}
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
		*/
		return null;
	}

	/**
	 * yeah...
	 */
	public String getErrorMessage() {
		return null;
	}
}
