package org.cfeclipse.cfml.editors.dnd.future;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.part.IDropActionDelegate;

/**
 * Performs dropping of CFMLs into views that contain resources.
 */
public class CFMLPluginDropAdapter implements IDropActionDelegate {
   /**
    * Method declared on IDropActionDelegate
    */
   public boolean run(Object source, Object target) {
      if (target instanceof IContainer) {
         CFMLTransfer transfer = CFMLTransfer.getInstance();
         File[] CFMLs = transfer.fromByteArray((byte[])source);
         IContainer parent = (IContainer)target;
         for (int i = 0; i < CFMLs.length; i++) {
            writeCFMLFile(parent, CFMLs[i]);
         }
         return true;
      }
      //drop was not successful so return false
      return false;
   }
   private void writeCFMLFile(IContainer parent, File CFML) {
      try {
         IFile file = parent.getFile(new Path(CFML.getName()));
         ByteArrayInputStream in = createFileContents(CFML);
         if (file.exists()) {
            file.setContents(in, IResource.NONE, null);
         } else {
            file.create(in, IResource.NONE, null);
         }
      } catch (CoreException e) {
         e.printStackTrace();
      }
   }
   private ByteArrayInputStream createFileContents(File CFML) {
      //write the hierarchy of CFMLs to string
      StringBuffer buf = new StringBuffer();
      writeCFMLString(CFML, buf, 0);
      return new ByteArrayInputStream(buf.toString().getBytes());
   }
   private void writeCFMLString(File CFML, StringBuffer buf, int depth) {
      for (int i = 0; i < depth; i++)
         buf.append('\t');
      buf.append(CFML.getName());
      buf.append('\n');
//      CFML[] children = CFML.getChildren();
//      for (int i = 0; i < children.length; i++)
//         writeCFMLString(children[i], buf, depth + 1);
   }
}