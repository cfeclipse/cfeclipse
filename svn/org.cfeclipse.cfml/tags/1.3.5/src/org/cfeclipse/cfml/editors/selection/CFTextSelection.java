package org.cfeclipse.cfml.editors.selection;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;

/**
 * A special text selection that gives access to the resolved and
 * enclosing element.
 */
public class CFTextSelection extends TextSelection {

	public CFTextSelection(IDocument document, int offset, int length) {
		super(document, offset, length);
		// TODO Auto-generated constructor stub
	}


}
