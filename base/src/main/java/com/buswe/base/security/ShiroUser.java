package com.buswe.base.security;

import java.util.Objects;
/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 */
public class ShiroUser implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8440193674745301481L;
	public String loginName;
	public String name;

	public ShiroUser(String loginName, String name) {
		this.loginName = loginName;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return loginName;
	}

	/**
	 * 重载hashCode,只计算loginName;
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(loginName);
	}

	/**
	 * 重载equals,只计算loginName;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ShiroUser other = (ShiroUser) obj;
		if (loginName == null) {
			if (other.loginName != null) {
				return false;
			}
		} else if (!loginName.equals(other.loginName)) {
			return false;
		}
		return true;
	}
}
