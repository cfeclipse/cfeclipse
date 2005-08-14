/**
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
 *        Revision 1.1  2003/11/03 21:19:09  bruceb
 *        progress callback
 *
 *
 */
package com.enterprisedt.net.ftp;

/**
 *  Allows the reporting of progress of the
 *  transfer of data
 *
 *  @author      Bruce Blackshaw
 *  @version     $Revision: 1.1 $
 */
public interface FTPProgressMonitor {

    /**
     * Report the number of bytes transferred so far. This may
     * not be entirely accurate for transferring text files in ASCII
     * mode, as new line representations can be represented differently
     * on different platforms.
     * 
     * @param count  count of bytes transferred
     */
    public void bytesTransferred(long count);
}
