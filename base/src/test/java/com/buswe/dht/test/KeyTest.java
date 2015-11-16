package com.buswe.dht.test;

import com.buswe.dht.node.DhtKeyFactory;

public class KeyTest {

	public static void main(String[] args) {
		DhtKeyFactory keyFactory = DhtKeyFactory.getInstance();
System.out.println(keyFactory.generate().toString());

	}

}
