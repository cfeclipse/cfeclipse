<?xml version="1.0" encoding="utf-8"?>
<!--
	Author: Rob Rohan
	File: toVerboseDictionary.xslt
	Date: 2004-03-08
	Purpose: 
		take an easier-to-type dictionary and make it
		into the verbose dictionary that cfeclipse expects.
		aslo uses the file html_groups to replace defined goups
-->
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
>
	<xsl:output method="xml" indent="yes" encoding="UTF-8"/>
	
	<xsl:variable name="groups" select="document('/home/rob/html_groups.xml')" />
	
	<xsl:template match="/">
		<xsl:comment>
		This file is generated - please report bugs to 
		http://cfeclipse.tigris.org/ you can make changes to this file if
		you like, but it will be overwritten on the next version if you
		do not report the bug.
		</xsl:comment>
		<dictionary>
			<xsl:apply-templates />
		</dictionary>
	</xsl:template>
	
	<xsl:template match="tags">
		<!-- <xsl:copy-of select="." /> -->
		<xsl:apply-templates select="tag" />
		<!-- <xsl:call-template name="getGroup">
			<xsl:with-param name="id" select="'core'"/>
		</xsl:call-template> -->
	</xsl:template>
	
	<xsl:template match="tag">
		<tag creator="{@creator}" name="{@name}" single="{@single}" xmlstyle="{@xmlstyle}">
			<xsl:apply-templates select="help" />
			<xsl:apply-templates select="parameter" />
			<xsl:apply-templates select="addgroup" />
		</tag>
	</xsl:template>
	
	<xsl:template match="parameter">
		<parameter name="{@name}" type="{@type}" required="{@required}">
			<xsl:apply-templates select="help" />
			<xsl:apply-templates select="values" />
			<!-- <xsl:copy-of select="." /> -->
		</parameter>
	</xsl:template>
	
	<xsl:template match="help">
		<help><xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
		<xsl:value-of select="." />
		<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text></help>
	</xsl:template>
	
	<xsl:template match="values">
		<values>
			<xsl:apply-templates select="value" />
		</values>
	</xsl:template>
	
	<xsl:template match="value">
		<xsl:copy-of select="." />
	</xsl:template>
	
	<xsl:template match="addgroup">
		<xsl:variable name="groupid" select="@id" />
		<xsl:call-template name="getGroup">
			<xsl:with-param name="id" select="$groupid" />
		</xsl:call-template>
	</xsl:template>
	
	<!-- ////////////////////////////////////////////////////////// -->
	<!-- this gets a group from the groups xml to save a bit of
		typing. This process loses the cdata section. Might
		want to fix that in the future, but since we can't
		do html in eclipse tooltips, cdata is not needed right
		now
	 -->
	<xsl:template name="getGroup">
		<xsl:param name="id" select="'core'" />
		<!-- <xsl:variable name="frag" select="$groups/groups/group[@id = $id]/*" /> -->
		
		<!-- <xsl:for-each select="$frag">
			<xsl:copy-of select="." />
		</xsl:for-each> -->
		
		<xsl:copy-of select="$groups/groups/group[@id = $id]/*" />
	</xsl:template>
	
	<xsl:template match="text()" />
	
	
</xsl:stylesheet>
