package org.cfeclipse.cfml.images;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.cfeclipse.cfml.views.images.IImageStore;

public abstract class AbstractImageEntry implements IImageStore {

	HashSet<IStoreImageListener> images = new HashSet<IStoreImageListener>();
	protected ArrayList<Object> imageList = new ArrayList<Object>();

	public AbstractImageEntry() {
		super();
	}

	public synchronized Collection<?> getContents() {
		return new ArrayList<Object>(this.imageList);
	}

	public void init() {
		final Thread s = new Thread() {
			public void run() {
				AbstractImageEntry.this.initPlatform();
			}
		};
		s.start();
	}

	synchronized void reprocess(HashSet<String> str) {
		final File file = this.getFile();
		final HashSet<ItemGroup> toRemove = new HashSet<ItemGroup>();
		final HashSet<ItemGroup> toAdd = new HashSet<ItemGroup>();
		for (final Object s : this.imageList) {
			final ItemGroup z = (ItemGroup) s;
			if (str.contains(z.getName())) {
				toRemove.add(z);
				str.remove(z.getName());
				final ArrayList<IImageEntry> za = new ArrayList<IImageEntry>();
				final ItemGroup newF = new ItemGroup(z.getName(), za);
				final File file2 = new File(file, z.getName());
				this.parse(za, file2, file2);
				if (!za.isEmpty()) {
					toAdd.add(newF);
				}
			}
		}
		for (final String s : str) {
			final ArrayList<IImageEntry> za = new ArrayList<IImageEntry>();
			final ItemGroup newF = new ItemGroup(s, za);
			final File file2 = new File(file, s);
			this.parse(za, file2, file2);
			if (!za.isEmpty()) {
				toAdd.add(newF);
			}
		}
		this.imageList.removeAll(toRemove);
		this.imageList.addAll(toAdd);
		this.fireChanged();
	}

	public abstract File getFile();

	protected void initPlatform() {
		this.imageList.clear();
		this.fireChanged();
		final File file = this.getFile();
		if ((file != null) && file.exists()) {
			final File[] listFiles = file.listFiles();
			for (final File f : listFiles) {
				if (f.getName().charAt(0) == '.') {
					continue;
				}
				// System.out.println(f);
				final ArrayList<IImageEntry> z = new ArrayList<IImageEntry>();
				final ItemGroup group = new ItemGroup(f.getName(), z);
				this.parse(z, f, f);
				if (group.getChildCount() > 0) {
					this.fetched(group);
				}
			}
		}
		this.fireChanged();
	}

	protected synchronized void fetched(ItemGroup group) {
		this.imageList.add(group);
		this.fireChanged();
	}

	protected synchronized void fireChanged() {
		for (final IStoreImageListener l : this.images) {
			l.platformChanged();
		}
	}

	@SuppressWarnings("unchecked")
	protected void parse(ArrayList ls, File f, File root) {
		if (f.getName().endsWith(".jar")) {
			try {
				final JarFile jar = new JarFile(f);
				try {
					this.process(ls, jar, f);
				} finally {
					jar.close();
				}
			} catch (final IOException e) {
			}
		} else if (f.isDirectory()) {
			if (f.getName().charAt(0) != '.') {
				final File[] listFiles = f.listFiles();
				for (final File fa : listFiles) {
					this.parse(ls, fa, root);
				}
			}
		} else if (AbstractImageEntry.isImage(f.getName())) {
			final int length = root.getAbsolutePath().length() + 1;
			String absolutePath = f.getAbsolutePath();
			absolutePath = absolutePath.replace('\\', '/');
			ls.add(new FileImageEntry(absolutePath, f.getName(), absolutePath.substring(length)));
		}
	}

	private void process(ArrayList<Object> ls, JarFile jar, File f) {
		final Enumeration<JarEntry> entries = jar.entries();
		while (entries.hasMoreElements()) {
			final JarEntry e = entries.nextElement();
			final String name2 = e.getName();
			final String name = name2;
			if (AbstractImageEntry.isImage(name)) {
				String nm = name2;
				final int lastIndexOf = nm.lastIndexOf('/');
				if (lastIndexOf > -1) {
					nm = nm.substring(lastIndexOf + 1);
				}
				final String substring = nm;
				ls.add(new JarImageEntry(f.getAbsolutePath(), name2, substring));
			}
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