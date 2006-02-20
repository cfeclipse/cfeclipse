/*
 * Created on 19-Jan-2005
 *
 * The MIT License
 * Copyright (c) 2004 Mark Drew
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
 **/
package org.cfeclipse.frameworks.fusebox4.objects;

import org.cfeclipse.frameworks.fusebox4.util.PluginImages;
import org.eclipse.core.resources.IFile;

/**
 * @author Administrator
 * 19-Jan-2005
 * fusebox3cfe2
 * Description: this is the DO command that actually points to somewhere else (i.e. an action such a circuit.fuseaction)
 */
public class FBXDo extends FBXObject{
	private String action;
	private FBXFuseAction parent;
	private IFile switchFile;
	private String icon = PluginImages.ICON_FBX_DO;
	
	/**
	 * 
	 */
	public FBXDo(String action) {
		setAction(action);
	}
	public FBXDo() {
		setAction(action);
	}
	/**
	 * @return Returns the action.
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action The action to set.
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @param parent The parent to set.
	 */
	public void setParent(FBXFuseAction parent) {
		this.parent = parent;
	}
	/**
	 * @return Returns the switchFile.
	 */
	public IFile getSwitchFile() {
		return switchFile;
	}
	
	public String getIcon() {
		
		return this.icon;
	}
	/**
	 * @param switchFile The switchFile to set.
	 */
	public void setSwitchFile(IFile switchFile) {
		this.switchFile = switchFile;
	}
	public String toString(){
		
		return this.getXmlNode().getAttributes().getNamedItem("action").getNodeValue();
		
	}
}
