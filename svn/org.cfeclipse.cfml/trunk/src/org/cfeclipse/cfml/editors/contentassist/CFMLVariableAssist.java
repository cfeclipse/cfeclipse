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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.dictionary.ISyntaxDictionary;
import org.cfeclipse.cfml.dictionary.ScopeVar;
import org.cfeclipse.cfml.dictionary.SyntaxDictionary;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.CFParser;
import org.cfeclipse.cfml.parser.VariableParserItem;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.cfeclipse.cfml.parser.docitems.TagItem;
import org.cfeclipse.cfml.preferences.AutoIndentPreferenceConstants;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.Template;


/**
 * Provides "arguments" assistance When you press the '.'
 * It looks at the document's VariableMap and searches for the last item, then depending what they are
 * Gives the right suggestsion
 * 
 * @author Mark Drew
 */
public class CFMLVariableAssist extends AssistContributor //extends DefaultTagAssistContributor
	   		 implements IAssistContributor 
{
	
	/**
	 * Dictionary where the scopes are. 
	 */
	private SyntaxDictionary sourceDict;
    
    /**
     * 
     */
    public CFMLVariableAssist() {
    	  this.sourceDict = DictionaryManager.getDictionary(DictionaryManager.CFDIC);
           if(this.sourceDict == null)
           		throw new IllegalArgumentException("CFMLScopeAssist()::CFMLScopeAssist()");
    	
    	
    }

	/**
	 * Cut out angular brackets for relevance sorting, since the template name
	 * does not contain the brackets.
	 * 
	 * @param key
	 *            the template
	 * @param state
	 *            the prefix
	 * @return the relevance of the <code>template</code> for the given
	 *         <code>prefix</code>
	 */
	protected int getRelevance(String key, IAssistState state) {
		// System.out.println("getRev for:" + template.getName() + " : |" +
		// prefix + "|");
		if (state.getDataSoFar().length() == 0) {
			// System.out.println("getRev=null");
			return 0;
		}
		if (key.toString().startsWith(state.getDataSoFar())) {
			// System.out.println("getRev=" + 90);
			return 90;
		}
		// System.out.println("getRev=" + 0);
		return 0;
	}
    
    
	/**
	 * We watch for angular brackets since those are often part of XML
	 * templates.
	 * 
	 * @param viewer
	 *            the viewer
	 * @param offset
	 *            the offset left of which the prefix is detected
	 * @return the detected prefix
	 */
	protected String extractPrefix(IDocument document, int offset) {
		int i = offset;
		if (i > document.getLength())
			return ""; //$NON-NLS-1$

		try {
			while (i > 0) {
				char ch = document.getChar(i - 1);
				if (ch != '.' && !Character.isJavaIdentifierPart(ch))
					break;
				i--;
			}
			return document.get(i, offset - i);
		} catch (BadLocationException e) {
			return ""; //$NON-NLS-1$
		}
	}
	
    /* (non-Javadoc)
     * @see org.cfeclipse.cfml.editors.contentassist.IAssistContributor#getTagProposals(org.cfeclipse.cfml.editors.contentassist.IAssistState)
     */
    public ICompletionProposal[] getTagProposals(IAssistState state) {
		if(!preferenceManager.getBooleanPref(AutoIndentPreferenceConstants.P_SUGGEST_VARIABLES)) {
			return null;
		}

    	ICFDocument doc = (ICFDocument)state.getIDocument();
    	// verify we have a parse tree to work with
    	if(doc.getCFDocument() == null || state.getITextView().getTextWidget().getSelectionText().length() > 0) {
    		return null;
    	}

    	//Check what partition we are in.
    	System.out.println(state.getOffsetPartition().toString());
    	    	
    	
        if (state.getTriggerData() == '#') {
            return getPageVariables(state, doc);
        }
        else if(state.getTriggerData() != '.'){
        	String paritionSection = "";
        	//could return everything that is in the map here.
        	try {
    			ITypedRegion partition = doc.getPartition(state.getOffset());
    			paritionSection = partition.getType();
    		} catch (BadLocationException e) {
    			e.printStackTrace();
    		}        
            return getPageVariables(state, doc);
    		//return null;
        }
        else {
        	
        	String allData = state.getDataSoFar();
        	
        	if(allData.endsWith(".")){
        		allData = allData.substring(0, allData.length()-1);
        	}
        	
        	String VarName = "";
        	
        	StringBuffer buf = new StringBuffer();
        	for (int i=allData.length()-1;i>=0;i--) {
        		if (!Character.isJavaIdentifierPart(allData.charAt(i))){
        			break; 
        		}
        		buf.insert(0,allData.charAt(i));
        	}
        	VarName = buf.toString();
        	
        	//Check that the variable is in the Map, otherwise return nothing
        	
        	
        	//Here we find out where we are in the document.
        	ICompletionProposal[] result = getArguments(VarName, state, doc);
        	
        	return result;
        }
    }
    /**
     * @param state
     * @param doc
     * @return
     */
    /**
     * @param varName
     * @param state
     * @param doc
     * @return
     */
    public ICompletionProposal[] getArguments(String varName, IAssistState state, ICFDocument doc){

    	//System.out.println("Starting the proposal constructor for " + varName);
    	
    	
    	//TODO: Break this out into methods, the flow is maddening!
    	
    	
    	CFParser parser = new CFParser();
        CFDocument cfdoc = parser.parseDoc(state.getIDocument().get());
	    String prefix = extractPrefix(state.getIDocument(), state.getOffset());

        //Get the variables:
        HashMap varMap = cfdoc.getVariableMap();
        ICompletionProposal[] proposals = null;
        Set scopeProposals = null;
        //Get the scopes we are looking for
//      Get an initial set of possibilities.
        //These are set in VariablesParser so go check that out if your variable isnt found
        System.out.println("pref"+prefix);
        //Find arguments in the document variable map
        VariableParserItem variableParserItem =  (VariableParserItem)varMap.get(varName);
       
       //TODO: for scopes such as FORM, URL and ATTRIBUTES, we need to do something different, since we will go and search for them tags that are CFPARAM
       
       boolean isScope = false;
       //if the variable exists
       if(variableParserItem != null){
    	   TagItem chosenTag = (TagItem)variableParserItem.getTagItem();
    	 //  System.out.println("Found Chosen Tag" + chosenTag);
    	   //Lets check we have a pre-defined scope for this type
    	   System.out.println(chosenTag.getClass().getName());
    	   if(chosenTag != null && (chosenTag instanceof TagItem || chosenTag instanceof CfmlTagItem)){
           	TagItem leTag = (TagItem)chosenTag;
           	String tagname  = leTag.getName();
           		
           		if(tagname.equalsIgnoreCase("cfquery")){
           			isScope = true;
           				// we now look for either result or name
           			//TODO: tokenise SELECT something, something FROM so we have those proposals too!
           			//foo.subString(foo.indexOf("."))
           			if(varName.equalsIgnoreCase(leTag.getAttributeValue("result"))){
           				scopeProposals =  ((ISyntaxDictionary)this.sourceDict).getFilteredScopeVars("CFQUERY");
           		
           			}else{
           				
           				//Go and get the proposals for this item, which is one set for the items, and another for the columns
           				//TODO: Add icons to this. so maybe we do a generic scope thing that adds scopeProposals with icons
           				           				
           				scopeProposals =  ((ISyntaxDictionary)this.sourceDict).getFilteredScopeVars("QUERY");
           				//Get the contents of the SQL and parse them
           				//get the end of leTag
           				int sqlStart = leTag.getEndPosition() + 1;
           				int sqlEnd = leTag.getMatchingItem().getStartPosition();
           				String docText = doc.get();
           				String sql = docText.substring(sqlStart, sqlEnd);
           				scopeProposals.addAll(parseSQL(sql));
           				
           			}
           		} //end cfquery
           		else if(tagname.equalsIgnoreCase("cfdirectory")){
           			isScope = true;
           			scopeProposals = ((ISyntaxDictionary)this.sourceDict).getFilteredScopeVars("CFDIRECTORY");
           		}
           		else if(tagname.equalsIgnoreCase("cfftp")){
           			isScope = true;
           				if(varName.equalsIgnoreCase(leTag.getAttributeValue("result"))){
           					scopeProposals = ((ISyntaxDictionary)this.sourceDict).getFilteredScopeVars("CFFTP");
           				}
           				else{
           					scopeProposals = ((ISyntaxDictionary)this.sourceDict).getFilteredScopeVars("CFFTPList");
           				}
           		}
           		else if(tagname.equalsIgnoreCase("cfsearch")){
           			isScope=true;
       					scopeProposals = ((ISyntaxDictionary)this.sourceDict).getFilteredScopeVars("CFSEARCH");
           		}
           		else if(tagname.equalsIgnoreCase("cfcollection")){
           			isScope=true;
       					scopeProposals = ((ISyntaxDictionary)this.sourceDict).getFilteredScopeVars("CFCOLLECTION");
           		}
    	   }
       }
       //We havent found it, but we need to check if there is something like FORM. URL. etc...
       //We might need to loop through them
       else{
    	   //System.out.println("No Tag Found");
    	   isScope = true;
    	   Set formScopes = new HashSet();
    	   Iterator hashIter = varMap.keySet().iterator();
    	   while(hashIter.hasNext()){
    		   
    		   
    		   Object keyObj = hashIter.next();
    		
    		   String key = (String)keyObj;
    		   if(key.toUpperCase().startsWith(varName.toUpperCase())){
    			   
    			   formScopes.add(key);
    		   }
    	   }
    	   scopeProposals = formScopes;
       }
       
      //System.out.println("finding scope proposals " + scopeProposals);
       
       if(scopeProposals != null){
    	   //Create the scope proposals
    	   proposals = new ICompletionProposal[scopeProposals.size()];
    	   //Now loop and add the proposals
    	   Iterator scopeIter = scopeProposals.iterator();
    	   
    	   int scopeCounter = 0;
    	   while(scopeIter.hasNext()){
    		   //Does it just return things?
    		  
    		   Object scopeKey = scopeIter.next();
    		   String scopeItem = scopeKey.toString();
    		   
    		   //Here we check what type these items are
    		   CompletionProposal proposal = null;
    		  
    		   if(scopeKey instanceof ScopeVar){
    			   //Lets find the help and assign some help to it
    			   ScopeVar sVar = (ScopeVar)scopeKey;
        			  
    			  scopeItem = scopeItem.substring(scopeItem.indexOf(".") + 1, scopeItem.length());
    			   
    			   proposal = new CompletionProposal(scopeItem.toString(),
    					   state.getOffset(),
	    	                0,
	    	                scopeItem.toString().length(),
	    	                CFPluginImages.get(CFPluginImages.ICON_VALUE),
	    	                scopeItem.toString(),
	    	                null,
	    	                sVar.getHelp());
    			   
    		   } else {
    			   //Need to remove the  text that has already been entered (textSoFar)    			   
    			   String replacementString = scopeItem.toString().replace(prefix, "");    			   
    			   proposal = new CompletionProposal(scopeItem.toString(),
    					   state.getOffset()-prefix.length(),
    					   prefix.length(),
    					   prefix.length()+replacementString.length(),
	    	                CFPluginImages.get(CFPluginImages.ICON_VALUE),
	    	                scopeItem.toString(),
	    	                null,
	    	                null);
    			   
    		   }
    		   proposals[scopeCounter] = proposal;
    		   scopeCounter++;
    		   
    		   
    	   }
    		
    	   
       }
       //We go and calculate some proposals, for say CFQUERY there is something in the scopes already
       
       //Before we return the proposals, we should filter them!
       
       
        
    	return proposals;
    }
    
    /**
     * 	This function parses an SQL string and tries to return a set of proposals for a
     *  drop down.
     *  
     *  TODO: parse columns that dont have an "AS" statement but are a function such as Count(something.something)
     * 
     * @param SQL string
     * @return Set of proposals for columns
     */
    private Set parseSQL(String sqlin){
    	Set columnProposals = new HashSet();
    	//Find the end of "SELECT" and the start of "FROM"
    	//if you dont find SELECT run away
    	int selectStartPos = sqlin.toLowerCase().indexOf("select", 0);
    	int fromStartPos = sqlin.toLowerCase().indexOf("from", 0);
    	if(selectStartPos == -1){
    		return null;
    	}
    	if(fromStartPos == -1){
    		return null;
    	}
    	
    	//Ok, so we found a SELECT and a FROM, lets get the middle bit!
    	String allColumns = sqlin.substring(selectStartPos + ("select").length(), fromStartPos).trim();
    	String[] columnArray = allColumns.split(",");
    	
    	for(int i=0; i<columnArray.length; i++){
    		String column = columnArray[i].trim();
    		//We need to find if it has an AS
    		
    		int findAS = column.toLowerCase().indexOf(" as ", 0);
    		
    		if(findAS != -1){
    			column = column.substring(findAS + 4, column.length()).trim();
    		}
    		columnProposals.add(column);
    	}
    	return columnProposals;
    }
    
    
    public ICompletionProposal[] getPageVariables(IAssistState state, ICFDocument doc){
    	CFParser parser = new CFParser();
        CFDocument cfdoc = parser.parseDoc(state.getIDocument().get());
        //Get the variables:
        HashMap varMap = cfdoc.getVariableMap();
        String prefix = extractPrefix(doc, state.getOffset());
        Set<CompletionProposal> proposals = new HashSet<CompletionProposal>();
        int relavance;
        Iterator keyIter = varMap.keySet().iterator();
        int propIter = 0;
        while(keyIter.hasNext()){
        	String key = (String)keyIter.next();
 		   if(key.toUpperCase().startsWith(prefix.toUpperCase()) && !key.toUpperCase().equals(prefix.toUpperCase())){
			   String replacementString = key.toString().replace(prefix, "");    			   
 			   CompletionProposal proposal = new CompletionProposal(key.toString(), state.getOffset()-prefix.length(), prefix.length(), prefix.length()+replacementString.length());            	
 			   proposals.add(proposal);
 			   propIter++;
 		   }

        	 
        //Loop through the vars and we are ok
        }
        return (ICompletionProposal[]) proposals.toArray(new ICompletionProposal[]{});
    }

	public String getId() {
		return "Variable Proposals";
	}

	public String getName() {
		return "Variable Proposals";
	}

	public void sessionEnded() {
		// TODO Auto-generated method stub
		
	}

	public void sessionStarted() {
		// TODO Auto-generated method stub
		
	}
}
