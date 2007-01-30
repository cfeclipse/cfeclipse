/*
 * Created on Sep 21, 2004
 * by Matt
 */
package org.cfeclipse.cfml.editors.pairs;

/**
 * @author Matt
 *
 * TODO UnsetAnchorException Class
 */
public class UnsetAnchorException extends Exception
{

	/**
	 * 
	 */
	public UnsetAnchorException()
	{
		super();
	}

	/**
	 * @param message
	 */
	public UnsetAnchorException(String message)
	{
		super(message);
	}

	/**
	 * @param message
	 */
	public UnsetAnchorException(Throwable exception)
	{
		super(exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public UnsetAnchorException(String message, Throwable exception)
	{
		super(message, exception);
	}

}
