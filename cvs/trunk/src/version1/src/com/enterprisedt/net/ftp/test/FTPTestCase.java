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
 *        Revision 1.11  2004/10/18 15:58:58  bruceb
 *        test encoding constructor
 *
 *        Revision 1.10  2004/08/31 10:44:49  bruceb
 *        minor tweaks re compile warnings
 *
 *        Revision 1.9  2004/07/23 08:33:44  bruceb
 *        enable testing for strict replies or not
 *
 *        Revision 1.8  2004/06/25 12:03:54  bruceb
 *        get mode from sys property
 *
 *        Revision 1.7  2004/05/11 21:58:05  bruceb
 *        getVersion() added
 *
 *        Revision 1.6  2004/05/01 17:05:59  bruceb
 *        cleaned up and deprecated
 *
 *        Revision 1.5  2004/04/17 18:38:38  bruceb
 *        tweaks for ssl and new parsing functionality
 *
 *        Revision 1.4  2004/04/05 20:58:41  bruceb
 *        latest hans tweaks to tests
 *
 *        Revision 1.3  2003/11/02 21:51:44  bruceb
 *        fixed bug re transfer mode not being set
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

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPControlSocket;
import com.enterprisedt.util.debug.FileAppender;
import com.enterprisedt.util.debug.Level;
import com.enterprisedt.util.debug.Logger;

import junit.framework.TestCase;

import java.util.Properties;
import java.util.Date;

import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.File;

/**
 *  Generic JUnit test class for FTP, that provides some
 *  useful methods for subclasses that implement the actual
 *  test cases
 *
 *  @author         Bruce Blackshaw
 *  @version        $Revision: 1.1 $
 */
abstract public class FTPTestCase extends TestCase {

    /**
     *  Revision control id
     */
    public static String cvsId = "@(#)$Id: FTPTestCase.java,v 1.1 2004-11-09 04:44:29 smilligan Exp $";

    /**
     *  Log stream
     */
    protected Logger log = Logger.getLogger(FTPTestCase.class);

    /**
     *  Reference to the FTP client
     */
    protected FTPClient ftp;

    /**
     *  Remote test host
     */
    protected String host;

    /**
     *  Test user
     */
    protected String user;

    /**
     *  User password
     */
    protected String password;

    /**
     *  Connect mode for test
     */
    protected FTPConnectMode connectMode;

    /**
     *  Socket timeout
     */
    protected int timeout;

    /**
     *  Remote directory that remote test files/dirs are in
     */
    protected String testdir;

    /**
     *  Remote text file
     */
    protected String remoteTextFile;

    /**
     *  Local text file
     */
    protected String localTextFile;

    /**
     *  Remote binary file
     */
    protected String remoteBinaryFile;

    /**
     *  Local binary file
     */
    protected String localBinaryFile;

    /**
     *  Local empty file
     */
    protected String localEmptyFile;

    /**
     *  Remote empty file
     */
    protected String remoteEmptyFile;

    /**
     *  Remote empty dir
     */
    protected String remoteEmptyDir;
    
    /**
     *  Strict reply checking?
     */
    protected boolean strictReplies = true;
    
    /**
     *  Loaded properties
     */
    protected Properties props = new Properties();
    
    /**
     *  Initialize test properties
     */
    public FTPTestCase() {     
        
        Logger.setLevel(Level.ALL);
                
        try {
            props.load(new FileInputStream("test.properties"));  
        }
        catch (IOException ex) {
            System.out.println("Failed to open test.properties");
            System.exit(-1);
        }
        
        // initialise our test properties
        host = props.getProperty("ftptest.host");
        user = props.getProperty("ftptest.user");
        password = props.getProperty("ftptest.password");
        
        // active or passive?
        String connectMode = System.getProperty("ftptest.connectmode");
        if (connectMode != null && connectMode.equalsIgnoreCase("active"))
            this.connectMode = FTPConnectMode.ACTIVE;
        else
            this.connectMode = FTPConnectMode.PASV;
        
        // socket timeout
        String timeout = props.getProperty("ftptest.timeout");
        this.timeout = Integer.parseInt(timeout);
        
        String strict = props.getProperty("ftptest.strictreplies");
        if (strict != null && strict.equalsIgnoreCase("false"))
            this.strictReplies = false;
        else
            this.strictReplies = true;

        // various test files and dirs
        testdir = props.getProperty("ftptest.testdir");
        localTextFile = props.getProperty("ftptest.file.local.text");
        remoteTextFile = props.getProperty("ftptest.file.remote.text");
        localBinaryFile = props.getProperty("ftptest.file.local.binary");
        remoteBinaryFile = props.getProperty("ftptest.file.remote.binary");
        localEmptyFile = props.getProperty("ftptest.file.local.empty");
        remoteEmptyFile = props.getProperty("ftptest.file.remote.empty");
        remoteEmptyDir = props.getProperty("ftptest.dir.remote.empty");
    }

