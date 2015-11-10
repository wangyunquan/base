package com.buswe.dht.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.buswe.dht.entity.Dhtfiles;
import com.buswe.dht.entity.Dhtinfo;
import com.buswe.dhtcrawler.db.mysql.exception.DhtException;
@Repository
public class DhtInfoDaoImpl implements DhtinfoDao  {

	@Resource
	NamedParameterJdbcTemplate namedjdbc;
	@Resource
	JdbcTemplate simpleJdbc;
	private final String insertDhtinfosql = "INSERT INTO dhtinfo (infohash,peer_Ipport,lastrequesttime,tag,name,dhtstate,filelength,singerfile,isindex,validstate,successcount) values"
			+ " ( :infohash,:peerIpport,:lastrequesttime,:tag,:name,:dhtstate,:filelength,:singerfile,:isindex,:validstate,:successcount)";
   private final String insertDhtFilesSql="insert into dhtfiles (infohash,singlefilelength,path) values (:infohash,:singlefilelength,:path)";
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
	public Boolean deleteDhtinfo(String infohash) {
		String deleteDhtinfo = "delete from dhtinfo where infohash=?";
		return simpleJdbc.update(deleteDhtinfo, infohash)>0?true:false;
	}
	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#loadByinfohash(java.lang.String)
	 */
	@Override
	public Dhtinfo loadByInfoHash(String infohash) {
		
		String sql="select * from Dhtinfo where infohash=?";
		Dhtinfo dhtinfo=	simpleJdbc.queryForObject(sql, Dhtinfo.class, infohash);
		dhtinfo.setDhtfiles(getDhtFilesByinfohash(dhtinfo.getInfohash()));
		return dhtinfo;
	}

	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#getDhtinfoByinfohash(java.lang.String)
	 */
	@Override
	public Dhtinfo getDhtinfoByInfoHash(String infohash) {
		return 	simpleJdbc.queryForObject("select * from dhtinfo where infohash=?", new String []{infohash}, Dhtinfo.class);
	}

	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#getDhtinfosByState(int, int)
	 */
	@Override
	public List<Dhtinfo> getDhtinfosByState(int state, int limit) throws DhtException {
	 String findSql="select * from Dhtinfo where dhtstate=? limit ?";
		return simpleJdbc.queryForList(findSql, Dhtinfo.class,state, limit);
	}
	@Override
	public List<String> getDhtinfoHashByState(int state, int limit)
	{
		 String findSql="select infohash from Dhtinfo where dhtstate=? limit ?";
			return simpleJdbc.queryForList(findSql, String.class, state,limit);
	}
	
	public Boolean updateDhtinfoSate(String infohash,Integer state)
	{
		return simpleJdbc.update("update Dhtinfo set dhtstate=? where  infohash=?",state, infohash)>0?true:false;
	}
	
	
	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#addDhtInfoCrawcount(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Boolean addDhtInfoCrawcount(String infohash){
		return simpleJdbc.update("update Dhtinfo set crawcount=crawcount+1 where  infohash=?", infohash)>0?true:false;
	}
	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#addDhtInfoSuccesscount(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Boolean addDhtInfoSuccesscount(String infohash){
		return simpleJdbc.update("update Dhtinfo set successcount=successcount+1 where  infohash=?", infohash)>0?true:false;
	}
	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#dhtinfoExsit(java.lang.String)
	 */
	@Override
	public Boolean dhtinfoExsit(String infohash)
	{
		return simpleJdbc.queryForInt("select count(1) from Dhtinfo where infohash=?",infohash)>0?true:false ;
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
      String deleteSql="delete from dhtfiles where infohash=?";
   simpleJdbc.update(deleteSql, dhtinfo.getInfohash());
   List<Dhtfiles>  files=  dhtinfo.getDhtfiles();
   if(files==null||files.size()==0)
   {return true;}
   Integer fileSize=files.size();
   SqlParameterSource [] sqlparam=new SqlParameterSource[fileSize] ;
      for(int i=0;i<fileSize;i++)
      {
    	  sqlparam[i]=dhtfilesSqlParam(files.get(i));
      }
   return   namedjdbc.batchUpdate(insertDhtFilesSql, sqlparam).length>0?true:false;
	}

	@Override
	public Boolean updateParseSuccess(Dhtinfo dhtinfo) {
	 
		String updateSql=" update Dhtinfo set creattime=:creattime,name=:name,singerfile=:singerfile,filelength=:filelength ,dhtstate=:dhtstate"
				+ "where infohash=:infohash";
		return namedjdbc.update(updateSql,  dhtinfoSqlParam(dhtinfo))>0?true:false;
	}

	@Override
	public List<Dhtinfo> getNotIndexedDhtinfo(Integer limit) {
		 String findSql="select * from Dhtinfo where isindex=1 limit ? ";
		 List<Dhtinfo> list = simpleJdbc.queryForList(findSql, Dhtinfo.class,limit);
		 for(Dhtinfo info:list)
		 {
			 info.setDhtfiles(getDhtFilesByinfohash(info.getInfohash()));
		 }
		 return list;
	}

	private List<Dhtfiles> getDhtFilesByinfohash(String infohash){
		String sql="select * from Dhtfiles where infohash=?";
		return simpleJdbc.queryForList(sql, Dhtfiles.class, infohash);
	}
	
	
	@Override
	public Integer updateDhtinfoIndexed(List<Dhtinfo> dhtinfoList) {
	 String sql="update Dhtinfo set isindex=:isindex where infohash=:infohash ";
	   Integer fileSize=dhtinfoList.size();
	   SqlParameterSource [] sqlparam=new SqlParameterSource[fileSize] ;
	      for(int i=0;i<fileSize;i++)
	      {
	    	  sqlparam[i]=dhtinfoSqlParam(dhtinfoList.get(i));
	      }
return	 namedjdbc.batchUpdate(sql, sqlparam).length;
	}

 

}
