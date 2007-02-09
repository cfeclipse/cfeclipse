<cfsetting enablecfoutputonly="true">  
<!---//-----------------------------------------------------------------------------------
Details:	
	Application: Tree Component Custom Tag
	Author: Daniel Vega (danvega@gmail.com)
	Created: 10/26/05
	Updated: 03/09/2006
	
	Copyright: (c) 2005 Letternine Inc.
	Version 0.9(beta)
	
History:
mm/dd/yyyy	Author			Version			Comments 
03/01/2006	dav				0.9				added multiple tree's on one page by adding attributes.treeName


Notes:
	* you need both tree.cfm & node.cfm for cf_tree to work
	  this tag uses a parent/child relationship to populate the tree.
	* tree.cfm controls the root node attributes along with tree configuration
	  while node.cfm handles all child nodes of root.
	* it is very important to close all child nodes <cf_node x=1 y=2 etc... />
	* attributes.useFolderLinks(tree configuration) NOT WORKING, CAN NOT DISABLE
	
Usage:

<cf_tree value="Root Node" globalTarget="_blank" baseIcon="img/base.gif">
	<cf_node id="1" pid="0" value="Parent 1" title="Parent 1" url="##"/>
	<cf_node id="2" pid="0" value="Parent 2" title="Parent 2" url="##"/>
	<cf_node id="3" pid="2" value="Branch 1" title="Branch 1 is first child of parent 2" url="##"/>
	<cf_node id="4" pid="2" value="Branch 2" title="Branch 2 is second child of parent 2" url="##"/>
	<cf_node id="5" pid="0" value="Parent 3" title="Parent 3" url="##"/>
</cf_tree>


Root Node Attributes:
	Attribute:	Type:		Required:	Default:				Description:
	
	treeName	(string)	no			t						Tree Name allows you to create multiple tree's on one page
	value		(string)	yes			null					The text to display
	url			(string)	no			""  					Url for the root node
	title		(string)	no			""  					Title for the root node
	target		(string)	no			_blank					Target for the root node(_blank,_self,_parent,_top,frame)
	baseIcon	(string)	no			img/folder_home.png		The Icon of the root node defaults to img/folder_home.png

Child Node Attributes
	Attribute:	Type:		Required:	Default:				Description:
	
	id			(numeric)	yes									Unique identity number.
	pid			(numeric)	yes									Number refering to the parent node
	value		(string)	yes								    The text to display
	url			(string)	no			""  					Url for the root node
	title		(string)	no			""  					Title for the root node
	target		(string)	no			_blank					Target for the root node(_blank,_self,_parent,_top,frame)
	icon		(string)	no			folder/page				Image file to use as the icon. Uses default if not specified
	iconOpen	(string)	no			folderOpen/page			Image file to use as the open icon. Uses default if not specified
	isOpen		(boolean)	no			false					Is the node open
	
Tree Configuration Attributes
	Attribute:		Type:		Required:	Default:	Description:
	
	globalTarget	(string)	no			""			Target for all the nodes, easier than having to set it at each node
	folderLinks		(boolean)	no			true		Should folders be links
	useSelection	(boolean)	no			true		Nodes can be selected(highlighted)
	useCookies		(boolean)	no			false		The tree uses cookies to rember it's state
	useLines		(boolean)	no			true		Tree is drawn with lines
	useIcons		(boolean)	no			true		Tree is drawn with icons
	useStatusText	(boolean)	no			false		Displays node names in the statusbar instead of the url
	closeSameLevel	(boolean)	no			false		Only one node within a parent can be expanded at the same time. openAll() and closeAll() functions do not work when this is enabled
	inOrder			(boolean)	no			false		If parent nodes are always added before children, setting this to true speeds up the tree
------------------------------------------------------------------------------------//--->

<!--- ROOT NODE SETUP --->

