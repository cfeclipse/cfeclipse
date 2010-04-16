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
package org.cfeclipse.cfml.snippets.properties;


import org.cfeclipse.cfml.snippets.preferences.CFMLPreferenceConstants;
import org.cfeclipse.cfml.snippets.preferences.CFMLPreferenceManager;
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
				
	public void setComponentRoot(String root, IProject project){
		IPreferenceStore store = new ProjectPropertyStore(project);
		store.setValue("componentRoot", root);
	
	}
	
	
}
