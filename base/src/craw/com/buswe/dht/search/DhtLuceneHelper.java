package com.buswe.dht.search;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;

import com.buswe.base.utils.LuceneUtils;
import com.buswe.dht.entity.Dhtfiles;
import com.buswe.dht.entity.Dhtinfo;

public class DhtLuceneHelper {
	private static final String NAME_FIELD = "name";
	public static final String INFO_HASH_FIELD = "infohash";
	// 分词的字段
	public static final String KEYWORD = "keyword";
	// StringField: 基础文本字段，可指定是否索引
	// StoredField: 仅存储不索引（也就是不能搜索、查询只能跟着文档取出来看）
	// TextField : 会在这上面应用分词器，用来做全文检索的
	public static  Document convertDocument(Dhtinfo dhtinfo) {
		Document doc = new Document();
		doc.add(new StringField(INFO_HASH_FIELD, dhtinfo.getInfohash(), Field.Store.YES));// StringField不参加分词
		doc.add(new StoredField(NAME_FIELD, dhtinfo.getName()));// StringField不参加分词
		//doc.add(new StoredField(TORRENTINFO_FIELD, object.get(TORRENTINFO_FIELD).toString()));// StringField不参加分词
		doc.add(new TextField(KEYWORD, getSegmentString(dhtinfo), Field.Store.NO));// 多文件的文件名
		return doc;
	}
	
	
 public static Query generateQuery(String searchString)
 {
	 //TODO 查询优化
		QueryParser qp = new QueryParser(KEYWORD, LuceneUtils.analyzer);
		Query query = qp.createBooleanQuery(KEYWORD, searchString, BooleanClause.Occur.MUST);
		return query;
 }
	
	
	private static String getSegmentString(Dhtinfo dhtinfo)
	{
		StringBuilder builder = new StringBuilder(dhtinfo.getName());
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
		return builder.toString();
	}
}
