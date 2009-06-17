/*
 * Created on Aug 29, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver Tupman
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
 */
package org.cfeclipse.cfml.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.preferences.BrowserPreferenceConstants;
import org.cfeclipse.cfml.preferences.CFMLPreferenceConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.QualifiedName;

/**
 * This is a helper class to provide various useful methods that related to Eclipse
 * resources (IResource) and any other class derived from IResource (e.g. IFile, IFolder,
 * etc.).
 * 
 * @author Oliver Tupman
 */
public class ResourceUtils {
	/**
	 * Gets suggestions for filenames & directories in the same directory as the current
	 * resource + the pathname supplied.<br/>
	 * Therefore a resource in \Fred\Dir supplied with a pathSoFar of "..\" would result in
	 * the list of pathnames that exist in \Fred (including the entry 'Dir')
	 * If pathSoFar is "..\a" then the list of pathnames will be \Fred\a*"
	 * 
	 * @param res The resource to find the file around 
	 * @param pathSoFar The path current typed in. 
	 * @return The list of suggestions based upon the resource & pathSoFar supplied
	 */
	public static Set getIResourceSurroundingResources(IResource res, String pathSoFar) {
		//
		// How this works:
		//
		// First off we check to see whether the user has inputted something
		// in pathSoFar. If so then we want to append any path info to the 
		// resources path. Also, if there's content in pathSoFar then we need
		// to retrieve anything that's non-path. So first off we separate the
		// pathSoFar into pre-path and postPath. Post path is used to filter
		// the set of files in the final directory.
		// Having done that we get the folder based upon the supplied resource and
		// the pre-path stuff. From that we can get the children and then
		// whip through them all testing to see if our postPath filters them
		// at all.
		String postPath = "";
		
		if(!pathSoFar.endsWith("\\") && !pathSoFar.endsWith("/")) {
			int lastSlash = pathSoFar.lastIndexOf("\\");
			lastSlash = (lastSlash == -1) ? pathSoFar.lastIndexOf("/") : lastSlash;
			if(lastSlash == -1) {
				postPath = pathSoFar;
				pathSoFar = "";
			} else {
				postPath = pathSoFar.substring(lastSlash, pathSoFar.length());
				pathSoFar = pathSoFar.substring(pathSoFar.length());
			}
		}

		IPath folder = res.getFullPath().removeLastSegments(1).append(pathSoFar);
		IFolder folderRes = res.getWorkspace().getRoot().getFolder(folder);
		
		HashSet suggestions = new HashSet();		
		if(folderRes == null) {
		} else {
			try {
				IResource children[] = folderRes.members();
				for(int i = 0; i < children.length; i++) {
					if(!children[i].getName().toLowerCase().startsWith(postPath.toLowerCase()))
						continue;
					
					if(children[i] instanceof IFolder || children[i] instanceof IFile) {
						suggestions.add(children[i]);
					}
				}
			}catch(CoreException ex) {
				// Fail gracefully as this function should not be called in a critical situation.
				ex.printStackTrace();
			}
		}
		return suggestions;
	}

    /**
     * Gets an array of the project natures associated with the supplied project.
     * 
     * @param srcProject The project to get the natures from
     * @return An array of natures
     * @throws CoreException Thrown by methods called
     */
    public static IProjectNature[] getProjectNatures(IProject srcProject) throws CoreException
    {
        //Assert.isNotNull(srcProject,"ResourceUtils::getProjectNatures()");
        if(srcProject == null)
        		throw new IllegalArgumentException("ResourceUtils::getProjectNatures()");
        
        IProjectDescription prjDesc = srcProject.getDescription();
        String [] natures = prjDesc.getNatureIds();
        IProjectNature [] natureArray = new IProjectNature[natures.length];
        for(int i = 0; i < natures.length; i++)
        {
            try {
                natureArray[i] = srcProject.getNature(natures[i]);
            }
            catch(CoreException ex) 
            {
                //System.err.println("ResourceUtils::getProjectNatures() - Exception getting project nature. Will get the rest though.");
                //ex.printStackTrace();
            }
        }
        return natureArray;
    }
    /**
     * Converts and Input Stream into a String Object (used for a number of parsers)
     * @param is
     * @return
     * @throws IOException
     */
    public static String getStringFromInputStream(InputStream is) throws IOException{
		int k;
		InputStream in = is;
		StringBuffer stringFromIS = new StringBuffer();
		int aBuffSize = 1123123;     		
		
		byte buff[] = new byte[aBuffSize]; 
		OutputStream xOutputStream = new ByteArrayOutputStream(aBuffSize); 
		while ( (k=in.read(buff) ) != -1){
			xOutputStream.write(buff,0,k);
		}
		stringFromIS.append(xOutputStream.toString()); 
		return stringFromIS.toString();
	 }

    /**
     * Checks whether a project has the supplied nature applied to it.
     * 
     * @param project2Check The project to check
     * @param natureID The ID of the nature to check for
     * 
     * @return true - nature applied, false - not yet
     * @throws CoreException 
     */
    public static boolean hasNature(IProject project2Check, String natureID)
    	throws CoreException
    {
        IProjectDescription description = project2Check.getDescription();
        String[] natures = description.getNatureIds();
        
        for(int i = 0; i < natures.length; i++)
        {
            if(natures[i].equals(natureID))
                return true;
        }
        return false;
    }

