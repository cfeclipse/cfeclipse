package org.cfeclipse.cfml.editors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.Assert;

public class CFJavaFileStorage implements IStorage {

	private File file;

	private IPath path;

	private String name;

	public CFJavaFileStorage(File file) {
		Assert.isNotNull(file);
		this.file = file;
		path = Path.fromOSString(file.getAbsolutePath());
		name = file.getName();
	}

	public InputStream getContents() throws CoreException {
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException ex) {
			throw new CoreException(new Status(IStatus.ERROR,
					"org.cfeclipse.cfml", IStatus.ERROR,
					"Unable to open input stream for CF Eclipse file", ex));
		}
		return fis;
	}

	public IPath getFullPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	public boolean isReadOnly() {
		return !file.canWrite();
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

}
