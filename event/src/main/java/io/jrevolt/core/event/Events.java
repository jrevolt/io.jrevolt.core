package io.jrevolt.core.event;

import static io.jrevolt.core.ctx.ServiceManager.service;

/**
 * @author Patrik Beno
 */
public class Events {

	static public EventManager eventManager() {
		return service(EventManager.class);
	}

	static public <T extends Event> T events(Class<T> type) {
		return eventManager().getEventDispatcher(type);
	}

	static public <T extends Event> void registerEventListener(Class<T> type, T listener) {
		eventManager().registerEventListener(type, listener);
	}
	              
	static public <T extends Event> void unregisterEventListener(T listener) {
		eventManager().unregisterEventListener(listener);
	}

}
