/*
 * Created on Nov 12, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.cfml.preferences;


import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.AbstractTextEditor;

/**
 * @author Stephen Milligan
 *
 * TODO: Add Default Browser information
 * TODO: Add Location of Project Templates
 */
public class EditorPreferenceConstants extends AbstractPreferenceConstants {

	/** Style constant for bracket matching as an outline */
	public static final int BRACKET_MATCHING_OUTLINE = 0;
	
	/** Style constant for bracket matching as a background color */
	public static final int BRACKET_MATCHING_BACKGROUND = 1;
	
	/** Style constant for bracket matching as bold text */
	public static final int BRACKET_MATCHING_BOLD = 2;
	
	
	/** preference key identifier for content assist delay */
	public static final String P_INSIGHT_DELAY = "cfeclipse.editor.insightDelay";
	
	/** Preference key identifier for insert spaces instead of tabs */
	public static final String P_INSERT_SPACES_FOR_TABS = "cfeclipse.editor.tabsAsSpaces";

	/** Preference key identifier for tab width */
	public static final String P_TAB_WIDTH = "cfeclipse.editor.tabWidth";

	/** Preference key identifier for max undo steps */
	public static final String P_MAX_UNDO_STEPS = "cfeclipse.editor.maxUndoSteps";

	/** Preference key identifier for enabling bracket matching */
	public static final String P_BRACKET_MATCHING_ENABLED = "cfeclipse.editor.bracketMatchingEnabled";

	/** Preference key identifier for bracket matching highlight color */
	public static final String P_BRACKET_MATCHING_COLOR = "cfeclipse.editor.bracketMatchingColor";

	/** Preference key identifier for whether or not tab indents single line if part of the line is selected */ 
	public static final String P_TAB_INDENTS_CURRENT_LINE 	= "cfeclipse.editor.tabIndentsCurrentLine";


	/** Preference key identifier for trimming trailing spaces when you save */
	public static final String P_RTRIM_ON_SAVE = "cfeclipse.editor.rTrimOnSave";

	/** Preference key identifier for line number color */
	public static final String P_LINE_NUMBER_COLOR = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER_COLOR;

	/** Preference key identifier for current line highlight color */
	public static final String P_CURRENT_LINE_COLOR = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE_COLOR;

	/** Preference key identifier for print margin size */
	public static final String P_PRINT_MARGIN_SIZE = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN_COLUMN;

	/** Preference key identifier for showing print margin */
	public static final String P_SHOW_PRINT_MARGIN = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN;
	
	/** Preference key identifier for print margin color */
	public static final String P_PRINT_MARGIN_COLOR = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN_COLOR;

	/** Preference key identifier for selection foreground color */
	public static final String P_SELECTION_FOREGROUND_COLOR = "AbstractTextEditor.Color.SelectionForeground";

	/** Preference key identifier for selection background color */
	public static final String P_SELECTION_BACKGROUND_COLOR = "AbstractTextEditor.Color.SelectionBackground";
	
	/** Preference key identifier for the text editor background color */
	public static final String P_COLOR_BACKGROUND = "AbstractTextEditor.Color.Background";
	
	/** Preference key identifier for showing the overview ruler */
	public static final String P_SHOW_OVERVIEW_RULER = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_OVERVIEW_RULER;
	
	/** Preference key identifier for highlighting the line the cursor is on */
	public static final String P_HIGHLIGHT_CURRENT_LINE = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE;
	
	/** Preference key identifier for enabling custom carets */
	public static final String P_ENABLE_CUSTOM_CARETS = "AbstractTextEditor.Accessibility.UseCustomCarets";
	
	/** Preference key identifier for using a wide caret */
	public static final String P_USE_WIDE_CARET = "AbstractTextEditor.Accessibility.WideCaret";
	
	/** Preference key identifier for using the system default for the selection foreground color */
	public static final String P_SELECTION_FOREGROUND_SYSTEM_DEFAULT = "AbstractTextEditor.Color.SelectionForeground.SystemDefault";
	
	/** Preference key identifier for using the system default for the selection background color */
	public static final String P_SELECTION_BACKGROUND_SYSTEM_DEFAULT = "AbstractTextEditor.Color.SelectionBackground.SystemDefault";
	
	/** Preference key identifier for warning when opening read only files */
	public static final String P_WARN_READ_ONLY_FILES = "cfeclipse.editor.warnReadOnlyFiles";
	
	/** Preference key identifier for bracket matching style */
	public static final String P_BRACKET_MATCHING_STYLE = "cfeclipse.editor.bracketMatchingStyle";
	
	/** Preference key identifier for the editor toolbar */
	public static final String P_SHOW_EDITOR_TOOLBAR = "cfeclipse.editor.showToolbar";

	/** Preference key identifier for the "smart caret positioning" */
	public static final String P_NAVIGATION_SMART_HOME_END = AbstractTextEditor.PREFERENCE_NAVIGATION_SMART_HOME_END;
	
	/**
	 * A named preference that specifies if the Ant formatter aligns the final
	 * &quote&gt&quote in multi-line element tags
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> the final
	 * &quote&gt&quote in multi-line element tags are aligned by the formatter.
	 * </p>
	 */
	public static final String FORMATTER_ALIGN= "formatter_align"; //$NON-NLS-1$
	
	/**
	 * A named preference that specifies the maximum line length for the Ant formatter.
	 * <p>
	 * Value is of type <code>int</code>.
	 * </p>
	 */
	public static final String FORMATTER_MAX_LINE_LENGTH= "formatter_max_line_length"; //$NON-NLS-1$
			
