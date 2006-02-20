<cfsilent>
<cfapplication 
	name="myFuseboxApp" 
	sessionmanagement="Yes" 					
	sessiontimeout="#CreateTimeSpan(0,0,30,0)#" 	
	clientmanagement="Yes">
</cfsilent>
<cfset FUSEBOX_APPLICATION_PATH = ""> 
<cfinclude template="fusebox4.runtime.cfmx.cfm">
