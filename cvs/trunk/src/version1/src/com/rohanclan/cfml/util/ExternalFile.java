/*
 * Created on Nov 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.util;
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
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ExternalFile extends File {

    public ExternalFile(IPath path, Workspace container) {
		super(path, container);
	}
    
    public Object getAdapter(Class adapter) {
        System.out.println("Adapter requested for " + adapter.getName());
        System.out.println("Retrieved " + super.getAdapter(adapter));
        return super.getAdapter(adapter);
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
}
