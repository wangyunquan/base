package com.buswe.dhtcrawler.entity;

import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class MongoToMySql  implements Runnable{
public static 	LinkedBlockingQueue<Dhtfiles> filequeue=new LinkedBlockingQueue<Dhtfiles>();
public static 	LinkedBlockingQueue<Dhtinfo> infoqueue=new LinkedBlockingQueue<Dhtinfo>();
 	int total=0;
  int fileadd=0;
  int infoadd=0;
  final  int bachnum=1000;
  SqlParameterSource[]  arrayFile=new SqlParameterSource[bachnum];	
  SqlParameterSource[]  arrayInfo=new SqlParameterSource[bachnum];	
	static final String filesql="insert into dhtfiles (singlefilelength, path, info_hash) values(:singlefilelength, :path, :info_hash)";
static	String infosql="INSERT INTO dhtinfo (info_hash,peer_Ipport,lastrequesttime,tag,name,dhtstate,filelength,singerfile) values"
	 		+ " ( :infoHash,:peerIpport,:lastrequesttime,:tag,:name,:dhtstate,:filelength,:singerfile)";
NamedParameterJdbcTemplate jdbc;
String type;
public MongoToMySql(NamedParameterJdbcTemplate jdbc,String type)
{
	this.jdbc=jdbc;
	this.type=type;
	}
@Override
public void run() {

		while(true)
		{
			if(type.equals("file"))
			{
				try {
					Dhtfiles file=filequeue.take();
					addFile(file);
				} catch (InterruptedException e) {
				 
					e.printStackTrace();
				}
		}
			else
			{
				try {
					Dhtinfo info=		infoqueue.take();
					addInfo(info);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
}
public   void addFile( Dhtfiles files)
{
	 SqlParameterSource sqlParameterSourceFile=new BeanPropertySqlParameterSource(files);
	 arrayFile[fileadd]=sqlParameterSourceFile;
	 fileadd++;
	 if(fileadd==bachnum)
	 {
		 try{
 jdbc.batchUpdate(filesql,  arrayFile);
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 arrayFile=new SqlParameterSource[bachnum];
			 fileadd=0;
		 }
arrayFile=new SqlParameterSource[bachnum];
fileadd=0;
	 }
}
public   void addInfo( Dhtinfo files)
{
	 SqlParameterSource sqlParameterSourceFile=new BeanPropertySqlParameterSource(files);
	 arrayInfo[infoadd]=sqlParameterSourceFile;
	 infoadd++;
	 total++;
	 if(infoadd==bachnum)
	 {
		 try{
 jdbc.batchUpdate(infosql,  arrayInfo);
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
		 infoadd=0;
		 arrayInfo=new SqlParameterSource[bachnum];
	 }
// System.out.println("fileadd"+fileadd+"  and "+arrayFile.length);
arrayInfo=new SqlParameterSource[bachnum];
infoadd=0;
System.out.println("total"+total);
	 }
}

}
