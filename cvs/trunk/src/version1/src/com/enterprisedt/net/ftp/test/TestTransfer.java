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
 *        Revision 1.3  2003/11/03 21:18:51  bruceb
 *        added test of progress callback
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

import com.enterprisedt.net.ftp.FTPProgressMonitor;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.enterprisedt.net.ftp.FTPException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *  Test get'ing and put'ing of remote files in various ways
 *
 *  @author     Bruce Blackshaw
 *  @version    $Revision: 1.1 $
 */
public class TestTransfer extends FTPTestCase {

    /**
     *  Revision control id
     */
    public static String cvsId = "@(#)$Id: TestTransfer.java,v 1.1 2004-11-09 04:44:29 smilligan Exp $";

    /**
     *  Get name of log file
     *
     *  @return name of file to log to
     */
    protected String getLogName() {
        return "TestTransfer.log";
    }

    /**
     *  Test transfering a binary file
     */
    public void testTransferBinary() throws Exception {

        log.debug("testTransferBinary()");

        connect();
        login();
        
        // monitor transfer progress
        ftp.setProgressMonitor(new TestProgressMonitor(), 2048);

        // move to test directory
        ftp.chdir(testdir);
        ftp.setType(FTPTransferType.BINARY);
        
        // put to a random filename
        String filename = generateRandomFilename();
        ftp.put(localBinaryFile, filename);

        // get it back        
        ftp.get(filename, filename);

        // delete remote file
        ftp.delete(filename);
        try {
            ftp.modtime(filename);
            fail(filename + " should not be found");
        }
        catch (FTPException ex) {
            log.debug("Expected exception: " + ex.getMessage());
        }

        // check equality of local files
        assertIdentical(localBinaryFile, filename);

        // and delete local file
        File local = new File(filename);
        local.delete();

        ftp.quit();
    }

    /**
     *  Test transfering a text file
     */
    public void testTransferText() throws Exception {

        log.debug("testTransferText()");

        connect();
        login();
        
        // monitor transfer progress
        ftp.setProgressMonitor(new TestProgressMonitor(), 2048);        

        // move to test directory
        ftp.chdir(testdir);
        ftp.setType(FTPTransferType.ASCII);

        // put to a random filename
        String filename = generateRandomFilename();
        ftp.put(localTextFile, filename);

        // get it back        
        ftp.get(filename, filename);

        // delete remote file
        ftp.delete(filename);
        try {
            ftp.modtime(filename);
            fail(filename + " should not be found");
        }
        catch (FTPException ex) {
            log.debug("Expected exception: " + ex.getMessage());
        }

        // check equality of local files
        assertIdentical(localTextFile, filename);

        // and delete local file
        File local = new File(filename);
        local.delete();

        ftp.quit();
    }

    /**
     *  Test getting a byte array
     */
    public void testGetBytes() throws Exception {

        log.debug("testGetBytes()");

        connect();
        login();
        
        // monitor transfer progress
        ftp.setProgressMonitor(new TestProgressMonitor(), 2048);        

        // move to test directory
        ftp.chdir(testdir);
        ftp.setType(FTPTransferType.BINARY);

        // get the file and work out its size
        String filename1 = generateRandomFilename();
        ftp.get(filename1, remoteBinaryFile);
        File file1 = new File(filename1);
        long len = file1.length();

        // now get to a buffer and check the length
        byte[] result = ftp.get(remoteBinaryFile);
        assertTrue(result.length == len);
        
        // put the buffer         
        String filename2 = generateRandomFilename();
        ftp.put(result, filename2);
        
        // get it back as a file
        ftp.get(filename2, filename2);
        
        // remove it remotely
        ftp.delete(filename2);
        
        // and now check files are identical
        File file2 = new File(filename2);
        assertIdentical(file1, file2);

        // and finally delete them
        file1.delete();
        file2.delete();
        
        ftp.quit();
    }

