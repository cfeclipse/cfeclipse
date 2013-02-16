/**
 * 
 */
package org.cfeclipse.cfml.frameworks.parsers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.frameworks.ConfigLoader;
import org.cfeclipse.cfml.frameworks.views.FrameworkFile;
import org.cfeclipse.cfml.frameworks.views.TreeParentNode;
import org.cfeclipse.cfml.util.CFMappings;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.ide.IDE;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.contrib.input.LineNumberElement;
import org.jdom.contrib.input.LineNumberSAXBuilder;
import org.jdom.xpath.XPath;

/**
 * This Class is a Generic parser of all framework files, it uses the definition to do clever stuff
 * (such as doing includes for fusebox etc)
 * @author markdrew
 *
 */
public class GenericFrameworkParser  {

	private Document frameworkDefinitionDocument;
	private Element frameworkDefinitionElement;
	
	private FrameworkFile fwfile;
	private TreeParentNode parentnode;
	
	//Mappings assigned to this project (to figure out paths)
	private CFMappings projectMappings;
	private HashMap<String, Long> problemMarkerMap = new HashMap<String, Long>();
	
	private Log logger = LogFactory.getLog(GenericFrameworkParser.class);
	
	/**
	 * The constructor needs to know which framework file you are working with, and the node that we are going to attach all the children to
	 * @param fwfile the actual framework file (contains a reference to the resource as well as the framework type it uses)
	 * @param parentnode The parent node in the tree, to which will are going to add children to
	 */
	public GenericFrameworkParser(FrameworkFile fwfile, TreeParentNode parentnode){
		this.fwfile = fwfile;
		this.parentnode = parentnode;
		
		this.frameworkDefinitionDocument = ConfigLoader.loadConfig("frameworks.xml");
		
		try {
			XPath x = XPath.newInstance("//framework[@id='"+ fwfile.getFrameworkType() +"']");
			
			Element element = (Element)x.selectSingleNode(frameworkDefinitionDocument);
			
			this.frameworkDefinitionElement = element;
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	//	this.projectMappings = new CFMappings(fwfile.getFile().getProject());
			
		
		
		
	}
	
	
	
     class FamilyMember extends Job {
        private String lastName;
           public FamilyMember(IResource fwresource) throws CoreException {
              super("updating file");
           }
           protected IStatus run(IProgressMonitor monitor) {
              // Take care of family business
              return Status.OK_STATUS;
           }
        }	

    /**
	 * Public method to start the parsing 
     * @throws JDOMException 
	 */

 	private void addProblemMarker(final FrameworkFile ffile, final String message) {
		Job job = new Job("Make Files") {
			public IStatus run(IProgressMonitor monitor) {
				try {
					IMarker marker;
					String fileLoc = fwfile.getFile().getLocation().toOSString();
					Long markerId = (Long) problemMarkerMap.get(fileLoc);
					if(markerId == null) {
						marker = fwfile.getFile().createMarker(IMarker.PROBLEM);
						marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
						marker.setAttribute(IMarker.LINE_NUMBER, new Integer(0));
						marker.setAttribute(IMarker.LOCATION, fwfile.getFile().getLocation().toOSString());
						marker.setAttribute(IMarker.MESSAGE, message);
						marker.setAttribute(IMarker.TEXT, message);
						marker.setAttribute(IDE.EDITOR_ID_ATTR, IDE.getDefaultEditor(fwfile.getFile()).getId());
						problemMarkerMap.put(fileLoc,marker.getId());
					}
				} catch (CoreException e) {
					return e.getStatus();
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();

	}
     
     
     public void parse() {
		
		IMarker marker;
		LineNumberSAXBuilder lbuilder = new LineNumberSAXBuilder();
		String fileLoc = fwfile.getFile().getLocation().toOSString();
		lbuilder.setValidation(false);

		
		try {
			Document frameworkDocument = lbuilder.build("file:///" + fileLoc);
			parse2(frameworkDocument.getRootElement(), this.parentnode, this.fwfile, true, true);			
		} catch (JDOMException e) {
			addProblemMarker(fwfile,e.getMessage());
			// TODO Auto-generated catch block
			//e.printStackTrace();
					// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void parse2(Element element, TreeParentNode parentNode, FrameworkFile fworkFile, boolean doIncludes, boolean doIgnores){
		//just start adding them to the tree and then we can talk about it...
		
		
		//Create a new tree node for this element, and bung it in
		TreeParentNode thisnode = new TreeParentNode(element.getName());
		thisnode.setElement(element);
		thisnode.setFrameworkType(this.fwfile.getFrameworkType());
		thisnode.setFrameworkFile(fworkFile);
		
		if (element instanceof LineNumberElement) {
			LineNumberElement lnElement = (LineNumberElement) element;
			thisnode.setLinenumber(lnElement.getStartLine());
		}
		
		
		//include
		if(isInclude(element) && doIncludes){
			parseInclude(element, thisnode);
		}
		
		//recurse
		List<?> children = element.getChildren();
		for (Iterator<?> iter = children.iterator(); iter.hasNext();) {
			Element childElement = (Element) iter.next();
			if(isIgnore(element) && doIgnores){
				parse2(childElement,parentNode, fworkFile, doIncludes, doIgnores);
			}
			else{
				parse2(childElement,thisnode, fworkFile, doIncludes, doIgnores);
			}
			
		}
		
		
		//ignore
		if(doIgnores){
			if(!isIgnore(element)){
				parentNode.addChild(thisnode);
			}
		}
		else {
			parentNode.addChild(thisnode);
		}
		
		
	}

	
	/**
	 * This function goes and get an included file, such as a circuit in fusebox
	 * @param element The XML element we are going to include
	 * @param parentNode
	 */
	private void parseInclude(Element element, TreeParentNode parentNode){
		//Find the right include rule for this element
		try {
			
			IFile foundFile = findIncludeFile(element);
			
			//Create the framework file
			FrameworkFile thisFwFile = new FrameworkFile(foundFile, parentNode.getFrameworkType());
		
			if(foundFile != null){
				LineNumberSAXBuilder lbuilder = new LineNumberSAXBuilder();
				Document inludeDocument = lbuilder.build("file:///" + foundFile.getLocation().toOSString());
				parse2(inludeDocument.getRootElement(), parentNode, thisFwFile, false, false);
			}
			
			
			
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}


	/**
	 * @param element
	 * @return 
	 * @throws JDOMException
	 * @throws CoreException
	 */
	private IFile findIncludeFile(Element element) throws JDOMException, CoreException {
		//Get the path that we are meant to be seeking
		XPath x = XPath.newInstance("//framework[@id='" + this.fwfile.getFrameworkType() + "']/include[@node='"+ element.getName() + "']");
		Element includeDef = (Element)x.selectSingleNode(this.frameworkDefinitionDocument);
		String pathAttributeName = includeDef.getAttributeValue("path");
		String pathValue = element.getAttributeValue(pathAttributeName);
		String includeType = includeDef.getAttributeValue("type");
		
		if(includeType == null){
			includeType = "absolute";
		}
		
		if(includeType.equalsIgnoreCase("relative")){
			//We add the extension to the original file 
			IResource file = this.fwfile.getFile();
			Path path = new Path(pathValue);
			IResource resource = file.getParent().findMember(path);
			
			IPath location = file.getParent().getFullPath();
			IPath path2 = location.append(pathValue);
			
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IWorkspaceRoot root = workspace.getRoot();
			IResource resource2 = root.findMember(path2);
			

			
			logger.debug("Looking for " + resource + " " + resource2);
			if(resource!=null && resource.exists() && resource instanceof IFolder){
				IFolder childFolder = (IFolder)resource;
				IResource[] itemSuggestsions = childFolder.members();
				List<?> includeFileNames = includeDef.getChildren("file");

				for (int i = 0; i < itemSuggestsions.length; i++) {
					if (itemSuggestsions[i] instanceof IFile) {
						IFile suggFile = (IFile) itemSuggestsions[i];
						for (Iterator<?> iter = includeFileNames.iterator(); iter.hasNext();) {
							Element incFileNameNode = (Element) iter.next();
						
							if(suggFile.getName().equalsIgnoreCase(incFileNameNode.getText()))
							return suggFile;
						}
					}
				}
			}
			else if (resource!=null && resource.exists() && resource instanceof IFile){
				logger.debug("Found an included file, about to go and run off and do it!");
				return (IFile)resource;
			}
			
		}
		
		
		//Other methods, like absolute etc (which would use mappings)
		/*
		
		IResource fullPath = projectMappings.getFullPath(pathValue);
		//	Now we would need to go and find the items, so we loop over the files named under the include node, if it is a folder
		// otherwise we see if the resource exists.
		// 	and then we loop over the children of this path
		
		logger.debug("We are off to look for" + fullPath.getType() + " file? "  + IResource.FILE);
		
		
		IFile foundFile = null;
		
		//if it is a folder, then we go searching for watches using the nodes
		//otherwise, we just use it as is
		
		if (fullPath instanceof IFolder) {
			IFolder folder = (IFolder) fullPath;
			List includeFiles = includeDef.getChildren();
			for (Iterator iter = includeFiles.iterator(); iter.hasNext();) {
				Element incFileElement = (Element) iter.next();
				IResource[] resources = folder.members();
					for (int i = 0; i < resources.length; i++) {
						if (resources[i] instanceof IFile) {
							IFile possMatchFile = (IFile) resources[i];
							if(possMatchFile.getName().equalsIgnoreCase(incFileElement.getText())){
								foundFile = possMatchFile;
								break;
							}
						}
					}
				}
		}
		else if(fullPath instanceof IFile) {
			IFile file = (IFile) fullPath;
			foundFile = file;
		}*/
		
		
		
		return null;
	}
	
	/**
	 * @param element
	 * @return
	 */
	private boolean isInclude(Element element) {
		
		//Check the items name and parent
		try {
			XPath x = XPath.newInstance("//framework[@id='"+ this.fwfile.getFrameworkType()+"']/include");
			List<?> list = x.selectNodes(this.frameworkDefinitionDocument);
			for (Iterator<?> iter = list.iterator(); iter.hasNext();) {
				Element includeElement = (Element) iter.next();
				
				//Definition variables;
				String inc_Name = includeElement.getAttributeValue("node");
				String inc_Parent = includeElement.getAttributeValue("parent");
				//Node variables
				String el_Name = element.getName();
				Element el_Parent = element.getParentElement();
				
				if(inc_Name.equalsIgnoreCase(el_Name)){
					//now we check if we have bot an inc_Parent name and el_Parent
					if(inc_Parent != null && el_Parent != null){
						if(inc_Parent.equalsIgnoreCase(el_Parent.getName())){
							return true;
						}
					}
					
				}
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	
	private boolean isIgnore(Element element){
		
		
		try {
			XPath x = XPath.newInstance("//framework[@id='"+ this.fwfile.getFrameworkType()+"']/ignore");
			List<?> list = x.selectNodes(this.frameworkDefinitionDocument);
			
			for (Iterator<?> iter = list.iterator(); iter.hasNext();) {
				Element ignoreElement = (Element) iter.next();
				if(ignoreElement.getAttributeValue("node").equalsIgnoreCase(element.getName())){
					return true;
				}
			}
			
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return false;
	}
	
}
