/*
 * Created on Oct 12, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver Tupman
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package org.cfeclipse.cfml.snippets.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.cfeclipse.cfml.snippets.SnippetPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;


/**
 * This is a property store for a project. It stores all of it's values within
 * the project via IProject::setPersistentProperty(). While IPreferenceStore
 * provides methods for setting defaults, these are currently ignored.
 *  
 * Default value requests are obtained from the global preference store, therefore
 * allowing global defaults that are applied on a per-project basis. 
 *
 * @author Oliver Tupman
 */
public class ProjectPropertyStore implements IPreferenceStore 
{
	/** this is the per project setting for their dictionary selection */
    //public static final String P_CFML_DICTIONARY = "cfmlDictionary";
    
    /** the default key to the dictionary to use in this project.
     * @see DictionaryManager 
     */
    //public static final String P_CFML_DICTIONARY_DEFAULT = "cfmx701";
    
    /** The project that this store wraps to provide the pref store */
    private IProject project;
    
    /** The list of items that are listenening for property changes */
    private ArrayList listeners;

    /** Temporary list of properties (that have been changed) */
    private HashMap props;
    
    /** Stores the default values for various items */
    //private HashMap defaults;
    
    private IPreferenceStore globalPrefs;
    
    /**
     * Constructs a property store based upon a project.
     * 
     * @param srcProject The project that will store the preferences
     */
    public ProjectPropertyStore(IProject srcProject) 
    {
        this();
        //Assert.isNotNull(srcProject,"ProjectPropertyStore::ProjectPropertyStore()");
        if(srcProject == null)
        		throw new IllegalArgumentException("ProjectPropertyStore::ProjectPropertyStore()");
        
        this.project = srcProject;
        this.listeners = new ArrayList();
        this.props = new HashMap();
        //this.defaults = new HashMap();
        //this.defaults.put(ICFMLPreferenceConstants.P_CFML_DICTIONARY, CFMLPreferenceManager.)
        this.globalPrefs = SnippetPlugin.getDefault().getPreferenceStore();
    }
    
    /**
     * Constructs a property store without a project.
     * This class will then store the set properties in a temporary variable
     * until the project is set. 
     */
    public ProjectPropertyStore()
    {
        super();
        this.project = null;
        this.listeners = new ArrayList();
        this.props = new HashMap();
    }

    /**
     * Gets the project associated with this property store.
     * 
     * @return The IProject otherwise null if no project is yet associated
     */
    public IProject getProject()
    {
        return this.project;
    }
    
