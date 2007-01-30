package org.cfeclipse.cfml.cfunit.views;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.cfunit.CFUnitTestCase;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;
import org.eclipse.ui.part.ViewPart;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
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
	public static final int ICON_RUN = 5;
	public static final int ICON_LINK = 6;
	public static final int ICON_SEARCH = 7;
	
	private static final String ICONS_PATH = "../../../../../../icons/obj16/";
	
	private Action actionRun;
	private Action actionSearch;
	private Action actionAutoload;
	
	public void createPartControl(Composite parent) {
		GridLayout gridLayout= new GridLayout();
		gridLayout.numColumns = 1;
		parent.setLayout(gridLayout);
		
		CFUnitTestCase.getInstence().setTest("");
		
		CFUnitViewCounterPanel fCounterPanel = new CFUnitViewCounterPanel(parent);
		fCounterPanel.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		CFUnitViewTesterPanel fProgressBar = new CFUnitViewTesterPanel(parent);
		fProgressBar.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		CFUnitViewTestList testlist = new CFUnitViewTestList( parent );
		testlist.setLayoutData( new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
				
		makeActions();
		contributeToActionBars();
	}
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(actionAutoload);
		manager.add(new Separator());
		manager.add(actionRun);
		manager.add(actionSearch);
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(actionRun);
		manager.add(actionSearch);
		manager.add(actionAutoload);
	}
	
	private void makeActions() {
		
		// Set up run menu action
		actionRun = new Action("Run Test") {
			public void run() {
				CFUnitTestCase.getInstence().run();
			}
		};
		actionRun.setToolTipText("Executes the current test case");
		actionRun.setImageDescriptor( getImageDescriptor( ICON_RUN ) );
		
		// Set up search menu action
		actionSearch = new Action("Find Test") {
			public void run() {
				browseFiles();
			}
		};
		actionSearch.setToolTipText("Search the workspace for a test case");
		actionSearch.setImageDescriptor( getImageDescriptor( ICON_SEARCH ) );
		
		
		// Set up the autoloader check icon
		CFMLPlugin.getDefault().getPreferenceStore().setDefault("CFUnitAutoload", true);
		boolean bAutoload = CFMLPlugin.getDefault().getPreferenceStore().getBoolean("CFUnitAutoload");
		
		actionAutoload = new Action("Auto-Load Tests", Action.AS_CHECK_BOX) {
			public void run() {
				CFMLPlugin.getDefault().getPreferenceStore().setValue("CFUnitAutoload", actionAutoload.isChecked());
			}
		};
		actionAutoload.setToolTipText("Automatically loads test cases when ColdFusion files are opened");
		actionAutoload.setImageDescriptor( getImageDescriptor( ICON_LINK ) );
		actionAutoload.setChecked( bAutoload );
	}
	

	/**
	 * Opens a dialog to assist the user find their unit test. If the user 
	 * selects a file it will update the global instance of TestCase.
	 */
	private void browseFiles() {
		CFUnitTestCase testcase = CFUnitTestCase.getInstence();
		
		ResourceListSelectionDialog listSelection = null;
		
		try {
			listSelection = new ResourceListSelectionDialog(
				null,
				ResourcesPlugin.getWorkspace().getRoot(), 
				IResource.FILE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		listSelection.setTitle("Find CFUnit Test");
		
		if(listSelection.open() == ResourceListSelectionDialog.OK) {
			Object[] result = listSelection.getResult();
			
			if(result.length == 1) {
				IResource resource = (IResource)result[0];
				testcase.setTest( CFUnitTestCase.getResourceFullName( resource ) );
				actionAutoload.setChecked( false );
			}
		}
	}
	
	public void setFocus() {}
	
	public static Image getIcon(int img) {
		return getImageDescriptor( img ).createImage();
	}
	
	public static ImageDescriptor getImageDescriptor(int img) {
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
				
			case ICON_RUN:
				name = "media-playback-start.png";
				break;
				
			case ICON_LINK:
				name = "emblem-symbolic-link.png";
				break;
				
			case ICON_SEARCH:
				name = "system-search.png";
				break;
				
			default:
				name = "blank.png";
		}
		
		return ImageDescriptor.createFromFile( CFUnitView.class, ICONS_PATH + name );
	}
	
	private void showMessage(String message) {
		MessageDialog.openInformation(null, "CFUnit View", message);
	}
}
