/*
 * Created on May 10, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.parser;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Oliver Tupman
 * 
 * Simple extension of ArrayList to indicate that something is a list of
 * document nodes. 
 */
public class CFNodeList extends ArrayList {
	/**
	 * 
	 */
	public CFNodeList() {
		super();
	}
	/**
	 * @param arg0
	 */
	public CFNodeList(int arg0) {
		super(arg0);
	}
	/**
	 * @param arg0
	 */
	public CFNodeList(Collection arg0) {
		super(arg0);
	}
}
