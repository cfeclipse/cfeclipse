package org.cfeclipse.cfml.editors.hover;

import org.eclipse.jface.text.ITextHover;

import org.eclipse.ui.IEditorPart;


/**
 * Provides a hover popup which appears on top of an editor with relevant
 * display information. If the text hover does not provide information no
 * hover popup is shown.
 * <p>
 * Clients may implement this interface.
 * </p>
 *
 * @see org.eclipse.ui.IEditorPart
 * @see org.eclipse.jface.text.ITextHover
 *
 * @since 2.0
 */
public interface ICFMLEditorTextHover extends ITextHover {

	/**
	 * Sets the editor on which the hover is shown.
	 *
	 * @param editor the editor on which the hover popup should be shown
	 */
	void setEditor(IEditorPart editor);

}