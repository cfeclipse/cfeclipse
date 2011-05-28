package org.cfeclipse.cfml.views.images;

import java.util.Collection;

import org.cfeclipse.cfml.images.IStoreImageListener;

public interface IImageStore {

	public void addListener(IStoreImageListener listener);

	public void removeListener(IStoreImageListener listener);

	public String getName();

	public Collection<?> getContents();
}
