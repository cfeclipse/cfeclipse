package com.rohanclan.cfml.views.dictionary;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.core.runtime.IAdaptable;
import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.dictionary.Function;
import com.rohanclan.cfml.dictionary.ScopeVar;
import com.rohanclan.cfml.dictionary.SyntaxDictionary;
import com.rohanclan.cfml.dictionary.Tag;
import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.views.packageview.FolderNode;
import com.rohanclan.cfml.views.packageview.FolderTypes;
import com.rohanclan.cfml.views.snips.SnipDoubleClickListener;
import com.rohanclan.cfml.views.snips.SnipTreeViewContentProvider;
import com.rohanclan.cfml.views.snips.SnipTreeViewLabelProvider;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class DictionaryView extends ViewPart {
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	private Action viewinfo;
	protected Text text, preview;
	protected Label previewLabel;
	protected LabelProvider labelProvider;


	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class TreeObject implements IAdaptable {
		private String name;
		private TreeParent parent;
		private String type;
		
		public TreeObject(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public void setParent(TreeParent parent) {
			this.parent = parent;
		}
		public TreeParent getParent() {
			return parent;
		}
		public String toString() {
			return getName();
		}
		public Object getAdapter(Class key) {
			return null;
		}
        /**
         * @return Returns the type.
         */
        protected String getType() {
            return type;
        }
        /**
         * @param type The type to set.
         */
        protected void setType(String type) {
            this.type = type;
        }
	}
	class TreeParent extends TreeObject {
		private ArrayList children;
		public TreeParent(String name) {
			super(name);
			children = new ArrayList();
		}
		public void addChild(TreeObject child) {
			children.add(child);
			child.setParent(this);
		}
		public void removeChild(TreeObject child) {
			children.remove(child);
			child.setParent(null);
		}
		public TreeObject [] getChildren() {
			return (TreeObject [])children.toArray(new TreeObject[children.size()]);
		}
		public boolean hasChildren() {
			return children.size()>0;
		}
	}
	class TagItem extends TreeParent{
	    private SyntaxDictionary dictionary;
	  
        
	    public TagItem(String name){
	        super(name);
	    } 
        
        /**
         * @return Returns the dictionary.
         */
        public SyntaxDictionary getDictionary() {
            return dictionary;
        }
        /**
         * @param dictionary The dictionary to set.
         */
        public void setDictionary(SyntaxDictionary dictionary) {
            this.dictionary = dictionary;
        }
	}
	class FunctionItem extends TreeParent{
	    private SyntaxDictionary dictionary;
	    
	  
	    public FunctionItem(String name){
	        super(name);
	    } 
        /**
         * @return Returns the dictionary.
         */
        public SyntaxDictionary getDictionary() {
            return dictionary;
        }
        /**
         * @param dictionary The dictionary to set.
         */
        public void setDictionary(SyntaxDictionary dictionary) {
            this.dictionary = dictionary;
        }
	}
	class ScopeItem extends TreeParent{
	    private SyntaxDictionary dictionary;
	    
	  
	    public ScopeItem(String name){
	        super(name);
	    } 
        /**
         * @return Returns the dictionary.
         */
        public SyntaxDictionary getDictionary() {
            return dictionary;
        }
        /**
         * @param dictionary The dictionary to set.
         */
        public void setDictionary(SyntaxDictionary dictionary) {
            this.dictionary = dictionary;
        }
	}
	
	class ViewContentProvider implements IStructuredContentProvider, 
										   ITreeContentProvider {
		private TreeParent invisibleRoot;

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				if (invisibleRoot==null) initialize();
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}
		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject)child).getParent();
			}
			return null;
		}
		public Object [] getChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent)parent).getChildren();
			}
			return new Object[0];
		}
		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent)parent).hasChildren();
			return false;
		}
