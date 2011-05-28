 

package org.cfeclipse.cfml.views.dictionary;

//import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.dictionary.Parameter;
import org.cfeclipse.cfml.dictionary.SyntaxDictionary;
import org.cfeclipse.cfml.dictionary.Tag;
import org.cfeclipse.cfml.editors.CFDocumentProvider;
import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.CFSyntaxDictionary;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.SQLSyntaxDictionary;
import org.cfeclipse.cfml.editors.actions.EditCustomTagAction;
import org.cfeclipse.cfml.editors.actions.EditFunctionAction;
import org.cfeclipse.cfml.editors.actions.EditTagAction;
import org.cfeclipse.cfml.editors.actions.InsertTagAction;
import org.cfeclipse.cfml.editors.partitioner.PartitionHelper;

import org.cfeclipse.cfml.preferences.CFMLPreferenceConstants;
import org.cfeclipse.cfml.properties.CFMLPropertyManager;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.cfeclipse.cfml.views.browser.BrowserView;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;



public class DictionaryView extends ViewPart {
	

	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action switchViewAction;
	private Action editTagAction;
	private Action viewinfo;
	private Action doubleClickAction;
	private Action viewhelp;
	private Action addCTagAction;
	
	protected Text searchField, preview;
	protected AttributesTable attrTable;
	protected Label previewLabel;
	protected LabelProvider labelProvider;
	private String viewtype = "standard";
	private DictionaryViewContentProvider contentprovider = new DictionaryViewContentProvider(viewtype, "cfmx701");
	public static final String ID_DICTIONARY = "org.cfeclipse.cfml.views.dictionary.DictionaryView";
	private DictionaryViewFilter viewfilter;

	/**
	 * The constructor.
	 */

	
	private IPartListener2 partListener2 = new IPartListener2() {
          
		public void partActivated(IWorkbenchPartReference ref) {
                if (ref.getPart(true) instanceof IEditorPart) {
                    editorActivated(getViewSite().getPage().getActiveEditor());
                }   
                  
            }

            public void partBroughtToTop(IWorkbenchPartReference ref) {
            	editorActivated(getViewSite().getPage().getActiveEditor());
                   
            }

            public void partClosed(IWorkbenchPartReference ref) {
            	combo.select(0);
            }

            public void partDeactivated(IWorkbenchPartReference ref) {
            	//combo.select(0);
            }

            public void partOpened(IWorkbenchPartReference ref) {
            	editorActivated(getViewSite().getPage().getActiveEditor());
            }

            public void partHidden(IWorkbenchPartReference ref) {}

            public void partVisible(IWorkbenchPartReference ref) {
            	editorActivated(getViewSite().getPage().getActiveEditor());
            }

            public void partInputChanged(IWorkbenchPartReference ref) {}

    };
	private Combo combo;
    
	public DictionaryView() {

	}
	
