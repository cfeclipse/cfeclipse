/*
 * Created on 	: 10-Sep-2004
 * Created by 	: Administrator
 * File		  	: CFEBuilder.java
 * Description	:
 * 
 * The MIT License
 * Copyright (c) 2004 Administrator
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
package org.cfeclipse.cfml.builders;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.cfeclipse.cfml.parser.CFLintPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;

import com.cflint.CFLint;
import com.cflint.config.CFLintConfig;
import com.cflint.plugins.CFLintScanner;
import com.cflint.tools.CFLintFilter;

/**
 * @author Administrator
 */
public class CFEBuilder extends IncrementalProjectBuilder {
    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
     */
  
     /* (non-Javadoc)
     * @see org.eclipse.core.internal.events.InternalBuilder#build(int, java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
     */
    protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
            throws CoreException {
		CFLintConfig config = null;
		CFLint cflint;
		try {
			cflint = new CFLint(config);
			cflint.setVerbose(true);
			cflint.setLogError(true);
			cflint.setQuiet(false);
			cflint.setShowProgress(false);
			cflint.setProgressUsesThread(true);
			CFLintFilter filter = CFLintFilter.createFilter(true);
			cflint.getBugs().setFilter(filter);
			List<CFLintScanner> scanners = cflint.getScanners();
			cflint.addScanner(new CFLintPlugin());
			cflint.process("", "source.cfc");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    /* (non-Javadoc)
     * @see org.eclipse.core.internal.events.InternalBuilder#clean(org.eclipse.core.runtime.IProgressMonitor)
     */
    protected void clean(IProgressMonitor monitor) throws CoreException {
        super.clean(monitor);
    }
    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
     */
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {
        super.setInitializationData(config, propertyName, data);
    }
    /* (non-Javadoc)
     * @see org.eclipse.core.internal.events.InternalBuilder#startupOnInitialize()
     */
    protected void startupOnInitialize() {
        super.startupOnInitialize();
    }
}

