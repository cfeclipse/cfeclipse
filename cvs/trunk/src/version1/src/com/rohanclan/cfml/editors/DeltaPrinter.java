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
		System.out.print("Resource ");
		System.out.print(res.getFullPath());
		
		if(res.getType() == IResource.FILE)
		{
			CFDocumentProvider.setLastFilename(res.getFullPath());
			CFDocumentProvider.setLastResource(res);
		}
		
		switch (delta.getKind()) 
		{
			case IResourceDelta.ADDED:
				System.out.println(" was added.");
				break;
			case IResourceDelta.REMOVED:
				System.out.println(" was removed.");
				break;
			case IResourceDelta.CHANGED:
				System.out.println(" has changed.");
				if(res.getType() == IResource.FILE)
				{
					//
					// Is a file! Somehow we need to get the IDocument for the changed
					// resource and then run the parser over it.
					System.out.println(" resource is a file!");
					IDocument tempDoc;
				}
				break;
			case IResourceDelta.OPEN:
				System.out.println("has been opened/closed(?)");
				break;
		}
		return true; // visit the children
	}
}