package com.buswe.dht.search;

import java.util.Date;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

import com.buswe.base.utils.LuceneUtils;
import com.buswe.dht.entity.Dhtfiles;
import com.buswe.dht.entity.Dhtinfo;

public class DhtLuceneHelper {
	public static final String NAME_FIELD = "name";
	public static final String INFO_HASH_FIELD = "infohash";
	// 分词的字段
	public static final String FILELIST = "filelist";
	public static final String CREATTIME="creattime";
	// StringField: 基础文本字段，可指定是否索引
	// StoredField: 仅存储不索引（也就是不能搜索、查询只能跟着文档取出来看）
	// TextField : 会在这上面应用分词器，用来做全文检索的
	public static  Document convertDocument(Dhtinfo dhtinfo) {
		Document doc = new Document();
		doc.add(new StringField(INFO_HASH_FIELD, dhtinfo.getInfohash(), Field.Store.YES));// StringField不参加分词
		TextField nameFiled =  new TextField(NAME_FIELD, dhtinfo.getName(), Field.Store.NO);
		nameFiled.setBoost(100);//提高优先级
		doc.add(nameFiled); 
		//doc.add(new StoredField(TORRENTINFO_FIELD, object.get(TORRENTINFO_FIELD).toString()));//  
		doc.add(new TextField(FILELIST, getSegmentString(dhtinfo), Field.Store.NO));// 多文件的文件名
		if(dhtinfo.getCreattime()!=null)
		{
			LongField timeField = new LongField(CREATTIME, dhtinfo.getCreattime().getTime(),  Store.NO);
			doc.add(timeField);
		}
		else
		{
			LongField timeField = new LongField(CREATTIME, 0,  Store.NO);
			doc.add(timeField);
		}
		return doc;
	}
	
	
 public static Query generateQuery(String searchString)
 {
	 BooleanQuery bq = new BooleanQuery();
	 try{
	 QueryParser nameQp = new QueryParser(NAME_FIELD, LuceneUtils.analyzer);
	 Query nameQuery=nameQp.parse(searchString);
	 nameQuery.setBoost(100);
		QueryParser fileQp = new QueryParser(FILELIST, LuceneUtils.analyzer);
		Query  fileQuery=fileQp.parse(searchString);
		 bq.add(nameQuery,BooleanClause.Occur.SHOULD);
		 bq.add(fileQuery,BooleanClause.Occur.SHOULD);
	//	Query query = qp.createBooleanQuery(FILELIST, searchString, BooleanClause.Occur.MUST);
	 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return bq;
 }
	
	
	private static String getSegmentString(Dhtinfo dhtinfo)
	{
		StringBuilder builder = new StringBuilder("");
		if(!dhtinfo.getSingerfile())
		{
			 List<Dhtfiles> dhtFiles=		dhtinfo.getDhtfiles();
			 if(dhtFiles!=null&&dhtFiles.size()>0)
			 {
				 for(Dhtfiles file:dhtFiles)
				 {
					 builder.append(file.getPath());
				 }
			 }
		}
		else
		{
			builder.append(dhtinfo.getName());
		}
		return builder.toString();
	}
	

	
}
