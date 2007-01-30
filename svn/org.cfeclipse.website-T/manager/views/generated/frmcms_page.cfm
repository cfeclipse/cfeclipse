    

<cfset listEvent = viewstate.getValue("myself") & viewstate.getValue("xe.list")  />
<cfset cms_pageRecord = viewstate.getValue("cms_pageRecord") />
<cfset keyString = "&pageid=#urlEncodedFormat(cms_pageRecord.getpageid())#" />
<cfset commitEvent = viewstate.getValue("myself") & viewstate.getValue("xe.commit") & keyString />
<cfset validation = viewstate.getValue("cms_pageValidation", structNew()) />

<cfset isNew = true />
		

		<cfif (not isNumeric(cms_pageRecord.getpageid()) and len(cms_pageRecord.getpageid())) or (isNumeric(cms_pageRecord.getpageid()) and cms_pageRecord.getpageid())>
			<cfset isNew = false />
		</cfif>
	
		
<cfoutput>
<div id="breadcrumb">
		<a href="#listEvent#">Cms_pages</a> / 
		<cfif isNew>
			Add New Cms_page
		<cfelse>
			#cms_pageRecord.getpagename()#
		</cfif>
</div>
</cfoutput>
<br />


								
<cfform action="#commitEvent#" class="edit">
    
<fieldset>
    

    <cfoutput>
    <input type="hidden" name="pageid" value="#cms_pageRecord.getpageid()#" />
    </cfoutput>
  
        <div class="formfield">
	        <label for="pagename" <cfif structKeyExists(validation, "pagename")>class="error"</cfif>><b>Pagename:</b></label>
	        <div>
	        
		        <cfinput 
									type="text" 
									class="input" 
									maxLength="100" 
									id="pagename" 
									name="pagename" 
									
										value="#cms_pageRecord.getpagename()#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="pagename" validation="#validation#" />
        </div>
    
        <div class="formfield">
	        <label for="parentpage" <cfif structKeyExists(validation, "parent")>class="error"</cfif>><b>Parent:</b>
	        </label>
	        <cfset valueQuery = viewstate.getValue("cms_pageList") />
	        <div>
        
          <select name="parentpage" id="parentpage" >
            
            <cfoutput query="valueQuery">
              <option value="#valueQuery.pageid#" <cfif cms_pageRecord.getparentpage() eq valueQuery.pageid>selected</cfif>>#valueQuery.pagename#</option>
            </cfoutput>
          </select>
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="parent" validation="#validation#" />
        </div>
      
        <div class="formfield">
	        <label for="pagedescription" <cfif structKeyExists(validation, "pagedescription")>class="error"</cfif>><b>Pagedescription:</b></label>
	        <div>
	        
		        <textarea class="input" id="pagedescription" name="pagedescription"><cfoutput>#cms_pageRecord.getpagedescription()#</cfoutput></textarea>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="pagedescription" validation="#validation#" />
        </div>
    
        <div class="formfield">
	        <label for="orderid" <cfif structKeyExists(validation, "orderid")>class="error"</cfif>><b>Orderid:</b></label>
	        <div>
	        
		        <cfinput 
									type="text" 
									class="input" 
									 
									id="orderid" 
									name="orderid" 
									
										value="#cms_pageRecord.getorderid()#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="orderid" validation="#validation#" />
        </div>
    
        <div class="formfield">
	        <label for="bPublished" <cfif structKeyExists(validation, "bPublished")>class="error"</cfif>><b>B Published:</b></label>
	        <div>
	        
		        <cfinput 
									type="text" 
									class="input" 
									 
									id="bPublished" 
									name="bPublished" 
									
										value="#cms_pageRecord.getbPublished()#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="bPublished" validation="#validation#" />
        </div>
    
        <div class="formfield">
	        <label for="layout" <cfif structKeyExists(validation, "layout")>class="error"</cfif>><b>Layout:</b></label>
	        <div>
	        
		        <cfinput 
									type="text" 
									class="input" 
									maxLength="100" 
									id="layout" 
									name="layout" 
									
										value="#cms_pageRecord.getlayout()#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="layout" validation="#validation#" />
        </div>
    
        <div class="formfield">
        	<label <cfif structKeyExists(validation, "childpages")>class="error"</cfif>><b>Childpages(s):</b></label>
          <cfset valueQuery = viewstate.getValue("cms_pageList") />
          <cfset selectedQuery = cms_pageRecord.getchildpagesIterator().getQuery() />

          <cfif viewstate.exists("childpages|pageid")>
            <cfset selectedList = viewstate.getValue("childpages|pageid") />
          <cfelse>
            <cfset selectedList = valueList(selectedQuery.pageid) />
          </cfif>
            
          <!--- 
            hidden makes the field always defined.  if this wasn't here, and you deleted this whole field
            from the control, you'd wind up deleting all child records during a genericCommit...
          --->
          <input type="hidden" name="childpages|pageid" value="" />
	        <div class="formfieldinputstack">
          <cfoutput query="valueQuery">
            <label for="childpages_#valueQuery.pageid#"><input type="checkbox" name="childpages|pageid" id="childpages_#valueQuery.pageid#" value="#valueQuery.pageid#"<cfif listFindNoCase(selectedList, "#valueQuery.pageid#")> checked</cfif>/>#valueQuery.pagename#</label><br />
          </cfoutput>
	        </div>
        </div>
          
      
        <div class="formfield">
        	<label <cfif structKeyExists(validation, "articles")>class="error"</cfif>><b>Articles(s):</b></label>
          <cfset valueQuery = viewstate.getValue("cms_articleList") />
          <cfset selectedQuery = cms_pageRecord.getarticlesIterator().getQuery() />

          <cfif viewstate.exists("articles|art_id")>
            <cfset selectedList = viewstate.getValue("articles|art_id") />
          <cfelse>
            <cfset selectedList = valueList(selectedQuery.art_id) />
          </cfif>
            
          <!--- 
            hidden makes the field always defined.  if this wasn't here, and you deleted this whole field
            from the control, you'd wind up deleting all child records during a genericCommit...
          --->
          <input type="hidden" name="articles|art_id" value="" />
	        <div class="formfieldinputstack">
          <cfoutput query="valueQuery">
            <label for="articles_#valueQuery.art_id#"><input type="checkbox" name="articles|art_id" id="articles_#valueQuery.art_id#" value="#valueQuery.art_id#"<cfif listFindNoCase(selectedList, "#valueQuery.art_id#")> checked</cfif>/>#valueQuery.art_title#</label><br />
          </cfoutput>
	        </div>
        </div>
          
      
<cfoutput>
<div class="controls">
 	<input type="submit" value="Save" />
  <input type="button" value="Cancel" onclick="document.location.replace('#listEvent#')" />
</div>
</cfoutput>
</fieldset>
</cfform>
    
	
