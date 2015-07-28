package com.buswe.moudle.cms.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.SearcherManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buswe.moudle.cms.CmsConstants;
import com.buswe.moudle.cms.entity.Article;
import com.buswe.moudle.cms.helper.PageHelper;

public class IndexHelper
{
  private static Logger logger = LoggerFactory.getLogger(IndexHelper.class);
  boolean isAutoCommit = true;
  private IndexWriter indexWriter;
  Analyzer analyzer;
  private SearcherManager searcherManager;
  
  public static String getKeywords(String s, boolean useSmart)
  {
    return null;
  }
  
  public void addDocument(Document document)
  {
    try
    {
      this.indexWriter.addDocument(document);
      if (this.isAutoCommit) {
        this.indexWriter.commit();
      }
    }
    catch (Exception e)
    {
      throw new LuceneException("Error during adding a document.", e);
    }
  }
  
  public void deleteDocuments(Query query)
  {
    try
    {
      this.indexWriter.deleteDocuments(new Query[] { query });
      if (this.isAutoCommit) {
        this.indexWriter.commit();
      }
    }
    catch (Exception e)
    {
      throw new LuceneException("Error during deleting a document.", e);
    }
  }
  
  public Document getDocument(Article info)
  {
    Document doc = new Document();
    
    doc.add(new StringField("id", info.getId(), Field.Store.YES));
    
    doc.add(new TextField("title", info.getTitle(), Field.Store.NO));
    
    doc.add(new TextField("keywords", info.getKeywords(), Field.Store.NO));
    
    String content = PageHelper.getTextWithoutPageBreak(info
      .getArticleData().getLobContent());
    
    doc.add(new TextField("text", content, Field.Store.NO));
    return doc;
  }
  
