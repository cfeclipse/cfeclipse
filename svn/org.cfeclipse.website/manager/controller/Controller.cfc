<cfcomponent displayname="Controller" extends="ModelGlue.unity.controller.Controller" output="false">
	<!--- 
		Any function set up to listen for the onRequestStart message happens before any of the <event-handlers>.
		This is a good place to put things like session defaults.
	--->
	<cffunction name="onRequestStart" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
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
</cfcomponent>