/**
 * 
 */
package org.cfeclipse.cfml.views.snips;

/**
 * 
 * This is a definition of a local snippet. This is what the Snip Reader can return
 * 
 * @author markdrew
 *
 */
public class SnippetObject {

	private String description;
	private String startBlock;
	private String endBlock;
	private String name;

	/**
	 * @param snipDescription
	 */
	public void setDescription(String snipDescription) {
		this.description = snipDescription;
		
	}

	/**
	 * @param snipStartBlock
	 */
	public void setStartBlock(String snipStartBlock) {
		this.startBlock = snipStartBlock;
		
	}

	/**
	 * @param snipEndBlock
	 */
	public void setEndBlock(String snipEndBlock) {
		this.endBlock = snipEndBlock;
		
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
		
	}

	public String getDescription() {
		return description;
	}

	public String getEndBlock() {
		return endBlock;
	}

	public String getName() {
		return name;
	}

	public String getStartBlock() {
		return startBlock;
	}

	
 
	
	
}
