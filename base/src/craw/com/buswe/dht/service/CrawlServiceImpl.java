package com.buswe.dht.service;

import java.io.File;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.buswe.base.config.ContextHolder;
import com.buswe.base.config.SystemEnvy;
import com.buswe.base.utils.LuceneUtils;
import com.buswe.base.utils.Threads;
import com.buswe.dht.entity.Dhtinfo;
import com.buswe.dht.node.DhtKeyFactory;
import com.buswe.dht.node.KadNet;
import com.buswe.dht.node.Node;
import com.buswe.dht.save.InsertDhtfilesThread;
import com.buswe.dht.save.KadParserTorrentServer;
import com.buswe.dht.save.SaveDhtThread;
import com.buswe.dht.search.DhtLuceneHelper;

@Service
public class CrawlServiceImpl implements CrawlService {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private String dhtIndexDir = SystemEnvy.WEBROOT + File.separator + "dhtIndex";
	@Resource
	DhtinfoService dhtinfoService;
	private KadParserTorrentServer parseServer;
	private SaveDhtThread saveToDbThread;
	private List<KadNet> kadnetList;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.buswe.dht.service.CrawlService#startDhtService(java.lang.Integer)
	 */
	@Override
	@Async // 异步执行
	public void startDhtService() {
		try {
			InetSocketAddress[] BOOTSTRAP_NODES = { //
					new InetSocketAddress("router.bittorrent.com", 6881), //
					new InetSocketAddress("dht.transmissionbt.com", 6881), //
					new InetSocketAddress("router.utorrent.com", 6881), };
			String  nodeport=ContextHolder.getProperty("dht.craw.config.node.nodeport");
			String totalNode=ContextHolder.getProperty("dht.craw.config.node.totalNode");
			kadnetList = new ArrayList<KadNet>();
			DhtKeyFactory keyFactory = DhtKeyFactory.getInstance();
			for (int i = 0; i < Integer.valueOf(totalNode); i++) {
				Node localNode = new Node(keyFactory.generate()).setInetAddress(InetAddress.getByName("0.0.0.0"))
						.setPoint(Integer.valueOf(nodeport) + i);// 这里注意InetAddress.getLocalHost();为空
				KadNet kadNet = new KadNet(null, localNode);
				kadNet.join(BOOTSTRAP_NODES).create();
				kadnetList.add(kadNet);
			}
			saveToDbThread = new SaveDhtThread(); // 保存到数据库的线程
			saveToDbThread.start();
			// 解析 dhtinfo的线程
			parseServer = new KadParserTorrentServer();
			parseServer.start();
			
			InsertDhtfilesThread insertFileThread=new InsertDhtfilesThread();
			insertFileThread.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stopDhtService()
	{
		for(KadNet kadnet:kadnetList)
		{
			kadnet.shutdown();
			kadnet=null;
		}
		saveToDbThread.shutdown();
		parseServer.shutdown();
		
	}
//正式的时候，必须打开这个，TODO
 // @Scheduled(cron="0 10 * * * ? ") //每天早上5点运行定时任务建立索引
  public void creatIndex() throws Exception
  {
		  creatIndex(1000);
  }
	public void creatIndex(Integer total) throws Exception {
		Analyzer analyzer = LuceneUtils.analyzer;
		File indexFolder = new File(dhtIndexDir);
		if (!indexFolder.exists()) {
			indexFolder.mkdirs();
		}
		FSDirectory dir = FSDirectory.open(new File(dhtIndexDir));
		dir.setReadChunkSize(104857600);// 100兆//TODO
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
		config.setOpenMode(OpenMode.CREATE_OR_APPEND);
		IndexWriter writer = new IndexWriter(dir, config);
		int doc_count = 0;
		List<Dhtinfo> dhtinfoList = dhtinfoService.getNotIndexedDhtinfo(total);
		if(dhtinfoList==null||dhtinfoList.size()==0)
		{
			Threads.sleep(1000*60*60);
		}
		try {
			for (Dhtinfo dhtinfo : dhtinfoList) {
				Document doc = DhtLuceneHelper.convertDocument(dhtinfo);
				writer.addDocument(doc);
				doc_count++;
			}
			writer.commit();
			dhtinfoService.updateDhtinfoIndexed(dhtinfoList);
			logger.debug("批量添加索引成功:" + doc_count);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
				writer = null;
			}
			if (dir != null) {
				dir.close();
				dir = null;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.buswe.dht.service.CrawlService#search(java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Dhtinfo> search(String searchString, Pageable page) throws Exception {
		Integer pageNumber = page.getPageNumber();
		Integer pageSize = page.getPageSize();
		FSDirectory index = FSDirectory.open(new File(dhtIndexDir));
		index.setReadChunkSize(104857600);// 100兆
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(20000, true);
		Query query = DhtLuceneHelper.generateQuery(searchString);
		searcher.search(query, collector);
		Integer total = collector.getTotalHits();// 总数
		ScoreDoc[] hits = collector.topDocs(pageNumber * pageSize, pageSize).scoreDocs; // 进行分页过滤
		List<Dhtinfo> list = new ArrayList<>();
		for (int i = 0; i < hits.length; ++i) {
			Document document = searcher.doc(hits[i].doc);
			String infohash = document.get(DhtLuceneHelper.INFO_HASH_FIELD);
			Dhtinfo info = dhtinfoService.loadByInfoHash(infohash);
			list.add(info);
		}
		PageImpl<Dhtinfo> result = new PageImpl<Dhtinfo>(list, page, total);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.buswe.dht.service.CrawlService#loadDhtinfo(java.lang.String)
	 */
	@Override
	public Dhtinfo loadDhtinfo(String infoHash) {
		// TODO 查看一次，增加一次点击
		return dhtinfoService.loadByInfoHash(infoHash);

	}
}
