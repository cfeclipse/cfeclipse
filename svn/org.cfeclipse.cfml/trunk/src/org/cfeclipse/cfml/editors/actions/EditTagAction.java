/*
 * Created on Nov 9, 2005
 *
 * The MIT License
 * Copyright (c) 2005 Mark Drew
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
package org.cfeclipse.cfml.editors.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.cfeclipse.cfml.dialogs.TagEditDialog;
import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.dictionary.SyntaxDictionary;
import org.cfeclipse.cfml.dictionary.Tag;
import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.EditableTags;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.partitioner.CFEPartition;
import org.cfeclipse.cfml.editors.partitioner.CFEPartitioner;
import org.cfeclipse.cfml.parser.docitems.AttributeItem;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.cfeclipse.cfml.util.CFDocUtils;
import org.cfeclipse.cfml.views.dictionary.TagFormatter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.texteditor.ITextEditor;


/**
 * This Class is used to call the Edit Tag Dialog and write out the contents to the editor
 * @author mdrew
 *
 */
public class EditTagAction implements IWorkbenchWindowActionDelegate,IEditorActionDelegate{
		protected Tag tag;
		protected Shell shell;
		protected IEditorPart ieditor;
		private ITextEditor editor = null;
		private CFEPartitioner partitioner;
		private Map selectedattributes;
		private boolean replace = true;
		private SyntaxDictionary dictionary;
		private static final Logger logger = Logger.getLogger(EditTagAction.class);
		/*
		 * constructors
		 */
		public EditTagAction(){
			super();
		}

		/**
		 * This  Tag Action needs a tag and a shell. It will setup a a *blank* dialog
		 * @param tag
		 * @param shell
		 */
		/*public EditTagAction(Tag tag, Shell shell) {
			System.out.println("Hello World 1");
			
			this.tag = tag;
			this.shell = shell;
			this.ieditor = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			this.dictionary =  DictionaryManager.getDictionary("CF_DICTIONARY");
		}*/
		
		/** This Tag ACtion needs a tag, a shell and the attibutes of a tag. It will setup a pre-filled dialog
		 * 
		 *//*
		public EditTagAction(Tag tag, Shell shell, Map attributes){
			System.out.println("Hello World 2");
			this.tag = tag;
			this.shell = shell;
			this.ieditor = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			this.selectedattributes = attributes;
			//Since we are passing values, has to be pre-filled
			this.replace = true;
		}
		*/
		public EditTagAction(String tag, Shell shell){
			this.shell = shell;
			//Make sure that this is the way to get the right dictionary for this file
			this.dictionary = DictionaryManager.getDictionary("CF_DICTIONARY");
			this.ieditor = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			this.tag = this.dictionary.getTag(tag);
			
		}
		
		
		public void setActiveEditor(IAction action, IEditorPart targetEditor) {
			
			this.editor = (ITextEditor)targetEditor;
	
			if(targetEditor != null){
			IDocument doc = editor.getDocumentProvider().getDocument(
					editor.getEditorInput());

			ICFDocument cfd = (ICFDocument) doc;

			this.partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
			}
		}
		
		
		
		public void setTagPosition(int start, int len){
		//	this.tagstart = start;
		//	this.taglength = len;
		}
				
	
		
	//Called from the keybinding	
	public void run(IAction action) {
			
		run();
	}

