/*
 * Created on Oct 19, 2004
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
package org.cfeclipse.cfml.editors.codefolding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.cfeclipse.cfml.preferences.CFMLPreferenceManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Stephen Milligan
 * 
 *         This class is used to set the code folding markers.
 */
public class CodeFoldingSetter {
	private QualifiedName foldStateQN = new QualifiedName("foldedCodeState", "foldState");

	private ITextEditor editor;

	private List<StoredFold> storedFolds = null;

	private ProjectionAnnotationModel model = null;

	private ICFDocument doc = null;

	private CFMLPreferenceManager preferenceManager = null;

	private IFile resource;

	private Boolean restoredFoldState;

	public CodeFoldingSetter(ITextEditor editor) {
		this.editor = editor;
		model = (ProjectionAnnotationModel) editor.getAdapter(ProjectionAnnotationModel.class);
		doc = (ICFDocument) editor.getDocumentProvider().getDocument(editor.getEditorInput());
		resource = ((IFile) editor.getEditorInput().getAdapter(IFile.class));
		preferenceManager = new CFMLPreferenceManager();
		restoredFoldState = false;
	}

	public synchronized void docChanged(boolean autoCollapse) {
		// storeMemento();
		// restoreMemento();
		if (preferenceManager.enableFolding()) {
			addMarksToModel(autoCollapse);
			if (preferenceManager.persistFoldState()) {
				if (!restoredFoldState) {
					restoredFoldState = true;
					restoreFoldState();
				} else {
					persistFoldState();
				}
			}
		}
	}

