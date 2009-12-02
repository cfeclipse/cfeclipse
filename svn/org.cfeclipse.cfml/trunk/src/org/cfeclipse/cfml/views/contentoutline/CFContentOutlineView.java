/*
 * Created on Apr 6, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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
package org.cfeclipse.cfml.views.contentoutline;

//import org.eclipse.core.resources.IResource;
import java.util.ArrayList;
import java.util.Iterator;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.actions.GenericOpenFileAction;
import org.cfeclipse.cfml.editors.actions.GotoFileAction;
import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.cfeclipse.cfml.parser.docitems.TagItem;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import com.sun.j3d.utils.universe.Viewer;

/**
 * @author Rob
 * 
 *         This is the default content outline view
 */
public class CFContentOutlineView extends ContentOutlinePage implements IPartListener, IPropertyListener,
		ISelectionListener {
	public static final String ID_CONTENTOUTLINE = "org.cfeclipse.cfml.views.contentoutline.cfcontentoutlineview";
	protected Action jumpAction, selectAction, deleteItem, expandAction, collapseAction, filterOnAction, openAction,
			removeFilters;
	protected Action filters[];
	private static String filter = "";
	protected static GotoFileAction gfa = new GotoFileAction();

	public void createControl(Composite parent) {
		super.createControl(parent);
		DocItem root = getRootInput();
		TreeViewer viewer = getTreeViewer();
		viewer.setContentProvider(new OutlineContentProvider(root));
		viewer.setLabelProvider(new OutlineLabelProvider());
		viewer.addSelectionChangedListener(this);
		// this listener listens to the editor (vs. this outline view)
		getSite().getPage().addPostSelectionListener(this);
		createActions();
		createToolbar();
		createContextMenu();
		hookGlobalActions();
		hookDoubleClickAction();
		reload(root);
	}

	/**
	 * Gets a 1 level tree of items in the document of a type. See the parser
	 * for the filter syntax
	 * 
	 * @param filter
	 * @return
	 */
	public DocItem getItems(String filter) {
		DocItem scratch = new TagItem(1, 1, 1, "root");

		DocItem rootItem = getRootInput();
		CFNodeList nodes = rootItem.selectNodes(filter);

		Iterator i = nodes.iterator();
		while (i.hasNext()) {
			try {
				scratch.addChild((DocItem) i.next());
			} catch (Exception e) {
				System.err.println("Tree item set error ");
				e.printStackTrace();
			}
		}

		return scratch;
	}

	/**
	 * Gets the root element of the document. Saves information into a local
	 * cache so if there is an error in the parser it uses the last known good
	 * outline (which kind of sucks but it looks better)
	 * 
	 * @return the root directory
	 */
	public DocItem getRootInput() {
		try {
			DocItem docRoot = null;

			IEditorPart iep = getSite().getPage().getActiveEditor();

			iep.addPropertyListener(this);
			getSite().getPage().addPartListener(this);

			ITextEditor ite = (ITextEditor) iep;
			ICFDocument icfd = null;
			CFDocument cfd = null;
			if (ite.getDocumentProvider().getDocument(iep.getEditorInput()) instanceof ICFDocument) {
				icfd = (ICFDocument) ite.getDocumentProvider().getDocument(iep.getEditorInput());
				cfd = icfd.getCFDocument();
			}
			// icfd.clearAllMarkers();
			// icfd.parseDocument();

			if (cfd != null) {
				docRoot = cfd.getDocumentRoot();
				// lastDocRoot = docRoot;
			}

			if (docRoot != null) {
				return docRoot;
			} else {
				return createFakeRoot();
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		// a fake root
		return createFakeRoot();
	}

	private TagItem createFakeRoot() {
		TagItem tg = new TagItem(1, 1, 1, "Unk");
		return tg;
	}

	/**
	 * gets the currently selected item in docitem form or <code>null</code> if
	 * there is none
	 * 
	 * @return
	 */
	private Iterator getSelectedDocItems() {
		Iterator selecteditem = null;

		// can't do much if nothing is selected
		if (getTreeViewer().getSelection().isEmpty()) {
			return null;
		} else {
			IStructuredSelection selection = (IStructuredSelection) getTreeViewer().getSelection();
			selecteditem = selection.iterator();
		}

		return selecteditem;
	}

	/**
	 * gets the currently selected item in docitem form or <code>null</code> if
	 * there is none
	 * 
	 * @return
	 */
	private DocItem getSelectedDocItem() {
		Iterator selecteditems = getSelectedDocItems();

		// can't do much if nothing is selected
		if (selecteditems == null || !selecteditems.hasNext()) {
			return null;
		}
		return (DocItem) selecteditems.next();
	}

	/**
	 * for recursive parent expansion in setSelectedDocItem
	 * 
	 * @return
	 */
	private void expandParentItems(DocItem item) {
		// can't do much if nothing is selected
		DocItem parentItem = item.getParent();
		if (parentItem != null) {
			TreeViewer tree = getTreeViewer();
			tree.setExpandedState(parentItem, true);
			tree.refresh(parentItem, false);
			expandParentItems(parentItem);
		}
	}

	/**
	 * sets the currently selected item in docitem form or <code>null</code> if
	 * there is none
	 * @return
	 */
	private void setSelectedDocItem(DocItem item) {
		// can't do much if nothing is selected
		if (item != null) {
			TreeViewer tree = getTreeViewer();
			tree.setSelection(new StructuredSelection(item), false);
			//tree.refresh(item, false);
			tree.setExpandedState(item, true);
			tree.reveal(item);
			// reveal takes care of this. Doh!
			//expandParentItems(item);
		}
	}

	/**
	 * Selects the item(s) within the source code view
	 */
	protected void selectItem() {
		// get a handle to the current editor and assign it to our temp action
		IEditorPart iep = getSite().getPage().getActiveEditor();
		Iterator selecteditems = getSelectedDocItems();

		if (!selecteditems.hasNext())
			return;

		ITextEditor editor = (ITextEditor) iep;
		DocItem firstItem = ((DocItem) selecteditems.next());
		int startPos = firstItem.getStartPosition();
		int endPos = firstItem.getEndPosition();
		if (!selecteditems.hasNext()) {
			// select whole tag
			CfmlTagItem cti = (CfmlTagItem) firstItem;
			if (cti.matchingItem != null) {
				if (cti.matchingItem.getStartPosition() < cti.getStartPosition()) {
					startPos = cti.matchingItem.getStartPosition();
					endPos = cti.getEndPosition();
				} else {
					startPos = cti.getStartPosition();
					endPos = cti.matchingItem.getEndPosition();
				}
			} else {
				startPos = cti.getStartPosition();
				endPos = cti.getEndPosition();
			}
		} else {
			// otherwise select selected items
			while (selecteditems.hasNext()) {
				endPos = ((DocItem) selecteditems.next()).getEndPosition();
			}
		}
		editor.selectAndReveal(startPos, endPos - startPos + 1);
	}

	/**
	 * Gets the selected item parses it, and adds the defined stuff to the
	 * editor
	 */
	protected void jumpToItem() {
		// get a handle to the current editor and assign it to our temp action
		IEditorPart iep = getSite().getPage().getActiveEditor();
		DocItem selecteditem = getSelectedDocItem();

		if (selecteditem == null)
			return;

		ITextEditor editor = (ITextEditor) iep;
		// editor.setHighlightRange(selecteditem.getStartPosition(),selecteditem.getMatchingItem().getEndPosition(),true);
		editor.setHighlightRange(selecteditem.getStartPosition(), 0, true);
	}

	/**
	 * filters the outline on the currenly selected element
	 */
	protected void filterOnSelected() {
		DocItem selecteditem = getSelectedDocItem();

		if (selecteditem == null)
			return;

		filter = "//" + selecteditem.getName();
		reload();
	}

	public void reload(DocItem root) {
		// saveExpandedElements();

		getTreeViewer().setInput(root);
		// temporary hack to expand a cfcomponent
		if (root.hasChildren() && root.getFirstChild().getName().compareToIgnoreCase("cfcomponent") == 0) {
			getTreeViewer().setExpandedState(root.getFirstChild(), true);
			getTreeViewer().refresh(root, false);
		}
		if (filter.length() == 0) {
			removeFilters.setEnabled(false);
		} else {
			removeFilters.setEnabled(true);
		}
	}

	public void reload() {
		if (filter.length() == 0) {
			DocItem di = getRootInput();
			reload(di);
		} else {
			reload(getItems(filter));
		}
	}

	/**
	 * creates all the default actions
	 */
	protected void createActions() {
		jumpAction = new Action("Jump To", CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_SHOW)) {
			public void run() {
				// we only want to jump if the change came from tree viewer
				// selection
				jumpToItem();
			}
		};
		jumpAction.setToolTipText("Jump to selected tag in the editor");

		selectAction = new Action("Select", CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_SHOW)) {
			public void run() {
				selectItem();
			}
		};
		selectAction.setToolTipText("Select tag in the editor");

		filterOnAction = new Action("Filter On This", CFPluginImages.getImageRegistry().getDescriptor(
				CFPluginImages.ICON_TAG)) {
			public void run() {
				filterOnSelected();
			}
		};
		filterOnAction.setToolTipText("Filter on selected tag type");

		expandAction = new Action("Expand All", AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.search",
				"$nl$/icons/full/elcl16/expandall.gif")) {
			public void run() {
				getTreeViewer().expandAll();
			}
		};
		expandAction.setToolTipText("Expand all");

		collapseAction = new Action("Collapse All", AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.search",
				"$nl$/icons/full/elcl16/collapseall.gif")) {
			public void run() {
				getTreeViewer().collapseAll();
			}
		};
		collapseAction.setToolTipText("Collapse all");

		openAction = new Action("Open File", CFPluginImages.getImageRegistry()
				.getDescriptor(CFPluginImages.ICON_IMPORT)) {
			public void run() {
				openFile();
			}
		};

		removeFilters = new Action("Remove Filter", CFPluginImages.getImageRegistry().getDescriptor(
				CFPluginImages.ICON_DELETE)) {
			public void run() {
				filter = "";
				reload();
			}
		};
		removeFilters.setToolTipText("Remove Filter");

		deleteItem = new Action("Delete Item", CFPluginImages.getImageRegistry().getDescriptor(
				CFPluginImages.ICON_DELETE)) {
			public void run() {
				// this doesn't do nothing
				System.out.println("this should delete");
			}
		};

		// /filters
		filters = new Action[6];

		filters[0] = new Action("Include", CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)) {
			public void run() {
				filter = "//cfinclude";
				reload(getItems(filter));
			}
		};

		filters[1] = new Action("Module", CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)) {
			public void run() {
				filter = "//cfmodule";
				reload(getItems(filter));
			}
		};

		filters[2] = new Action("Query", CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)) {
			public void run() {
				filter = "//cfquery";
				reload(getItems(filter));
			}
		};

		filters[3] = new Action("Set", CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)) {
			public void run() {
				filter = "//cfset";
				reload(getItems(filter));
			}
		};

		filters[4] = new Action("Case", CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)) {
			public void run() {
				filter = "//cfcase";
				reload(getItems(filter));
			}
		};

		filters[5] = new Action("Custom", CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)) {
			public void run() {
				InputDialog pathdialog = new InputDialog(getSite().getShell(), "CFML Path Filter",
						"Filter outline using path (i.e. \"//cfoutput\" for all the cfoutput tags):", "", null);

				if (pathdialog.open() == org.eclipse.jface.window.Window.OK) {
					String xpath = pathdialog.getValue();
					if (xpath.length() > 0) {
						filter = xpath;
						reload(getItems(filter));
					}
				}
			}
		};
	}

	private void createContextMenu() {
		// Create menu manager.
		MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				fillContextMenu(mgr);
			}
		});

		// Create menu.
		Menu menu = menuMgr.createContextMenu(getTreeViewer().getControl());
		getTreeViewer().getControl().setMenu(menu);
	}

	private void fillContextMenu(IMenuManager mgr) {
		mgr.add(selectAction);
		mgr.add(new Separator());
		mgr.add(filterOnAction);
		DocItem di = getSelectedDocItem();
		if (di != null) {
			String sname = di.getName();
			if (filter.length() > 0) {
				mgr.add(removeFilters);
			}
			if (sname.equals("cfinclude") || sname.equals("cfmodule")) {
				mgr.add(openAction);
			}
		}
		mgr.add(new Separator());
		int flen = filters.length;
		for (int i = 0; i < flen; i++) {
			mgr.add(filters[i]);
		}

	}

	protected void createToolbar() {
		IToolBarManager toolbarManager = super.getSite().getActionBars().getToolBarManager();
		toolbarManager.add(expandAction);
		toolbarManager.add(collapseAction);
		toolbarManager.add(selectAction);
		toolbarManager.add(filterOnAction);
		toolbarManager.add(removeFilters);
	}

	/**
	 * method to open the currently selected docitem if its a include or a
	 * module item
	 */
	private void openFile() {
		IEditorPart iep = getSite().getPage().getActiveEditor();
		DocItem selecteditem = getSelectedDocItem();
		if (selecteditem == null)
			return;

		String si = selecteditem.getName();
		if (si.equalsIgnoreCase("cfinclude") || si.equalsIgnoreCase("cfmodule")) {
			gfa.setActiveEditor(null, iep);
			gfa.run(null);
		}
	}

	private void hookDoubleClickAction() {
		getTreeViewer().addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				selectAction.run();
			}
		});
	}

	private void hookGlobalActions() {
		IActionBars bars = getSite().getActionBars();
		bars.setGlobalActionHandler(IWorkbenchActionConstants.DELETE, deleteItem);
		getTreeViewer().getControl().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.character == SWT.DEL && event.stateMask == 0 && deleteItem.isEnabled()) {
					deleteItem.run();
				}
			}
		});
	}

	public void selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent event) {
		// this fires when tree node selection changed
		// we check for a tree node so we don't make the editor jump
		if(((TreeSelection)event.getSelection()).getPaths().length > 0) {			
			jumpAction.run();
		}
		// getTreeViewer().refresh(new
		// StructuredSelection(event.getSelection()));
	}

	public void selectionChanged(IWorkbenchPart workbench, ISelection selection) {
		// this fires when editor is changed (thanks to addPostSelection in
		// createControl)
		if (selection != null && selection instanceof ITextSelection) {
			IEditorPart curEditor = workbench.getSite().getWorkbenchWindow().getActivePage().getActiveEditor();
			if (curEditor != null && curEditor instanceof CFMLEditor) {
				CFMLEditor curDoc = (CFMLEditor) curEditor;
				DocItem tagItem = curDoc.getSelectionCursorListener().getSelectedTag();
				if (tagItem != null) {
					setSelectedDocItem(tagItem);
				}
			}
		}
	}

	public void partActivated(IWorkbenchPart arg0) {
		// TODO Auto-generated method stub

	}

	public void partBroughtToTop(IWorkbenchPart arg0) {
		// TODO Auto-generated method stub

	}

	public void partClosed(IWorkbenchPart arg0) {
		// TODO Auto-generated method stub

	}

	public void partDeactivated(IWorkbenchPart arg0) {
		// TODO Auto-generated method stub

	}

	public void partOpened(IWorkbenchPart arg0) {
		// TODO Auto-generated method stub

	}

	public void propertyChanged(Object arg0, int arg1) {
		// TODO Auto-generated method stub

	}

}
