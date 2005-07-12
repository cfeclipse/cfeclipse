/* 
 * $Id: DebugSettings.java,v 1.1 2005-03-03 03:12:58 smilligan Exp $
 * $Revision: 1.1 $
 * $Date: 2005-03-03 03:12:58 $
 * 
 * Created Mar 2, 2005 2:24:30 PM
 *
 * CFEclipse - The Open Source ColdFusion Development Environment
 * Copyright (c) 2005 Stephen Milligan and others.  All rights reserved.
 * For more information on cfeclipse, please see http://cfeclipse.tigris.og/.
 *
 * ====================================================================
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the Eclipse Public License
 * as published by the Eclipse Foundation; either
 * version 1.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Eclipse 
 * Public License for more details.
 *
 * You should have received a copy of the Eclipse Public License with this 
 * software. If not, the full text of version 1.0 of the Eclipse Public License 
 * is available online at the following URL:
 * 
 * http://www.eclipse.org/legal/epl-v10.html
 * ====================================================================
 */
package com.rohanclan.cfml.plugindebug;

/**
 * This class contains the boolean flags that indicate whether debugging should
 * be enabled for any given package. Individual classes in each package should
 * implement a field declared as:
 * 
 * <code>private static final boolean DEBUG = DebugSettings.{package_id_value};</code>
 * 
 * Where {package_id_value} represents one of the fields in this class.
 * 
 * If you want to show debug messages for a complete package you should change
 * the relevant field in this class to true. If you want to only debug a specific 
 * class you can turn off debugging for the package and just turn it on for that 
 * class. See the javadocs for {@link DebugUtils} for information on the recommended way
 * to output debug information.
 * 
 * @author Stephen Milligan
 * @version $Revision: 1.1 $
 */
public class DebugSettings {

    /** Debug flag for classes in the com.rohanclan.cfml.builders package */
    public static final boolean BUILDERS = false;

    /** Debug flag for classes in the com.rohanclan.cfml package */
    public static final boolean CFML = false;

    /** Debug flag for classes in the com.rohanclan.cfml.dictionary package */
    public static final boolean DICTIONARY = false;

    /** Debug flag for classes in the com.rohanclan.cfml.editors package */
    public static final boolean EDITORS = true;

    /** Debug flag for classes in the com.rohanclan.cfml.editors.actions package */
    public static final boolean ACTIONS = false;

    /**
     * Debug flag for classes in the
     * com.rohanclan.cfml.editors.actions.codefolding package
     */
    public static final boolean ACTIONS_CODEFOLDING = false;

    /**
     * Debug flag for classes in the com.rohanclan.cfml.editors.codefolding
     * package
     */
    public static final boolean CODEFOLDING = false;

    /**
     * Debug flag for classes in the com.rohanclan.cfml.editors.contentassist
     * package
     */
    public static final boolean CONTENT_ASSIST = false;

    /**
     * Debug flag for classes in the com.rohanclan.cfml.editors.decoration
     * package
     */
    public static final boolean EDITORS_DECORATION = false;

    /** Debug flag for classes in the com.rohanclan.cfml.editors.dnd package */
    public static final boolean DND = false;

    /**
     * Debug flag for classes in the com.rohanclan.cfml.editors.indentstrategies
     * package
     */
    public static final boolean INDENT_STRATEGIES = false;

    /** Debug flag for classes in the com.rohanclan.cfml.editors.pairs package */
    public static final boolean EDITORS_PAIRS = false;

    /**
     * Debug flag for classes in the com.rohanclan.cfml.editors.partitioner
     * package
     */
    public static final boolean PARTITIONER = false;

    /**
     * Debug flag for classes in the
     * com.rohanclan.cfml.editors.partitioner.scanners package
     */
    public static final boolean SCANNERS = false;

    /**
     * Debug flag for classes in the
     * com.rohanclan.cfml.editors.partitioner.scanners.cfscript package
     */
    public static final boolean SCANNERS_CFSCRIPT = false;

    /**
     * Debug flag for classes in the
     * com.rohanclan.cfml.editors.partitioner.scanners.css package
     */
    public static final boolean SCANNERS_CSS = false;

    /**
     * Debug flag for classes in the
     * com.rohanclan.cfml.editors.partitioner.scanners.jscript package
     */
    public static final boolean SCANNERS_JS = false;

    /**
     * Debug flag for classes in the
     * com.rohanclan.cfml.editors.partitioner.scanners.rules package
     */
    public static final boolean SCANNERS_RULES = false;

