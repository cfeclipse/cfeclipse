/*
 * Created on Sep 21, 2004
 * by Matt
 */
package com.rohanclan.cfml.editors.pairs;

/**
 * @author Matt
 *
 * TODO UnableToFindMatchException Class
 */
public class UnableToFindMatchException extends Exception
{

	/**
	 * 
	 */
	public UnableToFindMatchException()
	{
		super();
	}

	/**
	 * @param message
	 */
	public UnableToFindMatchException(String message)
	{
		super(message);
	}

	/**
	 * @param message
	 */
	public UnableToFindMatchException(Throwable exception)
	{
		super(exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public UnableToFindMatchException(String message, Throwable exception)
	{
		super(message, exception);
	}

}
