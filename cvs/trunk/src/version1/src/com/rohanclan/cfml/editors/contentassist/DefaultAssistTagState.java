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


//import org.eclipse.ui.internal.misc.Assert;

/**
 * Default implementation of the content assist tag state.
 * 
 * @author Oliver Tupman
 */
public class DefaultAssistTagState extends DefaultAssistState implements
		IAssistTagState 
{
	/**
	 * The member for the tag name. It should contain the raw data from
	 * the assist with no change of case.
	 */
	private String tagName = null;
	/**
	 * The member for the attribute text. It should contain the raw data
	 * from the assist with no change of case.
	 */
	private String attrText = null;
	
	/**
	 * Sets the tag name for the assist
	 * @param tag The tagname 
	 */
	public void setTagName(String tag)
	{
		//Assert.isNotNull(tag,"DefaultAssistTagState::setTagName()");
		if(tag == null)
			throw new IllegalArgumentException("DefaultAssistTagState::setTagName()");
		this.tagName = tag;
	}
	
	/**
	 * Sets the attribute text for the assist.
	 * @param attrText The attribute text
	 */
	public void setAttrText(String attrText)
	{
		//Assert.isNotNull(attrText,"DefaultAssistTagState::setAttrText()");
		if(attrText == null)
			throw new IllegalArgumentException("DefaultAssistTagState::setAttrText()");
		this.attrText = attrText;
	}
	
	/**
	 * 
	 */
	public DefaultAssistTagState() {
		super();
	}
	
	/**
	 * Creates this assist state based upon the supplied assist state interface object
	 * plus the name of the tag that assist is required for.
	 * 
	 * @param baseState The base IAssistState object to base this instance upon
	 * @param tagName The name of the tag that assist is required for.
	 */
	public DefaultAssistTagState(IAssistState baseState, String tagName)
	{
		this(baseState);
		this.setTagName(tagName);
		this.setAttrText("");
	}
	
	/**
	 * Creates this assist state based upon the required parameters.
	 * 
	 * @param baseState The base IAssistState object to base this instance upon
	 * @param tagName The name of the tag name assist is required for
	 * @param attrText The text that follows the tag that represents it's current attributes
	 */
	public DefaultAssistTagState(IAssistState baseState, String tagName, String attrText)
	{
		this(baseState, tagName);
		this.setAttrText(attrText);
		this.setTextViewer(baseState.getITextView());
	}
	
	/**
	 * Creates this assist state based upon another.
	 * 
	 * @param baseState The base IAssistState object to base this instance upon.
	 */
	public DefaultAssistTagState(IAssistState baseState)
	{
		super();
		
		//Assert.isNotNull(baseState,"DefaultAssistTagState::DefaultAssistTagState()");
		if(baseState == null)
			throw new IllegalArgumentException("DefaultAssistTagState::DefaultAssistTagState()");
		
		this.setDataSoFar(baseState.getDataSoFar());
		this.setDoc(baseState.getIDocument());
		this.setOffset(baseState.getOffset());
		this.setOffsetPartition(baseState.getOffsetPartition());
		this.setPrevDelim(baseState.getPreviousDelimiterPosition());
		this.setTriggerChar(baseState.getTriggerData());
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.editors.contentassistors.IAssistTagState#getTagName()
	 */
	public String getTagName() {
		//Assert.isNotNull(this.tagName,"DefaultAssistTagState::getTagName()");
		if(this.tagName == null)
			throw new IllegalArgumentException("DefaultAssistTagState::getTagName()");
		return this.tagName;
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.editors.contentassistors.IAssistTagState#getAttributeText()
	 */
	public String getAttributeText() {
		//Assert.isNotNull(this.attrText,"DefaultAssistTagState::getAttributeText()");
		if(this.attrText == null)
			throw new IllegalArgumentException("DefaultAssistTagState::getAttributeText()");
		
		return this.attrText;
	}

}
