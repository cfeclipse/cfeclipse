/*
 * Created on Jul 14, 2004
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
package com.rohanclan.cfml.editors.contentassist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 
import org.eclipse.core.internal.utils.Assert;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.dictionary.Parameter;
import com.rohanclan.cfml.dictionary.SyntaxDictionary;
import com.rohanclan.cfml.dictionary.SyntaxDictionaryInterface;
import com.rohanclan.cfml.dictionary.Value;
import com.rohanclan.cfml.editors.CFPartitionScanner;
import com.rohanclan.cfml.editors.ICFEFileDocument;
import com.rohanclan.cfml.util.CFDocUtils;

/**
 * <p>
 * This is the root tag-based content assist for CFEclipse. It works out
 * whether the user is seeking insight about a tag, it's attributes or the
 * possible values for a tag attribute.
 * </p>
 * <p>
 * Having discovered which the user is requesting insight for, it then
 * calls the appropriate method that handles that insight type further.
 * Each method will calculate various bits and pieces before gathering
 * proposals from all of the registered Content Assist Contributors (CACors).
 * </p>
 * <p>
 * A CACor computes whether it wishes to provide insight. If it does
 * it returns an array of the relevant proposal type and these are then
 * merged into the overall proposals. If a CACor does not wish to 
 * contribute it simply returns null. 
 * </p>
 * <p>
 * The final set of proposals is then finalised where there are sorted,
 * icons assigned, etc. 
 * </p>
 * @author Oliver Tupman
 *
 */
public class CFContentAssist extends CFEContentAssist{
	 
	/**
	 * 
	 */
	public CFContentAssist() {
		super();
	}
	
	/**
	 * Provides a standarised user console message for debugging. 
	 * @param message The message to output to the console
	 */
	private void UserMsg(String message) {
// System.out.println("CFContentAssist - " + message);
	}
	/**
	 * Provides a standarised user console message for debugging.
	 * @param method The method logging a message
	 * @param message The message to output to the console
	 */
	private void UserMsg(String method, String message) {
// System.out.println("CFContentAssist::" + method + "() - " + message);
	}
	/**
	 * Provides a standarised user error console message for debugging.
	 * @param method The method logging a message
	 * @param message The message to output to the error console
	 */
	private void ErrMsg(String method, String message) {
		System.err.println("CFContentAssist::" + method + "() - " + message);
	}
	
	/**
	 * Helper function. Returns the value proposals for an attribute.
	 * 
	 * @param viewer The viewer, just pass in the computeCompletionProposals stuff
	 * @param offset Ditto
	 * @return Array of the proposals available.
	 */
	ICompletionProposal[] getAttributeValueProposals(IAssistTagState assistState)
	{
	    Assert.isNotNull(assistState);
	    
	    DefaultAssistAttributeState attrState = prepareForValueAssist(assistState);
	    ArrayList valueContributors = ((ICFEFileDocument)assistState.getIDocument()).getContentAssistManager().getValueAssistors();
	    Iterator CACorIter = valueContributors.iterator();
	    Set proposals = new TreeSet();
	    while(CACorIter.hasNext())
	    {
	        Value valueProps[] = ((IAssistAttrValueContributor)CACorIter.next()).getAttributeValueProposals(attrState);
	        if(valueProps == null)
	            continue;
	        
	        for(int i = 0; i < valueProps.length; i++)
	        {
	            proposals.add(valueProps[i]);
	        }
	    }
		
	    return makeSetToProposal(
				proposals,
				attrState.getOffset(),
				CFContentAssist.VALUETYPE,
				attrState.getValueSoFar().length()
			);
	}
	
	/**
	 * Pre-compiled pattern for getAttributeName
	 */
	private Pattern currAttribPattern = Pattern.compile("([\\w]*)\\s*=\\s*\\\"[\\.\\w\\s]*\\\"{0,1}$");

