/**
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
 *    $Log: Logger.java,v $
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
 *    Revision 1.7  2004/10/20 21:03:09  bruceb
 *    catch SecurityExceptions
 *
 *    Revision 1.6  2004/09/17 12:27:11  bruceb
 *    1.1 compat
 *
 *    Revision 1.5  2004/08/31 13:54:50  bruceb
 *    remove compile warnings
 *
 *    Revision 1.4  2004/08/16 21:08:08  bruceb
 *    made cvsids public
 *
 *    Revision 1.3  2004/06/25 11:52:26  bruceb
 *    fixed logging bug
 *
 *    Revision 1.2  2004/05/08 21:13:51  bruceb
 *    renamed property
 *
 *    Revision 1.1  2004/05/01 16:55:42  bruceb
 *    first cut
 *
 *
 */
package com.enterprisedt.util.debug;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *  Logger class that mimics log4j Logger class. If log4j integration
 *  is desired, the "edtftp.log4j" property should be set to "true" and
 *  log4j classes must be in the classpath
 *
 *  @author      Bruce Blackshaw
 *  @version     $Revision: 1.1 $
 */
public class Logger {
    
    /**
     *  Revision control id
     */
    public static String cvsId = "@(#)$Id: Logger.java,v 1.1 2004/11/09 04:44:29 smilligan Exp $";
    
    /**
     * Level of all loggers
     */
    private static Level globalLevel;
    
    /**
     * Timestamp formatter
     */
    private SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy HH:mm:ss.S");
    
    /**
     * Hash of all loggers that exist
     */
    private static Hashtable loggers = new Hashtable(10);
    
    /**
     * Vector of all appenders
     */
    private static Vector appenders = new Vector(2);
    
    /**
     * Shall we use log4j or not?
     */
    private boolean useLog4j = false;
    
    /**
     * Timestamp
     */
    private Date ts = new Date();
    
    /**
     * Class name for this logger
     */
    private String clazz;
    
    /**
     *  Log4j methods
     */
    private Method[][] methods = null;
    
    /**
     * Logger log4j object
     */
    private Object logger = null;
    
    /**
     * Arg arrays for use in invoke
     */
    private Object[] argsPlain = new Object[1];
    
    /**
     * Arg arrays for use in invoke
     */
    private Object[] argsThrowable = new Object[2];
    
    /**
     * Determine the logging level
     */
    static {
        String level = Level.OFF.toString();
        try {
            System.getProperty("edtftp.log.level", Level.OFF.toString());
        }
        catch (SecurityException ex) {
            System.out.println("Could not read property 'edtftp.log.level' due to security permissions");
        }

        globalLevel = Level.getLevel(level);
    }
    
    /**
     * Constructor
     * 
     * @param clazz     class this logger is for
     * @param uselog4j  true if using log4j
     */
    private Logger(String clazz, boolean uselog4j) {
        this.clazz = clazz;
        this.useLog4j = uselog4j;
        if (uselog4j)
            setupLog4j();
    }
    
    /**
     * Attempt to set up log4j logging. Of course, the classes
     * must be in the classpath
     */
    private void setupLog4j() {
        methods = new Method[Level.LEVEL_COUNT][2];
        try {
            Class log4jLogger = Class.forName("org.apache.log4j.Logger");
            Class[] args = { String.class };
            
            // get static logger method & use to get our logger
            Method getLogger = log4jLogger.getMethod("getLogger", args);
            Object[] invokeArgs = {clazz};
            logger = getLogger.invoke(null, invokeArgs);
            
            // now get the logger's methods and store them
            Class[] plainArgs = {Object.class};
            Class[] throwableArgs = {Object.class,Throwable.class};
            methods[Level.FATAL_INT][0] = log4jLogger.getMethod("fatal", plainArgs);
            methods[Level.FATAL_INT][1] = log4jLogger.getMethod("fatal", throwableArgs);
            methods[Level.ERROR_INT][0] = log4jLogger.getMethod("error", plainArgs);
            methods[Level.ERROR_INT][1] = log4jLogger.getMethod("error", throwableArgs);
            methods[Level.WARN_INT][0] = log4jLogger.getMethod("warn", plainArgs);
            methods[Level.WARN_INT][1] = log4jLogger.getMethod("warn", throwableArgs);
            methods[Level.INFO_INT][0] = log4jLogger.getMethod("info", plainArgs);
            methods[Level.INFO_INT][1] = log4jLogger.getMethod("info", throwableArgs);
            methods[Level.DEBUG_INT][0] = log4jLogger.getMethod("debug", plainArgs);
            methods[Level.DEBUG_INT][1] = log4jLogger.getMethod("debug", throwableArgs);
        } 
        catch (Exception ex) {
            useLog4j = false;
            error("Failed to initialize log4j logging", ex);
        } 
    }

    /**
     * Set all loggers to this level
     * 
     * @param level  new level
     */
    public static void setLevel(Level level) {
        globalLevel = level;
    }
    
    /**
     * Get a logger for the supplied class
     * 
     * @param clazz    full class name
     * @return  logger for class
     */
    public static Logger getLogger(Class clazz) {
        return getLogger(clazz.getName());
    }
           
