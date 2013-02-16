/*
 * Created on Mar 21, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver Tupman
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
package org.cfeclipse.cfml.parser;

//import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.dictionary.Function;
import org.cfeclipse.cfml.dictionary.Parameter;
import org.cfeclipse.cfml.model.CFModelChangeEvent;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.cfeclipse.cfml.parser.docitems.TagItem;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ISynchronizable;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
/** 
 * CFDocument basically is the main element for getting information about the 
 * ColdFusion document. It will contain the entire tree for the document, 
 * including CFScript'd items.
 *  
 * CFDocument is supposed to represent the document tree, variable list and 
 * whatever else for out-of-editor and in-editor documents. So it can be used 
 * for documents that the user is editing and for background loading a file 
 * (or files).
 */
public class CFDocument {
	/**
	 * Filename that this CFDocument is associated with. Don't think it's needed.
	 */
	protected String docFilename; 
	/**
	 * The root elements of the document. A CF document does NOT have to have a root element and
	 * so one is not inserted by default.
	 * @deprecated Use getDocumentRoot() to get the DocItem representing the root of the document
	 */
	public ArrayList docRoot;
	/**
	 * List of variables in the document. Not used at the moment.
	 */
	public ArrayList docVariables;	
	
	/**
	 * List of variables in the document, created in CFParser
	 */
	
	public HashMap variableMap = new HashMap();
	
	/**
	 * Not sure what this is for!
	 * @deprecated Do not use. Use getDocumentRoot()
	 */
	public Stack docTree = null;
	
	/**
	 * The internal root of the document tree.
	 * This will remain null if the tree parse fails.
	 */
	protected DocItem treeRoot = null;

	/**
	 * reconciler stuff
	 */
    private static int fgInstanceCount= 0;
    private IDocument fDocument;
	private boolean fShouldReconcile = true;
    private final Object fDirtyLock= new Object();
    private boolean fIsDirty= true;
	private CFDocument fCFDocument;

	/**
	 * Sets the document root to the root item of a CFDOC parse.
	 * @param newRootItem
	 */
	public void setDocumentRoot(DocItem newRootItem)
	{
		treeRoot = newRootItem;
	}
	
	/**
	 * Returns the root of the parsed document.
	 * @return The parsed document or null if the parse failed.
	 */
	public DocItem getDocumentRoot()
	{
		if(treeRoot == null)
		{
			//
			// Warn developer if the parse fails.
			System.err.println(
				"CFDocument::getDocumentRoot() - WARNING: treeRoot is null, have you run the parser yet?"
			);
		}
		return treeRoot;
	}
	
	public void addVariable(Variable newVar)
	{
		if(docVariables == null)
		{
			docVariables = new ArrayList();
		}
		docVariables.add(newVar);
	}
	
	/**
	 * Returns the filename of this CF document.
	 * @return The filename
	 */
	public String getFilename() {
		return docFilename;
	}

	/**
	 * Constructor.
	 *
	 */
	public CFDocument()
	{
		docFilename = "";
	}
	
	/**
	 * Constructs the document with a specific file to be used.
	 * @param filename The file that this document represents.
	 */
	public CFDocument(String filename) {
		docFilename = filename;
	}

