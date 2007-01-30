    
<cfset listEvent = viewstate.getValue("myself") & viewstate.getValue("xe.list")  />
<cfset commitEvent = viewstate.getValue("myself") & viewstate.getValue("xe.commit") & "&cms_userId=" & urlEncodedFormat(viewstate.getValue("cms_userId")) />
<cfset cms_userRecord = viewstate.getValue("cms_userRecord") />
<cfset validation = viewstate.getValue("cms_userValidation", structNew()) />

<cfoutput>
<div id="breadcrumb"><a href="#listEvent#">Cms_users</a> / View Cms_user</div>
</cfoutput>
<br />
  
<cfform class="edit">
    
<fieldset>
    

        <div class="formfield">
          <cfoutput>
	        <label for="username"><b>Username:</b></label>
	        <span class="input">#cms_userRecord.getusername()#</span>
	        </cfoutput>
        </div>
    
        <div class="formfield">
          <cfoutput>
	        <label for="password"><b>Password:</b></label>
	        <span class="input">#cms_userRecord.getpassword()#</span>
	        </cfoutput>
        </div>
    
        <div class="formfield">
          <cfoutput>
	        <label for="DisplayName"><b>Display Name:</b></label>
	        <span class="input">#cms_userRecord.getDisplayName()#</span>
	        </cfoutput>
        </div>
    
        <div class="formfield">
          <cfoutput>
	        <label for="bActive"><b>B Active:</b></label>
	        <span class="input">#cms_userRecord.getbActive()#</span>
	        </cfoutput>
        </div>
    
        <div class="formfield">
          <cfoutput>
	        <label for="bAdmin"><b>B Admin:</b></label>
	        <span class="input">#cms_userRecord.getbAdmin()#</span>
	        </cfoutput>
        </div>
    
        <div class="formfield">
          <cfoutput>
	        <label for="dtLastLogin"><b>Dt Last Login:</b></label>
	        <span class="input">#cms_userRecord.getdtLastLogin()#</span>
	        </cfoutput>
        </div>
    
        <div class="formfield">
          <cfoutput>
	        <label for="email"><b>Email:</b></label>
	        <span class="input">#cms_userRecord.getemail()#</span>
	        </cfoutput>
        </div>
    
</fieldset>
</div>
</cfform>
    
	
