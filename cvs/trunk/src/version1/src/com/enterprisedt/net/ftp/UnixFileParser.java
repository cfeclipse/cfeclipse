/**
 *
 *  edtFTPj
 * 
 *  Copyright (C) 2000-2004 Enterprise Distributed Technologies Ltd
 *
 *  www.enterprisedt.com
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  Bug fixes, suggestions and comments should be sent to bruce@enterprisedt.com
 *
 *  Change Log:
 *
 *    $Log: not supported by cvs2svn $
 *    Revision 1.10  2004/10/19 16:15:49  bruceb
 *    minor restructuring
 *
 *    Revision 1.9  2004/10/18 15:58:15  bruceb
 *    setLocale
 *
 *    Revision 1.8  2004/09/20 21:36:13  bruceb
 *    tweak to skip invalid lines
 *
 *    Revision 1.7  2004/09/17 14:56:54  bruceb
 *    parse fixes including wrong year
 *
 *    Revision 1.6  2004/07/23 08:32:36  bruceb
 *    made cvsId public
 *
 *    Revision 1.5  2004/06/11 10:19:59  bruceb
 *    fixed bug re filename same as user
 *
 *    Revision 1.4  2004/05/20 19:47:00  bruceb
 *    blanks in names fix
 *
 *    Revision 1.3  2004/05/05 20:27:41  bruceb
 *    US locale for date formats
 *
 *    Revision 1.2  2004/05/01 11:44:21  bruceb
 *    modified for server returning "total 3943" as first line
 *
 *    Revision 1.1  2004/04/17 23:42:07  bruceb
 *    file parsing part II
 *
 *    Revision 1.1  2004/04/17 18:37:23  bruceb
 *    new parse functionality
 *
 */
package com.enterprisedt.net.ftp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *  Represents a remote Unix file parser
 *
 *  @author      Bruce Blackshaw
 *  @version     $Revision: 1.1 $
 */
public class UnixFileParser extends FTPFileParser {

    /**
     *  Revision control id
     */
    public static String cvsId = "@(#)$Id: UnixFileParser.java,v 1.1 2004-11-09 04:44:29 smilligan Exp $";

    /**
     * Symbolic link symbol
     */
    private final static String SYMLINK_ARROW = "->";
    
    /**
     * Indicates symbolic link
     */
    private final static char SYMLINK_CHAR = 'l';
    
    /**
     * Indicates ordinary file
     */
    private final static char ORDINARY_FILE_CHAR = '-';
    
    /**
     * Indicates directory
     */
    private final static char DIRECTORY_CHAR = 'd';
    
    /**
     * Date formatter type 1
     */
    private SimpleDateFormat formatter1;
    
    /**
     * Date formatter type 2
     */
    private SimpleDateFormat formatter2;
    
    /**
     * Minimum number of expected fields
     */
    private final static int MIN_FIELD_COUNT = 8;
    
    /**
     * Constructor
     */
    public UnixFileParser() {
        setLocale(Locale.getDefault());
    }
    
    /**
     * Set the locale for date parsing of listings
     * 
     * @param locale    locale to set
     */
    public void setLocale(Locale locale) {
        formatter1 = new SimpleDateFormat("MMM-dd-yyyy", locale);
        formatter2 = new SimpleDateFormat("MMM-dd-yyyy-HH:mm", locale);
    }  
    
    /**
     * Is this a Unix format listing?
     * 
     * @param raw   raw listing line
     * @return true if Unix, false otherwise
     */
    public static boolean isUnix(String raw) {
        char ch = raw.charAt(0);
        if (ch == ORDINARY_FILE_CHAR || ch == DIRECTORY_CHAR || ch == SYMLINK_CHAR)
            return true;
        return false;
    }
    
