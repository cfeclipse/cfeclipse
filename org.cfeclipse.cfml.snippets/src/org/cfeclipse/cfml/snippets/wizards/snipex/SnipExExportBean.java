/**
 * 
 */
package org.cfeclipse.cfml.snippets.wizards.snipex;

import org.eclipse.core.resources.IProject;

/**
 * @author markdrew
 * 
 * Used to pass values amongst the wizards
 *
 */
public class SnipExExportBean {

	private IProject project;
	private String snipexServer;
	private Object files;
	private String email;
	private String username;
	private String password; //might not be used
	private String description;
	private String name;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Object getFiles() {
		return files;
	}
	public void setFiles(Object files) {
		this.files = files;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public IProject getProject() {
		return project;
	}
	public void setProject(IProject project) {
		this.project = project;
	}
	public String getSnipexServer() {
		return snipexServer;
	}
	public void setSnipexServer(String snipexServer) {
		this.snipexServer = snipexServer;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
