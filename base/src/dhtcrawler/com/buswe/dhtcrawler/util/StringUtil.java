package com.buswe.dhtcrawler.util;

public class StringUtil {
	public static boolean isEmpty(String text){
		if(text==null||text.endsWith("")){
			return true;
		}
		return false;
	}
}
