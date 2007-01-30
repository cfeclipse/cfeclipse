    
<cfset viewEvent = viewstate.getValue("myself") & viewstate.getValue("xe.view") />
<cfset editEvent = viewstate.getValue("myself") & viewstate.getValue("xe.edit") />
<cfset deleteEvent = viewstate.getValue("myself") & viewstate.getValue("xe.delete") />
<cfset cms_article_typeQuery = viewstate.getValue("cms_article_typeQuery") />

    
<cfoutput>
<div id="breadcrumb">Cms_article_types / <a href="#editEvent#">Add New Cms_article_type</a></div>
</cfoutput>
<br />
<table class="list">
<thead>
<tr>
  <cfset displayedColumns = 1 />
  
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>Type_name</th>
    
	<th>&nbsp;</th>
</tr>
</thead>
<tbody>
<cfif not cms_article_typeQuery.recordcount>
	<tr>
		<cfoutput><td colspan="#displayedColumns#"><em>No Records</em></td></cfoutput>
	</tr>
</cfif>
<cfoutput query="cms_article_typeQuery">
	<cfset keyString = "&type_id=#urlEncodedFormat(cms_article_typeQuery.type_id)#" />
	<tr <cfif cms_article_typeQuery.currentRow mod 2 eq 0>class="even"</cfif>>
    
			 		<td><a href="#viewEvent##keystring#">#htmlEditFormat(type_name)#</a></td>
				
		<td>
			<a href="#editEvent##keystring#">Edit</a>	
			<a href="##" onclick="if (confirm('Are you sure you want to delete this Cms_article_type?')) { document.location.replace('#deleteEvent##keystring#') }; return false">Delete</a>
		</td>
	</tr>
</cfoutput>
</tbody>
</table>
                 
	
