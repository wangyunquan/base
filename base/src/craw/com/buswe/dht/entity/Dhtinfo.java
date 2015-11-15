package com.buswe.dht.entity;

import java.util.Date;
import java.util.List;
/**
 * 一条DHT信息
 * @author 王云权
 *
 */
public class Dhtinfo {
private	String infohash;//info_hash
private	String peerIpport;//收到的节点的IP和端口
private	Date lastrequesttime=new Date();//上次请求时间
/**
 * 信息的状态参见@DhtinfoState 
 * 0;//状态正常，下载正常，解析成功
 1;//解析失败，无效
 2;//还没下载
 3;//下载失败  大于1的,需要下载
 默认为2
 */
private	Integer dhtstate=2;//
private	Integer crawcount=0;//抓取到的次数，表示当前在网络环境的热度 ，这只是表明是在find_peer。不一定成功

private	Integer hitcount=0;//被点击的次数
private	String tag;//标签
private	String name;//名称，即下载的名字
private	Long filelength; //如果是单文件，表示文件大小
private	Date creattime; //创建日期，是指文件的创建者
private	Boolean singerfile;  //是否是单文件
private Boolean isindex=false;//是否已索引 
/**
0;//已处理为不合法，不予展示
1;//默认状态；有效
2;//被投诉或举报
不等于0的，即可展示，默认1
 */
private Integer validstate=1;
private Integer successcount=0;// 抓取到的次数，annoucepeer次数，已成功正在下载

private List<Dhtfiles> dhtfiles;//子文件

 
	public List<Dhtfiles> getDhtfiles() {
	return dhtfiles;
}
public void setDhtfiles(List<Dhtfiles> dhtfiles) {
	this.dhtfiles = dhtfiles;
}
	public Integer getSuccesscount() {
	return successcount;
}
public void setSuccesscount(Integer successcount) {
	this.successcount = successcount;
}
	public Boolean getIsindex() {
	return isindex;
}
public void setIsindex(Boolean isindex) {
	this.isindex = isindex;
}
public Integer getValidstate() {
	return validstate;
}
public void setValidstate(Integer validstate) {
	this.validstate = validstate;
}
 
	public String getInfohash() {
	return infohash;
}
public void setInfohash(String infohash) {
	this.infohash = infohash;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((infohash == null) ? 0 : infohash.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dhtinfo other = (Dhtinfo) obj;
		if (infohash == null) {
			if (other.infohash != null)
				return false;
		} else if (!infohash.equals(other.infohash))
			return false;
		return true;
	}
	 
	 
	

}
