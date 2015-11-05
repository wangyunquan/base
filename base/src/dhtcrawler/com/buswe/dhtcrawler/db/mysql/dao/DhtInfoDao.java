package com.buswe.dhtcrawler.db.mysql.dao;

import java.util.List;

import com.buswe.dhtcrawler.db.models.DhtInfo_MongoDbPojo;
import com.buswe.dhtcrawler.db.mysql.exception.DhtException;

public interface DhtInfoDao {

	public void insert(DhtInfo_MongoDbPojo dhtInfo) throws DhtException;

	public void delete(DhtInfo_MongoDbPojo dhtInfo) throws DhtException;

	public void update(DhtInfo_MongoDbPojo dhtInfo) throws DhtException;

	public DhtInfo_MongoDbPojo findById(Integer iddhtInfo) throws DhtException;

	public List<DhtInfo_MongoDbPojo> findAll() throws DhtException;

	public List<DhtInfo_MongoDbPojo> getNoAnalyticDhtInfos(int count) throws DhtException;

}
