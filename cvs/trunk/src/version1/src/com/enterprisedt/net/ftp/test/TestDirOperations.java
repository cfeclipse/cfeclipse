/**
 *
 *  Java FTP client library.
 *
 *  Copyright (C) 2000  Enterprise Distributed Technologies Ltd
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
 *        Revision 1.5  2004/08/31 10:44:49  bruceb
 *        minor tweaks re compile warnings
 *
 *        Revision 1.4  2004/05/01 17:05:43  bruceb
 *        Logger stuff added
 *
 *        Revision 1.3  2003/05/31 14:54:05  bruceb
 *        cleaned up unused imports
 *
 *        Revision 1.2  2003/01/29 22:45:28  bruceb
 *        ??
 *
 *        Revision 1.1  2002/11/19 22:00:15  bruceb
 *        New JUnit test cases
 *
 *
 */

package com.enterprisedt.net.ftp.test;

import com.enterprisedt.net.ftp.FTPException;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *  Tests directory navigation and directory creation/deletion
 *  functionality
 *
 *  @author         Bruce Blackshaw
 *  @version        $Revision: 1.1 $
 */
public class TestDirOperations extends FTPTestCase {

    /**
     *  Revision control id
     */
    public static String cvsId = "@(#)$Id: TestDirOperations.java,v 1.1 2004-11-09 04:44:29 smilligan Exp $";

    /**
     *  Get name of log file
     *
     *  @return name of file to log to
     */
    protected String getLogName() {
        return "TestDirOperations.log";
    }

    /**
     *  Test we can make a directory, and change
     *  into it, and remove it
     */
    public void testDir() throws Exception {

        log.debug("testMkDir()");

        connect();
        login();

        // move to test directory
        ftp.chdir(testdir);
        
        // mkdir
        String dir = generateRandomFilename();
        ftp.mkdir(dir);

        // chdir into new dir
        ftp.chdir(dir);

        // pwd
        String wd = ftp.pwd();
        log.debug("PWD: " + wd);
        assertTrue(wd.indexOf(dir) >= 0);

        // remove the dir
        ftp.chdir("..");
        ftp.rmdir(dir);

        // check it doesn't exist
        try {
            ftp.chdir(dir);
            fail("chdir(" + dir + ") should have failed!");
        }
        catch (FTPException ex) {
            log.debug("Expected exception: " + ex.getMessage());
        }

        ftp.quit();        
    }


    /**
     *  Test renaming a dir
     */
    public void testRenameDir() throws Exception {

        log.debug("testRenameDir()");

        connect();
        login();

        // move to test directory
        ftp.chdir(testdir);  

        // mkdir
        String dir1 = generateRandomFilename();
        ftp.mkdir(dir1);

        // chdir into new dir and out again
        ftp.chdir(dir1);
        ftp.chdir("..");

        // generate another name guaranteed to be different
        // and rename this dir
        String dir2 = new StringBuffer(dir1).reverse().toString();
        ftp.rename(dir1, dir2);
        ftp.chdir(dir2);
        String wd = ftp.pwd();
        assertTrue(wd.indexOf(dir2) >= 0);
        
        // remove the dir
        ftp.chdir("..");
        ftp.rmdir(dir2);

        // check it doesn't exist
        try {
            ftp.chdir(dir2);
            fail("chdir(" + dir2 + ") should have failed!");
        }
        catch (FTPException ex) {
            log.debug("Expected exception: " + ex.getMessage());
        }

        ftp.quit(); 
    }


    /**
     *  Automatic test suite construction
     *
     *  @return  suite of tests for this class
     */
    public static Test suite() {
        return new TestSuite(TestDirOperations.class);
    } 

    /**
     *  Enable our class to be run, doing the
     *  tests
     */
    public static void main(String[] args) {       
        junit.textui.TestRunner.run(suite());
    }
}

