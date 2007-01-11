<cfset myself= ViewState.getValue('myself')>
<cfoutput>
<ul>
	<li><a href="#myself#cms_article_type.list">Article Types</a></li>
	<li><a href="#myself#cms_article.list">Articles</a></li>
	<li><a href="#myself#cms_testimonial.list">Testimonials</a></li>
	<li><a href="#myself#cms_user.list">Users</a></li>
	
</ul>

<ul>
	<li><a href="#myself#page.index">Home</a></li>
</ul>
</cfoutput>