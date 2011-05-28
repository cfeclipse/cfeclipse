/*
 * Created on Oct 27, 2004
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

import java.io.File;
import java.io.IOException;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.CFMLEditorPart;
import org.cfeclipse.cfml.editors.actions.InsertFileLink;
import org.cfeclipse.cfml.util.RelativePath;
import org.cfeclipse.cfml.util.ResourceUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRewriteTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.part.EditorInputTransfer;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ResourceTransfer;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;
import org.eclipse.swt.dnd.DropTargetAdapter;
/**
 * @author Stephen Milligan
 *
 * This class enables text drag and drop both 
 * within the editor and to and from it.
 * 
 */
public class CFEDragDropListener implements DragSourceListener, DropTargetListener {

    /**
     * The text editor on which the listener is installed.
     */
    private CFMLEditorPart editor = null;
    /**
     * The text widget which belongs to the viewer
     */
    private StyledText textWidget = null;
    /**
     * The projection viewer that is installed on the editor.
     */
    private ProjectionViewer viewer = null;
    
    /**
     * This allows us to figure out where a point is in widget co-ordinate space.
     */
    private WidgetPositionTracker widgetPositionTracker = null;
    
    /**
     * The listener that is installed on the text editor
     */
    private SelectionCursorListener cursorListener = null;

    /**
     * The position of the last drop target offset in viewer co-ordinates.
     */
    int lastOffset = -1;
    /**
     * 
     */
    final FileTransfer fileTransfer = FileTransfer.getInstance();
    /**
     * Class for converting OS data types to java types
     */
    final TextTransfer textTransfer = TextTransfer.getInstance();
    final EditorInputTransfer editorTransfer = EditorInputTransfer.getInstance();
    final ResourceTransfer resourceTransfer = ResourceTransfer.getInstance();
    final LocalSelectionTransfer localSelectionTransfer = LocalSelectionTransfer.getInstance();
    
    /**
     * This indidates whether or not the drag is internal.
     */
    private boolean isInternalDrag = true;
    
