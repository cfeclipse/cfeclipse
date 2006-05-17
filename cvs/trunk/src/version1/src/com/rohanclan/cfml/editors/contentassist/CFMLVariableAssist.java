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
package com.rohanclan.cfml.editors.contentassist;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.dictionary.ISyntaxDictionary;
import com.rohanclan.cfml.dictionary.SyntaxDictionary;
import com.rohanclan.cfml.editors.ICFDocument;
import com.rohanclan.cfml.parser.CFDocument;
import com.rohanclan.cfml.parser.CFParser;
import com.rohanclan.cfml.parser.docitems.TagItem;

/**
 * Provides "arguments" assistance When you press the '.'
 * It looks at the document's VariableMap and searches for the last item, then depending what they are
 * Gives the right suggestsion
 * 
 * @author Mark Drew
 */
public class CFMLVariableAssist 
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

    /* (non-Javadoc)
     * @see com.rohanclan.cfml.editors.contentassist.IAssistContributor#getTagProposals(com.rohanclan.cfml.editors.contentassist.IAssistState)
     */
    public ICompletionProposal[] getTagProposals(IAssistState state) {
    	ICFDocument doc = (ICFDocument)state.getIDocument();

    	
    	/*
         * Only show content assist if the trigger was .
         */
        if (state.getTriggerData() != '.') {
            return null;
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
     * This function finds the function you are in, then finds the arguments for that function.
     * @param state
     * @param doc
     * @return
     */
    public ICompletionProposal[] getArguments(String varName, IAssistState state, ICFDocument doc){

    	CFParser parser = new CFParser();
        CFDocument cfdoc = parser.parseDoc(state.getIDocument().get());
        
        //Get the variables:
        HashMap varMap = cfdoc.getVariableMap();
        ICompletionProposal[] proposals = null;
        Set scopeProposals = null;
        //Get the scopes we are looking for
//      Get an initial set of possibilities.
        //These are set in VariablesParser so go check that out if your variable isnt found
        
        //Find arguments in the document variable map
       Object chosenTag =  varMap.get(varName);
       
       //TODO: for scopes such as FORM, URL and ATTRIBUTES, we need to do something different, since we will go and search for them tags that are CFPARAM
       
       
       boolean isScope = false;
       //if the variable exists
       if(chosenTag != null){
    	   //Lets check we have a pre-defined scope for this type
    	   if(chosenTag instanceof TagItem){
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
       
       if(varName.equalsIgnoreCase("FORM") || varName.equalsIgnoreCase("URL") || varName.equalsIgnoreCase("ATTRIBUTES") ){
    	   isScope = true;
    	   Set formScopes = new HashSet();
    	   Iterator hashIter = varMap.keySet().iterator();
    	   while(hashIter.hasNext()){
    		   String key = (String)hashIter.next();
    		   if(key.toUpperCase().startsWith(varName.toUpperCase())){
    			   
    			   formScopes.add(key);
    		   }
    	   }
    	   scopeProposals = formScopes;
       }
       
       
       
       
       if(scopeProposals != null){
    	   //Create the scope proposals
    	   proposals = new ICompletionProposal[scopeProposals.size()];
    	   //Now loop and add the proposals
    	   Iterator scopeIter = scopeProposals.iterator();
    	   
    	   int scopeCounter = 0;
    	   while(scopeIter.hasNext()){
    		   String scopeItem = scopeIter.next().toString();
    		   
    		   
    		   
    		   if(isScope){
    			   //get the second one in SCOPE.VARIABLE
    			   scopeItem = scopeItem.substring(scopeItem.indexOf(".") + 1, scopeItem.length());
    			   
    			   //Get help from the scope
    		   }
    		   CompletionProposal proposal = new CompletionProposal(scopeItem.toString(), state.getOffset(), 0, scopeItem.toString().length());
    		   proposals[scopeCounter] = proposal;
    		   scopeCounter++;
    	   }
    		
    	   
       }
       //We go and calculate some proposals, for say CFQUERY there is something in the scopes already
       
       
        
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
    
}
