/*
 * Created on Oct 15, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
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
package com.rohanclan.cfml.preferences;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.util.Assert;

class StatusInfo
	implements IStatus
{

	private String fStatusMessage = null;
	private int fSeverity = 0;

	public StatusInfo()
	{
		this(0, null);
	}

	public StatusInfo(int severity, String message)
	{
		fStatusMessage = message;
		fSeverity = severity;
	}

	public boolean isOK()
	{
		return fSeverity == 0;
	}

	public boolean isWarning()
	{
		return fSeverity == 2;
	}

	public boolean isInfo()
	{
		return fSeverity == 1;
	}

	public boolean isError()
	{
		return fSeverity == 4;
	}

	public String getMessage()
	{
		return fStatusMessage;
	}

	public void setError(String errorMessage)
	{
		Assert.isNotNull(errorMessage);
		fStatusMessage = errorMessage;
		fSeverity = 4;
	}

	public void setWarning(String warningMessage)
	{
		Assert.isNotNull(warningMessage);
		fStatusMessage = warningMessage;
		fSeverity = 2;
	}

	public void setInfo(String infoMessage)
	{
		Assert.isNotNull(infoMessage);
		fStatusMessage = infoMessage;
		fSeverity = 1;
	}

	public void setOK()
	{
		fStatusMessage = null;
		fSeverity = 0;
	}

	public boolean matches(int severityMask)
	{
		return (fSeverity & severityMask) != 0;
	}

	public boolean isMultiStatus()
	{
		return false;
	}

	public int getSeverity()
	{
		return fSeverity;
	}

	public String getPlugin()
	{
		return "org.eclipse.ui.editors";
	}

	public Throwable getException()
	{
		return null;
	}

	public int getCode()
	{
		return fSeverity;
	}

	public IStatus[] getChildren()
	{
		return new IStatus[0];
	}
}