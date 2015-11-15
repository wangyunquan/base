package com.buswe.dht.paser;

import com.buswe.dht.util.Base32;
import com.buswe.dht.util.ByteUtil;

/**
 * 
 * @author 王云权 wangyunquan@live.com
 * CreateTime :2015年11月9日下午5:20:36
 */
public class TorrentinfoUrl {
	public  static  final String [] urls=new String []{
			
			"http://www.torrent.org.cn/Home/Torrent/download?hash=%1$s.torrent",
			"http://bt.box.n0808.com/%1$s/%2$s/%3$s.torrent", //迅雷
	//		 "http://magnet.vuze.com/magnetLookup?hash=%1$s",
	//		 "http://torrage.com/torrent/%1$s.torrent",
			 "http://torcache.net/torrent/%1$s.torrent",
		//	 "http://zoink.it/torrent/%1$s.torrent",
		//	 "http://torcache.net/torrent/%1$s.torrent"
	};
 /**
  * 根据infohash返回种子的下载地址
  * @param infoHash
  * @return
  */
public static String formatUrl(String info_hash,Integer time){
	String url="";
	switch(time)
	{
	case 1:		
		 url = String.format(urls[time], info_hash.substring(0, 2), info_hash.substring(info_hash.length() - 2, info_hash.length()), info_hash);
			break;
//	case 6:
//		String hash = Base32.encode(ByteUtil.HexString2Bytes(info_hash.trim()));
//	   url = String.format(urls[time], hash);
//		break;
		default:
			url = String.format(urls[time], info_hash);
			break;
	}
	
	return url;
}

 public static void main(String aa[])
 {
	 
	 System.out.println(formatUrl("8761E9485D810059BDD07BCCC1A635AA8212497B",3));
 }
}
