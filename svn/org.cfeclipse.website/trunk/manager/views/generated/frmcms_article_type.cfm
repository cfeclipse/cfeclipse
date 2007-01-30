    

<cfset listEvent = viewstate.getValue("myself") & viewstate.getValue("xe.list")  />
<cfset cms_article_typeRecord = viewstate.getValue("cms_article_typeRecord") />
<cfset keyString = "&type_id=#urlEncodedFormat(cms_article_typeRecord.gettype_id())#" />
<cfset commitEvent = viewstate.getValue("myself") & viewstate.getValue("xe.commit") & keyString />
<cfset validation = viewstate.getValue("cms_article_typeValidation", structNew()) />

<cfset isNew = true />
		

		<cfif (not isNumeric(cms_article_typeRecord.gettype_id()) and len(cms_article_typeRecord.gettype_id())) or (isNumeric(cms_article_typeRecord.gettype_id()) and cms_article_typeRecord.gettype_id())>
			<cfset isNew = false />
		</cfif>
	
		
<cfoutput>
<div id="breadcrumb">
		<a href="#listEvent#">Cms_article_types</a> / 
		<cfif isNew>
			Add New Cms_article_type
		<cfelse>
			#cms_article_typeRecord.gettype_name()#
		</cfif>
</div>
</cfoutput>
<br />


								
<cfform action="#commitEvent#" class="edit">
    
<fieldset>
    

    <cfoutput>
    <input type="hidden" name="type_id" value="#cms_article_typeRecord.gettype_id()#" />
    </cfoutput>
  
        <div class="formfield">
	        <label for="type_name" <cfif structKeyExists(validation, "type_name")>class="error"</cfif>><b>Type_name:</b></label>
	        <div>
	        
		        <cfinput 
									type="text" 
									class="input" 
									maxLength="85" 
									id="type_name" 
									name="type_name" 
									
										value="#cms_article_typeRecord.gettype_name()#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="type_name" validation="#validation#" />
        </div>
    
<cfoutput>
<div class="controls">
 	<input type="submit" value="Save" />
  <input type="button" value="Cancel" onclick="document.location.replace('#listEvent#')" />
</div>
</cfoutput>
</fieldset>
</cfform>
    
	
