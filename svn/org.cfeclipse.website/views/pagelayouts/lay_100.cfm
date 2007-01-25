
<div class="gradient"> 

  <div class="width100 floatRight">
		<cfset qContent = oPage.getContent()>
       	
		<cfloop query="qContent">
		<cfoutput>
		<p>
			<h2>#art_title#</h2>
         #art_content#
        </p>
		</cfoutput>
		</cfloop>
  </div>
</div>
