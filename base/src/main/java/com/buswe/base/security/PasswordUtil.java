package com.buswe.base.security;

import com.buswe.base.utils.Digests;
import com.buswe.base.utils.Encodes;
import com.buswe.moudle.core.entity.UserBasic;

public class PasswordUtil {
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	public static final String saltPassWord="wangyunquan";
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	public static  void entryptPassword(UserBasic user) {
//		byte[] salt = Digests.generateSalt(SALT_SIZE);
//		user.setSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), saltPassWord.getBytes(), HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
}
	
	
	public static void main(String aa[])
	{
		UserBasic user=new UserBasic();
		user.setPassword("123");
		entryptPassword(user);
		System.out.println(user.getPassword());
	}
}

	

