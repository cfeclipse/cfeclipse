package org.cfeclipse.cfml.views.dictionary;

public class AttributeItem {
	private String attributeName = "";
	private String attributeValue = "";
	private boolean required = false;
	
	public AttributeItem(String name, String value) {
		this.attributeName = name.trim();
		this.attributeValue = value.trim();
	}
	
	public String getAttributeName() {
		return attributeName;
	}
	
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public String getAttributeValue() {
		return attributeValue;
	}
	
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	public boolean isRequired() {
		return required;
	}
	
	public void setRequired(boolean required) {
		this.required = required;
	}
}
