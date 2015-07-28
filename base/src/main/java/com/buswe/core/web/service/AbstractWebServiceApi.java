package com.buswe.core.web.service;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractWebServiceApi
  implements WebServiceApi
{
  public String getIp(HttpServletRequest request)
  {
    String ip = request.getHeader("x-forwarded-for");
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("X-Forwarded-For");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getRemoteAddr();
    }
    if ((ip != null) && (ip.indexOf(',') != -1))
    {
      String[] ips = ip.split(",");
      for (int i = 0; i < ips.length; i++) {
        if ((ips[i] != null) && (!"unknown".equalsIgnoreCase(ips[i])))
        {
          ip = ips[i];
          break;
        }
      }
    }
    return ip;
  }
}
