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
package org.cfeclipse.cfml.editors.contentassist;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.dictionary.Function;
import org.cfeclipse.cfml.dictionary.SyntaxDictionary;
import org.cfeclipse.cfml.editors.contentassist.AssistContributor;
import org.cfeclipse.cfml.editors.contentassist.AssistUtils;
import org.cfeclipse.cfml.editors.contentassist.CFMLFunctionAssist;
import org.cfeclipse.cfml.editors.contentassist.DefaultAssistState;
import org.cfeclipse.cfml.editors.contentassist.IAssistContributor;
import org.cfeclipse.cfml.editors.contentassist.IAssistState;
import org.cfeclipse.cfml.editors.contentassist.TemplateAssist;
import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.cfeclipse.cfml.preferences.AutoIndentPreferenceConstants;
import org.cfeclipse.cfml.preferences.CFMLPreferenceManager;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.UndoEdit;

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
public class CFMLFunctionAssistContributor extends AssistContributor implements IAssistContributor {
	private static final short TAGTYPE = 0;
	//private static final short ATTRTYPE = 1;
	//private String className = "CFScriptCompletionProcessor";
	
	private String DictionaryToUse = DictionaryManager.CFDIC;

	private IAssistState fState;

	private boolean fSessionStarted;
	
	// Define the characters that make up the activation set.
	// There are three parts to the activation set:
	
	// 1) The standard completion chars. These are some activation characters 
	//that non-opener/closer characters
	// dunno tha twe need strings in here
//	protected static final String completionChars = "(;~\"#[\',abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	protected static final String completionChars = "(~#[,";

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
		return (completionChars).toCharArray();
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
	public CFMLFunctionAssistContributor(){
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
	

	/**
	 * gets the function prefix
	 * 
	 * @param viewer
	 *            the viewer
	 * @param offset
	 *            the offset left of which the prefix is detected
	 * @return the detected prefix
	 */
	protected String extractPrefix(ITextViewer viewer, int offset) {
		IDocument document = viewer.getDocument();
		int i = offset;
		if (i > document.getLength())
			return ""; //$NON-NLS-1$

		try {
			while (i > 0) {
				char ch = document.getChar(i - 1);
				if (ch != ';' && !Character.isJavaIdentifierPart(ch))
					break;
				i--;
			}
			return document.get(i, offset - i);
		} catch (BadLocationException e) {
			return ""; //$NON-NLS-1$
		}
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
		//String mName = "computeCompletionProposals";
		HashSet proposals = new HashSet();
		//String messages = "";
		//int length = 40;
		// TODO: Use the tokeniser!
		
		try {
			String invoker = viewer.getDocument().get(documentOffset-1,1);

	        changeDictionary(DictionaryManager.CFDIC);			

	
			IDocument document = viewer.getDocument();
			int start = document.getPartition(documentOffset).getOffset();
			String scanData =	document.get(start, documentOffset - start);
			scanData = scanData.replace('\n',' ');	// Eliminate any non-character characters
			scanData = scanData.replace('\r',' ');	// as this allows us to treat the buffer as
			scanData = scanData.replace('\t',' ');	// one long string
				String toBeMatched = extractPrefix(viewer,documentOffset).replaceAll("[\r\n\t]", " ").trim();
				if(toBeMatched.length() <= 0) {
					//return null;
				}
				Set poss = SyntaxDictionary.limitSet(
						DictionaryManager.getDictionary(DictionaryToUse).getAllFunctions(),
						toBeMatched
					);
				ICompletionProposal[] theseProps = makeSetToProposal(poss, documentOffset, TAGTYPE, toBeMatched.length());
		        return theseProps;

			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
			//Debug.println(mName, this, "Caught exception: " + e.getMessage());
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
			
////			hack job for blank proposal until proposals are less aggressive ticket #543
//			result[z] = new CompletionProposal(
//					"",
//					offset, 
//					0, 
//					0,
//					null,
//					"",
//					null,
//					""
//				);
//			z++;
////			hack over
			while(i.hasNext())
			{
				Function fun = (Function)i.next();
				
				//the toLowerCase is because of the createobject hack, we need
				//to either make a setting in prefs for upper/lower or find a
				//better way to handle the createobject case
				// dunnow what creatobject hack is, trying to comment out : denny
				//String name = fun.getName().toLowerCase();
				String name = fun.getName();
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
				// add a closing(
				name += "(";
				//default to the tag len and icon
				insertlen = name.length();
				name += ")";
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

	public String getId() {
		return "function.proposals";
	}

	public String getName() {
		return "Function Proposals";
	}

	public ICompletionProposal[] getTagProposals(IAssistState state) {
		if(!preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_SUGGEST_FUNCTIONS)) {
			return null;
		}
		
		fState = state;
		char activator = fState.getTriggerData();
		
		char[] activationChars = getCompletionProposalAutoActivationCharacters();
		String activationString = String.copyValueOf(activationChars, 0, activationChars.length);
		int wee = Arrays.asList(activationChars).indexOf(activator);
        if (state.getTriggerData() == ' '
        	|| state.getTriggerData() == '\t') {
            return null;
        }
		if(activationString.indexOf(activator) == -1) {
		//if(activationString.indexOf(activator) != -1 || fSessionStarted) {
			return computeCompletionProposals(state.getITextView(), state.getOffset());			
		} else {
			return null;
		}
	}
	
	@Override
	public void sessionStarted() {
		fSessionStarted = true;
		super.sessionStarted();
	}
	
	@Override
	public void sessionEnded() {
		fSessionStarted = false;
		super.sessionEnded();
	}
}
