/*
 * Created on Mar 20, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.parser;

import java.util.ArrayList;

/**
 * @author ollie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ProblemManager {
	/**
	 * 
	 */
	public ProblemManager() {}
	
	protected static ArrayList problemList = new ArrayList();
	
	public static void initProblemManager() 
	{
		
	}
	
	public static void addProblem(CFProblem newProblem)
	{
		problemList.add(newProblem);
	}
	
	public static ArrayList getProblemList()
	{
		return problemList;
	}
}
