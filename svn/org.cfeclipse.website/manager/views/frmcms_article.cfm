    

<cfset listEvent = viewstate.getValue("myself") & viewstate.getValue("xe.list")  />
<cfset cms_articleRecord = viewstate.getValue("cms_articleRecord") />
<cfset keyString = "&art_id=#urlEncodedFormat(cms_articleRecord.getart_id())#" />
<cfset commitEvent = viewstate.getValue("myself") & viewstate.getValue("xe.commit") & keyString />
<cfset validation = viewstate.getValue("cms_articleValidation", structNew()) />

<cfset isNew = true />
		

		<cfif (not isNumeric(cms_articleRecord.getart_id()) and len(cms_articleRecord.getart_id())) or (isNumeric(cms_articleRecord.getart_id()) and cms_articleRecord.getart_id())>
			<cfset isNew = false />
		</cfif>
	
		
<cfoutput>
<div id="breadcrumb">
		<a href="#listEvent#">Cms_articles</a> / 
		<cfif isNew>
			Add New Cms_article
		<cfelse>
			#cms_articleRecord.getart_title()#
		</cfif>
</div>
</cfoutput>
<br />


								
<cfform action="#commitEvent#" class="edit">
    
<fieldset>
    

    <cfoutput>
    <input type="hidden" name="art_id" value="#cms_articleRecord.getart_id()#" />
    </cfoutput>
  
        <div class="formfield">
	        <label for="art_title" <cfif structKeyExists(validation, "art_title")>class="error"</cfif>><b>Art_title:</b></label>
	        <div>
	        
		        <cfinput size="50" 
									type="text" 
									class="input" 
									maxLength="255" 
									id="art_title" 
									name="art_title" 
									
										value="#cms_articleRecord.getart_title()#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="art_title" validation="#validation#" />
        </div>
    
        <div class="formfield">
	        <label for="art_description" <cfif structKeyExists(validation, "art_description")>class="error"</cfif>><b>Art_description:</b></label>
	        <div>
	        
		        <textarea class="input" id="art_description" name="art_description" cols="80" rows="5"><cfoutput>#cms_articleRecord.getart_description()#</cfoutput></textarea>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="art_description" validation="#validation#" />
        </div>
    
        <div class="formfield">
	        <label for="art_content" <cfif structKeyExists(validation, "art_content")>class="error"</cfif>><b>Art_content:</b></label>
	        <div>
	        
		        <textarea class="input" id="art_content" name="art_content" cols="80" rows="10"><cfoutput>#cms_articleRecord.getart_content()#</cfoutput></textarea>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="art_content" validation="#validation#" />
        </div>
    
        <div class="formfield">
	        <label for="art_img" <cfif structKeyExists(validation, "art_img")>class="error"</cfif>><b>Art_img:</b></label>
	        <div>
	        
		        <cfinput 
									type="text" 
									class="input" 
									maxLength="255" 
									id="art_img" 
									name="art_img" 
									
										value="#cms_articleRecord.getart_img()#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="art_img" validation="#validation#" />
        </div>
    
        <div class="formfield">
	        <label for="art_type_id" <cfif structKeyExists(validation, "cms_article_type")>class="error"</cfif>><b>Cms_article_type:</b>
	        </label>
	        <cfset valueQuery = viewstate.getValue("cms_article_typeList") />
	        <div>
        
          <select name="art_type_id" id="art_type_id" >
            
            <cfoutput query="valueQuery">
              <option value="#valueQuery.type_id#" <cfif cms_articleRecord.getart_type_id() eq valueQuery.type_id>selected</cfif>>#valueQuery.type_name#</option>
            </cfoutput>
          </select>
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="cms_article_type" validation="#validation#" />
        </div>
      
        <div class="formfield">
	        <label for="userid" <cfif structKeyExists(validation, "cms_user")>class="error"</cfif>><b>Cms_user:</b>
	        </label>
	        <cfset valueQuery = viewstate.getValue("cms_userList") />
	        <div>
        
          <select name="userid" id="userid" >
            
            <cfoutput query="valueQuery">
              <option value="#valueQuery.userid#" <cfif cms_articleRecord.getuserid() eq valueQuery.userid>selected</cfif>>#valueQuery.username#</option>
            </cfoutput>
          </select>
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="cms_user" validation="#validation#" />
        </div>
      
        <div class="formfield">
	        <label for="bPublished" <cfif structKeyExists(validation, "bPublished")>class="error"</cfif>><b>B Published:</b></label>
	        <div>
	        
		        <cfinput 
									type="text" 
									class="input" 
									 
									id="bPublished" 
									name="bPublished" 
									
										value="#cms_articleRecord.getbPublished()#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="bPublished" validation="#validation#" />
        </div>
    
        <div class="formfield">
	        <label for="art_page_id" <cfif structKeyExists(validation, "cms_page")>class="error"</cfif>><b>Cms_page:</b>
	        </label>
	        <cfset valueQuery = viewstate.getValue("cms_pageList") />
	        <div>
        
          <select name="art_page_id" id="art_page_id" >
            
            <cfoutput query="valueQuery">
              <option value="#valueQuery.pageid#" <cfif cms_articleRecord.getart_page_id() eq valueQuery.pageid>selected</cfif>>#valueQuery.pagename#</option>
            </cfoutput>
          </select>
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="cms_page" validation="#validation#" />
        </div>
      <div class="formfield">
	        <label for="dtCreated" <cfif structKeyExists(validation, "dtCreated")>class="error"</cfif>><b>Dtcreated:</b></label>
	        <div>
		      
		      	<cfset dtCreatedValue = cms_articleRecord.getdtCreated()>
		      	<cfif NOT Len(dtCreatedValue)>
					<cfset dtCreatedValue = Now()>	
				</cfif>
		      
		        <cfinput 
									type="text" 
									class="input" 
									maxLength="50" 
									id="dtCreated" 
									name="dtCreated" 
									
										value="#dtCreatedValue#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="dtCreated" validation="#validation#" />
        </div>
<cfoutput>
<div class="controls">
 	<input type="submit" value="Save" />
  <input type="button" value="Cancel" onclick="document.location.replace('#listEvent#')" />
</div>
</cfoutput>
</fieldset>
</cfform>
    
	
