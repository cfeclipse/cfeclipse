package org.cfeclipse.cfml.images;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.views.images.IImageStore;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

public abstract class AbstractWorskspaceImageEntry implements IImageStore {

	HashSet<IStoreImageListener> images = new HashSet<IStoreImageListener>();
	protected ArrayList<Object> imageList = new ArrayList<Object>();

	public AbstractWorskspaceImageEntry() {
		super();
	}

	public synchronized Collection<?> getContents() {
		return new ArrayList<Object>(this.imageList);
	}

	public void init() {
		final Thread s = new Thread() {
			public void run() {
				AbstractWorskspaceImageEntry.this.initPlatform();
			}
		};
		s.start();
	}

	public synchronized void reprocess(HashSet<String> str) {
		final IContainer file = this.getFile();
		final HashSet<ItemGroup> toRemove = new HashSet<ItemGroup>();
		final HashSet<ItemGroup> toAdd = new HashSet<ItemGroup>();
		for (final Object s : this.imageList) {
			final ItemGroup z = (ItemGroup) s;
			if (str.contains(z.getName())) {
				toRemove.add(z);
				str.remove(z.getName());
				final ArrayList<IImageEntry> za = new ArrayList<IImageEntry>();
				final ItemGroup newF = new ItemGroup(z.getName(), za);
				final IResource file2 = ((IWorkspaceRoot) file).getProject(z.getName());
				this.parse(za, file2, file2);
				if (!za.isEmpty()) {
					toAdd.add(newF);
				}
			}
		}
		for (final String s : str) {
			final ArrayList<IImageEntry> za = new ArrayList<IImageEntry>();
			final ItemGroup newF = new ItemGroup(s, za);
			final IResource file2 = ((IWorkspaceRoot) file).getProject(s);
			this.parse(za, file2, file2);
			if (!za.isEmpty()) {
				toAdd.add(newF);
			}
		}
		this.imageList.removeAll(toRemove);
		this.imageList.addAll(toAdd);
		this.fireChanged();
	}

	public abstract IContainer getFile();

	protected void initPlatform() {
		this.imageList.clear();
		this.fireChanged();
		final IContainer file = this.getFile();
		if ((file != null) && file.exists()) {
			try {
				final IResource[] listFiles = file.members();
				for (final IResource f : listFiles) {
					if (f.getName().charAt(0) == '.') {
						continue;
					}
					final ArrayList<IImageEntry> z = new ArrayList<IImageEntry>();
					final ItemGroup group = new ItemGroup(f.getName(), z);
					if (f instanceof IProject && ((IProject) f).isOpen()) {
						this.parse(z, f, f);
						if (group.getChildCount() > 0) {
							this.fetched(group);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.fireChanged();
	}

	private synchronized void fetched(ItemGroup group) {
		this.imageList.add(group);
		this.fireChanged();
	}

	private synchronized void fireChanged() {
		for (final IStoreImageListener l : this.images) {
			l.platformChanged();
		}
	}

	@SuppressWarnings("unchecked")
	private void parse(ArrayList ls, IResource f, IResource root) {
		if (f instanceof IContainer) {
			if (f.getName().charAt(0) != '.') {
				IResource[] listFiles;
				try {
					listFiles = ((IContainer) f).members();
					for (final IResource fa : listFiles) {
						if (fa.getProject().isOpen()) {
							this.parse(ls, fa, root);
						}
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}

			}
		} else if (AbstractWorskspaceImageEntry.isImage(f.getName())) {
			ls.add(new IFileImageEntry((IFile) f));
		}
	}

	public static boolean isImage(String name) {
		return name.endsWith(".gif") || name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".bmp")
				|| name.endsWith(".jpeg") || name.endsWith(".tiff");
	}

	public synchronized void addListener(IStoreImageListener e) {
		this.images.add(e);
	}

	public synchronized void removeListener(IStoreImageListener e) {
		this.images.remove(e);
	}

}