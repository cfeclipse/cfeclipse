package org.cfeclipse.frameworks.fusebox.objects;

import org.cfeclipse.frameworks.fusebox.util.PluginImages;

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
