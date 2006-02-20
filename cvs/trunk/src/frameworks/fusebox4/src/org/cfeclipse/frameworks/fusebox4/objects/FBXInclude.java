package org.cfeclipse.frameworks.fusebox4.objects;

import org.cfeclipse.frameworks.fusebox4.util.PluginImages;

public class FBXInclude extends FBXObject {
	private String icon = PluginImages.ICON_FBX_FUSE;

	public String getIcon() {
		return this.icon;
	}

	public String getName() {
		return "Include:";
	}

	public String toString() {
		return getName();
	}
	
	
}
