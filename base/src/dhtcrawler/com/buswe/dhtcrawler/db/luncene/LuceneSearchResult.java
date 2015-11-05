package com.buswe.dhtcrawler.db.luncene;

import java.util.List;

import com.buswe.dhtcrawler.db.models.DhtInfo_MongoDbPojo;

public class LuceneSearchResult {
	private List<DhtInfo_MongoDbPojo> lists;
	private int total;

	public List<DhtInfo_MongoDbPojo> getLists() {
		return lists;
	}

	public void setLists(List<DhtInfo_MongoDbPojo> lists) {
		this.lists = lists;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
