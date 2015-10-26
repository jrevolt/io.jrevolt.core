package io.jrevolt.core.msg;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Message {

    String value();

}
