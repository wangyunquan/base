package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import com.dampcake.bencode.Bencode;

import dto.Node;
import lombok.Data;
import lombok.experimental.Builder;
import utilities.Utils;

/**
 * Created by wihoho on 19/9/15.
 */

public class DHTServer implements Runnable {
    private String id;
    private DatagramSocket socket;
    private Map<String, Node> nodeMap;
    private AtomicInteger count;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DatagramSocket getSocket() {
		return socket;
	}

	public void setSocket(DatagramSocket socket) {
		this.socket = socket;
	}

	public Map<String, Node> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(Map<String, Node> nodeMap) {
		this.nodeMap = nodeMap;
	}

	public AtomicInteger getCount() {
		return count;
	}

	public void setCount(AtomicInteger count) {
		this.count = count;
	}

	 

    @Override
    public void run() {
        System.out.println("Server starts");
        byte[] receiveData = new byte[65536];
        count = new AtomicInteger(0);

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                socket.receive(receivePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] validData = Utils.getByteArray(receiveData, 0, receivePacket.getLength() - 1);


            Map<String, Object> map = null;
            try {
                map = Utils.deBencode(validData);
                System.out.println(map+"  recevie");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Node sourceNode = new Node();
            sourceNode.setAddress(receivePacket.getAddress().toString());
            sourceNode.setPort(receivePacket.getPort()) ;
   
            try {
                onMessage(map, sourceNode);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onMessage(Map<String, Object> map, Node sourceNode) throws IOException {
        if (Objects.isNull(map.get("y")))
            return;

        // handle find_nodes response
        if (map.get("y").equals("r")) {
            Map<String, String> subMap = (Map<String, String>) map.get("r");
            if (subMap.containsKey("nodes")) {
                onFindNodesResponse(map);
            }

        } else if (map.get("y").equals("q")) {
            // handle ping
            switch ((String) map.get("q")) {
                case "ping":
                    onPing(map, sourceNode);
                    break;

                case "get_peers":
                    onGetPeers(map, sourceNode);
                    break;

                case "announce_peer":
                    System.out.println("Announce");
                    break;

                case "find_nodes":
                    onFindNodes(map, sourceNode);
                    break;
            }
        }
    }

    private void onFindNodesResponse(Map<String, Object> map) throws UnknownHostException {
        List<Node> decodedNodes = Utils.decodeNodes(((Map<String, String>) (map.get("r"))).get("nodes"));
        if (decodedNodes.isEmpty())
            return;

        synchronized (nodeMap) {
            decodedNodes.stream()
                    .filter(Node::isValid)
                    .forEach(n -> nodeMap.putIfAbsent(n.getAddress(), n));
        }

    }

    private void onFindNodes(Map<String, Object> map, Node sourceNode) {
        Map<String, String> subMap = (Map<String, String>) map.get("a");

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("t", map.get("t"));
        responseMap.put("y", "r");
        Map<String, String> subMap1 = new HashMap<>();
        subMap1.put("id", subMap.get("target"));
//        subMap1.put("nodes", );

    }

    private void onPing(Map<String, Object> map, Node sourceNode) throws IOException {
        Map<String, Object> pong = new HashMap<>();

        pong.put("t", map.get("t"));
        pong.put("y", "r");

        Map<String, String> subMap = new HashMap<>();
        subMap.put("id", this.getId());
        pong.put("r", subMap);

        sendMessage(pong, sourceNode);

    }

    private void sendMessage(Map<String, Object> map, Node targetNode) {
        try {
            byte[] sendData = Utils.enBencode(map);

            InetAddress destinationIp = InetAddress.getByName(targetNode.getAddress());
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, destinationIp, targetNode.getPort());
            socket.send(sendPacket);
        } catch (Exception e) {

        }

    }

    private void onGetPeers(Map<String, Object> map, Node sourceNode) throws IOException {
        if (Objects.nonNull(map.get("q")) && map.get("q").equals("get_peers")) {
            Map<String, String> subMap = (Map<String, String>) map.get("a");
            String infoHash = subMap.get("info_hash");

            int countNumber = count.incrementAndGet();
            System.out.println(countNumber + ":" + infoHash);

            // response
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("t", map.get("t"));
            responseMap.put("y", "r");
            Map<String, String> subMap1 = new HashMap<>();
            subMap1.put("id", Utils.randomId());
            subMap1.put("token", new String(Utils.getByteArray(infoHash.getBytes(Charset.forName("UTF-8")), 0, 1), Charset.forName("UTF-8")));
            subMap1.put("nodes", "");
            responseMap.put("r", subMap1);

            sendMessage(responseMap, sourceNode);
        }
    }

}
