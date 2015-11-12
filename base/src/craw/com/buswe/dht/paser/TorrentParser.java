package com.buswe.dht.paser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaircc.torrent.bencoding.BEncodedInputStream;
import org.yaircc.torrent.bencoding.BMap;

import com.buswe.dht.entity.Dhtfiles;
import com.buswe.dht.entity.Dhtinfo;
import com.buswe.dht.entity.DhtinfoState;

/**
 * 种子解析器
 * 
 * @author 王云权
 *
 */
public class TorrentParser implements TorrentConstantKey {
	protected static Logger logger = LoggerFactory.getLogger(TorrentParser.class);

	public static Boolean parse(InputStream in,Dhtinfo dhtinfo) {
		BEncodedInputStream bEncodedInputStream = new BEncodedInputStream(in);
		try {
			BMap bMap = (BMap) bEncodedInputStream.readElement();
			String encoding = UTF_8;// 编码方式 默认UTF-8
			if (bMap.containsKey(ENCODING)) {
				encoding = bMap.getString(ENCODING);
			}
			if (bMap.containsKey(CREATION_DATE)) { // 产生日期
				Long creattime = bMap.getLong(CREATION_DATE);
				Date date = new Date(new Long(creattime) * 1000);
				dhtinfo.setCreattime(date);
			}
			if (bMap.containsKey(INFO)) { // 种子信息
				BMap infoMap = bMap.getMap(INFO);
				if (infoMap != null) {
					String name = null;
					if (infoMap.containsKey(NAME_UTF_8)) { // 名称
						byte[] dd = (byte[]) infoMap.get(NAME_UTF_8);
						name = new String(dd, UTF_8);
					} else if (infoMap.containsKey(NAME)) {
						byte[] namearray = (byte[]) infoMap.get(NAME);
						name = new String(namearray, encoding);
					}
					dhtinfo.setName(name); //
					Long filelenth =0L;
					if (infoMap.containsKey(LENGTH)) {
						  filelenth = infoMap.getLong(LENGTH);
					}
						if (infoMap.containsKey(FILES)) { // 如果有子文件
							List<Object> filesMap = infoMap.getList(FILES);
							List<Dhtfiles> multiFiles = new ArrayList<Dhtfiles>(filesMap.size());
							Dhtfiles multiFile;
							for (Object multiFileobObject : filesMap) {
								BMap multiFilemap = (BMap) multiFileobObject;
								multiFile = new Dhtfiles();
								multiFile.setInfohash(dhtinfo.getInfohash());
								if (multiFilemap.containsKey(PATH_UTF_8)) {
									List<byte[]> utf8_Path_Bytes_List = (List<byte[]>) multiFilemap.get(PATH_UTF_8);
									for (byte[] utf8_Path_Bytes : utf8_Path_Bytes_List) {
										String path = new String(utf8_Path_Bytes, UTF_8);
										multiFile.setPath(path);
									}
								} else {
									List<byte[]> pathListbytearray = (List<byte[]>) multiFilemap.get(PATH);
									for (byte[] pathbytearray : pathListbytearray) {
										String path = new String(pathbytearray, encoding);
										multiFile.setPath(path);
									}
								}
								if (multiFilemap.containsKey(LENGTH)) {
									long length = multiFilemap.getLong(LENGTH);
									multiFile.setSinglefilelength(length);
									filelenth += length;
								}
								multiFiles.add(multiFile);
							}
							dhtinfo.setDhtfiles(multiFiles);
							dhtinfo.setSingerfile(false);
						} // 子文件解析完毕
						else
						{
							dhtinfo.setSingerfile(true);
						}
						dhtinfo.setFilelength(filelenth);
						dhtinfo.setDhtstate(DhtinfoState.DHTSTATE_OK);
				}
			} else {
				logger.debug("种子解析失败：" +dhtinfo.getInfohash());
				dhtinfo.setDhtstate(DhtinfoState.DHTSTATE_PARSING_FAIL);
				return false;
			}
		} catch (Exception e) {
			dhtinfo.setDhtstate(DhtinfoState.DHTSTATE_PARSING_FAIL);
			return false;
		} finally
		{
			try {
				bEncodedInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
}
