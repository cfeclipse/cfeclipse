/*
 * Created on May 06, 2006
 *
 * The MIT License
 * Copyright (c) 2006 Mark Drew
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.cfeclipse.cfml.dictionary.Function;
import org.cfeclipse.cfml.dictionary.Parameter;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.FunctionInfo;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;


/**
 * Provides "arguments" assistance when you are in a CFC. It should provide you a list of the arguments you have set.
 * 
 * @author Mark Drew
 */
public class CFMLArgumentAssist extends AssistContributor 
	   		 implements IAssistContributor 
{
    
    
    /**
     * 
     */
    public CFMLArgumentAssist() {
    }

    /* (non-Javadoc)
     * @see org.cfeclipse.cfml.editors.contentassist.IAssistContributor#getTagProposals(org.cfeclipse.cfml.editors.contentassist.IAssistState)
     */
	public ICompletionProposal[] getTagProposals(IAssistState state) {

		Set<Parameter> params = null; 
    	if (state.getDataSoFar().toLowerCase().matches(".*?arguments\\.[\\w]*$")) {
			CFDocument doc = ((ICFDocument) state.getIDocument()).getCFDocument();
			// get a reference to the containing function
			CfmlTagItem cti = getPreviousFunctionTag(state);
			Function func = doc.getFunctionByName(cti.getAttributeValue("name"));
			if (func != null) {
				params =  func.getParameters();			
				return prepareProposals(state, params);
			} else {
				return null;
			}
    	} else {
			try {
				if (state.getIDocument().getPartition(state.getOffset()).getType().equals(CFPartitionScanner.CF_SCRIPT)) {
					CFDocument doc = ((ICFDocument) state.getIDocument()).getCFDocument();
					CFNodeList funknodes = doc.getDocumentRoot().selectNodes(
							"//ASTFunctionDeclaration[#startpos<" + state.getOffset() + "]");
					if (funknodes.size() > 0) {
						FunctionInfo funknode = (FunctionInfo) funknodes.get(funknodes.size() - 1);
						Function func = doc.getFunctionByName(funknode.getFunctionName());
						if (func != null) {
							params = func.getParameters();
							return prepareProposals(state, params);
						} else {
							return null;
						}
					}
					return null;
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
    		return null;
    	}
				    	
	}

	private ICompletionProposal[] prepareProposals(IAssistState state,
			Set<Parameter> params) {
			
			if (params == null) {
				return null;
			}
		
			Set<CompletionProposal> proposals = new HashSet<CompletionProposal>();
			String prefix = determinePrefix(state);			  
			
			for (Parameter parameter : params) {
				String replacementString = parameter.getName().replace(prefix, "");
				CompletionProposal proposal = new CompletionProposal(parameter.getName(), state.getOffset() - prefix.length(), prefix.length(),
					prefix.length() + replacementString.length());
				if (parameter.getName().toLowerCase().startsWith(prefix.toLowerCase())) {
					proposals.add(proposal);
				}
			}
			
			return (ICompletionProposal[]) proposals.toArray(new ICompletionProposal[]{});		
	}
	
	private String determinePrefix(IAssistState state) {
		
		int lastIndexOfDot = state.getDataSoFar().lastIndexOf(".");
		return state.getDataSoFar().substring(lastIndexOfDot+1);
	}

	/**
	 * Gets the tag object prior to specified position
	 * @param startpos
	 * @param endpos
	 * @param includeClosingTags
	 * @return
	 */
	private CfmlTagItem getPreviousFunctionTag(IAssistState state) {

		CFDocument doc = ((ICFDocument) state.getIDocument()).getCFDocument();		
		CfmlTagItem closestItem = null;
		
		// there might be a parse error with the document itself, which nullifies this
		if(doc == null) {
			return null;
		}
		
		try {
			CFNodeList matchingNodes = doc.getDocumentRoot().selectNodes(
				"//cffunction"
			);
			int lineFound = 0;

			Iterator i = matchingNodes.iterator();
			while (i.hasNext()) {
				DocItem node = (DocItem) i.next();
				if (node instanceof CfmlTagItem) {
					CfmlTagItem currItem = (CfmlTagItem) node;
					if (lineFound == 0 ||
							(currItem.getEndPosition() < state.getOffset() && currItem.getLineNumber() > lineFound)) {
						lineFound = currItem.getLineNumber();
						closestItem = (CfmlTagItem) node;
					}
				}
			}
					
		}
		catch (Exception e) {
			closestItem = null;
		    e.printStackTrace();
		}
		if (closestItem != null) {
			// this looks odd, but without this call, we don't get the parentage
			return closestItem;  
		} else {
			return null;
		}
	}			

	public String getId() {
		return "argument.proposals";
	}

	public String getName() {
		return "Argument Proposals";
	}
    
}
