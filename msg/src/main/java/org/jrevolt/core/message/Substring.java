package org.jrevolt.core.message;

import java.nio.CharBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
class Substring implements CharSequence {

	String source;
	int start;
	int end;

	Substring(String source) {
		this.source = source;
		this.start = 0;
		this.end = source.length();
	}

	Substring(String source, int start, int end) {
		this.source = source;
		this.start = start;
		this.end = end;
	}

	///

	@Override
	public int length() {
		return end - start;
	}

	@Override
	public char charAt(int index) {
		return source.charAt(start + index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return source.subSequence(this.start + start, this.end + end);
	}

	@Override
	public String toString() {
		return source.substring(start, end);
	}

	///

	Substring copy() {
		return new Substring(source, start, end);
	}

	Substring seek(Pattern pattern) {
		return seek(pattern, true);
	}

	Substring seek(Pattern pattern, boolean skip) {
		Matcher m = pattern.matcher(this);
		assertFind(m);
		int ostart = start;
		start += m.start();
		if (skip) {
			start += (m.end() - m.start());
		}
		return new Substring(source, ostart, start);
	}

	void seek(Substring s, boolean skip) {
		assert isSameSource(s);
		start = s.end;
		if (skip) {
			start += s.length();
		}
	}

	/**
	 * Forwards this Substring to the beginning of the given reference, and returns the substring representing the
	 * forwared (discarded) part.
	 */
	Substring forward(Substring s) {
		assert isSameSource(s);
		int fwdstart = start;
		int fwdend = s.start;
		start = s.start;
		return new Substring(source, fwdstart, fwdend);
	}

	Substring substring(int start, int end) {
		return new Substring(source, this.start + start, this.start + end);
	}

	Substring substring(int start) {
		return substring(this.start + start, end);
	}

	boolean startsWith(String s) {
		return source.startsWith(s, start);
	}

	boolean matches(Pattern pattern) {
		return pattern.matcher(this).matches();
	}

	void skip(String s) {
		assert startsWith(s);
		start += s.length();
	}

	void skipAll(String s) {
		while (startsWith(s)) {
			start += s.length();
		}
	}

	boolean isSameSource(Substring s) {
		//noinspection StringEquality
		return source == s.source;
	}

	///

	public CharBuffer put(CharBuffer buf) {
//        buf.append(toString());
		for (int i = start; i < end; i++) {
			buf.put(source.charAt(i));
		}
		return buf;
	}

	char[] toChars() {
		char[] buf = new char[length()];
		source.getChars(start, end, buf, 0);
		return buf;
	}

	///

	void assertFind(Matcher matcher) {
		if (!matcher.find()) {
			throw new AssertionError(
					String.format("No match! Looking for \"%s\" in \"%s\"", matcher.pattern(), toString()));
		}
	}

}
