package com.buswe.dht.dao;

import java.util.List;

import com.buswe.dht.entity.Dhtinfo;
import com.buswe.dhtcrawler.db.mysql.exception.DhtException;

public interface DhtinfoDao {
	public		Boolean insertDhtinfo(Dhtinfo dhtInfo);
	public		Boolean deleteDhtinfo(String info_hash);
	public		Dhtinfo loadByInfoHash(String infohash);
	public		Dhtinfo getDhtinfoByInfoHash(String infohash);
	public		List<Dhtinfo> getDhtinfosByState(int state, int limit) throws DhtException;
	public	Boolean addDhtInfoCrawcount(String info_hash);
	public Boolean addDhtInfoSuccesscount(String info_hash);
	public Boolean dhtinfoExsit(String info_hash);
	public Boolean updateDhtinfoSate(String infohash,Integer state);
	public List<String> getDhtinfoHashByState(int state, int limit);
	public Boolean updateDhtFiles(Dhtinfo dhtinfo);
	public Boolean updateParseSuccess(Dhtinfo dhtinfo);
	
}