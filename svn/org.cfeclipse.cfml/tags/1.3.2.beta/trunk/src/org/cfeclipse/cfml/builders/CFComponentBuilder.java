package org.cfeclipse.cfml.builders;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class CFComponentBuilder extends IncrementalProjectBuilder {

	protected IProject[] build(int kind, Map args, 
		       IProgressMonitor monitor) {
		         if (kind == IncrementalProjectBuilder.FULL_BUILD) {
		            fullBuild(monitor);
		         } else {
		            IResourceDelta delta = getDelta(getProject());
		            if (delta == null) {
		               fullBuild(monitor);
		            } else {
		               incrementalBuild(delta, monitor);
		            }
		         }
		         return null;
		     }   
		      private void incrementalBuild(IResourceDelta delta, 
		       IProgressMonitor monitor) {
		         System.out.println("incremental build on "+delta);
		         try {
		            delta.accept(new IResourceDeltaVisitor() {
		               public boolean visit(IResourceDelta delta) {
		                  System.out.println("changed: "+
		                     delta.getResource().getRawLocation());
		                  return true; // visit children too
		               }
		            });
		         } catch (CoreException e) {
		            e.printStackTrace();
		         }
		      }
		      private void fullBuild(IProgressMonitor monitor) {
		         System.out.println("full build");
		      }
}


