package com.rohanclan.cfml.views.packageview.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.rohanclan.cfml.parser.CFDocument;
import com.rohanclan.cfml.parser.CFNodeList;
import com.rohanclan.cfml.parser.CFParser;
import com.rohanclan.cfml.parser.docitems.DocItem;
import com.rohanclan.cfml.parser.docitems.TagItem;
import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.util.ResourceUtils;

/**
 * @author mark
 * This object represents a CFC. So we should pass in the file itself, from 
 * which we can derive the rest
 */
public class ComponentNode implements IComponentViewObject {
	private String name;
	private IFile file;
	private ArrayList children; //functions in this Component
	private String image = CFPluginImages.ICON_CLASS;
	private IComponentViewObject parent;
	private String extendDetails;
	private long modStamp;
	
	public ComponentNode(IFile cfcfile){
		this.file = cfcfile;
		this.name = cfcfile.getName().replaceAll("." + cfcfile.getFileExtension(), "");
		this.modStamp = ((File)cfcfile).getModificationStamp();
		this.children = new ArrayList();
		initFunctions(cfcfile);
	}
	
	private void initFunctions(IFile cfcfile){
		//This parses the file and adds a  new node as a child
		
		String inputString = "";
		try {
			inputString  =ResourceUtils.getStringFromInputStream(cfcfile.getContents());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CFParser parser = new CFParser();
		CFDocument doc = parser.parseDoc(inputString);
		
		//Now we just want to add the nodes!
		DocItem docroot = doc.getDocumentRoot();
		CFNodeList nodes;
		nodes = docroot.selectNodes("//cffunction");
		Iterator iter = nodes.iterator();
		while(iter.hasNext()){
			TagItem thisTag = (TagItem)iter.next();
			FunctionNode funcnode = new FunctionNode(thisTag); 
			funcnode.setParent(this);
			children.add(funcnode);
			
		}
		//Iterate over the nodes
		
		
		System.out.println(this);
		
	}
	public ComponentNode(String name) {
		this.name = name;
	}

	public String getName() {
		//TODO: Remove the .cfc
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IFile getFile() {
		return file;
	}

	public void setFile(IFile file) {
		this.file = file;
	}

	/**
	 * @return Returns the children.
	 */
	public ArrayList getChildren() {
		return children;
	}

	/**
	 * @param children The children to set.
	 */
	public void setChildren(ArrayList children) {
		this.children = children;
	}
	
	public boolean hasChildren(){
		
		return this.children.size()>0;
	}
	
	public String toString(){
		return getName();
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#getParent()
	 */
	public IComponentViewObject getParent() {
		return this.parent;
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#setParent(com.rohanclan.cfml.views.packageview.objects.IComponentViewObject)
	 */
	public void setParent(IComponentViewObject parent) {
	 this.parent = parent;
		
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#getImage()
	 */
	public String getImage() {
		return this.image;
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#setImage(java.lang.String)
	 */
	public void setImage(String image) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#getPackageName()
	 */
	public String getPackageName() {
		String packageName = this.parent.getPackageName() + "." + getName();
		return packageName;
	}
	
	public String getCreateObjectSnippet(){
		String snippet =  "CreateObject(\"component\", \""+ getPackageName() +"\")";
		return snippet;
		
	}
	
	public String getDetails(){
		String details = "Details for " + toString() +"\n\n";
		details += "\nName: " + getPackageName();
		details += "\nPath: " + this.file.getFullPath();
		//details += "\nExtends: ";
		

		
		return details;
		
	}
}
