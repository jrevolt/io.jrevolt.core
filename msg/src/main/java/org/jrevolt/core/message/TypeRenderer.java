package org.jrevolt.core.message;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public abstract class TypeRenderer<T> {

	public abstract String getName();

	public abstract TypeRenderer<T> create(List<String> properties);

	public void render(MessageOutput out, T value) {
		out.write(value.toString());
	}

	public void renderNull(Writer out) throws IOException {
		out.write("null");
	}


}
