/*
 * Created on Feb 26, 2004
 *
 */
package com.keygeotech.utils;

/**
 * @author OLIVER
 * 
 * Debuging class - remove for final builds
 * @deprecated -r2- this is here to remind you to remove the class and any 
 * refrences to it when you do a build
 */
public class Debug {
	private static boolean debug = false;
	private static int debugLevel = 1;
	
	public static String getClassName(Object inClass)
	{
		return inClass.getClass().getName();
	}
	
	public static void println(String method, Object callingClass, String message)
	{
		String outName = getClassName(callingClass);
		if(outName.length() > 50)
			outName.substring(50, outName.length());
		
		println(outName + "::" + method + "-" + message);
	}
	
	public static void println(Object callingClass, String message)
	{
		String outStr = getClassName(callingClass) + " - " + message;
		
		println(outStr);
	}
	
	public static void println(String message)
	{
		if(debug)
			System.err.println(message);
	}
	
	public static void main(String[] args) {
		
	}
	
	/** @return The file and line number of the first calling function
	    * outside this package
	    */
	/*
	  public static String GetFileAndLine() {
	      String result = new String();
	      try {
		 //	 PrintWriter pw = new PrintWriter(System.out);
	 	 String temp = new String("c:\\logfile.txt"
	                                  + Thread.currentThread().toString()
					  + System.getProperty("user.name")
					  );
		 PrintWriter pw = new PrintWriter(new FileOutputStream(temp));
		 new Exception().printStackTrace(pw);
		 pw.close();
	 
		 FileInputStream in = new FileInputStream(temp);
		 BufferedReader f =
		    new BufferedReader(new InputStreamReader(in));
	 
		 f.readLine();
		 // Skip stack trace from this package
		 int index = -1;
		 String line;
		 do {
		    line = f.readLine();
		    index = line.indexOf("Logfile");
		 } while (-1 != index);
		 f.close();
	 
		 char c[] = new char [line.length()];
		 line.getChars(0, line.length(), c, 0);
		 int i = 0;
		 while (i < line.length() && c[i] != `(`) {
		    i++;
		 }
		 int start = ++i;
		 while (i < line.length() && c[i] != `)`) {
		    i++;
		 }
		 int end = i;
		 result = line.substring(start, end);
	      }
	      catch(Exception e) {
	      }
	 
	      return result;
	   }	
	   */
	
}
