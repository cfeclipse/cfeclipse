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

import org.tigris.cfeclipse.debugger.core.DebugCommand;
import org.tigris.cfeclipse.debugger.core.DebugResponse;
import org.tigris.cfeclipse.debugger.core.DebugSession;
import org.tigris.cfeclipse.debugger.core.DebugConnection;
import org.tigris.cfeclipse.debugger.core.UnknownCommandException;

import java.io.IOException;
import java.util.Map;
import java.net.SocketTimeoutException;

/**
 * BlueDragon specific protocol
 * 
 * @author rob
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class BlueDragonProtocol extends DebugProtocol {
	
	protected DebugConnection dc;
	
	public BlueDragonProtocol(DebugSession ds) throws IOException
	{
		super(ds); 
		dc = new DebugConnection(ds.getServer());
	}
	
	/**
     * Translates the cfeclipse debug command into a sendable command for
     * the remote system. returns the servers response
     * @param command
     * @return the server response in cfe format
     * @throws UnknownCommandException
     */
    public DebugResponse sendReceive(DebugCommand command) throws UnknownCommandException 
    {
    	String cfe_cmd = command.getCommand();
    	Map args = command.getArgs();
    	
    	String bdd_cmd = null;
    	
    	//is it a step command?
    	/*
    	 * To set the default step mode for new sessions, client sends 
    	 * +1:STEPON 
    	 * +:1:STEPON:DEFAULT 
    	 * 
    	 * To set the default step mode for new sessions 
    	 * +1:STEPOFF 
    	 * +:1:STEPOFF:DEFAULT 
    	 * 
    	 * You can set the step mode per session, in mid session, by 
    	 * appending the session id. 
    	 * 
    	 * +1:STEPOFF:s0 
    	 * +1:STEPON:s0 
    	 * 
    	 * To take a step to the next tag/expression, passing in the 
    	 * session identifier of the one you wish to step
    	 *  
    	 * +1:step:s01 
    	 * +:1:OK
    	 */
    	if(cfe_cmd.equals(DebugProtocol.CFE_COMMAND_STEP))
    	{
    		//if its a step command, is it a toggle or just a do a step?
    		if(args.containsKey(DebugProtocol.CFE_ARGUMENT_TOGGLE))
    		{
    			String stat = (String)args.get(DebugProtocol.CFE_ARGUMENT_TOGGLE);
    			bdd_cmd = buildCommand(cfe_cmd+stat,null);
    		}
    		else
    		{
    			//just do a step
    			bdd_cmd = buildCommand(cfe_cmd,null);
    		}
    	}
    	
    	///////////////////////////////////////////////////////////////////////
    	//now we have a bdd command send it and see what we get...
    	String server_response = "";
    	try
		{
    		server_response = dc.sendRecieve(bdd_cmd);
		}
    	catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
    	
    	checkForServerError(server_response,bdd_cmd);
    	
    	
    	System.out.println(server_response);
    	
    	DebugResponse dr = new DebugResponse();
    	
    	//TODO this should take the response and see if its xml
    	//etc - make it into something the ide will care about
    	dr.setResponse(server_response);
    	
    	return dr;
    }
    
    private void checkForServerError(String possible,String cmd) throws UnknownCommandException
    {
    	if(possible.indexOf("BAD") > 0)
    	{
    		throw new UnknownCommandException("Server does not know command: " + cmd);
    	}
    }
    
    
    private String buildCommand(String cmd, String arg)
    {
    	if(arg == null)
    	{
    		return "+" + "1" + ":" + cmd + ":" + debugsession.getSessionId();
    	}
    	else
    	{
    		return "+" + "1" + ":" + cmd + ":" + debugsession.getSessionId() + ":" + arg;
    	}
    }
    
    
    public String doLogin(String username, String password) throws DebugProtocolException 
	{
		try
		{
			String a = dc.sendRecieve(username + password);
			System.out.println(a);
			return a;
		}
		catch(SocketTimeoutException ste)
		{ 
			throw new DebugProtocolException(ste.getMessage());
		}
		catch(IOException ioe)
		{ 
			throw new DebugProtocolException(ioe.getMessage());
		}
	}
    
    /**
     * shutdown the connection
     */
    public void shutdown()
    {
    	try
		{
    		dc.close();
		}
    	catch(IOException ioe)
		{
    		ioe.printStackTrace(System.err);
		}
    }
    
    
    
    
    
    /**
     * call to go to the next step, all protocols will need to support this
     * @return
     */
    public String nextStep()
    {
    	return null;
    }
    
    /**
     * get a variable
     * @param var
     * @return
     */
    public String getVariable(String var)
    {
    	return null;
    }
    
    /**
     * set a break point
     * @param file
     * @param line
     * @param column
     * @return
     */
    public String setBreakPoint(String file, int line, int column)
    {
    	return null;
    }
}