	/**
	 * Retrieves the attribute name tha the user has requested content assist upon.
	 * 
	 * @param input The string input that contains the tag + all of the attributes from the document
	 * @return The string name of the attribute, otherwise null
	 */
	private String getAttributeName(String input)
	{
	    Matcher attrMatcher = this.currAttribPattern.matcher(input);
	    
	    if(!attrMatcher.find())
	        return null;
	    	    
	    int startPos = attrMatcher.start(/*attrMatcher.groupCount()*/1);  
	    int endPos = attrMatcher.end(1);
	    String retStr = input.substring(startPos, endPos); 
	    return retStr;
	}
		
	/**
	 * This prepares the attribute assist state prior to proposal gather.
	 * It works out the attribute that the user has typed in and the value
	 * text that user has typed in for the attribute (if any).
	 * 
     * @param assistState The current state of context
     * @return The completed DefaultAssistAttributeState object, ready for proposal gather
     */
    private DefaultAssistAttributeState prepareForValueAssist(IAssistTagState assistState) {
        String inputText = assistState.getDataSoFar();
	    int quotes = inputText.lastIndexOf("\"");
	    int lastSpace = inputText.substring(0, quotes).lastIndexOf(" ");
		String valueSoFar = "";
		
		if(quotes != -1)
		{
			// Attribute entered, user is typing.
			valueSoFar = inputText.substring(quotes+1, inputText.length());
		}
		else
			quotes = inputText.length() - 2;
		
		if(inputText.charAt(0) != '<') {
			System.err.println("input text did not begin with an open chevron!!");
		}
		String attribute = getAttributeName(inputText);
		
		DefaultAssistAttributeState attrState = new DefaultAssistAttributeState(assistState, attribute, valueSoFar);
        return attrState;
    }

    /**
     * Checks to make sure that the string passed represents a tag in
     * a backwards scan fashion. Basically it's to determine whether
     * the following:
     * 
     * &lt;cffunction name="asdf" 
     * 
     * Is a tag and that this:
     * 
     * &lt;cffunction name="asdf"&gt; is not in a tag
     * 
     * Also has to cope with this valid CFML:
     * 
     * &lt;cffunction name="adf" hint="&lt;&gt;"&gt;
     * 
     * TODO: This is a bodge job because the partitioner doesn't work correctly. I believe this method could be used for it!
     * 
     * @return True - the string represents a tag, false - not a tag 
     */
    private int lastOpenChevronPos = 0;
    private  boolean checkActuallyInTag(String cursorPrefix)
    {
        Assert.isNotNull(cursorPrefix);
        
        int chevronCount = 0;
        int quoteCount = 0;
        boolean inQuotes = false;      
        boolean inTag = false;
        String separators = "= \t\n\r";
        
        for(int i = cursorPrefix.length()-1; i >= 0; i--)
        {
            char currChar = cursorPrefix.charAt(i);
            if(currChar == '>' && !inQuotes)
            {
                //inTag = false;
                chevronCount--;
                break;
            }
            else if(currChar == '<' && !inQuotes)
            {
                inTag = true;
                this.lastOpenChevronPos = i;
                chevronCount++;
            }
            else if(currChar == '\"')
            {
                if(quoteCount == 0)
                {
                    for( i--; i >= 0; i--)
                    {
                        if(separators.indexOf(cursorPrefix.charAt(i)) == -1)
                        {
                            i++;
                            break;
                        }
                        else if(cursorPrefix.charAt(i) == '=')
                        {
                            inQuotes = true;
                            quoteCount++;
                            break;
                        }
                        
                    }
                }
                inQuotes = ( inQuotes ) ? false : true;
                quoteCount++;
            }
        }
        //
        // Check to make sure that the quote count was even (will this fail in an attribute? -
        // check end of string for open quote therefore in attribute?)        
        if(quoteCount % 2 != 0 && chevronCount > 0)
        {
            inTag = false;
        }
            
        return inTag;
    }
    
    /**
     * Dumps some state info to a string.
     * 
     * @param state The state to dump
     * @return The string containing the info dump
     */
    private static String getIAssistStateDescription(IAssistState state)
    {
        String retStr = "";
        retStr+= "# State dump follows:\n";
        retStr+= "Offset:           " + state.getOffset() + "\n";
        retStr+= "Data so far:      \"" + state.getDataSoFar() + "\"\n";
        //retStr+= "Prev delim pos:   " + state.getPreviousDelimiterPosition() + "\n";
        retStr+= "Trigger char:     \'" + state.getTriggerData() + "\'\n";
        retStr+= "Offset partition: \"" + state.getOffsetPartition().getType() + "\"\n";
        retStr+= "# End of state dump\n";
        
        return retStr;
    }
    
