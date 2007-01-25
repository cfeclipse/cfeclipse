<cfset myself = ViewState.getValue('myself')>
<cfset qBanners = ViewState.getValue('qBanners')>
<cfoutput>
<!-- MAIN MENU: Top horizontal menu of the site.  Use class="here" to turn the current page tab on -->
  <div id="mainMenu">
    <ul class="floatRight">
	  <li><a href="#myself#page.index" title="Home" class="here">Home</li>
      <li><a href="#myself#page&page=download" title="Download">Download</a></li>
      <li><a href="#myself#page&page=features" title="Features">Features</a></li>
      <li><a href="#myself#page&page=support" title="Support">Support</a></li>
      <li><a href="#myself#page&page=news" title="News">News</a></li>
	  <li><a href="#myself#page&page=about" title="About">About</a></li>
    </ul>
  </div>
  <div id="myHeader">
     
	  <div id="title">
		
<!--- 		T_CONTENT	ART_DESCRIPTION	ART_ID	ART_IMG	ART_PAGE_ID	ART_TITLE	ART_TYPE_ID	BPUBLISHED	USERID --->
		 <!--- <img src="/assets/img/spacer.gif" width="1" height="40" /><br /> --->
		<cfloop query="qBanners">
			<cfif Len(#art_content#)>
			<a href="#myself##art_content#" id="bannerlink"><img src="#art_img#" alt="#art_description#" border="0"/></a>
			<cfelse>
			<img src="#art_img#" alt="#art_description#" border="0"/>
			</cfif>
		</cfloop>

	  </div>
  </div>
</cfoutput>