/*******************************************************************************
 * Copyright (c) 2004, 2005 John-Mason P. Shackelford and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John-Mason P. Shackelford - initial API and implementation
 * 	   IBM Corporation - bug 52076
 *******************************************************************************/
package org.cfeclipse.cfml.editors.formatters;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.preferences.EditorPreferenceConstants;
import org.cfeclipse.cfml.preferences.EditorPreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;

public class FormattingPreferences {
	
	IPreferenceStore fPrefs= CFMLPlugin.getDefault().getPreferenceStore();
   
    public String getCanonicalIndent() {
       String canonicalIndent;
       if (!useSpacesInsteadOfTabs()) {
            canonicalIndent = "\t"; //$NON-NLS-1$
        } else {
            String tab = ""; //$NON-NLS-1$
            for (int i = 0; i < getTabWidth(); i++) {
                tab = tab.concat(" "); //$NON-NLS-1$
            }
            canonicalIndent = tab;
        }
        
        return canonicalIndent;
    }

	/**
	 * Sets the preference store for these formatting preferences.
	 * @param prefs the preference store to use as a reference for the formatting
	 * preferences
	 */
	public void setPreferenceStore(IPreferenceStore prefs) {
		fPrefs = prefs;
	}

	public boolean getEnforceMaximumLineWidth() {
		return fPrefs.getBoolean(EditorPreferenceConstants.FORMATTER_WRAP_LONG);
	}

	public boolean getCloseTags() {
		return fPrefs.getBoolean(EditorPreferenceConstants.FORMATTER_CLOSE_TAGS);
	}

	public boolean formatSQL() {
		return fPrefs.getBoolean(EditorPreferenceConstants.FORMATTER_FORMAT_SQL);
	}
        
    public int getMaximumLineWidth() {
        return fPrefs.getInt(EditorPreferenceConstants.FORMATTER_MAX_LINE_LENGTH);
    }  
    
    public boolean wrapLongTags() {
        return fPrefs.getBoolean(EditorPreferenceConstants.FORMATTER_WRAP_LONG);
    }
    
    public boolean alignElementCloseChar() {
        return fPrefs.getBoolean(EditorPreferenceConstants.FORMATTER_ALIGN);        
    }

	public int getTabWidth() {
		return fPrefs.getInt(EditorPreferenceConstants.P_TAB_WIDTH);
	}
	
	public boolean useSpacesInsteadOfTabs() {
    	return fPrefs.getBoolean(EditorPreferenceConstants.P_INSERT_SPACES_FOR_TABS);
    }

	public boolean tidyTags() {
    	return fPrefs.getBoolean(EditorPreferenceConstants.FORMATTER_TIDY_TAGS);
    }
	
	public boolean collapseWhiteSpace() {
    	return fPrefs.getBoolean(EditorPreferenceConstants.FORMATTER_COLLAPSE_WHITESPACE);
    }
	
	public boolean indentAllElements() {
    	return fPrefs.getBoolean(EditorPreferenceConstants.FORMATTER_INDENT_ALL_ELEMENTS);
    }
	
	public static boolean affectsFormatting(PropertyChangeEvent event) {
		String property= event.getProperty();
		return property.startsWith(EditorPreferenceConstants.FORMATTER_ALIGN) ||
			property.startsWith(EditorPreferenceConstants.FORMATTER_MAX_LINE_LENGTH) ||
			property.startsWith(EditorPreferenceConstants.P_INSERT_SPACES_FOR_TABS) ||
			property.startsWith(EditorPreferenceConstants.P_TAB_WIDTH) ||
			property.startsWith(EditorPreferenceConstants.FORMATTER_CLOSE_TAGS) ||
			property.startsWith(EditorPreferenceConstants.FORMATTER_TIDY_TAGS) ||
			property.startsWith(EditorPreferenceConstants.FORMATTER_COLLAPSE_WHITESPACE) ||
			property.startsWith(EditorPreferenceConstants.FORMATTER_INDENT_ALL_ELEMENTS) ||
			property.startsWith(EditorPreferenceConstants.FORMATTER_WRAP_LONG);
	}
}