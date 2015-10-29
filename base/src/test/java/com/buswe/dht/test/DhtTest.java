package com.buswe.dht.test;

import java.net.InetAddress;

import kademlia.JKademliaNode;
import kademlia.node.KademliaId;
import kademlia.node.Node;

public class DhtTest {

	public static void main(String[] args) throws Exception {
		/**
		 *     ("router.bittorrent.com", 6881),
    ("dht.transmissionbt.com", 6881),
    ("router.utorrent.com", 6881)
    67.215.246.10
		 */
//		Socket      kkSocket = new Socket("mgtracker.org", 2710);
//		kkSocket.getOutputStream().write("sd".getBytes());
		KademliaId kademliaId	=new KademliaId();
        JKademliaNode kad1 = new JKademliaNode("JoshuaK", kademliaId, 12049);
        
        JKademliaNode kad2 = new JKademliaNode("OwnerName2", new KademliaId(), 12057);
 
        Node node=new Node(kademliaId, InetAddress.getByName("mgtracker.org"),2710);
        kad1.bootstrap(node);
		

	}

}
