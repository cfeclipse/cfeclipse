package org.cfeclipse.cfml.views.images;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.cfeclipse.cfml.images.IImageEntry;
import org.cfeclipse.cfml.images.IImageEntryCallback;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class ImageCache {

	static LinkedHashMap<IImageEntry, Image> images = new LinkedHashMap<IImageEntry, Image>();

	static HashSet<IImageEntryCallback> callbacks = new HashSet<IImageEntryCallback>();

	static LinkedBlockingQueue<IImageEntry> loadedStack = new LinkedBlockingQueue<IImageEntry>();

	static int total = 0;

	static Thread imageLoader = new Thread() {

		public void run() {
			while (true) {
				IImageEntry image2;
				try {
					image2 = ImageCache.loadedStack.take();
					final long l0 = System.currentTimeMillis();
					long l1 = l0;
					do {
						try {
							final Image im = image2.getImage();
							ImageCache.total++;
							System.currentTimeMillis();
							synchronized (ImageCache.class) {
								try {

									ImageCache.images.put(image2, im);
									if (ImageCache.images.size() > 500) {
										final Image remove = ImageCache.images.remove(ImageCache.images.keySet()
												.iterator().next());
										remove.dispose();

									}

								} catch (final Exception e) {
									e.printStackTrace();
								}
							}
						} catch (Error e) {
							e.printStackTrace();
						}
						while (ImageCache.loadedStack.size() > 100) {
							ImageCache.loadedStack.remove();
						}
						image2 = ImageCache.loadedStack.poll();
						l1 = System.currentTimeMillis();
					} while ((((l1 - l0) < 10000) && (image2 != null)));
					ImageCache.notifyLoad();
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	};

	static {
		ImageCache.imageLoader.setPriority(Thread.MAX_PRIORITY);
		ImageCache.imageLoader.setDaemon(true);
		ImageCache.imageLoader.start();
	}

	public static void addCallback(IImageEntryCallback cb) {
		ImageCache.callbacks.add(cb);
	}

	public static void removeCallback(IImageEntryCallback cba) {
		ImageCache.callbacks.remove(cba);
	}

	public static Image getImage(final IImageEntry entry) throws IOException {
		synchronized (ImageCache.class) {
			final Image image = ImageCache.images.get(entry);
			if (image != null) {
				return image;
			}
		}
		ImageCache.loadedStack.add(entry);
		return entry.getImage();
		// return null;
	}

	public static void clearCache() {
		for (final Image m : ImageCache.images.values()) {
			m.dispose();
		}
		ImageCache.images.clear();
	}

	private static void notifyLoad() {
		Display.getDefault().asyncExec(new Runnable() {

			public void run() {
				for (final IImageEntryCallback c : ImageCache.callbacks) {
					c.imageLoaded();
				}
			}

		});
	}
}
