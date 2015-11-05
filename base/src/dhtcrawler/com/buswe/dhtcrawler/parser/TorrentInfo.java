package com.buswe.dhtcrawler.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.yaircc.torrent.bencoding.BDecodingException;
import org.yaircc.torrent.bencoding.BEncodedInputStream;
import org.yaircc.torrent.bencoding.BMap;
import org.yaircc.torrent.bencoding.BTypeException;

import com.buswe.dhtcrawler.db.mongodb.MongoCollection;
import com.buswe.dhtcrawler.exception.DownLoadException;
import com.buswe.dhtcrawler.exception.ParseException;
import com.buswe.dhtcrawler.util.KLog;
import com.buswe.dhtcrawler.util.StringUtil;
import com.buswe.dhtcrawler.util.Util;

@MongoCollection
public class TorrentInfo implements TorrentConstantKey {
	private String name;// 文件名称
	private long filelenth;// 文件大小 单位 byte(总文件大小)
	private long creattime;// 创建时间 creation date
	private List<MultiFile> multiFiles;
	private boolean singerFile = true;// 是否是单文件 如果是多文件，文件放假multiFiles中

	public String getFormatSize() {
		return Util.getFormatSize(filelenth);
	}

	// public String getHighlighterName(){
	// return getName().replace("", newChar)
	// }
	public String getFormatCreatTime() {
		return Util.getFormatCreatTime(creattime * 1000);
	}

	public String getName() {
		return name;
	}

	public long getFilelenth() {
		return filelenth;
	}

	public long getCreattime() {
		return creattime;
	}

	public List<MultiFile> getMultiFiles() {
		return multiFiles;
	}

	public boolean isSingerFile() {
		return singerFile;
	}

	public TorrentInfo(InputStream in) throws ParseException {
		BEncodedInputStream bEncodedInputStream = new BEncodedInputStream(in);
		try {
			try {
				parser(bEncodedInputStream);
			} catch (Exception e) {
				throw new ParseException("download is fails");
			}
		} finally {
			try {
				if (bEncodedInputStream != null) {
					bEncodedInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void parser(BEncodedInputStream bEncodedInputStream) throws IOException, BDecodingException, BTypeException {
		BMap bMap = (BMap) bEncodedInputStream.readElement();
		KLog.println(bMap);
		String encoding = null;// 编码方式 utf-8
		if (bMap.containsKey(ENCODING)) {
			encoding = bMap.getString(ENCODING);
			System.out.println("encoding======" + encoding);
		}
		if (bMap.containsKey(CREATION_DATE)) {
			creattime = bMap.getLong(CREATION_DATE);
			System.out.println("creattime=====" + creattime);
		}
		if (bMap.containsKey(INFO)) {
			BMap infoMap = bMap.getMap(INFO);
			if (infoMap != null) {
				if (infoMap.containsKey(NAME_UTF_8)) {
					byte[] dd = (byte[]) infoMap.get(NAME_UTF_8);
					name = new String(dd, UTF_8);
				} else if (infoMap.containsKey(NAME)) {
					byte[] namearray = (byte[]) infoMap.get(NAME);
					name = new String(namearray, StringUtil.isEmpty(encoding) ? UTF_8 : encoding);
				}
				KLog.println("name--utf8=" + name);

				if (infoMap.containsKey(LENGTH)) {
					filelenth = infoMap.getLong(LENGTH);
					KLog.println("filelenth=" + filelenth);
				}
				if (infoMap.containsKey(FILES)) {
					List<Object> filesMap = infoMap.getList(FILES);
					multiFiles = new ArrayList<MultiFile>(filesMap.size());
					MultiFile multiFile;
					for (Object multiFileobObject : filesMap) {
						BMap multiFilemap = (BMap) multiFileobObject;
						multiFile = new MultiFile();
						// System.out.println(multiFilemap);

						if (multiFilemap.containsKey(PATH_UTF_8)) {
							List<byte[]> utf8_Path_Bytes_List = (List<byte[]>) multiFilemap.get(PATH_UTF_8);
							for (byte[] utf8_Path_Bytes : utf8_Path_Bytes_List) {
								String path = new String(utf8_Path_Bytes, UTF_8);
								multiFile.setPath(path);
							}
						} else {
							List<byte[]> pathListbytearray = (List<byte[]>) multiFilemap.get(PATH);
							for (byte[] pathbytearray : pathListbytearray) {
								String path = new String(pathbytearray, StringUtil.isEmpty(encoding) ? UTF_8 : encoding);
								multiFile.setPath(path);
								// System.out.println("path=" + path);
							}
						}
						if (multiFilemap.containsKey(LENGTH)) {
							long length = multiFilemap.getLong(LENGTH);
							multiFile.setSingleFileLength(length);
							filelenth += length;
						}
						multiFiles.add(multiFile);
					}
					singerFile = false;
				}

			}
		}

	}

	/**
	 * 反向构造时候用到
	 */
	// --------------------------------------------反向构造时候用到
	public void setName(String name) {
		this.name = name;
	}

	public void setFilelenth(long filelenth) {
		this.filelenth = filelenth;
	}

	public void setCreattime(long creattime) {
		this.creattime = creattime;
	}

	public void setMultiFiles(List<MultiFile> multiFiles) {
		this.multiFiles = multiFiles;
	}

	public void setSingerFile(boolean singerFile) {
		this.singerFile = singerFile;
	}

	public TorrentInfo() {
		super();
	}

	// --------------------------------------------反向构造时候用到

	public TorrentInfo(String filePath) throws FileNotFoundException, ParseException   {
		this(new File(filePath));
	}

	public TorrentInfo(File file) throws FileNotFoundException, ParseException    {
		this(new FileInputStream(file));
	}

	/**
	 * 分词
	 * 
	 * @return
	 */
	public String getNeedSegmentationString() {
		StringBuilder builder = new StringBuilder(name);
		if (!isSingerFile()) {
			List<MultiFile> multiFiles = getMultiFiles();
			if (multiFiles != null && multiFiles.size() > 0) {
				for (MultiFile multiFile : multiFiles) {
					// System.out.println(multiFile.getPath());
					builder.append(multiFile.getPath());
				}
			}
		}
		return builder.toString();
	}

}
