package io.jrevolt.core.msg;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MessageProxy implements InvocationHandler {

    static public <T> T getMessageProxy(Class<T> messages) {
        return messages.cast(Proxy.newProxyInstance(
                messages.getClassLoader(), new Class[] {messages}, new MessageProxy()));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MessageParser parser = new MessageParser(method);
        MessageDef mdef = parser.parse();
        if (MessageDef.class.isAssignableFrom(method.getReturnType())) {
            return mdef;
        } else if (MessageImpl.class.isAssignableFrom(method.getReturnType())) {
            return new MessageImpl(method, args);
        } else if (String.class.isAssignableFrom(method.getReturnType())) {
            return new MessageImpl(method, args).render();
        }
        return null;
    }
}