    /**
     * This constructor sets up the editor, text widget viewer and cursor listeners
     * so that they are available to future method calls.
     * 
     */
    public CFEDragDropListener(CFMLEditorPart editor, ProjectionViewer viewer, SelectionCursorListener cursorListener) {
        try {
	        this.editor = editor;
	        this.textWidget = viewer.getTextWidget();
	        this.viewer = viewer;
	        this.cursorListener = cursorListener;
	        widgetPositionTracker = new WidgetPositionTracker(textWidget);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private Point fSelection;

	public void dragEnter(DropTargetEvent event) {
		fTextDragAndDropToken= null;
		fSelection= textWidget.getSelection();
		if (event.detail == DND.DROP_DEFAULT) {
			if ((event.operations & DND.DROP_MOVE) != 0) {
				event.detail= DND.DROP_MOVE;
			} else if ((event.operations & DND.DROP_COPY) != 0) {
				event.detail= DND.DROP_COPY;
			} else {
				event.detail= DND.DROP_NONE;
			}
		}
	}

	public void dragOperationChanged(DropTargetEvent event) {
		if (event.detail == DND.DROP_DEFAULT) {
			if ((event.operations & DND.DROP_MOVE) != 0) {
				event.detail= DND.DROP_MOVE;
			} else if ((event.operations & DND.DROP_COPY) != 0) {
				event.detail= DND.DROP_COPY;
			} else {
				event.detail= DND.DROP_NONE;
			}
		}
	}

	public void dragOver(DropTargetEvent event) {
		event.feedback |= DND.FEEDBACK_SCROLL;
		Point selection= textWidget.getSelection();
        Point mousePosition = textWidget.toControl(event.x,event.y);	  
        int modelOffset= editor.getWidgetOffset2ModelOffset(viewer, selection.x);
	    widgetPositionTracker.doScroll(mousePosition);
	    if (modelOffset != lastOffset) {
	        //viewer.setSelectedRange(modelOffset,0);
	        //editor.setHighlightRange(modelOffset,0,true);
	        lastOffset = modelOffset;
	    }	
	}

	
	public void drop(DropTargetEvent event) {
		try {
			if(textTransfer.isSupportedType(event.currentDataType)) {
				handleTextDrop(event);
				return;
			}
			if(resourceTransfer.isSupportedType(event.currentDataType)) {
				// we set this because we don't want the source resource deleted (DND.MOVE is default)
				event.detail = DND.DROP_COPY;
				handleResourceDrop(event);
				return;
			}
			if(fileTransfer.isSupportedType(event.currentDataType)) {
				// we set this because we don't want the source resource deleted (DND.MOVE is default)
				event.detail = DND.DROP_COPY;
				handleFileDrop(event);
				return;
			}
			if(localSelectionTransfer.isSupportedType(event.currentDataType)) {
				// we set this because we don't want the source resource deleted (DND.MOVE is default)
				event.detail = DND.DROP_COPY;
				handleLocalSelectionDrop(event);
				return;
			}

	    }
		catch (Exception e ) {
		    e.printStackTrace();
		}
	}
	
	
	/**
	 * Handles the dropping of text onto the editor.
	 * 
	 * If the operation is a move from within the editor, 
	 * the original is removed in addition to the new
	 * text being dropped.
	 * 
	 * @param event
	 */
	private void handleTextDrop(DropTargetEvent event) {
		try {
			if (fTextDragAndDropToken != null && event.detail == DND.DROP_MOVE) {
				// Move in same editor
				int caretOffset= textWidget.getCaretOffset();
				if (fSelection.x <= caretOffset && caretOffset <= fSelection.y) {
					event.detail= DND.DROP_NONE;
					return;
				}

				// Start compound change
				IRewriteTarget target= (IRewriteTarget)editor.getAdapter(IRewriteTarget.class);
				if (target != null)
					target.beginCompoundChange();
			}

			if (!((AbstractTextEditor) editor).validateEditorInputState()) {
				event.detail= DND.DROP_NONE;
				return;
			}

			String text= (String)event.data;
			if (((AbstractTextEditor) editor).isBlockSelectionModeEnabled()) {
				// FIXME fix block selection and DND
//				if (fTextDNDColumnSelection != null && fTextDragAndDropToken != null && event.detail == DND.DROP_MOVE) {
//					// DND_MOVE within same editor - remove origin before inserting
//					Rectangle newSelection= st.getColumnSelection();
//					st.replaceColumnSelection(fTextDNDColumnSelection, ""); //$NON-NLS-1$
//					st.replaceColumnSelection(newSelection, text);
//					st.setColumnSelection(newSelection.x, newSelection.y, newSelection.x + fTextDNDColumnSelection.width - fTextDNDColumnSelection.x, newSelection.y + fTextDNDColumnSelection.height - fTextDNDColumnSelection.y);
//				} else {
//					Point newSelection= st.getSelection();
//					st.insert(text);
//					IDocument document= getDocumentProvider().getDocument(getEditorInput());
//					int startLine= st.getLineAtOffset(newSelection.x);
//					int startColumn= newSelection.x - st.getOffsetAtLine(startLine);
//					int endLine= startLine + document.computeNumberOfLines(text);
//					int endColumn= startColumn + TextUtilities.indexOf(document.getLegalLineDelimiters(), text, 0)[0];
//					st.setColumnSelection(startColumn, startLine, endColumn, endLine);
//				}
			} else {
				Point newSelection= textWidget.getSelection();
				try {
					int modelOffset= editor.getWidgetOffset2ModelOffset(viewer, newSelection.x);
					viewer.getDocument().replace(modelOffset, 0, text);
				} catch (BadLocationException e) {
					return;
				}
				textWidget.setSelectionRange(newSelection.x, text.length());
			}
		} finally {
			fTextDragAndDropToken= null;
		}

	}

	
	/**
	 * Opens a dropped file in the CFMLEditor
	 * 
	 * @param event
	 */
	private void handleFileDrop(DropTargetEvent event) {
		/*
		 * Changing this method to handle files dropped on the text editor to 
		 * be linked, so files with the extension .css and .js for example create linked style and script blocks
		 * 
		 * Will also try and handle images, so an image is dropped. If it gets extended, there might be a need to get a bit clever
		 * and call different classes to handle info
		 * 
		 */
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		String files[] = (String[])event.data;
		
		for(int i=0; i < files.length; i++){
			File dropped = new File(files[i]);
			//IPath dPath = new IPath();
			
			if(dropped.isFile()){
				String currentpath = ( (IResource) ((FileEditorInput)editor.getEditorInput()).getFile() ).getLocation().toString();
				File target = new File(currentpath);
				
				String relPath = "";
				
				try {
					relPath = ResourceUtils.getRelativePath(target, dropped);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//Figure out the relative paths
				RelativePath relpather = new RelativePath();
				
				relPath =  relpather.getRelativePath(target, dropped);
				
				
//				System.out.println("Dropped File: " + dropped.getAbsolutePath());
//				System.out.println("Target File:  " + target.getAbsolutePath());
//				System.out.println("Relative path:" + relPath);
				
				InsertFileLink ifl = new InsertFileLink(dropped, relPath, editor);
				ifl.run();
		
			}
		}
		
	    Object result = fileTransfer.nativeToJava(event.currentDataType);
	    
	    String[] filenames = (String[])result;
	    /*System.out.println("The File " + filenames[0]);
	    
		IFile thisFile = root.getFile(new Path(filenames[0]));
		//File dropped = (File)root.getFile(new Path(filenames[0]));
	    String droppedPath = thisFile.getRawLocation().toString();
	    System.out.println("Dropped Path " + droppedPath);
		String targetPath = (	(IResource)((FileEditorInput)editor.getEditorInput()).getFile()).getRawLocation().toString();
	    	System.out.println("The path : " + targetPath);
	    
		//File target = (File)((FileEditorInput)editor.getEditorInput()).getFile();
				//System.out.println(	ResourceUtils.getRelativePath(dropped, target).toString());
			
	   
	
	  
	    for (int i=0;i<filenames.length;i++) {
	    		
	        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	        //JavaFileEditorInput input = new JavaFileEditorInput(new File(filenames[i]));
	        try {
	        	System.out.println("Actually doing the drop "  + filenames[1]);
	        	//page.openEditor(input,"org.cfeclipse.cfml.editors.CFMLEditor");
	        }
	        catch (Exception e) {
	        	System.out.println("There has been an error of type: " + e.getMessage());
	            //e.printStackTrace();
	        }
	    }
	    return;
	    /*
	    // TODO: Spike - This currently doesn't work as far as I know.
	    String[] files = (String[]) event.data;
		
		org.cfeclipse.cfml.editors.actions.GenericOpenFileAction
		gofa = new org.cfeclipse.cfml.editors.actions.GenericOpenFileAction();
		
		for(int i = 0; i < files.length; i++) {
			gofa.setFilename(files[i]);
			gofa.run();
		}
		*/
	}
	
	/**
	 * Opens a dropped resource in the CFMLEditor
	 * 
	 * @param event
	 */
	private void handleResourceDrop(DropTargetEvent event) {
		/*
		 * Changing this method to handle files dropped on the text editor to 
		 * be linked, so files with the extension .css and .js for example create linked style and script blocks
		 * 
		 * Will also try and handle images, so an image is dropped. If it gets extended, there might be a need to get a bit clever
		 * and call different classes to handle info
		 * 
		 */
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        Assert.isTrue(event.data instanceof IResource[]);
        IResource[] files = (IResource[]) event.data;
        for (int i = 0; i < files.length; i++) {
            if (files[i] instanceof IFile) {
                IFile file = (IFile) files[i];
                
                if (!file.isPhantom()) {
                	//do stuff                	
                }
            }
        }
	}
	
	/**
	 * Opens a dropped selection in the CFMLEditor
	 * 
	 * @param event
	 */
	private void handleLocalSelectionDrop(DropTargetEvent event) {
		/*
		 * Changing this method to handle files dropped on the text editor to be
		 * linked, so files with the extension .css and .js for example create
		 * linked style and script blocks
		 * 
		 * Will also try and handle images, so an image is dropped. If it gets
		 * extended, there might be a need to get a bit clever and call
		 * different classes to handle info
		 */
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		ISelection selection = (ISelection) event.data;
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection) selection;
			IAdaptable firstElement = (IAdaptable) treeSelection.getFirstElement();
			IFile file = (IFile) firstElement.getAdapter(IFile.class);
			if (file != null && file.isAccessible()) {
				File dropped = file.getLocation().toFile();
				String currentpath = ((IResource) ((FileEditorInput) editor.getEditorInput()).getFile()).getParent().getLocation()
						.toString();
				File target = new File(currentpath);

				String relPath = "";

				relPath = file.getProjectRelativePath().toString();
				RelativePath relpather = new RelativePath();

				relPath = relpather.getRelativePath(target, dropped);

				// System.out.println("Dropped File: " +
				// dropped.getAbsolutePath());
				// System.out.println("Target File:  " +
				// target.getAbsolutePath());
				// System.out.println("Relative path:" + relPath);

				InsertFileLink ifl = new InsertFileLink(dropped, relPath, editor);
				ifl.run();
			}
			System.err.println(treeSelection.getPaths()[0].toString());

		}
		// Object result =
		// localSelectionTransfer.nativeToJava(event.currentDataType);
	}


	String fSelectedText;
	private CFEDragDropListener fTextDragAndDropToken;

	public void dragStart(DragSourceEvent event) {
		fTextDragAndDropToken= null;
		try {
			fSelection= textWidget.getSelection();
			event.doit= isLocationSelected(new Point(event.x, event.y));

			ISelection selection= editor.getSelectionProvider().getSelection();
			if (selection instanceof ITextSelection)
				fSelectedText= ((ITextSelection)selection).getText();
			else // fallback to widget
				fSelectedText= textWidget.getSelectionText();
		} catch (IllegalArgumentException ex) {
			event.doit= false;
		}
	}

	private boolean isLocationSelected(Point point) {
		// FIXME: https://bugs.eclipse.org/bugs/show_bug.cgi?id=260922
		if (((AbstractTextEditor) editor).isBlockSelectionModeEnabled())
			return false;

		int offset= textWidget.getOffsetAtLocation(point);
		Point p= textWidget.getLocationAtOffset(offset);
		if (p.x > point.x)
			offset--;
		return offset >= fSelection.x && offset < fSelection.y;
	}

	public void dragSetData(DragSourceEvent event) {
		event.data= fSelectedText;
		fTextDragAndDropToken= this; // Can be any non-null object
	}

	public void dragFinished(DragSourceEvent event) {
		try {
			if (event.detail == DND.DROP_MOVE && ((AbstractTextEditor) editor).validateEditorInputState()) {
				Point newSelection= textWidget.getSelection();
				int length= fSelection.y - fSelection.x;
				int delta= 0;
				if (newSelection.x < fSelection.x)
					delta= length;
				textWidget.replaceTextRange(fSelection.x + delta, length, ""); //$NON-NLS-1$

				if (fTextDragAndDropToken == null) {
					// Move in same editor - end compound change
					IRewriteTarget target= (IRewriteTarget)editor.getAdapter(IRewriteTarget.class);
					if (target != null)
						target.endCompoundChange();
				}

			}
		} finally {
			fTextDragAndDropToken= null;
		}
	}

	public void dragLeave(DropTargetEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void dropAccept(DropTargetEvent event) {
		// TODO Auto-generated method stub
		
	}

	
	
}