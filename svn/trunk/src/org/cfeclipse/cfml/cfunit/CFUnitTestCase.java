package org.cfeclipse.cfml.cfunit;

import java.util.Observable;
import java.lang.Exception;

import java.net.URL;
import java.net.URLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * The test model object. This is used to store the state of the current test. 
 * This object is an observable singleton.
 * 
 * @author Robert Blackburn
 */
public class CFUnitTestCase extends Observable {
	
	public final static int STATE_NONE = 0; // No test is set
	public final static int STATE_UNTESTED = 1; // The test has been set but not tested
	public final static int STATE_TESTING = 3; // The test is currently being tested
	public final static int STATE_TESTED = 2; // The test has been tested
	
	static private CFUnitTestCase instence;
	
	private String name;
	private int state;
	//private int errorCount;
	//private int failureCount;
	private int runCount;
	private CFUnitTestResult[] results;
	private String[] metadata;
 	
	private CFUnitTestCase() {
		reset();
	}
	
	public static CFUnitTestCase getInstence() {
		if(instence == null) {
			instence = new CFUnitTestCase();
		}
		
		return instence;
	}
	
	public void reset() {
		setName( null );
		setState( STATE_NONE );
		setRunCount( 0 );
		
		notifyObservers();
	}
	
	public void setTest(String test) {
		if(!test.equals(  getName() )) {
			setName( test );
			setState( STATE_UNTESTED );
			setRunCount( 0 );
			clear();
			
			notifyObservers();
		}
	}
	
	/**
	 * Runs this test case and updates the results collection.
	 * @return True is executed successfully, false otherwise.
	 */
	public boolean run() { 
		if(!getName().trim().equals("")) {

			try {
				URL url = getURL();
				
				if(url != null) {
					URLConnection connection = url.openConnection();
					
					BufferedReader in = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
			      
					CFUnitTestResult test = null;
			        String line = in.readLine();
			        
			        readMetadata( line );
			        results = new CFUnitTestResult[ readCount() ];
			        
			        int i = 0;
			        while ((line = in.readLine()) != null) {
			        	if(line.trim().length() > 0) {
				        	if( line.charAt(0) == '[' & line.charAt( line.length()-1) == ']' ) {
				        		test = new CFUnitTestResult( line.substring(1, line.length()-1) );
				        		results[i] = test;
				        		i++;
								
				        	} else {
				        		if( test != null) {
				        			test.appendDetails( line );
				        		}
				        	}
			        	}
			        }
			        
			        this.setRunCount( results.length );
			        
			        in.close();
				}
			} catch(java.io.IOException e) {
				addCriticalErrorResult( e );
				notifyObservers();
				return false;
			}
			
			setChanged();
			notifyObservers();
			return true;
		} else {
			clear();			
			return false;
		}
	}
	
	/**
	 * Gets the URL to be used to execute the current test case.
	 * @return The test case executable URL
	 */
	private URL getURL() {
		
		String name = getName();
		
		try {
			URL url;
			
			if(name.indexOf("http://") != -1) {
				if(name.indexOf('?') == -1) {
					url = new URL( name + "?method=execute&html=0&verbose=2" );
				} else {
					url = new URL( name + "&method=execute&html=0&verbose=2" );
				}
								
			} else {
				
				String facade_url = CFMLPlugin.getDefault().getPreferenceStore().getString("CFUnitFacadeLocation");
				
				if( !facade_url.trim().equals("") ) {
					if( facade_url.indexOf(".cfc") == -1 ) {
						url = new URL( "http://" + facade_url + "/CFEclipseFacade.cfc?method=execute&test=" + name );
					} else {
						url = new URL( "http://" + facade_url + "?method=execute&test=" + name );
					}
				} else {
					addCriticalErrorResult( new Exception( "No CFUnit Facde URL Set\nTo set this preference click 'Window' > 'Preferences...', then select 'CFEclipse' > 'CFUnit'" ) );
					notifyObservers();
					return null;
				}
					
			}
			
			return url;
			
		} catch(Exception e) {
			addCriticalErrorResult( e );
			notifyObservers();
			return null;
		}
	}

	private void readMetadata(String line) {
		metadata = line.substring(1, line.length()-1).split(":");
	}
	
	private int readCount() {
		for(int i = 0; i < metadata.length; i++ ) {
			String element = metadata[i];
			
			int index = element.indexOf("=");
			String value = element.substring( index+1 );
			String key = element.substring(0, index );
			
			if(key.equals("count")) {
				return Integer.parseInt( value );
			}
		}
		
		return 0;
	}
	
	/**
	 * Adds a critical network error to the results. This will also clear all 
	 * revious result.
	 * @param line The error thrown when failure occurred
	 */
	private void addCriticalErrorResult(java.lang.Exception e) {
		clear();
		
		// Create critical error
		CFUnitTestResult test = new CFUnitTestResult( "init" );
		test.appendDetails( e.toString() );
		
		// Add critical error to results
		results = new CFUnitTestResult[ 1 ];
		results[0] = test;
		setRunCount( 0 );
		
		setChanged();
	}
	
	private void clear() {
		results = new CFUnitTestResult[ 0 ];
		setChanged();
		notifyObservers();
	}
	
	private void setName(String newName) {
		if(name == null) {
			name = newName;
			setChanged();
		} else if(!name.equals(newName)) {
			name = newName;
			setChanged();
		}
	}
	
	public String getName() {
		return name;
	}
	
	private void setState(int s) {
		if(state != s) {
			state = s;
			setChanged();
		}
	}
	
	public int getState() {
		return state;
	}
	
	public int getTestCount() {
		return results.length;
	}
	
	public int getErrorCount() {
		int c = 0;
		
		if(results != null) {
			for(int i = 0; i < results.length; i++ ) {
				if(results[i].getType() == CFUnitTestResult.ERROR) {
					c++;
				}
			}
		}
		return c;
	}
	
	public int getFailureCount() {
		int c = 0;
		
		if(results != null) {
			for(int i = 0; i < results.length; i++ ) {
				if(results[i].getType() == CFUnitTestResult.FAILURE) {
					c++;
				}
			}
		}
		
		return c;
	}
	
	private void setRunCount(int c) {
		if(state != c) {
			runCount = c;
			setChanged();
		}
	}
	
	public int getRunCount() {
		return runCount;
	}
	
	public CFUnitTestResult[] getResults() {
		return results;
	}
	

	/**
	 * Get the full path name of a resource based on its name and 
	 * location. For example, if a resource was 
	 * "/myProject/subfolder/SomeObject.cfc", this method would return 
	 * "myProject.subfolder.SomeObject"
	 * 
	 * @param resource The resource to get the name from
	 * @return The full path-based name of the resource
	 */
	public static String getResourceFullName(IResource resource) {
		if(!resource.getFileExtension().equals("cfc")) {
			MessageDialog.openError(null, "Selected file not a CFC", "The selected file was not a ColdFusion Component (CFC). A unit test must be a CFC that extends a TestCase. Please refer to the CFUnit web site (http://cfunit.sourceforge.net/) for help documents and information on how to set up unit tests.");
			return "";
			
		} else {
			String path = resource.getName();
			
			org.eclipse.core.resources.IContainer pathPart = resource.getParent();
			while(pathPart != null) {
				if(!pathPart.getName().trim().equals("")) {
					path = pathPart.getName()+'.'+path;
				}
				pathPart = pathPart.getParent();
			}
			
			// Remove the file extention
			path = path.substring(0, path.indexOf( resource.getFileExtension() )-1);
			
			return path;
		}
	}
	
}
