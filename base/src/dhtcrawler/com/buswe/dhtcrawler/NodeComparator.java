package com.buswe.dhtcrawler;

import java.math.BigInteger;
import java.util.Comparator;

import com.buswe.dht.node.KadNode;

 

public class NodeComparator implements Comparator<KadNode>{

	private final BigInteger	key;
	public NodeComparator(Key key)
	{
		this.key=key.getInt();
	}
	
	@Override
	public int compare(KadNode o1, KadNode o2) {
		BigInteger b1 = o1.getNode().getKey().getInt();
		BigInteger b2 = o2.getNode().getKey().getInt();
		b1 = b1.xor(key);
		b2 = b2.xor(key);

		// System.out.println("key: " + key.toString(2) + " key1: " + b1.toString(2) + " key2: " + b2.toString(2));
		// System.out.println("b1: " + b1.toString() + " b1 abs: " + b1.abs().toString() + " b1 signum: " + b1.signum() + " b2: " + b2.toString() + " b2 abs: " + b2.abs().toString() + " b2 signum: " + b2.signum() + " compare: " + b1.abs().compareTo(b2.abs()));

		if (b1.signum() == -1 && b2.signum() != -1) {
			return 1;
		}

		if (b1.signum() != -1 && b2.signum() == -1) {
			return -1;
		}

		return b1.abs().compareTo(b2.abs());
	}

}
