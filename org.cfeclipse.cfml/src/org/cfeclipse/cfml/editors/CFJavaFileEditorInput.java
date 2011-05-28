package org.cfeclipse.cfml.editors;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.ILocationProvider;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * This is essentially a copy of
 * org.eclipse.ui.internal.editors.text.JavaFileEditorInput.
 * 
 * But in 3.3 this class was removed, and it's internal anyways so we shouldn't
 * use it to start with
 */
public class CFJavaFileEditorInput implements IPathEditorInput, ILocationProvider, IPersistableElement, IStorageEditorInput
{
		
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
				return ((CFJavaFileEditorInput)object).getImageDescriptor();
			}

			/*
			 * @see org.eclipse.ui.model.IWorkbenchAdapter#getLabel(java.lang.Object)
			 */
			public String getLabel(Object o) {
				return ((CFJavaFileEditorInput)o).getName();
			}

			/*
			 * @see org.eclipse.ui.model.IWorkbenchAdapter#getParent(java.lang.Object)
			 */
			public Object getParent(Object o) {
				return null;
			}
		}
		
		private IStorage fStorage;
		
		private File fFile;
		
		private WorkbenchAdapter fWorkbenchAdapter;
		
		public CFJavaFileEditorInput(File file)
		{ 
			fFile = file;
			fWorkbenchAdapter = new WorkbenchAdapter();
		}
		/*
		 * @see org.eclipse.ui.IEditorInput#exists()
		 */
		public boolean exists() {
			return true;
		}

		/*
		 * @see org.eclipse.ui.IEditorInput#getName()
		 */
		public String getName() 
		{
			return fFile.getName();
		}

		/*
		 * @see org.eclipse.ui.IEditorInput#getToolTipText()
		 */
		public String getToolTipText() {
			try
			{
				return fFile.getCanonicalPath();
	    	} catch (IOException ex) {
	    		// ignore it, shouldn't happen anyways
	    	}
	    	return null;
		}
		
		/*
		 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
		 */
		public ImageDescriptor getImageDescriptor() 
		{
		    ImageDescriptor imageDesc = PlatformUI.getWorkbench().getEditorRegistry().getImageDescriptor(fFile.getName(), null);
		    if (imageDesc == null)
		    	imageDesc = PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
		    return imageDesc;
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
				
	    public String getFactoryId() 
	    {
	    	return CFJavaFileEditorInputFactory.FACTORY_ID;
	    }

		public IPersistableElement getPersistable() {
			return this;
		}
	    
	    public void saveState(IMemento memento)
	    {
	    	// not sure what to do here
	    	try
	    	{
	    		memento.putString(CFJavaFileEditorInputFactory.FILE_NAME, fFile.getCanonicalPath());
	    	} catch (IOException ex) {
	    		// ignore it, shouldn't happen anyways
	    	}
	    }

	    
		/*
		 * @see org.eclipse.ui.editors.text.ILocationProvider#getPath(java.lang.Object)
		 */
		public IPath getPath(Object element) {
			if (element instanceof CFJavaFileEditorInput) {
				CFJavaFileEditorInput input = (CFJavaFileEditorInput) element;
				return Path.fromOSString(input.fFile.getAbsolutePath());
			}
			return null;
		}

	    /*
		 * @see org.eclipse.ui.IPathEditorInput#getPath()
		 * @since 3.1
		 */
	    public IPath getPath() {
	        return Path.fromOSString(fFile.getAbsolutePath());
	    }

		/*
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object o) {
			if (o == this)
				return true;

			if (o instanceof CFJavaFileEditorInput) {
				CFJavaFileEditorInput input= (CFJavaFileEditorInput) o;
				return fFile.equals(input.fFile);
			}

	        if (o instanceof IPathEditorInput) {
	            IPathEditorInput input= (IPathEditorInput)o;
	            return getPath().equals(input.getPath());
	        }

			return false;
		}

		/*
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {
			return fFile.hashCode();
		}
		
		public IStorage getStorage() throws CoreException
		{
			if (fStorage == null)
				fStorage= new CFJavaFileStorage(fFile);
			return fStorage;		
		}
	}
