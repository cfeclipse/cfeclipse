/*
 * Created on Sep 21, 2004
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


import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.internal.utils.Assert;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

import com.rohanclan.cfml.dictionary.Parameter;
import com.rohanclan.cfml.dictionary.SyntaxDictionary;
import com.rohanclan.cfml.dictionary.SyntaxDictionaryInterface;
import com.rohanclan.cfml.dictionary.Value;
import com.rohanclan.cfml.editors.partitioner.scanners.CFPartitionScanner;

/**
 * The default tag assstor
 * 
 * The tag Assistor can be used to provide generic tag-based assist. Do not
 * use the CFMLTagAssist for that purpose because it only provides insight
 * if the user types "<cf".
 * 
 * This assistor provides the following:
 * 	- Generic tag-search assist
 *  - Generic tag attribute assist
 *  - Generic tag attribute value assist
 * 
 * It makes use of the syntax dictionary supplied to provide the assist.
 * 
 * @author Oliver Tupman
 */
public class DefaultTagAssistContributor extends CFEContentAssist 
			implements IAssistContributor, IAssistTagContributor, IAssistAttrValueContributor
{
	private SyntaxDictionary sourceDict = null;
	/**
	 * Setups up the assistor. You must supply the Syntax dictionary when creating
	 * this.
	 * 
	 * @param sourceDictionary The dictionary that the tag assist will base itself on.
	 */
	public DefaultTagAssistContributor(SyntaxDictionary sourceDictionary) {
		Assert.isNotNull(sourceDictionary,"DefaultTagAssistContributor::DefaultTagAssistContributor()");
		
		this.sourceDict = sourceDictionary;
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.editors.contentassistors.IAssistContributor#getProposals(com.rohanclan.cfml.editors.contentassistors.IAssistState)
	 */
	public ICompletionProposal[] getTagProposals(IAssistState state) {
	    //
	    // This method mainly just filters the source dictionaries tag
	    // library by the current data entered by the user.
		Assert.isNotNull(state,"DefaultTagAssistContributor::getTagProposals()");
		Assert.isNotNull(this.sourceDict,"DefaultTagAssistContributor::getTagProposals()");
		
		int offset = state.getOffset();
		IDocument doc = state.getIDocument();
		char invokerChar = state.getTriggerData();
		
		boolean doubleQuoted = false;
		boolean singleQuoted = false;
		int tagStart = 1;
		for (int i=state.getDataSoFar().length()-1;i>=0;i--) {
		    char c = state.getDataSoFar().charAt(i);
		    
		    if (c == '"') {
		        if (!singleQuoted) {
		            doubleQuoted = !doubleQuoted;
		        }
		    }
		    if (c == '\'') {
		        if (!doubleQuoted) {
		            singleQuoted = !singleQuoted;
		        }
		    }
		    if (!doubleQuoted && !singleQuoted) {
			    if (c == '<') {
			        tagStart = i+1;
			        break;
			    }
		    }
		}
		
		String tagLimiting = state.getDataSoFar().substring(tagStart).toLowerCase().trim();
		
		if(invokerChar == '\"')
		{
			try {
				if(doc.getLength() > offset 
				   && doc.getChar(offset) == '\"' &&
				   doc.getChar(offset-2) != '=')
				{	// " entered and there already is one in the document.
					doc.replace(offset, 1, "");
					return new ICompletionProposal[0];
				}
			} catch(BadLocationException ex) {
				ex.printStackTrace();
				return new ICompletionProposal[0];
			}
		}				
		
		return makeSetToProposal(
			((SyntaxDictionaryInterface)this.sourceDict).getFilteredElements(tagLimiting),
			offset,
			CFEContentAssist.TAGTYPE,
			tagLimiting.length()
		);
	}
	
	public static Object [] setToArray(Set inSet)
	{
	    Object objArray [] = new Object[inSet.size()];
	    
	    Iterator objIter = inSet.iterator();
	    for(int i = 0; objIter.hasNext(); i++)
	    {
	        objArray[i] = objIter.next();
	    }
	    
	    return objArray;
	}
	
	public Parameter [] getAttributeProposals(IAssistTagState state)
	{
	    //
	    // Filters the attribute library by tagname and then attribute
	    // data so far. It must then convert the Set returned by the
	    // syntax dictionary to be an array of Parameter's.
		Assert.isNotNull(this.sourceDict,"DefaultTagAssistContributor::getAttributeProposals()");
		Assert.isNotNull(state,"DefaultTagAssistContributor::getAttributeProposals()");
		
		Set filteredAttrs = this.sourceDict.getFilteredAttributes(
				state.getTagName().toLowerCase(), 
				state.getAttributeText()
			);
		return (Parameter [])filteredAttrs.toArray(new Parameter [filteredAttrs.size()]);
	}
	
	public Value [] getAttributeValueProposals(IAssistTagAttributeState state)
	{
	    //
	    // Grabs the filtered values from the syntax dictionary and
	    // then converts the set into an array of Value's
	    Set attrProps = this.sourceDict.getFilteredAttributeValues(state.getTagName(), state.getAttribute(), state.getValueSoFar());
	    if(attrProps == null)
	        return null;
	    
	    Iterator attrIter = attrProps.iterator();
	    Value retArray[] = new Value[attrProps.size()];
	    int i = 0;
	    while(attrIter.hasNext())
	    {
	        Object tempAttr = attrIter.next();
	        Assert.isTrue(tempAttr instanceof Value,"DefaultTagAssistContributor::getAttributeValueProposals()");
	            
	        retArray[i] = (Value)tempAttr;
	        i++;
	    }	    
	    
	    return retArray;
	}
	
	/**
	 * <p>
	 * Checks to see whether the partition supplied is of the correct type or not.
	 * Just a quick helper function.
	 * </p>
	 * <p>
	 * <b>NB:</b> It also checks against the default partition type 
	 * '__dftl_partition_content_type' for issue #199 "Content assist fails when 
	 * there is a non-enclosing tag.". - Ollie
	 * </p>
	 * 
	 * @param state The current state of the content assist
	 * @param partitionType The type of partition to test against.
	 * @return
	 */
	public static boolean isCorrectPartition(IAssistState state, String partitionType)
	{
	    /*
	    return state.getOffsetPartition().getType().equals(partitionType)
	        		|| state.getOffsetPartition().getType().equals(IDocument.DEFAULT_CONTENT_TYPE);
	    */
	    String partitions [] = {
	            CFPartitionScanner.FORM_END_TAG,
	            CFPartitionScanner.FORM_START_TAG_BEGIN,
	            CFPartitionScanner.FORM_START_TAG_END, 
	            CFPartitionScanner.CSS,
	            CFPartitionScanner.TABLE_END_TAG,
	            CFPartitionScanner.TABLE_START_TAG_BEGIN,
	            CFPartitionScanner.TABLE_START_TAG_END,
	            CFPartitionScanner.UNK_TAG,
	            CFPartitionScanner.HTM_END_TAG,
	            CFPartitionScanner.HTM_START_TAG_BEGIN,
	            CFPartitionScanner.HTM_START_TAG_END,
	            CFPartitionScanner.HTM_TAG_ATTRIBS,
	            IDocument.DEFAULT_CONTENT_TYPE};
	    
	    return AssistUtils.isInCorrectPartitionTypes(state, partitions);
	}
}
