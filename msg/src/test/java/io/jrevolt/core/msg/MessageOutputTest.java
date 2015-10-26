package io.jrevolt.core.msg;

import io.jrevolt.core.msg.MessageOutputWriter;
import io.jrevolt.core.msg.StringMessageOutput;

import org.junit.Test;

import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MessageOutputTest {

	@Test
	public void messageOutputWriter() throws Exception {
		StringWriter sw = new StringWriter();
		MessageOutputWriter out = new MessageOutputWriter(sw);
		out.write("LOL");
		out.close();
		String s = sw.toString();
		assertEquals("LOL", s);
	}

	@Test
	public void stringMessageOutput() {
		StringMessageOutput out = new StringMessageOutput();
		out.write("LOL");
		assertEquals("LOL", out.asString());
	}

}
