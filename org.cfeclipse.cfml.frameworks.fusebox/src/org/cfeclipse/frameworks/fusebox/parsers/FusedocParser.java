/*
 * Created on 17-Jan-2005
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
package org.cfeclipse.frameworks.fusebox.parsers;

import java.io.IOException;
import java.io.InputStream;

import org.cfeclipse.frameworks.fusebox.util.Utils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

/**
 * @author Administrator
 * 17-Jan-2005
 * fusebox3cfe2
 * Description: I am really a utility object. I just allow the parsing of fusedocs given a resource or a string
 */
public class FusedocParser {

	public static String getFusedoc(IProject project, IFile file){
    	String fusedoc = "";
    	String fusedocstart = "<fusedoc";
    	String fusedocend = "/fusedoc>";
    	int fdstartpos = 0;
    	int fdendpos = 0;
    	
    	
    	
    	String fileContents = "";
    	InputStream is= null;
    	
    	if(file.exists()){
    		try {
				is = file.getContents();
				fileContents = Utils.getStringFromInputStream(is);
				
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//I was going to regex this but lets try it simply
		
			fdstartpos = fileContents.indexOf(fusedocstart);
			fdendpos = fileContents.indexOf(fusedocend) + fusedocend.length();
			 
			if(fdstartpos != -1){
			fusedoc = fileContents.substring(fdstartpos, fdendpos);
			}
    		
    	}
    	
    	
    	return fusedoc;
    }
    
    public static String getFusedoc(String file){
    	String fusedoc = "";
    	String fusedocstart = "<fusedoc";
    	String fusedocend = "/fusedoc>";
    	int fdstartpos = 0;
    	int fdendpos = 0;
    	
    	
    	
    	String fileContents = "";
			fileContents = file;
			//I was going to regex this but lets try it simply
		
			fdstartpos = fileContents.indexOf(fusedocstart);
			fdendpos = fileContents.indexOf(fusedocend) + fusedocend.length();
			
			if(fdstartpos != -1){
				fusedoc = fileContents.substring(fdstartpos, fdendpos);
			}
    	
    	
    	
    	return fusedoc;
    }
    
}