    /* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeCompletionProposals(org.eclipse.jface.text.ITextViewer, int)
	 */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
	    
        DefaultAssistState assistState = AssistUtils.initialiseDefaultAssistState(viewer, offset);
        assistState.setPrevDelim(0);	// TODO: Bodge job, need to assign correct previous delim position
        assistState.setDataSoFar(assistState.getDataSoFar().trim());
        return getTagProposals(assistState);
	}
	
	private boolean inValidPartitionType(IAssistState state)
	{
	    //ITextViewer viewer = state.getITextView();
	    String partitions [] = {
	            CFPartitionScanner.CF_TAG,
	            CFPartitionScanner.FORM_TAG, 
	            CFPartitionScanner.CSS_TAG,
	            CFPartitionScanner.TABLE_TAG,
	            CFPartitionScanner.UNK_TAG,
	            CFPartitionScanner.J_SCRIPT,
	            CFPartitionScanner.ALL_TAG,
	            IDocument.DEFAULT_CONTENT_TYPE};
	    
	    return AssistUtils.isInCorrectPartitionTypes(state, partitions);
	    /*
	    int offset = state.getOffset();
	    for(int i = 0; i < partitions.length; i++)
	    {
	        if(AssistUtils.isCorrectPartitionType(viewer, offset, partitions[i]))
	        {
	            UserMsg("inValidPartitionType", "Found a matching partition type");
	            return true;
	        }
	    }
	    return false;
	    */
	}
	
	 /* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeCompletionProposals(org.eclipse.jface.text.ITextViewer, int)
	 */
	public ICompletionProposal[] getTagProposals(IAssistState assistState) {
        String mName = "computeCompletionProposal";
	    IDocument document = assistState.getIDocument();
	    String attrText = "";
		String prefix = "";
		
		if(!inValidPartitionType(assistState))
		{
		    UserMsg(mName, "Not in a valid partition type of \'" + assistState.getOffsetPartition().getType() + "\'");
		    return null;
		}

		
	    char invokerChar;
		prefix = assistState.getDataSoFar();
		invokerChar = assistState.getTriggerData();		
		if(!checkActuallyInTag(prefix))
		{
		    UserMsg(mName, "Not in a tag");
		    return null;
		}
		prefix = prefix.substring(this.lastOpenChevronPos).trim();

		ArrayList partItems = CFEContentAssist.getTokenisedString(/*assistState.getDataSoFar()*/prefix);
		//
		// The assumption here is that what will be entered goes along the lines of
		// <tagNameHere [attribute]*>
		// Therefore if the tokenised string has more than 1 element it will contain
		// the tagname then the attribute. So the last element will be the most
		// recent attribute.
		if(partItems.size() > 1)
		{
			attrText = (String)partItems.get(partItems.size()-1);
		}		
		
		boolean isDefinatelyAnAttribute = invokerChar == ' ';
		boolean invokerIsSpace = invokerChar == ' ';
		boolean invokerIsTab = invokerChar == '\t';
		boolean invokerIsCloseChevron = invokerChar == '>';
		boolean prefixHasOddQuotes = countQuotes(prefix) % 2 == 1;
		int prefixLength = prefix.length();
		//
		// if it looks like they have started typing the contents of an
		// attribtue (or they are done) set limiting to nothing
		if(attrText.indexOf("\"") > 0 || attrText.indexOf("'") > 0)
		{
			attrText = "";
		}
	
		//
		//	Test to see whether we're invoked within an attribute value def
		if(prefixHasOddQuotes) 
		{	
			DefaultAssistTagState attrTagState = prepareForAttributeAssist(assistState, attrText, prefix, partItems);
			return getAttributeValueProposals(attrTagState);
		}	// TODO: Need to figure out why the next line works...
		else if(attrText.length() <= 0 && !invokerIsSpace 
		        && !invokerIsTab && !invokerIsCloseChevron && !isDefinatelyAnAttribute) 
		{
			try 
			{
				return getTagProposalsFromCACors(assistState);
			}
			catch(BadLocationException ex) 
			{
				System.err.println("HTMLContentAssistant::computeCompletionProposals() - Caught exception in tag lookup :(");
				ex.printStackTrace();
				return null;
			}
		}
		else if(prefix.trim().length() > 0 || isDefinatelyAnAttribute)	
		{									
			if(partItems.size() == 0)	// Catch this potential error situation. Don't think it should happen.
			{
				UserMsg(mName, "partItems is 0 length for attribute insight. This is soooo wrong!");
				return null;
			}

			DefaultAssistTagState attrTagState = prepareForAttributeAssist(assistState, attrText, prefix, partItems);
			return getAttributeProposals(attrTagState, CFDocUtils.parseForAttributes(prefix));
		}
		else {
//			UserMsg(mName, "Hmm, decided to not even do anything!");
		}
		
		return null;
	}


    /**
	 * This method prepares a DefaultAssistTagState object ready for gathering attribute
	 * content assist. It creates the tagstate based upon the current standard assist
	 * state and adds on the required extras. 
	 * 
	 * @param assistState The current assist state for content assist
	 * @param attrText The attribute text
	 * @param prefix All of the data, tags, attributes & all
	 * @param partItems Prefix split around spaces
	 * @return The prepared DefaultAssistTagState
	 */
	private DefaultAssistTagState prepareForAttributeAssist(IAssistState assistState, 
														String attrText, String prefix, ArrayList partItems) 
	{
		Assert.isNotNull(assistState);
		Assert.isNotNull(attrText);
		Assert.isNotNull(prefix);
		Assert.isNotNull(partItems);
		
		String tagName = ((String)partItems.get(0)).substring(1).trim();
		
		DefaultAssistTagState attrTagState = new DefaultAssistTagState(assistState, tagName, attrText);
		
		UserMsg("prepareForAttributeAssist", "Providing attribute insight on tag \'" + tagName + "\'");
		return attrTagState;
	}

	/**
	 * Counts the number of quotes in a string.
	 * 
	 * @param string2Scan The string to scan 
	 * @return The number of quotes in the string
	 */
	private int countQuotes(String string2Scan) {
		Assert.isNotNull(string2Scan, "The parameter string2Scan is null");
		
		int quoteCount = 0;
		for(int i = string2Scan.length() -1; i >= 0; i--) {
			if(string2Scan.charAt(i) == '\"') 
				quoteCount++;
		}
		return quoteCount;
	}

	
	
	
	/**
	 * Actually retrieves the proposals from the attribute assist contributors.
	 * They are queried based upon the current state of the content assist,
	 * the name of the invocation tag and the rest of the info. 
	 * 
	 * @param state The current state of the assist
	 * @return The Set of Parameters that represent tha attribute contributions from the CACors
	 */
	private Set getAttribsFromContributors(IAssistTagState state)
	{
		Assert.isNotNull(state);
		
		ArrayList attrValCACors = ((ICFEFileDocument)state.getIDocument()).getContentAssistManager().getAttributeAssistors();
		HashSet retSet = new HashSet();
		Iterator CACorIter = attrValCACors.iterator();
		while(CACorIter.hasNext())
		{
		    IAssistTagContributor CACor = (IAssistTagContributor)CACorIter.next(); 
			Parameter [] possParams = CACor.getAttributeProposals(state);
			
			if(possParams == null)
			    continue;

// System.out.println("CFContentAssist::getAttribsFromContributors() - Got " + possParams.length + " from \'" + CACor.getClass().getName() + "\'");
			
			for(int i = 0; i < possParams.length; i++)
			{
				retSet.add(possParams[i]);
			}
		}
		
		return retSet;
	}
	
	/**
	 * Retrieves the attribute proposals.
	 * 
	 * It queries all of the registered attribute assist contributors for proposals.
	 * It performs the logic to eliminate attributes that already exist for the tag.
	 * It then eliminates all attributes that would not be triggered by the current
	 * attributes.
	 * 
	 * @param state The state of the assist
	 * @param attrText The attribute text 
	 * @param tagname The tag that the attribute assist has been invoked upon
	 * @param currAttribs A HashMap of the attributes that are current entered.
	 * @return Array of ICompletionProposals for the user to see
	 */
	private ICompletionProposal[] getAttributeProposals(IAssistTagState state, 
														Map currAttribs)
	{
		Assert.isNotNull(state);
		Assert.isNotNull(currAttribs);
		
		String prefix = state.getDataSoFar();
		String attrText = state.getAttributeText().trim();
		
		// hacks hacks everywhere :) this looks to see if there are an
		// odd number of " in the string prior to this invoke before 
		// showing attribute insight. (to keep it from showing attributes
		// inside of attributes)
		String quote_parts[] = prefix.substring(1, state.getTagName().length()).split("\"");
		
		if(quote_parts.length % 2 != 0)				
		{
		    
			// 
			// Return out best guess as to what attributes are available for the tag that the user
			// is working upon and the attributes they have already defined. Also filters out any
			// attributes that are already defined for the attribute.
			if(!(prefix.indexOf('>') < 0))
			{
			    return null;
			}
			Set suggestedAttribs = getAttribsFromContributors(state);
			try {
				
				ArrayList attribs2Remove = new ArrayList();
				if(suggestedAttribs == null)
					return new ICompletionProposal[0];
				
				Iterator attrIter = suggestedAttribs.iterator();
				
				while(attrIter.hasNext()) {
					Object attrObj = attrIter.next();
					Assert.isTrue(attrObj instanceof Parameter, "A parameter proposal from a tag attribute contributor is not of type Parameter");
					
					Parameter currParam = (Parameter)attrObj;
					//System.out.println("Testing \'" + currParam.getName() + "\'");
					if(currAttribs.containsKey(currParam.getName())) {
						attribs2Remove.add(currParam);
					}
					else if((currParam.isTriggered((HashMap)currAttribs) == Parameter.PARAM_NOTTRIGGERED)) {
// System.out.println("Adding to the remove list.");
						attribs2Remove.add(currParam);
					}
					//System.out.println("");
				}
				
				Iterator removeIter = attribs2Remove.iterator();
				while(removeIter.hasNext()) {
					suggestedAttribs.remove(removeIter.next());
				}	
			} catch(Throwable ex) {
				ex.printStackTrace();
				
			}
			
			return makeSetToProposal(
				CFDocUtils.eliminateDuplicateParams(suggestedAttribs, new HashSet(currAttribs.values())),
				state.getOffset(),
				ATTRTYPE,
				attrText.length()
			);
		}
	
		return null;		
	}

	/**
	 * Returns the tag proposals based upon current information available.
	 * The tag proposals are based upon the HTML & CFML syntax dictionaries.
	 * 
	 * @param offset The offset in the document that the user invoked assist
	 * @param document The document the user is working in.
	 * @param invokerChar The char that invoked content assist.
	 * @param prefix Everything from the invoke point to the start of the tag being worked upon
	 * @return An array of completion proposals, otherwise <code>null</code> for no match.
	 * @throws BadLocationException
	 */
	private ICompletionProposal[] getTagProposalsFromCACors(IAssistState assistState) throws BadLocationException 
	{
		Assert.isNotNull(assistState);
		
		ArrayList proposals = new ArrayList();
		ArrayList tagAssists = ((ICFEFileDocument)assistState.getIDocument()).getContentAssistManager().getTagAssistors();
		if(assistState.getDataSoFar().length() == 0) return null;		
		
		Iterator assistIter = tagAssists.iterator();
		while(assistIter.hasNext())
		{
			ICompletionProposal [] props = ((IAssistContributor)assistIter.next()).getTagProposals(assistState);
			if(props == null)
			    continue;
			
			for(int i = 0; i < props.length; i++)
			{
				proposals.add(props[i]);
			}
		}
		ICompletionProposal [] retProps = new ICompletionProposal[proposals.size()];
		for(int i = 0; i < retProps.length; i++)
		{
			retProps[i] = (ICompletionProposal)proposals.get(i);
		}
		return retProps;
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
		return new char[] { '<', 'f', ' ', 'F', '~', '\t', '\n', '\r', '>', '\"' };
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
