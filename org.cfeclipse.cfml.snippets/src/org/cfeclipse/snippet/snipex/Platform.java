package org.cfeclipse.snippet.snipex;

/**
 * Platform uses a bitwise mapping to track what platforms a snippet is 
 * compatible with.
 * <p />
 * By using a bitwise mapping, the snippets compatibility can be store din a 
 * single int variable. This helps with performance and uses less system 
 * resources. But it is also a convenient was to store and communicate 
 * compatibility.
 * <p />
 * hasPlatform() and addPlatform() are convenience methods to help handle this 
 * gracefully.
 * 
 * @author Robert Blackburn
 */
public class Platform {
	
	/** CF Server Version **/
	public static final int CF3 = 1 << 0;
	public static final int CF4 = 1 << 1;
	public static final int CF5 = 1 << 2;
	public static final int CF6 = 1 << 3;
	public static final int CF7 = 1 << 4;
	public static final int CF8 = 1 << 5; // For future use
	public static final int CF9 = 1 << 6; // For future use
	
	/** BlueDragon Versions **/
	public static final int BD5 = 1 << 10;
	public static final int BD6 = 1 << 11;
	public static final int BD7 = 1 << 12;
	public static final int BD8 = 1 << 13; // For future use
	public static final int BD9 = 1 << 14; // For future use

	/**
	 * Convenience method to help check to see if a platform is a 
	 * part of a platform set
	 * 
	 * @param platformSet The platforms to check against
	 * @param platformToCheck The platform to check for
	 * @return True of platformToCheck is in platforms
	 */
	public static boolean hasPlatform(int platformSet, int platformToCheck) {
		return ((platformSet & platformToCheck) == platformToCheck);
	}
	
	/**
	 * Convenience method to add a platform to a platform set
	 *  
	 * @param platformSet The platforms to add too
	 * @param platformToAdd The platform to be added
	 * @return The platformSet with the platformToAdd added
	 */
    public static int addPlatform(int platformSet, int platformToAdd) {
    	platformSet |= platformToAdd;
    	return platformSet;
    }
    
    /** Convert platofrm name to its mapcode
     * @param p The platform to get a map code for
     * @return The mapcode for the platform described, or 0 is an unknown type
     */
    public static int getMapCode(String p) {
    	if( p.equals("CF3")) {
    		return CF3;
    	} else if( p.equals("CF4")) {
    		return CF4;
    	} else if( p.equals("CF5")) {
    		return CF5;
    	} else if( p.equals("CF6")) {
    		return CF6;
    	} else if( p.equals("CF7")) {
    		return CF7;
    	} else if( p.equals("CF8")) {
    		return CF8;
    	} else if( p.equals("CF9")) {
    		return CF9;
    	} else if( p.equals("BD5")) {
    		return BD5;
    	} else if( p.equals("BD6")) {
    		return BD6;
    	} else if( p.equals("BD7")) {
    		return BD7;
    	} else if( p.equals("BD8")) {
    		return BD8;
    	} else if( p.equals("BD9")) {
    		return BD9;
    	}
    	
    	return 0;
    }
}
