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

import java.util.Iterator;
import java.util.*;
import java.util.regex.*;

import org.eclipse.core.internal.utils.Assert;
import org.eclipse.jface.text.contentassist.*;

import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.dictionary.Parameter;
import com.rohanclan.cfml.dictionary.Value;
import com.rohanclan.cfml.dictionary.Trigger;
import com.rohanclan.cfml.dictionary.SyntaxDictionary;
import com.rohanclan.cfml.dictionary.SyntaxDictionaryInterface;
import com.rohanclan.cfml.util.CFPluginImages;

/**
 * Provides CFML Function parameter assist. So the if
 * the user types "(" or "," they should see parameter 
 * info for the current function.
 *
 * @author Stephen Milligan
 */
public class CFMLFunctionAssist 
	   		 implements IAssistContributor 
{
    /**
     * Source dictionary for the scope info. Currently defaults
     * to the global CF dictionary.
     */
    private SyntaxDictionary sourceDict;
    
    /**
     * The name of the function we're trying to provide context insight for.
     */
    private String functionName = null;
    
    /**
     * The string to use to indent the function params below the function name.
     */
    private String paramIndent = "    ";
    
    /**
     * The number of parameters that have already been specified for the function
     *
     */
    private int paramsSoFar = -1;
    
    /**
     * The text typed so far in the current parameter if any.
     */
    private String paramText = "";
    
    /**
     * The list of parameters that have already been specified for the function
     *
     */
    private ArrayList paramList = new ArrayList();
    
    /**
     * The positions of any parameters that have explicitly declared index values in the dictionary
     *
     */
    private HashMap paramPositions = new HashMap();
    /**
     * 
     */
    public CFMLFunctionAssist() {
        this.sourceDict = DictionaryManager.getDictionary(DictionaryManager.CFDIC);
        Assert.isNotNull(this.sourceDict);
    }

    /* (non-Javadoc)
     * @see com.rohanclan.cfml.editors.contentassist.IAssistContributor#getTagProposals(com.rohanclan.cfml.editors.contentassist.IAssistState)
     */
    public ICompletionProposal[] getTagProposals(IAssistState state) {
        
        if (!checkContext(state))
        	return null;

        else {
            int length = this.functionName.length();
            
    		Set params = ((SyntaxDictionaryInterface)this.sourceDict).getFunctionParams(this.functionName);
    		String helpText = ((SyntaxDictionaryInterface)this.sourceDict).getFunctionHelp(this.functionName);
    		
    		
    		    		
    		
    		if (params == null) {
    		    return null;
    		}
    		
    		Parameter[] filteredParams = getFilteredParams(params);
    		
				int x = 0;
				String extraInfo = paramIndent + "<b>" + functionName + "</b> (\n";
				CompletionProposal proposal = null;
				String usage = "";
				Parameter activeParam = null;
				
				int paramCount = filteredParams.length;
				
				while(x < paramCount)
				{ 
			  	Parameter p = filteredParams[x];
			  		
					String delimiter = "";
					if (x+1 < paramCount) {
					    delimiter = " ,";
					}
					extraInfo += paramIndent + paramIndent;
					if (x == this.paramsSoFar) {
					    activeParam = p;
						extraInfo += "<b>";
					}
					extraInfo += p.toString() + delimiter;
					
					if (x == this.paramsSoFar) {
						extraInfo += "</b>";
					}
					extraInfo += "\n";
					
				  x++;
				}
				
				if (this.paramsSoFar == paramCount) {
					//System.out.println("End of params");
					return null;
				}
				
				extraInfo += paramIndent + ") \n\n";
				extraInfo += helpText;
				
				
				return getParamProposals(activeParam,extraInfo,state.getOffset(),paramCount);
				
        }
    }
    
    
    private Parameter[] getFilteredParams(Set params) {
    	HashSet s = new HashSet();
    	Parameter[] sortingArray = new Parameter[params.size()];
    	//Build the triggers map
  		HashMap paramMap = new HashMap();
      
      Object[] paramValues = paramList.toArray();
      Object[] paramNames = params.toArray();
      int n = 0;
      //System.out.println(paramValues.length + ":" + paramList.size());
      while(n < paramValues.length-1) {
      	String paramName = ((Parameter)paramNames[n]).getName();
      	String paramValue = (String)paramValues[paramValues.length-1-n];
      	if ((paramValue.startsWith("'") 
      			&& paramValue.endsWith("'"))
						|| paramValue.startsWith("\"") 
      			&& paramValue.endsWith("\"")) {
      		paramValue = paramValue.substring(1,paramValue.length()-1);
      	}
      	//System.out.println("Adding param to map: " + paramName + " - " + paramValue);
      	paramMap.put(paramName,paramValue);
      	n++;
      }
      

      Iterator i = params.iterator();
      int x = 0;
			while(i.hasNext())
			{
			    Object o = i.next();
			    if (o instanceof Parameter) {
			        Parameter p = (Parameter)o;
      
			      if (p.isTriggered(paramMap) == Parameter.PARAM_NOTTRIGGERED) {
			      	//System.out.println(p.getName() + " not triggered.");
			      	continue;
			      }
			      Trigger currentTrigger = p.activeTrigger();
		        if (currentTrigger != null) {
		        	//System.out.println(p.getName() + " index is " + currentTrigger.paramIndex());
		        	//this.paramPositions.put(p.getName(),new Integer(currentTrigger.paramIndex()));
		        	sortingArray[currentTrigger.paramIndex()] = p;
		        }
		        else {
		        	sortingArray[x] = p;
		        }
			      x++;
			    }
			    
			}
		
		Parameter[] resizedArray = new Parameter[x];
		x = 0;
		
		while(x<resizedArray.length) {
		    resizedArray[x] = sortingArray[x];
		    x++;
		}
			
    	return resizedArray;
    }
    
    
    private ICompletionProposal[] getParamProposals(Parameter activeParam,String extraInfo,int offset, int paramCount) {
        Set values = activeParam.getValues();
        
        if (values != null 
        		&& values.size() != 0) {
        	return getValueProposals(activeParam,extraInfo,offset,paramCount);
        }
        else if (activeParam.getType().equalsIgnoreCase("boolean")) {
            
            return getBooleanParamProposals(activeParam,extraInfo,offset,paramCount);
            
        }
        else {
            
				return getDefaultParamProposals(activeParam,extraInfo,offset,paramCount);

        }
        
    }
    
    
    private ICompletionProposal[] getBooleanParamProposals(Parameter activeParam,String extraInfo,int offset, int paramCount) {
        String value = "";
        String suffix = ",";
        ICompletionProposal[] result = new ICompletionProposal[2];
        
        value = "true";
        
        if (this.paramsSoFar == paramCount-1) {
            suffix = "";
        }
        CompletionProposal proposal = new CompletionProposal(value+suffix,
                offset,
                0,
                value.length()+suffix.length(),
                CFPluginImages.get(CFPluginImages.ICON_PARAM),
                value,
                null,
                extraInfo);
        
		
		result[0] = proposal;
        
        value = "false";
        proposal = new CompletionProposal(value,
                offset,
                0,
                value.length()+suffix.length(),
                CFPluginImages.get(CFPluginImages.ICON_PARAM),
                value,
                null,
                extraInfo);
        
		
		result[1] = proposal;
		
		return result;
    }
    
   
    private ICompletionProposal[] getDefaultParamProposals(Parameter activeParam,String extraInfo,int offset, int paramCount) {
      String value = "";
      String suffix = ",";
      ICompletionProposal[] result = new ICompletionProposal[1];
      
      if (activeParam.getType().equalsIgnoreCase("string")) {
          value = "\"\"";
      }
      else if (activeParam.getType().equalsIgnoreCase("regex")) {
          value = "\"[.]*\"";
      }
      
      if (activeParam.getDefaultValue() != null) {
          value = activeParam.getDefaultValue();
      }
      
      if (this.paramsSoFar == paramCount-1) {
          suffix = "";
      }
      CompletionProposal proposal = new CompletionProposal(value+suffix,
              offset,
              0,
              value.length()+suffix.length(),
              CFPluginImages.get(CFPluginImages.ICON_PARAM),
              activeParam.toString() + " - " + value,
              null,
              extraInfo);
      
	
	result[0] = proposal;
      
	
	return result;
  }
  
    private ICompletionProposal[] getValueProposals(Parameter activeParam,String extraInfo,int offset, int paramCount) {
      String value = "";
      String suffix = ",";
      Set values = activeParam.getValues();
      ICompletionProposal[] tmpResult = new ICompletionProposal[values.size()];
      
      
      if (this.paramsSoFar == paramCount-1) {
          suffix = "";
      }

      Iterator i = values.iterator();
      
      Pattern pattern = Pattern.compile("([\"']?)([^\"']*)([\"']?)");
      Matcher matcher = pattern.matcher(paramText);
      
      String cleanParamText = "";
      
      if (matcher.find()) {
          cleanParamText = matcher.group(2);
          if (cleanParamText.equals("\"")
              || cleanParamText.equals("'")) {
              cleanParamText = "";
          }
      }
      
      
      int x = 0;
      while (i.hasNext()) {
      	
      	Object o = i.next();
      	if (o instanceof Value) {
      		Value val = (Value)o;
      		if (cleanParamText.length() == 0
      		        || val.toString().toLowerCase().startsWith(cleanParamText.toLowerCase())) {
      		    String insertion = val.toString().substring(cleanParamText.length(),val.toString().length());
      		    int cursorOffset = insertion.length()+suffix.length();
      		    
      		    if (!paramText.endsWith("\"") 
      		            && paramText.startsWith("\"")) { 
      		        insertion += "\"";
      		        cursorOffset ++;
      		    }
      		    else if(paramText.startsWith("\"")){
      		        cursorOffset ++;
      		    }
      		    if (!paramText.endsWith("'")
      		            && paramText.startsWith("'")) { 
      		        insertion += "'";;
      		        cursorOffset ++;
      		    }
      		    else if(paramText.startsWith("'")){
      		        cursorOffset ++;
      		    }
      		    
		      CompletionProposal proposal = new CompletionProposal(
		              insertion+suffix,
		              offset,
		              0,
		              cursorOffset,
		              CFPluginImages.get(CFPluginImages.ICON_PARAM),
		              activeParam.toString() + " - " + val.toString(),
		              null,
		              extraInfo);
		      
		      //System.out.println("Added " + val.toString());
		      tmpResult[x] = proposal;
		      x++;
      		}
      	}
      }
      
      ICompletionProposal[] result = new ICompletionProposal[x];
      //System.out.println("Temp array length: " + x);
      for (int y=0;y<x;y++) {
          result[y] = tmpResult[y];
      }
	return result;
  }
  
  
    
    
    
    
    /**
     * Checks to see if the cursor is at a position in the document where 
     * function parameter info should be displayed.
     * @param docText
     * @param offset
     * @return
     */
    private boolean checkContext(IAssistState state) {
        this.paramsSoFar = 0;
        this.paramList = new ArrayList();
        this.paramPositions = new HashMap();
        String docText = state.getIDocument().get();
        this.paramText = "";
        int offset = state.getOffset();
        int newOffset = offset;
        
        try {
            String trigger = docText.substring(offset-1,offset);
            
            if (trigger.equals("#")) {
            	newOffset = offset -1;
            	trigger = docText.substring(offset-2,offset-1);
            	if (trigger.equals("'")
              		|| trigger.equals("\"")) {
              	newOffset = offset -2;
              	trigger = docText.substring(offset-3,offset-2);
              }
            }
            else if (trigger.equals("'")
            		|| trigger.equals("\"")) {
            	newOffset = offset -1;
            	trigger = docText.substring(offset-2,offset-1);
            	
            }
            
            /*
             * This block checks to see if we're in a parameter already. 
             * If so, it tries to figure out which one and resets the offset and trigger
             */
            
            if (!trigger.equals("(") 
                    && !trigger.equals(",")
                    && !trigger.equals(")")) {
                int lastParen = state.getDataSoFar().lastIndexOf("(");
                int lastComma = state.getDataSoFar().lastIndexOf(",");
                if (lastParen > 0 || lastComma > 0) {
                    if (lastParen > lastComma) {
                        newOffset = state.getOffset() - state.getDataSoFar().length() + lastParen + 1;
                        trigger = docText.substring(newOffset-1,newOffset);
                        paramText = docText.substring(newOffset,state.getOffset());
                    }
                    else {
                        newOffset = state.getOffset() - state.getDataSoFar().length() + lastComma + 1;
                        trigger = docText.substring(newOffset-1,newOffset);
                        paramText = docText.substring(newOffset,state.getOffset());
                       
                    }
                    // Auto insert closing " or '
                    docText.substring(state.getOffset(),state.getOffset()+1);
                    //System.out.println("Start of param text: " + paramText.substring(0,1));
                    //System.out.println("End of param text " + docText.substring(state.getOffset(),state.getOffset()+1));
                    
                    if (docText.length() > state.getOffset()
                            && docText.substring(state.getOffset(),state.getOffset()+1).equals(paramText.substring(0,1))) {
                        paramText += paramText.substring(0,1);
                        //System.out.println("Param: " + paramText);
                    }
                }
            }
            
            
            // Check if we're at the start of a function.
            if (trigger.equals("(")) {
               
                Pattern p = Pattern.compile("\\b\\w+\\($");
                Matcher m = p.matcher(docText.substring(0,newOffset));
                if(m.find()) {
                    this.functionName = m.group().substring(0,m.group().length()-1);
                    this.paramsSoFar = 0;
                    
                    return true;
                }
            }
            else if (trigger.equals(",")) {
                //System.out.println("Yep");
                //Pattern p = Pattern.compile("(\\b\\w+)\\(([^,]+,)*$");
                //Matcher m = p.matcher(docText.substring(0,offset));
                
                boolean singleQuotesOpen,doubleQuotesOpen,functionStart;
                singleQuotesOpen = doubleQuotesOpen = functionStart = false;
                int openFunctionCount = 0;
                
                byte[] docBytes = docText.substring(0,newOffset).getBytes();
                int lastParamEndedAt = docBytes.length;
                String functionText = docText.substring(0,newOffset);
                for (int i=docBytes.length-1;i>=0;i--) {
                    byte thisByte = docBytes[i];
                    //System.out.println("Looking at: " + (char)thisByte);
                    switch (thisByte) 
                    {
	                	case 34: if (!singleQuotesOpen) 
	                	{
	                	    doubleQuotesOpen = !doubleQuotesOpen;
	                	    //System.out.println("double qoute found. Open? " + doubleQuotesOpen);
	                	}
	                	break;
	                	
	                	case 39: if (!doubleQuotesOpen) 
	                	{
	                	    singleQuotesOpen = !singleQuotesOpen;
	                	    //System.out.println("single qoute found. Open? " + singleQuotesOpen);
	                	}
	                	break;
	                	
	                	case 40: if (!singleQuotesOpen && !doubleQuotesOpen) 
	                	{
	                	    openFunctionCount--;
	                	    if (openFunctionCount < 0) {
	                	        
	                	    	String thisParam = functionText.substring(i+1,lastParamEndedAt);
	  	                	  paramList.add(thisParam);
	  	                		lastParamEndedAt = i;
	  	                		
	                	    	functionStart = true;
	                	        //System.out.println("Function start found.");
	                	    }
	                	    else {
	                	        //System.out.println("Opening parentheses found. Open function count: " + openFunctionCount);
	                	    }
	                	}
	                	break;
                    	
	                	case 41: if (!singleQuotesOpen && !doubleQuotesOpen) 
	                	{
	                	    openFunctionCount ++;
	                	    //System.out.println("Closing parentheses found. Open function count: " + openFunctionCount);
	                	}
	                	break;
                    	
	                	case 44: if (!singleQuotesOpen 
	                	        	&& !doubleQuotesOpen 
	                	        	&& openFunctionCount < 1) 
	                	{
	                		
	                	  String thisParam = functionText.substring(i+1,lastParamEndedAt);
	                	  paramList.add(thisParam);
	                		lastParamEndedAt = i;
	                		this.paramsSoFar++;
	                	  //System.out.println("Comma found. Params so far " + this.paramsSoFar);
	                	}
                    	break;
	                	
                    	//default: System.out.println("Found " + (char)thisByte + ":" + (int)thisByte);
                    }
                    if (functionStart) {
                        offset = i+1;
                        //System.out.println("Looking in " + docText.substring(0,offset));
                        Pattern p = Pattern.compile("\\b\\w+\\($");
                        Matcher m = p.matcher(docText.substring(0,offset));
                        if(m.find()) {
                            //System.out.println("Found " + m.group());
                            this.functionName = m.group().substring(0,m.group().length()-1);
                            //System.out.println(this.functionName);
                            return true;
                        }
                    }
                }
            }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return false;
    }
    
    
    
    
}
