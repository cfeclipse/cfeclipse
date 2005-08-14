/**
 *
 *  edtFTPj
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
 *        Revision 1.30  2004/10/18 15:54:48  bruceb
 *        clearSOCKS added, set encoding for control sock, locale for parser
 *
 *        Revision 1.29  2004/09/21 21:28:28  bruceb
 *        fixed javadoc comment
 *
 *        Revision 1.28  2004/09/18 14:27:57  bruceb
 *        features() throw exception if not supported
 *
 *        Revision 1.27  2004/09/18 09:33:47  bruceb
 *        1.1.8 tweaks
 *
 *        Revision 1.26  2004/09/17 14:12:38  bruceb
 *        fixed javadoc re filemasks
 *
 *        Revision 1.25  2004/09/14 06:24:03  bruceb
 *        fixed javadoc comment
 *
 *        Revision 1.24  2004/08/31 13:48:29  bruceb
 *        resume,features,restructure
 *
 *        Revision 1.23  2004/07/23 08:34:32  bruceb
 *        strict replies or not, better tfr monitor reporting
 *
 *        Revision 1.22  2004/06/25 11:47:46  bruceb
 *        made 1.1.x compatible
 *
 *        Revision 1.21  2004/06/11 10:20:35  bruceb
 *        permit 200 to be returned from various cmds
 *
 *        Revision 1.20  2004/05/22 16:52:57  bruceb
 *        message listener
 *
 *        Revision 1.19  2004/05/15 22:37:22  bruceb
 *        put debugResponses back in
 *
 *        Revision 1.18  2004/05/13 23:00:34  hans
 *        changed comment
 *
 *        Revision 1.17  2004/05/08 21:14:41  bruceb
 *        checkConnection stuff
 *
 *        Revision 1.14  2004/04/19 21:54:06  bruceb
 *        final tweaks to dirDetails() re caching
 *
 *        Revision 1.13  2004/04/18 11:16:44  bruceb
 *        made validateTransfer() public
 *
 *        Revision 1.12  2004/04/17 18:37:38  bruceb
 *        new parse functionality
 *
 *        Revision 1.11  2004/03/23 20:26:49  bruceb
 *        tweak to size(), catch exceptions on puts()
 *
 *        Revision 1.10  2003/11/15 11:23:55  bruceb
 *        changes required for ssl subclasses
 *
 *        Revision 1.6  2003/05/31 14:53:44  bruceb
 *        1.2.2 changes
 *
 *        Revision 1.5  2003/01/29 22:46:08  bruceb
 *        minor changes
 *
 *        Revision 1.4  2002/11/19 22:01:25  bruceb
 *        changes for 1.2
 *
 *        Revision 1.3  2001/10/09 20:53:46  bruceb
 *        Active mode changes
 *
 *        Revision 1.1  2001/10/05 14:42:03  bruceb
 *        moved from old project
 *
 */

package com.enterprisedt.net.ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.Vector;

import com.enterprisedt.util.debug.Level;
import com.enterprisedt.util.debug.Logger;

/**
 *  Supports client-side FTP. Most common
 *  FTP operations are present in this class.
 *
 *  @author      Bruce Blackshaw
 *  @version     $Revision: 1.1 $
 */
public class FTPClient {

    /**
     *  Revision control id
     */
    public static String cvsId = "@(#)$Id: FTPClient.java,v 1.1 2004-11-09 04:44:29 smilligan Exp $";
    
    /**
     * Default byte interval for transfer monitor
     */
    final private static int DEFAULT_MONITOR_INTERVAL = 4096;
    
    /**
     * Default transfer buffer size
     */
    final private static int DEFAULT_BUFFER_SIZE = 4096;
    
    /**
     * Default encoding used for control data
     */
    final private static String DEFAULT_ENCODING = "US-ASCII";
    
    /**
     * SOCKS port property name
     */
    final private static String SOCKS_PORT = "socksProxyPort";

    /**
     * SOCKS host property name
     */
    final private static String SOCKS_HOST = "socksProxyHost";
    
    /**
     * Major version (substituted by ant)
     */
    private static String majorVersion = "@major_ver@";
    
    /**
     * Middle version (substituted by ant)
     */
    private static String middleVersion = "@middle_ver@";
    
    /**
     * Middle version (substituted by ant)
     */
    private static String minorVersion = "@minor_ver@";
    
    /**
     * Full version
     */
    private static int[] version;
    
    /**
     * Timestamp of build
     */
    private static String buildTimestamp = "@date_time@";
    
    
    /**
     * Work out the version array
     */
    static {
        try {
            version = new int[3];
            version[0] = Integer.parseInt(majorVersion);
            version[1] = Integer.parseInt(middleVersion);
            version[2] = Integer.parseInt(minorVersion);
        }
        catch (NumberFormatException ex) {
            System.err.println("Failed to calculate version: " + ex.getMessage());
        }
    }
    
    /**
     *  Format to interpret MTDM timestamp
     */
    private SimpleDateFormat tsFormat =
        new SimpleDateFormat("yyyyMMddHHmmss");
    
    /**
     * Logging object
     */
    private Logger log = Logger.getLogger(FTPClient.class);

    /**
     *  Socket responsible for controlling
     *  the connection
     */
	protected FTPControlSocket control = null;

    /**
     *  Socket responsible for transferring
     *  the data
     */
    protected FTPDataSocket data = null;

    /**
     *  Socket timeout for both data and control. In
     *  milliseconds
     */
    private int timeout = 0;
    
    /**
     * Use strict return codes if true
     */
    private boolean strictReturnCodes = true;
    
    /**
     *  Can be used to cancel a transfer
     */
    private boolean cancelTransfer = false;
    
    /**
     * If true, a file transfer is being resumed
     */
    private boolean resume = false;
    
    /**
     * Resume byte marker point
     */
    private long resumeMarker = 0;
    
    /**
     * Bytes transferred in between monitor callbacks
     */
    private long monitorInterval = DEFAULT_MONITOR_INTERVAL;
    
    /**
     * Size of transfer buffers
     */
    private int transferBufferSize = DEFAULT_BUFFER_SIZE;
    
    /**
     * Parses LIST output
     */
    private FTPFileFactory fileFactory = null;
    
    /**
     * Locale for date parsing
     */
    private Locale listingLocale = Locale.getDefault();
    
    /**
     *  Progress monitor
     */
    private FTPProgressMonitor monitor = null;  
    
    /**
     * Message listener
     */
    protected FTPMessageListener messageListener = null;

    /**
     *  Record of the transfer type - make the default ASCII
     */
    private FTPTransferType transferType = FTPTransferType.ASCII;

    /**
     *  Record of the connect mode - make the default PASV (as this was
     *  the original mode supported)
     */
    private FTPConnectMode connectMode = FTPConnectMode.PASV;

    /**
     *  Holds the last valid reply from the server on the control socket
     */
	protected FTPReply lastValidReply;    
    
