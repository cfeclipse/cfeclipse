package org.cfeclipse.cfml.images;

import java.util.Collection;

public interface IContentsCallback {

	public void contentsFetched(Collection<?> entries);

	public void contentsRemoved(Collection<?> entries);
}
