package com.buswe.dht.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buswe.dht.entity.Dhtinfo;

public interface CrawlService {

	void startDhtService(Integer size);

	Page<Dhtinfo> search(String searchString, Pageable page) throws Exception;

	Dhtinfo loadDhtinfo(String infoHash);

}