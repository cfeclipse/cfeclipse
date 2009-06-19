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

import java.util.HashMap;
import java.util.Map;

import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IFindReplaceTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.MarkerUtilities;

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
	public int selectionTextStart = -1;
	/**
	 * The contents of the selection according to the viewer
	 */
	public String selectionText = "";
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
	 * These will be used for word delineation
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

    private static String TYPE = "org.cfeclipse.cfml.occurrenceMarker";
	//private static String TYPE = "org.eclipse.core.resources.textmarker";
	//private static String TYPE = "org.cfeclipse.cfml.parserWarningMarker";

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
		clearMarkedOccurrences();
		if (!this.hovering) {
			ITextSelection sel = (ITextSelection) this.fViewer.getSelection();
			this.selectionTextStart = sel.getOffset();
			this.selectionText = sel.getText().trim();
			// this prevents it from firing while selecting text using the keyboard
			if (event.getSelectionProvider() instanceof IPostSelectionProvider && this.selectionText.length() > 0) {
				try {
					markOccurrences(this.selectionText);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
			else {
				//System.out.println("MarkOccurrences got non POST selection changed");
			}
		}
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

		if ((e.stateMask) != 0) {

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
		int startPos, endPos, pos;
		char c;
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

			pos = caretPos;
			int length = doc.getLength();
			while (pos < length) {
				c = doc.getChar(pos);
				if (breakWordChars.indexOf(c) >= 0 || !Character.isJavaIdentifierPart(c) && wordChars.indexOf(c) < 0)
					break;
				++pos;
			}
			endPos = pos;

			pos = caretPos;

			while (pos >= 0) {
				c = doc.getChar(pos);
				if (breakWordChars.indexOf(c) >= 0 || !Character.isJavaIdentifierPart(c) && wordChars.indexOf(c) < 0)
					break;
				--pos;
			}

			startPos = pos;

			
			if(startPos != endPos) {	
				selectRange(startPos, endPos);
//				markOccurrences(this.selectionText);
			}
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
	 * @throws BadLocationException 
	 * 
	 */
	protected void markOccurrences(String findString) throws BadLocationException {
		int index = 0;
		int occurrenceCount = 0;

		if (this.fViewer != null && findString.length() > 0) {

			ISelection initialSelection = this.fViewer.getSelection();

			if (initialSelection instanceof ITextSelection) {
				ITextSelection textSelection = (ITextSelection) initialSelection;
				if (!textSelection.isEmpty()) {
					String text = this.fViewer.getDocument().get();
					IDocument iDoc = (ICFDocument) this.fViewer.getDocument();
					IResource resource = ((ICFDocument) this.fViewer.getDocument()).getResource();
					index = text.indexOf(findString);
					while (index != -1) {

						occurrenceCount++;

						// AnnotationModel model = (AnnotationModel)
						// this.fViewer.getAnnotationModel();
						// Annotation occurrenceAnnotation = new Annotation(
						//									"org.eclipse.jdt.ui.occurrences", true, findString); //$NON-NLS-1$
						// occurrenceAnnotation.setText("Found:"+findString);
						// Position position = new Position(index,
						// findString.length());
						// if (position != null) {
						// model.addAnnotation(occurrenceAnnotation, position);
						// } else {
						// System.out.println("Null Position! Not good!");
						// }

						Map attrs = new HashMap();
						int lineNum = iDoc.getLineOfOffset(index)+1;
						MarkerUtilities.setMessage(attrs, "Occurrence " + occurrenceCount + " of " + findString);
						MarkerUtilities.setLineNumber(attrs, lineNum);
						attrs.put(IMarker.LOCATION, "line " + lineNum + ", Chars " + index + "-" + index
								+ findString.length());
						attrs.put(IMarker.CHAR_START, index);
						attrs.put(IMarker.CHAR_END, new Integer(index + findString.length()));

						try {
							MarkerUtilities.createMarker(resource, attrs, this.TYPE);
						} catch (CoreException excep) {
							excep.printStackTrace();
						} catch (Exception anyExcep) {
							anyExcep.printStackTrace();
						}
						index = text.indexOf(findString, index + findString.length());
					}
				}
			}
		}

	}
	
	/**
	 * Clears any marked occurrences
	 */
	private void clearMarkedOccurrences() {
		if (this.fViewer == null)
			return;

	      try {
			IMarker[] markers = ((ICFDocument)this.fViewer.getDocument()).getResource().findMarkers(this.TYPE, true, IResource.DEPTH_INFINITE);
			for(IMarker mark : markers) {
				mark.delete();
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
