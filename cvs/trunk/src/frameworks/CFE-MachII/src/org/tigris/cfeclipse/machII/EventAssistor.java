/*
 * Created on Oct 13, 2004
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
package org.tigris.cfeclipse.machII;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.contentassist.ICompletionProposal;

import com.rohanclan.cfml.editors.CFPartitionScanner;
import com.rohanclan.cfml.editors.ICFEFileDocument;
import com.rohanclan.cfml.editors.contentassist.AssistUtils;
import com.rohanclan.cfml.editors.contentassist.IAssistContributor;
import com.rohanclan.cfml.editors.contentassist.IAssistState;
import com.rohanclan.cfml.preferences.ICFMLPreferenceConstants;
import com.rohanclan.cfml.util.CFDocUtils;

/**
 * TODO Provide class description 
 *
 * @author Oliver Tupman
 */
public class EventAssistor implements IAssistContributor {

    /**
     * 
     */
    public EventAssistor() {
        super();
    }

    /* (non-Javadoc)
     * @see com.rohanclan.cfml.editors.contentassist.IAssistContributor#getTagProposals(com.rohanclan.cfml.editors.contentassist.IAssistState)
     */
    public ICompletionProposal[] getTagProposals(IAssistState state) {
        if(!(AssistUtils.isCorrectPartitionType(state.getITextView(), state.getOffset(), CFPartitionScanner.CF_SCRIPT)
           || AssistUtils.isCorrectPartitionType(state.getITextView(), state.getOffset(), CFPartitionScanner.CF_TAG)
           || AssistUtils.isCorrectPartitionType(state.getITextView(), state.getOffset(), CFPartitionScanner.ALL_TAG)
           || AssistUtils.isCorrectPartitionType(state.getITextView(), state.getOffset(), CFPartitionScanner.UNK_TAG)
           || AssistUtils.isCorrectPartitionType(state.getITextView(), state.getOffset(), CFPartitionScanner.TABLE_TAG)))
           return null;
        
        // &event= | equals is the trigger char
        
        Pattern regex = Pattern.compile("&event\\s*=\\s*(\\w*)$");
        Matcher matcher = regex.matcher(state.getIDocument().get());
        
        System.out.println("Here!");
        return null;
    }

}
