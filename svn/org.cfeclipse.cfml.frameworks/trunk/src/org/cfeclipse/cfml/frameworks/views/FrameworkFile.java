/**
 * 
 */
package org.cfeclipse.cfml.frameworks.views;

import org.eclipse.core.resources.IFile;

/**
 * @author markdrew
 * This is a simple bean to store in a list a resoure (a File) and its defied or
 * computed framework
 */
public class FrameworkFile {
	private IFile file;
	private String frameworkType;
	public FrameworkFile(IFile file, String frameworkType) {
		super();
		this.file = file;
		this.frameworkType = frameworkType;
	}
	public IFile getFile() {
		return file;
	}
	public void setFile(IFile file) {
		this.file = file;
	}
	public String getFrameworkType() {
		return frameworkType;
	}
	public void setFrameworkType(String frameworkType) {
		this.frameworkType = frameworkType;
	}
	
}
