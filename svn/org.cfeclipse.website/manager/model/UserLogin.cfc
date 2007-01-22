<cfcomponent hint="This is a login instance of a user" output="false">
	<cfset variables.username = "">
	<cfset variables.loggedin = false>
	<cfset variables.admin = false>

<!---START: getter and setter for variables.username --->
<cffunction name="getusername" output="false" returntype="string">
	<cfreturn variables.username>
</cffunction>

<cffunction name="setusername" output="false" returntype="void">
	<cfargument name="username" type="string">
	<cfset variables.username = arguments.username>
</cffunction>
<!--- END: getter and setter for variables.username --->


<!---START: getter and setter for variables.loggedin --->
<cffunction name="isloggedin" output="false" returntype="boolean">
	<cfreturn variables.loggedin>
</cffunction>

<cffunction name="setloggedin" output="false" returntype="void">
	<cfargument name="loggedin" type="boolean">
	<cfset variables.loggedin = arguments.loggedin>
</cffunction>
<!--- END: getter and setter for variables.loggedin --->

<!---START: getter and setter for variables.admin --->
<cffunction name="isAdmin" output="false" returntype="boolean">
	<cfreturn variables.admin>
</cffunction>

<cffunction name="setIsAdmin" output="false" returntype="void">
	<cfargument name="admin" type="boolean">
	<cfset variables.admin = arguments.admin>
</cffunction>
<!--- END: getter and setter for variables.admin --->
</cfcomponent>