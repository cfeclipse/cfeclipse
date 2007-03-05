/*
 * Created on 22-Feb-2006
 *
 * The MIT License
 * Copyright (c) 2006 markd
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

import java.io.File;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author markd
 *
 */
public class InsertFileLink extends GenericEncloserAction{
	private IDocument doc;
	private ISelection sel;
	private Encloser encloser;
	private String path;
	private ITextEditor editor;
	private File dropFile;
	
	public InsertFileLink(File file, String path, ITextEditor editor ){
		
			this.dropFile = file;
			this.editor = editor;
			this.doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput()); 
			this.sel = editor.getSelectionProvider().getSelection();
			this.encloser = new Encloser();
			this.path = path.replaceAll("\\\\", "/");
			
			

	}
	public void run(){
		String start = "";
		String end = "";
		
		//reverse the string?
		
		
		if(editor != null && editor.isEditable()){
			
//			if(path.toLowerCase().endsWith("cfm")){
//				start ="<cfinclude template=\"" + path + "\"/>";
//			}
//			else 
				if(path.toLowerCase().endsWith("js")){
				start ="<script language=\"javascript\" src=\"" + path +  "\"></script>\n" ;
				
			}
			else if(path.toLowerCase().endsWith("css")){
				start ="<link rel=\"stylesheet\" type=\"text/css\" href=\"" + path +  "\"/>\n" ;
				
			}
			else if(path.toLowerCase().endsWith("jpg") || path.toLowerCase().endsWith("gif") || path.toLowerCase().endsWith("png")){
				//Could we get the size?
				Image thisImg = new Image(null, this.dropFile.getAbsolutePath());
				int width = thisImg.getImageData().width;
				int height = thisImg.getImageData().height;
				
				start ="<img alt=\"\" width=\""+width+"\" height=\""+height+"\" src=\"" + path +  "\"/>\n" ;
				
			}
			else{
			 //we shall default to a link then!
			 start ="<a href=\"" + path + "\">";
			 end="</a>";
			}
			 encloser.enclose(doc, (ITextSelection)sel, start, end);
		 		
		}
		
	}

}
