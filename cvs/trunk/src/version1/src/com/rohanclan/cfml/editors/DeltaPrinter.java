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
import org.eclipse.jface.text.IDocument;

class DeltaPrinter implements IResourceDeltaVisitor 
{
	public boolean visit(IResourceDelta delta) 
	{
		IResource res = delta.getResource();
	
		if(res.getType() == IResource.FILE)
		{
			CFDocumentProvider.setLastFilename(res.getFullPath());
			CFDocumentProvider.setLastResource(res);
		}
		
		switch (delta.getKind()) 
		{
			case IResourceDelta.ADDED:
				break;
			case IResourceDelta.REMOVED:
				break;
			case IResourceDelta.CHANGED:
				if(res.getType() == IResource.FILE)
				{
					//
					// Is a file! Somehow we need to get the IDocument for the changed
					// resource and then run the parser over it.
					IDocument tempDoc;
				}
				break;
			case IResourceDelta.OPEN:
				break;
		}
		return true; // visit the children
	}
}