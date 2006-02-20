<cfsetting enablecfoutputonly="yes">
<!------------------------------------------------------------------------------------------
This file is broken into eleven sections. 
Section One is the Fusedoc for the file.
Section Two contains the public "API-style" variables that are used in the Fusebox framework.
Section Three established the private structure "fb_".
Section Four is the former formURL2attributes Custom Tag.
Section Five includes the fbx_circuits.cfm file.
Section Six creates a mirror of the fusebox.circuits strucure for reverse look-ups.
Section Seven includes the root fbx_settings.cfm file.
Section Eight massages attributes.fuseaction and begins the aliased lookup process.
Section Nine includes nested fbx_settings.cfm files top-to-bottom.
Section Ten includes the target circuit fbx_switch file, which processes the requested fuseaction.
Section Eleven includes any layout files, in bottom-to-top order to allow layouts to be nested. It also outputs the final display of the page.
------------------------------------------------------------------------------------------->

<!------------------------------------------------------------------------------------------
SECTION ONE
For more information about Fusedocs and how to read them, visit fusebox.org and halhelms.com. The ending HTML comment after the opening CFML comment prevents CFStudio from comment-coloring the Fusedoc.
------------------------------------------------------------------------------------------->
<!--- -->
<fusedoc fuse="FBX_FUSEBOX301_CF40.CFM" specification="2.0">
	<responsibilities>
		I am the code behind Fusebox 3.0 that handles nesting, layouts--oh, a bunch of stuff, really. PLEASE BE VERY CAREFUL ABOUT MAKING ANY CHANGES TO THIS FILE, AS IT WILL RENDER IT NON-COMPLIANT WITH THE STANDARD NOTED ABOVE. There is no need to modify this file for any settings. All settings can occur in fbx_settings.cfm.
	</responsibilities>
	<properties>
		<property name="version" value="3.01" />
		<property name="build" value="1b" />
		<history author="John Quarto-vonTivadar" date="27 Sep 2001" email="jcq@mindspring.com">Portions of code contributed by Steve Nelson, Hal Helms, Jeff Peters, Nat Papovich, Patrick McElhaney, Fred Sanders, Bill Wheatley and Stan Cox.</history>
		<history author="Nat Papovich" date="Oct 2001" email="mcnattyp@yahoo.com" type="Update">Converted to cfscripting, bug fixes for final release.</history>
		<history author="Nat Papovich" date="Nov 2001" email="mcnattyp@yahoo.com" type="Update" />
	</properties>
	<io>
		<in>
			<file action="module" path="fbx_savecontent.cfm" comments="new version of cf_bodycontent only required for CF versions less than 5.0" />
		</in>	
		<out>
			<structure name="fusebox" scope="variables" comments="this is the public API of variables that should be treated as read-only">
				<boolean name="isCustomTag" default="FALSE" />
				<boolean name="isHomeCircuit" default="FALSE" />
				<boolean name="isTargetCircuit" default="FALSE" />
				<string name="fuseaction" comments="will be assigned a literal value of 'fusebox.defaultfuseaction' if attributes.fuseaction comes in as 'circuit.' with no fuseaction passed." />
				<string name="circuit" />
				<string name="homeCircuit" />
				<string name="targetCircuit" />
				<string name="thisCircuit" />
				<string name="thisLayoutPath" />
				<boolean name="suppressErrors" default="FALSE" />
				<string name="currentPath" />
				<string name="rootPath" />
			</structure>
			<structure name="FB_" comments="Internal use only. Please treat the FB_ as a reserved structure, not to be touched.">
			</structure>
		</out>
	</io>
</fusedoc> 

--->

