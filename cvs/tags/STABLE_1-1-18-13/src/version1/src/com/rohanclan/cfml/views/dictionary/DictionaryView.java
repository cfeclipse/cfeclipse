package com.rohanclan.cfml.views.dictionary;

import java.io.File;
import java.util.Properties;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.*;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
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
import org.eclipse.core.runtime.IAdaptable;
import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.dictionary.Function;
import com.rohanclan.cfml.dictionary.ScopeVar;
import com.rohanclan.cfml.dictionary.SyntaxDictionary;
import com.rohanclan.cfml.dictionary.Tag;
import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.views.browser.BrowserView;
import com.rohanclan.cfml.views.cfcmethods.CFCMethodViewItem;
import com.rohanclan.cfml.views.packageview.FolderNode;
import com.rohanclan.cfml.views.packageview.FolderTypes;
import com.rohanclan.cfml.views.snips.SnipDialog;
import com.rohanclan.cfml.views.snips.SnipDoubleClickListener;
import com.rohanclan.cfml.views.snips.SnipTreeViewContentProvider;
import com.rohanclan.cfml.views.snips.SnipTreeViewLabelProvider;
import com.rohanclan.cfml.views.snips.SnipVarItem;
import com.rohanclan.cfml.editors.actions.Encloser;
import com.rohanclan.cfml.editors.actions.GenericEncloserAction;
import com.rohanclan.cfml.editors.actions.GetHelpAction;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class DictionaryView extends ViewPart {
	private TreeViewer viewer;

	private DrillDownAdapter drillDownAdapter;

	private Action action1;

	private Action action2;

	private Action doubleClickAction;

	private Action viewinfo;

	private Action viewhelp;

	protected Text text, preview;

	protected Label previewLabel;

	protected LabelProvider labelProvider;

	public static final String ID_DICTIONARY = "com.rohanclan.cfml.views.dictionary";

	/*
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */

	private final class DoubleClickAction implements IDoubleClickListener {
		public void doubleClick(DoubleClickEvent event) {
			doubleClickAction.run();
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
				if (invisibleRoot == null)
					initialize();
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}

		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject) child).getParent();
			}
			return null;
		}

		public Object[] getChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent) parent).getChildren();
			}
			return new Object[0];
		}

		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent) parent).hasChildren();
			return false;
		}

		/*
		 * We will set up a dummy model to initialize tree heararchy. In a real
		 * code, you will connect to a real model and expose its hierarchy.
		 */
		private void initialize() {

			TreeParent root = new TreeParent("Dictionaries");

			Map dicts = DictionaryManager.getDictionaries();
			Iterator dictIter = dicts.keySet().iterator();

			while (dictIter.hasNext()) {

				String currObject = dictIter.next().toString();
				TreeParent dic = new TreeParent(currObject);

				Iterator tagIter = null;
				TreeParent tags = new TreeParent("tags");
				Iterator funcIter = null;
				TreeParent functions = new TreeParent("functions");
				Iterator scopeIter = null;
				TreeParent scopes = new TreeParent("scopes");

				SyntaxDictionary syntaxitems = DictionaryManager
						.getDictionary(currObject);

				/*
				 * Loop through the tags
				 */
				try {
					//loop through the children I guess
					tagIter = syntaxitems.getAllTags().iterator();

					while (tagIter.hasNext()) {
						Tag currTag = (Tag) tagIter.next();
						TagItem tag = new TagItem(currTag.getName());
						tag.setDictionary(syntaxitems);

						tags.addChild(tag);

					}
				} catch (Throwable ex) {
					System.out
							.println("Errror whilst trying to get the tags for: "
									+ currObject.toString());
					ex.printStackTrace();
				}

				/*
				 * Loop through the functions
				 */
				try {
					//loop through the children I guess
					funcIter = syntaxitems.getAllFunctions().iterator();

					while (funcIter.hasNext()) {
						Function currFunc = (Function) funcIter.next();
						FunctionItem func = new FunctionItem(currFunc.getName());
						func.setDictionary(syntaxitems);
						functions.addChild(func);
					}
				} catch (Throwable ex) {
					System.out
							.println("Errror whilst trying to get the functions");
					ex.printStackTrace();
				}

				/*
				 * Loop through the scopes
				 */

				try {
					scopeIter = syntaxitems.getAllScopeVars().iterator();

					while (scopeIter.hasNext()) {
						ScopeVar currScope = (ScopeVar) scopeIter.next();
						ScopeItem scopeitem = new ScopeItem(currScope
								.getValue());
						scopeitem.setDictionary(syntaxitems);
						scopes.addChild(scopeitem);

					}
				} catch (Throwable ex) {
					System.out
							.println("Errror whilst trying to get the scopes");
					ex.printStackTrace();
				}

				if (tags.hasChildren()) {
					dic.addChild(tags);
				}
				if (functions.hasChildren()) {
					dic.addChild(functions);
				}
				if (scopes.hasChildren()) {
					dic.addChild(scopes);
				}
				root.addChild(dic);

			}

			/*
			 * TreeObject to1 = new TreeObject("Leaf 1"); TreeObject to2 = new
			 * TreeObject("Leaf 2"); TreeObject to3 = new TreeObject("Leaf 3");
			 * TreeParent p1 = new TreeParent("Parent 1"); p1.addChild(to1);
			 * p1.addChild(to2); p1.addChild(to3);
			 * 
			 * TreeObject to4 = new TreeObject("Leaf 4"); TreeParent p2 = new
			 * TreeParent("Parent 2"); p2.addChild(to4);
			 * 
			 * TreeParent root = new TreeParent("Root"); root.addChild(p1);
			 * root.addChild(p2);
			 * 
			 * invisibleRoot = new TreeParent(""); invisibleRoot.addChild(root);
			 */

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
			this.folderIcons.put(FolderTypes.WWWROOT,
					CFPluginImages.ICON_PVIEW_FOLDER_WWW);
			this.folderIcons.put(FolderTypes.CFCROOT,
					CFPluginImages.ICON_PVIEW_FOLDER_CFC);
			this.folderIcons.put(FolderTypes.CF_ROOT,
					CFPluginImages.ICON_PVIEW_FOLDER_CUS);
		}

		public String getText(Object obj) {
			return obj.toString();
		}

		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;

			if (obj instanceof FunctionItem) {

				imageKey = CFPluginImages.ICON_FUNC;
				return CFPluginImages.get(imageKey);
			} else if (obj instanceof TagItem) {
				imageKey = CFPluginImages.ICON_TAG;
				return CFPluginImages.get(imageKey);

			} else if (obj instanceof ScopeItem) {
				imageKey = CFPluginImages.ICON_SERVER;
				return CFPluginImages.get(imageKey);

			} else if (obj instanceof TreeParent) {
				imageKey = ISharedImages.IMG_OBJ_FOLDER;

			}

			return PlatformUI.getWorkbench().getSharedImages().getImage(
					imageKey);
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
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		try {
			//	  Create a grid layout object so the text and treeviewer
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			layout.verticalSpacing = 0;
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			parent.setLayout(layout);
			
			//This is what makes the controls resizable
	        SashForm sash = new SashForm(parent,SWT.VERTICAL);
	        GridData sashData = new GridData(GridData.FILL_BOTH);
	        sashData.horizontalSpan = 2;
	        sash.setLayoutData(sashData);
	        
	        sash.setLayout(new FillLayout());
	        
	        // Create a layout with no margins for the containers below
	        GridLayout containerLayout = new GridLayout();
	        containerLayout.marginHeight = 0;
	        containerLayout.marginWidth = 0;
	        
	        // Container for the top half of the view
	        Composite topHalf = new Composite(sash,SWT.BORDER);
	        topHalf.setLayout(containerLayout);
	        topHalf.setLayoutData(new GridData(GridData.FILL_BOTH));
	        
	        // Container for the bottom half of the view
	        Composite bottomHalf = new Composite(sash,SWT.BORDER);
	        bottomHalf.setLayout(containerLayout);
	        bottomHalf.setLayoutData(new GridData(GridData.FILL_BOTH));
	        
	        // The text item to hold the name of the currently selected dictionary item
			text = new Text(topHalf, SWT.READ_ONLY | SWT.SINGLE | SWT.BORDER);
			GridData layoutData = new GridData();
			layoutData.grabExcessHorizontalSpace = true;
			layoutData.horizontalAlignment = GridData.FILL;
			text.setLayoutData(layoutData);

			// The dictionary tree viewer
			viewer = new TreeViewer(topHalf, SWT.RESIZE | SWT.BORDER);
			drillDownAdapter = new DrillDownAdapter(viewer);
			viewer.setContentProvider(new ViewContentProvider());
			viewer.setLabelProvider(new ViewLabelProvider());
			viewer.setSorter(new NameSorter());
			viewer.setInput(getViewSite());
			viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
			
		
			// The title block for the preview area
			previewLabel = new Label(bottomHalf, SWT.WRAP);
			GridData gridData = new GridData();
			gridData.horizontalSpan = 2;
			gridData.horizontalIndent = 5;
			previewLabel.setLayoutData(gridData);
			previewLabel.setText("Preview"); //$NON-NLS-1$
			
			
			// The text box that contains the preview
			preview = new Text(bottomHalf, SWT.READ_ONLY | SWT.MULTI | SWT.BORDER
					| SWT.V_SCROLL | SWT.H_SCROLL | SWT.RESIZE);
			preview.setLayoutData(new GridData(GridData.FILL_BOTH));
			preview.setBackground(new Color(Display.getCurrent(),255,255,255));

			makeActions();
			hookContextMenu();
			hookDoubleClickAction();
			//contributeToActionBars();
			hookListeners();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		//manager.add(action1);
		//manager.add(action2);
		//manager.add(viewinfo);
		manager.add(viewhelp);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		//manager.add(action1);
		//manager.add(action2);
		manager.add(viewhelp);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		viewhelp = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();
				String urldest = "http://www.cfdocs.org/";
				String keyword = "";
				//			Get thecurrent page
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				IViewReference ref[] = page.getViewReferences();
				System.out.println(page.getLabel());
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
		//viewinfo.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

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
		viewinfo.setToolTipText("View this items informatino");
		viewinfo.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();
				//showMessage("Double-click detected on "+obj.toString());
				viewTag(obj);
			}
		};
	}

	protected void hookListeners() {
		//add a selection listener so we can look at the selected file and
		//get the help information out
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				String name;
				String desc;

				ISelection selection = event.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();

				// if the selection is empty clear the label
				if (event.getSelection().isEmpty()) {
					text.setText("");
					preview.setText("");
					return;
				}

				if (obj instanceof TagItem) {
					TagItem tg = (TagItem) obj;
					text.setText(tg.getName());
					preview.setText(tg.getHelp());
				} else if (obj instanceof FunctionItem) {
					FunctionItem func = (FunctionItem) obj;
					text.setText(func.getName());
					preview.setText(func.getHelp());
				} else if (obj instanceof ScopeItem) {
					ScopeItem scopei = (ScopeItem) obj;
					text.setText(scopei.getName());
					preview.setText(scopei.getHelp());
				} else {
					text.setText("");
					preview.setText("");
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
	 * checks what is selected in the tree and then opens the call to the dialog
	 * box
	 * 
	 * @param obj
	 */

	protected void viewTag(Object obj) {

		if (obj instanceof TagItem) {
			try {
				TagViewer tagview = new TagViewer(this.getViewSite().getShell());
				TagItem tg = (TagItem) obj;
				tagview.setTitle(tg.getName());
				tagview.setTag(tg);
				Set attribs = tg.getDictionary().getElementAttributes(
						tg.getName());
				tagview.setAtributes(attribs);
				// This will hold the attribute name and value pairs after the
				// dialog is closed
				Properties fieldStore = new Properties();
				tagview.setFieldStore(fieldStore);

				// Open the dialog and check if the OK button was pressed
				if (tagview.open() == IDialogConstants.OK_ID) {

					// OK button was pressed. Check the values and do whatever
					// we need to with them.
					
					//Get Info about the editor
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					IEditorPart iep = this.getViewSite().getWorkbenchWindow().getActivePage().getActiveEditor();
					IDocument doc =  ((ITextEditor)iep).getDocumentProvider().getDocument(iep.getEditorInput());
					ITextEditor ite = (ITextEditor)iep;
					ISelection sel = ite.getSelectionProvider().getSelection();
					int cursorOffset = ((ITextSelection)sel).getOffset();
					int selectionLength = ((ITextSelection)sel).getLength();
					Encloser encloser = new Encloser();
					//-> this inserts it encloser.enclose(doc,(ITextSelection)sel,selectedMethod.getInsertString(),"");
					
					
					
					
					//End Get info about the editor
					TagFormatter tf = new TagFormatter(tg.getTag());
					if(selectionLength > 0){
						tf.setWrapping(true);
					}
					
					Enumeration e = fieldStore.keys();
					while (e.hasMoreElements()) {
						//We could pass the attributes back to the tag
						//Item. or we could do something more radical like have a TagFormatter
						String attribute = e.nextElement().toString();
						String value = fieldStore.get(attribute).toString();
						tf.addAttribute(attribute, value);

						
						System.out.println(attribute + "," + value);
					}
					System.out.println(tf.toString());
					
					encloser.enclose(doc,(ITextSelection)sel,tf.getTagStart(),tf.getTagEnd());
					
					
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		/*
		 * else if (obj instanceof FunctionItem){ FunctionItem func =
		 * (FunctionItem)obj; tagview.setTitle(func.getName()); tagview.open();
		 *  } else if(obj instanceof ScopeItem){ ScopeItem scopei =
		 * (ScopeItem)obj; tagview.setTitle(scopei.getName());
		 * //showMessage(scopei.getName()); tagview.open();
		 *  } else{ tagview.close(); }
		 */

	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}