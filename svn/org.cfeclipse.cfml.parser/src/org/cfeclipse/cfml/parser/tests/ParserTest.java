package org.cfeclipse.cfml.parser.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringBufferInputStream;

import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.CFParser;

public class ParserTest {

	/**
	 * We pass in a directory with files to parse
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Try and load some files to this
		
		if(args.length < 1){
			System.out.println("You need to pass a directory with cfm or cfc files to parse");
			System.exit(1);
		}
		for (int i = 0; i < args.length; i++) {
			System.out.println("about to parse " + args[i]);
		
			//We go and get the files that we are going to parse
			File parseDirectory = new File(args[i]);
			if(parseDirectory.isDirectory()){
				File[] files = parseDirectory.listFiles();
				for (int j = 0; j < files.length; j++) {
					if(files[j].isFile() && !files[j].isHidden()){
						String fileContents = getContents(files[j]);
					 System.out.println(fileContents);
						//Parse the contents
					 CFParser parser = new CFParser();
					 CFDocument document = parser.parseDoc(fileContents);
					}
						
				}
				
				
			}
		
		
		
		}
		
		

	}
	
	static public String getContents(File aFile) {
	    //...checks on aFile are elided
	    StringBuilder contents = new StringBuilder();
	    
	    try {
	      //use buffering, reading one line at a time
	      //FileReader always assumes default encoding is OK!
	      BufferedReader input =  new BufferedReader(new FileReader(aFile));
	      try {
	        String line = null; //not declared within while loop
	        /*
	        * readLine is a bit quirky :
	        * it returns the content of a line MINUS the newline.
	        * it returns null only for the END of the stream.
	        * it returns an empty String if two newlines appear in a row.
	        */
	        while (( line = input.readLine()) != null){
	          contents.append(line);
	          contents.append(System.getProperty("line.separator"));
	        }
	      }
	      finally {
	        input.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    
	    return contents.toString();
	  }

}
