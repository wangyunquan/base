package com.buswe.dht.exception;

import java.lang.Thread.UncaughtExceptionHandler;

public abstract class ErrHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		caughtEnd();
	}
	public abstract void caughtEnd();

}
