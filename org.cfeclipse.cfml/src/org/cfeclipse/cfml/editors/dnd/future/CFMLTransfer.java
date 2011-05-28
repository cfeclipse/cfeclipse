package org.cfeclipse.cfml.editors.dnd.future;

import java.io.*;

import org.eclipse.core.resources.IResource;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;
/**
 * Class for serializing CFMLs to/from a byte array
 */
public class CFMLTransfer extends ByteArrayTransfer {
   private static CFMLTransfer instance = new CFMLTransfer();
   private static final String TYPE_NAME = "CFML-transfer-format";
   private static final int TYPEID = registerType(TYPE_NAME);

   /**
    * Returns the singleton CFML transfer instance.
    */
   public static CFMLTransfer getInstance() {
      return instance;
   }
   /**
    * Avoid explicit instantiation
    */
   private CFMLTransfer() {
   }
   protected File[] fromByteArray(byte[] bytes) {
      DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));

      try {
         /* read number of CFMLs */
         int n = in.readInt();
         /* read CFMLs */
         File[] CFMLs = new File[n];
         for (int i = 0; i < n; i++) {
            File CFML = readCFML(null, in);
            if (CFML == null) {
               return null;
            }
            CFMLs[i] = CFML;
         }
         return CFMLs;
      } catch (IOException e) {
         return null;
      }
   }
   /*
    * Method declared on Transfer.
    */
   protected int[] getTypeIds() {
      return new int[] { TYPEID };
   }
   /*
    * Method declared on Transfer.
    */
   protected String[] getTypeNames() {
      return new String[] { TYPE_NAME };
   }
   /*
    * Method declared on Transfer.
    */
   protected void javaToNative(Object object, TransferData transferData) {
      byte[] bytes = toByteArray((File[])object);
      if (bytes != null)
         super.javaToNative(bytes, transferData);
   }
   /*
    * Method declared on Transfer.
    */
   protected Object nativeToJava(TransferData transferData) {
      byte[] bytes = (byte[])super.nativeToJava(transferData);
      return fromByteArray(bytes);
   }
   /**
    * Reads and returns a single CFML from the given stream.
    */
   private File readCFML(File parent, DataInputStream dataIn) throws IOException {
      /**
       * CFML serialization format is as follows:
       * (String) name of CFML
       * (int) number of child CFMLs
       * (CFML) child 1
       * ... repeat for each child
       */
      String name = dataIn.readUTF();
      int n = dataIn.readInt();
      File newParent = new File(parent, name);
      for (int i = 0; i < n; i++) {
         readCFML(newParent, dataIn);
      }
      return newParent;
   }
   protected byte[] toByteArray(File[] CFMLs) {
      /**
       * Transfer data is an array of CFMLs.  Serialized version is:
       * (int) number of CFMLs
       * (CFML) CFML 1
       * (CFML) CFML 2
       * ... repeat for each subsequent CFML
       * see writeCFML for the (CFML) format.
       */
      ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(byteOut);

      byte[] bytes = null;

      try {
         /* write number of markers */
         out.writeInt(CFMLs.length);

         /* write markers */
         for (int i = 0; i < CFMLs.length; i++) {
            writeCFML((File)CFMLs[i], out);
         }
         out.close();
         bytes = byteOut.toByteArray();
      } catch (IOException e) {
         //when in doubt send nothing
      }
      return bytes;
   }
   /**
    * Writes the given CFML to the stream.
    */
   private void writeCFML(File CFML, DataOutputStream dataOut) throws IOException {
      /**
       * CFML serialization format is as follows:
       * (String) name of CFML
       * (int) number of child CFMLs
       * (CFML) child 1
       * ... repeat for each child
       */
      dataOut.writeUTF(CFML.getName());
//      IResource[] children = CFML.getgetChildren();
//      dataOut.writeInt(children.length);
//      for (int i = 0; i < children.length; i++) {
//         writeCFML(children[i], dataOut);
//      }
   }
}