package io.jrevolt.core.util;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class ExceptionHelper {

    static public void wait(Object o) {
        try {
            o.wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Assert.logNotYetHandled(e);
        }
    }



}
