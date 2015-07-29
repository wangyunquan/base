package com.buswe.base.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

public class SecurityMetadataSourceAuthImpl
  implements FilterInvocationSecurityMetadataSource
{
  private Set<ConfigAttribute> authSet = new HashSet<ConfigAttribute>();
  
  public Collection<ConfigAttribute> getAttributes(Object object)
    throws IllegalArgumentException
  {
    HttpServletRequest request = ((FilterInvocation)object)
      .getRequest();
    String requestpath = getRequestPath(request);
    String auth = requestpath.replace("/", "_").toUpperCase().substring(1);
    ConfigAttribute security = new SecurityConfig(auth);
    return Arrays.asList(new ConfigAttribute[] { security });
  }
  
  private String getRequestPath(HttpServletRequest request)
  {
    String url = request.getServletPath();
    if (request.getPathInfo() != null) {
      url = url + request.getPathInfo();
    }
    return url;
  }
  public Collection<ConfigAttribute> getAllConfigAttributes()
  {
    return this.authSet;
  }
  public boolean supports(Class<?> clazz)
  {
    return true;
  }
}
