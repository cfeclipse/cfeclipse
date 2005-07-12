/*
 * Created on Nov 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.external;
/*
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.internal.localstore.CoreFileSystemLibrary;
import org.eclipse.core.internal.preferences.EclipsePreferences;
import org.eclipse.core.internal.resources.Container;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.internal.resources.ProjectPreferences;
import org.eclipse.core.internal.resources.ResourceException;
import org.eclipse.core.internal.resources.ResourceInfo;
import org.eclipse.core.internal.utils.Assert;
import org.eclipse.core.internal.utils.Policy;
*/
import org.eclipse.core.internal.resources.File;
import org.eclipse.core.internal.resources.MarkerInfo;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.internal.utils.Assert;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ExternalFile extends File {

    Workspace workspace;

	private ExternalMarkerAnnotationModel model = null;
	
    public ExternalFile(IPath path, Workspace container) {
		super(path, container);
		workspace = container;
		model = new ExternalMarkerAnnotationModel(this);
	}
    
    public Object getAdapter(Class adapter) {

        return super.getAdapter(adapter);
    }
    
    
    /* (non-Javadoc)
	 * @see IResource#createMarker(String)
	 */
	public IMarker createMarker(String type) throws CoreException {
		Assert.isNotNull(type,"ExternalFile::createMarker()");
		final ISchedulingRule rule = workspace.getRuleFactory().markerRule(this);
		try {
			workspace.prepareOperation(rule, null);
			checkAccessible(getFlags(getResourceInfo(false, false)));
			workspace.beginOperation(true);
			MarkerInfo info = new MarkerInfo();
			info.setType(type);
			info.setCreationTime(System.currentTimeMillis());
			workspace.getMarkerManager().add(this, info);
			return new ExternalMarker();
			//return new ExternalMarker(this, info.getId());
		} 
		finally {
			workspace.endOperation(rule, false, null);
		}
	}
    
    public void checkExists(int flags, boolean checkType) throws CoreException {
        return;
    }
    
    public boolean exists() {
        return true;
    }
    
    public void checkValidPath(IPath toValidate, int type, boolean lastSegmentOnly) throws CoreException {
     return;   
    }
    
    public ExternalMarkerAnnotationModel getAnnotationModel() {
        return model;
    }
    
}
