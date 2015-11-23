package com.buswe.crawler.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashSet;

import com.buswe.dht.node.KadNode;
import com.buswe.dht.node.Node;

public class MainJavaTest {

	public static void main(String[] args) throws Exception {
		LinkedHashSet<KadNode> set=new LinkedHashSet<KadNode>();
		KadNode kn1=new KadNode();
		Node n1=new Node();
		n1.setInetAddress(InetAddress.getByName("192.168.1.1"));
		kn1.setNode(n1);
		set.add(kn1);
		
		
		KadNode kn2=new KadNode();
		Node n2=new Node();
		n2.setInetAddress(InetAddress.getByName("192.168.2.2"));
		kn2.setNode(n2);
		set.add(kn2);
		
		
		KadNode kn3=new KadNode();
		Node n3=new Node();
		n3.setInetAddress(InetAddress.getByName("192.168.1.1"));
		kn3.setNode(n3);
		set.add(kn3);
		
		System.out.println(set.size());
		
//		set.add("2");
//		set.add("3");
//		set.add("4");
//		for(KadNode str:set)
//		{
//			System.out.println(str);
//		}
		
	}

}
