package com.konka.dhtsearch.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public final class UrlUtils {
	private UrlUtils() {
	}

	private static final int CONNECT_TIMEOUT = 10 * 1000; // to prevent Starvation/Livelock on the webserver
	private static final int READ_TIMEOUT = 10 * 1000; // to prevent Starvation/Livelock on the webserver

	/**
	 * Retrieve a document from the specified URL using the HTTP request headers specified in the Map. Setting the connect and read timeout for the URLConnection is the obligation of the method that created it. That philosophy keeps this method side-effect free.
	 */
	public static InputStream getByteArray(final URLConnection con) throws IOException {
		Validate.notNull(con);

		final InputStream is = con.getInputStream();
		Validate.notNull(is);
		return is;
	}

	/** Retrieve a document from the specified URL */
	public static InputStream getByteArray(final URL url) throws IOException {
		Validate.notNull(url);

		final URLConnection con = url.openConnection();
		Validate.notNull(con);

		con.setConnectTimeout(CONNECT_TIMEOUT); // to prevent Starvation/Livelock on the webserver
		con.setReadTimeout(READ_TIMEOUT); // to prevent Starvation/Livelock on the webserver

		return getByteArray(con);
	}

	/** Retrieve a document from the specified URL using the HTTP request headers specified in the Map. */
	public static InputStream getByteArray(final URLConnection con, final Map<String, String> headers) throws IOException {
		Validate.notNull(con);
		Validate.notEmpty(headers);

		for (Map.Entry<String, String> me : headers.entrySet()) {
			final String name = me.getKey();
			Validate.notEmpty(name);

			final String value = me.getValue();
			Validate.notEmpty(value);

			con.setRequestProperty(name, value);
		}

		return getByteArray(con);
	}

	/** Retrieve a document from the specified URL using the HTTP request headers specified in the Map. */
	public static InputStream getByteArray(final URL url, final Map<String, String> headers) throws IOException {
		Validate.notNull(url);
		Validate.notEmpty(headers);

		final URLConnection con = url.openConnection();
		Validate.notNull(con);

		con.setConnectTimeout(CONNECT_TIMEOUT); // to prevent Starvation/Livelock on the webserver
		con.setReadTimeout(READ_TIMEOUT); // to prevent Starvation/Livelock on the webserver

		return getByteArray(con, headers);
	}

}