    /**
     * Parse server supplied string, e.g.:
     * 
     * lrwxrwxrwx   1 wuftpd   wuftpd         14 Jul 22  2002 MIRRORS -> README-MIRRORS
     * -rw-r--r--   1 b173771  users         431 Mar 31 20:04 .htaccess
     * 
     * @param raw   raw string to parse
     */
    public FTPFile parse(String raw) throws ParseException {
        
        // test it is a valid line, e.g. "total 342522" is invalid
        if (!isUnix(raw))
            return null;
        
        String[] fields = split(raw);
         
        if (fields.length < MIN_FIELD_COUNT) {
            StringBuffer listing = new StringBuffer("Unexpected number of fields in listing '");
            listing.append(raw).append("' - expected minimum ").append(MIN_FIELD_COUNT). 
                    append(" fields but found ").append(fields.length).append(" fields");
            throw new ParseException(listing.toString(), 0);
        }
        
        // field pos
        int index = 0;
        
        // first field is perms
        char ch = raw.charAt(0);
        String permissions = fields[index++];
        ch = permissions.charAt(0);
        boolean isDir = false;
        boolean isLink = false;
        if (ch == DIRECTORY_CHAR)
            isDir = true;
        else if (ch == SYMLINK_CHAR)
            isLink = true;
        
        // some servers don't supply the link count
        int linkCount = 0;
        if (fields.length > MIN_FIELD_COUNT) {
            try {
                linkCount = Integer.parseInt(fields[index++]);
            }
            catch (NumberFormatException ignore) {}
        }
        
        // owner and group
        String owner = fields[index++];
        String group = fields[index++];
        
        // size
        long size = 0L;
        String sizeStr = fields[index++];
        try {
            size = Long.parseLong(sizeStr);
        }
        catch (NumberFormatException ex) {
            throw new ParseException("Failed to parse size: " + sizeStr, 0);
        }
        
        // next 3 are the date time
        int dateTimePos = index;
        Date lastModified = null;
        StringBuffer stamp = new StringBuffer(fields[index++]);
        stamp.append('-').append(fields[index++]).append('-');
        
        String field = fields[index++];
        if (field.indexOf(':') < 0) {
            stamp.append(field); // year
            lastModified = formatter1.parse(stamp.toString());
        }
        else { // add the year ourselves as not present
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            stamp.append(year).append('-').append(field);
            lastModified = formatter2.parse(stamp.toString());
            
            // can't be in the future - must be the previous year
            if (lastModified.after(cal.getTime())) {
                cal.setTime(lastModified);
                cal.add(Calendar.YEAR, -1);
                lastModified = cal.getTime();
            }
        }
            
        // name of file or dir. Extract symlink if possible
        String name = null;
        String linkedname = null;
        
        // we've got to find the starting point of the name. We
        // do this by finding the pos of all the date/time fields, then
        // the name - to ensure we don't get tricked up by a userid the
        // same as the filename,for example
        int pos = 0;
        boolean ok = true;
        for (int i = dateTimePos; i < dateTimePos+3; i++) {
            pos = raw.indexOf(fields[i], pos);
            if (pos < 0) {
                ok = false;
                break;
            }
            else { // move on the length of the field
                pos += fields[i].length();
            }
        }
        if (ok) {
            String remainder = raw.substring(pos).trim();
            if (!isLink) 
                name = remainder;
            else { // symlink, try to extract it
                pos = remainder.indexOf(SYMLINK_ARROW);
                if (pos <= 0) { // couldn't find symlink, give up & just assign as name
                    name = remainder;
                }
                else { 
                    int len = SYMLINK_ARROW.length();
                    name = remainder.substring(0, pos).trim();
                    if (pos+len < remainder.length())
                        linkedname = remainder.substring(pos+len);
                }
            }
        }
        else {
            throw new ParseException("Failed to retrieve name: " + raw, 0);  
        }
        
        FTPFile file = new FTPFile(FTPFile.UNIX, raw, name, size, isDir, lastModified);
        file.setGroup(group);
        file.setOwner(owner);
        file.setLink(isLink);
        file.setLinkCount(linkCount);
        file.setLinkedName(linkedname);
        file.setPermissions(permissions);
        return file;
    }
}
