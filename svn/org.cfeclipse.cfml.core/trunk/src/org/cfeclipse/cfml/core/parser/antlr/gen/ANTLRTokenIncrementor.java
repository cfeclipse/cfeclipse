package org.cfeclipse.cfml.core.parser.antlr.gen;

/*
Copyright (c) 2007 Mark Mandel, Mark Drew

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.	
*/

import java.io.*;


/**
 * Tool for incrementing antlr Tokens, so that island parsers
 * don't overlap
 * 
 * @author mark
 *
 */
public class ANTLRTokenIncrementor
{
	private int increment;
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		File[] files = new File[args.length - 1];
		
		for(int counter = 1; counter < args.length; counter++)
		{
			File file = new File(args[counter]);
			files[counter - 1] = file;
			System.out.println(file.toString());
		}
		
		new ANTLRTokenIncrementor(Integer.parseInt(args[0]), files);
	}

	public ANTLRTokenIncrementor(int increment, File[] files)
	{
		setIncrement(increment);
		for(File f : files)
		{
			replaceTokens(f);
		}
	}
	
	private void replaceTokens(File file)
	{
		File renameFile = new File(file.getAbsolutePath() + ".temp");
		System.out.println("rename: " + renameFile.toString());

		//copyFile(file, renameFile);
		if(!file.renameTo(renameFile))
		{
			System.out.println("File " + file.toString() + " failed to rename");
			System.exit(0);
		}
		
		String line;
		
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(renameFile));
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			
			while((line = input.readLine()) != null)
			{
				if(line.trim().toLowerCase().startsWith("public static final int"))
				{
					System.out.println("line found:" + line);
					
					String[] split = line.split("=");
					
					if(!split[0].endsWith("EOF"))
					{
						int ident = Integer.parseInt(split[1].replace(";", ""));
						
						ident = ident + getIncrement();
						
						line = split[0] + "=" + ident + ";"; 
					}
					
				}
				
				output.write(line);
				output.newLine();
			}
			
			input.close();
			output.flush();
			output.close();
		} 
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();			
		}
		
		renameFile.delete();
	}
	
	private void copyFile(File fromFile, File toFile)
	{
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(fromFile));
			BufferedWriter output = new BufferedWriter(new FileWriter(toFile));
			
			String line;
			
			while((line = input.readLine()) != null)
			{
				output.write(line);
				output.newLine();
			}
			
			input.close();
			output.flush();
			output.close();
		}
		catch (FileNotFoundException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}		
	}
	
	private int getIncrement()
	{
		return increment;
	}

	private void setIncrement(int increment)
	{
		this.increment = increment;
	}
}
