/*
 * Created on 07-Jan-2005
 *
 * 
 */
package org.cfeclipse.frameworks.fusebox.parsers;

import java.util.ArrayList;

import org.cfeclipse.frameworks.fusebox.objects.FBXApplication;
import org.cfeclipse.frameworks.fusebox.objects.FBXCircuit;
import org.eclipse.core.resources.IProject;

/**
 * @author Mark Drew
 *
 */
public interface IFBXparser {
	public abstract FBXApplication parse();

	/**
	 * Pass in the project for which you want to get an application for
	 * @param project
	 * @return
	 */
	public abstract FBXApplication parse(IProject project);

	public abstract ArrayList getCircuits(IProject project);

	/**
	 * This method returns a HashMap of key/value pairs of the circuits used so if we pass in 
	 * <code>
	 * <cfscript>
	 * 	fusebox.circuit.home 		= "home";
	 *  fusebox.circuit.services 	= "home/services";
	 * 	fusebox.curcuit.products	= "home/products";
	 * </cfscript>
	 * </code>
	 * or
	 * <code>
	 * <cfset fusebox.circuit.home = "home">
	 * <cfset fusebox.circuit.services 	= "home/services">
	 * <cfset fusebox.curcuit.products	= "home/products">
	 * </code>
	 *  it should return
	 * 	key			value
	 * 	home		home
	 * 	services	/services
	 * 	producrs	/products
	 * @param fbxcircuits
	 * @return 
	 */
	public abstract ArrayList parseCircuits(String fbxcircuits);

	public abstract ArrayList getXFAs(FBXCircuit circuit, IProject project);

	public abstract ArrayList parseXFA(String xfafile);

	public abstract ArrayList getFuseactions(FBXCircuit circuit,
			IProject project);
}