    /**
     * Applies the CFE nature to the supplied project.
     * 
     * If the nature is already applied it will return.
     * @param project2ApplyTo The project to apply the nature to.
     * @param natureID The ID of the nature to apply to the project
     * 
     * @throws CoreException
     */
    public static void applyNature(IProject project2ApplyTo, String natureID) 
    	throws CoreException
    {
        IProjectDescription description = project2ApplyTo.getDescription();
        String[] natures = description.getNatureIds();
        
        for(int i = 0; i < natures.length; i++)
        {
            if(natures[i].equals(natureID))
                return;
        }
        
        String[] newNatures = new String[natures.length + 1];
        System.arraycopy(natures, 0, newNatures, 0, natures.length);
        newNatures[natures.length] = natureID;
        description.setNatureIds(newNatures);
        project2ApplyTo.setDescription(description, null);
    }

    /**
     * Removes the CFE nature from a project.
     * If the project does not have the nature it simply returns.
     * @param project Project to remove the nature from.
     * @param natureID The ID of the nature to remove
     * 
     * @throws CoreException
     */
    public static void removeNature(IProject project, String natureID)
    	throws CoreException
    {
        IProjectDescription description = project.getDescription();
        String[] natures = description.getNatureIds();
        
        if(natures.length == 0)
            return;
        
        if(!hasNature(project, natureID))
            return;
        
        String[] newNatures = new String[natures.length - 1];
        
        for(int i = 0, newNaturePos = 0; i < natures.length; i++)
        {
            if(!natures[i].equals(natureID))
            {
                newNatures[newNaturePos] = natures[i];
                newNaturePos++;
            }
        }
        description.setNatureIds(newNatures);
        project.setDescription(description, null);
    }
    
    /**
     * Reaturns the path of a file or directory relative to a directory,
     * in native format.
     * @return The relative path.
     *     It never starts with separator char (/ on UN*X).
     * @throws IOException if the two paths has no common parent directory
     *   (such as <code>C:\foo.txt</code> and <code>D:\foo.txt</code>), or
     *   the the paths are malformed.
     */
    public static String getRelativePath(File fromDir, File toFileOrDir)
            throws IOException {
        char sep = File.separatorChar;
        String ofrom = fromDir.getCanonicalPath();
        String oto = toFileOrDir.getCanonicalPath();
        boolean needSepEndForDirs;
        if (!ofrom.endsWith(File.separator)) {
            ofrom += sep;
            needSepEndForDirs = false;
        } else {
            needSepEndForDirs = true;
        }
        boolean otoEndsWithSep;
        if (!oto.endsWith(File.separator)) {
            oto += sep;
            otoEndsWithSep = false;
        } else {
            otoEndsWithSep = true;
        }
        String from = ofrom.toLowerCase();
        String to = oto.toLowerCase();
        
        StringBuffer path = new StringBuffer(oto.length());

        int fromln = from.length();
        goback: while (true) {
            if (to.regionMatches(0, from, 0, fromln)) {
                File fromf = new File(ofrom.substring(
                        0, needSepEndForDirs ? fromln : fromln - 1));
                File tof = new File(oto.substring(
                        0, needSepEndForDirs ? fromln : fromln - 1));
                if (fromf.equals(tof)) {
                    break goback;
                }
            }
            path.append(".." + sep);
            fromln--;
            while (fromln > 0 && from.charAt(fromln - 1) != sep) {
                fromln--;
            }
            if (fromln == 0) {
                throw new IOException(
                        "Could not find common parent directory in these "
                        + "paths: " + ofrom + " and " + oto);
            }
        }
        path.append(oto.substring(fromln));
        if (!otoEndsWithSep && path.length() != 0) {
            path.setLength(path.length() - 1);
        }

        return path.toString();
    }
    
    //Sets and gets persistent Properties
    
    public static String getURL(IResource resource){
    	
    	String string = findRootURL(resource, "");
    	if(string.matches("(?i).*/Test.*.cfc|(?i).*Test.cfc")){
    		string += CFMLPlugin.getDefault().getPluginPreferences().getString(BrowserPreferenceConstants.P_TESTCASE_QUERYSTRING);
    	}
    	return string;
    	
    }
    
    //TODO: Make this figure out the path properly!!!
    
    
    private static String findRootURL(IResource resource, String offset){
    	if(resource instanceof IProject){
    		String projectURL = getPersistentProperty(resource, "", CFMLPreferenceConstants.P_PROJECT_URL);
    		
    		//need to check the url here.
    		if(projectURL.charAt(projectURL.length()-1) != '/'){
    			projectURL += "/";
    		}
    		return projectURL + offset;
    	}
    	else {
    		if(hasProperty(resource, "", CFMLPreferenceConstants.P_PROJECT_URL)){
    			 String folderURL = getPersistentProperty(resource, "", CFMLPreferenceConstants.P_PROJECT_URL);
    			return folderURL + offset;
    		}
    		else{
    			if(offset.length()!=0){
    				return findRootURL(resource.getParent(), resource.getName() + "/" + offset);
    			}
    			else{
    				return findRootURL(resource.getParent(), resource.getName());	
    			}
    		}
    	}
    }
    public static String getPersistentProperty(IResource resource, String qualifier, String name){
    	try {
			return resource.getPersistentProperty(new QualifiedName(qualifier , name));
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public static boolean hasProperty(IResource resource, String qualifier, String name){
    		String persistentProperty = getPersistentProperty(resource, qualifier, name);
    		if(persistentProperty == null || persistentProperty.trim().length() == 0){
    			return false;
    		}
    	return true;
    }
    
}
