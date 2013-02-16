package org.cfeclipse.cfml.frameworks.actions;

import org.cfeclipse.cfml.frameworks.views.TreeNode;
import org.eclipse.core.internal.resources.Project;
import org.jdom.Document;
import org.jdom.Element;

@SuppressWarnings("restriction")
public interface IBaseAction {

	public abstract void setElement(Element actionElement);

	public abstract void setNode(TreeNode treeNode);
	public abstract void run();

	public abstract void setInsertSnippet(String insertSnippet) ;

	public abstract void setProject(Project project);
	
	public abstract void setVirtualDocument(Document virtualDocument);
}