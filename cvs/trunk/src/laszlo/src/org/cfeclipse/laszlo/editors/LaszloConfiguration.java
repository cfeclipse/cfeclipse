/*
 * Created on Nov 14, 2004
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
package org.cfeclipse.laszlo.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.editors.CFConfiguration;
import com.rohanclan.cfml.editors.CFTextHover;
import com.rohanclan.cfml.editors.ColorManager;
import com.rohanclan.cfml.editors.partitioner.scanners.CFPartitionScanner;
import com.rohanclan.cfml.editors.partitioner.scanners.jscript.JavaScriptScanner;
//import com.rohanclan.cfml.editors.script.ScriptScanner;
import com.rohanclan.cfml.preferences.CFMLColorsPreferenceConstants;

public class LaszloConfiguration extends CFConfiguration { 
	
	/**
	 * @param colorManager
	 * @param editor
	 */
	public LaszloConfiguration(ColorManager colorManager, LaszloEditor editor) {
		super(colorManager, editor);
	}
	
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			//CFPartitionScanner.ALL_TAG,
			//CFPartitionScanner.HTM_TAG,
			CFPartitionScanner.HTM_START_TAG,
			CFPartitionScanner.HTM_COMMENT,
			CFPartitionScanner.J_SCRIPT
		};
	}	
	
	/**
	 * Override the javascript scanner so we can provide laszlo specific stuff
	 * @return
	 */
	protected JavaScriptScanner getScriptScanner() 
	{
		Token textToken = new Token(
			new TextAttribute(
				super.colorManager.getColor(
					super.preferenceManager.getColor(
							CFMLColorsPreferenceConstants.P_COLOR_DEFAULT_TEXT
					)
				)
			)
		);
		LaszloScriptScanner scriptscanner = new LaszloScriptScanner(
			super.colorManager, super.preferenceManager
		);
		scriptscanner.setDefaultReturnToken(textToken);
		return scriptscanner;
	}
	
	
	/**
	 * Register the text hover
	 * @author Oliver Tupman
	 */
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType)
	{
		//keep the hover only in the parts where it should be
		//i.e. not in comments ... (we could add a javascript
		//css specific thing in the future...)
		if(!(contentType == CFPartitionScanner.HTM_COMMENT))
		{
			//load the text hover with the cf dictionary
			return new CFTextHover(
				DictionaryManager.getDictionary(LaszloSyntaxDictionary.LASDIC)
			);
		}
		return null;
	}
}