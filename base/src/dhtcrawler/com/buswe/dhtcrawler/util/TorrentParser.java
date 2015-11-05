package com.buswe.dhtcrawler.util;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buswe.dhtcrawler.db.models.DhtInfoStateCode;
import com.buswe.dhtcrawler.db.models.DhtInfo_MongoDbPojo;
import com.buswe.dhtcrawler.db.mongodb.MongodbUtil;
import com.buswe.dhtcrawler.db.mongodb.MongodbUtilProvider;
import com.buswe.dhtcrawler.exception.DownLoadException;
import com.buswe.dhtcrawler.exception.ParseException;
import com.buswe.dhtcrawler.parser.TorrentInfo;

public class TorrentParser {
	
	/**
	 * 修改种子，添加网址信息
	 */
	  protected static Logger logger = LoggerFactory.getLogger(TorrentParser.class);
 	private static final String baseurl = "http://bt.box.n0808.com/%1$s/%2$s/%3$s.torrent";
	private static final String baseurl_2 = "http://magnet.vuze.com/magnetLookup?hash=%1$s";// base32
	private static final String baseurl_3 = "http://torrage.com/torrent/%1$s.torrent";//
	private static final String baseurl_4 = "http://torcache.net/torrent/%1$s.torrent";//
	private static final String baseurl_5 = "http://zoink.it/torrent/%1$s.torrent";//
    private static final  String baseurl_6="http://torcache.net/torrent/%1$s.torrent";
	private static final HttpUrlUtils httpUrlUtils = new HttpUrlUtils();
	private static final MongodbUtil dhtInfoDao = MongodbUtilProvider.getMongodbUtil();

