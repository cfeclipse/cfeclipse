package org.cfeclipse.cfml.editors;

import java.io.File;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;

public class CFJavaFileEditorInputFactory implements IElementFactory {

	//NOTE: this constant is also in the plugin.xml file
	public static String FACTORY_ID = "org.cfeclipse.cfml.CFJavaFileEditorInputFactory";
	public static String FILE_NAME = "FileName";

	public CFJavaFileEditorInputFactory() 
	{
		super();
	}
	
	public IAdaptable createElement(IMemento memento) {
		String fileName = memento.getString(FILE_NAME);
		if (fileName != null)
		{
			File file = new File(fileName);
			return new CFJavaFileEditorInput(file);
		}

		return null;
	}

}
