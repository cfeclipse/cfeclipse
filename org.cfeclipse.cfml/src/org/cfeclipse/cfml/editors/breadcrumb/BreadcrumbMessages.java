package org.cfeclipse.cfml.editors.breadcrumb;

import org.eclipse.osgi.util.NLS;


/**
 * Helper class to get NLSed messages.
 *
 * @since 3.4
 */
public class BreadcrumbMessages extends NLS {

	private static final String BUNDLE_NAME= BreadcrumbMessages.class.getName();

	public static String BreadcrumbItemDropDown_showDropDownMenu_action_toolTip;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, BreadcrumbMessages.class);
	}

	private BreadcrumbMessages() {
	}
}