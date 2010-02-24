/* 
 * $Id: DebugUtils.java,v 1.1 2005/03/03 03:12:58 smilligan Exp $
 * $Revision: 1.1 $
 * $Date: 2005/03/03 03:12:58 $
 * 
 * Created Mar 2, 2005 2:49:15 PM
 *
 * CFEclipse - The Open Source ColdFusion Development Environment
 * Copyright (c) 2005 Stephen Milligan and others.  All rights reserved.
 * For more information on cfeclipse, please see http://cfeclipse.tigris.og/.
 *
 * ====================================================================
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the Eclipse Public License
 * as published by the Eclipse Foundation; either
 * version 1.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Eclipse 
 * Public License for more details.
 *
 * You should have received a copy of the Eclipse Public License with this 
 * software. If not, the full text of version 1.0 of the Eclipse Public License 
 * is available online at the following URL:
 * 
 * http://www.eclipse.org/legal/epl-v10.html
 * ====================================================================
 */
package org.cfeclipse.cfml.plugindebug;

import org.cfeclipse.cfml.CFMLPlugin;

/**
 * This class outputs debugging information and should be used in place of
 * System.out.println(); when you want to find out what's going on in your
 * code.
 * 
 * The main reason for using this class instead is so that we can modify
 * the behaviour in the future to write the output to a debug view or to a log 
 * file, so if a particular user is having issues with a build of CFEclipse
 * we can give them a build with the relevant debug flags in DebugSettings
 * turned on.
 * 
 * Usage for this class should be of the form:
 * 
 * if (DebugSettings.PARSER) {
 * 	DebugUtils.printMessage("Something happened in the parser.");
 * }
 * 
 * The rationale being that the fields in DebugSettings are declared as 
 * public static final boolean, so the compiler replaces them with the boolean
 * value at compile time. When it sees the if statement above it the reads it
 * as either:
 * 
 * if(true) {
 * 	DebugUtils.printMessage(getClass(),"Something happened in the parser.");
 * }
 * 
 * or 
 * if (false) {
 * 	DebugUtils.printMessage(getClass(),"Something happened in the parser.");
 * }
 * 
 * In the first case, the compiler will remove the if statmement and leave this:
 *  
 * DebugUtils.printMessage(getClass(),"Something happened in the parser.");
 * 
 * In the second case it will remove the whole statement. In other words, the debug
 * code incurs zero runtime overhead if all the flags are set to false.
 * 
 * The downside is that in order to change the flag you have to recompile any
 * classes that use the flag.
 * 
 * @author Stephen Milligan
 * @version $Revision: 1.1 $
 */
public class DebugUtils {

    public static void printMessage(final Class caller, final String message) {
		CFMLPlugin.log(caller.getName() + ":: " + message);
    }


    public static void printError(final Class caller, final String message) {
    	CFMLPlugin.log(caller.getName() + ":: " + message);
    }
    
}


/* 
 * CVS LOG
 * ====================================================================
 *
 * $Log: DebugUtils.java,v $
 * Revision 1.1  2005/03/03 03:12:58  smilligan
 * Added plugindebug package and a couple of utility classes to allow for more structured debug within the plugin.
 *
 */