package org.cfeclipse.cfml.util;

import java.lang.reflect.Method;

//because Assert changed packages, wrap it in some nifty introspection stuff

public class EclipseAssert
{
	public EclipseAssert()
	{
		// not intended. the methods in this class are static.
	}

	protected static Class getAssertClassObject()
	{
		Class result = null;
		try
		{
			result = Class.forName("org.eclipse.core.internal.utils.Assert");
		}
		catch (ClassNotFoundException e)
		{
			try
			{
				result = Class.forName("org.eclipse.core.runtime.Assert");
			}
			catch (ClassNotFoundException ex)
			{
				// this should not happen, one of the two above will exist
				result = null;
			}
		}
		return result;
	}
	
	protected static Object invokeMethod(String methodName, Class[] paramTypes, Object[] paramValues)
	{
		Class a = getAssertClassObject();
		Object result = null;
		if (a != null)
		{
			try
			{
				Method m = a.getMethod(methodName, paramTypes);
				if (m != null)
				{
					result = m.invoke(a, paramValues);
				}
			} catch (Throwable t)  //NoSuchMethodError and InvocationTargetException, but we really don't care what in our case
			{
				result = null;
			}
		}
		
		return result;
	}

	public static boolean isLegal(boolean expression)
	{
		Object result = invokeMethod("isLegal", new Class[]{Boolean.class}, new Object[]{new Boolean(expression)});
		return ((Boolean)result).booleanValue();
	}

	public static boolean isLegal(boolean expression, String message)
	{
		Object result = invokeMethod("isLegal", new Class[]{Boolean.class, String.class}, new Object[]{new Boolean(expression), message});
		return ((Boolean)result).booleanValue();
	}

	public static void isNotNull(Object object)
	{
		invokeMethod("isNotNull", new Class[]{Object.class}, new Object[]{object});
	}

	public static void isNotNull(Object object, String message)
	{
		invokeMethod("isNotNull", new Class[]{Object.class, String.class}, new Object[]{object, message});
	}

	public static boolean isTrue(boolean expression)
	{
		Object result = invokeMethod("isTrue", new Class[]{Boolean.class}, new Object[]{new Boolean(expression)});
		return ((Boolean)result).booleanValue();
	}

	public static boolean isTrue(boolean expression, String message)
	{
		Object result = invokeMethod("isTrue", new Class[]{Boolean.class, String.class}, new Object[]{new Boolean(expression), message});
		return ((Boolean)result).booleanValue();
	}
}
