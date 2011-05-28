package org.cfeclipse.cfml.images;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.swt.dnd.TransferData;

public class ImageTransferWrapper {

	static Class<?> transfer;

	static {
		try {
			transfer = Class.forName("org.eclipse.swt.dnd.ImageTransfer");
		} catch (ClassNotFoundException e) {

		}

	}

	public static boolean isAvalable() {
		return transfer != null;
	}

	public static Object getInstance() {
		try {
			Method method = transfer.getMethod("getInstance");
			return method.invoke(null);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		return null;
	}

	public static boolean isSupportedType(TransferData d) {
		try {
			Method method = transfer.getMethod("isSupportedType", new Class[] { TransferData.class });
			return (Boolean) method.invoke(getInstance(), d);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		return false;
	}
}
