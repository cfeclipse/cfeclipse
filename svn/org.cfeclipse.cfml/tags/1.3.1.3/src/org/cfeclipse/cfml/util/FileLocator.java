package org.cfeclipse.cfml.util;

import java.lang.reflect.Method;
import java.net.URL;

public class FileLocator
{

	public static URL LocateURL(URL baseURL, String fileName)
	{
		URL configurl = null;
		try
		{
			Class fl = Class.forName("org.eclipse.core.runtime.FileLocator");
			if (fl != null)
			{
				Method m = fl.getMethod("toFileURL", new Class[]{URL.class});
				URL local = (URL)m.invoke(fl, new Object[]{baseURL});
			
				m = fl.getMethod("resolve", new Class[]{URL.class});
				configurl = (URL)m.invoke(fl, new Object[]{new URL(local, fileName)});
			}
		} catch (Throwable t)
		{
			configurl = null;
		}
		
		if (configurl == null)
		{
			// do it the 3.1 way, because we couldn't find FileLocator
			try
			{
				Class p = Class.forName("org.eclipse.core.runtime.Platform");
				if (p != null)
				{
					Method m = p.getMethod("asLocalURL", new Class[]{URL.class});
					URL local = (URL)m.invoke(p, new Object[]{baseURL});
				
					m = p.getMethod("resolve", new Class[]{URL.class});
					configurl = (URL)m.invoke(p, new Object[]{new URL(local, fileName)});
				}
			} catch (Throwable tr)
			{
				configurl = null;
			}
		}
		return configurl;
	}
	
}