    /**
     *  Setup is called before running each test
     */    
    protected void setUp() throws Exception {
        Logger.addAppender(new FileAppender(getLogName()));
        int[] ver = FTPClient.getVersion();
        log.info("FTP version: " + ver[0] + "." + ver[1] + "." + ver[2]);
        log.info("FTP build timestamp: " + FTPClient.getBuildTimestamp());
    }
    
    /**
     *  Deallocate resources at close of each test
     */
    protected void tearDown() throws Exception {
        Logger.shutdown();
    }

    /**
     *  Connect to the server and setup log stream
     */
    protected void connect() throws Exception {
    	connect(0);
    }
    
    /**
     *  Connect to the server and setup log stream
     */
    protected void connect(int timeout) throws Exception {
    	// connect
    	ftp = new FTPClient(host, FTPControlSocket.CONTROL_PORT, timeout, "US-ASCII");
    	ftp.setConnectMode(connectMode);
        if (!strictReplies) {
            log.warn("Strict replies not enabled");
            ftp.setStrictReturnCodes(false);
        }
    }
    
    /**
     *  Login to the server
     */
    protected void login() throws Exception {
        ftp.login(user, password);
    }

    /**
     *  Generate a random file name for testing
     *
     *  @return  random filename
     */
    protected String generateRandomFilename() {
        Date now = new Date();
        Long ms = new Long(now.getTime());
        return ms.toString();
    }

    /**
     *  Test to see if two buffers are identical, byte for byte
     *
     *  @param buf1   first buffer
     *  @param buf2   second buffer
     */
    protected void assertIdentical(byte[] buf1, byte[] buf2) 
        throws Exception {
        
        assertEquals(buf1.length, buf2.length);
        for (int i = 0; i < buf1.length; i++)
            assertEquals(buf1[i], buf2[i]);
    }

    /**
     *  Test to see if two files are identical, byte for byte
     *
     *  @param file1  name of first file
     *  @param file2  name of second file
     */
    protected void assertIdentical(String file1, String file2) 
        throws Exception {
        File f1 = new File(file1);
        File f2 = new File(file2);
        assertIdentical(f1, f2);
    }

    /**
     *  Test to see if two files are identical, byte for byte
     *
     *  @param file1  first file object
     *  @param file2  second file object
     */
    protected void assertIdentical(File file1, File file2) 
        throws Exception {

        BufferedInputStream is1 = null;
        BufferedInputStream is2 = null;
        try {
            // check lengths first
            assertEquals(file1.length(), file2.length());
            log.debug("Identical size [" + file1.getName() + 
                        "," + file2.getName() + "]"); 

            // now check each byte
            is1 = new BufferedInputStream(new FileInputStream(file1));
            is2 = new BufferedInputStream(new FileInputStream(file2));
            int ch1 = 0;
            int ch2 = 0;
            while ((ch1 = is1.read()) != -1 && 
                   (ch2 = is2.read()) != -1) {
                assertEquals(ch1, ch2);
            }            
            log.debug("Contents equal");
        }
        catch (IOException ex) {
            fail("Caught exception: " + ex.getMessage());
        }
        finally {
            if (is1 != null)
                is1.close();
            if (is2 != null)
                is2.close();
        }
    }
        
    /**
     *  Get name of log file
     *
     *  @return name of file to log to
     */
    abstract protected String getLogName();
}

