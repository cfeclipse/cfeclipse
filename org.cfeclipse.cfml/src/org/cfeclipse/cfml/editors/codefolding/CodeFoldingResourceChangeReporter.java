package org.cfeclipse.cfml.editors.codefolding;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

public class CodeFoldingResourceChangeReporter implements IResourceChangeListener {

	public void resourceChanged(IResourceChangeEvent event) {
		try {
			if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
				IResourceDelta changes = event.getDelta();
				changes.accept(new IResourceDeltaVisitor() {
					public boolean visit(IResourceDelta change) {
						if (change.getResource().getType() == IResource.FILE) {
							// rename the fold state file
							if (change.getMovedToPath() != null) {
								File foldFile = CodeFoldingSetter.getFoldStateFile((IFile) change.getResource());
								if (foldFile.exists()) {
									String newName = change.getMovedToPath().toFile().getName() + "_folds";
									String oldName = foldFile.toPath().toFile().getName();
									String oldPath = foldFile.toPath().toFile().toString();
									String newPath = oldPath.replace(oldName, newName);
									foldFile.toPath().toFile().renameTo(new File(newPath));
								}
							}
							if (change.getKind() == IResourceDelta.REMOVED && change.getMovedToPath() == null
									&& change.getMovedFromPath() == null) {
								File foldFile = CodeFoldingSetter.getFoldStateFile((IFile) change.getResource());
								if (foldFile.exists()) {
									foldFile.delete();
								}
							}
						}
						;
						return true;
					}
				});
			}

		} catch (CoreException temp) {
			System.err.println("Caught a CoreException");
		}
	}
}