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
 *        Revision 1.3  2004/04/17 18:38:38  bruceb
 *        tweaks for ssl and new parsing functionality
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
import com.enterprisedt.net.ftp.FTPFile;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *  Tests the various commands that list directories
 *
 *  @author     Bruce Blackshaw
 *  @version    $Revision: 1.1 $
 */
public class TestListings extends FTPTestCase {

    /**
     *  Revision control id
     */
    public static String cvsId = "@(#)$Id: TestListings.java,v 1.1 2004-11-09 04:44:29 smilligan Exp $";

    /**
     *  Get name of log file
     *
     *  @return name of file to log to
     */
    protected String getLogName() {
        return "TestListings.log";
    }

    /**
     *  Test directory listings
     */ 
    public void testDir() throws Exception {

        connect();
        login();

        // move to test directory
        ftp.chdir(testdir);

        // list current dir
        String[] list = ftp.dir();
        print(list);

        // list current dir by name
        list = ftp.dir(".");
        print(list);

        // list empty dir by name
        list = ftp.dir(remoteEmptyDir);
        print(list);

        // non-existent file
		String randomName = generateRandomFilename();
        try {        
            list = ftp.dir(randomName);
            print(list);
		}
		catch (FTPException ex) {
			if (ex.getReplyCode() != 550)
				fail("dir(" + randomName + ") should throw 550 for non-existent dir");
		}            
        
        ftp.quit();
    }

    /**
     *  Test full directory listings
     */ 
    public void testDirFull() throws Exception {

        connect();
        login();

        // move to test directory
        ftp.chdir(testdir);

        // list current dir by name
        String[] list = ftp.dir(".", true);
        print(list);
        
        log.debug("******* dirDetails *******");
        FTPFile[] files = ftp.dirDetails(".");
        print(files);
        log.debug("******* end dirDetails *******");

        // list empty dir by name
        list = ftp.dir(remoteEmptyDir, true);
        print(list);

        // non-existent file. Some FTP servers don't send
        // a 450/450, but IIS does - so we catch it and
        // confirm it is a 550
        String randomName = generateRandomFilename();
        try {        
        	list = ftp.dir(randomName, true);
        	print(list);
        }
        catch (FTPException ex) {
        	if (ex.getReplyCode() != 550)
				fail("dir(" + randomName + ") should throw 550 for non-existent dir");
        }
        
        ftp.quit();
    }

    /**
     *  Helper method for dumping a listing
     * 
     *  @param list   directory listing to print
     */
    private void print(String[] list) {
        log.debug("Directory listing:");
        for (int i = 0; i < list.length; i++)
            log.debug(list[i]);
        log.debug("Listing complete");
    }
    
    /**
     *  Helper method for dumping a listing
     * 
     *  @param list   directory listing to print
     */
    private void print(FTPFile[] list) {
        log.debug("Directory listing:");
        for (int i = 0; i < list.length; i++)
            log.debug(list[i].toString());
        log.debug("Listing complete");
    }
    

    /**
     *  Automatic test suite construction
     *
     *  @return  suite of tests for this class
     */
    public static Test suite() {
        return new TestSuite(TestListings.class);
    } 

    /**
     *  Enable our class to be run, doing the
     *  tests
     */
    public static void main(String[] args) {       
        junit.textui.TestRunner.run(suite());
    }
}

