/*
 * Created on Sep 20, 2004
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
package org.cfeclipse.cfml.editors.contentassist;

//import org.eclipse.core.internal.utils.Assert;
import org.cfeclipse.cfml.editors.partitioner.CFEPartition;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;


/**
 * This is the default implementation of the assist state information.
 * The assist state represents how the user has triggered the content assist
 * request. This may be post-trigger char. So the user may not have entered
 * a trigger char but be post-trigger and therefore entering text that we
 * should filter upon.
 * 
 * @author Oliver Tupman
 */
public class DefaultAssistState implements IAssistState {
    
    private ITextViewer textViewer;
    
    private CFEPartition[] fPartitions = null; 
    
    public ITextViewer getITextView() {
        //Assert.isNotNull(this.textViewer,"DefaultAssistState::getITextView()");
        if(this.textViewer == null)
        		throw new IllegalArgumentException("DefaultAssistState::getITextView()");
        
        return this.textViewer;
    }
    
    public void setTextViewer(ITextViewer newViewer)
    {
        //Assert.isNotNull(newViewer,"DefaultAssistState::setTextViewer()");
        if(newViewer == null)
    			throw new IllegalArgumentException("DefaultAssistState::setTextViewer()");
        this.textViewer = newViewer;
    }
	/**
	 * The document
	 */
	private IDocument doc = null;
	/**
	 * Offset within the document that we're currently at
	 */
	private int offset = -1;
	/**
	 * The char that triggered the content assist (aka the 
	 * previous character typed in)
	 */
	private char triggerChar;
	/**
	 * The data since the last delimiter.
	 */
	private String dataSoFar = null;
	/**
	 * The location of the previous delimter, absolute position.
	 */
	private int prevDelim = -1;

	/**
	 * The region type in which the user invoked content assist.
	 */
	private ITypedRegion prevRegion = null;
	
	/**
	 * 
	 */
	public DefaultAssistState() {
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.cfml.editors.contentassistors.IAssistState#getDocument()
	 */
	public IDocument getIDocument() {
		//Assert.isNotNull(this.doc,"DefaultAssistState::getIDocument()");
		if(this.doc == null)
			throw new IllegalArgumentException("DefaultAssistState::getIDocument()");
		return this.doc;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.cfml.editors.contentassistors.IAssistState#getOffset()
	 */
	public int getOffset() {
		//Assert.isTrue(this.offset >= 0,"DefaultAssistState::getOffset()");
		if(!(this.offset >= 0))
			throw new IllegalArgumentException("DefaultAssistState::getOffset()");
		
		return this.offset;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.cfml.editors.contentassistors.IAssistState#getTriggerData()
	 */
	public char getTriggerData() {
		return this.triggerChar;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.cfml.editors.contentassistors.IAssistState#getDataSoFar()
	 */
	public String getDataSoFar() {
		//Assert.isNotNull(this.dataSoFar,"DefaultAssistState::getDataSoFar()");
		if(this.dataSoFar == null)
			throw new IllegalArgumentException("DefaultAssistState::getDataSoFar()");
		return this.dataSoFar;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.cfml.editors.contentassistors.IAssistState#getPreviousDelimiterPosition()
	 */
	public int getPreviousDelimiterPosition() {
		//Assert.isTrue(this.prevDelim >= 0,"DefaultAssistState::getPreviousDelimiterPosition()");
		if(!(this.prevDelim >= 0))
			throw new IllegalArgumentException("DefaultAssistState::getPreviousDelimiterPosition()");
		return this.prevDelim;
	}
	
	
	public void setDoc(IDocument doc) {
		//Assert.isNotNull(doc,"DefaultAssistState::setDoc()");
		if(doc == null)
			throw new IllegalArgumentException("DefaultAssistState::setDoc()");
		this.doc = doc;
	}

	public void setPrevDelim(int prevDelim) {
		//Assert.isTrue(prevDelim >= 0,"DefaultAssistState::setPrevDelim()");
		if(!(prevDelim >= 0))
			throw new IllegalArgumentException("DefaultAssistState::setPrevDelim()");
		this.prevDelim = prevDelim;
	}

	public void setTriggerChar(char triggerChar) {
		this.triggerChar = triggerChar;
	}
	public void setDataSoFar(String dataSoFar) {
		//Assert.isNotNull(dataSoFar,"DefaultAssistState::setDataSoFar()");
		if(dataSoFar == null)
			throw new IllegalArgumentException("DefaultAssistState::setDataSoFar()");
		this.dataSoFar = dataSoFar;
	}
	
	public void setOffset(int offset) {
		//Assert.isTrue(offset >= 0,"DefaultAssistState::setOffset()");
		if(!(offset >= 0))
			throw new IllegalArgumentException("DefaultAssistState::setPrevDelim()");
		this.offset = offset;
	}
	
	public ITypedRegion getOffsetPartition() {
		return this.prevRegion;
	}

	public void setOffsetPartition(ITypedRegion prevRegion)
	{
		this.prevRegion = prevRegion;
	}
	
	public void setRelevantPartitions(CFEPartition[] partitions) {
	    fPartitions = partitions;
	}
	
	public CFEPartition[] getRelevantPartitions() {
	    return fPartitions;
	}
}
