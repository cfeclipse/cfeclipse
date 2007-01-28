<div class="gradient"> 
<cfset qContent = oPage.getContent()>
       	
		<cfloop query="qContent">
		<cfoutput>
  <div class="width100 floatRight">
		<cfif Len(art_img)>
			
		<cfif Right(art_img,3) EQ "swf">
			<div style="float:right;margin:5px;">
			<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab##version=8,0,0,0" width="120" height="120" id="feature#art_id#" align="left" hspace="10">
			<param name="allowScriptAccess" value="sameDomain" />
			<param name="movie" value="#art_img#" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="##ffffff" />
			<embed src="#art_img#" quality="high" bgcolor="##ffffff" width="120" height="120" name="feature#art_id#" align="left" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" hspace="10"/>
			</object>
			</div>	
		<cfelse>
			<img src="#art_img#" align="left" border="0" hspace="10">
		</cfif>
			
		
		
		</cfif>
			<cfif Len(art_title)><h2>#art_title#</h2></cfif>
			<cfif Len(art_description)><h3>#art_description#</h3></cfif>
			<p>#art_content#</p>
		
  </div>
</cfoutput>
		</cfloop>
</div>


					<cffunction name="getname" output="false" access="public" returntype="string">
							<cfreturn variables.name>
						</cffunction>
					
						<cffunction name="setname" output="false" access="public" returntype="void">
							<cfargument name="name" required="true" type="string}">
							<cfset variables.name = arguments.name>
						</cffunction>