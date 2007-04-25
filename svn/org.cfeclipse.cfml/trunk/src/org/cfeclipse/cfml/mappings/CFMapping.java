/**
 * 
 */
package org.cfeclipse.cfml.mappings;

import org.eclipse.core.resources.IResource;

/**
 * @author markdrew
 *
 */
public class CFMapping {
	private IResource resource;
	private String mapping;
	public CFMapping(IResource resource, String mapping) {
		super();
		this.resource = resource;
		this.mapping = mapping;
	}
	public String getMapping() {
		return mapping;
	}
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
	public IResource getResource() {
		return resource;
	}
	public void setResource(IResource resource) {
		this.resource = resource;
	}
	
}
