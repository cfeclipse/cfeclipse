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

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.OccurrencesFinder;
import org.cfeclipse.cfml.editors.partitioner.CFEPartition;
import org.cfeclipse.cfml.editors.partitioner.CFEPartitioner;
import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.ISelectionValidator;
import org.eclipse.jface.text.ITextInputListener;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.link.LinkedModeModel;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.MarkerUtilities;

import cfml.parsing.CFMLParser;
import cfml.parsing.CFMLSource;
import cfml.parsing.ParserTag;


/**
 * @author Stephen Milligan
 *
 * This listener keeps track of where the mouse is relative to the currently selected text
 * and whether or not the mouse is currently down.
 */
public class SelectionCursorListener implements MouseListener, MouseMoveListener, MouseTrackListener, ISelectionChangedListener {
    /**
     * The text editor that the selection listener is installed on
     */
    //private ITextEditor editor = null;
    /**
     * The StyledText that belongs to the viewer
     */
    private StyledText textWidget = null;
    /**
     * The projection viewer for this editor
     */
    private ProjectionViewer fViewer = null;
    
    /**
     * This allows us to figure out where a point is in widget co-ordinate space.
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
	public String selectionText;
    /**
     * Is the mouse currently hovering over a selected area
     */
    private boolean hovering = false;
    /**
     * Was the mouse down the last time we were notified
     */
    private boolean mouseDown = false;
    
    /**
     * Indicates whether or not the selection needs to be expanded
     * to contain folded text. This is set to true when the 
     * selection ends at the end of a line.
     */
    public boolean expandSelection = false;
    
    /**
     * This allows us to handle the case where the user clicks and releases on a selection.
     * Mouse down sets it to true
     * Mouse move sets it to false
     * Mouse up checks it's value and calls reset() if true.
     */
    private boolean downUp = false;


	/*
	 * These will be used for word delineation
	 */
	private String partOfWordChars;
	private String breakWordChars;
	private String partOfWordCharsAlt;
	private String breakWordCharsAlt;
	private String partOfWordCharsShift;
	private String breakWordCharsShift;
	private CfmlTagItem lastSelectedTag;
	private CfmlTagItem selectedTag;
	private boolean selectedTagWasSelected;
	private CFMLEditor editor;
	private static String TYPE = "org.cfeclipse.cfml.occurrencemarker";
	private static String tagBeginEndAnnotation = "org.cfeclipse.cfml.tagbeginendmarker";
	//private static String TYPE = "org.eclipse.core.resources.textmarker";
	//private static String TYPE = "org.cfeclipse.cfml.parserWarningMarker";

	/**
	 * Holds the current occurrence annotations.
	 * @since 3.1
	 */
	private Annotation[] fOccurrenceAnnotations= null;

	private OccurrencesFinderJob fOccurrencesFinderJob;
	private OccurrencesFinderJobCanceler fOccurrencesFinderJobCanceler;
	
	private ITextSelection fForcedMarkOccurrencesSelection;
	private boolean fMarkOccurrenceAnnotations = true;
	private boolean fStickyOccurrenceAnnotations = false;
	private CfmlTagItem currentDocItem;
	private MouseEvent lastMouseEvent;
	private String[] wordCharArray;

    
    /**
     * This class listens to the mouse position relative to any selected text 
     * and keeps track of whether or not the mouse is currently over a selection.
     */
    public SelectionCursorListener(CFMLEditor editor, ProjectionViewer viewer, String[] wordChars) {
        this.editor = editor;
        this.fViewer = viewer;
        this.textWidget = viewer.getTextWidget();
        this.arrowCursor = new Cursor(this.textWidget.getDisplay(),SWT.CURSOR_ARROW);
        this.textCursor = new Cursor(this.textWidget.getDisplay(),SWT.CURSOR_IBEAM);
        this.widgetOffsetTracker = new WidgetPositionTracker(this.textWidget);
		setWordSelectionChars(wordChars);
    }
    