	//Dispose of the listener 
	public void dispose() {
		getSite().getPage().removePartListener(partListener2);
		super.dispose();
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
			containerLayout.numColumns =1;

			
			
			GridLayout topLayout = new GridLayout();
			topLayout.marginHeight = 0;
			topLayout.marginWidth = 0;
			topLayout.numColumns =3;
			
			// Container for the top half of the view
			Composite topHalf = new Composite(sash, SWT.BORDER);
			topHalf.setLayout(topLayout);
			topHalf.setLayoutData(new GridData(GridData.FILL_BOTH));
			
		
			
			// Container for the bottom half of the view
			Composite bottomHalf = new Composite(sash, SWT.BORDER);
			bottomHalf.setLayout(containerLayout);
			bottomHalf.setLayoutData(new GridData(GridData.FILL_BOTH));
			
			
		
			
			
			GridData spanData = new GridData();
			spanData.grabExcessHorizontalSpace = true;
			spanData.horizontalAlignment = GridData.FILL;
			spanData.horizontalSpan = 3;
			
			
			
			
			
			//Combo showing the current dictionary and all the available dictionaries
			combo = new Combo(topHalf, SWT.READ_ONLY|SWT.BORDER);
			combo.setLayoutData(spanData);
			 String [][] options = DictionaryManager.getConfiguredDictionaries();
			 
			 for (int i = 0; i < options.length; i++) {
					combo.add(options[i][1]);
			}
			 combo.select(0);
			 
			combo.addSelectionListener(new SelectionListener(){

				public void widgetDefaultSelected(SelectionEvent e) {
					
				}

				public void widgetSelected(SelectionEvent e) {
					if(combo.getText().length() > 0){
						viewer.setContentProvider(new DictionaryViewContentProvider(viewtype, combo.getText()));
						viewer.expandToLevel(2);
					}
				}
				
				
			});
			 
			
			
			GridData noSpanData = new GridData();
			noSpanData.grabExcessHorizontalSpace = true;
			noSpanData.horizontalAlignment = GridData.FILL;
			
			// This will allow you to type the tag name and get info on it
			searchField = new Text(topHalf, SWT.SINGLE | SWT.BORDER);
			
			searchField.setLayoutData(noSpanData);
			
			searchField.addKeyListener(new KeyListener(){

				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					if(e.character == SWT.CR){
						String searchpattern = searchField.getText();
						
						if(searchpattern.trim().length() > 0){
							viewfilter.setMatch(searchpattern);
							viewer.addFilter(viewfilter);
							viewer.expandToLevel(5);
						}
					}
				}

				public void keyReleased(KeyEvent e) {
					return;
					
				}
				
			});
			
			
			//add search and clear buttons
			Button btnSearch = new Button(topHalf, SWT.NONE);
			btnSearch.setText("Search");
			btnSearch.addMouseListener(new MouseListener(){

				public void mouseDoubleClick(MouseEvent e) {
					String searchpattern = searchField.getText();
					
					if(searchpattern.trim().length() > 0){
						viewfilter.setMatch(searchpattern);
						viewer.addFilter(viewfilter);
						viewer.expandToLevel(5);
					}
				}

				public void mouseDown(MouseEvent e) {
					System.out.println("button pressed");
					String searchpattern = searchField.getText();
					
					if(searchpattern.trim().length() > 0){
						viewfilter.setMatch(searchpattern);
						viewer.addFilter(viewfilter);
						viewer.refresh();
						viewer.expandToLevel(5);
					}
					
				}

				public void mouseUp(MouseEvent e) {}
			});
			btnSearch.addKeyListener(new KeyListener(){

				public void keyPressed(KeyEvent e) {
					String searchpattern = searchField.getText();
					if(e.character == ' ' && searchpattern.trim().length() > 0){
							viewfilter.setMatch(searchpattern);
							viewer.addFilter(viewfilter);
							viewer.expandToLevel(5);
					}
					
				}

				public void keyReleased(KeyEvent e) {
					return;
					
				}});
			
			
			Button btnClear = new Button(topHalf, SWT.NONE);
			btnClear.setText("Clear");
			btnClear.addMouseListener(new MouseListener(){

				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
					searchField.setText("");
					viewer.removeFilter(viewfilter);
					viewer.refresh();
					viewer.expandToLevel(1);
				}

				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
					searchField.setText("");
					viewer.removeFilter(viewfilter);
					viewer.refresh();
					viewer.expandToLevel(1);
				}

