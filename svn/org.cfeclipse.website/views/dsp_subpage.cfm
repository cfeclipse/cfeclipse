<div class="gradient">
<cfset oPage = ViewState.getValue('pageObject')>
<cfset crumbtail = oPage.getParentPages()>
<cfset layout = oPage.getLayout()>
<cfsavecontent variable="visCrumbtail">
<cfoutput>	
	<cfset counter = 0>
<cfloop list="#crumbtail#" index="crumb">
	<cfif counter NEQ 0>
		&gt;
	</cfif>
	
	<cfif crumb neq oPage.getPageName()>
		<cfif counter EQ 0> <!--- we are in the homepage --->
		<a href="#CGI.SCRIPT_NAME#">#crumb#</a>
		<cfelse>
		<a href="#ViewState.getValue('myself')#page&page=#crumb#">#crumb#</a>
		</cfif>
	<cfelse>
#crumb#
	</cfif>
	<cfset counter = counter + 1>
</cfloop>
</cfoutput>
</cfsavecontent>

<h2 class="clear"><cfoutput>#visCrumbtail#</cfoutput></h2>
<p>
	<cfoutput>#PAragraphFormat(oPage.getDescription())#</cfoutput>
</p>
<cfinclude template="pagelayouts/#oPage.getLayout('lay_25_75')#.cfm">
</div>