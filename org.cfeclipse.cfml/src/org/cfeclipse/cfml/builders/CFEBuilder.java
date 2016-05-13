package org.cfeclipse.cfml.builders;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.parser.CFLintPlugin;
import org.cfeclipse.cfml.preferences.CFLintPreferenceConstants;
import org.cfeclipse.cfml.preferences.ParserPreferenceConstants;
import org.cfeclipse.cfml.properties.CFMLPropertyManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;

import com.cflint.BugInfo;
import com.cflint.BugList;
import com.cflint.CFLint;
import com.cflint.config.CFLintConfig;
import com.cflint.plugins.CFLintScanner;
import com.cflint.tools.CFLintFilter;

/**
 * 
 * CFEclipse project builder
 * 
 * @author Denny Valliant
 */
public class CFEBuilder extends IncrementalProjectBuilder {

	CFLintConfig config = null;
	CFLint cflint;
	CFMLPropertyManager propertyManager = new CFMLPropertyManager();

	protected IProject[] build(int kind, Map args, IProgressMonitor monitor) {
		if (!propertyManager.getCFLintEnabledProject(getProject())) {
			try {
				IResourceDelta delta = getDelta(getProject());
				delta.getResource().deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_ONE);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			return null;
		}

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

	private void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) {
		System.out.println("incremental build on " + delta);
		try {
			delta.accept(new IResourceDeltaVisitor() {
				public boolean visit(IResourceDelta delta) {
					getResourceBuildMarkers(delta.getResource());
					return true; // visit children too
				}
			});
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void fullBuild(IProgressMonitor monitor) {
		try {
			getProject().accept(new CFMLBuildVisitor());
		} catch (CoreException e) {
		}
	}

	private void getResourceBuildMarkers(IResource res) {
		if (!res.isAccessible() || !res.exists())
			return;
		if (res.getRawLocation() == null || res.getRawLocation().toFile().isDirectory())
			return;
		if (!res.getRawLocation().toFile().getAbsolutePath().endsWith(".cfm")
				&& !res.getRawLocation().toFile().getAbsolutePath().endsWith(".cfc"))
			return;
		try {
			res.deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_ONE);
//			res.deleteMarkers(null, true, IResource.DEPTH_ONE);
			if (cflint == null) {
				cflint = new CFLint(config);
				cflint.setVerbose(true);
				cflint.setLogError(true);
				cflint.setQuiet(false);
				cflint.setShowProgress(false);
				cflint.setProgressUsesThread(true);
				CFLintFilter filter = CFLintFilter.createFilter(true);
				cflint.getBugs().setFilter(filter);
				// List<CFLintScanner> scanners = cflint.getScanners();
				// cflint.addScanner(new CFLintPlugin());
			}
			cflint.getBugs().getBugList().clear();
			File sourceFile = res.getRawLocation().makeAbsolute().toFile();
			System.out.println("Scanning " + sourceFile.getAbsolutePath());
			cflint.scan(sourceFile);
			BugList bugList = cflint.getBugs();
			for (BugInfo bugInfo : bugList) {
				addMarker(res, bugInfo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class CFMLBuildVisitor implements IResourceVisitor {
		public boolean visit(IResource res) {
			getResourceBuildMarkers(res);
			// build the specified resource.
			// return true to continue visiting children.
			return true;
		}
	}

	private static final String MARKER_TYPE = "org.cfeclipse.cfml.cflintmarker";

	private void addMarker(IResource res, BugInfo bug) {
		try {
			IMarker marker = res.createMarker(MARKER_TYPE);
			int lineNumber = bug.getLine();
			marker.setAttribute(IMarker.MESSAGE, bug.getSeverity() + ": " + bug.getMessage());
			if (bug.getSeverity().equals("WARNING")) {
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
			}
			else if (bug.getSeverity().equals("INFO")) {
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
			} else {
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			}
			if (lineNumber == -1) {
				lineNumber = 1;
			}
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
