package org.cfeclipse.cfml.images;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class IFileImageEntry implements IImageEntry {

	private final IFile file;

	private final String name;

	public IFileImageEntry(IFile file) {
		super();
		this.name = file.getName();
		this.file = file;
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
		final IFileImageEntry other = (IFileImageEntry) obj;
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
		try {
			return this.file.getContents();
		} catch (CoreException e) {
			throw new IOException(e.getStackTrace().toString());
		}
	}

	public Image getImage() throws IOException {
		InputStream stream = this.getStream();
		try {
			return new Image(Display.getDefault(), stream);
		} finally {
			stream.close();
		}
	}

	public String getName() {
		return this.name;
	}

	public String getFile() {
		return this.file.getFullPath().toPortableString();
	}

	public String getPath() {
		return this.file.getProjectRelativePath().toPortableString();
	}
}
