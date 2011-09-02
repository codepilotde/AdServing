package net.mad.ads.server.utils.listener;

import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryEvicted;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryInvalidated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryInvalidatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;

@Listener
public class CacheListener {

	@CacheEntryCreated
	public void create(CacheEntryCreatedEvent event) {
	}

	@CacheEntryRemoved
	public void remove(CacheEntryRemovedEvent event) {
	}

	@CacheEntryInvalidated
	public void invalid(CacheEntryInvalidatedEvent event) {
	}
}
