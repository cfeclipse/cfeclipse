/*
 * Created on Oct 19, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.editors.codefolding;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;


import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.ui.texteditor.ITextEditor;


import com.rohanclan.cfml.editors.CFPartitionScanner;
import com.rohanclan.cfml.editors.ICFDocument;
import com.rohanclan.cfml.preferences.CFMLPreferenceManager;


import com.rohanclan.cfml.parser.*;
import com.rohanclan.cfml.parser.docitems.*;

/**
 * @author Stephen Milligan
 * 
 * This class is used to set the code folding markers.
 */
public class CodeFoldingSetter {

    private ITextEditor editor;
    
    private HashMap snapshot = null;
    
    private ProjectionAnnotationModel model = null;
    
    private ICFDocument doc = null;
    
    private CFMLPreferenceManager preferenceManager = null;

    public CodeFoldingSetter(ITextEditor editor) {
        this.editor = editor;
        model = (ProjectionAnnotationModel) editor
        .getAdapter(ProjectionAnnotationModel.class);
        doc = (ICFDocument)editor.getDocumentProvider().getDocument(editor.getEditorInput());
        preferenceManager = new CFMLPreferenceManager();
    }

    public synchronized void docChanged(boolean autoCollapse) {
        if (preferenceManager.enableFolding()) {
            addMarksToModel(autoCollapse);
        }
    }

