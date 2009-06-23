package org.cfeclipse.cfml.parser;
import org.cfeclipse.cfml.parser.docitems.TagItem;
/**
 * @author Mark Drew
 * This class is what is stored in the documentVariablesMap.
 * It defines the location of the variable, the type and the name of it
 * The variable content assist will use this, especially the line number as that is what we want
 * 
 */
public class VariableParserItem {
	private String variable;
	private int lineNumber;
	private TagItem tagItem;
	
	/**
	 * @param variable
	 * @param lineNumber
	 * @param tagItem
	 */
	public VariableParserItem(String variable, int lineNumber, TagItem tagItem) {
		super();
		this.variable = variable;
		this.lineNumber = lineNumber;
		this.tagItem = tagItem;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public TagItem getTagItem() {
		return tagItem;
	}
	public void setTagItem(TagItem tagItem) {
		this.tagItem = tagItem;
	}
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	
	public String toString(){
		return variable + " : " + tagItem.getName();
	}
}
