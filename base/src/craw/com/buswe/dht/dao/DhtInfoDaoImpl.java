package com.buswe.dht.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.buswe.dht.entity.Dhtfiles;
import com.buswe.dht.entity.Dhtinfo;
import com.buswe.dhtcrawler.db.mysql.exception.DhtException;

public class DhtInfoDaoImpl implements DhtinfoDao  {

	@Resource
	NamedParameterJdbcTemplate namedjdbc;
	@Resource
	JdbcTemplate simpleJdbc;
	private final String insertDhtinfosql = "INSERT INTO dhtinfo (info_hash,peer_Ipport,lastrequesttime,tag,name,dhtstate,filelength,singerfile,isindex,validstate,successcount) values"
			+ " ( :infoHash,:peerIpport,:lastrequesttime,:tag,:name,:dhtstate,:filelength,:singerfile,:isindex,:validstate,:successcount)";
   private final String insertDhtFilesSql="insert into dhtfiles (info_hash,singlefilelength,path) values (:infoHash,:singlefilelength,:path)";
	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#insertDhtinfo(com.buswe.dht.entity.Dhtinfo)
	 */
	@Override
	public Boolean insertDhtinfo(Dhtinfo dhtInfo) {
		int i = namedjdbc.update(insertDhtinfosql, dhtinfoSqlParam(dhtInfo));
		return i > 0 ? true : false;
	}

	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#deleteDhtinfo(java.lang.String)
	 */
	@Override
	public Boolean deleteDhtinfo(String info_hash) {
		String deleteDhtinfo = "delete from dhtinfo where info_hash=?";
		return simpleJdbc.update(deleteDhtinfo, info_hash)>0?true:false;
	}
	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#loadByInfoHash(java.lang.String)
	 */
	@Override
	public Dhtinfo loadByInfoHash(String infohash) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#getDhtinfoByInfoHash(java.lang.String)
	 */
	@Override
	public Dhtinfo getDhtinfoByInfoHash(String infohash) {
		return 	simpleJdbc.queryForObject("select * from dhtinfo where info_hash=?", new String []{infohash}, Dhtinfo.class);
	}

	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#getDhtinfosByState(int, int)
	 */
	@Override
	public List<Dhtinfo> getDhtinfosByState(int state, int limit) throws DhtException {
	 String findSql="select * from Dhtinfo limit ?";
		return simpleJdbc.queryForList(findSql, Dhtinfo.class, limit);
	}
	
	public List<String> getDhtinfoHashByState(int state, int limit)
	{
		 String findSql="select infoHash from Dhtinfo limit ?";
			return simpleJdbc.queryForList(findSql, String.class, limit);
	}
	
	public Boolean updateDhtinfoSate(String infohash,Integer state)
	{
		return simpleJdbc.update("update Dhtinfo set dhtstate=? where  info_hash=?",state, infohash)>0?true:false;
	}
	
	
	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#addDhtInfoCrawcount(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Boolean addDhtInfoCrawcount(String info_hash){
		return simpleJdbc.update("update Dhtinfo set crawcount=crawcount+1 where  info_hash=?", info_hash)>0?true:false;
	}
	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#addDhtInfoSuccesscount(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Boolean addDhtInfoSuccesscount(String info_hash){
		return simpleJdbc.update("update Dhtinfo set successcount=successcount+1 where  info_hash=?", info_hash)>0?true:false;
	}
	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#dhtinfoExsit(java.lang.String)
	 */
	@Override
	public Boolean dhtinfoExsit(String info_hash)
	{
		return simpleJdbc.queryForInt("select count(1) from Dhtinfo where info_hash=?",info_hash)>0?true:false ;
	}
	private SqlParameterSource dhtinfoSqlParam(Dhtinfo dhtInfo) {
		return new BeanPropertySqlParameterSource(dhtInfo);
	}

	private SqlParameterSource dhtfilesSqlParam(Dhtfiles dhtfiles)
	{
		return new BeanPropertySqlParameterSource(dhtfiles);
	}
	@Override
	public Boolean updateDhtFiles(Dhtinfo dhtinfo) {
      String deleteSql="delete from dhtfiles where info_hash=?";
   simpleJdbc.update(deleteSql, dhtinfo.getInfoHash());
   List<Dhtfiles>  files=  dhtinfo.getDhtfiles();
   if(files==null||files.size()==0)
   {return true;}
   Integer fileSize=files.size();
   SqlParameterSource [] sqlparam=new SqlParameterSource[fileSize] ;
      for(int i=0;i<fileSize;i++)
      {
    	  sqlparam[i]=dhtfilesSqlParam(files.get(i));
      }
   return   namedjdbc.batchUpdate(deleteSql, sqlparam).length>0?true:false;
	}

	@Override
	public Boolean updateParseSuccess(Dhtinfo dhtinfo) {
	 
		String updateSql=" update Dhtinfo set creattime=:creattime,name=:name,singerfile=:singerfile,filelength=:filelength ,dhtstate=:dhtstate"
				+ "where info_hash=:infoHash";
		return namedjdbc.update(updateSql,  dhtinfoSqlParam(dhtinfo))>0?true:false;
	}

}
