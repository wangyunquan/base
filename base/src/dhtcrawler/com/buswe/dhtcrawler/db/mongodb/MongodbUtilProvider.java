package com.buswe.dhtcrawler.db.mongodb;

import java.io.InputStream;
import java.util.Properties;

import com.buswe.dhtcrawler.db.models.DhtInfo_MongoDbPojo;
import com.buswe.dhtcrawler.db.models.PeerInfo;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class MongodbUtilProvider {
	public static MongodbUtil mongodbUtil;

	public static MongodbUtil getMongodbUtil() {

		if (mongodbUtil == null) {
			try {
				InputStream in = MongodbUtilProvider.class.getClassLoader().getResourceAsStream("config.properties");
				Properties p = new Properties();
				p.load(in);
				in.close();
				String ipAddress = p.getProperty("ipAddress", "localhost");
				String dbname = p.getProperty("dbname", "test");
				String prot = p.getProperty("prot", "27017");
				// Mongo m = new Mongo("localhost", 27017);
				// Mongo m = new Mongo("198.98.102.169", 27017);
				MongoOptions options = new MongoOptions();
				options.setConnectTimeout(20000);
				//options.setAutoConnectRetry(true);
				options.setSocketTimeout(15000);
				options.setMaxWaitTime(5000);
				options.setThreadsAllowedToBlockForConnectionMultiplier(5000);
				Mongo m = new Mongo(new ServerAddress(ipAddress, Integer.parseInt(prot)), options);
				DB db = m.getDB(dbname);
				// db.dropDatabase();
				mongodbUtil = new MongodbUtil(db);
				init(mongodbUtil);
				init2(mongodbUtil);

			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalArgumentException("mongodbUtil初始化失败:error=" + e);
			}
		}

		return mongodbUtil;
	}

	static void init(MongodbUtil mongodbUtil) {
		DBCollection dbCollection = mongodbUtil.getDBCollection(DhtInfo_MongoDbPojo.class);

		BasicDBObject basicDBObject1 = new BasicDBObject("info_hash", 1);

		BasicDBObject basicDBObject2 = new BasicDBObject();
		basicDBObject2.put("unique", true);
		basicDBObject2.put("dropDups", true);
//		dbCollection.createIndex(basicDBObject1);
	//	dbCollection.ensureIndex(basicDBObject1, basicDBObject2);// 创建唯一索引

		// db.Users.ensureIndex({name:1,sex:-1})

	}

	static void init2(MongodbUtil mongodbUtil) {
		DBCollection dbCollection = mongodbUtil.getDBCollection(PeerInfo.class);

		BasicDBObject basicDBObject1 = new BasicDBObject();
		basicDBObject1.put("ipAddress", 1);
		basicDBObject1.put("port", 1);

		BasicDBObject basicDBObject2 = new BasicDBObject();
		basicDBObject2.put("unique", true);
		basicDBObject2.put("dropDups", true);
	//	dbCollection.createIndex(basicDBObject1);
	//	dbCollection.ensureIndex(basicDBObject1, basicDBObject2);// 创建唯一索引

	}
}
