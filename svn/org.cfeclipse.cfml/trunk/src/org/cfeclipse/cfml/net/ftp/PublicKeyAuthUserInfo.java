package org.cfeclipse.cfml.net.ftp;

import com.jcraft.jsch.UserInfo;

public class PublicKeyAuthUserInfo implements UserInfo
{

	private String password;

	public PublicKeyAuthUserInfo(String password) {
	this.password = password;
    }

	public String getPassphrase()
    {
        return password;
    }

    public String getPassword()
    {
        return null;
    }

    public boolean promptPassword(String s)
    {
        return false;
    }

    public boolean promptPassphrase(String s)
    {
        return true;
    }

    public boolean promptYesNo(String s)
    {
        // trust
        return false;
    }

    public void showMessage(String s)
    {
    	System.out.println("Internal Jsch message:" + s);
    }
}
