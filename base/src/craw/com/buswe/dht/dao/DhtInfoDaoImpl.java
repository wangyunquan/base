package com.buswe.dht.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.buswe.dht.entity.Dhtfiles;
import com.buswe.dht.entity.Dhtinfo;
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
		
		String sql="select * from dhtinfo where infohash=?";
		 BeanPropertyRowMapper<Dhtinfo> infoRowMaper=new BeanPropertyRowMapper<Dhtinfo>(Dhtinfo.class);
		List<Dhtinfo> dhtinfos=	simpleJdbc.query(sql, infoRowMaper,infohash);
		if(dhtinfos==null||dhtinfos.size()==0)
			return null;
		else
		{
			Dhtinfo dhtinfo=dhtinfos.get(0);
		dhtinfo.setDhtfiles(getDhtFilesByinfohash(dhtinfo.getInfohash()));
		return dhtinfo;
		}
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
	public List<Dhtinfo> getDhtinfosByState(int state, int limit)   {
	 String findSql="select * from dhtinfo where dhtstate=? limit ?";
	 BeanPropertyRowMapper<Dhtinfo> infoRowMaper=new BeanPropertyRowMapper<Dhtinfo>(Dhtinfo.class);
		return simpleJdbc.query(findSql, infoRowMaper,state, limit);
	}
	@Override
	public List<String> getDhtinfoHashByState(int state, int limit)
	{
		 String findSql="select infohash from dhtinfo where dhtstate=? limit ?";
			return simpleJdbc.queryForList(findSql, String.class, state,limit);
	}
	
	public Boolean updateDhtinfoSate(String infohash,Integer state)
	{
		return simpleJdbc.update("update dhtinfo set dhtstate=? where  infohash=?",state, infohash)>0?true:false;
	}
	
	
	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#addDhtInfoCrawcount(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Boolean addDhtInfoCrawcount(String infohash){
		return simpleJdbc.update("update dhtinfo set crawcount=crawcount+1,lastrequesttime=CURRENT_TIMESTAMP "
				+ "where  infohash=?", infohash)>0?true:false;
	}
	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#addDhtInfoSuccesscount(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Boolean addDhtInfoSuccesscount(String infohash){
		return simpleJdbc.update("update dhtinfo set successcount=successcount+1,lastrequesttime=CURRENT_TIMESTAMP where  infohash=?", infohash)>0?true:false;
	}
	/* (non-Javadoc)
	 * @see com.buswe.dht.dao.DhtinfoDao#dhtinfoExsit(java.lang.String)
	 */
	@Override
	public Boolean dhtinfoExsit(String infohash)
	{
		return simpleJdbc.queryForInt("select count(1) from dhtinfo where infohash=?",infohash)>0?true:false ;
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
	 
		String updateSql=" update dhtinfo set creattime=:creattime,name=:name,singerfile=:singerfile,filelength=:filelength ,dhtstate=:dhtstate"
				+ " where infohash=:infohash";
		return namedjdbc.update(updateSql,  dhtinfoSqlParam(dhtinfo))>0?true:false;
	}

	@Override
	public List<Dhtinfo> getNotIndexedDhtinfo(Integer limit) {
		 String findSql="select * from dhtinfo where isindex=0 and dhtstate=0 limit ? ";
		 BeanPropertyRowMapper<Dhtinfo> infoRowMaper=new BeanPropertyRowMapper<Dhtinfo>(Dhtinfo.class);
		 List<Dhtinfo> list = simpleJdbc.query(findSql, infoRowMaper,limit);
		 for(Dhtinfo info:list)
		 {
			 info.setDhtfiles(getDhtFilesByinfohash(info.getInfohash()));
		 }
		 return list;
	}

	private List<Dhtfiles> getDhtFilesByinfohash(String infohash){
		String sql="select * from dhtfiles where infohash=?";
		 BeanPropertyRowMapper<Dhtfiles> rowMaper=new BeanPropertyRowMapper<Dhtfiles>(Dhtfiles.class);
		return simpleJdbc.query(sql, rowMaper, infohash);
	}
	
	
	@Override
	public Integer updateDhtinfoIndexed(List<Dhtinfo> dhtinfoList) {
	 String sql="update dhtinfo set isindex=1 where infohash=:infohash ";
	   Integer fileSize=dhtinfoList.size();
	   SqlParameterSource [] sqlparam=new SqlParameterSource[fileSize] ;
	      for(int i=0;i<fileSize;i++)
	      {
	    	  sqlparam[i]=dhtinfoSqlParam(dhtinfoList.get(i));
	      }
return	 namedjdbc.batchUpdate(sql, sqlparam).length;
	}

 

}
