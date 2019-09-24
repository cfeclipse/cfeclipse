package org.cfeclipse.cfml.editors;

import org.eclipse.swt.widgets.Composite;
import org.cfeclipse.cfml.editors.breadcrumb.BreadcrumbViewer;
import org.cfeclipse.cfml.editors.breadcrumb.EditorBreadcrumb;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.texteditor.ITextEditor;



/**
 * The breadcrumb for the Java editor. Shows Java elements. Requires a Java editor.
 *
 * @since 3.4
 */
public class CFMLEditorBreadcrumb extends EditorBreadcrumb {

	public CFMLEditorBreadcrumb(ITextEditor editor) {
        super(editor);
        // TODO Auto-generated constructor stub
    }

    private static final boolean SHOW_LIBRARIES_NODE= true;


    @Override
    protected Object getCurrentInput() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected BreadcrumbViewer createViewer(Composite parent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected boolean reveal(Object element) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean open(Object element) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected ActionGroup createContextMenuActionGroup(ISelectionProvider selectionProvider) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void activateBreadcrumb() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void deactivateBreadcrumb() {
        // TODO Auto-generated method stub
        
    }
}