<cfif thisTag.executionMode eq "start">
	<cfsilent>
		<cfparam name="attributes.validation" type="struct" />
		<cfparam name="attributes.property" type="string" />
	</cfsilent>
	<cfif structKeyExists(attributes.validation, attributes.property)
				and isArray(attributes.validation[attributes.property])>
		<cfset errs = attributes.validation[attributes.property] />
		<div class="error">
		<ul>
		<cfoutput>
		<cfloop from="1" to="#arrayLen(errs)#" index="i">
			<li>#errs[i]#</li>
		</cfloop>
		</cfoutput>
		</ul>
		</div>
	</cfif>
</cfif>