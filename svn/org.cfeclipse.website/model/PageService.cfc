<cfcomponent displayname="PageService" output="false">

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
	
	
	<cffunction name="getPage" returntype="any" output="false">
		<cfargument name="pagename">
		<cfset var r_oPage = "">
			
				<cfquery name="q_GetPage" datasource="#variables.dsn#">
					SELECT pageid, pagename, parentpage, layout, pagedescription
					from cms_page
					WHERE pagename = <cfqueryparam cfsqltype="cf_sql_varchar" value="#arguments.pagename#">
				</cfquery>
			
				<cfif q_GetPage.recordcount>
					<cfset oPage = variables.BeanFactory.getBean("Page")>
					<cfset oPage.setPageID(q_getPage.pageid)>
					<cfset oPage.setPageName(q_getPage.pagename)>
					<cfset oPage.setparentpageid(q_getPage.parentpage)>
					<cfset oPage.setLayout(q_getPage.layout)>
					<cfset oPage.setDescription(q_getPage.pagedescription)>
					<cfset r_oPage = oPage>
				</cfif>
	
					
	
		<cfreturn r_oPage />
	</cffunction>
	
	
	
	
	
	
</cfcomponent>