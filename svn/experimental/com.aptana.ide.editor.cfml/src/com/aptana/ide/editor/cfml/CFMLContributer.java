package com.aptana.ide.editor.cfml;

import com.aptana.ide.editor.cfml.parsing.CFMLMimeType;
import com.aptana.ide.editors.unified.BaseContributor;
import com.aptana.ide.editors.unified.IUnifiedEditorContributor;
import com.aptana.ide.editors.unified.UnifiedReconcilingStrategy;

public class CFMLContributer extends BaseContributor implements
		IUnifiedEditorContributor {

	public CFMLContributer(String language) {
		super(language);
	}

	public UnifiedReconcilingStrategy getReconcilingStrategy() {
		return new UnifiedReconcilingStrategy();
	}

	public String getLocalContentType(){
		return CFMLMimeType.MimeType;
	}
}
