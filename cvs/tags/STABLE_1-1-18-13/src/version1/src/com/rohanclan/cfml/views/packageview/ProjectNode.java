/*
 * Created on Aug 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.packageview;

import org.eclipse.core.resources.IProject;


/**
 * @author OLIVER
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ProjectNode extends TreeParent
{

	IProject srcProject;
	
	public ProjectNode(IProject prj) {
		super(prj.getName());
		this.srcProject = prj;
	}
	
	public IProject getProject() {
		return this.srcProject;
	}

}
