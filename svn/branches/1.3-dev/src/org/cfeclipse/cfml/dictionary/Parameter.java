/*
 * Created on Feb 27, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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
package org.cfeclipse.cfml.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.cfeclipse.cfml.parser.docitems.AttributeItem;


/**
 * @author Rob
 *
 * This is a function's (and tag's) parameter (aka attribute).
 */
public class Parameter implements Comparable {
	/** The Parameter is not triggered (and therefore not available to the user at code assist) */
	public static final int PARAM_NOTTRIGGERED	= 0x0;
	/** The Parameter is triggered (and therefore available to the user at code assist, but not marked as mandatory/required) */
	public static final int	PARAM_TRIGGERED		= 0x1;
	/** The Parameter is required (generally and'ed with PARAM_TRIGGERED to indicate that it is triggered & required */ 
	public static final int PARAM_REQUIRED 		= 0x2;
	
	/** Is this parameter required by default (ignoring any 
	protected boolean paramRequired = false;
	*/
	
	private Trigger activeTrigger = null;
	
	/** The list of things that triggers this parameter (if not required by default) */
	ArrayList triggers = new ArrayList();
	
	/**
	 * Adds a trigger object that will cause this parameter to be required / presented
	 * as optional.
	 * 
	 * @param newTriggerSet The new trigger to set
	 * @see #org.cfeclipse.cfml.dictionary.Trigger
	 */
	public void addTrigger(Trigger newTriggerSet) {
		//System.out.println("Parameter::addTriger() - Param \' " + this.name + "\' now has " + this.triggers.size() + "\' triggers");
		this.triggers.add(newTriggerSet);
	}
	
	/**
	 * Checks the set of parameters to see whether any of the trigger lists are matched.
	 * 
	 * @param availParams name/value string pairs of parameters currently entered
	 * @return Whether the parameter is triggered or not (and whether it's required). Values will be one of:
	 * <ul>
	 * 	<li><code>PARAM_REQUIRED</code> - Parameter required & triggered (will be <code>PARAM_REQUIRED | PARAM_TRIGGERED</code>)</li>
	 *  <li><code>PARAM_TRIGGERED</code> - Parameter triggered</li>
	 *  <li><code>PARAM_NOTTRIGGERED</code> - Parameter not triggered</li>
	 * </ul>
	 */
	public int isTriggered(HashMap availParams)
	{
		
		/*
		 * isTriggered flies through the Parameter's trigger list asking each trigger
		 * whether they are activated by the available parameters (i.e. in the case of
		 * code assist, the one's that are currently entered).
		 * 
		 * TODO: The code assistor will have to forward-scan from the caret pos to get and succeeding attributes. Doh! 
		 */
		//System.out.print("Parameter::isTriggered() [" + this.name + "] - ");
		if(this.triggers.size() == 0 && this.required)
		{
			activeTrigger = null;
			//System.out.println(" no params, triggered & required");
			return PARAM_REQUIRED | PARAM_TRIGGERED;
		}
		else if(this.triggers.size() == 0)
		{
			activeTrigger = null;
			//System.out.println(" no params, triggered.");
			return PARAM_TRIGGERED;
		}
		
		Iterator trigIter = triggers.iterator();
		
		while(trigIter.hasNext())
		{
			Trigger currTrigger =(Trigger)trigIter.next(); 
			int trigVal = currTrigger.WillTrigger(availParams);
			if((trigVal & PARAM_TRIGGERED) == PARAM_TRIGGERED)
			{
				activeTrigger = currTrigger;
				//System.out.println("Param required");
				return trigVal;
			}
		}
		
		activeTrigger = null;
		//System.out.println("Param not triggered");
		return PARAM_NOTTRIGGERED;	// Fell through to here, available parameters didn't match any triggers.
	}
	
	
	protected String name = "";
	protected String type = Procedure.VOID;
	protected String help = "";
	protected String defaultValue = "";
	protected Set values;
	protected boolean required = false;
	protected String category ="General";
		
