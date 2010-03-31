package org.cfeclipse.cfml.images;

import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class PasteImageAction implements IObjectActionDelegate {

	private ISelection selection;

	private static final String[] FILE_TYPES = new String[] { "JPG", "GIF", "PNG", "TIFF" };
	private static final int[] FILE_TYPE_CODES = new int[] { SWT.IMAGE_JPEG, SWT.IMAGE_GIF, SWT.IMAGE_PNG,
			SWT.IMAGE_TIFF };

	private final class NameEndExtensionDialog extends InputDialog {

		private NameEndExtensionDialog(Shell parentShell) {
			super(parentShell, "Please specify file name and format", "Please specify file name:", "",
					new IInputValidator() {

						public String isValid(String newText) {
							return null;
						}

					});

		}

		int selection;

		protected Control createDialogArea(Composite parent) {
			final Composite createDialogArea = (Composite) super.createDialogArea(parent);
			final Control[] children = createDialogArea.getChildren();
			for (final Control c : children) {
				c.setLayoutData(new GridData());
			}
			final GridLayout layout = (GridLayout) createDialogArea.getLayout();
			layout.numColumns += 4;
			final Label l = new Label(createDialogArea, SWT.NONE);
			l.setText("Format:");
			final Combo m = new Combo(createDialogArea, SWT.READ_ONLY | SWT.BORDER);
			m.setItems(PasteImageAction.FILE_TYPES);
			m.addSelectionListener(new SelectionListener() {

				public void widgetDefaultSelected(SelectionEvent e) {

				}

				public void widgetSelected(SelectionEvent e) {
					NameEndExtensionDialog.this.selection = m.getSelectionIndex();
				}

			});
			m.select(3);
			return createDialogArea;
		}

	}

	private Clipboard fClipboard;

	public void run(IAction action) {
		if (!ImageTransferWrapper.isAvalable()) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Paste Image action requires Eclipse 3.4",
					"Paste Image action requires Eclipse 3.4");
			return;
		}
		Clipboard clipboard;
		if (this.fClipboard != null) {
			clipboard = this.fClipboard;
		} else {
			clipboard = new Clipboard(Display.getDefault());
		}
		try {
			final TransferData[] availableTypes = clipboard.getAvailableTypes();
			final ArrayList<TransferData> supportedData = new ArrayList<TransferData>();
			for (final TransferData d : availableTypes) {
				final boolean supportedType = ImageTransferWrapper.isSupportedType(d);
				if (supportedType) {
					supportedData.add(d);
				}
			}
			if (supportedData.isEmpty()) {
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Unable to paste",
						"There is no pastable images in the cliboard.");
				return;
			}

			final StructuredSelection selection = (StructuredSelection) this.selection;
			if (selection.isEmpty()) {
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Unable to paste",
						"Please select container to paste.");
				return;
			}
			final Object firstElement = selection.getFirstElement();
			IContainer res = null;
			if (firstElement instanceof IResource) {
				final IResource mn = (IResource) firstElement;
				if (mn instanceof IContainer) {
					res = (IContainer) mn;
				} else if (mn instanceof IFile) {
					final IFile fl = (IFile) mn;
					res = fl.getParent();
				}
			}
			if (firstElement instanceof IAdaptable) {
				final IAdaptable ad = (IAdaptable) firstElement;
				final Object adapter = ad.getAdapter(IResource.class);
				if (adapter != null) {
					final IResource mn = (IResource) adapter;
					if (mn instanceof IContainer) {
						res = (IContainer) mn;
					} else if (mn instanceof IFile) {
						final IFile fl = (IFile) mn;
						res = fl.getParent();
					}
				}
			}
			if (res == null) {
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Unable to paste",
						"Please select container to paste.");
				return;
			}
			for (final TransferData d : supportedData) {
				final boolean supportedType = ImageTransferWrapper.isSupportedType(d);
				if (!supportedType) {
					MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Strange Error",
							"Unsupported transfer type here.");
				}
				final ImageData contents = (ImageData) clipboard.getContents((Transfer) ImageTransferWrapper
						.getInstance());
				if (contents != null) {

					final ImageLoader imageLoader = new ImageLoader();

					imageLoader.data = new ImageData[] { contents };
					imageLoader.logicalScreenHeight = contents.height;
					imageLoader.logicalScreenWidth = contents.width;
					final NameEndExtensionDialog dialog = new NameEndExtensionDialog(Display.getCurrent()
							.getActiveShell());

					final int open = dialog.open();
					if (open == Window.OK) {
						final String value = dialog.getValue();
						this.save(imageLoader, value, res, dialog.selection);
					}
				}
				// new java.awt.datatransfer.Clipboard("").
			}
		} finally {
			if (this.fClipboard == null) {
				clipboard.dispose();
			}
		}

	}

	private void save(ImageLoader imageLoader, String value, IContainer res, int types) {
		final IFile file = res.getFile(new Path(value));
		if (file.exists()) {
			final boolean openConfirm = MessageDialog.openConfirm(Display.getCurrent().getActiveShell(),
					"File already exists", "File with specified name already exists. Overwrite?");
			if (!openConfirm) {
				return;
			}
		}
		final IPath rawLocation = file.getRawLocation();
		try {
			try {
				imageLoader.save(rawLocation.toOSString(), PasteImageAction.FILE_TYPE_CODES[types]);
			} catch (final SWTException e) {
				e.printStackTrace();
				MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error during image saving", e
						.getMessage());
			}
			file.refreshLocal(1, null);
		} catch (final CoreException e) {
			e.printStackTrace();
		}
	}

	public PasteImageAction() {
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {

	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

}
