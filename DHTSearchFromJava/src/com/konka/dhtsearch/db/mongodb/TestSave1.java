package com.konka.dhtsearch.db.mongodb;

import com.konka.dhtsearch.db.models.DhtInfo_MongoDbPojo;
import com.konka.dhtsearch.parser.TorrentInfo;

public class TestSave1 {
	public static void main(String args[]) throws Exception {
//		Mongo m = new Mongo("localhost", 27017);
//		DB db = m.getDB("test");
//
//		 db.dropDatabase();
//		MongodbUtil orm = new MongodbUtil(db);

//		List<MultiFile> lists = new ArrayList<>();
//
//		for (int i = 0; i < 5; i++) {
//			MultiFile multiFile = new MultiFile();
//			multiFile.setPath("c:/ddd/we/ddd" + i);
//			multiFile.setSingleFileLength(123545222l);
//			lists.add(multiFile);
//		}
		DhtInfo_MongoDbPojo dhtInfo_MongoDbPojo = new DhtInfo_MongoDbPojo();
		dhtInfo_MongoDbPojo.setInfo_hash("的打发打发发达省份费大幅12");
		TorrentInfo torrentInfo=new TorrentInfo("D:/a2.torrent");
		dhtInfo_MongoDbPojo.setTorrentInfo(torrentInfo);
		
		MongodbUtilProvider.getMongodbUtil().save(dhtInfo_MongoDbPojo);;
		MongodbUtilProvider.getMongodbUtil().save(dhtInfo_MongoDbPojo);;
//		MongodbUtilProvider.getMongodbUtil().save(dhtInfo_MongoDbPojo);;
//		MongodbUtilProvider.getMongodbUtil().save(dhtInfo_MongoDbPojo);;
//		DhtInfo dhtInfo = new DhtInfo();
//		dhtInfo.setAnalysised(200);
//		dhtInfo.setCreateTime(123456755);
//		dhtInfo.setFileList(lists);
//		dhtInfo.setFileName("我是文件名");
//		dhtInfo.setFileSize(12345675);
//		dhtInfo.setInfo_hash("125515241534531fds");
//		dhtInfo.setLastRequestsTime(125454125121L);
//		dhtInfo.setPeerIp("127.0.0.1:8811");
//		dhtInfo.setTag("tag");
//		dhtInfo.setTorrentFilePath("path");
//		orm.save(dhtInfo_MongoDbPojo);
		System.out.println("dddd");
	}
}
