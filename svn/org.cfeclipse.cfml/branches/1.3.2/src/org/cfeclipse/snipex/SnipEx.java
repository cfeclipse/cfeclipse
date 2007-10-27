package org.cfeclipse.snipex;

import java.net.URL;
import java.util.Date;

public class SnipEx extends Library {
	private Date loadDate;
	
	public SnipEx(URL src, boolean fullReload) {
		setSource(src);
		setLoadDate( new Date() );
		
		new LibraryHandler( this , fullReload);
	}
	
	public SnipEx(URL src, Date d,  boolean fullReload) {
		setSource( src );
		setLoadDate( d );

		new LibraryHandler( this , fullReload);
	}
	
	/** Accessor Methods **/
	public Date getLoadDate() { return loadDate; }
	
	/** Mutator Methods **/
	public void setLoadDate(Date l) { loadDate = l; }
	
	/** Convenience Methods **/
}
