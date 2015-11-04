package com.konka.dhtsearch.bittorrentkad.handlers;

import java.util.Collection;

import com.konka.dhtsearch.bittorrentkad.concurrent.CompletionHandler;
import com.konka.dhtsearch.bittorrentkad.krpc.KadMessage;
import com.konka.dhtsearch.bittorrentkad.net.MessageDispatcher;
import com.konka.dhtsearch.bittorrentkad.net.filter.MessageFilter;

/**
 * Base class for all incoming message handlers
 * 
 * @author eyal.kibbar@gmail.com
 *
 */
public abstract class AbstractHandler implements CompletionHandler<KadMessage, String> {

	private final MessageDispatcher  msgDispatcherProvider;

	protected AbstractHandler(MessageDispatcher  msgDispatcherProvider) {
		this.msgDispatcherProvider = msgDispatcherProvider;
	}

	/**
	 * @return all the filters associated with this handler
	 */
	protected abstract Collection<MessageFilter> getFilters();

	/**
	 * Register this handler for start receiving messages
	 */
	public void register() {
		MessageDispatcher  dispatcher = msgDispatcherProvider;

		for (MessageFilter filter : getFilters()) {
			dispatcher.addFilter(filter);
		}

//		dispatcher.setConsumable(false).setCallback(null, this).register();
	}
}
