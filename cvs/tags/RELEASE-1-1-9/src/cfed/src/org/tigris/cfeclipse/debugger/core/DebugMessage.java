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
import java.util.Map;
import java.util.HashMap;
import org.tigris.cfeclipse.debugger.protocol.DebugProtocol;

public class DebugMessage {
	private String command;
	private Map args;
	
	public DebugMessage()
	{
		setCommand(DebugProtocol.CFE_COMMAND_NOOP);
		setArgs(new HashMap());
	}
	
	/**
	 * @return Returns the args.
	 */
	public Map getArgs() 
	{
		return args;
	}
	
	/**
	 * @param args The args to set.
	 */
	public void setArgs(Map args) 
	{
		this.args = args;
	}
	
	/**
	 * @return Returns the command.
	 */
	public String getCommand() 
	{
		return command;
	}
	
	/**
	 * @param command The command to set.
	 */
	public void setCommand(String command) 
	{
		this.command = command;
	}
}