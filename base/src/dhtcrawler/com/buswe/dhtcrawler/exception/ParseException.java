package com.buswe.dhtcrawler.exception;

public class ParseException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4424637432835502134L;
	public ParseException() {
		super();
	}

	public ParseException(String msg) {
        super(msg);
    }

	public ParseException(Exception exception) {
		super(exception);
	}
	
	
}
