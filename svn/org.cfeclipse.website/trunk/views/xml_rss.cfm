<?xml version="1.0" encoding="ISO-8859-1" ?>
<cfcontent type="text/xml">
<!--- RSS FORMAT --->
<cfset qRss = ViewState.getValue('qRSS')>
<rss version="2.0">
	<channel>
		<title>CFEclipse.org</title>
		<link>http://www.cfeclipse.org/</link>
		<description>News about CFEclipse</description>
		<language>en-us</language>
		<copyright>Copyright 2004-<cfoutput>#DateFormat(Now(), "yyyy")#</cfoutput>CFEclipse.org</copyright>
		<lastBuildDate><cfoutput>#dateFormat(Now(), "ddd, dd mmm yyyy")# #TimeFormat(Now(), "HH:mm:ss")# EST</cfoutput></lastBuildDate>
		<!--- items to go here --->
		<cfoutput query="qRSS">
		<item>
			<title>#XMLFormat(ART_TITLE)#</title>
			<description>#XMLFormat(art_description)#</description>
			<link>http://#CGI.SERVER_NAME#/#ViewState.getValue('myself')##pagename#</link>
			<author>#DisplayName#</author>
			<pubDate>#xmlFormat(dateFormat(DTCREATED, "ddd, dd mmm yyyy"))# #XmlFormat(TimeFormat(DTCREATED, "HH:mm:ss"))#</pubDate>
		</item>
		</cfoutput>
		
	</channel>




</rss>