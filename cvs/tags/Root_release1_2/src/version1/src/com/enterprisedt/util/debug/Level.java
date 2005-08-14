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
 *    $Log: not supported by cvs2svn $
 *    Revision 1.5  2004/08/31 13:54:49  bruceb
 *    remove compile warnings
 *
 *    Revision 1.4  2004/08/16 21:08:08  bruceb
 *    made cvsids public
 *
 *    Revision 1.3  2004/06/25 11:52:26  bruceb
 *    fixed logging bug
 *
 *    Revision 1.2  2004/05/08 21:14:20  bruceb
 *    string -> level
 *
 *    Revision 1.1  2004/05/01 16:55:42  bruceb
 *    first cut
 *
 *
 */
package com.enterprisedt.util.debug;

/**
 *  Simple debug level class. Uses the same interface (but
 *  not implementation) as log4j, so that the debug
 *  classes could be easily replaced by log4j (by changing 
 *  imports - too dangerous to rely on class path and using
 *  the same package names)
 *
 *  @author      Bruce Blackshaw
 *  @version     $Revision: 1.1 $
 */
public class Level {
    
    /**
     *  Revision control id
     */
    public static String cvsId = "@(#)$Id: Level.java,v 1.1 2004-11-09 04:44:29 smilligan Exp $";
    
    final static int OFF_INT = -1;
    
    final private static String OFF_STR = "OFF";
        
    final static int FATAL_INT = 0;
    
    final private static String FATAL_STR = "FATAL";
    
    final static int ERROR_INT = 1;
    
    final private static String ERROR_STR = "ERROR";
    
    final static int WARN_INT = 2;
    
    final private static String WARN_STR = "WARN";
    
    final static int INFO_INT = 3;
    
    final private static String INFO_STR = "INFO";
    
    final static int DEBUG_INT = 4;
    
    final private static String DEBUG_STR = "DEBUG";
        
    final static int ALL_INT = 10;
    
    final private static String ALL_STR = "ALL";
    
    final static int LEVEL_COUNT = 5;
    
    /**
     * Off level
     */
    public static Level OFF = new Level(OFF_INT, OFF_STR);
    
    /**
     * Fatal level
     */
    public static Level FATAL = new Level(FATAL_INT, FATAL_STR);    

    /**
     * OFF level
     */
    public static Level ERROR = new Level(ERROR_INT, ERROR_STR);

    /**
     * Warn level
     */
    public static Level WARN = new Level(WARN_INT, WARN_STR);
    
    /**
     * Info level
     */
    public static Level INFO = new Level(INFO_INT, INFO_STR);
    
    /**
     * Debug level
     */
    public static Level DEBUG = new Level(DEBUG_INT, DEBUG_STR);
    
    /**
     * All level
     */
    public static Level ALL = new Level(ALL_INT, ALL_STR);    
    
    /**
     * The level's integer value
     */
    private int level = OFF_INT;
    
    /**
     * The level's string representation
     */
    private String string;
    
    /**
     * Private constructor so no-one outside the class can
     * create any more instances
     * 
     * @param level     level to set this instance at
     * @param string    string representation
     */
    private Level(int level, String string) {
        this.level = level;
        this.string = string;
    }
    
    /**
     * Get integer log level
     * 
     * @return log level
     */
    int getLevel() {
        return level;
    }
    
    /**
     * Is this level greater or equal to the supplied level
     * 
     * @param l      level to test against
     * @return  true if greater or equal to, false if less than
     */
    boolean isGreaterOrEqual(Level l) {
        if (this.level >= l.level)
            return true;
        return false;
    }
    
    /**
     * Get level from supplied string
     * 
     * @param level level as a string
     * @return level object or null if not found
     */
    static Level getLevel(String level) {
        if (OFF.toString().equalsIgnoreCase(level))
            return OFF;
        if (FATAL.toString().equalsIgnoreCase(level))
            return FATAL;
        if (ERROR.toString().equalsIgnoreCase(level))
            return ERROR;
        if (WARN.toString().equalsIgnoreCase(level))
            return WARN;
        if (INFO.toString().equalsIgnoreCase(level))
            return INFO;
        if (DEBUG.toString().equalsIgnoreCase(level))
            return DEBUG;
        if (ALL.toString().equalsIgnoreCase(level))
            return ALL;
        return null;
    }
    
    /**
     * String representation
     * 
     * @return string
     */
    public String toString() {
        return string;
    }

}
