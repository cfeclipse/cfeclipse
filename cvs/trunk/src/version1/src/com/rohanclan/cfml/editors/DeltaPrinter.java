package com.rohanclan.cfml.editors;

import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResource;

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
				break;
			case IResourceDelta.OPEN:
				System.out.println("has been opened/closed(?)");
				break;
		}
		return true; // visit the children
	}
}