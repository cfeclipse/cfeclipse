/*
 * Created on Apr 5, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package com.rohanclan.cfml.editors;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.contentassist.CFEContentAssistManager;
import com.rohanclan.cfml.editors.contentassist.IAssistAttrValueContributor;
import com.rohanclan.cfml.editors.contentassist.IAssistContributor;
import com.rohanclan.cfml.editors.contentassist.IAssistTagContributor;
import com.rohanclan.cfml.natures.CFENature;
import com.rohanclan.cfml.natures.IContentAssistContributorNature;
import com.rohanclan.cfml.parser.CFDocument;
import com.rohanclan.cfml.parser.CFNodeList;
import com.rohanclan.cfml.parser.CFParser;
import com.rohanclan.cfml.parser.CommentParser;
import com.rohanclan.cfml.parser.docitems.CfmlTagItem;
import com.rohanclan.cfml.preferences.CFMLPreferenceConstants;
import com.rohanclan.cfml.preferences.ParserPreferenceConstants;
import com.rohanclan.cfml.util.ResourceUtils;
import com.rohanclan.cfml.external.ExternalFile;
import com.rohanclan.cfml.editors.partitioner.CFEPartition;
import org.eclipse.jface.text.Position;

/**
 * <p>
 * This is mostly just a wrapper document that holds the parser and is
 * attached to a Document. So if you can get access to a document you
 * can get to this and get the parser.
 * </p>
 * <p>
 * If you have an IDocument you can perform the following to get an ICFDocument:
 * </p>
 * <pre>
 * \/\/
 * if(doc instanceof ICFDocument)
 * { 
 *   ICFDocument cfDoc = (ICFDocument)doc;
 *   ... perform operations ...
 * </pre>
 * <p>
 * With an ICFDocument class you are able to access the parse result.
 * </p>
 * @author Rob
 */
public class ICFDocument extends Document implements ICFEFileDocument {

    /** The parser that was/is run over this document */
	protected CFParser docParser = null;
	/** The resource that this document is derived from. Required for the parser */
	protected IResource lastRes = null;
	/** Comment parser - parses the TODOs. Will one day be put into the CFParser */
	private CommentParser commentParser = new CommentParser();
	/** ?? */
	private static Thread t;
	/** The parse result of the document. NB: can be null if there were errors */
	private CFDocument docStructure = null;
	
	/**
     * The private CAM for this document.
     * NB: Not used as it's a possible solution for per-project frameworks
     * (and therefore per-project CACors)
     */
    private CFEContentAssistManager docCAM;
	
    /** Represents the time at which the CAM was last refreshed */
    private Date lastCAMRefresh;
    
	public ICFDocument()
	{
		super();
		this.docCAM = null;
	}
	
	/**
	 * Gets the parser for this document.
	 * 
	 * @return An instance of a parser.
	 */
	public CFParser getParser()
	{
		return docParser;
	}
	
	/**
	 * Calls the parser and runs it over this document.<br/>
	 * It then calls the comment parser.
	 * 
	 */
	public void parseDocument()
	{
		if(docParser != null)
		{
			
		    IPreferenceStore prefStore = CFMLPlugin.getDefault().getPreferenceStore();
			docParser.setCFScriptParsing(prefStore.getBoolean(ParserPreferenceConstants.P_PARSE_DOCFSCRIPT));
			docParser.setReportErrors(prefStore.getBoolean(ParserPreferenceConstants.P_PARSE_REPORT_ERRORS));
			docStructure = docParser.parseDoc();
			
			commentParser.ParseDocument(this,lastRes);
			commentParser.setTaskMarkers();
			
			if(docStructure == null)
				System.err.println(
					"ICFDocument::parseDocument() - Parse result is null!"
				);
		}
	}
	
