<cffunction name="ToXML" output="false" returntype="any">
	<cfargument name="xmlVersion" type="string"  required="true"/>
	<cfargument name="xmlDoc" type="any" required="false" default="#XmlNew()#"/>
	<cfscript>
		var thisXml = "";

		switch(xmlVersion)
		{
			case "0.2":
				thisXML = XmlElemNew(xmlDoc, "user");
				thisXml.XmlAttributes["id"] = GetQAKey("USR", this.UserID, "QAG");
				thisXml.XmlAttributes["contactRef"] = GetQAKey("CNT", this.ContactID, this.CompanyID);

				break;
			default:
				Throw("", 0, "The QA protocol version number #xmlVersion# is unknown.!", "Unknown QA protocol version number");
		}

		return thisXml;		
	</cfscript>
</cffunction>