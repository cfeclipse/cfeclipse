
<cfset qContent = oPage.getContent("", "dtCreated", "DESC")>
       	
		<cfloop query="qContent">
<div class="gradient"> 			
			
		<cfoutput>
			
			<cfif Len(art_title)><h2>#art_title#</h2></cfif>
			<cfif Len(art_description)><h3>#ParagraphFormat(art_description)#</h3></cfif>
          <p>
			
			
			
			#ParagraphFormat(art_content)#
			
			<span class="block small"><a href="mailto:#email#" title="Contact #DisplayName#">#DisplayName#</a> on #DateFormat(dtCreated,"MMMM dd yyyy")#</span>
          
			</p>
		
		</cfoutput>
</div>
		</cfloop>


