package com.konka.dhtsearch.parser;

 
public class TorrentException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4447936700208361828L;
	public TorrentException(String message, Throwable exception) {
        super(message, exception);
    }
    public TorrentException(Throwable ex) {
        super(ex);
    }
    public TorrentException(String message) {
        super(message);
    }
}