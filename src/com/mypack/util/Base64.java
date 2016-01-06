package com.mypack.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Base64 {
	/***
	 * encode by Base64
	 */
	public static String encodeBase64(byte[]input){
		try {
			Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
			Method mainMethod= clazz.getMethod("encode", byte[].class);
			mainMethod.setAccessible(true);
			 Object retObj=mainMethod.invoke(null, new Object[]{input});
			 return (String)retObj;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/***
	 * decode by Base64
	 */
	public static String decodeBase64(String input){
		try {
			Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
			Method mainMethod= clazz.getMethod("decode", String.class);
			mainMethod.setAccessible(true);
			 Object retObj=mainMethod.invoke(null, input);
			 return new String((String) retObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
