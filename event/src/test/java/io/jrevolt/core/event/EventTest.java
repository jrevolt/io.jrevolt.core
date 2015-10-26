package io.jrevolt.core.event;

import static io.jrevolt.core.ctx.ContextManager.*;
import io.jrevolt.core.ctx.ContextObject;
import static io.jrevolt.core.event.Events.events;
import io.jrevolt.core.util.Log;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Patrik Beno
 */
public class EventTest {

	static public interface ITestEvents extends Event {
		void testEvent();
	}

	static public class CTestEvents implements Event {
		public void testEvent() {}
	}

	static class Ctx implements ContextObject {
		int listenerInvocations;
	}

	@Test
	public void sendEvent() {
		newContext();
		try {
			events(ITestEvents.class).testEvent();
		} finally {
			closeContext();
		}
	}

	@Test
	public void interfaceEvents() {
		newContext();
		try {
			context().set(Ctx.class, new Ctx());

			Events.registerEventListener(ITestEvents.class, listener());
			Events.registerEventListener(ITestEvents.class, listener());

			Events.registerEventListener(ITestEvents.class, () -> System.out.println("Handled, vole!"));

			events(ITestEvents.class).testEvent();

			assert context(Ctx.class).listenerInvocations == 2;

		} finally {
			closeContext();
		}
	}

	@Test @Ignore
	public void classEvents() {
		newContext();
		try {
			context().set(Ctx.class, new Ctx());

			Events.registerEventListener(CTestEvents.class, clistener());
			Events.registerEventListener(CTestEvents.class, clistener());

			events(CTestEvents.class).testEvent();

			assert context(Ctx.class).listenerInvocations == 2;

		} finally {
			closeContext();
		}
	}

	ITestEvents listener() {
		return () -> {
            context(Ctx.class).listenerInvocations++;
            Log.debug("ITestEvents.testEvent(): in listener !");
        };
	}

	CTestEvents clistener() {
		return new CTestEvents() {
			public void testEvent() {
				context(Ctx.class).listenerInvocations++;
				Log.debug("CTestEvents.testEvent(): in listener !");
			}
		};
	}

}
