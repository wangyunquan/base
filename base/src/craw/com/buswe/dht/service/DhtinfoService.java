package com.buswe.dht.service;

import java.util.List;

import com.buswe.dht.entity.Dhtinfo;
 

public interface DhtinfoService {

	/**
	 * 新增一个dhtinfo
	 * @param dhtInfo
	 * @return
	 */
	public Boolean insertDhtinfo(Dhtinfo dhtInfo);
 
	public Boolean deleteDhtinfo(String dhtInfo);
	/**
	 * 加载dhtinfo及其子文件
	 * @param infohash
	 * @return
	 * @throws DhtException
	 */
	public Dhtinfo loadByInfoHash(String infohash)  ;
	/**
	 * 查询dhtinfo，不包括子文件
	 * @param infohash
	 * @return
	 */
	public Dhtinfo getDhtinfoByInfoHash(String infohash);
	/**
	 * 根据状态查询dhtinfo，
	 * @param state
	 * @param limit 数量
	 * @return
	 * @throws DhtException
	 */
	public List<Dhtinfo> getDhtinfosByState(int state,int limit) ;
/**
 * 
 * 批量添加,逐个根据infohash进行检查。
 * 如果存在，如果是 crawcount>0 则进行更新 crawcount+1，并且更新 lastrequesttime的时间
 * 如果是 annoutce，则更新 successcount>0 并且更新 lastrequesttime的时间
 * 
 * 如果不存在，则新增
 * 
 * @param dhtinfoList
 * @return
 */
	public Boolean saveBatchDhtinfo(List<Dhtinfo> dhtinfoList);
	
	/**
	 *  下载后更新链接信息 
	 * @param dhtinfo 
	 * @param updateDhtFiles 是否更新子文件，如果无效，则删除掉
	 * @return
	 */
	public Boolean updateDhtinfoDownLoad(Dhtinfo dhtinfo,Boolean updateDhtFiles);
	/**
	 * 根据状态查找indohash
	 * @param state
	 * @param limit
	 * @return
	 */
	public List<String> getDhtinfoHashByState(int state, int limit);
	
	
	public		List<Dhtinfo>  getNotIndexedDhtinfo(Integer limit);
	public Integer updateDhtinfoIndexed(List<Dhtinfo> dhtinfoList);
}
