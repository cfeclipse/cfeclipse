/*
 * Created on Sep 22, 2004
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
package com.rohanclan.cfml.editors.contentassist;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.internal.utils.Assert;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.IDocument;

import com.rohanclan.cfml.dictionary.Value;
import com.rohanclan.cfml.editors.ICFDocument;
import com.rohanclan.cfml.util.ResourceUtils;

/**
 * This class provides attr/val content assist for cfinclude's template attribute.
 * It provides a list of templates to be included. 
 *
 * @author Oliver Tupman
 */
public class CFIncludeTemplateAssist implements IAssistAttrValueContributor {

    /**
     * Default constructor for this assist. Nothing to provide. 
     */
    public CFIncludeTemplateAssist() {
        super();
    }

    /**
     * Determines whether the assist will trigger or not.
     * 
     * @param state The current state of content assist
     * @return True - trigger, false - don't trigger
     */
    private boolean trigger(IAssistTagAttributeState state)
    {
        Assert.isNotNull(state);
        
        if(state.getTagName().compareToIgnoreCase("cfinclude") != 0)
            return false;
        
        if(state.getAttribute().compareToIgnoreCase("template") != 0)
            return false;
        
        return true;
    }
    
    /* (non-Javadoc)
     * @see com.rohanclan.cfml.editors.contentassistors.IAssistAttrValueContributor#getProposals(com.rohanclan.cfml.editors.contentassistors.IAssistTagAttributeState)
     */
    public Value[] getAttributeValueProposals(IAssistTagAttributeState state) {
        Assert.isNotNull(state, "Parameter state is null");

        if(!trigger(state))
            return null;
        
        IDocument doc = state.getIDocument();
        Set attrProps = new TreeSet();
        if(!(doc instanceof ICFDocument))
            return null;
        
		Set surroundingFiles;
		
		try {
		surroundingFiles = ResourceUtils.getIResourceSurroundingResources(
			((ICFDocument)doc).getResource(), 
			state.getValueSoFar()
		);
		} catch(Throwable ex) {
		    ex.printStackTrace();
		    return null;
		}
		
		Iterator fileIter = surroundingFiles.iterator();
		while(fileIter.hasNext()) {
			IResource res = (IResource)fileIter.next();
			String name = res.getName();
			name+= (res instanceof IFolder) ? "/" : "";
			attrProps.add(new Value(name));
		}
        return valueArrayFromSet(attrProps);
    }
    
    /**
     * Converts a set that contains Value objects into a Value array.
     * Copys every element from the set to the array.
     * 
     * @param sourceSet The source set of Value's. Will throw an assertion error if there is something other than a Value in this
     * @return The array of Value's
     */
    private Value[] valueArrayFromSet(Set sourceSet)
    {
        Assert.isNotNull(sourceSet, "Parameter sourceSet is null");
        
        Value retArray [] = new Value[sourceSet.size()];
        Iterator sourceIter = sourceSet.iterator();
        for(int i = 0; sourceIter.hasNext(); i++)
        {
            Object tempObj = sourceIter.next();
            
            Assert.isTrue(tempObj instanceof Value);
            
            retArray[i] = (Value)tempObj;
        }
        return retArray;
    }

}
