package org.cfeclipse.cfml.editors.dnd.future;

import java.util.Iterator;

import org.eclipse.ui.part.PluginTransfer;
import org.eclipse.ui.part.PluginTransferData;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
/**
 * Supports dragging gadgets from a structured viewer.
 */
public class CFEDragListener extends DragSourceAdapter {
   private StructuredViewer viewer;
   public CFEDragListener(StructuredViewer viewer) {
      this.viewer = viewer;
   }
   /**
    * Method declared on DragSourceListener
    */
   public void dragFinished(DragSourceEvent event) {
      if (!event.doit)
         return;
      //if the gadget was moved, remove it from the source viewer
      if (event.detail == DND.DROP_MOVE) {
         IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
         for (Iterator it = selection.iterator(); it.hasNext();) {
            //((Gadget)it.next()).setParent(null);
         }
         viewer.refresh();
      }
   }
   /**
    * Method declared on DragSourceListener
    */
   public void dragSetData(DragSourceEvent event) {
      IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
      //Gadget[] gadgets = (Gadget[])selection.toList().toArray(new Gadget[selection.size()]);
      if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
         event.data = selection;
      } else if (PluginTransfer.getInstance().isSupportedType(event.dataType)) {
         //byte[] data = TextTransfer.getInstance().totoByteArray();
         //event.data = new PluginTransferData("org.eclipse.ui.examples.gdt.gadgetDrop", data);
      }
   }
   /**
    * Method declared on DragSourceListener
    */
   public void dragStart(DragSourceEvent event) {
      event.doit = !viewer.getSelection().isEmpty();
   }
}