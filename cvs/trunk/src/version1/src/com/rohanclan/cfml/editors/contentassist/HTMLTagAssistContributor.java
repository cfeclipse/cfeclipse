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
package com.rohanclan.cfml.editors.contentassist;

import org.eclipse.jface.text.contentassist.ICompletionProposal;

import com.rohanclan.cfml.dictionary.Parameter;
import com.rohanclan.cfml.dictionary.SyntaxDictionary;
import com.rohanclan.cfml.dictionary.Value;
import com.rohanclan.cfml.editors.CFPartitionScanner;

/**
 * TODO Provide class description 
 *
 * @author Oliver Tupman
 */
public class HTMLTagAssistContributor extends DefaultTagAssistContributor {

   
    
    public ICompletionProposal[] getTagProposals(IAssistState state) {
        if(!DefaultTagAssistContributor.isCorrectPartition(state, CFPartitionScanner.UNK_TAG))
            return null;
        
        return super.getTagProposals(state);
    }
    public Value[] getAttributeValueProposals(IAssistTagAttributeState state) {
        if(!DefaultTagAssistContributor.isCorrectPartition(state, CFPartitionScanner.ALL_TAG))
            return null;
       
        return super.getAttributeValueProposals(state);
    }
    public Parameter[] getAttributeProposals(IAssistTagState state) {
        if(!DefaultTagAssistContributor.isCorrectPartition(state, CFPartitionScanner.ALL_TAG))
            return null;

        return super.getAttributeProposals(state);
    }
    /**
     * @param sourceDictionary
     */
    public HTMLTagAssistContributor(SyntaxDictionary sourceDictionary) {
        super(sourceDictionary);
    }

}
