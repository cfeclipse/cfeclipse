package org.cfeclipse.cfml.editors.contentassist;

import org.eclipse.jface.resource.ImageDescriptor;

public class AssistContributor {

	/** The image descriptor for this category, or <code>null</code> if none specified. */
	private final ImageDescriptor fImage = null;
	
	private boolean fIsSeparateCommand= true;
	private boolean fIsEnabled= true;
	private boolean fIsIncluded= true;
	
	private int fSortOrder= 0x10000;
	private String fLastError= null;
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}

	public void sessionEnded() {
		// TODO Auto-generated method stub
		
	}

	public void sessionStarted() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Returns the name of the described extension
	 * without mnemonic hint in order to be displayed
	 * in a message.
	 * 
	 * @return Returns the name
	 */
	public String getDisplayName() {
		return getName();
		//return LegacyActionTools.removeMnemonics(fName);
	}

	/**
	 * Returns the image descriptor of the described category.
	 * 
	 * @return the image descriptor of the described category
	 */
	public ImageDescriptor getImageDescriptor() {
		return fImage;
	}
	
	/**
	 * Sets the separate command state of the category.
	 * 
	 * @param enabled the new enabled state.
	 */
	public void setSeparateCommand(boolean enabled) {
		fIsSeparateCommand= enabled;
	}
	
	/**
	 * Returns the enablement state of the category.
	 * 
	 * @return the enablement state of the category
	 */
	public boolean isSeparateCommand() {
		return fIsSeparateCommand;
	}
	
	/**
	 * @param included the included
	 */
	public void setIncluded(boolean included) {
		fIsIncluded= included;
	}
	
	/**
	 * @return included
	 */
	public boolean isIncluded() {
		return fIsIncluded;
	}

	public boolean isEnabled() {
		return fIsEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		fIsEnabled= isEnabled;
	}
	
	/**
	 * @return sortOrder
	 */
	public int getSortOrder() {
		return fSortOrder;
	}
	
	/**
	 * @param sortOrder the sortOrder
	 */
	public void setSortOrder(int sortOrder) {
		fSortOrder= sortOrder;
	}

	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
