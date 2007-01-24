
	<!--- <div class="width25 floatLeft">
        <cfset aChildren = oPage.getChildPages()>
		<ul>
			<cfloop from="1" to="#ArrayLen(aChildren)#" index="p">
			<cfoutput><li><a href="#ViewState.getValue('myself')#page&page=#aChildren[p]#">#aChildren[p]#</a></li></cfoutput>
			 </cfloop>	
		</ul>
		  
		
      </div> --->
	<cfset qTestimonials = viewstate.getValue('qryTestimonials', QueryNew('null'))>
	<cfset midpoint = Ceiling(qTestimonials.recordcount/2)>



	<div class="width50 floatLeft">
		
		<!--- loop through half of them --->
		<cfloop query="qTestimonials"  startrow="1" endrow="#midpoint#">
		<cfoutput>
		<H3>#tes_person#</H3>	
		<cfif Len(tes_jobtitle) OR Len(tes_company)>
		<h4><cfif Len(tes_jobtitle)>#tes_jobtitle#</cfif> <cfif Len(tes_company)>#tes_company#</cfif> </h4>	
		</cfif>
		<cfif Len(tes_url)><h5><a href="#tes_url#" target="_blank">#tes_url#</a></h5></cfif>
		<blockquote><p>#ParagraphFormat(Trim(tes_text))#</p></blockquote>
		</cfoutput>
		</cfloop>
      </div>

      <div class="width50 floatRight">
		<cfloop query="qTestimonials"  startrow="#midpoint+1#" endrow="#qTestimonials.recordcount#">
		<cfoutput>
		<H3>#tes_person#</H3>
		<h4><cfif Len(tes_jobtitle)>#tes_jobtitle#</cfif> <cfif Len(tes_company)>#tes_company#</cfif> </h4>	
		<cfif Len(tes_url)><h5>#tes_url#</h5></cfif>
        <blockquote><p>#ParagraphFormat(Trim(tes_text))#</p></blockquote>
		</cfoutput>
		</cfloop>
      </div>
<!--- <cfdump var="#qTestimonials#"> --->
      <!--- <div class="width75 floatRight">
		
		TESTIMONIALS!
		<cfset qContent = oPage.getContent('feature')>
       	
		<cfloop query="qContent">
		<cfoutput>
		<p>
			<h2>#art_title#</h2>
         #art_content#
        </p>
		</cfoutput>
		</cfloop>
      </div> --->
