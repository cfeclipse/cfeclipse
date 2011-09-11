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

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author denny
 * 
 *         This takes the highlighted text and turns it into cfscript
 */
public class ToCFScriptAction extends WordManipulator implements IWorkbenchWindowActionDelegate,IEditorActionDelegate {

	ITextEditor editor = null;
	
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
		//get the document and selection and pass it to the word manipulator
		//so it can extract and rewrite what we want (super class)
		IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput()); 
		ITextSelection sel = (ITextSelection)editor.getSelectionProvider().getSelection();
		this.setControler(doc,sel);
	}
	
	/**
	 * override manipulate, just return the string to lower case
	 */
	public String manipulate(String highlighted) {
		if (editor != null && editor.isEditable()) {
			Matcher m = Pattern.compile("(?is)<cfset\\s+(.*?)\\s?/?>").matcher("").reset(highlighted);
			while (m.find()) {
				highlighted = m.replaceFirst(m.group(1)+";");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<cfproperty\\s+(.*?)\\s?/?>").matcher("").reset(highlighted);
			while (m.find()) {
				highlighted = m.replaceFirst("property " + m.group(1) + ";");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<cfif\\s+(.*?)\\s?>").matcher("").reset(highlighted);
			while (m.find()) {
				highlighted = m.replaceFirst("if ("+m.group(1)+") {");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<cfelseif\\s+(.*?)\\s?>").matcher("").reset(highlighted);
			while (m.find()) {
				highlighted = m.replaceFirst("elseif ("+m.group(1)+") {");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<cfelse\\s?>").matcher("").reset(highlighted);
			while (m.find()) {
				highlighted = m.replaceFirst("} else {");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)</cfif\\s?>").matcher("").reset(highlighted);
			while (m.find()) {
				highlighted = m.replaceFirst("}");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<!---(.*?)--->").matcher("").reset(highlighted);
			while (m.find()) {
				highlighted = m.replaceFirst("/*"+m.group(1)+"*/");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<cflocation\\s+(.*?)\\s?/?>").matcher("").reset(highlighted);
			while (m.find()) {
				String args = tagArgsToFunctionArgs(m.group(1));
				highlighted = m.replaceFirst("location("+args+");");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<cfdump\\s+(.*?)\\s?/?>").matcher("").reset(highlighted);
			while (m.find()) {
				String args = tagArgsToFunctionArgs(m.group(1));
				highlighted = m.replaceFirst("dump("+args+");");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<cfabort\\s*?/?>").matcher("").reset(highlighted);
			while (m.find()) {
				highlighted = m.replaceFirst("abort;");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<cfloop\\s+(.*?)\\s?>").matcher("").reset(highlighted);
			String origText = highlighted;
			while (m.find()) {
				HashMap argsMap = tagArgsToMap(m.group(1));
				if(argsMap.containsKey("condition")){					
					String condition = argsMap.get("condition").toString();
					highlighted = m.replaceFirst("while("+condition+") {");
				}
				if(argsMap.containsKey("collection")){					
					String collection = argsMap.get("collection").toString();
					collection = collection.substring(1, collection.length()-1); // remove #s
					String item = argsMap.get("item").toString();
					highlighted = m.replaceFirst("for(var "+item+" in "+collection+") {");
				}
				if(argsMap.containsKey("from")){
					String from = argsMap.get("from").toString();
					String to = argsMap.get("to").toString();
					String index = argsMap.get("index").toString();
					String step = "1";
					String opperator = "+";
					String comparator = "<=";
					if(argsMap.get("step")!=null) {
						step = argsMap.get("step").toString();
					}
					if(Integer.parseInt(from) > Integer.parseInt(to)) {
						comparator = ">=";
						opperator = "";
					}
					highlighted = m.replaceFirst("for("+index+"="+from+"; "+index+comparator+to+"; "+index+"="+index+opperator+step+") {");
				}
				if(argsMap.containsKey("list")){
					String list = argsMap.get("list").toString();
					String item = argsMap.get("index").toString();
					String delimiters = "";
					if(argsMap.get("delimiters")!=null) {
						delimiters = ","+argsMap.get("delimiters").toString();
					}
					highlighted = m.replaceFirst("for(var "+item+" in listToArray("+list+delimiters+")) {");
				}
				if(argsMap.containsKey("query")){
					String query = argsMap.get("query").toString();
					highlighted = m.replaceFirst("for(i=1; i lte "+query+".recordcount; i=i+1) {");
				}
				m.reset(highlighted);
				if(highlighted.equals(origText)) {
					break;
				}
			}
			m = Pattern.compile("(?is)</cfloop\\s?>").matcher("").reset(highlighted);
			while (m.find()) {
				highlighted = m.replaceFirst("}");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<cfoutput>(.*?)</cfoutput>").matcher("").reset(highlighted);
			while (m.find()) {
				highlighted = m.replaceFirst("writeOutput(\""+m.group(1).replace("\"", "\"\"")+"\");");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<cffunction\\s+(.*?)>(.*?)</cffunction>").matcher("").reset(highlighted);
			while (m.find()) {
				String args = tagArgsToFunctionArgs(m.group(1));
				highlighted = m.replaceFirst("function "+args + "(\""+m.group(2).replace("\"", "\"\"")+"\");");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<cfcomponent\\s+(.*?)>(.*?)</cfcomponent>").matcher("").reset(highlighted);
			while (m.find()) {
				String args = m.group(1);
				highlighted = m.replaceFirst("component " + args + " {" + m.group(2) + "};");
				m.reset(highlighted);
			}
			return highlighted;
		} else {
			return highlighted;
		}
	}

	private String tagArgsToFunctionArgs(String args) {
		Matcher m = Pattern.compile("(?si)(\\w+)[\\s=]+(((\\x22|\\x27|#)((?!\\4).|\\4{2})*\\4))").matcher("").reset(args);
		String ret ="";
		while (m.find()) {
			ret+= (m.group(1)+"="+m.group(2)) + ", ";
		}
		return ret.substring(0,ret.length()-2);
	}

	private HashMap tagArgsToMap(String args) {
		HashMap argMap = new HashMap();
		Matcher m = Pattern.compile("(?si)(\\w+)[\\s=]+(((\\x22|\\x27|#)((?!\\4).|\\4{2})*\\4))").matcher("").reset(args);
		String ret ="";
		while (m.find()) {
			argMap.put(m.group(1), m.group(2).substring(1, m.group(2).length()-1));
		}
		return argMap;
	}
	
	
	public void selectionChanged(IAction action, ISelection selection){
		if(editor != null){
			setActiveEditor(null,  editor.getSite().getPage().getActiveEditor());
		}
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbenchWindow window) {
		IEditorPart activeEditor = window.getActivePage().getActiveEditor();
		if(activeEditor instanceof ITextEditor){
			editor = (ITextEditor)activeEditor;
		}
		
	}
}
