package com.buswe.dht.dao;

import java.util.List;

import com.buswe.dht.entity.Dhtinfo;

public interface DhtinfoDao {
	public		Boolean insertDhtinfo(Dhtinfo dhtInfo);
	public		Boolean deleteDhtinfo(String info_hash);
	public		Dhtinfo loadByInfoHash(String infohash);
	public		Dhtinfo getDhtinfoByInfoHash(String infohash);
	public		List<Dhtinfo> getDhtinfosByState(int state, int limit)  ;
	public	Boolean addDhtInfoCrawcount(String info_hash);
	public Boolean addDhtInfoSuccesscount(String info_hash);
	public Boolean dhtinfoExsit(String info_hash);
	public Boolean updateDhtinfoSate(String infohash,Integer state);
	public List<String> getDhtinfoHashByState(int state, int limit);
	public Boolean updateDhtFiles(Dhtinfo dhtinfo);
	public Boolean updateParseSuccess(Dhtinfo dhtinfo);
	/**
	 * 
	 * @param limit
	 * @return
	 */
	public		List<Dhtinfo>  getNotIndexedDhtinfo(Integer limit);
	public Integer updateDhtinfoIndexed(List<Dhtinfo> dhtinfoList);
	public Integer getTotalDhtinfo();
}