package org.cfeclipse.frameworks.fusebox4.parsers;

/**
 * @author mark
 * This class tries to find the relevant files.
 * This could be a singleton since we only use it to find a particular file
 * 
 */
public class FBXFileFinder {
	private String scriptFileDelimiter = "cfm";
	private String fbxcircuits = "fusebox.xml";
	private String fbxswitch = "circuit.xml";
	private String altfbxcircuits = "fusebox.xml" + "." + scriptFileDelimiter;
	private String altfbxswitch = "circuit.xml" + "." + scriptFileDelimiter;
	
	
	
}
