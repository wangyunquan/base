package com.konka.dhtsearch.bittorrentkad.krpc.downbittorrent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

import org.yaircc.torrent.bencoding.BEncodedOutputStream;
import org.yaircc.torrent.bencoding.BMap;
import org.yaircc.torrent.bencoding.HashBMap;

import com.konka.dhtsearch.bittorrentkad.krpc.get_peers.GetPeersRequest;
import com.konka.dhtsearch.util.Util;

/**
 * 下载种子的类
 * @author 耳东 (cgp@0731life.com)
 *
 */
public class ClientPeer implements Runnable {
	private byte[] bytes;
	private GetPeersRequest request;

	public ClientPeer(byte[] bytes, GetPeersRequest request) {
		super();
		this.bytes = bytes;
		this.request = request;
	}

	@Override
	public void run() {
		Socket socket = null;
		OutputStream os = null;
		InputStream in = null;

		byte[] ip = Arrays.copyOfRange(bytes, 0, 4);
		byte[] p = Arrays.copyOfRange(bytes, 4, 6);

		System.out.println("开始连接");

		try {
			socket = new Socket();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getByAddress(ip), Util.bytesToInt(p));
			System.out.println(inetSocketAddress);
			socket.connect(inetSocketAddress, 10 * 1000);
			System.out.println("成功");

			byte[] w = new byte[68];
			w[0] = 19;
			byte[] ext = new byte[8];
			ext[5] = 0x10;

			System.arraycopy("BitTorrent protocol".getBytes(), 1, w, 0, 19);
			System.arraycopy(ext, 20, w, 0, 8);
			System.arraycopy(Util.HexString2Bytes(request.getInfo_hash()), 28, w, 0, 20);
			System.arraycopy(Util.HexString2Bytes(request.getInfo_hash()), 48, w, 0, 20);

			os = socket.getOutputStream();
			// socket.setSoTimeout(10 * 1000);
			// "BitTorrent protocol"
			// request.getInfo_hash();

			os.write(BEncodedOutputStream.bencode(w));
			os.flush();
			// {'m': {'ut_metadata', 3}, 'metadata_size': 31235}
			BMap bMap = new HashBMap();
			bMap.put("metadata_size", 31235);

			BMap m = new HashBMap();
			m.put("ut_metadata", 0);
			bMap.put("m", m);
			byte[] bytes = BEncodedOutputStream.bencode(bMap);
			os.write(bytes);
			os.flush();

			in = socket.getInputStream();
			// socket.setSoTimeout(10 * 1000);
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[1024];
			int rc = 0;
			while ((rc = in.read(buff, 0, buff.length)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			byte[] in2b = swapStream.toByteArray();

			System.out.println("收到的返回数据=" + new String(in2b));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
