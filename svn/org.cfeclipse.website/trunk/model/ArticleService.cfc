<cfcomponent output="false">
	<cfset variables.dsn = "">
	<cfset variables.BeanFactory = "">
	
<!---START: getter and setter for variables.dsn --->
<cffunction name="getDsn" output="false" returntype="string">
	<cfreturn variables.dsn>
</cffunction>

<cffunction name="setDsn" output="false" returntype="void">
	<cfargument name="dsn" type="string">
	<cfset variables.dsn = arguments.dsn>
</cffunction>


<cffunction name="setBeanFactory" access="public" output="false" returntype="void" hint="I set a BeanFactory (Spring-interfaced IoC container) to inject into all created objects)." >
	<cfargument name="beanFactory" type="coldspring.beans.beanFactory" _type="coldspring.beans.beanFactory" required="true" />
		 <cfset variables.BeanFactory = arguments.beanFactory />
</cffunction>
<!--- END: getter and setter for variables.dsn --->


	<cffunction name="getArticles" access="public" returntype="query" hint="returns an array of contnent (to loop thorugh)">
		<cfargument name="pageid" required="false">
		<cfargument name="type" default="" hint="content type to filter by">
		<cfargument name="orderByField" default="">
		<cfargument name="orderByOrder" default="ASC">
		<cfargument name="limit" default="">
		<cfargument name="random" default="false">
		<cfargument name="rss" default="">
		
		<cfset var r_aContent = ArrayNew(1)>
		<cfset var qryContentQuery = 0>
	 	
	 	
	 		
		
	 	<cfquery name="qryContentQuery" datasource="#variables.dsn#" result="qNews">
		 	SELECT     cms_article.* , cms_user.username, cms_user.DisplayName, cms_user.email, cms_page.pagename
			FROM         cms_article 
			
			
			INNER JOIN
                      cms_article_type ON cms_article.art_type_id = cms_article_type.type_id
		 	INNER JOIN 
		 			cms_user ON cms_article.userid = cms_user.userid
		 	INNER JOIN
		 			cms_page ON cms_article.art_page_id = cms_page.pageid
		 	WHERE cms_article.bPublished = 1
		 	
			<!--- AND dtDisplay <= <cfqueryparam cfsqltype="cf_sql_timestamp" value="#Now()#"> --->
			<cfif Len(arguments.pageid)>
			AND cms_article.art_page_id = <cfqueryparam cfsqltype="cf_sql_numeric" value="#arguments.pageid#">
			</cfif>
			
			<cfif Len(arguments.type)>
			AND cms_article_type.type_name = <cfqueryparam cfsqltype="cf_sql_varchar" value="#arguments.type#">
			</cfif>
			
			<cfif Len(arguments.rss)>
			AND cms_article.brss = <cfqueryparam cfsqltype="cf_sql_integer" value="#arguments.rss#">
			
			</cfif>
			
			<cfif Len(arguments.orderByField)>
			ORDER BY #arguments.orderByField# #arguments.orderByOrder#
			<cfelseif Len(arguments.random)>
			ORDER BY  RAND()
			</cfif>
			
			<cfif Len(arguments.limit)>
			LIMIT #arguments.limit#
			</cfif>
		</cfquery>
	 	
		
		<cfreturn qryContentQuery />
	</cffunction>





</cfcomponent>