    /**
     * Get a logger for the supplied class
     * 
     * @param clazz    full class name
     * @return  logger for class
     */
    public static Logger getLogger(String clazz) {
        Logger logger = (Logger)loggers.get(clazz);
        if (logger == null) {
            boolean useLog4j = false;
            try {
                String log4j = System.getProperty("edtftp.log.log4j");
                if (log4j != null && log4j.equalsIgnoreCase("true")) {
                    useLog4j = true;
                }
            }
            catch (SecurityException ex) {
                System.out.println("Could not read property 'edtftp.log.log4j' due to security permissions");
            }
            logger = new Logger(clazz, useLog4j);
            loggers.put(clazz, logger);
        }
        return logger;
    }
    
    /**
     * Add an appender to our list
     * 
     * @param newAppender
     */
    public static void addAppender(Appender newAppender) {
        appenders.addElement(newAppender);
    }
    
    /**
     * Close all appenders
     */
    public static void shutdown() {
        for (int i = 0; i < appenders.size(); i++) {
            Appender a = (Appender)appenders.elementAt(i);
            a.close();
        }        
    }
    
    /**
     * Log a message 
     * 
     * @param level     log level
     * @param message   message to log
     * @param t         throwable object
     */
    public void log(Level level, String message, Throwable t) {
        if (useLog4j)
            log4jLog(level, message, t);
        else if (globalLevel.isGreaterOrEqual(level))
            ourLog(level, message, t);
    }
    
    /**
     * Log a message to log4j
     * 
     * @param level     log level
     * @param message   message to log
     * @param t         throwable object
     */
    private void log4jLog(Level level, String message, Throwable t) {
        
        // set up arguments
        Object[] args = null;
        int pos = -1;
        if (t == null) {
            args = argsPlain;
            pos = 0;
        }
        else {
            args = argsThrowable;
            args[1] = t;
            pos = 1;
        }
        args[0] = message;
        
        // retrieve the correct method
        Method method = methods[level.getLevel()][pos];
        
        // and invoke the method
        try {
            method.invoke(logger, args);
        } 
        catch (Exception ex) { // there's a few, we don't care what they are
            ourLog(Level.ERROR, "Failed to invoke log4j method", ex);
            ourLog(level, message, t);
        }
    }

    /**
     * Log a message to our logging system
     * 
     * @param level     log level
     * @param message   message to log
     * @param t         throwable object
     */
    private void ourLog(Level level, String message, Throwable t) {
        ts.setTime(System.currentTimeMillis());
        String stamp = format.format(ts);
        StringBuffer buf = new StringBuffer(level.toString());
        buf.append(" [").append(clazz).append("] ").append(stamp).
        append(" : ").append(message);
        if (appenders.size() == 0) { // by default to stdout
            System.out.println(buf.toString());
            if (t != null) {
                t.printStackTrace(System.out);
            }
        }
        else {
            for (int i = 0; i < appenders.size(); i++) {
                Appender a = (Appender)appenders.elementAt(i);
                a.log(buf.toString());
                if (t != null) {
                    a.log(t);
                }
            }
        }
    }
        
    /**
     * Log an info level message
     * 
     * @param message   message to log
     */
    public void info(String message)  {
        log(Level.INFO, message, null); 
    }
    
    /**
     * Log an info level message
     * 
     * @param message   message to log
     * @param t         throwable object
     */
    public void info(String message, Throwable t)  {
        log(Level.INFO, message, t); 
    }

    /**
     * Log a warning level message
     * 
     * @param message   message to log
     */
    public void warn(String message)  {
        log(Level.WARN, message, null); 
    }
    
    /**
     * Log a warning level message
     * 
     * @param message   message to log
     * @param t         throwable object
     */
    public void warn(String message, Throwable t)  {
        log(Level.WARN, message, t); 
    }
    
    /**
     * Log an error level message
     * 
     * @param message   message to log
     */
    public void error(String message)  {
        log(Level.ERROR, message, null);   
    }
    
    /**
     * Log an error level message
     * 
     * @param message   message to log
     * @param t         throwable object
     */
    public void error(String message, Throwable t)  {
        log(Level.ERROR, message, t);   
    } 
    
    /**
     * Log a fatal level message
     * 
     * @param message   message to log
     */
    public void fatal(String message)  {
        log(Level.FATAL, message, null); 
    }

    /**
     * Log a fatal level message
     * 
     * @param message   message to log
      * @param t         throwable object
    */
    public void fatal(String message, Throwable t)  {
        log(Level.FATAL, message, t); 
    }
    
    /**
     * Log a debug level message
     * 
     * @param message   message to log
     */
    public void debug(String message)  {
        log(Level.DEBUG, message, null); 
    }
    

    /**
     * Log a debug level message
     * 
     * @param message   message to log
     * @param t         throwable object
     */
    public void debug(String message, Throwable t)  {
        log(Level.DEBUG, message, t); 
    }
    
    /**
     * Is logging enabled for the supplied level?
     * 
     * @param level   level to test for
     * @return true   if enabled
     */
    public boolean isEnabledFor(Level level) {
        if (globalLevel.isGreaterOrEqual(level))
            return true;
        return false;
    }
    
    /**
     * Is logging enabled for the supplied level?
     * 
     * @return true if enabled
     */
    public boolean isDebugEnabled() {
        return isEnabledFor(Level.DEBUG);
    }
    
    /**
     * Is logging enabled for the supplied level?
     * 
     * @return true if enabled
     */
    public boolean isInfoEnabled()  {
        return isEnabledFor(Level.INFO);
    }
}
