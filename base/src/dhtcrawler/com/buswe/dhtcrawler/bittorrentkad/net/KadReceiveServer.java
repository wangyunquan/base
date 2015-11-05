package com.buswe.dhtcrawler.bittorrentkad.net;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.yaircc.torrent.bencoding.BDecodingException;
import org.yaircc.torrent.bencoding.BEncodedInputStream;
import org.yaircc.torrent.bencoding.BList;
import org.yaircc.torrent.bencoding.BMap;
import org.yaircc.torrent.bencoding.BTypeException;

import com.buswe.dhtcrawler.Key;
import com.buswe.dhtcrawler.Node;
import com.buswe.dhtcrawler.bittorrentkad.KadNet;
import com.buswe.dhtcrawler.bittorrentkad.concurrent.CompletionHandler;
import com.buswe.dhtcrawler.bittorrentkad.krpc.KadMessage;
import com.buswe.dhtcrawler.bittorrentkad.krpc.KadRequest;
import com.buswe.dhtcrawler.bittorrentkad.krpc.downbittorrent.ClientPeer;
import com.buswe.dhtcrawler.bittorrentkad.krpc.find_node.FindNodeRequest;
import com.buswe.dhtcrawler.bittorrentkad.krpc.find_node.FindNodeResponse;
import com.buswe.dhtcrawler.bittorrentkad.krpc.get_peers.GetPeersRequest;
import com.buswe.dhtcrawler.bittorrentkad.krpc.get_peers.GetPeersResponse;
import com.buswe.dhtcrawler.bittorrentkad.krpc.ping.PingRequest;
import com.buswe.dhtcrawler.bittorrentkad.krpc.ping.PingResponse;
import com.buswe.dhtcrawler.db.models.DhtInfo_MongoDbPojo;
import com.buswe.dhtcrawler.db.mongodb.MongodbUtilProvider;
import com.buswe.dhtcrawler.util.Util;

/**
 * 守护线程，负责接受和发送消息
 * 
 */
public class KadReceiveServer implements Runnable, DHTConstant {

	private final ExecutorService srvExecutor = new ScheduledThreadPoolExecutor(10);
	private final AtomicBoolean isActive = new AtomicBoolean(false);
	private final Thread startThread;
	private final static Set<String> info_hashset = new HashSet<String>();
	private final Selector selector;
	private final KadNet kadNet;

	// private final DhtInfoDao dhtInfoDao = ;
	public KadReceiveServer(Selector selector, KadNet kadNet) {
		startThread = new Thread(this);
		this.selector = selector;
		this.kadNet = kadNet;
	}

	/**
	 * 回复Ping请求
	 * 
	 * @param bMap
	 * @throws BTypeException
	 * @throws IOException
	 */
	protected void hanldePingRequest(String transaction, Node src) throws BTypeException, IOException {
		PingResponse pingResponse = new PingResponse(transaction, src);
		kadNet.sendMessage(pingResponse);
		addNodeToQueue(src);
	}

	/**
	 * 回复Get_Peers请求
	 * 
	 * @param decodedData
	 * @throws BTypeException
	 *             info_hash
	 * @throws IOException
	 */
	protected void handleGet_PeersRequest(String transaction, BMap decodedData, Node src) throws BTypeException, IOException {
		byte[] bytesFromInfohash = (byte[]) decodedData.getMap(A).get(INFO_HASH);
		String infoHash = Util.hex(bytesFromInfohash);
		handleInfoHash(infoHash, src);
		GetPeersRequest getPeersRequest = new GetPeersRequest(transaction, src);
		getPeersRequest.setInfo_hash(Util.hex(bytesFromInfohash));

		// sendGet_Peers(transaction, bytesFromInfohash, src);//
		// *******发送请求。查找infohash

		GetPeersResponse getPeersResponse = new GetPeersResponse(transaction, src);
		List<Node> nodes = kadNet.findNode(new Key(bytesFromInfohash));
		getPeersResponse.setNodes(nodes);
		addNodeToQueue(src);
		kadNet.sendMessage(getPeersResponse);
	}

