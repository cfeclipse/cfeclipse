/*
 * Created on Jun 12, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package org.tigris.cfeclipse.debugger.protocol;

import java.lang.String;
import org.tigris.cfeclipse.debugger.core.DebugCommand;
//import org.tigris.cfeclipse.debugger.core.DebugResponse;
import org.tigris.cfeclipse.debugger.core.DebugSession;
import org.tigris.cfeclipse.debugger.core.UnknownCommandException;

import java.io.IOException;
import java.util.List;

/**
 * abstract class to create a protocol for a product that will need to interact
 * with the debugger. The protocol mostly routes DebugCommands to product 
 * specific command. Since we only have access to the BlueDragon debugger we will
 * make the wild assumption that all protocol commands for all products can be 
 * sent as strings... 
 * @author rob
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public abstract class DebugProtocol {
	/** noop command */
	public static final String CFE_COMMAND_NOOP 	= "NOOP";
	/** If a file change is needed to be taken from the stack */
	public static final String CFE_COMMAND_POPFILE 	= "POPFILE";
	/** If a file change is needed to be pushed on the stack */
	public static final String CFE_COMMAND_PUSHFILE = "PUSHFILE";
	/** line number argument */
	public static final String CFE_ARGUMENT_LINENUMBER = "LINENUMBER";
	/** column number argument */
	public static final String CFE_ARGUMENT_COLUMNNUMBER = "LINENUMBER";
	/** command to manipulate steps */
	public static final String CFE_COMMAND_STEP   	= "STEP";
	/** command to manipulate trace */
	public static final String CFE_COMMAND_TRACE  	= "TRACE";
	/** command to manipulate profiles */
	public static final String CFE_COMMAND_PROFIE 	= "PROFILE";
	/** get or show data */
	public static final String CFE_COMMAND_DATA 	= "GETDATA";
	
	/** some command accept toggles (like step: could be on or off or
	 * could just be a call to step) if the argument of toggle is there
	 * the value will be either on or off */
	public static final String CFE_ARGUMENT_TOGGLE 	= "TOGGLE";
	/** toggle a command on */
	public static final String CFE_VALUE_ON			= "ON";
	/** toggle a command off */
	public static final String CFE_VALUE_OFF		= "OFF";
	
	/** a handle to the debug session. username
	 * password, sessionid, etc can be gotten here
	 */
	protected DebugSession debugsession;
	
	/**
	 * the protocol passed the session that started it. this ctor will need
	 * to be used unless you know something I dont.
	 * @param ds
	 */
	public DebugProtocol(DebugSession ds) throws IOException
	{
		debugsession = ds;
	}
    
	/**
	 * do any clean up - shutdown the connection etc
	 */
	public abstract void shutdown();
	
	
    /**
     * Translates the cfeclipse debug command into a sendable command for
     * the remote system, it should then communcate with the remote system and 
     * provide a response
     * @param command
     * @return the server response in cfe format
     * @throws UnknownCommandException
     */
    abstract public List sendReceive(DebugCommand command) throws UnknownCommandException;
    
    /**
     * log into the server with username and password.
     * @param username 
     * @param password
     * @return the login message if one exists
     * @throws DebugProtocolException if bad username password etc
     */
    abstract public String doLogin(String username, String password) throws DebugProtocolException;
        
    /**
     * call to go to the next step, all protocols will need to support this
     * @return
     */
    abstract public String nextStep();
    
    /**
     * get a variable
     * @param var
     * @return
     */
    abstract public String getVariable(String var);
    
    /**
     * set a break point
     * @param file
     * @param line
     * @param column
     * @return
     */
    abstract public String setBreakPoint(String file, int line, int column);
}