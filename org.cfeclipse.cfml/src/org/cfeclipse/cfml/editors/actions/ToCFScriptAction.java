/*
 * So this is a pretty crappy implementation of tags-to-cfscript, and really a parser 
 * is what we need, and really really we shouldn't be using strings the way we are, but what the hell.
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
			m = Pattern.compile("(?is)<cfreturn\\s+(.*?)\\s?/?>").matcher("").reset(highlighted);
			while (m.find()) {
				String args = tagArgsToFunctionArgs(m.group(1));
				highlighted = m.replaceFirst("return " + args + ";");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<cfdump\\s+(.*?)\\s?/?>").matcher("").reset(highlighted);
			while (m.find()) {
				String args = tagArgsToFunctionArgs(m.group(1));
				highlighted = m.replaceFirst("dump("+args+");");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<cftransaction\\s+(.*?)>(.*?)</cftransaction>").matcher("").reset(highlighted);
			while (m.find()) {
				String args = tagArgsToFunctionArgs(m.group(1));
				highlighted = m.replaceFirst("transaction " + args + "{" + m.group(2) + "}");
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
			m = Pattern.compile("(?is)[\\s]+?<cfscript>(.*?)[\\s]+?</cfscript>").matcher("").reset(highlighted);
			while (m.find()) {
				highlighted = m.replaceFirst(m.group(1));
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<cffunction\\s+(.*?)>(.*?)</cffunction>").matcher("").reset(highlighted);
			while (m.find()) {
				HashMap args = tagArgsToMap(m.group(1));
				String name = getLowerCaseKeyValue(args, "name", "");
				String access = getLowerCaseKeyValue(args, "access", "");
				String returnType = getLowerCaseKeyValue(args, "returntype", "");
				String functionArgs = tagArgumentToScriptArgument(m.group(2));
				highlighted = m.replaceFirst(access + returnType + "function " + name.trim() + "(" + functionArgs + ") "
						+ tagArgsToFunctionArgs(m.group(1)).trim() + " {"
 + m.group(2).replaceAll("(?is)[\\s]+?<cfargument\\s+(.*?)>", "")
 + "}");
				m.reset(highlighted);
			}
			m = Pattern.compile("(?is)<cfcomponent\\s+(.*?)>(.*?)</cfcomponent>").matcher("").reset(highlighted);
			while (m.find()) {
				String args = m.group(1);
				highlighted = m.replaceFirst("component " + args + " {" + m.group(2) + "}");
				m.reset(highlighted);
			}
			return highlighted;
		} else {
			return highlighted;
		}
	}

	private String tagArgumentToScriptArgument(String string) {
		Matcher m = Pattern.compile("(?is)<cfargument\\s+(.*?)>").matcher("").reset(string);
		String argsString = "";
		while (m.find()) {
			HashMap args = tagArgsToMap(m.group(1));
			String name = getLowerCaseKeyValue(args, "name", "");
			String defaultValue = getLowerCaseKeyValue(args, "default", "");
			String displayname = getLowerCaseKeyValue(args, "displayname", "");
			String hint = getLowerCaseKeyValue(args, "hint", "");
			String required = getLowerCaseKeyValue(args, "required", "");
			String type = getLowerCaseKeyValue(args, "type", "");
			// String functionArgs = tagArgumentToScriptArgument(m.group(1));
			if (Boolean.parseBoolean(required.trim())) {
				required = "required ";
			}
			if (!defaultValue.equals("")) {
				defaultValue = "=\"" + defaultValue + "\"";
			}
			argsString += required + type + name + defaultValue + ",";
		}
		return argsString.substring(0, argsString.length() - 1).trim();
	}

	private String getLowerCaseKeyValue(HashMap inMap, String key, String defaultValue) {
		String value = (String) inMap.get(key.toLowerCase());
		if (value == null) {
			return defaultValue;
		}
		return value + " ";
	}

	private String tagArgsToFunctionArgs(String args) {
		HashMap argMap = tagArgsToMap(args);
		String ret = "";
		String name = "";
		String access = "";
		String returnType = "";
		for (Object k : argMap.keySet()) {
			String keyName = k.toString();
			if (keyName.equals("name")) {
			} else if (keyName.equals("access")) {
			} else if (keyName.equals("output")) {
			} else if (keyName.equals("returntype")) {
			} else {
				ret += (keyName + "=\"" + argMap.get(keyName)) + "\" ";
			}
		}
		return ret;
	}

	private HashMap tagArgsToMap(String args) {
		HashMap argMap = new HashMap();
		Matcher m = Pattern.compile("(?si)(\\w+)[\\s=]+(((\\x22|\\x27|#)((?!\\4).|\\4{2})*\\4))").matcher("").reset(args);
		String ret ="";
		while (m.find()) {
			argMap.put(m.group(1).toLowerCase(), m.group(2).substring(1, m.group(2).length() - 1));
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
