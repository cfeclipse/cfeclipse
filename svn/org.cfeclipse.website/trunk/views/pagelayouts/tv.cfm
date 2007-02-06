
<cfset qContent = oPage.getContent("", "dtCreated", "DESC")>
       	
		<cfloop query="qContent">
	<cfloop query="qContent">
<div class="gradient" style="width:210px;height:350px; margin:15px;float:left;"> 			
			
		<cfoutput>
			<cfif DateDiff("d",DateFormat(dtCreated,"MMMM dd yyyy"),now()) LT 14>	
			<img src="/assets/img/new_star.gif" align="left">
			</cfif>
			
			
			<cfif Len(art_title)><h3>#art_title#</h3></cfif>
			<cfif Len(art_img)>
				<cfif Len(art_content)><a href="#art_content#" target="viewScreen"><img src="#art_img#"></a>
				<cfelse>
				<img src="#art_img#">
				</cfif>
			</cfif>
			<cfif Len(art_description)><p>#art_description#</p></cfif>
			<cfif Len(art_content)><a href="#art_content#" target="viewScreen">Play &gt;</a></cfif>
          <p>
			<span class="block small"><a href="mailto:#email#" title="Contact #DisplayName#">#DisplayName#</a> on #DateFormat(dtCreated,"MMMM dd yyyy")#</span>
          </p>
		
		</cfoutput>
</div>
		</cfloop>


