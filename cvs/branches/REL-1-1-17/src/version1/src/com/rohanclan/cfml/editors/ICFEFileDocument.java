/*
 * Created on Sep 25, 2004
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
package com.rohanclan.cfml.editors;

import org.eclipse.core.resources.IResource;

import com.rohanclan.cfml.editors.contentassist.CFEContentAssistManager;

/**
 * <p>
 * An IResourceDocument is one that has an Eclipse IResource
 * associated with it that represents the file that the document
 * is based upon.
 * </p>
 * <p>
 * Clients that implement this interface also allow the use of the CFE
 * plugable content assist system. A class that implements this interface
 * will therefore have a series of content assist contributors (CACor). These
 * CACors can be queried to provide proposals for the user.
 * </p>
 * 
 * @author Oliver Tupman
 */
public interface ICFEFileDocument {
    /**
     * Returns the resource that this document is based upon
     * 
     * @return The IResource that should represent the IFile for this document
     */
    public IResource getResource();
    
    /**
     * Sets the IResource that this document is based upon (not guaranteed to
     * change the IDocument contents).
     * 
     * @param res The resource to base this document on
     */
    public void setResource(IResource res);
    
    /**
     * Retrieves the Content Assist Manager (CAM) for this document. This is the 
     * manager for the CACors that are associated with this document.
     * 
     * The default implementation is for the implementing class to query the
     * CFE project nature and ask it what CACors are available. Generally the
     * project will return it's own privately maintained CAM. 
     * 
     * @return The CAM that will provide CACors for this document.
     */
    public CFEContentAssistManager getContentAssistManager();
}
