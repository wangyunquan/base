package servlet;

import java.lang.reflect.ParameterizedType;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;

import com.konka.dhtsearch.AppManager;
import com.konka.dhtsearch.Key;
import com.konka.dhtsearch.Node;
import com.konka.dhtsearch.bittorrentkad.KadNet;
import com.konka.dhtsearch.db.mysql.exception.DhtException;

public class SearchText {
	private static final InetSocketAddress[] BOOTSTRAP_NODES = { //
	new InetSocketAddress("router.bittorrent.com", 6881), //
			new InetSocketAddress("dht.transmissionbt.com", 6881),//
			new InetSocketAddress("router.utorrent.com", 6881), };

	public static void main(String[] args) throws DhtException {
		int size = 3;
		try {
			for (int i = 0; i < size; i++) {
				AppManager.init();// 1---
				Key key = AppManager.getKeyFactory().generate();
				Node localNode = new Node(key).setInetAddress(InetAddress.getByName("0.0.0.0")).setPoint(20200 + i);// 这里注意InetAddress.getLocalHost();为空
				new KadNet(null, localNode).join(BOOTSTRAP_NODES).create();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// try {
		// text();
		// } catch (NoSuchFieldException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (SecurityException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// DhtInfoDao dao = DaoFactory.getPersonaDao();
		// DhtInfo dhtinfo = new DhtInfo();
		// dhtinfo.setInfo_hash("dddddddddddddddddddddddddd");
		// for (int i = 0; i < 100; i++) {
		// dao.insert(dhtinfo);
		// }

		// try {
		// TorrentInfo torrentInfo = new TorrentInfo("D:/a3.torrent");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private List<String> list = new LinkedList<String>();

	public static void text() throws NoSuchFieldException, SecurityException {
		ParameterizedType pt = (ParameterizedType) SearchText.class.getDeclaredField("list").getGenericType();
		System.out.println(pt.getActualTypeArguments().length);
		System.out.println(pt.getActualTypeArguments()[0]);
	}
}
