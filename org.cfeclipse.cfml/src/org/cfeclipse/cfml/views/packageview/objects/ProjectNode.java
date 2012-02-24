package org.cfeclipse.cfml.views.packageview.objects;

import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;


/**
 * This node represents an eclipse project.
 * @author mdrew
 * @author mike nimer
 */
public class ProjectNode extends TreeParent
{
    private IProject project;
    private String packageName;
    
    
    public ProjectNode(IProject project)
    {
        super(project.getProject().getName());        
        this.project = project;

        // Set up the packagename
        System.out.println("Getting the project mapping root");
       
        
        this.addChild( new TreeObject("working") );
    }
    
    public String getImage()
    {
        return CFPluginImages.ICON_FOLDER;
    }
    
    public String getName(){
    	String PackageName = project.getName();
    	
    	 try
         {
         	
             QualifiedName propertyName = new QualifiedName("", "componentRoot");

             String pname = project.getProject().getPersistentProperty(propertyName);
             if (pname != null)
             {
                 this.setName(pname);
                 PackageName = pname;
                 this.packageName = PackageName;
             }
           

         }
         catch (CoreException e)
         {}
    	return PackageName;
    }
    

    public IProject getProject()
    {
        return project;
    }

    public void setProject(IProject project)
    {
        this.project = project;
    }


    /**
     * @return Returns the packageName.
     */
    public String getPackageName()
    {
        return packageName;
    }

    /**
     * @param packageName The packageName to set.
     */
    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }    
    
    public String toString()
    {
        return this.getName();    
    }
    

    public void expand()
    {
        this.clearChildren();
        //this.addChild( new TreeObject("test") );

        
        try
        {
            IResource children[] = this.project.members();
            for (int i = 0; i < children.length; i++)
            {
                // Now we create the packages and then add them
                // There might be some children that arent packages
                if (children[i] instanceof IFolder)
                {
                    PackageNode packageNode = new PackageNode((IFolder) children[i]);
                    packageNode.setParent(this);
                    this.addChild(packageNode);
                }
                else if (children[i] instanceof IFile)
                {
                    IFile file = (IFile) children[i];
					if (file.getFileExtension() != null && file.getFileExtension().equalsIgnoreCase("cfc"))
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


}
