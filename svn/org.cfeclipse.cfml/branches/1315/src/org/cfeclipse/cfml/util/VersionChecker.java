/**
 * 
 */
package org.cfeclipse.cfml.util;

import org.eclipse.core.resources.ResourcesPlugin;

/**
 * @author markdrew
 *
 */
public class VersionChecker {

	private String eclipseVersion = "";

	public VersionChecker() {
		super();
		eclipseVersion = (String)ResourcesPlugin.getPlugin().getBundle().getHeaders().get(org.osgi.framework.Constants.BUNDLE_VERSION);
		
	} 

	/**
	 * This function returns true if the major/minor version is less than or equal to the version passed in
	 * @param version
	 * @return
	 */
	public boolean isEqualTo(String version){
		//The version that comes in is in the format 3.2.xxxx
		String[] checkVersion = version.split(".");
		String[] eclipseVersionSplit = eclipseVersion.split(".");
		if(checkVersion[0] == eclipseVersionSplit[0] && checkVersion[1] == eclipseVersionSplit[1]){
			return true;
		}
		
		return false;
	}
	
	public boolean isLess(String version){
		
		
		String[] checkVersion = version.split("\\.");
		String[] eclipseVersionSplit = eclipseVersion.split("\\.");
		
		//Create the maj/minor trunk
		
		
		int checkMajor = Integer.parseInt(checkVersion[0] + checkVersion[1]);
		
		int eclipseMajor = Integer.parseInt(eclipseVersionSplit[0] + eclipseVersionSplit[1]);
		if(eclipseMajor < checkMajor){
			return true;
		}
		return false;
	}
	
}
