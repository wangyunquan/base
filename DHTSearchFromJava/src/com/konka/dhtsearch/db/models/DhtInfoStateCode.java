package com.konka.dhtsearch.db.models;

public interface DhtInfoStateCode {
	int NO_DOWNLOAD = 100;// 还没有下载
	int DOWNLOAD_FAILED = 401;// 下载失败
	int DOWNLOAD_SUCCESS_BUT_PARSING_FAILED = 204;// 下载ok，解析失败
	int DOWNLOADSUCCESS_AND_PARSING_SUCCESS = 200;// 都ok
}
