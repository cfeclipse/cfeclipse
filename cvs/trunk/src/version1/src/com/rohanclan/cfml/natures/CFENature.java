/*
 * Created on 	: 10-Sep-2004
 * Created by 	: Administrator
 * File		  	: CFENature.java
 * Description	:
 * 
 */
package com.rohanclan.cfml.natures;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CFENature implements IProjectNature {

    private IProject project;

    public void configure() throws CoreException {
       // Add nature-specific information
       // for the project, such as adding a builder
       // to a project's build spec.
        System.out.println("Configuring the nature");
    }
    public void deconfigure() throws CoreException {
       // Remove the nature-specific information here.
    }
    public IProject getProject() {
       return project;
    }
    public void setProject(IProject value) {
        
       project = value;
    }
 }
