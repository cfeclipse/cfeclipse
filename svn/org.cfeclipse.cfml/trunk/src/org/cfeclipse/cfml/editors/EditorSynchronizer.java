/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.cfeclipse.cfml.editors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.link.ILinkedModeListener;
import org.eclipse.jface.text.link.LinkedModeModel;

/**
 * Synchronizes the Ant editor with the state of its linked mode
 * 
 * @since 3.1
 */
public class EditorSynchronizer implements ILinkedModeListener {
    
    private final CFMLEditor fEditor;
    private final boolean fWasOccurrencesOn;
    
    /**
     * Creates a new synchronizer.
     * 
     * @param editor the Ant editor that will be 
     *        synchonized with the linked mode
     */
    public EditorSynchronizer(CFMLEditor editor) {
        Assert.isLegal(editor != null);
        fEditor= editor;
        if(fEditor.getSelectionCursorListener() != null) {
        	fWasOccurrencesOn = fEditor.getSelectionCursorListener().isMarkingOccurrences();        	
        } else {
        	fWasOccurrencesOn = false;
        }
        fEditor.setInLinkedMode(true, fWasOccurrencesOn);
    }

    /*
     * @see org.eclipse.jface.text.link.ILinkedModeListener#left(org.eclipse.jface.text.link.LinkedModeModel, int)
     */
    public void left(LinkedModeModel environment, int flags) {
    	fEditor.setInLinkedMode(false, fWasOccurrencesOn);
    }

    /*
     * @see org.eclipse.jface.text.link.ILinkedModeListener#suspend(org.eclipse.jface.text.link.LinkedModeModel)
     */
    public void suspend(LinkedModeModel environment) {
    }

    /*
     * @see org.eclipse.jface.text.link.ILinkedModeListener#resume(org.eclipse.jface.text.link.LinkedModeModel, int)
     */
    public void resume(LinkedModeModel environment, int flags) {
    }
}