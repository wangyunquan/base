package com.buswe.dht.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.buswe.dht.node.Key;
import com.buswe.dht.node.Node;
 
/**
 *编码工具类
 * @author 王云权
 *
 */
public class BencodUtil {
	public static byte[] nodesToBytes(List<Node> nodes) {
		int size = nodes.size();
		byte[] nodesbyte = new byte[size * 26];
		for (int i = 0; i < size; i++) {
			Node node = nodes.get(i);
			byte[] id = node.getKey().getBytes();
			byte[] address = node.getInetAddress().getAddress();
			byte[] port = ByteUtil.intTobytes(node.getPort());
			// System.arraycopy(s,0,a,0,s.length);
			System.arraycopy(id, 0, nodesbyte, 0 + i * 26, id.length);
			System.arraycopy(address, 0, nodesbyte, 0 + i * 26 + id.length, address.length);
			System.arraycopy(port, 0, nodesbyte, 0 + i * 26 + id.length + address.length, port.length);
		}
		return nodesbyte;
	}
	public static List<Node> passNodes(byte[] nodesbyteArray) throws UnknownHostException {
		int bytelength = nodesbyteArray.length;
		if (bytelength % 26 != 0) {
			return null;
		}
		int count = bytelength / 26;
		List<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < count; i++) {
			byte[] nid = Arrays.copyOfRange(nodesbyteArray, i * 26, i * 26 + 20);
			byte[] ip = Arrays.copyOfRange(nodesbyteArray, i * 26 + 20, i * 26 + 24);
			byte[] p = Arrays.copyOfRange(nodesbyteArray, i * 26 + 24, i * 26 + 26);

			InetAddress inet4Address = InetAddress.getByAddress(ip);
			Node node = new Node(new Key(nid));
			Integer poin = ByteUtil.bytesToInt(p);
			if (poin != 0) {
				node.setInetAddress(inet4Address).setPoint(poin);
				nodes.add(node);
			}
		}
		return nodes;
	}
}
