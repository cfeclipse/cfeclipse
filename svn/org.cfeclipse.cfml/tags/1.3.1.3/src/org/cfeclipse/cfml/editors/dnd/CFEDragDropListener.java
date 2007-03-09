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

import org.cfeclipse.cfml.editors.actions.InsertFileLink;
import org.cfeclipse.cfml.util.RelativePath;
import org.cfeclipse.cfml.util.ResourceUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Stephen Milligan
 *
 * This class enables text drag and drop both 
 * within the editor and to and from it.
 * 
 */
public class CFEDragDropListener  implements DropTargetListener, DragSourceListener {

    /**
     * The text editor on which the listener is installed.
     */
    private ITextEditor editor = null;
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
    /**
     * This indidates whether or not the drag is internal.
     */
    private boolean isInternalDrag = true;
    
    /**
     * This constructor sets up the editor, text widget viewer and cursor listeners
     * so that they are available to future method calls.
     * 
     */
    public CFEDragDropListener(ITextEditor editor, ProjectionViewer viewer, SelectionCursorListener cursorListener) {
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

    /**
     * This event is fired when a drag event enter the 
     * text widget area (The editor area is the same as 
     * the text widget area).
     * 
     * It sets up the cursor indicator for copy,move or none
     * 
     */
    public void dragEnter(DropTargetEvent event) {
        /*
		if(event.detail == DND.DROP_DEFAULT) {
			if ((event.operations & DND.DROP_MOVE) != 0 
			        ||(event.operations & DND.DROP_COPY) != 0) {
			    if (isCopy) {
			        event.detail = DND.DROP_COPY;
			    }
			    else {
			        event.detail = DND.DROP_MOVE;
			    }
			}
			else {
			    event.detail = DND.DROP_NONE;
			}
		}
		*/
        
    }
	
    /**
     * This event is fired when the mouse button is down and
     * a drag is in progress.
     * 
     * It checks to see if the cursor needs to move, if
     * the viewer needs to scroll, if a drop is valid on
     * the current mouse position, and acts as appropriate.
     * 
     */
	public void dragOver(DropTargetEvent event) {
	    try {
			event.feedback = DND.FEEDBACK_SELECT | DND.FEEDBACK_SCROLL;
			
			if (textTransfer.isSupportedType(event.currentDataType)) {
				//NOTE: on unsupported platforms this will return null
			    
				//String t = (String)(textTransfer.nativeToJava(event.currentDataType));
				
				//if(t != null) {
				    // Convert the display co-ordinates to text widget co-ordinates
				    
			        Point mousePosition = textWidget.toControl(event.x,event.y);
			        
				    int widgetOffset = widgetPositionTracker.getWidgetOffset(mousePosition);
				    int viewerOffset = viewer.widgetOffset2ModelOffset(widgetOffset);
				   
				    widgetPositionTracker.doScroll(mousePosition);
				    
				    
				    // Make sure we don't allow text to be dropped onto itself
				    if (viewerOffset > cursorListener.selectionStart 
				            && viewerOffset < cursorListener.selectionStart + cursorListener.selection.length()) {
				        event.feedback = DND.DROP_NONE;
				    }
				    else if (viewerOffset != lastOffset) {
				        viewer.setSelectedRange(viewerOffset,0);
				        editor.setHighlightRange(viewerOffset,0,true);
					    //editor.setFocus();
				        lastOffset = viewerOffset;
				    }
				//}
			}
	    }
		catch (Exception e ) {
		    e.printStackTrace();
		}
	}
	
	
	public void dragOperationChanged(DropTargetEvent event){ 
	
		//allow text to be moved but files should only be copied
	    /*
	     * TODO Need to implement this for file drag and drop.
		if(fileTransfer.isSupportedType(event.currentDataType)){
			if(event.detail != DND.DROP_COPY) {
				event.detail = DND.DROP_NONE;
			}
		}
		*/
	}
	
	public void dragLeave(DropTargetEvent event){
	    // Can't do much in here because the drag has already left
	}
	
	public void dropAccept(DropTargetEvent event){
	}
	
	public void drop(DropTargetEvent event) {
		try {
			if(textTransfer.isSupportedType(event.currentDataType)) {
				handleTextDrop(event);
			}
			
			if(fileTransfer.isSupportedType(event.currentDataType)){
				handleFileDrop(event);
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
		try
		{
		    String text =  (String)event.data;
		    AnnotationTracker annotationTracker = new AnnotationTracker(viewer);
		    // Figure out where to drop the text
		    TextSelection sel = (TextSelection)viewer.getSelectionProvider().getSelection();
		    // Offset of the drop point in viewer co-ordinates
		    int dropOffset = sel.getOffset();
		    		    
		    // Delete the text from the old location
		    if (!isInternalDrag 
		            && (event.detail & DND.DROP_MOVE) != 0) {
		        // Offset of the selection start in viewer co-ordinates
		        int selectionOffset = cursorListener.selectionStart;
			    int length = cursorListener.selection.length();
			    
			    annotationTracker.createAnnotationList(selectionOffset,length);
			    
		        viewer.getDocument().replace(selectionOffset,length,"");
		        
		        
			    // Update the drop offset to adjust for deleted text.
			    if (selectionOffset < dropOffset) {
			        dropOffset -= length;
			    }
		    }
		    
		    //Drop the text in the cursor location
			viewer.getDocument().replace(dropOffset, 0, text);
		    
			annotationTracker.applyAnnotations(dropOffset);
			
			//System.out.println("Selection dropped");
			
	        sel = new TextSelection(dropOffset,text.length());

			cursorListener.reset();
			
	        viewer.getSelectionProvider().setSelection(sel);
	        //System.out.println("Dropped text re-selected.");
			editor.setFocus();
			isInternalDrag = false;
			lastOffset = -1;

		}
		catch(Exception e)
		{
			e.printStackTrace();
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
	
	
	


    public void dragStart(DragSourceEvent event) {
	  	
        try {
	        if (!cursorListener.doDrag()) {
	            event.doit = false;
	            return;
	        }
	        
	        isInternalDrag = false;
	        

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

    
	public void dragSetData(DragSourceEvent event) {
	    try {
			// Provide the data of the requested type.
			if(TextTransfer.getInstance().isSupportedType(event.dataType)) {
				
			    String selectedText = cursorListener.selection;
	
		        event.data = selectedText;
	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	


	/**
	 * resets the <code>SelectionCursorListener</code> if the drag
	 * finishes without a copy or move.
	 */
	public void dragFinished(DragSourceEvent event) {
	    try {
			if(event.detail == DND.DROP_MOVE){
			    //System.out.println("Data Moved");
			}
			else if(event.detail == DND.DROP_COPY) {
			    //System.out.println("Data Copied");
			}
			// We don't know why it stopped, but we want to reset the listener state. 
			else {
			    cursorListener.reset();
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}