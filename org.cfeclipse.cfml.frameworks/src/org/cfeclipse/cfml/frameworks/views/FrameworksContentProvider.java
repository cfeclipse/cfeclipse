/*
 * Created on Mar 27, 2007 by markdrew
 *
 * The MIT License
 * Copyright (c) 2007 CFEclipse Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package org.cfeclipse.cfml.frameworks.views;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.frameworks.parsers.GenericFrameworkParser;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IViewSite;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

public class FrameworksContentProvider implements IStructuredContentProvider,
		ITreeContentProvider {

	private MGCoreFilesListener savelistener = new MGCoreFilesListener();
	private HashMap<String, Document> xmlDocments = new HashMap<String, Document>();
	private Log logger = LogFactory.getLog(FrameworksContentProvider.class);
	private IViewSite viewSite;
	private IProject project;
	private TreeParentNode invisibleRoot;
	private TreeViewer viewer;
	private Document virtualDocument;

	class MGCoreFilesListener implements IResourceChangeListener {

		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta rootDelta = event.getDelta();
			IResource changedFile = findSavedResource(rootDelta);
			// logger.debug("I heard there was a change to " + changedFile +
			// " and the event was" + event.getType());

			if (changedFile != null) {
				// get the open state of the viewer..
				Object[] expandedElements = viewer.getExpandedElements();
				// for (int i = 0; i < expandedElements.length; i++) {
				// logger.debug(expandedElements[i]);
				// }

				initialize();
				viewer.refresh();
				viewer.setExpandedElements(expandedElements);
				viewer.expandToLevel(3);
			}
		}

		private IResource findSavedResource(IResourceDelta rootDelta) {
			IResource resource = null;
			IResource res = rootDelta.getResource();
			if (res.getType() == IResource.FILE
					&& !((rootDelta.getFlags() & IResourceDelta.CONTENT) == 0)) {

				return res;
			} else {
				IResourceDelta[] delta = rootDelta.getAffectedChildren();
				for (int i = 0; i < delta.length; i++) {
					resource = findSavedResource(delta[i]);
				}
			}

			return resource;
		}

	}

	public void addModifyListner() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.addResourceChangeListener(savelistener,
				IResourceChangeEvent.POST_CHANGE);
	}

	public void removeModifyListner() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.removeResourceChangeListener(savelistener);

	}

	public FrameworksContentProvider(IViewSite viewSite, IProject project,
			TreeViewer viewer) {
		super();
		this.viewSite = viewSite;
		this.project = project;
		this.viewer = viewer;
		addModifyListner();
	}

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		logger.debug("input has changed");
	}

	public void dispose() {
		removeModifyListner();
	}

	public Object[] getElements(Object parent) {
		if (parent.equals(viewSite)) {
			if (invisibleRoot == null)
				initialize();
			return getChildren(invisibleRoot);
		}
		return getChildren(parent);
	}

	public Object getParent(Object child) {
		if (child instanceof TreeNode) {
			return ((TreeNode) child).getParent();
		}
		return null;
	}

	public Object[] getChildren(Object parent) {
		if (parent instanceof TreeParentNode) {
			return ((TreeParentNode) parent).getChildren();
		}
		return new Object[0];
	}

	public boolean hasChildren(Object parent) {
		if (parent instanceof TreeParentNode)
			return ((TreeParentNode) parent).hasChildren();
		return false;
	}

	private void clearProblemMarkers(ArrayList<?> frameworkFiles) {
		final ArrayList<?> ffiles = frameworkFiles;
		Job job = new Job("Make Files") {
			public IStatus run(IProgressMonitor monitor) {
				try {
					for (Iterator<?> iter = ffiles.iterator(); iter.hasNext();) {
						FrameworkFile ffile = (FrameworkFile) iter.next();
						monitor.beginTask("Create some files", 100);
						ffile.getFile()
								.deleteMarkers(IMarker.PROBLEM, false, 0);
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

	/*
	 * We will set up a dummy model to initialize tree heararchy. In a real
	 * code, you will connect to a real model and expose its hierarchy.
	 */
	private void initialize() {
		// Add listeners to the right files

		// Get that Project's MG Config
		if (project == null) {
			TreeParentNode root = new TreeParentNode("No Project Selected");
			root.setType("no_project");
			invisibleRoot = new TreeParentNode("");
			invisibleRoot.addChild(root);
			return;
		}

		// Try to list all the projects

		// logger.debug("Starting the parse of the project");
		ProjectParser pp = new ProjectParser(project);

		ArrayList<?> frameworkFiles = pp.getFrameworkFiles();
		clearProblemMarkers(frameworkFiles);
		// Quickly check if there are no folders or files to parse

		if (frameworkFiles.size() == 0) {
			TreeParentNode root = new TreeParentNode(null, "no_project");
			root.setName("No Project configuration path defined");
			invisibleRoot = new TreeParentNode(null, "root");
			invisibleRoot.addChild(root);
			return;
		}

		// Once we have done that, we will have a master list of FrameworkFiles
		// to parse!

		// Create an XML document of all the nodes

		TreeParentNode root = new TreeParentNode(null, "framework_root");
		root.setName(project.getName());
		root.setFrameworkType("framework_root");

		// loop through the files that need to be parsed, we shall create a root
		// node for each and then parse the actual document

		for (Iterator<?> iter = frameworkFiles.iterator(); iter.hasNext();) {
			FrameworkFile ffile = (FrameworkFile) iter.next();

			TreeParentNode ffNode = new TreeParentNode(ffile.getFile()
					.getProjectRelativePath().toOSString());
			ffNode.setFrameworkFile(ffile);
			ffNode.setFrameworkType(ffile.getFrameworkType());
			ffNode.setName(ffile.getFrameworkType());

			GenericFrameworkParser gfp = new GenericFrameworkParser(ffile,
					ffNode);
			gfp.parse();

			// logger.debug("Done loading the generic parser");

			root.addChild(ffNode);

		}

		// Parse the tree, cloning the elements and adding them to the XML
		// Document
		invisibleRoot = new TreeParentNode("");
		invisibleRoot.addChild(root);

		Document xmlDocument = new Document();
		Element rootElement = new Element("root");
		xmlDocument.setRootElement(rootElement);

		convertTreeToXML(invisibleRoot.getChildren(), rootElement);

		this.virtualDocument = xmlDocument;
		StringWriter sw = new StringWriter();
		XMLOutputter xmlOut = new XMLOutputter();
		try {
			xmlOut.output(xmlDocument, sw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.debug("theXML is" + sw.toString());
		return;

	}

	private void convertTreeToXML(TreeParentNode[] nodes, Element parentElement) {

		for (int i = 0; i < nodes.length; i++) {
			// Create a new Element to add to the parentElement
			Element newElement = new Element(nodes[i].getFrameworkType());
			if (nodes[i].getElement() != null) {
				Element el = (Element) nodes[i].getElement().clone();
				if (el.getText().trim().length() == 0) {
					el.removeContent();
				}

				newElement = el;
				// newElement.setName(clone.getName());
				// newElement.setAttributes(clone.getAttributes());
			}

			if (nodes[i].hasChildren()) {
				convertTreeToXML(nodes[i].getChildren(), newElement);
			}

			parentElement.addContent(newElement);

		}
	}

	public void setDocument(String key, Document document) {
		this.xmlDocments.put(key, document);
	}

	public Document getDocument(String key) {
		return (Document) this.xmlDocments.get(key);
	}

	public Document getVirtualDocument() {
		return virtualDocument;
	}

	public void setVirtualDocument(Document virtualDocument) {
		this.virtualDocument = virtualDocument;
	}

}
