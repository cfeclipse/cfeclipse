/*
 * Created on Aug 2, 2004
 * 
 * TODO: Make the parsing make a flat tree ( as it is in the Java navigation)
 */
package com.rohanclan.cfml.views.packageview;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IViewSite;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.views.packageview.components.PackageViewTree;
import com.rohanclan.cfml.views.packageview.objects.ProjectNode;
import com.rohanclan.cfml.views.packageview.objects.TreeObject;
import com.rohanclan.cfml.views.packageview.objects.TreeParent;

/**
 * The package level content provider
 * 
 * @author markd
 * @author mike nimer
 */
public class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider
{
    private TreeParent invisibleRoot;

    private PackageViewTree parent;
    private IViewSite site;

    public ViewContentProvider(PackageViewTree parent, IViewSite site)
    {
        this.parent = parent;
        this.site = site;
        initialize();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer v, Object oldInput, Object newInput)
    {
    // System.out.println("changed=" +v.toString());
    }

    public void dispose()
    {}

    public Object[] getElements(Object parent)
    {
        if (parent.equals(site))
        {
            if (invisibleRoot == null)
                initialize();
            return getChildren(invisibleRoot);
        }
        return getChildren(parent);
    }

    public Object getParent(Object child)
    {
        if (child instanceof TreeObject) { return ((TreeObject) child).getParent(); }
        return null;
    }

    public Object[] getChildren(Object parent)
    {
        if (parent instanceof TreeParent) { return ((TreeParent) parent).getChildren(); }
        return new Object[0];
    }

    public boolean hasChildren(Object parent)
    {
        if (parent instanceof TreeParent)
            return ((TreeParent) parent).hasChildren();
        return false;
    }

    private IProject[] getProjects()
    {
    	//We should only return open projects
        return CFMLPlugin.getWorkspace().getRoot().getProjects();
    }

    /**
     * 1. This is where we add the root.
     */
    private void initialize()
    {
        invisibleRoot = new TreeParent("");
        
        IProject[] projects = getProjects();
        
        
        for(int i = 0; i < projects.length; i++) 
        {
        	if(projects[i].isOpen())
        		invisibleRoot.addChild( new ProjectNode(projects[i]) ); 
        
        }
        
    }

}