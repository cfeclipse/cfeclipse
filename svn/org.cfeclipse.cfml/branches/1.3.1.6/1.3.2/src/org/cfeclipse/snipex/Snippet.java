package org.cfeclipse.snipex;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * Snippet model object - used to store snippet properties.
 * 
 * @author Robert Blackburn
 *
 */
public class Snippet {
	
	private String id;
	private String name;
	private String help;
	private String description;
	private String startText;
	private String endText;
	private String author;
	private int platforms;
	private Date lastupdated;
	private URL detailsAddress;
	private boolean template = false;
	
	/** Accessor Methods **/
	public String getID() { return id; }
	public String getName() { return name; }
	public String getHelp() { return help; }
	public String getDescription() { return description; }
	public String getStartText() { return startText; }
	public String getEndText() { return endText; }
	public int getPlatforms() { return platforms; }
	public String getAuthor() { return author; }
	public Date getLastUpdated() { return lastupdated; }
	public URL getDetailsAddress() { return detailsAddress; }
	public boolean isTemplate() { return template; }
	
	/** Mutator Methods **/
	public void setID(String i) { id = i; }
	public void setName(String n) { name = n; }
	public void setHelp(String h) { help = h; }
	public void setDescription(String d) { description = d; }
	public void setStartText(String t) { startText = t; }
	public void setEndText(String t) { endText = t; }
	public void setPlatforms(int p) { platforms = p; }
	public void setAuthor(String a) { author = a; }
	public void setLastUpdated(Date d) { lastupdated = d; }
	public void setDetailsAddress(URL url) { detailsAddress = url; }
	public void setDetailsAddress(String url) throws MalformedURLException { detailsAddress = new URL( url ); }
	public void setTemplate(boolean t) { template = t; }
	
	/** Convenience Methods **/
	public void setPlatforms(String p, String delimiter) { 
		String[] newPlatforms = p.split( delimiter );
		int pf = getPlatforms();
		
		for(int i=0; i<newPlatforms.length; i++){
			pf |= Platform.getMapCode( newPlatforms[i] );
		}
		
		setPlatforms( pf );
	}

	public String toString() {
		return getName();
	}
}
