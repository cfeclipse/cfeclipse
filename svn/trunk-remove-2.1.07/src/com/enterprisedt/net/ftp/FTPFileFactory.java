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
 *    $Log: FTPFileFactory.java,v $
 *    Revision 1.1  2004/11/09 04:44:29  smilligan
 *    Uber monster FTP stuff commit.
 *
 *    This is mostly working now, but there are a few known issues:
 *
 *    External files and remote FTP files don't get a LHS ruler. That means no line numbers, no folding etc.
 *    FTP files don't correctly report when they are read only, so they appear editable, appear to save, but the changes aren't stored on the server.
 *    You can currently only create new ftp connections. There isn't an interface for managing them.
 *    The FTP stuff probably needs to be done in it's own thread with a progress monitor. Othewise it can kill your workspace if it dies.
 *
 *    Revision 1.7  2004/10/19 16:15:16  bruceb
 *    swap to unix if seems like unix listing
 *
 *    Revision 1.6  2004/10/18 15:57:16  bruceb
 *    set locale
 *
 *    Revision 1.5  2004/08/31 10:45:50  bruceb
 *    removed unused import
 *
 *    Revision 1.4  2004/07/23 08:31:52  bruceb
 *    parser rotation
 *
 *    Revision 1.3  2004/05/01 11:44:21  bruceb
 *    modified for server returning "total 3943" as first line
 *
 *    Revision 1.2  2004/04/17 23:42:07  bruceb
 *    file parsing part II
 *
 *    Revision 1.1  2004/04/17 18:37:23  bruceb
 *    new parse functionality
 *
 */

package com.enterprisedt.net.ftp;

import java.text.ParseException;
import java.util.Locale;

import com.enterprisedt.util.debug.Logger;

/**
 *  Factory for creating FTPFile objects
 *
 *  @author      Bruce Blackshaw
 *  @version     $Revision: 1.1 $
 */
public class FTPFileFactory {
    
    /**
     *  Revision control id
     */
    public static String cvsId = "@(#)$Id: FTPFileFactory.java,v 1.1 2004/11/09 04:44:29 smilligan Exp $";
    
    /**
     * Logging object
     */
    private Logger log = Logger.getLogger(FTPFileFactory.class);

    /**
     * Windows server comparison string
     */
    final static String WINDOWS_STR = "WINDOWS";
                  
    /**
     * UNIX server comparison string
     */
    final static String UNIX_STR = "UNIX";
        
    /**
     * SYST string
     */
    private String system;
    
    /**
     * Cached windows parser
     */
    private FTPFileParser windows = new WindowsFileParser();
    
    /**
     * Cached unix parser
     */
    private FTPFileParser unix = new UnixFileParser();
    
    /**
     * Does the parsing work
     */
    private FTPFileParser parser = null;
    
    /**
     * Rotate parsers when a ParseException is thrown?
     */
    private boolean rotateParsers = true;
     
    /**
     * Constructor
     * 
     * @param system    SYST string
     */
    public FTPFileFactory(String system) throws FTPException {
        setParser(system);
    }
    
    /**
     * Constructor. User supplied parser. Note that parser
     * rotation (in case of a ParseException) is disabled if
     * a parser is explicitly supplied
     * 
     * @param parser   the parser to use
     */
    public FTPFileFactory(FTPFileParser parser) {
        this.parser = parser;
        rotateParsers = false;
    }   
    
    /**
     * Set the locale for date parsing of listings
     * 
     * @param locale    locale to set
     */
    public void setLocale(Locale locale) {
        windows.setLocale(locale);
        unix.setLocale(locale);
        parser.setLocale(locale); // might be user supplied
    }
    
    /**
     * Set the remote server type
     * 
     * @param system    SYST string
     */
    private void setParser(String system) throws FTPException {
        this.system = system;
        if (system.toUpperCase().startsWith(WINDOWS_STR))
            parser = windows;
        else if (system.toUpperCase().startsWith(UNIX_STR))
            parser = unix;
        else
            throw new FTPException("Unknown SYST: " + system);
    }
    
    
    /**
     * Parse an array of raw file information returned from the
     * FTP server
     * 
     * @param files     array of strings
     * @return array of FTPFile objects
     */
    public FTPFile[] parse(String[] files) throws ParseException {
               
        FTPFile[] temp = new FTPFile[files.length];
        
        // quick check if no files returned
        if (files.length == 0)
            return temp;
        
        // swap to Unix if looks like Unix listing
        if (parser == windows && UnixFileParser.isUnix(files[0])) {
            parser = unix;
            log.info("Swapping Windows parser to Unix");
        }
        
        int count = 0;
        for (int i = 0; i < files.length; i++) {
            try {
                if (files[i] == null || files[i].length() == 0)
                    continue;
                FTPFile file = parser.parse(files[i]);
                // we skip null returns - these are duff lines we know about and don't
                // really want to throw an exception
                if (file != null) {
                    temp[count++] = file;
                }
            }
            catch (ParseException ex) {
                if (rotateParsers) { // first error, let's try swapping parsers
                    rotateParsers = false; // only do once
                    rotateParsers();
                    FTPFile file = parser.parse(files[i]);
                    if (file != null)
                        temp[count++] = file;
                }
                else // rethrow
                    throw ex;
            }
        }
        FTPFile[] result = new FTPFile[count];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }
    
    /**
     * Swap from one parser to the other. We can just check
     * object references
     */
    private void rotateParsers() {
        if (parser == unix) {
            parser = windows;
            log.info("Rotated parser to Windows");
        }
        else if (parser == windows){
            parser = unix;
            log.info("Rotated parser to Unix");
        }
    }

    /**
     * Get the SYST string
     * 
     * @return the system string.
     */
    public String getSystem() {
        return system;
    }


}
