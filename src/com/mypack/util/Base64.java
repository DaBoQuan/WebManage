package com.mypack.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Base64 {
	/***
	 * encode by Base64
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static String encodeBase64(byte[]input) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
		Method mainMethod= clazz.getMethod("encode", byte[].class);
		mainMethod.setAccessible(true);
		Object retObj=mainMethod.invoke(null, new Object[]{input});
		return (String)retObj;
	}
	/***
	 * decode by Base64
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static String decodeBase64(String input) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
		Method mainMethod= clazz.getMethod("decode", String.class);
		mainMethod.setAccessible(true);
		Object retObj=mainMethod.invoke(null, input);
		return new String((String) retObj);
	}
	public static String str2HexStr(String str) {

	    char[] chars = "0123456789ABCDEF".toCharArray();
	    StringBuilder sb = new StringBuilder("");
	    byte[] bs = str.getBytes();
	    int bit;

	    for (int i = 0; i < bs.length; i++) {
	      bit = (bs[i] & 0x0f0) >> 4;
	      sb.append(chars[bit]);
	      bit = bs[i] & 0x0f;
	      sb.append(chars[bit]);
	      //sb.append(' ');
	    }
	    return sb.toString().trim();
	  }
}
