<h3>Oops!</h3>

<cfset exception = viewstate.getValue("exception") />

<cfoutput>
<table>
	<tr>
		<td valign="top"><b>Message</b></td>
		<td valign="top">#exception.message#</td>
	</tr>
	<tr>
		<td valign="top"><b>Detail</b></td>
		<td valign="top">#exception.detail#</td>
	</tr>
	<tr>
		<td valign="top"><b>Extended Info</b></td>
		<td valign="top">#exception.ExtendedInfo#</td>
	</tr>
	<tr>
		<td valign="top"><b>Tag Context</b></td>
		<td valign="top">
			<cfset tagCtxArr = exception.TagContext />
			<cfloop index="i" from="1" to="#ArrayLen(tagCtxArr)#">
				<cfset tagCtx = tagCtxArr[i] />
				#tagCtx['template']# (#tagCtx['line']#)<br>
			</cfloop>
		</td>
	</tr>
</table>
</cfoutput>