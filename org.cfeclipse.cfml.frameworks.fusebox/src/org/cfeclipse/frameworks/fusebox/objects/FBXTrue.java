package org.cfeclipse.frameworks.fusebox.objects;

import org.cfeclipse.frameworks.fusebox.util.PluginImages;

public class FBXTrue extends FBXObject {
	private String icon = PluginImages.ICON_FBX_TRUE;
	
	public String getIcon() {
		return this.icon;
	}

	public String getName() {
		return "True";
	}

	public String toString() {
		return getName();
	}

}
