package com.aptana.ide.editor.cfml;

import org.eclipse.ui.texteditor.IDocumentProvider;

import com.aptana.ide.editors.unified.IFileServiceFactory;
import com.aptana.ide.editors.unified.IUnifiedEditorContributor;
import com.aptana.ide.editors.unified.UnifiedEditor;

public class CFMLEditor extends UnifiedEditor {

	public CFMLEditor() {
		super();
		addPluginToPreferenceStore(CFMLPlugin.getDefault());
	}

	public String getDefaultFileExtension() {
		return "cfm";
	}
	
	
	protected IUnifiedEditorContributor createLocalContributor() {
		return new CFMLContributer("cfml");
	}


	@Override
	public IFileServiceFactory getFileServiceFactory() {
		return CFMLFileServiceFactory.getInstance();
	}

	@Override
	public IDocumentProvider createDocumentProvider() {
		// TODO Auto-generated method stub
		return null;
	}


}
