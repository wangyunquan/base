package com.buswe.dhtcrawler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KLog {
	private static boolean debug = true;
	  protected  static Logger logger = LoggerFactory.getLogger(KLog.class);
	public static void println(Object text) {
		if (debug) {
			logger.debug(text.toString());
		}
	}
}
