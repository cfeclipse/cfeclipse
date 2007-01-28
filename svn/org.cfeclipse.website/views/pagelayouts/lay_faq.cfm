<div class="gradient"> 
<cfset qContent = oPage.getContent()>
		<ul>
		<cfloop query="qContent">
			<cfoutput><li><a href="##link_#art_id#">#art_title#</a></li></cfoutput>
		</cfloop>
		</ul>   
    	
		<cfloop query="qContent">
		<cfoutput>
		<a name="link_#art_id#"></a>
		<blockquote>
			<cfif Len(art_title)><h2>#art_title#</h2></cfif>
			<cfif Len(art_description)><h3>#ParagraphFormat(art_description)#</h3></cfif>
          <p>
			
			
			
			#ParagraphFormat(art_content)#
			
			<span class="block small grey"><a href="mailto:#email#" title="Contact #DisplayName#">#DisplayName#</a> on #DateFormat(dtCreated,"MMMM dd yyyy")#</span>
          
			</p>
        </blockquote>
		
		</cfoutput>
		</cfloop>
</div>

