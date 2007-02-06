package org.cfeclipse.cfml.cfunit;

import java.util.Observable;
import java.util.Observer;

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
		
		CFUnitTestCase tc = new CFUnitTestCase( test );
		CFUnitTestCase[] ts = {tc};
		
		setTestCases( ts );
		setLabel( test );
		
		notifyObservers();
	}
	
	public boolean run() {		
		if(cases != null) {
			for(int i = 0; i < cases.length; i++ ) {
				cases[i].run();
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
	
	/*** End: Static Test Case Methods ***/
}
