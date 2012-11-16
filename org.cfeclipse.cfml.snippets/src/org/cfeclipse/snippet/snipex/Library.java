package org.cfeclipse.snippet.snipex;

import java.util.Date;
import java.util.Vector;
import java.net.URL;
import java.net.MalformedURLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Library model object - used to store library properties.
 * 
 * @author Robert Blackburn
 *
 */
public class Library {
	private String id;
	private String name;
	private Vector snippets;
	private Vector libraries;
	private Date createdAt;
	private String description;
	private URL source;
	private URL detailsAddress;
	private URL proposalAddress;
	
	
	//logger
	private Log logger = LogFactory.getLog(Library.class);
	/** Base Constructors **/
	public Library() {}
	
	public Library(URL source) {
		setSource(source);
	}
	
	/** Accessor Methods **/
	public String getID() { return id; }
	public String getName() { return name; }
	public Date getCreatedAt() { return createdAt; }
	public String getDescription() { return description; }
	public URL getSource() { return source; }
	public URL getDetailsAddress() { return detailsAddress; }
	public URL getProposalAddress() { return proposalAddress; }
	public Vector getSnippets() { 
		if(snippets == null) {
			 load(true);
		}
		return snippets; 
	}
	public Vector getLibraries() {
		if(libraries == null) {
			 load(true);
		}
		return libraries;
	}
	
	/** Mutator Methods **/
	public void setID(String i) { id = i; }
	public void setName(String n) { name = n; }
	public void setSnippets(Vector s) { snippets = s; }
	public void setLibraries(Vector l) { libraries = l; }
	public void setCreatedAt(Date d) { createdAt = d; }
	public void setDescription(String d) { description = d; }
	public void setSource(URL url) { source = url; }
	public void setSource(String url) throws MalformedURLException { source = new URL( url ); }
	public void setDetailsAddress(URL url) { detailsAddress = url; }
	public void setDetailsAddress(String url) throws MalformedURLException { detailsAddress = new URL( url ); }
	public void setProposalAddress(URL url) { proposalAddress = url; }
	public void setProposalAddress(String url) throws MalformedURLException { proposalAddress = new URL( url ); }
	
	/** Convenience Methods **/
	public boolean addLibrary(Library l) { return libraries.add( l ); }
	public boolean removeLibrary(Library l) { return libraries.remove( l ); }
	public void clearLibraries() { libraries.clear(); }
	public int getLibCount() { return libraries.size(); }
	
	public boolean addSnippet(Snippet s) { return snippets.add( s ); }
	public boolean removeSnippet(Snippet s) { return snippets.remove( s ); }
	public void clearSnippet() { snippets.clear(); }
	public int getSnipCount() { return snippets.size(); }
	
	/** Specialized Methods **/
	private void load(boolean fullReload) {
		setSnippets( new Vector() );
		setLibraries( new Vector() );
		new LibraryHandler( this, fullReload );
	}

	public String toString() {
		return getName();
	}

	/**
	 * This method sends a snippet to the server for submission
	 */
	
}
