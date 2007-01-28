<cfcomponent displayname="Page" output="false">
	<cfset variables.pageid = 0>
	<cfset variables.pagename = "">
	<cfset variables.parentpageid = 0>
	<cfset variables.layout = "">
	<cfset variables.dsn = "">
	<cfset variables.description = "">
	
<!---START: getter and setter for variables.dsn --->
<cffunction name="getdsn" output="false" returntype="string">
	<cfreturn variables.dsn>
</cffunction>

<cffunction name="setdsn" output="false" returntype="void">
	<cfargument name="dsn" type="string">
	<cfset variables.dsn = arguments.dsn>
</cffunction>
<!--- END: getter and setter for variables.dsn --->


<!---START: getter and setter for variables.pageid --->
<cffunction name="getpageid" output="false" returntype="numeric">
	<cfreturn variables.pageid>
</cffunction>

<cffunction name="setpageid" output="false" returntype="void">
	<cfargument name="pageid" type="numeric">
	<cfset variables.pageid = arguments.pageid>
</cffunction>
<!--- END: getter and setter for variables.pageid --->

<!---START: getter and setter for variables.pagename --->
<cffunction name="getpagename" output="false" returntype="string">
	<cfreturn variables.pagename>
</cffunction>

<cffunction name="setpagename" output="false" returntype="void">
	<cfargument name="pagename" type="string">
	<cfset variables.pagename = arguments.pagename>
</cffunction>
<!--- END: getter and setter for variables.pagename --->
<!---START: getter and setter for variables.parentpageid --->
<cffunction name="getparentpageid" output="false" returntype="numeric">
	<cfreturn variables.parentpageid>
</cffunction>

<cffunction name="setparentpageid" output="false" returntype="void">
	<cfargument name="parentpageid" type="numeric">
	<cfset variables.parentpageid = arguments.parentpageid>
</cffunction>
<!--- END: getter and setter for variables.parentpageid --->

<!---START: getter and setter for variables.layout --->
<cffunction name="getlayout" output="false" returntype="string">
	<cfargument name="defaultLayout" default="lay_25_75">
	
	<cfif Len(variables.layout)>
		<cfreturn variables.layout/>
	</cfif>
	
	<cfif NOT ArrayLen(getChildPages())>
		<cfreturn "lay_100">	
	</cfif>
	<!--- also we can do a default --->
	
	
	
	<cfreturn arguments.defaultLayout>
</cffunction>

<cffunction name="setlayout" output="false" returntype="void">
	<cfargument name="layout" type="string">
	<cfset variables.layout = arguments.layout>
</cffunction>
<!--- END: getter and setter for variables.layout --->

<!---START: getter and setter for variables.description --->
<cffunction name="getdescription" output="false" returntype="string">
	<cfreturn variables.description />
</cffunction>

<cffunction name="setdescription" output="false" returntype="void">
	<cfargument name="description" type="string">
	<cfset variables.description = arguments.description>
</cffunction>
<!--- END: getter and setter for variables.description --->

	<cffunction name="getParentPages" access="public" returntype="string" hint="returns a comma delimited list of parent pages, recursing upwards">
			<cfargument name="pagename"  default="#variables.pagename#" hint="the id of the page we want to find its parent from">
			<cfargument name="pagelist" default="" type="string" hint="the list of strings that we are passing in">
			<cfset var q_getParent = 0>
			<cfset var lPages = arguments.pagename>
					<cfquery name="q_getParent" datasource="#variables.dsn#">
					SELECT pageid, pagename, parentpage
					from cms_page
					WHERE pageid = (
					SELECT parentpage FROM cms_page WHERE pagename = <cfqueryparam cfsqltype="cf_sql_varchar" value="#arguments.pagename#">)
					</cfquery>
					<cfif q_getParent.recordcount>
						<cfset lPages = ListPrepend(lPages, getParentPages(q_getParent.pagename, lPages))>
					</cfif>

			<cfreturn lPages>
	</cffunction>

	<cffunction name="getChildPages" access="public" returntype="array" hint="returns a structure of child pages">
		<cfargument name="pagename" type="string" default="#variables.pagename#">
		<cfset var q_getChildren = 0>
		<cfset var aChildPages = ArrayNew(1)>	
			
				<cfquery name="q_getChildren" datasource="#variables.dsn#">
					SELECT pageid, pagename, parentpage, orderid
					from cms_page
					WHERE parentpage = (
						SELECT pageid FROM cms_page WHERE pagename = <cfqueryparam cfsqltype="cf_sql_varchar" value="#arguments.pagename#">
					)
					AND bPublished=1
					ORDER BY orderid
				</cfquery>
				
				<cfif q_getChildren.recordcount>
					<cfloop query="q_getChildren">
						
						<cfset stSubPages = StructNew()>
						<cfset stSubPages.name = q_getChildren.pagename>
						<!---  <cfset stSubPages.children = getChildPages(q_getChildren.pagename)> --->
						 <cfset ArrayAppend(aChildPages, q_getChildren.pagename)>
					
					</cfloop>
				</cfif>
		<cfreturn aChildPages />
	</cffunction>


	<cffunction name="getContent" access="public" returntype="query" hint="returns an array of contnent (to loop thorugh)">
		<cfargument name="type" default="" hint="content type to filter by">
		<cfset var r_aContent = ArrayNew(1)>
		<cfset var qryContentQuery = 0>
	 	
	 	<cfquery name="qryContentQuery" datasource="#variables.dsn#">
		 	SELECT     cms_article.*
			FROM         cms_article 
			
			
			INNER JOIN
                      cms_article_type ON cms_article.art_type_id = cms_article_type.type_id
		 	
		 	WHERE cms_article.bPublished = 1
			<!--- AND dtDisplay <= <cfqueryparam cfsqltype="cf_sql_timestamp" value="#Now()#"> --->
			AND cms_article.art_page_id = <cfqueryparam cfsqltype="cf_sql_numeric" value="#variables.pageid#">
			<cfif Len(content_type)>
			AND cms_article_type.type_name = <cfqueryparam cfsqltype="cf_sql_varchar" value="#content_type#">
			</cfif>

		</cfquery>
	 		
		
		<cfreturn qryContentQuery />
	</cffunction>


</cfcomponent>