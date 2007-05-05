package org.cfeclipse.cfml.mappings;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.ui.ide.ResourceUtil;

/**
 * This statuic class manages the the figuring out and setting of mappings for
 * IProjects and IFolders 
 * @author markdrew
 */
public class MappingManager {

	public static final String P_MAPPING_NAME = "Mapping";
	public static final String P_MAPPING_QUALIFIER = "org.cfeclipse.cfml";
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
			resource.setPersistentProperty(new QualifiedName(P_MAPPING_QUALIFIER, P_MAPPING_NAME), mapping);
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
	public static CFMapping[] getMappings(IProject project){
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
	private static void findMappings(IResource resource, ArrayList mappings){
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
			persistentProperty = resource.getPersistentProperty(new QualifiedName(P_MAPPING_QUALIFIER, P_MAPPING_NAME));
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return (persistentProperty == null) ? "" : persistentProperty;
	}
	
	
	/**
	 * Return the Resource for a mapping (might be a file or a folder). This is case sensitive for some reason.
	 * The mappings have to be absolute, so they MUST start with  /
	 * Some examples are:
	 * 	/Somefile.cfm
	 *  /SomeDirectory/Soemthing.cfm
	 *  /Mapping/SomeDirectory/Something.cfm
	 * @param project
	 * @param path
	 * @return
	 * @author markdrew
	 * @throws MappedPathException 
	 * 
	 */
	public static IResource resolveAbsoluteMapping(IProject project, String path) throws MappedPathException{
		
		//First check if it starts with a / 
		if(!path.startsWith("/")){
			MappedPathException e = new MappedPathException("Absolute Paths must start with a /");
			throw e;
		}
		//Ok if we havent thrown anything we find the first segment of the mapping
		String[] strings = path.split("/");
		String FirstMapping = strings[1];
		
		CFMapping foundMapping = null;
		CFMapping[] mappings = getMappings(project);
		for (int i = 0; i < mappings.length; i++) {
			if(mappings[i].getMapping().equalsIgnoreCase("/" + FirstMapping)){
				foundMapping = mappings[i];
				break;
			}
		}
		if(foundMapping !=null){
			//remove the mapping from the action path
			String subPath = path.substring(foundMapping.getMapping().length(), path.length());
			
			IResource resource = foundMapping.getResource();
			if (resource instanceof IProject) {
				IProject mappedItem = (IProject) resource;
				
				IResource resource2 = mappedItem.findMember(subPath);
				return resource2;
			}
		}
		else{
			//if we didnt find the mapping, try it as a project absolute one
			IResource resource = project.findMember(new Path(path));
			return resource;
		}
		
		
		return null;
	}
	
	/**
	 * Removes a mapping from a project 
	 * @param project The project that we are going to remove the mapping from
	 * @param mapping The string representation of a mapping (e.g. "/config")
	 */
	public static void removeMapping(CFMapping mapping){
		try {
			mapping.getResource().setPersistentProperty(new QualifiedName(P_MAPPING_QUALIFIER, P_MAPPING_NAME), "");
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
