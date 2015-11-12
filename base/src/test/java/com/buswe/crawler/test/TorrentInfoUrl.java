//package com.buswe.crawler.test;
//
//import com.buswe.dht.util.ByteUtil;
//import com.buswe.dhtcrawler.util.Base32;
//
//public class TorrentInfoUrl {
//
//	public static void main(String[] args) {
//		
//		String baseurl = "http://bt.box.n0808.com/%1$s/%2$s/%3$s.torrent";
//		String url2= "http://magnet.vuze.com/magnetLookup?hash=%1$s";
//		String info_hash = "6c0d5e8e841debf547a5d4401849a011a15e7ddd".toUpperCase();
//		String url = String.format(baseurl, info_hash.substring(0, 2), info_hash.substring(info_hash.length() - 2, info_hash.length()), info_hash);
//		System.out.println(url);
//		String hash = Base32.encode(ByteUtil.HexString2Bytes(info_hash.trim()));
//		   url = String.format(url2, hash);
//		   System.out.println(url);
//		   
//		   
// 
//	}
//
//}
