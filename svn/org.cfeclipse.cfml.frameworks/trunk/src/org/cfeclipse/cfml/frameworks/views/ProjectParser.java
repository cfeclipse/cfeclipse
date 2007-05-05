package org.cfeclipse.cfml.frameworks.views;

import java.util.ArrayList;
import java.util.Iterator;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.frameworks.FrameworkManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

/**
 * @author markdrew
 * 	This class is used to check the contents of a Project and to construct a parse list
 * 	of framework files and folders
 */
public class ProjectParser {

	private IProject project;
	private ArrayList<FrameworkFile> filesToParse;
	private ArrayList foldersToParse;
	private ArrayList foldersToScan; //these are the folders we are going to check for items to add.

	private Log logger = LogFactory.getLog(ProjectParser.class);

	private FrameworkManager frameworkManager;
	
	public ProjectParser(IProject project) {
		// When we construct this object, we do a few parses.
		
		// Stage1Parser (fileParser): Look for all the FILES that have been marked as framework files and add them
		// Stage2Parser (directoryParser): If no files have been found, then parse any directories and mark the files as parsed
		// Stage3Parser (defaultDirectoryParser): If no files have been found, then parse any default directories 
		
		// If we havent found any yet, then there are none.
		
		logger.debug("Initialising");

		frameworkManager = new FrameworkManager();
		
		this.project = project;
		this.filesToParse = new ArrayList<FrameworkFile>();
		this.foldersToParse = new ArrayList();
		this.foldersToScan = new ArrayList();
		
		//STAGE 1
		logger.debug("Stage 1 file parser" );
		fileParser(project);
		
		//STAGE 2: this parses marked folder, and then checks the contents, it also checks that they are not defined already in the filesToParse
		logger.debug("Stage 2 folder parser");
		folderParser(project);
		
		
		//STAGE 3: if there are no files, we fall back on parsing the default folders
		if(filesToParse.size() == 0){
			logger.debug("Stage 3 default folder parser");
			addDefaultFolders();
		}
		
		//STAGE 4: We now parse the folders, adding items to it
		folderChecker();
		
		/*
		try {
			parseProject(project);

			if (this.filesToParse.isEmpty() && this.foldersToParse.isEmpty()) {
				addDefaultFolders();
			}
		} catch (CoreException e) {
			logger.error(e);
		}
		*/
	}
	
	





