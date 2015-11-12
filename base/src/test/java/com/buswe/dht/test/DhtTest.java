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
//public class DhtTest {
//
//	public static void main(String[] args) throws Exception {
//		/**
//		 *     ("router.bittorrent.com", 6881),
//    ("dht.transmissionbt.com", 6881),
//    ("router.utorrent.com", 6881)
//    67.215.246.10
//		 */
////		Socket      kkSocket = new Socket("mgtracker.org", 2710);
////		kkSocket.getOutputStream().write("sd".getBytes());
//		KademliaId kademliaId	=new KademliaId();
//		/**
//		 *     this(
//                ownerId,
//                new Node(defaultId, InetAddress.getLocalHost(), udpPort),
//                udpPort,
//                new DefaultConfiguration()
//                
//                114.244.232.168
//        );
//		 */
//        JKademliaNode kad1 = new JKademliaNode("wangyunquan", new KademliaId(),6891);
//  //JKademliaNode kad2 = new JKademliaNode("wangxiaoyong", new KademliaId(), 12057);
//        kad1.bootstrap(new Node(new KademliaId(),InetAddress.getByName("dht.transmissionbt.com"),6881));
//		
//        System.out.println(kad1);
//      //  System.out.println(kad2);
//
//        /* Print the node states every few minutes */
//        KadConfiguration config = new DefaultConfiguration();
//        Timer timer = new Timer(true);
//        timer.schedule(
//                new TimerTask()
//                {
//                    @Override
//                    public void run()
//                    {
//                        System.out.println(kad1.getRoutingTable().getAllNodes().size());
//         //  System.out.println(kad2.getRoutingTable().getAllNodes().size());
//                    }
//                },
//                // Delay                        // Interval
//                config.restoreInterval(), config.restoreInterval()
//        );
//	}
//
//}
