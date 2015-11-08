package com.buswe.dhtcrawler.db.mysql.dao;

import java.util.List;

import com.buswe.dhtcrawler.db.mysql.exception.DhtException;
import com.buswe.dhtcrawler.entity.Dhtinfo;
public interface DhtInfoDao {
	public void insert(Dhtinfo dhtInfo) throws DhtException;
	public void delete(Dhtinfo dhtInfo) throws DhtException;
	public void update(Dhtinfo dhtInfo) throws DhtException;
	public Dhtinfo loadByInfoHash(String infohash) throws DhtException;
	public List<Dhtinfo> getDhtinfosByState(int state) throws DhtException;
}
