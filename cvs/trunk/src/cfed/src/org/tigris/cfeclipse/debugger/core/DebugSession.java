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
package org.tigris.cfeclipse.debugger.core;

import java.lang.String;
import java.net.URL;
import java.net.MalformedURLException;
import org.tigris.cfeclipse.debugger.protocol.DebugProtocol;
import org.tigris.cfeclipse.debugger.protocol.DebugProtocolException;
import java.io.IOException;

//this should be removed at some point
import org.tigris.cfeclipse.debugger.protocol.BlueDragonProtocol;

public class DebugSession {
	/** the username to use */
	private String username;
	/** the password to use */
	private String password;
	/** the server to connect to: note this is for the debug
	 * session connection <b>not</b> for the page. for example
	 * BDD needs to connect to http://localhost:45000/ to open the 
	 * debug terminal, not the page.
	 */
	private URL server;
	
	/** the server should provide this */
	private String sessionid;
	
	/** what protocol we should load and use */
	private DebugProtocol debugprotocol;
	
	/**
	 * the default ctor. If they dont pass anything assume
	 * its localhost bd sever stuff with no auth
	 */
    public DebugSession()
    {
    	setServer("http://192.168.1.202:45000");	
    	setUsername("");
    	setPassword("");
    	try
		{
    		setDebugProtocol(new BlueDragonProtocol(this));
    		debugprotocol.doLogin(username,password);
		}
    	catch(IOException ioe)
		{
    		ioe.printStackTrace(System.err);
		}
    	catch(DebugProtocolException dpe)
		{
    		dpe.printStackTrace(System.err);
		}
    }
    
    /**
     * After the connection is ready to go, all commands should be sent this a 
     * way
     * @param command the cfe debug command
     * @return the response from the server
     */
    public DebugResponse issueCommand(DebugCommand command) throws UnknownCommandException
    {
    	return debugprotocol.sendReceive(command);
    }
    
	/**
	 * @return Returns the debugprotocol.
	 */
	public DebugProtocol getDebugprotocol() 
	{
		return debugprotocol;
	}
	
	/**
	 * @param debugprotocol The debugprotocol to set.
	 */
	public void setDebugProtocol(DebugProtocol debugprotocol)
	{
		this.debugprotocol = debugprotocol;
	}
	
	/**
	 * @return Returns the password.
	 */
	public String getPassword() 
	{
		return password;
	}
	
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	/**
	 * @return Returns the server.
	 */
	public URL getServer() 
	{
		return server;
	}
	
	public void setServer(String url)
	{
		try
		{
			setServer(new URL(url));
		}
    	catch(MalformedURLException mae)
		{
    		mae.printStackTrace(System.err);
		}
	}
	
	/**
	 * @param server The server to set.
	 */
	public void setServer(URL server) 
	{
		this.server = server;
	}
	
	/**
	 * @return Returns the sessionid.
	 */
	public String getSessionId() 
	{
		return sessionid;
	}
	
	/**
	 * @param sessionid The sessionid to set.
	 */
	public void setSessionId(String sessionid) 
	{
		this.sessionid = sessionid;
	}
	
	/**
	 * @return Returns the username.
	 */
	public String getUsername() 
	{
		return username;
	}
	
	/**
	 * @param username The username to set.
	 */
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	public void end()
	{
		debugprotocol.shutdown();
	}
}