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
	
	public static String parse(String str) {
		
		
		
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
		String newStr = str.replaceAll("\\$\\$\\{DATE\\}",currentTime.toLocaleString());
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM");
		String formattedMonth = formatter.format(currentTime);
		newStr = newStr.replaceAll("\\$\\$\\{MONTH\\}",formattedMonth);
		
		formatter = new SimpleDateFormat("kk:mm:ss");
		String formattedTime = formatter.format(currentTime);
		newStr = newStr.replaceAll("\\$\\$\\{TIME\\}",formattedTime);
		
		
		
		return newStr;
		
	}
}
