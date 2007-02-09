<cfset qPageList = ViewState.getValue('qPageList')>
<cfset qArticleList = ViewState.getValue('qArticleList')>
<!--- insert the tree of content --->
<link rel="StyleSheet" href="tree.css" type="text/css">
<script src="views/treeHandler.cfm"></script>
<div class="gradient tree">
	
	<cfset expandto = "">
 <cf_tree value="cfeclipse.org" globalTarget="_self" baseIcon="img/page_world.png">
    <cfloop query="qPageList"> 
		<cfset icon="img/note.png">
		<cfif NOT bPublished>
		<cfset icon="img/note_edit.png">
		</cfif>
		 <cfset PageisOpen = iif(viewState.getValue("pageid") EQ pageid, DE(true), DE(false))>
		<cfif expandto EQ "" and PageisOpen>
			<cfset expandto = PAGEID>
		</cfif>
		

		 <cf_node id="#PAGEID#" pid="#PARENTPAGE#" value="#PAGENAME#" title="#pagename#" url="#ViewState.getValue('myself')#cms_page.edit&pageid=#pageid#" icon="#icon#" iconOpen="#icon#"/>
     </cfloop>
	
	 <cfloop query="qArticleList"> 
		 
		 <cfset isOpen = iif(viewState.getValue("art_id") EQ art_id, DE(true), DE(false))>
		<cfset icon="img/page.png">
		<cfif NOT bPublished>
		<cfset icon="img/page_edit.png">
		</cfif>
		
		<cfif expandto EQ "" and isOpen>
			<cfset expandto = "#art_id#0000">
		</cfif>
 
		 <cf_node id="#art_id#0000" pid="#art_page_id#" value="#art_title#" title="#art_title#" url="#ViewState.getValue('myself')#cms_article.edit&art_id=#art_id#" icon="#icon#" iconOpen="img/page_gear.png" isOpen="#isOpen#" />
     </cfloop>
	
	
   </cf_tree>
</div>

<cfif Len(expandto)>
<script>
<cfoutput>t.openTo(#expandto#, true);</cfoutput>
</script>
</cfif>