    /**
     *  Instance initializer. Sets formatter to GMT.
     */
    {
        tsFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }  
    
    
    /**
     * Get the version of edtFTPj
     * 
     * @return int array of {major,middle,minor} version numbers 
     */
    public static int[] getVersion() {
        return version;
    }
    
    /**
     * Get the build timestamp
     * 
     * @return d-MMM-yyyy HH:mm:ss z build timestamp 
     */
    public static String getBuildTimestamp() {
        return buildTimestamp;
    }

    /**
     *  Constructor. Creates the control
     *  socket
     *
     *  @param   remoteHost  the remote hostname
     */
    public FTPClient(String remoteHost)
        throws IOException, FTPException {

		this(remoteHost, FTPControlSocket.CONTROL_PORT, 0);
    }

    /**
     *  Constructor. Creates the control
     *  socket
     *
     *  @param   remoteHost  the remote hostname
     *  @param   controlPort  port for control stream (-1 for default port)
     */
    public FTPClient(String remoteHost, int controlPort)
        throws IOException, FTPException {

		this(remoteHost, controlPort, 0);
    }
    
    
    /**
     *  Constructor. Creates the control
     *  socket
     *
     *  @param   remoteHost  the remote hostname
     *  @param   controlPort  port for control stream (use -1 for the default port)
     *  @param  timeout       the length of the timeout, in milliseconds
     *                        (pass in 0 for no timeout)
     */
    public FTPClient(String remoteHost, int controlPort, int timeout)
    throws IOException, FTPException {

        this(InetAddress.getByName(remoteHost), controlPort, timeout);
    }

    /**
     *  Constructor. Creates the control
     *  socket
     *
     *  @param   remoteHost  the remote hostname
     *  @param   controlPort  port for control stream (use -1 for the default port)
     *  @param  timeout       the length of the timeout, in milliseconds
     *                        (pass in 0 for no timeout)
     *  @param   encoding         character encoding used for data
     */
    public FTPClient(String remoteHost, int controlPort, int timeout, String encoding)
    throws IOException, FTPException {

        this(InetAddress.getByName(remoteHost), controlPort, timeout, encoding);
    }

    /**
     *  Constructor. Creates the control
     *  socket
     *
     *  @param   remoteAddr  the address of the
     *                       remote host
     */
    public FTPClient(InetAddress remoteAddr)
        throws IOException, FTPException {

		this(remoteAddr, FTPControlSocket.CONTROL_PORT, 0);
    }
    

    /**
     *  Constructor. Creates the control
     *  socket. Allows setting of control port (normally
     *  set by default to 21).
     *
     *  @param   remoteAddr  the address of the
     *                       remote host
     *  @param   controlPort  port for control stream
     */
    public FTPClient(InetAddress remoteAddr, int controlPort)
        throws IOException, FTPException {

		this(remoteAddr, controlPort, 0);
    }

    /**
     *  Constructor. Creates the control
     *  socket. Allows setting of control port (normally
     *  set by default to 21).
     *
     *  @param   remoteAddr    the address of the
     *                          remote host
     *  @param   controlPort   port for control stream (-1 for default port)
     *  @param  timeout        the length of the timeout, in milliseconds 
     *                         (pass in 0 for no timeout)
     */
    public FTPClient(InetAddress remoteAddr, int controlPort, int timeout)
        throws IOException, FTPException {
        if (controlPort < 0)
            controlPort = FTPControlSocket.CONTROL_PORT;
		initialize(new FTPControlSocket(remoteAddr, controlPort, timeout, DEFAULT_ENCODING, null));
    }
    
    /**
     *  Constructor. Creates the control
     *  socket. Allows setting of control port (normally
     *  set by default to 21).
     *
     *  @param   remoteAddr    the address of the
     *                          remote host
     *  @param   controlPort   port for control stream (-1 for default port)
     *  @param   timeout        the length of the timeout, in milliseconds 
     *                         (pass in 0 for no timeout)
     *  @param   encoding         character encoding used for data
     */
    public FTPClient(InetAddress remoteAddr, int controlPort, int timeout, String encoding)
        throws IOException, FTPException {
        if (controlPort < 0)
            controlPort = FTPControlSocket.CONTROL_PORT;
        initialize(new FTPControlSocket(remoteAddr, controlPort, timeout, encoding, null));
    }
    
    /**
     *  Default constructor for use by subclasses
     */
    protected FTPClient() {
    }
    
    /**
     * Checks if the client has connected to the server and throws an exception if it hasn't.
     * This is only intended to be used by subclasses
     * 
     * @throws FTPException Thrown if the client has not connected to the server.
     */
    protected void checkConnection(boolean shouldBeConnected) throws FTPException {
    	if (shouldBeConnected && control==null)
    		throw new FTPException("The FTP client has not yet connected to the server.  "
    				+ "The requested action cannot be performed until after a connection has been established.");
    	else if (!shouldBeConnected && control!=null)
    		throw new FTPException("The FTP client has already been connected to the server.  "
    				+"The requested action must be performed before a connection is established.");
    }
    	
    /**
     * Set the control socket explicitly
     * 
     * @param control   control socket reference
     */
	protected void initialize(FTPControlSocket control) {
		this.control = control;
        control.setMessageListener(messageListener);
	}
    
    /**
     *  Switch debug of responses on or off
     *
     *  @param  on  true if you wish to have responses to
     *              the log stream, false otherwise
     *  @deprecated  use the Logger class to switch debugging on and off
     */
    public void debugResponses(boolean on) {
        if (on)
            Logger.setLevel(Level.DEBUG);
        else
            Logger.setLevel(Level.OFF);
    }    
    
    /**
     * Set strict checking of FTP return codes. If strict 
     * checking is on (the default) code must exactly match the expected 
     * code. If strict checking is off, only the first digit must match.
     * 
     * @param strict    true for strict checking, false for loose checking
     */
    public void setStrictReturnCodes(boolean strict) {
        this.strictReturnCodes = strict;
        if (control != null)
            control.setStrictReturnCodes(strict);
    }
    
    /**
     * Determine if strict checking of return codes is switched on. If it is 
     * (the default), all return codes must exactly match the expected code.  
     * If strict checking is off, only the first digit must match.
     * 
     * @return  true if strict return code checking, false if non-strict.
     */
    public boolean isStrictReturnCodes() {
        return strictReturnCodes;
    }

    /**
     *   Set the TCP timeout on the underlying socket.
     *
     *   If a timeout is set, then any operation which
     *   takes longer than the timeout value will be
     *   killed with a java.io.InterruptedException. We
     *   set both the control and data connections
     *
     *   @param millis The length of the timeout, in milliseconds
     */
    public void setTimeout(int millis)
        throws IOException {

        this.timeout = millis;
        control.setTimeout(millis);
    }

    /**
     *  Set the connect mode
     *
     *  @param  mode  ACTIVE or PASV mode
     */
    public void setConnectMode(FTPConnectMode mode) {
        connectMode = mode;
    }
    