    /**
     *  Test the stream functionality
     */
    public void testTransferStream() throws Exception {

        log.debug("testTransferStream()");

        connect();
        login();
        
        // monitor transfer progress
        ftp.setProgressMonitor(new TestProgressMonitor(), 2048);        

        // move to test directory
        ftp.chdir(testdir);
        ftp.setType(FTPTransferType.BINARY);

        // get file as output stream        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ftp.get(out, remoteBinaryFile);

        // convert to byte array
        byte[] result1 = out.toByteArray();

        // put this 
        String filename = generateRandomFilename();
        ftp.put(new ByteArrayInputStream(result1), filename);

        // get it back
        byte[] result2 = ftp.get(filename);

        // delete remote file
        ftp.delete(filename);

        // and compare the buffers
        assertIdentical(result1, result2);

        ftp.quit();
    }

    /**
     *  Test the append functionality in put()
     */
    public void testPutAppend() throws Exception {

        log.debug("testPutAppend()");

        connect();
        login();
        
        // monitor transfer progress
        ftp.setProgressMonitor(new TestProgressMonitor(), 2048);        

        // move to test directory
        ftp.chdir(testdir);
        ftp.setType(FTPTransferType.BINARY);
        
        // put to a random filename
        String filename = generateRandomFilename();
        ftp.put(localBinaryFile, filename);

        // second time, append
        ftp.put(localBinaryFile, filename, true);

        // get it back & delete remotely
        ftp.get(filename, filename);
        ftp.delete(filename);

        // check it is twice the size
        File file1 = new File(localBinaryFile);
        File file2 = new File(filename);
        assertTrue(file1.length()*2 == file2.length());

        // and finally delete it
        file2.delete();

        ftp.quit();
    }

    /**
     *  Test transferring empty files
     */
    public void testTransferEmpty() throws Exception {

        log.debug("testTransferEmpty()");

        connect();
        login();

        // move to test directory
        ftp.chdir(testdir);      

        // get an empty file
        ftp.get(remoteEmptyFile, remoteEmptyFile);
        File empty = new File(remoteEmptyFile);
        assertTrue(empty.exists());
        assertTrue(empty.length() == 0);

        // delete it
        empty.delete();

        // put an empty file
        ftp.put(localEmptyFile, localEmptyFile);        
        
        // get it back as a different filename
        String filename = generateRandomFilename();
        ftp.get(filename, localEmptyFile);
        empty = new File(filename);
        assertTrue(empty.exists());
        assertTrue(empty.length() == 0);

        // delete file we got back (copy of our local empty file)
        empty.delete();

        // and delete the remote empty file we
        // put there
        ftp.delete(localEmptyFile);

        ftp.quit();  
    }

    /**
     *  Test transferring non-existent files
     */
    public void testTransferNonExistent() throws Exception {

        log.debug("testTransferNonExistent()");

        connect();
        login();

        // move to test directory
        ftp.chdir(testdir);  

        // generate a name & try to get it
        String filename = generateRandomFilename();
        try {
            ftp.get(filename, filename);
            fail(filename + " should not be found");
        }
        catch (FTPException ex) {
            log.debug("Expected exception: " + ex.getMessage());

            // ensure we don't have a local file of that name produced
            File file = new File(filename);
            assertFalse(file.exists());
        }

        // generate name & try to put
        filename = generateRandomFilename();
        try {
            ftp.put(filename, filename);
            fail(filename + " should not be found");
        }
        catch (FileNotFoundException ex) {
            log.debug("Expected exception: " + ex.getMessage());
        }        

        ftp.quit();      
    }
    
    
    /**
     *  Test of progress monitor functionality     
     */
    class TestProgressMonitor implements FTPProgressMonitor {

		/* (non-Javadoc)
		 * @see com.enterprisedt.net.ftp.FTPProgressMonitor#bytesTransferred(long)
		 */
		public void bytesTransferred(long count) {
			log.debug(count + " bytes transferred");			
		}            
    }
    
    /**
     *  Automatic test suite construction
     *
     *  @return  suite of tests for this class
     */
    public static Test suite() {
        return new TestSuite(TestTransfer.class);
    } 

    /**
     *  Enable our class to be run, doing the
     *  tests
     */
    public static void main(String[] args) {       
        junit.textui.TestRunner.run(suite());
    }
}

