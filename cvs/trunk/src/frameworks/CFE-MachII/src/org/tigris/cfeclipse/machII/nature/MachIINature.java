/*
 * Created on Oct 13, 2004
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
package org.tigris.cfeclipse.machII.nature;

import java.util.Date;

import org.eclipse.core.internal.utils.Assert;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.tigris.cfeclipse.machII.EventAssistor;

import com.rohanclan.cfml.editors.contentassist.CFEContentAssistManager;
import com.rohanclan.cfml.natures.IContentAssistContributorNature;

/**
 * TODO Provide class description 
 *
 * @author Oliver Tupman
 */
public class MachIINature implements IContentAssistContributorNature {
    private CFEContentAssistManager manager;
    private Date lastCAMUpdate;
    private IProject srcProject;
    /**
     * 
     */
    public MachIINature() {
        super();
        manager = new CFEContentAssistManager();
        manager.registerRootAssist(new EventAssistor());
        this.lastCAMUpdate = new Date();
    }

    /* (non-Javadoc)
     * @see com.rohanclan.cfml.natures.IContentAssistContributorNature#getNatureCAM()
     */
    public CFEContentAssistManager getNatureCAM() {
        return this.manager;
    }

    /* (non-Javadoc)
     * @see com.rohanclan.cfml.natures.IContentAssistContributorNature#getLastUpdateDate()
     */
    public Date getLastUpdateDate() {
        return this.lastCAMUpdate;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    public void configure() throws CoreException {

    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    public void deconfigure() throws CoreException {
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IProjectNature#getProject()
     */
    public IProject getProject() {
        return this.srcProject;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
     */
    public void setProject(IProject project) {
        Assert.isNotNull(project);
        this.srcProject = project;
    }

}
