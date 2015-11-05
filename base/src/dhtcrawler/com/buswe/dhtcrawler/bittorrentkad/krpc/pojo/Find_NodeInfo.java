package com.buswe.dhtcrawler.bittorrentkad.krpc.pojo;

import java.util.List;

import com.buswe.dhtcrawler.Node;

//find_node Query = {"t":"aa", "y":"q", "q":"find_node", "a": {"id":"abcdefghij0123456789", "target":"mnopqrstuvwxyz123456"}}
//bencoded = d1:ad2:id20:abcdefghij01234567896:target20:mnopqrstuvwxyz123456e1:q9:find_node1:t2:aa1:y1:qe
//Response = {"t":"aa", "y":"r", "r": {"id":"0123456789abcdefghij", "nodes": "def456..."}}
//bencoded = d1:rd2:id20:0123456789abcdefghij5:nodes9:def456...e1:t2:aa1:y1:re
public class Find_NodeInfo {
	private String ransactionID;
	private String id;
	private List<Node> nodes;

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public String getRansactionID() {
		return ransactionID;
	}

	public void setRansactionID(String ransactionID) {
		this.ransactionID = ransactionID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

 
	
}
