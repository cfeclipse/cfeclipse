/*
 * Created on Oct 8, 2004
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
package com.rohanclan.cfml.natures;

import java.util.Date;

import org.eclipse.core.resources.IProjectNature;

import com.rohanclan.cfml.editors.contentassist.CFEContentAssistManager;

/**
 * Extends IProjectNature. A class that implements this interface will provide
 * not just the Eclipse project nature interface but also the CFEclipse content
 * assist interface. 
 * 
 * This interface allows implementing classes to provide their own custom
 * content assist. For example, a CSS project nature may well have a source
 * model of CSS 1 or 2 (or both). The implementing nature will store the
 * CSS version and then provide an CAM instance that has CACors that use
 * the appropriate source model. 
 * 
 * @author Oliver Tupman
 */
public interface IContentAssistContributorNature extends IProjectNature {
    /**
     * Get's the nature's Content Assist Manager. The nature's CAM provides
     * the CACors for the project.
     * 
     * @return
     */
    public CFEContentAssistManager getNatureCAM();
    
    /**
     * Retrieves when the CAM was last updated.
     * 
     * @return The date that this CAM was last updated.
     */
    public Date getLastUpdateDate();
}
