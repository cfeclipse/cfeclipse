/**
 *
 *  Copyright (C) 2004  Enterprise Distributed Technologies Ltd
 *
 *  www.enterprisedt.com
 *
 *  Change Log:
 *
 *        $Log: not supported by cvs2svn $
 *        Revision 1.2  2004/10/19 16:16:08  bruceb
 *        made test more realistic
 *
 *        Revision 1.1  2004/09/17 14:23:03  bruceb
 *        test harness
 *
 *
 */
package com.enterprisedt.net.ftp.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.FTPFileFactory;
import com.enterprisedt.util.debug.Level;
import com.enterprisedt.util.debug.Logger;

/**
 *  Test harness for testing out listings. Simply copy and
 *  paste a listing into a file and use this test harness to
 *  pinpoint the error 
 * 
 *  @author      Bruce Blackshaw
 *  @version     $Revision: 1.1 $
 */
public class FileParserTest {
    
    /**
     * Standard main()
     * 
     * @param args  standard args - supply filename
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            usage();
        }
        
        Logger log = Logger.getLogger(FileParserTest.class);
        Logger.setLevel(Level.ALL);
        
        String type = args[0];
        String filename = args[1];
        if (!type.equalsIgnoreCase("UNIX") && !type.equalsIgnoreCase("WINDOWS")) {
            usage();
        }
        log.debug("Type=" + type);
        
        Vector lines = new Vector();
        BufferedReader reader = null;
        String line = null;
        try {
            FTPFileFactory ff = new FTPFileFactory(type);
            reader = new BufferedReader(new FileReader(filename));
            while ((line = reader.readLine()) != null) {
                lines.addElement(line);
                System.out.println(line);
            }
            String[] listings = new String[lines.size()];
            lines.copyInto(listings);
            FTPFile[] files = ff.parse(listings);
            for (int i = 0; i < files.length; i++)
                System.out.println(files[i].toString());
        }
        catch (IOException ex) {
            System.out.println("Failed to read file: " + filename);
            ex.printStackTrace();
        } 
        catch (ParseException ex) {
            System.out.println("Failed to parse line '" + line + "'");
            ex.printStackTrace();
        }
        catch (FTPException ex) {
            System.out.println("Failed to parse line '" + line + "'");
            ex.printStackTrace();
        }
    }
    
    /**
     * Usage statement
     *
     */
    private static void usage() {
        System.out.println("Usage: FileParserTest UNIX|WINDOWS filename");
        System.exit(-1);        
    }

}
