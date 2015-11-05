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

package com.buswe.dhtcrawler.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

public class Request {

	private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
	private static final int DEFAULT_TIMEOUTMS = 5 * 1000;

	/**
	 * 支持的请求方法
	 */
	public interface Method {
		int DEPRECATED_GET_OR_POST = -1;
		int GET = 0;
		int POST = 1;
		int PUT = 2;
		int DELETE = 3;
	}

	private final int mMethod;

	private final String mUrl;

	private Map<String, String> mParams;

	public Map<String, String> getmParams() {
		return mParams;
	}

	public void setmParams(Map<String, String> mParams) {
		this.mParams = mParams;
	}

	public Request(int method, String url) {
		mMethod = method;
		mUrl = url;
	}

	public Request(String url) {
		mMethod = Method.GET;
		mUrl = url;
	}

	/**
	 * Returns the socket timeout in milliseconds per retry attempt. (This value can be changed per retry attempt if a backoff is specified via backoffTimeout()). If there are no retry attempts remaining, this will cause delivery of a {@link TimeoutError} error.
	 */
	public int getTimeoutMs() {
		return DEFAULT_TIMEOUTMS;
	}

	public int getMethod() {
		return mMethod;
	}

	public String getUrl() {
		return mUrl;
	}

	public Map<String, String> getHeaders() {
		return Collections.emptyMap();
	}

	protected Map<String, String> getParams() {
		return mParams;
	}

	protected String getParamsEncoding() {
		return DEFAULT_PARAMS_ENCODING;
	}

	public String getBodyContentType() {
		return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
	}

	/**
	 * Returns the raw POST or PUT body to be sent.
	 * 
	 * @throws AuthFailureError
	 *             in the event of auth failure
	 */
	public byte[] getBody() {
		Map<String, String> params = getParams();
		if (params != null && params.size() > 0) {
			return encodeParameters(params, getParamsEncoding());
		}
		return null;
	}

	/**
	 * Converts <code>params</code> into an application/x-www-form-urlencoded encoded string.
	 */
	private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
		StringBuilder encodedParams = new StringBuilder();
		try {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
				encodedParams.append('=');
				encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
				encodedParams.append('&');
			}
			return encodedParams.toString().getBytes(paramsEncoding);
		} catch (UnsupportedEncodingException uee) {
			throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
		}
	}

}
