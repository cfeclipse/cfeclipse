/*
 * Created on 	: 10-Sep-2004
 * Created by 	: Administrator
 * File		  	: CFENature.java
 * Description	:
 * 
 */
package com.rohanclan.cfml.natures;

import java.util.Date;

import org.eclipse.core.internal.utils.Assert;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.editors.cfscript.CFScriptCompletionProcessor;
import com.rohanclan.cfml.editors.contentassist.CFContentAssist;
import com.rohanclan.cfml.editors.contentassist.CFEContentAssistManager;
import com.rohanclan.cfml.editors.contentassist.CFMLFunctionAssist;
import com.rohanclan.cfml.editors.contentassist.CFMLScopeAssist;
import com.rohanclan.cfml.editors.contentassist.CFMLTagAssist;
import com.rohanclan.cfml.editors.contentassist.HTMLTagAssistContributor;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CFENature implements IContentAssistContributorNature {

    public Date getLastUpdateDate() {
        Assert.isNotNull(this.lastUpdate);
        return this.lastUpdate;
    }
    /** The project that this nature is associated with */
    private IProject project;
    /** The Content Assist Manager for this project */
    private CFEContentAssistManager camInstance;

    private Date lastUpdate;
    
    public CFENature()
    {
        System.out.println("CFENature::CFENature() - Nature created");
        setupCAM();
    }
    
    public CFEContentAssistManager getNatureCAM()
    {
        return this.camInstance;
    }
    
    /**
     * Setups up the Content Assist Manager
     * 
     */
    private void setupCAM() {
        this.camInstance = new CFEContentAssistManager();
        
        CFMLTagAssist cfmlAssistor = new CFMLTagAssist(DictionaryManager.getDictionary(DictionaryManager.CFDIC));
        HTMLTagAssistContributor htmlAssistor = new HTMLTagAssistContributor(DictionaryManager.getDictionary(DictionaryManager.HTDIC));

        CFScriptCompletionProcessor cfscp = new CFScriptCompletionProcessor();
		cfscp.changeDictionary(DictionaryManager.JSDIC);
        
		this.camInstance.registerRootAssist(cfscp);
        this.camInstance.registerRootAssist(new CFContentAssist());
        this.camInstance.registerRootAssist(new CFMLScopeAssist());
        this.camInstance.registerRootAssist(new CFMLFunctionAssist());

        this.camInstance.registerTagAssist(cfmlAssistor);
        this.camInstance.registerAttributeAssist(cfmlAssistor);
        this.camInstance.registerValueAssist(cfmlAssistor);

        this.camInstance.registerTagAssist(htmlAssistor);
        this.camInstance.registerAttributeAssist(htmlAssistor);
        this.camInstance.registerValueAssist(htmlAssistor);
        
        this.camInstance.registerTagAssist(new CFMLScopeAssist());
        
        this.lastUpdate = new Date();
    }
    
    public void configure() throws CoreException {
       // Add nature-specific information
       // for the project, such as adding a builder
       // to a project's build spec.
        System.out.println("CFENature::CFENature() - Nature configured");
    }
    public void deconfigure() throws CoreException {
       // Remove the nature-specific information here.
        System.out.println("CFENature::CFENature() - Nature deconfigured");
    }
    public IProject getProject() {
        System.out.println("CFENature::CFENature() - Project retrieved");
       return this.project;
    }
    public void setProject(IProject value) {
        System.out.println("CFENature::CFENature() - Project set");
       this.project = value;
    }
    
    public static void removeNature(IProject proj) {
        // placeholder 
    }
    
    public static void applyNature(IProject proj) throws CoreException {
        // placeholder 
    }
    
    public static boolean hasCFENature(IProject proj) throws CoreException { 
        return false;
    }
 }
