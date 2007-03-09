package org.cfeclipse.cfml.properties;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;

/**
 * This class stores the URL that can be mapped for each resource.
 * If a given resource doesn't have a URL attached, we look at the parent, etc, until we find a URL property
 * 
 * @author markdrew
 *
 */
public class URLPropertyStore implements IPreferenceStore{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.IPreferenceStore#addPropertyChangeListener(org.eclipse.jface.util.IPropertyChangeListener)
	 */
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	public boolean contains(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public void firePropertyChangeEvent(String name, Object oldValue, Object newValue) {
		// TODO Auto-generated method stub
		
	}

	public boolean getBoolean(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getDefaultBoolean(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public double getDefaultDouble(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getDefaultFloat(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getDefaultInt(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getDefaultLong(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getDefaultString(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public double getDouble(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getFloat(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getInt(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getLong(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getString(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDefault(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean needsSaving() {
		// TODO Auto-generated method stub
		return false;
	}

	public void putValue(String name, String value) {
		// TODO Auto-generated method stub
		
	}

	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	public void setDefault(String name, double value) {
		// TODO Auto-generated method stub
		
	}

	public void setDefault(String name, float value) {
		// TODO Auto-generated method stub
		
	}

	public void setDefault(String name, int value) {
		// TODO Auto-generated method stub
		
	}

	public void setDefault(String name, long value) {
		// TODO Auto-generated method stub
		
	}

	public void setDefault(String name, String defaultObject) {
		// TODO Auto-generated method stub
		
	}

	public void setDefault(String name, boolean value) {
		// TODO Auto-generated method stub
		
	}

	public void setToDefault(String name) {
		// TODO Auto-generated method stub
		
	}

	public void setValue(String name, double value) {
		// TODO Auto-generated method stub
		
	}

	public void setValue(String name, float value) {
		// TODO Auto-generated method stub
		
	}

	public void setValue(String name, int value) {
		// TODO Auto-generated method stub
		
	}

	public void setValue(String name, long value) {
		// TODO Auto-generated method stub
		
	}

	public void setValue(String name, String value) {
		// TODO Auto-generated method stub
		
	}

	public void setValue(String name, boolean value) {
		// TODO Auto-generated method stub
		
	}

}
