/*
 * Created on Oct 15, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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