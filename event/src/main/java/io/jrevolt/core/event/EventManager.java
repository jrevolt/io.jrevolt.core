package io.jrevolt.core.event;

import io.jrevolt.core.ctx.Service;

import java.util.List;

/**
 * @author Patrik Beno
 */
public interface EventManager extends Service {

	<T extends Event> void registerEventListener(Class<T> type, T listener);

	<T extends Event> void unregisterEventListener(T listener);

	<T extends Event> List<T> getListeners(Class<T> type);

	<T extends Event> T getEventDispatcher(Class<T> type);
}
