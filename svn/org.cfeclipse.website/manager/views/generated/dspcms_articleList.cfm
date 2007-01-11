    
<cfset viewEvent = viewstate.getValue("myself") & viewstate.getValue("xe.view") />
<cfset editEvent = viewstate.getValue("myself") & viewstate.getValue("xe.edit") />
<cfset deleteEvent = viewstate.getValue("myself") & viewstate.getValue("xe.delete") />
<cfset cms_articleQuery = viewstate.getValue("cms_articleQuery") />

    
<cfoutput>
<div id="breadcrumb">Cms_articles / <a href="#editEvent#">Add New Cms_article</a></div>
</cfoutput>
<br />
<table class="list">
<thead>
<tr>
  <cfset displayedColumns = 1 />
  
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>Art_title</th>
    
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>Art_img</th>
    
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>Dtcreated</th>
    
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>Dt Display</th>
    
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>B Published</th>
    
	<th>&nbsp;</th>
</tr>
</thead>
<tbody>
<cfif not cms_articleQuery.recordcount>
	<tr>
		<cfoutput><td colspan="#displayedColumns#"><em>No Records</em></td></cfoutput>
	</tr>
</cfif>
<cfoutput query="cms_articleQuery">
	<cfset keyString = "&art_id=#urlEncodedFormat(cms_articleQuery.art_id)#" />
	<tr <cfif cms_articleQuery.currentRow mod 2 eq 0>class="even"</cfif>>
    
			 		<td><a href="#viewEvent##keystring#">#htmlEditFormat(art_title)#</a></td>
				
			 		<td><a href="#viewEvent##keystring#">#htmlEditFormat(art_img)#</a></td>
				
			 		<td><a href="#viewEvent##keystring#">#dateFormat(dtcreated, "m/d/yyyy")# #timeFormat(dtcreated, "h:mm TT")#</a></td>
				
			 		<td><a href="#viewEvent##keystring#">#dateFormat(dtDisplay, "m/d/yyyy")# #timeFormat(dtDisplay, "h:mm TT")#</a></td>
				
			 		<td><a href="#viewEvent##keystring#">#htmlEditFormat(bPublished)#</a></td>
				
		<td>
			<a href="#editEvent##keystring#">Edit</a>	
			<a href="##" onclick="if (confirm('Are you sure you want to delete this Cms_article?')) { document.location.replace('#deleteEvent##keystring#') }; return false">Delete</a>
		</td>
	</tr>
</cfoutput>
</tbody>
</table>
                 
	
