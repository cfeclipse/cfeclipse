<cfsetting enablecfoutputonly="true">  
<!---//-----------------------------------------------------------------------------------
Details:	
	Application: Tree Component Custom Tag
	Author: Daniel Vega (danvega@gmail.com)
	Created: 10/26/05
	Copyright: (c) 2005 Letternine Inc.
	Version 0.8(beta)
	
History:
mm/dd/yyyy	Author			Version			Comments 


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

<!--- Unique identity number. --->
<cfparam name="attributes.id" type="numeric">
<!---Number refering to the parent node. The value for the root node has to be -1. --->
<cfparam name="attributes.pid" type="numeric">
<!--- Text label for the node. --->
<cfparam name="attributes.value" type="string">
<!--- Url for the node. --->
<cfparam name="attributes.url" type="string" default="">
<!--- Title for the node, used for alt text on mouseover --->
<cfparam name="attributes.title" type="string" default="">
<!--- Target for the node. --->
<cfparam name="attributes.target" type="string" default="">
<!--- Image file to use as the icon. Uses default if not specified. --->
<cfparam name="attributes.icon" type="string" default="">
<!--- Image file to use as the open icon. Uses default if not specified. --->
<cfparam name="attributes.iconOpen" type="string" default="">
<!--- Is the node open. --->
<cfparam name="attributes.isOpen" type="boolean" default="false">



<cfif ThisTag.ExecutionMode EQ "Start">
  <cfassociate basetag="cf_tree">
  <cfsetting enablecfoutputonly="false">
</cfif>

<cfif thistag.executionMode IS "end">
	<cfsetting enablecfoutputonly="false">
</cfif>