    /**
     * Resets the listener to a state where the mouse isn't hovering over a selection.
     *
     */
    public void reset() {
        this.hovering = false;
        this.selectionStart = -1;
        this.selectionText = "";
        this.textWidget.setCursor(this.textCursor);
        this.mouseDown = false;
        //System.out.println("Listener reset");
    }

    /**
     * sets the work selection break/continue chars.
     *
     */
	public void setWordSelectionChars(String[] wordChars) {
		// this is nasty.  Be sure to update OccurrencesFinder if changed!
		this.wordCharArray = wordChars;
		this.partOfWordChars = wordChars[0];
		this.breakWordChars = wordChars[1];
		this.partOfWordCharsAlt = wordChars[2];
		this.breakWordCharsAlt = wordChars[3];
		this.partOfWordCharsShift = wordChars[4];
		this.breakWordCharsShift = wordChars[5];		
	}
    
    /**
     * gets the work selection break/continue chars.
     *
     */
	public String[] getWordSelectionChars() {
		return this.wordCharArray;
	}

	public void setSelectedTag() {
		// TODO Auto-generated method stub
		//CFMLEditor curDoc = (CFMLEditor) this.fViewer.getDocument();
		//ICFDocument cfd = (ICFDocument) curDoc.getDocumentProvider().getDocument(curDoc.getEditorInput());
		TextSelection sel = (TextSelection) this.fViewer.getSelection();
		int startPos = sel.getOffset();
		ICFDocument cfd = (ICFDocument) this.fViewer.getDocument();
		CfmlTagItem cti = cfd.getTagAt(startPos, startPos, true);
		if(cti != null && this.selectedTag != null) {
			if(cti.getStartPosition() == this.selectedTag.getStartPosition()){				
				this.selectedTagWasSelected = true;
			} else {
				clearTagBeginEndMarkers();
				this.selectedTagWasSelected = false;				
			}
		} else {
			clearTagBeginEndMarkers();			
		}
		this.selectedTag = null;
		try {
			// cfcomponent returns ASTVarDeclaration -- syntax is null for whatever reason (ASTVar's from cfscript?!)
			if(cti != null 
				&& !cti.getName().equals("CfmlComment") && !cti.getName().equals("cfscript")
				&& !cti.getName().startsWith("AST")) {
 			if(cti.getName().equals("CfmlCustomTag") || cti.hasClosingTag() ) {
					markBeginEndTags(cti);	
			}
		} 
		} catch (Exception e) {
			System.err.println(cti.getName());
			e.printStackTrace();
		}			
		CFEPartitioner partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();		
		CFEPartition part = partitioner.findClosestPartition(startPos);
		if(part == null) {
			return;
		}
		startPos = part.offset;
		if (cti != null) {
			this.currentDocItem = cti;
			this.selectedTag = cti;
		} else {
			this.currentDocItem = cfd.getTagAt(startPos, startPos, false);
			while (this.currentDocItem == null && startPos >= 0 && part != null) {
				startPos = part.offset;
				this.currentDocItem = cfd.getTagAt(startPos, startPos + part.length + 1, false);
				part = partitioner.getPreviousPartition(startPos);
			}
			this.selectedTag = this.currentDocItem;
		}
	}
	public CfmlTagItem getSelectedTag() {
		return this.selectedTag;
	}
	public DocItem getCurrentDocItem() {
		return this.currentDocItem;
	}
	public boolean getSelectedTagWasSelected() {
		return this.selectedTagWasSelected;
	}
	public MouseEvent getLastMouseEvent() {
		return this.lastMouseEvent;
	}
    
    /**
     * Allows the drag drop listener to know if it's ok to start a drag.
     * 
     * @return
     */
    public boolean doDrag() {
        if (this.hovering && this.mouseDown) {
            return true;
        }
        return false;
    }
    
    
    /**
     * Sent when the mouse pointer passes into the area of
     * the screen covered by a control.
     *
     * @param e an event containing information about the mouse enter
     */
    public void mouseEnter(MouseEvent e) {
        // do nothing
        //reset();
    }

