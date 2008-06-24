/*
 * Created on Oct 28, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package org.cfeclipse.cfml.editors.dnd;

import java.util.Iterator;

import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IFindReplaceTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;
import java.util.regex.PatternSyntaxException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Stephen Milligan
 * 
 * This listener keeps track of where the mouse is relative to the currently
 * selected text and whether or not the mouse is currently down.
 */
/**
 * @author valliant
 * 
 */
public class MarkOccurrencesListener implements MouseListener,
		ISelectionChangedListener {
	/**
	 * The text editor that the selection listener is installed on
	 */
	// private ITextEditor editor = null;
	/**
	 * The StyledText that belongs to the viewer
	 */
	private StyledText textWidget = null;
	/**
	 * The projection viewer for this editor
	 */
	private ProjectionViewer fViewer = null;

	/**
	 * This allows us to figure out where a point is in widget co-ordinate
	 * space.
	 */
	private WidgetPositionTracker widgetOffsetTracker = null;

	/**
	 * The cursor that indicates stuff can be dragged
	 */
	private Cursor arrowCursor = null;
	/**
	 * The regular text I-Beam cursor
	 */
	private Cursor textCursor = null;

	/**
	 * The offset of the start of the selected text in viewer co-ordinates
	 */
	public int selectionStart = -1;
	/**
	 * The contents of the selection according to the viewer
	 */
	public String selection = "";
	/**
	 * Is the mouse currently hovering over a selected area
	 */
	private boolean hovering = false;
	/**
	 * Was the mouse down the last time we were notified
	 */
	private boolean mouseDown = false;

	/*
	 * This will be used for finding/marking occurrences of a selected word
	 */
	private IFindReplaceTarget fTarget;

	/*
	 * These will be used for word deleniation
	 */
	private String partOfWordChars;
	private String breakWordChars;
	private String partOfWordCharsAlt;
	private String breakWordCharsAlt;
	private String partOfWordCharsShift;
	private String breakWordCharsShift;

	/**
	 * Indicates whether or not the selection needs to be expanded to contain
	 * folded text. This is set to true when the selection ends at the end of a
	 * line.
	 */
	public boolean expandSelection = false;

	/**
	 * This allows us to handle the case where the user clicks and releases on a
	 * selection. Mouse down sets it to true Mouse move sets it to false Mouse
	 * up checks it's value and calls reset() if true.
	 */
	private boolean downUp = false;


	/**
	 * This class listens to the mouse position relative to any selected text
	 * and keeps track of whether or not the mouse is currently over a
	 * selection.
	 */
	public MarkOccurrencesListener(ITextEditor editor, ProjectionViewer viewer,String[] wordChars) {
		// this.editor = editor;
		this.textWidget = viewer.getTextWidget();
		this.fViewer = viewer;
		this.arrowCursor = new Cursor(this.textWidget.getDisplay(), SWT.CURSOR_ARROW);
		this.textCursor = new Cursor(this.textWidget.getDisplay(), SWT.CURSOR_IBEAM);
		this.widgetOffsetTracker = new WidgetPositionTracker(this.textWidget);
		setWordSelectionChars(wordChars);
	}

	/**
	 * This class listens to the mouse position relative to any selected text
	 * and keeps track of whether or not the mouse is currently over a
	 * selection.
	 */
	public void setWordSelectionChars(String[] wordChars) {
		this.partOfWordChars = wordChars[0];
		this.breakWordChars = wordChars[1];
		this.partOfWordCharsAlt = wordChars[2];
		this.breakWordCharsAlt = wordChars[3];
		this.partOfWordCharsShift = wordChars[4];
		this.breakWordCharsShift = wordChars[5];		
	}


	/**
	 * This is notified when the selection is changed in the viewer.
	 * 
	 * If a drag is in progress or the cursor is already over a selection, the
	 * selection change is ignored.
	 * 
	 */

	public void selectionChanged(SelectionChangedEvent event) {
		if (!this.hovering) {
			ITextSelection sel = (ITextSelection) this.fViewer.getSelection();
			this.selectionStart = sel.getOffset();
			this.selection = sel.getText();
		}
		clearMarkedOccurrences();
	}

	/**
	 * Sent when a mouse button is pressed twice within the (operating system
	 * specified) double click period.
	 * 
	 * @param e
	 *            an event containing information about the mouse double click
	 * 
	 * @see org.eclipse.swt.widgets.Display#getDoubleClickTime()
	 */
	public void mouseDoubleClick(MouseEvent e) {

		TextSelection sel = (TextSelection) this.fViewer.getSelection();

		int startpos = sel.getOffset() + sel.getLength();

		if ((e.stateMask & SWT.MOD1) != 0) {

			ICFDocument cfd = (ICFDocument) this.fViewer.getDocument();
			CfmlTagItem cti = cfd.getTagAt(startpos, startpos, true);

			int start = 0;
			int length = 0;
			if (cti != null) {

				if ((e.stateMask & SWT.SHIFT) != 0 && cti.matchingItem != null) {

					if (cti.matchingItem.getStartPosition() < cti.getStartPosition()) {
						start = cti.matchingItem.getStartPosition();
						length = cti.getEndPosition() - cti.matchingItem.getStartPosition() + 1;
					} else {
						start = cti.getStartPosition();
						length = cti.matchingItem.getEndPosition() - cti.getStartPosition() + 1;
					}

				} else {
					if (cti.matchingItem != null && cti.matchingItem.getStartPosition() <= startpos
							&& cti.matchingItem.getEndPosition() >= startpos) {
						start = cti.matchingItem.getStartPosition();
						length = cti.matchingItem.getEndPosition() - cti.matchingItem.getStartPosition() + 1;
					} else {
						start = cti.getStartPosition();
						length = cti.getEndPosition() - cti.getStartPosition() + 1;
					}
				}

				TextSelection newSel = new TextSelection(cfd, start, length);
				this.fViewer.setSelection(newSel);

			} else {
				startpos = this.fViewer.getSelectedRange().x;
				selectWord(startpos, e);
			}
		} else {

			startpos = this.fViewer.getSelectedRange().x;
			selectWord(startpos, e);
		}

	}

	protected boolean selectWord(int caretPos, MouseEvent e) {

		IDocument doc = this.fViewer.getDocument();
		int startPos, endPos;
		String normalWordChars = this.partOfWordChars;
		String breakWordChars = this.breakWordChars;
		String wordChars = normalWordChars;
		String altWordChars = this.partOfWordCharsAlt;
		String altBreakWordChars = this.breakWordCharsAlt;
		String shiftWordChars = this.partOfWordCharsShift;
		String shiftBreakWordChars = this.breakWordCharsShift;
		try {
			if ((e.stateMask == SWT.ALT || e.stateMask == SWT.SHIFT + SWT.ALT)) {
				wordChars = wordChars + altWordChars;
				breakWordChars = altBreakWordChars;
			}
			if ((e.stateMask == SWT.SHIFT || e.stateMask == SWT.SHIFT + SWT.ALT)) {
				wordChars = wordChars + shiftWordChars;
				breakWordChars = shiftBreakWordChars;
			}

			int pos = caretPos;
			char c;

			while (pos >= 0) {
				c = doc.getChar(pos);
				if (breakWordChars.indexOf(c) >= 0 || !Character.isJavaIdentifierPart(c) && wordChars.indexOf(c) < 0)
					break;
				--pos;
			}

			startPos = pos;

			pos = caretPos;
			int length = doc.getLength();

			while (pos < length) {
				c = doc.getChar(pos);
				if (breakWordChars.indexOf(c) >= 0 || !Character.isJavaIdentifierPart(c) && wordChars.indexOf(c) < 0)
					break;
				++pos;
			}

			endPos = pos;
			selectRange(startPos, endPos);
			markOccurrences(this.fViewer.getFindReplaceTarget().getSelectionText());
			return true;

		} catch (BadLocationException x) {
			// ?
		}

		return false;
	}

	private void selectRange(int startPos, int stopPos) {
		int offset = startPos + 1;
		int length = stopPos - offset;
		this.fViewer.setSelectedRange(offset, length);
	}

	/**
	 * Sent when a mouse button is pressed.
	 * 
	 * @param e
	 *            an event containing information about the mouse button press
	 */
	public void mouseDown(MouseEvent e) {
		if ((e.stateMask & SWT.CONTROL) == 0) {
			this.mouseDown = true;
			this.downUp = true;
		}
	}

	/**
	 * Sent when a mouse button is released.
	 * 
	 * @param e
	 *            an event containing information about the mouse button release
	 */
	public void mouseUp(MouseEvent e) {
	}

	/**
	 * Mark occurrences of selected string
	 * 
	 * @param findString
	 * 
	 */
	protected void markOccurrences(String findString) {
		clearMarkedOccurrences();

		boolean somethingFound = false;

		if (findString != null && findString.length() > 0) {

			try {
				// somethingFound= highlightOccurrences(findString,isCaseSensitiveSearch(), isWholeWordSearch(),
				// isRegExSearchAvailableAndChecked());
				somethingFound = highlightOccurrences(findString, false, false, false);
				if (somethingFound) {
					// statusMessage(""); //$NON-NLS-1$
				} else {
					// statusMessage(EditorMessages.FindReplace_Status_noMatch_label);
				}
				// the below stuff is for regular expressions- might be used
				// someday?
			} catch (PatternSyntaxException ex) {
				// statusError(ex.getLocalizedMessage());
			} catch (IllegalStateException ex) {
				// we don't keep state in this dialog
			}
		}

	}

	/**
	 * Find and highlight occurrences of selected string
	 * 
	 * @param findString
	 * @param caseSensitive
	 * @param wholeWord
	 * @param regExSearch
	 * @return
	 */

	public boolean highlightOccurrences(String findString, boolean caseSensitive, boolean wholeWord, boolean regExSearch) {
		int index = 1;
		int startIndex = -1;
		int initialOffset = this.fViewer.getTopIndex();
		boolean somethingFound = false;

		if (this.fViewer == null) {
			return false;
		}

		fTarget = this.fViewer.getFindReplaceTarget();

		ISelection initialSelection = this.fViewer.getSelection();
		this.fViewer.setRedraw(false);

		while (index != -1) {
			// System.out.println("find "+findString);
			index = fTarget.findAndSelect(index + findString.length(), findString, true, caseSensitive, wholeWord);

			if (startIndex != -1 && startIndex == index)
				break;

			if (index != -1) {
				if (!somethingFound)
					startIndex = index;

				somethingFound = true;

				AnnotationModel model = (AnnotationModel) this.fViewer.getAnnotationModel();
				Annotation findAnnotation = new Annotation(
						"org.cfeclipse.cfml.markOccurrencesAnnotation", true, findString); //$NON-NLS-1$
				Position position = new Position(index, findString.length());
				if (position != null) {
					model.addAnnotation(findAnnotation, position);
				} else {
					System.out.println("Null Position! Not good!");
				}

			}
		}
		this.fViewer.setSelection(initialSelection, true);
		this.fViewer.setRedraw(true);
		this.fViewer.setTopIndex(initialOffset);
		


		return somethingFound;
	}

	/**
	 * Clears any marked occurrences
	 */
	private void clearMarkedOccurrences() {
		if (this.fViewer == null)
			return;

		AnnotationModel model = (AnnotationModel) this.fViewer.getAnnotationModel();
		Iterator iter = model.getAnnotationIterator();

		while (iter.hasNext()) {
			Annotation findAnnotation = (Annotation) iter.next();
			if (findAnnotation.getType().equals("org.cfeclipse.cfml.markOccurrencesAnnotation")) { //$NON-NLS-1$
				model.removeAnnotation(findAnnotation);
			}
		}
	}

	/*
	 * TEMPORARY debugging stuff
	 * 
	 */

	/**
	 * Sent when a key is pressed on the system keyboard.
	 * 
	 * @param e
	 *            an event containing information about the key press
	 */
	public void keyPressed(KeyEvent e) {
		// System.out.println("Key Pressed " + e.keyCode);
	}

	/**
	 * Sent when a key is released on the system keyboard.
	 * 
	 * @param e
	 *            an event containing information about the key release
	 */
	public void keyReleased(KeyEvent e) {

		// System.out.println("Key Released " + e.keyCode);

	}

}
