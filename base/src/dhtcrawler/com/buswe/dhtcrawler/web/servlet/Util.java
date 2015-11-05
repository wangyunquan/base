package com.buswe.dhtcrawler.web.servlet;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.buswe.dhtcrawler.db.luncene.LuceneSearchResult;
import com.buswe.dhtcrawler.db.models.DhtInfo_MongoDbPojo;
import com.buswe.dhtcrawler.parser.MultiFile;
import com.buswe.dhtcrawler.web.entity.FileInfo;
import com.buswe.dhtcrawler.web.entity.SearchResultInfo;
import com.buswe.dhtcrawler.web.entity.SubFileInfo;

public class Util {
	public static String getFormatSize(long size) {
		DecimalFormat formater = new DecimalFormat("####.0");
		if (size < 1024) {
			return size + "byte";
		} else if (size < 1024l * 1024l) {
			float kbsize = size / 1024f;
			return formater.format(kbsize) + "KB";
		} else if (size < 1024l * 1024l * 1024l) {
			float mbsize = size / 1024f / 1024f;
			return formater.format(mbsize) + "MB";
		} else if (size < (1024l * 1024l * 1024l * 1024l)) {
			float gbsize = size / 1024f / 1024f / 1024f;
			return formater.format(gbsize) + "GB";
		} else if (size < 1024 * 1024 * 1024 * 1024 * 1024) {
			float gbsize = size / 1024f / 1024f / 1024f / 1024f;
			return formater.format(gbsize) + "TB";
		} else {
			return "未知";
		}
	}

	public static String getFormatCreatTime(long time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
		String ctime = formatter.format(time);
		return ctime;
	}

	public static SearchResultInfo luceneSearchResult2SearchResultInfo(LuceneSearchResult luceneSearchResult) {
		SearchResultInfo searchResultInfo = new SearchResultInfo();
		searchResultInfo.setTotal(luceneSearchResult.getTotal());
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		for (DhtInfo_MongoDbPojo dbPojo : luceneSearchResult.getLists()) {
			FileInfo fileInfo = new FileInfo();
			fileInfo.setCreateTime(dbPojo.getTorrentInfo().getFormatCreatTime());
			fileInfo.setFileSize(dbPojo.getTorrentInfo().getFormatSize());
			fileInfo.setFileType("视频");
			fileInfo.setInfo_hash(dbPojo.getInfo_hash());
			fileInfo.setName(dbPojo.getTorrentInfo().getName());
			List<SubFileInfo> subFileInfos = new ArrayList<SubFileInfo>();
			if (dbPojo.getTorrentInfo().isSingerFile()) {
				fileInfo.setSubFileCount(1);
			} else {
				List<MultiFile> multiFiles = dbPojo.getTorrentInfo().getMultiFiles();
				fileInfo.setSubFileCount(multiFiles.size());
				for (MultiFile multiFile : multiFiles) {
					SubFileInfo subFileInfo = new SubFileInfo();
					subFileInfo.setName(multiFile.getPath());
					subFileInfo.setSubFileSize(multiFile.getFormatSize());
					subFileInfos.add(subFileInfo);
				}
			}
			fileInfo.setSubfileInfos(subFileInfos);
			fileInfos.add(fileInfo);
		}
		searchResultInfo.setFileInfos(fileInfos);
		return searchResultInfo;
	}
}
