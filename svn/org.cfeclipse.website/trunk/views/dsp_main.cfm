<cfset qMainContent = ViewState.getValue('qHPContent')>
<cfset qFeatureList = ViewState.getValue('qRandFeatures')>
<cfset stMetaData = ViewState.getValue('metadata')>
<cfset qNews = ViewState.getValue('qNews')>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--
	Site design inspired by http://fullahead.org
-->
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en-GB">

<head>
  <title>CFEclipse: The ColdFusion IDE for Eclipse</title>
  <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
<cfloop collection="#stMetaData#" item="name">
<cfoutput>
  <meta name="#name#" content="#stMetaData[name]#" /></cfoutput>

</cfloop>
  <meta name="robots" content="index, follow, noarchive" />
  <meta name="googlebot" content="noarchive" />
 <link rel="shortcut icon" href="favicon.ico" >
  <link rel="stylesheet" type="text/css" href="/assets/css/html.css" media="screen, projection, tv " />
  <link rel="stylesheet" type="text/css" href="/assets/css/layout.css" media="screen, projection, tv" />
  <link rel="stylesheet" type="text/css" href="/assets/css/print.css" media="print" />

</head>


<body>
<!-- CONTENT: Holds all site content except for the footer.  This is what causes the footer to stick to the bottom -->
<div id="content">

	<cfoutput>#ViewCollection.getView("topnav")#</cfoutput>
  

  <!-- PAGE CONTENT BEGINS: This is where you would define the columns (number, width and alignment) -->
  <div id="page">


    <!-- 25 percent width column, aligned to the left -->
    <div class="width25 floatLeft leftColumn">
     <h1>News</h1>
	<cfloop query="qNews">
	<cfoutput>
	<div class="newsSnippet">
		<h2>#art_title#</h2>
		<p>#art_description#</p>
	</div>
	 <p>&nbsp;</p>
	</cfoutput>
	<!--- <div class="newsSnippet">
		<h2><a href="##">Beta 1.3 Fixed for Eclipse 3.1</a></h2>
		<p>
		Some users mentioned that they were having problems with the update of CFEclipse Beta 1.3 to run on Eclipse 3.1, the errors have been fixed and an updated package is available from the beta page.
		</p>
	
	</div> --->
	</cfloop>
    </div>




    <!-- 75 percent width column, aligned to the right -->
    <div class="width75 floatRight">


      <!-- Gives the gradient block -->
      <div class="gradient">

    
		<cfoutput query="qMainContent">
		<h1>#art_title#</h1>
        
        <p>#art_content#</p>
		</cfoutput>
	  

	  
	  <h1>Features</h1>
		 <!---  <cfdump var="#ViewState.getValue('qRandFeatures')#"> --->
		   <blockquote class="go">
          <p>
			CFEclipse has a wealth of features to appeal to ColdFusion developers
          </p>
        </blockquote>
		 
		<div class="content">
		
				<cfoutput query="qFeatureList">
		
					<div class="wide3">
						<p>#ART_TITLE#</p>
						
						<cfif Len(art_img)>
					<!--- 	<a href="#ViewState.getValue('myself')#page&page=features##feature_#art_id#"> --->
							<img alt="Code Folding" src="#art_img#"  />
					<!--- 	</a> --->
						<cfelse>
						<span style="text-align: justify;">
					<!--- 	<a href="#ViewState.getValue('myself')#page&page=features##feature_#art_id#"> --->
							#ART_DESCRIPTION#
						<!--- </a> --->
						</span>
						</cfif>

					</div>
					
					</cfoutput>
		
				</div>

				<div class="clear mozclear"></div>
		</div>
	<!--- 	<ul>
          <li><b>Code Folding</b></li>
          <li><b>Snippets</b></li>
          <li><b>Task List</b></li>
          <li><b>Tag Completion</b></li>
          <li><b>Syntax Highlighting</b></li>
		  <li><b>and more ...</b></li>
        </ul> --->
	
			
		
<cfset qryTest = ViewState.getValue("qryTestimonials")>
<cfset myself = ViewState.getValue("myself")>
	<div class="gradient">
        <h1>Testimonials</h1>
        
		<cfoutput query="qryTest">
		
			<!--- 	TES_ID	TES_IMAGE	TES_JOBTITLE	TES_PERSON	TES_TEASER		TES_URL --->
		<cfif Len(TES_URL)>
		<h2><a href="#TES_URL#" target="_blank">#TES_PERSON#<cfif Len(TES_COMPANY)>, #TES_COMPANY#</cfif></a></h2>
		<cfelse>
		<h2>#TES_PERSON#<cfif Len(TES_COMPANY)>, #TES_COMPANY#</cfif></h2>
		</cfif>

        <p>
			<cfif Len(TES_IMAGE)>
				
				<cfif Len(TES_URL)>
          <a href="#TES_URL#" title="#TES_PERSON#<cfif Len(TES_COMPANY)>, #TES_COMPANY#</cfif>"><img src="/assets/img/buntelt.jpg" alt="pic" class="floatLeft"/></a>
				</cfif>
		</cfif>
			<span class="testimonial">"</span>#TES_TEASER#<span class="testimonial">"</span>
        </p>
		<p>&nbsp;</p>
		
		</cfoutput>		
       
        

       <!---  <blockquote class="go">
          <p>
			<cfoutput><a href="#myself#page&page=testimonials">Show your support by sending a testimonial to the CFEclipse team.</a></cfoutput>
          </p>
        </blockquote>
 --->
      </div>
	


    </div>

  </div>

</div>


<!-- FOOTER: Site footer for links, copyright, etc. -->
<div id="footer">

  <div id="width">
    <span class="floatLeft">
      valid <a href="http://validator.w3.org/check?uri=referer" title="Validate XHTML">XHTML</a> <span class="grey">|</span>
      should be valid <a href="http://jigsaw.w3.org/css-validator" title="Validate CSS">CSS</a>
    </span>

    <span class="floatRight">
     
    </span>
  </div>

</div>

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