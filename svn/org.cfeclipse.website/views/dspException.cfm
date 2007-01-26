<h1>DOH!</h1>

<cfset exception = viewstate.getValue("exception") />

<h2>Something has gone amiss! Whilst we sort it out, you can see for yourself what the error was:</h2>

<p>
	The server says: <cfoutput> <em>#exception.detail#</em></cfoutput>


</p>

<p>

	Please email <a href="mailto:mark.drew@gmail.com?subject=help! I spotted an error on the cfeclipse website!"> the administrator </a>to let them know of the error!
</p>

<!--- <cfoutput>
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
</cfoutput> --->