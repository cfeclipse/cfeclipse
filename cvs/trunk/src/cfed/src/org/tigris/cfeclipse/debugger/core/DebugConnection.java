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

import java.net.*;
import java.net.ConnectException;
import java.io.*;

/**
 * this is a simple class to send raw string data to and from a server. If
 * you require a more robust connection type (i.e. passing objects and what not
 * you can either subclass this or write your own connection object.
 * @author rob
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DebugConnection {
	/** the timeout for this connection */
	public static final int TIMEOUT = 3000;
	
	protected Socket s;
	protected PrintWriter out;
	protected BufferedReader in;
	
	/** the end of message - if a line of text starts with this
	 * character then it is the end of the message
	 */
	//private static String EOM = "+";
	
	/**
	 * Create a Debug Connection object using URL
	 * @param url
	 * @throws IOException
	 */
	public DebugConnection(URL url) throws IOException, ConnectException
	{
		initConnection(url);
	}
	
	/**
	 * Startup the connection to url. Its a good idea to call close
	 * on an instance of an object before using this method. the ctor
	 * calls this method. Note: if you are subclassing, this method is 
	 * String focused
	 * @param url
	 * @throws IOException
	 */
	public void initConnection(URL url) throws IOException, ConnectException
	{
		s = new Socket(url.getHost(),url.getPort());
		out = new PrintWriter(s.getOutputStream());
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		//s.setSoTimeout(TIMEOUT);
	}
	
	/**
	 * Sends commands down the pipe and returns whatever the response is.
	 * note this adds \r\n to the string, if you dont want that you'll have
	 * to make your own connection object in the protocol
	 * @param send the string to send
	 * @return the string from the server
	 * @throws IOException
	 * @throws SocketTimeoutException
	 */
	public String sendRecieve(String send, String sessionid, int messageid)
		throws IOException, SocketTimeoutException
	{
		StringBuffer resp = new StringBuffer();
		String line;
		
		if(send.length() > 0)
		{
			System.out.println("Sending: " + send);
			
			out.print(send + "\r\n");
			out.flush();
		}
		
		for(;;)
		{
			line = in.readLine();
			
			if(line == null) 
				throw new IOException("Unexpected EOF");
			
			resp.append(line + "\n");
			
			//when in step mode...
			if(line.indexOf("PAUSED") > 0) break;
			
			//the id sent (when asking for variables it will be a multi line
			//message that ends with +123
			if(line.equals("+:" + messageid)) break;
			
			//the current session id - for end of session calls
			//this is going to be a problem in global non step mode as
			//the buffer will clear right away
			if(line.equals("+:" + sessionid + ":END")) break;
			
			if(line.equals("+0:BAD")) break;
		}
		
		System.out.println("Full: " + resp.toString());
		
		return resp.toString();
	}
	
	/* public String login(String send) throws IOException, SocketTimeoutException
	{
		StringBuffer resp = new StringBuffer();
		String line;
		
		out.print(send + "\r\n");
		out.flush();
		
		s.setSoTimeout(10000);
		
		boolean oktobreak = false;
		while((line = in.readLine()) != null)
		{
			resp.append(line);
			
			if(line.endsWith("STARTSESSION"))
				oktobreak = true;
			
			if(oktobreak)
				if(line.startsWith(EOM)) break;
			
		}
		
		s.setSoTimeout(TIMEOUT);
		
		return resp.toString();
	} */
	
	
	/**
	 * Shuts down this connection. one can call the initConnection after this
	 * if connection to another server is required.
	 * @throws IOException
	 */
	public void close() throws IOException
	{
		out.close();
		in.close();
		s.close();
	}
	
	/**
	 * @return Returns the EOM string (End of message). 
	 */
	//public static String getEOM() {
	//	return EOM;
	//}
	
	/**
	 * @param eom The EOM to set (end of message) if the a line of text
	 * starts with this string then the message is complete.
	 */
	//public static void setEOM(String eom) {
	//	EOM = eom;
	//}
}