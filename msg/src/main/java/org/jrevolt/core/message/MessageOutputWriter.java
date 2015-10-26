package org.jrevolt.core.message;

import java.io.IOException;
import java.io.Writer;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MessageOutputWriter extends MessageOutput implements AutoCloseable {

	protected Writer out;

	public MessageOutputWriter(Writer out) {
		this.out = out;
	}

	@Override
	public void write(char[] chars) {
		try {
			out.write(chars);
		} catch (IOException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public void close() throws Exception {
		out.close();
	}
}
