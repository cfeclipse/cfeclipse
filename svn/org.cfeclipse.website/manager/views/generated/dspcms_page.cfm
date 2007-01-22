    
<cfset listEvent = viewstate.getValue("myself") & viewstate.getValue("xe.list")  />
<cfset commitEvent = viewstate.getValue("myself") & viewstate.getValue("xe.commit") & "&cms_pageId=" & urlEncodedFormat(viewstate.getValue("cms_pageId")) />
<cfset cms_pageRecord = viewstate.getValue("cms_pageRecord") />
<cfset validation = viewstate.getValue("cms_pageValidation", structNew()) />

<cfoutput>
<div id="breadcrumb"><a href="#listEvent#">Cms_pages</a> / View Cms_page</div>
</cfoutput>
<br />
  
<cfform class="edit">
    
<fieldset>
    

        <div class="formfield">
          <cfoutput>
	        <label for="pagename"><b>Pagename:</b></label>
	        <span class="input">#cms_pageRecord.getpagename()#</span>
	        </cfoutput>
        </div>
    
        <div class="formfield">
          <cfoutput>
	        <label for="parent"><b>Parent:</b>
	        </label>
					<cfset targetObject = cms_pageRecord.getparent() />
	        <div>
	       		#targetObject.getpagename()#
	        </div>
	        </cfoutput>
        </div>
      
        <div class="formfield">
          <cfoutput>
	        <label for="pagedescription"><b>Pagedescription:</b></label>
	        <span class="input">#cms_pageRecord.getpagedescription()#</span>
	        </cfoutput>
        </div>
    
        <div class="formfield">
          <cfoutput>
	        <label for="orderid"><b>Orderid:</b></label>
	        <span class="input">#cms_pageRecord.getorderid()#</span>
	        </cfoutput>
        </div>
    
        <div class="formfield">
          <cfoutput>
	        <label for="bPublished"><b>B Published:</b></label>
	        <span class="input">#cms_pageRecord.getbPublished()#</span>
	        </cfoutput>
        </div>
    
        <div class="formfield">
          <cfoutput>
	        <label for="layout"><b>Layout:</b></label>
	        <span class="input">#cms_pageRecord.getlayout()#</span>
	        </cfoutput>
        </div>
    
        <div class="formfield">
        	<label><b>pagename(s):</b></label>
          <cfset selectedQuery = cms_pageRecord.getchildpagesIterator().getQuery() />

	        <div class="formfieldinputstack">
          <cfoutput query="selectedQuery">
            #selectedQuery.pagename#<br />
          </cfoutput>
	        </div>
        </div>
          
      
        <div class="formfield">
        	<label><b>art_title(s):</b></label>
          <cfset selectedQuery = cms_pageRecord.getarticlesIterator().getQuery() />

	        <div class="formfieldinputstack">
          <cfoutput query="selectedQuery">
            #selectedQuery.art_title#<br />
          </cfoutput>
	        </div>
        </div>
          
      
</fieldset>
</div>
</cfform>
    
	
