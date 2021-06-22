package fr.azefgh456.acore.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflexionUtils {

	
	/*
	 * Methode Reflexion
	 * 
	 */
	
	public static Method getMethod(Object obj, String name) throws Exception{
		Method method = obj.getClass().getDeclaredMethod(name, new Class[0]);
		if(!method.isAccessible()) method.setAccessible(true);
		
		return method;
	}

	public static Method getMethod(Object obj, String name, Class<?>... clazz) throws Exception{
		Method method = obj.getClass().getDeclaredMethod(name, clazz);
		if(!method.isAccessible()) method.setAccessible(true);
		
		return method;
	}
	
	public static Object invokeMethod(Method method, Object obj, Object... objs) throws Exception{
		return method.invoke(obj, objs);
	}
	
	  public static Object invokeMethod(Object obj, String name) throws Exception { return invokeMethod(obj, name, true); }
	  public static Object invokeMethod(Object obj, String name, boolean declared) throws Exception {
	    Method met = declared ? obj.getClass().getDeclaredMethod(name, new Class[0]) : obj.getClass().getMethod(name, new Class[0]);
	    return met.invoke(obj, new Object[0]);
	  }
	
	/*
	 * Filed Reflexion
	 * 
	 */
	  public static Object getFieldObject(Object object, Field field) throws Exception { return field.get(object); }
	  public static Field getField(Object clazz, String name) throws Exception { return getField(clazz, name, true); }
	  public static Field getField(Object clazz, String name, boolean declared) throws Exception { return getField(clazz.getClass(), name, declared); }
	  public static Field getField(Class<?> clazz, String name) throws Exception { return getField(clazz, name, true); }
	  public static Field getField(Class<?> clazz, String name, boolean declared) throws Exception {
	    Field field = declared ? clazz.getDeclaredField(name) : clazz.getField(name);
	    if (!field.isAccessible()) {
	      field.setAccessible(true);
	    }
	    
	    return field;
	  }
	
}
