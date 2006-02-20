package org.cfeclipse.frameworks.fusebox4.objects;

import org.cfeclipse.frameworks.fusebox4.util.PluginImages;

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
