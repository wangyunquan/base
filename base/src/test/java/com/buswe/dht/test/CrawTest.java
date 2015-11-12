//package com.buswe.dht.test;
//
//import java.net.InetAddress;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import kademlia.DefaultConfiguration;
//import kademlia.JKademliaNode;
//import kademlia.KadConfiguration;
//import kademlia.node.KademliaId;
//import kademlia.node.Node;
//
//public class CrawTest {
//
//	public static void main(String[] args) throws Exception {
//		//      n3.setAddress("router.utorrent.com");
//		 JKademliaNode my = new JKademliaNode("wangyunquan", new KademliaId(), 7574);
//		//   JKademliaNode kad3 = new JKademliaNode("xiaoyonggege", new KademliaId(), 7783);
//		 
//		 Node node=new Node( new KademliaId(),InetAddress.getByName("209.126.117.226"),49153);
//		 my.bootstrap(node); 
//		 
//		 
//		 /**
//		  * 	 *     ("router.bittorrent.com", 6881),
//    ("dht.transmissionbt.com", 6881),
//    ("router.utorrent.com", 6881)
//		  */
//		 
//         KadConfiguration config = new DefaultConfiguration();
//         Timer timer = new Timer(true);
//         timer.schedule(
//                 new TimerTask()
//                 {
//                     @Override
//                     public void run()
//                     {
//                         System.out.println(my.getRoutingTable().getAllNodes().size());
//                         System.out.println(my.getStatistician());
//                     }
//                 },
//                 // Delay                        // Interval
//                 config.restoreInterval(), config.restoreInterval()
//         );
//	}
//
//}
