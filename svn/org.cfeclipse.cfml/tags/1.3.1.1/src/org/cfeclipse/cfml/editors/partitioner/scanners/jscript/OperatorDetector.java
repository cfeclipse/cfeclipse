/*
 * Created on Jan 31, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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
package org.cfeclipse.cfml.editors.partitioner.scanners.jscript;

import java.util.Set;

import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.eclipse.jface.text.rules.IWordDetector;

/**
 * @author Rob
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OperatorDetector implements IWordDetector {

	private JSSyntaxDictionary jssd;
	
	public OperatorDetector()
	{
		super();
		jssd = (JSSyntaxDictionary)DictionaryManager.getDictionary(DictionaryManager.JSDIC);
	}
	
	/* (non-Javadoc)
	 * Method declared on IWordDetector.
	 */
	public boolean isWordStart(char character) 
	{
		
		boolean isok = !Character.isLetterOrDigit(character); 
		
		if(isok)
		{
			Set set = jssd.getOperators();
			if(set.contains(character+""))
			{
				return true;
			}
		}
		
		return false; //&& 
			//((character == ' ') || Character.isLetter(character) || (character == '_'));
	}
	
	/* (non-Javadoc)
	 * Method declared on IWordDetector.
	 */
	public boolean isWordPart(char character) 
	{
		Set set = jssd.getOperators();
		return set.contains(character+"");
		
		//make sure any following char is a valid one - since I am using this for
		//non user defined things we have a bit of leeway
		//return Character.isLetterOrDigit(character) || (character == '-')
		//|| (character == '_'); //|| (0x7F <= character && character <= 0xFF);
	} 
}
