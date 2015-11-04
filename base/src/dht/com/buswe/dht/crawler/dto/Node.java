package com.buswe.dht.crawler.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by wihoho on 19/9/15.
 */

public class Node {
    private String id;
    private String address;
    private int port;

    public boolean isValid() {
        if (StringUtils.isBlank(address))
            return false;

        if (port < 1 || port > 65535)
            return false;

        return true;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
    
    
}
