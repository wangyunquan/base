package com.buswe.base.security;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import com.buswe.base.utils.Encodes;
import com.buswe.moudle.core.entity.Rolesinfo;
import com.buswe.moudle.core.entity.UserBasic;
import com.buswe.moudle.core.service.UserBasicService;
@Service
public class ShiroDbRealm extends AuthorizingRealm {

	@Resource
	UserBasicService service;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		UserBasic user = service.findByLoginName(shiroUser.loginName);

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		for (Rolesinfo role : user.getRolesinfos()) {
			// 基于Role的权限信息
			info.addRole(role.getRolename());
			// 基于Permission的权限信息
		//	info.addStringPermissions(role.getRolesAuthority());
		}
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		UserBasic user = service.findByLoginName(token.getUsername());
		if (user != null) {
			if (!user.getEnabled()) {
				throw new DisabledAccountException();
			}

			byte[] salt = PasswordUtil.saltPassWord.getBytes();
			return new SimpleAuthenticationInfo(new ShiroUser(user.getLoginName(), user.getName()), user.getPassword(),
					ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}
	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(PasswordUtil.HASH_ALGORITHM);
		matcher.setHashIterations(PasswordUtil.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}
 
}
