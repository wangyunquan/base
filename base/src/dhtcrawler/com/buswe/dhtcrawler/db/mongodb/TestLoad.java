package com.buswe.dhtcrawler.db.mongodb;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.buswe.base.config.annotation.DaoConfig;
import com.buswe.dhtcrawler.db.models.DhtInfo_MongoDbPojo;
import com.buswe.dhtcrawler.entity.Dhtfiles;
import com.buswe.dhtcrawler.entity.Dhtinfo;
import com.buswe.dhtcrawler.entity.MongoToMySql;
import com.buswe.dhtcrawler.parser.MultiFile;
import com.buswe.dhtcrawler.parser.TorrentInfo;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


public class TestLoad {
static	int total=0;
	 static int fileadd=0;
	 static int infoadd=0;
	 static final  int bachnum=100;
	static SqlParameterSource[]  arrayFile=new SqlParameterSource[bachnum];	
	static SqlParameterSource[]  arrayInfo=new SqlParameterSource[bachnum];	
	static final String filesql="insert into dhtfiles (singlefilelength, path, info_hash) values(:singlefilelength, :path, :info_hash)";
static	String infosql="INSERT INTO dhtinfo (info_hash,peer_Ipport,lastrequesttime,tag,name,dhtstate,filelength,singerfile) values"
	 		+ " ( :infoHash,:peerIpport,:lastrequesttime,:tag,:name,:dhtstate,:filelength,:singerfile)";
	public static void main(String args[]) throws Exception
	{
		
		 AnnotationConfigApplicationContext ctx =
			     new AnnotationConfigApplicationContext();
			 ctx.register(DaoConfig.class);
			 ctx.refresh();
			 NamedParameterJdbcTemplate jdbc=	 ctx.getBean(NamedParameterJdbcTemplate.class);
			
			    ExecutorService service = Executors.newCachedThreadPool();
		 
			    	service.execute(new MongoToMySql(jdbc,"file"));
			 
			    	service.execute(new MongoToMySql(jdbc,"info"));
	 
  MongodbUtil dhtInfoDao = MongodbUtilProvider.getMongodbUtil();
 		 DBCollection col=  dhtInfoDao.getDBCollection("dht");
 		DBCursor curr = 	 col.find();
 			while (curr.hasNext()) {
 				DBObject dbo = curr.next();
 				DhtInfo_MongoDbPojo obj = dhtInfoDao.loadOne(DhtInfo_MongoDbPojo.class, dbo);
 				 Dhtinfo dhtinfo=new Dhtinfo();
 				 dhtinfo.setInfoHash(obj.getInfo_hash());
 				 dhtinfo.setPeerIpport(obj.getPeerIp());
 				 if(obj.getLastRequestsTime()!=0)
 				 dhtinfo.setLastrequesttime(new Date(obj.getLastRequestsTime()));
 				 dhtinfo.setDhtstate(obj.getAnalysised());
 				 dhtinfo.setTag(obj.getTag());
 				 if(obj.getTorrentInfo()!=null)
 				 {
 					TorrentInfo info=	obj.getTorrentInfo();
 					dhtinfo.setName(info.getName());
 					dhtinfo.setFilelength(info.getFilelenth());
 					dhtinfo.setSingerfile(info.isSingerFile());
 				 if(info.getCreattime()!=0)
 					dhtinfo.setCreattime(new Date(new Long(info.getCreattime())*1000));
 				 
 				 if(info.getMultiFiles()!=null)
 				 {
 					 for(MultiFile mfile:info.getMultiFiles())
 					 {
 						 Dhtfiles files=new Dhtfiles();
 						 files.setInfo_hash(obj.getInfo_hash());
 						 files.setPath(mfile.getPath());
 						 files.setSinglefilelength(mfile.getSingleFileLength());
 						MongoToMySql.filequeue.put(files);
 						// addFile(files,jdbc);
 					 }
 				 }
 				 }
 				MongoToMySql.infoqueue.put(dhtinfo);
 				//addInfo(dhtinfo,jdbc);
 		}
	}
	
//	 public static void addFile( Dhtfiles files,NamedParameterJdbcTemplate jdbc)
//	 {
//		 SqlParameterSource sqlParameterSourceFile=new BeanPropertySqlParameterSource(files);
//		 arrayFile[fileadd]=sqlParameterSourceFile;
//		 fileadd++;
//		 if(fileadd==bachnum)
//		 {
//	  jdbc.batchUpdate(filesql,  arrayFile);
//	 System.out.println("fileadd"+fileadd+"  and "+arrayFile.length);
//	 arrayFile=new SqlParameterSource[bachnum];
//	 fileadd=0;
//		 }
//	 }
//	 public static void addInfo( Dhtinfo files,NamedParameterJdbcTemplate jdbc)
//	 {
//		 SqlParameterSource sqlParameterSourceFile=new BeanPropertySqlParameterSource(files);
//		 arrayInfo[infoadd]=sqlParameterSourceFile;
//		 infoadd++;
//		 total++;
//		 if(infoadd==bachnum)
//		 {
//	  jdbc.batchUpdate(infosql,  arrayInfo);
//	// System.out.println("fileadd"+fileadd+"  and "+arrayFile.length);
//	 arrayInfo=new SqlParameterSource[bachnum];
//	 infoadd=0;
//	 System.out.println("total"+total);
//		 }
//	 }
 
	
 
}
