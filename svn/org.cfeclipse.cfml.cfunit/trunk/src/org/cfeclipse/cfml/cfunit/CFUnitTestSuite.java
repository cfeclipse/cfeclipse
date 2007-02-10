package org.cfeclipse.cfml.cfunit;

import java.io.LineNumberReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.widgets.Display;

import org.cfeclipse.cfml.CFMLPlugin;

public class CFUnitTestSuite extends Observable implements Observer {
	static private CFUnitTestSuite instence;
	
	private String label = "";
	private boolean executed = false;
	private CFUnitTestCase[] cases;
	private String[] metadata;
	
	private CFUnitTestSuite() {
		reset();
	}
	
	public static CFUnitTestSuite getInstence() {
		if(instence == null) {
			instence = new CFUnitTestSuite();
		}
		
		return instence;
	}
	
	public void reset() {
		setTestCases( null );
		notifyObservers();
	}
	
	public void setTest(String test) {
		if(cases != null) {
			if(cases.length == 1) {
				if(cases[0].getName().equals( test )) {
					return;
				}
			}
		}
		
		setLabel( test );
		LineNumberReader reader = getTestListReader( test );
		
		if(reader != null) {
			try {
				
				// TODO: Rework this to avoid two calls to getTestListReader
				LineNumberReader counter = getTestListReader( test );
				int linecount = 0;
				while (counter.readLine() != null) {
					linecount++;
				}
				counter.close();
				
				if( linecount > 0) {
					CFUnitTestCase[] ts = new CFUnitTestCase[ linecount ];
					
					String line;
					int i = 0;
			        while ((line = reader.readLine()) != null) {
			        	if(line.trim().length() > 0) {
			        		CFUnitTestCase tc = new CFUnitTestCase( line );
			        		ts[i] = tc;
			        		i++;
			        	}
			        }
			        
			        setTestCases( ts );
				} else {
					CFUnitTestCase tc = new CFUnitTestCase( test );
					CFUnitTestCase[] ts = {tc};
					setTestCases( ts );
				}
		        
				reader.close();
				
			} catch(java.io.IOException e) {}			
		} else {
			
			CFUnitTestCase tc = new CFUnitTestCase( test );
			CFUnitTestCase[] ts = {tc};
			setTestCases( ts );
			
		}
		
		notifyObservers();
	}
	
	public boolean run() {		
		
		if(cases != null) {
			Display display = Display.getCurrent();
			
			for(int i = 0; i < cases.length; i++ ) {
				display.asyncExec( cases[i] );
			}
		}
		
		setExecuted( true );
		notifyObservers();
		
		return true;
	}
	
	public CFUnitTestCase[] getTestCases() {
		return cases;
	}

	public void setTestCases(CFUnitTestCase[] tc) {
		if(cases == null || !tc.equals(cases)) {
			if(tc != null) {
				for(int i = 0; i < tc.length; i++ ) {
					tc[i].addObserver( this );
				}
			}
			
			cases = tc;
			
			setExecuted( false );
			setChanged();
			
		}
	}
	
	public String[] getMetadata() {
		return metadata;
	}

	public void setMetadata(String[] s) {
		if(!s.equals(metadata)) {
			metadata = s;
			setChanged();
		}
	}
	
	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(boolean e) {
		if(e != executed) {
			executed = e;
			setChanged();
		}
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String l) {
		if(!l.equals(label)) {
			label = l;
			setChanged();
		}
	}

	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}
	
	public String toString() {
		return getLabel();
	}
	
	/*** Begin: Static Test Case Methods ***/
	public static int getTestCount() {
		CFUnitTestCase[] testcases = getInstence().getTestCases();
		int c = 0;
		
		if(testcases != null) {
			for(int i = 0; i < testcases.length; i++ ) {
				c = c + testcases[i].getTestCount();
			}
		}
		
		return c;
	}
	
	public static int getErrorCount() {
		CFUnitTestCase[] testcases = getInstence().getTestCases();
		int c = 0;
		
		if(testcases != null) {
			for(int i = 0; i < testcases.length; i++ ) {
				c = c + testcases[i].getErrorCount();
			}
		}
		
		return c;
	}
	
	public static int getFailureCount() {
		CFUnitTestCase[] testcases = getInstence().getTestCases();
		int c = 0;
		
		if(testcases != null) {
			for(int i = 0; i < testcases.length; i++ ) {
				c = c + testcases[i].getFailureCount();
			}
		}
		
		return c;
	}
	
	public static int getRunCount() {
		CFUnitTestCase[] testcases = getInstence().getTestCases();
		int c = 0;
		
		if(testcases != null) {
			for(int i = 0; i < testcases.length; i++ ) {
				c = c + testcases[i].getRunCount();
			}
		}
		
		return c;
	}
	
	public static CFUnitTestResult getTestResult(String name) {
		CFUnitTestCase[] testcases = getInstence().getTestCases();
		
		if(testcases != null) {
			for(int i = 0; i < testcases.length; i++ ) {
				CFUnitTestCase tc = testcases[i];
				CFUnitTestResult[] r = tc.getResults();
				
				if(r != null) {
					for(int x = 0; x < r.length; x++ ) {
						if(r[x].toString().equals(name)) {
							return r[x];
						}
					}
				}
			}
		}
		
		return null;
	}
	
	public static CFUnitTestCase getTestCase(String name) {
		CFUnitTestCase[] testcases = getInstence().getTestCases();
		
		if(testcases != null) {
			for(int i = 0; i < testcases.length; i++ ) {
				CFUnitTestCase tc = testcases[i];
				if(tc.toString().equals(name)) {
					return tc;
				}
			}
		}
		
		return null;
	}
	
	private LineNumberReader getTestListReader( String location ) {
		
		try {
			URL url = getTestListURL( location );
			
			if(url != null) {
				URLConnection connection = url.openConnection();
				return new LineNumberReader( new InputStreamReader( connection.getInputStream() ) );
			}
		} catch(java.io.IOException e) {
			return null;
		}
		
		return null;
	}

	/**
	 * Gets the URL to be used to execute the current test case.
	 * @return The test case executable URL
	 */
	private URL getTestListURL( String location ) {
		
		try {
			
			String facade_url = CFMLPlugin.getDefault().getPreferenceStore().getString("CFUnitFacadeLocation");
			
			if( !facade_url.trim().equals("") ) {
				return new URL( "http://" + facade_url + "/CFEclipseFacade.cfc?method=getTests&location=" + location );
			} else {
				return null;
			}
						
		} catch(Exception e) {
			notifyObservers();
			return null;
		}
	}
	
	/*** End: Static Test Case Methods ***/
}
