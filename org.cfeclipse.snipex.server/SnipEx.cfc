<cfcomponent output="false">
	 
	<cfif structKeyExists(URL, "libid")>
		<cfset URL.method = "getLibrary" />
	<cfelseif structKeyExists(URL, "snipid")>
		<cfset URL.method = "getSnippet" />
	<cfelseif NOT structKeyExists(URL, "method")>
		<cfset URL.method = "getSnipEx" />
	
	</cfif>
	 
	
	<cffunction name="getSnipEx" output="yes" access="remote">
		<cfset var content = "" />
		<cfset var library = queryLibrary( application.rootID ) />
		<cfset var libs = queryLibraries( application.rootID ) />
		<cfset var snips = querySnippets( application.rootID ) />
		
		<cfxml variable="content">
			<cfoutput>
			<?xml version="1.0" encoding="utf-8"?>
			<snipex name="#library.name#" id="#library.libid#" createdAt="#dateFormat(library.created, "mm-dd-yyyy")#"  updatedAt="#dateFormat(library.updated, "mm-dd-yyyy")#">
				<libraries>
			        <cfloop query="libs">  
						<library id="#libs.libid#" name="#libs.name#" createdAt="#dateFormat(libs.created, "mm-dd-yyyy")#"  updatedAt="#dateFormat(libs.updated, "mm-dd-yyyy")#">
							<![CDATA[#libs.description#]]>
						</library>
					</cfloop>
				</libraries>
				
				<snippets>
					<cfloop query="snips">
				 	<snippet id="#snips.snipid#" template="#snips.template#">
			               <name>#snips.name#</name>
			               <help><![CDATA[#snips.help#]]></help>
			               <description><![CDATA[#snips.description#]]></description>
			               <starttext><![CDATA[#snips.starttext#]]></starttext>
			               <endtext><![CDATA[#snips.endtext#]]></endtext>
			               <author>#snips.author#</author>
			               <platforms>#snips.platforms#</platforms>
			               <created>#dateFormat(snips.created, "mm-dd-yyyy")#</created>
			               <lastupdated>#dateFormat(snips.updated, "mm-dd-yyyy")#</lastupdated>
			          </snippet>
					</cfloop>
				</snippets>
			</snipex>
			</cfoutput>
		</cfxml>
		
		<cfcontent type="text/xml" reset="true"><cfoutput>#content#</cfoutput>
	</cffunction>
	
	<cffunction name="getLibrary" output="yes" access="remote">
		<cfargument name="libid" />
		
		<cfset var content = "" />
		<cfset var library = queryLibrary( arguments.libid ) />
		<cfset var libs = queryLibraries( arguments.libid ) />
		<cfset var snips = querySnippets( arguments.libid ) />
		
		<cfxml variable="content">
			<cfoutput>
			<?xml version="1.0" encoding="utf-8"?>
			<library name="#library.name#" id="#library.libid#" createdAt="#dateFormat(library.created, "mm-dd-yyyy")#"  updatedAt="#dateFormat(library.updated, "mm-dd-yyyy")#">
				<libraries>
			        <cfloop query="libs">  
						<library id="#libs.libid#" name="#libs.name#" createdAt="#dateFormat(libs.created, "mm-dd-yyyy")#"  updatedAt="#dateFormat(libs.updated, "mm-dd-yyyy")#">
							<![CDATA[#libs.description#]]>
						</library>
					</cfloop>
				</libraries>
				
				<snippets>
					<cfloop query="snips">
				 	<snippet id="#snips.snipid#" template="#snips.template#">
			               <name>#snips.name#</name>
			               <help><![CDATA[#snips.help#]]></help>
			               <description><![CDATA[#snips.description#]]></description>
			               <starttext><![CDATA[#snips.starttext#]]></starttext>
			               <endtext><![CDATA[#snips.endtext#]]></endtext>
			               <author>#snips.author#</author>
			               <platforms>#snips.platforms#</platforms>
			               <created>#dateFormat(snips.created, "mm-dd-yyyy")#</created>
			               <lastupdated>#dateFormat(snips.updated, "mm-dd-yyyy")#</lastupdated>
			          </snippet>
					</cfloop>
				</snippets>
			</library>
			</cfoutput>
		</cfxml>
		
		<cfcontent type="text/xml" reset="true"><cfoutput>#content#</cfoutput>
	</cffunction>
	
	<cffunction name="getSnippet" output="yes" access="remote">
		<cfargument name="snipid" />
		
		<cfset var content = "" />
		<cfset var snippet = querySnippet( arguments.snipid ) />
		
		<cfxml variable="content">
			<cfoutput>
			 	<snippet id="#snippet.snipid#" template="#snippet.template#">
		               <name>#snippet.name#</name>
		               <help><![CDATA[#snippet.help#]]></help>
		               <description><![CDATA[#snippet.description#]]></description>
		               <starttext><![CDATA[#snippet.starttext#]]></starttext>
		               <endtext><![CDATA[#snippet.endtext#]]></endtext>
		               <author>#snippet.author#</author>
		               <platforms>#snippet.platforms#</platforms>
		               <created>#dateFormat(snippet.created, "mm-dd-yyyy")#</created>
		               <lastupdated>#dateFormat(snippet.updated, "mm-dd-yyyy")#</lastupdated>
		          </snippet>
			</cfoutput>
		</cfxml>
		
		<cfcontent type="text/xml" reset="true"><cfoutput>#content#</cfoutput>
	</cffunction>
	
	
	
	<!--- SQL Methods --->
	<cffunction name="queryLibrary" output="no" access="private">
		<cfargument name="libid" />
		
		<cfset var qResults = "" />

		<cfquery datasource="#application.dsn#" name="qResults">
			SELECT  libid, name, description, parent, created, updated 
			FROM lib 
			WHERE libid = <cfqueryparam value="#arguments.libid#" />
		</cfquery>
		
		<cfreturn qResults />
	</cffunction>

	<cffunction name="querySnippet" output="no" access="private">
		<cfargument name="snipid" />
		
		<cfset var qResults = "" />
		
		<cfquery datasource="#application.dsn#" name="qResults">
			SELECT  snipid, name, parent, help, description, starttext, endtext, author, platforms, template, created, updated
			FROM snip 
			WHERE snipid = <cfqueryparam value="#arguments.snipid#" />
		</cfquery>
		
		<cfreturn qResults />
	</cffunction>
	
	<cffunction name="queryLibraries" output="no" access="private">
		<cfargument name="libid" />
		
		<cfset var qResults = "" />
		
		<cfquery datasource="#application.dsn#" name="qResults">
			SELECT libid, name, description, parent, created, updated  
			FROM lib 
			WHERE parent = <cfqueryparam value="#arguments.libid#" />
		</cfquery>
		
		<cfreturn qResults />
	</cffunction>
	
	<cffunction name="querySnippets" output="no" access="private">
		<cfargument name="libid" />
		
		<cfset var qResults = "" />
		
		<cfquery datasource="#application.dsn#" name="qResults">
			SELECT snipid, name, parent, help, description, starttext, endtext, author, platforms, template, created, updated
			FROM snip 
			WHERE parent = <cfqueryparam value="#arguments.libid#" />
			AND published = 1
		</cfquery>
		
		<cfreturn qResults />
	</cffunction>
	
	
	<cffunction name="submitSnippet" output="true" access="remote">
		
		<cftry>
		
			<cfquery datasource="#application.dsn#" name="qInsert">
				INSERT INTO snip(name, description, starttext, endtext, parent,author, useremail, help, published, created, updated)
				VALUES(
					<cfqueryparam value = "#arguments.snippetname#" CFSQLType = "cf_sql_varchar">,
					<cfqueryparam value = "#arguments.snippetdescription#" CFSQLType = "cf_sql_varchar">,
					<cfqueryparam value = "#arguments.snippetstartblock#" CFSQLType = "cf_sql_varchar">,
					<cfqueryparam value = "#arguments.snippetendblock#" CFSQLType = "cf_sql_varchar">,
					<cfqueryparam value = "#arguments.libraryid#" CFSQLType = "cf_sql_integer">,
					<cfqueryparam value = "#arguments.username#" CFSQLType = "cf_sql_varchar">,
					<cfqueryparam value = "#arguments.useremail#" CFSQLType = "cf_sql_varchar">,
					'',
					
					<cfif application.moderate>
						<cfqueryparam value = "0" CFSQLType = "cf_sql_integer">,
					<cfelse>
						<cfqueryparam value = "1" CFSQLType = "cf_sql_integer">	,
					</cfif>
					
					<cfqueryparam value="#createodbcdatetime(Now())#" cfsqltype="cf_sql_timestamp">,
					<cfqueryparam value="#createodbcdatetime(Now())#" cfsqltype="cf_sql_timestamp">
				)
				
			</cfquery>
			<cfoutput>Snippet has been submitted</cfoutput>
		<cfcatch>
					<cfoutput>#CFCATCH.Message#</cfoutput>
					<cfdump var="#CFCATCH#">		
		</cfcatch>
		</cftry>
		

	</cffunction>
	
	<cffunction name="canSubmit" access="remote" output="true">
		<cfparam name="application.canSubmit" default="true">
		
		<cfoutput>#application.canSubmit#</cfoutput>
	</cffunction>
	
	<cffunction name="submitApplication"  access="remote" output="true">
	
	
	
	
	</cffunction>
	
	
</cfcomponent>
