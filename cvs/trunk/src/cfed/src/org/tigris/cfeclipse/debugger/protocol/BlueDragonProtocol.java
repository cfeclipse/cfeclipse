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
import java.util.HashMap;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

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
	
	public BlueDragonProtocol(DebugSession ds) throws IOException, ConnectException
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
    public List sendReceive(DebugCommand command) throws UnknownCommandException 
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
    			bdd_cmd = buildCommand(cfe_cmd+stat,null,command);
    		}
    		else
    		{
    			//just do a step
    			bdd_cmd = buildCommand(cfe_cmd,null,command);
    		}
    	}
    	
    	///////////////////////////////////////////////////////////////////////
    	//now we have a bdd command send it and see what we get...
    	String server_response = "";
    	try
		{
    		server_response = dc.sendRecieve(
    			bdd_cmd,debugsession.getSessionId(),command.hashCode()
			);
		}
    	catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
    	  	
    	System.out.println(server_response);
    	
    	return unPackMessage(server_response);
    }
    
    /* private void checkForServerError(String possible,String cmd) throws UnknownCommandException
    {
    	if(possible.indexOf("BAD") > 0)
    	{
    		throw new UnknownCommandException("Server does not know command: " + cmd);
    	}
    } */
    
    
    private String buildCommand(String cmd, String arg, DebugCommand command)
    {
    	if(arg == null)
    	{
    		return "+" + command.hashCode() + ":" + cmd + ":" + debugsession.getSessionId();
    	}
    	else
    	{
    		return "+" + command.hashCode() + ":" + cmd + ":" + debugsession.getSessionId() + ":" + arg;
    	}
    }
    
    /**
     * Turns a string of several server responses in to a list of DebugResponses
     * @param messages
     * @return
     */
    public List unPackMessage(String messages)
    {
    	StringTokenizer st = new StringTokenizer(messages,"\n");
    	List allMessages = new ArrayList();
    	StringBuffer singlemessage = new StringBuffer();
    	
    	while(st.hasMoreTokens())
    	{
    		String line = st.nextToken();
    		
    		System.out.println("going to append " + line);
    		
    		singlemessage.append(line + "\n");
    		
    		//single line message, often just a command
    		if(line.startsWith("+") && !singlemessage.toString().startsWith("$"))
    		{
    			System.out.println("this is a single line command");
    			//add in the command
    			allMessages.add(breakOutSingleCommand(singlemessage.toString()));
    			//zero out buffer
    			singlemessage = new StringBuffer();
    			
    		//end of a multi line response
    		}else if(line.startsWith("+") && singlemessage.toString().startsWith("$"))
    		{
    			System.out.println("this is a multi line commnad");
    			StringTokenizer stok;
    			
    			//this can take a couple formats...
    			if(singlemessage.toString().indexOf(":STARTSESSION") > 0
    				|| singlemessage.toString().indexOf(":ENDSESSION") > 0
    			)
    			//{
    				//begining of a session
    			//}
    			//else if(singlemessage.toString().startsWith("ENDSESSION"))
    			{
    				//end session
    				stok = new StringTokenizer(singlemessage.toString(),"\n");
    				//this will create responses like TAGS[3] FUNCTIONS[2]
    				//they will be listed as commands but I think that'll be
    				//ok
    				while(stok.hasMoreTokens())
    				{
    					allMessages.add(
    						breakOutSingleCommand(stok.nextToken())
						);
    				}
    				singlemessage = new StringBuffer();
    			}
    			else
    			{
    				//its probably a data dump. It'll take up several lines and
    				//the xml data is on the second line
    				stok = new StringTokenizer(singlemessage.toString(),"\n");
    				DebugResponse dr = new DebugResponse();
    				
    				//skip the header info
    				stok.nextToken();
    				
					//and the xml line is in the format -:1:<xmldata>...
					StringTokenizer cmd = new StringTokenizer(stok.nextToken(),":");
					//skip the first 2 tokends
					cmd.nextToken(); cmd.nextToken();
					
					//grab the xml data
					String xmldata = cmd.nextToken();
					System.err.println(xmldata);
    				
					dr.setCommand(DebugProtocol.CFE_COMMAND_DATA);
					
					allMessages.add(dr);
					singlemessage = new StringBuffer();
    			}
    		}
    	}
    	
    	return allMessages;
    }
    
    /**
     * Turns a single +count:command[:arguments] into a DebugResponse object
     * @param line
     * @return
     */
    private DebugResponse breakOutSingleCommand(String line)
    {
    	System.out.println("** breaking out command " + line + " **");
    	DebugResponse dr = new DebugResponse();
    	StringTokenizer cmd = new StringTokenizer(line.toString(),":");
		Map args = new HashMap();
		
		int i=0;
		
		while(cmd.hasMoreTokens())
		{
			String part = cmd.nextToken();
			//System.out.println("part: " + part);
			switch(i)
			{
				case 0: 
					//the + part
					break;
				case 1: 
					//the s0 session part
					System.out.println("   session: " + part);
					args.put("0",part);
					break;
				case 2:
					//the command part
					dr.setCommand(part);
					System.out.println("   command: " + part);
					break;
				default:
					//assume the rest are arguments
					args.put(""+(i-2),part);
					System.out.println("   arg" + (i-2) + ": " + part);
			}
			i++;
		}
		dr.setArgs(args);
    	
    	return dr;
    }
    
    
    public String doLogin(String username, String password) throws DebugProtocolException 
	{
		try
		{
			System.out.println("Sending login command");
			
			String rawdata = dc.sendRecieve("","",1);
			List listback = unPackMessage(rawdata);
			
			//grab the second response after login because the first one will
			//be the bd banner
			DebugResponse dr = (DebugResponse)listback.get(1);
			
			return (String)dr.getArgs().get("0");
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
}