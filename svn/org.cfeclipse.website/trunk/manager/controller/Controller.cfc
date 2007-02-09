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
	  
	</cffunction>
	
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
		<cfset dsn = getModelGlue().getBean('reactorConfiguration').getDSN()>
		
		<cfquery name="qryContentQuery" datasource="#dsn#" result="qNews">
		 	SELECT     cms_article.* , cms_user.username, cms_user.DisplayName, cms_user.email, cms_page.pagename
			FROM         cms_article 
			
			
			INNER JOIN
                      cms_article_type ON cms_article.art_type_id = cms_article_type.type_id
		 	INNER JOIN 
		 			cms_user ON cms_article.userid = cms_user.userid
		 	INNER JOIN
		 			cms_page ON cms_article.art_page_id = cms_page.pageid
		 	WHERE cms_article.bPublished = 1
		 
			AND cms_article.brss = 1
			
			ORDER BY dtCreated DESC
		</cfquery>


		<cfsavecontent variable="rssOutput"><?xml version="1.0" encoding="ISO-8859-1" ?>
<rss version="2.0">
	<channel>
		<title>CFEclipse.org</title>
		<link>http://www.cfeclipse.org/</link>
		<description>News about CFEclipse</description>
		<language>en-us</language>
		<copyright>Copyright 2004-<cfoutput>#DateFormat(Now(), "yyyy")#</cfoutput>CFEclipse.org</copyright>
		<lastBuildDate><cfoutput>#dateFormat(Now(), "ddd, dd mmm yyyy")# #TimeFormat(Now(), "HH:mm:ss")# EST</cfoutput></lastBuildDate>
		<!--- items to go here --->
		<cfoutput query="qryContentQuery">
		<item>
			<title>#XMLFormat(ART_TITLE)#</title>
			<description>#XMLFormat(art_description)#</description>
			<link>http://#CGI.SERVER_NAME#/index.cfm?event=page&page#pagename#&contentid=#art_id#</link>
			<author>#DisplayName#</author>
			<pubDate>#xmlFormat(dateFormat(DTCREATED, "ddd, dd mmm yyyy"))# #XmlFormat(TimeFormat(DTCREATED, "HH:mm:ss"))#</pubDate>
		</item>
		</cfoutput>
		
	</channel>




</rss>
		
		</cfsavecontent>
		<cfset rssFilePath =expandpath("../rss.xml")>
		<cffile action="write" file="#rssFilePath#" output="#rssOutput#">
		
	</cffunction>
	
	
	<cffunction name="getPages" access="public" returntype="void" output="false">
		  <cfargument name="event" type="any">
		
		<cfdump var="#variables#">
		<cfabort>
	
		
	
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
	
	
</cfcomponent>