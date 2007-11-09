/*
 * Created on Feb 26, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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

//import org.eclipse.jface.viewers.ISelection;
//import org.eclipse.ui.IEditorActionDelegate;
//import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;

/**
 * @author Rob
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public abstract class WordManipulator {
	
	/**
	 * Gets the word out that they have highlighted
	 * @param doc the document change the text in 
	 * @param sel the current selection
	 */
	public void setControler(IDocument doc, ITextSelection sel)
	{
		try
		{
			if(!sel.isEmpty())
			{
				int offset = sel.getOffset();
				int len  = sel.getLength();
				doc.replace(
					offset, 
					len, 
					manipulate(doc.get(offset,len))
				);
			}
		}
		catch(BadLocationException e)
		{
			e.printStackTrace(System.err);
		}
	}
	
	/**
	 * Over ride this method to change the text before it is adjusted
	 * and return the new string to be added to the document
	 * @param higlighted
	 * @return
	 */
	public abstract String manipulate(String higlighted);
}