    /**
     * Sent when the mouse pointer passes out of the area of
     * the screen covered by a control.
     *
     * @param e an event containing information about the mouse exit
     */
    public void mouseExit(MouseEvent e) {
        //reset();
    }

    /**
     * Sent when the mouse pointer hovers (that is, stops moving
     * for an (operating system specified) period of time) over
     * a control.
     *
     * @param e an event containing information about the hover
     */
    public void mouseHover(MouseEvent e) {
        // do nothing
    }
    
    
    /**
     * Sent when the mouse moves.
     *
     * @param e an event containing information about the mouse move
     */
    public void mouseMove(MouseEvent e) {
        // If the selection is draggable we want to ignore this event.
        
        if (!this.mouseDown) {
        	
	        Point pt = new Point(e.x,e.y);
	        
	        if (pointOnSelection(pt)) {
	            this.textWidget.setCursor(this.arrowCursor);
	            this.hovering = true;
	        }
	        else {
	            this.textWidget.setCursor(this.textCursor);
	            this.hovering = false;
	        }
        }
        else {
            this.downUp = false;
        }
    }
    
    /**
     * This is notified when the selection is changed in the viewer.
     * 
     * If a drag is in progress or the cursor is already over a selection, 
     * the selection change is ignored.
     * 
     */
    
