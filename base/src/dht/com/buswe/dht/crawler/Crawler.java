package com.buswe.dht.crawler;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import com.buswe.dht.crawler.dto.Node;
import com.buswe.dht.crawler.udp.DHTClient;
import com.buswe.dht.crawler.udp.DHTServer;
import com.buswe.dht.crawler.utilities.Utils;

/**
 * Created by wihoho on 20/9/15.
 */
public class Crawler {

    public static void main(String[] args) throws SocketException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress("0.0.0.0", 6884);
        DatagramSocket socket = new DatagramSocket(inetSocketAddress);

        String nodeId = Utils.randomId();
        Map<String, Node> nodeMap = new HashMap<>();
        new Node();
        Node n1 =   new Node();
        n1.setAddress("router.bittorrent.com");
        n1.setPort(6881);
        Node n2 =  new Node();
        n2.setAddress("dht.transmissionbt.com");
        n2.setPort(6881);
        Node n3 =  new Node();
        n3.setAddress("router.utorrent.com");
        n3.setPort(6881);
        nodeMap.put(n1.getAddress(), n1);
        nodeMap.put(n2.getAddress(), n2);
        nodeMap.put(n3.getAddress(), n3);

        DHTClient dhtClient = new DHTClient();
        dhtClient.setId(nodeId);
        dhtClient.setNodeMap(nodeMap);
        dhtClient.setSocket(socket);
 

        DHTServer dhtServer =new  DHTServer ();
        dhtServer.setId(nodeId);
        dhtServer.setSocket(socket);
        dhtServer.setNodeMap(nodeMap);

        Thread client = new Thread(dhtClient);
        Thread server = new Thread(dhtServer);

        client.start();
        server.start();

    }
}
