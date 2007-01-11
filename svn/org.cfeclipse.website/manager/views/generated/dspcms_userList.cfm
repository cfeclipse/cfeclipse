    
<cfset viewEvent = viewstate.getValue("myself") & viewstate.getValue("xe.view") />
<cfset editEvent = viewstate.getValue("myself") & viewstate.getValue("xe.edit") />
<cfset deleteEvent = viewstate.getValue("myself") & viewstate.getValue("xe.delete") />
<cfset cms_userQuery = viewstate.getValue("cms_userQuery") />

    
<cfoutput>
<div id="breadcrumb">Cms_users / <a href="#editEvent#">Add New Cms_user</a></div>
</cfoutput>
<br />
<table class="list">
<thead>
<tr>
  <cfset displayedColumns = 1 />
  
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>Username</th>
    
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>Password</th>
    
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>Display Name</th>
    
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>B Active</th>
    
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>B Admin</th>
    
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>Dt Last Login</th>
    
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>Email</th>
    
	<th>&nbsp;</th>
</tr>
</thead>
<tbody>
<cfif not cms_userQuery.recordcount>
	<tr>
		<cfoutput><td colspan="#displayedColumns#"><em>No Records</em></td></cfoutput>
	</tr>
</cfif>
<cfoutput query="cms_userQuery">
	<cfset keyString = "&userid=#urlEncodedFormat(cms_userQuery.userid)#" />
	<tr <cfif cms_userQuery.currentRow mod 2 eq 0>class="even"</cfif>>
    
			 		<td><a href="#viewEvent##keystring#">#htmlEditFormat(username)#</a></td>
				
			 		<td><a href="#viewEvent##keystring#">#htmlEditFormat(password)#</a></td>
				
			 		<td><a href="#viewEvent##keystring#">#htmlEditFormat(DisplayName)#</a></td>
				
			 		<td><a href="#viewEvent##keystring#">#htmlEditFormat(bActive)#</a></td>
				
			 		<td><a href="#viewEvent##keystring#">#htmlEditFormat(bAdmin)#</a></td>
				
			 		<td><a href="#viewEvent##keystring#">#dateFormat(dtLastLogin, "m/d/yyyy")# #timeFormat(dtLastLogin, "h:mm TT")#</a></td>
				
			 		<td><a href="#viewEvent##keystring#">#htmlEditFormat(email)#</a></td>
				
		<td>
			<a href="#editEvent##keystring#">Edit</a>	
			<a href="##" onclick="if (confirm('Are you sure you want to delete this Cms_user?')) { document.location.replace('#deleteEvent##keystring#') }; return false">Delete</a>
		</td>
	</tr>
</cfoutput>
</tbody>
</table>
                 
	
