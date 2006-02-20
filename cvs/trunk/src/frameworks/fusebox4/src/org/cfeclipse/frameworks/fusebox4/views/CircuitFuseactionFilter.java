/*
 * Created on 18-Jan-2005
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
package org.cfeclipse.frameworks.fusebox4.views;

import org.cfeclipse.frameworks.fusebox4.objects.FBXApplication;
import org.cfeclipse.frameworks.fusebox4.objects.FBXCircuit;
import org.cfeclipse.frameworks.fusebox4.objects.FBXFuse;
import org.cfeclipse.frameworks.fusebox4.objects.FBXFuseAction;
import org.cfeclipse.frameworks.fusebox4.objects.FBXRoot;
import org.cfeclipse.frameworks.fusebox4.util.Utils;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * @author Administrator
 * 18-Jan-2005
 * fusebox3cfe2
 * Description: This filter matches a circuit and 
 */
public class CircuitFuseactionFilter extends ViewerFilter{


	private String circuitmatch;
	private String fuseactionmatch; 


	public boolean select(Viewer viewer, Object parentElement, Object element) {
		boolean match = true;
		//would we do it for each?
		if(element instanceof FBXRoot){
			Utils.println("Always display the root");
			return true;
		}
		if(element instanceof FBXApplication){
			Utils.println("Always display the application");
			return true;
		}
		if(element instanceof FBXCircuit){
			if(this.circuitmatch != null){
				if(this.circuitmatch.trim().length() > 0){
					Utils.println("Filter the circuit on: " + this.circuitmatch);
					
					
					// doesnt work in JDK 1.4.2
					//return ((FBXCircuit)element).getName().contains(this.circuitmatch);
					return ((FBXCircuit)element).getName().indexOf(this.circuitmatch) != -1;
					
					
				}
				
			}
			
			return true;
		}
		if(element instanceof FBXFuseAction){
			if(this.fuseactionmatch != null){
				if(this.fuseactionmatch.trim().length() > 0){
					// Not working in old JDK
					//return ((FBXFuseAction)element).getName().contains(this.fuseactionmatch);
					return ((FBXFuseAction)element).getName().indexOf(this.fuseactionmatch) != -1;
				}
					
				
			}
			return true;
		}
		if(element instanceof FBXFuse){
			Utils.println("Always display the fuseaction");
			return true;
		}
		/*
		|| element instanceof  
		|| element instanceof FBXFuseAction 
		|| element instanceof FBXFuse 
		|| element instanceof FBXFuseAction 
		&& ((FBXFuseAction)element).getName().contains(match);*/
		
		return match;
	}
	/**
	 * @return Returns the circuitmatch.
	 */
	public String getCircuitmatch() {
		return circuitmatch;
	}
	/**
	 * @param circuitmatch The circuitmatch to set.
	 */
	public void setCircuitmatch(String circuitmatch) {
		this.circuitmatch = circuitmatch;
	}
	/**
	 * @return Returns the fuseactionmatch.
	 */
	public String getFuseactionmatch() {
		return fuseactionmatch;
	}
	/**
	 * @param fuseactionmatch The fuseactionmatch to set.
	 */
	public void setFuseactionmatch(String fuseactionmatch) {
		this.fuseactionmatch = fuseactionmatch;
	}
}