<cfscript>
/*******************************************************************************************
SECTION TWO
SECTION TWO
The fusebox structure below is a structure encompassing the public Fusebox API. We recommend making no changes to this structure as it will render your application non-compliant to the Fusebox 3.0 standard.

	fusebox.IsCustomTag:
The boolean variable is set by the Fusebox framework which will automatically determine if it is being called as a custom tag. Currently, Fusebox offers no expanded support for applications being called as Custom Tags. But you can programatically alter your application if it's being called as Custom Tag by checking the value of this variable. This can be helpful for changing (or removing altogether) layout files in fbx_layouts.cfm.
	fusebox.IsHomeCircuit:
This boolean variable is set and re-set as the Fusebox framework does its business pulling in fbx_settings.cfm files and the fbx_switch.cfm file and fbx_layouts.cfm files (and associated layout files). It is TRUE only when the currently accessed circuit is the home circuit running this application.
	fusebox.IsTargetCircuit:
Like isHomeCircuit above, this boolean variable is used during the process of cfincluding files. It is TRUE only when the currently accessed circuit is the target circuit running specified by circuit.fuseaction.
	fusebox.Circuit:
This is the first part of the compound fuseaction that gets passed as attributes.fuseaction.
	fusebox.Fuseaction:
This is the second part of a compound fuseaction that gets passed as attributes.fuseaction. fusebox.fuseaction is the variable expression evaluated in fbx_switch.cfm.
	fusebox.HomeCircuit:
This variable is set to the root-level circuit as defined in fusebox.circuits strucure.
	fusebox.TargetCircuit:
This is the circuit the requested fuseaction is to run in. The difference between this variable and fusebox.circuit above, is that this variable is the circuit alias that was found in the fusebox.circuits file as opposed to the circuit that is being attempted to be found. In all non-error situations fusebox.TargetCircuit and fusebox.Circuit will be the same.
	fusebox.ThisCircuit:
Like IsTargetCircuit and IsHomeCircuit above, this variable is set and re-set during the process of running the fbx_settings.cfm files and the fbx_switch.cfm file, and refers to the circuit alias of the circuit from which files are currently being accessed.
	fusebox.ThisLayoutPath:
This is the directory path that the layout file being used is called from. This variable changes as the layouts are nested one inside another, building the overall page layout.
	fusebox.suppressErrors:
A boolean variable, which defaults to FALSE. If TRUE, the Fusebox framework will attempt to give you "smarter" errors that may occur from within its own code as it applies to the Framework itself. If FALSE (default), you will receive the native CF error messages. During development you may want to turn this to TRUE and FALSE alternately to ensure you've got your framework set up properly. Set this to TRUE in a production enviroment, since at that point errors that occur will not be Fusebox framework errors but rahter erros in your fuseactions and fuses.	
	fusebox.Circuits:
This variable is a structure whose aliases are the circuit names created in fbx_circuits.cfm and whose values are the directory paths to those circuits.
	fusebox.currentPath:
This variable takes you from the root circuit to any location it is called. If you use images in directories beneath individual circuits, this variable will point to that circuit like "directory/directory/".
	fusebox.rootPath:
This variable takes you from the circuit it is being called from, back to the root. This is helpful to determine your location relative to the root application.
*******************************************************************************************/
fusebox = structNew();
if (findNoCase("cf_", "," & getBaseTagList()))
	fusebox.IsCustomTag=TRUE;
else fusebox.IsCustomTag=FALSE;
fusebox.IsHomeCircuit=FALSE;
fusebox.IsTargetCircuit=FALSE;
fusebox.fuseaction="";
fusebox.circuit="";
fusebox.HomeCircuit="";
fusebox.TargetCircuit="";
fusebox.thisCircuit="";
fusebox.thislayoutpath="";
fusebox.suppressErrors=FALSE;
fusebox.Circuits=structNew();
fusebox.currentPath="";
fusebox.rootPath="";

/*******************************************************************************************
SECTION THREE
FB_ is a structure encompassing "private" variables used by the underlying Fusebox framework. Make no changes to it without a full understanding of the ramifications of those changes. 
*******************************************************************************************/
FB_=structNew();

/*******************************************************************************************
SECTION FOUR
This code used to be in a Custom Tag called formURL2attributes.cfm. It copies all incoming FORM and URL variables to ATTRIBUTES scope.
*******************************************************************************************/
if (NOT IsDefined("attributes"))
    attributes=structNew();
fb_.fu2a=StructNew();
for(fb_.fu2a.urlcount=1; 
 fb_.fu2a.urlcount lte listlen(cgi.query_string,"&");
 fb_.fu2a.urlcount=fb_.fu2a.urlcount+1){
 	fb_.fu2a.valuepair=listgetat(cgi.query_string,fb_.fu2a.urlcount,"&");
	fb_.fu2a.urlname=listgetat(fb_.fu2a.valuepair,1,"="); 
	if (refindnocase("[[:alpha:]]",left(trim(fb_.fu2a.urlname),1)) 
	 and not isdefined("attributes." & fb_.fu2a.urlname))
		structinsert(attributes,fb_.fu2a.urlname,evaluate("url." & fb_.fu2a.urlname));}
