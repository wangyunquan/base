package com.buswe.core.web.service;

import javax.servlet.http.HttpServletRequest;

import com.buswe.core.web.bean.IpLocation;

public abstract interface WebServiceApi
{
  public abstract IpLocation getIpLoaction(String paramString);
  
  public abstract String getIp(HttpServletRequest paramHttpServletRequest);
}
