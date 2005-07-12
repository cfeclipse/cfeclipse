/*
 * Created on Feb 2, 2004
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
package com.rohanclan.cfml.editors;

import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResource;

/**
 * 
 * @author Oliver Tupman
 *
 * This DeltaPrinter is a left over from the old method of obtaining an
 * opened CF document's IResource. I've left this in in case we discover
 * it could be useful elsewhere (like saving a file and then calling the
 * parser)
 */
class DeltaPrinter implements IResourceDeltaVisitor 
{
	public boolean visit(IResourceDelta delta) 
	{
		IResource res = delta.getResource();

		switch (delta.getKind()) 
		{
			case IResourceDelta.ADDED:
				break;
			case IResourceDelta.REMOVED:
				break;
			case IResourceDelta.CHANGED:
/*				
 				//
 				// This probably shouldn't all go here, but it's a good reference
 				// on how to get the data from a resource. 
			//System.out.println("Resource has been changed.");
				if(res.getType() == IResource.FILE)
				{
				//System.out.println("Resource is a file");
					//
					// Need to somehow get the data from the file!
					IFile tempFile = (IFile)res;
					IPath location = res.getLocation();
					try {
					//System.out.println("Getting resource contents...");
						BufferedInputStream inStr = new BufferedInputStream(tempFile.getContents(true));
					//System.out.println("Got contents");
						try {
							char [] inData;
							inData = Util.getInputStreamAsCharArray(inStr, -1, null);
							
							String temp = new String(inData);
							CFParser parser = new CFParser(temp, tempFile);
							parser.parseDoc();
						} catch(IOException ioExcep) {
							System.err.println("Caught IO Exception: \'" + ioExcep.getMessage() + "\'");
						//System.out.println("Caught IO Exception: \'" + ioExcep.getMessage() + "\'");
						}
					} catch(CoreException excep) {
						System.err.println("Caught core exception \'" + excep.getMessage() + "\'");
					}
					
				}
*/				
				break;
			case IResourceDelta.OPEN:
				break;
		}
		return true; // visit the children
	}
}