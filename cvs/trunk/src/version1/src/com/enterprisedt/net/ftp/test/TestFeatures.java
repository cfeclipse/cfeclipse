/**
 *
 *  edtFTPj
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
 *        Revision 1.2  2004/09/18 14:28:23  bruceb
 *        rename test method
 *
 *        Revision 1.1  2004/08/31 10:44:17  bruceb
 *        test code
 *
 *
 */

package com.enterprisedt.net.ftp.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.enterprisedt.net.ftp.FTPException;

/**
 *  Test general methods such as site() and quote()
 *
 *  @author         Bruce Blackshaw
 *  @version        $Revision: 1.1 $
 */
public class TestFeatures extends FTPTestCase {

    /**
     *  Revision control id
     */
    public static String cvsId = "@(#)$Id: TestFeatures.java,v 1.1 2004-11-09 04:44:29 smilligan Exp $";

    /**
     *  Get name of log file
     *
     *  @return name of file to log to
     */
    protected String getLogName() {
        return "TestFeatures.log";
    }

    /**
     *  Test features() command
     */
    public void testFeatures() throws Exception {
        
        connect();
        login();

        // system
        try {
            String[] features = ftp.features();
            for (int i = 0; i < features.length; i++)
                log.debug("Feature: " + features[i]);
        }
        catch (FTPException ex) {
            log.warn("FEAT not implemented");
        }
        
        // complete
        ftp.quit();
    }

    /**
     *  Automatic test suite construction
     *
     *  @return  suite of tests for this class
     */
    public static Test suite() {
        return new TestSuite(TestFeatures.class);
    }

    /**
     *  Enable our class to be run, doing the
     *  tests
     */
    public static void main(String[] args) {       
        junit.textui.TestRunner.run(suite());
    } 

}

