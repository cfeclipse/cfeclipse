/*
 * Created on May 6, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.snips;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import org.eclipse.core.resources.IFile;
import java.io.File;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SnippetVarParser {
	/**
	 * 
	 */

	
	public SnippetVarParser() {
		super();
	}
	
	public static String parse(String str, IFile activeFile) {
		
		String currentFile = "";
		String currentFolder = "";
		String currentPath = "";
		
		if (activeFile != null) {
		 currentFile = activeFile.getName();
		 currentPath = activeFile.getRawLocation().toFile().getAbsolutePath();
		 File fullPath = new File(currentPath);
		 currentFolder = fullPath.getParent();
		 
		 // Get your lauging gear round this little lot :)
		 currentFile = currentFile.replaceAll("\\\\","\\\\\\\\");
		 currentPath = currentPath.replaceAll("\\\\","\\\\\\\\");
		 currentFolder = currentFolder.replaceAll("\\\\","\\\\\\\\");
		}

		
		
		/*
		 * TODO: Need to write the implementation for each of these vars
		 * 
		 * 
		 * Snipet var strings from Homseite
		 * 
		 *     
		 * $${DATE}
		 * $${MONTH}
		 * $${TIME}
		 * $${DATETIME}
		 * $${DAYOFWEEK}
		 * $${CURRENTFILE} - Current file name (just the file)
		 * $${CURRENTFOLDER} - Current folder (just the folder)
		 * $${CURRENTPATH} - Current path (full file name)
		 * $${USERNAME} - Current user
		 * $${MONTHNUMBER} - Month as a number
		 * $${DAYOFMONTH} - Day of month as a number
		 * $${DAYOFWEEKNUMBER} - Day of week (the week starts on Sunday)
		 * $${DATETIME24} - DateTime24 - a 24 hour clock version of datetime.
		 * $${YEAR} - Current year.
		 * $${YEAR2DIGIT} - Current two digit year.
		 * 
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
		
		
		return newStr;
		
	}
}
