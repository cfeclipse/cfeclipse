/*******************************************************************************
 *  Copyright (c) 2004, 2009 IBM Corporation and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.cfeclipse.cfml.templates.template;

import java.util.ArrayList;
import java.util.List;

import org.cfeclipse.cfml.editors.formatters.XmlDocumentFormatter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateBuffer;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.viewers.ISelection;

public class CFTemplateContext extends DocumentTemplateContext {
	
	
	private ITextViewer fViewer;
	private String fContextId;
	private String fLineDelimiter;

	public CFTemplateContext(TemplateContextType type, ITextViewer contextViewer, Position position) {
		super(type, contextViewer.getDocument(), position);
		fViewer = contextViewer;
		fContextId = type.getId();
		fLineDelimiter = org.eclipse.jface.text.TextUtilities.getDefaultLineDelimiter(contextViewer.getDocument());
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.templates.TemplateContext#evaluate(org.eclipse.jface.text.templates.Template)
	 */
	public TemplateBuffer evaluate(Template template) throws BadLocationException, TemplateException {

		TemplateBuffer templateBuffer= super.evaluate(template);
		IDocument doc = fViewer.getDocument();
		ITextSelection selection = (ITextSelection) fViewer.getSelectionProvider().getSelection();
		if (templateBuffer == null) {
			return null;
		}
		//TODO Not enabled see bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=55356
//		if (false && AntUIPlugin.getDefault().getPreferenceStore().getBoolean(AntEditorPreferenceConstants.TEMPLATES_USE_CODEFORMATTER)) {
//			FormattingPreferences prefs = new FormattingPreferences();
//			CFMLFormatter.format(templateBuffer, this, prefs);
//		}

  		StringBuffer indented = new StringBuffer();
		String pattern = templateBuffer.getString();
		String[] lines = pattern.replaceAll("\\r?(\\n)","$1").split("\\n");
		int lineOffset = doc.getLineInformationOfOffset(selection.getOffset()).getOffset();
		String soFar = doc.get(lineOffset, selection.getOffset() - lineOffset);
		soFar = soFar.replaceAll("\\S", "");
		int indentLength = XmlDocumentFormatter.computeIndent(soFar, 4);
		int[] list = new int[lines.length];
		indented.append(lines[0]).append(fLineDelimiter);
		for(int x = 1; x < lines.length; x++ ){
			indented.append(soFar);
			indented.append(lines[x]);
			indented.append(fLineDelimiter);
			list[x]= (new Integer(soFar.length()*x));
		}
		indented.setLength(indented.lastIndexOf(fLineDelimiter));
    	TemplateVariable[] variables= templateBuffer.getVariables();
		for (int i= 0; i != variables.length; i++) {
		    TemplateVariable variable= variables[i];
			int[] offsets= new int[variable.getOffsets().length];
			for (int j= 0; j != offsets.length; j++) {
				int offset = variable.getOffsets()[j];
				int varLine = pattern.substring(0,offset).split(fLineDelimiter).length;
				offsets[j]= offset + list[varLine-1];
			}			
		 	variable.setOffsets(offsets);   
		}
		templateBuffer.setContent(indented.toString(), variables);

		
		return templateBuffer;
	}
		
	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.templates.DocumentTemplateContext#getEnd()
	 */
	public int getEnd() {
		int replacementOffset = getCompletionOffset();
		int replacementLength = getCompletionLength();
		if (replacementOffset > 0 && getDocument().get().charAt(replacementOffset - 1) == '<' && getDocument().getLength() > 1) {
			replacementLength++;
		}
		return replacementLength + replacementOffset;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.templates.DocumentTemplateContext#getStart()
	 */
	public int getStart() {
		int replacementOffset= getCompletionOffset();
		if (replacementOffset > 0 && getDocument().get().charAt(replacementOffset - 1) == '<') {
			replacementOffset--;
		}
		return replacementOffset;
	}
}
