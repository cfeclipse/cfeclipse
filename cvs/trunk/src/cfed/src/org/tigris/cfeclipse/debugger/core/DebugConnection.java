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
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;
import java.util.logging.*;

/**
 * this is a simple class to send raw string data to and from a server. If
 * you require a more robust connection type (i.e. passing objects and what not
 * you can either subclass this or write your own connection object.
 * @author rob
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DebugConnection extends Thread {
	
	/** for multiplexing non-blocking I/O */
	protected Selector selector;
	/** a shared buffer for debug info */
	protected ByteBuffer buffer;
	/** Debuggers that dont have a channel yet */
	protected List pendingDebuggers;
	/** set when release is called */
	protected boolean released = false;
	/** Logging output */
	protected Logger log;
	/** the last known URI (for subsequent commnads) */
	protected URI cURI;
	
	protected DebugInformation debuginfo;
	
	static final Charset LATAN1 = Charset.forName("ISO-8859-1");
	
	
	/** the timeout for this connection */
	//public static final int TIMEOUT = 3000;
	
	//protected Socket s;
	//protected PrintWriter out;
	//protected BufferedReader in;
	
	/** the end of message - if a line of text starts with this
	 * character then it is the end of the message
	 */
	//private static String EOM = "+";
	
	/**
	 * Create a Debug Connection object using URL
	 * @param url
	 * @throws IOException
	 */
	public DebugConnection(Logger log) throws IOException
	{
		//initConnection(url);
		if(log == null)
			log = Logger.getLogger(this.getClass().getName());
		this.log = log;
		
		//create selector
		selector = Selector.open();
		//allocate buffer
		buffer = ByteBuffer.allocate(64*1024);
		pendingDebuggers = Collections.synchronizedList(new ArrayList());
		this.start();
	}
	
	/**
	 * Startup the connection to url. Its a good idea to call close
	 * on an instance of an object before using this method. the ctor
	 * calls this method. Note: if you are subclassing, this method is 
	 * String focused
	 * @param url
	 * @throws IOException
	 */
	public DebugInformation initConnection(URI uri, IDebugListener l) throws IOException, ConnectException
	{
		if(released)
		{
			throw new IllegalStateException("Can't init after release");
		}
		
		this.cURI = uri;
		
		String scheme = uri.getScheme();
		//scheme prolly = http, dont care right now...
		String hostname = uri.getHost();
		int port = uri.getPort();
		if(port == -1) 
			port = 8888;
		String path = uri.getPath();
		//uri.getRawQuery() ?...
		
		//create a debug object with the url info
		debuginfo = new DebugInformation(
			hostname,port,path,l
		);
		
		//add it to the list of pending connections
		pendingDebuggers.add(debuginfo);
		
		//ask the thread to stop blocking in the select call so that it will
		//notice and process this new object
		selector.wakeup();
		
		//return the debug info so the caller can monitor
		return debuginfo;
		
		//s = new Socket(url.getHost(),url.getPort());
		//out = new PrintWriter(s.getOutputStream());
		//in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		//s.setSoTimeout(TIMEOUT);
	}
	
	public void release()
	{
		released = true;
		try
		{
			selector.close();
		}
		catch(IOException ioe)
		{
			log.log(Level.SEVERE, "Error closing selector", ioe);
		}
	}
	
	public void run()
	{
		log.info("Debug Thread Starting...");
		
		while(!released)
		{
			try
			{
				selector.select();
			}
			catch(IOException ioe)
			{
				log.log(Level.SEVERE, "Error in select",ioe);
				return;
			}
			
			//if we were told to quit...
			if(released) break;
			
			if(!this.pendingDebuggers.isEmpty())
			{
				synchronized(pendingDebuggers)
				{
					Iterator i = pendingDebuggers.iterator();
					while(i.hasNext())
					{
						//get the pending object from the list
						DebugInformation di = (DebugInformation)i.next();
						i.remove();
						
						//now begin an asynchronous connection to the specified
						//host and port we dont block while waiting to connect
						SelectionKey key = null;
						SocketChannel channel = null;
						try
						{
							//Open an unconnected channel
							channel = SocketChannel.open();
							//put it in non-blocking mode
							channel.configureBlocking(false);
							//register it with the selector specifiying that
							//we want to know when its ready to connect
							key = channel.register(
								selector,
								SelectionKey.OP_READ | SelectionKey.OP_CONNECT,
								di
							);
							
							//create the server address
							SocketAddress address = new InetSocketAddress(
								di.host,di.port
							);
							
							//ask the channel to start connecting note that we
							//dont send any data yet we'll do that when the 
							//connection completes
							channel.connect(address);
						}
						catch(Exception e)
						{
							e.printStackTrace(System.err);
						}
					}
				}
			}
			
			//now get the set of keys that are ready for connecting
			Set keys = selector.selectedKeys();
			if(keys == null)
				continue;
			
			for(Iterator i = keys.iterator(); i.hasNext(); )
			{
				SelectionKey key = (SelectionKey)i.next();
				i.remove();
				
				//get the di object we attached to the key
				DebugInformation di = (DebugInformation)key.attachment();
				//get the channel
				SocketChannel channel = (SocketChannel)key.channel();
				
				try
				{
					if(key.isConnectable())
					{
						//if the channel is ready to connect go fer it
						if(channel.finishConnect())
						{
							di.status = Status.CONNECTED;
							//**********************************
							//String request = "TEST" + di.getPath();
							
							//Wrap in a CharBuffer and encode
							//ByteBuffer requestBytes = LATAN1.encode(
							//	CharBuffer.wrap(request)
							//);
							
														
							ByteBuffer requestBytes = di.getSendData();
							
							//send the request
							while(requestBytes.hasRemaining())
								channel.write(requestBytes);
							
							log.info("Sent stuff");
							//***********************************
						}
					}
					
					if(key.isReadable())
					{
						//if the key says there is data to be read then read
						//it and store it in the di object
						int numbytes = channel.read(buffer);
						
						//if we read some bytes, store them otherwise the 
						//conversation is complete
						if(numbytes != -1)
						{
							//prepare to drain the buffer
							buffer.flip();
							di.addData(buffer);
							buffer.clear();
							log.info("Read " + numbytes);
						}
						else
						{
							//if there are no more bytes to read
							//we are done with the key
							key.cancel();
							//and the channel
							channel.close();
							di.status = Status.DONE;
							
							if(di.dbl != null) //notify listener
								di.dbl.done(di);
							
							log.info("Done");
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace(System.err);
					try
					{
						if(key != null) key.cancel();
						channel.close();
					}
					catch(Exception exc)
					{
						exc.printStackTrace(System.err);
					}
					this.released = true;
				}
			}//for
		}//while
		
		log.info("Thread exiting");
	}
	
	
	public String sendRecieve(String send, String sessionid, int messageid)	throws IOException
	{
		debuginfo.setSendData(send);
		
		//add it to the list of pending connections
		pendingDebuggers.add(debuginfo);
		
		//ask the thread to stop blocking in the select call so that it will
		//notice and process this new object
		selector.wakeup();
		
		return "";
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
	/* public String sendRecieve(String send, String sessionid, int messageid)
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
	} */
	
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
		this.released = true;
		/* try
		{
			if(channel != null)
			{
				channel.close();
			}
		}
		catch(IOException e)
		{
			log.log(Level.WARNING,e.toString());
		}
		*/
		//out.close();
		//in.close();
		//s.close();
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
