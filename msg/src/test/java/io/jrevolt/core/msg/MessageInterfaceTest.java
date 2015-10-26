package io.jrevolt.core.msg;

import io.jrevolt.core.msg.Message;
import io.jrevolt.core.msg.MessageProxy;

import org.junit.Test;

import java.time.Instant;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MessageInterfaceTest {

    interface MyMessages {
//        @Message("User ${username} logged in at ${time}")
        @Message("On ${time}, user ${username} logged in!")
        String userLoggedIn(String username, Instant time);
    }

    @Test
    public void test() {
        MyMessages messages = MessageProxy.getMessageProxy(MyMessages.class);
        Object o = messages.userLoggedIn("johndoe", Instant.now());
        System.out.println(o);
    }

}
