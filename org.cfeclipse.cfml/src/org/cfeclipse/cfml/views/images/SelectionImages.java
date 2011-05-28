package org.cfeclipse.cfml.views.images;

import java.util.HashSet;

import org.cfeclipse.cfml.images.AbstractWorskspaceImageEntry;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

public class SelectionImages extends AbstractWorskspaceImageEntry {

	private static SelectionImages instance;

	public String getName() {
		return "Workspace";
	}

	private SelectionImages() {
		this.init();

		ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {

			public void resourceChanged(IResourceChangeEvent event) {
				final HashSet<String> str = new HashSet<String>();
				try {
					event.getDelta().accept(new IResourceDeltaVisitor() {

						public boolean visit(IResourceDelta delta) throws CoreException {
							final IResource resource = delta.getResource();
							if (resource instanceof IProject) {
								str.add(resource.getName());
								return false;
							}
							return true;
						}

					});
				} catch (final CoreException e) {
					e.printStackTrace();
				}
				SelectionImages.this.reprocess(str);
			}

		});
	}

	public static SelectionImages getSelectionImages() {
		if (SelectionImages.instance != null) {
			return SelectionImages.instance;
		}
		final SelectionImages selectionImages = new SelectionImages();
		SelectionImages.instance = selectionImages;
		return selectionImages;
	}

	public IContainer getFile() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

}
