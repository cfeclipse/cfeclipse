<cfcomponent>
	<cfsetting showdebugoutput="false" enablecfoutputonly="true" />
	
	<cffunction name="onApplicationStart" returnType="boolean">
	   <cfset application.rootID = "1" />
	   <cfset application.name = "SnipEx" />
	   <cfset application.servername = "SnipEx Server">
	   <cfset application.dsn = "cfeclipse" />
	   <cfset application.moderate = true /> <!--- If set to true, snippets will have to set the published to true manually --->
	   <cfset application.canSubmit = true /> <!--- If set to true, snippets can be submitted to this server --->
	   <cfreturn true />
	</cffunction>
	
</cfcomponent>
