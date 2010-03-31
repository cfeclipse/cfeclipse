package org.cfeclipse.cfml.images;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.WeakHashMap;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class JarImageEntry implements IImageEntry {

	private final String file;
	private final String entryPath;
	private final String name;

	public JarImageEntry(String file, String entryPath, String name) {
		super();
		this.file = file;
		this.entryPath = entryPath;
		this.name = name;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.entryPath == null) ? 0 : this.entryPath.hashCode());
		result = prime * result + ((this.file == null) ? 0 : this.file.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final JarImageEntry other = (JarImageEntry) obj;
		if (this.entryPath == null) {
			if (other.entryPath != null) {
				return false;
			}
		} else if (!this.entryPath.equals(other.entryPath)) {
			return false;
		}
		if (this.file == null) {
			if (other.file != null) {
				return false;
			}
		} else if (!this.file.equals(other.file)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	static WeakHashMap<String, JarFile> files = new WeakHashMap<String, JarFile>();

	public Image getImage() throws IOException {
		JarFile file = JarImageEntry.files.get(this.file);
		if (file == null) {
			file = new JarFile(new File(this.file));
			JarImageEntry.files.put(this.file, file);
		}
		final ZipEntry entry = file.getJarEntry(this.entryPath);
		final InputStream inputStream = file.getInputStream(entry);
		final Image image = new Image(Display.getDefault(), inputStream);
		inputStream.close();
		return image;
	}

	public String getName() {
		return this.name;
	}

	public String getFile() {
		try {
			final File file2 = CFMLPlugin.getDefault().getStateLocation().toFile();
			final File createTempFile = new File(file2, this.name);
			final JarFile file = new JarFile(new File(this.file));
			final ZipEntry entry = file.getJarEntry(this.entryPath);
			final InputStream inputStream = file.getInputStream(entry);
			final FileOutputStream st = new FileOutputStream(createTempFile);
			final BufferedInputStream sa = new BufferedInputStream(inputStream);
			while (sa.available() >= 0) {
				final int read = sa.read();
				if (read == -1) {
					break;
				}
				st.write(read);
			}
			st.close();

			file.close();
			createTempFile.deleteOnExit();
			return createTempFile.getAbsolutePath();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getPath() {
		if (this.entryPath.length() > 0) {
			if (this.entryPath.charAt(0) == '/') {
				return this.entryPath.substring(1);
			}
		}
		return this.entryPath;
	}

	public InputStream getStream() throws IOException {
		final JarFile file = new JarFile(new File(this.file));
		final ZipEntry entry = file.getJarEntry(this.entryPath);
		final InputStream inputStream = file.getInputStream(entry);
		return inputStream;
	}
}
