package org.cfeclipse.cfml.cfunit.views;

import org.cfeclipse.cfml.cfunit.CFUnitTestCase;
import org.eclipse.ui.part.ViewPart;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class CFUnitView extends ViewPart {
	public final static String ID_CFUNIT = "org.cfeclipse.cfml.cfunit.views.CFUnitTest";
	
	public static final int ICON_NONE = 0;
	public static final int ICON_LOGO = 1;
	public static final int ICON_FAILURE = 3;
	public static final int ICON_ERROR = 2;
	public static final int ICON_NWERROR = 4;
	
	private static final String ICONS_PATH = "../../../../../../icons/obj16/";
	
	public void createPartControl(Composite parent) {
		GridLayout gridLayout= new GridLayout();
		gridLayout.numColumns = 1;
		parent.setLayout(gridLayout);
		
		CFUnitTestCase.getInstence().setTest("");
		
		CFUnitViewCounterPanel fCounterPanel = new CFUnitViewCounterPanel(parent);
		fCounterPanel.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		CFUnitViewTesterPanel fProgressBar = new CFUnitViewTesterPanel(parent);
		fProgressBar.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		CFUnitViewControls controls = new CFUnitViewControls(parent);
		controls.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		CFUnitViewTestList testlist = new CFUnitViewTestList( parent );
		testlist.setLayoutData( new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
		
		//Label filler = new Label(parent, SWT.NONE);
		//filler.setLayoutData( new GridData(GridData.GRAB_VERTICAL));
	}
	
	public void setFocus() {}
	
	public static Image getIcon(int img) {
		String name = "";
	
		switch( img ) {
			case ICON_NONE:
				name = "blank.png";
				break;
			
			case ICON_LOGO:
				name = "cfunit.png";
				break;
			
			case ICON_FAILURE:
				name = "emblem-important.png";
				break;
			
			case ICON_ERROR:
				name = "dialog-error.png";
				break;
			
			case ICON_NWERROR:
				name = "network-error.png";
				break;
				
			default:
				name = "blank.png";
		}
		
		ImageDescriptor id = ImageDescriptor.createFromFile( CFUnitView.class, ICONS_PATH + name );
		return id.createImage();
	}
}
