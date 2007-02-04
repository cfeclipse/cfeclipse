<cfset myself = ViewState.getValue('myself') />
<cfset section = ViewState.getValue('section') />
<cfset aMainPages = ViewState.getValue('mainNav')>

<div id="mainMenu">
	 <cfoutput>
	<ul class="floatRight">
		<li><a href="#myself#page.index" title="Home" class="#iif(section EQ "home", DE('here'), DE('not_here'))#">Home</a></li>
		<cfloop from="1" to="#ArrayLen(aMainPages)#" index="p">
	    <li><a href="#myself#page&page=#aMainPages[p]#" title="#aMainPages[p]#" class="#iif(section EQ aMainPages[p], DE('here'), DE('not_here'))#">#aMainPages[p]#</a></li>
	    </cfloop>

	</ul>
	</cfoutput>  
</div>