    /**
     * @param root
     * @param model
     */
    private void addMarksToModel(boolean autoCollapse) {
        try{
	        if (model != null) {
	            
	            // We need this to keep track of what should be collapsed once we've added all the markers.
	            HashMap markerMap = new HashMap();
	            
	            scrubAnnotations();

	            if (preferenceManager.foldCFMLComments()) {
	                foldPartitions(markerMap,CFPartitionScanner.HTM_COMMENT, preferenceManager.collapseCFMLComments() && autoCollapse,preferenceManager.minimumFoldingLines()-1);
	            }
	            if (preferenceManager.foldHTMLComments()) {
	                foldPartitions(markerMap,CFPartitionScanner.CF_COMMENT,preferenceManager.collapseHTMLComments() && autoCollapse,preferenceManager.minimumFoldingLines()-1);
	            }
	            
	            for (int i=1;i<9;i++) {
	                //System.out.println("Checking " + preferenceManager.foldingTagName(i).trim());
	                if (preferenceManager.foldTag(i) 
	                        && preferenceManager.foldingTagName(i).trim().length() > 0) {
	                    foldTags(markerMap,preferenceManager.foldingTagName(i).trim(), preferenceManager.collapseTag(i)&& autoCollapse, preferenceManager.minimumFoldingLines()-1);
	                }
	            }
	            
	            // Now collapse anything that should be collapsed
	            Iterator x = markerMap.keySet().iterator();
	    		
	            while(x.hasNext()) {
	                ProjectionAnnotation p = (ProjectionAnnotation)x.next();
	                boolean collapsed = ((Boolean)markerMap.get(p)).booleanValue();
	                if (collapsed) { 
	                    model.collapse(p);
	                }
	            }
	            
	            
	        }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    private void foldTags(HashMap markerMap, String tagName, boolean autoCollapse, int minLines) {
        DocItem rootItem = null;
        try {
            rootItem = doc.getCFDocument().getDocumentRoot();
        }
        catch (NullPointerException e) {
            //e.printStackTrace();
            System.out.println("CodeFoldingSetter::foldTags got a null from doc.getCFDocument().");
            return;
        }
		//nodes = rootItem.selectNodes("//function[#startpos>=0 and #endpos < 200]");
        CFNodeList nodes = rootItem.selectNodes("//" + tagName.toLowerCase());
        
        Iterator it = nodes.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof CfmlTagItem) {
                CfmlTagItem tag = (CfmlTagItem)o;

               
                    int start = tag.getStartPosition();
                    int length = tag.matchingItem.getEndPosition() - start;
                    try {
                        int startLine = doc.getLineOfOffset(start);
                        int endLine = doc.getLineOfOffset(start+length);
                        start = doc.getLineOffset(startLine); 
                        length = doc.getLineOffset(endLine) + doc.getLineLength(endLine) - start;
                        if (endLine - startLine > minLines) {
                            
                            addFoldingMark(markerMap, start,length,new CommentProjectionAnnotation(), autoCollapse);
                        }
                    }
                    catch (BadLocationException blx) {
                        
                    }
               
            }
        }
    }
    
    
    
    
    private void foldPartitions(HashMap markerMap, String partitionType, boolean autoCollapse,int minLines) {
        // This will hold the regions that should have folding markers.
        ArrayList regions = new ArrayList();
        
	    IDocumentPartitioner partitioner = doc.getDocumentPartitioner();
		ITypedRegion[] regionArray  = partitioner.computePartitioning(0,doc.getLength());
		
        for (int i = 0;i<regionArray.length;i++) {
		    ITypedRegion region = regionArray[i];
		    if (region.getType() == partitionType) {
		        Position position= new Position(region.getOffset(), region.getLength());
				regions.add(region);
		        
		    }
		}
		
        foldRegions(markerMap, regions, autoCollapse, minLines);
    }
    
    
    private void scrubAnnotations() {
        Iterator iter = model.getAnnotationIterator();
        
        while (iter.hasNext()) {
            Object o = iter.next();
            ProjectionAnnotation annotation = (ProjectionAnnotation)o;
            if (!(annotation instanceof CustomProjectionAnnotation)
                    && !annotation.isCollapsed()) {
                Position p = model.getPosition(annotation);
                try {
	                int startLine = doc.getLineOfOffset(p.getOffset());
	                int endLine = doc.getLineOfOffset(p.getOffset() + p.getLength());
	                if (endLine - startLine <= 2) {
	                    model.removeAnnotation(annotation);
             	  }
                }
                catch (BadLocationException e ) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    
    /**
     *  
     * @param regions
     * @param model
     * @param doc
     */
    private void foldRegions(HashMap markerMap, ArrayList regions, boolean autoCollapse, int minLines) {
        int i=0;

        try {
            
            for (Iterator iter = regions.iterator(); iter.hasNext();++i) {
                
                ITypedRegion element = (ITypedRegion) iter.next();
                // Find the start and end lines of the region
                
                int start = element.getOffset();
                int length  = element.getLength();
                int startLine = doc.getLineOfOffset(start);
                int endLine = doc.getLineOfOffset(start+length);
                // Make sure our position starts at the start of the line
                start = doc.getLineOffset(startLine);
                
                if (endLine - startLine > minLines) {
	                try {
	                    CommentProjectionAnnotation annotation = new CommentProjectionAnnotation();
	                    addFoldingMark(markerMap, start, length, annotation, autoCollapse);
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
    public void addFoldingMark(HashMap markerMap, int start, int length, ProjectionAnnotation annotation, boolean autoCollapse) throws BadLocationException {

        	if(!preferenceManager.enableFolding()) {
        	    return;
        	}
        
            Position position = new Position(start, length);
            
            Iterator i = model.getAnnotationIterator();

		    
            while (i.hasNext()) {
                Object o = i.next();
                Position thisPosition = model.getPosition((ProjectionAnnotation)o);

                if (thisPosition.offset == position.offset) {
    		        
                    return;
                }
            }
            markerMap.put(annotation,new Boolean(autoCollapse));
            model.addAnnotation(annotation, position);
            
            
    }
    
    
    public void addFoldToSelection(boolean collapse) {
        if(!preferenceManager.enableFolding()) {
    	    return;
    	}
		initModel();
		
		HashMap markerMap = new HashMap();
		
		ITextSelection textSelection = getSelection();
		
		if (!textSelection.isEmpty()) {
			int start= textSelection.getStartLine();
			int end= textSelection.getEndLine();
			if (start < end)
			try {
				
				int offset= doc.getLineOffset(start);
				int endOffset= doc.getLineOffset(end + 1);
				
				addFoldingMark(markerMap, offset,endOffset-offset,new CustomProjectionAnnotation(collapse), collapse);
				
			} catch (BadLocationException x) {
			}
		}
		
    }
    
    
    public void removeFoldFromSelection() {
        if(!preferenceManager.enableFolding()) {
    	    return;
    	}
        initModel();
		
		ITextSelection textSelection = getSelection();
		
		if (!textSelection.isEmpty()) {
		    try {
				int start = doc.getLineOffset(textSelection.getStartLine());
				int end = start + textSelection.getLength();
				
				Iterator i = model.getAnnotationIterator();
				while (i.hasNext()) {
				    ProjectionAnnotation annotation = (ProjectionAnnotation)i.next();
				    if (annotation instanceof CustomProjectionAnnotation) {
				       
				        Position position = model.getPosition(annotation);
				        
			            if (position.offset >= start 
			                    && position.offset <= end) {
			                model.removeAnnotation(annotation);
			            }
				    }
				    
				}
		    }
		    catch (BadLocationException bex) {
		        bex.printStackTrace();
		    }
			
		}
		
    }

    
    public void expandSelection() {
        
        if(!preferenceManager.enableFolding()) {
    	    return;
    	}
        
        initModel();
        
        ITextSelection textSelection = getSelection();
        
        if (!textSelection.isEmpty()) {
		    try {
				int start = doc.getLineOffset(textSelection.getStartLine());
				int end = start + textSelection.getLength();
				
				Iterator i = model.getAnnotationIterator();
				while (i.hasNext()) {
				    ProjectionAnnotation annotation = (ProjectionAnnotation)i.next();
			      
			        Position position = model.getPosition(annotation);
			        
		            if (position.offset >= start 
		                    && position.offset <= end) {
		                model.expand(annotation);
		            }
			    
				    
				}
		    }
		    catch (BadLocationException bex) {
		        bex.printStackTrace();
		    }
			
		}
        
    }

    public void toggleSelection() {
        
        if(!preferenceManager.enableFolding()) {
    	    return;
    	}
        
        initModel();
        
        Boolean collapsing = null;
        

        
        ITextSelection textSelection = getSelection();
        int cursorOffset = textSelection.getOffset();
        int selectionLength = textSelection.getLength();
        
        if (!textSelection.isEmpty()) {
		    try {
				int start = doc.getLineOffset(textSelection.getStartLine());
				int end = start + textSelection.getLength();
				
				Iterator i = model.getAnnotationIterator();
				while (i.hasNext()) {
				    ProjectionAnnotation annotation = (ProjectionAnnotation)i.next();
				    
			        Position position = model.getPosition(annotation);

		            if (position.offset >= start 
		                    && position.offset <= end) {

					    if (collapsing == null) {
					        if (annotation.isCollapsed()) {
					            collapsing = new Boolean(false);
					        }
					        else {
					            collapsing = new Boolean(true);
					        }
					        //System.out.println("Collapsing set to " + collapsing.booleanValue());
					    }
		                if (collapsing.booleanValue()) {
		                   model.collapse(annotation);
		                   setSelection(position.offset-1,0);
		                }
		                else {
		                    model.expand(annotation);
		                }
		            }
			    
				    
				}
		    }
		    catch (BadLocationException bex) {
		        bex.printStackTrace();
		    }
			
		}
        
    }

    
    public void collapseSelection() {
        
        if(!preferenceManager.enableFolding()) {
    	    return;
    	}
        
        initModel();
        
        ITextSelection textSelection = getSelection();
        
        if (!textSelection.isEmpty()) {
		    try {
				int start = doc.getLineOffset(textSelection.getStartLine());
				int end = start + textSelection.getLength();
				
				Iterator i = model.getAnnotationIterator();
				while (i.hasNext()) {
				    ProjectionAnnotation annotation = (ProjectionAnnotation)i.next();
				       
			        Position position = model.getPosition(annotation);
			        
		            if (position.offset >= start 
		                    && position.offset <= end) {
		                model.collapse(annotation);
		            }
				    
				    
				}
		    }
		    catch (BadLocationException bex) {
		        bex.printStackTrace();
		    }
			
		}
    }
    
    
    private ITextSelection getSelection() {
        ITextSelection selection= (ITextSelection)editor.getSelectionProvider().getSelection();
		ITextSelection textSelection= (ITextSelection) selection;
		return textSelection;
    }
    
    
    private void setSelection(int offset,int length) {
        TextSelection selection = new TextSelection(offset,length);
		editor.getSelectionProvider().setSelection(selection);
		
    }
    
    
    public void takeSnapshot() {
        
        if(!preferenceManager.enableFolding()) {
    	    return;
    	}
        
        snapshot = new HashMap();
        ProjectionAnnotationModel model = (ProjectionAnnotationModel) editor
        .getAdapter(ProjectionAnnotationModel.class);
        Iterator iter = model.getAnnotationIterator();
        while (iter.hasNext()) {
            ProjectionAnnotation p = (ProjectionAnnotation)iter.next();
            snapshot.put(p,new Boolean(p.isCollapsed()));
        }
    }
    
    
    public void restoreSnapshot() {
        
        if(!preferenceManager.enableFolding()) {
    	    return;
    	}
        
        if (this.snapshot != null) {
	        ProjectionAnnotationModel model = (ProjectionAnnotationModel) editor
	        .getAdapter(ProjectionAnnotationModel.class);
	        Iterator iter = model.getAnnotationIterator();
	        while (iter.hasNext()) {
	            ProjectionAnnotation p = (ProjectionAnnotation)iter.next();
	            if (((Boolean)snapshot.get(p)).booleanValue()) {
	                model.collapse(p);
	            }
	            else {
	                model.expand(p);
	            }
	            
	        }
        }
    }
    
    
    private void initModel() {
        if (model == null) {
        model = (ProjectionAnnotationModel) editor.getAdapter(ProjectionAnnotationModel.class);
        }
    }
    
    
    

	
}