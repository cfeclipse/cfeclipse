/*
 * Created on Jun 30, 2004
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

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @author Rob
 *
 * A default implementaiton implementing IDebugInformation
 */
public class DebugInformation implements IDebugInformation
{
	//final for thread saftey
	final String host;
	final int port;
	final String path;
	final IDebugListener dbl;
	//volatile fields may be changed concurrently
	volatile String senddata = "";
	volatile Status status;
	volatile byte[] data = new byte[0];
	
	public DebugInformation(String host, int port, String path, IDebugListener dbl)
	{
		this.host = host;
		this.port = port;
		this.path = path;
		this.dbl = dbl;
	}
	
	public String getHost(){return host;}
	public int getPort(){ return port;}
	public String getPath(){ return path;}
	public Status getStatus(){ return status;}
	public byte[] getData(){ return data; }
	
	protected void addData(ByteBuffer buffer)
	{
		int oldlen = data.length;
		int numbytes = buffer.remaining();
		int newlen = oldlen + numbytes;
		byte[] newdata = new byte[newlen];
		//copy old bytes
		System.arraycopy(data,0,newdata,0,oldlen);
		//copy new bytes
		buffer.get(newdata, oldlen, numbytes);
		//save new array
		data = newdata;
	}
	
	public void setSendData(String to)
	{
		this.senddata = to;
	}
	
	public ByteBuffer getSendData()
	{
		ByteBuffer requestBytes = DebugConnection.LATAN1.encode(
			CharBuffer.wrap(senddata)
		);
		
		return requestBytes;
	}
}
