package com.konka.dhtsearch.util;

public class KLog {
	private static boolean debug = true;

	public static void println(Object text) {
		if (debug) {
			System.out.println(text);
		}
	}
}
