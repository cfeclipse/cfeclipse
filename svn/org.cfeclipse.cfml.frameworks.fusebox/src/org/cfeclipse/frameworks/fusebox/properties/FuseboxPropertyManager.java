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
package org.cfeclipse.frameworks.fusebox.properties;


import org.cfeclipse.frameworks.fusebox.FuseboxPlugin;
import org.cfeclipse.frameworks.fusebox.preferences.FuseboxPreferenceManager;
import org.eclipse.jface.preference.PreferenceStore;
import java.io.IOException;

/**
 * @author Mark Drew
 *
 * This controls the properies for the per project settings
 */
public class FuseboxPropertyManager {
	/**
	 * 
	 */
	
	private PreferenceStore store;
	private FuseboxPreferenceManager  preferenceManager;
	
	public FuseboxPropertyManager() {
		super();
		this.store =  FuseboxPlugin.getDefault().getPropertyStore();
		try {
			store.load();
		}
		catch (Exception e) {
			//System.err.println("FuseboxPropertyManager::FuseboxPropertyManager() - Couldn't load property store");
			//e.printStackTrace();
		} 
		this.preferenceManager = new FuseboxPreferenceManager();
	}
	
	
	public void initializeDefaultValues() {
        //store.setDefault(FuseboxPreferenceConstants.P_FUSEBOX_PATH, preferenceManager.defaultFuseboxVersion());
        store.setDefault(FuseboxPreferenceConstants.P_FUSEBOX_PATH, preferenceManager.defaultFuseboxRoot());
	}
	
	public String fuseboxVersion(){
		return store.getString(FuseboxPreferenceConstants.P_FUSBOX_VERSION).trim();
	}
	public String defaultFuseboxVersion(){
		//return preferenceManager.fuseboxVersion();
		return "some default";
	}

	public void setFuseboxVersion(String version) {
		store.setValue(FuseboxPreferenceConstants.P_FUSBOX_VERSION,version);
		try {
			store.save();
		}
		catch (IOException e) {
			System.err.println("Failed to save property store " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String fuseboxPath(){
		return store.getString(FuseboxPreferenceConstants.P_FUSEBOX_PATH).trim();
	}
	public String defaultFuseboxPath(){
		return "/";
	}
	
	public void setFuseboxPath(String path){
		store.setValue(FuseboxPreferenceConstants.P_FUSEBOX_PATH,path);
		try {
			store.save();
		}
		catch (IOException e) {
			System.err.println("Failed to save property store " + e.getMessage());
		}
	}
	
	
	
}
