package com.buswe.dhtcrawler.entity;

import java.util.Date;

public class Dhtinfo {
	String infoHash;
	String peerIpport;
	Date lastrequesttime;
	Integer dhtstate;
	Integer crawcount;
	Integer hitcount;
	String tag;
	String name;
	Long filelength;
	Date creattime;
	Boolean singerfile;
	public String getInfoHash() {
		return infoHash;
	}
	public void setInfoHash(String infoHash) {
		this.infoHash = infoHash;
	}
	public String getPeerIpport() {
		return peerIpport;
	}
	public void setPeerIpport(String peerIpport) {
		this.peerIpport = peerIpport;
	}
	public Date getLastrequesttime() {
		return lastrequesttime;
	}
	public void setLastrequesttime(Date lastrequesttime) {
		this.lastrequesttime = lastrequesttime;
	}
	public Integer getDhtstate() {
		return dhtstate;
	}
	public void setDhtstate(Integer dhtstate) {
		this.dhtstate = dhtstate;
	}
	public Integer getCrawcount() {
		return crawcount;
	}
	public void setCrawcount(Integer crawcount) {
		this.crawcount = crawcount;
	}
	public Integer getHitcount() {
		return hitcount;
	}
	public void setHitcount(Integer hitcount) {
		this.hitcount = hitcount;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getFilelength() {
		return filelength;
	}
	public void setFilelength(Long filelength) {
		this.filelength = filelength;
	}
	public Date getCreattime() {
		return creattime;
	}
	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}
	public Boolean getSingerfile() {
		return singerfile;
	}
	public void setSingerfile(Boolean singerfile) {
		this.singerfile = singerfile;
	}
	 
	 
	

}
