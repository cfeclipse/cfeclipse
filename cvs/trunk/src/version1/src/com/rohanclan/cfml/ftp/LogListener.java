/*
 * Created on Nov 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.ftp;

import java.util.ArrayList;
import java.util.Iterator;
import com.enterprisedt.net.ftp.FTPMessageListener;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LogListener implements FTPMessageListener {
        
    
    	private static ArrayList listeners = new ArrayList();
        /**
         * Log of messages
         */
        private StringBuffer log = new StringBuffer();
        
        /**
         * Log an FTP command being sent to the server
         * 
         * @param cmd   command string
         */
        public void logCommand(String cmd) {
            log.append(cmd).append("\n");
            Iterator i = listeners.iterator();
            while (i.hasNext()) {
                Object o = i.next();
                if (o instanceof FTPMessageListener) {
                    try {
                        ((FTPMessageListener)o).logCommand(cmd);
                    }
                    catch (Exception e) {
                        listeners.remove(o);
                     }
                }
            }
        }
        
        /**
         * Log an FTP reply being sent back to the client
         * 
         * @param reply   reply string
         */
        public void logReply(String reply) {
            log.append(reply).append("\n");
            Iterator i = listeners.iterator();
            while (i.hasNext()) {
                Object o = i.next();
                if (o instanceof FTPMessageListener) {
                    try {
                        ((FTPMessageListener)o).logReply(reply);
                    }
                    catch (Exception e) {
                        listeners.remove(o);
                     }
                }
            }
        }
        
        /**
         * Get the log of messages
         * 
         * @return  message log as a string
         */
        public String getLog() {
            return log.toString();
        }
        
        /**
         * Clear the log of all messages
         */
        public void clearLog() {
            log = new StringBuffer();
        }
        
        public static void addListener(FTPMessageListener listener) {
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
         }
        
        public static void removeListener(FTPMessageListener listener) {
            listeners.remove(listener);
        }

    }
