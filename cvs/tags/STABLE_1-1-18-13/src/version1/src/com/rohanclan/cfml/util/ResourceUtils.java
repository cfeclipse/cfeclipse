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
package com.rohanclan.cfml.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.internal.utils.Assert;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

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
        Assert.isNotNull(srcProject,"ResourceUtils::getProjectNatures()");
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
}
