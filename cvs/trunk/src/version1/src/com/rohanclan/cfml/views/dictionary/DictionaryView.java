package com.rohanclan.cfml.views.dictionary;

//import java.io.File;
import java.util.Properties;
import java.util.Enumeration;
import java.util.Set;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.*;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.views.browser.BrowserView;
import com.rohanclan.cfml.dictionary.Tag;
import com.rohanclan.cfml.editors.actions.EditCustomTagAction;
import com.rohanclan.cfml.editors.actions.EditFunctionAction;
import com.rohanclan.cfml.editors.actions.EditTagAction;
import com.rohanclan.cfml.editors.actions.Encloser;


public class DictionaryView extends ViewPart {
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action switchViewAction;
	private Action editTagAction;
	private Action viewinfo;
	private Action doubleClickAction;
	private Action viewhelp;
	private Action addCTagAction;
	
	protected Text text, preview;
	protected AttributesTable attrTable;
	protected Label previewLabel;
	protected LabelProvider labelProvider;
	private String viewtype = "standard";
	private DictionaryViewContentProvider contentprovider = new DictionaryViewContentProvider(viewtype);
	public static final String ID_DICTIONARY = "com.rohanclan.cfml.views.dictionary";
	private DictionaryViewFilter viewfilter;

	/**
	 * The constructor.
	 */
	public DictionaryView() {

	}
	private final class DoubleClickAction implements IDoubleClickListener {
		public void doubleClick(DoubleClickEvent event) {
			doubleClickAction.run();
		}
	}
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		try {
	    // Create a grid layout object for the view
		GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			layout.verticalSpacing = 0;
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			parent.setLayout(layout);
			
			

			
			// This is what makes the controls resizable
			SashForm sash = new SashForm(parent, SWT.VERTICAL);
			GridData sashData = new GridData(GridData.FILL_BOTH);
			sashData.horizontalSpan = 1;
			sash.setLayoutData(sashData);

			sash.setLayout(new FillLayout());

			
			// Create a layout with no margins for the containers below
			GridLayout containerLayout = new GridLayout();
			containerLayout.marginHeight = 0;
			containerLayout.marginWidth = 0;

			
			
			
			
			// Container for the top half of the view
			Composite topHalf = new Composite(sash, SWT.BORDER);
			topHalf.setLayout(containerLayout);
			topHalf.setLayoutData(new GridData(GridData.FILL_BOTH));

		
			
			// Container for the bottom half of the view
			Composite bottomHalf = new Composite(sash, SWT.BORDER);
			bottomHalf.setLayout(containerLayout);
			bottomHalf.setLayoutData(new GridData(GridData.FILL_BOTH));

			// This will allow you to type the tag name and get info on it
			text = new Text(topHalf, SWT.SINGLE | SWT.BORDER);
			GridData layoutData = new GridData();
			layoutData.grabExcessHorizontalSpace = true;
			layoutData.horizontalAlignment = GridData.FILL;
			text.setLayoutData(layoutData);

			// The dictionary tree viewer
			viewer = new TreeViewer(topHalf, SWT.RESIZE | SWT.BORDER);
			drillDownAdapter = new DrillDownAdapter(viewer);
			viewer.setContentProvider(contentprovider);
			viewer.setLabelProvider(new ViewLabelProvider());
			viewer.setSorter(new NameSorter());
			viewer.setInput(getViewSite());
			viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
			viewer.expandToLevel(2);
			
			attrTable = new AttributesTable(bottomHalf);
			
			
			
			
			makeActions();
			hookContextMenu();
			hookDoubleClickAction();
			hookListeners();
			contributeToActionBars();
			//hookFilters();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void hookFilters(){
		this.viewfilter = new DictionaryViewFilter();
	}
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				DictionaryView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(switchViewAction);
		manager.add(new Separator());
		//manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		//Somehow we have to know what this item is
		
		ISelection selection = viewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();
		
		System.out.println(obj.getClass());
		if(obj instanceof TagItem){
			Tag thistag = ((TagItem)obj).getTag();
			
			//if(thistag.isCustomTag()){
				manager.add(editTagAction);
			//}
		}
		else if(obj instanceof TreeParent && ((TreeParent)obj).getName().equals("Tags")){
			manager.add(addCTagAction);
		}
		
