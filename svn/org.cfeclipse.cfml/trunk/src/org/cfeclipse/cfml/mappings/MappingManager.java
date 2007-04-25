/**
 * 
 */
package org.cfeclipse.cfml.mappings;

import java.util.ArrayList;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

/**
 * This statuic class manages the the figuring out and setting of mappings for
 * IProjects and IFolders 
 * @author markdrew
 *
 */
public class MappingManager {

	
	
	public static void setMapping(IResource resource, String mapping){
		
	}
	
	public CFMapping[] getMappings(IProject project){
		ArrayList mappings = new ArrayList();
		findMappings(project, mappings);
		
		//if they are null, we set the project as having the base "/" mapping
		if(mappings.isEmpty()){
			setMapping(project, "/");
			CFMapping mapping = new CFMapping(project, "/");
			mappings.add(mapping);
		}
		
		return (CFMapping[]) mappings.toArray(new CFMapping[mappings.size()]);
	}
	
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
	
	public static String getMapping(IResource resource){
		
			String persistentProperty = null;
			try {
				persistentProperty = resource.getPersistentProperty(new QualifiedName("org.cfeclipse.cfml", "Mapping"));
			} catch (CoreException e) {
				e.printStackTrace();
			}
			if(persistentProperty != null && persistentProperty.length() > 0){
				return persistentProperty;
			}
		
		return persistentProperty;
	}
	
}
