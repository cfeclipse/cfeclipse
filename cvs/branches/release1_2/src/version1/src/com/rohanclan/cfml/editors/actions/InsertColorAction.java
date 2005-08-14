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
package com.rohanclan.cfml.editors.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Rob
 *
 * Inserts a color using the systems color control
 */
public class InsertColorAction extends WordManipulator implements IEditorActionDelegate {
	
	protected ITextEditor editor = null;
	/** the actual color control, this might not be good as static on all OSs
	 * but works pretty well on the Mac... keep an eye...
	 */
	protected static ColorDialog colordialog = null;
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) 
	{
		if( targetEditor instanceof ITextEditor )
		{
			editor = (ITextEditor)targetEditor;
		}
	}
	
	/**
	 * this gets called for every action
	 */
	public void run(IAction action) 
	{
		if(editor != null  && editor.isEditable())
		{
			//get the document and selection and pass it to the word manipulator
			//so it can extract and rewrite what we want (super class)
			IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput()); 
			ITextSelection sel = (ITextSelection)editor.getSelectionProvider().getSelection();
			this.setControler(doc,sel);
		}
	}
	
	/**
	 * Tries to use the input as a hex color triplet and set the color control 
	 * to the passed in color, and will return any color selection made with the 
	 * color control in a hex triplet (no hashes included)
	 */
	public String manipulate(String highlighted)
	{
		//this could be a selected color
		String hexstr = highlighted;
		
		//Shell shell = this.editor.getSite().getShell();
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		
		if(shell != null)
		{
			if(colordialog == null){
				colordialog = new ColorDialog(shell);
			}
			
			RGB startrgb = guessRGBColor(highlighted);
			
			if(startrgb != null){
				colordialog.setRGB(startrgb);
			}
						
			colordialog.open();
			
			RGB selectedcolor = colordialog.getRGB();
			if(selectedcolor != null)
			{
				hexstr = ensureTwoDigits(Integer.toHexString(selectedcolor.red)) 
					+ ensureTwoDigits(Integer.toHexString(selectedcolor.green))
					+ ensureTwoDigits(Integer.toHexString(selectedcolor.blue));
			}
		}else{
			System.err.println("Shell is null for some reason");
		}
		return hexstr;
	}
	
	/**
	 * Tries to make an RGB object out of a hex string. Retruns null if it fails
	 * @param input
	 * @return
	 */
	private RGB guessRGBColor(String input){
		try{
			//only try this if it looks somewhat like a color
			if(input.length() >= 6)
			{
				String red 	= input.substring(0,2);
				String green = input.substring(2,4);
				String blue 	= input.substring(4,6);
				
				RGB rgb = new RGB(
					Integer.parseInt(red,16),
					Integer.parseInt(green,16),
					Integer.parseInt(blue,16)
				);
				
				return rgb;
			}
		}catch(Exception e){
			//we failed some how just return null
			e.printStackTrace(System.err);
		}
		return null;
	}
	
	
	/**
	 * Simple method to make sure we get stuff like "0f" instead of just "f". Used
	 * in color hex string making
	 * @param str
	 * @return
	 */
	private String ensureTwoDigits(String str){
		if(str.length() == 0)
		{
			return "00";
		}
		else if(str.length() == 1)
		{
			return "0" + str;
		}
		else
		{
			return str;
		}
	}
	
	public void selectionChanged(IAction action, ISelection selection) {;}
}
