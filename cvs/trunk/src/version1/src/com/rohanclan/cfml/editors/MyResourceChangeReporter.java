/*
 * Created on Feb 2, 2004
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

package com.rohanclan.cfml.editors;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

public class MyResourceChangeReporter implements IResourceChangeListener {
	
	public void resourceChanged(IResourceChangeEvent event) 
	{
		IResource res = event.getResource();
		try {
			switch (event.getType()) {
				case IResourceChangeEvent.PRE_CLOSE:
					//System.out.println(" is about to close.");
					break;
				case IResourceChangeEvent.PRE_DELETE:
					//System.out.println(" is about to be deleted.");
					break;
				case IResourceChangeEvent.POST_CHANGE:
					//System.out.println("Resources have changed.");
					event.getDelta().accept(new DeltaPrinter());
					break;
				/* these two seem to be deprecated... 
				case IResourceChangeEvent.PRE_AUTO_BUILD:
				//System.out.println("Auto build about to run.");
					event.getDelta().accept(new DeltaPrinter());
					break;
			 	case IResourceChangeEvent.POST_AUTO_BUILD:
				//System.out.println("Auto build complete.");
					event.getDelta().accept(new DeltaPrinter());
					break;
				*/
			}
		}catch(CoreException temp) {
			System.err.println("Caught a CoreException");
		}
	}
}