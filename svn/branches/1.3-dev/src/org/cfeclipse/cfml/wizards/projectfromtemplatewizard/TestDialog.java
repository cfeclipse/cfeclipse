package org.cfeclipse.cfml.wizards.projectfromtemplatewizard;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

public class TestDialog extends Composite {

	public TestDialog(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		setSize(new Point(300, 200));
		setLayout(new GridLayout());
	}

}
