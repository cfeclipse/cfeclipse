package org.cfeclipse.snipex;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Vector;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class LibraryHandler extends DefaultHandler {
	private boolean isStackReadyForText = false;
	
	private Library library;
	private Vector libraries;
	private Library currentLibrary;
	private Snippet currentSnippet;
	private StringBuffer currentString;
	private long maxCacheAge = 604800000; // 1 week
	
    public LibraryHandler(Library l, boolean fullReload) {
    	super();
    	
    	library = l;
		library.setLibraries( new Vector() );
		library.setSnippets( new Vector() );
		
		try {
			String dir = System.getProperty("user.dir")+"/cache";
			System.out.println(dir);
			File cacheFolder = new File(dir);
			if( !cacheFolder.exists() ) {
				cacheFolder.mkdir();
			}
			
			URL src = library.getSource();
			String query = src.getQuery();
			
			File cache;
			if(query != null) {
				cache = new File( dir+"\\lib."+src.getHost()+src.getPath().replace('/', '.')+"."+query.replace('&', '.').replace('=', '_')+".xml");
			} else {
				cache = new File( dir+"\\lib."+src.getHost()+src.getPath().replace('/', '.')+".xml");
			}
			
			long age = new Date().getTime()-cache.lastModified();
			
			// If cache file not found or is old create it
			if(!cache.exists() || age > maxCacheAge || fullReload) {
				
				// Open the URL and cache file
				URLConnection connection = library.getSource().openConnection();
				java.io.FileOutputStream output = new java.io.FileOutputStream( cache );
				InputStreamReader stream = new InputStreamReader( connection.getInputStream() );
				
				// Loop over the XML and write it to the cache file
				int x;
				while ((x = stream.read()) != -1) {
					output.write( x );
				}
				
				// Close the streams
				output.close();
				stream.close();
				
			}
			
			// Read the cache file
			java.io.FileInputStream input = new java.io.FileInputStream( cache );
			
			// Parse the XML file
			SAXParserFactory tSaxParserFact = SAXParserFactory.newInstance();
			SAXParser tSAXParser = tSaxParserFact.newSAXParser();
			XMLReader xr = tSAXParser.getXMLReader();
			
			xr.setContentHandler( this );
			xr.setErrorHandler( this );
			
			xr.parse( new InputSource( input ) );
			
        } catch( java.lang.Exception e) {
        	System.err.println( "LibraryHandler failed:" + library.getSource() + " - "+e );	        	
        }
        
    }
    
    public void startDocument () {}

    public void endDocument () {}
    
    public void startElement (String uri, String name, String qName, Attributes atts) {
    	if( qName.equalsIgnoreCase("snipex")) {
    		library.setName(atts.getValue("name"));
    	
    	} else if( qName.equalsIgnoreCase("library") && !atts.getValue("id").equals(library.getID()) ) {
    		currentLibrary = new Library();
    		currentLibrary.setName(atts.getValue("name"));
    		currentLibrary.setID(atts.getValue("id"));
    		
    		try {
    			// Get the library URL without the query string
    			String lurl = library.getSource().toString();
    			
    			if(lurl.indexOf('?') > 0) {
    				lurl = lurl.substring(0, lurl.indexOf('?'));
    			}
    			
    			// Set the sub-library's URL
	    		URL u = new URL( lurl+"?libid="+atts.getValue("id") );
	    		currentLibrary.setSource( u );
			} catch(MalformedURLException e) {}
			    		
			currentString = new StringBuffer();
    		isStackReadyForText = true;
    		
    		library.addLibrary( currentLibrary );
    		
    	} else if( qName.equalsIgnoreCase("snippet")) {
    		currentSnippet = new Snippet();
    		currentSnippet.setID( atts.getValue("id") );
    		currentSnippet.setTemplate( Boolean.valueOf( atts.getValue("template") ).booleanValue() );
    		isStackReadyForText = false;
    		library.addSnippet(currentSnippet);
    		
    	} else if (	qName.equalsIgnoreCase("name") ||
	    			qName.equalsIgnoreCase("help") ||
					qName.equalsIgnoreCase("description") ||
					qName.equalsIgnoreCase("starttext") ||
					qName.equalsIgnoreCase("endtext") ||
					qName.equalsIgnoreCase("author") ||
					qName.equalsIgnoreCase("platforms") ||
					qName.equalsIgnoreCase("lastupdated") ) {
    		
			currentString = new StringBuffer();
    		isStackReadyForText = true;
    		
    	}
    }

    public void endElement (String uri, String name, String qName) {
    	if( qName.equalsIgnoreCase("library") && currentLibrary != null) {
    		currentLibrary.setDescription( currentString.toString().trim() );
    		currentLibrary = null;
    	} else {
    		if(currentSnippet != null) {
	    		if( qName.equalsIgnoreCase("name") ) {
		    		currentSnippet.setName(currentString.toString().trim());
		    	} else if( qName.equalsIgnoreCase("help") ) {
		    		currentSnippet.setHelp(currentString.toString().trim());
		    	} else if( qName.equalsIgnoreCase("description") ) {
		    		currentSnippet.setDescription(currentString.toString().trim());
		    	} else if( qName.equalsIgnoreCase("starttext") ) {
		    		currentSnippet.setStartText(currentString.toString().trim());
		    	} else if( qName.equalsIgnoreCase("endtext") ) {
		    		currentSnippet.setEndText(currentString.toString().trim());
		    	} else if( qName.equalsIgnoreCase("author") ) {
		    		currentSnippet.setAuthor(currentString.toString().trim());
		    	} else if( qName.equalsIgnoreCase("platforms") ) {
		    		String p = currentString.toString().trim();
		    		currentSnippet.setPlatforms(p, ",");
		    	}
    		}
    	}
    	
    	isStackReadyForText = false;
    }
    
    public void characters (char ch[], int start, int length) {
    	if( isStackReadyForText == true ) {
    		currentString.append( ch, start, length );
    	} else {}
	}
	
	public Vector getLibraries() {
		return libraries;
	}
	
}
