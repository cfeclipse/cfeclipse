package com.rohanclan.cfml.parser;

public class CFProblem {
	protected String filename = "";
	protected String description = "";
	protected String location = "";
	protected int lineNumber = -1;
	
	public CFProblem(String newDesc)
	{
		description = newDesc;
	}
	public CFProblem(String newDesc, String probFile)
	{
		description = newDesc;
		filename = probFile;
	}
	public CFProblem(String newDesc, String probFile, int probLine)
	{
		lineNumber = probLine;
		description = newDesc;
		filename = probFile;
	}
	
	public String getFilename() { return filename; }
	public String getDescription() { return description; }
	public String getLocation() { return location; }
	public int getLineNumber() { return lineNumber; }
}