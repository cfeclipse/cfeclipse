/*
 * Created on Sep 25, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver Tupman
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
package org.cfeclipse.cfml.editors.contentassist;

import java.util.ArrayList;

import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

/**
 * <p>
 * CFE has it's own method for content assist to allow a easier method of
 * contributing information to the user.
 * </p>
 * <p>
 * This class maintains a registry of all of the available Content Assist
 * Contributors (CACors). Currently the following CACor registration points
 * are available:
 * </p>
 * <p>
 * - Tag assist (user has typed a chevron)
 * - Attribute assist (user is working on a tag and is now after attributes
 * - Attribute value assist (user is now working within an attribute)
 * </p>
 * <p>
 * Clients can register their interest to provide assistance at runtime.
 * In the future the best way to register CACors will be via the extension
 * points that CFE will provide.
 * </p>
 * 
 * <p>
 * Duplicate CACors are not allowed. Registering an already-registered CACor 
 * will result in the original being replaced.
 * </p>
 * 
 * <strong>WARNING:</strong> The content assist interfaces are likey to 
 * change in the near future as the system is fine-tuned. 
 *
 * @author Oliver Tupman
 */
public class CFEContentAssistManager {

    /** The tag assistors */
    private ArrayList tagAssistors;
    /** The attribute assistors */
    private ArrayList attrAssistors;
    /** The attribute value assistors  */
    private ArrayList valueAssistors;
    /** 
     * <p>
     * A root assistor is an instance of a class that implements the standard
     * Eclipse content assist interface OR IAssistContributor. These contributors
     * will be queried when a trigger char is typed.
     * </p>
     * <p>
     * For example the root assistors are the standard CFML assistor that provides
     * tag, tag attribute and tag attr/value insight.
     * </p>
     * <p>
     * Other contributors could well be generic, expression-based contributors that
     * wish to provide some sort of support for other insight. An example of this is
     * the scope-support. Entering a period after 'application' will cause the 
     * scope-insight to be displayed.
     * </p>
     */ 
    private ArrayList rootAssistors;
    
    
    /**
     * Constructs a CAM with no contributors registered.
     */
    public CFEContentAssistManager() {
        this.tagAssistors = new ArrayList();
        this.attrAssistors = new ArrayList();
        this.valueAssistors = new ArrayList();
        this.rootAssistors = new ArrayList();
    }
    
    /**
     * Registers and possibly replaces an assistor
     * 
     * @param list The list that we are storing the assistors in
     * @param newAssistor The new assistor
     */
    private void registerAssistor(ArrayList list, Object newAssistor)
    {
        //Assert.isNotNull(list,"CFEContentAssist::registerAssistor()");
        //Assert.isNotNull(newAssistor,"CFEContentAssist::registerAssistor()");
        if(list == null || newAssistor == null)
        		throw new IllegalArgumentException("CFEContentAssist::registerAssistor()");
        
        int cPos = list.indexOf(newAssistor);
        
        if(cPos != -1)
        {
            list.set(cPos, newAssistor);
        }
        else
            list.add(newAssistor);
        
    }
    
    /**
     * Gets the list of registered tag assistors 
     * @return ArrayList containing the assistors
     */
    public ArrayList getTagAssistors()
    {
        //Assert.isNotNull(this.tagAssistors,"CFEContentAssist::getTagAssistors()");
        if(this.tagAssistors == null)
    			throw new IllegalArgumentException("CFEContentAssist::getTagAssistors()");
        return this.tagAssistors;
    }
    
    /**
     * Gets the list of registered tag attribute assistors 
     * @return ArrayList containing the assistors
     */
    public ArrayList getAttributeAssistors()
    {
        //Assert.isNotNull(this.attrAssistors,"CFEContentAssist::getAttributeAssistors()");
        if(this.tagAssistors == null)
			throw new IllegalArgumentException("CFEContentAssist::getTagAssistors()");
        return this.attrAssistors;
    }
    
    /**
     * Gets the list of registered tag attribute value assistors 
     * @return ArrayList containing the assistors
     */
    public ArrayList getValueAssistors()
    {
        //Assert.isNotNull(this.valueAssistors,"CFEContentAssist::getValueAssistors()");
        if(this.valueAssistors == null)
			throw new IllegalArgumentException("CFEContentAssist::getValueAssistors()");
        return this.valueAssistors;
    }
    /**
     * <p>
     * The primary assistant for CF docs will call each of these for
     * every trigger char entered by the user that invokes content assist.
     * </p>
     * <p>
     * Please note that it is recommended that classes implement the
     * CFE-internal IAssistContributor interface rather than 
     * IContentAssistProcessor. This allows primary assist to pass the
     * state object that it has already calculated therefore saving
     * some processing cycles.
     * </p>
     * @return The ArrayList of root assistors which will be of either IContentAssistProcessor or IAssistContributor
     */
    public ArrayList getRootAssistors()
    {
        //Assert.isNotNull(this.rootAssistors,"CFEContentAssist::getRootAssistors()");
        if(this.rootAssistors == null)
			throw new IllegalArgumentException("CFEContentAssist::getRootAssistors()");
        return this.rootAssistors;
    }
    
    /**
     * <p>
     * Registers a root assistant, one that handles all of it's own
     * state computation and proposal gathering.
     * </p>
     * <p>
     * The primary assistant for CF docs will call each of these for
     * every trigger char entered by the user that invokes content assist.
     * </p>
     * <p>
     * Please note that it is recommended that classes implement the
     * CFE-internal IAssistContributor interface rather than 
     * IContentAssistProcessor. This allows primary assist to pass the
     * state object that it has already calculated therefore saving
     * some processing cycles.
     * </p>
     * @param rootAssistant The new assistant to register
     */
    public void registerRootAssist(IContentAssistProcessor rootAssistant)
    {
        registerAssistor(this.rootAssistors, rootAssistant);
    }
    
    /**
     * <p>
     * Registers a root assistant, one that handles all of it's own
     * state computation and proposal gathering.
     * </p>
     * <p>
     * The primary assistant for CF docs will call each of these for
     * every trigger char entered by the user that invokes content assist.
     * </p>
     * <p>
     * The difference between this and registerRootAssist(IContentAssistProcessor)
     * is that the primary assistant will fill the IAssistState object and will
     * therefore save some processing power. 
     *  </p>
     * @param rootAssistor The new assistant to register
     */
    public void registerRootAssist(IAssistContributor rootAssistor)
    {
        registerAssistor(this.rootAssistors, rootAssistor);
    }
    
    public void registerTagAssist(IAssistContributor newAssistor)
    {
        registerAssistor(this.tagAssistors, newAssistor);
    }
    
    public void registerAttributeAssist(IAssistTagContributor newAssistor)
    {
        registerAssistor(this.attrAssistors, newAssistor);
    }
    
    public void registerValueAssist(IAssistAttrValueContributor newAssistor)
    {
        registerAssistor(this.valueAssistors, newAssistor);
    }

}
