package com.buswe.dht.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buswe.dht.entity.Dhtinfo;

public interface CrawlService {

	public		void startDhtService();
	public void stopDhtService();
	public		Dhtinfo loadDhtinfo(String infoHash);
	public	Page<Dhtinfo> search(String searchString, Pageable page) throws Exception;
	public void creatIndex() throws Exception;
}