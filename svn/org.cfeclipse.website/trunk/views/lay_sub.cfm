<!--
	Site design inspired by http://fullahead.org
-->
<cfset oPage = ViewState.getValue('PageObject')>
<cfset section = oPage.getPageName()>
<cfset myself = ViewState.getValue('myself')>
<cfset stMetaData = ViewState.getValue('metadata')>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en-AU">

<head>

  <title>CFEclipse: <cfoutput>#oPage.getPageName()#</cfoutput></title>

  <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
<cfloop collection="#stMetaData#" item="name">
<cfoutput>
  <meta name="#name#" content="#stMetaData[name]#" /></cfoutput>

</cfloop>
  <meta name="robots" content="index, follow, noarchive" />
  <meta name="googlebot" content="noarchive" />
 <link rel="shortcut icon" href="favicon.ico" />
<cfoutput><link rel="alternate" type="application/rss+xml" title="RSS" href="#ViewState.getValue('myself')#rss" /></cfoutput>
  <link rel="stylesheet" type="text/css" href="/assets/css/html.css" media="screen, projection, tv " />
  <link rel="stylesheet" type="text/css" href="/assets/css/layout.css" media="screen, projection, tv" />
  <link rel="stylesheet" type="text/css" href="/assets/css/print.css" media="print" />

</head>

<body class="subpage">
<!-- CONTENT: Holds all site content except for the footer.  This is what causes the footer to stick to the bottom -->
<div id="subpage_content">

  
	<div id="subpage_menu">
		<cfoutput>#ViewCollection.getView("topnav")#</cfoutput>
		<div id="subpage_logo"></div>
	</div>  
		  
   <!--- <div class="subpage_block_black"></div> --->

  <!-- PAGE CONTENT BEGINS: This is where you would define the columns (number, width and alignment) -->
  <div id="page">

		<cfoutput>#ViewCollection.getView('body')#</cfoutput>
  </div> <!-- END page -->

</div> <!-- end subpage_content -->

<!-- FOOTER: Site footer for links, copyright, etc. -->
<div id="footer">

  <div id="width">
    <span class="floatLeft">
      valid <a href="http://validator.w3.org/check?uri=referer" title="Validate XHTML">XHTML</a> <span class="grey">|</span>
      should be valid <a href="http://jigsaw.w3.org/css-validator" title="Validate CSS">CSS</a>
    </span>

    <span class="floatRight">  </span>
  </div>

</div> <!-- end footer -->
<cfif ViewState.getValue('google_production','false')>
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript">
</script>
<script type="text/javascript">
_uacct = "UA-259985-2";
urchinTracker();
</script>
</cfif>
</body>

</html>