package org.cfeclipse.cfml.editors.dnd.future;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;

public class CFEDropAdapter  extends ViewerDropAdapter {

	protected CFEDropAdapter(Viewer viewer) {
		super(viewer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean performDrop(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateDrop(Object arg0, int arg1, TransferData arg2) {
		// TODO Auto-generated method stub
		return false;
	}

}