	/**
	 * A named preference that specifies if the Ant formatter should wrap elements that are longer than
	 * the maximum line length.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> long elements are wrapped
	 * when formatting in the editor.
	 * </p>
	 */
	public static final String FORMATTER_WRAP_LONG= "formatter_wrap_long"; //$NON-NLS-1$
	
	/**
	 * Boolean preference identifier constant which specifies whether the Ant editor should
	 * format templates on insert.
	 */
	public static final String TEMPLATES_USE_CODEFORMATTER= "templates_use_codeformatter"; //$NON-NLS-1$

	public static final String FORMATTER_CLOSE_TAGS = "formatter_close_tags";

	public static final String FORMATTER_FORMAT_SQL = "formatter_format_sql";
		
	/**
	 * Sets the default values for the preferences managed by {@link EditorPreferencePage}:
	 * <ul>
	 * <li>P_INSIGHT_DELAY - 500</li>
	 * <li>P_INSERT_SPACES_FOR_TABS - false</li>
	 * <li>P_TAB_WIDTH - 4</li>
	 * <li>P_MAX_UNDO_STEPS - 25</li>
	 * <li>P_BRACKET_MATCHING_COLOR - "255,0,0"</li>
	 * <li>P_TAB_INDENTS_CURRENT_LINE - true</li>
	 * <li>P_RTRIM_ON_SAVE - false</li>
	 * <li>P_LINE_NUMBER_COLOR - "0,0,0"</li>
	 * <li>P_CURRENT_LINE_COLOR - "232,242,254"</li>
	 * <li>P_PRINT_MARGIN_COLOR - "176,180,185"</li>
	 * <li>P_PRINT_MARGIN_SIZE - 80</li>
	 * <li>P_SHOW_PRINT_MARGIN - false</li>
	 * <li>P_SELECTION_FOREGROUND_COLOR - "212,208,200"</li>
	 * <li>P_SELECTION_BACKGROUND_COLOR - "128,128,128"</li>
	 * <li>P_COLOR_BACKGROUND - "255,255,255"</li>
	 * <li>P_SHOW_OVERVIEW_RULER - true</li>
	 * <li>P_HIGHLIGHT_CURRENT_LINE - true</li>
	 * <li>P_ENABLE_CUSTOM_CARETS - true</li>
	 * <li>P_SELECTION_FOREGROUND_SYSTEM_DEFAULT - true</li>
	 * <li>P_SELECTION_BACKGROUND_SYSTEM_DEFAULT - true</li>
	 * <li>P_WARN_READ_ONLY_FILES - true</li>
	 * <li>P_BRACKET_MATCHING_STYLE - BRACKET_MATCHING_BOLD</li>
	 * <li>P_NAVIGATION_SMART_HOME_END - false</li>
	 * </ul>
	 * 
	 */
	public static void setDefaults(IPreferenceStore store) {
		store.setDefault(P_INSIGHT_DELAY,500);
		store.setDefault(P_INSERT_SPACES_FOR_TABS,false);
		store.setDefault(P_TAB_WIDTH,4);
		store.setDefault(P_BRACKET_MATCHING_COLOR,"255,0,0");
		store.setDefault(P_BRACKET_MATCHING_ENABLED,true);
		store.setDefault(P_TAB_INDENTS_CURRENT_LINE,true);
		store.setDefault(P_RTRIM_ON_SAVE,false);
		store.setDefault(P_LINE_NUMBER_COLOR,"0,0,0");
		store.setDefault(P_CURRENT_LINE_COLOR,"232,242,254");
		store.setDefault(P_SHOW_PRINT_MARGIN,true);
		store.setDefault(P_PRINT_MARGIN_SIZE,80);
		store.setDefault(P_PRINT_MARGIN_COLOR,"176,180,185");
		store.setDefault(P_SELECTION_FOREGROUND_COLOR,"255,255,255");
		store.setDefault(P_SELECTION_BACKGROUND_COLOR,"10,36,106");
		store.setDefault(P_COLOR_BACKGROUND,"255,255,255");
		store.setDefault(P_SHOW_OVERVIEW_RULER,true);
		store.setDefault(P_HIGHLIGHT_CURRENT_LINE,true);
		store.setDefault(P_ENABLE_CUSTOM_CARETS,true);
		store.setDefault(P_USE_WIDE_CARET,true);
		store.setDefault(P_SELECTION_FOREGROUND_SYSTEM_DEFAULT,false);
		store.setDefault(P_SELECTION_BACKGROUND_SYSTEM_DEFAULT,false);
		store.setDefault(P_WARN_READ_ONLY_FILES,true);
		store.setDefault(P_BRACKET_MATCHING_STYLE,BRACKET_MATCHING_BOLD);
		store.setDefault(P_SHOW_EDITOR_TOOLBAR, false);
		store.setDefault(P_NAVIGATION_SMART_HOME_END, false);

		store.setDefault(FORMATTER_ALIGN, false);
		store.setDefault(FORMATTER_MAX_LINE_LENGTH, 80);
		store.setDefault(FORMATTER_WRAP_LONG, false);
		store.setDefault(FORMATTER_CLOSE_TAGS, true);
		
		store.setDefault(TEMPLATES_USE_CODEFORMATTER, true);
		store.setDefault(FORMATTER_FORMAT_SQL, false);

	}
	
	
}