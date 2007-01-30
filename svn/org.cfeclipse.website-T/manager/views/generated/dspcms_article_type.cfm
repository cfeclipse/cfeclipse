    
<cfset listEvent = viewstate.getValue("myself") & viewstate.getValue("xe.list")  />
<cfset commitEvent = viewstate.getValue("myself") & viewstate.getValue("xe.commit") & "&cms_article_typeId=" & urlEncodedFormat(viewstate.getValue("cms_article_typeId")) />
<cfset cms_article_typeRecord = viewstate.getValue("cms_article_typeRecord") />
<cfset validation = viewstate.getValue("cms_article_typeValidation", structNew()) />

<cfoutput>
<div id="breadcrumb"><a href="#listEvent#">Cms_article_types</a> / View Cms_article_type</div>
</cfoutput>
<br />
  
<cfform class="edit">
    
<fieldset>
    

        <div class="formfield">
          <cfoutput>
	        <label for="type_name"><b>Type_name:</b></label>
	        <span class="input">#cms_article_typeRecord.gettype_name()#</span>
	        </cfoutput>
        </div>
    
</fieldset>
</div>
</cfform>
    
	
