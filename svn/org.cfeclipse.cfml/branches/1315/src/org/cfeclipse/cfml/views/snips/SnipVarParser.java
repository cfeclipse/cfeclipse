/*
 * Created on May 6, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
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
package org.cfeclipse.cfml.views.snips;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.widgets.Shell;

/**
 * Pre parses a snippet checking for global variables such as user and date, then opens a dialog with the snippet text that is left
 * @author Stephen Milligan
 */
public class SnipVarParser {

	public SnipVarParser() {
		super();
	}
	

	public static String parse(String str, IFile activeFile, Shell shell ) {
		
		String currentFile = "";
		String currentFolder = "";
		String currentPath = "";
		String currentProjectPath = "";
		
		
		/* not sure why this next block is needed, but it causes the snippet 
		 * insert to fail on QA. Hope I am not misunderstanding something...
		 * */
		 if (activeFile != null) {
		 	try {
			 currentFile = activeFile.getName();
			 currentPath = activeFile.getRawLocation().toFile().getAbsolutePath();
			 File fullPath = new File(currentPath);
			 currentFolder = fullPath.getParent();
			 currentProjectPath = activeFile.getParent().toString();
			 String[] path = currentProjectPath.split("/");
			 if (path.length > 0) {
			 	currentProjectPath = path[path.length-1];
			 }
			 // Get your laughing gear round this little lot :)
			 currentFile = currentFile.replaceAll("\\\\","\\\\\\\\");
			 currentPath = currentPath.replaceAll("\\\\","\\\\\\\\");
			 currentFolder = currentFolder.replaceAll("\\\\","\\\\\\\\");
			 currentProjectPath = currentProjectPath.replaceAll("\\\\","\\\\\\\\");
		 	}
		 	catch (Exception e) {
		 		e.printStackTrace(System.err);
		 	}
		}
		
		/*     
		 * $${DATE}
		 * $${MONTH}
		 * $${TIME}
		 * $${DATETIME}
		 * $${DAYOFWEEK}
		 * $${CURRENTFILE} - Current file name (just the file)
		 * $${CURRENTFOLDER} - Current folder (The path to the containing folder)
		 * $${CURRENTPATH} - Current path (full file name)
		 * $${CURRENTPRJPATH} - Just the folder
		 * $${USERNAME} - Current user
		 * $${MONTHNUMBER} - Month as a number
		 * $${DAYOFMONTH} - Day of month as a number
		 * $${DAYOFWEEKNUMBER} - Day of week (the week starts on Sunday)
		 * $${DATETIME24} - DateTime24 - a 24 hour clock version of datetime.
		 * $${YEAR} - Current year.
		 * $${YEAR2DIGIT} - Current two digit year. 
		 */
		
		Calendar calendar = new GregorianCalendar();
		Date currentTime = new Date();
		calendar.setTime(currentTime);
		String newStr = str;
		
		newStr = newStr.replaceAll("\\$\\$\\{FULLDATE\\}",currentTime.toString());

		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
		String formattedDate = formatter.format(currentTime);
		newStr = newStr.replaceAll("\\$\\$\\{DATE\\}",formattedDate);
		
		formatter = new SimpleDateFormat("MMMM");
		String formattedMonth = formatter.format(currentTime);
		newStr = newStr.replaceAll("\\$\\$\\{MONTH\\}",formattedMonth);
		
		formatter = new SimpleDateFormat("k:mm:ss a");
		String formattedTime = formatter.format(currentTime);
		newStr = newStr.replaceAll("\\$\\$\\{TIME\\}",formattedTime);
		
		formatter = new SimpleDateFormat("M/d/yyyy K:mm:ss a");
		String formattedDateTime = formatter.format(currentTime);
		newStr = newStr.replaceAll("\\$\\$\\{DATETIME\\}",formattedDateTime);
		
		formatter = new SimpleDateFormat("EEEE");
		String formattedDayOfWeek = formatter.format(currentTime);
		newStr = newStr.replaceAll("\\$\\$\\{DAYOFWEEK\\}",formattedDayOfWeek);
		
		newStr = newStr.replaceAll("\\$\\$\\{CURRENTFILE\\}",currentFile);
		
		newStr = newStr.replaceAll("\\$\\$\\{CURRENTFOLDER\\}",currentFolder);
		
		newStr = newStr.replaceAll("\\$\\$\\{CURRENTPATH\\}",currentPath);
		
		newStr = newStr.replaceAll("\\$\\$\\{CURRENTPRJPATH\\}",currentProjectPath);

		
		newStr = newStr.replaceAll("\\$\\$\\{USERNAME\\}",System.getProperty("user.name"));
		
		formatter = new SimpleDateFormat("MM");
		String formattedMonthNumber = formatter.format(currentTime);
		newStr = newStr.replaceAll("\\$\\$\\{MONTHNUMBER\\}",formattedMonthNumber);
		
		formatter = new SimpleDateFormat("dd");
		String formattedDayOfMonth = formatter.format(currentTime);
		newStr = newStr.replaceAll("\\$\\$\\{DAYOFMONTH\\}",formattedDayOfMonth);
		
		newStr = newStr.replaceAll("\\$\\$\\{DAYOFWEEKNUMBER\\}",Integer.toString(calendar.get(Calendar.DAY_OF_WEEK)));
		
		formatter = new SimpleDateFormat("M/d/yyyy kk:mm:ss");
		String formattedDateTime24 = formatter.format(currentTime);
		newStr = newStr.replaceAll("\\$\\$\\{DATETIME24\\}",formattedDateTime24);
		
		formatter = new SimpleDateFormat("yyyy");
		String formattedYear = formatter.format(currentTime);
		newStr = newStr.replaceAll("\\$\\$\\{YEAR\\}",formattedYear);
		
		formatter = new SimpleDateFormat("yy");
		String formattedYear2Digit = formatter.format(currentTime);
		newStr = newStr.replaceAll("\\$\\$\\{YEAR2DIGIT\\}",formattedYear2Digit);
		
		//send the snip string to the smart dialog
		SnipSmartDialog ssd = new SnipSmartDialog();
		//newStr = ssd.parse(newStr,activeFile, shell);
		newStr = SnipSmartDialog.parse(newStr, shell);
		
		return newStr;
	}
}
