/*
 * Created on Apr 15, 2008
 * by Christopher Bradford
 *
 */

package org.cfeclipse.cfml.editors.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;

/**
 * @author Christopher Bradford
 *
 */
public class ClipboardLinkAction extends GenericEncloserAction {

    /**
    *
    * Encloses highlighted text in <code><a href="[Contents of Clipboard]">...</a></code> tags
    */
	public ClipboardLinkAction()
	{
		super();
	}

	public void run()
	{
		getClipboardText();
		super.run();
	}

	public void run(IAction action)
	{
		getClipboardText();
		super.run(action);
	}

	private void getClipboardText()
	{
		Clipboard clipboard = new Clipboard(editor.getSite().getWorkbenchWindow().getWorkbench().getDisplay());
		TextTransfer transfer = TextTransfer.getInstance();
		String clipboardContents = (String)clipboard.getContents(transfer);
		setEnclosingStrings("<a href=\"" + clipboardContents + "\">", "</a>");
	}

}
