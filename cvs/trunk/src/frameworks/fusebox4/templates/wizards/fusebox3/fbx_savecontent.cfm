<!---
<fusedoc fuse="FBX_SaveContent.cfm">
	<responsibilities>
		I am a custom tag version of cfsavecontent's functionality. This tag was originally released as cf_bodycontent by Steve Nelson. This tag is required if you are using a CF server version below 5.0. 
	</responsibilities>	
	<io>
		<in>
			<string name="variable" scope="attributes" default="fusebox.layout" />
		</in>
		<out>
			<string name="~attributes.variable~" scope="caller" />
		</out>
	</io>
</fusedoc>
--->
<cfif not thistag.HasEndTag>
	<cfset thistag.generatedcontent="">
	You must have an end Tag
	<cfabort>
</cfif>
<cfparam name="attributes.variable" default="fusebox.layout">
<cfif thistag.executionmode is "end">
	<cfset "caller.#attributes.variable#"=thistag.generatedcontent>
	<cfset thistag.generatedcontent="">
</cfif>