package com.konka.dhtsearch.bittorrentkad.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.konka.dhtsearch.Node;
import com.konka.dhtsearch.bittorrentkad.bucket.KBuckets;
import com.konka.dhtsearch.bittorrentkad.cache.KadCache;
import com.konka.dhtsearch.bittorrentkad.krpc.KadMessage;
import com.konka.dhtsearch.bittorrentkad.krpc.find_node.FindNodeRequest;
import com.konka.dhtsearch.bittorrentkad.krpc.find_node.FindNodeResponse;
import com.konka.dhtsearch.bittorrentkad.net.KadSendMsgServer;
import com.konka.dhtsearch.bittorrentkad.net.MessageDispatcher;
import com.konka.dhtsearch.bittorrentkad.net.filter.MessageFilter;
import com.konka.dhtsearch.bittorrentkad.net.filter.TypeMessageFilter;

/**
 * 处理器 查找已知的节点相近的数据 Handle find node requests by giving the known closest nodes to the requested key from the KBuckets data structure
 * 
 */
public class KademliaFindNodeHandler extends AbstractHandler {
	private final KadSendMsgServer kadServer;
	private final Node localNode;
	private final KadCache cache;
	private final KBuckets kBuckets;
	private final int kBucketSize;

	KademliaFindNodeHandler(final MessageDispatcher msgDispatcherProvider, final KadSendMsgServer kadServer,//
			final Node localNode, final KadCache cache, final KBuckets kBuckets, final int kBucketSize) {
		super(msgDispatcherProvider);

		this.kadServer = kadServer;
		this.localNode = localNode;
		this.cache = cache;
		this.kBuckets = kBuckets;
		this.kBucketSize = kBucketSize;
	}

	@Override
	public void completed(final KadMessage msg, final String attachment) {

		final FindNodeRequest findNodeRequest = ((FindNodeRequest) msg);
		final FindNodeResponse findNodeResponse = findNodeRequest.generateResponse(this.localNode).setCachedResults(false);

		List<Node> cachedResults = null;

		if (!findNodeRequest.shouldSearchCache())
			findNodeResponse.setNodes(this.kBuckets.getClosestNodesByKey(findNodeRequest.getKey(), this.kBucketSize));
		else {
			// requester ask to search in cache
			cachedResults = this.cache.search(findNodeRequest.getKey());

			if (cachedResults == null) {
				findNodeResponse.setNodes(this.kBuckets.getClosestNodesByKey(findNodeRequest.getKey(), this.kBucketSize));
			} else {
				findNodeResponse.setNodes(new ArrayList<Node>(cachedResults)).setCachedResults(true);

			}
		}

		try {
//			findNodeResponse.set
			this.kadServer.send( findNodeResponse);//这里要修改
//			this.kadServer.send(msg.getSrc(), findNodeResponse);
		} catch (final IOException e) {
			// could not send back a response
			// nothing to do
			e.printStackTrace();
		}
	}

	@Override
	public void failed(final Throwable exc, final String attachment) {
		// should never b here
		exc.printStackTrace();
	}

	@Override
	protected Collection<MessageFilter> getFilters() {
		// only accept FindNodeRequests messages
		return Arrays.asList(new MessageFilter[] { new TypeMessageFilter(FindNodeRequest.class) });
	}
}
