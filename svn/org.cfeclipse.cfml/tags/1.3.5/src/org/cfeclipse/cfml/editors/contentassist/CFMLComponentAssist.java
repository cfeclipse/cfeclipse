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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cfeclipse.cfml.dictionary.Parameter;
import org.cfeclipse.cfml.dictionary.Trigger;
import org.cfeclipse.cfml.dictionary.Value;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.CFParser;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.cfeclipse.cfml.parser.docitems.TagItem;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.cfeclipse.cfml.util.ResourceUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;


/**
 * Provides Component content assist. Looks for a CFC with the same name as the one you have just written and tries to find methods for that CFC
 * 
 *
 * @author Mark Drew
 */
public class CFMLComponentAssist extends AssistContributor 
	   		 implements IAssistContributor 
{
    
    /**
     * The name of the function we're trying to provide context insight for.
     */
    private String cfcname = null;
    
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
    //private HashMap paramPositions = new HashMap();
    
    /**
     * 
     */
    public CFMLComponentAssist() {
    }

    /* (non-Javadoc)
     * @see org.cfeclipse.cfml.editors.contentassist.IAssistContributor#getTagProposals(org.cfeclipse.cfml.editors.contentassist.IAssistState)
     */
    public ICompletionProposal[] getTagProposals(IAssistState state) {
    	ICFDocument doc = (ICFDocument)state.getIDocument();
    	IProject project = doc.getResource().getProject();
    	
    	
    	
    	/*
         * Only show content assist if the trigger was .
         */
        if (state.getTriggerData() != '.') {
        	
            return null;
        }
        else {
        	String allData = state.getDataSoFar();
        	//Remove the last character, who is, by definition a "."
        	if(allData.endsWith(".")){
        		allData = allData.substring(0, allData.length()-1);
        	}

        	String CFCName = "";
        	
        	StringBuffer buf = new StringBuffer();
        	for (int i=allData.length()-1;i>=0;i--) {
        		if (!Character.isJavaIdentifierPart(allData.charAt(i))){
        			break; 
        		}
        		buf.insert(0,allData.charAt(i));
        	}
    		CFCName = buf.toString();
    			
    		
    		//All going well, we have something!
    		
    		System.out.println("CFC = " + CFCName);
    	
    		
    		IFile foundCFC = findCFC(CFCName + ".cfc", project);
    	
        	ICompletionProposal[] result = new ICompletionProposal[1];
        	
        	
        	result = getFunctions(foundCFC, state.getOffset());
        	
        	
        	return result;
        }
    }
    
    
    /**
     * This function loops through a project finding references to the CFC we seek.
     * This can be done in 2 ways, break at the first, or return a whole bunch of proposals
     * 
     * 	Initally breaks at the first one
     * @param cfcname
     */
    private IFile findCFC(String cfcname, IProject project){
    	//Will need recursive function
    	IFile foundCFC = null;
   
    	
    	try {
    	 	IResource firstChildren[] = project.members();
		
		
				// To make this function quicker, doing two loops. The first is through the files, 
				// Then, we go into the directory, why? becuase I dont want to loop through the whole directory 
				// tree if the file is in the first directory!
				
		
				//Now loop through directories if we didnt find the file.
				if(foundCFC == null){
					for (int i = 0; i < firstChildren.length; i++)
			        {
						Object item = firstChildren[i];
			        	if(item instanceof IFolder){
			        		foundCFC = reFindCFC(cfcname, (IFolder)item);
			        		if(foundCFC != null){
			        			return foundCFC;
			        		}
			        	}
			        	else{  //Its a file
			        		IFile theFile = (IFile)item;
			        		if(theFile.getName().equalsIgnoreCase(cfcname)){
			        			return theFile;
			        		}
			        		
			        	}
			        }
				}
    	} catch (CoreException e) {
			e.printStackTrace();
		}
		
    	return foundCFC;
    }
    
    private IFile reFindCFC(String cfcname, IFolder folder){
 	IFile foundCFC = null;
   
    	
    	try {
    	 	IResource firstChildren[] = folder.members();
		
		
				// To make this function quicker, doing two loops. The first is through the files, 
				// Then, we go into the directory, why? becuase I dont want to loop through the whole directory 
				// tree if the file is in the first directory!
				
		
				//Now loop through directories if we didnt find the file.
				if(foundCFC == null){
					for (int i = 0; i < firstChildren.length; i++)
			        {
						Object item = firstChildren[i];
			        	if(item instanceof IFolder){
			        		foundCFC = reFindCFC(cfcname, (IFolder)item);
			        		if(foundCFC != null){
			        			return foundCFC;
			        		}
			        	}
			        	else{  //Its a file
			        		IFile theFile = (IFile)item;
			        		if(theFile.getName().equalsIgnoreCase(cfcname)){
			        			return theFile;
			        		}
			        		
			        	}
			        }
				}
    	} catch (CoreException e) {
			e.printStackTrace();
		}
		
    	return foundCFC;
   }

   
    
    private ICompletionProposal[]  getFunctions(IFile cfcresource, int offset){
    	if(cfcresource == null){
    		System.out.println("Resource not found");
    		return null;
    	}
    	
    	//
    	//Now that we have the resource, convert it to a string
    	  String inputString = "";
          try
          {
              inputString = ResourceUtils.getStringFromInputStream(cfcresource.getContents());
          }
          catch (IOException e)
          {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
          catch (CoreException e)
          {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
    	
    	
    	 CFParser parser = new CFParser();
         CFDocument doc = parser.parseDoc(inputString);

         // Now we just want to add the nodes!
         DocItem docroot = doc.getDocumentRoot();
         CFNodeList nodes;
         nodes = docroot.selectNodes("//cffunction");
         Iterator iter = nodes.iterator();
         ICompletionProposal[] result = new ICompletionProposal[nodes.size()];
         int iterAdder = 0;
         
         while (iter.hasNext())
         {
        	 
        	TagItem thisTag = (TagItem) iter.next();
        	String funcName = thisTag.getAttributeValue("name", "unnamed");
        	String funcReturn = thisTag.getAttributeValue("returntype", "any");

        	 String proposalName = funcName + "()" + " - " + funcReturn;
             
        	 CompletionProposal proposal = new CompletionProposal(proposalName, offset, 1, 1);
             
             result[iterAdder] = proposal;
             
             iterAdder++;
             
         }
    	return result;
    }
     
    private Parameter[] getFilteredParams(Set params) {
    	//HashSet s = new HashSet();
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
      //String value = "";
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
        //this.paramPositions = new HashMap();
        String docText = state.getIDocument().get();
        this.paramText = "";
        int offset = state.getOffset();
        int newOffset = offset;
        
        try {
            String trigger = docText.substring(offset-1,offset);
            //System.out.println("Triggered by ["+trigger+"]");
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
                int lastOpenParen = state.getDataSoFar().lastIndexOf("(");
                int lastComma = state.getDataSoFar().lastIndexOf(",");
                int lastCloseParen = state.getDataSoFar().lastIndexOf(")");
                if (lastOpenParen > 0 || lastComma > 0) {
                    if (lastOpenParen > lastComma) {
                        newOffset = state.getOffset() - state.getDataSoFar().length() + lastOpenParen + 1;
                        trigger = docText.substring(newOffset-1,newOffset);
                        paramText = docText.substring(newOffset,state.getOffset());
                    }
                    else if (lastComma > lastCloseParen){
                        newOffset = state.getOffset() - state.getDataSoFar().length() + lastComma + 1;
                        trigger = docText.substring(newOffset-1,newOffset);
                        paramText = docText.substring(newOffset,state.getOffset());
                       
                    }
                    else {
                        return false;
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
              //      this.functionName = m.group().substring(0,m.group().length()-1);
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
                         //   this.functionName = m.group().substring(0,m.group().length()-1);
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

	public void sessionEnded() {
		// TODO Auto-generated method stub
		
	}

	public void sessionStarted() {
		// TODO Auto-generated method stub
		
	}

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
    
    
}
