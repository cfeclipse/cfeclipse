/*
 * Created on Oct 15, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.text.Assert;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

class OverlayPreferenceStore
	implements IPreferenceStore
{
	public static final class TypeDescriptor
	{

		private TypeDescriptor()
		{
		}

		TypeDescriptor(TypeDescriptor typedescriptor)
		{
			this();
		}
	}

	public static class OverlayKey
	{

		TypeDescriptor fDescriptor = null;
		String fKey = null;

		public OverlayKey(TypeDescriptor descriptor, String key)
		{
			fDescriptor = descriptor;
			fKey = key;
		}
	}

	private class PropertyListener
		implements IPropertyChangeListener
	{

		public void propertyChange(PropertyChangeEvent event)
		{
			OverlayKey key = findOverlayKey(event.getProperty());
			if(key != null)
				propagateProperty(fParent, key, fStore);
		}

		PropertyListener()
		{
		}
	}


	public static final TypeDescriptor BOOLEAN = new TypeDescriptor(null);
	public static final TypeDescriptor DOUBLE = new TypeDescriptor(null);
	public static final TypeDescriptor FLOAT = new TypeDescriptor(null);
	public static final TypeDescriptor INT = new TypeDescriptor(null);
	public static final TypeDescriptor LONG = new TypeDescriptor(null);
	public static final TypeDescriptor STRING = new TypeDescriptor(null);
	private IPreferenceStore fParent = null;
	private IPreferenceStore fStore = null;
	private OverlayKey fOverlayKeys[] = null;
	private PropertyListener fPropertyListener = null;
	private boolean fLoaded = false;

	public OverlayPreferenceStore(IPreferenceStore parent, OverlayKey overlayKeys[])
	{
		fParent = parent;
		fOverlayKeys = overlayKeys;
		fStore = new PreferenceStore();
	}

	private OverlayKey findOverlayKey(String key)
	{
		for(int i = 0; i < fOverlayKeys.length; i++)
			if(fOverlayKeys[i].fKey.equals(key))
				return fOverlayKeys[i];

		return null;
	}

	private boolean covers(String key)
	{
		return findOverlayKey(key) != null;
	}

	private void propagateProperty(IPreferenceStore orgin, OverlayKey key, IPreferenceStore target)
	{
		if(orgin.isDefault(key.fKey))
		{
			if(!target.isDefault(key.fKey))
				target.setToDefault(key.fKey);
			return;
		}
		TypeDescriptor d = key.fDescriptor;
		if(BOOLEAN == d)
		{
			boolean originValue = orgin.getBoolean(key.fKey);
			boolean targetValue = target.getBoolean(key.fKey);
		    
			if(targetValue != originValue)
				target.setValue(key.fKey, originValue);
		} else
		if(DOUBLE == d)
		{
			double originValue = orgin.getDouble(key.fKey);
			double targetValue = target.getDouble(key.fKey);
			if(targetValue != originValue)
				target.setValue(key.fKey, originValue);
		} else
		if(FLOAT == d)
		{
			float originValue = orgin.getFloat(key.fKey);
			float targetValue = target.getFloat(key.fKey);
			if(targetValue != originValue)
				target.setValue(key.fKey, originValue);
		} else
		if(INT == d)
		{
			int originValue = orgin.getInt(key.fKey);
			int targetValue = target.getInt(key.fKey);
			if(targetValue != originValue)
				target.setValue(key.fKey, originValue);
		} else
		if(LONG == d)
		{
			long originValue = orgin.getLong(key.fKey);
			long targetValue = target.getLong(key.fKey);
			if(targetValue != originValue)
				target.setValue(key.fKey, originValue);
		} else
		if(STRING == d)
		{
			String originValue = orgin.getString(key.fKey);
			String targetValue = target.getString(key.fKey);
		    //System.out.println("Propogating " + key.fKey + " with a value of " + originValue);
			if(targetValue != null && originValue != null && !targetValue.equals(originValue))
				target.setValue(key.fKey, originValue);
		}
	}

	public void propagate()
	{
		for(int i = 0; i < fOverlayKeys.length; i++){
		    
			propagateProperty(fStore, fOverlayKeys[i], fParent);
		}

	}

	private void loadProperty(IPreferenceStore orgin, OverlayKey key, IPreferenceStore target, boolean forceInitialization)
	{
		TypeDescriptor d = key.fDescriptor;
		if(BOOLEAN == d)
		{
			if(forceInitialization)
				target.setValue(key.fKey, true);
			target.setValue(key.fKey, orgin.getBoolean(key.fKey));
			target.setDefault(key.fKey, orgin.getDefaultBoolean(key.fKey));
		} else
		if(DOUBLE == d)
		{
			if(forceInitialization)
				target.setValue(key.fKey, 1.0D);
			target.setValue(key.fKey, orgin.getDouble(key.fKey));
			target.setDefault(key.fKey, orgin.getDefaultDouble(key.fKey));
		} else
		if(FLOAT == d)
		{
			if(forceInitialization)
				target.setValue(key.fKey, 1.0F);
			target.setValue(key.fKey, orgin.getFloat(key.fKey));
			target.setDefault(key.fKey, orgin.getDefaultFloat(key.fKey));
		} else
		if(INT == d)
		{
			if(forceInitialization)
				target.setValue(key.fKey, 1);
			target.setValue(key.fKey, orgin.getInt(key.fKey));
			target.setDefault(key.fKey, orgin.getDefaultInt(key.fKey));
			
		} else
		if(LONG == d)
		{
			if(forceInitialization)
				target.setValue(key.fKey, 1L);
			target.setValue(key.fKey, orgin.getLong(key.fKey));
			target.setDefault(key.fKey, orgin.getDefaultLong(key.fKey));
		} else
		if(STRING == d)
		{
			if(forceInitialization)
				target.setValue(key.fKey, "1");
			target.setValue(key.fKey, orgin.getString(key.fKey));
			target.setDefault(key.fKey, orgin.getDefaultString(key.fKey));
		}
	}

	public void load()
	{
		for(int i = 0; i < fOverlayKeys.length; i++)
			loadProperty(fParent, fOverlayKeys[i], fStore, true);

		fLoaded = true;
	}

	public void loadDefaults()
	{
		for(int i = 0; i < fOverlayKeys.length; i++)
			setToDefault(fOverlayKeys[i].fKey);

	}

	public void start()
	{
		if(fPropertyListener == null)
		{
			fPropertyListener = new PropertyListener();
			fParent.addPropertyChangeListener(fPropertyListener);
		}
	}

	public void stop()
	{
		if(fPropertyListener != null)
		{
			fParent.removePropertyChangeListener(fPropertyListener);
			fPropertyListener = null;
		}
	}

	public void addPropertyChangeListener(IPropertyChangeListener listener)
	{
		fStore.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(IPropertyChangeListener listener)
	{
		fStore.removePropertyChangeListener(listener);
	}

	public void firePropertyChangeEvent(String name, Object oldValue, Object newValue)
	{
		fStore.firePropertyChangeEvent(name, oldValue, newValue);
	}

	public boolean contains(String name)
	{
		return fStore.contains(name);
	}

	public boolean getBoolean(String name)
	{
		return fStore.getBoolean(name);
	}

	public boolean getDefaultBoolean(String name)
	{
		return fStore.getDefaultBoolean(name);
	}

	public double getDefaultDouble(String name)
	{
		return fStore.getDefaultDouble(name);
	}

	public float getDefaultFloat(String name)
	{
		return fStore.getDefaultFloat(name);
	}

	public int getDefaultInt(String name)
	{
		return fStore.getDefaultInt(name);
	}

	public long getDefaultLong(String name)
	{
		return fStore.getDefaultLong(name);
	}

	public String getDefaultString(String name)
	{
		return fStore.getDefaultString(name);
	}

	public double getDouble(String name)
	{
		return fStore.getDouble(name);
	}

	public float getFloat(String name)
	{
		return fStore.getFloat(name);
	}

	public int getInt(String name)
	{
		return fStore.getInt(name);
	}

	public long getLong(String name)
	{
		return fStore.getLong(name);
	}

	public String getString(String name)
	{
		return fStore.getString(name);
	}

	public boolean isDefault(String name)
	{
		return fStore.isDefault(name);
	}

	public boolean needsSaving()
	{
		return fStore.needsSaving();
	}

	public void putValue(String name, String value)
	{
		if(covers(name))
			fStore.putValue(name, value);
	}

	public void setDefault(String name, double value)
	{
		if(covers(name))
			fStore.setDefault(name, value);
	}

	public void setDefault(String name, float value)
	{
		if(covers(name))
			fStore.setDefault(name, value);
	}

	public void setDefault(String name, int value)
	{
		if(covers(name))
			fStore.setDefault(name, value);
	}

	public void setDefault(String name, long value)
	{
		if(covers(name))
			fStore.setDefault(name, value);
	}

	public void setDefault(String name, String value)
	{
		if(covers(name))
			fStore.setDefault(name, value);
	}

	public void setDefault(String name, boolean value)
	{
		if(covers(name))
			fStore.setDefault(name, value);
	}

	public void setToDefault(String name)
	{
		fStore.setToDefault(name);
	}

	public void setValue(String name, double value)
	{
		if(covers(name))
			fStore.setValue(name, value);
	}

	public void setValue(String name, float value)
	{
		if(covers(name))
			fStore.setValue(name, value);
	}

	public void setValue(String name, int value)
	{
		if(covers(name))
			fStore.setValue(name, value);
	}

	public void setValue(String name, long value)
	{
		if(covers(name))
			fStore.setValue(name, value);
	}

	public void setValue(String name, String value)
	{
		if(covers(name))
			fStore.setValue(name, value);
	}

	public void setValue(String name, boolean value)
	{
		if(covers(name))
			fStore.setValue(name, value);
	}

	public void addKeys(OverlayKey keys[])
	{
		Assert.isTrue(!fLoaded);
		Assert.isNotNull(keys);
		int overlayKeysLength = fOverlayKeys.length;
		OverlayKey result[] = new OverlayKey[keys.length + overlayKeysLength];
		int i = 0;
		for(int length = overlayKeysLength; i < length; i++)
			result[i] = fOverlayKeys[i];

		i = 0;
		for(int length = keys.length; i < length; i++)
			result[overlayKeysLength + i] = keys[i];

		fOverlayKeys = result;
		if(fLoaded)
			load();
	}





}