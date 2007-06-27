package org.cfeclipse.cfml.core.parser;

/*
Copyright (c) 2007 Mark Mandel, Mark Drew

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.	
*/

/**
 * @author mark
 *
 */
public class DefaultCFMLDictionary implements ICFMLDictionary
{
	public boolean conatinsCFScript(String tagName)
	{
		if(tagName.toLowerCase().equals("cfscript"))
		{
			return true;
		}
		
		return false;
	}
	
	
	public boolean isColdFusionTag(String tagName)
	{
		System.out.println("******* isColdFusionTag: " + tagName);
		if(tagName.toLowerCase().startsWith("cf"))
		{
			return true;
		}
		
		return false;
	}

	public boolean usesAttributes(String tagName)
	{
		tagName = tagName.toLowerCase();
		System.out.println("******* usesAttributes: " + tagName);
		if(tagName.equals("cfset") || tagName.equals("cfif") || tagName.equals("cfelseif"))
		{
			return false;
		}
		return true;
	}

	public boolean allowsCFMLAssignment(String tagName)
	{
		System.out.println("******* allowsCFMLAssignment: " + tagName);
		if(tagName.equals("cfset"))
		{			
			return true;
		}
		return false;
	}

	public boolean allowsCFMLCondition(String tagName)
	{
		System.out.println("******* allowsCFMLCondition: " + tagName);
		if(tagName.equals("cfif") || tagName.equals("cfelseif"))
		{
			return false;
		}
		return false;
	}
	
	
}
