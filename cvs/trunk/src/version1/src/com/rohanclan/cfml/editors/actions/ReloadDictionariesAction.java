/*
 * Created on Jun 20, 2004
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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.editors.CFMLEditor;

/**
 * @author Rob
 *
 * Action to insert an html br - need to figure out how to assing snipps to keys
 * most of these actions are too easy...
 */
public class ReloadDictionariesAction  implements IEditorActionDelegate {
    protected ITextEditor editor = null;
    
	public ReloadDictionariesAction()
	{
	    
	}
	
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) 
	{
		//System.err.println(targetEditor);
		//System.out.println( "Changin (" + start + ")(" + end + ")" );
		if( targetEditor instanceof ITextEditor || targetEditor instanceof CFMLEditor ){
			editor = (ITextEditor)targetEditor;
		}
	}
	
	public void run() {
	    DictionaryManager.initDictionaries();
	}
	
	public void run(IAction action) 
	{
	    run();
	}
	public void selectionChanged(IAction action, ISelection selection){;}
}
