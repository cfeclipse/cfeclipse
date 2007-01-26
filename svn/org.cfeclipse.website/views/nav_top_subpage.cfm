<cfset myself = ViewState.getValue('myself') />
<cfset section = ViewState.getValue('section') />
	 <cfoutput>
	<ul class="floatRight">
		<li><a href="#myself#page.index" title="Home" class="#iif(section EQ "home", DE('here'), DE('not_here'))#">Home</a></li>
	    <li><a href="#myself#page&page=download" title="Download" class="#iif(section EQ "download", DE('here'), DE('not_here'))#">Download</a></li>
	   <!---  <li><a href="#myself#page&page=features" title="Features" class="#iif(section EQ "features", DE('here'), DE('not_here'))#">Features</a></li> --->
	    <!--- <li><a href="#myself#page&page=support" title="Support" class="#iif(section EQ "support", DE('here'), DE('not_here'))#">Support</a></li> --->
	    <!--- <li><a href="#myself#page&page=news" title="News" class="#iif(section EQ "news", DE('here'), DE('not_here'))#">News</a></li>
		<li><a href="#myself#page&page=about" title="About" class="#iif(section EQ "about", DE('here'), DE('not_here'))#">About</a></li> --->
	</ul>
	</cfoutput>  