  /* Error */
  public org.springframework.data.domain.Page<String> page(Query query, String field, org.springframework.data.domain.Pageable pageable)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aload_0
    //   4: getfield 145	yunquan/wang/moudle/cms/search/IndexHelper:searcherManager	Lorg/apache/lucene/search/SearcherManager;
    //   7: invokevirtual 147	org/apache/lucene/search/SearcherManager:maybeRefresh	()Z
    //   10: pop
    //   11: aload_0
    //   12: getfield 145	yunquan/wang/moudle/cms/search/IndexHelper:searcherManager	Lorg/apache/lucene/search/SearcherManager;
    //   15: invokevirtual 153	org/apache/lucene/search/SearcherManager:acquire	()Ljava/lang/Object;
    //   18: checkcast 157	org/apache/lucene/search/IndexSearcher
    //   21: astore 4
    //   23: aload_3
    //   24: invokeinterface 159 1 0
    //   29: aload_3
    //   30: invokeinterface 165 1 0
    //   35: iadd
    //   36: istore 5
    //   38: aload 4
    //   40: aload_1
    //   41: iload 5
    //   43: invokevirtual 168	org/apache/lucene/search/IndexSearcher:search	(Lorg/apache/lucene/search/Query;I)Lorg/apache/lucene/search/TopDocs;
    //   46: astore 6
    //   48: aload 6
    //   50: getfield 172	org/apache/lucene/search/TopDocs:scoreDocs	[Lorg/apache/lucene/search/ScoreDoc;
    //   53: arraylength
    //   54: istore 7
    //   56: iload 7
    //   58: aload_3
    //   59: invokeinterface 159 1 0
    //   64: isub
    //   65: istore 8
    //   67: iload 8
    //   69: ifle +70 -> 139
    //   72: new 178	java/util/ArrayList
    //   75: dup
    //   76: iload 8
    //   78: invokespecial 180	java/util/ArrayList:<init>	(I)V
    //   81: astore 9
    //   83: aload_3
    //   84: invokeinterface 159 1 0
    //   89: istore 11
    //   91: goto +38 -> 129
    //   94: aload 6
    //   96: getfield 172	org/apache/lucene/search/TopDocs:scoreDocs	[Lorg/apache/lucene/search/ScoreDoc;
    //   99: iload 11
    //   101: aaload
    //   102: astore 10
    //   104: aload 9
    //   106: aload 4
    //   108: aload 10
    //   110: getfield 183	org/apache/lucene/search/ScoreDoc:doc	I
    //   113: invokevirtual 188	org/apache/lucene/search/IndexSearcher:doc	(I)Lorg/apache/lucene/document/Document;
    //   116: aload_2
    //   117: invokevirtual 191	org/apache/lucene/document/Document:get	(Ljava/lang/String;)Ljava/lang/String;
    //   120: invokeinterface 194 2 0
    //   125: pop
    //   126: iinc 11 1
    //   129: iload 11
    //   131: iload 7
    //   133: if_icmplt -39 -> 94
    //   136: goto +8 -> 144
    //   139: invokestatic 199	java/util/Collections:emptyList	()Ljava/util/List;
    //   142: astore 9
    //   144: aload 6
    //   146: getfield 205	org/apache/lucene/search/TopDocs:totalHits	I
    //   149: istore 10
    //   151: new 208	org/springframework/data/domain/PageImpl
    //   154: dup
    //   155: aload 9
    //   157: aload_3
    //   158: iload 10
    //   160: i2l
    //   161: invokespecial 210	org/springframework/data/domain/PageImpl:<init>	(Ljava/util/List;Lorg/springframework/data/domain/Pageable;J)V
    //   164: astore 13
    //   166: aload 4
    //   168: ifnull +12 -> 180
    //   171: aload_0
    //   172: getfield 145	yunquan/wang/moudle/cms/search/IndexHelper:searcherManager	Lorg/apache/lucene/search/SearcherManager;
    //   175: aload 4
    //   177: invokevirtual 213	org/apache/lucene/search/SearcherManager:release	(Ljava/lang/Object;)V
    //   180: aload 13
    //   182: areturn
    //   183: astore 12
    //   185: aload 4
    //   187: ifnull +12 -> 199
    //   190: aload_0
    //   191: getfield 145	yunquan/wang/moudle/cms/search/IndexHelper:searcherManager	Lorg/apache/lucene/search/SearcherManager;
    //   194: aload 4
    //   196: invokevirtual 213	org/apache/lucene/search/SearcherManager:release	(Ljava/lang/Object;)V
    //   199: aload 12
    //   201: athrow
    //   202: astore 4
    //   204: new 52	yunquan/wang/moudle/cms/search/LuceneException
    //   207: dup
    //   208: ldc 217
    //   210: aload 4
    //   212: invokespecial 56	yunquan/wang/moudle/cms/search/LuceneException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   215: athrow
    // Line number table:
    //   Java source line #136	-> byte code offset #0
    //   Java source line #138	-> byte code offset #3
    //   Java source line #143	-> byte code offset #11
    //   Java source line #144	-> byte code offset #23
    //   Java source line #145	-> byte code offset #38
    //   Java source line #146	-> byte code offset #48
    //   Java source line #147	-> byte code offset #56
    //   Java source line #149	-> byte code offset #67
    //   Java source line #150	-> byte code offset #72
    //   Java source line #152	-> byte code offset #83
    //   Java source line #153	-> byte code offset #94
    //   Java source line #154	-> byte code offset #104
    //   Java source line #152	-> byte code offset #126
    //   Java source line #156	-> byte code offset #136
    //   Java source line #157	-> byte code offset #139
    //   Java source line #159	-> byte code offset #144
    //   Java source line #160	-> byte code offset #151
    //   Java source line #162	-> byte code offset #166
    //   Java source line #163	-> byte code offset #171
    //   Java source line #160	-> byte code offset #180
    //   Java source line #161	-> byte code offset #183
    //   Java source line #162	-> byte code offset #185
    //   Java source line #163	-> byte code offset #190
    //   Java source line #165	-> byte code offset #199
    //   Java source line #166	-> byte code offset #202
    //   Java source line #167	-> byte code offset #204
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	216	0	this	IndexHelper
    //   0	216	1	query	Query
    //   0	216	2	field	String
    //   0	216	3	pageable	org.springframework.data.domain.Pageable
    //   1	194	4	searcher	org.apache.lucene.search.IndexSearcher
    //   202	9	4	e	Exception
    //   36	6	5	n	int
    //   46	99	6	results	org.apache.lucene.search.TopDocs
    //   54	78	7	length	int
    //   65	12	8	size	int
    //   81	24	9	content	java.util.List<String>
    //   142	14	9	content	java.util.List<String>
    //   102	7	10	hit	org.apache.lucene.search.ScoreDoc
    //   149	10	10	total	int
    //   89	41	11	i	int
    //   183	17	12	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   3	166	183	finally
    //   0	180	202	java/lang/Exception
    //   183	202	202	java/lang/Exception
  }
  
  public Query buildQuery(String keywords)
  {
    try
    {
      return MultiFieldQueryParser.parse(
       keywords, new String[] { "title", "text" }, 
        new BooleanClause.Occur[] { BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD }, this.analyzer);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