	/**
	 * Gets a tag object given a starting and ending position
	 * @param startpos
	 * @param endpos
	 * @return
	 */
	public CfmlTagItem getTagAt(int startpos, int endpos)
	{

	    return getTagAt(startpos,endpos,false);
	}

	
	/**
	 * Gets a tag object given a starting and ending position
	 * @param startpos
	 * @param endpos
	 * @param includeClosingTags
	 * @return
	 */
	public CfmlTagItem getTagAt(int startpos, int endpos, boolean includeClosingTags)
	{
		//build the xpath
		String attrString = "[#startpos<" + startpos + " and #endpos>" + endpos + "]";
		//System.out.println(attrString);
		CFDocument docRoot = getCFDocument();
		try {
			CFNodeList matchingNodes = docRoot.getDocumentRoot().selectNodes(
				"//*" + attrString
				,includeClosingTags
			);
			
			//there should only be 0 or 1 nodes in any one position (unless it spans
			//more then one tag I suppose
			if(matchingNodes.size() > 0)
			{
				return (CfmlTagItem)matchingNodes.get(0);
			}
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets the tag name at the given position (i.e. include,set,module,etc)
	 * @param startpos
	 * @param endpos
	 * @return
	 */
	public String getTagNameAt(int startpos, int endpos)
	{
		String str = null;
		CfmlTagItem cti = getTagAt(startpos,endpos);
		
		if(cti != null)
		{
			return cti.getName();
		}
		
		return null;
	}
	
	/**
	 * Sets the resource that is associated with this document.<br/>
	 * It also resets the parser for this document. 
	 * 
	 * @param newRes The resource that represents this document.
	 */
	public void setParserResource(IResource newRes)
	{
	    this.lastRes = newRes;
		if(docParser == null)
		{
			docParser = new CFParser(this, lastRes);
			IPreferenceStore prefStore = CFMLPlugin.getDefault().getPreferenceStore();
			docParser.setCFScriptParsing(prefStore.getBoolean(ParserPreferenceConstants.P_PARSE_DOCFSCRIPT));
		}
		else
		{
			docParser.parseDoc(this);
		}
	}
	
	/**
	 * Retrieves the IResource that this document is based upon
	 * @return the resource
	 */
	public IResource getResource() {
		return this.lastRes;
	}
	
	/**
	 * Sets the resource that is associated with this document.<br/>
	 * It also resets the parser for this document. 
	 * 
	 * @param newRes The resource that represents this document.
	 */
	public void setResource(IResource newRes)
	{
	    this.setParserResource(newRes);
	}
	
	/**
	 * Clears all of the markers (i.e. problem, todo, etc) that are associated
	 * with this document.
	 *
	 */
	
	public void clearAllMarkers()
	{
	    
		try
		{
		    
			lastRes.deleteMarkers(
					IMarker.PROBLEM, true, IResource.DEPTH_ONE
				);
			

			lastRes.deleteMarkers(
			        "com.rohanclan.cfml.todomarker", true, IResource.DEPTH_ONE
				);
			

		}
		catch(CoreException ce)
		{
			ce.printStackTrace(System.err);
		}
		catch (Exception e) {
		    //e.printStackTrace();
		}
	}
	
	
	/**
	 * Gets the parse result of this document.
	 * @return If not null, the parse result, if null there was a parse error.
	 */
	public CFDocument getCFDocument()
	{
		return docStructure;
	}
	
	/**
	 * Retrieves the Content Assist Manager (CAM) for this document. This is the 
     * manager for the CACors that are associated with this document.
     * 
     * The current implementation of this simply retrieves the CFE-global CAM
     * as project natures are not yet implemented. Once implemented this will work
     * in the default manner.
	 * 
	 * @see com.rohanclan.cfml.editors.ICFEFileDocument
	 */
    public CFEContentAssistManager getContentAssistManager() {
        if (this.getResource() instanceof ExternalFile) {
            return CFMLPlugin.getDefault().getGlobalCAM();
        }
		if(this.lastRes == null)
		    return CFMLPlugin.getDefault().getGlobalCAM();

		CFEContentAssistManager returnManager = null;
		CFENature nature;		    
		try {
		    if(!ResourceUtils.hasNature(this.lastRes.getProject(), CFMLPlugin.NATURE_ID))
		        return CFMLPlugin.getDefault().getGlobalCAM();
		    
		    nature = (CFENature)lastRes.getProject().getNature(CFMLPlugin.NATURE_ID);
		    
		    //returnManager = nature.getNatureCAM();
		    returnManager = getCAM();
		} catch(CoreException ex) {
		    ex.printStackTrace();
		    
		}
		return returnManager;
    }
    
    protected CFEContentAssistManager getCAM()
    {
        try {
            refreshCAM();
        }
        catch(CoreException ex)
        {
            ex.printStackTrace();
        }
        return this.docCAM;
    }
    
    /**
     * Recreates the document's CAM based upon the natures that provide content
     * assist.
     * 
     * @param natureIter Iterator of a collection of IContentAssistContributorNature's
     */
    private void refreshCAMFromList(Iterator natureIter)
    {
        this.docCAM = new CFEContentAssistManager();
        while(natureIter.hasNext())
        {
            CFEContentAssistManager currManager = ((IContentAssistContributorNature)natureIter.next()).getNatureCAM();
            Iterator currIter = currManager.getRootAssistors().iterator();
            while(currIter.hasNext())
            {
                Object currRoot = currIter.next();
                if(currRoot instanceof IAssistContributor)
                    this.docCAM.registerRootAssist((IAssistContributor)currRoot);
                else if(currRoot instanceof IContentAssistProcessor)
                    this.docCAM.registerRootAssist((IContentAssistProcessor)currRoot);
            }
            
            currIter = currManager.getTagAssistors().iterator();
            while(currIter.hasNext())
            {
                this.docCAM.registerTagAssist((IAssistContributor)currIter.next());
            }
            
            currIter = currManager.getAttributeAssistors().iterator();
            while(currIter.hasNext())
                this.docCAM.registerAttributeAssist((IAssistTagContributor)currIter.next());
            
            currIter = currManager.getValueAssistors().iterator();
            while(currIter.hasNext())
                this.docCAM.registerValueAssist((IAssistAttrValueContributor)currIter.next());
        }
    }
    
    private void refreshCAM() throws CoreException
    {
        if(this.docCAM == null)
        {
            this.docCAM = new CFEContentAssistManager();
            this.lastCAMRefresh = new Date(0);	// Init last refresh date to the epoch
        }
        IProject project = this.lastRes.getProject();
        IProjectNature [] natures = ResourceUtils.getProjectNatures(project);
        ArrayList cacNatures = new ArrayList();
        
        boolean needUpdate = false;
        
        for(int i = 0; i < natures.length; i++)
        {
            if(!(natures[i] instanceof IContentAssistContributorNature))
                continue;
            
            IContentAssistContributorNature cacorNature = (IContentAssistContributorNature)natures[i];
            
            if(cacorNature.getLastUpdateDate().after(this.lastCAMRefresh))
            {
                needUpdate = true;
            }
            cacNatures.add(cacorNature);
        }
        
        if(needUpdate)
        {
            refreshCAMFromList(cacNatures.iterator());
        }
        
        this.lastCAMRefresh = new Date();
    }
    
    /**
     * Refreshes this document's CAM from the framework natures associated
     * with the project that this document belongs to.
     *
     * The idea is that each document has it's own CAM. Or perhaps a project
     * should have it's own CAM. Unsure at the moment. This is only a
     * possible solution.
     * 
     * Basically when a framework nature is associated with a project it
     * also registers itself under the naturelist property of that project.
     * The naturelist property is simply a colon-separated list of project
     * natures (is there a way to get a list of natures associated with
     * a project?). From this list (or a proper method) it goes through
     * each asking them for their CACs that they provide (if any).
     * 
     * Each CAC is added to the doc's CAM, ready for access by an editor.
     * I don't believe we can do it any other way if we wish to support
     * multiple frameworks within the same project. Though we could
     * perhaps implement a IProject or extend whatever Eclipse uses and
     * have the CAM stored in the project.
     * 
     * Alternatively every time a nature is associated with a project 
     * we index that along side the project. The plugin maintains the
     * CAM for each project and requests for the CAM work in a simliar
     * manner as the access to the global CAM at the moment.
     *
     */
/*    
    private void refreshCAM()
    {
        docCAM = new CFEContentAssistManager();
        try {
            IProject docPrj = this.getResource().getProject();
            QualifiedName name = new QualifiedName("com.rohanclan.cfml.props", "naturelist");
            String frameworkNatures = docProj.getPersistentProperty(name);
            String frameworksNames [] = frameworkNatures.split(":");
            for(int fIndex = 0; fIndex < frameworksNames.length; fIndex++)
            {
                ICFEProjectNature fNature = (ICFEProjectNature)docProj.getNature(frameworksNames[fIndex]);
                if(fNature.getAttributeCAC() != null)
                    this.docCAM.registerAttributeAssist(fNature.getAttributeCAC());
                if(fNature.getTagCAC() != null)
                    this.docCAM.registerTagAssist(fNature.getTagCAC());
                if(fNature.getAttrValCAC() != null)
                    this.docCAM.registerValueAssist(fNature.getAttrValCAC());
            }
        }catch(CoreException ex)
        {
            ex.printStackTrace();
        }        
    }
*/    
}
