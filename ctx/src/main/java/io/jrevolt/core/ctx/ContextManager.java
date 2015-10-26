package io.jrevolt.core.ctx;

import static io.jrevolt.core.util.Assert.NOTNULL;
import static io.jrevolt.core.util.Assert.doAssert;

/**
 * @author Patrik Beno
 */
public class ContextManager {

	static private final ContextManager INSTANCE = new ContextManager();

	static public ContextManager contextManager() {
		return INSTANCE;
	}

	static public Context newContext() {
		return contextManager().beginNewContext();
	}

    static public Context newContext(Context parent) {
        return contextManager().beginNewContext(parent);
    }

	static public Context closeContext() {
		return contextManager().closeCurrentContext();
	}

	static public Context context() {
		return contextManager().getCurrentContext();
	}

	static public <T extends ContextObject> T context(Class<T> type) {
		return contextManager().getCurrentContext().get(type);
	}

	static public void executeWithContext(ContextRunner ctxrunner) {
		newContext();
		try {
			ctxrunner.init();
			ctxrunner.execute();
		} finally {
			try {
				ctxrunner.close();
			} finally {
				closeContext();
			}
		}
	}
	
	public boolean hasContext() {
		return tlContext.get() != null;
	}

	///

	ThreadLocal<Context> tlContext;

	{
		tlContext = new ThreadLocal<>();
	}

	///

	protected Context beginNewContext() {
		Context ctx = new Context(tlContext.get());
		tlContext.set(ctx);
		return ctx;
	}

    protected Context beginNewContext(Context parent) {
        doAssert(!hasContext(),
                "Cannot create forked subcontext: current thread is already running within a context. Thread: %s. Context: %s.",
                Thread.currentThread(), tlContext.get());
        Context ctx = new Context(parent);
        tlContext.set(ctx);
        return ctx;
    }

	protected Context closeCurrentContext() {
		Context ctx = getCurrentContext();

		ctx.authorizeClose();

		ctx.close();

		ctx = ctx.parent;
		tlContext.set(ctx);

		return ctx;
	}

	public Context getCurrentContext() {
		return NOTNULL(tlContext.get(), "No Context!");
	}


}