    /**
     * Set a listener that handles all FTP messages
     * 
     * @param listener  message listener
     */
    public void setMessageListener(FTPMessageListener listener) {
        this.messageListener = listener;
        if (control != null)
           control.setMessageListener(listener);
    }
    
    /**
     *  Set a progress monitor for callbacks. The bytes transferred in
     *  between callbacks is only indicative. In many cases, the data is
     *  read in chunks, and if the interval is set to be smaller than the
     *  chunk size, the callback will occur after after chunk transfer rather
     *  than the interval.
     *
     *  @param  monitor   the monitor object
     *  @param  interval  bytes transferred in between callbacks
     */
    public void setProgressMonitor(FTPProgressMonitor monitor, long interval) {
        this.monitor = monitor;
        this.monitorInterval = interval;
    }    
    
    /**
     *  Set a progress monitor for callbacks. Uses default callback
     *  interval
     *
     *  @param  monitor   the monitor object
     */
    public void setProgressMonitor(FTPProgressMonitor monitor) {
        this.monitor = monitor;
    }    
        
    /**
     *  Get the bytes transferred between each callback on the
     *  progress monitor
     * 
     * @return long     bytes to be transferred before a callback
     */
    public long getMonitorInterval() {
        return monitorInterval;
    }
    
    /**
     * Set the size of the buffers used in writing to and reading from
     * the data sockets
     * 
     * @param size  new size of buffer
     */
    public void setTransferBufferSize(int size) {
        transferBufferSize = size;
    }
    
    /**
     * Get the size of the buffers used in writing to and reading from
     * the data sockets
     * 
     * @return  transfer buffer size
     */
    public int getTransferBufferSize() {
        return transferBufferSize;
    }
    
    /**
     *  Cancels the current transfer. Generally called from a separate
     *  thread. Note that this may leave partially written files on the
     *  server or on local disk, and should not be used unless absolutely
     *  necessary. The server is not notified
     */
    public void cancelTransfer() {
        cancelTransfer = true;
    }  
       
    /**
     *  Login into an account on the FTP server. This
     *  call completes the entire login process
     *
     *  @param   user       user name
     *  @param   password   user's password
     */
    public void login(String user, String password)
        throws IOException, FTPException {
    	
    	checkConnection(true);

        FTPReply reply = control.sendCommand("USER " + user);
        
        // we allow for a site with no password - 230 response
        String[] validCodes = {"230", "331"};
        lastValidReply = control.validateReply(reply, validCodes);
        if (lastValidReply.getReplyCode().equals("230"))
            return;
        else {
            password(password);
        }
    }
    
    /**
     *  Supply the user name to log into an account
     *  on the FTP server. Must be followed by the
     *  password() method - but we allow for
     *
     *  @param   user       user name
     */
    public void user(String user)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("USER " + user);