	public Parameter(String name)
	{
		this.name = name;
	}
	
	public Parameter(String name, String type)
	{
		this(name);
		this.type = type;
	}
	
	public Parameter(String name, String type, boolean required)
	{
		this(name,type);
		this.required = required;
	}
	
	public Parameter(String name, String type, boolean required, String defaultValue)
	{
		this(name,type);
		this.required = required;
		this.defaultValue = defaultValue;
	}
	
	public Parameter(String name, String type, boolean required, String defaultValue, String category)
	{
		this(name,type);
		this.required = required;
		this.defaultValue = defaultValue;
		this.category = category;
	}
	/**
	 * Checks to see if this parameter (attribute) is required
	 * @return
	 */
	public boolean isRequired()
	{
		return required;
	}
	
	/**
	 * Returns whether this tag is required comparing it to the attributes that are in there
	 * Have to check with the triggers of this parameter... wherever they come from!
	 * @author Mark Drew
	 * 
	 * @param availParams
	 * @return wheter its required
	 */
	public boolean isReqpuired(HashMap availParams){
	
		//If this doesnt have triggers and is required, return true
		if(triggers.isEmpty() || triggers == null){
			return required;
		}
		
		boolean r_Required = required;
		
		Iterator trigIter = triggers.iterator();
		while(trigIter.hasNext()){
			HashMap mTriggers = ((Trigger)trigIter.next()).triggerParams;
			Iterator mTriggerIter = mTriggers.keySet().iterator();
			while(mTriggerIter.hasNext()){
				Object attrib = mTriggerIter.next();
				if(availParams.containsKey(attrib)){
					AttributeItem obj = (AttributeItem)availParams.get(attrib);
					if(obj.getValue().equalsIgnoreCase(mTriggers.get(attrib).toString())){
						return  false;
						
					}
					
				}
			}
		}
		
		return required;
	}
	
	/**
	 * Returns the currently active trigger or null.
	 * @return
	 */
	public Trigger activeTrigger() {
		return activeTrigger;
	}
	/**
	 * this sets the name and type of this parameter - generally this should not
	 * be used as types dont often change.
	 * @param name the param name
	 * @param type the param type @see Procedure
	 */
	public void setNameAndType(String name, String type)
	{
		this.name = name;
		this.type = type;
	}
	
	/**
	 * Adds a default value to this parameter
	 * @param value the value to add
	 */
	public void addValue(Value value)
	{
		if(this.values == null)
			values = new HashSet();
		
		values.add(value);
	}
	
	public Set getValues()
	{
		if(this.values == null)
			return new HashSet();

		
		//System.err.println("Parameter::getValues() - I have " + values.size() + " elements");
		return values;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getDefaultValue()
	{
		return this.defaultValue;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public String getHelp()
	{
		return help;
	}
	
	public void setHelp(String help)
	{
		this.help = help;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		if(required) {
			sb.append(name + " - " + type);
		}
		else {
			sb.append("[" + name + " - " + type );
			if (this.defaultValue != null) {
			    sb.append(" \"" + this.defaultValue + "\"");
			}
			sb.append("]");
		}
		
		return sb.toString();
	}
	
	public boolean equals(Object obj)
	{
		if(obj instanceof Parameter)
		{
			//if the name is the same and the type is the same
			//assume its the same
			if( ((Parameter)obj).getName().equals(this.name) && ((Parameter)obj).getType().equals(this.type) )
			{
				return true;
			}
		}
		
		return false;
	}
	
	public int compareTo(Object o)
	{
		if(o == null)
			throw new NullPointerException("Null!");
		
		if(o instanceof Parameter)
		{
			return name.compareTo( ((Parameter)o).getName() );
		}
		
		return 0;
	}

	public String getCategory() {
		return category;
	}
	
}