    /**
     * Sets the source project. Transfers the temporary properties to the new
     * project.
     * 
     * @param srcProject The project to base this store upon.
     */
    public void setProject(IProject srcProject)
    {
        if(srcProject == null)
            return;
        
        this.project = srcProject;
        
        Iterator propIter = this.props.keySet().iterator();
        while(propIter.hasNext())
        {
            String propName = (String)propIter.next();
            this.putValueHandled(propName, (String)this.props.get(propName));
        }
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#addPropertyChangeListener(org.eclipse.jface.util.IPropertyChangeListener)
     */
    public void addPropertyChangeListener(IPropertyChangeListener listener) {
        this.addPropertyChangeListener(listener);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#contains(java.lang.String)
     */
    public boolean contains(String name) {
        String retVal = null;
        try {
            if(this.project != null)
                retVal = this.project.getPersistentProperty(getQName(name));
            else
                retVal = (String)this.props.get(name);
        }
        catch(CoreException ex)
        {
            ex.printStackTrace();
        }
        return retVal != null;
    }

    public static QualifiedName getQName(String name)
    {
        return new QualifiedName("org.cfeclipse.cfml.", name);
    }
    
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#firePropertyChangeEvent(java.lang.String, java.lang.Object, java.lang.Object)
     */
    public void firePropertyChangeEvent(String name, Object oldValue, Object newValue) 
    {
        Object srcObj = (this.project != null) ? (Object)project : (Object)this.props;
        PropertyChangeEvent event = new PropertyChangeEvent(srcObj, name, oldValue, newValue);
        
        Iterator listenerIter = this.listeners.iterator();
        
        while(listenerIter.hasNext())
        {
            IPropertyChangeListener listener = (IPropertyChangeListener)listenerIter.next();    
            listener.propertyChange(event);
        }
    }

    private String getProperty(QualifiedName qName)
    {
        String retVal = null;
        try {
            if(this.project != null)
                retVal = this.project.getPersistentProperty(qName);
            else
                retVal = (String)this.props.get(qName.getLocalName());
        } catch(CoreException ex) {
            ex.printStackTrace();
        } 
        return retVal;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#getBoolean(java.lang.String)
     */
    public boolean getBoolean(String name) {
        if(!this.contains(name))
            return getDefaultBoolean(name);
        
        String strVal = getProperty(getQName(name));
        return strVal.equals("true");
    }

    private String getDefault(String name)
    {
        return this.globalPrefs.getDefaultString(name);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#getDefaultBoolean(java.lang.String)
     */
    public boolean getDefaultBoolean(String name) {
        return this.globalPrefs.getDefaultBoolean(name);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#getDefaultDouble(java.lang.String)
     */
    public double getDefaultDouble(String name) {
        return this.globalPrefs.getDefaultDouble(name);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#getDefaultFloat(java.lang.String)
     */
    public float getDefaultFloat(String name) {
        return this.globalPrefs.getDefaultFloat(name);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#getDefaultInt(java.lang.String)
     */
    public int getDefaultInt(String name) {
        return this.globalPrefs.getDefaultInt(name); 
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#getDefaultLong(java.lang.String)
     */
    public long getDefaultLong(String name) {
        return this.globalPrefs.getDefaultLong(name);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#getDefaultString(java.lang.String)
     */
    public String getDefaultString(String name) {
        return this.globalPrefs.getDefaultString(name);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#getDouble(java.lang.String)
     */
    public double getDouble(String name) {
        if(!this.contains(name))
            return getDefaultDouble(name);
        
        double retVal = 0.0;
        
        try {
            retVal = Double.parseDouble(getString(name));
        } catch(NumberFormatException ex) {
            
        }
        return retVal;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#getFloat(java.lang.String)
     */
    public float getFloat(String name) {
        if(!this.contains(name))
            return getDefaultFloat(name);
        
        float retVal = 0;
        try {
            retVal = Float.parseFloat(getString(name));
        } catch(NumberFormatException ex) {
            
        }
        return retVal;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#getInt(java.lang.String)
     */
    public int getInt(String name) {
        if(!this.contains(name))
            return getDefaultInt(name);
        int retVal = 0;
        
        try {
            retVal = Integer.parseInt(getString(name));
        }catch(NumberFormatException ex) {
            
        }
        return retVal;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#getLong(java.lang.String)
     */
    public long getLong(String name) {
        if(!this.contains(name))
            return getDefaultLong(name);
        
        long retVal = 0;
        try {
            retVal = Long.parseLong(getString(name));
        } catch(NumberFormatException ex) {
            
        }
        return retVal;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#getString(java.lang.String)
     */
    public String getString(String name) {
        String retVal = "";
        if(this.contains(name))
        {
            retVal = getProperty(getQName(name));
        }
        return retVal;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#isDefault(java.lang.String)
     */
    public boolean isDefault(String name) {
        if(!this.contains(name))
            return false;
        
        return this.getString(name).equals(this.getDefault(name));
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#needsSaving()
     */
    public boolean needsSaving() {
        return this.props.size() > 0;
    }

    private void putValueHandled(String name, String value)
    {
        try {
            if(this.project != null)
                this.project.setPersistentProperty(getQName(name), value);
            
            this.props.put(name, value);
        }catch(CoreException ex) {
            ex.printStackTrace();
        }
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#putValue(java.lang.String, java.lang.String)
     */
    public void putValue(String name, String value) {
        putValueHandled(name, value);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#removePropertyChangeListener(org.eclipse.jface.util.IPropertyChangeListener)
     */
    public void removePropertyChangeListener(IPropertyChangeListener listener) {
        this.listeners.remove(listener);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String, double)
     */
    public void setDefault(String name, double value) {;}

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String, float)
     */
    public void setDefault(String name, float value) {;}

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String, int)
     */
    public void setDefault(String name, int value) {;}

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String, long)
     */
    public void setDefault(String name, long value) {;}

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String, java.lang.String)
     */
    public void setDefault(String name, String defaultObject) {;}

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String, boolean)
     */
    public void setDefault(String name, boolean value) {;}

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#setToDefault(java.lang.String)
     */
    public void setToDefault(String name) {;}

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String, double)
     */
    public void setValue(String name, double value) {
        this.setValue(name, Double.toString(value));
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String, float)
     */
    public void setValue(String name, float value) {
        this.setValue(name, Float.toString(value));
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String, int)
     */
    public void setValue(String name, int value) {
        this.setValue(name, Integer.toString(value));
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String, long)
     */
    public void setValue(String name, long value) {
        this.setValue(name, Long.toString(value));
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String, java.lang.String)
     */
    public void setValue(String name, String value) {
        String oldValue = this.getString(name);
        
        this.putValueHandled(name, value);
        this.firePropertyChangeEvent(name, oldValue, value);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String, boolean)
     */
    public void setValue(String name, boolean value) {
        this.setValue(name, (value) ? "true" : "false");
    }

}
