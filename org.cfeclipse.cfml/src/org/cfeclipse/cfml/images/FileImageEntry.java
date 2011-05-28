package org.cfeclipse.cfml.images;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class FileImageEntry implements IImageEntry {

	private final String file;
	private final String relativePath;
	private final String name;

	public FileImageEntry(String file, String name, String relativePath) {
		super();
		this.file = file;
		this.name = name;
		this.relativePath = relativePath;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		final FileImageEntry other = (FileImageEntry) obj;
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

	public InputStream getStream() throws IOException {
		return new FileInputStream(this.file);
	}

	public Image getImage() throws IOException {
		return new Image(Display.getDefault(), this.getStream());
	}

	public String getName() {
		return this.name;
	}

	public String getFile() {
		return this.file;
	}

	public String getPath() {
		return this.relativePath;
	}
}
