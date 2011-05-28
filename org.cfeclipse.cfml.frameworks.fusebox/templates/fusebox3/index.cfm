<!--- include the core FuseBox  --->
<cflock type="READONLY" name="#server.coldfusion.productVersion#" timeout="10">
	<cfset variables.fuseboxVersion=Replace(Replace(ListDeleteAt(server.coldfusion.productVersion,4),",","","all")," ","","all")>
	<cfset variables.fuseboxOSName=server.os.name>
</cflock>

<cfif variables.fuseboxVersion lte 450>
	<cfinclude template="fbx_fusebox30_CF40.cfm">
<cfelseif variables.fuseboxVersion lt 500>
	<cfif variables.fuseboxOSName contains "Windows">
		<cfinclude template="fbx_fusebox30_CF45.cfm">
	<cfelse>
		<cfinclude template="fbx_fusebox30_CF45_nix.cfm">
	</cfif>
<cfelse>
	<cfif variables.fuseboxOSName contains "Windows">
		<cfinclude template="fbx_fusebox30_CF50.cfm">
	<cfelse>
		<cfinclude template="fbx_fusebox30_CF50_nix.cfm">
	</cfif>
</cfif>

<!--- This file is not a part of the Fusebox Spec. It is provided merely as a courtesy. Remove all the code on this page except for the <cfinclude> line for the platform and CF version you are running. --->