	//called directly from right click
	public void run(){
	
		//Get the editor, and the various bits we need such as the document, the selection and the shell
		IEditorPart activeEditor = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		ITextEditor thisEdit = (ITextEditor)activeEditor;
		IDocument doc =  thisEdit.getDocumentProvider().getDocument(thisEdit.getEditorInput());
		ISelection sel = thisEdit.getSelectionProvider().getSelection();
		final ITextSelection textSelection = (ITextSelection) thisEdit.getSelectionProvider().getSelection();
		Shell shell = activeEditor.getEditorSite().getShell();
		ICFDocument cfd = (ICFDocument) doc;
		CFEPartitioner partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
		
		int selstart = textSelection.getOffset();
		
		//Find the closest partition, so that we can then get the start and end of the START tag (where all the attributes are)	
		CFEPartition currentPartition = partitioner.findClosestPartition(selstart);
		
		if (currentPartition == null) {
			return; //Just quit, we dont know where we are in the document
        }
		
		if (currentPartition.isMidPartition() || currentPartition.isEndPartition()) {
			  CFEPartition prevPartition = partitioner.getPreviousPartition(currentPartition.getOffset());
	            while (prevPartition != null && (!prevPartition.isStartPartition())) {
	                prevPartition = partitioner.getPreviousPartition(prevPartition.getOffset());
	            }
		 
	            currentPartition = prevPartition;
	  }
		
		/*
		 * Found the start, now find the end
		 * This is done by looping, getting the next partition until we find the close of the tag
		 */
		 String tagName = currentPartition.getTagName();
	      int stackDepth = 0;

		CFEPartition endPartition = partitioner.getNextPartition(currentPartition.getOffset());
		
		/*
		 * Find the ending of the start tag
		 */
		while(endPartition!=null){
            if (endPartition.isStartPartition() && currentPartition.getTagName().equalsIgnoreCase(endPartition.getTagName())) {
              //  System.out.println("Encountered nested start tag before end tag");
                stackDepth++;
                endPartition = partitioner.getNextPartition(endPartition.getOffset());
                continue;
            }
            if (stackDepth > 0) {
               // System.out.println("Found match for nested tag; removing from stack");
                stackDepth--;
                endPartition = partitioner.getNextPartition(endPartition.getOffset());
                continue;
            }
            if (endPartition.isCloser() && tagName.equalsIgnoreCase(endPartition.getTagName())) {
                     break;
            }
           break;
		}
		
		
		/*
		 * Set the selection
		 */
		int tagStart = currentPartition.getOffset();
		int tagEnd = endPartition.getOffset() + endPartition.getLength() - currentPartition.getOffset()+1;
		
		TextSelection selection = new TextSelection(tagStart, tagEnd);
		ISelectionProvider selectionProvider = thisEdit.getSelectionProvider();
		selectionProvider.setSelection(selection);
		
		
		/*
		 * Get the actual textual content of the tag
		 */
		String tagText = "";
		try {
			
			tagText = doc.get(selection.getOffset(), selection.getLength());
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		 * Get the attributes as a map
		 * TODO: I am not sure if map's keep the ordering... need to check this
		 */
		Map attributeMap = CFDocUtils.parseStartTag(currentPartition.getTagName(), tagText);

		
		/*
		 * Now open the TagEditDialog, we know the tag name (as a string) and the tag attributes (as a map)
		 * So, we go and get the tag from the dictionary
		 */
		Tag tagToEdit = DictionaryManager.getDictionary("CF_DICTIONARY").getTag(currentPartition.getTagName());
				
		
		/*
		 * Setup the tageditor dialog
		 */
		TagEditDialog tagview = new TagEditDialog(shell, tagToEdit);
		tagview.setSelectedattributes(attributeMap);
			
						
			/*
			 * Once the editor closes, we do this
			 */
			if(tagview.open() == IDialogConstants.OK_ID){
				Properties fieldStore = tagview.getFieldStore();  	//The new items
				ArrayList propOrder = new ArrayList();				//The order of the itmes
				Properties attributesToRender = new Properties();	//The attributes that we are going to be formatting
				
				//Put the original attributes in
				Set oldFieldSet = attributeMap.keySet();
				for (Iterator iter = oldFieldSet.iterator(); iter.hasNext();) {
					String oldElement = (String) iter.next();
					propOrder.add(oldElement);
					
				}
				attributesToRender.putAll(attributeMap);

				//Loop through the new ones
				Set newFieldsSet = fieldStore.keySet();
				for (Iterator iter = newFieldsSet.iterator(); iter.hasNext();) {
					String element = (String) iter.next();
					
					if(attributesToRender.containsKey(element)){
						attributesToRender.setProperty(element, fieldStore.getProperty(element));
					}
					else{
						propOrder.add(element);
						attributesToRender.put(element, fieldStore.getProperty(element));
					}
				}
				
			
				
				/*
				 * Pass in the attributes into a Tag Formatter
				 */
				TagFormatter tf = new TagFormatter(tagToEdit, attributesToRender, propOrder);
				
				//Here is where we actually do the insertion
				
					if(thisEdit instanceof ITextEditor){
						try {
							cfd.replace(selection.getOffset(), selection.getLength(), tf.getTagStart());
						} catch (BadLocationException e) {
							e.printStackTrace();
						}
						thisEdit.setFocus();
					}
			}

	}

	public ITextEditor getEditor() {
		return editor;
	}

	public void setEditor(ITextEditor editor) {
		this.editor = editor;
	}

	public IEditorPart getIeditor() {
		return ieditor;
	}

	public void setIeditor(IEditorPart ieditor) {
		this.ieditor = ieditor;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	//public void run(IAction action) {
		//TODO: THIS IS WHAT IS CALLED FROM A KEY BINDING!
		//to run we want to set:
			// ieditor
			// shell
			// tag
			// dictionary
			// maybe selected items
		
		
		/* Code from right click action ... 
		 * 
		 * 	int startpos = sel.getOffset();
					//Find the length just in case
					int len = Math.max(sel.getLength(),1);
					
					//default start and end are at the cursor
					int startoftag = sel.getOffset();
					int endoftag = sel.getOffset();
					int lengthoftag = endoftag - startoftag;
					
					try {
						startoftag = doc.search(startpos, "<", false, true, false);
						endoftag = doc.search(startpos, ">", true, true, false);
						lengthoftag = endoftag - startoftag + 1;
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					TextSelection selection = new TextSelection(startoftag, lengthoftag);
					editor.getSelectionProvider().setSelection(selection);
					TextSelection seli = (TextSelection)editor.getSelectionProvider().getSelection();
					
					//Now we have the whole start tag, we can then pass the tagname and 
					CFEPartitioner partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
					CFEPartition part = partitioner.findClosestPartition(startpos);
					
					
					Map tagattribs = CFDocUtils.parseStartTag(part.getTagName(), seli.getText());
					//find you which dictionary this belongs to!
				
					Tag tag = null;
					SyntaxDictionary dic = EditableTags.getDictionary(part.getType());
					
		 * 
		 * 
		 */
		
		//Get the editor, then get the rest of the stuff;....
		/*
		
		 System.out.println(action);
		
		this.dictionary = DictionaryManager.getDictionary("CF_DICTIONARY");
		
		this.shell = this.ieditor.getSite().getShell();
		
		
		ITextEditor thisEdit = (ITextEditor)this.ieditor;
		IDocument doc =  thisEdit.getDocumentProvider().getDocument(this.ieditor.getEditorInput());
		ISelection sel = thisEdit.getSelectionProvider().getSelection();
		ICFDocument cfd = (ICFDocument) doc;
		final ITextSelection textSelection = (ITextSelection) thisEdit.getSelectionProvider().getSelection();
		
		int selstart = textSelection.getOffset();
		
		// We dont need the length... the cursor is within a tag (hopefully) if it is not, then we just escape....
		// 
		// int selectionLength = ((ITextSelection) sel).getLength();
		
		
		
		CFEPartitioner partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
		CFEPartition[] startTagPartitions = partitioner.getStartTagPartitions(selstart);
		for (int i = 0; i < startTagPartitions.length; i++) {
			System.out.println("Getting the partition types: " + startTagPartitions[i].getType());
		}
		
		
		
		CFEPartition part = partitioner.findClosestPartition(selstart);
		
		
		
		
		
//		default start and end are at the cursor
	
		
		//find the start and end of a tag from where we are... we could use the partitioner
		
		
	
		
		System.err.println("the partition type " + part.getType());
		System.err.println("the partition name " + part.getTagName());
		this.tag = this.dictionary.getTag(part.getTagName());
		*/


	//	run();
	//}

	public void selectionChanged(IAction action, ISelection selection){
		// ugly hack that verifies we only try to work with a CF file.  Something is wrong upstream, we should not need this
		if( editor != null) {
			IEditorPart activeEditor = editor.getSite().getPage().getActiveEditor();
			if(activeEditor != null && activeEditor instanceof CFMLEditor){
				setActiveEditor(null,  editor.getSite().getPage().getActiveEditor());
			}
		}
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbenchWindow window) {
		IEditorPart activeEditor = window.getActivePage().getActiveEditor();
		if(activeEditor instanceof CFMLEditor){
			editor = (ITextEditor)activeEditor;
		}
		
	}
	

}
