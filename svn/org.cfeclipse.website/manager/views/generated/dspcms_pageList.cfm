    
<cfset viewEvent = viewstate.getValue("myself") & viewstate.getValue("xe.view") />
<cfset editEvent = viewstate.getValue("myself") & viewstate.getValue("xe.edit") />
<cfset deleteEvent = viewstate.getValue("myself") & viewstate.getValue("xe.delete") />
<cfset cms_pageQuery = viewstate.getValue("cms_pageQuery") />

    
<cfoutput>
<div id="breadcrumb">Cms_pages / <a href="#editEvent#">Add New Cms_page</a></div>
</cfoutput>
<br />
<table class="list">
<thead>
<tr>
  <cfset displayedColumns = 1 />
  
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>Pagename</th>
    
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>Orderid</th>
    
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>B Published</th>
    
		  <cfset displayedColumns = displayedColumns + 1 />
	 		<th>Layout</th>
    
	<th>&nbsp;</th>
</tr>
</thead>
<tbody>
<cfif not cms_pageQuery.recordcount>
	<tr>
		<cfoutput><td colspan="#displayedColumns#"><em>No Records</em></td></cfoutput>
	</tr>
</cfif>
<cfoutput query="cms_pageQuery">
	<cfset keyString = "&pageid=#urlEncodedFormat(cms_pageQuery.pageid)#" />
	<tr <cfif cms_pageQuery.currentRow mod 2 eq 0>class="even"</cfif>>
    
			 		<td><a href="#viewEvent##keystring#">#htmlEditFormat(pagename)#</a></td>
				
			 		<td><a href="#viewEvent##keystring#">#htmlEditFormat(orderid)#</a></td>
				
			 		<td><a href="#viewEvent##keystring#">#htmlEditFormat(bPublished)#</a></td>
				
			 		<td><a href="#viewEvent##keystring#">#htmlEditFormat(layout)#</a></td>
				
		<td>
			<a href="#editEvent##keystring#">Edit</a>	
			<a href="##" onclick="if (confirm('Are you sure you want to delete this Cms_page?')) { document.location.replace('#deleteEvent##keystring#') }; return false">Delete</a>
		</td>
	</tr>
</cfoutput>
</tbody>
</table>
                 
	
