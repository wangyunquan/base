/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.konka.dhtsearch.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.konka.dhtsearch.exception.DownLoadException;
import com.konka.dhtsearch.util.Request.Method;

/**
 * An {@link HttpStack} based on {@link HttpURLConnection}.
 */
public class HttpUrlUtils {

	private static final String HEADER_CONTENT_TYPE = "Content-Type";

	public InputStream performRequest(Request request) throws DownLoadException, IOException {
		String url = request.getUrl();
		HashMap<String, String> map = new HashMap<String, String>();
		map.putAll(request.getHeaders());

		URL parsedUrl = new URL(url);
		HttpURLConnection connection = openConnection(parsedUrl, request);
		for (String headerName : map.keySet()) {// 添加header
			connection.addRequestProperty(headerName, map.get(headerName));
		}
		setConnectionParametersForRequest(connection, request);
		// Initialize HttpResponse with data from the HttpURLConnection.
		int responseCode = connection.getResponseCode();
		if (responseCode != HttpURLConnection.HTTP_OK) {
			// -1 is returned by getResponseCode() if the response code could
			// not be retrieved.
			// Signal to the caller that something was wrong with the
			// connection.
			throw new DownLoadException("download is fails");
		}

		return connection.getInputStream();
	}

	/**
	 * Create an {@link HttpURLConnection} for the specified {@code url}.
	 */
	protected HttpURLConnection createConnection(URL url) throws IOException {
		return (HttpURLConnection) url.openConnection();
	}

	/**
	 * Opens an {@link HttpURLConnection} with parameters.
	 * 
	 * @param url
	 * @return an open connection
	 * @throws IOException
	 */
	private HttpURLConnection openConnection(URL url, Request request) throws IOException {
		HttpURLConnection connection = createConnection(url);

		int timeoutMs = request.getTimeoutMs();
		connection.setConnectTimeout(timeoutMs);
		connection.setReadTimeout(timeoutMs);
		connection.setUseCaches(false);
		connection.setDoInput(true);
		return connection;
	}

	/* package */
	static void setConnectionParametersForRequest(HttpURLConnection connection, Request request) throws IOException {
		switch (request.getMethod()) {
			case Method.DEPRECATED_GET_OR_POST:
				// This is the deprecated way that needs to be handled for backwards
				// compatibility.
				// If the request's post body is null, then the assumption is that
				// the request is
				// GET. Otherwise, it is assumed that the request is a POST.
				byte[] postBody = request.getBody();
				if (postBody != null) {
					// Prepare output. There is no need to set Content-Length
					// explicitly,
					// since this is handled by HttpURLConnection using the size of
					// the prepared
					// output stream.
					connection.setDoOutput(true);
					connection.setRequestMethod("POST");
					connection.addRequestProperty(HEADER_CONTENT_TYPE, request.getBodyContentType());
					DataOutputStream out = new DataOutputStream(connection.getOutputStream());
					out.write(postBody);
					out.close();
				}
				break;
			case Method.GET:
				// Not necessary to set the request method because connection
				// defaults to GET but
				// being explicit here.
				connection.setRequestMethod("GET");
				break;
			case Method.DELETE:
				connection.setRequestMethod("DELETE");
				break;
			case Method.POST:
				connection.setRequestMethod("POST");
				addBodyIfExists(connection, request);
				break;
			case Method.PUT:
				connection.setRequestMethod("PUT");
				addBodyIfExists(connection, request);
				break;
			default:
				throw new IllegalStateException("Unknown method type.");
		}
	}

	private static void addBodyIfExists(HttpURLConnection connection, Request request) throws IOException {
		byte[] body = request.getBody();
		if (body != null) {
			connection.setDoOutput(true);
			connection.addRequestProperty(HEADER_CONTENT_TYPE, request.getBodyContentType());
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.write(body);
			out.close();
		}
	}
}
