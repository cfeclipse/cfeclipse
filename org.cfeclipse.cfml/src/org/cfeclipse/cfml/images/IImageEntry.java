package org.cfeclipse.cfml.images;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.graphics.Image;

public interface IImageEntry {

	public Image getImage() throws IOException;

	public InputStream getStream() throws IOException;

	public String getName();

	public String getFile();

	public String getPath();
}
