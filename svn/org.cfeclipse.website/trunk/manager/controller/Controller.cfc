<cfcomponent displayname="Controller" extends="ModelGlue.unity.controller.Controller" output="false">
	
		<cfset variables.reactor ="">
	
	<!--- 
		Any function set up to listen for the onRequestStart message happens before any of the <event-handlers>.
		This is a good place to put things like session defaults.
	--->
	<cffunction name="onRequestStart" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
	  <cfset variables.reactor = getModelGLue().getOrmService()>
	  
	  <cfparam name="session.userlogin" default="#getModelGlue().getBean('UserLogin')#">
	  	
	  	<cfset pageevent = arguments.event.getValue('event') >

		<cfif pageevent NEQ "logout" AND pageevent NEQ "login" AND NOT session.userlogin.isLoggedIn()>
		 		<cfset arguments.event.forward('login')>
		<cfelseif pageevent EQ "login" AND arguments.event.valueExists('j_username') AND arguments.event.valueExists('j_password')>
			<!--- if it is a login request --->
				
				<cfset userGateway = variables.reactor.CREATEGATEWAY('cms_user')>
				<cfinvoke method="GETBYFIELDS" component="#userGateway#" returnvariable="r_User">
					<cfinvokeargument name="username" value="#arguments.event.getValue("j_username")#">
					<cfinvokeargument name="password" value="#arguments.event.getValue("j_password")#">
				</cfinvoke>
				<cfif r_User.recordcount>
					<cfset session.userLogin.setUserName(r_User.username)>
					<cfset session.userLogin.setLoggedIn(true)>
					<cfset session.userLogin.setIsAdmin(r_User.bAdmin)>
					<cfset arguments.event.forward('page.index')>
				<cfelse>
					<cfset arguments.event.setValue("message", "Unable to log you in, please check your username and password")>
					<cfset arguments.event.forward('login')>
				
				</cfif>
				
		
		</cfif>


	



	  <!--- 
	  <!--- forward any event that isnt an attempt to login --->
		<cfif pageevent NEQ "logout" OR pageevent NEQ "login">
			
		<!--- 	<cfif  NOT session.userlogin.isLoggedIn()> --->
			<!--- FORWARD THEM TO THE LOGIN PAGE --->
				<cflocation url="index.cfm?event=login">
		<!--- 	</cfif> --->
			
		 </cfif> --->
		
		<!--- 
		  <cfif NOT  arguments.event.valueExists('j_username') AND NOT arguments.event.valueExists('j_password')>
			<!--- still attempting to access a page --->
			  
		  
		  
		  
			<!--- check which request we are in --->
			<cfif arguments.event.getValue('event') NEQ "login" AND NOT session.userLogin.isLoggedIn()>
			<cfdump var="#arguments.event.getAllValues()#" label="notloggedin">
			
			
			
			
			</cfif>
			
		
	  </cfif>
	   --->
	 
	 
	  <!--- <cfset reactor = getModelGLue().getOrmService()>
	  <cfset page = reactor.createRecord('cms_Page')>
	  <cfset page.setPageId(1)>
	  <cfset page.load()>
	  <cfdump var="#page.getParent().getPageID()#">
	  <cfdump var="#page.GETCHILDPAGESITERATOR().getArray()#" label="getChildren">
		<cfabort> --->
	  
	</cffunction>
	<cffunction name="doLogout" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
	  
	  
	  	<cfset session.userlogin = getModelGlue().getBean('UserLogin')>
	  
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
	
	<cffunction name="saveRSS" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
		
		<cfset stCriteria = StructNew()>
		<cfset stCriteria.BPublished = 1>
		<cfset stCriteria.BRss = 1>
		<cfset cmsArticlesALL = getModelGlue().getORMAdapter().list('cms_article', stCriteria ,"DTCREATED", false)>
		<cfdump var="#cmsArticlesALL#">
		
		
		 <cfdump var="#event.getAllValues()#">
	 	<cfabort>
	
	</cffunction>
	
	
	
</cfcomponent>