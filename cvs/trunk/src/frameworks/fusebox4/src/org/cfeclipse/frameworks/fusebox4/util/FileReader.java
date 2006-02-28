/*
 * Created on 22-Dec-2004
 * @author Mark Drew
 */
package org.cfeclipse.frameworks.fusebox4.util;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * @author Mark Drew
 * 
 * this class is a helper class that allows you to get a file, or the contents
 * of the file if not saved and parse the blighters
 */
public class FileReader {
	private boolean isSaved;
	private String filecontents;
	private IProject project;
	private IPath path;
	private String stringPath;
	private IFile currFile;
	
	
	
	/**
	 * Pass in the file path and the project
	 */
	/**
	 * @param path
	 * @param project
	 */
	public FileReader(String path, IProject project) {
		this.stringPath = path;
		this.project = project;
		
		
	}
	
	/**
	 * @return
	 */
	public String getFileContents(){
	
		IFile thisFile = project.getFile(this.stringPath);
		InputStream is = null;
		String fileContents = null;
		
		if(thisFile.exists()){
			try {
				is = thisFile.getContents();
				fileContents = Utils.getStringFromInputStream(is);
				
			} catch (CoreException e1) {
				
				e1.printStackTrace();
			} 
		}
		return fileContents;
	}
	
	/**
	 * @param file
	 * @param alternate
	 * @return
	 */
	public IFile getFilePath(String file, String alternate){
		//This will go and get the proper path for a file, upper or lower, and we can put a list of 
		//Accepted endings too, to deal with circuit.xml ¦¦ circuit.xml.cfm as well as fusebox.xml ¦¦ fusebox.xml.cfm
		IFile mainFile = project.getFile(stringPath + file);
		
		/*
		if(!cirFile.exists()){  if we dont have an upper case 
			cirFile = project.getFile(fbxpath + fbxcircuits.toLowerCase());
			if(!cirFile.exists()){
				cirFile = null;
				Utils.println("FBX4parser:getCircuits: circuit file not found");
			}
		}*/ 
		
		if(!mainFile.exists()){ /* we havent found it so try lowercase */
			mainFile = project.getFile(stringPath + file.toLowerCase());
			if(!mainFile.exists()){
				//Utils.println("FileReader:getFilePath: file not found:" + mainFile);
//				Now we do a check for the alternate
				IFile altFile = project.getFile(stringPath + alternate);
				
				if(!altFile.exists()){
					altFile = project.getFile(stringPath + alternate.toLowerCase());
					if(!altFile.exists()){
						//Utils.println("FileReader:getFilePath: alt file not found:" + altFile);
						return null;
					} else{
						return altFile;
					}
				}
				else {
					return altFile;
				}
			
			} else {
				return mainFile;
			}
				
		} else {
			return mainFile;
		}
		
		
		
	}
		
}
