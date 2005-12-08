package com.rohanclan.cfml.editors.actions;

import java.lang.reflect.Method;

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
	public DynamicActionRunner(String classname ) {
		
		
		/*Object o; 
		Class c;
		System.out.println("*** loading class " + item.getData().toString());
		try {
			 c = Class.forName(item.getData().toString());  // Dynamically load the class
			 o = c.newInstance();          // Dynamically instantiate it
		
			 System.out.println("*** I have an instance of " + o.getClass().toString());
			 
//			--- find if it has a run method
				try {
					  Method m = c.getMethod("run", null);
					  m.invoke(o, null);
					} catch(Exception e) { System.out.println("*** method run not found"); }
		} catch (Exception e) { e.printStackTrace();} 
		*/
		
		
	}

	/**
	 * Actually run our class
	 */
	public void run(){
		
		
		
	}
	
	
}
