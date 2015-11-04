package com.konka.dhtsearch.db;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * DB工具类
 * 
 * @author cgpllx1@qq.com (www.kubeiwu.com)
 * @date 2014-8-18
 */
public class DbUtil {

	public static final <T> boolean isEmpty(List<T> lists) {
		if (lists == null || lists.size() == 0) {
			return true;
		}
		return false;
	}

	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Method getFieldGetMethod(Class<?> clazz, Field f) {
		String fn = f.getName();
		Method m = null;
		if (f.getType() == boolean.class) {
			m = getBooleanFieldGetMethod(clazz, fn);
		}
		if (m == null) {
			m = getFieldGetMethod(clazz, fn);
		}
		return m;
	}

	public static Method getFieldSetMethod(Class<?> clazz, Field f) {
		String fn = f.getName();
		String mn = "set" + fn.substring(0, 1).toUpperCase() + fn.substring(1);
		try {
			return clazz.getDeclaredMethod(mn, f.getType());
		} catch (NoSuchMethodException e) {
			if (f.getType() == boolean.class) {
				return getBooleanFieldSetMethod(clazz, f);
			}
		}
		return null;
	}

	public static Method getBooleanFieldSetMethod(Class<?> clazz, Field f) {
		String fn = f.getName();
		String mn = "set" + fn.substring(0, 1).toUpperCase() + fn.substring(1);
		if (isISStart(f.getName())) {
			mn = "set" + fn.substring(2, 3).toUpperCase() + fn.substring(3);
		}
		try {
			return clazz.getDeclaredMethod(mn, f.getType());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 布尔值 对外提供的set方法是isXxx
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static Method getBooleanFieldGetMethod(Class<?> clazz, String fieldName) {
		String mn = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		if (isISStart(fieldName)) {
			mn = fieldName;
		}
		try {
			return clazz.getDeclaredMethod(mn);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static boolean isISStart(String fieldName) {
		if (fieldName == null || fieldName.trim().length() == 0)
			return false;
		// is开头，并且is之后第一个字母是大写 比如 isOk
		return fieldName.startsWith("is") && !Character.isLowerCase(fieldName.charAt(2));
	}

	public static Method getFieldGetMethod(Class<?> clazz, String fieldName) {
		String mn = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		try {
			return clazz.getDeclaredMethod(mn);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date stringToDateTime(String strDate) {
		if (strDate != null) {
			try {
				return SDF.parse(strDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
