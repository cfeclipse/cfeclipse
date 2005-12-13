package com.rohanclan.cfml.editors.actions;

import java.lang.reflect.Method;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.internal.Workbench;

/**
 * This class is used from the editor toolbar to dynamically load action classes. 
 * We pass in a string of what we want to do and then this class figures out the other attributes of that class
 * so that it can run 
 * @author mdrew
 *
 */
public class DynamicActionRunner {
	Object o;
	Class c;
	

	/**
	 * Setup so we are ready to run our action
	 * @param classname
	 */
	public DynamicActionRunner(String classname) {
		
		Object o; 
		Class c;
		try {
				c = Class.forName(classname);  // Dynamically load the class
				o = c.newInstance();          // Dynamically instantiate it
			
					//first check if this method exists, then run it... for both below.
				Method setActEd = c.getMethod("setActiveEditor", new Class[]{IAction.class, IEditorPart.class} );
				Method r = c.getMethod("run", null);
					
				setActEd.invoke(o, new Object[]{null,Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor()});
				r.invoke(o, null);
				    
			} catch (Exception e) {
			
				e.printStackTrace();
			}
		
	}

}
