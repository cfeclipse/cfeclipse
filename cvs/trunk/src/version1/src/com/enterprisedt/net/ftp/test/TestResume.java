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
 *
 *  Change Log:
 *
 *        $Log: not supported by cvs2svn $
 *        Revision 1.2  2004/08/31 13:49:01  bruceb
 *        test cancelResume()
 *
 *        Revision 1.1  2004/08/31 10:44:17  bruceb
 *        test code
 * 
 */

package com.enterprisedt.net.ftp.test;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPProgressMonitor;
import com.enterprisedt.net.ftp.FTPTransferType;

/**
 *  Test get'ing and put'ing of remote files in various ways
 *
 *  @author     Bruce Blackshaw
 *  @version    $Revision: 1.1 $
 */
public class TestResume extends FTPTestCase {

    /**
     *  Revision control id
     */
    public static String cvsId = "@(#)$Id: TestResume.java,v 1.1 2004-11-09 04:44:29 smilligan Exp $";

    /**
     *  Get name of log file
     *
     *  @return name of file to log to
     */
    protected String getLogName() {
        return "TestResume.log";
    }

    /**
     *  Test resuming when putting a binary file
     */
    public void testResumePut() throws Exception {

        log.debug("testResumePut()");

        connect();
        login();
        
        // move to test directory
        ftp.chdir(testdir);
        ftp.setType(FTPTransferType.BINARY);
        
        // put to a random filename
        String filename = generateRandomFilename();
        
        // use monitor to abort
        ftp.setProgressMonitor(new CancelProgressMonitor(ftp), 4096); 
                
        // initiate the put
        ftp.put(localBinaryFile, filename);
        
        // should be cancelled - now resume
        ftp.resume();
        
        // put again - should append
        ftp.put(localBinaryFile, filename);
                
        // get it back        
        ftp.get(filename, filename);

        // check equality of local files
        assertIdentical(localBinaryFile, filename);
        
        // finally, just check cancelResume works
        ftp.cancelResume();
        
        // get it back        
        ftp.get(filename, filename);        
        
        // delete remote file
        ftp.delete(filename);

        // and delete local file
        File local = new File(filename);
        local.delete();

        ftp.quit();
    }
    
    /**
     *  Test resuming when putting a binary file
     */
    public void testResumeGet() throws Exception {

        log.debug("testResumeGet()");

        connect();
        login();
        
        // move to test directory
        ftp.chdir(testdir);
        ftp.setType(FTPTransferType.BINARY);
        
        // put to a random filename
        String filename = generateRandomFilename();
        
        // use monitor to abort
        ftp.setProgressMonitor(new CancelProgressMonitor(ftp), 4096); 
                
        // initiate the put
        ftp.get(filename, remoteBinaryFile);
        
        // should be cancelled - now resume
        ftp.resume();
        
        // get again - should append
        ftp.get(filename, remoteBinaryFile);
        
        String filename2 = generateRandomFilename();;
                
        // do another get - complete this time    
        ftp.get(filename2, remoteBinaryFile);

        // check equality of local files
        assertIdentical(filename, filename2);
        
        // and delete local file
        File local = new File(filename);
        local.delete();
        local = new File(filename2);
        local.delete();
        
        ftp.quit();
    }
      
    /**
     *  Automatic test suite construction
     *
     *  @return  suite of tests for this class
     */
    public static Test suite() {
        return new TestSuite(TestResume.class);
    } 

    /**
     *  Enable our class to be run, doing the
     *  tests
     */
    public static void main(String[] args) {       
        junit.textui.TestRunner.run(suite());
    }
    
    /**
     *  As soon it receives notification of bytes transferred, it
     *  cancels the transfer    
     */
    class CancelProgressMonitor implements FTPProgressMonitor {
        
        /**
         * True if cancelled 
         */
        private boolean cancelled = false;
        
        /**
         * FTPClient reference
         */
        private FTPClient ftpClient;
        
        /**
         * Constructor
         * 
         * @param ftp   FTP client reference
         */
        public CancelProgressMonitor(FTPClient ftp) {
            this.ftpClient = ftp;
        }
        
        /* (non-Javadoc)
         * @see com.enterprisedt.net.ftp.FTPProgressMonitor#bytesTransferred(long)
         */
        public void bytesTransferred(long bytes) {
            if (!cancelled) {
                ftpClient.cancelTransfer();
                cancelled = true;
            }
        }            
    }
}

