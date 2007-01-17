<cfset myself = ViewState.getValue('myself') />
<cfset section = ViewState.getValue('section') />
<div id="subpage_menu">
	<div style="float:left;"><img src="assets/img/gradient_sub_header.jpg" alt="CFEclipse Sub Page Logo" /></div>
	<cfoutput>
	<ul class="floatRight">
		<li><a href="#myself#page.index" title="Home" class="#iif(section EQ "home", DE('here'), DE('not_here'))#">Home</a></li>
	    <li><a href="#myself#download" title="Download" class="#iif(section EQ "download", DE('here'), DE('not_here'))#">Download</a></li>
	    <li><a href="#myself#features" title="Features" class="#iif(section EQ "features", DE('here'), DE('not_here'))#">Features</a></li>
	    <li><a href="#myself#support" title="Support" class="#iif(section EQ "support", DE('here'), DE('not_here'))#">Support</a></li>
	    <li><a href="#myself#news" title="News" class="#iif(section EQ "news", DE('here'), DE('not_here'))#">News</a></li>
		<li><a href="#myself#about" title="About" class="#iif(section EQ "about", DE('here'), DE('not_here'))#">About</a></li>
	</ul>
	</cfoutput>
</div>