package org.cfeclipse.cfml.cfunit.wizards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.cfeclipse.cfml.views.cfcmethods.CFCMethodViewItem;

/**
 * This is the CFUnit wizard.
 * Its role is to create a new test case in the provided 
 * container. If the container resource (a folder or a 
 * project) is selected in the workspace when the wizard 
 * is opened, it will accept it as the target container.
 * 
 * @auther Robert Blackburn
 */
public class NewCFUnitWizard extends Wizard implements INewWizard {
	
	public final static String TITLE = "New MXUnit Testcase";
	public final static String PREFERENCES_PAGE_NAME = "preferencesPage";
	public final static String METHODS_PAGE_NAME = "methodsPage";
	public final static String PAGE_TITLE = "MXUnit Wizard";
	public final static String PAGE_DESCRIPTION = "For help or documentation: http://mxunit.org";
	public final ImageDescriptor PAGE_TITLE_IMG = ImageDescriptor.createFromFile( getClass(), "../../../../../../icons/obj16/mxunit_descriptor.gif" );
	
	private NewCFUnitWizardPage1 preferencesPage;
	private NewCFUnitWizardPage2 methodsPage;
	private ISelection selection;
	private IEditorPart editor;
	
	/**
	 * Constructor for NewCfmlWizard.
	 */
	public NewCFUnitWizard() {
		super();
		this.setWindowTitle( TITLE );
		setNeedsProgressMonitor( true );
	}
	
	/**
	 * Adding the page to the wizard.
	 */
	public void addPages() {
	    if (editor == null) {
	    	preferencesPage = new NewCFUnitWizardPage1(selection, PREFERENCES_PAGE_NAME, PAGE_TITLE, PAGE_TITLE_IMG);
	    } else {
	    	preferencesPage = new NewCFUnitWizardPage1(editor, PREFERENCES_PAGE_NAME, PAGE_TITLE, PAGE_TITLE_IMG);
	    }
	    preferencesPage.setDescription( PAGE_DESCRIPTION );
	    addPage( preferencesPage );
	}
	
	public void addMethodsPage() {
		methodsPage = new NewCFUnitWizardPage2(METHODS_PAGE_NAME, PAGE_TITLE, PAGE_TITLE_IMG);
		methodsPage.setDescription( PAGE_DESCRIPTION );
		addPage( methodsPage );
		
		methodsPage.setPreviousPage( preferencesPage );
		preferencesPage.setNextPage( methodsPage );
	}
	
