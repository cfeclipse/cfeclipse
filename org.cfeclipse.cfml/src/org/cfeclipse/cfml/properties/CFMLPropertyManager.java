/*
 * Created on Apr 29, 2004
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
package org.cfeclipse.cfml.properties;


import org.cfeclipse.cfml.preferences.CFMLPreferenceConstants;
import org.cfeclipse.cfml.preferences.CFMLPreferenceManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @author Stephen Milligan
 *
 * This controls the properies for the per project settings
 * 
 * TODO: We should put things like project mapping that the open new browser works with
 */
public class CFMLPropertyManager {
	//private PreferenceStore store;
	private CFMLPreferenceManager preferenceManager;
	
	public CFMLPropertyManager() 
	{
		super();
		//this.store = CFMLPlugin.getDefault().getPropertyStore();
	
		try 
		{
			//store.load();
		}
		catch (Exception e) 
		{
			//System.err.println("CFMLPropertyManager::CFMLPropertyManager() - Couldn't load property store");
			//e.printStackTrace();
		} 
		this.preferenceManager = new CFMLPreferenceManager();
	}
	
	public IPreferenceStore getStore(IProject project)
	{
		return new ProjectPropertyStore(project);
	}
	
	public void initializeDefaultValues(IProject project) {
		IPreferenceStore store = new ProjectPropertyStore(project);
        store.setDefault(CFMLPreferenceConstants.P_SNIPPETS_PATH, preferenceManager.snippetsPath());
        store.setDefault(CFMLPreferenceConstants.P_PROJECT_URL, preferenceManager.defaultProjectURL());
        store.setDefault(CFMLPreferenceConstants.P_DEFAULT_HELP_URL, preferenceManager.defaultHelpURL());
		store.setDefault(CFMLPreferenceConstants.P_DICTIONARIES_PATH, preferenceManager.defaultDictionaryDir());
	}
	
	public String getCurrentDictionary(IFile file){
		IPreferenceStore store = new ProjectPropertyStore(file.getProject());
		String dict = store.getString(CFMLPreferenceConstants.P_CFML_DICTIONARY);
		if (dict.length() < 1) {
			dict = CFMLPreferenceConstants.P_CFML_DICTIONARY_DEFAULT;
		}
		return dict;
	}
	
	public String getDictionaryDir() {
		String dictDir = preferenceManager.dictionaryDir();
		return dictDir;
	}

	public String getCurrentDictionary(IProject project)
	{
		String dict;
		IPreferenceStore store = new ProjectPropertyStore(project);
		if (project.isAccessible()) {
			dict = store.getString(CFMLPreferenceConstants.P_CFML_DICTIONARY);
			if (dict.length() < 1) {
				dict = CFMLPreferenceConstants.P_CFML_DICTIONARY_DEFAULT;
			}
		} else {
			dict = CFMLPreferenceConstants.P_CFML_DICTIONARY_DEFAULT;
		}
		return dict;
	}
	
	public String snippetsPath(IProject project) {
		IPreferenceStore store = new ProjectPropertyStore(project);
		return store.getString(CFMLPreferenceConstants.P_SNIPPETS_PATH).trim();
	}
	

	public String defaultSnippetsPath() {
		return preferenceManager.snippetsPath();
	}
	
	public void setSnippetsPath(String path, IProject project) {
		IPreferenceStore store = new ProjectPropertyStore(project);
		store.setValue(CFMLPreferenceConstants.P_SNIPPETS_PATH,path);
	}
	
	public String projectURL(IProject project) {
		IPreferenceStore store = new ProjectPropertyStore(project);
		return store.getString(CFMLPreferenceConstants.P_PROJECT_URL).trim();
	}
	
	public String helpURL(IProject project) {
		IPreferenceStore store = new ProjectPropertyStore(project);
		String helpUrl = store.getString(CFMLPreferenceConstants.P_DEFAULT_HELP_URL).trim();
		if(helpUrl == "" || helpUrl == null) {
			return defaultHelpURL();
		} else {
			return helpUrl;
		}
	}
	
	public String defaultProjectURL() {
		return preferenceManager.defaultProjectURL();
	}
	
	public void setProjectURL(String path, IProject project) {
		IPreferenceStore store = new ProjectPropertyStore(project);
		store.setValue(CFMLPreferenceConstants.P_PROJECT_URL,path);
	}
	
	public String defaultHelpURL() {		
		return preferenceManager.defaultHelpURL();
	}

	public void setHelpURL(String url, IProject project) {
		IPreferenceStore store = new ProjectPropertyStore(project);
		store.setValue(CFMLPreferenceConstants.P_DEFAULT_HELP_URL,url);
	}
	
	public void setComponentRoot(String root, IProject project){
		IPreferenceStore store = new ProjectPropertyStore(project);
		store.setValue("componentRoot", root);
	
	}
	
	
}