/*
 * We will set up a dummy model to initialize tree heararchy.
 * In a real code, you will connect to a real model and
 * expose its hierarchy.
 */
		private void initialize() {
		    
		    TreeParent root = new TreeParent("Dictionaries");
		    
		    Map dicts = DictionaryManager.getDictionaries();
		    Iterator dictIter = dicts.keySet().iterator();
		    
		    
			    while(dictIter.hasNext()) {
			    
			        String currObject = dictIter.next().toString();
			        TreeParent dic = new TreeParent(currObject);
			        	
			        Iterator tagIter = null;
			        TreeParent tags = new TreeParent("tags");
			        Iterator funcIter = null;
			        TreeParent functions = new TreeParent("functions");
			        Iterator scopeIter = null;
			        TreeParent scopes = new TreeParent("scopes");
			        
			        
			        
			        SyntaxDictionary syntaxitems = DictionaryManager.getDictionary(currObject);
			        
			       /*
			        * Loop through the tags
			        */
			        try{ 
			        	//loop through the children I guess
			         	tagIter = syntaxitems.getAllTags().iterator();
			        	
			        	 while(tagIter.hasNext()) {
			        	     Tag currTag = (Tag)tagIter.next();
			        	     TagItem tag = new TagItem(currTag.getName());
			        	     tag.setDictionary(syntaxitems);
			        	     
			        	     tags.addChild(tag);
			        	     
			        	 }
				    } catch(Throwable ex){
				        System.out.println("Errror whilst trying to get the tags for: " + currObject.toString());
				        ex.printStackTrace(); 
				    }
				    
				    /*
				     * Loop through the functions
				     * */
				    try{ 
			        	//loop through the children I guess
			         	funcIter = syntaxitems.getAllFunctions().iterator();
			        	
			        	 while(funcIter.hasNext()) {
			        	     Function currFunc = (Function)funcIter.next();
			        	     FunctionItem func = new FunctionItem(currFunc.getName());
			        	     func.setDictionary(syntaxitems);
			        	     functions.addChild(func);			        	     
			        	 }
				    } catch(Throwable ex){
				        System.out.println("Errror whilst trying to get the functions");
				        ex.printStackTrace(); 
				    }
			       
				    
				    /*
				     * Loop through the scopes
				     * */
				   
				    try {
                        scopeIter = syntaxitems.getAllScopeVars().iterator();
                        
                        while(scopeIter.hasNext()){
                            ScopeVar currScope = (ScopeVar)scopeIter.next();
                            ScopeItem scopeitem = new ScopeItem(currScope.getValue());
                            scopeitem.setDictionary(syntaxitems);
                            scopes.addChild(scopeitem);
                            
                        }
                    } catch (Throwable ex) {
                        System.out.println("Errror whilst trying to get the scopes");
				        ex.printStackTrace(); 
                    }
				    
				    
				    if(tags.hasChildren())	{  dic.addChild(tags); }
			        if(functions.hasChildren()) { dic.addChild(functions); }
			        if(scopes.hasChildren()) { dic.addChild(scopes); }
			        root.addChild(dic);
			        
			        
			    }
		    
		    /*TreeObject to1 = new TreeObject("Leaf 1");
			TreeObject to2 = new TreeObject("Leaf 2");
			TreeObject to3 = new TreeObject("Leaf 3");
			TreeParent p1 = new TreeParent("Parent 1");
			p1.addChild(to1);
			p1.addChild(to2);
			p1.addChild(to3);
			
			TreeObject to4 = new TreeObject("Leaf 4");
			TreeParent p2 = new TreeParent("Parent 2");
			p2.addChild(to4);
			
			TreeParent root = new TreeParent("Root");
			root.addChild(p1);
			root.addChild(p2);
			
			invisibleRoot = new TreeParent("");
			invisibleRoot.addChild(root);*/
		    
			
			
			
		
			
			invisibleRoot = new TreeParent("");
			invisibleRoot.addChild(root);
		}
	}
	class ViewLabelProvider extends LabelProvider {

	    private HashMap folderIcons = new HashMap();
		
		/**
		 * 
		 */
		public ViewLabelProvider() {
			super();
			this.folderIcons.put(FolderTypes.WWWROOT, CFPluginImages.ICON_PVIEW_FOLDER_WWW);
			this.folderIcons.put(FolderTypes.CFCROOT, CFPluginImages.ICON_PVIEW_FOLDER_CFC);
			this.folderIcons.put(FolderTypes.CF_ROOT, CFPluginImages.ICON_PVIEW_FOLDER_CUS);
		}
	    
		public String getText(Object obj) {
			return obj.toString();
		}
		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			
			
			if (obj instanceof FunctionItem){
			   
			    imageKey = CFPluginImages.ICON_FUNC;
			    return CFPluginImages.get(imageKey);
			}
			else if (obj instanceof TagItem){
			    imageKey = CFPluginImages.ICON_TAG;
			    return CFPluginImages.get(imageKey);
			    
			}
			else if (obj instanceof ScopeItem){
			    imageKey = CFPluginImages.ICON_SERVER;
			    return CFPluginImages.get(imageKey);
			    
			}
			else if (obj instanceof TreeParent){
			   imageKey = ISharedImages.IMG_OBJ_FOLDER;
			    
			}
			
			return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
		}
	}
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public DictionaryView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
	    
