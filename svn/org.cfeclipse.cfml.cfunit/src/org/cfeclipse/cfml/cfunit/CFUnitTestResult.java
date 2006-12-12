package org.cfeclipse.cfml.cfunit;

import java.util.Observable;
import java.util.ArrayList;
import java.util.Iterator;

public class CFUnitTestResult extends Observable {
	public final static int SUCCESS = 0;
	public final static int ERROR = 1;
	public final static int FAILURE = 2;
	public final static int NWERROR = 3;
	
	private String name;
	private int type;
	private ArrayList details = new ArrayList();
	
	public CFUnitTestResult(String name) {
		setType( SUCCESS );
		setName( name );
	}
	
	public CFUnitTestResult(String name, int t) {
		setName( name );
		setType( t );
	}
	
	private void setName(String newName) {
		name = newName;
		
		if(name.equals("init")) {
			setType( NWERROR );
		}
		
		setChanged();
	}
	
	public String getName() {
		return name;
	}

	private void setType(int t) {
		type = t;
		setChanged();
	}
	
	public int getType() {
		return type;
	}
	
	public void appendDetails(String newLine) {
		
		if(newLine != null) {
			if(newLine.trim().length() > 0) {
				if( newLine.equals("FAILURE") ) {
					setType( FAILURE );
				} else if(newLine.contains("ERROR")) {
					setType( ERROR );
				}
			}
		
			details.add( newLine );
		}
	}
	
	public String[] getDetails() {
		String[] results = new String[ details.size() ];
		Iterator it = details.iterator();
		
		int i = 0;
		while( it.hasNext() ) {
			String s = it.next().toString();
			results[ i ] = s;
			i++;
		}
		
		return results;
	}
	
	public String toString() {
		return getName();
	}
	
}
