package com.buswe.dhtcrawler.exception;

public class DownLoadException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4424637432835502134L;
	public DownLoadException() {
		super();
	}

	public DownLoadException(String msg) {
        super(msg);
    }

	public DownLoadException(Exception exception) {
		super(exception);
	}
	
	
}
