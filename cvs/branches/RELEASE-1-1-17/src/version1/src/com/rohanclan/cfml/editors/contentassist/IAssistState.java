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
package com.rohanclan.cfml.editors.contentassist;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;

/**
 * AssistInfo is filled by the CFE-core. The core content assistors will determine
 * cursor position, trigger chars, etc. They use it internally and then provide
 * it to any assist contributors.
 */
public interface IAssistState
{
    /**
     * Gets the document that the user is working upon.
     * Clients can test & cast for CFML docs if they wish.
     * @returns the document
     */
    public IDocument getIDocument();
    
    /**
     * Gets the offset within the document that the assist is
     * being triggered at
     * @returns the offset within the doc
     */
    public int getOffset();
    /**
     * Gets the character that caused the trigger.
     * In some cases this may be Ctrl+Space.
     * @returns the trigger char.
     */
    public char getTriggerData();

    /**
     * Gets the data from the last delimiter to the current
     * document position.
     * @returns the data so far (duh)
     */
    public String getDataSoFar();
    
    /**
     * Gets the position in the document where the last delimiter is.
     * @returns the offset within the doc of the previous delimiter
     */
    public int getPreviousDelimiterPosition();
    
    /**
     * Gets the info about the partition that the offset exists within.
     * 
     * @return The ITypedRegion representing the partition. This may be null if not set.
     */
    public ITypedRegion getOffsetPartition();
    
    /**
     * All content assist is associated with a text viewer. This method returns
     * the text viewer that this content assist has been called upon.
     * 
     * @return The text viewer associated with the content assist.
     */
    public ITextViewer getITextView();
} 
