package com.buswe.dht.paser;

public class TorrentinfoUrl {
	private static String [] torrentinfoUrl=new String []{
			"http://bt.box.n0808.com/%1$s/%2$s/%3$s.torrent",
			 "http://magnet.vuze.com/magnetLookup?hash=%1$s",
			 "http://torrage.com/torrent/%1$s.torrent",
			 "http://torcache.net/torrent/%1$s.torrent",
			 "http://zoink.it/torrent/%1$s.torrent",
			 "http://torcache.net/torrent/%1$s.torrent"
	};
	//TODO  应该作为可配置的，从配置文件中读取，方便维护和检查
	public static String [] getTorrentinfoUrl()
	{
		return torrentinfoUrl;
	}

}
