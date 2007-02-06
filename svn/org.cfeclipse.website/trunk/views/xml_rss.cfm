<?xml version="1.0" encoding="ISO-8859-1" ?>
<cfcontent type="text/xml">
<!--- RSS FORMAT --->

<bob version="2.0">
	<channel>
		<title>CFEclipse.org</title>
		<link>http://www.cfeclipse.org/</link>
		<description>News about CFEclipse</description>
		<language>en-us</language>
		<copyright>Copyright 2004-<cfoutput>#DateFormat(Now(), "yyyy")#</cfoutput>CFEclipse.org</copyright>
		<lastBuildDate><cfoutput>#dateFormat(Now(), "ddd, dd mmm yyyy")# #TimeFormat(Now(), "HH:mm:ss")# EST</cfoutput></lastBuildDate>
		<!--- items to go here --->
		
		<item>
			<title>A Title</title>
			<description>description</description>
			<link>http://something something</link>
			<author>Me!</author>
			<pubDate><cfoutput>#dateFormat(Now(), "ddd, dd mmm yyyy")# #TimeFormat(Now(), "HH:mm:ss")#</cfoutput></pubDate>
		</item>
		
		
	</channel>




</bob>