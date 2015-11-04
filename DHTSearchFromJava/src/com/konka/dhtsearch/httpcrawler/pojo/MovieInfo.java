package com.konka.dhtsearch.httpcrawler.pojo;

public class MovieInfo {
	private String movieName;// 电影名
	private int tag;// 对已经采集了的图片进行标记 默认为0 ，处理了用1标识

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

}
