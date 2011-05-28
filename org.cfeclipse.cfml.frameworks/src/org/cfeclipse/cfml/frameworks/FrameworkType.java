package org.cfeclipse.cfml.frameworks;

public class FrameworkType {
	private String id;
	private String name;
	public FrameworkType(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
}