if (isdefined("form.fieldnames")){
	for(fb_.fu2a.formcount=1; 
	 fb_.fu2a.formcount LTE ListLen(form.fieldnames);
	 fb_.fu2a.formcount=fb_.fu2a.formcount+1){
	 	fb_.fu2a.formfield=ListGetAt(form.fieldnames,fb_.fu2a.formcount);
		if (refindnocase("[[:alpha:]]",left(trim(fb_.fu2a.formfield),1)) and 
		 not isdefined("attributes." & trim(fb_.fu2a.formfield))){
		 	structinsert(attributes,trim(fb_.fu2a.formfield),evaluate("form.#trim(fb_.fu2a.formfield)#"));}}}
structdelete(fb_,"fu2a",0);
</cfscript>

<!------------------------------------------------------------------------------------------
SECTION FIVE
Attempt to include the fbx_circuits.cfm file, which should be in the same directory as this file.
------------------------------------------------------------------------------------------->
<cftry>
   <cfinclude template="fbx_Circuits.cfm"> 
	<cfcatch>
		<cfif fusebox.suppressErrors>
			<cfoutput>The Fusebox framework could not find the file fbx_Circuits.cfm. If you think this error is incorrect, turn off the Fusebox suppress error messages flag by setting fusebox.SuppressErrors to FALSE, and you will receive ColdFusion's "normal" error output.</cfoutput><cfabort>
		<cfelse><cfthrow message="#cfcatch.message#" detail="#cfcatch.detail#"><cfabort></cfif>
	</cfcatch>
</cftry>

<!------------------------------------------------------------------------------------------
SECTION SIX
Create a reverse path look-up of the fusebox.Circuits structure which can be used later to conveniently look up the circuit alias of whichever circuit is being accessed at that moment, particularly when determining fusebox.thisCircuit. 
------------------------------------------------------------------------------------------->
<cftry>
	<cfscript>
	FB_.ReverseCircuitPath=StructNew();
	for (aCircuitName in fusebox.Circuits){
		FB_.ReverseCircuitPath[fusebox.Circuits[aCircuitName]]=aCircuitName;
		if (ListLen(fusebox.Circuits[aCircuitName], "/") EQ 1){
			fusebox.HomeCircuit=aCircuitName;
			fusebox.IsHomeCircuit=TRUE;}}
	</cfscript>
	<cfcatch>
		<cfif fusebox.suppressErrors>
			<cfoutput>The circuits structure does not exist. This must be defined in the Fusebox application's root fbx_circuits.cfm file. If you think this error is incorrect, turn off the Fusebox suppress error messages flag by setting fusebox.SuppressErrors to FALSE, and you will receive ColdFusion's "normal" error output.</cfoutput><cfabort>
		<cfelse><cfthrow message="#cfcatch.message#" detail="#cfcatch.detail#"><cfabort></cfif>
	</cfcatch>
</cftry>

<!------------------------------------------------------------------------------------------
SECTION SEVEN
Attempt to include the fbx_settings.cfm file from the root directory, the same directory that this file is being run from.
------------------------------------------------------------------------------------------->
<cftry>
	<cfinclude template="fbx_Settings.cfm">
	<cfcatch>
		<cfif fusebox.suppressErrors>
			<cfoutput>The Fusebox framework could not find the file fbx_Settings.cfm. If you think this error is incorrect, turn off the Fusebox suppress error messages flag by setting fusebox.SuppressErrors to FALSE, and you will receive ColdFusion's "normal" error output.</cfoutput><cfabort>
		<cfelse><cfthrow message="#cfcatch.message#" detail="#cfcatch.detail#"><cfabort></cfif>
	</cfcatch>
</cftry>

<!------------------------------------------------------------------------------------------
SECTION EIGHT
Dissect attributes.fuseaction (available in the attributes scope either from being converted in the formURL2attributes section or via the above included fbx_settings.cfm file in the root directory). If attributes.fuseaction is not a compound fuseaction (i.e. it only includes the circuit in the form of "?fuseaction=circuit."), then set the fuseaction as blank, which the target circuit's CFDEFAULTCASE tag will pick up. Now look up the target circuit in the fusebox.circuit structure for the full path to the circuit.
------------------------------------------------------------------------------------------->
<cftry>
	<cfscript>
	FB_.rawFA = attributes.fuseaction; //preserve the original fuseaction
	if (ListLen(FB_.rawFA, '.') is 1 and Right(FB_.rawFA,1) is '.')
		//circuit only specified, no fuseaction such as "fuseaction=circuit."
		fusebox.fuseaction = "fusebox.defaultfuseaction";
	else
		fusebox.fuseaction = ListGetAt( FB_.rawFA, 2,  '.' );
	fusebox.circuit = ListGetAt( FB_.rawFA, 1, '.');
	fusebox.TargetCircuit=fusebox.circuit; //preserve for later
	</cfscript>
	<cfcatch>
		<!--- In this case, we want the core file custom error message to always display, since this code is solid, but the attributes.fuseaction may be incorrect and thus produce an error. --->
		<cfoutput>The variable "attributes.fuseaction" is not available or the Fusebox framework could not find the circuit you requested: "#fusebox.circuit#". If you think this error is incorrect, turn off the Fusebox suppress error messages flag by setting fusebox.SuppressErrors to FALSE, and you will receive ColdFusion's "normal" error output.<p><cfthrow message="#cfcatch.message#" detail="#cfcatch.detail#"></cfoutput><cfabort>
	</cfcatch>
