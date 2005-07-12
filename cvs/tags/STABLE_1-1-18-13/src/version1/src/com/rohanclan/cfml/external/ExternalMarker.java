/*
 * Created on Nov 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.external;

import java.util.Map;

import org.eclipse.core.internal.utils.Assert;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ExternalMarker implements IMarker {

	/** Marker identifier. */
	protected long id;

	/** Resource with which this marker is associated. */
	protected IResource resource;
	
    /**
     * 
     */
    public ExternalMarker() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    ExternalMarker(IResource resource, long id) {
		Assert.isLegal(resource != null,"ExternalMarker::ExternalMarker()");
		this.resource = resource;
		this.id = id;
	}

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#delete()
     */
    public void delete() throws CoreException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#exists()
     */
    public boolean exists() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#getAttribute(java.lang.String)
     */
    public Object getAttribute(String attributeName) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#getAttribute(java.lang.String, int)
     */
    public int getAttribute(String attributeName, int defaultValue) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#getAttribute(java.lang.String, java.lang.String)
     */
    public String getAttribute(String attributeName, String defaultValue) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#getAttribute(java.lang.String, boolean)
     */
    public boolean getAttribute(String attributeName, boolean defaultValue) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#getAttributes()
     */
    public Map getAttributes() throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#getAttributes(java.lang.String[])
     */
    public Object[] getAttributes(String[] attributeNames) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#getCreationTime()
     */
    public long getCreationTime() throws CoreException {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#getId()
     */
    public long getId() {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#getResource()
     */
    public IResource getResource() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#getType()
     */
    public String getType() throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#isSubtypeOf(java.lang.String)
     */
    public boolean isSubtypeOf(String superType) throws CoreException {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#setAttribute(java.lang.String, int)
     */
    public void setAttribute(String attributeName, int value)
            throws CoreException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#setAttribute(java.lang.String, java.lang.Object)
     */
    public void setAttribute(String attributeName, Object value)
            throws CoreException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#setAttribute(java.lang.String, boolean)
     */
    public void setAttribute(String attributeName, boolean value)
            throws CoreException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#setAttributes(java.lang.String[], java.lang.Object[])
     */
    public void setAttributes(String[] attributeNames, Object[] values)
            throws CoreException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IMarker#setAttributes(java.util.Map)
     */
    public void setAttributes(Map attributes) throws CoreException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class adapter) {
        // TODO Auto-generated method stub
        return null;
    }

}
