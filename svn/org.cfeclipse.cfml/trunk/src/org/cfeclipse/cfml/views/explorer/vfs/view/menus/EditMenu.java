package org.cfeclipse.cfml.views.explorer.vfs.view.menus;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.util.Os;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;

import org.cfeclipse.cfml.util.CFPluginImages;
import org.cfeclipse.cfml.views.explorer.vfs.view.VFSUtil;
import org.cfeclipse.cfml.views.explorer.vfs.view.VFSView;

public class EditMenu {
	private static Clipboard clipboard;

	public static Clipboard getClipboard() {
		if (clipboard == null) {
			clipboard = new Clipboard(Display.getCurrent());
		}

		return clipboard;
	}

	public static class CopyAction extends Action {
		VFSView _window;

		public CopyAction(VFSView fsApplication, String text, String toolTip) {
			_window = fsApplication;

			ImageRegistry icons = VFSView.iconCache;

			setText(text);
			setToolTipText(toolTip);

			setImageDescriptor(icons.getDescriptor(CFPluginImages.ICON_COPY));

		}

		public void run() {
			VFSView theApp = (VFSView) _window;

			TableItem[] tableItems = theApp.getTable().getSelection();
			TreeItem[] treeItems = theApp.getTree().getSelection();

			// if no table items then use tree items
			boolean useTree = (tableItems.length == 0) ? true : false;
			int size = (useTree) ? treeItems.length : tableItems.length;

			// Comma separated list of file URIs to copy
			StringBuffer fileUris = new StringBuffer();

			for (int i = 0; i < size; i++) {
				/*
				 * In Order for FileTransfer to Work (under DragAndDrop), the
				 * name must begin w/ / in UNIX systems
				 */
				String prefix = Os.isFamily(Os.OS_FAMILY_UNIX) ? "/" : "";

				if (useTree) {
					fileUris.append(prefix + (String) treeItems[i].getData(VFSView.TREEITEMDATA_URI) + " ");
				} else {
					final FileObject fo = (FileObject) tableItems[i].getData(VFSView.TABLEITEMDATA_FILE);
					fileUris.append(prefix + fo.toString() + " ");
				}

			}

			TextTransfer text_transfer = TextTransfer.getInstance();

			try {
				getClipboard().setContents(new Object[] { fileUris.toString() }, new Transfer[] { text_transfer });

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Paste paths from clipboard
	 */
	public static class PasteAction extends Action {
		VFSView vfsView;

		public PasteAction(VFSView fsApplication, String text, String toolTip) {
			vfsView = fsApplication;

			setText(text);
			setToolTipText(toolTip);

			ImageRegistry icons = VFSView.iconCache;
			setImageDescriptor(icons.getDescriptor(CFPluginImages.ICON_PASTE));
		}

		public void run() {
			// Get the destination folder from tree
			VFSView fVFSView = vfsView;

			TableItem[] tableItems = fVFSView.getTable().getSelection();
			TreeItem[] treeItems = fVFSView.getTree().getSelection();
			// if no table items then use tree items
			boolean useTree = (tableItems.length == 0) ? true : false;
			String destUri = null;
			String destConnectionId = null;
			int size = (useTree) ? treeItems.length : tableItems.length;
			if (useTree) {
				destUri = (String) treeItems[0].getData(VFSView.TREEITEMDATA_URI);
				destConnectionId = (String) treeItems[0].getData(VFSView.TREEITEMDATA_CONNECTIONID);
			} else {
				final FileObject destFile = ((FileObject) tableItems[0].getData(VFSView.TABLEITEMDATA_FILE));
				try {
					destUri = destFile.getURL().toString();
				} catch (FileSystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				destConnectionId  = (String) tableItems[0].getData(VFSView.TABLEITEMDATA_CONNECTIONID);
			}

			// Source URIs from clipboard
			Clipboard clip = getClipboard();

			if (clip != null && destUri != null) {
				String tmp = (String) clip.getContents(TextTransfer.getInstance());
				String[] paths = tmp.split(" ");

				FileSystemManager fsManager = fVFSView.getFileSystemManager();

				try {
					// destination object
					FileObject targetFile = fVFSView.resolveURI(destUri, destConnectionId);

					VFSUtil.copyFiles(fsManager, paths, fVFSView.fConnections.get(fVFSView.fSourceConnectionId),
							targetFile, fVFSView.fConnections.get(destConnectionId));

					// refresh
					fVFSView.notifyRefreshFiles(new FileObject[] { targetFile });

				} catch (FileSystemException e) {
					VFSView.debug(e);
				}
			}
		}
	}

}
