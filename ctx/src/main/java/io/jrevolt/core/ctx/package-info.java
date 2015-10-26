/**
 * Stack-based context management support library.
 *
 * <h2>Context</h2>
 *
 * Context is a structure attached to a given thread of execution which provides stack-based storage
 * for arbitrary context objects. 
 *
 * <h2>Context Objects</h2>
 *
 * Context objects are primarily data structures attached to and accesiable via a given context.
 * Anyway, there is no Data-Only! restriction.
 * <p>
 * Context objects must implement interface {@link io.jrevolt.core.ctx.ContextObject}.
 * The motivation behind this requirement is support for type classification, IDE autocompletion, etc.
 *
 * <h3>Context Lifecycle</h3>
 *
 * <code><pre>
 *     ContextManager.newContext();
 *     try {
 *         // work with context
 *     } finally {
 *         // must be closed in the same method as created !
 *         ContextManager.closeContext();
 *         // all context objects in the current context are released
 *     }
 * </pre></code>
 *
 * <h3>Accessing Context</h3>
 *
 * <code><pre>
 *     // access context
 *     ContextManager.context();
 *
 *     // store arbitrary context object in the current context, use the object type as the key
 *     ContextManager.context().set(SomeContextObject.class, new SomeContextObject());
 *
 *     // obtain concrete context object of a given type from current context
 *     SomeContextObject sco = ContextManager.context().get(SomeContextObject.class);
 *
 *     // remove context object from the current context
 *     ContextManager.context().remove(SomeContextObject.class);
 *     // or just forget it - it will be released on closeContext()
 * </pre></code>
 *
 * <h2>Services</h2>
 *
 * Services are general purpose use-case oriented objects (funtions, methods, operations),
 * usually stateless (but there is no such restriction enforced).
 * <p>
 * Services are managed and provided by special dedicated context object called
 * {@link io.jrevolt.core.ctx.ServiceManager ServiceManager}.
 *
 * <h3>Registering Services</h3>
 *
 * <code><pre>
 *     # resource META-INF/org.jrevolt/services.properties accessible by context classloader
 *     # syntax:
 *     # interface.class.name=implementation.class.name
 *     mypackage.MyServiceInterface=impl.MyServiceImplementation
 * </pre></code>
 *
 * <h3>Accessing Services</h3>
 *
 * <code><pre>
 *     MyServiceInterface service = ServiceManager.service(MyServiceInterface.class);
 * </pre></code>
 *
 * @see io.jrevolt.core.ctx.ContextManager
 * @see io.jrevolt.core.ctx.ServiceManager
 *
 * @author Patrik Beno
 */
package io.jrevolt.core.ctx;

