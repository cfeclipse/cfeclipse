/*
 * Created on Jan 30, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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
package org.cfeclipse.cfml.editors;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.editors.partitioner.CFEPartitioner;
import org.cfeclipse.cfml.editors.partitioner.PartitionTypes;
import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.cfeclipse.cfml.external.ExternalFile;
import org.cfeclipse.cfml.external.ExternalMarkerAnnotationModel;
import org.cfeclipse.cfml.net.RemoteFileEditorInput;
import org.cfeclipse.cfml.net.ftp.FTPConnection;
import org.cfeclipse.cfml.properties.CFMLPropertyManager;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.eclipse.ui.internal.editors.text.JavaFileEditorInput;
import org.eclipse.ui.part.FileEditorInput;


/**
 * This document handles the opening and closing of CF documents.
 * It assigns and runs a parser over a document.
 * 
 * @author Rob
 */
public class CFDocumentProvider extends FileDocumentProvider
{
	private ExternalMarkerAnnotationModel model = null;
    
	protected IDocument createDocument(Object element) throws CoreException 
	{
		ICFDocument document = null;
		
		document = new ICFDocument();
		if(setDocumentContent(document, (IEditorInput) element, getEncoding(element))) 
		{
			setupDocument(element, document);
		}
		
		if(document != null) 
		{
			
			//REMOVED the following lines as we would never know which project it is in.
			
			//FileEditorInput input = (FileEditorInput)element;
			
			//This is what is WRONG! We need to know what project the document is in so we can get the right library...
			
			//try to load the proper dictionary syntax for this document			
			CFMLPropertyManager pm = new CFMLPropertyManager();
			
			//How do we know which project this goes to?
			//String currentDict = pm.getCurrentDictionary(input.getFile().getProject());
			//if(currentDict == null || currentDict == "") 
			String currentDict = DictionaryManager.getFirstVersion(DictionaryManager.CFDIC);
			
			DictionaryManager.loadDictionaryFromCache(
				currentDict,
				DictionaryManager.CFDIC
			);
			/////
			
			IDocumentPartitioner partitioner = new CFEPartitioner(
				new CFPartitionScanner(), PartitionTypes.ALL_PARTITION_TYPES
			);

			partitioner.connect(document);
			
			//returns an IFile which is a subclass of IResource
			try 
			{
			    if(element instanceof FileEditorInput) 
				{
					document.setParserResource(((FileEditorInput)element).getFile());
					document.clearAllMarkers();
					document.parseDocument();
				}
			    else if(element instanceof JavaFileEditorInput) 
				{
			        String filepath = ((JavaFileEditorInput)element).getPath(element).toString();
			        IPath path = new Path(filepath);
			        Workspace workspace = (Workspace)CFMLPlugin.getWorkspace();
			        IFile file = new ExternalFile(path,workspace);
			        model = ((ExternalFile)file).getAnnotationModel();
				
					document.setParserResource(file);
					document.clearAllMarkers();
					document.parseDocument();
				}
			    else if (element instanceof RemoteFileEditorInput) 
				{
			        String filepath = ((RemoteFileEditorInput)element).getPath(element).toString();
			        Path path = new Path(filepath);
			        Workspace workspace = (Workspace)CFMLPlugin.getWorkspace();
			        ExternalFile file = new ExternalFile(path,workspace);
			        model = file.getAnnotationModel();
			        document.setParserResource(file);
			        document.clearAllMarkers();
			        document.parseDocument();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace(System.err);
			}

			document.setDocumentPartitioner(partitioner);
		}
		
		return document;
	}

	protected boolean setDocumentContent(IDocument document, IEditorInput editorInput, String encoding) throws CoreException 
	{
		if(editorInput instanceof JavaFileEditorInput) 
		{
			JavaFileEditorInput input = (JavaFileEditorInput) editorInput;
			FileInputStream contentStream = null;
			
			try 
			{
				contentStream = new FileInputStream(input.getPath(editorInput).toFile());
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			setDocumentContent(document, contentStream, encoding);
		}
		
		if(editorInput instanceof RemoteFileEditorInput) 
		{
			RemoteFileEditorInput input = (RemoteFileEditorInput) editorInput;
			FTPConnection connection = new FTPConnection();
			BufferedInputStream contentStream = null;
			contentStream = connection.getInputStream(input.getPath(editorInput).toString());
			
			setDocumentContent(document, contentStream, encoding);
		}
		
		return super.setDocumentContent(document, editorInput, encoding);
	}

	protected void doSaveDocument(IProgressMonitor monitor, Object element,
			IDocument document, boolean overwrite) throws CoreException 
	{
		if(document instanceof ICFDocument) 
		{
			((ICFDocument) document).clearAllMarkers();
			((ICFDocument) document).parseDocument();	
		}
		
		if(element instanceof RemoteFileEditorInput)  
		{
			try 
			{
				saveExternalFile((RemoteFileEditorInput)element,document);
			}
			catch (IOException e) 
			{
				Status status = new Status(IStatus.ERROR,"org.cfeclipse.cfml",IStatus.OK,e.getMessage(),e);
				throw new CoreException(status);
			}
		} else if(!(element instanceof FileEditorInput) && element instanceof IPathEditorInput)
		{
			try 
			{
				saveExternalFile((IPathEditorInput)element,document);
			}
			catch (IOException e) 
			{
				Status status = new Status(IStatus.ERROR,"org.cfeclipse.cfml",IStatus.OK,e.getMessage(),e);
				throw new CoreException(status);
			}
		}
				
		super.doSaveDocument(monitor, element, document, overwrite);
	}
	
	private void saveExternalFile(IPathEditorInput input, IDocument doc) throws IOException 
	{
		FileWriter writer = new FileWriter(input.getPath().toFile());
		writer.write(doc.get());
		writer.close();
	}
	
	private void saveExternalFile(RemoteFileEditorInput input, IDocument doc) throws IOException 
	{
		BufferedOutputStream contentStream = null;
		FTPConnection connection = new FTPConnection();
		connection.saveFile(doc.get().getBytes(),input.getPath(input).toString());
	}
	
	public IAnnotationModel getAnnotationModel(Object element) 
	{
		if(element instanceof FileEditorInput) 
		{
			return super.getAnnotationModel(element);
		}
	    
		return model;
	}
	
	public boolean isModifiable(Object element) 
	{
		if(!isStateValidated(element)) 
		{
			if (element instanceof IFileEditorInput) 
			{
				return true;
			}
		}
		
		if(element instanceof JavaFileEditorInput) 
		{
		    JavaFileEditorInput input = (JavaFileEditorInput)element;
	        return input.getPath(input).toFile().canWrite();
		}
		
		if(element instanceof RemoteFileEditorInput) 
		{
		    RemoteFileEditorInput input = (RemoteFileEditorInput)element;    
		    return input.canWrite();
		}
		
		return super.isModifiable(element);
	}
	
	public boolean isReadOnly(Object element) 
	{
	    if(element instanceof JavaFileEditorInput) 
	    {
	    		JavaFileEditorInput input = (JavaFileEditorInput)element;
	    		return !input.getPath(input).toFile().canWrite();
	    }
	    
	    if(element instanceof RemoteFileEditorInput) 
	    {
	    		RemoteFileEditorInput input = (RemoteFileEditorInput)element;
	    		return !input.canWrite();
	    }
	    return super.isReadOnly(element);
	}
}