    /**
     * Debug flag for classes in the
     * com.rohanclan.cfml.editors.partitioner.scanners.sql package
     */
    public static final boolean SCANNERS_SQL = false;

    /** Debug flag for classes in the com.rohanclan.cfml.external package */
    public static final boolean EXTERNAL = false;

    /** Debug flag for classes in the com.rohanclan.cfml.natures package */
    public static final boolean NATURES = false;

    /** Debug flag for classes in the com.rohanclan.cfml.net package */
    public static final boolean NET = false;

    /** Debug flag for classes in the com.rohanclan.cfml.net.ftp package */
    public static final boolean NET_FTP = false;

    /** Debug flag for classes in the com.rohanclan.cfml.parser package */
    public static final boolean PARSER = false;

    /**
     * Debug flag for classes in the com.rohanclan.cfml.parser.cfmltagitems
     * package
     */
    public static final boolean PARSER_TAGITEMS = false;

    /** Debug flag for classes in the com.rohanclan.cfml.parser.cfscript package */
    public static final boolean PARSER_CFSCRIPT = false;

    /** Debug flag for classes in the com.rohanclan.cfml.parser.docitems package */
    public static final boolean PARSER_DOCITEMS = false;

    /** Debug flag for classes in the com.rohanclan.cfml.parser.exception package */
    public static final boolean PARSER_EXCEPTION = false;

    /** Debug flag for classes in the com.rohanclan.cfml.parser.xpath package */
    public static final boolean PARSER_XPATH = false;

    /**
     * Debug flag for classes in the com.rohanclan.cfml.parser.xpath.expressions
     * package
     */
    public static final boolean PARSER_XPATH_EXPRESSIONS = false;

    /** Debug flag for classes in the com.rohanclan.cfml.perspective package */
    public static final boolean PERSPECTIVE = false;

    /** Debug flag for classes in the com.rohanclan.cfml.preferences package */
    public static final boolean PREFERENCES = false;

    /** Debug flag for classes in the com.rohanclan.cfml.properties package */
    public static final boolean PROPERTIES = false;

    /** Debug flag for classes in the com.rohanclan.cfml.ui.actions package */
    public static final boolean UI_ACTIONS = false;

    /** Debug flag for classes in the com.rohanclan.cfml.util package */
    public static final boolean UTIL = false;

    /** Debug flag for classes in the com.rohanclan.cfml.views package */
    public static final boolean VIEWS = false;

    /** Debug flag for classes in the com.rohanclan.cfml.views.browser package */
    public static final boolean VIEWS_BROWSER = false;

    /** Debug flag for classes in the com.rohanclan.cfml.views.cfcmethods package */
    public static final boolean VIEWS_CFCMETHODS = false;

    /**
     * Debug flag for classes in the com.rohanclan.cfml.views.contentoutline
     * package
     */
    public static final boolean VIEWS_CONTENT_OUTLINE = false;

    /** Debug flag for classes in the com.rohanclan.cfml.views.dictionary package */
    public static final boolean VIEWS_DICTIONARY = false;

    /** Debug flag for classes in the com.rohanclan.cfml.views.explorer package */
    public static final boolean VIEWS_EXPLORER = false;

    /**
     * Debug flag for classes in the com.rohanclan.cfml.views.explorer.ftp
     * package
     */
    public static final boolean VIEWS_EXPLORER_FTP = false;

    /**
     * Debug flag for classes in the com.rohanclan.cfml.views.packageview
     * package
     */
    public static final boolean VIEWS_PACKAGE = false;

    /** Debug flag for classes in the com.rohanclan.cfml.views.snips package */
    public static final boolean VIEWS_SNIPS = false;

    /** Debug flag for classes in the com.rohanclan.cfml.wizards package */
    public static final boolean WIZARDS = false;

    /**
     * Debug flag for classes in the com.rohanclan.cfml.wizards.cfcwizard
     * package
     */
    public static final boolean WIZARDS_CFC = false;

    /**
     * Debug flag for classes in the com.rohanclan.cfml.wizards.cfmlwizard
     * package
     */
    public static final boolean WIZARDS_CFML = false;

    /**
     * Debug flag for classes in the
     * com.rohanclan.cfml.wizards.templatefilewizard package
     */
    public static final boolean WIZARDS_TEMPLATE_FILE = false;

}

/*
 * CVS LOG ====================================================================
 * 
 * $Log: not supported by cvs2svn $
 */