</cftry>

<!------------------------------------------------------------------------------------------
SECTION NINE
Attempt to include any nested fbx_Settings.cfm files, in top-to-bottom order so that variables set in children fbx_settings.cfm files can overwrite variables set in higher fbx_settings.cfm files. To prevent children fbx_settings.cfm files from overwriting variables, use CFPARAM rather than CFSET. Alternately, any child fbx_settings.cfm can CFSET a variable and lower fbx_settings.cfm files cannot overwrite it unless they use CFSET. If any fbx_settings.cfm file or directory alias cannot be found, then continue on.
------------------------------------------------------------------------------------------->
<cfscript>
FB_.fullPath=ListRest(fusebox.Circuits[fusebox.TargetCircuit], "/"); //make a variable to hold the full path down to the target, excluding the root
FB_.Corepath=""; //initialize
fusebox.thisCircuit=fusebox.HomeCircuit; //current circuit, set to root now
</cfscript>
<cfloop list="#FB_.fullpath#" index="aPath" delimiters="/">
	<cfscript>
	FB_.Corepath=ListAppend(FB_.Corepath, aPath, "/"); //add the current circuit with / as delim
	fusebox.IsHomeCircuit=FALSE; //fbx_settings.cfm files included in this loop are not the home circuit because the home circuit's fbx_Settings is needed much earlier in the process
	fusebox.currentPath=FB_.Corepath & "/";
	fusebox.rootPath=repeatString("../", ListLen(fusebox.currentPath, '/'));
	</cfscript>
	<cftry>
		<cfif StructKeyExists(FB_.ReverseCircuitPath, fusebox.Circuits[fusebox.HomeCircuit] & "/" & FB_.CorePath)>
			<cfset fusebox.thisCircuit=FB_.ReverseCircuitPath[fusebox.Circuits[fusebox.HomeCircuit] & "/" & FB_.CorePath] > 
			<cfif fusebox.thisCircuit EQ fusebox.TargetCircuit>
				<cfset fusebox.IsTargetCircuit=TRUE>
			<cfelse>
				<cfset fusebox.IsTargetCircuit=FALSE>
			</cfif>
			<cfinclude template="#fusebox.currentPath#fbx_Settings.cfm"><!---include the actual file--->
		</cfif>
		<cfcatch>
			<cfif fusebox.suppressErrors><!--- suppressed error --->
			<cfelse><cfthrow message="#cfcatch.message#" detail="#cfcatch.detail#"></cfif>
		</cfcatch>		
	</cftry>
</cfloop>

<!------------------------------------------------------------------------------------------
SECTION TEN
Now "reach down" and include the fbx_switch.cfm in the target circuit, executing fusebox.fuseaction. Store the contents of the output into a variable called fusebox.layout.
------------------------------------------------------------------------------------------->
<cfscript>
fusebox.thisCircuit=fusebox.TargetCircuit;
fusebox.IsTargetCircuit= TRUE;
FB_.fuseboxpath=FB_.fullpath; //make directory path to the target circuit
if (Len(FB_.fuseboxpath)){
	//if the target circuit is NOT the root circuit
	FB_.fuseboxpath=FB_.fuseboxpath & "/";
	fusebox.IsHomeCircuit = FALSE;}
else
	fusebox.IsHomeCircuit = TRUE;
fusebox.currentPath=fb_.fuseboxpath;
fusebox.rootPath=repeatString("../", ListLen(fb_.fuseboxpath, '/'));
</cfscript>
<cftry>
 	<cf_fbx_savecontent variable="fusebox.layout">
		<cfoutput><cfinclude template="#FB_.fuseboxpath#fbx_Switch.cfm"></cfoutput><!---include the target fbx_switch.cfm file--->
 	</cf_fbx_savecontent> 
	<cfcatch>
		<cfif fusebox.suppressErrors>
			<cfoutput>I could not find #FB_.fuseboxpath#fbx_Switch.cfm (or one of its components such as an included fuse) in the "#fusebox.circuit#" circuit. If you think this error is incorrect, turn off the Fusebox suppress error messages flag by setting fusebox.SuppressErrors to FALSE, and you will receive ColdFusion's "normal" error output, which could be generated by a file included from fbx_switch.cfm.</cfoutput><cfabort>
		<cfelse><cfthrow message="#cfcatch.message#" detail="#cfcatch.detail#"><cfabort></cfif>
	</cfcatch>
