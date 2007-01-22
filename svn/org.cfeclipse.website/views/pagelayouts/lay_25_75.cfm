
	<div class="width25 floatLeft">
        <cfset aChildren = oPage.getChildPages()>
		<ul>
			<cfloop from="1" to="#ArrayLen(aChildren)#" index="p">
			<cfoutput><li><a href="#ViewState.getValue('myself')#page&page=#aChildren[p]#">#aChildren[p]#</a></li></cfoutput>
			 </cfloop>	
		</ul>
		  
		
      </div>
	

      <div class="width75 floatRight">
		<cfset qContent = oPage.getContent('feature')>
       	
		<cfloop query="qContent">
		<cfoutput>
		<p>
			<h2>#art_title#</h2>
         #art_content#
        </p>
		</cfoutput>
		</cfloop>
      </div>
