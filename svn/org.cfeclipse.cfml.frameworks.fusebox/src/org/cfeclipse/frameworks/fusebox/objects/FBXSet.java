package org.cfeclipse.frameworks.fusebox.objects;

import org.cfeclipse.frameworks.fusebox.util.PluginImages;

public class FBXSet extends FBXObject {
	private String icon = PluginImages.ICON_FBX_IF;

	public String getIcon() {
		
		return this.icon;
	}

	public String getName() {
		return "Set:";
	}

	public String toString() {
		return getName();
	}
	
	
	
	
	
}