</cftry> 

<!------------------------------------------------------------------------------------------
SECTION ELEVEN
Now handle the layouts, resolving them in bottom-to-top order to nest the circuits, if needed. Also set fusebox.thisCircuit equal to the circuit name of the current circuit the code is passing through, which will let any layout files in circuits know where they are. If attempting to include any layout file throws an error, then do nothing and continue on. This ENTIRE section and functionality of nesting layouts and controlling layouts via layout files is optional. If you do not have any layout files (fbx_layout.cfm), or only have a layout file in your root directory, everything will still work.
------------------------------------------------------------------------------------------->
<cfset FB_.circuitalias = fusebox.Circuits[fusebox.TargetCircuit] >
<cfset FB_.layoutpath = fusebox.Circuits[fusebox.TargetCircuit] >
<cfloop condition="Len(FB_.layoutpath) GT 0"> <!---loop as long as the layout path has more circuits to run--->
	<cfif StructKeyExists(FB_.ReverseCircuitPath, FB_.circuitalias)>
		<cftry>
			<cfset fusebox.thisCircuit = FB_.ReverseCircuitPath[FB_.circuitalias] >
			<cfcatch>
				<cfset fusebox.thisCircuit = "">
			</cfcatch>
		</cftry>
		<cfscript>
		if (fusebox.thisCircuit EQ fusebox.Targetcircuit) fusebox.IsTargetCircuit=TRUE;
			else fusebox.IsTargetCircuit=FALSE;
		if (fusebox.thisCircuit EQ fusebox.HomeCircuit)	fusebox.IsHomeCircuit=TRUE;
			else fusebox.IsHomeCircuit=FALSE;
		fusebox.ThisLayoutPath=ListRest(FB_.layoutpath,"/");
		if (Len(fusebox.thislayoutpath)) fusebox.thislayoutpath=fusebox.thislayoutpath & "/";
		fusebox.currentPath=fusebox.thislayoutpath;
		fusebox.rootPath=repeatString("../", ListLen(fusebox.thislayoutpath, '/'));
		</cfscript>
	 	<cftry> 
			<!--- include the fbx_Layouts.cfm file for this circuit which decides which layout file to use--->
			<cfinclude template="#fusebox.thislayoutpath#fbx_Layouts.cfm">
	 		<cfcatch>
				<cfset fusebox.layoutfile = ""><cfset fusebox.layoutdir = "">
			</cfcatch>
	 	</cftry>
		<cftry>
			<!--- attempt to include the actual layout file which was set by fbx_Layouts.cfm --->
			<cfif Len(fusebox.layoutfile)>
				<cf_fbx_savecontent variable="fusebox.layout">	
					<cfoutput><cfinclude template="#fusebox.thislayoutpath##fusebox.layoutdir##fusebox.layoutfile#"></cfoutput>
				</cf_fbx_savecontent>
			</cfif>
		<cfcatch>
			<cfif fusebox.suppressErrors>
				<cfoutput>I could not find the layoutfile #fusebox.thislayoutpath##fusebox.layoutdir##fusebox.layoutfile# specified by #fusebox.thislayoutpath#fbx_Layouts.cfm. If you think this error is incorrect, turn off the Fusebox suppress error messages flag by setting fusebox.SuppressErrors to FALSE, and you will receive ColdFusion's "normal" error output.</cfoutput><cfabort>
			<cfelse><cfthrow message="#cfcatch.message#" detail="#cfcatch.detail#"><cfabort></cfif>
		</cfcatch>
		</cftry>
	</cfif>
	<cfset FB_.layoutpath = ListDeleteAt(FB_.layoutpath, ListLen(FB_.layoutpath, "/"), "/")> <!---remove one level of the path--->
	<cfset FB_.circuitalias = ListDeleteAt(FB_.circuitalias, ListLen(FB_.circuitalias, "/"), "/")>
</cfloop>
<!--- Finally, output the totally-nested layout --->
<cfoutput>#trim(fusebox.layout)#</cfoutput>

<cfsetting enablecfoutputonly="no">