/*
 * Created on Sep 22, 2004
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

//import org.eclipse.core.internal.utils.Assert;

/**
 * @author Oliver Tupman
 */
public class DefaultAssistAttributeState extends DefaultAssistTagState
        implements IAssistTagAttributeState {

    /**
     * The attribute the user is requesting assist on.
     */
    private String attribute = null;
    /**
     * The attribute value that the user has so far typed in.
     */
    private String valueSoFar = null;
    
    /**
     * @param baseState
     */
    public DefaultAssistAttributeState() {
        super();
    }
    
    /**
     * Constructs this state from a tag assist state.
     * 
     * @param baseState The base assist
     */
    public DefaultAssistAttributeState(IAssistTagState baseState)
    {
        super(baseState);
        setTagName(baseState.getTagName());
        setAttrText(baseState.getAttributeText());
    }
    
    /**
     * Constructs this state from a tag assist state plus the name of
     * the attribute.
     * 
     * @param baseState The base assist
     * @param attributeText The attribute that insight is requested for
     */
    public DefaultAssistAttributeState(IAssistTagState baseState, String attributeText)
    {
        this(baseState);
        setAttribute(attributeText);
    }
    
    /**
     * Constructs this state from a tag assist state plus he name of the attribute
     * and the value typed in so far by the user.
     * 
     * @param baseState The base assist
     * @param attributeText The attribute that insight is required for
     * @param valueSoFar The value typed in so far by the user
     */
    public DefaultAssistAttributeState(IAssistTagState baseState, String attributeText, String valueSoFar)
    {
        this(baseState, attributeText);
        setValueSoFar(valueSoFar);
        setTextViewer(baseState.getITextView());
    }

    /**
     * Sets the attribute for the state that this represents.
     * TODO: Make sure this works with cfset 
     * @param attributeText The name of the attribute
     */
    public void setAttribute(String attributeText)
    {
        //Assert.isNotNull(attributeText,"DefaultAssistAttributeState::setAttribute()");
        if(attributeText == null)
        		throw new IllegalArgumentException("DefaultAssistAttributeState::setAttribute()");
        this.attribute = attributeText;
    }
    
    /**
     * Sets the attribute value so far.
     * 
     * @param valueSoFar
     */
    public void setValueSoFar(String valueSoFar)
    {
        //Assert.isNotNull(valueSoFar,"DefaultAssistAttributeState::setValueSoFar()");
        if(valueSoFar == null)
    			throw new IllegalArgumentException("DefaultAssistAttributeState::setValueSoFar()");
        this.valueSoFar = valueSoFar;
    }
    
    /* (non-Javadoc)
     * @see com.rohanclan.cfml.editors.contentassistors.IAssistTagAttributeState#getAttribute()
     */
    public String getAttribute() {
        //Assert.isNotNull(this.attribute,"DefaultAssistAttributeState::getAttribute()");
        if(this.attribute == null)
			throw new IllegalArgumentException("DefaultAssistAttributeState::getAttribute()");
        return this.attribute;
    }

    /* (non-Javadoc)
     * @see com.rohanclan.cfml.editors.contentassistors.IAssistTagAttributeState#getValueSoFar()
     */
    public String getValueSoFar() {
        //Assert.isNotNull(this.valueSoFar,"DefaultAssistAttributeState::getValueSoFar()");
        if(this.valueSoFar == null)
			throw new IllegalArgumentException("DefaultAssistAttributeState::getValueSoFar()");
        return this.valueSoFar;
    }

}