	/**
	 * @param root
	 * @param model
	 */
	private void addMarksToModel(boolean autoCollapse) {
		try {
			if (model != null) {

				// We need this to keep track of what should be collapsed once
				// we've added all the markers.
				HashMap markerMap = new HashMap();

				scrubAnnotations();

				if (preferenceManager.foldCFMLComments()) {
					foldPartitions(markerMap, CFPartitionScanner.CF_COMMENT, preferenceManager.collapseCFMLComments()
							&& autoCollapse, preferenceManager.minimumFoldingLines() - 1);
					foldPartitions(markerMap, CFPartitionScanner.CF_SCRIPT_COMMENT_BLOCK, preferenceManager.collapseCFMLComments()
							&& autoCollapse, preferenceManager.minimumFoldingLines() - 1);
					foldPartitions(markerMap, CFPartitionScanner.JAVADOC_COMMENT, preferenceManager.collapseCFMLComments() && autoCollapse,
							preferenceManager.minimumFoldingLines() - 1);
				}
				if (preferenceManager.foldHTMLComments()) {
					foldPartitions(markerMap, CFPartitionScanner.HTM_COMMENT, preferenceManager.collapseHTMLComments()
							&& autoCollapse, preferenceManager.minimumFoldingLines() - 1);
				}

				for (int i = 1; i < 9; i++) {
					// System.out.println("Checking " +
					// preferenceManager.foldingTagName(i).trim());
					if (preferenceManager.foldTag(i) && preferenceManager.foldingTagName(i).trim().length() > 0) {
						foldTags(markerMap, preferenceManager.foldingTagName(i).trim(), preferenceManager
								.collapseTag(i)
								&& autoCollapse, preferenceManager.minimumFoldingLines() - 1);
					}
				}

				// Now collapse anything that should be collapsed
				Iterator x = markerMap.keySet().iterator();

				while (x.hasNext()) {
					ProjectionAnnotation p = (ProjectionAnnotation) x.next();
					boolean collapsed = ((Boolean) markerMap.get(p)).booleanValue();
					if (collapsed) {
						model.collapse(p);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void foldTags(HashMap markerMap, String tagName, boolean autoCollapse, int minLines) {
		DocItem rootItem = null;
		try {
			rootItem = doc.getCFDocument().getDocumentRoot();
		} catch (NullPointerException e) {
			// e.printStackTrace();
			System.out.println("CodeFoldingSetter::foldTags got a null from doc.getCFDocument().");
			return;
		}
		// nodes =
		// rootItem.selectNodes("//function[#startpos>=0 and #endpos < 200]");
		CFNodeList nodes = rootItem.selectNodes("//" + tagName.toLowerCase());

		Iterator it = nodes.iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (o instanceof CfmlTagItem && ((CfmlTagItem) o).matchingItem != null) {
				CfmlTagItem tag = (CfmlTagItem) o;

				int start = tag.getStartPosition();
				int length = tag.matchingItem.getEndPosition() - start;
				try {
					int startLine = doc.getLineOfOffset(start);
					int endLine = doc.getLineOfOffset(start + length);
					start = doc.getLineOffset(startLine);
					length = doc.getLineOffset(endLine) + doc.getLineLength(endLine) - start;
					if (endLine - startLine > minLines) {

						addFoldingMark(markerMap, start, length, new TagProjectionAnnotation(tagName), autoCollapse);
					}
				} catch (BadLocationException blx) {

				}

			}
		}
	}

	private void foldPartitions(HashMap markerMap, String partitionType, boolean autoCollapse, int minLines) {
		// This will hold the regions that should have folding markers.
		ArrayList regions = new ArrayList();

		IDocumentPartitioner partitioner = doc.getDocumentPartitioner();
		ITypedRegion[] regionArray = partitioner.computePartitioning(0, doc.getLength());

		for (int i = 0; i < regionArray.length; i++) {
			ITypedRegion region = regionArray[i];
			if (region.getType() == partitionType) {
				// Position position= new Position(region.getOffset(),
				// region.getLength());
				regions.add(region);
			}
		}

		foldRegions(markerMap, regions, autoCollapse, minLines, partitionType);
	}

    private void scrubAnnotations() {
        Iterator iter = model.getAnnotationIterator();
        
        while (iter.hasNext()) {
            Object o = iter.next();
            ProjectionAnnotation annotation = (ProjectionAnnotation)o;
            if (!(annotation instanceof TagProjectionAnnotation)
                    && !annotation.isCollapsed()) {
				/* Changed the requirement here as there were problems relating 
				 * to ticket #143
				 * 
				 * We should remove all annotations as they are going to be
				 * re-created as part of the addMarksToModel function call that 
				 * calls this function in the first place. If we don't, we get
				 * strange conflicts that cause IndexOutOfBounds errors.
				 * 
				 * This is a partial fix to the situation and it is only half
				 * the story. My thoughts are that this issue lies somewhere in
				 * the CFEPartitioner. This issue doesn't just relate to 
				 * comments.
				 * 
				 * Paul V. Ticket #143
				*/
	                
               	model.removeAnnotation(annotation);
            }
        }
    }

	public void expandAll() {
		initModel();
		model.expandAll(0, doc.getLength());
	}

	public void collapseAll() {
		initModel();
		Iterator i = model.getAnnotationIterator();
		while (i.hasNext()) {
			model.collapse((ProjectionAnnotation) i.next());
		}
	}

	/**
	 * 
	 * @param regions
	 * @param model
	 * @param doc
	 */
	private void foldRegions(HashMap markerMap, ArrayList regions, boolean autoCollapse, int minLines, String regionType) {
		int i = 0;

		try {

			for (Iterator iter = regions.iterator(); iter.hasNext(); ++i) {

				ITypedRegion element = (ITypedRegion) iter.next();
				// Find the start and end lines of the region

				int start = element.getOffset();
				int length = element.getLength();
				int startLine = doc.getLineOfOffset(start);
				int endLine = doc.getLineOfOffset(start + length);
				int end = doc.getLineLength(endLine);
				int stop = doc.getLineOffset(endLine) + end;
				// Make sure our position starts at the start of the line
				start = doc.getLineOffset(startLine);
				length = length + element.getOffset() - doc.getLineOffset(startLine);
				if (endLine - startLine > minLines) {
					try {
						CommentProjectionAnnotation annotation = new CommentProjectionAnnotation(regionType);
						addFoldingMark(markerMap, start, stop - start, annotation, autoCollapse);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param start
	 * @param length
	 * @param model
	 * @param annotation
	 * @throws BadLocationException
	 */
	public void addFoldingMark(HashMap markerMap, int start, int length, ProjectionAnnotation annotation,
			boolean autoCollapse) throws BadLocationException {

		if (!preferenceManager.enableFolding()) {
			return;
		}

		Position position = new Position(start, length);

		Iterator i = model.getAnnotationIterator();

		while (i.hasNext()) {
			Object o = i.next();
			Position thisPosition = model.getPosition((ProjectionAnnotation) o);

			if (thisPosition.offset == position.offset) {

				return;
			}
		}
		markerMap.put(annotation, new Boolean(autoCollapse));
		model.addAnnotation(annotation, position);

	}

	public void addFoldToSelection(boolean collapse) {
		if (!preferenceManager.enableFolding()) {
			return;
		}
		initModel();

		HashMap markerMap = new HashMap();

		ITextSelection textSelection = getSelection();

		if (!textSelection.isEmpty()) {
			int start = textSelection.getStartLine();
			int end = textSelection.getEndLine();
			if (start < end)
				try {

					int offset = doc.getLineOffset(start);
					int endOffset = doc.getLineOffset(end + 1);

					addFoldingMark(markerMap, offset, endOffset - offset, new CustomProjectionAnnotation(collapse),
							collapse);

				} catch (BadLocationException x) {
				}
		}
		persistFoldState();
	}

	public void removeFoldFromSelection() {
		if (!preferenceManager.enableFolding()) {
			return;
		}
		initModel();

		ArrayList annotations = findAnnotations(getSelection());
		Iterator i = annotations.iterator();
		while (i.hasNext()) {
			model.removeAnnotation((ProjectionAnnotation) i.next());
		}
		persistFoldState();
	}

	public void toggleSelection() {

		if (!preferenceManager.enableFolding()) {
			return;
		}

		initModel();

		Boolean collapsing = null;

		ArrayList annotations = findAnnotations(getSelection());

		Iterator i = annotations.iterator();

		// int cursorOffset =
		// ((ITextSelection)editor.getSelectionProvider().getSelection()).getOffset();
		// int selectionLength =
		// ((ITextSelection)editor.getSelectionProvider().getSelection()).getLength();

		while (i.hasNext()) {
			ProjectionAnnotation annotation = (ProjectionAnnotation) i.next();

			if (collapsing == null) {
				if (annotation.isCollapsed()) {
					collapsing = new Boolean(false);
				} else {
					collapsing = new Boolean(true);
				}

			}
			if (collapsing.booleanValue()) {
				model.collapse(annotation);
			} else {
				model.expand(annotation);
			}

		}

	}

	public void expandMatchingMarkers() {

		if (!preferenceManager.enableFolding()) {
			return;
		}

		initModel();
		ArrayList annotations = findAnnotations(getSelection());

		Iterator i = annotations.iterator();
		while (i.hasNext()) {
			CFEProjectionAnnotation p = (CFEProjectionAnnotation) i.next();
			String type = p.getRegionType();
			Iterator j = model.getAnnotationIterator();
			while (j.hasNext()) {
				CFEProjectionAnnotation p1 = (CFEProjectionAnnotation) j.next();
				if (p1.getRegionType().equalsIgnoreCase(type)) {
					model.expand(p1);
				}
			}
		}

	}

	public void collapseMatchingMarkers() {

		if (!preferenceManager.enableFolding()) {
			return;
		}

		initModel();
		ArrayList annotations = findAnnotations(getSelection());

		Iterator i = annotations.iterator();
		while (i.hasNext()) {
			CFEProjectionAnnotation p = (CFEProjectionAnnotation) i.next();
			String type = p.getRegionType();
			Iterator j = model.getAnnotationIterator();
			while (j.hasNext()) {
				CFEProjectionAnnotation p1 = (CFEProjectionAnnotation) j.next();
				if (p1.getRegionType().equalsIgnoreCase(type)) {
					model.collapse(p1);
				}
			}
		}

	}

	public void collapseTags(String regionType) {

		if (!preferenceManager.enableFolding()) {
			return;
		}

		initModel();

		Iterator i = model.getAnnotationIterator();
		while (i.hasNext()) {
			Object o = i.next();
			if (o instanceof TagProjectionAnnotation
					&& ((TagProjectionAnnotation) o).getRegionType().equalsIgnoreCase(regionType)) {
				model.collapse((ProjectionAnnotation) o);
			}
		}

	}

	public void expandSelection() {

		if (!preferenceManager.enableFolding()) {
			return;
		}

		initModel();

		ArrayList annotations = findAnnotations(getSelection());
		Iterator i = annotations.iterator();
		while (i.hasNext()) {
			model.expand((ProjectionAnnotation) i.next());
		}

	}

	public void collapseSelection() {

		if (!preferenceManager.enableFolding()) {
			return;
		}

		initModel();

		ArrayList annotations = findAnnotations(getSelection());
		Iterator i = annotations.iterator();
		while (i.hasNext()) {
			model.collapse((ProjectionAnnotation) i.next());
		}

	}

	/**
	 * Returns a list of annotations that are either in or surrounding the
	 * selected text.
	 * 
	 * @param selection
	 * @return
	 */
	private ArrayList findAnnotations(ITextSelection selection) {
		ArrayList annotations = new ArrayList();

		if (!selection.isEmpty()) {
			try {
				int start = doc.getLineOffset(selection.getStartLine());
				int end = start + selection.getLength();

				// Check to see if there's anything selected. If not we want to
				// find the fold that contains the cursor
				boolean findContainer = selection.getLength() > 0 ? false : true;

				ProjectionAnnotation containingAnnotation = null;
				Position oldPosition = null;

				Iterator i = model.getAnnotationIterator();
				while (i.hasNext()) {
					ProjectionAnnotation annotation = (ProjectionAnnotation) i.next();

					Position position = model.getPosition(annotation);
					if (position.offset >= start && position.offset <= end) {
						annotations.add(annotation);
						// If there's nothing selected we can exit once we find
						// the first match
						if (findContainer) {
							containingAnnotation = null;
							break;
						}
					} else if (findContainer && position.offset < start && position.length + position.offset > end) {
						if (containingAnnotation == null) {
							containingAnnotation = annotation;
							oldPosition = position;
						} else if (position.offset > oldPosition.offset) {
							containingAnnotation = annotation;
							oldPosition = position;
						}
					}

				}
				if (containingAnnotation != null) {
					annotations.add(containingAnnotation);
					// int endOfLine = oldPosition.offset +
					// doc.getLineLength(doc.getLineOfOffset(oldPosition.offset))-2;
					TextSelection newSelection = new TextSelection(doc, oldPosition.offset, 0);
					editor.getSelectionProvider().setSelection(newSelection);
				}
			} catch (BadLocationException bex) {
				bex.printStackTrace();
			}

		}

		return annotations;
	}

	private ITextSelection getSelection() {
		ITextSelection selection = (ITextSelection) editor.getSelectionProvider().getSelection();
		ITextSelection textSelection = (ITextSelection) selection;
		return textSelection;
	}

	/*
	 * private void setSelection(int offset,int length) { TextSelection
	 * selection = new TextSelection(offset,length);
	 * editor.getSelectionProvider().setSelection(selection);
	 * 
	 * }
	 */

	/*
	 * public void storeMemento() { initModel();
	 * if(!preferenceManager.enableFolding()) { return; }
	 * 
	 * IMemento memento = ((CFMLEditor) editor).getMemento(); if(memento ==
	 * null) { ((CFMLEditor) editor).saveState(memento);
	 * saveStateAction.run(null); memento = ((CFMLEditor) editor).getMemento();
	 * }
	 * 
	 * snapshot = new HashMap(); ProjectionAnnotationModel model =
	 * (ProjectionAnnotationModel) editor
	 * .getAdapter(ProjectionAnnotationModel.class); Iterator iter =
	 * model.getAnnotationIterator(); while (iter.hasNext()) {
	 * ProjectionAnnotation p = (ProjectionAnnotation)iter.next();
	 * snapshot.put(p,new Boolean(p.isCollapsed())); }
	 * memento.createChild("CollapsedAnnotations");
	 * memento.getChild("CollapsedAnnotations"
	 * ).putTextData(snapshot.toString()); ((CFMLEditor)
	 * editor).saveState(memento); saveStateAction.run(null);
	 * 
	 * }
	 * 
	 * 
	 * public void restoreMemento() { IMemento memento = ((CFMLEditor)
	 * editor).getMemento(); if(preferenceManager.enableFolding()) { String data
	 * = memento.getChild("CollapsedAnnotations").getTextData(); return; }
	 * 
	 * }
	 */

	public void persistFoldState() {
		if (preferenceManager.persistFoldState() && resource != null && !resource.isPhantom()) {			
			StringBuffer sb = new StringBuffer();
			Iterator iter = model.getAnnotationIterator();
			int positions = 0;
			while (iter.hasNext()) {
				positions++;
				ProjectionAnnotation annotation = (ProjectionAnnotation) iter.next();
				Position position = model.getPosition(annotation);
				sb.append(position.offset + "," + position.length + ",");
				sb.append((annotation.isCollapsed())?1:0);
				sb.append("\n");
			}
			if (positions < 300) {
				try {
					if (resource.isAccessible()) {
						resource.setPersistentProperty(foldStateQN, sb.toString());
					}
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void loadSnapshotFromProperty() {
		String foldStates = "";
		storedFolds = new ArrayList();
		try {
			foldStates = resource.getPersistentProperty(foldStateQN);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!preferenceManager.enableFolding() || foldStates == null) {
			return;
		}
		String[] values = foldStates.split("\n");
		for (int i = 0; i < values.length; i++) {
				String[] split = values[i].split(",");
			if(split.length == 3) {
				storedFolds.add(new StoredFold(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
			}
		}
	}

	public void restoreFoldState() {
		if(resource != null) {
			loadSnapshotFromProperty();
			if (this.storedFolds != null) {
				Iterator iter;
				HashMap aHashMap = new HashMap();
				for (StoredFold fold : storedFolds) {
					iter = model.getAnnotationIterator(fold.offset, fold.length, false, false);
					if (iter.hasNext()) {
						ProjectionAnnotation p = (ProjectionAnnotation) iter.next();
						if (fold.collapsed!=0) {
							model.collapse(p);
						} else {
							model.expand(p);
						}
					} else {
						try {
							addFoldingMark(aHashMap, fold.offset, fold.length, new CustomProjectionAnnotation(fold.collapsed!=0),
									fold.collapsed!=0);
						} catch (BadLocationException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	private void initModel() {
		if (model == null) {
			model = (ProjectionAnnotationModel) editor.getAdapter(ProjectionAnnotationModel.class);
		}
	}

	/*
	 * cheese class for iterating;
	 */
	private class StoredFold {
		public StoredFold(int offset, int length, int i) {
			super();
			this.offset = offset;
			this.length = length;
			this.collapsed = i;
		}

		public int offset;
		public int length;
		public int collapsed;
	}

}