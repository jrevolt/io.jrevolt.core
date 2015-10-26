package io.jrevolt.core.event.impl;

import io.jrevolt.core.event.Event;
import io.jrevolt.core.event.Events;
import io.jrevolt.core.util.Assert;
import io.jrevolt.core.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class EventDispatcherProxy implements InvocationHandler {

	Class<? extends Event> type;
	Event proxy;

	public EventDispatcherProxy(Class<? extends Event> type) {
		this.type = type;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (method.getDeclaringClass() != type) {
			Method m = getClass().getMethod(method.getName(), method.getParameterTypes());
			return m.invoke(this, args);
		}

		List<? extends Event> listeners = Events.eventManager().getListeners(type);
		if (!listeners.isEmpty()) {
			Log.debug("Dispatching event %s.%s to %s listeners", type.getSimpleName(), method.getName(), listeners.size());
		}

		for (Event e : listeners) {
			invoke(e, method, args);
		}

		return null;
	}

	protected void invoke(Event listener, Method method, Object[] args) {
		try {
			method.invoke(listener, args);
		} catch (IllegalAccessException e) {
			throw Assert.unexpected(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Log.warn("Error dispatching event %s.%s. Failing listener: %s. Error: %s",
						type.getSimpleName(), method.getName(), listener, e.getCause());
		}
	}

	public Event getProxy() {
		if (proxy == null) {
			proxy = (Event) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type }, this);
		}
		return proxy;
	}
}
