package com.buswe.dhtcrawler.db.mysql.exception;

public class DhtException extends Exception {

	private static final long serialVersionUID = -3203899399754071316L;

	public DhtException() {
		super();
	}

	public DhtException(String msg) {
        super(msg);
    }

	public DhtException(Exception exception) {
		super(exception);
	}

}
