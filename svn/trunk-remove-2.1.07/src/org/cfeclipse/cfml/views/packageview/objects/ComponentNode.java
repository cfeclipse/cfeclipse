package org.cfeclipse.cfml.views.packageview.objects;

import java.io.IOException;
import java.util.Iterator;

import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.CFParser;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.cfeclipse.cfml.parser.docitems.TagItem;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.cfeclipse.cfml.util.ResourceUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;


/**
 * This object represents a CFC. So we should pass in the file itself, from which we can derive the rest
 * @author mark 
 * @author mike nimer 
 */
public class ComponentNode extends TreeParent
{
    private IFile file;


    public ComponentNode(IFile cfcfile)
    {
        super(cfcfile.getName().replaceAll("." + cfcfile.getFileExtension(), ""));
        this.file = cfcfile;
        //this.modStamp = ((IFile) cfcfile).getModificationStamp();
        expand(cfcfile);
    }

    private void expand(IFile cfcfile)
    {
        this.clearChildren();
        // This parses the file and adds a new node as a child

        String inputString = "";
        try
        {
            inputString = ResourceUtils.getStringFromInputStream(cfcfile.getContents());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (CoreException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        CFParser parser = new CFParser();
        CFDocument doc = parser.parseDoc(inputString);

        // Now we just want to add the nodes!
        DocItem docroot = doc.getDocumentRoot();
        CFNodeList nodes;
        nodes = docroot.selectNodes("//cffunction");
        Iterator iter = nodes.iterator();
        while (iter.hasNext())
        {
            TagItem thisTag = (TagItem) iter.next();
            FunctionNode funcnode = new FunctionNode(thisTag);
            funcnode.setParent(this);
            this.addChild(funcnode);

        }
        // Iterate over the nodes

        System.out.println(this);

    }

    public ComponentNode(String name)
    {
        super(name);
    }

    public IFile getFile()
    {
        return file;
    }

    public void setFile(IFile file)
    {
        this.file = file;
    }

    public String getImage()
    {       
        return CFPluginImages.ICON_CFC;
    }
    
    public String toString()
    {
        return getName();
    }

    public String getCreateObjectSnippet()
    {
        String snippet = "CreateObject(\"component\", \"" + "\")";
        return snippet;

    }

    public String getDetails()
    {
        String details = "Details for " + toString() + "\n\n";
        details += "\nName: " + getName();//getPackageName()
        details += "\nPath: " + this.file.getFullPath();
        //details += "\nExtends: ";

        return details;

    }
}