	/**
	 * 保存磁力连接
	 * 
	 * @param info_hash
	 */
	private void saveInfoHash(String info_hash, Node src) {// TODO 保存磁力连接
		try {
			DhtInfo_MongoDbPojo dhtInfo_MongoDbPojo = new DhtInfo_MongoDbPojo();
			dhtInfo_MongoDbPojo.setInfo_hash(info_hash);
			dhtInfo_MongoDbPojo.setPeerIp(src.getSocketAddress().toString());

			// DhtInfo dhtInfo = new DhtInfo();
			// dhtInfo.setInfo_hash(info_hash);
			// dhtInfo.setPeerIp(src.getSocketAddress().toString());
			MongodbUtilProvider.getMongodbUtil().save(dhtInfo_MongoDbPojo);
			// DaoFactory.getDhtInfoDao().insert(dhtInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	private void sendGet_Peers(String transaction, byte[] infohash, Node src) {
		GetPeersRequest getPeersRequest = new GetPeersRequest(transaction, src);
		getPeersRequest.setInfo_hash(Util.hex(infohash));
		sendGet_Peers(getPeersRequest);
	}

	int fail = 0;
	int countcount = 0;

	private void sendGet_Peers(KadRequest getPeersRequest) {
		MessageDispatcher dispatcher = new MessageDispatcher(kadNet, getPeersRequest.getTransaction());
		dispatcher.setConsumable(true)//
				// .addFilter(new
				// IdMessageFilter(findNodeResponse.getTransaction()))// 只接受的类型
				// .addFilter(new TypeMessageFilter(FindNodeResponse.class))//
				.setCallback(null, new CompletionHandler<KadMessage, BMap>() {
					@Override
					public void completed(KadMessage msg, BMap decodedData) {
						// System.out.println(decodedData);
						BMap responseMap;
						try {
							responseMap = decodedData.getMap(R);
							if (responseMap.containsKey(NODES)) {
								byte[] bytesFromNodes = (byte[]) responseMap.get(NODES);
								parsergetpeersNodes(msg, bytesFromNodes);
							} else if (responseMap.containsKey(VALUES)) {
								// System.out.println("收到相应value==========" +
								// decodedData);
								BList bList = responseMap.getList(VALUES);
								GetPeersRequest request = (GetPeersRequest) msg;
								// request.g
								for (Object bytesobj : bList) {
									ClientPeer cp = new ClientPeer((byte[]) bytesobj, request);
									srvExecutor.execute(cp);

								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void failed(Throwable exc, BMap nothing) {
						// System.out.println("相响应错误了==" + ++fail);
					}
				});
		try {
			dispatcher.send(getPeersRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parsergetpeersNodes(KadMessage msg, byte[] bytesFromNodes) throws UnknownHostException {
		List<Node> nodes = Util.passNodes(bytesFromNodes);
		for (int i = 0; i < nodes.size(); i++) {
			KadRequest kadRequest = (KadRequest) msg;
			kadRequest.setNode(nodes.get(i));
			sendGet_Peers(kadRequest);
		}

	}

	int count = 0;

	/**
	 * 处理请求信息
	 * 
	 * @param decodedData
	 * @throws BTypeException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */

	protected synchronized void handleRequestMsg(InetSocketAddress inetSocketAddress, BMap decodedData, String transaction) throws BTypeException, NoSuchAlgorithmException, IOException {

		if (decodedData.containsKey(Q)) {
			String q_value = decodedData.getString(Q);// find_node or
														// getpeers===

			Key key = new Key((byte[]) decodedData.getMap(A).get(ID));

			final Node to = new Node(key).setSocketAddress(inetSocketAddress);
			if (q_value.equals(FIND_NODE)) {
				handleFind_NodeRequest(transaction, decodedData, to);
			} else if (q_value.equals(GET_PEERS)) {
				handleGet_PeersRequest(transaction, decodedData, to);
			} else if (q_value.equals(PING)) {
				hanldePingRequest(transaction, to);
			} else if (q_value.equals(ANNOUNCE_PEER)) {
				hanldeAnnounce_PeerRequest(transaction, decodedData, to);// 不回复
			} else {
			}
			// switch (q_value) {
			// case FIND_NODE://
			// handleFind_NodeRequest(transaction, decodedData, to);
			// break;
			// case GET_PEERS://
			// handleGet_PeersRequest(transaction, decodedData, to);
			// break;
			// case PING://
			// hanldePingRequest(transaction, to);
			// break;
			// case ANNOUNCE_PEER://
			// hanldeAnnounce_PeerRequest(transaction, decodedData, to);// 不回复
			// break;
			// default:
			// break;
			// }

		} else {
			return;
		}
	}

	private void hanldeAnnounce_PeerRequest(String transaction, BMap decodedData, Node to) throws BTypeException {
		String info_hash = Util.hex((byte[]) decodedData.getMap(A).get(INFO_HASH));
		handleInfoHash(info_hash, to);
	}

	private void handleInfoHash(String info_hash, Node to) {
		if (!info_hashset.contains(info_hash)) {
			if (info_hashset.size() > 1000) {
				info_hashset.removeAll(info_hashset);
			}
			info_hashset.add(info_hash);
			// TODO 这里要保存种子
			saveInfoHash(info_hash, to);
			System.out.println("种子数=" + info_hashset.size());
		}
	}

	/**
	 * 回复find_node请求
	 * 
	 * @param decodedData
	 * @throws BTypeException
	 * @throws IOException
	 */
	private void handleFind_NodeRequest(String transaction, BMap decodedData, Node src) throws BTypeException, IOException {
		byte[] target = (byte[]) decodedData.getMap(A).get(TARGET);
		List<Node> lists = kadNet.findNode(new Key(target));
		FindNodeResponse findNodeResponse = new FindNodeResponse(transaction, src);
		findNodeResponse.setNodes(lists);
		kadNet.sendMessage(findNodeResponse);
		addNodeToQueue(src);

	}

	/**
	 * The server loop:
	 * 
	 * @category accept a message from socket
	 * @category parse message
	 * @category handle the message in a thread pool 这个线程用来接受信息
	 */
	@Override
	public void run() {
		this.isActive.set(true);

		while (this.isActive.get()) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(65536);
			try {
				int eventsCount = selector.select();// 这里堵塞
				if (eventsCount > 0) {
					Set<SelectionKey> selectedKeys = selector.selectedKeys();
					Iterator<SelectionKey> iterator = selectedKeys.iterator();
					while (iterator.hasNext()) {
						SelectionKey sk = iterator.next();
						iterator.remove();
						if (sk.isReadable()) {
							DatagramChannel datagramChannel = (DatagramChannel) sk.channel();
							SocketAddress target = datagramChannel.receive(byteBuffer);// 接受数据

							byteBuffer.flip();
							byte[] dst = new byte[byteBuffer.limit()];
							byteBuffer.get(dst);
							byteBuffer.clear();
							handleIncomingData((InetSocketAddress) target, dst);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 处理响应信息
	 * 
	 * @param decodedData
	 * @param src
	 * @param transaction
	 * @param messageDispatcher
	 * @throws BTypeException
	 * @throws BDecodingException
	 * @throws UnknownHostException
	 */
	private void handleResponseMsg(BMap decodedData, String transaction) throws BTypeException, BDecodingException, UnknownHostException {

		MessageDispatcher messageDispatcher = MessageDispatcher.findMessageDispatcherByTag(transaction);// 取出之前的请求对象
		if (messageDispatcher != null) {// 有记录
			KadRequest kadRequest = messageDispatcher.getKadRequest();
			messageDispatcher.handle(kadRequest, decodedData);
			if (kadRequest.getClass() == FindNodeRequest.class) {
				handlefindNodeResponse(decodedData);// *****
			} else if (kadRequest.getClass() == PingRequest.class) {

			} else if (kadRequest.getClass() == GetPeersRequest.class) {

			} else {
				// TODO 响应的操作应该根据请求的id t判断是哪个响应，t 一次必须改变
			}
			// messageDispatcher.handle(msg);
		} else {// 没有记录就按照大众处理
			handlefindNodeResponse(decodedData);// *****
		}
	}

	/**
	 * 只接受findNode的响应
	 * 
	 * @param bMap
	 * @throws UnknownHostException
	 * @throws BTypeException
	 */
	private void handlefindNodeResponse(BMap decodedData) throws UnknownHostException, BTypeException {
		if (decodedData.containsKey(R)) {
			BMap respondData = decodedData.getMap(R);
			if (respondData.containsKey(NODES)) {
				byte[] nodesbyteArray = (byte[]) respondData.get(NODES);
				List<Node> nodes = Util.passNodes(nodesbyteArray);

				nodesbyteArray = null;// 回收
				addNodesToQueue(nodes);
			}
		}
	}

	private void addNodesToQueue(List<Node> nodes) {
		for (Node node : nodes) {
			kadNet.addNodeToBuckets(node);
		}
	}

	private void addNodeToQueue(Node node) {
		kadNet.addNodeToBuckets(node);
	}

	private void handleIncomingData(final InetSocketAddress target, final byte[] dst) {
		this.srvExecutor.execute(new Runnable() {// 交给线程池处理
					@Override
					public void run() {
						try {
							BMap decodedData = (BMap) BEncodedInputStream.bdecode(dst);

							String transaction = Util.hex((byte[]) (decodedData.get(T)));
							if (decodedData.containsKey(Y)) {
								String y = decodedData.getString(Y);
								if (Q.equals(y)) {// 对方请求
									handleRequestMsg(target, decodedData, transaction);// 处理响应时候不需要解析令牌
								} else if (R.equals(y)) {// 对方的响应
									handleResponseMsg(decodedData, transaction);
								}
							}
						} catch (BDecodingException e) {
							// System.out.println("长度="+dst.length);
							// System.out.println(new String(dst));
							// System.out.println(target);
							// e.printStackTrace();
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						} catch (BTypeException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}

				});
	}

	/**
	 * Shutdown the server and closes the socket 关闭服务
	 * 
	 * @param kadServerThread
	 */
	public void shutdown() {
		this.isActive.set(false);
		startThread.interrupt();
		try {
			startThread.join();
		} catch (final InterruptedException e) {
		}
	}

	public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
		startThread.setUncaughtExceptionHandler(eh);
	}

	public void start() {
		startThread.start();
	}

}
