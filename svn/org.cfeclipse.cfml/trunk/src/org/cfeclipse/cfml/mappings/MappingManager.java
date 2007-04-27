package org.cfeclipse.cfml.mappings;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

/**
 * This statuic class manages the the figuring out and setting of mappings for
 * IProjects and IFolders 
 * @author markdrew
 */
public class MappingManager {

	// Logger for this class
	private Log logger = LogFactory.getLog(MappingManager.class);

	
	/**
	 * Sets a mapping to a resource so that it can be used in path translation.
	 * @param resource The resource the mapping references.
	 * @param mapping  The mapping name used to reference the resource.
	 * @author Mike Kelp  
	 */
	public static void setMapping(IResource resource, String mapping) {
		// Ensure we have a / at the beginning of the mapping for consistency
		if (mapping.charAt(0) != '/') {
			mapping = "/".concat(mapping);
		}
		
		// Set the mapping property of this resource
		try {
			resource.setPersistentProperty(new QualifiedName("org.cfeclipse.cfml", "Mapping"), mapping);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Gets the mappings set for a given project.
	 * @param project The project to search for mappings.
	 * @return An array of mappings that exist for the project.
	 * @author Mark Drew
	 */
	public CFMapping[] getMappings(IProject project){
		ArrayList mappings = new ArrayList();
		findMappings(project, mappings);
		
		// if they are null, we set the project as having the base "/" mapping
		if(mappings.isEmpty()){
			setMapping(project, "/");
			CFMapping mapping = new CFMapping(project, "/");
			mappings.add(mapping);
		}
		
		return (CFMapping[]) mappings.toArray(new CFMapping[mappings.size()]);
	}


	/**
	 * Recursively searches a resource for mappings and appends them to the mappings ArrayList.
	 * @param resource The resource to search for mappings.
	 * @param mappings The ArrayList to add the mappings to.
	 * @author Mark Drew
	 */
	private void findMappings(IResource resource, ArrayList mappings){
		if (resource instanceof IProject) {
			IProject mappingCandidate = (IProject) resource;
			String mapping = getMapping(mappingCandidate);
			if(mapping!= null){
				CFMapping mappingObj = new CFMapping(mappingCandidate,mapping);
				mappings.add(mappingObj);
			}
			try {
				IResource[] resources = mappingCandidate.members();
				for (int i = 0; i < resources.length; i++) {
					findMappings(resources[i], mappings);
				}
				
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		else if(resource instanceof IFolder){
			IFolder mappingFolderCandidate = (IFolder) resource;
			String folderMapping = getMapping(mappingFolderCandidate);
			if(folderMapping != null){
				CFMapping folderMappingObj = new CFMapping(mappingFolderCandidate, folderMapping);
				mappings.add(folderMappingObj);
				
				try {
					IResource[] resources = mappingFolderCandidate.members();
					for (int i = 0; i < resources.length; i++) {
						findMappings(resources[i], mappings);
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	/**
	 * Gets the mapping property for a resource.
	 * @param resource
	 * @return
	 * @author Mark Drew
	 */
	public static String getMapping(IResource resource){
		String persistentProperty = null;

		try {
			persistentProperty = resource.getPersistentProperty(new QualifiedName("org.cfeclipse.cfml", "Mapping"));
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return (persistentProperty == null) ? "" : persistentProperty;
	}
	
}
