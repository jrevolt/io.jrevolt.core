package org.jrevolt.core.message;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class StringMessageOutput extends MessageOutput {

	StringBuilder sb = new StringBuilder(1024);

	@Override
	public void write(char[] chars) {
		sb.append(chars);
	}

	public String asString() {
		return sb.toString();
	}
}
