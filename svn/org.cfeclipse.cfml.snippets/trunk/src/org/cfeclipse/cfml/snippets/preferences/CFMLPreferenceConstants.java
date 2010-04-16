package org.cfeclipse.cfml.snippets.preferences;

import org.cfeclipse.cfml.snippets.SnippetPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;

public class CFMLPreferenceConstants {

	public static final String P_SNIPPETS_PATH = "_snippetsPath";
	/** Preference key identifier for current line highlight color */
	public static final String P_TAB_WIDTH = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH;
	public static final String P_INSERT_SPACES_FOR_TABS = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SPACES_FOR_TABS;

	/**
	 * Sets up the default values for preferences managed by {@link CFMLPreferencesPage} .
	 * <ul>
	 * <li>P_SNIPPETS_PATH - CFMLPlugin.getDefault().getStateLocation().toString()</li>
	 * <li>P_PROJECT_URL - DEFAULT_PROJECT_URL</li>
	 * <li>P_TABBED_BROWSER - false</li>
	 * <li>P_CFML_DICTIONARY - "cfml.xml"</li>
	 * </ul> 
	 */
	
	public static void setDefaults(IPreferenceStore store) { 
		store.setDefault(P_SNIPPETS_PATH,SnippetPlugin.getDefault().getStateLocation().toString()+"/snippets");		
		store.setDefault(P_TAB_WIDTH,AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH);		
		store.setDefault(P_INSERT_SPACES_FOR_TABS,AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SPACES_FOR_TABS);		
	}
	
}
