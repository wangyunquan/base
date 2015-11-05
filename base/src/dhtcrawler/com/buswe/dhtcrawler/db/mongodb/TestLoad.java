package com.buswe.dhtcrawler.db.mongodb;
import java.util.List;

import com.buswe.dhtcrawler.db.models.DhtInfo_MongoDbPojo;
import com.mongodb.DB;
import com.mongodb.Mongo;


public class TestLoad {
	 
	public static void main(String args[]) throws Exception
	{
		Mongo m = new Mongo();
		DB db = m.getDB("test");
		MongodbUtil orm = new MongodbUtil(db);
		
//		List<Employee> employee = orm.loadAll(Employee.class);
		List<DhtInfo_MongoDbPojo> project = orm.findAll(DhtInfo_MongoDbPojo.class);
//		List<Manager> manager = orm.loadAll(Manager.class);
		
		
//		System.out.println(project.get(0).getTorrentInfo().getMultiFiles().get(0).getPath());
//		System.out.println(project.get(0).getTorrentInfo().getMultiFiles().get(0).getSingleFileLength());
	}
}