//	  Create a grid layout object so the text and treeviewer
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.verticalSpacing = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		parent.setLayout(layout);
		
		
		text = new Text(parent, SWT.READ_ONLY | SWT.SINGLE | SWT.BORDER);
		GridData layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		text.setLayoutData(layoutData);
	    
		/*viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());*/
		
		viewer = new TreeViewer(parent);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());

		layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		layoutData.verticalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(layoutData);
		
		
		previewLabel = new Label(parent, SWT.WRAP);
        GridData gridData = new GridData();
        gridData.horizontalSpan = 2;
        previewLabel.setLayoutData(gridData);
        previewLabel.setText("Preview"); //$NON-NLS-1$
		
		
		preview = new Text(parent, SWT.READ_ONLY | SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		// layout the text field above the treeviewer
		layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.heightHint = 100;
		layoutData.horizontalAlignment = GridData.FILL;
		preview.setLayoutData(layoutData);
		
		
		
		
		makeActions();
		//hookContextMenu();
		//hookDoubleClickAction();
		//contributeToActionBars();
		hookListeners();
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
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(viewinfo);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		
		
		
		
		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}
		};
		
		viewinfo = new Action(){
		    public void run() {
		        ISelection selection = viewer.getSelection();
		        Object obj = ((IStructuredSelection)selection).getFirstElement();
		        String desc = "none";
		        if(obj instanceof TagItem){
		            TagItem tg = (TagItem)obj;
		            desc = tg.getDictionary().getTag(tg.getName()).getHelp();
		         } 
		        else if(obj instanceof FunctionItem){
		            FunctionItem fi = (FunctionItem)obj;
		            desc = fi.getDictionary().getFunctionHelp(fi.getName());
		            
		        }
		        
		        
		        
		        showMessage(desc);
		    }
		};
		viewinfo.setText("View Info");
		viewinfo.setToolTipText("View this items informatino");
		viewinfo.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		
	}

	
	protected void hookListeners() 
	{
		//add a selection listener so we can look at the selected file and
		//get the help information out
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) 
			{
			    String name;
			    String desc;
			    
			    ISelection selection = event.getSelection();
		        Object obj = ((IStructuredSelection)selection).getFirstElement();
		        
			    
			    System.out.println("User has touched something" + obj.toString());
				// if the selection is empty clear the label
				if(event.getSelection().isEmpty()) 
				{
				    text.setText("");
				    preview.setText("");
					return;
				} 
				
				
				 if(obj instanceof TagItem){
				     TagItem tg = (TagItem)obj;
			         name = tg.getName();
				     desc = tg.getDictionary().getTag(tg.getName()).getHelp();
			            
				     text.setText(name);
					 preview.setText(desc);
		         } 
		        else if(obj instanceof FunctionItem){
		            FunctionItem func = (FunctionItem)obj;
		            name = func.getName();
		            desc = func.getDictionary().getFunctionHelp(name);
		            
		            text.setText(name);
				    preview.setText(desc);
		            
		        }
		        else if(obj instanceof ScopeItem){
		            ScopeItem scopei = (ScopeItem)obj;
		            
		            name = scopei.getName();
		            //desc = scopei.getDictionary().;
		            
		            text.setText(name);
				    preview.setText("");
		            
		        }
				else
				{
					//IStructuredSelection selection = (IStructuredSelection)event.getSelection();
					StringBuffer toShow = new StringBuffer("");
					StringBuffer toPreview = new StringBuffer("");
					
					//TODO: check what it is and get the description
					
					
					
					text.setText("");
					preview.setText("");
				}
				
				
			}
		});
		
	}
	
	
	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Dictionary View",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
