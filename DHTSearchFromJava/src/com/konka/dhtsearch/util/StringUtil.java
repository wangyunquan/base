package com.konka.dhtsearch.util;

public class StringUtil {
	public static boolean isEmpty(String text){
		if(text==null||text.endsWith("")){
			return true;
		}
		return false;
	}
}
