package com.buswe.dht.service;

import java.util.List;

import javax.annotation.Resource;

import com.buswe.dht.dao.DhtinfoDao;
import com.buswe.dht.entity.Dhtinfo;
import com.buswe.dhtcrawler.db.mysql.exception.DhtException;

public class DhtinfoServiceImpl implements DhtinfoService {
	@Resource
	DhtinfoDao dhtinfoDao;

	@Override
	public Boolean insertDhtinfo(Dhtinfo dhtInfo) {
		return dhtinfoDao.insertDhtinfo(dhtInfo);
	}

	@Override
	public Boolean deleteDhtinfo(String info_hash) {
		return dhtinfoDao.deleteDhtinfo(info_hash);
	}

	@Override
	public Dhtinfo loadByInfoHash(String infohash) {
		// TODO,加载子文件
		return null;
	}

	@Override
	public Dhtinfo getDhtinfoByInfoHash(String infohash) {

		return dhtinfoDao.getDhtinfoByInfoHash(infohash);
	}

	@Override
	public List<Dhtinfo> getDhtinfosByState(int state, int limit) throws DhtException {
		return dhtinfoDao.getDhtinfosByState(state, limit);
	}

	@Override
	public Boolean saveBatchDhtinfo(List<Dhtinfo> dhtinfoList) {
		for (Dhtinfo info : dhtinfoList) {
			String infohash=info.getInfoHash();
			Boolean exsit = dhtinfoDao.dhtinfoExsit(infohash);
			if(exsit)
			{
				if(info.getCrawcount()>0)
 			{
					dhtinfoDao.addDhtInfoCrawcount(infohash);
 			}else	if(info.getSuccesscount()>0)
 			{
 				dhtinfoDao.addDhtInfoSuccesscount(infohash);
 			}
 			else
 			{
 				//TODO 错误
 			}
			}
			else
			{
				dhtinfoDao.insertDhtinfo(info);
			}
		}
		return null;
	}

	@Override
	public Boolean updateDhtinfoSate(String infohash, Integer state) {
		return null;
	}

	@Override
	public List<String> getDhtinfoHashByState(int state, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

}
