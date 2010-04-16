/*
 * Created on Apr 22, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package org.cfeclipse.cfml.snippets.preferences;

//import java.net.URL;
import org.cfeclipse.cfml.snippets.SnippetPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;

/**
 * @author Stephen Milligan
 */
public class CFMLPreferenceManager {

	private IPreferenceStore store;

	public CFMLPreferenceManager() {
		store = SnippetPlugin.getDefault().getPreferenceStore();
		initializeDefaultValues();
	}

	public boolean getBooleanPref(String prefKey) {
		return store.getBoolean(prefKey);
	}

	public String getStringPref(String prefKey) {
		return store.getString(prefKey);
	}

	/*
	 * private String getColorString(RGB color) { return color.red + "," +
	 * color.green + "," + color.blue; }
	 */

	public void initializeDefaultValues() {

		CFMLPreferenceConstants.setDefaults(store);
		SnipExPreferenceConstants.setDefaults(store);
	}

	/**
	 * Gets an RGB from the preference store using key as the key. If the key
	 * does not exist, it returns 0,0,0
	 * 
	 * @param key
	 * @return
	 */
	public RGB getColor(String key) {
		// try to get the color as a string from the store
		String rgbString = store.getString(key);
		// System.err.println(key + " :: " + rgbString);

		// if we didnt get anything back...
		if (rgbString.length() <= 0) {
			// try to get it from the default settings
			rgbString = store.getDefaultString(key);

			// if we still didnt get anything use black
			if (rgbString.length() <= 0) {
				// Force a stack trace to see what called this.
				try {
					rgbString = null;
					System.out.println(rgbString.length());
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.err.println("Color key: " + key
						+ " is a no show using black");
				rgbString = "0,0,0";
			}
		}

		// make sure we get an ok string
		rgbString = deParen(rgbString);

		RGB newcolor = null;
		try {
			newcolor = StringConverter.asRGB(deParen(rgbString));
		} catch (Exception e) {
			System.err.println("Woah... got an odd color passed: " + key);
			e.printStackTrace(System.err);
		}

		return newcolor;
	}

	/**
	 * for some reason the color can get stored as {RGB 12, 1, 1} and the rbg
	 * maker thingy expects them in 12,1,1, format so this cleans up the string
	 * a bit
	 * 
	 * @param item
	 * @return
	 */
	private String deParen(String item) {
		String d = item.replace('{', ' ').replace('}', ' ');
		d = d.replaceAll("[RGB ]", "").trim();
		return d;
	}

	public String snippetsPath() {
		return store.getString(CFMLPreferenceConstants.P_SNIPPETS_PATH).trim();
	}

	public String defaultSnippetsPath() {
		return SnippetPlugin.getDefault().getStateLocation().toString();
	}

	public int tabWidth() {
		// System.out.println("Tab width retrieved as: " +
		// Integer.parseInt(store.getString(ICFMLPreferenceConstants.P_TAB_WIDTH).trim()));
		return Integer.parseInt(store.getString(
				CFMLPreferenceConstants.P_TAB_WIDTH).trim());
	}

	public int defaultTabWidth() {
		return store.getDefaultInt(CFMLPreferenceConstants.P_TAB_WIDTH);
	}

	public boolean insertSpacesForTabs() {
		return store
				.getString(CFMLPreferenceConstants.P_INSERT_SPACES_FOR_TABS)
				.trim().equalsIgnoreCase("true");
	}

	public boolean defaultSpacesForTabs() {
		return store
				.getDefaultBoolean(CFMLPreferenceConstants.P_INSERT_SPACES_FOR_TABS);
	}

}
