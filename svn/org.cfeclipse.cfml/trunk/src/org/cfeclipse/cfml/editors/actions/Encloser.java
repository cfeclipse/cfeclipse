/*
 * Created on Feb 15, 2004
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
package org.cfeclipse.cfml.editors.actions;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;

/**
 * @author Rob
 *
 * This is mostly used as a class to wrap text with something (like a comment,
 * or a cf variable, etc). This class is often extended not used directly
 */
public class Encloser 
{
	/** 
	 * Wraps the selection with the start and end string
	 * @param doc the document this belongs to
	 * @param sel the selection to be wrapped
	 * @param start the string to put before the selection
	 * @param end the string to put before the selection
	 */
	public int enclose(IDocument doc, ITextSelection sel, String start, String end)
	{
		StringBuffer cmtpart = new StringBuffer();
		try
		{
			if(!sel.isEmpty())
			{
			
				int offset = sel.getOffset();
				int len  = sel.getLength();
				
				if(start.length() <= 0 || end.length() <= 0)
				{
					len++;
				}
				
				cmtpart.append(start);
				// dont go past end of file.
				if( offset >= doc.getLength() ) {
					len = 0;
				}

				// Copy the selection into a local var and then check to see if it
				// is a whitespace character before appending.. if it is white space,
				// set the length of the insert to 0 and don't bother appending the selection.				
				// This seems to stop any corruption of the text editor buffer. Paul V.
				String selection = doc.get(offset,len);
				if(len == 1 && selection.matches("[ \t\n\r]")) {
					len = 0;
				} else {
					cmtpart.append(selection);
				}
				cmtpart.append(end);
				
				doc.replace(offset, len, cmtpart.toString());
			}
		}
		catch(BadLocationException ble)
		{
			ble.printStackTrace(System.err);
		}
		return cmtpart.length();
	}
}
