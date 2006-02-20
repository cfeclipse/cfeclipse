/*
 * Created on 14-Jan-2005
 *
 * The MIT License
 * Copyright (c) 2004 Mark Drew
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
 **/
package org.cfeclipse.frameworks.fusebox4.objects;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

/**
 * @author Administrator
 * 14-Jan-2005
 * fusebox3cfe2
 * Description: This class is a folder for any XFA's that are associated with a circuit
 */
public class XFAFolder {
	private FBXCircuit parent;
	private ArrayList children;
	private IProject project;
	private IFile circuitFile;
	private IFile switchFile;
	private String error;
	private String folderName = "XFAs";
	
	/**
	 * A default constructor
	 */
	public XFAFolder() {
		super();
		children = new ArrayList();
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
	/**
	 * @return Returns the circuitFile.
	 */
	public IFile getCircuitFile() {
		return circuitFile;
	}
	/**
	 * @param circuitFile The circuitFile to set.
	 */
	public void setCircuitFile(IFile circuitFile) {
		this.circuitFile = circuitFile;
	}
	/**
	 * @return Returns the parent.
	 */
	public FBXCircuit getParent() {
		return parent;
	}
	/**
	 * @param parent The parent to set.
	 */
	public void setParent(FBXCircuit parent) {
		this.parent = parent;
	}
	/**
	 * @return Returns the project.
	 */
	public IProject getProject() {
		return project;
	}
	/**
	 * @param project The project to set.
	 */
	public void setProject(IProject project) {
		this.project = project;
	}
	/**
	 * @return Returns the switchFile.
	 */
	public IFile getSwitchFile() {
		return switchFile;
	}
	/**
	 * @param switchFile The switchFile to set.
	 */
	public void setSwitchFile(IFile switchFile) {
		this.switchFile = switchFile;
	}
	
	public void addChild(FBXxfa child) {
		children.add(child);
		
	}
	
	public void addChildren(ArrayList children){
		this.children.addAll(children);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return folderName;
	}
	public boolean hasChildren() {
			return children.size()>0;
	}
}