<!--- Tree Instance Name - for multiple trees within one page --->
<cfparam name="attributes.treeName" type="string" default="t">
<!--- Text label for the node. --->
<cfparam name="attributes.value" type="string">
<!--- Url for the node. --->
<cfparam name="attributes.url" type="string" default="">
<!--- Title for the node, used for alt text on mouseover --->
<cfparam name="attributes.title" type="string" default="">
<!--- Target for the node. --->
<cfparam name="attributes.target" type="string" default="_blank">
<!--- This is the base nodes icon --->
<cfparam name="attributes.baseIcon" type="string" default="img/folder_home.png">

<!--- CONFIGURATION OF THE TREE ATTRIBUTES --->

<!--- Target for all the nodes --->
<cfparam name="attributes.globalTarget" type="string" default="">
<!--- Should folders be links --->
<cfparam name="attributes.folderLinks" type="boolean" default="true">
<!--- Nodes can be selected(highlighted) --->
<cfparam name="attributes.useSelection" type="boolean" default="true">
<!--- The tree uses cookies to rember it's state --->
<cfparam name="attributes.useCookies" type="boolean" default="false">
<!--- Tree is drawn with lines --->
<cfparam name="attributes.useLines" type="boolean" default="true">
<!--- Tree is drawn with icons --->
<cfparam name="attributes.useIcons" type="boolean" default="true">
<!--- Displays node names in the statusbar instead of the url --->
<cfparam name="attributes.useStatusText" type="boolean" default="false">
<!--- Only one node within a parent can be expanded at the same time. openAll() and closeAll() functions do not work when this is enabled --->
<cfparam name="attributes.closeSameLevel" type="boolean" default="false">
<!--- If parent nodes are always added before children, setting this to true speeds up the tree --->
<cfparam name="attributes.inOrder" type="boolean" default="false">

<cfif ThisTag.ExecutionMode EQ "Start">	
<cfelse>


	<!--- validate that child nodes are going to be added to the tree --->
	<cfparam name="thistag.assocAttribs" default="#arrayNew(1)#">
	<cfif arrayLen(thistag.assocAttribs) eq 0>
	
	<!--- inform the user no children have been defined --->
	<cfoutput><span class="error">No Children Found!</span></cfoutput>
	
	<cfelse>
	
	<cfoutput>
	<script>
		//create the tree
		#attributes.treeName# = new dTree('#attributes.treeName#');
		
		//tree configuration
		#attributes.treeName#.config.target='#attributes.globalTarget#';
		#attributes.treeName#.config.folderLinks=#attributes.folderLinks#;
		#attributes.treeName#.config.useSelection=#attributes.useSelection#;
		#attributes.treeName#.config.useCookies=#attributes.useCookies#;
		#attributes.treeName#.config.useLines=#attributes.useLines#;
		#attributes.treeName#.config.useIcons=#attributes.useIcons#;
		#attributes.treeName#.config.useStatusText=#attributes.useStatusText#;
		#attributes.treeName#.config.closeSameLevel=#attributes.closeSameLevel#;
		#attributes.treeName#.config.inOrder=#attributes.inOrder#;
		
		//add the root node
		#attributes.treeName#.icon.root="#attributes.baseIcon#";
		#attributes.treeName#.add(0,-1,'#attributes.value#','#attributes.url#','#attributes.title#','#attributes.target#');
		
		//add children nodes
		//add(id,pid,value,url,title,target,icon,iconOpen,open)
		<cfloop from="1" to="#arrayLen(thistag.assocAttribs)#" index="x">
		#attributes.treeName#.add(#thistag.assocAttribs[x].id#,#thistag.assocAttribs[x].pid#,"#thistag.assocAttribs[x].value#",'#thistag.assocAttribs[x].url#','#thistag.assocAttribs[x].title#','#thistag.assocAttribs[x].target#','#thistag.assocAttribs[x].icon#','#thistag.assocAttribs[x].iconOpen#',#thistag.assocAttribs[x].isOpen#);
		</cfloop> 
		//write the tree to screen
		document.write("<div class='tree'>" + #attributes.treeName# + "</div>");
	</script>
	</cfoutput>
	
	</cfif>

<cfsetting enablecfoutputonly="false">
</cfif> 

<cfif thistag.executionMode IS "end">
	<cfsetting enablecfoutputonly="false">
</cfif>