	public void setEditor(IEditorPart editorPart) {
	    this.editor = editorPart;
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String containerName = preferencesPage.getContainerName();
		final String fileName = preferencesPage.getFileName();
		final String runnerName = preferencesPage.getRunner();
		final boolean createRunner = preferencesPage.isCreateRunner();
		
		//call the helper function to make the cfc text
		final StringBuffer sb = createStringBuffer();
		final InputStream runner = getRunnerContents();
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName, fileName, runnerName, createRunner, monitor, sb, runner);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * The worker method. It will find the container, create the
	 * file if missing or just replace its contents, and open
	 * the editor on the newly created file.
	 */
	private void doFinish(String containerName, String fileName, String runnerName, boolean isCreateRunner, IProgressMonitor monitor, StringBuffer sb, InputStream rs) throws CoreException {
		
		// Get the resource container
		monitor.beginTask("Creating " + fileName, 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException("Container \"" + containerName + "\" does not exist.");
		}
		
		IContainer container = (IContainer) resource;

		// Create the runner file
		if( isCreateRunner ) {
			final IFile runner;
			if (runnerName.lastIndexOf('.') != -1) {
				runner = container.getFile(new Path(runnerName));
			} else {
				runner = container.getFile(new Path(runnerName + ".cfm"));
			}
			
			try {
				InputStream stream = new ByteArrayInputStream(sb.toString().getBytes());
				if (runner.exists()) {
					runner.setContents(rs, true, true, monitor);
				} else {
					runner.create(rs, true, monitor);
				}
				
				stream.close();
			} catch (IOException e) {
				e.printStackTrace(System.err);
			}
		}
		
		// Create the test case file
		final IFile file;
		if (fileName.lastIndexOf('.') != -1) {
			file = container.getFile(new Path(fileName));
		} else {
			file = container.getFile(new Path(fileName + ".cfc"));
		}
		
		try {
			InputStream stream = new ByteArrayInputStream(sb.toString().getBytes());
			if (file.exists()) {
				file.setContents(stream, true, true, monitor);
			} else {
				file.create(stream, true, monitor);
			}
			
			stream.close();
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
		
		monitor.worked(1);
		monitor.setTaskName("Opening file for editing...");
		
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (PartInitException e) {
					e.printStackTrace(System.err);
				}
			}
		});
		monitor.worked(1);
	}
	
	/**
	 * Creates the CFML code to add to the new file
	 * @return The buffered string of the test case's contents
	 */
	private StringBuffer createStringBuffer() {
		StringBuffer sb = new StringBuffer();
		
		String cfcName = preferencesPage.getFileName().trim();
		String frameworkPath = preferencesPage.getFrameworkExtendsPath().trim();
		
		CFCMethodViewItem[] methods = null;
		if(methodsPage != null) {
			methods = methodsPage.getSelectedMethods();
			if(methods != null) {
				if( methods.length < 1) {
					methods = null;
				}
			}
		}
		
		int dotLoc = cfcName.lastIndexOf('.');
		if (dotLoc != -1) {
			cfcName = cfcName.substring(0, dotLoc);
		}
				
		sb.append( "<cfcomponent name=\"" + cfcName + "\" extends=\"" + frameworkPath + ".TestCase\">\n\n" );

		if( preferencesPage.isCreateSetupStub() ){
			sb.append("	<cffunction name=\"setUp\" returntype=\"void\" access=\"public\"></cffunction>\n\n" );
		}

		if( preferencesPage.isCreateTeardownStub() ){
			sb.append("	<cffunction name=\"tearDown\" returntype=\"void\" access=\"public\"></cffunction>\n\n" );
		}
		
		if( preferencesPage.isCreateOutputStub() ){
			sb.append("	<cffunction name=\"testOutput\" returntype=\"void\" access=\"public\" hint=\"Tests the template's output\">\n");
			sb.append("		<!--- TODO: Implement output test --->\n");
			sb.append("		<cfset fail(\"This test not yet implemented\") />\n");
			sb.append("	</cffunction>\n\n");
		}
		
		if(methods != null) {
			for (int i = 0; i < methods.length; i++) {
				CFCMethodViewItem m = methods[i];
				
				String methodDesc = m.toString();
				String methodName = methodDesc.substring(0, methodDesc.indexOf("(") ).trim();
				
				sb.append("	<cffunction name=\"test"+capitalizeFirst(methodName)+"\" returntype=\"void\" access=\"public\" hint=\"Tests "+methodName+"\">\n");
				sb.append("		<!--- TODO: Implement test - "+methodDesc.trim()+" --->\n");
				sb.append("		<cfset fail(\"This test not yet implemented\") />\n");
				sb.append("	</cffunction>\n\n");
			}
		} else {
			if(!preferencesPage.isCreateOutputStub()) {
				/* This is a generic test stub used if there were no stubs created 
				 * for methods or template output. */
				sb.append("	<!--- TODO: Implement Tests --->\n");
				sb.append("	<cffunction name=\"test\" returntype=\"void\" access=\"public\">\n");
				sb.append("		<cfset fail(\"Test case not yet implemented\") />\n");
				sb.append("	</cffunction>\n\n");
			}
		}
		sb.append("</cfcomponent>");
		return sb;
	}

	/**
	 * Creates an input stream of the contents of a test runner file.
	 * @return The contents of a runner file
	 */
	private InputStream getRunnerContents() {
		String contents = "";
		String path = preferencesPage.getFrameworkExtendsPath().trim();
		
		contents += "<cfsilent>\n";
		contents += "	<cfset webroot = ExpandPath(\"/\") />\n";
		contents += "	<cfset absDir = ExpandPath(\".\") />\n";
		contents += "	<cfset cfcPath = listChangeDelims( replaceNoCase(absDir,webroot, \"\"), \".\", \"\\\")  />\n\n";

		contents += "	<cfset tests = ArrayNew(1)>\n\n";
		
		contents += "	<cfdirectory action = \"list\" directory = \"#absDir#\" name = \"qTests\" filter=\"Test*.cfc\" />\n\n";
		
		contents += "	<cfloop query=\"qTests\">\n";
		contents += "		<cfset arrayAppend(tests, cfcPath & \".\" & listFirst(qTests.name, \".\") ) />\n";
		contents += "	</cfloop>\n\n";
		
		contents += "	<cfset testsuite = CreateObject(\"component\", \"" + path + ".TestSuite\").init( tests )>\n\n";
		
		contents += "</cfsilent>\n\n";

		contents += "<cfinvoke component=\"" + path + ".TestRunner\" method=\"run\">\n";
		contents += "	<cfinvokeargument name=\"test\" value=\"#testsuite#\">\n";
		contents += "	<cfinvokeargument name=\"name\" value=\"\">\n";
		contents += "</cfinvoke>\n\n";

		contents += "<cfoutput query=\"qTests\">\n";
		contents += "	<ul>\n";
		contents += "		<li><a href=\"#qTests.name#?method=execute&html=1&verbose=1\">#qTests.name#</a></li>\n";
		contents += "	</ul>\n";
		contents += "</cfoutput>\n";
		
		return new java.io.ByteArrayInputStream( contents.getBytes() );
	}
	
	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "com.eclipse.cfml", IStatus.OK, message, null);
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
	
	public static String capitalizeFirst(String str) {
		return (str.length()>0)? Character.toUpperCase(str.charAt(0))+str.substring(1):str;
	}
}
