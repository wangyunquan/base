package com.buswe.dht.util;

public class ByteUtil {

	/**
	 * 将int端口转为2 byte
	 */
	public static byte[] intTobytes(int value) {
		byte[] src = new byte[2];
		src[1] = (byte) ((value >> 8) & 0xFF);
		src[0] = (byte) ((value >> 0) & 0xFF);
		return src;
	}
	/**
	 * 将2byte 的端口转为int
	 */
	public static int bytesToInt(byte[] src) {
		int value = 0;
		value |= (src[0] & 0xFF) << 8;
		value |= (src[1] & 0xFF) << 0;
		return value;
	}
	/**
	 * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
	 */
	public static int bytesToInt2(byte[] src, int offset) {
		int value;
		value = (((src[offset] & 0xFF) << 24) //
				| ((src[offset + 1] & 0xFF) << 16) //
				| ((src[offset + 2] & 0xFF) << 8) //
		| (src[offset + 3] & 0xFF));
		return value;
	}
	// 返回十六进制字符串
	public static String hex(byte[] arr) {
		// System.out.println("t的长度=" + arr.length);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}
	/**
	 * 
	 * @param hexstr 16尽职字符串
	 * @return
	 */
	public static byte[] HexString2Bytes(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}
	private static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}
}
