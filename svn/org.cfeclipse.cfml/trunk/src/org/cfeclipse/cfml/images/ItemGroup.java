package org.cfeclipse.cfml.images;

import java.util.ArrayList;

public class ItemGroup {

	String name;
	ArrayList<IImageEntry> childItems = new ArrayList<IImageEntry>();

	public ItemGroup(String name, ArrayList<IImageEntry> childItems) {
		super();
		this.name = name;
		this.childItems = childItems;
	}

	public String getName() {
		return this.name;
	}

	public int getChildCount() {
		return this.childItems.size();
	}

	public IImageEntry getImage(int num) {
		return this.childItems.get(num);
	}
}
