package com.konka.dhtsearch;

import java.util.Random;

public class AppManager {
	private static AppManager appManager;

	private static KeyFactory keyFactory;

	public static AppManager getInstance() {
		if (appManager == null) {
			init();
		}
		return appManager;
	}

	public static KeyFactory getKeyFactory() {
		return keyFactory;
	}

	private AppManager() {
		super();
		try {
			keyFactory = new RandomKeyFactory(20, new Random(), "SHA-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void init() {
		appManager = new AppManager();
	}
}
