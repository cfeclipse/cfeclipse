/*
 * Created on Jan 24, 2005
 * by Christopher Bradford
 *
 */
package com.rohanclan.cfml.editors.actions;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorActionDelegate;

/**
 * @author Christopher Bradford
 *
 *	This class manages references to actions (IActionDelegate) that can then be repeated.
 *	Any object that implements IActionDelegate can register itself as the last action
 *	(optionally for a given ITextEditor).
 *
 */
public class LastActionManager {

    private IActionDelegate lastAction;
    private Map lastActionsByEditor;
    
    /**
     * 
     * Manages the last Action that was used
     */
    public LastActionManager() {
        this.lastAction = new GenericEncloserAction();
        this.lastActionsByEditor = new HashMap();
    }

    /**
     * @param editor the editor in which to perform the action
     * @return the last Action for the given editor. If none is registered for the given editor, return the last action registered.
     */
    public IActionDelegate getLastActionForEditor(ITextEditor editor) {
        if(editor != null) {
            IActionDelegate act = (IActionDelegate)this.lastActionsByEditor.get(editor);
            if (act != null) {
                // System.out.println("Returning last action for editor: " + act.getClass().getName());
                return act;
            }
        }
        // System.out.println("Returning last action: " + this.lastAction.getClass().getName());
        return this.lastAction;
    }
    
    /**
     * @return the last Action, regardless of editor
     */
    public IActionDelegate getLastAction() {
        // System.out.println("Returning last action: " + this.lastAction.getClass().getName());
        return this.lastAction;
    }
    
    /**
     * @param lastAction the Action to set
     */
    public void setLastAction(IActionDelegate lastAction) {
        setLastAction(null, lastAction);
    }

    /**
     * @param editor the editor for which to set the last action
     * @param lastAction the Action to set
     */
    public void setLastAction(ITextEditor editor, IActionDelegate lastAction) {
        if(editor != null) {
            this.lastActionsByEditor.put(editor, lastAction);
        }
        this.lastAction = lastAction;
        // System.out.println("Setting last action: " + lastAction.getClass().getName());
    }

    /**
     * @param editor the editor for which to remove the action
     */
    public void removeAction(ITextEditor editor) {
        if(editor != null) {
            this.lastActionsByEditor.remove(editor);
        }
        // System.out.println("Removing action");
    }
}
