<div class="gradient"> 
<cfset qContent = oPage.getContent()>
       	
		<cfloop query="qContent">
		<cfoutput>
  <div class="width100 floatRight">
			<cfif Len(art_title)><h2>#art_title#</h2></cfif>
			<cfif Len(art_description)><h3>#ParagraphFormat(art_description)#</h3></cfif>
			<p>#ParagraphFormat(art_content)#</p>
		<blockquote>
          <p>
            This is a default blockquote with author information included.  If you look at the source, you can see the author info gets its style from a combination of generic classes.
			
			<span class="block small grey"><a href="http://fullahead.org/contact.html" title="Contact snop">snop</a> on March 19th, 2006</span>
          
			</p>
        </blockquote>
		
  </div>
</cfoutput>
		</cfloop>
</div>

