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
import java.util.Set;
import java.util.regex.*;

import org.eclipse.core.internal.utils.Assert;
import org.eclipse.jface.text.contentassist.*;

import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.dictionary.Parameter;
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
        
        if (!checkContext(state.getIDocument().get(),state.getOffset()))
        	return null;

        else {
            int length = this.functionName.length();
            
    		Set params = ((SyntaxDictionaryInterface)this.sourceDict).getFunctionParams(this.functionName);
    		String helpText = ((SyntaxDictionaryInterface)this.sourceDict).getFunctionHelp(this.functionName);
    		
    		if (params == null) {
    		    return null;
    		}

    		Iterator i = params.iterator();
			int x = 0;
			String extraInfo = paramIndent + "<b>" + functionName + "</b> (\n";
			CompletionProposal proposal = null;
			String usage = "";
			Parameter activeParam = null;
			
			while(i.hasNext())
			{
			    Object o = i.next();
			    if (o instanceof Parameter) {
			        Parameter p = (Parameter)o;

					String delimiter = "";
					if (x < params.size()) {
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
			}
			
			extraInfo += paramIndent + ") \n\n";
			extraInfo += helpText;
			
			return getParamProposals(activeParam,extraInfo,state.getOffset(),params.size());
			
        }
    }
    
    
    private ICompletionProposal[] getParamProposals(Parameter activeParam,String extraInfo,int offset, int paramCount) {
        
        if (activeParam.getType().equalsIgnoreCase("boolean")) {
            
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
            suffix = ")";
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
            suffix = ")";
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
    
    
    
    
    
    
    /**
     * Checks to see if the cursor is at a position in the document where 
     * function parameter info should be displayed.
     * @param docText
     * @param offset
     * @return
     */
    private boolean checkContext(String docText, int offset) {
        this.paramsSoFar = 0;
        try {
            String trigger = docText.substring(offset-1,offset);
            // Check if we're at the start of a function.
            if (trigger.equals("(")) {
                Pattern p = Pattern.compile("\\b\\w+\\($");
                Matcher m = p.matcher(docText.substring(0,offset));
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
                
                
                byte[] docBytes = docText.substring(0,offset).getBytes();
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
	                	    this.paramsSoFar++;
	                	    //System.out.println("Comma found. Params so far" + this.paramsSoFar);
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
