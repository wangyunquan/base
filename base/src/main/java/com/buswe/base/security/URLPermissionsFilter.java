package com.buswe.base.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

public class URLPermissionsFilter extends PermissionsAuthorizationFilter{
	/**
	 *@param mappedValue 指的是在声明url时指定的权限字符串，如/User/create.do=perms[User:create].我们要动态产生这个权限字符串，所以这个配置对我们没用
	 */
	public boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws IOException {
		  Subject subject = getSubject(request, response);
		  if(subject.getPrincipal() == null)
			  return false;
		  String perms=buildPermissions(request);
		  boolean isPermitted = true;
//	                if (!subject.isPermitted(perms) ){
//	                    isPermitted = false;
//	                }
//	            else {
//	                if (!subject.isPermittedAll(perms)) {
//	                    isPermitted = false;
//	                }
//	            }
	        return isPermitted;
	}

	/**
	 * 根据请求URL产生权限字符串，这里只产生，而比对的事交给Realm
	 * @param request
	 * @return
	 */
	protected String buildPermissions(ServletRequest request) {
	 
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getServletPath();
	 
		/*String regex = "/(.*?)/(.*?)\\.(.*)";
		if(url.matches(regex)){
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(url);
			String controller =  matcher.group(1);
			String action = matcher.group(2);
			
		}*/
		return path;
	}
}
