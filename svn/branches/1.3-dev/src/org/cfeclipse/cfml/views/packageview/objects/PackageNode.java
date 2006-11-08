package org.cfeclipse.cfml.views.packageview.objects;

import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;


/**
 * This node represents the cfc "pacakage". 
 * Note: CF supports multiple "packages" for the same CFC, so this is a best guess.
 * 
 * @author mdrew
 * @author mike nimer
 */
public class PackageNode extends TreeParent
{
    private IResource folder;
    private TreeParent parent;

    public PackageNode(IFolder folder)
    {
        super(folder.getName());
        this.folder = folder;
        // here we init the children then!
        expand(folder);
    }

    private void expand(IFolder folder)
    {
        this.clearChildren();
        // go through the first root

        try
        {
            IResource children[] = folder.members();
            for (int i = 0; i < children.length; i++)
            {
                if (children[i] instanceof IFolder)
                {
                    PackageNode packageNode = new PackageNode((IFolder) children[i]);
                    packageNode.setParent(this);
                    this.addChild(packageNode);
                }
                else if (children[i] instanceof IFile)
                {
                    IFile file = (IFile) children[i];
                    if ("cfc".equalsIgnoreCase(file.getFileExtension()))
                    {
                        ComponentNode component = new ComponentNode(file);
                        component.setParent(this);
                        this.addChild(component);
                    }

                }
            }

        }
        catch (CoreException e)
        {
            e.printStackTrace();
        }

    }
    
    public String getImage()
    {       
        return CFPluginImages.ICON_PACKAGE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.cfeclipse.cfml.views.packageview.objects.IComponentViewObject#getPackageName()
     * 
     * public String getPackageName() { String packageName = ""; if
     * (!"".equalsIgnoreCase(this.parent.getPackageName())) { return
     * this.parent.getPackageName() + "." + getName(); } else { return
     * getName(); }
     *  }
     */
    // todo: look in cvs and revert this back.
    public String getPackageName()
    {
        return getName();
    }

    public String toString()
    {
        return getPackageName();
    }
}
