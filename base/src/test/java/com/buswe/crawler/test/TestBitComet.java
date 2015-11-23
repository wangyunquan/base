package com.buswe.crawler.test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.buswe.dht.util.ByteUtil;

public class TestBitComet {
	
	
	public static void main(String [] aa)
	{
		String infoHashHex="478e1b3a63d2ff0ff8c9cb22001e6d2c893ab734";
		String base = "http://torrent-cache.bitcomet.org:36869/get_torrent?info_hash=%1$s&key=%2$s";
		
		String downurl=base.format(base, infoHashHex,calcKey(infoHashHex));
 
		System.out.println(downurl);
	 
		

	}
	
 
	public static String   calcKey(String infoHashHex){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update("bc".getBytes());
			md.update(ByteUtil.HexString2Bytes(infoHashHex));
			md.update("torrent".getBytes());
			return ByteUtil.hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
