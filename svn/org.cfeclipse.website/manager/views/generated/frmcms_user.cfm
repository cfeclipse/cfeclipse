    

<cfset listEvent = viewstate.getValue("myself") & viewstate.getValue("xe.list")  />
<cfset cms_userRecord = viewstate.getValue("cms_userRecord") />
<cfset keyString = "&userid=#urlEncodedFormat(cms_userRecord.getuserid())#" />
<cfset commitEvent = viewstate.getValue("myself") & viewstate.getValue("xe.commit") & keyString />
<cfset validation = viewstate.getValue("cms_userValidation", structNew()) />

<cfset isNew = true />
		

		<cfif (not isNumeric(cms_userRecord.getuserid()) and len(cms_userRecord.getuserid())) or (isNumeric(cms_userRecord.getuserid()) and cms_userRecord.getuserid())>
			<cfset isNew = false />
		</cfif>
	
		
<cfoutput>
<div id="breadcrumb">
		<a href="#listEvent#">Cms_users</a> / 
		<cfif isNew>
			Add New Cms_user
		<cfelse>
			#cms_userRecord.getusername()#
		</cfif>
</div>
</cfoutput>
<br />


								
<cfform action="#commitEvent#" class="edit">
    
<fieldset>
    

    <cfoutput>
    <input type="hidden" name="userid" value="#cms_userRecord.getuserid()#" />
    </cfoutput>
  
        <div class="formfield">
	        <label for="username" <cfif structKeyExists(validation, "username")>class="error"</cfif>><b>Username:</b></label>
	        <div>
	        
		        <cfinput 
									type="text" 
									class="input" 
									maxLength="45" 
									id="username" 
									name="username" 
									
										value="#cms_userRecord.getusername()#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="username" validation="#validation#" />
        </div>
    
        <div class="formfield">
	        <label for="password" <cfif structKeyExists(validation, "password")>class="error"</cfif>><b>Password:</b></label>
	        <div>
	        
		        <cfinput 
									type="text" 
									class="input" 
									maxLength="45" 
									id="password" 
									name="password" 
									
										value="#cms_userRecord.getpassword()#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="password" validation="#validation#" />
        </div>
    
        <div class="formfield">
	        <label for="DisplayName" <cfif structKeyExists(validation, "DisplayName")>class="error"</cfif>><b>Display Name:</b></label>
	        <div>
	        
		        <cfinput 
									type="text" 
									class="input" 
									maxLength="100" 
									id="DisplayName" 
									name="DisplayName" 
									
										value="#cms_userRecord.getDisplayName()#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="DisplayName" validation="#validation#" />
        </div>
    
        <div class="formfield">
	        <label for="bActive" <cfif structKeyExists(validation, "bActive")>class="error"</cfif>><b>B Active:</b></label>
	        <div>
	        
		        <cfinput 
									type="text" 
									class="input" 
									 
									id="bActive" 
									name="bActive" 
									
										value="#cms_userRecord.getbActive()#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="bActive" validation="#validation#" />
        </div>
    
        <div class="formfield">
	        <label for="bAdmin" <cfif structKeyExists(validation, "bAdmin")>class="error"</cfif>><b>B Admin:</b></label>
	        <div>
	        
		        <cfinput 
									type="text" 
									class="input" 
									 
									id="bAdmin" 
									name="bAdmin" 
									
										value="#cms_userRecord.getbAdmin()#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="bAdmin" validation="#validation#" />
        </div>
    
        <div class="formfield">
	        <label for="dtLastLogin" <cfif structKeyExists(validation, "dtLastLogin")>class="error"</cfif>><b>Dt Last Login:</b></label>
	        <div>
	        
		        <cfinput 
									type="text" 
									class="input" 
									 
									id="dtLastLogin" 
									name="dtLastLogin" 
									
										value="#dateFormat(cms_userRecord.getdtLastLogin(), "m/d/yyyy")# #timeFormat(cms_userRecord.getdtLastLogin(), "h:mm TT")#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="dtLastLogin" validation="#validation#" />
        </div>
    
        <div class="formfield">
	        <label for="email" <cfif structKeyExists(validation, "email")>class="error"</cfif>><b>Email:</b></label>
	        <div>
	        
		        <cfinput 
									type="text" 
									class="input" 
									maxLength="255" 
									id="email" 
									name="email" 
									
										value="#cms_userRecord.getemail()#" 
									
						/>
		      
	        </div>
	        <cfmodule template="/ModelGlue/customtags/validationErrors.cfm" property="email" validation="#validation#" />
        </div>
    
<cfoutput>
<div class="controls">
 	<input type="submit" value="Save" />
  <input type="button" value="Cancel" onclick="document.location.replace('#listEvent#')" />
</div>
</cfoutput>
</fieldset>
</cfform>
    
	
