<cfcomponent displayname="Controller" extends="ModelGlue.unity.controller.Controller" output="false">
	<!--- 
		Any function set up to listen for the onRequestStart message happens before any of the <event-handlers>.
		This is a good place to put things like session defaults.
	--->

	<cfset variables.pageservice = "">
	<cfset variables.ArticleService = "">
	<!---START: getter and setter for variables.PageService --->
	<cffunction name="getPageService" output="false" returntype="any">
		<cfreturn variables.PageService>
	</cffunction>
	
	<cffunction name="setPageService" output="false" returntype="void">
		<cfargument name="PageService" type="any">
		<cfset variables.PageService = arguments.PageService>
	</cffunction>
	<!--- END: getter and setter for variables.PageService --->

	<cffunction name="getArticleService" output="false" returntype="any">
		<cfreturn variables.ArticleService>
	</cffunction>
	
	<cffunction name="setArticleService" output="false" returntype="void">
	<cfargument name="ArticleService" type="any">
		<cfset variables.ArticleService = arguments.ArticleService>
	
	</cffunction>


	
	<cffunction name="onRequestStart" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
	  <cfset variables.dsn = getModelGlue().getBean('reactorConfiguration').getDSN()>	
	  
	  <cfset stConfig = getModelGlue().getBean("googleConfig").getConfig() />
   		<cfloop collection="#stConfig#" item="conf">
    		  <cfset arguments.event.setValue("google_" & conf, stConfig[conf])>
   		</cfloop>
	
	<cfset stMetaData = getModelGlue().getBean("metadata").getConfig()>
    <cfset arguments.event.setValue("metadata", stMetaData)>
	  
	  <!--- get the navigation --->
	  <cfset arguments.event.setValue('section', arguments.event.getValue('page'))>
	  <cfset arguments.event.setValue('mainNav', variables.pageservice.getPage("home").getChildPages())>
	  
	
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
	
	<cffunction name="getRandTestimonials" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
	  
	 	 <cfset var dsn = getModelGlue().GETIOCCONTAINER().getBean('reactorConfiguration').getDSN()>
	  	<cfset var qryRandTestimonials = "">
	  	
	  	<cfquery name="qryRandTestimonials" datasource="#dsn#">
			SELECT * FROM cms_testimonial
			WHERE bPublished = 1
			ORDER BY RAND() Limit 2			
		</cfquery>
		<cfset arguments.event.setValue('qryTestimonials', qryRandTestimonials)>	  	
	  	
	</cffunction>
	
	<cffunction name="getTestimonials" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
	  
	 	 <cfset var dsn = getModelGlue().GETIOCCONTAINER().getBean('reactorConfiguration').getDSN()>
	  	<cfset var qryRandTestimonials = "">
	  	
	  	<cfquery name="qryRandTestimonials" datasource="#dsn#">
			SELECT * FROM cms_testimonial
			WHERE bPublished = 1
			ORDER BY orderid
		</cfquery>
		<cfset arguments.event.setValue('qryTestimonials', qryRandTestimonials)>	  	
	  	
	</cffunction>
	
	<cffunction name="getContent" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
	  
	 	<!--- arguments you can pass into this from the calling message --->
	 	<cfset var page_id = arguments.event.getArgument("pageid", "")>
	 	<cfset var content_type = arguments.event.getArgument("type" ,"")>
		<cfset var retQuery = arguments.event.getArgument("queryName", "content")> <!--- Default this to "content" --->
		
		<cfset var random = arguments.event.getArgument("random", "false")>
		<cfset var maxrows = arguments.event.getArgument("maxrows", 1000)>
		<cfset var orderby = arguments.event.getArgument("orderby", "")>
		<cfset var orderdirection = arguments.event.getArgument("orderdirection", "")>
		
		<cfset var rss = arguments.event.getArgument("rss", "")>
		
		<cfset var qryContentQuery = 0>
	
		
		<cfinvoke component="#variables.ArticleService#" 
			method="getArticles" 
			returnvariable="qryContentQuery"> 
				<cfinvokeargument name="pageid" value="#page_id#"/>
				<cfinvokeargument name="type" value="#content_type#"/>
				<cfinvokeargument name="orderByField" value="#orderby#"/>
				<cfinvokeargument name="orderByOrder" value="#orderdirection#"/>
				<cfinvokeargument name="limit" value="#maxrows#"/>
				<cfinvokeargument name="random" value="#random#"/>
				<cfinvokeargument name="rss" value="#rss#"/>
				
		</cfinvoke>
	 	
	 	<!--- 
	 	<cfquery name="qryContentQuery" datasource="#variables.dsn#" result="qryContentResult">
		 	SELECT     cms_article.*
			FROM         cms_article INNER JOIN
                      cms_article_type ON cms_article.art_type_id = cms_article_type.type_id
		 	WHERE cms_article.bPublished = 1
			<!--- AND dtDisplay <= <cfqueryparam cfsqltype="cf_sql_timestamp" value="#Now()#"> --->
			
			<cfif Len(content_type)>
			AND cms_article_type.type_name = <cfqueryparam cfsqltype="cf_sql_varchar" value="#content_type#">
			</cfif>
			<cfif random>
			ORDER BY RAND() Limit #maxrows#
			</cfif>
		</cfquery>
	 		 --->
	 	
		<cfset arguments.event.setValue(retQuery, qryContentQuery)> 
	</cffunction>
	
	<cffunction name="setValue" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
	  
	  <cfset stArguments = arguments.event.getAllArguments()>
	  <cfloop collection="#stArguments#" item="arg">
			<cfset arguments.event.setValue(arg, stArguments[arg])>
		</cfloop>
	  
	 </cffunction>

	<cffunction name="getPage" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
	  
	  	<cfset pagename = arguments.event.getValue('page', "")>
	  	<cfset arguments.event.setValue("pageObject", variables.pageservice.getPage(pagename))>
	  	
	  	<!--- TODO:  bit of a hack this, will need to fix up--->
	  	<cfif pagename EQ "testimonials">
				<cfset getTestimonials(arguments.event)>
			
		</cfif>
	  
	  </cffunction>
	 



	
	

</cfcomponent>