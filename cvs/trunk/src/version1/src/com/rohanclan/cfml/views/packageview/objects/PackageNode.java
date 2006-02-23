package com.rohanclan.cfml.views.packageview.objects;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.rohanclan.cfml.util.CFPluginImages;

public class PackageNode implements IComponentViewObject {
	private String name;

	private String image = CFPluginImages.ICON_PACKAGE;

	private String image_off = CFPluginImages.ICON_PACKAGE_OFF;

	private IResource folder;

	private ArrayList children;

	private IComponentViewObject parent;

	public PackageNode(IFolder folder) {
		this.folder = folder;
		this.name = folder.getName();
		initChildren(folder);
		// here we init the children then!
	}

	private void initChildren(IFolder folder) {
		ArrayList folderChildren = new ArrayList();

		// go through the first root

		try {
			IResource children[] = folder.members();
			for (int i = 0; i < children.length; i++) {
				if (children[i] instanceof IFolder) {
					PackageNode packageNode = new PackageNode(
							(IFolder) children[i]);
					packageNode.setParent(this);
					folderChildren.add(packageNode);
				} else if (children[i] instanceof IFile) {
					IFile file = (IFile) children[i];
					if ("cfc".equalsIgnoreCase(file.getFileExtension())) {
						ComponentNode component = new ComponentNode(file);
						component.setParent(this);
						folderChildren.add(component);
					}

				}
			}

		} catch (CoreException e) {
			e.printStackTrace();
		}
		this.children = folderChildren;

	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		// String returnName = this.getParent().getName() + "." + this.name;
		return this.name;
	}

	public String getImage() {
		if (!hasChildren()) {
			return image_off;
		}
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return Returns the children.
	 */
	public ArrayList getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            The children to set.
	 */
	public void setChildren(ArrayList children) {
		this.children = children;
	}

	public boolean hasChildren() {
		return children.size() > 0;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#getParent()
	 */
	public IComponentViewObject getParent() {
		return this.parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#setParent(com.rohanclan.cfml.views.packageview.objects.IComponentViewObject)
	 */
	public void setParent(IComponentViewObject parent) {
		this.parent = parent;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#getPackageName()
	 */
	public String getPackageName() {
		String packageName = "";
		if (!"".equalsIgnoreCase(this.parent.getPackageName())) {
			return this.parent.getPackageName() + "." + getName();
		} else {
			return getName();
		}

	}

	public String toString() {
		return getPackageName();

	}
}
