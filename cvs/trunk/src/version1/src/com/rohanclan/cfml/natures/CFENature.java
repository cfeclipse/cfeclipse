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
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.rohanclan.cfml.CFMLPlugin;
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
    
    /**
     * Removes the CFE nature from a project.
     * If the project does not have the nature it simply returns.
     * 
     * @param project Project to remove the nature from.
     * @throws CoreException
     */
    public static void removeNature(IProject project)
    	throws CoreException
    {
        IProjectDescription description = project.getDescription();
        String[] natures = description.getNatureIds();
        
        if(natures.length == 0)
            return;
        
        if(!hasCFENature(project))
            return;
        
        String[] newNatures = new String[natures.length - 1];
        
        for(int i = 0, newNaturePos = 0; i < natures.length; i++)
        {
            if(!natures[i].equals(CFMLPlugin.NATURE_ID))
            {
                newNatures[newNaturePos] = natures[i];
                newNaturePos++;
            }
        }
        description.setNatureIds(newNatures);
        project.setDescription(description, null);
    }
    
    /**
     * Applies the CFE nature to the supplied project.
     * 
     * If the nature is already applied it will return.
     * 
     * @param project2ApplyTo The project to apply the nature to.
     * @throws CoreException
     */
    public static void applyNature(IProject project2ApplyTo) 
    	throws CoreException
    {
        IProjectDescription description = project2ApplyTo.getDescription();
        String[] natures = description.getNatureIds();
        
        for(int i = 0; i < natures.length; i++)
        {
            if(natures[i].equals(CFMLPlugin.NATURE_ID))
                return;
        }
        
        String[] newNatures = new String[natures.length + 1];
        System.arraycopy(natures, 0, newNatures, 0, natures.length);
        newNatures[natures.length] = CFMLPlugin.NATURE_ID;
        description.setNatureIds(newNatures);
        project2ApplyTo.setDescription(description, null);
    }
    
    /**
     * Checks whether a project has the CFE nature applied to it.
     * 
     * @param project2Check The project to check
     * @return true - nature applied, false - not yet
     * @throws CoreException 
     */
    public static boolean hasCFENature(IProject project2Check)
    	throws CoreException
    {
        IProjectDescription description = project2Check.getDescription();
        String[] natures = description.getNatureIds();
        
        for(int i = 0; i < natures.length; i++)
        {
            if(natures[i].equals(CFMLPlugin.NATURE_ID))
                return true;
        }
        return false;
    }
 }
