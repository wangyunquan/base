package com.buswe.work.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SimpleTest {

	public static void main(String[] args) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
	System.out.println(formatter.format(new Date(new Long(1424912597)* 1000)));
	System.out.println(1424912597* 10);
	System.out.println(System.currentTimeMillis());

	}

}