				public void mouseUp(MouseEvent e) {
					return;
					
				}
				
			});
			btnClear.addKeyListener(new KeyListener(){

				public void keyPressed(KeyEvent e) {
					searchField.setText("");
					viewer.removeFilter(viewfilter);
					viewer.refresh();
					viewer.expandToLevel(1);
					
				}

				public void keyReleased(KeyEvent e) {
					return;
				}
				
			});
			

			// The dictionary tree viewer
			viewer = new TreeViewer(topHalf, SWT.RESIZE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
			drillDownAdapter = new DrillDownAdapter(viewer);
			
			viewer.setContentProvider(contentprovider);
			viewer.setLabelProvider(new ViewLabelProvider());
			viewer.setSorter(new NameSorter());
			GridData viewerGD = new GridData(GridData.FILL_BOTH);
			viewerGD.horizontalSpan = 3;
			viewer.getControl().setLayoutData(viewerGD);
			viewer.setInput(getViewSite());
			

			viewer.expandToLevel(2);
			attrTable = new AttributesTable(bottomHalf);
			
			
			
			
			preview = new Text(bottomHalf, SWT.READ_ONLY | SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FLAT);
			// layout the text field above the treeviewer
			GridData previewGD = new GridData(GridData.FILL, GridData.FILL, true, true);
			previewGD.grabExcessHorizontalSpace = true;
			previewGD.heightHint = 20;
			previewGD.horizontalAlignment = GridData.FILL;
			previewGD.verticalAlignment = GridData.FILL;
			previewGD.grabExcessVerticalSpace = true;
			preview.setLayoutData(previewGD);
			
			
			getSite().getPage().addPartListener(partListener2);
			
			makeActions();
			hookContextMenu();
			hookDoubleClickAction();
			hookListeners();
			contributeToActionBars();
			hookFilters();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * This function returns a default dictionary or the one from the file passed in
	 * @param file
	 * @return
	 */

	 private void editorActivated(IEditorPart editor) {
	        //Check whether this view is active or not... dunno why
	    	if (!getViewSite().getPage().isPartVisible(this))
	        	return;
	        
	        if(editor !=null){
	        	
		        IEditorInput input = editor.getEditorInput();
		        
		  
		        IFile file = ResourceUtil.getFile(input);
		        if (file != null){
		        	CFMLPropertyManager propman = new CFMLPropertyManager();
		        	String currentDictionary = propman.getCurrentDictionary(file);
		        	combo.setText(currentDictionary);
		        	viewer.setContentProvider(new DictionaryViewContentProvider(viewtype, combo.getText()));
					viewer.expandToLevel(2);
		        }
	        }
	    }
	 
	 
	 
	 private DictionaryViewContentProvider getContentProviderByFile(IFile file){
		 CFMLPropertyManager propMan = new CFMLPropertyManager();
		 String currentDictionary = propMan.getCurrentDictionary(file);
	//SyntaxDictionary dictionary = DictionaryManager.getDictionaries()getDictionary(key)(currentDictionary);
	
		
		  Map dictionaries = DictionaryManager.getDictionaries();
		 //Object object = DictionaryManager.getDictionaries().get("CF_DICTIONARY");
		// CFSyntaxDictionary cfmlDict = (CFSyntaxDictionary)object;
		// String p_cfml_dictionary = CFMLPreferenceConstants.P_CFML_DICTIONARY;
		 System.out.println(dictionaries);
		 return null;
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
	//	manager.add(switchViewAction);
	//	manager.add(new Separator());
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
		//manager.add(switchViewAction);
		
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
				//TODO: Replace this with the getHelpAction
				
				
				
				
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();
				String keyword = "";
				// Get thecurrent page
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
				String urldest = "http://www.cfeclipse.org/cfdocs/?query="; //TODO: Change this help url out.
				
				
				String theFullURL = urldest + keyword;
				
				IExtensionRegistry extReg = Platform.getExtensionRegistry();
				if(extReg.getExtensions("com.adobe.coldfusion_help_7").length > 0){
					IWorkbenchHelpSystem helpsys = Workbench.getInstance().getHelpSystem();
					helpsys.search(keyword);
				}
				else{
						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						try {
						   BrowserView browser = (BrowserView)page.showView(BrowserView.ID_BROWSER);
						   browser.setUrl(theFullURL, BrowserView.HELP_TAB);
						   browser.setFocus(BrowserView.HELP_TAB);
						}
						catch(Exception e) {
						    e.printStackTrace();
						}
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
					preview.setText("");
					return;
				}
				else{
					attrTable.setAttributes(obj);
					
				}

				if (obj instanceof TagItem) {
					TagItem tg = (TagItem) obj;
					//text.setText(tg.getName());
					preview.setText(tg.getHelp());
					//attrTable.setAttributes(obj);
				} else if (obj instanceof FunctionItem) {
					FunctionItem func = (FunctionItem) obj;
					///text.setText(func.getName());
					preview.setText(func.getHelp());
				} else if (obj instanceof ScopeItem) {
					ScopeItem scopei = (ScopeItem) obj;
					//text.setText(scopei.getName());
					preview.setText(scopei.getHelp());
				} else {
					//text.setText("");
					preview.setText("");
				}

			}
		});
		
		attrTable.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
				if(e.item != null) {					
					if (e.item.getData() instanceof Parameter) {
						Parameter param = (Parameter) e.item.getData();
						preview.setText(param.getHelp());
					}
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
				InsertTagAction eta = new InsertTagAction(tg.getTag(), this.getViewSite().getShell());
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