	/*
	 * @category http://torrage.com/torrent/66B106B04F931DA3485282C43CF66F6BD795C8C4.torrent
	 * 
	 * @category http://torcache.net/torrent/66B106B04F931DA3485282C43CF66F6BD795C8C4.torrent
	 * 
	 * @category http://zoink.it/torrent/66B106B04F931DA3485282C43CF66F6BD795C8C4.torrent
	 * 
	 * @category http://magnet.vuze.com/magnetLookup?hash=ANRBNFHQ5CZM5BZBNSM4WXFDV4RQFHRX
	 * 
	 * @category http://bt.box.n0808.com/05/A5/05153F611B337A378F73F0D32D2C16D362D06BA5.torrent;
	 */
	private static TorrentInfo parseRequest(Request request) throws ParseException, DownLoadException {
		InputStream inputStream = null;

		TorrentInfo torrentInfo = null;
		try {
			inputStream = httpUrlUtils.performRequest(request);
			torrentInfo = new TorrentInfo(inputStream);
		} catch (DownLoadException e) {
			throw new DownLoadException("download is fails");
		} catch (ParseException e) {
			throw new ParseException("download is fails");
		} catch (IOException e) {
			throw new DownLoadException("download is fails");
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return torrentInfo;

	}

	/**
	 * 出错会依次解析，解析到最后一个位置
	 * 
	 * @param dhtInfo
	 */
	public static void parseDhtInfo(DhtInfo_MongoDbPojo dhtInfo) {
		try {
			parseDhtInfo_1(dhtInfo);
		
		} catch (Exception e) {
			try {
				
				parseDhtInfo_2(dhtInfo);
			} catch (Exception e1) {
				String[] uris = { baseurl_3, baseurl_4, baseurl_5 ,baseurl_6};
				parseDhtInfo_PublicMethod(dhtInfo, uris, uris.length - 1);
				System.out.println("下载ok");
			}
		}
	}

	/**
	 * 迅雷
	 * 
	 * @param dhtInfo
	 * @throws Exception
	 */
	public static void parseDhtInfo_1(DhtInfo_MongoDbPojo dhtInfo) throws Exception {
		String info_hash = dhtInfo.getInfo_hash().trim().toUpperCase();
		String url = String.format(baseurl, info_hash.substring(0, 2), info_hash.substring(info_hash.length() - 2, info_hash.length()), info_hash);
		Request request = new Request(url);

		try {
			TorrentInfo torrentInfo = parseRequest(request);

			dhtInfo.setAnalysised(DhtInfoStateCode.DOWNLOADSUCCESS_AND_PARSING_SUCCESS);
			dhtInfo.setTorrentInfo(torrentInfo);
			dhtInfoDao.update(dhtInfo);
			logger.debug("迅雷下载种子成功:"+url);
		} catch (DownLoadException downLoadException) {

			dhtInfo.setAnalysised(DhtInfoStateCode.DOWNLOAD_FAILED);
			dhtInfoDao.update(dhtInfo);
			throw new Exception(downLoadException);
		} catch (ParseException parseException) {
			dhtInfo.setAnalysised(DhtInfoStateCode.DOWNLOAD_SUCCESS_BUT_PARSING_FAILED);
			dhtInfoDao.update(dhtInfo);
			throw new Exception(parseException);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("修改数据库失败");
		}
	}

	
	/**
	 * Base32
	 * @param dhtInfo
	 * @throws Exception
	 */

	public static void parseDhtInfo_2(DhtInfo_MongoDbPojo dhtInfo) throws Exception {
		String hash = Base32.encode(Util.HexString2Bytes(dhtInfo.getInfo_hash().trim()));
		String url = String.format(baseurl_2, hash);
		Request request = new Request(url);
		try {
			TorrentInfo torrentInfo = parseRequest(request);

			dhtInfo.setAnalysised(DhtInfoStateCode.DOWNLOADSUCCESS_AND_PARSING_SUCCESS);
			dhtInfo.setTorrentInfo(torrentInfo);

			dhtInfoDao.update(dhtInfo);
			logger.debug("magnet.vuze.com下载种子成功:"+url);
		} catch (DownLoadException downLoadException) {

			dhtInfo.setAnalysised(DhtInfoStateCode.DOWNLOAD_FAILED);
			dhtInfoDao.update(dhtInfo);
			throw new Exception(downLoadException);

		} catch (ParseException parseException) {
			dhtInfo.setAnalysised(DhtInfoStateCode.DOWNLOAD_SUCCESS_BUT_PARSING_FAILED);
			dhtInfoDao.update(dhtInfo);

			throw new Exception(parseException);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 大众的解析方法 hash_info变大写
	 * 
	 * @param dhtInfo
	 * @throws Exception
	 */
	public static void parseDhtInfo_PublicMethod(DhtInfo_MongoDbPojo dhtInfo, String uri) throws Exception {
		String info_hash = dhtInfo.getInfo_hash().trim().toUpperCase();
		String url = String.format(uri, info_hash);
		Request request = new Request(url);
		try {
			TorrentInfo torrentInfo = parseRequest(request);
			dhtInfo.setAnalysised(DhtInfoStateCode.DOWNLOADSUCCESS_AND_PARSING_SUCCESS);
			dhtInfo.setTorrentInfo(torrentInfo);
			dhtInfoDao.update(dhtInfo);
			logger.debug("url下载种子成功:"+url);
		} catch (DownLoadException downLoadException) {

			dhtInfo.setAnalysised(DhtInfoStateCode.DOWNLOAD_FAILED);
			dhtInfoDao.update(dhtInfo);

			throw new Exception(downLoadException);
		} catch (ParseException parseException) {
			dhtInfo.setAnalysised(DhtInfoStateCode.DOWNLOAD_SUCCESS_BUT_PARSING_FAILED);
			dhtInfoDao.update(dhtInfo);

			throw new Exception(parseException);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void parseDhtInfo_PublicMethod(DhtInfo_MongoDbPojo dhtInfo, String[] uris, int i) {
		try {
			if (i >= 0 && i < uris.length) {
				parseDhtInfo_PublicMethod(dhtInfo, uris[i]);
			}
		} catch (Exception e) {
			parseDhtInfo_PublicMethod(dhtInfo, uris, i-1);
		}
	}

}
