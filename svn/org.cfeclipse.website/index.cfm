<cfsilent>
<!---
	If you want to implement a sub-application configuration,
	first, change	the following to a unique name
	for this sub-application:

	<cfset ModelGlue_APP_KEY = "mySubapplication" />

	Then, you'll need to include the parent application's
	application.cfm file with something like one of the
	following lines of code:

	<cfinclude template="/application.cfm" />
	<cfinclude template="../application.cfm" />
	<cfinclude template="/whereever/my/parent/app/is/application.cfm" />
--->

<!---
  If your path to ColdSpring.xml is custom, change it here.  Otherwise,
  it will default to Config/ColdSpring.xml
	<cfset ModelGlue_CONFIG_PATH = expandPath(".") & "/config/ColdSpring.xml" />
--->

<!---
  If your path to /modelglue/unity/config/Configuration.xml is custom, change it here.  Otherwise,
  it will default to Config/ColdSpring.xml
	<cfset ModelGlue_CORE_CONFIG_PATH = expandPath("/ModelGlue/unity/config/Configuration.xml") />
--->



<!--- If your path to ModelGlue.cfm is different, you'll need to change this line. --->
</cfsilent><cfinclude template="/ModelGlue/unity/ModelGlue.cfm" />