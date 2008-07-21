/*
 * Created on May 10, 2004
 *
 */
package org.cfeclipse.cfml.parser.exception;

/**
 * Thrown by a DocItem when it cannot find the node requested, either for an insert,
 * removal or replacement.
 * @author Oliver Tupman
 *
 */
public class NodeNotFound extends Exception {
	/**
	 * 
	 */
	public NodeNotFound() {
		super();
	}
	/**
	 * @param arg0
	 */
	public NodeNotFound(String arg0) {
		super(arg0);
	}
	/**
	 * @param arg0
	 */
	public NodeNotFound(Throwable arg0) {
		super(arg0);
	}
	/**
	 * @param arg0
	 * @param arg1
	 */
	public NodeNotFound(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
