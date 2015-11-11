package com.buswe.base.config.annotation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.buswe.base.security.JCaptchaValidateFilter;
import com.buswe.base.security.MyFormAuthenticationFilter;
import com.buswe.base.security.ShiroDbRealm;
import com.buswe.base.security.URLPermissionsFilter;

@Configuration
public class SecurityConfig {

	@Bean(name = "shiroFilter")
	ShiroFilterFactoryBean shiroFilterFactoryBean(org.apache.shiro.mgt.SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/");
		Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();
		/**
		 * /logout = logout /account/** = user /api/secure/** = authcBasic
		 * 
		 * 
		 * DefaultFilter 规定了默认的Filter，在ShiroFilterFactoryBean时通过
		 * DefaultFilterChainManager获取到
		 * 
		 */

		Map<String, Filter> filters = new HashMap<String, Filter>();

		MyFormAuthenticationFilter authcFilter = new MyFormAuthenticationFilter();
		filters.put("authc", authcFilter);
		// 验证码过滤器
		JCaptchaValidateFilter captchaFilter = new JCaptchaValidateFilter();
		captchaFilter.setEnabled(true);
		captchaFilter.setJcaptchaParam("jcaptchaCode");
		captchaFilter.setFailureKeyAttribute("shiroLoginFailure");
		filters.put("jCaptchaValidate", captchaFilter);
		URLPermissionsFilter urlPermissionFilter = new URLPermissionsFilter();
		filters.put("urlPermission", urlPermissionFilter);
		shiroFilterFactoryBean.setFilters(filters);
		filterChainDefinitionMap.put("/login", "jCaptchaValidate,authc");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/core/**", "urlPermission");
		filterChainDefinitionMap.put("/cms/**", "urlPermission");
		filterChainDefinitionMap.put("/dht/**", "urlPermission");
		filterChainDefinitionMap.put("/api/secure/**", "authcBasic");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public WebSecurityManager securityManager(org.apache.shiro.cache.ehcache.EhCacheManager securityCacheManager,
			ShiroDbRealm shiroDbRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setCacheManager(securityCacheManager);
		securityManager.setRealm(shiroDbRealm);
		return securityManager;
	}

	/**
	 * security Cach use the shared enchache
	 * 
	 * @return
	 */
	@Bean
	public org.apache.shiro.cache.ehcache.EhCacheManager securityCacheManager(CacheManager cacheManager) {
		EhCacheCacheManager encacheManager = (EhCacheCacheManager) cacheManager;
		net.sf.ehcache.CacheManager manager = encacheManager.getCacheManager();
		org.apache.shiro.cache.ehcache.EhCacheManager securityCacheManager = new org.apache.shiro.cache.ehcache.EhCacheManager();
		securityCacheManager.setCacheManager(manager);
		return securityCacheManager;
	}

	@Bean
	AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			org.apache.shiro.mgt.SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * 保证实现了Shiro内部lifecycle函数的bean执行 --
	 * 
	 * @return
	 */
	@Bean(name = "lifecycleBeanPostProcessor")
	LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * AOP式方法级权限检查
	 * 
	 * @return
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}
}
