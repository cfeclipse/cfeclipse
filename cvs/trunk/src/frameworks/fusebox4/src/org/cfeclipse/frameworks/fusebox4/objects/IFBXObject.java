/*
 * Created on 20-Feb-2006
 * Copyright (c) 2006 markd
 */
package org.cfeclipse.frameworks.fusebox4.objects;

import java.util.ArrayList;

import org.w3c.dom.Node;

/**
 * @author markd
 *
 */
public interface IFBXObject {

	/**
	 * @return Returns the error.
	 */
	public abstract String getError();

	/**
	 * @return Returns the error.
	 */
	public abstract boolean hasError();

	/**
	 * @param error The error to set.
	 */
	public abstract void setError(String error);

	/**
	 * Pass in an error ID, this makes it easier to define what errors there are
	 * <li>
	 * 	<ul>0 = no error</ul>
	 *  <ul>1 = Circuits File Not Found</ul>
	 *  <ul>2 = Switch File Not Found</ul>
	 * </li>
	 * @param errorid
	 */
	public abstract void setError(int errorid);

	/**
	 * @return Returns the errorid.
	 */
	public abstract int getErrorid();

	/**
	 * @param errorid The errorid to set.
	 */
	public abstract void setErrorid(int errorid);

	/**
	 * @return Returns the fusedoc.
	 */
	public abstract String getFusedoc();

	/**
	 * @param fusedoc The fusedoc to set.
	 */
	public abstract void setFusedoc(String fusedoc);

	/**
	 * @return Returns the extension.
	 */
	public abstract String getExtension();

	/**
	 * @param extension The extension to set.
	 */
	public abstract void setExtension(String extension);

	//Methods for the listners
	public abstract void addListener(IDeltaListener listener);

	public abstract void removeListener(IDeltaListener listener);

	public abstract String getName();

	public abstract void setName(String name);

	public abstract void addChild(IFBXObject child);

	public abstract void removeChild(IFBXObject child);

	public abstract Object[] getChildren();

	public abstract boolean hasChildren();

	public abstract void setChildren(ArrayList children);

	public abstract Node getXmlNode();

	public abstract void setXmlNode(Node xmlNode);

	public abstract String getIcon();

	public abstract void setIcon(String icon);

	public abstract IFBXObject getParent();

	public abstract void setParent(IFBXObject parent);

	public abstract String toString();

	public abstract String getLabel();
	

}