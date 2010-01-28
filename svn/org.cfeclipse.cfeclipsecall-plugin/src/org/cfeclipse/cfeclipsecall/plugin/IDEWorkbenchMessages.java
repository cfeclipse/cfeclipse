/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM - Initial API and implementation
 * Benjamin Muskalla - bug 29633
 * Oakland Software Incorporated (Francis Upton) <francisu@ieee.org>
 *		- Bug 224997 [Workbench] Impossible to copy project
 *******************************************************************************/
package org.cfeclipse.cfeclipsecall.plugin;

import org.eclipse.osgi.util.NLS;

public class IDEWorkbenchMessages extends NLS {
	private static final String BUNDLE_NAME = "org.cfeclipse.cfeclipsecall.plugin.messages";//$NON-NLS-1$

	public static String IDE_noFileEditorFound;

	public static String OpenLocalFileAction_title;
	public static String OpenLocalFileAction_message_fileNotFound;
	public static String OpenLocalFileAction_message_filesNotFound;
	public static String OpenLocalFileAction_message_errorOnOpen;
	public static String OpenLocalFileAction_title_selectWorkspaceFile;
	public static String OpenLocalFileAction_message_fileLinkedToMultiple;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, IDEWorkbenchMessages.class);
	}

}