		// manager.add(viewinfo);
		manager.add(viewhelp);
		
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(switchViewAction);
		
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	/**
	 * Create the actions
	 */
	private void makeActions() {
		/**
		 * Get external help action
		 */
		
		viewhelp = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();
				String urldest = "http://www.cfdocs.org/";
				String keyword = "";
				// Get thecurrent page
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				//IViewReference ref[] = page.getViewReferences();
				//System.out.println(page.getLabel());
				/* Now we get the tag that we clicked on */
				if (obj instanceof TagItem) {
					TagItem tg = (TagItem) obj;
					keyword = tg.getName();
					
				} else if (obj instanceof FunctionItem) {
					FunctionItem fi = (FunctionItem) obj;
					keyword = fi.getName();
				} else if (obj instanceof ScopeItem) {
					ScopeItem si = (ScopeItem) obj;
					keyword = si.getName();
				} else {

					keyword = "";

				}
				try {

					BrowserView browser = (BrowserView) page
							.showView(BrowserView.ID_BROWSER);
					browser.setUrl(urldest + keyword, BrowserView.HELP_TAB);
					browser.setFocus(BrowserView.HELP_TAB);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		viewhelp.setText("View Online Help");
		viewhelp.setToolTipText("View online help for this tag or function");
		//viewinfo.setImageDescriptor(CFPluginImages.get(CFPluginImages.ICON_ALERT));
		
		

		/**
		 * View the info on an item (depricated?)
		 */
		viewinfo = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();
				String desc = "none";
				if (obj instanceof TagItem) {
					TagItem tg = (TagItem) obj;
					desc = tg.getDictionary().getTag(tg.getName()).getHelp();
					
				} else if (obj instanceof FunctionItem) {
					FunctionItem fi = (FunctionItem) obj;
					desc = fi.getDictionary().getFunctionHelp(fi.getName());

				}
				showMessage(desc);
			}
		};
		viewinfo.setText("View Info");
		viewinfo.setToolTipText("View this items information");
		viewinfo.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));

		/**
		 * Change the layout of how the tags are displayed
		 */
		switchViewAction = new Action() {
			public void run() {
				if(viewtype.equals("standard")){
					viewtype = "category";
					switchViewAction.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.DIC_UNCATEGORISE));
					contentprovider.changeSorting(viewtype);
					viewer.setContentProvider(contentprovider);
					viewer.expandToLevel(2);
					
				}else{
					viewtype = "standard";
					switchViewAction.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.DIC_CATEGORISE));
					contentprovider.changeSorting(viewtype);
					viewer.setContentProvider(contentprovider);
					viewer.expandToLevel(2);
					
				}
				
				
			}
		};
		//switchViewAction.setText("Switch View");
		switchViewAction
				.setToolTipText("Changes the order from categorised to a list of items");
	
		switchViewAction.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.DIC_CATEGORISE));
		

		editTagAction = new Action() {
			public void run() {
			
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection).getFirstElement();
				editTag(obj);
			}
		};
		
		editTagAction.setText("Edit this custom tag");
		editTagAction.setToolTipText("Allows you to edit this custom tag with the visual tag editor");
		editTagAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		
		addCTagAction = new Action() {
			public void run() {
				editTag(null);
			}
		};
		
		addCTagAction.setText("Add a custom tag");
		addCTagAction.setToolTipText("Allows you to define a new custom tag");
		addCTagAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection).getFirstElement();
				
				if(viewer.getExpandedState(obj)){
					
					viewer.setExpandedState(obj, false);
				}
				else{ 
					viewer.expandToLevel(obj,1);
				}
				
				viewTag(obj);
			}
		};
	}

	protected void hookListeners() {
		// add a selection listener so we can look at the selected file and
		// get the help information out
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				//String name;
				//String desc;

				ISelection selection = event.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();

				// if the selection is empty clear the label
				if (event.getSelection().isEmpty()) {
					//text.setText("");
					//preview.setText("");
					return;
				}
				else{
					attrTable.setAttributes(obj);
				
				}

				if (obj instanceof TagItem) {
					TagItem tg = (TagItem) obj;
					//text.setText(tg.getName());
					//preview.setText(tg.getHelp());
					//attrTable.setAttributes(obj);
				} else if (obj instanceof FunctionItem) {
					FunctionItem func = (FunctionItem) obj;
					///text.setText(func.getName());
					//preview.setText(func.getHelp());
				} else if (obj instanceof ScopeItem) {
					ScopeItem scopei = (ScopeItem) obj;
					//text.setText(scopei.getName());
					//preview.setText(scopei.getHelp());
				} else {
					//text.setText("");
					//preview.setText("");
				}

			}
		});

		text.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				System.out.println("seaching for: "  + text.getText());
				String searchpattern = text.getText();
				
				//if there is something to find 
				if(searchpattern.trim().length() > 1){
					viewfilter.match = searchpattern;
					viewer.addFilter(viewfilter);
					viewer.expandAll();
				}else if(searchpattern.trim().length() == 0 ){
				//This doesnt seem to be removing the view.
					viewer.removeFilter(viewfilter);
				} else {
					viewer.removeFilter(viewfilter);
					
				}
				
				
			}
		});
		
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new DoubleClickAction());
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(),
				"Dictionary View", message);
	}

	/**
	 * Opens the dialog for a tag or function to be inserted
	 * @param obj This can be either a TagItem or a FunctionItem
	 */

	protected void viewTag(Object obj) {

		//This will actually write out the tag to the page.
		//Thinking about this, instead of a Tag Item, maybe we should just use a Tag? 
		
		if (obj instanceof TagItem) {
				TagItem tg = (TagItem)obj;
				EditTagAction eta = new EditTagAction(tg.getTag(), this.getViewSite().getShell());
					eta.run();
		}
		else if(obj instanceof FunctionItem){
				FunctionItem fn = (FunctionItem)obj;
				EditFunctionAction efa = new EditFunctionAction(fn.getFunction(), this.getViewSite().getShell());
					efa.run();
					
		}
	}
	/**
	 * Edits a custom tag, and writes the XML to the user.xml (which one.. we dont know!
	 * @param obj
	 */
	protected void editTag(Object obj){
		
		if(obj instanceof TagItem){
			Tag tg = ((TagItem)obj).getTag();
			EditCustomTagAction ecta = new EditCustomTagAction(tg, this.getViewSite().getShell());
				ecta.run();
		}
		else if (obj == null){
			EditCustomTagAction ecta = new EditCustomTagAction(this.getViewSite().getShell());
			ecta.run();
		}
		
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}