        // we allow for a site with no password - 230 response
        String[] validCodes = {"230", "331"};
        lastValidReply = control.validateReply(reply, validCodes);
    }


    /**
     *  Supplies the password for a previously supplied
     *  username to log into the FTP server. Must be
     *  preceeded by the user() method
     *
     *  @param   password       The password.
     */
    public void password(String password)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("PASS " + password);

        // we allow for a site with no passwords (202)
        String[] validCodes = {"230", "202"};
        lastValidReply = control.validateReply(reply, validCodes);
    }

    /**
     *  Set up SOCKS v4/v5 proxy settings. This can be used if there
     *  is a SOCKS proxy server in place that must be connected thru.
     *  Note that setting these properties directs <b>all</b> TCP
     *  sockets in this JVM to the SOCKS proxy
     *
     *  @param  port  SOCKS proxy port
     *  @param  host  SOCKS proxy hostname
     */
    public static void initSOCKS(String port, String host) {
        Properties props = System.getProperties();
        props.put(SOCKS_PORT, port);
        props.put(SOCKS_HOST, host);
        System.setProperties(props);
    }

    /**
     *  Set up SOCKS username and password for SOCKS username/password
     *  authentication. Often, no authentication will be required
     *  but the SOCKS server may be configured to request these.
     *
     *  @param  username   the SOCKS username
     *  @param  password   the SOCKS password
     */
    public static void initSOCKSAuthentication(String username,
                                               String password) {
        Properties props = System.getProperties();
        props.put("java.net.socks.username", username);
        props.put("java.net.socks.password", password);
        System.setProperties(props);
    }
    
    /**
     * Clear SOCKS settings. Note that setting these properties affects 
     * <b>all</b> TCP sockets in this JVM
     */
    public static void clearSOCKS() {
        
        Properties prop = System.getProperties(); 
        prop.remove(SOCKS_HOST); 
        prop.remove(SOCKS_PORT); 
        System.setProperties(prop); 
    }

    /**
     *  Get the name of the remote host
     *
     *  @return  remote host name
     */
    String getRemoteHostName() {
        return control.getRemoteHostName();
    }

    /**
     *  Issue arbitrary ftp commands to the FTP server.
     *
     *  @param command     ftp command to be sent to server
     *  @param validCodes  valid return codes for this command
     * 
     *  @return  the text returned by the FTP server
     */
    public String quote(String command, String[] validCodes)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand(command);

        // allow for no validation to be supplied
        if (validCodes != null && validCodes.length > 0) {
            lastValidReply = control.validateReply(reply, validCodes);
            return lastValidReply.getReplyText();
        }
        else {
            throw new FTPException("Valid reply code must be supplied");
        }            
    }    


    /**
     *  Get the size of a remote file. This is not a standard FTP command, it
     *  is defined in "Extensions to FTP", a draft RFC 
     *  (draft-ietf-ftpext-mlst-16.txt)
     *
     *  @param  remoteFile  name or path of remote file in current directory
     *  @return size of file in bytes      
     */      
     public long size(String remoteFile)
         throws IOException, FTPException {
     	
     	 checkConnection(true);
     	
         FTPReply reply = control.sendCommand("SIZE " + remoteFile);
         lastValidReply = control.validateReply(reply, "213");

         // parse the reply string .
         String replyText = lastValidReply.getReplyText();
         
         // trim off any trailing characters after a space, e.g. webstar
         // responds to SIZE with 213 55564 bytes
         int spacePos = replyText.indexOf(' ');
         if (spacePos >= 0)
             replyText = replyText.substring(0, spacePos);
         
         // parse the reply
         try {
             return Long.parseLong(replyText);
         }
         catch (NumberFormatException ex) {
             throw new FTPException("Failed to parse reply: " + replyText);
         }         
     }
     
     /**
      * Make the next file transfer (put or get) resume. For puts(), the
      * bytes already transferred are skipped over, while for gets(), if 
      * writing to a file, it is opened in append mode, and only the bytes
      * required are transferred.
      * 
      * Currently resume is only supported for BINARY transfers (which is
      * generally what it is most useful for).
      * 
      * @throws FTPException
      */
     public void resume() throws FTPException {
         if (transferType.equals(FTPTransferType.ASCII))
             throw new FTPException("Resume only supported for BINARY transfers");
         resume = true;
     }
     
     /**
      * Cancel the resume. Use this method if something goes wrong
      * and the server is left in an inconsistent state
      * 
      * @throws IOException
      * @throws FTPException
      */
     public void cancelResume() 
         throws IOException, FTPException {
         restart(0);
         resume = false;
     }
     
     /**
      * Issue the RESTart command to the remote server 
      * 
      * @param size     the REST param, the mark at which the restart is 
      *                  performed on the remote file. For STOR, this is retrieved
      *                  by SIZE
      * @throws IOException
      * @throws FTPException
      */
     private void restart(long size) 
         throws IOException, FTPException {
         FTPReply reply = control.sendCommand("REST " + size);
         lastValidReply = control.validateReply(reply, "350");
     }


    /**
     *  Put a local file onto the FTP server. It
     *  is placed in the current directory.
     *
     *  @param  localPath   path of the local file
     *  @param  remoteFile  name of remote file in
     *                      current directory
     */
    public void put(String localPath, String remoteFile)
        throws IOException, FTPException {

        put(localPath, remoteFile, false);
    }

    /**
     *  Put a stream of data onto the FTP server. It
     *  is placed in the current directory.
     *
     *  @param  srcStream   input stream of data to put
     *  @param  remoteFile  name of remote file in
     *                      current directory
     */
    public void put(InputStream srcStream, String remoteFile)
        throws IOException, FTPException {

        put(srcStream, remoteFile, false);
    }


    /**
     *  Put a local file onto the FTP server. It
     *  is placed in the current directory. Allows appending
     *  if current file exists
     *
     *  @param  localPath   path of the local file
     *  @param  remoteFile  name of remote file in
     *                      current directory
     *  @param  append      true if appending, false otherwise
     */
    public void put(String localPath, String remoteFile,
                    boolean append)
        throws IOException, FTPException {
    	    	
        // get according to set type
        if (getType() == FTPTransferType.ASCII) {
            putASCII(localPath, remoteFile, append);
        }
        else {
            putBinary(localPath, remoteFile, append);
        }
        validateTransfer();
     }

    /**
     *  Put a stream of data onto the FTP server. It
     *  is placed in the current directory. Allows appending
     *  if current file exists
     *
     *  @param  srcStream   input stream of data to put
     *  @param  remoteFile  name of remote file in
     *                      current directory
     *  @param  append      true if appending, false otherwise
     */
    public void put(InputStream srcStream, String remoteFile,
                    boolean append)
        throws IOException, FTPException {

        // get according to set type
        if (getType() == FTPTransferType.ASCII) {
            putASCII(srcStream, remoteFile, append);
        }
        else {
            putBinary(srcStream, remoteFile, append);
        }
        validateTransfer();
    }

    /**
     * Validate that the put() or get() was successful.  This method is not
     * for general use.
     */
    public void validateTransfer()
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        // check the control response
        String[] validCodes = {"225", "226", "250", "426", "450"};
        FTPReply reply = control.readReply();
        
        // permit 426/450 error if we cancelled the transfer, otherwise
        // throw an exception
        String code = reply.getReplyCode();
        if ( (code.equals("426")||code.equals("450")) && !cancelTransfer )
            throw new FTPException(reply);
        
        lastValidReply = control.validateReply(reply, validCodes);
    }
    
    /**
     * Close the data socket
     */
    private void closeDataSocket() {
        if (data != null) {
            try {
                data.close();
                data = null;
            }
            catch (IOException ex) {
                log.warn("Caught exception closing data socket", ex);
            }
        }
    }

    /**
     *  Request the server to set up the put
     *
     *  @param  remoteFile  name of remote file in
     *                      current directory
     *  @param  append      true if appending, false otherwise
     */
    private void initPut(String remoteFile, boolean append)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        // reset the cancel flag
        cancelTransfer = false;

        boolean close = false;
        data = null;
        try {
            // set up data channel
            data = control.createDataSocket(connectMode);
            data.setTimeout(timeout);
            
            // if resume is requested, we must obtain the size of the
            // remote file and issue REST
            if (resume) {
                if (transferType.equals(FTPTransferType.ASCII))
                    throw new FTPException("Resume only supported for BINARY transfers");
                resumeMarker = size(remoteFile);
                restart(resumeMarker);
            }
    
            // send the command to store
            String cmd = append ? "APPE " : "STOR ";
            FTPReply reply = control.sendCommand(cmd + remoteFile);
    
            // Can get a 125 or a 150
            String[] validCodes = {"125", "150"};
            lastValidReply = control.validateReply(reply, validCodes);
        }
        catch (IOException ex) {
            close = true;
            throw ex;
        }
        catch (FTPException ex) {
            close = true;
            throw ex;
        }
        finally {
            if (close) {
                resume = false;
                closeDataSocket();
            }
        }
    }


    /**
     *  Put as ASCII, i.e. read a line at a time and write
     *  inserting the correct FTP separator
     *
     *  @param localPath   full path of local file to read from
     *  @param remoteFile  name of remote file we are writing to
     *  @param  append      true if appending, false otherwise
     */
    private void putASCII(String localPath, String remoteFile, boolean append)
        throws IOException, FTPException {

        // create an inputstream & pass to common method
        InputStream srcStream = new FileInputStream(localPath);
        putASCII(srcStream, remoteFile, append);
    }

    /**
     *  Put as ASCII, i.e. read a line at a time and write
     *  inserting the correct FTP separator
     *
     *  @param  srcStream   input stream of data to put
     *  @param  remoteFile  name of remote file we are writing to
     *  @param  append      true if appending, false otherwise
     */
    private void putASCII(InputStream srcStream, String remoteFile,
                          boolean append)
        throws IOException, FTPException {

        // need to read line by line ...
        LineNumberReader in = null;
        BufferedWriter out = null;
        long size = 0;
        try {
            in = new LineNumberReader(new InputStreamReader(srcStream));

            initPut(remoteFile, append);
    
            // get an character output stream to write to ... AFTER we
            // have the ok to go ahead AND AFTER we've successfully opened a
            // stream for the local file
            out = new BufferedWriter(
                    new OutputStreamWriter(data.getOutputStream()));
    
            // write \r\n as required by RFC959 after each line
            long monitorCount = 0;
            int ch = -1;        
            while ((ch = in.read()) != -1 && !cancelTransfer) {
                size++;
                monitorCount++;
                if (ch == '\n') 
                    out.write(FTPControlSocket.EOL);
                else
                    out.write(ch);
                
                if (monitor != null && monitorCount > monitorInterval) {
                    monitor.bytesTransferred(size); 
                    monitorCount = 0;  
                }
            }
        }
        finally {
            try {
                if (in != null)
                    in.close();
            }
            catch (IOException ignore) {}
            try {
                if (out != null) {
                    out.flush();
                    //out.close(); // Hans
                }
            }
            catch (IOException ignore) {}
            
            closeDataSocket();
            
            // notify the final transfer size
            if (monitor != null)
                monitor.bytesTransferred(size);
        }
    }


    /**
     *  Put as binary, i.e. read and write raw bytes
     *
     *  @param localPath   full path of local file to read from
     *  @param remoteFile  name of remote file we are writing to
     *  @param  append      true if appending, false otherwise
     */
    private void putBinary(String localPath, String remoteFile,
                           boolean append)
        throws IOException, FTPException {

        // open input stream to read source file ... do this
        // BEFORE opening output stream to server, so if file not
        // found, an exception is thrown
        InputStream srcStream = new FileInputStream(localPath);
        putBinary(srcStream, remoteFile, append);
    }

    /**
     *  Put as binary, i.e. read and write raw bytes
     *
     *  @param  srcStream   input stream of data to put
     *  @param  remoteFile  name of remote file we are writing to
     *  @param  append      true if appending, false otherwise
     */
    private void putBinary(InputStream srcStream, String remoteFile,
                           boolean append)
        throws IOException, FTPException {

        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        long size = 0;
        try {
            in = new BufferedInputStream(srcStream);
    
            initPut(remoteFile, append);
    
            // get an output stream
            out = new BufferedOutputStream(
                    new DataOutputStream(data.getOutputStream()));
            
            // if resuming, we skip over the unwanted bytes
            if (resume) {
                in.skip(resumeMarker);
            }
    
            byte[] buf = new byte[transferBufferSize];
    
            // read a chunk at a time and write to the data socket            
            long monitorCount = 0;
            int count = 0;
            while ((count = in.read(buf)) > 0 && !cancelTransfer) {
                out.write(buf, 0, count);
                size += count;
                monitorCount += count;
                if (monitor != null && monitorCount > monitorInterval) {
                    monitor.bytesTransferred(size); 
                    monitorCount = 0;  
                }
            }
        }
        finally {
            resume = false;
            try {
                if (in != null)
                    in.close();
            }
            catch (IOException ignore) {}    
    
            // flush and clean up
            try {
                if (out != null) {
                    out.flush();
                    out.close(); // Hans XXXXXXX
                }                
            }
            catch (IOException ignore) {}  
            
            closeDataSocket();
            
            // notify the final transfer size
            if (monitor != null)
                monitor.bytesTransferred(size);  
            // log bytes transferred
            log.debug("Transferred " + size + " bytes to remote host");           
        }
    }


    /**
     *  Put data onto the FTP server. It
     *  is placed in the current directory.
     *
     *  @param  bytes        array of bytes
     *  @param  remoteFile  name of remote file in
     *                      current directory
     */
    public void put(byte[] bytes, String remoteFile)
        throws IOException, FTPException {

        put(bytes, remoteFile, false);
    }

    /**
     *  Put data onto the FTP server. It
     *  is placed in the current directory. Allows
     *  appending if current file exists
     *
     *  @param  bytes        array of bytes
     *  @param  remoteFile  name of remote file in
     *                      current directory
     *  @param  append      true if appending, false otherwise
     */
    public void put(byte[] bytes, String remoteFile, boolean append)
        throws IOException, FTPException {

        initPut(remoteFile, append);

        // get an output stream
        BufferedOutputStream out =
            new BufferedOutputStream(
                new DataOutputStream(data.getOutputStream()));

        try {
            // write array
            out.write(bytes, 0, bytes.length);
        }
        finally {
    
            // flush and clean up
            try {
                out.flush();
                //out.close(); // Hans
            }
            catch (IOException ignore) {}
    
            closeDataSocket();
        }

        validateTransfer();
    }


    /**
     *  Get data from the FTP server. Uses the currently
     *  set transfer mode.
     *
     *  @param  localPath   local file to put data in
     *  @param  remoteFile  name of remote file in
     *                      current directory
     */
    public void get(String localPath, String remoteFile)
        throws IOException, FTPException {

        // get according to set type
        if (getType() == FTPTransferType.ASCII) {
            getASCII(localPath, remoteFile);
        }
        else {
            getBinary(localPath, remoteFile);
        }
        validateTransfer();
    }

    /**
     *  Get data from the FTP server. Uses the currently
     *  set transfer mode.
     *
     *  @param  destStream  data stream to write data to
     *  @param  remoteFile  name of remote file in
     *                      current directory
     */
    public void get(OutputStream destStream, String remoteFile)
        throws IOException, FTPException {

        // get according to set type
        if (getType() == FTPTransferType.ASCII) {
            getASCII(destStream, remoteFile);
        }
        else {
            getBinary(destStream, remoteFile);
        }
        validateTransfer();
    }


    /**
     *  Request to the server that the get is set up
     *
     *  @param  remoteFile  name of remote file
     */
    private void initGet(String remoteFile)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        // reset the cancel flag
        cancelTransfer = false;            

        boolean close = false;
        data = null;
        try {
            // set up data channel
            data = control.createDataSocket(connectMode);
            data.setTimeout(timeout);
            
            // if resume is requested, we must issue REST
            if (resume) {
                if (transferType.equals(FTPTransferType.ASCII))
                    throw new FTPException("Resume only supported for BINARY transfers");
                restart(resumeMarker);
            }
    
            // send the retrieve command
            FTPReply reply = control.sendCommand("RETR " + remoteFile);
    
            // Can get a 125 or a 150
            String[] validCodes1 = {"125", "150"};
            lastValidReply = control.validateReply(reply, validCodes1);
        }
        catch (IOException ex) {
            close = true;
            throw ex;
        }
        catch (FTPException ex) {
            close = true;
            throw ex;
        }
        finally {
            if (close) {
                resume = false;
                closeDataSocket();
            }
        }
    }


    /**
     *  Get as ASCII, i.e. read a line at a time and write
     *  using the correct newline separator for the OS
     *
     *  @param localPath   full path of local file to write to
     *  @param remoteFile  name of remote file
     */
    private void getASCII(String localPath, String remoteFile)
        throws IOException, FTPException {

        // B.McKeown:
        // Call initGet() before creating the FileOutputStream.
        // This will prevent being left with an empty file if a FTPException
        // is thrown by initGet().
        initGet(remoteFile);

        // B. McKeown: Need to store the local file name so the file can be
        // deleted if necessary.
        File localFile = new File(localPath);

        // create the buffered stream for writing
        BufferedWriter out =
            new BufferedWriter(
                new FileWriter(localPath));

        // get an character input stream to read data from ... AFTER we
        // have the ok to go ahead AND AFTER we've successfully opened a
        // stream for the local file
        LineNumberReader in =
            new LineNumberReader(
                new BufferedReader(
                   new InputStreamReader(data.getInputStream())));

        // B. McKeown:
        // If we are in active mode we have to set the timeout of the passive
        // socket. We can achieve this by calling setTimeout() again.
        // If we are in passive mode then we are merely setting the value twice
        // which does no harm anyway. Doing this simplifies any logic changes.
        data.setTimeout(timeout);

        // output a new line after each received newline
        IOException storedEx = null;
        long size = 0;
        long monitorCount = 0;
        try {
            int ch = -1;        
            while ((ch = readChar(in)) != -1 && !cancelTransfer) {
                size++;
                monitorCount++;
                if (ch == '\n')
                    out.newLine();
                else
                    out.write(ch);
                
                if (monitor != null && monitorCount > monitorInterval) {
                    monitor.bytesTransferred(size); 
                    monitorCount = 0;  
                }                    
            }            
        }
        catch (IOException ex) {
            storedEx = ex;
            localFile.delete();
        }
        finally {
            out.close();
        }

        closeDataSocket();
        
		// if we failed to write the file, rethrow the exception
		if (storedEx != null)
			throw storedEx;
        else if (monitor != null) // notify final size
            monitor.bytesTransferred(size);
            
    }

    /**
     *  Get as ASCII, i.e. read a line at a time and write
     *  using the correct newline separator for the OS
     *
     *  @param destStream  data stream to write data to
     *  @param remoteFile  name of remote file
     */
    private void getASCII(OutputStream destStream, String remoteFile)
        throws IOException, FTPException {

        initGet(remoteFile);

        // create the buffered stream for writing
        BufferedWriter out =
            new BufferedWriter(
                new OutputStreamWriter(destStream));

        // get an character input stream to read data from ... AFTER we
        // have the ok to go ahead
        LineNumberReader in =
            new LineNumberReader(
                new BufferedReader(
                    new InputStreamReader(data.getInputStream())));

        // B. McKeown:
        // If we are in active mode we have to set the timeout of the passive
        // socket. We can achieve this by calling setTimeout() again.
        // If we are in passive mode then we are merely setting the value twice
        // which does no harm anyway. Doing this simplifies any logic changes.
        data.setTimeout(timeout);

        // output a new line after each received newline
        IOException storedEx = null;
        long size = 0;
        long monitorCount = 0;        
        try {
            int ch = -1;        
            while ((ch = readChar(in)) != -1 && !cancelTransfer) {
                size++;
                monitorCount++;
                if (ch == '\n')
                    out.newLine();
                else
                    out.write(ch);
                    
                if (monitor != null && monitorCount > monitorInterval) {
                    monitor.bytesTransferred(size); 
                    monitorCount = 0;  
                }                                        
            }            
        }
        catch (IOException ex) {
            storedEx = ex;
        }
        finally {
            out.close();
        }
        
        closeDataSocket();

        // if we failed to write the file, rethrow the exception
        if (storedEx != null)
            throw storedEx;
        else if (monitor != null)
            monitor.bytesTransferred(size); 
    }


    /**
     *  Get as binary file, i.e. straight transfer of data
     *
     *  @param localPath   full path of local file to write to
     *  @param remoteFile  name of remote file
     */
    private void getBinary(String localPath, String remoteFile)
        throws IOException, FTPException {

        // B. McKeown: Need to store the local file name so the file can be
        // deleted if necessary.
        File localFile = new File(localPath);
        
        // if resuming, we must find the marker
        if (localFile.exists() && resume)
            resumeMarker = localFile.length();

        // B.McKeown:
        // Call initGet() before creating the FileOutputStream.
        // This will prevent being left with an empty file if a FTPException
        // is thrown by initGet().
        initGet(remoteFile);

        // create the buffered output stream for writing the file
        BufferedOutputStream out =
            new BufferedOutputStream(
                new FileOutputStream(localPath, resume));

        // get an input stream to read data from ... AFTER we have
        // the ok to go ahead AND AFTER we've successfully opened a
        // stream for the local file
        BufferedInputStream in =
            new BufferedInputStream(
                new DataInputStream(data.getInputStream()));

        // B. McKeown:
        // If we are in active mode we have to set the timeout of the passive
        // socket. We can achieve this by calling setTimeout() again.
        // If we are in passive mode then we are merely setting the value twice
        // which does no harm anyway. Doing this simplifies any logic changes.
        data.setTimeout(timeout);

        // do the retrieving
        long size = 0;
        long monitorCount = 0; 
        byte [] chunk = new byte[transferBufferSize];
        int count;
        IOException storedEx = null;

        // read from socket & write to file in chunks
        try {
            while ((count = readChunk(in, chunk, transferBufferSize)) >= 0 && !cancelTransfer) {
                out.write(chunk, 0, count);
                size += count;
                monitorCount += count;
                
                if (monitor != null && monitorCount > monitorInterval) {
                    monitor.bytesTransferred(size); 
                    monitorCount = 0;  
                }                                                        
            }
        }
        catch (IOException ex) {
            storedEx = ex;
            localFile.delete();
        }
        finally {
            out.close();
        }
        resume = false;

        // close streams
        closeDataSocket();

        // if we failed to write the file, rethrow the exception
        if (storedEx != null)
            throw storedEx;
        else if (monitor != null)
            monitor.bytesTransferred(size);  

        // log bytes transferred
    	log.debug("Transferred " + size + " bytes from remote host");
    }

    /**
     *  Get as binary file, i.e. straight transfer of data
     *
     *  @param destStream  stream to write to
     *  @param remoteFile  name of remote file
     */
    private void getBinary(OutputStream destStream, String remoteFile)
        throws IOException, FTPException {

        initGet(remoteFile);

        // create the buffered output stream for writing the file
        BufferedOutputStream out =
            new BufferedOutputStream(destStream);

        // get an input stream to read data from ... AFTER we have
        // the ok to go ahead AND AFTER we've successfully opened a
        // stream for the local file
        BufferedInputStream in =
            new BufferedInputStream(
                new DataInputStream(data.getInputStream()));

        // B. McKeown:
        // If we are in active mode we have to set the timeout of the passive
        // socket. We can achieve this by calling setTimeout() again.
        // If we are in passive mode then we are merely setting the value twice
        // which does no harm anyway. Doing this simplifies any logic changes.
        data.setTimeout(timeout);

        // do the retrieving
        long size = 0;
        long monitorCount = 0; 
        byte [] chunk = new byte[transferBufferSize];
        int count;
        IOException storedEx = null;

        // read from socket & write to file in chunks
        try {
            while ((count = readChunk(in, chunk, transferBufferSize)) >= 0 && !cancelTransfer) {
                out.write(chunk, 0, count);
                size += count;
                monitorCount += count;
                
                if (monitor != null && monitorCount > monitorInterval) {
                    monitor.bytesTransferred(size); 
                    monitorCount = 0;  
                }                                                                        
            }
        }
        catch (IOException ex) {
            storedEx = ex;
        }
        finally {
            out.close();
        }

        closeDataSocket();

        // if we failed to write to the stream, rethrow the exception
        if (storedEx != null)
            throw storedEx;
        else if (monitor != null)
            monitor.bytesTransferred(size);  

		// log bytes transferred
		log.debug("Transferred " + size + " bytes from remote host");            
    }

    /**
     *  Get data from the FTP server. Transfers in
     *  whatever mode we are in. Retrieve as a byte array. Note
     *  that we may experience memory limitations as the
     *  entire file must be held in memory at one time.
     *
     *  @param  remoteFile  name of remote file in
     *                      current directory
     */
    public byte[] get(String remoteFile)
        throws IOException, FTPException {

        initGet(remoteFile);

        // get an input stream to read data from
        BufferedInputStream in =
            new BufferedInputStream(
                new DataInputStream(data.getInputStream()));

        // B. McKeown:
        // If we are in active mode we have to set the timeout of the passive
        // socket. We can achieve this by calling setTimeout() again.
        // If we are in passive mode then we are merely setting the value twice
        // which does no harm anyway. Doing this simplifies any logic changes.
        data.setTimeout(timeout);

        // do the retrieving
        long size = 0;
        long monitorCount = 0; 
        byte [] chunk = new byte[transferBufferSize];  // read chunks into
        byte [] resultBuf = null; // where we place result
        ByteArrayOutputStream temp =
            new ByteArrayOutputStream(transferBufferSize); // temp swap buffer
        int count;  // size of chunk read

        // read from socket & write to file
        while ((count = readChunk(in, chunk, transferBufferSize)) >= 0 && !cancelTransfer) {
            temp.write(chunk, 0, count);
            size += count;
            monitorCount += count;
            
            if (monitor != null && monitorCount > monitorInterval) {
                monitor.bytesTransferred(size); 
                monitorCount = 0;  
            }                                                                        

        }
        temp.close();

        // get the bytes from the temp buffer
        resultBuf = temp.toByteArray();
        
        closeDataSocket();

        // notify final transfer size
        if (monitor != null)
            monitor.bytesTransferred(size); 
        
        validateTransfer();
        
        return resultBuf;
    }


    /**
     *  Run a site-specific command on the
     *  server. Support for commands is dependent
     *  on the server
     *
     *  @param  command   the site command to run
     *  @return true if command ok, false if
     *          command not implemented
     */
    public boolean site(String command)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        // send the retrieve command
        FTPReply reply = control.sendCommand("SITE " + command);

        // Can get a 200 (ok) or 202 (not impl). Some
        // FTP servers return 502 (not impl)
        String[] validCodes = {"200", "202", "502"};
        lastValidReply = control.validateReply(reply, validCodes);

        // return true or false? 200 is ok, 202/502 not
        // implemented
        if (reply.getReplyCode().equals("200"))
            return true;
        else
            return false;
    }


    /**
     *  List a directory's contents
     *
     *  @param  dirname  the name of the directory (<b>not</b> a file mask)
     *  @return a string containing the line separated
     *          directory listing
     *  @deprecated  As of FTP 1.1, replaced by {@link #dir(String)}
     */
    public String list(String dirname)
        throws IOException, FTPException {

        return list(dirname, false);
    }


    /**
     *  List a directory's contents as one string. A detailed
     *  listing is available, otherwise just filenames are provided.
     *  The detailed listing varies in details depending on OS and
     *  FTP server.
     *
     *  @param  dirname  the name of the directory(<b>not</b> a file mask)
     *  @param  full     true if detailed listing required
     *                   false otherwise
     *  @return a string containing the line separated
     *          directory listing
     *  @deprecated  As of FTP 1.1, replaced by {@link #dir(String,boolean)}
     */
    public String list(String dirname, boolean full)
        throws IOException, FTPException {

        String[] list = dir(dirname, full);

        StringBuffer result = new StringBuffer();
        String sep = System.getProperty("line.separator");

        // loop thru results and make into one string
        for (int i = 0; i < list.length; i++) {
            result.append(list[i]);
            result.append(sep);
        }

        return result.toString();
    }
    
    
    /**
     * Override the chosen file factory with a user created one - meaning
     * that a specific parser has been selected
     * 
     * @param fileFactory
     */
    public void setFTPFileFactory(FTPFileFactory fileFactory) {
        this.fileFactory = fileFactory;
    }
    
    /**
     * Set the locale for date parsing of dir listings
     * 
     * @param locale    new locale to use
     */
    public void setParserLocale(Locale locale) {
        listingLocale = locale;
    }
    
    /**
     *  List a directory's contents as an array of FTPFile objects.
     *  Should work for Windows and most Unix FTP servers - let us know
     *  about unusual formats (http://www.enterprisedt.com/forums/index.php)
     *
     *  @param   dirname  name of directory OR filemask
     *  @return  an array of FTPFile objects
     */
    public FTPFile[] dirDetails(String dirname) 
        throws IOException, FTPException, ParseException {
        
        // create the factory
        if (fileFactory == null)
            fileFactory = new FTPFileFactory(system());
        fileFactory.setLocale(listingLocale);
                
        // get the details and parse
        return fileFactory.parse(dir(dirname, true));
    }

    /**
     *  List current directory's contents as an array of strings of
     *  filenames.
     *
     *  @return  an array of current directory listing strings
     */
    public String[] dir()
        throws IOException, FTPException {

        return dir(null, false);
    }

    /**
     *  List a directory's contents as an array of strings of filenames.
     *
     *  @param   dirname  name of directory OR filemask
     *  @return  an array of directory listing strings
     */
    public String[] dir(String dirname)
        throws IOException, FTPException {

        return dir(dirname, false);
    }


    /**
     *  List a directory's contents as an array of strings. A detailed
     *  listing is available, otherwise just filenames are provided.
     *  The detailed listing varies in details depending on OS and
     *  FTP server. Note that a full listing can be used on a file
     *  name to obtain information about a file
     *
     *  @param  dirname  name of directory OR filemask
     *  @param  full     true if detailed listing required
     *                   false otherwise
     *  @return  an array of directory listing strings
     */
    public String[] dir(String dirname, boolean full)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        // set up data channel
        data = control.createDataSocket(connectMode);
        data.setTimeout(timeout);

        // send the retrieve command
        String command = full ? "LIST ":"NLST ";
        if (dirname != null)
            command += dirname;

        // some FTP servers bomb out if NLST has whitespace appended
        command = command.trim();
        FTPReply reply = control.sendCommand(command);

        // check the control response. wu-ftp returns 550 if the
        // directory is empty, so we handle 550 appropriately. Similarly
        // proFTPD returns 450 
        String[] validCodes1 = {"125", "150", "450", "550"};
        lastValidReply = control.validateReply(reply, validCodes1);  

        // an empty array of files for 450/550
        String[] result = new String[0];
        
        // a normal reply ... extract the file list
        String replyCode = lastValidReply.getReplyCode();
        if (!replyCode.equals("450") && !replyCode.equals("550")) {
			// get a character input stream to read data from .
			LineNumberReader in =
				new LineNumberReader(
					 new InputStreamReader(data.getInputStream()));

			// read a line at a time
			Vector lines = new Vector();    
			String line = null;
			while ((line = readLine(in)) != null) {
				lines.addElement(line);
			}
            closeDataSocket();
                
            // check the control response
            String[] validCodes2 = {"226", "250"};
            reply = control.readReply();
            lastValidReply = control.validateReply(reply, validCodes2);

            // empty array is default
            if (!lines.isEmpty()) {
                result = new String[lines.size()];
                lines.copyInto(result);
            }
        }
        else { // 450 or 550 - still need to close data socket
            closeDataSocket();
        }
        return result;
    }

    /**
     * Attempts to read a specified number of bytes from the given 
     * <code>InputStream</code> and place it in the given byte-array.
     * The purpose of this method is to permit subclasses to execute
     * any additional code necessary when performing this operation. 
     * @param in The <code>InputStream</code> to read from.
     * @param chunk The byte-array to place read bytes in.
     * @param chunksize Number of bytes to read.
     * @return Number of bytes actually read.
     * @throws IOException Thrown if there was an error while reading.
     */
    protected int readChunk(BufferedInputStream in, byte[] chunk, int chunksize) 
    	throws IOException {
    		
    	return in.read(chunk, 0, chunksize);
    }
    
    /**
     * Attempts to read a single character from the given <code>InputStream</code>. 
     * The purpose of this method is to permit subclasses to execute
     * any additional code necessary when performing this operation. 
     * @param in The <code>LineNumberReader</code> to read from.
     * @return The character read.
     * @throws IOException Thrown if there was an error while reading.
     */
    protected int readChar(LineNumberReader in) 
    	throws IOException {
    		
    	return in.read();
    }
    
    /**
     * Attempts to read a single line from the given <code>InputStream</code>. 
     * The purpose of this method is to permit subclasses to execute
     * any additional code necessary when performing this operation. 
     * @param in The <code>LineNumberReader</code> to read from.
     * @return The string read.
     * @throws IOException Thrown if there was an error while reading.
     */
    protected String readLine(LineNumberReader in) 
    	throws IOException {
    		
    	return in.readLine();
    }

    /**
     *  Gets the latest valid reply from the server
     *
     *  @return  reply object encapsulating last valid server response
     */
    public FTPReply getLastValidReply() {
        return lastValidReply;
    }


    /**
     *  Get the current transfer type
     *
     *  @return  the current type of the transfer,
     *           i.e. BINARY or ASCII
     */
    public FTPTransferType getType() {
        return transferType;
    }

    /**
     *  Set the transfer type
     *
     *  @param  type  the transfer type to
     *                set the server to
     */
    public void setType(FTPTransferType type)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        // determine the character to send
        String typeStr = FTPTransferType.ASCII_CHAR;
        if (type.equals(FTPTransferType.BINARY))
            typeStr = FTPTransferType.BINARY_CHAR;

        // send the command
        FTPReply reply = control.sendCommand("TYPE " + typeStr);
        lastValidReply = control.validateReply(reply, "200");

        // record the type
        transferType = type;
    }


    /**
     *  Delete the specified remote file
     *
     *  @param  remoteFile  name of remote file to
     *                      delete
     */
    public void delete(String remoteFile)
        throws IOException, FTPException {
    	
    	checkConnection(true);
        String[] validCodes = {"200", "250"};
        FTPReply reply = control.sendCommand("DELE " + remoteFile);
        lastValidReply = control.validateReply(reply, validCodes);
    }


    /**
     *  Rename a file or directory
     *
     * @param from  name of file or directory to rename
     * @param to    intended name
     */
    public void rename(String from, String to)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("RNFR " + from);
        lastValidReply = control.validateReply(reply, "350");

        reply = control.sendCommand("RNTO " + to);
        lastValidReply = control.validateReply(reply, "250");
    }


    /**
     *  Delete the specified remote working directory
     *
     *  @param  dir  name of remote directory to
     *               delete
     */
    public void rmdir(String dir)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("RMD " + dir);

        // some servers return 200,257, technically incorrect but
        // we cater for it ...
        String[] validCodes = {"200", "250", "257"};
        lastValidReply = control.validateReply(reply, validCodes);
    }


    /**
     *  Create the specified remote working directory
     *
     *  @param  dir  name of remote directory to
     *               create
     */
    public void mkdir(String dir)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("MKD " + dir);
        
        // some servers return 200,257, technically incorrect but
        // we cater for it ...
        String[] validCodes = {"200", "250", "257"};
        lastValidReply = control.validateReply(reply, validCodes);
    }


    /**
     *  Change the remote working directory to
     *  that supplied
     *
     *  @param  dir  name of remote directory to
     *               change to
     */
    public void chdir(String dir)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("CWD " + dir);
        lastValidReply = control.validateReply(reply, "250");
    }

    /**
     *  Get modification time for a remote file
     *
     *  @param    remoteFile   name of remote file
     *  @return   modification time of file as a date
     */
    public Date modtime(String remoteFile)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("MDTM " + remoteFile);
        lastValidReply = control.validateReply(reply, "213");

        // parse the reply string ...
        Date ts = tsFormat.parse(lastValidReply.getReplyText(),
                                 new ParsePosition(0));
        return ts;
    }

    /**
     *  Get the current remote working directory
     *
     *  @return   the current working directory
     */
    public String pwd()
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("PWD");
        lastValidReply = control.validateReply(reply, "257");

        // get the reply text and extract the dir
        // listed in quotes, if we can find it. Otherwise
        // just return the whole reply string
        String text = lastValidReply.getReplyText();
        int start = text.indexOf('"');
        int end = text.lastIndexOf('"');
        if (start >= 0 && end > start)
            return text.substring(start+1, end);
        else
            return text;
    }
    
    
    /**
     *  Get the server supplied features
     *
     *  @return   string containing server features, or null if no features or not
     *             supported
     */
    public String[] features()
        throws IOException, FTPException {
        
        checkConnection(true);
        
        FTPReply reply = control.sendCommand("FEAT");
        String[] validCodes = {"211", "500", "502"};
        lastValidReply = control.validateReply(reply, validCodes);
        if (lastValidReply.getReplyCode().equals("211"))
            return lastValidReply.getReplyData();
        else
            throw new FTPException(reply);
    }
    
    /**
     *  Get the type of the OS at the server
     *
     *  @return   the type of server OS
     */
    public String system()
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("SYST");
        lastValidReply = control.validateReply(reply, "215");
        return lastValidReply.getReplyText();
    }

    /**
     *  Get the help text for the specified command
     *
     *  @param  command  name of the command to get help on
     *  @return help text from the server for the supplied command
     */
    public String help(String command)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("HELP " + command);
        String[] validCodes = {"211", "214"};
        lastValidReply = control.validateReply(reply, validCodes);
        return lastValidReply.getReplyText();
    }

    /**
     *  Quit the FTP session
     *
     */
    public void quit()
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        fileFactory = null;
        try {
            FTPReply reply = control.sendCommand("QUIT");
            String[] validCodes = {"221", "226"};
            lastValidReply = control.validateReply(reply, validCodes);
        }
        finally { // ensure we clean up the connection
            control.logout();
            control = null;
        }
    }
}



