package org.cfeclipse.cfml.wizards.cfcwizard;

public class BeanTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CFCBean appCFCBean = new CFCBean();
		
		appCFCBean.setDisplayName("Controller.cfc");
		appCFCBean.setExtendCFC("ModelGlue.unity.controller.Controller");
		appCFCBean.setOutput("false");
		
		
		//Creaate some properties for testing
		
		CFCPropertyBean propBean = new CFCPropertyBean();
		propBean.setName("variables.name");
		propBean.setType("String");
		propBean.setShouldWriteGetter(true);
		propBean.setShouldWriteSetter(true);
		
		appCFCBean.addPropertyBean(propBean);
		
		//Create the event
		
		CFCArgumentBean eventBean = new CFCArgumentBean();
		eventBean.setName("event");
		eventBean.setType("any");
		
		//Create some function beans
		
		CFCFunctionBean onRequestStartBean = new CFCFunctionBean();
		onRequestStartBean.setName("onRequestStart");
		onRequestStartBean.setAccess("public");
		onRequestStartBean.setReturnType("void");
		onRequestStartBean.setOutput(false);

		onRequestStartBean.addArgumentBean("1", eventBean);
		
		appCFCBean.addFunctionBean(onRequestStartBean);
		
		
		CFCFunctionBean onRequestEnd = new CFCFunctionBean();
		onRequestEnd.setName("onRequestEnd");
		onRequestEnd.setAccess("public");
		onRequestEnd.setReturnType("void");
		onRequestEnd.setOutput(false);
		
		onRequestEnd.addArgumentBean("1", eventBean);
		appCFCBean.addFunctionBean(onRequestEnd);
		
		CFCFunctionBean onQueueComplete = new CFCFunctionBean();
		onQueueComplete.setName("onQueueComplete");
		onQueueComplete.setAccess("public");
		onQueueComplete.setReturnType("void");
		onQueueComplete.setOutput(false);
		
		onQueueComplete.addArgumentBean("1", eventBean);
		appCFCBean.addFunctionBean(onQueueComplete);
		
		
		CFCFileFactory cfcFactory = new CFCFileFactory();
		
		StringBuffer string = cfcFactory.getString(appCFCBean);

		string = cfcFactory.getScriptString(appCFCBean);
		
	/*	
		<cfcomponent displayname="Controller" extends="ModelGlue.unity.controller.Controller" output="false">
		<!--- 
			Any function set up to listen for the onRequestStart message happens before any of the <event-handlers>.
			This is a good place to put things like session defaults.
		--->
		
		<cffunction name="onRequestStart" access="public" returnType="void" output="false">
		  <cfargument name="event" type="any">
		  <cfset variables.dsn = getModelGlue().getBean('reactorConfiguration').getDSN()>	 
		  
		  <cfdump var="#getModelGlue().getBean('softCache')#">
		   
		   <cfabort>
		</cffunction>

		<!--- 
			Any function set up to listen for the onQueueComplete message happens after all event-handlers are
			finished running and before any views are rendered.  This is a good place to load constants (like UDF
			libraries) that the views may need.
		--->
		<cffunction name="onQueueComplete" access="public" returnType="void" output="false">
		  <cfargument name="event" type="any">
		</cffunction>

		<!--- 
			Any function set up to listen for the onRequestStart message happens after views are rendered.
		--->
		<cffunction name="onRequestEnd" access="public" returnType="void" output="false">
		  <cfargument name="event" type="any">
		</cffunction>
		
	</cfcomponent>*/
		
		
		
	}

}
