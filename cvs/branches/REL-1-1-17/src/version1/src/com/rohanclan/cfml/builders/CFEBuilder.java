/*
 * Created on 	: 10-Sep-2004
 * Created by 	: Administrator
 * File		  	: CFEBuilder.java
 * Description	:
 * 
 */
package com.rohanclan.cfml.builders;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
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
        // TODO Auto-generated method stub
        return null;
    }
    /* (non-Javadoc)
     * @see org.eclipse.core.internal.events.InternalBuilder#clean(org.eclipse.core.runtime.IProgressMonitor)
     */
    protected void clean(IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub
        super.clean(monitor);
    }
    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
     */
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {
        // TODO Auto-generated method stub
        super.setInitializationData(config, propertyName, data);
    }
    /* (non-Javadoc)
     * @see org.eclipse.core.internal.events.InternalBuilder#startupOnInitialize()
     */
    protected void startupOnInitialize() {
        // TODO Auto-generated method stub
        super.startupOnInitialize();
    }
}

