package io.jrevolt.core.msg;

import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.CharBuffer;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class PerfTest {

	String DATA = "######################################################################";
	int count = 1000;// * 1000 * 1000;

	@BeforeClass
	static public void before() {
		PerfTest pt = new PerfTest();
		pt.count = pt.count / 100;
		pt.charBuffer();
		pt.stringBuilder();
	}

	@Test
	public void charBuffer() {
//        CharBuffer cbuf = ByteBuffer.allocateDirect(32 * 1024).asCharBuffer();
		CharBuffer cbuf = CharBuffer.allocate(32 * 1024);
		char[] chars = DATA.substring(0, 32).toCharArray();
		for (int i = 0; i < count; i++) {
			cbuf.clear();
			DATA.getChars(0, 32, chars, 0);
			cbuf.put(chars);
		}
		cbuf.toString();
	}

	@Test
	public void stringBuilder() {
		StringBuilder sb = new StringBuilder(32 * 1024);
		char[] chars = DATA.substring(0, 32).toCharArray();
		for (int i = 0; i < count; i++) {
			sb.setLength(0);
			DATA.getChars(0, 32, chars, 0);
			sb.append(chars);
		}
		sb.toString();
	}

}
