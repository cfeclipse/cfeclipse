package com.rohanclan.cfml.editors;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

public class MyResourceChangeReporter implements IResourceChangeListener {
	public void resourceChanged(IResourceChangeEvent event) 
	{
		IResource res = event.getResource();
		System.out.println("####################\n#########################\n#####################");
		try {
			switch (event.getType()) {
				case IResourceChangeEvent.PRE_CLOSE:
					System.out.println(" is about to close.");
					break;
				case IResourceChangeEvent.PRE_DELETE:
					System.out.println(" is about to be deleted.");
					break;
				case IResourceChangeEvent.POST_CHANGE:
					System.out.println("Resources have changed.");
					event.getDelta().accept(new DeltaPrinter());
					break;
				case IResourceChangeEvent.PRE_AUTO_BUILD:
					System.out.println("Auto build about to run.");
					event.getDelta().accept(new DeltaPrinter());
					break;
			 	case IResourceChangeEvent.POST_AUTO_BUILD:
					System.out.println("Auto build complete.");
					event.getDelta().accept(new DeltaPrinter());
					break;
			}
		}catch(CoreException temp) {
			System.out.println("Caught a CoreException");
		}
	}
}