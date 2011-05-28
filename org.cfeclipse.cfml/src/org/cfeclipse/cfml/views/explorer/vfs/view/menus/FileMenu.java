package org.cfeclipse.cfml.views.explorer.vfs.view.menus;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.PreferencesUtil;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.cfeclipse.cfml.net.FTPConnectionProperties;
import org.cfeclipse.cfml.preferences.FtpConnectionDialog;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.cfeclipse.cfml.views.explorer.vfs.view.VFSUtil;
import org.cfeclipse.cfml.views.explorer.vfs.view.VFSView;

public class FileMenu {

	public static class ConnectionAction extends Action {
		VFSView _window;

		public ConnectionAction(VFSView window, String text, String toolTip) {
			_window = window;
			setText(text);
			setToolTipText(toolTip);
			ImageRegistry icons = VFSView.iconCache;
			setImageDescriptor(icons.getDescriptor(CFPluginImages.ICON_DRIVE));
		}

		public void run() {
			PreferenceDialog pref = PreferencesUtil.createPreferenceDialogOn(_window.getSite().getShell(),
					"org.cfeclipse.cfml.preferences.FtpConnectionDialog",
					new String[] { "org.cfeclipse.cfml.preferences.FtpConnectionDialog" }, null);
			if (pref != null) {
				pref.setBlockOnOpen(true);
				FtpConnectionDialog conp = (FtpConnectionDialog) pref.getSelectedPage();
				TreeItem[] treeItems =_window.getTree().getSelection();
				if(treeItems.length == 1) {
					String connectionId = (String) treeItems[0].getData(VFSView.TREEITEMDATA_CONNECTIONID);
					conp.setCurrentConnectionId(connectionId);
				}
				pref.open();
				_window.treeRefreshRemoteURIs();
			}
		}
	}

	public static class NewFolderAction extends Action {
		VFSView _window;

		public NewFolderAction(VFSView window, String text, String toolTip) {
			_window = window;
			setText(text);
			setToolTipText(toolTip);
			ImageRegistry icons = VFSView.iconCache;
			setImageDescriptor(icons.getDescriptor(CFPluginImages.ICON_FOLDER_NEW));
		}

		public void run() {
			VFSUtil.newFolder(_window.getCurrentDirectory());
			try {
				_window.notifySelectedDirectory(_window.getCurrentDirectory(),_window.getCurrentConnectionId());
				_window.notifyRefreshFiles(new FileObject[]{_window.getCurrentDirectory()});
			} catch (FileSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static class NewFileAction extends Action {
		VFSView fVFSView;

		public NewFileAction(VFSView window, String text, String toolTip) {
			fVFSView = window;
			setText(text);
			setToolTipText(toolTip);
			ImageRegistry icons = VFSView.iconCache;
			setImageDescriptor(icons.getDescriptor(CFPluginImages.ICON_FILE));
		}

		public void run() {
			TreeItem[] treeItems = fVFSView.getTree().getSelection();
			VFSUtil.newFile(fVFSView.getCurrentDirectory());
			try {
				fVFSView.notifyRefreshFiles(new FileObject[]{fVFSView.getCurrentDirectory()});
				fVFSView.getTree().setSelection(treeItems);
			} catch (FileSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static class DeleteAction extends Action {
		VFSView _window;

		public DeleteAction(VFSView window, String text, String toolTip) {
			_window = window;
			setText(text);
			setToolTipText(toolTip);
			ImageRegistry icons = VFSView.iconCache;
			setImageDescriptor(icons.getDescriptor(CFPluginImages.ICON_DELETE));
		}

		public void run() {
			String currentConnectionId = _window.getCurrentConnectionId();
			MessageBox confirm = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_QUESTION | SWT.YES
					| SWT.NO);
			confirm.setMessage("Are you sure you want to delete the " + currentConnectionId + " connection?");
			if (confirm.open() == SWT.YES) {
				FTPConnectionProperties.deleteConnection(currentConnectionId);
				_window.treeRefreshRemoteURIs();
			}
		}

		public void run(TreeItem item) {
			String currentConnectionId = _window.getCurrentConnectionId();
			MessageBox confirm = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_QUESTION | SWT.YES
					| SWT.NO);
			confirm.setMessage("Are you sure you want to delete the " + currentConnectionId + " connection?");
			if (confirm.open() == SWT.YES) {
				FTPConnectionProperties.deleteConnection(currentConnectionId);
				_window.treeRefreshRemoteURIs();
				item.dispose();
			}
		}
	}

	void handleClientRequest(String theRequestParam) {
		try {
			String requestParam = theRequestParam;
			if (requestParam == null || requestParam.trim().length() == 0)
				return;
//if (lastRequest.equals(requestParam)) {
//	CFECallPlugin.log( IStatus.INFO, "same request again... " + theRequestParam );
//	return;
//}
//lastRequest = requestParam;

			requestParam = requestParam.replace('?', '|');

			int idx = requestParam.indexOf("://");
			if (idx != -1) {
				boolean removeUrl = true;
				String start = theRequestParam.substring(0, idx);
				for (int i = 0; i < start.length(); i++) {
					if (!Character.isLetter(start.charAt(i))) {
						removeUrl = false;
						break;
					}
				}
				if (removeUrl) {
					requestParam = URLDecoder.decode(requestParam.substring(idx + 3), "UTF-8");
				}
			}
			if (File.separator.equals("/")) {
				requestParam = requestParam.replaceAll("\\\\", "\\/");
			} else {
				requestParam = requestParam.replaceAll("\\/", "\\\\");
			}

			final String request = requestParam;

			final String[] parts = request.split("\\|");
			final int row[] = { 0 };
			final int col[] = { 0, 0 };
			if (parts.length > 1) {
				row[0] = Integer.parseInt(parts[1]);
				if (parts.length > 2) {
					String strCols[] = parts[2].split("\\-");
					for (int i = 0; i < strCols.length; i++) {
						col[i] = Integer.parseInt(strCols[i]);
					}
				}
			}

			boolean focus = false;
			if (this.openHandlers != null) {
				for (int i = 0; i < this.openHandlers.size(); i++) {
					IHandleOpenRequest handler = (IHandleOpenRequest) this.openHandlers.get(i);
					handler.handleOpenRequest(theRequestParam, parts[0], row[0], col, focus);
				}
			}
		} catch (Throwable T) {
			T.printStackTrace();
		}
	}
	
	private List openHandlers = null;

	private void initOpenHandler() {
		if (this.openHandlers == null) {
			boolean addNew = false;
			synchronized (this) {
				if (this.openHandlers == null) {
					this.openHandlers = new ArrayList();
					addNew = true;
				}
			}
			if (addNew) {
				IExtensionRegistry reg = Platform.getExtensionRegistry();
				IExtensionPoint extensionPoint = reg.getExtensionPoint("org.cfeclipse.cfeclipsecall.core.openhandler");
				IExtension extensions[] = extensionPoint.getExtensions();
				for (int i = 0; i < extensions.length; i++) {
					IExtension ext = extensions[i];
					IConfigurationElement[] elements = ext.getConfigurationElements();
					for (int j = 0; j < elements.length; j++) {
						IConfigurationElement el = elements[j];
						String clz = el.getAttribute("handlerClass");
						if (clz != null) {
							try {
								IHandleOpenRequest handleOpenRequest = (IHandleOpenRequest) Class.forName(clz).newInstance();
								this.openHandlers.add(handleOpenRequest);
							} catch (Throwable T) {
								T.printStackTrace();
							}
						} 
					}
				}
			}
		}
	}
	
}