    public void selectionChanged(SelectionChangedEvent event) {
		setSelectedTag();
        if (!this.hovering) {
        	if(editor.isMarkingOccurrences()) {
        		ISelection selection = event.getSelection();
        		if (event.getSelectionProvider() instanceof IPostSelectionProvider) {
        			if (selection instanceof ITextSelection) {
						ITextSelection textSelection= (ITextSelection)selection;
						try{
							updateOccurrenceAnnotations(textSelection, editor.getCFModel());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					//markOccurrences(this.selectionText);			
        		}
        		else {
        			//System.out.println("MarkOccurrences got non POST selection changed");
        		}
        	}
        }
		this.lastMouseEvent = null;
    }
    /**
     * Determines if the selection needs to be expanded
     * to account for a closed fold.
     * 
     * If so, it modifies the selection in the editor
     * and updates the selected text.
     * 
     */
    private void checkFolding() {
        int widgetOffset = this.fViewer.modelOffset2WidgetOffset(this.selectionStart);
        String[] lines = this.selectionText.split(this.textWidget.getLineDelimiter());
        int widgetLine = this.textWidget.getContent().getLineAtOffset(widgetOffset);
        int lineCount = 0;
        
        if (lines.length > 0) {
            lineCount = lines.length -1;
        }
        
        // If we've already grabbed the text inside a fold we 
        // could end up with more lines than the widget knows about.
        if (widgetLine+lineCount > this.textWidget.getLineCount()) {
            return;
        }

        String line = this.textWidget.getContent().getLine(widgetLine+lineCount);
        
        if (lines.length > 0 
                && line.equals(lines[lines.length-1])) {
            // Figure out the viewer offset for the start of the line.
            int widgetLineStart = this.textWidget.getContent().getOffsetAtLine(widgetLine + lineCount);
            int viewerLineStart = this.fViewer.widgetOffset2ModelOffset(widgetLineStart);
           
            ProjectionAnnotationModel model = this.fViewer.getProjectionAnnotationModel();
            Iterator i = model.getAnnotationIterator();
            while (i.hasNext()) {
                ProjectionAnnotation annotation = (ProjectionAnnotation)i.next();
                Position pos = model.getPosition(annotation);
                /* Check if the line is the start line of a collapsed
                 * region.
                 */
                if (pos.offset == viewerLineStart 
                        && annotation.isCollapsed()) {
                    int selectionLength = viewerLineStart - this.selectionStart + pos.length;
                    // Grab the current caret position so we can put it back after changing the selection
                    Point oldCaret = this.textWidget.getCaret().getLocation();
                    TextSelection sel = new TextSelection(this.fViewer.getDocument(),this.selectionStart,selectionLength);
                    this.fViewer.setSelection(sel,false);
                    /* Restore the caret. Using this rather than textWidget.setCaretOffset()
                     * because setCaretOffset() clears the selection
                     */ 
                    this.textWidget.getCaret().setLocation(oldCaret);
                }
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

		int startpos = sel.getOffset()+1;

		if ((e.stateMask & SWT.MOD1) != 0) {

			CFMLParser fCfmlParser = new CFMLParser();
			ICFDocument cfd = (ICFDocument) this.fViewer.getDocument();
			CFMLSource cfmlSource = fCfmlParser.addCFMLSource(cfd.getCFDocument().getFilename(),cfd.get());
			ParserTag tag = cfmlSource.getEnclosingTag(startpos);
			int start = 0;
			int length = 0;
			if (tag != null) {

				if ((e.stateMask & SWT.SHIFT) != 0) {
					start = tag.getBegin();
					length = tag.getEnd() - tag.getBegin();
				} else {
					if (tag.getEndTagBegin() <= startpos
							&& tag.getEndTagEnd() >= startpos) {
						start = tag.getEndTagBegin();
						length = tag.getEndTagEnd() - tag.getEndTagBegin();
					} else {
						start = tag.getStartTagBegin();
						length = tag.getStartTagEnd() - tag.getStartTagBegin();
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
	// TODO:  move this to org.cfeclipse.cfml.editors.selection.CFTextSelector
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
	private void selectRange(int startPos, int stopPos) 
	{
		int offset = startPos + 1;
		int length = stopPos - offset;
		this.fViewer.setSelectedRange(offset, length);
	}    
    
    
    /**
     * Sent when a mouse button is pressed.
     *
     * @param e an event containing information about the mouse button press
     */
    public void mouseDown(MouseEvent e) {
        if ((e.stateMask & SWT.CONTROL) == 0) {
            this.mouseDown = true;
            this.downUp = true;
        }
        this.lastMouseEvent = e;
    }

    /**
     * Sent when a mouse button is released.
     *
     * @param e an event containing information about the mouse button release
     */
    public void mouseUp(MouseEvent e) {
        if ((e.stateMask & SWT.CONTROL) == 0) {
            this.mouseDown = false;
	        if (this.downUp) {
	            reset();
	        }
	        if (this.selectionStart >= 0) {
	            checkFolding();
	        }
	        
        }
    }

	
	/**
	 * Returns true if the given point is on top of a selection.
	 * 
	 * @param pt - Point in widget co-ordinates
	 * @return
	 */
	private boolean pointOnSelection(Point pt) {
	    try {
	        if (this.selectionStart >= 0) {
	            int offset = this.widgetOffsetTracker.getWidgetOffset(pt);
	            // Convert to viewer co-ordinates
	            offset = this.fViewer.widgetOffset2ModelOffset(offset);
	            
	            if(this.selectionStart <= offset 
	                    && this.selectionStart + this.selectionText.length() > offset) {
	                 return true;
	            }
	        }
	    }
        catch (Exception ex) {
            // do nothing
        }
        return false;
	}
		
	protected void markBeginEndTags(CfmlTagItem tagItem) {
		if(tagItem.getMatchingItem() != null) {			
			Map tagOpen = new HashMap();
			Map tagClose = new HashMap();
			MarkerUtilities.setMessage(tagOpen, "Open " + tagItem.getName());
			MarkerUtilities.setLineNumber(tagOpen, tagItem.getLineNumber());
			tagOpen.put(IMarker.LOCATION, "line " + tagItem.getLineNumber() + ", Chars " + tagItem.getStartPosition() + "-" + tagItem.getEndPosition());
			tagOpen.put(IMarker.CHAR_START, tagItem.getStartPosition());
			tagOpen.put(IMarker.CHAR_END, tagItem.getEndPosition());

			MarkerUtilities.setMessage(tagClose, "Close " + tagItem.getName());
			MarkerUtilities.setLineNumber(tagClose, tagItem.getMatchingItem().getLineNumber());
			tagClose.put(IMarker.LOCATION, "line " + tagItem.getMatchingItem().getLineNumber() + ", Chars " + tagItem.getMatchingItem().getStartPosition() + "-" + tagItem.getMatchingItem().getEndPosition());
			tagClose.put(IMarker.CHAR_START, tagItem.getMatchingItem().getStartPosition());
			tagClose.put(IMarker.CHAR_END, tagItem.getMatchingItem().getEndPosition());
			
			try {
				MarkerUtilities.createMarker(((ICFDocument)this.fViewer.getDocument()).getResource(), tagOpen, this.tagBeginEndAnnotation);
				MarkerUtilities.createMarker(((ICFDocument)this.fViewer.getDocument()).getResource(), tagClose, this.tagBeginEndAnnotation);
			} catch (CoreException excep) {
				excep.printStackTrace();
			} catch (Exception anyExcep) {
				anyExcep.printStackTrace();
			}		
		}
	}

	/**
	 * Clears any begin end tag markers
	 */
	public void clearTagBeginEndMarkers() {
		if (this.fViewer == null)
			return;

	      try {
			IMarker[] markers = ((ICFDocument)this.fViewer.getDocument()).getResource().findMarkers(this.tagBeginEndAnnotation, true, IResource.DEPTH_INFINITE);
			for(IMarker mark : markers) {
				mark.delete();
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Finds and marks occurrence annotations.
	 * 
	 * @since 3.1
	 */
	class OccurrencesFinderJob extends Job {
		
		private IDocument fDocument;
		private ISelection fSelection;
		private ISelectionValidator fPostSelectionValidator;
		private boolean fCanceled= false;
		private IProgressMonitor fProgressMonitor;
		private List fPositions;
		
		public OccurrencesFinderJob(IDocument document, List positions, ISelection selection) {
			super("Occurrences Marker"); //$NON-NLS-1$
			fDocument= document;
			fSelection= selection;
			fPositions= positions;
			
			if (editor.getSelectionProvider() instanceof ISelectionValidator)
				fPostSelectionValidator= (ISelectionValidator)editor.getSelectionProvider();
		}
		
		// cannot use cancel() because it is declared final
		void doCancel() {
			fCanceled= true;
			cancel();
		}
		
		private boolean isCanceled() {
			return fCanceled || fProgressMonitor.isCanceled() || fForcedMarkOccurrencesSelection == fSelection;

			//			return fCanceled || fProgressMonitor.isCanceled()
//				||  fPostSelectionValidator != null && !(fPostSelectionValidator.isValid(fSelection) || fForcedMarkOccurrencesSelection == fSelection)
//				|| LinkedModeModel.hasInstalledModel(fDocument);
		}
		
		/*
		 * @see Job#run(org.eclipse.core.runtime.IProgressMonitor)
		 */
		public IStatus run(IProgressMonitor progressMonitor) {
			
			fProgressMonitor= progressMonitor;
			
			if (isCanceled())
				return Status.CANCEL_STATUS;
			
			ITextViewer textViewer= (ITextViewer) fViewer;
			if (textViewer == null)
				return Status.CANCEL_STATUS;
			
			IDocument document= textViewer.getDocument();
			if (document == null)
				return Status.CANCEL_STATUS;
			
			IDocumentProvider documentProvider= editor.getDocumentProvider();
			if (documentProvider == null)
				return Status.CANCEL_STATUS;
		
			IAnnotationModel annotationModel= documentProvider.getAnnotationModel(editor.getEditorInput());
			if (annotationModel == null)
				return Status.CANCEL_STATUS;
			
			// Add occurrence annotations
			int length= fPositions.size();
			Map annotationMap= new HashMap(length);
			for (int i= 0; i < length; i++) {
				
				if (isCanceled())
					return Status.CANCEL_STATUS;
				
				String message;
				Position position= (Position) fPositions.get(i);
				
				// Create & add annotation
				try {
					message= document.get(position.offset, position.length);
				} catch (BadLocationException ex) {
					// Skip this match
					continue;
				}
				annotationMap.put(
						new Annotation("org.cfeclipse.cfml.occurrenceAnnotation", false, message), //$NON-NLS-1$
						position);
			}
			
			if (isCanceled()) {
				return Status.CANCEL_STATUS;
            }
			
            Object lock= editor.getLockObject(document);
            if (lock == null) {
                updateAnnotations(annotationModel, annotationMap);
            } else {
                synchronized (lock) {
                    updateAnnotations(annotationModel, annotationMap);
                }
            }

			return Status.OK_STATUS;
		}

        private void updateAnnotations(IAnnotationModel annotationModel, Map annotationMap) {
            if (annotationModel instanceof IAnnotationModelExtension) {
            	((IAnnotationModelExtension)annotationModel).replaceAnnotations(fOccurrenceAnnotations, annotationMap);
            } else {
            	removeOccurrenceAnnotations();
            	Iterator iter= annotationMap.entrySet().iterator();
            	while (iter.hasNext()) {
            		Map.Entry mapEntry= (Map.Entry)iter.next();
            		annotationModel.addAnnotation((Annotation)mapEntry.getKey(), (Position)mapEntry.getValue());
            	}
            }
            fOccurrenceAnnotations= (Annotation[])annotationMap.keySet().toArray(new Annotation[annotationMap.keySet().size()]);
        }
	}	
	
	/**
	 * Cancels the occurrences finder job upon document changes.
	 * 
	 * @since 3.1
	 */
	class OccurrencesFinderJobCanceler implements IDocumentListener, ITextInputListener {

		public void install() {
			ISourceViewer sourceViewer= fViewer;
			if (sourceViewer == null)
				return;
				
			StyledText text= sourceViewer.getTextWidget();
			if (text == null || text.isDisposed())
				return;

			sourceViewer.addTextInputListener(this);
			
			IDocument document= sourceViewer.getDocument();
			if (document != null)
				document.addDocumentListener(this);
		}
		
		public void uninstall() {
			ISourceViewer sourceViewer= fViewer;
			if (sourceViewer != null)
				sourceViewer.removeTextInputListener(this);

			IDocumentProvider documentProvider= editor.getDocumentProvider();
			if (documentProvider != null) {
				IDocument document= documentProvider.getDocument(editor.getEditorInput());
				if (document != null)
					document.removeDocumentListener(this);
			}
		}
				

		/*
		 * @see org.eclipse.jface.text.IDocumentListener#documentAboutToBeChanged(org.eclipse.jface.text.DocumentEvent)
		 */
		public void documentAboutToBeChanged(DocumentEvent event) {
			if (fOccurrencesFinderJob != null)
				fOccurrencesFinderJob.doCancel();
		}

		/*
		 * @see org.eclipse.jface.text.IDocumentListener#documentChanged(org.eclipse.jface.text.DocumentEvent)
		 */
		public void documentChanged(DocumentEvent event) {
		}

		/*
		 * @see org.eclipse.jface.text.ITextInputListener#inputDocumentAboutToBeChanged(org.eclipse.jface.text.IDocument, org.eclipse.jface.text.IDocument)
		 */
		public void inputDocumentAboutToBeChanged(IDocument oldInput, IDocument newInput) {
			if (oldInput == null)
				return;

			oldInput.removeDocumentListener(this);
		}

		/*
		 * @see org.eclipse.jface.text.ITextInputListener#inputDocumentChanged(org.eclipse.jface.text.IDocument, org.eclipse.jface.text.IDocument)
		 */
		public void inputDocumentChanged(IDocument oldInput, IDocument newInput) {
			if (newInput == null)
				return;
			newInput.addDocumentListener(this);
		}
	}
	
	
	public void installOccurrencesFinder() {
		fMarkOccurrenceAnnotations= true;
		
		if (editor.getSelectionProvider() != null) {
			ISelection selection= editor.getSelectionProvider().getSelection();
			if (selection instanceof ITextSelection) {
				fForcedMarkOccurrencesSelection= (ITextSelection) selection;
				updateOccurrenceAnnotations(fForcedMarkOccurrencesSelection, editor.getCFModel());
			}
		}
		if (fOccurrencesFinderJobCanceler == null) {
			fOccurrencesFinderJobCanceler= new OccurrencesFinderJobCanceler();
			fOccurrencesFinderJobCanceler.install();
		}
	}
	
	public void uninstallOccurrencesFinder() {
		fMarkOccurrenceAnnotations= false;
		
		if (fOccurrencesFinderJob != null) {
			fOccurrencesFinderJob.cancel();
			fOccurrencesFinderJob= null;
		}

		if (fOccurrencesFinderJobCanceler != null) {
			fOccurrencesFinderJobCanceler.uninstall();
			fOccurrencesFinderJobCanceler= null;
		}
		
		removeOccurrenceAnnotations();
	}

	/**
	 * Updates the occurrences annotations based
	 * on the current selection.
	 * 
	 * @param selection the text selection
	 * @param antModel the model for the buildfile
	 * @since 3.1
	 */
	public void updateOccurrenceAnnotations(ITextSelection selection, CFDocument antModel) {

		if (fOccurrencesFinderJob != null)
			fOccurrencesFinderJob.cancel();

		if (!fMarkOccurrenceAnnotations) {
			return;
		}
		
		if (selection == null || antModel == null) {
			return;
		}
		
		IDocument document= fViewer.getDocument();
		if (document == null) {
			return;
		}
		
		List positions= null;
		
		OccurrencesFinder finder= new OccurrencesFinder(editor, antModel, document, selection.getOffset());
		positions= finder.perform();
		
		if (positions == null || positions.size() == 0) {
			if (!fStickyOccurrenceAnnotations) {
				removeOccurrenceAnnotations();
			}
			return;
		}
		
		fOccurrencesFinderJob= new OccurrencesFinderJob(document, positions, selection);
		fOccurrencesFinderJob.run(new NullProgressMonitor());
	}
	
	public void removeOccurrenceAnnotations() {
		IDocumentProvider documentProvider= editor.getDocumentProvider();
		if (documentProvider == null) {
			return;
		}
		
		IAnnotationModel annotationModel= documentProvider.getAnnotationModel(editor.getEditorInput());
		if (annotationModel == null || fOccurrenceAnnotations == null) {
			return;
		}

		IDocument document= documentProvider.getDocument(editor.getEditorInput());
        Object lock= editor.getLockObject(document);
        if (lock == null) {
            updateAnnotationModelForRemoves(annotationModel);
        } else {
            synchronized (lock) {
                updateAnnotationModelForRemoves(annotationModel);
            }
        }
	}


    private void updateAnnotationModelForRemoves(IAnnotationModel annotationModel) {
        if (annotationModel instanceof IAnnotationModelExtension) {
        	((IAnnotationModelExtension)annotationModel).replaceAnnotations(fOccurrenceAnnotations, null);
        } else {
        	for (int i= 0, length= fOccurrenceAnnotations.length; i < length; i++) {
        		annotationModel.removeAnnotation(fOccurrenceAnnotations[i]);
        	}
        }
        fOccurrenceAnnotations= null;
    }	
	public boolean isMarkingOccurrences() {
		return fMarkOccurrenceAnnotations;
	}	
	
}
