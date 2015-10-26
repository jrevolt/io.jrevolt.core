package io.jrevolt.core.ctx;

import static io.jrevolt.core.util.Assert.doAssert;
import io.jrevolt.core.util.Assert;
import io.jrevolt.core.util.ExceptionHelper;
import io.jrevolt.core.util.IOHelper;
import io.jrevolt.core.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public class Context {

	static private final int STACKTRACE_OFFSET = 4;

	protected Context parent;
    protected Thread owner;

    protected Map<Class, Object> items;

    protected StackTraceElement creator;

    protected List<Context> forks;

    boolean done;
    final Object sync = new Object();

	{
		items = new HashMap<>();
	}

	public Context(Context parent) {
		this.parent = parent;
        this.owner = Thread.currentThread();
        this.creator = Thread.currentThread().getStackTrace()[STACKTRACE_OFFSET];
        if (isForkedSubContext()) {
            parent.registerForkedSubContext(this);
        }
    }

    public boolean isDone() {
        return done;
    }

	public <T extends ContextObject> T get(Class<T> type) {
		T t = type.cast(items.get(type));
		if (t == null && parent != null) {
			t = parent.get(type);
		}
		return t;
	}

	public <T extends ContextObject> T set(Class<T> type, T object) {
		items.put(type, Assert.typecast(object, type));
		return object;
	}

	public <T extends ContextObject> void remove(Class<T> type) {
		items.remove(type);
	}

	protected void authorizeClose() {
        doAssert(owner == Thread.currentThread(), "Only owner can close context: %s", owner);
		StackTraceElement ste = Thread.currentThread().getStackTrace()[STACKTRACE_OFFSET];
		doAssert(ste.getClassName().equals(creator.getClassName()) && ste.getMethodName().equals(creator.getMethodName()),
					"Unauthorized caller (%s). Context can be closed by its creator only (%s)", ste, creator);
	}

	protected void close() {
        synchronized (sync) {
            if (hasForkedSubContexts()) {
                //noinspection SynchronizeOnNonFinalField
                synchronized (forks) {
                    Log.debug("Closing context %s. Waiting for completion of %d subcontexts...", forks.size());
                    for (Context subcontext : forks) {
                        subcontext.await();
                    }
                    forks.clear();
                    forks = null;
                }
            }
            for (Object o : items.values()) {
                IOHelper.closeSilently(o);
            }
            if (isForkedSubContext()) {
                parent.clearForkedSubContext(this);
            }

            done = true;
            sync.notifyAll();
        }
    }

    public void await() {
        if (isDone()) { return; }
        doAssert(Thread.currentThread() != owner, "Preventing deadlock! Cannot await() context completion within the context itself.");
        synchronized (sync) {
            while (hasForkedSubContexts()) {
                for (Context subctx : forks) {
                    subctx.await();
                }
            }
            while (!isDone()) {
                ExceptionHelper.wait(sync);
            }
        }
    }

    protected boolean hasParent() {
        return parent != null;
    }

    protected boolean hasForkedSubContexts() {
        return forks != null && !forks.isEmpty();
    }

    protected boolean isForkedSubContext() {
        return hasParent() && owner != parent.owner;
    }

    synchronized
    protected void registerForkedSubContext(Context context) {
        if (forks == null) { forks = new LinkedList<>(); }
        forks.add(context);
    }

    synchronized
    protected void clearForkedSubContext(Context context) {
        if (hasForkedSubContexts()) {
            forks.remove(context);
        }
    }

}
