package com.buswe.test.lucene.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.buswe.moudle.cms.lucene.Searchable;

/**
 * 测试索引的对象
 * @author Winter Lau
 */
public class Person implements Searchable {

	private long id;
	private String title;
	private String address;

	public Person(){}
	public Person(long id, String t, String b){
		this.id = id;
		this.title = t;
		this.address = b;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getId() {
		return id;
	}

	@Override
	public int compareTo(Searchable o) {
		return 0;
	}

	@Override
	public long id() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public List<String> storeFields() {
		return Arrays.asList("id", "title");
	}

	@Override
	public List<String> indexFields() {
		return Arrays.asList("title","address");
	}

	@Override
	public Map<String, String> extendStoreDatas() {
		return null;
	}

	@Override
	public Map<String, String> extendIndexDatas() {
		return null;
	}

	@Override
	public List<? extends Searchable> ListAfter(long id, int count) {
		return null;
	}
	@Override
	public float boost() {
		return 1.1f;
	}
}
