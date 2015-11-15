package com.buswe.dht.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.dht.dao.DhtinfoDao;
import com.buswe.dht.entity.Dhtinfo;
import com.buswe.dht.entity.DhtinfoState;
import com.buswe.dht.util.context.DhtContextHolder;

@Service("dhtinfoService")
@Transactional("dataSouceTransaction")
public class DhtinfoServiceImpl implements DhtinfoService {
	  protected Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private DhtinfoDao dhtinfoDao;
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
		return dhtinfoDao.loadByInfoHash(infohash);
	}

	@Override
	public Dhtinfo getDhtinfoByInfoHash(String infohash) {

		return dhtinfoDao.getDhtinfoByInfoHash(infohash);
	}

	@Override
	public List<Dhtinfo> getDhtinfosByState(int state, int limit)  {
		return dhtinfoDao.getDhtinfosByState(state, limit);
	}

	@Override
	public Boolean saveDhtinfo(Dhtinfo info) {
			String infohash=info.getInfohash();
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
	 
		return true;
	}

	@Override
	public Boolean updateDhtinfoDownLoad(Dhtinfo dhtinfo,Boolean updateDhtFiles){
		
		Integer dhtinfoState=dhtinfo.getDhtstate();
		
		switch(dhtinfoState)
		{
		case DhtinfoState.DHTSTATE_DOWNLOAD_FAIL :
			return			dhtinfoDao.updateDhtinfoSate(dhtinfo.getInfohash(), DhtinfoState.DHTSTATE_DOWNLOAD_FAIL);
			 
		case DhtinfoState.DHTSTATE_PARSING_FAIL :
	return		dhtinfoDao.updateDhtinfoSate(dhtinfo.getInfohash(), DhtinfoState.DHTSTATE_PARSING_FAIL );
		case DhtinfoState.DHTSTATE_OK:
		{
		Boolean saveinfo=	dhtinfoDao.updateParseSuccess(dhtinfo);
		Boolean savefile=true;
			if(updateDhtFiles)
			{
				try {
					DhtContextHolder.PUBLIC_DHTFILEINSERT_QUEUE.put(dhtinfo);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return false;
				}
			//	savefile=	dhtinfoDao.updateDhtFiles(dhtinfo);
				return true;
			}
			if(!(saveinfo&&savefile))
			{
				logger.error(dhtinfo.getInfohash()+  "  更新数据失败");
				return	 false;
			}
			return	 true;
		}
		default :
			logger.error(dhtinfo.getInfohash()+  "  状态异常");
			return false;
		}
		
	}

	@Override
	public List<String> getDhtinfoHashByState(int state, int limit) {
		return dhtinfoDao.getDhtinfoHashByState(state, limit);
	}
	@Override
	public List<Dhtinfo> getNotIndexedDhtinfo(Integer limit) {
		return dhtinfoDao.getNotIndexedDhtinfo(limit);
	}
	@Override
	public Integer updateDhtinfoIndexed(List<Dhtinfo> dhtinfoList) {
		return dhtinfoDao.updateDhtinfoIndexed(dhtinfoList);
	}
	@Override
	public Boolean updateDhtFiles(Dhtinfo info) {
		 
		return dhtinfoDao.updateDhtFiles(info);
	}

}
