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

import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.rohanclan.cfml.dictionary.Value;

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
		TreeSet suggestions = new TreeSet();
		IPath folder;
		IFolder folderRes;
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
		folder = res.getFullPath().removeLastSegments(1).append(pathSoFar);
		folderRes = res.getWorkspace().getRoot().getFolder(folder);
	
		if(folderRes == null) {
		} else {
			try {
				IResource children[] = folderRes.members();
				for(int i = 0; i < children.length; i++) {
					if(!children[i].getName().toLowerCase().startsWith(postPath.toLowerCase()))
						continue;
					
					if(children[i] instanceof IFolder) {
						suggestions.add(new Value(children[i].getName() + "/"));
					} else if(children[i] instanceof IFile) {
						suggestions.add(new Value(children[i].getName()));
					}
				}
			}catch(CoreException ex) {
				// Fail gracefully as this function should not be called in a critical situation.
				ex.printStackTrace();
			}
		}
		return suggestions;
	}
}