	/**
	 *  STAGE 1
	 *  This function is a read only function for files, checking if they are a framework file and adding it to the list
	 * 
	 * @param project
	 */
	private void fileParser(IResource projectOrFolder){
		
		if (projectOrFolder instanceof IProject) {
			IProject projectToParse = (IProject) projectOrFolder;
			try {
				IResource[] resources = projectToParse.members();
				for (int i = 0; i < resources.length; i++) {
					if (resources[i] instanceof IFile) {
						//check the file and add it to the list, as well as marking it as a framework file
						checkAndAddResource(resources[i]);
					}
					else if(resources[i] instanceof IFolder){
						fileParser(resources[i]);
					}
				}
				
				
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		else if (projectOrFolder instanceof IFolder){
			IFolder folderToParse = (IFolder) projectOrFolder;
			try {
				IResource[] resources = folderToParse.members();
				for (int i = 0; i < resources.length; i++) {
					if (resources[i] instanceof IFile) {
						//check the file and add it to the list, as well as marking it as a framework file
						checkAndAddFile((IFile)resources[i]);
					}
					else if(resources[i] instanceof IFolder){
						fileParser(resources[i]);
					}
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		
	}


	
	
	/**
	 * 	STAGE 2 
	 * this function is a read only function for folders, checking if they are framework marked folders and adding their children to the list
	 * The problem that I could forsee with this is that we are doing double checking, but this would only be for folders that are marked as config
	 * 
	 * @param projectOrFolder
	 */
	private void folderParser(IResource projectOrFolder){
		//we iteratively loop through folders, then if we find one, we add it to the folders to check, 
		if (projectOrFolder instanceof IProject) {
			IProject projectToScan = (IProject) projectOrFolder;
			//check and add it
			checkAndAddResource(projectOrFolder);
			try {
				IResource[] resources = projectToScan.members();
				for (int i = 0; i < resources.length; i++) {
					folderParser(resources[i]);
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (projectOrFolder instanceof IFolder) {
			IFolder folderToScan = (IFolder) projectOrFolder;
			checkAndAddResource(folderToScan);
			try {
				IResource[] resources = folderToScan.members();
				for (int i = 0; i < resources.length; i++) {
					folderParser(resources[i]);
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	/**
	 * STAGE 2.5
	 * Now we have a list of folders, we loop through them, and get the files underneath them, checking that they are:
	 *  1. NOT already marked
	 *  2. If they are of a framework type, and they are not already in the list, marking them and adding them to the list  
	 */
	private void folderChecker() {
			
		ArrayList folderConsiderations = this.foldersToParse;
		for (Iterator iter = folderConsiderations.iterator(); iter.hasNext();) {
			IResource element = (IResource) iter.next();
			
			IResource[] resources = new IResource[]{};
			if (element instanceof IProject) {
				IProject projectFolder = (IProject) element;
				try {
					resources = projectFolder.members();
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(element instanceof IFolder){
				IFolder normalFolder = (IFolder) element;
				try {
					resources = normalFolder.members();
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
					for (int i = 0; i < resources.length; i++) {
						if (resources[i] instanceof IFile) {
							IFile fileConsideration = (IFile) resources[i];
							
							String frameworkType2 = frameworkManager.getFrameworkType(fileConsideration);
							if(!fileisMarked(fileConsideration)  && frameworkType2 != null){
								//we *should* add it here, but we need to check first
								boolean found = false;
								for (Iterator iterator = this.filesToParse
										.iterator(); iterator.hasNext();) {
									FrameworkFile fwFile = (FrameworkFile) iterator.next();
									if(fwFile.getFile().equals(fileConsideration)){
										found = true;
									}
								}
								if(!found){
									//we mark the file 
									setFrameworkForFile(fileConsideration, frameworkType2);
									FrameworkFile frameworkFile = new FrameworkFile(fileConsideration, frameworkType2);
									this.filesToParse.add(frameworkFile);
									
								}
							}
						}
					}
			
		}
		
	}
	/**
	 * @param fileConsideration
	 */
	private void setFrameworkForFile(IFile fileConsideration, String frameworkType) {
		// TODO Auto-generated method stub
		try {
			fileConsideration.setPersistentProperty(new QualifiedName("", "isFrameworkConfig"), "true");
			fileConsideration.setPersistentProperty(new QualifiedName("", "FrameworkType"), frameworkType);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	

	/**
	 * STAGE 3
	 * @author Mike Kelp
	 * Adds the default framework folders (from the frameworks config)
	 * to the search path for framework files.
	 */
	private void addDefaultFolders() {
		logger.trace("CFFrameworks: ProjectParser - Adding Default Folders");

		try {
			// Get the default framework folders that exist in this project
			IResource[] defaultFolders = frameworkManager.getDefaultFrameworkDirectories(this.project);
	
			/*
			 * Loop over the default framework folders, adding them to the
			 * resources to search.
			 */ 
			try {
				for (int currFolder = 0; currFolder < defaultFolders.length; currFolder++) {
					// Set persistent property so frameworkFiles will not be empty next time
					defaultFolders[currFolder].setPersistentProperty(new QualifiedName("", "isFrameworkConfig"), "true");
	
					// Add the folder to the list of files to parse
					this.foldersToParse.add(defaultFolders[currFolder]);
				}
			} catch (CoreException e) {
				logger.error(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function should only be passed Project and folder resources
	 * @param resource
	 * @throws CoreException
	 */
	private void parseProject(IResource resource) throws CoreException{
		if (resource instanceof IProject) {
			IProject proj = (IProject)resource;
			checkAndAddResource(proj);
			
			IResource[] resources = proj.members();
			for (int i = 0; i < resources.length; i++) {
				if(resources[i] instanceof IFile){
					checkAndAddResource(resources[i]);
				}
				else{
					parseProject(resources[i]);
				}
			}
		}
		else if (resource instanceof IFolder){
			IFolder fold = (IFolder)resource;
			checkAndAddResource(fold);
			
			IResource[] resources = fold.members();
			for (int i = 0; i < resources.length; i++) {
				if(resources[i] instanceof IFile){
					checkAndAddResource(resources[i]);
				}
				else{
					parseProject(resources[i]);
				}
			}
		}
	}

	/**
	 * @param proj
	 */
	private void checkAndAddResource(IResource resource) {
		if(resource != null){
			try {
				String persistentProperty = resource.getPersistentProperty(new QualifiedName("", "isFrameworkConfig"));
				
				if(persistentProperty != null && persistentProperty.equals("true")){
					if(resource instanceof IFile){
						//Make a framework file and get the setting
						checkAndAddFile((IFile)resource);
					}
					else {
						this.foldersToParse.add(resource);
					}
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * This checks a file, and adds it to the list
	 * @param resource
	 */
	private void checkAndAddFile(IFile resource) {
		
		String fileFramework = getFileFramework(resource);
		if(fileisMarked(resource) && fileFramework != null){
			//Create a framework file
			FrameworkFile file = new FrameworkFile(resource, fileFramework);
			filesToParse.add(file);
		}
	}
	
	
	private boolean fileisMarked(IFile file){
		try {
			String persistentProperty = file.getPersistentProperty(new QualifiedName("", "isFrameworkConfig"));
			if(persistentProperty != null && persistentProperty.equals("true")){
				return true;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private String getFileFramework(IFile file){
		try {
			String persistentProperty = file.getPersistentProperty(new QualifiedName("", "FrameworkType"));
			if(persistentProperty != null && persistentProperty.length() > 0){
				return persistentProperty;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList getFrameworkFiles() {

			//markd: when this method is called we should have already parsed the project and got all the
		//IFiles in an array, now we create a new array that we return with FrameworkFiles
		
	
		
		return this.filesToParse;
		
		//Need to do a couple of parses
		// Stage1Parser: Look for all the FILES that have been marked as framework files and add them
		// Stage2Parser: If no files have been found, then parse any directories and mark the files as parsed
		// Stage3Parser: If no files have been found, then parse any default directories 
		// If we havent found any yet, then there are none.
		
		/*
		ArrayList frameworkFiles = new ArrayList();
		
		ArrayList foldersToParse = getFoldersToParse();
//		Loop through the folders, getting items, computing what type they could be
		
		for (Iterator iter = foldersToParse.iterator(); iter.hasNext();) {
				Object item = iter.next();
			if (item instanceof IProject) {
				IProject proj = (IProject) item;
				try {
					IResource[] resources = proj.members();
					for (int i = 0; i < resources.length; i++) {
						if(resources[i] instanceof IFile){
							String frameworkType = frameworkManager.getFrameworkType(resources[i]);
							if(frameworkType != null){
								FrameworkFile frameworkFile = new FrameworkFile((IFile)resources[i], frameworkType);
								frameworkFiles.add(frameworkFile);
							}
						}
					}
					
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (item instanceof IFolder) {
				IFolder folder = (IFolder) item;
				try {
					IResource[] resources = folder.members();
					for (int i = 0; i < resources.length; i++) {
						if(resources[i] instanceof IFile){
							String frameworkType = frameworkManager.getFrameworkType(resources[i]);
							if(frameworkType != null){
									FrameworkFile frameworkFile = new FrameworkFile((IFile)resources[i], frameworkType);
									frameworkFiles.add(frameworkFile);
							}
						}
					}
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		//Loop through the files, knowing what type they are (they should be pre-checked by this stage)
		ArrayList filesToParse = getFilesToParse();
		for (Iterator iter = filesToParse.iterator(); iter.hasNext();) {
			IFile fileItem = (IFile) iter.next();
			
			try {
				String persistentProperty = fileItem.getPersistentProperty(new QualifiedName("", "FrameworkType"));
				if(persistentProperty != null){
					FrameworkFile frameworkFile = new FrameworkFile(fileItem,persistentProperty);
					frameworkFiles.add(frameworkFile);
				}
				
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return frameworkFiles;*/
	}
	

	public ArrayList getFilesToParse() {
		return filesToParse;
	}

	public void setFilesToParse(ArrayList filesToParse) {
		this.filesToParse = filesToParse;
	}

	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	/**
	 * @return
	 */
	public ArrayList getFoldersToParse() {
		return this.foldersToParse;
	}
	
	
}
