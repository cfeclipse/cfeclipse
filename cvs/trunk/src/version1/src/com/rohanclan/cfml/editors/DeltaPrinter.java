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