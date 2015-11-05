package com.buswe.dhtcrawler.db.models;

import com.buswe.dhtcrawler.db.mongodb.MongoCollection;
import com.buswe.dhtcrawler.parser.TorrentInfo;

 
/**
 * 存储到数据库中的对象
 * @author 耳东 (cgp@0731life.com)
 */
@MongoCollection(value="dht")
public class DhtInfo_MongoDbPojo {
	private String info_hash;
	private String peerIp;
	private TorrentInfo torrentInfo;
	private String torrentFilePath;
	private long lastRequestsTime;// 最后请求时间
	private int analysised = DhtInfoStateCode.NO_DOWNLOAD;
	private String tag = "";// 标识

	public TorrentInfo getTorrentInfo() {
		return torrentInfo;
	}

	public void setTorrentInfo(TorrentInfo torrentInfo) {
		this.torrentInfo = torrentInfo;
	}

	public String getInfo_hash() {
		return info_hash;
	}

	public void setInfo_hash(String info_hash) {
		this.info_hash = info_hash;
	}

	public String getPeerIp() {
		return peerIp;
	}

	public void setPeerIp(String peerIp) {
		this.peerIp = peerIp;
	}

	public String getTorrentFilePath() {
		return torrentFilePath;
	}

	public void setTorrentFilePath(String torrentFilePath) {
		this.torrentFilePath = torrentFilePath;
	}

	public long getLastRequestsTime() {
		return lastRequestsTime;
	}

	public void setLastRequestsTime(long lastRequestsTime) {
		this.lastRequestsTime = lastRequestsTime;
	}

	public int getAnalysised() {
		return analysised;
	}

	public void setAnalysised(int analysised) {
		this.analysised = analysised;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}


}
