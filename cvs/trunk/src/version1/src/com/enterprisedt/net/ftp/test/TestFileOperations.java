/**
 *
 *  Java FTP client library.
 *
 *  Copyright (C) 2000-2003  Enterprise Distributed Technologies Ltd
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
 *        $Log: not supported by cvs2svn $
 *        Revision 1.6  2004/08/31 10:44:49  bruceb
 *        minor tweaks re compile warnings
 *
 *        Revision 1.5  2004/05/01 17:05:43  bruceb
 *        Logger stuff added
 *
 *        Revision 1.4  2004/04/17 18:38:38  bruceb
 *        tweaks for ssl and new parsing functionality
 *
 *        Revision 1.3  2003/11/02 21:51:32  bruceb
 *        test for size()
 *
 *        Revision 1.2  2003/05/31 14:54:05  bruceb
 *        cleaned up unused imports
 *
 *        Revision 1.1  2002/11/19 22:00:15  bruceb
 *        New JUnit test cases
 *
 *
 */

package com.enterprisedt.net.ftp.test;

import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *  Tests various file operations
 *
 *  @author         Bruce Blackshaw
 *  @version        $Revision: 1.1 $
 */
public class TestFileOperations extends FTPTestCase {

    /**
     *  Revision control id
     */
    public static String cvsId = "@(#)$Id: TestFileOperations.java,v 1.1 2004-11-09 04:44:29 smilligan Exp $";

    /**
     *  Formatter for modtime
     */
    private SimpleDateFormat modFormatter = 
        new SimpleDateFormat("yyyy/MM/dd @ HH:mm:ss");

    /**
     *  Get name of log file
     *
     *  @return name of file to log to
     */
    protected String getLogName() {
        return "TestFileOperations.log";
    }

    /**
     *  Test remote deletion
     */
    public void testDelete() throws Exception {
        // test existent & non-existent file
        connect();
        login();

        // move to test directory
        ftp.chdir(testdir);

       try {
            // try to delete a non-existent file
            String file = generateRandomFilename();
            log.debug("Deleting a non-existent file");
            ftp.delete(file);
        }
        catch (FTPException ex) {
            log.debug("Expected exception: " + ex.getMessage());
        }

       // how to delete a file and make it repeatable?

        
        ftp.quit();
    }

    /**
     *  Test renaming of a file
     */
    public void testRename() throws Exception {
        // test existent & non-existent file
        connect();
        login();

        // move to test directory
        ftp.chdir(testdir);

        // rename file
        String rename = remoteTextFile + ".renamed";
        ftp.rename(remoteTextFile, rename);
        
        // get its mod time
        Date modTime = ftp.modtime(rename);
        String mod = modFormatter.format(modTime);
        log.debug(rename + ": " + mod);

        // modtime on original file should fail
        try {
            ftp.modtime(remoteTextFile);
        }
        catch (FTPException ex) {
            log.debug("Expected exception: " + ex.getMessage());
        }
        
        // and rename file back again
        ftp.rename(rename, remoteTextFile);

        // and modtime should succeed 
        modTime = ftp.modtime(remoteTextFile);
        mod = modFormatter.format(modTime);
        log.debug(remoteTextFile + ": " + mod);

        // modtime on renamed (first time) file should 
        // now fail (as we've just renamed it to original)
        try {
            ftp.modtime(rename);
        }
        catch (FTPException ex) {
            log.debug("Expected exception: " + ex.getMessage());
        }    

        ftp.quit();
    }


    /**
     *  Test finding the modification time 
     *  of a file
     */
    public void testModtime() throws Exception {
        
        connect();
        login();

        // move to test directory
        ftp.chdir(testdir);

        // get modtime
        log.debug("Modtime on existing file: " + remoteTextFile);
        Date modTime = ftp.modtime(remoteTextFile);
        String mod = modFormatter.format(modTime);
        log.debug(remoteTextFile + ": " + mod);

        try {
            // get modtime on non-existent file
            String file = generateRandomFilename();
            log.debug("Modtime on non-existent file");
            modTime = ftp.modtime(file);
            mod = modFormatter.format(modTime);
            log.debug(remoteTextFile + ": " + mod);     
        }
        catch (FTPException ex) {
            log.debug("Expected exception: " + ex.getMessage());
        }

        ftp.quit();    
    }
    
    /**
     *  Test the size() method
     */
    public void testSize() throws Exception {
        
        connect();
        login();

        // move to test directory
        ftp.chdir(testdir);
        ftp.setType(FTPTransferType.BINARY);

        // put to a random filename
        String filename = generateRandomFilename();
        ftp.put(localTextFile, filename);

        // find size of local file
        File local = new File(localTextFile);
        long sizeLocal = local.length();
        
        // find size of remote
        long sizeRemote = ftp.size(filename);

        // delete remote file
        ftp.delete(filename);
        
        if (sizeLocal != sizeRemote) {
            String msg = "Local size(" + sizeLocal + ") != remote size(" + sizeRemote + ")";
            log.debug(msg);
            throw new Exception(msg);
        }
            

        ftp.quit();
    }    
    

    /**
     *  Automatic test suite construction
     *
     *  @return  suite of tests for this class
     */
    public static Test suite() {
        return new TestSuite(TestFileOperations.class);
    } 

    /**
     *  Enable our class to be run, doing the
     *  tests
     */
    public static void main(String[] args) {       
        junit.textui.TestRunner.run(suite());
    }
}

