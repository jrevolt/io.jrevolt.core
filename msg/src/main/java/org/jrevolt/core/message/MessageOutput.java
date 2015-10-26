package org.jrevolt.core.message;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public abstract class MessageOutput {

	public abstract void write(char[] chars);

	public void write(String s) {
		char[] buf = getBuffer(s.length());
		s.getChars(0, s.length(), buf, 0);
		write(buf);
	}

	public void write(StringBuilder sb) {
		char[] buf = getBuffer(sb.length());
		sb.getChars(0, sb.length(), buf, 0);
		write(buf);
	}

	protected char[] getBuffer(int length) {
		return new char[length];
	}

}
