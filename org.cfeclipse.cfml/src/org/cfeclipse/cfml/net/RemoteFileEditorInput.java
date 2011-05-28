/*
 * Created on Nov 6, 2004
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
package org.cfeclipse.cfml.net;


import java.net.URL;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.editors.text.ILocationProvider;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * @author Stephen Milligan
 *
 * This class acts as the editor input for the CFMLEditor.
 * It is capable of working with files both within the workspace 
 * and opened from external locations.
 */
public class RemoteFileEditorInput implements IEditorInput, ILocationProvider {


	/**
	 * The workbench adapter which simply provides the label.
	 * 
	 * @since 3.1
	 */
	private class WorkbenchAdapter implements IWorkbenchAdapter {
		/*
		 * @see org.eclipse.ui.model.IWorkbenchAdapter#getChildren(java.lang.Object)
		 */
		public Object[] getChildren(Object o) {
			return null;
		}
	
		/*
		 * @see org.eclipse.ui.model.IWorkbenchAdapter#getImageDescriptor(java.lang.Object)
		 */
		public ImageDescriptor getImageDescriptor(Object object) {
			return null;
		}
	
		/*
		 * @see org.eclipse.ui.model.IWorkbenchAdapter#getLabel(java.lang.Object)
		 */
		public String getLabel(Object o) {
			return ((RemoteFileEditorInput)o).getName();
		}
	
		/*
		 * @see org.eclipse.ui.model.IWorkbenchAdapter#getParent(java.lang.Object)
		 */
		public Object getParent(Object o) {
			return null;
		}
	}
    

	private RemoteFile fFile;
	private FileObject fObject;
	private WorkbenchAdapter fWorkbenchAdapter= new WorkbenchAdapter();

    /**
     * 
     */
    public RemoteFileEditorInput(RemoteFile file) {
		
		fFile= file;
		fObject = file.getFileItem();
		fWorkbenchAdapter= new WorkbenchAdapter();
	}
    
    
    
	public RemoteFileEditorInput(FileObject object) {
		this.fObject = object;
	}



	/*
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	public boolean exists() {
		if(fObject!=null){
			try {
				return fObject.exists();
			} catch (FileSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fFile.exists();
	}
	
	public boolean canWrite() {
		if(fObject!=null){
			try {
				return fObject.isWriteable();
			} catch (FileSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    return fFile.canWrite();
	}

	/*
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	/*
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	public String getName() {
		if(fObject!=null){
			
					return fObject.getName().getBaseName() + " [" + fObject.getName().getFriendlyURI() + "]";
		
		}
		return fFile.getName();
	}

	/*
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	public IPersistableElement getPersistable() {
		return null;
	}

	/*
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	public String getToolTipText() {
		if(fObject!=null){
			return fObject.getName().getType().toString();
		}
		return fFile.getAbsolutePath();
	}

	/*
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		if (ILocationProvider.class.equals(adapter))
			return this;
		if (IWorkbenchAdapter.class.equals(adapter))
			return fWorkbenchAdapter;
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	/*
	 * @see org.eclipse.ui.editors.text.ILocationProvider#getPath(java.lang.Object)
	 */
	public IPath getPath(Object element) {
		if (element instanceof RemoteFileEditorInput) {
			RemoteFileEditorInput input= (RemoteFileEditorInput) element;
			if(fObject !=null){
				return new Path(input.fObject.getName().getPath());
			}
				
			return new Path(input.fFile.getFileItem().getName().getPath());
		}
		return null;
	}
	
	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
		if (o instanceof RemoteFileEditorInput) {
			RemoteFileEditorInput input = (RemoteFileEditorInput) o;
			if(fObject!=null){
				return fObject.equals(input.fObject);
			}
			return fFile.equals(input.fFile);		
		}
		
		return false;
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		if(fObject != null){
			return fObject.hashCode();
		}
		return fFile.hashCode();
	}



	public FileObject getFileObject() {
		return fObject;
	}



	public void setFileObject(FileObject object) {
		fObject = object;
	}
}
