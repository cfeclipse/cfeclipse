/*
 * Created on Mar 27, 2007 by markdrew
 *
 * The MIT License
 * Copyright (c) 2007 CFEclipse Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package org.cfeclipse.cfml.frameworks.views;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.frameworks.Activator;
import org.cfeclipse.cfml.frameworks.FrameworkManager;
import org.cfeclipse.cfml.frameworks.actions.ActionManager;
import org.cfeclipse.cfml.frameworks.actions.IBaseAction;
import org.cfeclipse.cfml.frameworks.dialogs.CSAddBeanDialog;
import org.cfeclipse.cfml.frameworks.dialogs.ViewXMLDialog;
import org.cfeclipse.cfml.frameworks.preferences.ActionsPreferencePage;
import org.cfeclipse.cfml.frameworks.util.FWXImages;
import org.cfeclipse.cfml.frameworks.views.FrameworksView.TreeFilter;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.part.*;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;


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

public class FrameworksView extends ViewPart {


	

	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private Action action2;
	private Text xmlTextEditor;
	private Action refreshAction;
	private Action viewXMLAction;
	
	//Coldspring actions
	private Action csAddBeanAction;
	
	public static String ID = "org.cfeclipse.cfml.frameworks.views.FrameworksView";
	
	private Action doubleClickAction;
	private ComboViewer comboViewer;
	private IProject currentProject;
	private String currentProjectName = "No Project Selected";
	
	private ActionManager actionManager = new ActionManager(); 
	Label projLabel;

	private Log viewlog = LogFactory.getLog(FrameworksView.class);
	
	private EditorEventListener eelistener;
	

	private IPartListener2 partListener2 = new IPartListener2() {
		private Log listenerlogger = LogFactory.getLog(IPartListener2.class);
		
			public void partActivated(IWorkbenchPartReference ref) {
			
                if (ref.getPart(true) instanceof IEditorPart) {
                    editorActivated(getViewSite().getPage().getActiveEditor());
                }   
                  
            }

            public void partBroughtToTop(IWorkbenchPartReference ref) {
                   // editorActivated(getViewSite().getPage().getActiveEditor());
            }

            public void partClosed(IWorkbenchPartReference ref) {
            	editorActivated(getViewSite().getPage().getActiveEditor());
            	
            }

            public void partDeactivated(IWorkbenchPartReference ref) {
            	//if we close we want to recheck
            	
            }

            public void partOpened(IWorkbenchPartReference ref) {
           //         editorActivated(getViewSite().getPage().getActiveEditor());
            }

            public void partHidden(IWorkbenchPartReference ref) {}

            public void partVisible(IWorkbenchPartReference ref) {
                  //  editorActivated(getViewSite().getPage().getActiveEditor());
            }

            public void partInputChanged(IWorkbenchPartReference ref) {}

    };
	private Text text;
	private TreeFilter treeFilter;
	protected static String fDefaultSearchText = "Type to search";
	
	
    
    public void refreshFrameworkTree(){
    	IEditorInput input = getViewSite().getPage().getActiveEditor().getEditorInput();
        IFile file = ResourceUtil.getFile(input);
        if (file != null){
        		currentProject = file.getProject();
        		currentProjectName = currentProject.getName();
        		projLabel.setText(currentProject.getName());
            	viewer.setContentProvider(new FrameworksContentProvider(getViewSite(), currentProject, viewer));
            	viewer.setLabelProvider( new ViewLabelProvider());
            	viewer.expandToLevel(2);
        		
        
        }
        BufferedInputStream bit = null;
        JarInputStream bi = null;
        JarEntry je = null;
        
    }

    private void editorActivated(IEditorPart editor) {
       
    	
    	//if (!getViewSite().getPage().isPartVisible(this))
        //	return;
        
    	
    	
        if(editor !=null){
        	
	        IEditorInput input = editor.getEditorInput();
	        IFile file = ResourceUtil.getFile(input);
	        if (file != null){
	        	
	        	if(!file.getProject().getName().equals(currentProjectName)){
	        		
	        		System.out.println("setting  the project");
	//        		Check if we have changed projects
	        		currentProject = file.getProject();
	        		currentProjectName = currentProject.getName();
	        		projLabel.setText(currentProject.getName());
	            	viewer.setContentProvider(new FrameworksContentProvider(getViewSite(), currentProject, viewer));
	            	viewer.expandToLevel(2);
	            //	
	            //	
	        		
	        	}
	        }
        }
    }
	
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public FrameworksView() {
		
	}
	
	public void setTreeFilter(String string){
		if(!string.equals(text.getText())) {			
			text.setText(string);
			text.setFocus();
		}
		treeFilter.setSearchText(string);
		viewer.refresh();
		if(string.length() == 0 || string.equals(fDefaultSearchText)){
			text.setText(fDefaultSearchText);
			text.selectAll();
			viewer.collapseAll();
			viewer.expandToLevel(2);
		} else {
			viewer.expandAll();
		}
	}
	
	// Startup and shutdown methods
	public void dispose() {
		getSite().getPage().removePartListener(partListener2);
	}

	private Boolean childrenContainString(TreeParentNode item, String searchStringRegex) {
		if(item.getName().matches(searchStringRegex)) {
			return true;
		}
		if(item.getElement() != null && item.getElement().getAttributes() != null) {			
			Iterator attrs = item.getElement().getAttributes().iterator();
			while(attrs.hasNext()) {
				Attribute attr = (Attribute) attrs.next();
				if(attr.getName().matches(searchStringRegex)) {
					return true;
				}
				if(attr.getValue().matches(searchStringRegex)) {
					return true;
				}
			}
		}
		if(item.hasChildren()) {
			for(TreeParentNode child : item.getChildren()) {
				if(childrenContainString(child,searchStringRegex)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public class TreeFilter extends ViewerFilter {

		private String searchStringRegex;
		private String searchString;

		public void setSearchText(String s) {
			// Search must be a substring of the existing value
			this.searchStringRegex = "(?i)" + s + ".*";
			this.searchString=s;
		}

		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (searchStringRegex == null || searchStringRegex.length() == 0 || searchStringRegex.equals(".*.*")) {
				return true;
			}
			if(element instanceof TreeParentNode) {
				return childrenContainString((TreeParentNode) element, searchStringRegex);
			} else {
				System.err.println("wee");
			}
			return false;
		}
	}
	   
	 /**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.verticalSpacing = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		parent.setLayout(layout);
		
		//add a label here:
		
		//Create a "label" to display information in. I'm
		//using a text field instead of a lable so you can
		//copy-paste out of it.
		text = new Text(parent, SWT.SEARCH | SWT.ICON_CANCEL);
		// layout the text field above the treeviewer
		GridData layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		text.setLayoutData(layoutData);
		text.setText(fDefaultSearchText);
		
		layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
//		layout the tree viewer below the text field
		projLabel = new Label(parent, SWT.NONE);
		projLabel.setText(currentProjectName);
		projLabel.setLayoutData(layoutData);
		
		layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		layoutData.verticalAlignment = GridData.FILL;		
		
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new FrameworksContentProvider(getViewSite(), currentProject, viewer));
		
		viewer.setLabelProvider( new ViewLabelProvider());
		   
		//viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
		viewer.getControl().setLayoutData(layoutData);
		viewer.expandToLevel(2);
		
		treeFilter = new TreeFilter(); 
		viewer.addFilter(treeFilter);
		text.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				if (e.detail == SWT.CANCEL) {
					text.setText(fDefaultSearchText);
					setTreeFilter("");
				}
			}
		});

		text.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				if(text.getText().equals(fDefaultSearchText)){
					text.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(text.getText().length() == 0){
					text.setText(fDefaultSearchText);
				}
			}
			});

		text.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				if(ke.keyCode == SWT.ARROW_DOWN) {
					viewer.getTree().setFocus();
				}
				setTreeFilter(text.getText());
			}
		});

		//Add drop capabilities
		//http://www.eclipse.org/articles/Article-Workbench-DND/drag_drop.html
		
		int ops = DND.DROP_COPY | DND.DROP_MOVE;
		   Transfer[] transfers = new Transfer[] { GenericTransfer.getInstance()};
		   viewer.addDropSupport(ops, transfers, new GenericTreeDropAdapter(viewer));
		
		
		
		//Add a nice big textfield
		GridData layoutData2 = new GridData();
		layoutData2.grabExcessHorizontalSpace = true;
		layoutData2.horizontalAlignment = GridData.FILL;
		layoutData2.heightHint = 80;
		xmlTextEditor = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		xmlTextEditor.setLayoutData(layoutData2);
		
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		hookSingleClickAction();
		contributeToActionBars();
		
		//Add listener
		editorActivated(getViewSite().getPage().getActiveEditor());
		getSite().getPage().addPartListener(partListener2);
		
	}

	private void hookSingleClickAction() {
		viewer.addPostSelectionChangedListener(new ISelectionChangedListener(){

			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				TreeSelection sel = (TreeSelection)event.getSelection();
				if(sel.getFirstElement() != null){
					TreeNode firstElement = (TreeNode)sel.getFirstElement();
					Element element = firstElement.getElement();
					if(element != null){
						
						XMLOutputter outputter = new XMLOutputter();
						String string = outputter.outputString(element);
						xmlTextEditor.setText(string);
					}
				}
				else {
					xmlTextEditor.setText("");
				}
				
			}});
		
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				FrameworksView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
	//	fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
	
		TreeSelection sel = (TreeSelection)viewer.getSelection();
		if(sel.getFirstElement() instanceof TreeParentNode){
			TreeNode selNode = (TreeNode)sel.getFirstElement();
			//get the virtual XML document for this project
			FrameworksContentProvider contentProvider = (FrameworksContentProvider)viewer.getContentProvider();
			Object[] rightClickActions = actionManager.getRightClickActions(selNode, currentProject, contentProvider.getVirtualDocument());
			
			for (int i = 0; i < rightClickActions.length; i++) {
				manager.add((Action)rightClickActions[i]);
			}
		}
		
		//manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
		
		//drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		//manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		//manager.add(action1);
		//manager.add(action2);
		manager.add(refreshAction);
		manager.add(viewXMLAction);
		//manager.add(new Separator());
		//drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		
		csAddBeanAction = new Action(){
			public void run() {
				//showMessage("Executing Add Bean");
				
				
				//CUT
				//try the JDOM in-situ
				
				
				TreeSelection sel = (TreeSelection)viewer.getSelection();
				if(sel.getFirstElement() instanceof TreeParentNode){
					TreeParentNode selNode = (TreeParentNode)sel.getFirstElement();
					if(selNode.getType().equals(FrameworkManager.COLDSPRING)){
						CSAddBeanDialog addDialog = new  CSAddBeanDialog(viewer.getControl().getShell());
					
						if(addDialog.open() == IDialogConstants.OK_ID){
							String id = addDialog.getCfcName().getText();
							String className = addDialog.getCfcExtends().getText();
							System.out.println("Dialog says " + id + " " + className);
							Document document = selNode.getDocument();
							Element child = document.getRootElement();
							
							//Bean Element
							Element newBean = new Element("bean");
							newBean.setAttribute("id", addDialog.getCfcName().getText());
							newBean.setAttribute("class", addDialog.getCfcExtends().getText());
							
							
							child.addContent(newBean);
							XMLOutputter outputter = new XMLOutputter();
							try {
								outputter.output(child, System.out);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						}
						
						
						
					}
					
					
				}
				
				//CUT
			}
		};
		csAddBeanAction.setText("Add bean from CFC");
		csAddBeanAction.setToolTipText("Adds a bean to coldspring from a CFC");
		csAddBeanAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		
		refreshAction = new Action(){
			public void run() {
			 //showMessage("some refresh");
			 
				
			refreshFrameworkTree();
			 
			}
		};
		refreshAction.setImageDescriptor(FWXImages.getImageRegistry().getDescriptor(FWXImages.ICON_REFRESH));
		
		
		viewXMLAction = new Action(){
			public void run() {
				
				ViewXMLDialog vxd = new ViewXMLDialog(getSite().getShell(),  ((FrameworksContentProvider)viewer.getContentProvider()).getVirtualDocument());
				vxd.open();
				// open dialog with just an XML thing
			}
		};
		viewXMLAction.setImageDescriptor(FWXImages.getImageRegistry().getDescriptor(FWXImages.ICON_XML_VIEW));
		
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		action2 = new Action() {
			public void run() {
				
				TreeSelection sel = (TreeSelection)viewer.getSelection();
				if(sel.getFirstElement() instanceof TreeParentNode){
					TreeParentNode selNode = (TreeParentNode)sel.getFirstElement();
					ActionsPreferencePage page = new ActionsPreferencePage();
					page.setFilter(selNode);
					   PreferenceManager mgr = new PreferenceManager();
					   IPreferenceNode node = new PreferenceNode("1", page);
					   mgr.addToRoot(node);
					   PreferenceDialog dialog = new PreferenceDialog(viewer.getControl().getShell(), mgr);
					   dialog.create();
					   dialog.setMessage(page.getTitle());
					   dialog.open();
				}

			}
		};
		action2.setText("Configure...");
		action2.setToolTipText("Configure the actions for this node");
		
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			
			Log clickLogger = LogFactory.getLog(IDoubleClickListener.class);
			
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				TreeSelection sel = (TreeSelection)event.getSelection();
				
				
				//loop through selections maybe?
				
				Iterator nodeIterator = sel.iterator();
				while (nodeIterator.hasNext()) {
					Object element = (Object) nodeIterator.next();
					if (element instanceof TreeParentNode) {
						TreeParentNode selNodeItem = (TreeParentNode) element;
						FrameworksContentProvider contentProvider = (FrameworksContentProvider)viewer.getContentProvider();
						Object[] leftClickActions = actionManager.getLeftClickAction(selNodeItem, currentProject, contentProvider.getVirtualDocument());
						for (int i = 0; i < leftClickActions.length; i++) {
							clickLogger.debug("clicked on" + selNodeItem + " got action  "+ leftClickActions[i]);
							IBaseAction action = (IBaseAction)leftClickActions[i];
							action.run();
						}
						
					}
					
				}
				
				
				 /*if (sel.getFirstElement() instanceof TreeParentNode){
					 
					 
					 
					 TreeParentNode parent = (TreeParentNode)sel.getFirstElement();
					 showMessage(parent.getName() + " " + parent.getType());
					 
					 if(parent.getType().equalsIgnoreCase("framework_cs")){
					 	}
					 	else if(parent.getElement().getName().equalsIgnoreCase("include")){
							openIncludeTemplate(parent);
						}
						else if(parent.getElement().getName().equalsIgnoreCase("message")){
							
						}
						else{
							System.out.println(parent.getElement().getName());
						}
					 	
						
					}
				 else if(sel.getFirstElement() instanceof TreeNode){
					TreeNode selNode = (TreeNode)sel.getFirstElement();
					
					
					
				} */
				
				//doubleClickAction.run();
			}

			private void openIncludeTemplate(TreeNode selNode) {
				FrameworksContentProvider contentProvider = (FrameworksContentProvider)viewer.getContentProvider();
				Document csDocument = contentProvider.getDocument(FrameworkManager.COLDSPRING);
				 try {
					XPath x  = XPath.newInstance("//bean[@id='modelGlueConfiguration']/property[@name='viewMappings']/value");
					Element viewMapping    = (Element)x.selectSingleNode(csDocument);
				
					String templateFile = selNode.getElement().getAttributeValue("template");	
					String pathToTemplateFile = viewMapping.getText().substring(currentProject.getName().length()+1) + "/" + templateFile;
					
					
					IWorkbenchPage page = getSite().getPage();
					IFile file = currentProject.getFile(new Path(pathToTemplateFile));
					
					//Set the path which is project - path;
					 IEditorDescriptor desc = PlatformUI.getWorkbench().
				      getEditorRegistry().getDefaultEditor(file.getName());
					 
					 //from http://wiki.eclipse.org/index.php/FAQ_How_do_I_open_an_editor_on_a_file_in_the_workspace%3F
					 //to open an editor at a marker/line number
					 //IDE.openEditor(page, marker);
					 
					 try {
						page.openEditor(new FileEditorInput(file),desc.getId());
					} catch (PartInitException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				} catch (JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Model Glue",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}