<cfset myself = ViewState.getValue('myself')>

<cfoutput>
<!-- MAIN MENU: Top horizontal menu of the site.  Use class="here" to turn the current page tab on -->
  <div id="mainMenu">
    <ul class="floatRight">
	  <li><a href="#myself#page.index" title="Home" class="here">Home</li>
      <li><a href="#myself#download" title="Download">Download</a></li>
      <li><a href="#myself#features" title="Features">Features</a></li>
      <li><a href="#myself#support" title="Support">Support</a></li>
      <li><a href="#myself#documentation" title="Documentation">Documentation</a></li>
      <li><a href="#myself#news" title="News">News</a></li>
    </ul>
  </div>
  <div id="myHeader">
     
	  <div id="title">
		  <img src="/assets/img/spacer.gif" width="1" height="40" /><br/>
		  <img src="/assets/img/cfeclipse.gif" />
	  </div>
  </div>
</cfoutput>