	public Set<Function> getFunctions() {
		Matcher matcher;
		Pattern pattern;
		String name = "", type = "", required = "", defaultvalue = "";
		CFNodeList nodes = getDocumentRoot().selectNodes("//cffunction");
		CFNodeList scriptNodes = getDocumentRoot().selectNodes("//ASTFunctionDeclaration");
		Iterator i = nodes.iterator();
		Set<Function> functions = new HashSet<Function>();
		pattern = Pattern.compile("(\\w+)[\\s=]+(((\\x22|\\x27)((?!\\4).|\\4{2})*\\4))", Pattern.CASE_INSENSITIVE);
		while (i.hasNext()) {
			TagItem currItem = (TagItem) i.next();
			String funcName = currItem.getAttributeValue("name", "unnamed");
			String funcReturn = currItem.getAttributeValue("returntype", "any");

			Function function = new Function(funcName, funcReturn, Byte.parseByte("8"));
			// System.out.println(currItem.getItemData());
			if (currItem.hasChildren() && currItem.getFirstChild().getName().equals("cfargument")) {
				Iterator childNodes = currItem.getChildNodes().iterator();
				DocItem childNode;
				while (childNodes.hasNext()) {
					childNode = (DocItem) childNodes.next();
					if (childNode.getName().equals("cfargument")) {
						matcher = pattern.matcher(childNode.getItemData());
						while (matcher.find()) {
							String value = matcher.group(2).replaceAll("'", "").replaceAll("\"", "");
							if (matcher.group(1).toLowerCase().equals("name")) {
								name = value;
							}
							if (matcher.group(1).toLowerCase().equals("type")) {
								type = value;
							}
							if (matcher.group(1).toLowerCase().equals("required")) {
								required = value;
							}
							if (matcher.group(1).toLowerCase().equals("default")) {
								defaultvalue = value;
							}
						}
						Parameter newParam = new Parameter(name, type, Boolean.valueOf(required), defaultvalue);
						name = type = required = defaultvalue = "";
						function.addParameter(newParam);
					}

				}
			}
			functions.add(function);
		}
		i = scriptNodes.iterator();
		while (i.hasNext()) {
			FunctionInfo currItem = (FunctionInfo) i.next();
			String funcName = currItem.getFunctionName();
			String funcReturn = currItem.getReturnType();
			Function function = new Function(funcName, funcReturn, Byte.parseByte("8"));
			// System.out.println(currItem.getItemData());
			List args = currItem.getParameters();
			Iterator j = args.iterator();
			while (j.hasNext()) {
				Map<String, String> parameterAttribs = (Map) j.next();
				name = parameterAttribs.get("name");
				type = parameterAttribs.get("type");
				required = parameterAttribs.get("required");
				defaultvalue = parameterAttribs.get("default");
				Parameter newParam = new Parameter(name, type, Boolean.valueOf(required), defaultvalue);
				name = type = required = defaultvalue = "";
				function.addParameter(newParam);
			}
			functions.add(function);
		}
		return functions;
	}
	
	public ArrayList getDocVariables() {
		return docVariables;
	}

	public void setDocVariables(ArrayList docVariables) {
		this.docVariables = docVariables;
	}

	public HashMap getVariableMap() {
		return variableMap;
	}
	/**
	 * Utility function to view what variables the document currently has.
	 * @return
	 */
	public String dumpVariables(){
		System.out.println("\nDump Variables:");

		String vars = "---------------------\n";
		Iterator mapIter = variableMap.keySet().iterator();
		while(mapIter.hasNext()){
			String keyItem = mapIter.next().toString();
			vars += "Key: " + keyItem +"\t=\t";
			if(variableMap.get(keyItem) instanceof CfmlTagItem){
				CfmlTagItem tag = (CfmlTagItem)variableMap.get(keyItem);
				vars += tag.getName() + "\n";
			}
			else{
				vars += variableMap.get(keyItem).getClass() + "\n";
			}
		}
		vars += "---------------------";
		return vars;
		
		
	}

	public void setVariableMap(HashMap variableMap) {
		this.variableMap = variableMap;
	}

	public void setShouldReconcile(boolean shouldReconcile) {
        fShouldReconcile= shouldReconcile;
        if (fShouldReconcile) {
            reconcile();
        }       		
	}

    public void reconcile() {
        synchronized (fDirtyLock) {
            if (!fShouldReconcile || !fIsDirty) {
                return;
            }
            fIsDirty= false;
        }

        synchronized (getLockObject()) {
            if (fDocument != null) {
            	CFParser parser = new CFParser();
                fCFDocument = parser.parseDoc(fDocument.get());
            } 
            CFMLPlugin.getDefault().notifyCFModelListeners(new CFModelChangeEvent(this));
        }
    }

    private Object getLockObject() {
        if (fDocument instanceof ISynchronizable) {
            Object lock= ((ISynchronizable)fDocument).getLockObject();
            if (lock != null) {
                return lock;
            }
        }
        return this;
    }

    public Function getFunctionByName(String functionName) {
        Set<Function> funcs = this.getFunctions();
        for (Function fn : funcs) {
                if (fn.getName().equalsIgnoreCase(functionName)) {
                        return fn;
                }
        }
        return null;
    }
   
}