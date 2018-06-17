/*
 * Created on Sep 9, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
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
package org.cfeclipse.cfml.editors.hover;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextHoverExtension2;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.MarkerAnnotation;


/**
 * @author Stephen Milligan This class handles the hover text for source viewer markers.
 */
public class CFAnnotationHover extends AbstractAnnotationHover
{
	
    public CFAnnotationHover(boolean allAnnotations) {
		super(allAnnotations);
	}
//
//	public String getHoverInfo(ISourceViewer sourceViewer, int lineNumber)
//    {
//
//        String[] messages = getMessagesForLine(sourceViewer, lineNumber);
//
//        if (messages.length == 0)
//            return null;
//
//        StringBuffer buffer = new StringBuffer();
//        for (int i = 0; i < messages.length; i++)
//        {
//            buffer.append(messages[i]);
//            if (i < messages.length - 1)
//                buffer.append(System.getProperty("line.separator")); //$NON-NLS-1$
//        }
//        return buffer.toString();
//    }

    private String[] getMessagesForLine(ISourceViewer viewer, int line)
    {
        IDocument document = viewer.getDocument();
        IAnnotationModel model = viewer.getAnnotationModel();

        if (model == null)
            return new String[0];

        ArrayList messages = new ArrayList();

        Iterator iter = model.getAnnotationIterator();
        while (iter.hasNext())
        {
            Object object = iter.next();
            if (object instanceof MarkerAnnotation)
            {
                MarkerAnnotation annotation = (MarkerAnnotation) object;
                if (compareRulerLine(model.getPosition(annotation), document, line))
                {
                    IMarker marker = annotation.getMarker();
                    String message = marker.getAttribute(IMarker.MESSAGE, (String) null);
                    if (message != null && message.trim().length() > 0)
                        messages.add(message);
                }
            }
        }
        return (String[]) messages.toArray(new String[messages.size()]);
    }

    private boolean compareRulerLine(Position position, IDocument document, int line)
    {

        try
        {
            if (position.getOffset() > -1 && position.getLength() > -1)
            {
                return document.getLineOfOffset(position.getOffset()) == line;
            }
        }
        catch (BadLocationException e)
        {
        }
        return false;
    }
    

}