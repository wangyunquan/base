package com.konka.dhtsearch.util;

import java.util.List;

import com.konka.dhtsearch.parser.MultiFile;
import com.konka.dhtsearch.parser.TorrentInfo;

public class FilterUtil {
	public static String[] videoTypes = { ".AVI", ".3GP", ".RM", ".MP4", ".MEPG", ".WMA", ".RMVB", ".FLV" };

	static boolean idVideoType(String fileName) {
		for (String videoType : videoTypes) {
			if (fileName.toUpperCase().endsWith(videoType)) {
				return true;
			}
		}
		return false;
	}

	public static boolean checkVideoType(TorrentInfo torrentInfo) {
		if (torrentInfo.isSingerFile()) {
			return idVideoType(torrentInfo.getName());
		} else {
			List<MultiFile> multiFiles = torrentInfo.getMultiFiles();
			for (MultiFile multiFile : multiFiles) {
				return idVideoType(multiFile.getPath());
			}
		}
		return false;
	}
}
