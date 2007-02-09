package org.cfeclipse.frameworks.fusebox.objects;

import org.cfeclipse.frameworks.fusebox.util.PluginImages;

public class FBXInvoke extends FBXObject {
	private String icon = PluginImages.ICON_FBX4_MOD;

	public String getIcon() {
		return this.icon;
	}

	public String getName() {
		
		return "Invoke: ";
	}

	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
	
	
	
}
