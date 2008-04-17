/*
 * Created on Jan 30, 2004
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
package org.cfeclipse.cfml.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextViewer;
/**
 * @author Rob
 *
 * How to handle double clicks. At present this is assigned to all partitions
 * (again wizard file)
 */
public class CFDoubleClickStrategy implements ITextDoubleClickStrategy {
	protected ITextViewer fText;
	
	public void doubleClicked(ITextViewer part) 
	{
		int pos = part.getSelectedRange().x;

		if (pos < 0) return;
		
		this.fText = part;

		//if(!selectComment(pos)) 
		//{
			selectWord(pos);
		//}
	}
	
	/* protected boolean selectComment(int caretPos) 
	{
		IDocument doc = fText.getDocument();
		int startPos, endPos;

		try 
		{
			int pos = caretPos;
			char c = ' ';

			while (pos >= 0) 
			{
				c = doc.getChar(pos);
				if (c == '\\') 
				{
					pos -= 2;
					continue;
				}
				if (c == Character.LINE_SEPARATOR || c == '\"')
					break;
				--pos;
			}

			if (c != '\"')
				return false;

			startPos = pos;

			pos = caretPos;
			int length = doc.getLength();
			c = ' ';

			while (pos < length) 
			{
				c = doc.getChar(pos);
				if (c == Character.LINE_SEPARATOR || c == '\"')
					break;
				++pos;
			}
			
			if (c != '\"')
				return false;

			endPos = pos;

			int offset = startPos + 1;
			int len = endPos - offset;
			fText.setSelectedRange(offset, len);
			return true;
		} 
		catch (BadLocationException x) 
		{
			//?
		}

		return false;
	} */
	
	protected boolean selectWord(int caretPos) 
	{

		IDocument doc = this.fText.getDocument();
		int startPos, endPos;
		
		try 
		{
			int pos = caretPos;
			char c;

			while (pos >= 0)
			{
				c = doc.getChar(pos);
				if(!Character.isJavaIdentifierPart(c) && c != '-' && c != '_')
					break;
				--pos;
			}

			startPos = pos;

			pos = caretPos;
			int length = doc.getLength();

				/*
				 * We should ignore "_"'s since they are used as variable names, very annoying!
				 * 
				 */
			while (pos < length) 
			{
				c = doc.getChar(pos);
				if(!Character.isJavaIdentifierPart(c) && c != '-' && c != '_')
					break;
				++pos;
			}

			endPos = pos;
			selectRange(startPos, endPos);
			return true;

		} 
		catch (BadLocationException x) 
		{
			//?
		}

		return false;
	}

	private void selectRange(int startPos, int stopPos) 
	{
		int offset = startPos + 1;
		int length = stopPos - offset;
		this.fText.setSelectedRange(offset, length);
	}
}